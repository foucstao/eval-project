import axios from "axios";
import NProgress from "nprogress";
import router from "@/router";
import store from "@/store";
import { ElMessage } from 'element-plus'
let config = {
    Global: true,
    baseURL: "/space/",
    timeout: 60 * 1000, // Timeout
};
const service = axios.create(config);
let loadingCount = 0;
// 请求拦截
service.interceptors.request.use(
    (config) => {
        NProgress.done();
        if (config.Global) {
            NProgress.start();
        }
        loadingCount++;
        //*请求头添加token
        const token = store.getters.token;
        if (token) {
            config.headers.Authorization = "Bearer " + token
            config.headers.userId = sessionStorage.uid
            config.headers.username = sessionStorage.uname
        }
        // Do something before request is sent
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// 响应拦截
service.interceptors.response.use(
    (response) => {
        loadingCount--;
        if (loadingCount <= 0) {
            NProgress.done();
        }

        let type = "success";
        if (response.data.code != "00") {
            type = "error";
        }
        return response.data;
    },
    (error) => {
        loadingCount--;
        if (loadingCount <= 0) {
            NProgress.done();
        }
        if (error && error.response) {
            let message = "";
            switch (error.response.status) {
                case 400:
                    message = "请求错误";
                    break;
                case 401: {
                    message = "未授权，请登录";
                    router.replace({
                        name: "Login",
                    });
                    break;
                }
                case 403:
                    message = "没有权限，拒绝访问";
                    break;
                case 404:
                    message = `请求地址出错`;
                    break;
                case 408:
                    message = "请求超时";
                    break;
                case 500:
                    message = "服务器内部错误";
                    break;
                case 501:
                    message = "服务未实现";
                    break;
                case 502:
                    message = "网关错误";
                    break;
                case 503:
                    message = "服务不可用";
                    break;
                case 504:
                    message = "网关超时";
                    break;
                case 505:
                    message = "HTTP版本不受支持";
                    break;
                default:
                    break;
            }
            let { msg } = error;
            if (msg == "Network Error") {
                message = "后端接口连接异常";
            }
            else if (msg.includes("timeout")) {
                message = "系统接口请求超时";
            }
            else if (msg.includes("Request failed with status code")) {
                message = "系统接口" + msg.substr(message.length - 3) + "异常";
            }
            ElMessage({
                message: message,
                type: 'error',
            })
        }
        return Promise.reject(error);
    }
);

export default service;
