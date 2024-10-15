package com.eval.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.base.common.APIReturnData;
import com.base.controller.BaseAPIController;
import com.eval.pojo.EvalObject;
import com.eval.service.EvalObjectService;

// import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/eval/evalobj")
public class EvalObjectController extends BaseAPIController {

	/*
	 * peng---version
	 */
	@Autowired
	EvalObjectService indexService;

	// @ApiOperation("单项查询")
	// @GetMapping("/{code}")
	@RequestMapping(value = "/{code}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public APIReturnData getItem(@PathVariable String code) {
		List<EvalObject> index = indexService.selectByCode(code);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", index.size());
		apiReturnData.putData("tableList", index);
		return apiReturnData;
	}

	// findByInstitution(institution);
	@GetMapping("/institution/{institution}")
	public APIReturnData findByInstitution(@PathVariable String institution) {
		List<EvalObject> index = indexService.selectByCode(institution);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", index.size());
		apiReturnData.putData("tableList", index);
		return apiReturnData;
	}

	// @ApiOperation("全部查询")
	// @GetMapping("/")
	@RequestMapping(value = "/page", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public APIReturnData getAllItems() {
		List<EvalObject> indexes = indexService.selectDataAll();
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
	public APIReturnData add(@Validated @RequestBody EvalObject indexobj) {
		indexService.insert(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", indexobj);
		return apiReturnData;
	}

	@PutMapping("/edit")
	public APIReturnData edit(@Validated @RequestBody EvalObject indexobj) {
		indexService.update(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}
}