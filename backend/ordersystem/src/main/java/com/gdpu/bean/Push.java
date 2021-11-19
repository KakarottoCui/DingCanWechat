package com.gdpu.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Push implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "push_id", type = IdType.AUTO)
    private Integer pushId;

    private String navigatorUrl;

    private String imageUrl;

    /**
     * 0为轮播类型推送，1为取餐方式，2为资讯类型推送
     */
    private Integer type;
    /*
     * 资讯的说明文本
     */
    private String text;

}
