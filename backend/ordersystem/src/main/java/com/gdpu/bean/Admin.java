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
public class Admin implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "admin_id", type = IdType.AUTO)
    private Integer adminId;

    private Integer roleId;

    private String account;

    private String password;

    private String name;

    private Integer age;

    private String sex;

    private String salt;


}
