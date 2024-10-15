package com.eval.controller;

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
import com.base.common.utils.DateUtils;
import com.base.common.utils.request.SecurityUtils;
import com.base.controller.BaseAPIController;
import com.eval.pojo.EvalObject;
import com.eval.pojo.IndexImportance;
import com.eval.service.IndexImportanceService;

// import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/eval/indexImportance")
public class IndexImportanceController extends BaseAPIController {

	@Autowired
	IndexImportanceService indexService;

	// @ApiOperation("单项查询")
	@RequestMapping("/{code}")
	public APIReturnData getItem(@PathVariable String code) {
		List<IndexImportance> index = indexService.selectByIndexFirst(code);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", index.size());
		apiReturnData.putData("tableList", index);
		return apiReturnData;
	}

	// @ApiOperation("全部查询")
	@RequestMapping("/page")
	public APIReturnData getAllItems() {
		List<IndexImportance> indexes = indexService.selectDataAll();
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	// selectByObj(IndexImportance imp)
	@PostMapping("/obj")
	public APIReturnData getImportanceByObj(@Validated @RequestBody IndexImportance imp) {
		List<IndexImportance> indexes = indexService.selectByObj(imp);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	@DeleteMapping("/remove/{id}")
	public APIReturnData remove(@PathVariable Long id) {
		indexService.delete(id);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("删除成功");
		return apiReturnData;
	}

	@PostMapping("/add")
	public APIReturnData add(@Validated @RequestBody IndexImportance indexobj) {
		indexService.insert(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", indexobj);
		return apiReturnData;
	}

	@PutMapping("/edit")
	public APIReturnData edit(@Validated @RequestBody IndexImportance indexobj) {
		indexService.update(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}

}