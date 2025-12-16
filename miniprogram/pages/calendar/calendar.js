// pages/calendar.js
const util = require('../../utils/util.js')
const { request } = require('../../utils/request')
import Message from 'tdesign-miniprogram/message/index';
Page({

    /**
     * 页面的初始数据
     */
    data: {
        scrolling: false,
        timer: null,
        monthMarks: [],
        currentMark: '',
        currentWeight: undefined,
        selectedDates: [],
        formatCalendar: null,
        showMarkDialog: false,
        showWeightDialog: false,
        minDate: util.getMinDate().getTime(),
        maxDate: util.getMaxDate().getTime(),
        popupVisible: true,
        today: {},
        changeType: 'same' // 初始为不变状态
    },
    formatCalendar(day) {
        const { date } = day;
        // 获取前一天的日期
        const prevDate = new Date(date);
        prevDate.setDate(prevDate.getDate() - 1);

        const currentDay = this.data.monthMarks.find(item => item.day === date.getTime());
        const preDay = this.data.monthMarks.find(item => item.day === prevDate.getTime());
        if (!currentDay) {
            return day;
        }
        let className = "";
        if (currentDay.markRemark) {
            day.prefix = currentDay.markRemark;
            className = 'is-love ';
        }
        let preWeight = preDay?.weight;
        let weight = currentDay.weight;
        if (undefined !== weight && null !== weight) {
            let suffix = weight;
            if (undefined !== preWeight && null !== preWeight) {
                let diff = weight - preWeight;
                if (diff > 0) {
                    suffix = suffix + '↑';
                    className = className + 'is-weight ';
                } else if (diff < 0) {
                    suffix = suffix + '↓';
                    className = className + 'is-light ';
                }
            }
            day.suffix = suffix;
        }
        day.className = className;
        return day;
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        // 使用 async/await 或 then 确保执行顺序
        this.initMonthMarks()
        this.setData({
            formatCalendar: this.formatCalendar.bind(this),
            selectedDates: [today.getTime()]
        })
        this.data.selectedDates.push(today.getTime());
        this.calcWeightChange(today);
    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh() {
        this.initMonthMarks();
    },
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage() {

    },
    /**
     * 初始化月份数据的方法
     */
    initMonthMarks() {
        console.log('init month marks')
        const currentDate = new Date();
        const year = currentDate.getFullYear();
        const month = currentDate.getMonth();
        request({
            url: '/api/calendar/getDailyInfoList',
            method: 'POST',
            data: { targetYear: String(year), targetMonth: String(month + 1) }
        }).then(resData => {
            if (resData.message === 'success') {
                // 更新数据
                this.setData({
                    monthMarks: resData.data
                });
            }
        }).catch(err => {
            console.error('登录失败', err);
        });
    },
    calcWeightChange(day) {
        const currentDate = new Date(day);
        const year = currentDate.getFullYear();
        const month = currentDate.getMonth();
        currentDate.setHours(0, 0, 0, 0);
        let yesterday = new Date(currentDate);
        yesterday.setDate(currentDate.getDate() - 1);
        let changeType = 'same';
        const todayWeight = { ...this.data.monthMarks.find(item => item.day === currentDate.getTime()), weightChange: '-' };
        const yesterdayWeight = { ...this.data.monthMarks.find(item => item.day === yesterday.getTime()) };
        if (!todayWeight.weight) {
            todayWeight.weight = '-';
            todayWeight.weightChange = '-';
            this.setData({
                today: todayWeight,
                changeType: changeType
            })
            return;
        }
        const diff = todayWeight.weight - yesterdayWeight.weight;
        if (diff > 0) {
            todayWeight.weightChange = '上升'
            changeType = 'up';
        } else if (diff < 0) {
            todayWeight.weightChange = '下降'
            changeType = 'down';
        }
        this.setData({
            today: todayWeight,
            changeType: changeType
        })
    },
    handleSelect(e) {
        const { value } = e.detail;
        // this.data.selectedDates = value;
        this.setData({
            selectedDates: value
        })
        console.log(value);
    },
    addTodoList() {
        Message.info({
            context: this,
            offset: [90, 32],
            duration: 5000,
            icon: false,
            // single: false, // 打开注释体验多个消息叠加效果
            content: '待办还处于待办状态',
        });
    },
    markDate() {
        console.log('mark date');
        this.setData({
            showMarkDialog: true,
            currentMark: ''
        });
        console.log('mark date end')
    },
    recordWeight() {
        console.log('record weight');
        this.setData({
            showWeightDialog: true,
            currentWeight: null
        });
    },
    onScroll() {
        clearTimeout(this.timer);
        this.setData({
            scrolling: true,
        });
        this.timer = setTimeout(() => {
            this.setData({
                scrolling: false,
            });
        }, 100);
    },
    onInputChange(e) {
        this.setData({
            currentMark: e.detail.value
        })
        console.log('输入内容：', e.detail.value);
    },
    onInputWeightChange(e) {
        this.setData({
            currentWeight: e.detail.value
        })
        console.log('输入内容：', e.detail.value);
    },
    onConfirmInput() {
        if (this.data.selectedDates.length === 0) {
            this.setData({
                showMarkDialog: false
            });
            return;
        }
        const values = new Set(this.data.selectedDates);
        const existingDays = new Set(this.data.monthMarks.map(marks => { return marks.day }));
        const newMarks = Array.from(values)
            .filter(day => !existingDays.has(day))
            .map(day => ({
                day: day,
                markRemark: this.data.currentMark,
                weight: undefined
            }));
        this.setData({
            monthMarks: [...this.data.monthMarks.map(marks =>
                values.has(marks.day) ? { ...marks, markRemark: this.data.currentMark } : marks
            ), ...newMarks],
            showMarkDialog: false,
            selectedDates: []
        });
        wx.setStorageSync('monthMarks', this.data.monthMarks);

    },
    onCancelInput() {
        this.setData({
            showMarkDialog: false,
            currentMark: ''
        });
    },
    onConfirmWeightInput() {
        if (this.data.selectedDates.length === 0) {
            this.setData({
                showWeightDialog: false
            });
            return;
        }
        const values = new Set(this.data.selectedDates);
        const existingDays = new Set(this.data.monthMarks.map(marks => { return marks.day }));
        const newMarks = Array.from(values)
            .filter(day => !existingDays.has(day))
            .map(day => ({
                day: day,
                markRemark: undefined,
                weight: this.data.currentWeight
            }));
        this.setData({
            monthMarks: [...this.data.monthMarks.map(marks =>
                values.has(marks.day) ? { ...marks, weight: this.data.currentWeight } : marks
            ), ...newMarks],
            showWeightDialog: false,
            selectedDates: []
        });
        wx.setStorageSync('monthMarks', this.data.monthMarks);
        this.calcWeightChange(new Date());
    },
    onCancelWeightInput() {
        this.setData({
            showWeightDialog: false,
            currentWeight: undefined
        })
    },
    handlePanelChange(e) {
        const { year, month } = e.detail;
        this.setData({
            minDate: new Date(year, month - 2, 1).getTime(),
            maxDate: new Date(year, month, 0).getTime(),
            selectedDates: []
        })
        console.log('year: ', year, 'month: ', month);
    },
    onVisibleChange(e) {
        this.setData({
            popupVisible: e.detail.visible,
        });
    }
})