package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eval.pojo.NodeTypeIndexLink;

@Mapper
public interface NodeTypeIndexLinkMapper extends BaseMapper<NodeTypeIndexLink> {

	List<NodeTypeIndexLink> selectByCode(String node_type_code);

	List<NodeTypeIndexLink> selectDataAll();
}