import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

export function useTable(apiFn, options = {}) {
  const dataList = ref([])
  const loading = ref(false)

  const loadData = async () => {
    loading.value = true
    try {
      const res = await apiFn()
      dataList.value = res.data || []
    } catch (error) {
      ElMessage.error(options.loadErrorMsg || '加载数据失败')
    } finally {
      loading.value = false
    }
  }

  return {
    dataList,
    loading,
    loadData
  }
}

export function useDialog() {
  const dialogVisible = ref(false)
  const isEdit = ref(false)

  const openDialog = (edit = false) => {
    isEdit.value = edit
    dialogVisible.value = true
  }

  const closeDialog = () => {
    dialogVisible.value = false
  }

  return {
    dialogVisible,
    isEdit,
    openDialog,
    closeDialog
  }
}

export function useTreeSelect(apiFn, options = {}) {
  const treeData = ref([])
  const loading = ref(false)

  const loadTree = async () => {
    loading.value = true
    try {
      const res = await apiFn()
      treeData.value = res.data || []
    } catch (error) {
      ElMessage.error(options.loadErrorMsg || '加载树形数据失败')
    } finally {
      loading.value = false
    }
  }

  return {
    treeData,
    loading,
    loadTree
  }
}

export function useCrud(options) {
  const { addFn, updateFn, deleteFn, loadFn, successMsg = '操作成功' } = options
  const { dialogVisible, isEdit, openDialog, closeDialog } = useDialog()

  const handleSubmit = async (formData) => {
    try {
      if (isEdit.value) {
        await updateFn(formData)
      } else {
        await addFn(formData)
      }
      ElMessage.success(successMsg)
      closeDialog()
      loadFn && loadFn()
    } catch (error) {
      ElMessage.error('操作失败')
    }
  }

  const handleDelete = async (id, confirmMsg = '确定要删除吗？') => {
    try {
      await ElMessageBox.confirm(confirmMsg, '提示', { type: 'warning' })
      await deleteFn(id)
      ElMessage.success('删除成功')
      loadFn && loadFn()
    } catch (error) {
      if (error !== 'cancel') {
        ElMessage.error('删除失败')
      }
    }
  }

  return {
    dialogVisible,
    isEdit,
    openDialog,
    closeDialog,
    handleSubmit,
    handleDelete
  }
}
