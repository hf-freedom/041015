<template>
  <el-dialog v-model="visible" :title="title" :width="width" @close="handleClose">
    <el-form :model="modelValue" :label-width="labelWidth">
      <slot></slot>
    </el-form>
    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="$emit('submit')">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false
  },
  title: {
    type: String,
    default: ''
  },
  width: {
    type: [Number, String],
    default: '500px'
  },
  labelWidth: {
    type: String,
    default: '100px'
  },
  formData: {
    type: Object,
    default: () => ({})
  }
})

const emit = defineEmits(['update:modelValue', 'submit', 'close'])

const visible = computed({
  get: () => props.modelValue,
  set: (val) => emit('update:modelValue', val)
})

const handleClose = () => {
  emit('update:modelValue', false)
  emit('close')
}
</script>
