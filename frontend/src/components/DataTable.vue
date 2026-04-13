<template>
  <div class="data-table">
    <el-table :data="data" border stripe v-bind="$attrs">
      <slot />
    </el-table>
  </div>
</template>

<script setup>
defineProps({
  data: {
    type: Array,
    default: () => []
  }
})
</script>

<style scoped>
.data-table {
  width: 100%;
}
</style>
