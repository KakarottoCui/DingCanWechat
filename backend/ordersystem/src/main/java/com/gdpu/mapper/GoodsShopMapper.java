package com.gdpu.mapper;

import com.gdpu.bean.GoodsShop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */
public interface GoodsShopMapper extends BaseMapper<GoodsShop> {

    void updateGoodsAvailable(GoodsShop goodsShop);

    Integer getGoodsAvailable(GoodsShop goodsShop);

    List<GoodsShop> getGoodsByShopId(Integer shopId);

    void add(GoodsShop goodsShop);
}
