package com.eval.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.common.APIReturnData;
import com.eval.pojo.EvalNodeEvaluation;
import com.eval.pojo.EvalNodeIndexEvaluation;
import com.eval.pojo.EvalObjEvaluation;
import com.eval.pojo.EvalObjIndexModel;
import com.eval.pojo.Fuzzy;
import com.eval.pojo.NodeTypeWeight;
import com.eval.service.FuzzyEvaluation;
import com.eval.vo.EvalResult;
import com.eval.vo.NodeTypeEvalVo;

@RestController
@RequestMapping("api/eval/fuzzy")
public class FuzzyController {

    @Autowired
    FuzzyEvaluation indexService;

    //指定了返回的类型为json格式  返回最终的评价向量
    @PostMapping(value = "/mult", produces = MediaType.APPLICATION_JSON_VALUE)
    //会抛出异常信息
    public APIReturnData weightXMatrix(@RequestBody Fuzzy f) throws Exception {
        Double[] c = indexService.weightXMatrix(f);
        APIReturnData apiReturnData = new APIReturnData();
        apiReturnData.success("success");
        apiReturnData.putData("value", c);
        return apiReturnData;
    }

    // calculate weights of all indexes.
    @PostMapping(path = "/genWeight")
    public APIReturnData genWeightsByModel(@RequestBody EvalObjIndexModel objIndexModel) {
        List<NodeTypeWeight> uds = indexService.calWeightByIndexModel(objIndexModel);
        APIReturnData apiReturnData = new APIReturnData();
        apiReturnData.success("weights updated success");
        apiReturnData.putData("count", uds);
        return apiReturnData;
    }


    // join all steps of evaluation of an instance. total api. post way
    @PostMapping(path = "/instance")
    public APIReturnData genInstanceFuzzy(@RequestBody EvalObjIndexModel instance) {
        EvalResult res = indexService.calInstanceFuzzy(instance);
        APIReturnData apiReturnData = new APIReturnData();
        apiReturnData.success("success");
        apiReturnData.putData("result", res);
        return apiReturnData;
    }



    // 生成各个指标的隶属度向量 （根据指标输入值,隶属度函数,评级）
    @PostMapping(path = "/index") // part api
    public APIReturnData genIndexFuzzy(@RequestBody EvalObjIndexModel instance) {
        List<EvalNodeIndexEvaluation> res = indexService.calIndexMembership(instance);
        APIReturnData apiReturnData = new APIReturnData();
        apiReturnData.success("success");
        apiReturnData.putData("result", res);
        return apiReturnData;
    }

    @PostMapping(path = "/node") // part api.
    public APIReturnData genNodeFuzzy(@RequestBody EvalObjIndexModel instance) {
        //List<EvalNodeEvaluation> res = indexService.calNodeFuzzy(instance);
        List<EvalNodeEvaluation> res = indexService.calNodeFuzzy_new(instance);
        APIReturnData apiReturnData = new APIReturnData();
        apiReturnData.success("success");
        apiReturnData.putData("result", res);
        return apiReturnData;
    }

    @PostMapping(path = "/type") // part api.
    public APIReturnData genNodeTypeFuzzy(@RequestBody EvalObjIndexModel instance) {
        List<NodeTypeEvalVo> res = indexService.calNodeTypeFuzzy(instance);
        APIReturnData apiReturnData = new APIReturnData();
        apiReturnData.success("success");
        apiReturnData.putData("result", res);
        return apiReturnData;
    }

    @PostMapping(path = "/obj") // part api.
    public APIReturnData genObjFuzzy(@RequestBody EvalObjIndexModel instance) {
        String res = indexService.calObjFuzzy_new(instance);
        APIReturnData apiReturnData = new APIReturnData();
        apiReturnData.success("success");
        apiReturnData.putData("result", res);
        return apiReturnData;
    }
}
