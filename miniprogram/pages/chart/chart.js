// pages/chart/chart.js
import * as echarts from '../../components/ec-canvas/echarts'
const util = require('../../utils/util.js')

function initChart(canvas, width, height) {
    const monthsData = wx.getStorageSync('monthMarks') || [];
    const [xdata, ydata] = monthsData.reduce(([xdata, ydata], item) => {
        if (item.weight) {
            xdata.push(util.formatDate(new Date(item.day)));
            ydata.push(parseFloat(item.weight));
        }
        return [xdata, ydata];
    }, [[], []]);
    const chart = echarts.init(canvas, null, {
        width: width,
        height: height
    });
    canvas.setChart(chart);
    var option = {
        xAxis: {
            type: 'category',
            data: xdata
        },
        yAxis: {
            type: 'value',
            min: (Math.min(...ydata)-5)||0
        },
        series: [
            {
                data: ydata,
                type: 'line'
            }
        ]
    };
    chart.setOption(option);
    return chart;
}
Page({

    /**
     * 页面的初始数据
     */
    data: {
        ec: {
            onInit: initChart
        }
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {

    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow() {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide() {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage() {

    }
})