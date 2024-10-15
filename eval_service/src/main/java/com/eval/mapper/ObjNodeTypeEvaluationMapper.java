package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.eval.pojo.ObjNodeTypeEvaluation;

@Mapper
@Repository
public interface ObjNodeTypeEvaluationMapper extends BaseMapper<ObjNodeTypeEvaluation> {

	List<ObjNodeTypeEvaluation> selectByCode(String nodeType);

	List<ObjNodeTypeEvaluation> selectByObj(String obj);

	List<ObjNodeTypeEvaluation> selectByEvalInstance(String evalInstance);

	List<ObjNodeTypeEvaluation> selectByCondition(ObjNodeTypeEvaluation typeEvaluation);

}