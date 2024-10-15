import request from '@/plugins/axios'

// 查询用户列表
export function listUser(queryForm, data) {
	return request({
		url: `api/v1/user/page?pageNum=` + queryForm.pageNum + "&pageSize=" + queryForm.pageSize,
		method: 'post',
		data: data
	})
}
// 查询用户
export function getUser(userId) {
	return request({
		url: `api/v1/user/` + userId,
		method: 'get',
	})
}
// 新增用户
export function addUser(data) {
	return request({
		url: 'api/v1/user',
		method: 'post',
		data: data
	})
}
// 修改用户
export function updateUser(data) {
	return request({
		url: 'api/v1/user',
		method: 'put',
		data: data
	})
}
// 删除用户
export function delUser(userId) {
	return request({
		url: 'api/v1/user/' + userId,
		method: 'delete',
	})
}
