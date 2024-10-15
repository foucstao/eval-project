package com.eval.controller;

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
import com.base.controller.BaseAPIController;
import com.eval.pojo.IndexMembership;
import com.eval.service.IndexMembershipService;

// import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/eval/membership")
public class IndexMembershipController extends BaseAPIController {

	@Autowired
	IndexMembershipService indexService;

	// @ApiOperation("单项查询")
	@RequestMapping("/{index_code}")
	public APIReturnData getItem(@PathVariable String index_code) {
		List<IndexMembership> index = indexService.selectByCode(index_code);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("tableList", index);
		return apiReturnData;
	}

	// @ApiOperation("全部查询")
	@RequestMapping("/page")
	public APIReturnData getAllItems() {
		List<IndexMembership> indexes = indexService.selectDataAll();
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	// List<IndexMembership> selectByCondition(IndexMembership member)
	@PostMapping("/member")
	public APIReturnData getMembershipParameters(@Validated @RequestBody IndexMembership member) {
		List<IndexMembership> indexes = indexService.selectByCondition(member);
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
	public APIReturnData add(@Validated @RequestBody IndexMembership evalGrade) {
		indexService.insert(evalGrade);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", evalGrade);
		return apiReturnData;
	}

	@PutMapping("/edit")
	public APIReturnData edit(@Validated @RequestBody IndexMembership evalGrade) {
		indexService.update(evalGrade);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}

}
