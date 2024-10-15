package com.eval.service;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.eval.ApplicationTest;
import com.eval.pojo.EvalNodeEvaluation;
import com.eval.pojo.EvalNodeIndexEvaluation;
import com.eval.pojo.EvalObjEvaluation;
import com.eval.pojo.EvalObjIndexModel;
import com.eval.pojo.EvalObjNodeIndexInput;
import com.eval.pojo.NodeTypeWeight;
import com.eval.pojo.WeightVector;
import com.eval.vo.EvalResult;
import com.eval.vo.NodeTypeEvalVo;

public class FuzzyEvaluationTest extends ApplicationTest {

    @Autowired
    FuzzyEvaluation ec;

    // @Test
    // public void testProduceWeightVector() {
    // // double[][] M = new double[][] { { 4, -2 }, { 3, -1 } };
    // // double[][] M = new double[][] { { 4, 6, 5 }, { 2, 4, 3 }, { -5, -9, -7 }
    // };
    // // double[][] M = new double[][] { { 4, -2 }, { 1, 1 } };
    // double[][] M = new double[][] {
    // { 1, 1, 4, 1 / 3, 3 },
    // { 1, 1, 4, 1 / 3, 3 },
    // { 1 / 4, 1 / 4, 1, 1 / 3, 1 / 2 },
    // { 3, 3, 3, 1, 3 },
    // { 1 / 3, 1 / 3, 2, 1 / 3, 1 }
    // };
    // // double[][] M = new double[][] {
    // // { 1, 1, 1 / 4, 3, 1 / 3 },
    // // { 1, 1, 1 / 4, 3, 1 / 3 },
    // // { 4, 4, 1, 3, 2 },
    // // { 1 / 3, 1 / 3, 1 / 3, 1, 1 / 3 },
    // // { 3, 3, 1 / 2, 3, 1 } };

    // FuzzyEvaluation ec = new FuzzyEvaluation();
    // WeightVector w = ec.produceWeightVector(M);
    // System.out.println(w.toString());
    // }

    // @Test
    // public void testCalNodeTypeWeights() {
    // String[] ids = { "MacState", "numberOfFailure", "LifeTime" };
    // WeightVector w = ec.calNodeTypeWeights(ids, "v1");
    // System.out.println(w.toString());

    // }

    // @Test
    // public void testCalWeightByIndexModel() {
    // EvalObjIndexModel objIndexModel = new EvalObjIndexModel();
    // objIndexModel.setIndexModel("I1");
    // objIndexModel.setImportanceVersion("v1");
    // List<NodeTypeWeight> res = ec.calWeightByIndexModel(objIndexModel);
    // System.out.println("updated cnt: ");
    // System.out.println(res.toString());
    // }

    // calIndexMembership
    // @Test
    // public void testCalMembershipValue() {
    // EvalObjIndexModel eoni = new EvalObjIndexModel();
    // eoni.setEvalInstance("evalworkshopi1001");
    // List<EvalNodeIndexEvaluation> res = ec.calIndexMembership(eoni);
    // System.out.println(res.toString());
    // }
    // calNodeFuzzy

    // @Test
    // public void testCalNodeFuzzy() {
    // EvalObjIndexModel eoni = new EvalObjIndexModel();
    // eoni.setEvalInstance("evalworkshopi1001");
    // List<EvalNodeEvaluation> res = ec.calNodeFuzzy(eoni);
    // System.out.println(res.toString());
    // }

    // calObjFuzzy

    // @Test
    // public void testCalObjFuzzy() {
    // EvalObjIndexModel model = new EvalObjIndexModel();
    // model.setEvalInstance("evalworkshopi1001");
    // model.setClusterFunction("max");
    // List<EvalObjEvaluation> res = ec.calObjFuzzy(model);
    // System.out.println(res.toString());
    // }

    // calNodeTypeFuzzy
    @Test
    public void testCalNodeTypeFuzzy() {
        EvalObjIndexModel model = new EvalObjIndexModel();
        model.setEvalInstance("evalworkshopi1001");
        model.setClusterFunction("max");
        List<NodeTypeEvalVo> res = ec.calNodeTypeFuzzy(model);
        System.out.println(res.toString());
    }

    // calInstanceFuzzy

    // @Test
    // public void testCalInstanceFuzzy() {
    // EvalObjIndexModel model = new EvalObjIndexModel();
    // model.setEvalInstance("evalworkshopi1001");
    // model.setClusterFunction("max");
    // EvalResult res = ec.calInstanceFuzzy(model);
    // System.out.println(res.toString());
    // }

}
