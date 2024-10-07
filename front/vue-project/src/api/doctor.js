import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'


export const doctorListService = (name)=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    
    const param = 'name='+name;
    return request.post('/doctor/getDoctorList',param)
}

//添加医生
export const docototAddService = (doctor)=>{
    return request.post('/doctor/add',doctor)
}

export const doctorDeleteService = (id)=>{
    const param = 'id='+id;
    return request.post('/doctor/delete',param)
}

export const doctorUpdateService = (doctor)=>{
    return request.post('/doctor/update',doctor)
}
// //文章分类修改
// export const articleCategoryUpdateService = (categoryData)=>{
//    return  request.put('/category',categoryData)
// }

// //文章分类删除
// export const articleCategoryDeleteService = (id)=>{
//     return request.delete('/category?id='+id)
// }

// //文章列表查询
// export const articleListService = (params)=>{
//    return  request.get('/article',{params:params})
// }

// //文章添加
// export const articleAddService = (articleData)=>{
//     return request.post('/article',articleData);

// }