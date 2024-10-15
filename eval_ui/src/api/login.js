import request from '@/plugins/axios'

// 登录方法
export function login(data) {
	return request({
		method: 'post',
		url: `/login`,
		data: data
	})
}