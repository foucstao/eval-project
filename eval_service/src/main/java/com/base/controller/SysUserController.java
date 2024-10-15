package com.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.util.StringUtil;
import com.base.common.APIReturnData;
import com.base.common.query.QueryFilter;
import com.base.common.utils.request.SecurityUtils;
import com.base.pojo.SysUser;
import com.base.service.SysUserService;


@RestController
@RequestMapping("api/v1/user")
public class SysUserController extends BaseAPIController {

	@Autowired
	SysUserService sysUserService;

	@PostMapping("/page")
	public APIReturnData list(@RequestBody QueryFilter queryFilter) {
		startPage();
		List<SysUser> list = sysUserService.selectList(queryFilter.getQueryList());
		return getDataTable(list);
	}

	@PostMapping
	public APIReturnData add(@Validated @RequestBody SysUser sysUser) {
		sysUser.setPassword(SecurityUtils.encryptPassword(sysUser.getPassword()));
		sysUserService.insert(sysUser);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", sysUser);
		return apiReturnData;
	}

	@PostMapping("/checkPwd")
	public APIReturnData selectByName(@RequestBody SysUser user) {

		SysUser sysUser = sysUserService.selectByName(user.getName());

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		APIReturnData apiReturnData = new APIReturnData();
		if (encoder.matches(user.getPassword(), sysUser.getPassword())) {
			apiReturnData.success();
		} else {
			apiReturnData.error();
		}
		sysUser.setPassword(null);
		apiReturnData.putData("sysUser", sysUser);
		return apiReturnData;
	}

	@PutMapping
	public APIReturnData edit(@Validated @RequestBody SysUser sysUser) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!StringUtil.isEmpty(sysUser.getPassword())) {
			sysUser.setPassword(encoder.encode(sysUser.getPassword()));
		}
		sysUserService.update(sysUser);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}

	@GetMapping("/{id}")
	public APIReturnData getInfo(@PathVariable Long id) {
		SysUser sysUser = sysUserService.selectById(id);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("data", sysUser);
		return apiReturnData;
	}

	@DeleteMapping("/{ids}")
	public APIReturnData remove(@PathVariable Long[] ids) {
		sysUserService.delete(ids);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("删除成功");
		return apiReturnData;
	}
}
