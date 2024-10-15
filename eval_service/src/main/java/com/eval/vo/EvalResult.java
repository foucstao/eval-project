package com.eval.vo;

import java.util.List;

import com.eval.pojo.EvalNodeEvaluation;
import com.eval.pojo.EvalNodeIndexEvaluation;
import com.eval.pojo.EvalObjEvaluation;

import lombok.Data;

@Data
public class EvalResult {
    private List<EvalNodeIndexEvaluation> indexFuzzy;
    private List<EvalNodeEvaluation> nodeFuzzy;
    private List<NodeTypeEvalVo> typeFuzzy;
    private List<EvalObjEvaluation> objFuzzy;
    private String description;
}
