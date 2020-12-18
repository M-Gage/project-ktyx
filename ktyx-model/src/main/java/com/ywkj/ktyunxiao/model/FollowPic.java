package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 跟进图片模型
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FollowPic {

  /**
   * 跟进图片Id
   */
  private Integer followPicId;

  /**
   *跟进id
   */
  private String followId;
  /**
   * 图片路径
   */
  private String picPath;
  /**
   *预览图路径
   */
  private String previewPath;

  /**
   *公司id
   */
  private String companyId;

  public FollowPic() {
  }

  public FollowPic(String followId, String picPath, String previewPath,String companyId) {
    this.followId = followId;
    this.picPath = picPath;
    this.previewPath = previewPath;
    this.companyId = companyId;
  }
}
