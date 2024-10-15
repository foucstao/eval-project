package com.eval.mapper;

import java.util.List;

import com.eval.pojo.EvalNode;
import org.apache.ibatis.annotations.Mapper;

import com.eval.pojo.NodeType;

@Mapper
public interface NodeTypeMapper extends BaseMapper<NodeType> {

	List<NodeType> selectByCode(String code);

	List<NodeType> selectDataAll();

	List<NodeType> selectNodeByType(String relationType);
}