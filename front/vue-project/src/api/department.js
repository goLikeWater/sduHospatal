import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//文章分类列表查询
export const departmentListService = ()=>{
    return request.get('/department/getDepartmentList')
}

