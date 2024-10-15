import request from '@/plugins/axios'

// 查询菜单列表
export function listMenu(userId) {
	return request({
		url: 'api/v1/menu/getmenurole/' + userId,
		method: 'get',
	})
}

// 新增菜单
export function addMenu(data) {
	return request({
		url: 'api/v1/menu',
		method: 'post',
		data: data
	})
}

// 修改菜单
export function updateMenu(data) {
	return request({
		url: 'api/v1/menu',
		method: 'put',
		data: data
	})
}

// 删除菜单
export function delMenu(menuId) {
	return request({
		url: 'api/v1/menu',
		method: 'delete',
		data: menuId
	})
}