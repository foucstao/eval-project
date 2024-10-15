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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.base.common.APIReturnData;

import com.base.controller.BaseAPIController;
import com.eval.pojo.EvalNode;
import com.eval.service.EvalNodeService;

// import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/eval/evalnode")
public class EvalNodeController extends BaseAPIController {

	@Autowired
	EvalNodeService indexService;

	// @ApiOperation("单项查询")
	// @GetMapping("/{code}")
	@RequestMapping(value = "/{code}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public APIReturnData getItem(@PathVariable String code) {
		List<EvalNode> index = indexService.selectByCode(code);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", index.size());
		apiReturnData.putData("tableList", index);
		return apiReturnData;
	}

	@GetMapping("/pcode/{pcode}")
	public APIReturnData findByPcode(@PathVariable String pcode) {
		List<EvalNode> index = indexService.selectByPcode(pcode);
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
		List<EvalNode> indexes = indexService.selectDataAll();
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	// findByInstitution(String institution)
	@GetMapping("/institution/{institution}")
	public APIReturnData findByInstitution(@PathVariable String institution) {
		List<EvalNode> indexes = indexService.findByInstitution(institution);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	@RequestMapping(value = "/typeAll/{relationType}", method = { RequestMethod.GET, RequestMethod.POST })
	public APIReturnData getTypeAllItems(@PathVariable String relationType) {
		List<EvalNode> indexes = indexService.selectNodeByType(relationType);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	@PostMapping("/query")
	public APIReturnData getNodesByCondition(@Validated @RequestBody EvalNode node) {
		List<EvalNode> nodes = indexService.selectNodesByObj(node);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", nodes.size());
		apiReturnData.putData("tableList", nodes);
		return apiReturnData;
	}

	// @ApiOperation("nodeTree")
	// @GetMapping("/nodeTree")
	@RequestMapping(value = "/nodeTree", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public APIReturnData getNodeTree() {
		List<EvalNode> indexList = indexService.selectDataAll();
		List<EvalNode> treeList = indexService.getTree(indexList);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("treelist", treeList);
		apiReturnData.putData("noTreelist", indexList);
		return apiReturnData;
	}

	@RequestMapping(value = "/typeNodeTree/{relationType}", method = { RequestMethod.GET, RequestMethod.POST })
	public APIReturnData getTypeNodeTree(@PathVariable String relationType) {
		List<EvalNode> indexList = indexService.selectNodeByType(relationType);
		List<EvalNode> treeList = indexService.getTree(indexList);
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
	public APIReturnData add(@Validated @RequestBody EvalNode indexobj) {
		indexService.insert(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", indexobj);
		return apiReturnData;
	}

	@PutMapping("/edit")
	public APIReturnData edit(@Validated @RequestBody EvalNode indexobj) {
		indexService.update(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}
}
