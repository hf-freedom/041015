import request from '@/utils/request'

export function getMenuList() {
  return request({
    url: '/menu/list',
    method: 'get'
  })
}

export function getMenuTree() {
  return request({
    url: '/menu/tree',
    method: 'get'
  })
}

export function getUserMenus() {
  return request({
    url: '/menu/user',
    method: 'get'
  })
}

export function getMenuById(id) {
  return request({
    url: `/menu/${id}`,
    method: 'get'
  })
}

export function addMenu(data) {
  return request({
    url: '/menu/add',
    method: 'post',
    data
  })
}

export function updateMenu(data) {
  return request({
    url: '/menu/update',
    method: 'put',
    data
  })
}

export function deleteMenu(id) {
  return request({
    url: `/menu/delete/${id}`,
    method: 'delete'
  })
}
