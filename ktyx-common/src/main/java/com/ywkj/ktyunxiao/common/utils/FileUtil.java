package com.ywkj.ktyunxiao.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文件工具类
 *
 * @author LiWenHao
 * @date 2018/1/3 15:30
 */
@Slf4j
@Component
public class FileUtil {

    private static String serverFilePath;

    @Value("${file.upload-Path}")
    public void setServerFilePath(String serverFilePath) {
        FileUtil.serverFilePath = serverFilePath;
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @return
     */
    public static Map<String, Object> fileUpload(MultipartFile file) {
        Map<String, Object> map = new HashMap<>(16);
        //原始文件名
        String oldFileName = file.getOriginalFilename();
        //判断文件不为空
        if (!file.isEmpty()) {
            File dir = new File(serverFilePath);
            //如果路径文件夹不存在就创建！
            if (!dir.exists()) {
                if(!dir.mkdir()){
                    throw new RuntimeException("服务器文件路径创建失败！");
                }
            }
            log.info("==> fileUpload: 原文件名 {}", oldFileName);
            //新文件名
            String newFileName = createNewFileName(oldFileName);
            log.info("==> fileUpload: 新文件名 {}", newFileName);
            File serverFile = new File(serverFilePath + newFileName);
            try {
                file.transferTo(serverFile);
                log.info("==> fileUpload: 上传成功！地址 {}", serverFile);
                map.put("success", true);
                map.put("newFileName", newFileName);
                map.put("fileUrl", serverFilePath);
            } catch (IOException e) {
                log.error("==> fileUpload: 上传失败！{}", e.getMessage());
                map.put("success", false);
            }
        } else {
            log.warn("==> fileUpload: 文件大小为空,文件名 {}", oldFileName);
            map.put("success", false);
        }
        return map;
    }


    /**
     * 批量上传文件
     *
     * @param files 文件数组
     * @return
     */
    public static List<Map<String, Object>> batchFileUpload(MultipartFile[] files) {
        Stream<MultipartFile> stream = Arrays.stream(files);
        return stream.map(FileUtil::fileUpload).collect(Collectors.toList());
    }

    /**
     * 生成新的文件名
     *
     * @param oldFileName 原始的文件名
     * @return
     */
    public static String createNewFileName(String oldFileName) {
        return System.currentTimeMillis() + "-" + StringUtil.getUUID() + "." + getFileSuffix(oldFileName);
    }

    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    /**
     * 文件下载
     *
     * @return
     */
    public static void fileDown(HttpServletResponse response, String fileName) {
        InputStream stream = FileUtil.class.getClassLoader().getResourceAsStream("static/excel/" + fileName);
        // 设置强制下载不打开
        response.setContentType("application/force-download");
        // 设置文件名
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            bis = new BufferedInputStream(stream);
            os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
