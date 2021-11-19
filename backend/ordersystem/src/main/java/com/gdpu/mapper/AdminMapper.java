package com.gdpu.mapper;

import com.gdpu.bean.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */
public interface AdminMapper extends BaseMapper<Admin> {
    Admin getByAccount(String account);
}
