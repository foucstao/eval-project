package com.base.controller;

import java.io.IOException;
import java.util.List;


import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.base.common.APIReturnData;
import com.base.common.excel.ExcelUtil;
import com.base.common.query.QueryFilter;
import com.base.pojo.SysMenuRole;
import com.base.pojo.SysRole;
import com.base.service.SysMenuRoleService;
import com.base.service.SysRoleService;
import com.base.vo.SysRoleVo;


@RestController
@RequestMapping("api/v1/role")
public class SysRoleController extends BaseAPIController {

	@Autowired
	SysRoleService sysRoleService;

	@Autowired
	SysMenuRoleService sysMenuRoleService;

	@PostMapping
	public APIReturnData add(@RequestBody SysRoleVo sysRoleVo) {
		System.out.println(sysRoleVo.toString());
		SysRole sysRole = new SysRole();

		BeanUtils.copyProperties(sysRoleVo, sysRole);
		sysRoleService.insert(sysRole);

		SysMenuRole sysMenuRole = new SysMenuRole();
		sysMenuRole.setRoleId(sysRole.getId());
		sysMenuRole.setMenuIds(StringUtils.join(sysRoleVo.getMenuIds(), ","));
		sysMenuRole.setSelectIds(StringUtils.join(sysRoleVo.getSelectIds(), ","));
		sysMenuRoleService.insert(sysMenuRole);

		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", sysRole);
		return apiReturnData;
	}

	@DeleteMapping("/{ids}")
	public APIReturnData remove(@PathVariable Long[] ids) {
		int row = sysRoleService.delete(ids);
		APIReturnData apiReturnData = new APIReturnData();
		if (row == 0) {
			apiReturnData.error("删除失败，有用户绑定，请解除绑定后再试");
			return apiReturnData;
		}
		apiReturnData.success("删除成功");
		return apiReturnData;
	}

	/**
	 * 导入列表
	 */
	@PostMapping("/import")
	public APIReturnData importList(MultipartFile file) throws Exception {
		ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
		List<SysRole> roleInfoList = util.importExcel(file.getInputStream());
		String result = sysRoleService.insertSysRoleList(roleInfoList);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success(result);
		return apiReturnData;
	}

	/**
	 * 下载导入模板
	 */
	@PostMapping("/importTemplate")
	public void importTemplate(HttpServletResponse response) throws IOException {
		ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
		util.importTemplateExcel(response, "角色信息");
	}

	@PutMapping
	public APIReturnData edit(@Validated @RequestBody SysRoleVo sysRoleVo) {

		SysRole sysRole = new SysRole();

		BeanUtils.copyProperties(sysRoleVo, sysRole);
		SysMenuRole sysMenuRole = new SysMenuRole();
		sysMenuRole.setRoleId(sysRole.getId());
		sysMenuRole.setMenuIds(StringUtils.join(sysRoleVo.getMenuIds(), ","));
		sysMenuRole.setSelectIds(StringUtils.join(sysRoleVo.getSelectIds(), ","));
		sysRoleService.update(sysRole);
		sysMenuRoleService.updateByRoleId(sysMenuRole);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}

//	@PreAuthorize("hasAuthority('sys:role:list')")
	@PostMapping("/page")
	public APIReturnData list(@RequestBody QueryFilter queryFilter) {
		System.out.println(queryFilter);
		startPage();
		List<SysRole> list = sysRoleService.selectList(queryFilter.getQueryList());
		return getDataTable(list);
	}

	@GetMapping("/noPage")
	public APIReturnData listNoPage(String queryList) {
		List<SysRole> list = sysRoleService.selectList(strToQueryList(queryList));
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("tableList", list);
		return getDataTable(list);
	}

	@PostMapping("/export")
	public void export(HttpServletResponse response, String queryList) throws IOException {
		List<SysRole> list = sysRoleService.selectList(strToQueryList(queryList));
		ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
		util.exportExcel(response, list, "sysRole");
	}

	@GetMapping("/{id}")
	public APIReturnData getInfo(@PathVariable Long id) {
		SysRole sysRole = sysRoleService.selectById(id);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("data", sysRole);
		return apiReturnData;
	}
}
