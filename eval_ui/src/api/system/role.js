import request from '@/plugins/axios'

// 查询角色列表
export function listRole(tablePage, data) {
	return request({
		url: `api/v1/role/page?pageNum=` + tablePage.pageNum + "&pageSize=" + tablePage.pageSize,
		method: 'post',
		data: data
	})
}

// 查询角色列表
export function listRoleNoPage() {
	return request({
		url: `api/v1/role/noPage`,
		method: 'get',
	})
}

// 查询角色
export function getRole(roleId) {
	return request({
		url: `api/v1/role/` + roleId,
		method: 'get',
	})
}

// 新增角色
export function addRole(data) {
	return request({
		url: 'api/v1/role',
		method: 'post',
		data: data
	})
}

// 修改角色
export function updateRole(data) {
	return request({
		url: 'api/v1/role',
		method: 'put',
		data: data
	})
}

// 删除角色
export function delRole(roleId) {
	return request({
		url: 'api/v1/role/' + roleId,
		method: 'delete',
	})
}

//获取角色允许访问的菜单
export function getAllowMenu(roleId) {
	return request({
		url: `api/v1/menurole/getallowmenu/${roleId}`,
		method: 'get'
	})
}
