package com.zhixing.campus.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhixing.campus.entity.User;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhixing.campus.common.Result;

import com.zhixing.campus.service.IShopService;
import com.zhixing.campus.entity.Shop;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZC
 * @since 2023-04-10
 */
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Resource
    private IShopService shopService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Shop shop) {
        shopService.saveOrUpdate(shop);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        shopService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        shopService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(shopService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(shopService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(shopService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
    //模糊查询，根据商品名字搜索....9090/shop/search?name=???
    @GetMapping("/search")
    public Result findname(@RequestParam String name){
        QueryWrapper<Shop> wrapper = new QueryWrapper<Shop>();
        wrapper.like(StringUtils.isNotBlank(name),"shop_name",name);
        wrapper.orderByDesc("id");
        if(shopService.getOne(wrapper) != null)
            return Result.success(shopService.list(wrapper));
        else
            return Result.error("400","未搜索到相关商品");
    }

}

