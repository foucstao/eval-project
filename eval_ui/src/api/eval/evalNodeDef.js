import request from '@/plugins/axios'

export function getTree(url){
    return request({
        url:url,
        method:'get',
    })
}

export function getType(url){
    return request({
        url:url,
        method:'get',
    })
}

export function addNode(url,data){
    return request(
        {
            url:url+'/add',
            method:'post',
            data:data
        }
    )
}