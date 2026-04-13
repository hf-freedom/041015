import { ref, reactive } from 'vue'

export function useCrud(fetchApi, addApi, updateApi, deleteApi) {
  const dataList = ref([])
  const loading = ref(false)
  const dialogVisible = ref(false)
  const formType = ref('add')
  const currentId = ref(null)

  const formData = reactive({})

  const fetchData = async () => {
    loading.value = true
    try {
      const res = await fetchApi()
      dataList.value = res.data
    } finally {
      loading.value = false
    }
  }

  const handleAdd = () => {
    formType.value = 'add'
    Object.keys(formData).forEach(key => {
      formData[key] = ''
    })
    dialogVisible.value = true
  }

  const handleEdit = (row) => {
    formType.value = 'edit'
    currentId.value = row.id
    Object.keys(row).forEach(key => {
      formData[key] = row[key]
    })
    dialogVisible.value = true
  }

  const handleDelete = async (id) => {
    await deleteApi(id)
    await fetchData()
  }

  const handleSubmit = async () => {
    if (formType.value === 'add') {
      await addApi(formData)
    } else {
      await updateApi(formData)
    }
    dialogVisible.value = false
    await fetchData()
  }

  return {
    dataList,
    loading,
    dialogVisible,
    formType,
    currentId,
    formData,
    fetchData,
    handleAdd,
    handleEdit,
    handleDelete,
    handleSubmit
  }
}
