package com.gdpu.bean;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsPackages implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer goodsId;

    private Integer packageId;

    private String packageName;


}
