package com.eval.vo;

import lombok.Data;

@Data
public class InputVo {
    // "evalInstance" : "evalworkshopi1001",
    // "evalObjCode" : "workshop",
    // "nodeCode" : "mac1",
    // "indexCode" : "LifeTime",
    // "inputValue" : 14

    private String evalInstance;
    private String evalObjCode;
    private String nodeCode;
    private String indexCode;
    private Double inputValue;
    private String institution;
    private String remark;
}
