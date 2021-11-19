package com.gdpu.vo;


import com.gdpu.bean.OrderForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderFormVo extends OrderForm {
    private static final Long serialVersionUID = 1L;
    private Integer page = 1;
    private Integer limit = 10;


}
