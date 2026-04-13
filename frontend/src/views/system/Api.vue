<template>
  <div class="api-management page-container">
    <PageCard title="API权限管理" show-add add-text="新增API" @add="handleAdd">
      <DataTable :data="apiList" border stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="名称" width="180" />
        <el-table-column prop="url" label="URL" />
        <el-table-column prop="method" label="方法" width="100">
          <template #default="{ row }">
            <el-tag :type="getMethodType(row.method)">
              {{ row.method }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="menuId" label="菜单ID" width="100" />
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
      :title="isEdit ? '编辑API' : '新增API'"
      :initial-data="form"
      @submit="handleSubmit"
    >
      <template #default="{ form }">
        <el-form-item label="名称">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="URL">
          <el-input v-model="form.url" />
        </el-form-item>
        <el-form-item label="请求方法">
          <el-select v-model="form.method" style="width: 100%">
            <el-option label="GET" value="GET" />
            <el-option label="POST" value="POST" />
            <el-option label="PUT" value="PUT" />
            <el-option label="DELETE" value="DELETE" />
          </el-select>
        </el-form-item>
        <el-form-item label="关联菜单">
          <el-tree-select
            v-model="form.menuId"
            :data="menuTree"
            :props="{ label: 'name', value: 'id' }"
            check-strictly
            clearable
          />
        </el-form-item>
      </template>
    </FormDialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getApiList, addApi, updateApi, deleteApi } from '@/api/api'
import { getMenuTree } from '@/api/menu'
import { ElMessage } from 'element-plus'
import { PageCard, DataTable, FormDialog } from '@/components'

const apiList = ref([])
const menuTree = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const form = ref({
  id: null,
  name: '',
  url: '',
  method: 'GET',
  menuId: null
})

const getMethodType = (method) => {
  const types = {
    GET: 'success',
    POST: 'primary',
    PUT: 'warning',
    DELETE: 'danger'
  }
  return types[method] || 'info'
}

const loadData = async () => {
  try {
    const [apis, menus] = await Promise.all([getApiList(), getMenuTree()])
    apiList.value = apis.data || []
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
    url: '',
    method: 'GET',
    menuId: null
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
      await updateApi(formData)
    } else {
      await addApi(formData)
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
    await deleteApi(row.id)
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
.api-management {
  padding: 20px;
}
</style>
