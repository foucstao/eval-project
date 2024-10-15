import request from '@/plugins/axios'

// 查询用户列表
export function listTest(queryForm, data) {
	return request({
		url: `api/v1/test/page?pageNum=` + queryForm.pageNum + "&pageSize=" + queryForm.pageSize,
		method: 'post',
		data: data
	})
}
// 查询用户
export function getTest(testId) {
	return request({
		url: `api/v1/test/` + testId,
		method: 'get',
	})
}
// 新增用户
export function addTest(data) {
	return request({
		url: 'api/v1/test',
		method: 'post',
		data: data
	})
}
// 修改用户
export function updateTest(data) {
	return request({
		url: 'api/v1/test',
		method: 'put',
		data: data
	})
}
// 删除用户
export function delTest(testId) {
	return request({
		url: 'api/v1/test/' + testId,
		method: 'delete',
	})
}
