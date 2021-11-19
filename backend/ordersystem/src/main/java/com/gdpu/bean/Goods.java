package com.gdpu.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Goods implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "goods_id", type = IdType.AUTO)
    private Integer goodsId;

    private Integer pid;

    private String name;

    private String imageUrl;

    private Integer price;

    private String packages;


    private Integer goodsNum;

    private Integer menuNum;

    @TableField(exist = false)
    private Integer available;

    @TableField(exist = false)
    private String title;

    @TableField(exist = false)
    private Integer shopId;

    @TableField(exist = false)
    private String address;

    @TableField(exist = false)
    private Integer roleId;

}
