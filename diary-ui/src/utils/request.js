// 简单的请求封装（基于 fetch）
const BASE_URL = import.meta.env.VITE_API_BASE || 'http://localhost:8080/'

export async function request({ url, method = 'GET', data, headers = {} } = {}) {
  const token = localStorage.getItem('authToken')
  const fetchHeaders = {
    'Content-Type': 'application/json',
    ...headers,
  }
  if (token) {
    fetchHeaders['Authorization'] = `Bearer ${token}`
  }

  const finalUrl = BASE_URL.replace(/\/$/, '') + '/' + String(url).replace(/^\//, '')

  const res = await fetch(finalUrl, {
    method,
    headers: fetchHeaders,
    body: method.toUpperCase() === 'GET' || !data ? undefined : JSON.stringify(data),
  })

  let json = null
  try {
    json = await res.json()
  } catch (e) {
    // 非 JSON 响应
  }

  if (!res.ok) {
    const msg = (json && json.msg) || res.statusText || 'Network Error'
    const err = new Error(msg)
    err.response = json
    throw err
  }

  return json
}

export function setAuthToken(token) {
  if (token) {
    localStorage.setItem('authToken', token)
  } else {
    localStorage.removeItem('authToken')
  }
}

export function getAuthToken() {
  return localStorage.getItem('authToken')
}

export default request
