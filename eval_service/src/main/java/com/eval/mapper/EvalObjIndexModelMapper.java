package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.eval.pojo.EvalObjIndexModel;

@Mapper
@Repository
public interface EvalObjIndexModelMapper extends BaseMapper<EvalObjIndexModel> {

	List<EvalObjIndexModel> selectDataAll();

	List<EvalObjIndexModel> selectByObj(String obj);

	EvalObjIndexModel selectByInstance(String evalInstance);

	List<EvalObjIndexModel> findByInstitution(String institution);

	List<EvalObjIndexModel> selectByCondition(EvalObjIndexModel indexModel);

}