/**
 * 录音记录前缀ip
 */
var audio_src_prefix = 'http://audio.meiwo.cn:8990/';

var CommonUtils = {

    /**
     * 字符串类型日期转Date类型
     * @param fDate ： 2017-12-27
     * @returns {Date}
     */
    stringToDate: function(fDate){
        var fullDate = fDate.split("-");
        return new Date(fullDate[0], fullDate[1]-1, fullDate[2], 0, 0, 0);
    },
    /**
     * 日期转字符串
     * @param date
     * @returns {string}
     */
    dateToString: function (date, fmt) {
        return date.Format(fmt);
    },
    /**
     * 计算两个日期直接的天数
     * @param sDate1   2018-01-01
     * @param sDate2
     * @returns {number | *}
     * @constructor
     */
    DateDiff: function(sDate1,  sDate2){
        var  aDate,  oDate1,  oDate2,  iDays
        aDate  =  sDate1.split("-")
        oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2006格式
        aDate  =  sDate2.split("-")
        oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])
        iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数
        return  iDays
    },
    /**
     * 强制保留2位小数，如：2，会在2后面补上00.即2.00
     * @param x
     * @returns {*}
     */
    toDecimal2: function(x) {
        if (isNaN(parseFloat(x))) {
            return false;
        }
        var f = Math.round(x*100)/100;
        var s = f.toString();
        var rs = s.indexOf('.');
        if (rs < 0) {
            rs = s.length;
            s += '.';
        }
        while (s.length <= rs + 2) {
            s += '0';
        }
        return s;
    },
    /**
     * 数字金额大写转换(可以处理整数,小数,负数)
     * @param n
     * @returns {string}
     */
    smalltoBIG: function(n) {
        var fraction = ['角', '分'];
        var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
        var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟']  ];
        var head = n < 0? '欠': '';
        n = Math.abs(n);
        var s = '';

        for (var i = 0; i < fraction.length; i++) {
            s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
        }
        s = s || '整';
        n = Math.floor(n);

        for (var i = 0; i < unit[0].length && n > 0; i++) {
            var p = '';
            for (var j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[n % 10] + unit[1][j] + p;
                n = Math.floor(n / 10);
            }
            s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')  + unit[0][i] + s;
        }
        return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
    },
    /**
     * 浏览器全屏展示
     */
    browserFullScreen: function (title) {
        var el = document.documentElement;
        var rfs = el.requestFullScreen || el.webkitRequestFullScreen ||
            el.mozRequestFullScreen || el.msRequestFullScreen;
        if(typeof rfs != "undefined" && rfs) {
            rfs.call(el);
        } else if(typeof window.ActiveXObject != "undefined") {
            //for IE，这里其实就是模拟了按下键盘的F11，使浏览器全屏
            var wscript = new ActiveXObject("WScript.Shell");
            if(wscript != null) {
                wscript.SendKeys("{F11}");
            }
        }
        setTimeout(function () {
            CommonUtils.fullScreen(title)
        }, 300);
    },
    /**
     * 浏览器退出全屏
     */
    browserExitFullScreen: function () {
        var el = document;
        var cfs = el.cancelFullScreen || el.webkitCancelFullScreen ||
            el.mozCancelFullScreen || el.exitFullScreen;
        if(typeof cfs != "undefined" && cfs) {
            cfs.call(el);
        } else if(typeof window.ActiveXObject != "undefined") {
            //for IE，这里和fullScreen相同，模拟按下F11键退出全屏
            var wscript = new ActiveXObject("WScript.Shell");
            if(wscript != null) {
                wscript.SendKeys("{F11}");
            }
        }
    },
    /**
     * 列表全屏展示
     * @param title
     */
    fullScreen: function(title) {
        $(this).dialog({
            id: 'full_screen',
            url: 'fullScreenTable.html',
            title: title,
            width: 950,
            height: 480,
            mask: true,
            minable:false,
            resizable:false,
            drawable:false,
            max: true,
            onClose: function () {
                CommonUtils.browserExitFullScreen();
                return true;
            }
        });
    },
    dataSourceFullScreen : {

    }
};
/**
 * 日期格式化
 * @param fmt
 * @returns {*}
 * @constructor
 */
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};