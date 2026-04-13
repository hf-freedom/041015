import { ref } from 'vue'
import { ElMessage } from 'element-plus'

export function useTreeSelect(apiFn, options = {}) {
  const treeData = ref([])
  const loading = ref(false)

  const loadTree = async () => {
    loading.value = true
    try {
      const res = await apiFn()
      treeData.value = res.data || []
    } catch (error) {
      ElMessage.error(options.loadErrorMsg || '加载树形数据失败')
    } finally {
      loading.value = false
    }
  }

  return {
    treeData,
    loading,
    loadTree
  }
}

export function useTreeCheck(treeRef) {
  const getCheckedKeys = () => {
    return treeRef.value?.getCheckedKeys() || []
  }

  const setCheckedKeys = (keys) => {
    treeRef.value?.setCheckedKeys(keys || [])
  }

  const getCheckedNodes = () => {
    return treeRef.value?.getCheckedNodes() || []
  }

  return {
    getCheckedKeys,
    setCheckedKeys,
    getCheckedNodes
  }
}
