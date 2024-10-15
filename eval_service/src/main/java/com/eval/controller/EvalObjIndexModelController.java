package com.eval.controller;

import java.util.List;

import com.eval.pojo.EvalObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.base.common.APIReturnData;
import com.base.controller.BaseAPIController;
import com.eval.pojo.EvalObjIndexModel;
import com.eval.service.EvalObjIndexModelService;

// 评估指标表
@RestController
@RequestMapping("api/eval/objIndexModel")
public class EvalObjIndexModelController extends BaseAPIController {

	@Autowired
	EvalObjIndexModelService indexService;

	// add the findAll api
	@RequestMapping(value = "/page", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public APIReturnData getAllItems() {
		List<EvalObjIndexModel> indexes = indexService.selectDataAll();
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	@GetMapping("/obj/{obj}")
	public APIReturnData getIndexModelByObj(@PathVariable String obj) {
		List<EvalObjIndexModel> indexes = indexService.selectByObj(obj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	// EvalObjIndexModel selectByInstance(String evalInstance)
	@GetMapping("/instance/{evalInstance}")
	public APIReturnData getIndexModelByInstance(@PathVariable String evalInstance) {
		EvalObjIndexModel indexes = indexService.selectByInstance(evalInstance);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("InstanceJson", indexes);
		return apiReturnData;
	}

	// findByInstitution(institution);
	@GetMapping("/institution/{institution}")
	public APIReturnData findByInstitution(@PathVariable String institution) {
		List<EvalObjIndexModel> indexes = indexService.findByInstitution(institution);
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

	@PostMapping("/indexModel")
	public APIReturnData findByInstitution(@RequestBody EvalObjIndexModel indexModel) {
		List<EvalObjIndexModel> indexes = indexService.findByCondition(indexModel);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	@PostMapping("/add")
	public APIReturnData add(@Validated @RequestBody EvalObjIndexModel indexobj) {
		indexService.insert(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", indexobj);
		return apiReturnData;
	}

	@PutMapping("/edit")
	public APIReturnData edit(@Validated @RequestBody EvalObjIndexModel indexobj) {
		indexService.update(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}
}