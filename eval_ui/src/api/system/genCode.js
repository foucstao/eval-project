import request from '@/plugins/axios'

// 查询列表
export function listCode(queryForm) {
	return request({
		url: `api/v1/gen/page?pageNum=` + queryForm.pageNum + "&pageSize=" + queryForm.pageSize,
		method: 'get',
	})
}

// 下载代码
export function downCode(data) {
	return request.post('api/v1/gen/down', data, {
		responseType: 'blob'
	})
}

// 批量下载代码
export function batchDownCode(data) {
	console.log(data)
	return request.post('api/v1/gen/batchDown', data, {
		responseType: 'blob'
	})
}
