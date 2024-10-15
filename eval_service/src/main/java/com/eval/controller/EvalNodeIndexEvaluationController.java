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

import com.eval.pojo.EvalNodeIndexEvaluation;
import com.eval.pojo.EvalObjIndexModel;
import com.eval.service.EvalNodeIndexEvaluationService;
import com.eval.vo.EvalNodeIndexEvaluationVo;
import com.eval.vo.NodeTypeEvalVo;

// import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/eval/nodeIndex")
public class EvalNodeIndexEvaluationController extends BaseAPIController {

	@Autowired
	EvalNodeIndexEvaluationService indexService;

	// @ApiOperation("单项查询")
	// @GetMapping("/{code}")
	@RequestMapping(value = "/{code}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public APIReturnData getItem(@PathVariable String code) {
		List<EvalNodeIndexEvaluation> index = indexService.selectByCode(code);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("tableList", index);
		return apiReturnData;
	}

	// @ApiOperation("全部查询")
	// @GetMapping("/")
	@RequestMapping(value = "/page", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public APIReturnData getAllItems() {
		List<EvalNodeIndexEvaluation> indexes = indexService.selectDataAll();
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	// @ApiOperation("节点指标联表查询")
	// @GetMapping("/n2index/{eval_obj}")
	@RequestMapping(value="/n2index/{eval_obj}", method = { RequestMethod.GET, RequestMethod.POST })
	public APIReturnData getNodesJoinIndexesByEvalObj(@PathVariable String eval_obj) {
		List<EvalNodeIndexEvaluationVo> indexes = indexService.selectAllNodesJoinIndexesByEvalObj(eval_obj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	//新的方法来实现获取表数据
	@GetMapping("/instancesimple/{instance}")
	public APIReturnData getByInstance(@PathVariable String instance) {
		List<EvalNodeIndexEvaluation> indexes = indexService.selectByInstanceSimple(instance);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	@GetMapping("/instance/{evalInstance}")
	public APIReturnData getNodeIndexesByInstance(@PathVariable String evalInstance) {
		List<EvalNodeIndexEvaluationVo> indexes = indexService.selectNodeIndexesByInstance(evalInstance);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	@PostMapping("/evalOnNodeTypeRating")
	public APIReturnData evalOnNodeTypeRating(@Validated @RequestBody EvalObjIndexModel model) {
		List<NodeTypeEvalVo> indexes = indexService.evalOnNodeTypeRating(model);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("total", indexes.size());
		apiReturnData.putData("tableList", indexes);
		return apiReturnData;
	}

	@RequestMapping("/getNodeTree/{eval_obj}")
	public APIReturnData getNodeTreeWithIndex(@PathVariable String eval_obj) {
		List<EvalNodeIndexEvaluationVo> indexList = indexService.selectAllNodesJoinIndexesByEvalObj(eval_obj);
		List<EvalNodeIndexEvaluationVo> treeList = indexService.getTree(indexList);
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
	public APIReturnData add(@Validated @RequestBody EvalNodeIndexEvaluation indexobj) {
		indexService.insert(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", indexobj);
		return apiReturnData;
	}

	@PutMapping("/edit")
	public APIReturnData edit(@Validated @RequestBody EvalNodeIndexEvaluation indexobj) {
		indexService.update(indexobj);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}
}
