<template>
  <el-table-column prop="status" label="状态" :width="width">
    <template #default="{ row }">
      <el-tag :type="row.status === 1 ? 'success' : 'danger'">
        {{ row.status === 1 ? '启用' : '禁用' }}
      </el-tag>
    </template>
  </el-table-column>
</template>

<script setup>
defineProps({
  width: {
    type: [Number, String],
    default: 100
  }
})
</script>
