<template>
  <el-tag :type="tagType" size="small">
    {{ tagText }}
  </el-tag>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  status: {
    type: Number,
    default: 1
  },
  activeText: {
    type: String,
    default: '启用'
  },
  inactiveText: {
    type: String,
    default: '禁用'
  },
  activeType: {
    type: String,
    default: 'success'
  },
  inactiveType: {
    type: String,
    default: 'danger'
  }
})

const tagType = computed(() => {
  return props.status === 1 ? props.activeType : props.inactiveType
})

const tagText = computed(() => {
  return props.status === 1 ? props.activeText : props.inactiveText
})
</script>
