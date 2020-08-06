package com.huanfion.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.huanfion.wms.entity.User;
import com.huanfion.wms.mapper.UserMapper;
import com.huanfion.wms.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author huanfion
 * @since 2020-07-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService, UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if(user==null){
            throw new UsernameNotFoundException("用户："+username+"不存在！");
        }
        List<String> permissioncodes = Arrays.asList("ADMIN","USER");
                //tbPermissionService.getUserPermissions(tbUser.getId());//获取按钮权限
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        permissioncodes.forEach(permissioncode -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permissioncode);
            grantedAuthorities.add(grantedAuthority);
        });
        boolean enabled = true; // 可用性 :true:可用 false:不可用
        boolean accountNonExpired = true; // 过期性 :true:没过期 false:过期
        boolean credentialsNonExpired = true; // 有效性 :true:凭证有效 false:凭证无效
        boolean accountNonLocked = true; // 锁定性 :true:未锁定 false:已锁定

        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),enabled,accountNonExpired,credentialsNonExpired,accountNonLocked, grantedAuthorities);
    }
}
