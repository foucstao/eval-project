package com.base.controller;

import java.util.ArrayList;
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
import com.base.common.utils.DateUtils;
import com.base.common.utils.StringUtils;
import com.base.pojo.SysDictData;
import com.base.service.SysDictDataService;

@RestController
@RequestMapping("api/v1/dict/data")
public class SysDictDataController extends BaseAPIController {

	@Autowired
	SysDictDataService dictDataService;

	@PostMapping("/page")
	public APIReturnData list(@RequestBody QueryFilter queryFilter) {
		startPage();
		List<SysDictData> list = dictDataService.selectList(queryFilter.getQueryList());
		return getDataTable(list);
	}

	/**
	 * 根据字典类型查询字典数据信息
	 */
	@GetMapping(value = "/type/{dictType}")
	public APIReturnData dictType(@PathVariable String dictType) {
		List<SysDictData> data = dictDataService.selectDictDataByType(dictType);
		if (StringUtils.isNull(data)) {
			data = new ArrayList<SysDictData>();
		}
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.putData("data", data);
		apiReturnData.success();
		return apiReturnData;
	}

	@PostMapping
	public APIReturnData add(@Validated @RequestBody SysDictData sysDictData) {
		sysDictData.setCreateTime(DateUtils.getNowDate());
		dictDataService.insert(sysDictData);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", sysDictData);
		return apiReturnData;
	}

	@PutMapping
	public APIReturnData edit(@Validated @RequestBody SysDictData sysDictData) {
		dictDataService.update(sysDictData);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}

	@GetMapping("/{id}")
	public APIReturnData getInfo(@PathVariable Long id) {
		SysDictData sysDictData = dictDataService.selectById(id);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("data", sysDictData);
		return apiReturnData;
	}

	@DeleteMapping("/{ids}")
	public APIReturnData remove(@PathVariable Long[] ids) {
		dictDataService.delete(ids);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("删除成功");
		return apiReturnData;
	}
}
