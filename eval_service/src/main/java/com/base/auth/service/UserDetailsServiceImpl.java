package com.base.auth.service;


import com.base.auth.LoginUser;
import com.base.common.exception.BaseException;
import com.base.common.utils.StringUtils;
import com.base.pojo.SysMenu;
import com.base.pojo.SysUser;
import com.base.service.SysMenuRoleService;
import com.base.service.SysMenuService;
import com.base.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户验证处理
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private SysUserService userService;

//    @Autowired
//    private SysPermissionService permissionService;

    @Autowired
    private SysMenuRoleService menuRoleService;

    @Autowired
    private SysMenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        SysUser user = userService.selectByName(username);
        if (StringUtils.isNull(user))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new BaseException("登录用户：" + username + " 不存在");
        }
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        Map<String, Long[]> map = menuRoleService.selectByRoleId(user.getRole());
        List<SysMenu> roles = menuService.selectByIds(map.get("selectArray"), user.getId());
        Set<String> authorities = null;
        if (roles != null && !roles.isEmpty()) {
            authorities = new HashSet<>();
            for(SysMenu item:roles){
                if (!StringUtils.isEmpty(item.getPerms())) {
                    authorities.add(item.getPerms());
                }
            }
        }

        return new LoginUser(user, authorities);
    }
}
