package com.base.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.common.APIReturnData;
import com.base.common.query.BaseQuery;
import com.base.common.utils.request.SecurityUtils;
import com.base.pojo.SysMenu;
import com.base.pojo.SysUser;
import com.base.service.SysMenuRoleService;
import com.base.service.SysMenuService;
import com.base.service.SysUserService;

@RestController
@RequestMapping("api/v1/menu")
public class SysMenuController extends BaseAPIController {

	@Autowired
	SysMenuService menuService;

	@Autowired
	SysMenuRoleService menuRoleService;

	@Autowired
	SysUserService userService;

	@PostMapping
	public APIReturnData add(@Validated @RequestBody SysMenu sysMenu) {
		menuService.insert(sysMenu);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", sysMenu);
		return apiReturnData;
	}

	@PutMapping
	public APIReturnData edit(@Validated @RequestBody SysMenu sysMenu) {
		menuService.update(sysMenu);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}

	@GetMapping("/tree")
	public APIReturnData selectObjectListTree(String queryList) {
		List<SysMenu> menuList = menuService.selectList(strToQueryList(queryList));
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.putData("list", menuList);
		return apiReturnData;
	}

	@GetMapping("/{id}")
	public APIReturnData getInfo(@PathVariable Long id) {
		SysMenu sysMenu = menuService.selectById(id);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("data", sysMenu);
		return apiReturnData;
	}

	@GetMapping("/getmenurole/{userId}")
	public APIReturnData getMenuRole(@PathVariable Long userId) {
		SysUser user = userService.selectById(userId);
		Long[] menuArray = menuRoleService.selectByRoleId(user.getRole()).get("menuArray");
		List<SysMenu> menuList = menuService.selectByIds(menuArray, SecurityUtils.getUserId());
		List<SysMenu> treeList = menuService.getTree(menuList);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("list", treeList);
		apiReturnData.putData("noTreelist", menuList);
		return apiReturnData;
	}

	@DeleteMapping
	public APIReturnData remove(@RequestBody Long[] ids) {

		System.out.println(ids);
		BaseQuery baseQurey = new BaseQuery();
		baseQurey.setName("parent_id");
		baseQurey.setOperation("=");
		baseQurey.setValue(ids[0].toString());

		List<BaseQuery> querieList = Arrays.asList(baseQurey);
		List<SysMenu> menus = menuService.selectList(querieList);
		APIReturnData apiReturnData = new APIReturnData();
		if (menus.size() > 0) {
			apiReturnData.error("存在子菜单，不能删除");
			return apiReturnData;
		}
		menuService.delete(ids);
		apiReturnData.success("删除成功");
		return apiReturnData;
	}

}
