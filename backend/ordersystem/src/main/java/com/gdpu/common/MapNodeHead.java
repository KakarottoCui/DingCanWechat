package com.gdpu.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapNodeHead {
    private Integer id;
    private Integer sum;
    private String shopname;
    private Date date;
    private String phone;
    private Integer finish;
    private String openId;
    private Integer shopId;
    private List<MapNode> children = new ArrayList<>();
    public MapNodeHead(Integer id,Integer sum,String shopname,Date date,String phone,Integer finish,String openId,Integer shopId){
        this.id = id;
        this.sum = sum;
        this.shopname = shopname;
        this.date = date;
        this.phone = phone;
        this.finish = finish;
        this.openId = openId;
        this.shopId = shopId;
    }
}
