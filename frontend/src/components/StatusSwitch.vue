<template>
  <el-form-item :label="label">
    <el-switch
      :model-value="modelValue"
      :active-value="1"
      :inactive-value="0"
      @update:model-value="$emit('update:modelValue', $event)"
    />
  </el-form-item>
</template>

<script setup>
defineProps({
  label: {
    type: String,
    default: '状态'
  },
  modelValue: {
    type: Number,
    default: 1
  }
})

defineEmits(['update:modelValue'])
</script>
