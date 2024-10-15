package com.eval.pojo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EvalIndex {
	// iid, pid, code, pcode, name, type, desc, affiliation, param1, param2, param3,
	// param4

	private Long id;

	private Long pid;

	private String code;

	private String pcode;

	private String nodeType;

	private String relationType;

	private String name;

	private Boolean isLeaf;

	private String description;

	private List<EvalIndex> children = new ArrayList<EvalIndex>();
}