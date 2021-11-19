package com.gdpu.controller;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdpu.bean.*;
import com.gdpu.common.*;
import com.gdpu.service.CountService;
import com.gdpu.service.GoodsService;
import com.gdpu.service.OrderFormService;
import com.gdpu.service.ShopService;
import com.gdpu.vo.OrderFormVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/orderForm")
public class OrderFormController {
    @Resource
    private OrderFormService orderFormService;

    @Resource
    private CountService countService;

    @Resource
    private ShopService shopService;

    @Resource
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //后台处获取所有订单
    @RequestMapping("loadAllOrder")
    public DataGridView loadAllOrder(OrderFormVo orderFormVo){
        IPage<OrderForm> page = new Page<>(orderFormVo.getPage(),orderFormVo.getLimit());
        QueryWrapper<OrderForm> queryWrapper = new QueryWrapper<>();
        orderFormService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    //小程序处获取所有订单
    @RequestMapping("loadOrderForm")
    public DataGridView loadOrderForm(OrderFormVo orderFormVo){
//        System.out.println("----------------------------------"+orderFormVo.getShopId());
        Integer  shopIdEspecially = 0;
        if(WebUtils.getSession().getAttribute("shopId")!=null){
            shopIdEspecially = Integer.parseInt(WebUtils.getSession().getAttribute("shopId").toString());
        }

//        Admin tbUser =(Admin) WebUtils.getSession().getAttribute("user");
//        int role = tbUser.getRoleId();
        IPage<OrderForm> page = new Page<>(orderFormVo.getPage(),orderFormVo.getLimit());
        QueryWrapper<OrderForm> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq(null !=orderFormVo.getOpenId(),"open_id",orderFormVo.getOpenId());
        queryWrapper.eq(0!=shopIdEspecially,"shop_id",shopIdEspecially);
        queryWrapper.eq(null!=orderFormVo.getShopId(),"shop_id",orderFormVo.getShopId());
        queryWrapper.like(null!=orderFormVo.getPhone(),"phone",orderFormVo.getPhone());
//        queryWrapper.like(null!=orderFormVo.getShopname(),"address",orderFormVo.getShopname());
        queryWrapper.eq(null!=orderFormVo.getId() && 0!=orderFormVo.getId(),"order_id",orderFormVo.getId());
        List<OrderForm> list = orderFormService.list(queryWrapper);
//        list.
        List<MapNodeHead> map = new ArrayList<>();
        Hashtable<Integer,Integer> ids = new Hashtable<>();
        for(OrderForm orderForm : list){
            if (!ids.containsKey(orderForm.getOrderId())){

                MapNodeHead mapNodeHead = new MapNodeHead(
                        orderForm.getOrderId(), orderForm.getPriceSum(),
                        shopService.getById(orderForm.getShopId()).getAddress(), orderForm.getOrderTime(), orderForm.getPhone(),orderForm.getFinish(),orderForm.getOpenId(),orderForm.getShopId());
                map.add(mapNodeHead);
                int value = map.indexOf(mapNodeHead);
                ids.put(orderForm.getOrderId(),value);
                int index =ids.get(orderForm.getOrderId());
                Goods goods = goodsService.getById(orderForm.getGoodsId());
                String goodsname = goods.getName();
                String icon = goods.getImageUrl();
                Integer number = orderForm.getNumber();
                Integer price = orderForm.getPriceSum();
                MapNode mapNode = new MapNode(goodsname,icon,number,price);
                map.get(index).getChildren().add(mapNode);
            }else {
                int index =ids.get(orderForm.getOrderId());
                Goods goods = goodsService.getById(orderForm.getGoodsId());
                String goodsname = goods.getName();
                String icon = goods.getImageUrl();
                Integer number = orderForm.getNumber();
                Integer price = orderForm.getPriceSum();
                MapNode mapNode = new MapNode(goodsname,icon,number,price);

                map.get(index).getChildren().add(mapNode);
            }
        }

        return new DataGridView(map);
     }

    //下单，记录到数据库中
    @RequestMapping("getForm")
    public void getForm(OrderFormVo orderFormVo){
//        System.out.println("OpenId = " + OpenId);
//        System.out.println(orderFormVo.getPriceSum());

        try{
            Count count = countService.getById(1);
            count.setOrderId(count.getOrderId()+1);
            Integer orderid =  count.getOrderId();
            countService.updateById(count);
            //将字符串转化成标准josn格式
            String jsons = "{\"data\":" +orderFormVo.getBought()+"}";
//        System.out.println("jsons = " + jsons);
            JSONObject json = new JSONObject(jsons);
            JSONArray array = json.getJSONArray("data");
            String[] str2 = orderFormVo.getNumbers().substring(1,orderFormVo.getNumbers().length()-1).split(",");
            for(int i =0;i<array.size();i++){
//            String title = array.getJSONObject(i).get("title").toString();
//            System.out.println("title = " + title);
                String id = array.getJSONObject(i).get("id").toString();
//            System.out.println("id = " + id);
                orderFormVo.setOrderId(orderid);
                orderFormVo.setOrderTime(new Date());
                orderFormVo.setNumber(Integer.parseInt(str2[Integer.parseInt(id)-9]));
                orderFormVo.setGoodsId(Integer.parseInt(id)+2);
                orderFormVo.setFinish(0);
                orderFormService.save(orderFormVo);
            }
//            WebUtils.getSession().setAttribute("doFlash",1);

            redisTemplate.opsForValue().set("doFlash","1");
        }catch (Exception e){
            System.out.println("e = " + e);
        }
//        for (int i =0;i<str2.length;i++){
//            System.out.println(str2[i]);
//        }


//        Timer timer = new Timer(true);
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//            }
//        },500);
    }

    @RequestMapping("orderFlash")
    public String orderFlash(){

        return redisTemplate.opsForValue().get("doFlash");
    }

    @RequestMapping("closeOrderForm")
    public void closeOrderForm(){
//        WebUtils.getSession().setAttribute("doFlash",0);
        redisTemplate.opsForValue().set("doFlash","0");
    }
    @RequestMapping("orderFinish")
    public ResultObj orderFinish(OrderFormVo orderFormVo){
        try{
            orderFormVo.setFinish(1);
            QueryWrapper<OrderForm> queryWrapper = new QueryWrapper<>();
//            System.out.println("-----------------------------"+orderFormVo.getOrderId());
            queryWrapper.eq("order_id",orderFormVo.getOrderId());
            orderFormService.update(orderFormVo,queryWrapper);

            return ResultObj.FINISH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.FINISH_ERROR;
        }
    }
}

