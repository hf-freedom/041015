import { computed } from 'vue'
import { useUserStore } from '@/stores/user'

export function useAuth() {
  const userStore = useUserStore()

  const token = computed(() => userStore.token)
  const userInfo = computed(() => userStore.userInfo)
  const isLoggedIn = computed(() => !!userStore.token)

  const hasPermission = (permission) => {
    if (!userStore.userInfo?.permissions) return false
    return userStore.userInfo.permissions.includes(permission)
  }

  const hasRole = (role) => {
    if (!userStore.userInfo?.roles) return false
    return userStore.userInfo.roles.includes(role)
  }

  return {
    token,
    userInfo,
    isLoggedIn,
    hasPermission,
    hasRole
  }
}
