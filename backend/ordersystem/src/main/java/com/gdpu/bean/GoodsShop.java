package com.gdpu.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsShop implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer shopId;

    private Integer goodsId;

    private Integer available;

//    @TableField(exist = false)
//    private Integer goods_num;
//
//    @TableField(exist = false)
//    private Integer menu_num;
}
