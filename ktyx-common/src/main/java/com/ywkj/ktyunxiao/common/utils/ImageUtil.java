package com.ywkj.ktyunxiao.common.utils;

import com.ywkj.ktyunxiao.common.config.SystemConfig;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * 图片工具类
 * @author LiWenHao
 * @date 2017/2/7 11:11
 */
@Slf4j
@Component
public class ImageUtil {

    private static String previewPath;

    @Value("${file.preview-path}")
    public void setPreviewPath(String previewPath) {
        ImageUtil.previewPath = previewPath;
    }

    /**
     * 生成缩略图
     * @param fileUrl   原始文件地址
     * @return
     */
    public static String createPreviewImg(String fileUrl) {
        File file = new File(previewPath);
        //如果目录不存在就创建
        if(!file.exists()){
            if(!file.mkdir()){
                throw new RuntimeException("图片预览图路径创建失败！");
            }
        }
        //创建缩略图的名称
        String previewUrl = FileUtil.createNewFileName(fileUrl);
        try {
//            Thumbnails.of(fileUrl).scale(SystemConfig.scaleSize).toFile(previewPath + previewUrl);
            Thumbnails.of(fileUrl).size(100,100).keepAspectRatio(false).toFile(previewPath + previewUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("预览图生成成功！");
        return previewUrl;
    }

    /**
     * 获取缩略图地址
     * @return
     */
    public static String getPreviewPath() {
        return previewPath;
    }
}
