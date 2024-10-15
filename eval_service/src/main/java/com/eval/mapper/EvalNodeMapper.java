package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.eval.pojo.EvalNode;

@Mapper
@Repository
public interface EvalNodeMapper extends BaseMapper<EvalNode> {

	List<EvalNode> selectByCode(String code);

	List<EvalNode> selectByPcode(String pcode);

	List<EvalNode> selectDataAll();

	List<EvalNode> selectNodeByType(String relationType);

	List<EvalNode> selectNodesByObj(EvalNode node);

	List<EvalNode> findByInstitution(String institution);

	int updatePidByInstitution(String institution);
}