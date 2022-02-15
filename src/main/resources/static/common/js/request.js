import axios from 'axios';
import Config from '@/config';

// 创建axios实例
const service = axios.create({
  baseURL: Config.request_baseUrl, // api 的 base_url
  timeout: Config.request_timeout // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  config => { // 配置

    // config.headers['Authorization'] = 'Bearer ' + `JWT`; // 让每个请求携带自定义token 请根据实际情况自行修改
    // config.headers['Content-Type'] = 'application/json'; // 默认就是application/json形式的
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error);
  }
)

// response 拦截器
service.interceptors.response.use(
  response => { // 正常返回
    const code = response.status;
    console.log(code);
    console.log(response);

    // 需要返回结果。
    return response.data;
    // return Promise.reject('error');  // 不满足我们想要的条件时可自定义返回错误。
  },
  error => { // 出现错误时
    let code = 0
    try {
      code = error.response.data.status
    } catch (e) { // 没有状态码时可能是以下错误
      if (error.toString().indexOf('Error: timeout') !== -1) {
        console.log('网络请求超时');
        return Promise.reject(error);
      }
      if (error.toString().indexOf('Error: Network Error') !== -1) {
        console.log('网络请求错误');
        return Promise.reject(error)
      }
    }

    console.log(`错误码：${code}`);
    // 可以根据错误码进行相关操作，比如 401，跳转到登录页重新登录。
    return Promise.reject(error)
  }
)
export default service

export const get = (url, params = {}) => {
  return service({
    method: 'get',
    url: `${url}`,
    params: params
  });
};

export const post = (url, params) => {
  return service({
    method: 'post',
    url: `${url}`,
    data: params
  })
};

export const put = (url, params) => {
  return service({
    method: 'put',
    url: `${url}`,
    data: params
  })
};

export const deleteRequest = (url, params = {}) => {
  return service({
    method: 'delete',
    url: `${url}`,
    data: params
  });
};

export const formPost = (url, params) => {
  return service({
    method: 'post',
    url: `${url}`,
    data: params,
    transformRequest: [function (data) {
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  });
};

export const uploadFileRequest = (url, params) => {
  return service({
    method: 'post',
    url: `${url}`,
    data: params,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
};

export const formPut = (url, params) => {
  return service({
    method: 'put',
    url: `${url}`,
    data: params,
    transformRequest: [function (data) {
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  });
};