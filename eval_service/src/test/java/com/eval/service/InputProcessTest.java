package com.eval.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.eval.ApplicationTest;
import com.eval.vo.InputVo;
import com.eval.vo.InstanceVo;
import com.eval.vo.NodeVo;
import com.alibaba.fastjson2.JSON;

public class InputProcessTest extends ApplicationTest {
    @Autowired
    InputProcess input;

    // @Test
    // public void testCreateEvalObjAndInstance() {
    // String jsonStr = input.readJsonFile("data\\instance.json");
    // InstanceVo instanceVo = input.createEvalObjAndInstance(jsonStr);
    // System.out.println(instanceVo);
    // }

    @Test
    public void testCreateNodeTable() {
        String json_Str = input.readJsonFile("data\\node.json");
        List<NodeVo> list = new ArrayList<NodeVo>();
        list = JSON.parseArray(json_Str, NodeVo.class);
        List<NodeVo> res = input.createNodeTable(list);
        System.out.println(res.toString());
    }

    // createEvalInstanceAndIndexTemplate
    @Test
    public void testCreateEvalInstanceAndIndexTemplate() {
        String jsonStr = input.readJsonFile("data\\instance.json");
        InstanceVo instanceVo = JSON.parseObject(jsonStr, InstanceVo.class);

        InstanceVo res = input.createEvalInstanceAndIndexTemplate(instanceVo);
        System.out.println(res);
    }

    // updateInputTable
    @Test
    public void testUpdateInputTable() {
        String json_Str = input.readJsonFile("data\\inputValue.json");
        List<InputVo> list = new ArrayList<InputVo>();
        list = JSON.parseArray(json_Str, InputVo.class);
        List<InputVo> res = input.updateInputTable(list);
        System.out.println(res.toString());
    }

}
