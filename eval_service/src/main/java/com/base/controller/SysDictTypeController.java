package com.base.controller;

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
import com.base.common.query.QueryFilter;
import com.base.pojo.SysDictType;
import com.base.service.SysDictDataService;
import com.base.service.SysDictTypeService;

@RestController
@RequestMapping("api/v1/dict/type")
public class SysDictTypeController extends BaseAPIController {

	@Autowired
	SysDictTypeService dictTypeService;

	@Autowired
	SysDictDataService dataService;

	@PostMapping("/page")
	public APIReturnData list(@RequestBody QueryFilter queryFilter) {
		startPage();
		List<SysDictType> list = dictTypeService.selectList(queryFilter.getQueryList());
		return getDataTable(list);
	}

	@PostMapping
	public APIReturnData add(@Validated @RequestBody SysDictType sysDictType) {
		dictTypeService.insert(sysDictType);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", sysDictType);
		return apiReturnData;
	}

	@PutMapping
	public APIReturnData edit(@Validated @RequestBody SysDictType sysDictType) {
		dictTypeService.update(sysDictType);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}

	@GetMapping("/{id}")
	public APIReturnData getInfo(@PathVariable Long id) {
		SysDictType sysDictType = dictTypeService.selectById(id);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("data", sysDictType);
		return apiReturnData;
	}

	@DeleteMapping("/{ids}")
	public APIReturnData remove(@PathVariable Long[] ids) {
		dictTypeService.delete(ids);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("删除成功");
		return apiReturnData;
	}
}
