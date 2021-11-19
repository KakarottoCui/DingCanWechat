package com.gdpu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdpu.bean.Admin;
import com.gdpu.bean.Shop;
import com.gdpu.common.DataGridView;
import com.gdpu.common.ResultObj;
import com.gdpu.common.WebUtils;
import com.gdpu.service.CountService;
import com.gdpu.service.ShopService;
import com.gdpu.vo.ShopVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/shop")
public class ShopController {
    @Resource
    private ShopService shopService;

        @RequestMapping("getAddress")
    public DataGridView getAddress(){

        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        List<Shop> list = shopService.list(queryWrapper);
        return new DataGridView(list);
    }

    @RequestMapping("switchShop")
    public ResultObj switchShop(Integer shopId){
        try{
            WebUtils.getSession().setAttribute("shopId",shopId);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("loadAllShop")
    public DataGridView loadAllShop(ShopVo shopVo){

        int shopId = Integer.parseInt(WebUtils.getSession().getAttribute("shopId").toString());
        Admin admin = (Admin)WebUtils.getSession().getAttribute("user");
        int roleId = admin.getRoleId();
        IPage<Shop> page = new Page<>(shopVo.getPage(),shopVo.getLimit());
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
//        System.out.println("shopVo = " + shopVo.getAddress());
//        System.out.println("shopVo = " + shopVo.getShopId());
        queryWrapper.eq(0!=roleId,"shop_id",roleId);
        queryWrapper.eq(null != shopVo.getShopId() && shopVo.getShopId()!=0,"shop_id",shopVo.getShopId());
        queryWrapper.like(StringUtils.isNotBlank(shopVo.getAddress()),"address",shopVo.getAddress());
        shopService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @RequestMapping("loadAllShopForSelect")
    public DataGridView loadAllShopForSelect(){
        int shopId = Integer.parseInt(WebUtils.getSession().getAttribute("shopId").toString());
        Admin admin = (Admin)WebUtils.getSession().getAttribute("user");
        int roleId = admin.getRoleId();
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq(1!=shopId,"shop_id",shopId);
        queryWrapper.eq(0!=roleId,"shop_id",roleId);
        List<Shop> list = shopService.list(queryWrapper);
        return new DataGridView((list));
    }

    @RequestMapping("updateShop")
    public ResultObj updateShop(ShopVo shopVo){
        try{
            shopService.updateById(shopVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    @RequestMapping("addShop")
    public ResultObj addShop(ShopVo shopVo){
        try{
            shopVo.setAdminId(0);
            shopService.save(shopVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("deleteShop")
    public ResultObj deleteShop(Integer id){
        try{

            shopService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

//    @RequestMapping("loadAll")
//    public DataGridView loadAll(){
//        IPage<Shop> page = new Page<>();
//        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
//        shopService.page(queryWrapper);
//    }
}

