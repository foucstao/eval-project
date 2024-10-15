package com.eval;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.web.WebAppConfiguration;
import com.eval.controller.EvalNodeControllerTest;
import com.BaseSpringbootProjectApplication;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseSpringbootProjectApplication.class)
@WebAppConfiguration
public class ApplicationTest {
    protected Logger logger = LoggerFactory.getLogger(EvalNodeControllerTest.class);

    @BeforeClass
    public static void beforeClass() {
        System.out.println("=================BeforeClass================");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("=================AfterClass================");
    }

    @Before
    public void beforeTest() {
        System.out.println("before test");
    }

    @After
    public void afterTest() {
        System.out.println("after test");
    }

}
