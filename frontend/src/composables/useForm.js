import { ref } from 'vue'
import { ElMessage } from 'element-plus'

export function useForm(options) {
  const { submit, onSuccess, initialData = {} } = options

  const loading = ref(false)
  const formRef = ref(null)
  const formData = ref({ ...initialData })

  const resetForm = () => {
    formData.value = { ...initialData }
    formRef.value?.resetFields()
  }

  const handleSubmit = async (done) => {
    try {
      await formRef.value?.validate()
      loading.value = true
      await submit(formData.value)
      ElMessage.success('操作成功')
      onSuccess?.()
      done?.()
    } catch (error) {
      if (error !== 'cancel') {
        ElMessage.error('操作失败')
      }
    } finally {
      loading.value = false
    }
  }

  const setFormData = (data) => {
    formData.value = { ...data }
  }

  return {
    formRef,
    formData,
    loading,
    resetForm,
    handleSubmit,
    setFormData
  }
}
