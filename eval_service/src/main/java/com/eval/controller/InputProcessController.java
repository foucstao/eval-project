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

import com.alibaba.fastjson2.JSONObject;
import com.base.common.APIReturnData;

import com.eval.service.InputProcess;

import com.eval.vo.InputVo;
import com.eval.vo.InstanceVo;
import com.eval.vo.NodeVo;

@RestController
@RequestMapping("api/eval/input")
public class InputProcessController {

    @Autowired
    InputProcess indexService;

    @PostMapping(path = "/nodeTable", produces = "application/json;charset=UTF-8")
    public APIReturnData createNodeTable(@RequestBody List<NodeVo> list) {
        List<NodeVo> res = indexService.createNodeTable(list);
        APIReturnData apiReturnData = new APIReturnData();
        apiReturnData.success("success");
        apiReturnData.putData("result", res);
        return apiReturnData;
    }

    // calculate index fuzzy evaluation. part api.
    @PostMapping(path = "/template", produces = "application/json;charset=UTF-8")
    public APIReturnData createEvalInstanceAndIndexTemplate(@RequestBody InstanceVo instanceVo) {
        InstanceVo res = indexService.createEvalInstanceAndIndexTemplate(instanceVo);
        APIReturnData apiReturnData = new APIReturnData();
        apiReturnData.success("success");
        apiReturnData.putData("result", res);
        return apiReturnData;
    }

    @PostMapping(path = "/updateInput", produces = "application/json;charset=UTF-8")
    public APIReturnData updateInputTable(@RequestBody List<InputVo> input) {
        List<InputVo> res = indexService.updateInputTable(input);
        APIReturnData apiReturnData = new APIReturnData();
        apiReturnData.success("success");
        apiReturnData.putData("result", res);
        return apiReturnData;
    }

}
