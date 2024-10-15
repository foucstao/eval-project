package com.eval.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EvalObjNodeVo {

	private String evalObjCode;
	private Long id;
	private Long pid;
	private String nodeCode;
	private String nodeName;
	private String source;
	private String sink;
	private String nodeType;
	private String relationType; // two type: thing, relation
	private Boolean isLeaf;
	private String institution;

	private List<EvalObjNodeVo> children = new ArrayList<EvalObjNodeVo>();
}