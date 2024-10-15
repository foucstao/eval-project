package com.eval.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.eval.ApplicationTest;
import com.eval.service.InputProcess;

import lombok.val;

public class InputProcessControllerTest extends ApplicationTest {

    @Autowired
    private InputProcessController ec;
    @Autowired
    InputProcess input;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(ec).build();
    }

    @Test
    public void testUpdateInputTable() throws Exception {
        // String model = "{\"indexModel\": \"I1\", \"version\": \"v1\"}";
        String json_Str = input.readJsonFile("data\\inputValue.json");
        // JSONObject jsonObject = JSON.parseObject(json_Str);
        // JSONArray jsonArr = JSON.parseArray(json_Str);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/eval/input/updateInput")
                .content(json_Str)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());

    }

}
