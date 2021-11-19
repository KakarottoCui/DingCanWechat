package com.gdpu.vo;

import com.gdpu.bean.GoodsShop;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsShopVo extends GoodsShop {
    private static final Long serialVersionUID = 1L;


    private Integer page = 1;
    private Integer limit = 10;
}
