package com.eval.controller;

import java.util.List;

import com.eval.pojo.EvalNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.base.common.APIReturnData;
import com.base.controller.BaseAPIController;
import com.eval.pojo.NodeType;
import com.eval.service.NodeTypeService;

// import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/eval/nodeType")
public class NodeTypeController extends BaseAPIController {

	@Autowired
	NodeTypeService indexService;

	// @ApiOperation("单项查询")
	@RequestMapping("/{code}")
	public APIReturnData getItem(@PathVariable String code) {
		List<NodeType> index = indexService.selectByCode(code);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("tableList", index);
		return apiReturnData;
	}

	// @ApiOperation("全部查询")
	@RequestMapping("/page")
	public APIReturnData getAllItems() {
		List<NodeType> indexes = indexService.selectDataAll();
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	@RequestMapping(value="typeAll/{relationType}", method={RequestMethod.GET,RequestMethod.POST})
	public APIReturnData getTypeAllItems(@PathVariable String relationType) {
		List<NodeType> indexes = indexService.selectNodeByType(relationType);
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
	public APIReturnData add(@Validated @RequestBody NodeType indexobj) {
		indexService.insert(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", indexobj);
		return apiReturnData;
	}

	@PutMapping("/edit")
	public APIReturnData edit(@Validated @RequestBody NodeType indexobj) {
		indexService.update(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}
}
