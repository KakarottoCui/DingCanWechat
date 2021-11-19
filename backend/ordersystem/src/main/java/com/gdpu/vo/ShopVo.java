package com.gdpu.vo;

import com.gdpu.bean.Shop;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShopVo extends Shop {
    private static final long serialVersionUID = 1L;
    private Integer page = 1;
    private Integer limit = 10;
}
