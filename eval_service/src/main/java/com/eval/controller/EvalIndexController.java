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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.base.common.APIReturnData;
import com.base.controller.BaseAPIController;
import com.eval.pojo.EvalIndex;
import com.eval.service.EvalIndexService;

// @Tag(name = "EvalIndexController", description = "指标相关接口")
@RestController
@RequestMapping("api/eval/index") // 本类接口的地址
public class EvalIndexController extends BaseAPIController {

	@Autowired
	EvalIndexService indexService;

	// @ApiOperation("单指标查询")
	// @Operation(summary = "selectByIndexCode", description = "单指标查询")
	// @GetMapping("/{code}")
	@RequestMapping(value = "{code}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public APIReturnData getItem(@PathVariable String code) {
		List<EvalIndex> index = indexService.selectByIndexCode(code);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("tableList", index);
		return apiReturnData;
	}

	// @ApiOperation("全部指标查询")
	// @GetMapping("/")
	@RequestMapping(value = "/page", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public APIReturnData getAllItems() {
		List<EvalIndex> indexes = indexService.selectIndexAll();
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	@RequestMapping(value = "/typeAll/{relationType}", method = { RequestMethod.GET, RequestMethod.POST })
	public APIReturnData getTypeAllItems(@PathVariable String relationType) {
		List<EvalIndex> indexes = indexService.selectIndexByType(relationType);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	// selectIndexByNodeType(String nodeType)
	@GetMapping("nodeType/{nodeType}")
	public APIReturnData getIndexesByNodeType(@PathVariable String nodeType) {
		List<EvalIndex> indexes = indexService.selectIndexByNodeType(nodeType);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	@GetMapping("/getTree")
	public APIReturnData getTree() {
		List<EvalIndex> indexList = indexService.selectIndexAll();
		List<EvalIndex> treeList = indexService.getTree(indexList);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("treelist", treeList);
		apiReturnData.putData("noTreelist", indexList);
		return apiReturnData;
	}

	@RequestMapping(value = "/indexTree/{relationType}", method = { RequestMethod.GET, RequestMethod.POST })
	public APIReturnData indexTree(@PathVariable String relationType) {
		List<EvalIndex> indexList = indexService.selectIndexByType(relationType);
		List<EvalIndex> treeList = indexService.getTree(indexList);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("treelist", treeList);
		apiReturnData.putData("noTreelist", indexList);
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
	public APIReturnData add(@Validated @RequestBody EvalIndex indexobj) {
		indexService.insert(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", indexobj);
		return apiReturnData;
	}

	@PutMapping("/edit")
	public APIReturnData edit(@Validated @RequestBody EvalIndex indexobj) {
		indexService.update(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}
}
