import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'

export const patientListService = (num)=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    
    const param = 'num='+num;
    return request.post('/patient/list',param)
}

//添加医生
export const patientAddService = (patient)=>{
    return request.post('/patient/add',patient)
}

export const patientDeleteService = (id)=>{
    const param = 'id='+id;
    return request.post('/patient/delete',param)
}

export const patientUpdateService = (patient)=>{
    return request.post('/patient/update',patient)
}