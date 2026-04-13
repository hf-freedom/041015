import { ref, reactive } from 'vue'

export function useDialog(initForm = {}) {
  const visible = ref(false)
  const formData = reactive({ ...initForm })

  const open = () => {
    Object.keys(initForm).forEach(key => {
      formData[key] = initForm[key]
    })
    visible.value = true
  }

  const close = () => {
    visible.value = false
  }

  return {
    visible,
    formData,
    open,
    close
  }
}
