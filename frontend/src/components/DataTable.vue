<template>
  <div class="data-table">
    <el-table :data="data" v-loading="loading" stripe border style="width: 100%">
      <slot></slot>
    </el-table>
    <el-pagination
      v-if="showPagination"
      class="table-pagination"
      :page-size="pageSize"
      :current-page="currentPage"
      :total="total"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script setup>
const props = defineProps({
  data: {
    type: Array,
    required: true
  },
  loading: {
    type: Boolean,
    default: false
  },
  showPagination: {
    type: Boolean,
    default: true
  },
  pageSize: {
    type: Number,
    default: 10
  },
  currentPage: {
    type: Number,
    default: 1
  },
  total: {
    type: Number,
    default: 0
  }
})

const emit = defineEmits(['size-change', 'current-change'])

const handleSizeChange = (val) => {
  emit('size-change', val)
}

const handleCurrentChange = (val) => {
  emit('current-change', val)
}
</script>

<style scoped>
.data-table {
  background: #fff;
  border-radius: 4px;
}

.table-pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
