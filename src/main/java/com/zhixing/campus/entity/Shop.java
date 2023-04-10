package com.zhixing.campus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ZC
 * @since 2023-04-10
 */
@Getter
@Setter
  @ApiModel(value = "Shop对象", description = "")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("商品id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("商品名")
      private String shopName;

      @ApiModelProperty("商品价格")
      private Object shopPrice;

      @ApiModelProperty("商品描述")
      private String shopDescription;

      @ApiModelProperty("商品发布人")
      private String shopOwner;


}
