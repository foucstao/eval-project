package com.base.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.common.APIReturnData;
import com.base.pojo.SysMenuRole;
import com.base.service.SysMenuRoleService;


@RestController
@RequestMapping("api/v1/menurole")
public class SysMenuRoleController extends BaseAPIController {

	@Autowired
	SysMenuRoleService menuRoleService;

	@PostMapping
	public APIReturnData add(@Validated @RequestBody SysMenuRole sysMenuRole) {
		menuRoleService.insert(sysMenuRole);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", sysMenuRole);
		return apiReturnData;
	}

	@GetMapping("/getallowmenu/{roleid}")
	public APIReturnData selectAllowMenu(@PathVariable String roleid) {
		Map<String, Long[]> map = menuRoleService.selectByRoleId(roleid);
		Long[] menuArray = map.get("menuArray");
		Long[] selectArray = map.get("selectArray");
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("menuArray", menuArray);
		apiReturnData.putData("selectArray", selectArray);
		return apiReturnData;
	}

	@PutMapping
	public APIReturnData updateByRoleId(@RequestBody SysMenuRole sysMenuRole) {
		menuRoleService.updateByRoleId(sysMenuRole);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}
}
