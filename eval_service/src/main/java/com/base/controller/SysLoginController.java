package com.base.controller;

import com.base.auth.AuthenticationContextHolder;
import com.base.auth.LoginUser;
import com.base.auth.service.TokenService;
import com.base.common.APIReturnData;

import com.base.common.exception.BaseException;
import com.base.vo.LoginBody;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 登录验证
 */
@RestController
public class SysLoginController
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public APIReturnData login(@RequestBody LoginBody loginBody)
    {
        APIReturnData apiReturnData = new  APIReturnData();
        // 生成令牌
        // 用户验证
        Authentication authentication = null;
        try
        {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginBody.getUsername(), loginBody.getPassword());
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                throw new BaseException("用户不存在/密码错误");
            }
            else
                throw new BaseException(e.getMessage());
            }
        finally
        {
            AuthenticationContextHolder.clearContext();
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
     String token=   tokenService.createToken(loginUser);

        apiReturnData.putData("token",token);
        loginUser.getUser().setPassword("");
        apiReturnData.putData("user",loginUser.getUser());
       return apiReturnData;
    }

}
