package com.zhixing.campus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhixing.campus.common.Result;
import com.zhixing.campus.entity.Time;
import com.zhixing.campus.service.ITimeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/match")
public class MatchController {
    @Resource
    private ITimeService timeService;

    //通过自习id号来匹配：寻找学伴
    @GetMapping("/get")
    public Result matchpeople(@RequestParam Integer id){
        Time time = timeService.getById(id);
        QueryWrapper<Time> wrapper = new QueryWrapper<Time>();
        wrapper.eq("address",time.getAddress());//地址匹配
        wrapper.between("timestart",time.getTimestart().minusMinutes(30),time.getTimestart());//通过开始时间前30min之内匹配
        wrapper.ne("id",id);//排除自己的自习信息
        wrapper.orderByDesc("timestart");//按时间倒序排列
        List<Time> list=timeService.list(wrapper);
        //System.out.println(list.size());
        if(list.size()==0){//通过list的长度判断结果
            return Result.error("400","未匹配到合适的学伴!");
        }else {
            return Result.success(list.get(0));
        }
    }
}
