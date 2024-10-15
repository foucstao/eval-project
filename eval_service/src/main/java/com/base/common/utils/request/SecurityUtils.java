package com.base.common.utils.request;


import com.base.auth.LoginUser;
import com.base.common.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.base.common.utils.constant.CacheConstants;
import com.base.common.utils.text.Convert;

public class SecurityUtils {

	/**
	 * 获取Authentication
	 */
	public static Authentication getAuthentication()
	{
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 **/
	public static LoginUser getLoginUser()
	{
		try
		{
			return (LoginUser) getAuthentication().getPrincipal();
		}
		catch (Exception e)
		{
			throw new BaseException( HttpStatus.UNAUTHORIZED.value(),"获取用户信息异常");
		}
	}

	/**
	 * 获取用户
	 */
	public static String getUsername() {
		String username = ServletUtils.getRequest().getHeader(CacheConstants.DETAILS_USERNAME);
		return ServletUtils.urlDecode(username);
	}

	/**
	 * 获取用户ID
	 */
	public static Long getUserId() {
		return Convert.toLong(ServletUtils.getRequest().getHeader(CacheConstants.DETAILS_USER_ID));
	}

	/**
	 * 是否为管理员
	 * 
	 * @param userId 用户ID
	 * @return 结果
	 */
	public static boolean isAdmin(Long userId) {
		return userId != null && 1L == userId;
	}

	/**
	 * 生成BCryptPasswordEncoder密码
	 *
	 * @param password 密码
	 * @return 加密字符串
	 */
	public static String encryptPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

	/**
	 * 判断密码是否相同
	 *
	 * @param rawPassword     真实密码
	 * @param encodedPassword 加密后字符
	 * @return 结果
	 */
	public static boolean matchesPassword(String rawPassword, String encodedPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}
