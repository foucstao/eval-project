package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eval.pojo.EvalNodeIndexEvaluation;
import com.eval.pojo.EvalObjIndexModel;
import com.eval.vo.EvalNodeIndexEvaluationVo;
import com.eval.vo.NodeTypeEvalVo;

@Mapper
public interface EvalNodeIndexEvaluationMapper extends BaseMapper<EvalNodeIndexEvaluation> {

	List<EvalNodeIndexEvaluation> selectByCode(String code);

	List<EvalNodeIndexEvaluation> selectDataAll();

	List<EvalNodeIndexEvaluationVo> selectAllNodesJoinIndexesByEvalObj(String eval_obj);

	List<EvalNodeIndexEvaluationVo> selectNodeIndexesByInstance(String evalInstance);

	List<EvalNodeIndexEvaluation> selectByCondition(EvalNodeIndexEvaluation enie);

	List<NodeTypeEvalVo> selectEvalGroupbyNodeTypeRating(EvalObjIndexModel model);

	//List<EvalNodeIndexEvaluation> selectByInstanceSimple(String instance);
	// evalOnNodeTypeRating
	List<NodeTypeEvalVo> evalOnNodeTypeRating(EvalObjIndexModel model);

    List<EvalNodeIndexEvaluation> selectByInstanceSimple(String instance);
}