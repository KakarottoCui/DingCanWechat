package com.gdpu.bean;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderForm implements Serializable {

    private static final long serialVersionUID=1L;

    //    private String openid;
    private Integer orderId;

    private String phone;

    private Integer goodsId;

    private Integer number;

    private Date orderTime;

    private String openId;

    private Integer shopId;

    private Integer priceSum;

    private Integer finish;

    @TableField(exist = false)
    private String bought;
    @TableField(exist = false)
    private String numbers;


    @TableField(exist = false)
    private Integer id;

}
