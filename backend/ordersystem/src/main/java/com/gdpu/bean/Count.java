package com.gdpu.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Count implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "count_id", type = IdType.AUTO)
    private Integer countId;

    private Integer orderId;

    private Integer shopId;

}
