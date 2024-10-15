package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eval.pojo.EvalObject;

@Mapper
public interface EvalObjectMapper extends BaseMapper<EvalObject> {

	List<EvalObject> selectByCode(String eval_code);

	List<EvalObject> selectDataAll();

	List<EvalObject> findByInstitution(String institution);

}