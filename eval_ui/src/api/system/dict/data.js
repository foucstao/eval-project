import request from '@/plugins/axios'

// 查询字典数据列表
export function listDictData(queryForm, data) {
	return request({
		url: `api/v1/dict/data/page?pageNum=` + queryForm.pageNum + "&pageSize=" + queryForm.pageSize,
		method: 'post',
		data: data
	})
}
// 查询字典数据
export function getDictData(dataId) {
	return request({
		url: `api/v1/dict/data/` + dataId,
		method: 'get',
	})
}
// 新增字典数据
export function addDictData(data) {
	return request({
		url: 'api/v1/dict/data',
		method: 'post',
		data: data
	})
}
// 修改字典数据
export function updateDictData(data) {
	return request({
		url: 'api/v1/dict/data',
		method: 'put',
		data: data
	})
}
// 删除字典数据
export function delDictData(dataId) {
	return request({
		url: 'api/v1/dict/data/' + dataId,
		method: 'delete',
	})
}

// 查询字典数据列表
export function getDicts(dictType) {
	return this.$axios
		.get(`api/v1/dict/data/type/` + dictType)

}
