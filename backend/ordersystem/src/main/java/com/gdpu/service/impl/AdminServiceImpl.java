package com.gdpu.service.impl;

import com.gdpu.bean.Admin;
import com.gdpu.mapper.AdminMapper;
import com.gdpu.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Resource
    AdminMapper adminMapper;

    @Override
    public Admin getByAccount(String account){
        return adminMapper.getByAccount(account);
    }
}
