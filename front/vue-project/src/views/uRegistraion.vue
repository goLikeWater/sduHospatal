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

const form = reactive({
    patientId:null,
}
);

//挂号
const addRegistration =async()=>{

    let result = await registrationAddService(form);

    if(result.code === 0)ElMessage.success('添加成功');
    getRegistrationList();

}



getRegistrationList()



</script>



<template>
  <el-button type="primary" @click="addRegistration">挂号</el-button>



  <el-table :data="registrations" style="width: 100%">
    <el-table-column prop="registrationNum" label="挂号序号" width="160" />  
    <el-table-column prop="patientNum" label="证件号"width="180" />
    <el-table-column prop="patientName" label="患者姓名" width="80" />    
    <el-table-column prop="doctorName" label="医生姓名"width="80" />
    <el-table-column prop="department" label="科室"width="180" />
    <el-table-column prop="date" label="日期"width="180" />
    <el-table-column prop="time" label="时间"width="180" />
    <el-table-column prop="state" label="状态"width="180" />



    
  </el-table>
</template>