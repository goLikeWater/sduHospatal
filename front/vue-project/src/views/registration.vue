<script setup>
import { ref ,reactive} from 'vue'
import { ElMessage } from 'element-plus'
import { Delete, Edit, } from '@element-plus/icons-vue'



import {registrationListService,registrationAddService,registrationDeleteService } from '@/api/registration.js'
// ,appointmentEditService
//得到预约列表
const registrations = ref([
  {       
    "date": null,
    "patientName": null,
    "patientNum": null,
    "doctorName": null,
    "appointmentNum": null,
    "registrationNum": null,
    "patientId": null,
    "id": null,
    "time": null,
    "department": null,
    "time":null,
    "state":null
  }
  
]
)

const getRegistrationList = async() => {
  let result = await registrationListService(); 

  registrations.value = result.data;
};


import { computed } from 'vue';

const currentPage = ref(1);
const pageSize = ref(10);

// 计算当前页的数据
const pageData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return registrations.value.slice(start, end);
});

// 处理页码变化
const handleCurrentChange = (val) => {
  currentPage.value = val;
};


getRegistrationList()




//添加挂号信息
const patientList = ref([{
    "patientId": 1,
    "name": "患者A",
    "num": "A123",
  }
])
const patient = ref({
  "patientId":null,
  "patientName":null,
  "patientNum":null}
)



import {patientListService} from '@/api/patient.js'
const getPatientList = async() =>{
  let result = await patientListService("");
  patientList.value = result.data;

}
getPatientList()


const addRegistrationForm = ref(false)

const form = reactive({
    patientId:null,

})

const addRegistration =async()=>{

  let result = await registrationAddService(form);
  
  if(result.code === 0)ElMessage.success('添加成功');
  addRegistrationForm.value = false
  getRegistrationList();

}





//删除预约信息

const deleteRegistration=async(registration)=>{
  let result = await registrationDeleteService(registration.id)
  if(result.code === 0)ElMessage.success('删除成功')
  getRegistrationList();
}






</script>



<template>
  <el-button type="primary" @click="addRegistrationForm = true">add</el-button>

    <el-dialog v-model="addRegistrationForm" title="添加预约" width="500">
        <el-form :model="form" label-width="120px">
            <el-form-item label="选择患者">
              <el-select v-model="form.patientId" placeholder="请选择患者">
                <el-option
                  v-for="patient in patientList"
                  :key="patient.patientId"
                  :value="patient.patientId">
                  <template #default="{ label }">
                    <div>
                      <strong>证件号：</strong>{{ patient.num }}
                      <strong>姓名：</strong>{{ patient.name }}
                    </div>
                  </template>
                </el-option>
              </el-select>
            </el-form-item>
        
            <el-form-item>
              <el-button type="primary" @click="addRegistration">提交</el-button>
              <el-button type="primary" @click="addRegistrationForm = false">取消</el-button>

            </el-form-item>
          </el-form>
    </el-dialog>




  <el-table :data="pageData" style="width: 100%">
    <el-table-column prop="registrationNum" label="挂号序号" width="160" />  
    <el-table-column prop="patientNum" label="证件号"width="180" />
    <el-table-column prop="patientName" label="患者姓名" width="80" />    
    <el-table-column prop="doctorName" label="医生姓名"width="80" />
    <el-table-column prop="department" label="科室"width="180" />
    <el-table-column prop="date" label="日期"width="180" />
    <el-table-column prop="time" label="时间"width="180" />
    <el-table-column prop="state" label="状态"width="180" />

      <el-table-column  label="操作" width="180">   
        <template #default="{ row }">
          <el-button size="small" type="primary" @click="deleteRegistration(row)"  :icon="Delete" />  
        </template>

      </el-table-column>
    
  </el-table>


  <!-- 分页组件 -->
  <div class="pagination-container">
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="registrations.length"
      layout="total, prev, pager, next, jumper"
      style="display: flex;
    justify-content: flex-end; /* 将分页组件对齐到容器的右边 */
    padding: 10px 0; /* 添加上下内边距 */">
    </el-pagination>
  </div>



</template>