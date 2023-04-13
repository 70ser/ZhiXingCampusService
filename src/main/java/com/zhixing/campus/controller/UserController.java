package com.zhixing.campus.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhixing.campus.common.Result;

import com.zhixing.campus.service.IUserService;
import com.zhixing.campus.entity.User;

import org.springframework.web.bind.annotation.RestController;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZC
 * @since 2023-01-25
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody User user) {
        userService.saveOrUpdate(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        userService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(userService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
    @GetMapping("/login")
    public  Result login(@RequestParam String username,@RequestParam String password){
        password= SecureUtil.md5(password);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        User user = userService.getOne(queryWrapper);
        if(user!=null){
            return Result.success(user);
        }else{
            return Result.error("400","用户名或密码错误");
        }
    }
    @GetMapping("/register")
    public  Result register(@RequestParam String username,@RequestParam String password){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = userService.getOne(queryWrapper);
        if(user!=null){
            return Result.error("400","用户名已存在");
        }else{
            password= SecureUtil.md5(password);
            User user1 = new User();
            user1.setUsername(username);
            user1.setPassword(password);
            userService.save(user1);
            return Result.success(user1);
        }
    }
    @GetMapping("/logout")
    public  Result logout(){
        //do nothing
        return Result.success();
    }
}

