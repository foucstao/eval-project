package com.base.service;

import java.util.ArrayList;
import java.util.List;


import com.base.common.query.BaseQuery;
import com.base.common.query.QueryUtil;
import com.base.pojo.SysMenu;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import com.base.common.utils.request.SecurityUtils;
import com.base.mapper.SysMenuMapper;
import com.base.mapper.SysMenuRoleMapper;
import com.base.pojo.SysMenu;

/**
 * 
 * @author 天涯浪子
 *
 *         2020年7月1日-上午9:29:37
 */
@Service("menuService")
public class SysMenuService {

	@Resource
	SysMenuMapper menuMapper;

	@Resource
	SysMenuRoleMapper menuRoleMapper;

	public List<SysMenu> selectList(List<BaseQuery> queryList) {
		String condition = QueryUtil.queryAll(queryList);
		return menuMapper.selectList(condition);
	}

	public SysMenu selectById(Long id) {
		return  menuMapper.selectByPrimaryKey(id);
	}

	public int insert(SysMenu m) {
		return menuMapper.insert(m);
	}

	public int update(SysMenu m) {
		return menuMapper.updateByPrimaryKey(m);
	}

	public int delete(Long[] ids) {
		return menuMapper.deletesByPrimaryKey(ids);
	}

	public List<SysMenu> getTree(List<SysMenu> menuList) {
		List<SysMenu> trees = new ArrayList<SysMenu>();
		for (SysMenu menu : menuList) {
			if (menu.getParentId() == 0) {
				trees.add(menu);
			}
			for (SysMenu it : menuList) {
				if (it.getParentId() == menu.getId()) {
					menu.getChildren().add(it);
				}
			}
		}
		return trees;
	}

	public List<SysMenu> selectByIds(Long[] array, Long userId) {
		if (SecurityUtils.isAdmin(userId)) {
			return menuMapper.selectList("");
		}
		return menuMapper.selectByIds(array);
	}
}
