package com.huanfion.wms.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.huanfion.wms.entity.User;
import com.huanfion.wms.service.IUserService;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;

public class CommonMetaObjectHandler implements MetaObjectHandler {
    /**
     * 创建时间
     */
    private final String createDate = "createdate";
    /**
     * 修改时间
     */
    private final String modifyDate = "modifydate";
    /**
     * 创建者ID
     */
    private final String createId = "createid";

    /**
     * 修改者ID
     */
    private final String modifyId = "modifyid";

    /**
     * 创建者 名称
     */
    private final String creator="creator";
    /**
     * 修改者名称
     */
    private final String modifier="modifier";

    @Autowired
    @Qualifier(value = "userServiceImpl")
    private IUserService userService;
    @Override
    public void insertFill(MetaObject metaObject) {
        User currentUser = getCurrentUser();
        setFieldValByName(createDate, LocalDateTime.now(),metaObject);
        setFieldValByName(createId,currentUser.getId(),metaObject);
        setFieldValByName(creator,currentUser.getUsername(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        User currentUser = getCurrentUser();
        setFieldValByName(modifyDate, LocalDateTime.now(),metaObject);
        setFieldValByName(modifyId,currentUser.getId(),metaObject);
        setFieldValByName(modifier,currentUser.getUsername(),metaObject);
    }
    private User getCurrentUser(){
        String name = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, name));
        if(user==null){
            throw new UsernameNotFoundException("找不到该用户，用户名：" + name);
        }
        return  user;
    }
}
