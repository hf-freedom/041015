import request from '@/utils/request'

export function getUserList() {
  return request({
    url: '/user/list',
    method: 'get'
  })
}

export function getUserById(id) {
  return request({
    url: `/user/${id}`,
    method: 'get'
  })
}

export function addUser(data) {
  return request({
    url: '/user/add',
    method: 'post',
    data
  })
}

export function updateUser(data) {
  return request({
    url: '/user/update',
    method: 'put',
    data
  })
}

export function deleteUser(id) {
  return request({
    url: `/user/delete/${id}`,
    method: 'delete'
  })
}

export function getUserRoles(userId) {
  return request({
    url: `/user/roles/${userId}`,
    method: 'get'
  })
}

export function updateUserRoles(userId, roleIds) {
  return request({
    url: `/user/roles/${userId}`,
    method: 'put',
    data: roleIds
  })
}

export function updateProfile(data) {
  return request({
    url: '/user/profile',
    method: 'put',
    data
  })
}

export function updatePassword(oldPassword, newPassword) {
  return request({
    url: '/user/password',
    method: 'put',
    data: { oldPassword, newPassword }
  })
}
