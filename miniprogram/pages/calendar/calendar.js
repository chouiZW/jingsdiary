// pages/calendar.js
const util = require("../../utils/util.js");
const {
  request
} = require("../../utils/request");
import Message from "tdesign-miniprogram/message/index";
Page({
  /**
   * 页面的初始数据
   */
  data: {
    scrolling: false,
    timer: null,
    monthMarks: [],
    currentMark: "",
    currentWeight: undefined,
    selectedDates: [],
    formatCalendar: null,
    showMarkDialog: false,
    showWeightDialog: false,
    minDate: util.getMinDate().getTime(),
    maxDate: util.getMaxDate().getTime(),
    popupVisible: true,
    today: {},
    changeType: "same", // 初始为不变状态
  },
  formatCalendar(day) {
    const {
      date
    } = day;
    const currentDay = this.data.monthMarks.find((item) => util.isSameDay(new Date(item.targetDate), date));
    if (!currentDay) {
      return day;
    }
    let className = "";
    if (currentDay.mark) {
      day.prefix = currentDay.mark;
      className = "is-love ";
    }
    let weight = currentDay.weight;
    if (undefined !== weight && null !== weight) {
      let suffix = weight;
      let diff = currentDay.weightDiff;
      if (diff > 0) {
        suffix = suffix + "↑";
        className = className + "is-weight ";
      } else if (diff < 0) {
        suffix = suffix + "↓";
        className = className + "is-light ";
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
    // 先加载月份数据，确保渲染时已有标记数据
    (async () => {
      try {
        await this.initMonthMarks();
      } catch (err) {
        console.error("initMonthMarks failed:", err);
      }
      this.setData({
        formatCalendar: this.formatCalendar.bind(this),
        selectedDates: [today.getTime()],
      });
    })();
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
  onShareAppMessage() {},
  /**
   * 初始化月份数据的方法
   */
  initMonthMarks() {
    console.log("init month marks");
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();
    // 返回 Promise 以便外部 await
    return request({
        url: "/api/calendar/getDailyInfoList",
        method: "POST",
        data: {
          targetYear: String(year),
          targetMonth: String(month + 1),
        },
      })
      .then((resData) => {
        if (resData && resData.msg === "success") {
          console.log('查询成功：' + resData)
          // 确保 monthMarks 为数组
          this.setData({
            monthMarks: Array.isArray(resData.data) ? resData.data : [],
          });
          console.log(this.data.monthMarks)
        }
        return resData;
      })
      .catch((err) => {
        console.error("初始化失败", err);
        throw err;
      });
  },
  handleSelect(e) {
    const {
      value
    } = e.detail;
    let today = {}
    if(value && value.length === 1){
      today = this.data.monthMarks.find(item => util.isSameDay(new Date(item.targetDate), new Date(value[0])))
      if (today && today.weight){
        this.data.today = { weight: today.weight, weightDiff: today.weightDiff}
      }
    }
    this.setData({
      selectedDates: value,
      today: today
    });
    console.log(value);
  },
  addTodoList() {
    Message.info({
      context: this,
      offset: [90, 32],
      duration: 5000,
      icon: false,
      // single: false, // 打开注释体验多个消息叠加效果
      content: "待办还处于待办状态",
    });
  },
  markDate() {
    console.log("mark date");
    this.setData({
      showMarkDialog: true,
      currentMark: "",
    });
    console.log("mark date end");
  },
  recordWeight() {
    console.log("record weight");
    this.setData({
      showWeightDialog: true,
      currentWeight: null,
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
      currentMark: e.detail.value,
    });
    console.log("输入内容：", e.detail.value);
  },
  onInputWeightChange(e) {
    this.setData({
      currentWeight: e.detail.value,
    });
    console.log("输入内容：", e.detail.value);
  },
  onConfirmInput() {
    if (this.data.selectedDates.length === 0) {
      this.setData({
        showMarkDialog: false,
      });
      return;
    }
    request({
        url: "/api/calendar/updateMarks",
        method: "POST",
        data: {
          targetDays: this.data.selectedDates,
          mark: this.data.currentMark,
        },
      })
      .then((res) => {
        if (res.data) {
          this.setData({
            showMarkDialog: false,
            currentMark: "",
            selectedDates: [],
          });
        }
      })
      .catch((err) => {
        console.log("更新失败：");
      });
  },
  onCancelInput() {
    this.setData({
      showMarkDialog: false,
      currentMark: "",
    });
  },
  onConfirmWeightInput() {
    if (this.data.selectedDates.length === 0) {
      this.setData({
        showWeightDialog: false,
      });
      return;
    }

    const dates = this.data.selectedDates[0];

    request({
        url: "/api/healthRecord/addOrUpdateHealthRecord",
        method: "POST",
        data: {
          targetDate: dates,
          weight: this.data.currentWeight,
        },
      })
      .then((res) => {
        if (res.data) {
          this.setData({
            showWeightDialog: false,
            selectedDates: [],
          });
        }
      })
      .catch((err) => {
        console.log("更新失败：");
      });
  },
  onCancelWeightInput() {
    this.setData({
      showWeightDialog: false,
      currentWeight: undefined,
    });
  },
  handlePanelChange(e) {
    const {
      year,
      month
    } = e.detail;
    this.setData({
      minDate: new Date(year, month - 2, 1).getTime(),
      maxDate: new Date(year, month, 0).getTime(),
      selectedDates: [],
    });
    console.log("year: ", year, "month: ", month);
  },
  onVisibleChange(e) {
    this.setData({
      popupVisible: e.detail.visible,
    });
  },
});