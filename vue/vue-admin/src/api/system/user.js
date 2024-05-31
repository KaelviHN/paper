import request from '@/utils/request'
import { praseStrEmpty } from '@/utils/sg'
// 查询用户列表
export function listUser(query) {
  return request({
    url: '/system/user/list',
    method: 'get',
    params: query
  })
}
// 查询用户详细
export function getUser(id) {
  return request({
    url: '/system/user/' + praseStrEmpty(id),
    method: 'get'
  })
}

// 新增用户
export function addUser(data) {
  return request({
    url: '/system/user',
    method: 'post',
    data: data
  })
}
// 删除用户
export function delUser(id) {
  return request({
    url: '/system/user/' + id,
    method: 'delete'
  })
}
// 用户密码重置
export function resetUserPwd(id, password) {
  const data = {
    id,
    password
  }
  return request({
    url: '/system/user/resetPwd',
    method: 'put',
    data: data
  })
}

// 修改用户
export function updateUser(data) {
  return request({
    url: '/system/user',
    method: 'put',
    data: data
  })
}
// 用户状态修改
export function changeUserStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/system/user/changeStatus',
    method: 'put',
    data: data
  })
}
