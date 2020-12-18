var $parentNode = window.parent.document;

function $childNode(name) {
    return window.frames[name]
}

// tooltips
$('.tooltip-demo').tooltip({
    selector: "[data-toggle=tooltip]",
    container: "body"
});

// 使用animation.css修改Bootstrap Modal
$('.modal').appendTo("body");

$("[data-toggle=popover]").popover();

//折叠ibox
$('.collapse-link').click(function () {
    var ibox = $(this).closest('div.ibox');
    var button = $(this).find('i');
    var content = ibox.find('div.ibox-content');
    content.slideToggle(200);
    button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
    ibox.toggleClass('').toggleClass('border-bottom');
    setTimeout(function () {
        ibox.resize();
        ibox.find('[id^=map-]').resize();
    }, 50);
});

//关闭ibox
$('.close-link').click(function () {
    var content = $(this).closest('div.ibox');
    content.remove();
});

//判断当前页面是否在iframe中
if (top == this) {
    var gohome = '<div class="gohome"><a class="animated bounceInUp" href="index.ftl?v=4.0" title="返回首页"><i class="fa fa-home"></i></a></div>';
    $('body').append(gohome);
}

//animation.css
function animationHover(element, animation) {
    element = $(element);
    element.hover(
        function () {
            element.addClass('animated ' + animation);
        },
        function () {
            //动画完成之前移除class
            window.setTimeout(function () {
                element.removeClass('animated ' + animation);
            }, 2000);
        });
}

//拖动面板
function WinMove() {
    var element = "[class*=col]";
    var handle = ".ibox-title";
    var connect = "[class*=col]";
    $(element).sortable({
        handle: handle,
        connectWith: connect,
        tolerance: 'pointer',
        forcePlaceholderSize: true,
        opacity: 0.8,
    })
        .disableSelection();
};

/*将数组笛卡尔积*/
var DescartesUtils = {
    /**
     *    * 如果传入的参数只有一个数组，求笛卡尔积结果    *
     *
     * @param arr1
     *            一维数组    *
     * @returns {Array}    
     */
    descartes1: function (arr1) {
        // 返回结果，是一个二维数组
        var result = [];
        var i = 0;
        for (i = 0; i < arr1.length; i++) {
            var item1 = arr1[i];
            result.push([item1]);
        }
        return result;
    },

    /**
     *    * 如果传入的参数只有两个数组，求笛卡尔积结果    *
     *
     * @param arr1
     *            一维数组    *
     * @param arr2
     *            一维数组    *
     * @returns {Array}    
     */
    descartes2: function (arr1, arr2) {
        // 返回结果，是一个二维数组
        var result = [];
        var i = 0, j = 0;
        for (i = 0; i < arr1.length; i++) {
            var item1 = arr1[i];
            for (j = 0; j < arr2.length; j++) {
                var item2 = arr2[j];
                result.push([item1, item2]);
            }
        }
        return result;
    },

    /**
     * @param arr2D
     *            二维数组    *
     * @param arr1D
     *            一维数组    *
     * @returns {Array}    
     */
    descartes2DAnd1D: function (arr2D, arr1D) {
        var i = 0, j = 0;
        // 返回结果，是一个二维数组
        var result = [];

        for (i = 0; i < arr2D.length; i++) {
            var arrOf2D = arr2D[i];
            for (j = 0; j < arr1D.length; j++) {
                var item1D = arr1D[j];
                result.push(arrOf2D.concat(item1D));
            }
        }

        return result;
    },

    descartes3: function (list) {
        var listLength = list.length;
        var i = 0, j = 0;
        // 返回结果，是一个二维数组
        var result = [];
        // 为了便于观察，采用这种顺序
        var arr2D = DescartesUtils.descartes2(list[0], list[1]);
        for (i = 2; i < listLength; i++) {
            var arrOfList = list[i];
            arr2D = DescartesUtils.descartes2DAnd1D(arr2D, arrOfList);
        }
        return arr2D;
    },

    // 笛卡儿积组合
    descartes: function (list) {
        if (!list) {
            return [];
        }
        if (list.length <= 0) {
            return [];
        }
        if (list.length == 1) {
            return DescartesUtils.descartes1(list[0]);
        }
        if (list.length == 2) {
            return DescartesUtils.descartes2(list[0], list[1]);
        }
        if (list.length >= 3) {
            return DescartesUtils.descartes3(list);
        }
    }

};

/*sleep方法*/
function sleep(numberMillis) {
    var now = new Date();
    var exitTime = now.getTime() + numberMillis;
    while (true) {
        now = new Date();
        if (now.getTime() > exitTime)
            return;
    }
}

function print(obj) {
    console.log(JSON.stringify(obj))
}

function dataIsNull(result) {
    if (result.data.length === 0 || result.data === null) {
        layer.msg("没有请求到数据");
        return;
    }
}

function getInterval(isWeek, isMonth, isQuarter) {
    var now = new Date(); //当前日期
    var nowDayOfWeek = now.getDay(); //今天本周的第几天
    var nowDay = now.getDate(); //当前日
    var nowMonth = now.getMonth(); //当前月
    var nowYear = now.getFullYear(); //当前年

    //获得某月的天数
    function getMonthDays(myMonth) {
        var monthStartDate = new Date(nowYear, myMonth, 1);
        var monthEndDate = new Date(nowYear, myMonth + 1, 1);
        return (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
    }

    //获得本季度的开始月份
    function getQuarterStartMonth() {
        var quarterStartMonth = 0;
        if (nowMonth < 3) {
            quarterStartMonth = 0;
        }
        if (2 < nowMonth && nowMonth < 6) {
            quarterStartMonth = 3;
        }
        if (5 < nowMonth && nowMonth < 9) {
            quarterStartMonth = 6;
        }
        if (nowMonth > 8) {
            quarterStartMonth = 9;
        }
        return quarterStartMonth;
    }

    if (isWeek) {
        //获得本周的开始日期
        var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
        //获得本周的结束日期
        var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));
        return formatDate(weekStartDate) + "-" + formatDate(weekEndDate);
    } else if (isMonth) {
        //获得本月的开始日期
        var monthStartDate = new Date(nowYear, nowMonth, 1);
        //获得本月的结束日期
        var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
        return formatDate(monthStartDate) + "-" + formatDate(monthEndDate);
    } else if (isQuarter) {
        //获得本季度的开始日期
        var quarterStartDate = new Date(nowYear, getQuarterStartMonth(), 1);
        formatDate(quarterStartDate);
        //或的本季度的结束日期
        var quarterEndMonth = getQuarterStartMonth() + 2;
        var quarterEndDate = new Date(nowYear, quarterEndMonth, getMonthDays(quarterEndMonth));
        return formatDate(quarterStartDate) + "-" + formatDate(quarterEndDate);
    } else {
        return nowYear + "-01-01-" + nowYear + "-12-31";
    }
}

function preDate(numDate) {
    var now = new Date(); //当前日期
    var pre = new Date();
    pre.setDate(pre.getDate() + numDate);
    return formatDate(pre) + " ~ " + formatDate(now);
}

//格式化日期：yyyy-MM-dd
function formatDate(date) {
    var myyear = date.getFullYear();
    var mymonth = date.getMonth() + 1;
    var myweekday = date.getDate();
    if (mymonth < 10) {
        mymonth = "0" + mymonth;
    }
    if (myweekday < 10) {
        myweekday = "0" + myweekday;
    }
    return (myyear + "-" + mymonth + "-" + myweekday);
}

