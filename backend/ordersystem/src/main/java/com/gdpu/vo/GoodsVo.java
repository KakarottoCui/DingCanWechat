package com.gdpu.vo;

import com.gdpu.bean.Goods;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsVo extends Goods {
    private static final Long serialVersionUID = 1L;


    private long page = 1;
    private long limit = 10;
}
