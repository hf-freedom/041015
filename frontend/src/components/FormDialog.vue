<template>
  <el-dialog
    v-model="visible"
    :title="title"
    :width="width"
    destroy-on-close
    @close="handleClose"
  >
    <el-form
      ref="formRef"
      :model="formData"
      :rules="rules"
      label-width="100px"
    >
      <slot :form="formData" />
    </el-form>
    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" :loading="loading" @click="handleSubmit">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, watch } from 'vue'

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
    type: String,
    default: '500px'
  },
  initialData: {
    type: Object,
    default: () => ({})
  },
  rules: {
    type: Object,
    default: () => ({})
  }
})

const emit = defineEmits(['update:modelValue', 'submit'])

const visible = ref(false)
const loading = ref(false)
const formRef = ref(null)
const formData = ref({ ...props.initialData })

watch(() => props.modelValue, (val) => {
  visible.value = val
  if (val) {
    formData.value = { ...props.initialData }
  }
})

watch(() => props.initialData, (val) => {
  if (visible.value) {
    formData.value = { ...val }
  }
})

const handleClose = () => {
  visible.value = false
  emit('update:modelValue', false)
  formRef.value?.resetFields()
}

const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    loading.value = true
    emit('submit', { ...formData.value }, () => {
      loading.value = false
      handleClose()
    })
  } catch (error) {
    console.error('Form validation failed:', error)
  }
}
</script>
