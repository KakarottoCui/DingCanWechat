package com.gdpu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys")
public class SystemController {

    @RequestMapping("toLogin")
    public String toLogin(){
        return "/index/login";
    }

    @RequestMapping("index")
    public String index(){
        return "/index/index";
    }
    @RequestMapping("toRoleManager")
    public String toRoleManager(){
        return "/role/roleManager";
    }

    /**
     * 跳转到用户管理
     * @return
     */
    @RequestMapping("toUserManager")
    public String toUserManager(){
        return "/user/userManager";
    }

    @RequestMapping("toShopManager")
    public String toShopManager(){
        return "/shop/shopManager";
    }

    @RequestMapping("toGoodsManager")
    public String toGoodsManager(){
        return "/goods/goodsManager";
    }

    @RequestMapping("toOrderManager")
    public String toOrderManager(){
        return "order/orderManager";
    }

}
