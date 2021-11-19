package com.gdpu.vo;


import com.gdpu.bean.Push;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PushVo extends Push {

    private static final Long serialVersionUID = 1L;


    private Integer page = 1;
    private Integer limit = 10;
}
