package com.eval.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.eval.ApplicationTest;

public class EvalObjNodeLinkControllerTest extends ApplicationTest {

    @Autowired
    private EvalObjNodeLinkController con;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(con).build();
    }

    // @Test
    // public void testgetAllItemsByObj() throws Exception {
    // MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
    // .get("/api/eval/evalObjNodeLink/obj/workshop"))
    // .andExpect(MockMvcResultMatchers.status().isOk())
    // .andDo(MockMvcResultHandlers.print())
    // .andReturn();

    // logger.info(mvcResult.getResponse().getContentAsString());
    // }

    @Test
    public void testgetTree() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/eval/evalObjNodeLink/tree/workshop"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }
}
