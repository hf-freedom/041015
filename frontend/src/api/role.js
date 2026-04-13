import request from '@/utils/request'

export function getRoleList() {
  return request({
    url: '/role/list',
    method: 'get'
  })
}

export function getRoleById(id) {
  return request({
    url: `/role/${id}`,
    method: 'get'
  })
}

export function addRole(data) {
  return request({
    url: '/role/add',
    method: 'post',
    data
  })
}

export function updateRole(data) {
  return request({
    url: '/role/update',
    method: 'put',
    data
  })
}

export function deleteRole(id) {
  return request({
    url: `/role/delete/${id}`,
    method: 'delete'
  })
}

export function getRoleMenus(roleId) {
  return request({
    url: `/role/menus/${roleId}`,
    method: 'get'
  })
}

export function updateRoleMenus(roleId, menuIds) {
  return request({
    url: `/role/menus/${roleId}`,
    method: 'put',
    data: menuIds
  })
}

export function getRoleUsers(roleId) {
  return request({
    url: `/role/users/${roleId}`,
    method: 'get'
  })
}

export function bindUsers(roleId, userIds) {
  return request({
    url: `/role/bindUsers/${roleId}`,
    method: 'post',
    data: userIds
  })
}

export function unbindUsers(roleId, userIds) {
  return request({
    url: `/role/unbindUsers/${roleId}`,
    method: 'post',
    data: userIds
  })
}
