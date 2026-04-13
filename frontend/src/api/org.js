import request from '@/utils/request'

export function getOrgList() {
  return request({
    url: '/org/list',
    method: 'get'
  })
}

export function getOrgTree() {
  return request({
    url: '/org/tree',
    method: 'get'
  })
}

export function getOrgById(id) {
  return request({
    url: `/org/${id}`,
    method: 'get'
  })
}

export function addOrg(data) {
  return request({
    url: '/org/add',
    method: 'post',
    data
  })
}

export function updateOrg(data) {
  return request({
    url: '/org/update',
    method: 'put',
    data
  })
}

export function deleteOrg(id) {
  return request({
    url: `/org/delete/${id}`,
    method: 'delete'
  })
}
