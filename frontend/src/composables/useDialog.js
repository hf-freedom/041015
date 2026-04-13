import { ref } from 'vue'

export function useDialog() {
  const visible = ref(false)
  const title = ref('')
  const data = ref(null)

  const open = (dialogTitle = '', dialogData = null) => {
    title.value = dialogTitle
    data.value = dialogData
    visible.value = true
  }

  const close = () => {
    visible.value = false
    data.value = null
  }

  const toggle = () => {
    visible.value = !visible.value
  }

  return {
    visible,
    title,
    data,
    open,
    close,
    toggle
  }
}
