package com.eval.vo;

import com.base.common.BaseEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class NodeVo extends BaseEntity {
    // "code" : "worker2mac1",
    // "pcode" : "workermachine",
    // "name" : "工人2-机器1",
    // "source" : "worker2",
    // "sink" : "mac1",
    // "nodeType" : "WORKER-MACHINE",
    // "isLeaf" : 1,
    // "relationType" : "relation",
    // "description" : null,
    // "evalObjCode" : "workshop"
    private String code;
    private String pcode;
    private String name;
    private String source;
    private String sink;
    private String nodeType;
    private Boolean isLeaf;
    private String relationType;
    private String description;
    private String evalObjCode;
    private String institution;
}
