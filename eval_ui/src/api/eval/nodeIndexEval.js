import request from '@/plugins/axios'

export function getObjNodeIndexList(instacne){
    return request({
        url:'api/eval/objindexlink/DikarNodeIndex/'+instacne,
        method:'get',
    })
}

//get方法获取数据
export function axiosget(url){
    return request({
        url:url,
        method:'get',
    })
}

export function axiosput(url,data){
    return request({
        url:url,
        method:'put',
        data:data
    })
}

export function axiosPost(url,data){
    return request({
        url:url,
        method:'post',
        data:data
    })
}

export function axiosDelete(url,id){
    return request({
        url:url+'/'+id,
        method:'delete',
    })
}