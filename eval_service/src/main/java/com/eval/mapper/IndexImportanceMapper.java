package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eval.pojo.IndexImportance;

@Mapper
public interface IndexImportanceMapper extends BaseMapper<IndexImportance> {

	List<IndexImportance> selectByIndexFirst(String index_first);

	List<IndexImportance> selectByObj(IndexImportance imp);

	List<IndexImportance> selectDataAll();
}