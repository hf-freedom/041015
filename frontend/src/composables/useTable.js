import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

export function useTable(options) {
  const { fetchList, deleteItem, onEdit } = options

  const dataList = ref([])
  const loading = ref(false)
  const dialogVisible = ref(false)
  const isEdit = ref(false)
  const formData = ref({})

  const loadData = async () => {
    loading.value = true
    try {
      const res = await fetchList()
      dataList.value = res.data || []
    } catch (error) {
      ElMessage.error('加载数据失败')
    } finally {
      loading.value = false
    }
  }

  const handleAdd = (initialData = {}) => {
    isEdit.value = false
    formData.value = { ...initialData }
    dialogVisible.value = true
  }

  const handleEdit = (row, extraData = {}) => {
    isEdit.value = true
    formData.value = { ...row, ...extraData }
    dialogVisible.value = true
    onEdit?.(row)
  }

  const handleDelete = async (row, idField = 'id') => {
    try {
      await ElMessageBox.confirm('确定要删除该记录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
      await deleteItem(row[idField])
      ElMessage.success('删除成功')
      loadData()
    } catch (error) {
      if (error !== 'cancel') {
        ElMessage.error('删除失败')
      }
    }
  }

  const handleDialogClose = () => {
    dialogVisible.value = false
    formData.value = {}
  }

  onMounted(loadData)

  return {
    dataList,
    loading,
    dialogVisible,
    isEdit,
    formData,
    loadData,
    handleAdd,
    handleEdit,
    handleDelete,
    handleDialogClose
  }
}
