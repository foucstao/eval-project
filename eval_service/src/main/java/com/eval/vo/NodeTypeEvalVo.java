package com.eval.vo;

import lombok.Data;

@Data
public class NodeTypeEvalVo {
    // c.eval_instance, c.eval_obj_code, c.node_type, c.rating,
    // ${clusterFunction}(c.eval_value) as eval_value
    private Long id;
    private String evalInstance;
    private String evalObjCode;
    private String nodeType;
    private String relationType;
    private String rating;
    private Double evalValue;
}
