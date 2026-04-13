import request from '@/utils/request'

export function getApiList() {
  return request({
    url: '/permission/list',
    method: 'get'
  })
}

export function getApiById(id) {
  return request({
    url: `/permission/${id}`,
    method: 'get'
  })
}

export function addApi(data) {
  return request({
    url: '/permission/add',
    method: 'post',
    data
  })
}

export function updateApi(data) {
  return request({
    url: '/permission/update',
    method: 'put',
    data
  })
}

export function deleteApi(id) {
  return request({
    url: `/permission/delete/${id}`,
    method: 'delete'
  })
}
