package com.gdpu.vo;

import com.gdpu.bean.Admin;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AdminVo extends Admin {
    private static final Long serialVersionUID = 1L;


    private Integer page = 1;
    private Integer limit = 10;
}
