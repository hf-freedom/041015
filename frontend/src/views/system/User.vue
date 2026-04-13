<template>
  <div class="user-management page-container">
    <PageCard title="用户管理" show-add add-text="新增用户" @add="handleAdd">
      <DataTable :data="userList" border stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="nickname" label="昵称" width="120" />
        <el-table-column prop="email" label="邮箱" width="180" />
        <el-table-column prop="phone" label="电话" width="120" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <StatusTag :status="row.status" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="280">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="primary" @click="handleAssignRole(row)">分配角色</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </DataTable>
    </PageCard>

    <FormDialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑用户' : '新增用户'"
      :initial-data="form"
      @submit="handleSubmit"
    >
      <template #default="{ form }">
        <el-form-item label="用户名">
          <el-input v-model="form.username" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="密码" v-if="!isEdit">
          <el-input v-model="form.password" type="password" show-password />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="所属机构">
          <el-tree-select
            v-model="form.orgId"
            :data="orgTree"
            :props="{ label: 'name', value: 'id' }"
            check-strictly
            clearable
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </template>
    </FormDialog>

    <el-dialog v-model="roleDialogVisible" title="分配角色" width="400px">
      <el-checkbox-group v-model="selectedRoles">
        <el-checkbox v-for="role in roleList" :key="role.id" :label="role.id">
          {{ role.name }}
        </el-checkbox>
      </el-checkbox-group>
      <template #footer>
        <el-button @click="roleDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSaveRoles">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getUserList, addUser, updateUser, deleteUser, getUserRoles, updateUserRoles } from '@/api/user'
import { getOrgTree } from '@/api/org'
import { getRoleList } from '@/api/role'
import { ElMessage } from 'element-plus'
import { PageCard, DataTable, FormDialog, StatusTag } from '@/components'

const userList = ref([])
const orgTree = ref([])
const roleList = ref([])
const dialogVisible = ref(false)
const roleDialogVisible = ref(false)
const isEdit = ref(false)
const form = ref({
  id: null,
  username: '',
  password: '',
  nickname: '',
  email: '',
  phone: '',
  orgId: null,
  status: 1
})
const selectedRoles = ref([])
const currentUserId = ref(null)

const loadData = async () => {
  try {
    const [users, orgs, roles] = await Promise.all([
      getUserList(),
      getOrgTree(),
      getRoleList()
    ])
    userList.value = users.data || []
    orgTree.value = orgs.data || []
    roleList.value = roles.data || []
  } catch (error) {
    ElMessage.error('加载数据失败')
  }
}

const handleAdd = () => {
  isEdit.value = false
  form.value = {
    id: null,
    username: '',
    password: '',
    nickname: '',
    email: '',
    phone: '',
    orgId: null,
    status: 1
  }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  form.value = { ...row }
  dialogVisible.value = true
}

const handleSubmit = async (formData, done) => {
  try {
    if (isEdit.value) {
      await updateUser(formData)
    } else {
      await addUser(formData)
    }
    ElMessage.success('操作成功')
    done()
    loadData()
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const handleDelete = async (row) => {
  try {
    await deleteUser(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    ElMessage.error('删除失败')
  }
}

const handleAssignRole = async (row) => {
  currentUserId.value = row.id
  const res = await getUserRoles(row.id)
  selectedRoles.value = res.data || []
  roleDialogVisible.value = true
}

const handleSaveRoles = async () => {
  try {
    await updateUserRoles(currentUserId.value, selectedRoles.value)
    ElMessage.success('分配成功')
    roleDialogVisible.value = false
  } catch (error) {
    ElMessage.error('分配失败')
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.user-management {
  padding: 20px;
}
</style>
