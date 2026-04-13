<template>
  <div class="menu-management page-container">
    <PageCard title="菜单管理" show-add add-text="新增菜单" @add="handleAdd">
      <DataTable :data="menuList" border stripe row-key="id" default-expand-all>
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
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <StatusTag :status="row.status" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </DataTable>
    </PageCard>

    <FormDialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑菜单' : '新增菜单'"
      :initial-data="form"
      @submit="handleSubmit"
    >
      <template #default="{ form }">
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
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </template>
    </FormDialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMenuList, getMenuTree, addMenu, updateMenu, deleteMenu } from '@/api/menu'
import { ElMessage } from 'element-plus'
import { PageCard, DataTable, FormDialog, StatusTag } from '@/components'

const menuList = ref([])
const menuTree = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
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
  try {
    const [list, tree] = await Promise.all([getMenuList(), getMenuTree()])
    menuList.value = list.data || []
    menuTree.value = tree.data || []
  } catch (error) {
    ElMessage.error('加载数据失败')
  }
}

const handleAdd = () => {
  isEdit.value = false
  form.value = {
    id: null,
    name: '',
    path: '',
    component: '',
    parentId: 0,
    icon: '',
    sort: 1,
    type: 1,
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
      await updateMenu(formData)
    } else {
      await addMenu(formData)
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
    await deleteMenu(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    ElMessage.error('删除失败')
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.menu-management {
  padding: 20px;
}
</style>
