<template>
  <el-card>
    <template #header>
      <div class="card-header">
        <span>{{ title }}</span>
        <el-button v-if="showAdd" type="primary" @click="$emit('add')">
          {{ addText }}
        </el-button>
      </div>
    </template>
    <slot></slot>
  </el-card>
</template>

<script setup>
defineProps({
  title: {
    type: String,
    default: ''
  },
  showAdd: {
    type: Boolean,
    default: true
  },
  addText: {
    type: String,
    default: '新增'
  }
})

defineEmits(['add'])
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
