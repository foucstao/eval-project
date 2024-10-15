package com.eval.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.base.common.APIReturnData;
import com.base.controller.BaseAPIController;
import com.eval.pojo.EvalObjNodeLink;
import com.eval.service.EvalObjNodeLinkService;
import com.eval.vo.EvalObjNodeVo;

// import io.swagger.annotations.ApiOperation;
// 评价对象挂接节点的接口

@RestController
@RequestMapping("api/eval/evalObjNodeLink")
public class EvalObjNodeLinkController extends BaseAPIController {

	@Autowired
	EvalObjNodeLinkService indexService;

	// @ApiOperation("单项查询")
	@RequestMapping("/code/{code}")
	public APIReturnData getItem(@PathVariable String code) {
		List<EvalObjNodeLink> index = indexService.selectByCode(code);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", index.size());
		apiReturnData.putData("tableList", index);
		return apiReturnData;
	}

	// @ApiOperation("全部查询")
	@RequestMapping("/page")
	public APIReturnData getAllItems() {
		List<EvalObjNodeLink> indexes = indexService.selectDataAll();
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	// 输入评价对象，获得评价对象的节点列表，无树结构
	@RequestMapping("/obj/{code}")
	public APIReturnData getAllItemsByObj(@PathVariable String code) {
		List<EvalObjNodeVo> objnodes = indexService.selectAllJoinNode(code);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", objnodes.size());
		apiReturnData.putData("tableList", objnodes);
		return apiReturnData;
	}

	// findByInstitution(String institution)
	@GetMapping("/institution/{institution}")
	public APIReturnData findByInstitution(@PathVariable String institution) {
		List<EvalObjNodeLink> objnodes = indexService.findByInstitution(institution);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", objnodes.size());
		apiReturnData.putData("tableList", objnodes);
		return apiReturnData;
	}

	// 输入评价对象代码，获得评价对象的节点树
	@RequestMapping("/tree/{eval_obj}")
	public APIReturnData getTree(@PathVariable String eval_obj) {
		List<EvalObjNodeVo> treeList = indexService.genNodeTree(eval_obj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("treelist", treeList);
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
	public APIReturnData add(@Validated @RequestBody EvalObjNodeLink indexobj) {
		indexService.insert(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", indexobj);
		return apiReturnData;
	}

	@PutMapping("/edit")
	public APIReturnData edit(@Validated @RequestBody EvalObjNodeLink indexobj) {
		indexService.update(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}
}
