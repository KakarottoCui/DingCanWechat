package com.gdpu.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapNode {
    private String goodsname;
    private String icon;
    private Integer number;
    private Integer price;

}
