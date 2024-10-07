import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'

export const registrationListService = ()=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    
    return request.get('/registration/list')
}


export const registrationAddService = (form)=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    
    return request.post('/registration/add',form)
}

export const registrationDeleteService = (id)=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    const param = "id="+id
    
    return request.post('/registration/delete',param)
}

//修改医生预约信息
export const registrationEditService = (registration)=>{
    return request.post('/registration/update',registration)
}