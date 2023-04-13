package com.zhixing.campus.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author WGM
 * @since 2023-04-13
 */
@Getter
@Setter
  @ApiModel(value = "Help对象", description = "")
public class Help implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("求助id号")
        private Integer id;

      @ApiModelProperty("求助发起时间")
      private LocalDateTime starttime;

      @ApiModelProperty("求助时效（即求助发布多长时间后失效）")
      private Integer time;

      @ApiModelProperty("求助的内容")
      private String doing;

      @ApiModelProperty("完成求助的酬劳")
      private Integer pay;

      @ApiModelProperty("求助发布人")
      private Integer people;


}
