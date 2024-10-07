  <script  setup>
  import { reactive } from 'vue'
  import { ref } from 'vue';

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

// do not use same name with ref
  const form = reactive({
    username:null,
    password:null,
    name:null,
    department:null,

  })
  
  const onSubmit = () => {
    console.log(form.department)
  }
  </script>


<template>
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
        <el-select v-model="form.department" placeholder="please select your zone">
          <el-option
          v-for="department in departmentList"
          :key="department.departmentId"
          :label="department.dname"
          :value="department.departmentId"
        />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Create</el-button>
        <el-button>Cancel</el-button>
      </el-form-item>
    </el-form>
  </template>

  