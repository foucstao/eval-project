package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eval.pojo.EvalObjNodeIndexInput;
import com.eval.vo.DikarNodeIndex;

@Mapper
public interface EvalObjNodeIndexInputMapper extends BaseMapper<EvalObjNodeIndexInput> {

	List<EvalObjNodeIndexInput> selectByCode(String eval_obj_code);

	List<EvalObjNodeIndexInput> selectDataAll();

	List<EvalObjNodeIndexInput> selectByInstance(String instance);

	// selectByCondition
	List<EvalObjNodeIndexInput> selectByCondition(EvalObjNodeIndexInput obj);

	List<DikarNodeIndex> dikarByInstance(String instance);

}