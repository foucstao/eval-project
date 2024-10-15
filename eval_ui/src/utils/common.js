import { ElMessage } from 'element-plus'
import moment from 'moment'
import qs from 'qs';
import request from '@/plugins/axios'
/**
 *提交搜索
 */
export function handleQuery(params, getDataList) {
    params.pageSize = 10;
    params.pageNum = 1;
    getDataList();
};
/**
 *重置表单
 */
export function resetForm(refName) {
    refName.resetFields();
};


//每页条数事件
export function handleSizeChange(val, tablePage, getDataList) {
    console.log(tablePage)
    tablePage.pageSize = val;
    getDataList();
};
//改变页数事件
export function handleCurrentChange(val, tablePage, getDataList) {
    tablePage.pageNum = val;
    getDataList();
};

/**
 *设置查询参数
 */
export function setQueryParams(name, operation, value, queryList) {
    const queryParams = {
        name: name,
        operation: operation,
        value: value
    }
    queryList.push(queryParams)
}

/**
 *全局消息提示
 */
export function globalMessage(status, msg) {
    if (status === 200) {
        ElMessage({
            message: msg,
            type: 'success',
        })
    } else {
        ElMessage({
            message: msg,
            type: 'error',
        })
    }
}

// 回显数据字典
export function selectDictLabel(datas, value) {
    var actions = [];
    Object.keys(datas).some((key) => {
        if (datas[key].dictValue == ('' + value)) {
            actions.push(datas[key].dictLabel);
            return true;
        }
    })
    return actions.join('');
}

//日期格式化
export function formatDate(date, formatStr) {
    return moment(date).format(formatStr);
}

// 通用下载方法
export function download(url, params, filename) {
    return request.post(url, null, {
        params: qs.stringify(params),
        responseType: 'blob'
    }).then((res) => {
        const blob = new Blob([res]);//处理文档流
        const down = document.createElement('a');
        down.download = filename;
        down.style.display = 'none';//隐藏,没必要展示出来
        down.href = URL.createObjectURL(blob);
        document.body.appendChild(down);
        down.click();
        URL.revokeObjectURL(down.href); // 释放URL 对象
        document.body.removeChild(down);//下载完成移除
    }).catch((r) => {
        window.console.error(r)
    })
}
