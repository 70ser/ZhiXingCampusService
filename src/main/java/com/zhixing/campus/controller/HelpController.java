package com.zhixing.campus.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhixing.campus.common.Result;

import com.zhixing.campus.service.IHelpService;
import com.zhixing.campus.entity.Help;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author WGM
 * @since 2023-04-13
 */
@CrossOrigin
@RestController
@RequestMapping("/help")
public class HelpController {

    @Resource
    private IHelpService helpService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Help help) {
        helpService.saveOrUpdate(help);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        helpService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        helpService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(helpService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(helpService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        QueryWrapper<Help> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(helpService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

