<script  setup>
import { ref ,reactive} from 'vue'
import { ElMessage } from 'element-plus'
import { Delete, Edit, } from '@element-plus/icons-vue'


import {appointmentListService,appointmentAddService,appointmentDeleteService ,appointmentEditService} from '@/api/appointment.js'
// ,patientUpdateService



//得到预约列表


const departments = ref([{
  "departmentId":null,
  "dname":null,
  "address":null}
])

import {departmentListService} from '@/api/department.js'
const getDepartmentList = async() =>{
  let result = await departmentListService();
  departments.value = result.data;

}
getDepartmentList();


 const searchForm =ref({
  "date":null,
  "departmentId":null
})




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

//添加预约信息


const docAppointmentList = ref([{
    "docAppointmentId": 1,
    "username": "张三",
    "doctorId": 101,
    "doctorName": "李医生",
    "num": 1,
    "date": "2024-09-01",
    "fee": 100,
  }
])

const docAppointment = ref(
  {
    "docAppointmentId": 2,
    "username": "李四",
    "doctorId": 102,
    "doctorName": "王医生",
    "num": 2,
    "date": "2024-09-02",
    "fee": 150,
  },
)

import {docAppointmentListService} from '@/api/docAppointment.js'
const getDocAppointmentList = async() =>{

  const processedForm = {
      departmentId:searchForm.value.departmentId,
      date: searchForm.value.date instanceof Date ? searchForm.value.date.toISOString().substring(0, 10) : '', // 如果是日期对象，转换为字符串,
  }
  let result = await docAppointmentListService(processedForm);
  docAppointmentList.value = result.data;
  addAppointmentForm.value = true


}


const addAppointmentForm = ref(false)

const form = reactive({
    docAppointmentId:null,

})



const addAppointment =async()=>{
  getDocAppointmentList()

  let result = await appointmentAddService(form);
  
  if(result.code === 0)ElMessage.success('添加成功');
  addAppointmentForm.value = false
  getAppointmentList();

}



const deleteAddAppointmentForm=()=>{
  clearAddAppointmentForm();
  addAppointmentForm.value = false

}

const clearAddAppointmentForm = () => {
  Object.assign(form, {
    patientId: null,
    docAppointmentId: null
  });
};


//删除预约信息


const deleteAppointment=async(appointment)=>{
  let result = await appointmentDeleteService(appointment.id)
  if(result.code === 0)ElMessage.success('删除成功')
  getAppointmentList()
}


getAppointmentList();
</script>




<template>
  <div>
    <span>日期 </span>
    <el-date-picker
    v-model="searchForm.date"
    type="datetime"
    placeholder="Pick a Date"
    format="YYYY/MM/DD"
      />
    <span> 科室 </span>
    <el-select
      v-model="searchForm.departmentId"
      placeholder="Select"
      size="large"
      style="width: 240px"
    >
    <el-option
      v-for="item in departments"
      :key="item.departmentId"
      :label="item.dname"
      :value="item.departmentId"
    />
  </el-select>
    <el-button type="primary" @click="getDocAppointmentList">选择预约</el-button>

      <el-dialog v-model="addAppointmentForm" title="选择预约" width="500">
          <el-form :model="form" label-width="120px">
              
          
              <el-form-item label="预约信息">
                <el-select v-model="form.docAppointmentId" placeholder="请选择预约">
                  <el-option
                    v-for="appointment in docAppointmentList"
                    :key="appointment.docAppointmentId"
                    :value="appointment.docAppointmentId">
                    <template #default="{ label }">
                      <div>                  
                        <strong>工号：</strong>{{ appointment.username }}
                        <strong>医生：</strong>{{ appointment.doctorName }}
                        <strong>日期：</strong>{{ appointment.date }}
                        <strong>费用：</strong>{{ appointment.fee }}元
                      </div>
                    </template>
                  </el-option>
                </el-select>
              </el-form-item>
          
              <el-form-item>
                <el-button type="primary" @click="addAppointment">提交</el-button>
                <el-button type="primary" @click="deleteAddAppointmentForm">取消</el-button>

              </el-form-item>
            </el-form>
      </el-dialog>
  </div>
  

    <el-table :data="appointments" style="width: 100%">
      <el-table-column prop="appointmentNum" label="预约号" width="160" />  
      <el-table-column prop="patientNum" label="证件号"width="180" />
      <el-table-column prop="patientName" label="患者姓名" width="80" />    
      <el-table-column prop="doctorName" label="医生姓名"width="80" />
      <el-table-column prop="department" label="科室"width="180" />
      <el-table-column prop="date" label="日期"width="180" />
      <el-table-column prop="fee" label="费用"width="180" />
      <el-table-column prop="state" label="状态"width="180" />

        <el-table-column  label="操作" width="180">   
          <template #default="{ row }">
            <el-button size="small" type="primary" @click='deleteAppointment(row)':icon="Delete" />  
          </template>

        </el-table-column>
      
    </el-table>
</template>