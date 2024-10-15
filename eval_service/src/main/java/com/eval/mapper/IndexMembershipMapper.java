package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eval.pojo.IndexMembership;

@Mapper
public interface IndexMembershipMapper extends BaseMapper<IndexMembership> {

	List<IndexMembership> selectByCode(String index_code);

	List<IndexMembership> selectDataAll();

	List<IndexMembership> selectByCondition(IndexMembership member);
}