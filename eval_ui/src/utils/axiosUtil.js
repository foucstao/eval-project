import request from '@/plugins/axios'

export default {
	// 获取信息
	axiosGet(url, data) {
		return request({
			url: url,
			method: 'get',
			data: data
		})
	},
	// 新增信息
	axiosPost(url, data) {
		return request({
			url: url,
			method: 'post',
			headers:{
				"Content-Type":"application/json"
			},
			data: data
		})
	},
	// 修改信息
	axiosPut(url, data) {
		return request({
			url: url,
			method: 'put',
			data: data
		})
	},
	// 删除信息
	axiosDelete(url) {
		return request({
			url: url,
			method: 'delete',
		})
	},
	//新增查询方法
	axiosGet2(url,code){
		return request({
			url:url+"/"+code,
			method:'get'
		})
	}
}