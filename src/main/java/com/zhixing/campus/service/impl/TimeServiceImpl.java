package com.zhixing.campus.service.impl;

import com.zhixing.campus.entity.Time;
import com.zhixing.campus.mapper.TimeMapper;
import com.zhixing.campus.service.ITimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WGM
 * @since 2023-04-11
 */
@Service
public class TimeServiceImpl extends ServiceImpl<TimeMapper, Time> implements ITimeService {

}
