import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login, getUserInfo, logout } from '@/api/auth'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(null)
  const menus = ref([])

  const loginAction = async (username, password) => {
    const res = await login(username, password)
    if (res.code === 200) {
      token.value = res.data.token
      userInfo.value = res.data.user
      localStorage.setItem('token', res.data.token)
      return true
    }
    return false
  }

  const getUserInfoAction = async () => {
    const res = await getUserInfo()
    if (res.code === 200) {
      userInfo.value = res.data.user
      menus.value = res.data.menus
    }
  }

  const logoutAction = async () => {
    await logout()
    token.value = ''
    userInfo.value = null
    menus.value = []
    localStorage.removeItem('token')
  }

  return {
    token,
    userInfo,
    menus,
    loginAction,
    getUserInfoAction,
    logoutAction
  }
})
