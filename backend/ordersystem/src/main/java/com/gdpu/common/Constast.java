package com.gdpu.common;

public class Constast {

    /*状态码*/
    public static final Integer SUCCESS = 200;
    public static final Integer ERROR = -1;

    /*菜单权限类型，menu用来表明菜单,permission用来表明权限,存放于Permission表中*/
    public static final String TYPE_MENU = "menu";
    public static final String TYPE_PERMISSION = "permission";

    /*权限是否可用,存放于Permission表中*/
    public static final Integer AVAILABLE_TRUE = 1;
    public static final Integer AVAILABLE_FALSE = 0;
    /*用户类型*/
    public static final Integer USER_TYPE_SUPER = 0;
    public static final Integer USER_TYPE_NORMAL = 1;
    /*展开类型*/
    public static final Integer OPEN_TRUE = 1;
    public static final Integer OPEN_FALSE = 0;

    public static final Integer SUPER_USER_OWN = 4;

}
