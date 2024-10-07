<script  setup>

import { ref } from 'vue'
import { ElMessage } from 'element-plus'

const input1 = ref('');
const input2 = ref('');
const input3 = ref('');

const pwd = ref(
    {
        "oldPwd":null,
        "newPwd":null,
        "rePwd":null
    }
)

import { userPwdResetService } from '@/api/user';

const pwdReset = async()=>{
    pwd.value = {
        "oldPwd":input1.value,
        "newPwd":input2.value,
        "rePwd":input3.value
    }
    let result = await userPwdResetService(pwd.value);
    if(result.code === 0)ElMessage.success('修改成功');


}

</script>


<template>
  <div class="form-container">
    <div class="form-item">
      <el-tag type="primary">原密码</el-tag>
      <el-input v-model="input1" style="width: 240px" placeholder="Please input" />
    </div>
    <div class="form-item">
      <el-tag type="primary">新密码</el-tag>
      <el-input v-model="input2" style="width: 240px" placeholder="Please input" />
    </div>
    <div class="form-item">
      <el-tag type="primary">确认密码</el-tag>
      <el-input v-model="input3" style="width: 240px" placeholder="Please input" />
    </div>
    <div class="form-item">
    <el-button class="button" type="primary" auto-insert-space @click="pwdReset" >
        确认
    </el-button>
    </div>
  </div>
</template>


<style scoped>
.form-container {
  display: flex;
  flex-direction: column;
  gap: 20px; /* 设置行间距 */
}

.form-item {
  display: flex;
  align-items: center;
  gap: 10px; /* 设置标签和输入框之间的间距 */
}
</style>

  