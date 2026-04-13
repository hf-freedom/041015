<template>
  <div class="menu-management">
    <PageCard title="菜单管理" @add="handleAdd">
      <el-table :data="menuList" border stripe row-key="id" default-expand-all>
        <el-table-column prop="name" label="名称" width="180" />
        <el-table-column prop="path" label="路径" width="180" />
        <el-table-column prop="icon" label="图标" width="120" />
        <el-table-column prop="sort" label="排序" width="80" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">
            <el-tag :type="row.type === 1 ? 'primary' : 'success'">
              {{ row.type === 1 ? '菜单' : '按钮' }}
            </el-tag>
          </template>
        </el-table-column>
        <StatusColumn />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </PageCard>

    <FormDialog v-model="dialogVisible" :title="isEdit ? '编辑菜单' : '新增菜单'" :formData="form" @submit="handleSubmit">
      <el-form-item label="名称">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="路径">
        <el-input v-model="form.path" />
      </el-form-item>
      <el-form-item label="组件">
        <el-input v-model="form.component" />
      </el-form-item>
      <el-form-item label="上级菜单">
        <el-tree-select
          v-model="form.parentId"
          :data="menuTree"
          :props="{ label: 'name', value: 'id' }"
          check-strictly
          clearable
        />
      </el-form-item>
      <el-form-item label="图标">
        <el-input v-model="form.icon" />
      </el-form-item>
      <el-form-item label="排序">
        <el-input-number v-model="form.sort" :min="0" />
      </el-form-item>
      <el-form-item label="类型">
        <el-radio-group v-model="form.type">
          <el-radio :label="1">菜单</el-radio>
          <el-radio :label="2">按钮</el-radio>
        </el-radio-group>
      </el-form-item>
      <StatusSwitch v-model="form.status" />
    </FormDialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMenuList, getMenuTree, addMenu, updateMenu, deleteMenu } from '@/api/menu'
import { ElMessage, ElMessageBox } from 'element-plus'
import PageCard from '@/components/PageCard.vue'
import FormDialog from '@/components/FormDialog.vue'
import StatusColumn from '@/components/StatusColumn.vue'
import StatusSwitch from '@/components/StatusSwitch.vue'
import { useTable, useDialog, useTreeSelect } from '@/composables/useTable'

const { dataList: menuList, loadData: loadMenus } = useTable(getMenuList)
const { treeData: menuTree, loadTree: loadMenuTree } = useTreeSelect(getMenuTree)
const { dialogVisible, isEdit, openDialog, closeDialog } = useDialog()

const form = ref({
  id: null,
  name: '',
  path: '',
  component: '',
  parentId: 0,
  icon: '',
  sort: 1,
  type: 1,
  status: 1
})

const loadData = async () => {
  await Promise.all([loadMenus(), loadMenuTree()])
}

const resetForm = () => ({
  id: null,
  name: '',
  path: '',
  component: '',
  parentId: 0,
  icon: '',
  sort: 1,
  type: 1,
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
      await updateMenu(form.value)
    } else {
      await addMenu(form.value)
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
    await ElMessageBox.confirm('确定要删除该菜单吗？', '提示', { type: 'warning' })
    await deleteMenu(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

onMounted(() => {
  loadData()
})
</script>
