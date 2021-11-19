package com.gdpu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdpu.bean.Push;
import com.gdpu.common.DataGridView;
import com.gdpu.service.PushService;
import com.gdpu.vo.PushVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 */
//在controller层请求处理完了返回时，
//没有使用@RestController或@ResponseBody而返回了非json格式，会报错
@RestController
@RequestMapping("/home")
public class PushController {
    @Resource
    private PushService pushService;

    @RequestMapping("swiperdata")
    public DataGridView getSwiper(@Param("PushVo")PushVo pushVo){
        //声明一个分页对象，用于获取数据总数、数据
        IPage<Push> page = new Page<>(pushVo.getPage(),pushVo.getLimit());
        //声明一个queryWrapper
        QueryWrapper<Push> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",0);
        pushService.page(page,queryWrapper);
        return new DataGridView(page.getRecords());
    }
    @RequestMapping("waydata")
    public DataGridView getWay(@Param("PushVo")PushVo pushVo){
        //声明一个分页对象，用于获取数据总数、数据
        IPage<Push> page = new Page<>(pushVo.getPage(),pushVo.getLimit());
        //声明一个queryWrapper
        QueryWrapper<Push> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",1);
        pushService.page(page,queryWrapper);
        return new DataGridView(page.getRecords());
    }
    @RequestMapping("newsdata")
    public DataGridView getNews(@Param("PushVo")PushVo pushVo){
        //声明一个分页对象，用于获取数据总数、数据
        IPage<Push> page = new Page<>(pushVo.getPage(),pushVo.getLimit());
        //声明一个queryWrapper
        QueryWrapper<Push> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",2);
        pushService.page(page,queryWrapper);
        return new DataGridView(page.getRecords());
    }
}

