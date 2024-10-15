package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.eval.pojo.EvalIndex;

@Mapper
@Repository
public interface EvalIndexMapper extends BaseMapper<EvalIndex> {

	List<EvalIndex> selectByIndexCode(String code);

	List<EvalIndex> selectIndexAll();

	List<EvalIndex> selectIndexByType(String relationType);

	List<EvalIndex> selectIndexByNodeType(String nodeType);
}