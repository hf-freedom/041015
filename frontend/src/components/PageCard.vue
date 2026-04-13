<template>
  <el-card>
    <template #header v-if="title || $slots.header">
      <div class="card-header">
        <slot name="header">
          <span>{{ title }}</span>
          <el-button v-if="showAdd" type="primary" @click="handleAdd">{{ addText }}</el-button>
        </slot>
      </div>
    </template>
    <slot />
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
    default: false
  },
  addText: {
    type: String,
    default: '新增'
  }
})

const emit = defineEmits(['add'])

const handleAdd = () => {
  emit('add')
}
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
