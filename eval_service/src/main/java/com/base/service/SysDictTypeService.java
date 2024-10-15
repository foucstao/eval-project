package com.base.service;

import com.base.common.query.BaseQuery;
import com.base.common.query.QueryUtil;
import com.base.mapper.SysDictTypeMapper;
import com.base.pojo.SysDictType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.pojo.SysDictType;

import java.util.List;

@Service
public class SysDictTypeService {

    @Autowired
    SysDictTypeMapper sysDictTypeMapper;

    public List<SysDictType> selectList(List<BaseQuery> queryList) {
        String condition = QueryUtil.queryAll(queryList);
        return sysDictTypeMapper.selectList(condition);
    }

    public SysDictType selectById(Long id) {
        return  sysDictTypeMapper.selectByPrimaryKey(id);
    }

    public int insert(SysDictType m) {
        return sysDictTypeMapper.insert(m);
    }

    public int update(SysDictType m) {
        return sysDictTypeMapper.updateByPrimaryKey(m);
    }

    public int delete(Long[] ids) {
        return sysDictTypeMapper.deletesByPrimaryKey(ids);
    }

}
