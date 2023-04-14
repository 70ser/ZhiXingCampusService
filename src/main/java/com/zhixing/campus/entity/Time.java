package com.zhixing.campus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalTime;
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
 * @since 2023-04-11
 */
@Getter
@Setter
  @ApiModel(value = "Time对象", description = "")
public class Time implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("自习id号")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户id号")
      private Integer userid;

      @ApiModelProperty("自习开始时间")
      private LocalTime timestart;

      @ApiModelProperty("自习结束时间")
      private LocalTime timeover;

      @ApiModelProperty("自习地址")
      private String address;

      @ApiModelProperty("自习科目")
      private String subject;
}
