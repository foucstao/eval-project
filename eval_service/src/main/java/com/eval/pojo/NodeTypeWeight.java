package com.eval.pojo;

import lombok.Data;

@Data
public class NodeTypeWeight {
    private String nodeType;
    private WeightVector weight;
    private String description;
    private Integer cntUpdated;
    private Boolean isWeighted = false;
}
