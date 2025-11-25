const formatTime = date => {
    const year = date.getFullYear()
    const month = date.getMonth() + 1
    const day = date.getDate()
    const hour = date.getHours()
    const minute = date.getMinutes()
    const second = date.getSeconds()

    return `${[year, month, day].map(formatNumber).join('/')} ${[hour, minute, second].map(formatNumber).join(':')}`
}
const formatDate = date => {
    const year = date.getFullYear()
    const month = date.getMonth() + 1
    const day = date.getDate()

    return `${[year, month, day].map(formatNumber).join('-')}`
}
const formatMonth = date => {
    const year = date.getFullYear()
    const month = date.getMonth() + 1

    return `${[year, month].map(formatNumber).join('-')}`
}

const formatNumber = n => {
    n = n.toString()
    return n[1] ? n : `0${n}`
}

const getMinDate = (date=new Date()) =>{
    const  month  = date.getMonth();
    date.setDate(1);
    date.setMonth(month - 1);
    date.setHours(0, 0, 0, 0);
    return date;
}

const getMaxDate = (date=new Date()) => {
    const  month  = date.getMonth();
    date.setDate(0);
    date.setMonth(month + 1);
    date.setHours(0, 0, 0, 0);
    return date;
}

module.exports = {
    formatTime,
    formatDate,
    formatMonth,
    getMinDate,
    getMaxDate
}
