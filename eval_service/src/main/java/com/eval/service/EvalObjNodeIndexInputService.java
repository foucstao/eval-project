package com.eval.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eval.mapper.EvalObjNodeIndexInputMapper;
import com.eval.pojo.EvalObjNodeIndexInput;
import com.eval.vo.DikarNodeIndex;

@Service
public class EvalObjNodeIndexInputService extends BaseService<EvalObjNodeIndexInput> {

	@Autowired
	EvalObjNodeIndexInputMapper indexMapper;

	public List<EvalObjNodeIndexInput> selectByCode(String eval_obj_code) {
		return indexMapper.selectByCode(eval_obj_code);
	}

	public List<EvalObjNodeIndexInput> selectByInstance(String instance) {
		return indexMapper.selectByInstance(instance);
	}

	public List<EvalObjNodeIndexInput> selectDataAll() {
		return indexMapper.selectDataAll();
	}

	// selectByCondition(EvalObjNodeIndexLink obj);
	public List<EvalObjNodeIndexInput> selectByCondition(EvalObjNodeIndexInput obj) {
		return indexMapper.selectByCondition(obj);
	}

	public List<DikarNodeIndex> dikarByInstance(String instance) {
		return indexMapper.dikarByInstance(instance);
	}

	public List<DikarNodeIndex> generateNodeIndexTemplate(String inst) {
		//使用dikerByInstance获取result的初始值
		List<DikarNodeIndex> result = dikarByInstance(inst);

		for (DikarNodeIndex nodeIndex : result) {
			//将联表的数据 带入到input表的格式中
			EvalObjNodeIndexInput input = new EvalObjNodeIndexInput();
			input.setEvalInstance(nodeIndex.getEvalInstance());
			input.setEvalObjCode(nodeIndex.getEvalObjCode());
			input.setNodeCode(nodeIndex.getNodeCode());
			input.setIndexCode(nodeIndex.getIndexCode());

			//check out if the 'input' already exists
			List<EvalObjNodeIndexInput> r = selectByCondition(input);
			CopyObjectUtil.copyProperties(nodeIndex, input, true);

			//如果数据库中已经存在有这个数据，则直接加入，如果存在，则采用更新方式
			if(r.size() > 0) {
				// 更新方式
				int u = update(input);
				nodeIndex.setRemark(String.format("updated %d.", u));
			}else{
				// 如果不存在，则采用input方式来更新数据库，并记录时间
				int u = insert(input);
				nodeIndex.setRemark(String.format("inserted %d.", u));
			}
		}
		return result;
	}
}