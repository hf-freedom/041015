<template>
  <el-table-column label="操作" :width="width">
    <template #default="{ row }">
      <el-button size="small" @click="$emit('edit', row)">编辑</el-button>
      <slot name="extra" :row="row"></slot>
      <el-button size="small" type="danger" @click="$emit('delete', row)">删除</el-button>
    </template>
  </el-table-column>
</template>

<script setup>
defineProps({
  width: {
    type: [Number, String],
    default: 200
  }
})

defineEmits(['edit', 'delete'])
</script>
