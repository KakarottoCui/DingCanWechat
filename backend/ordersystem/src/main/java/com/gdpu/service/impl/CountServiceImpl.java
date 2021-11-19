package com.gdpu.service.impl;

import com.gdpu.bean.Count;
import com.gdpu.mapper.CountMapper;
import com.gdpu.service.CountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class CountServiceImpl extends ServiceImpl<CountMapper, Count> implements CountService {

}
