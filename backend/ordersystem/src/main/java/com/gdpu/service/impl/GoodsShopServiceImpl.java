package com.gdpu.service.impl;

import com.gdpu.bean.Goods;
import com.gdpu.bean.GoodsShop;
import com.gdpu.mapper.GoodsShopMapper;
import com.gdpu.service.GoodsShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class GoodsShopServiceImpl extends ServiceImpl<GoodsShopMapper, GoodsShop> implements GoodsShopService {

    @Resource
    GoodsShopMapper goodsShopMapper;

    @Override
    public void updateGoodsAvailable(GoodsShop goodsShop){
        goodsShopMapper.updateGoodsAvailable(goodsShop);
    }

    @Override
    public Integer getGoodsAvailable(GoodsShop goodsShop){

        return goodsShopMapper.getGoodsAvailable(goodsShop);
    }

    @Override
    public List<GoodsShop> getGoodsByShopId(Integer shopId){
        return goodsShopMapper.getGoodsByShopId(shopId);
    }

    @Override
    public void add(GoodsShop goodsShop){
        goodsShopMapper.add(goodsShop);
    }
}
