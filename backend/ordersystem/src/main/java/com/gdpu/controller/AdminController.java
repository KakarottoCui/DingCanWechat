package com.gdpu.controller;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdpu.bean.Admin;
import com.gdpu.common.DataGridView;
import com.gdpu.common.ResultObj;
import com.gdpu.service.AdminService;
import com.gdpu.vo.AdminVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    /*查询用户*/
    @RequestMapping("loadAllAdmin")
    public DataGridView loadAllAdmin(AdminVo adminVo){
        IPage<Admin> page = new Page<>(adminVo.getPage(),adminVo.getLimit());
        QueryWrapper<Admin> queryWrapper = new QueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(adminVo.getName()),"name",adminVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(adminVo.getAccount()),"account",adminVo.getAccount());
        queryWrapper.eq(StringUtils.isNotBlank(adminVo.getSex()),"sex",adminVo.getSex());
        adminService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @RequestMapping("addAdmin")
    public ResultObj addAdmin(AdminVo adminVo){
        try{
            String account = adminVo.getAccount();
            Admin admin = adminService.getByAccount(account);
            if(null != admin){
                return ResultObj.ADD_ERROR_EXIST;
            }
            //设置盐
            String salt = IdUtil.simpleUUID().toUpperCase();
            adminVo.setSalt(salt);
            //设置密码
            adminVo.setPassword(new Md5Hash(adminVo.getPassword(),salt,2).toString());
            //设置性别
            String sex = adminVo.getSex()=="1"?"男":"女";
            adminVo.setSex(sex);
            adminService.save(adminVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("updateAdmin")
    public ResultObj updateAdmin(AdminVo adminVo){
        try {
            String salt = adminService.getById(adminVo.getAdminId()).getSalt();
            adminVo.setPassword(new Md5Hash(adminVo.getPassword(),salt,2).toString());
//            //设置性别
            String sex = "1".equals(adminVo.getSex())?"男":"女";
            adminVo.setSex(sex);
            adminService.updateById(adminVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
    @RequestMapping("deleteAdmin")
    public ResultObj deleteAdmin(Integer id){
        try {
            this.adminService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}

