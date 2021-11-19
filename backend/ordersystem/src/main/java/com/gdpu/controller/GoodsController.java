package com.gdpu.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdpu.bean.Admin;
import com.gdpu.bean.Goods;
import com.gdpu.bean.GoodsShop;
import com.gdpu.bean.Role;
import com.gdpu.common.AppFileUtils;
import com.gdpu.common.DataGridView;
import com.gdpu.common.ResultObj;
import com.gdpu.common.WebUtils;
import com.gdpu.service.GoodsService;
import com.gdpu.service.GoodsShopService;
import com.gdpu.service.RoleService;
import com.gdpu.vo.GoodsVo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.DateUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    GoodsService goodsService;

    @Resource
    GoodsShopService goodsShopService;

    @Resource
    RoleService roleService;

    @RequestMapping("loadAllGoods")
    public DataGridView loadAllGoods(GoodsVo goodsVo){
//        IPage<Goods> page = new Page<>(goodsVo.getPage(),goodsVo.getLimit());
//        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
//        queryWrapper.notIn("pid",-1);
//        queryWrapper.notIn("pid",0);
//        queryWrapper.notIn("pid",1);
//        Admin admin = (Admin)WebUtils.getSession().getAttribute("user");
//        int roleId = admin.getRoleId();
//        int shopId = Integer.parseInt(WebUtils.getSession().getAttribute("shopId").toString());
//        List<GoodsShop> list = goodsShopService.getGoodsByShopId(shopId);
//        for (GoodsShop goodsShop: list) {
//            queryWrapper.in("goods_id",goodsShop.getGoodsId());
////            int available = goodsShopService.getGoodsAvailable(goodsShop);
////            goodsVo.setAvailable(available);
//        }

        IPage<Goods> page = new Page<>(goodsVo.getPage(),goodsVo.getLimit());
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.notIn("pid",-1);
        queryWrapper.notIn("pid",0);
        queryWrapper.notIn("pid",1);
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getName()),"name",goodsVo.getName());
        queryWrapper.eq(goodsVo.getPrice()!=null,"price",goodsVo.getPrice());
        queryWrapper.eq(goodsVo.getPid()!=null&&goodsVo.getPid()!=0,"pid",goodsVo.getPid());
        goodsService.page(page,queryWrapper);
        List<Goods> list = page.getRecords();
        int shopId = Integer.parseInt(WebUtils.getSession().getAttribute("shopId").toString());
        Admin admin = (Admin)WebUtils.getSession().getAttribute("user");
        int roleId = admin.getRoleId();
        for(Goods goods : list){
//            goods.setRoleId(roleId);
            goods.setTitle(goodsService.getById(goods.getPid()).getName());

            GoodsShop goodsShop = new GoodsShop();
            goodsShop.setGoodsId(goods.getGoodsId());
            goodsShop.setShopId(shopId);
            int available = goodsShopService.getGoodsAvailable(goodsShop)==null?0:goodsShopService.getGoodsAvailable(goodsShop);
            goods.setAvailable(available);
        }
        page.setRecords(list);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @RequestMapping("loadAllTitleForSelect")
    public DataGridView loadAllTitleForSelect(){
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",1);
        return new DataGridView(goodsService.list(queryWrapper));
    }

    @RequestMapping("updateGoods")
    public ResultObj updateGoods(GoodsVo goodsVo){
        try {
            Integer pid = goodsService.getById(goodsVo.getGoodsId()).getPid();
            if(goodsVo.getPid() == 0){
                goodsVo.setPid(pid);
            }
            int shopId = Integer.parseInt(WebUtils.getSession().getAttribute("shopId").toString());
            GoodsShop goodsShop = new GoodsShop();
            goodsShop.setAvailable(goodsVo.getAvailable());
            goodsShop.setGoodsId(goodsVo.getGoodsId());
            goodsShop.setShopId(shopId);
            goodsShopService.updateGoodsAvailable(goodsShop);
            goodsService.updateById(goodsVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    @RequestMapping("addGoods")
    public ResultObj addGoods(GoodsVo goodsVo){
        try {
            goodsVo.setMenuNum(0);
            goodsVo.setGoodsNum(0);
            goodsVo.setImageUrl("https://s3.ax1x.com/2021/01/31/yAcwEn.png");
            goodsService.save(goodsVo);
            GoodsShop goodsShop = new GoodsShop();
            int shopId = Integer.parseInt(WebUtils.getSession().getAttribute("shopId").toString());
            goodsShop.setShopId(shopId);
            goodsShop.setGoodsId(goodsVo.getGoodsId());
            goodsShop.setAvailable(goodsVo.getAvailable());
            goodsShopService.add(goodsShop);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

//    @RequestMapping("upload")
//    public Map<String, Object> upload(MultipartFile mf) throws IOException {
//        //1.得到文件名
//        String oldName = mf.getOriginalFilename();
//        //2.根据旧的文件名生成新的文件名
//        String newName= AppFileUtils.createNewFileName(oldName);
//        //3.得到当前日期的字符串
//        String dirName= DateUtil.format(new Date(), "yyyy-MM-dd");
//        //4.构造文件夹
//        File dirFile=new File(AppFileUtils.UPLOAD_PATH,dirName);
//        //5.判断当前文件夹是否存在
//        if(!dirFile.exists()) {
//            //如果不存在则创建新文件夹
//            dirFile.mkdirs();
//        }
//        //6.构造文件对象
//        File file=new File(dirFile, newName+"_temp");
//        //7.把mf里面的图片信息写入file
//        try {
//            mf.transferTo(file);
//        } catch (IllegalStateException | IOException e) {
//            e.printStackTrace();
//        }
//        Map<String,Object> map=new HashMap<String, Object>();
//        map.put("path",dirName+"/"+newName+"_temp");
//        return map;
//    }

//    @RequestMapping("deleteGoods")
//    public ResultObj deleteGoods(Integer id){
//        try {
//            Goods goods = goodsService.getById(id);
//            goods.setStatus(0);
//            goodsService.updateById(goods);
//            return ResultObj.DELETE_SUCCESS;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultObj.DELETE_ERROR;
//        }
//    }
}

