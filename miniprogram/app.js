// app.js
import {request} from "./utils/request.js";
App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
        success: (res) => {
          if(res.code){
            request({
              url: '/auth/login',
              method: 'POST',
              data:{ code:res.code, loginType:1}
          }).then(resData=>{
            debugger
              wx.setStorageSync('token', resData.data.token);
              wx.navigateTo({
                url: '/pages/calendar/calendar'
              })
          }).catch(err=>{
              console.error('登录失败', err);
          });
          }
        }
    })
  },
  globalData: {
    userInfo: null
  }
})
