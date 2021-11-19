package com.gdpu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdpu.bean.Goods;
import com.gdpu.bean.GoodsShop;
import com.gdpu.common.DataGridView;
import com.gdpu.common.TreeNode;
import com.gdpu.common.TreeNodeBuilder;
import com.gdpu.service.GoodsService;
import com.gdpu.service.GoodsShopService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/goods")
public class GoodsShopController {

    @Resource
    private GoodsShopService goodsShopService;
    @Resource
    private GoodsService goodsService;

    @RequestMapping("getGoods")
    public DataGridView getGoods(Integer shopid){

        QueryWrapper<GoodsShop> queryWrapper = new QueryWrapper<>();
        Goods goods;
        List<GoodsShop> list;
        //根据商店id来获取商品id，并生成goodsshop列表
        queryWrapper.eq("shop_id",shopid);
        //按照queryWrapper的规则获得list，但此时的list并不是按照goods_id排序的
        list = goodsShopService.list(queryWrapper);
        //list排序，GoodsShop::getGoodsId意为对象的实例方法引用语法
        list.sort(Comparator.comparing(GoodsShop::getGoodsId));

        //根据goods来生成树，用pid和goods_id来确认树的父子关系
        List<TreeNode> treeNodes = new ArrayList<>();
        Integer gNum = 0;
        Integer mNum = 0;
        for(GoodsShop goodsShop: list){
//            System.out.println(goodsShop.getGoodsId());
           goods = goodsService.getById(goodsShop.getGoodsId());
           //由于json的起始index为0，而这里的起始index为2，因此-2来变为起始index等于0
           Integer id = goods.getGoodsId()-2;
           Integer pid = goods.getPid()-2;
           if(pid>=0) gNum++;
           String title = goods.getName();
           String icon = goods.getImageUrl();
           Integer price = goods.getPrice();
           Integer available = goodsShop.getAvailable();
           Integer goodsNum = goods.getGoodsNum();
//            System.out.println("goods_num = " + goodsNum);
           Integer menuNum = goods.getMenuNum();
//            System.out.println("menu_num = " + menuNum);
           treeNodes.add(new TreeNode(id,pid,title,icon,price,available,goodsNum,menuNum));
        }
        mNum = list.size()-gNum-2;
//        System.out.println(treeNodes);
//        treeNodes.set(1,new TreeNode(-1,-2,"订单管理系统",null,null,1,gNum,mNum));
        //用获得的菜单信息生成相应的Json，topId为根节点的goods_id
//        System.out.println(treeNodes.get(0));
//        System.out.println(treeNodes.get(1));
//        treeNodes.set(1,new TreeNode(-1,-2,"订单管理系统",null,null,1,gNum,mNum));
        List<TreeNode> list1 = TreeNodeBuilder.build(treeNodes,-2);
//        System.out.println(list1.get(0));
        return new DataGridView(list1);
    }
//    @RequestMapping("getTotal")
//    public DataGridView getTotal(){
//        List<num> list = new ArrayList();
//        list.add(new num(goods_num,menu_num));
//        return new DataGridView(list);
//
//    }
}

