// pages/chart/chart.js
import * as echarts from '../../components/ec-canvas/echarts'
const util = require('../../utils/util.js')

Page({
    data: {
        ec: {},
        chartHeight: 0, // 动态计算的图表高度
    },
    chartInstance: null,
    onLoad() {
        // 1. 初始化时计算图表尺寸（竖屏优先）
        this.calcChartSize();
        // 2. 初始化 ECharts 配置
        this.initECharts();
    },

    onShow() {
        // 3. 页面显示时（如从后台返回），重新适配尺寸
        this.calcChartSize();
        this.resizeChart();
    },

    // 计算图表尺寸（核心：竖屏高度占满可用空间）
    calcChartSize() {
        const systemInfo = wx.getWindowInfo();
        const windowHeight = systemInfo.windowHeight; // 屏幕可用高度（竖屏时为纵向高度）
        const windowWidth = systemInfo.windowWidth; // 屏幕宽度

        // 竖屏高度：占屏幕高度的 70%~80%（根据需求调整，避免遮挡其他内容）
        const chartHeight = windowHeight * 0.75;
        // 图表宽度：屏幕宽度 - 左右边距（20rpx * 2 = 40rpx，转 px 需除以 2）
        const chartWidth = windowWidth - 40;

        this.setData({
            chartHeight,
            chartWidth
        });
    },

    // 初始化 ECharts 配置
    initECharts() {
        const that = this;
        this.setData({
            ec: {
                onInit: function (canvas, width, height) {
                    // 4. 初始化图表实例（用动态计算的宽高覆盖默认值）
                    const chart = echarts.init(canvas, null, {
                        width: that.data.chartWidth,
                        height: that.data.chartHeight,
                        devicePixelRatio: wx.getWindowInfo().pixelRatio // 适配高清屏
                    });
                    canvas.setChart(chart);
                    that.chartInstance = chart; // 保存实例

                    // 5. 适配型配置（重点看注释）
                    const option = that.getChartOption();
                    chart.setOption(option);
                    return chart;
                }
            }
        });
    },

    // 图表核心配置（竖屏适配关键项）
    getChartOption() {
        const systemInfo = wx.getWindowInfo();
        // 动态计算字体大小：根据屏幕宽度缩放（375px 屏为基准，其他屏按比例放大/缩小）
        const baseFontSize = 11;
        const fontSize = baseFontSize * (systemInfo.windowWidth / 375);

        const monthsData = wx.getStorageSync('monthMarks') || [];
        monthsData.sort((a, b) => a.day - b.day)
        const [xdata, ydata] = monthsData.reduce(([xdata, ydata], item) => {
            if (item.weight) {
                xdata.push(util.formatDate(new Date(item.day)));
                ydata.push(parseFloat(item.weight));
            }
            return [xdata, ydata];
        }, [[], []]);

        return {
            //  tooltip 适配：文字大小随屏幕缩放
            tooltip: {
                trigger: 'axis',
                axisPointer: { type: 'shadow' },
                textStyle: { fontSize: fontSize - 1 },
                padding: 10,
                borderRadius: 4
            },

            // 网格适配：关键！预留边距，避免内容溢出
            grid: {
                left: '8%', // 左边留足空间（Y轴文字+名称）
                right: '5%', // 右边留白
                top: '10%', // 顶部（图例下方）留白
                bottom: '18%', // 底部（X轴文字）留白（竖屏X轴横向排列，需多留空间）
                containLabel: true // 强制包含坐标轴文字，避免被截断
            },

            // X轴适配：分类轴，避免文字重叠
            xAxis: {
                type: 'category',
                data: xdata,
                axisLabel: {
                    fontSize: fontSize,
                    rotate: 30, // 文字旋转（竖屏X轴分类多时常⽤）
                    margin: 10, // 文字与轴线间距
                    interval: 0 // 强制显示所有标签（避免自动隐藏）
                },
            },

            // Y轴适配：数据轴，动态范围
            yAxis: {
                type: 'value',
                name: '体重',
                nameTextStyle: { fontSize: fontSize, padding: [0, 0, 0, -5] }, // 名称左移，节省空间
                axisLabel: {
                    fontSize: fontSize,
                    formatter: '{value}' // 简化数值格式（大数据可加单位，如 {value}k）
                },
                min: (Math.min(...ydata) - 5) || 0
            },

            // 系列配置：线条和点大小适配
            series: [
                {
                    type: 'line',
                    data: ydata,
                    smooth: true,
                    lineStyle: { width: 2.5 },
                    itemStyle: { color: '#4299e1', radius: 3.5 }, // 点大小适配
                    areaStyle: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                            { offset: 0, color: 'rgba(66, 153, 225, 0.3)' },
                            { offset: 1, color: 'rgba(66, 153, 225, 0)' }
                        ])
                    }
                }
            ]
        };
    },

    // 图表尺寸重绘（适配屏幕变化）
    resizeChart() {
        const chart = this.chartInstance;
        if (chart) {
            chart.resize({
                width: this.data.chartWidth,
                height: this.data.chartHeight
            });
        }
    }
});