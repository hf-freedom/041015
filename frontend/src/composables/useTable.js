import { ref, reactive } from 'vue'

export function useTable() {
  const tableData = ref([])
  const loading = ref(false)
  const pagination = reactive({
    page: 1,
    pageSize: 10,
    total: 0
  })

  const handleSizeChange = (val) => {
    pagination.pageSize = val
    pagination.page = 1
  }

  const handleCurrentChange = (val) => {
    pagination.page = val
  }

  return {
    tableData,
    loading,
    pagination,
    handleSizeChange,
    handleCurrentChange
  }
}
