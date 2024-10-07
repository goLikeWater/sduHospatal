<script setup>
import { reactive,ref } from 'vue';
import { Edit, Delete } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus'


import { docAppointmentListService,
  docAppointmentAddService,
  docAppointmentEditService,
  docAppointmentDeleteService } from '@/api/docAppointment';



// ===============
// 得到预约列表
// ===============

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


const docAppointmentList = ref([
  {    


    "docAppointmentId":null,
    "username": null,
    "doctorId": null,
    "doctorName": null,
    "num": null,
    "date": null,
    "fee": null,

  }
]
)

 const searchForm =ref({
  "date":null,
  "departmentId":null
})


const getDocAppointmentList=async()=>{
  
  const processedForm = {
      departmentId:searchForm.value.departmentId,
      date: searchForm.value.date instanceof Date ? searchForm.value.date.toISOString().substring(0, 10) : '', // 如果是日期对象，转换为字符串,
  }
  let result = await docAppointmentListService(processedForm)
  docAppointmentList.value = result.data

}



// ===============
// 添加预约信息
// ===============


import { doctorListService } from '@/api/doctor';

const doctorList = ref([
  {
    "id":null,
    "name": 1,
    "department": null,
    "username": null
  }
  
]
)

const getDoctorList = async() => {
  let result = await doctorListService(""); 

  doctorList.value = result.data;
};

getDoctorList();

const form = reactive({
    doctorId:null,
    date:null,
    num:null,
    fee:null,
})
const addDocAppointmentForm = ref(false)

const addDocAppointment=async()=>{
  
  const processedForm = {
      doctorId:form.doctorId,
      date: form.date,
      num: parseInt(form.num, 10), // 转换为整数，基数为10
      fee: parseFloat(form.fee) // 可以保持为浮点数
  }
  let result = await docAppointmentAddService(processedForm)
  if(result.code === 0)ElMessage.success('添加成功');
  addDocAppointmentForm.value = false
  getDocAppointmentList()
}


// ===============
// 编辑预约信息
// ===============
const form1 = reactive({
    docAppointmentId:null,
    date:null,
    num:null,
    fee:null,
})
const editDocAppointmentForm = ref(false)

const editDocAppointment=async(docAppointment)=>{
  editDocAppointmentForm.value = true
    Object.assign(form1, {
      docAppointmentId:docAppointment.docAppointmentId,
      date:docAppointment.date,
      num:docAppointment.num,
      fee:docAppointment.fee,
      
  });  
}
const saveDocAppointment=async()=>{
  editDocAppointmentForm.value = true
  const processedForm = {
      docAppointmentId: form1.docAppointmentId,
      date: form1.date,
      num: parseInt(form1.num, 10), // 转换为整数，基数为10
      fee: parseFloat(form1.fee) // 可以保持为浮点数
  }
  let result = await docAppointmentEditService(processedForm)
  if(result.code === 0)ElMessage.success('编辑成功')
  editDocAppointmentForm.value = false
  getDocAppointmentList()
}
// ===============
// 删除预约信息
// ===============

const deleteDocAppointment=async(docAppointment)=>{
  let result = await docAppointmentDeleteService(docAppointment.docAppointmentId)
  if(result.code === 0)ElMessage.success('删除成功')
  getDocAppointmentList()
}


import { computed } from 'vue';

const currentPage = ref(1);
const pageSize = ref(10);

// 计算当前页的数据
const pageData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return docAppointmentList.value.slice(start, end);
});

// 处理页码变化
const handleCurrentChange = (val) => {
  currentPage.value = val;
};


getDocAppointmentList()

</script>


<template>
  <div>
    <span>日期 </span>
    <el-date-picker
    v-model="searchForm.date"
    type="datetime"
    placeholder="Pick a Date"
    format="YYYY/MM/DD "
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
    <el-button type="primary" @click="getDocAppointmentList">Search</el-button>

    <el-button type="primary" @click="addDocAppointmentForm = true">add</el-button>

      <el-dialog v-model="addDocAppointmentForm" title="添加预约信息" width="500">
        <el-form :model="form" label-width="auto" style="max-width: 600px">
          <el-form-item label="选择医生">
            <el-select v-model="form.doctorId" placeholder="请选择医生">
              <el-option
                v-for="doctor in doctorList"
                :key="doctor.doctorId"
                :value="doctor.doctorId">
                <template #default="{ label }">
                  <div>                  
                    <strong>工号：</strong>{{ doctor.username }}
                    <strong>医生：</strong>{{ doctor.name }}
                    <strong>科室：</strong>{{ doctor.department }}
                  </div>
                </template>
              </el-option>
            </el-select>
          </el-form-item>
        <el-form-item label="日期">
          <el-input v-model="form.date" />
        </el-form-item>
        <el-form-item label="容量">
          <el-input type="number" v-model="form.num" />
        </el-form-item>
        <el-form-item label="费用:（元）">
          <el-input v-model="form.fee" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addDocAppointment">添加</el-button>
          <el-button @click="addDocAppointmentForm = false">取消</el-button>
        </el-form-item>
        </el-form>
      </el-dialog>


  </div>
  <el-table :data="pageData" style="width: 100%">
    <el-table-column prop="username" label="医生工号" width="160" />
    <el-table-column prop="doctorName" label="医生姓名" width="160" />
    <el-table-column prop="date" label="日期" width="160" />
    <el-table-column prop="num" label="容量" width="80" />    
    <el-table-column prop="fee" label="费用:（元）"width="120" />
      <el-table-column  label="操作" width="300">   
        <template #default="{ row }">
          <el-button size="small" type="primary" @click='editDocAppointment(row)' :icon="Edit"  style="margin-right: 10px;"/>
          <el-dialog v-model="editDocAppointmentForm"  title="编辑预约资料" width="500">
            <el-form :model="form1" label-width="auto" style="max-width: 600px">
            <el-form-item label="日期">
              <el-input v-model="form1.date" />
            </el-form-item>
            <el-form-item label="容量">
              <el-input type="number" v-model="form1.num" />
            </el-form-item>
            <el-form-item label="费用:（元）">
              <el-input v-model="form1.fee" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveDocAppointment">保存</el-button>
              <el-button @click="editDocAppointmentForm = false">取消</el-button>
            </el-form-item>
            </el-form>
          </el-dialog>
          <el-button size="small" type="primary" @click='deleteDocAppointment(row)':icon="Delete" />  
        </template>

      </el-table-column>
    
  </el-table>

  <!-- 分页组件 -->
  <div class="pagination-container">
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="docAppointmentList.length"
      layout="total, prev, pager, next, jumper"
      style="display: flex;
    justify-content: flex-end; /* 将分页组件对齐到容器的右边 */
    padding: 10px 0; /* 添加上下内边距 */">
    </el-pagination>
  </div>
</template>