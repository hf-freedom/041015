<template>
  <div class="role-management">
    <PageCard title="角色管理" @add="handleAdd">
      <el-table :data="roleList" border stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="名称" width="150" />
        <el-table-column prop="code" label="编码" width="150" />
        <el-table-column prop="description" label="描述" />
        <StatusColumn width="100" />
        <el-table-column label="操作" width="280">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="primary" @click="handleAssignMenu(row)">分配菜单</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </PageCard>

    <FormDialog v-model="dialogVisible" :title="isEdit ? '编辑角色' : '新增角色'" :formData="form" @submit="handleSubmit">
      <el-form-item label="名称">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="编码">
        <el-input v-model="form.code" />
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="form.description" type="textarea" />
      </el-form-item>
      <StatusSwitch v-model="form.status" />
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
import { ElMessage, ElMessageBox } from 'element-plus'
import PageCard from '@/components/PageCard.vue'
import FormDialog from '@/components/FormDialog.vue'
import StatusColumn from '@/components/StatusColumn.vue'
import StatusSwitch from '@/components/StatusSwitch.vue'
import { useTable, useDialog, useTreeSelect } from '@/composables/useTable'
import { useTreeCheck } from '@/composables/useTree'

const { dataList: roleList, loadData: loadRoles } = useTable(getRoleList)
const { treeData: menuTree, loadTree: loadMenuTree } = useTreeSelect(getMenuTree)
const { dialogVisible, isEdit, openDialog, closeDialog } = useDialog()

const menuDialogVisible = ref(false)
const menuTreeRef = ref(null)
const { setCheckedKeys, getCheckedKeys } = useTreeCheck(menuTreeRef)
const currentRoleId = ref(null)
const form = ref({
  id: null,
  name: '',
  code: '',
  description: '',
  status: 1
})

const loadData = async () => {
  await Promise.all([loadRoles(), loadMenuTree()])
}

const resetForm = () => ({
  id: null,
  name: '',
  code: '',
  description: '',
  status: 1
})

const handleAdd = () => {
  form.value = resetForm()
  openDialog(false)
}

const handleEdit = (row) => {
  form.value = { ...row }
  openDialog(true)
}

const handleSubmit = async () => {
  try {
    if (isEdit.value) {
      await updateRole(form.value)
    } else {
      await addRole(form.value)
    }
    ElMessage.success('操作成功')
    closeDialog()
    loadData()
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该角色吗？', '提示', { type: 'warning' })
    await deleteRole(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const handleAssignMenu = async (row) => {
  currentRoleId.value = row.id
  const res = await getRoleMenus(row.id)
  menuDialogVisible.value = true
  await nextTick()
  setCheckedKeys(res.data || [])
}

const handleSaveMenus = async () => {
  try {
    const checkedKeys = getCheckedKeys()
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
