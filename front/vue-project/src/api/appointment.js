import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'

export const appointmentListService = (param)=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    
    return request.post('/appointment/list',param)
}

export const appointmentAddService = (form)=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    
    return request.post('/appointment/add',form)
}

export const appointmentDeleteService = (id)=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    const param = "id="+id
    
    return request.post('/appointment/delete',param)
}

//修改医生预约信息
export const appointmentEditService = (appointment)=>{
    return request.post('/appointment/update',appointment)
}