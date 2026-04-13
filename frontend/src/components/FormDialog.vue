<template>
  <el-dialog :title="title" v-model="visible" :width="width">
    <el-form :model="form" label-width="100px">
      <slot></slot>
    </el-form>
    <template #footer>
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="handleConfirm">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
const props = defineProps({
  title: {
    type: String,
    required: true
  },
  visible: {
    type: Boolean,
    required: true
  },
  form: {
    type: Object,
    required: true
  },
  width: {
    type: String,
    default: '500px'
  }
})

const emit = defineEmits(['update:visible', 'cancel', 'confirm'])

const handleCancel = () => {
  emit('update:visible', false)
  emit('cancel')
}

const handleConfirm = () => {
  emit('confirm')
}
</script>
