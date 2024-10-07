import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'

export const docAppointmentListService = (searchForm)=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    return request.post('/docAppointment/list',searchForm)
}


//添加医生预约信息
export const docAppointmentAddService = (docAppointment)=>{
    return request.post('/docAppointment/add',docAppointment)
}
//修改医生预约信息
export const docAppointmentEditService = (docAppointment)=>{
    return request.post('/docAppointment/update',docAppointment)
}
//删除医生预约信息
export const docAppointmentDeleteService = (docAppointmentId)=>{
    const param = "id="+docAppointmentId
    
    return request.post('/docAppointment/delete',param)
}