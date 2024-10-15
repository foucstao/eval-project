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
import com.eval.pojo.EvalObjNodeIndexInput;
import com.eval.service.EvalObjNodeIndexInputService;
import com.eval.vo.DikarNodeIndex;

@RestController
@RequestMapping("api/eval/objindexlink")
public class EvalObjNodeIndexInputController extends BaseAPIController {

	@Autowired
	EvalObjNodeIndexInputService indexService;

	// @ApiOperation("单项查询")
	@RequestMapping("/objCode/{eval_obj_code}")
	public APIReturnData getItem(@PathVariable String eval_obj_code) {
		List<EvalObjNodeIndexInput> index = indexService.selectByCode(eval_obj_code);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", index.size());
		apiReturnData.putData("tableList", index);
		return apiReturnData;
	}

	@GetMapping("/instance/{instance}")
	public APIReturnData getIndexByInstance(@PathVariable String instance) {
		List<EvalObjNodeIndexInput> index = indexService.selectByInstance(instance);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", index.size());
		apiReturnData.putData("tableList", index);
		return apiReturnData;
	}

	//Dikar联表生成数据
	@GetMapping("/DikarNodeIndex/{instance}")
	public APIReturnData generateNodeIndexModel(@PathVariable String instance) {
		List<DikarNodeIndex> index = indexService.generateNodeIndexTemplate(instance);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", index.size());
		apiReturnData.putData("tableList", index);
		return apiReturnData;
	}

	@PostMapping("/nodeIndexObj")
	public APIReturnData selectByCondition(@Validated @RequestBody EvalObjNodeIndexInput instance) {
		List<EvalObjNodeIndexInput> index = indexService.selectByCondition(instance);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", index.size());
		apiReturnData.putData("tableList", index);
		return apiReturnData;
	}

	@RequestMapping("/page")
	public APIReturnData getAllItems() {
		List<EvalObjNodeIndexInput> indexes = indexService.selectDataAll();
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
	public APIReturnData add(@Validated @RequestBody EvalObjNodeIndexInput evalGrade) {
		indexService.insert(evalGrade);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", evalGrade);
		return apiReturnData;
	}

	@PutMapping("/edit")
	public APIReturnData edit(@Validated @RequestBody EvalObjNodeIndexInput evalGrade) {
		indexService.update(evalGrade);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}
}
