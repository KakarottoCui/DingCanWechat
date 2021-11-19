package com.gdpu.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {

    private Integer code;
    private String msg;

    public static final ResultObj LOGIN_SUCCESS = new ResultObj(Constast.SUCCESS,"登录成功");
    public static final ResultObj LOGIN_ERROR_PASS = new ResultObj(Constast.ERROR,"登录失败，用户名或密码不正确");
    public static final ResultObj LOGIN_ERROR_CODE = new ResultObj(Constast.ERROR,"登录失败，验证码不正确");


    public static final ResultObj ADD_SUCCESS = new ResultObj(Constast.SUCCESS,"添加成功");
    public static final ResultObj ADD_ERROR = new ResultObj(Constast.ERROR,"添加失败");
    public static final ResultObj ADD_ERROR_EXIST = new ResultObj(Constast.ERROR,"注册失败，用户名已存在");

    public static final ResultObj DELETE_SUCCESS = new ResultObj(Constast.SUCCESS,"删除成功");
    public static final ResultObj DELETE_ERROR = new ResultObj(Constast.ERROR,"删除失败");
    public static final ResultObj DELETE_ERROR_STOCK = new ResultObj(Constast.ERROR,"仓库中还有存货，无法删除仓库");
    public static final ResultObj UPDATE_SUCCESS = new ResultObj(Constast.SUCCESS,"修改成功");
    public static final ResultObj UPDATE_ERROR = new ResultObj(Constast.ERROR,"修改失败");
    public static final ResultObj UPDATE_ERROR_EXCEED = new ResultObj(Constast.ERROR,"修改失败,当前库存超出新的预警上限");
    public static final ResultObj UPDATE_ERROR_UNDER = new ResultObj(Constast.ERROR,"修改失败，当前库存低于新的预警上限");

    public static final ResultObj ADD_ERROR_EXCEED = new ResultObj(Constast.ERROR,"库存超过上限，入库失败");
    public static final ResultObj ADD_ERROR_UNDER = new ResultObj(Constast.ERROR,"库存低于下限，出库失败");

    public static final ResultObj ERROR_ZERO = new ResultObj(Constast.ERROR,"数量不能为0");

    public static final ResultObj UPLOAD_SUCCESS = new ResultObj(Constast.SUCCESS,"上传成功");
    public static final ResultObj UPLOAD_ERROR = new ResultObj(Constast.ERROR,"上传失败");

    public static final ResultObj FINISH_SUCCESS = new ResultObj(Constast.SUCCESS,"成功");
    public static final ResultObj FINISH_ERROR = new ResultObj(Constast.ERROR,"失败");

}
