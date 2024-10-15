package com.base.service;

import java.util.Collections;
import java.util.List;

import com.base.common.query.BaseQuery;
import com.base.common.query.QueryUtil;
import com.base.pojo.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.common.exception.BaseException;
import com.base.common.utils.DateUtils;
import com.base.common.utils.StringUtils;
import com.base.mapper.SysRoleMapper;
import com.base.mapper.SysUserMapper;
import com.base.pojo.SysRole;
import com.base.pojo.SysUser;

/**
 * 
 * @author 天涯浪子
 *
 *         2020年7月1日-上午9:29:43
 */
@Service
public class SysRoleService{

	@Autowired
	SysRoleMapper sysRoleMapper;

	@Autowired
	SysUserMapper sysUserMapper;

	public List<SysRole> selectList(List<BaseQuery> queryList) {
		String condition = QueryUtil.queryAll(queryList);
		return sysRoleMapper.selectList(condition);
	}

	public SysRole selectById(Long id) {
		return  sysRoleMapper.selectByPrimaryKey(id);
	}

	public int insert(SysRole m) {
		return sysRoleMapper.insert(m);
	}

	public int update(SysRole m) {
		return sysRoleMapper.updateByPrimaryKey(m);
	}



	public List<SysRole> selectByIds(Long[] array) {
		return sysRoleMapper.selectByIds(array);
	}

	public int delete(Long[] ids) {
		List<SysUser> sysUser = sysUserMapper.checkRole(ids);
		if (sysUser.size() > 0) {
			return 0;
		} else {
			return sysRoleMapper.deletesByPrimaryKey(ids);
		}
	}

	/**
	 * 批量新增软件适配器
	 *
	 * @param list 角色列表
	 * @return 结果
	 */
	public String insertSysRoleList(List<SysRole> list) {

		int row = 0;
		StringBuffer resultStr = new StringBuffer();

		list.removeAll(Collections.singleton(null));
		if (list.size() == 0) {
			throw new BaseException("导入的数据不能为空！");
		}
		try {
			for (SysRole sysRole : list) {
				if (StringUtils.isNotEmpty(sysRole.getRoleName())) {
					sysRole.setCreateTime(DateUtils.getNowDate());
					sysRoleMapper.insert(sysRole);
					row++;
				} else {
					resultStr.append("<br/>权限字符为" + sysRole.getRoleKey() + "的数据,角色名称为空");
				}
			}
			if (!StringUtils.isEmpty(resultStr)) {
				resultStr.insert(0, "导入错误如下：");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException("操作失败:请核对文件内容或联系管理员");
		}
		resultStr.append("<br/>此次共添加了" + row + "条数据");
		return resultStr.toString();
	}

	public static void main(String[] args) {
	}
}
