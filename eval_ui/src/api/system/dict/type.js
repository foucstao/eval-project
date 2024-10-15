import request from '@/plugins/axios'

// 查询字典类型列表
export function listDictType(queryForm, data) {
	return request({
		url: `api/v1/dict/type/page?pageNum=` + queryForm.pageNum + "&pageSize=" + queryForm.pageSize,
		method: 'post',
		data: data
	})
}
// 查询字典类型
export function getDictType(dataId) {
	return request({
		url: `api/v1/dict/type/` + dataId,
		method: 'get',
	})
}
// 新增字典类型
export function addDictType(data) {
	return request({
		url: 'api/v1/dict/type',
		method: 'post',
		data: data
	})
}
// 修改字典类型
export function updateDictType(data) {
	return request({
		url: 'api/v1/dict/type',
		method: 'put',
		data: data
	})
}
// 删除字典类型
export function delDictType(dataId) {
	return request({
		url: 'api/v1/dict/type/' + dataId,
		method: 'delete',
	})
}
