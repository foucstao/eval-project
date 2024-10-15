package com.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.base.common.query.BaseQuery;
import com.base.common.query.QueryUtil;
import com.base.pojo.SysMenuRole;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import com.base.common.utils.StringUtils;
import com.base.mapper.SysMenuRoleMapper;
import com.base.pojo.SysMenuRole;

/**
 * 
 * @author 天涯浪子
 *
 *         2020年7月1日-上午9:29:29
 */
@Service
public class SysMenuRoleService  {

	@Resource
	SysMenuRoleMapper menuRoleMapper;

	public List<SysMenuRole> selectList(List<BaseQuery> queryList) {
		String condition = QueryUtil.queryAll(queryList);
		return menuRoleMapper.selectList(condition);
	}

	public SysMenuRole selectById(Long id) {
		return  menuRoleMapper.selectByPrimaryKey(id);
	}

	public int insert(SysMenuRole m) {
		return menuRoleMapper.insert(m);
	}

	public int update(SysMenuRole m) {
		return menuRoleMapper.updateByPrimaryKey(m);
	}

	public int delete(Long[] ids) {
		return menuRoleMapper.deletesByPrimaryKey(ids);
	}
	
	public Map<String, Long[]> selectByRoleId(String roleids) {
		List<SysMenuRole> menuRoleList = menuRoleMapper.selectByRoleId(StringUtils.strToLongArray(roleids));
		StringBuffer menus = new StringBuffer();
		StringBuffer selects = new StringBuffer();
		menuRoleList.forEach(item -> {
			if (!StringUtils.isEmpty(item.getMenuIds())) {
				menus.append(item.getMenuIds() + ",");
			}
			if (!StringUtils.isEmpty(item.getSelectIds())) {
				selects.append(item.getSelectIds() + ",");
			}
		});
		Map<String, Long[]> map = new HashMap<String, Long[]>();
		Long[] menuArray = StringUtils.strToLongArray(menus.toString());
		menuArray = StringUtils.duplicateRemovalBySet(menuArray, Long.class);

		Long[] selectArray = StringUtils.strToLongArray(selects.toString());
		selectArray = StringUtils.duplicateRemovalBySet(selectArray, Long.class);

		map.put("menuArray", menuArray);
		map.put("selectArray", selectArray);
		return map;
	}

	public int updateByRoleId(SysMenuRole sysMenuRole) {
		List<SysMenuRole> menuRoleList = menuRoleMapper.selectByRoleId(StringUtils.strToLongArray(sysMenuRole.getRoleId()+""));
		if(menuRoleList.size()==0){
			return 	menuRoleMapper.insert(sysMenuRole);
		}else {
			return menuRoleMapper.updateByRoleId(sysMenuRole);
		}
	}
}
