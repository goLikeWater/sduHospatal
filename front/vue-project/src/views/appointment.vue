<script  setup>
import { ref ,reactive} from 'vue'
import { ElMessage } from 'element-plus'
import { Delete, Edit, } from '@element-plus/icons-vue'

const input1 = ref('')
const input2 = ref('')

import {appointmentListService,appointmentAddService,appointmentDeleteService ,appointmentEditService} from '@/api/appointment.js'
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

const searchForm =ref({
  "patientNum":null,
})

const getAppointmentList = async() => {
  
  let result = await appointmentListService(searchForm.value); 

  appointments.value = result.data;
};

//添加预约信息
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
const searchForm1 =ref({
  "date":null,
  "departmentId":null
})
import {docAppointmentListService} from '@/api/docAppointment.js'
const getDocAppointmentList = async() =>{
  let result = await docAppointmentListService(searchForm1.value);
  docAppointmentList.value = result.data;

}
getDocAppointmentList()


const addAppointmentForm = ref(false)

const form = reactive({
    patientId:null,
    docAppointmentId:null,

})

const addAppointment =async()=>{

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

//编辑预约信息

const form1 = reactive({
    id:null,
    patientId:null,
    patientName:null,
    docAppointmentId:null,
})
const editAppointmentForm = ref(false)

const editAppointment=async(appointment)=>{
  editAppointmentForm.value = true
    Object.assign(form1, {
        id:appointment.id,
        patientId:appointment.patientId,
        docAppointmentId:appointment.docAppointmentId,
        patientName:appointment.patientName,
  });  
}
const saveAppointment=async()=>{
  editAppointmentForm.value = true
  console.log(form1)
  let result = await appointmentEditService(form1)
  if(result.code === 0)ElMessage.success('编辑成功')
  editAppointmentForm.value = false
  getAppointmentList()
}


import { computed } from 'vue';

const currentPage = ref(1);
const pageSize = ref(10);

// 计算当前页的数据
const pageData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return appointments.value.slice(start, end);
});

// 处理页码变化
const handleCurrentChange = (val) => {
  currentPage.value = val;
};


getAppointmentList();
</script>




<template>
  <div>
    <span>患者证件号</span>
    <el-input
      v-model:modelValue="searchForm.patientNum"
      style="width: 200px"
      placeholder="请输入患者证件号"
    />
    <el-button type="primary" @click="getAppointmentList">Search</el-button>

    <el-button type="primary" @click="addAppointmentForm = true">add</el-button>

    <el-dialog v-model="addAppointmentForm" title="添加预约" width="500">
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
        
            <el-form-item label="选择预约">
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
  

    <el-table :data="pageData" style="width: 100%">
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
            <el-button size="small" type="primary" @click='editAppointment(row)' :icon="Edit"  style="margin-right: 10px;"/>
            <el-dialog v-model="editAppointmentForm" title="编辑患者资料" width="500">
              <el-form :model="form1" label-width="auto" style="max-width: 600px">
                <el-form-item label="患者姓名" >
                    <el-input v-model="form1.patientName" disabled/>
                  </el-form-item>
                    
              
                  <el-form-item label="选择预约">
                    <el-select v-model="form1.docAppointmentId" placeholder="请选择预约">
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
                <el-button type="primary" @click="saveAppointment">保存</el-button>
                <el-button @click="editAppointmentForm = false">取消</el-button>
              </el-form-item>
              </el-form>
            </el-dialog>
            <el-button size="small" type="primary" @click='deleteAppointment(row)':icon="Delete" />  
          </template>

        </el-table-column>
      
    </el-table>


  <!-- 分页组件 -->
  <div class="pagination-container">
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="appointments.length"
      layout="total, prev, pager, next, jumper"
      style="display: flex;
    justify-content: flex-end; /* 将分页组件对齐到容器的右边 */
    padding: 10px 0; /* 添加上下内边距 */">
    </el-pagination>
  </div>


</template>