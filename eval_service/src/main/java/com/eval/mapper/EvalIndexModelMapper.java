package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.eval.pojo.EvalIndexModel;

@Mapper
@Repository
public interface EvalIndexModelMapper extends BaseMapper<EvalIndexModel> {

	List<EvalIndexModel> selectByIndexModel(String indexModel);

	List<EvalIndexModel> selectByIndexModelAndNodeType(String indexModel, String nodeType);

	List<EvalIndexModel> selectModelByObj(EvalIndexModel obj);

}