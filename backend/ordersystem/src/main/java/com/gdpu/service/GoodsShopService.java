package com.gdpu.service;

import com.gdpu.bean.GoodsShop;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface GoodsShopService extends IService<GoodsShop> {

    void updateGoodsAvailable(GoodsShop goodsShop);

    Integer getGoodsAvailable(GoodsShop goodsShop);

    List<GoodsShop> getGoodsByShopId(Integer shopId);

    void add(GoodsShop goodsShop);
}
