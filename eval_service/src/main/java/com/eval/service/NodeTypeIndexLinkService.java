package com.eval.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eval.mapper.NodeTypeIndexLinkMapper;
import com.eval.pojo.NodeTypeIndexLink;

@Service
public class NodeTypeIndexLinkService extends BaseService<NodeTypeIndexLink> {

	@Autowired
	NodeTypeIndexLinkMapper indexMapper;

	public List<NodeTypeIndexLink> selectByCode(String node_type_code) {
		return indexMapper.selectByCode(node_type_code);
	}

	public List<NodeTypeIndexLink> selectDataAll() {
		return indexMapper.selectDataAll();
	}
}
