<template>
  <div class="profile">
    <el-card>
      <template #header>
        <span>个人中心</span>
      </template>
      <el-form :model="profileForm" label-width="100px" style="max-width: 500px">
        <el-form-item label="用户名">
          <el-input v-model="profileForm.username" disabled />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="profileForm.nickname" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="profileForm.email" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="profileForm.phone" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateProfile">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card style="margin-top: 20px">
      <template #header>
        <span>修改密码</span>
      </template>
      <el-form :model="passwordForm" label-width="100px" style="max-width: 500px">
        <el-form-item label="原密码">
          <el-input v-model="passwordForm.oldPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="passwordForm.newPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input v-model="passwordForm.confirmPassword" type="password" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="changePassword">修改密码</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { updateProfile as updateProfileApi, updatePassword } from '@/api/user'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()

const profileForm = ref({
  username: '',
  nickname: '',
  email: '',
  phone: ''
})

const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

onMounted(() => {
  if (userStore.userInfo) {
    profileForm.value = { ...userStore.userInfo }
  }
})

const updateProfile = async () => {
  try {
    await updateProfileApi(profileForm.value)
    ElMessage.success('保存成功')
    userStore.userInfo = { ...userStore.userInfo, ...profileForm.value }
  } catch (error) {
    ElMessage.error('保存失败')
  }
}

const changePassword = async () => {
  if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
    ElMessage.error('两次密码输入不一致')
    return
  }
  try {
    await updatePassword(passwordForm.value.oldPassword, passwordForm.value.newPassword)
    ElMessage.success('密码修改成功')
    passwordForm.value = { oldPassword: '', newPassword: '', confirmPassword: '' }
  } catch (error) {
    ElMessage.error('密码修改失败')
  }
}
</script>

<style scoped>
.profile {
  padding: 0;
}
</style>
