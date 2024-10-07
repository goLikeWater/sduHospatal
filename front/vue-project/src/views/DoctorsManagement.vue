<script  setup>
import { ref ,reactive} from 'vue'
import { ElMessage } from 'element-plus'

const input2 = ref('')

const doctors = ref([
  {
    "birthday": null,
    "address": null,
    "gender": null,
    "phone": null,
    "introduce": null,
    "doctorId":null,
    "personId":null,
    "name": null,
    "department": null,
    "email": null,
    "username": null
  }
  
]
)

import { Delete, Edit, } from '@element-plus/icons-vue'

// ————————————————————————————————————————————
// 得到医生列表
// ———————————————————————————————————————————
import {doctorListService,docototAddService,doctorDeleteService, doctorUpdateService} from '@/api/doctor.js'
const getDoctorList = async() => {
  let result = await doctorListService(input2.value); 

  doctors.value = result.data;
};

// ————————————————————————————————————————————
// 添加医生
// ————————————————————————————————————————————
const departmentList = ref([{
  "departmentId":null,
  "dname":null,
  "address":null}
])
const department = ref({
  "departmentId":null,
  "dname":null,
  "address":null}
)

import {departmentListService} from '@/api/department.js'
const getDepartmentList = async() =>{
  let result = await departmentListService();
  departmentList.value = result.data;

}
getDepartmentList();
const addDoctorForm = ref(false)

const form = reactive({
    username:null,
    password:null,
    name:null,
    departmentId:null,

})

const addDoctor =async()=>{

  let result = await docototAddService(form);
  
  if(result.code === 0)ElMessage.success('添加成功');
  addDoctorForm.value = false
  getDoctorList();

}

const clearAddDoctorForm = () => {
  Object.assign(form, {
    username: null,
    password: null,
    name: null,
    departmentId: null,
  });
};

// ————————————————————————————————
// 删除医生
// ————————————————————————————————
const deleteDoctor=async(doctor)=>{
  let result = await doctorDeleteService(doctor.doctorId);
  if(result.code === 0)ElMessage.success('删除成功');
  getDoctorList();

}

// ——————————————————————————————
// 编辑医生
// ——————————————————————————————
const editDoctorForm = ref(false)

const form1 = reactive({
    doctorId:null,   
    username:null,
    name:null,
    birthday:null,
    phone:null,
    address:null,
    email:null,

})

const editDoctor=(doctor)=>{
    editDoctorForm.value = true
    Object.assign(form1, {
      doctorId:doctor.doctorId,
      username:doctor.username,
      name:doctor.name,
      birthday:doctor.birthday,
      phone:doctor.phone,
      address:doctor.address,
      email:doctor.email,
  });
}

const saveDoctor=async()=>{
    let result =await doctorUpdateService(form1)
    if(result.code === 0)ElMessage.success('修改成功');
    editDoctorForm.value = false
    getDoctorList();
}

import { computed } from 'vue';

const currentPage = ref(1);
const pageSize = ref(10);

// 计算当前页的数据
const pageData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return doctors.value.slice(start, end);
});

// 处理页码变化
const handleCurrentChange = (val) => {
  currentPage.value = val;
};



getDoctorList();

</script>


<template>
  <div>
    <span>姓名</span>
    <el-input
      v-model:modelValue="input2"
      style="width: 200px"
      placeholder="请输入姓名"
    />
    <el-button type="primary" @click="getDoctorList">Search</el-button>

    <el-button type="primary" @click="addDoctorForm = true">add</el-button>

    <el-dialog v-model="addDoctorForm" title="添加医生" width="500">
      <el-form :model="form" label-width="auto" style="max-width: 600px">
      <el-form-item label="用户名">
        <el-input v-model="form.username" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" />
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="科室">
        <el-select v-model="form.departmentId" placeholder="请选择科室">
          <el-option
          v-for="department in departmentList"
          :key="department.departmentId"
          :label="department.dname"
          :value="department.departmentId"
        />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="addDoctor">添加</el-button>
        <el-button @click="addDoctorForm = false">取消</el-button>
      </el-form-item>
      </el-form>
    </el-dialog>


  </div>
  
    <el-table :data="pageData" style="width: 100%">
      <el-table-column prop="username" label="用户名" width="160" />
      <el-table-column prop="name" label="姓名" width="80" />    
      <el-table-column prop="department" label="科室"width="80" /> 
      <el-table-column prop="phone" label="手机号"width="180" />
      <el-table-column prop="birthday" label="生日"width="180" />
      <el-table-column prop="address" label="地址"width="180" />
      <el-table-column prop="email" label="邮箱"width="180" />
      <el-table-column prop="introduce" label="介绍"width="160" />
        <el-table-column  label="操作" width="300">   
          <template #default="{ row }">
            <el-button size="small" type="primary" @click='editDoctor(row)' :icon="Edit"  style="margin-right: 10px;"/>
            <el-dialog v-model="editDoctorForm" title="编辑医生资料" width="500">
              <el-form :model="form1" label-width="auto" style="max-width: 600px">
              <el-form-item label="用户名">
                <el-input v-model="form1.username" disabled/>
              </el-form-item>
              <el-form-item label="姓名">
                <el-input v-model="form1.name" disabled/>
              </el-form-item>
              <el-form-item label="电话">
                <el-input v-model="form1.phone" />
              </el-form-item>
              <el-form-item label="出生日期">
                <el-input v-model="form1.birthday" />
              </el-form-item>
              <el-form-item label="地址">
                <el-input v-model="form1.address" />
              </el-form-item>
              <el-form-item label="邮箱">
                <el-input v-model="form1.email" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="saveDoctor">保存</el-button>
                <el-button @click="editDoctorForm = false">取消</el-button>
              </el-form-item>
              </el-form>
            </el-dialog>
            <el-button size="small" type="primary" @click='deleteDoctor(row)':icon="Delete" />  
          </template>

        </el-table-column>
      
    </el-table>

      
    <!-- 分页组件 -->
    <div class="pagination-container">
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="doctors.length"
        layout="total, prev, pager, next, jumper">
      </el-pagination>
    </div>
  
    
  </template>
  
  <style scoped>
    .demo-pagination-block + .demo-pagination-block {
      margin-top: 10px;
    }
    .demo-pagination-block .demonstration {
      margin-bottom: 16px;
    }
    .el-p{
      margin-top:20px;
      display: flex;
      justify-content: flex-start;
    }
    .pagination-container {
      display: flex;
      justify-content: flex-end; /* 将分页组件对齐到容器的右边 */
      padding: 10px 0; /* 添加上下内边距 */
    }
  </style>
  
  