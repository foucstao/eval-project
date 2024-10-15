package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eval.pojo.EvalObjNodeLink;
import com.eval.vo.EvalObjNodeVo;

@Mapper
public interface EvalObjNodeLinkMapper extends BaseMapper<EvalObjNodeLink> {

	List<EvalObjNodeLink> selectByCode(String eval_obj_code);

	List<EvalObjNodeVo> selectAllJoinNode(String eval_obj_code);

	List<EvalObjNodeLink> findByInstitution(String institution);

	List<EvalObjNodeLink> selectDataAll();

	List<EvalObjNodeLink> selectByCondition(EvalObjNodeLink objNode);

}