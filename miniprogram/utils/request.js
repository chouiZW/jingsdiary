//通过wx.request封装api请求
const baseURL = 'http://127.0.0.1:8080';

function request({ url, method = 'GET', data = {}, header = {}, showLoading = true }) {
    if (showLoading) wx.showLoading({ title: '加载中...', mask: true });
    const token = wx.getStorageSync('token');
    if (token) header = Object.assign({ Authorization: `Bearer ${token}` }, header);

    return new Promise((resolve, reject) => {
        wx.request({
            url: url.startsWith('http') ? url : baseURL + url,
            method,
            data,
            header: Object.assign({ 'Content-Type': 'application/json' }, header),
            success: (res) => {
                const { statusCode, data: resData } = res;
                if (statusCode >= 200 && statusCode < 300) {
                    resolve(resData);
                } else if (statusCode === 401) {
                    wx.removeStorageSync('token');
                    wx.showToast({ title: '请重新登录', icon: 'none' });
                    reject(res);
                } else {
                    wx.showToast({ title: (resData && resData.message) || '请求失败', icon: 'none' });
                    reject(res);
                }
            },
            fail: (err) => {
                wx.showToast({ title: '网络错误', icon: 'none' });
                reject(err);
            },
            complete: () => {
                if (showLoading) wx.hideLoading();
            }
        });
    });
}

function get(url, data = {}, options = {}) {
    return request(Object.assign({ url, method: 'GET', data }, options));
}

function post(url, data = {}, options = {}) {
    return request(Object.assign({ url, method: 'POST', data }, options));
}

module.exports = { request, get, post };