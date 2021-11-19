package com.gdpu.service;

import com.gdpu.bean.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdpu.bean.TbUser;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface AdminService extends IService<Admin> {
    Admin getByAccount(String account);
}
