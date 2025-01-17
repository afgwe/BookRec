import axios from '@/utils/request'
import CryptoJS from 'crypto-js'

// 登录
export const loginAPI = user => {
  return axios.post('/users/login', user)
}

export const AdminLoginAPI = user => {
  return axios.post('/users/syslogin', user)
}

// 注册
export const registerAPI = data => {
  const user = {
    username: data.username,
    password: CryptoJS.SHA256(data.password).toString(),
    code: data.code
  }
  return axios.post('/users/register', user)
}

// 分页查询用户
export const getUsersByPageAPI = (currentPage, pageSize, searchData) => {
  return axios.post(`/users/${currentPage}/${pageSize}`, searchData)
}