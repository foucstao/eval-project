package com.eval.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class InstanceVo {
    // {
    // "id" : 1,
    // "evalInstance" : "xxevalworkshopi1001",
    // "evalObjCode" : "xxworkshop",
    // "evalObjName" : "xx生产车间",
    // "indexModel" : "I1",
    // "importanceVersion" : "v1",
    // "clusterFunction" : "max",
    // "rateVersion" : "v1"
    // }

    private String evalInstance;
    private String evalObjCode;
    private String evalObjName;
    private String indexModel;
    private String importanceVersion;
    private String clusterFunction;
    private String rateVersion;
    private String description;
    private String institution;
    private String remark;
    private List<DikarNodeIndex> dikar = new ArrayList<DikarNodeIndex>();
}
