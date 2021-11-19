package com.gdpu.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdpu.bean.Admin;
import com.gdpu.common.ActiveUser;
import com.gdpu.service.AdminService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class UserRealm extends AuthorizingRealm {


    @Resource
    private AdminService adminService;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /*
    *   授权
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
    /*
    *   认证
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //mybatisplus3.2.0的条件构造器QueryWrapper<TbUser>用于筛选相同的账号
        QueryWrapper<Admin> queryWrapper = new QueryWrapper();
        //在数据库中筛选账号相同的user
        queryWrapper.eq("account",token.getPrincipal().toString());
        Admin admin = adminService.getOne(queryWrapper);
        if(null != admin){
            //新建活动用户
            ActiveUser activeUser = new ActiveUser();
            activeUser.setAdmin(admin);
            //获取数据库中该用户的盐，盐由md5加密算法生成
            ByteSource credentials = ByteSource.Util.bytes(admin.getSalt());
            //判断密码是否相同
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, admin.getPassword(),credentials,this.getName());
            return info;
        }
        return null;
    }
}
