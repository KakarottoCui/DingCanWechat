package com.gdpu.controller;


import com.gdpu.common.ActiveUser;
import com.gdpu.common.ResultObj;
import com.gdpu.common.WebUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;


@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("login")
    public ResultObj login(String loginName, String password){

        Subject subject = SecurityUtils.getSubject();
        //token认证
        AuthenticationToken token = new UsernamePasswordToken(loginName, password);
        try{
            //对用户进行认证登陆
            subject.login(token);
            //通过subject获取以认证活动的user
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            //将user存储到session中
            WebUtils.getSession().setAttribute("user",activeUser.getAdmin());
            Integer Id;
            if (activeUser.getAdmin().getRoleId()!=0){
                Id = activeUser.getAdmin().getRoleId();
            } else {
                Id=1;
            }
            WebUtils.getSession().setAttribute("shopId",Id);
//            WebUtils.getSession().setAttribute("doFlash",1);
            //将doFlash写入redis数据库中
            redisTemplate.opsForValue().set("doFlash","0");
            return ResultObj.LOGIN_SUCCESS;
        } catch (AuthenticationException e){
            e.printStackTrace();
            return ResultObj.LOGIN_ERROR_PASS;
        }
    }
}
