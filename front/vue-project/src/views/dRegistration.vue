<script setup>
import { ref ,reactive} from 'vue'
import { ElMessage } from 'element-plus'
import { Delete, Edit, } from '@element-plus/icons-vue'



import {registrationListService,registrationEditService} from '@/api/registration.js'
// 
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

//编辑挂号状态


const form1 = reactive({
    id:null,
    registrationNum:null,
    state:null,
})
const editRegistrationForm = ref(false)

const editRegistration=async(registration)=>{
  editRegistrationForm.value = true
    Object.assign(form1, {
        id:registration.id,
        registrationNum:registration.registrationNum,
        state:registration.state,
  });  
}
const saveRegistration=async()=>{
  editRegistrationForm.value = true
  let result = await registrationEditService(form1)
  if(result.code === 0)ElMessage.success('编辑成功')
  editRegistrationForm.value = false
  getRegistrationList()
}



getRegistrationList()



</script>



<template>



  <el-table :data="registrations" style="width: 100%">
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
            <el-button size="small" type="primary" @click='editRegistration(row)' :icon="Edit"  style="margin-right: 10px;"/>
            <el-dialog v-model="editRegistrationForm" title="编辑患者资料" width="500">
                <el-form :model="form1" label-width="auto" style="max-width: 600px">

                    <el-form-item label="挂号序号" >
                        <el-input v-model="form1.registrationNum" disabled/>
                    </el-form-item>
                    <el-form-item label="挂号状态" >
                        <el-input type="number" v-model="form1.state" />
                    </el-form-item>
                <el-form-item>    
                <el-button type="primary" @click="saveRegistration">保存</el-button>
                <el-button @click="editRegistrationForm = false">取消</el-button>
                </el-form-item>
              </el-form>
            </el-dialog>
        </template>

        </el-table-column>
      


    
  </el-table>
</template>