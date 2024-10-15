package com.eval.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EvalNodeIndexEvaluationVo {
	private Long eid;// eval id,
	private Long id; // node id
	private String evalInstance;
	private String indexModel;
	private String evalObjCode;
	private Long pid; // parent id of node.
	private String nodeCode;
	private String nodeName;
	private String source;
	private String sink;
	private String nodeType;
	private String relationType; // two type: thing, relation
	private Boolean isLeaf;
	private String indexCode;
	private String indexName;
	private Double inputValue;
	private String membership;
	private Double evalValue;

	private List<EvalNodeIndexEvaluationVo> children = new ArrayList<EvalNodeIndexEvaluationVo>();
}