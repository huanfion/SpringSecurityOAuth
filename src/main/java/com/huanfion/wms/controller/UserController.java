package com.huanfion.wms.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huanfion.wms.common.controller.BaseController;
import com.huanfion.wms.common.framework.BeanConverter;
import com.huanfion.wms.common.model.AjaxResult;
import com.huanfion.wms.entity.User;
import com.huanfion.wms.entity.dto.UserInput;
import com.huanfion.wms.entity.query.UserParam;
import com.huanfion.wms.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author huanfion
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    IUserService userService;

    @GetMapping("/page")
    public AjaxResult userPage(UserParam param) {
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.like(StrUtil.isNotBlank(param.getUsername()),User::getUsername,param.getUsername()).or()
                .like(StrUtil.isNotBlank(param.getUsername()),User::getNickname,param.getUsername())
                .like(StrUtil.isNotBlank(param.getPhone()),User::getPhone,param.getPhone())
                .like(StrUtil.isNotBlank(param.getEmail()),User::getEmail,param.getEmail())
                .eq(ObjectUtil.isNotNull(param.getStatus()),User::getStatus,param.getStatus());
        Page<User> userPage = userService.page(getPage(), userWrapper);
        return AjaxResult.success(userPage);
    }

    @GetMapping("/list")
    public AjaxResult userList(UserParam param){
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.like(StrUtil.isNotBlank(param.getUsername()),User::getUsername,param.getUsername()).or()
                .like(StrUtil.isNotBlank(param.getUsername()),User::getNickname,param.getUsername())
                .like(StrUtil.isNotBlank(param.getPhone()),User::getPhone,param.getPhone())
                .like(StrUtil.isNotBlank(param.getEmail()),User::getEmail,param.getEmail())
                .eq(ObjectUtil.isNotNull(param.getStatus()),User::getStatus,param.getStatus());
        List<User> list = userService.list();
        return AjaxResult.success(list);
    }

    @PostMapping("/add")
    public AjaxResult insertUser(@RequestBody @Valid UserInput input){
        User user = BeanConverter.convert(User.class, input);
        user.setCount(0L);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return AjaxResult.success(user);
    }
    @GetMapping("/currentUser")
    public String currentUser() {
        String currentUser = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            currentUser = ((UserDetails) principal).getUsername();
        } else {
            currentUser = principal.toString();
        }
        return currentUser;
    }
}

