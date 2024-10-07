<script  setup>
import { ref ,reactive} from 'vue'
import { ElMessage } from 'element-plus'
import { Delete, Edit, } from '@element-plus/icons-vue'

const input1 = ref('')
const input2 = ref('')

import {appointmentListService} from '@/api/appointment.js'
// ,patientUpdateService



//得到预约列表
const appointments = ref([
  {       
    "id": null,
    "appointmentNum": 2,
    "patientId":null,
    "patientNum": 3,  
    "patientName":null,    
    "doctorName": null,
    "department": null,
    "date": null,
    "fee": null,
    "state":null
  }
  
]
)

const getAppointmentList = async() => {
  let result = await appointmentListService({"a":null}); 

  appointments.value = result.data;
};



getAppointmentList();
</script>




<template>
  

    <el-table :data="appointments" style="width: 100%">
      <el-table-column prop="appointmentNum" label="预约号" width="160" />  
      <el-table-column prop="patientNum" label="证件号"width="180" />
      <el-table-column prop="patientName" label="患者姓名" width="80" />    
      <el-table-column prop="doctorName" label="医生姓名"width="80" />
      <el-table-column prop="department" label="科室"width="180" />
      <el-table-column prop="date" label="日期"width="180" />
      <el-table-column prop="fee" label="费用"width="180" />
      <el-table-column prop="state" label="状态"width="180" />

        
    </el-table>
</template>