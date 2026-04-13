import { ref, onMounted, onUnmounted } from 'vue'

export function useLoading(initLoading = false) {
  const loading = ref(initLoading)

  const startLoading = () => {
    loading.value = true
  }

  const stopLoading = () => {
    loading.value = false
  }

  const withLoading = async (fn) => {
    loading.value = true
    try {
      return await fn()
    } finally {
      loading.value = false
    }
  }

  return {
    loading,
    startLoading,
    stopLoading,
    withLoading
  }
}

export function useAsync(asyncFn, immediate = false) {
  const data = ref(null)
  const error = ref(null)
  const loading = ref(false)

  const execute = async (...args) => {
    loading.value = true
    error.value = null
    try {
      const result = await asyncFn(...args)
      data.value = result
      return result
    } catch (e) {
      error.value = e
      throw e
    } finally {
      loading.value = false
    }
  }

  if (immediate) {
    onMounted(execute)
  }

  return {
    data,
    error,
    loading,
    execute
  }
}
