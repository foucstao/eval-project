package com.eval.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class  DikarNodeIndex {
    // a.institution, a.eval_instance, a.eval_obj_code,
    // b.node_code, c.node_type, d.index_code, c.pcode, c.relation_type,
    // d.index_model
    private String institution;
    private String evalInstance;
    private String evalObjCode;
    private String nodeCode;
    private String nodeType;
    private String indexCode;
    private String pcode;
    private String relationType;
    private String indexModel;
    private String remark;

    private List<DikarNodeIndex> children = new ArrayList<DikarNodeIndex>();

}
