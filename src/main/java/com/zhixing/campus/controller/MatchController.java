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
        wrapper.eq("address",time.getAddress());
        wrapper.between("timestart",time.getTimestart().minusMinutes(30),time.getTimestart());
        wrapper.ne("id",id);
        wrapper.orderByDesc("timestart");
        List<Time> list=timeService.list(wrapper);
        //return Result.success(timeService.list(wrapper));
        //wrapper.eq("address",time.getAddress()).lt("",);
        //wrapper.orderByDesc("id");
        if(list.get(0)==null){
              //return Result.success(list.get(0));
            return Result.error("400","未匹配到合适的学伴!");
        }else {
            //return Result.error("400","未匹配到合适的学伴!");
            return Result.success(list.get(0));
        }
    }
}
