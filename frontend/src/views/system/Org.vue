<template>
  <div class="org-management page-container">
    <PageCard title="组织机构管理" show-add add-text="新增机构" @add="handleAdd">
      <DataTable :data="orgList" border stripe row-key="id" default-expand-all>
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="code" label="编码" width="120" />
        <el-table-column prop="sort" label="排序" width="80" />
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
      :title="isEdit ? '编辑机构' : '新增机构'"
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
        <el-form-item label="上级机构">
          <el-tree-select
            v-model="form.parentId"
            :data="orgTree"
            :props="{ label: 'name', value: 'id' }"
            check-strictly
            clearable
          />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" />
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
import { getOrgList, getOrgTree, addOrg, updateOrg, deleteOrg } from '@/api/org'
import { ElMessage } from 'element-plus'
import { PageCard, DataTable, FormDialog, StatusTag } from '@/components'

const orgList = ref([])
const orgTree = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const form = ref({
  id: null,
  name: '',
  code: '',
  parentId: 0,
  sort: 1,
  status: 1
})

const loadData = async () => {
  try {
    const [list, tree] = await Promise.all([getOrgList(), getOrgTree()])
    orgList.value = list.data || []
    orgTree.value = tree.data || []
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
    parentId: 0,
    sort: 1,
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
      await updateOrg(formData)
    } else {
      await addOrg(formData)
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
    await deleteOrg(row.id)
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
.org-management {
  padding: 20px;
}
</style>
