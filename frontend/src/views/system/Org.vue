<template>
  <div class="org-management">
    <PageCard title="组织机构管理" @add="handleAdd">
      <el-table :data="orgList" border stripe row-key="id" default-expand-all>
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="code" label="编码" width="120" />
        <el-table-column prop="sort" label="排序" width="80" />
        <StatusColumn />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </PageCard>

    <FormDialog v-model="dialogVisible" :title="isEdit ? '编辑机构' : '新增机构'" :formData="form" @submit="handleSubmit">
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
      <StatusSwitch v-model="form.status" />
    </FormDialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getOrgList, getOrgTree, addOrg, updateOrg, deleteOrg } from '@/api/org'
import { ElMessage, ElMessageBox } from 'element-plus'
import PageCard from '@/components/PageCard.vue'
import FormDialog from '@/components/FormDialog.vue'
import StatusColumn from '@/components/StatusColumn.vue'
import StatusSwitch from '@/components/StatusSwitch.vue'
import { useTable, useDialog, useTreeSelect } from '@/composables/useTable'

const { dataList: orgList, loadData: loadOrgs } = useTable(getOrgList)
const { treeData: orgTree, loadTree: loadOrgTree } = useTreeSelect(getOrgTree)
const { dialogVisible, isEdit, openDialog, closeDialog } = useDialog()

const form = ref({
  id: null,
  name: '',
  code: '',
  parentId: 0,
  sort: 1,
  status: 1
})

const loadData = async () => {
  await Promise.all([loadOrgs(), loadOrgTree()])
}

const resetForm = () => ({
  id: null,
  name: '',
  code: '',
  parentId: 0,
  sort: 1,
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
      await updateOrg(form.value)
    } else {
      await addOrg(form.value)
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
    await ElMessageBox.confirm('确定要删除该机构吗？', '提示', { type: 'warning' })
    await deleteOrg(row.id)
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
