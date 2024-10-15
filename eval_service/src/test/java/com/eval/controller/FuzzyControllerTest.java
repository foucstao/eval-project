package com.eval.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.eval.ApplicationTest;

public class FuzzyControllerTest extends ApplicationTest {

    @Autowired
    private FuzzyController ec;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(ec).build();
    }

    // @Test
    // public void testMult() throws Exception {
    // String ff = "{\"a\": [1.0,3.0,4.0], \"b\":
    // [[2.0,3.0,1.0],[5.0,6.0,3.0],[4.0,4.0,3.0]] }";
    // MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
    // .post("/api/eval/fuzzy/weighted")
    // .content(ff)
    // .contentType(MediaType.APPLICATION_JSON))
    // .andReturn();
    // logger.info(mvcResult.getResponse().getContentAsString());

    // }

    @Test
    public void testGenWeightsByModel() throws Exception {
        String model = "{\"indexModel\": \"I1\", \"version\": \"v1\"}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/eval/fuzzy/genWeight")
                .content(model)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());

    }
}
