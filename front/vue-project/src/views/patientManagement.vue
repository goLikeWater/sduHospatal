<script setup>
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { Delete, Edit, } from '@element-plus/icons-vue'

const input2 = ref('')

import {patientListService,patientAddService,patientDeleteService,patientUpdateService} from '@/api/patient.js'

const patients = ref([
  {
    "birthday": null,
    "address": null,
    "gender": null,
    "phone": null,
    "patientId":null,
    "personId":null,
    "name": null,
    "email": null,
    "username": null
  }
  
]
)

const getPatientList = async() => {
  let result = await patientListService(input2.value); 

  patients.value = result.data;
};


const addPatientForm = ref(false)
const form = reactive({
    username:null,
    password:null,
    name:null,
    num:null,

})

const addPatient =async()=>{

  let result = await patientAddService(form);
  
  if(result.code === 0)ElMessage.success('添加成功');
  addPatientForm.value = false
  getPatientList();

}


// ————————————————————————————————
// 删除医生
// ————————————————————————————————
const deletePatient=async(patient)=>{
  let result = await patientDeleteService(patient.patientId);
  if(result.code === 0)ElMessage.success('删除成功');
  getPatientList();

}


// ——————————————————————————————
// 编辑医生
// ——————————————————————————————
const editPatientForm = ref(false)

const form1 = reactive({
    patientId:null,   
    username:null,
    name:null,
    birthday:null,
    phone:null,
    address:null,
    email:null,

})

const editPatient=(patient)=>{
    editPatientForm.value = true
    Object.assign(form1, {
      patientId:patient.patientId,
      username:patient.username,
      name:patient.name,
      birthday:patient.birthday,
      phone:patient.phone,
      address:patient.address,
      email:patient.email,
  });
}

const savePatient=async()=>{
    let result =await patientUpdateService(form1)
    if(result.code === 0)ElMessage.success('修改成功');
    editPatientForm.value = false
    getPatientList();
}

import { computed } from 'vue';

const currentPage = ref(1);
const pageSize = ref(10);

// 计算当前页的数据
const pageData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return patients.value.slice(start, end);
});

// 处理页码变化
const handleCurrentChange = (val) => {
  currentPage.value = val;
};

getPatientList()


</script>



<template>
  <div>
    <div>
      <span>证件号</span>
      <el-input
        v-model:modelValue="input2"
        style="width: 200px"
        placeholder="请输入证件号"
      />
      <el-button type="primary" @click="getPatientList">Search</el-button>
  
      <el-button type="primary" @click="addPatientForm = true">add</el-button>
  
      <el-dialog v-model="addPatientForm" title="添加患者" width="500">
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
        <el-form-item label="证件号">
          <el-input v-model="form.num" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addPatient">添加</el-button>
          <el-button @click="addPatientForm = false">取消</el-button>
        </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  
      <el-table :data="pageData" style="width: 100%">
        <el-table-column prop="username" label="用户名" width="160" />  
              <el-table-column prop="num" label="证件号"width="180" />
        <el-table-column prop="name" label="姓名" width="80" />    
        <el-table-column prop="phone" label="手机号"width="180" />
        <el-table-column prop="birthday" label="生日"width="180" />
        <el-table-column prop="address" label="地址"width="180" />
        <el-table-column prop="email" label="邮箱"width="180" />
          <el-table-column  label="操作" width="180">   
            <template #default="{ row }">
              <el-button size="small" type="primary" @click='editPatient(row)' :icon="Edit"  style="margin-right: 10px;"/>
              <el-dialog v-model="editPatientForm" title="编辑患者资料" width="500">
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
                  <el-button type="primary" @click="savePatient">保存</el-button>
                  <el-button @click="editPatientForm = false">取消</el-button>
                </el-form-item>
                </el-form>
              </el-dialog>
              <el-button size="small" type="primary" @click='deletePatient(row)':icon="Delete" />  
            </template>
  
          </el-table-column>
        
      </el-table>

    <!-- 分页组件 -->
    <div class="pagination-container">
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="patients.length"
        layout="total, prev, pager, next, jumper">
      </el-pagination>
    </div>
  </div>
</template>


<style>

.pagination-container {
  display: flex;
  justify-content: flex-end; /* 将分页组件对齐到容器的右边 */
  padding: 10px 0; /* 添加上下内边距 */
}
</style>