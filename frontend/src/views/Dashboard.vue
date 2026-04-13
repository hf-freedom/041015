<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" style="background: #409EFF">
              <el-icon size="30"><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.users }}</div>
              <div class="stat-label">用户数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" style="background: #67C23A">
              <el-icon size="30"><OfficeBuilding /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.orgs }}</div>
              <div class="stat-label">机构数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" style="background: #E6A23C">
              <el-icon size="30"><UserFilled /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.roles }}</div>
              <div class="stat-label">角色数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" style="background: #F56C6C">
              <el-icon size="30"><Menu /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.menus }}</div>
              <div class="stat-label">菜单数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="margin-top: 20px">
      <template #header>
        <span>欢迎使用权限管理系统</span>
      </template>
      <div>
        <p>本系统提供以下功能：</p>
        <ul>
          <li>用户管理：管理用户账号并分配角色</li>
          <li>组织机构：管理组织架构</li>
          <li>角色管理：管理角色并分配权限</li>
          <li>菜单管理：管理系统菜单</li>
          <li>API权限：管理API接口权限</li>
        </ul>
        <p style="margin-top: 20px">
          默认管理员账号：<strong>admin / admin123</strong>
        </p>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getUserList } from '@/api/user'
import { getOrgList } from '@/api/org'
import { getRoleList } from '@/api/role'
import { getMenuList } from '@/api/menu'

const stats = ref({
  users: 0,
  orgs: 0,
  roles: 0,
  menus: 0
})

onMounted(async () => {
  try {
    const [users, orgs, roles, menus] = await Promise.all([
      getUserList(),
      getOrgList(),
      getRoleList(),
      getMenuList()
    ])
    stats.value = {
      users: users.data?.length || 0,
      orgs: orgs.data?.length || 0,
      roles: roles.data?.length || 0,
      menus: menus.data?.length || 0
    }
  } catch (error) {
    console.error('Failed to load stats')
  }
})
</script>

<style scoped>
.dashboard {
  padding: 0;
}

.stat-card {
  display: flex;
  align-items: center;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.stat-info {
  margin-left: 20px;
}

.stat-value {
  font-size: 28px;
  font-weight: 600;
  color: #333;
}

.stat-label {
  font-size: 14px;
  color: #999;
  margin-top: 5px;
}

ul {
  padding-left: 20px;
}

li {
  margin: 10px 0;
}
</style>
