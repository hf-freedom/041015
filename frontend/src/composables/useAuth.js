import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

export function useAuth() {
  const router = useRouter()
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || 'null'))

  const isLoggedIn = computed(() => !!token.value)

  const setAuth = (newToken, user) => {
    token.value = newToken
    userInfo.value = user
    localStorage.setItem('token', newToken)
    localStorage.setItem('userInfo', JSON.stringify(user))
  }

  const clearAuth = () => {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  const logout = () => {
    clearAuth()
    ElMessage.success('退出成功')
    router.push('/login')
  }

  return {
    token,
    userInfo,
    isLoggedIn,
    setAuth,
    clearAuth,
    logout
  }
}
