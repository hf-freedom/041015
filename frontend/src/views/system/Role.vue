<template>
  <div class="role-management page-container">
    <PageCard title="角色管理" show-add add-text="新增角色" @add="handleAdd">
      <DataTable :data="roleList" border stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="名称" width="150" />
        <el-table-column prop="code" label="编码" width="150" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <StatusTag :status="row.status" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="280">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="primary" @click="handleAssignMenu(row)">分配菜单</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </DataTable>
    </PageCard>

    <FormDialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑角色' : '新增角色'"
      :initial-data="form"
      @submit="handleSubmit"
    >
      <template #default="{ form }">
        <el-form-item label="名称">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="编码">
          <el-input v-model="form.code" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </template>
    </FormDialog>

    <el-dialog v-model="menuDialogVisible" title="分配菜单" width="500px">
      <el-tree
        ref="menuTreeRef"
        :data="menuTree"
        :props="{ label: 'name', children: 'children' }"
        show-checkbox
        node-key="id"
        default-expand-all
      />
      <template #footer>
        <el-button @click="menuDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSaveMenus">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import { getRoleList, addRole, updateRole, deleteRole, getRoleMenus, updateRoleMenus } from '@/api/role'
import { getMenuTree } from '@/api/menu'
import { ElMessage } from 'element-plus'
import { PageCard, DataTable, FormDialog, StatusTag } from '@/components'

const roleList = ref([])
const menuTree = ref([])
const dialogVisible = ref(false)
const menuDialogVisible = ref(false)
const isEdit = ref(false)
const menuTreeRef = ref(null)
const currentRoleId = ref(null)
const form = ref({
  id: null,
  name: '',
  code: '',
  description: '',
  status: 1
})

const loadData = async () => {
  try {
    const [roles, menus] = await Promise.all([getRoleList(), getMenuTree()])
    roleList.value = roles.data || []
    menuTree.value = menus.data || []
  } catch (error) {
    ElMessage.error('加载数据失败')
  }
}

const handleAdd = () => {
  isEdit.value = false
  form.value = {
    id: null,
    name: '',
    code: '',
    description: '',
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
      await updateRole(formData)
    } else {
      await addRole(formData)
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
    await deleteRole(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    ElMessage.error('删除失败')
  }
}

const handleAssignMenu = async (row) => {
  currentRoleId.value = row.id
  const res = await getRoleMenus(row.id)
  menuDialogVisible.value = true
  await nextTick()
  menuTreeRef.value?.setCheckedKeys(res.data || [])
}

const handleSaveMenus = async () => {
  try {
    const checkedKeys = menuTreeRef.value?.getCheckedKeys() || []
    await updateRoleMenus(currentRoleId.value, checkedKeys)
    ElMessage.success('分配成功')
    menuDialogVisible.value = false
  } catch (error) {
    ElMessage.error('分配失败')
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.role-management {
  padding: 20px;
}
</style>
