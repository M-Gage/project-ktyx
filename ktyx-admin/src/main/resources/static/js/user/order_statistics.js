var goods = window.location.href
    , point = goods.split("point=")[1]
    , DI = getInterval(true, false, false)
    , staffId
    ,
    noDataStr = "<div style='width: " + ($(window).width() - 200) + "px;height: " + ($(window).height() - 200) + "px;margin:0 auto;padding-top:80px;'>" +
        "<img  style='width: " + ($(window).width() - 200) + "px;height: " + ($(window).height() - 200) + "px;' src='../static/img/no-data.jpg'/><br/>" +
        "</div>";
if (point === undefined) {
    point = 0;
    staffId = goods.split("staff=")[1]
}
$(function () {
    require.config({
        paths: {
            echarts: '../static/js/plugins/echar2'
        }
    });
    $("#orderAmount").height($(window).height() - 100);
    $("#orderRank").height($(window).height() - 100);
    $("#orderGoods").height($(window).height() - 100);
    // $("#fp-nav").removeClass("right")
    var $mlNav = $('.ml-nav');
    $('#statistics').fullpage({
        verticalCentered: !1,
        navigation: !0,
        onLeave: function (index, nextIndex, direction) {
            if (index === 2 && direction === 'up') {
                if (!$("#0").attr("flag")) {
                    loadOrderAmount(DI, true);
                    $("#0").attr("flag", "true")
                }
                $mlNav.animate({
                    top: 80
                }, 680);
            } else if (index === 1 && direction === 'down') {
                if (!$("#1").attr("flag")) {
                    loadOrderRank(DI);
                    $("#1").attr("flag", "true")
                }
                $mlNav.animate({
                    top: 0
                }, 400);
            } else if (index === 3 && direction === 'up') {
                if (!$("#1").attr("flag")) {
                    loadOrderRank(DI);
                    $("#1").attr("flag", "true")
                }
                $mlNav.animate({
                    top: 0
                }, 400);
            } else {
                if (!$("#2").attr("flag")) {
                    loadOrderGoods(DI);
                    $("#2").attr("flag", "true")
                }
                $mlNav.animate({
                    top: -66
                }, 400);
            }

        }
    });
});

function loadFirstStatistics() {
    var a = $("a");
    a.eq((a.length - 3)).attr("id", "0").click(function () {
        if (!$(this).attr("flag")) {
            loadOrderAmount(DI, true);
            $(this).attr("flag", "true")
        }
    });
    a.eq((a.length - 2)).attr("id", "1").click(function () {
        if (!$(this).attr("flag")) {
            loadOrderRank(DI);
            $(this).attr("flag", "true")
        }
    });
    a.eq((a.length - 1)).attr("id", "2").click(function () {
        if (!$(this).attr("flag")) {
            loadOrderGoods(DI);
            $(this).attr("flag", "true")
        }
    });

    $("#" + point).click();
}



$("[name=week]").click(function () {
    var _this = $(this);
    if (_this.parent().parent().prev().html().substr(0, 2) === _this.html()) {
        return;
    }
    _this.parent().parent().prev().html(_this.html() + " <span class='caret'></span>");
    var week = getInterval(true, false, false), weekObjs = $("[name=week]");
    for (var i in weekObjs) {
        if (weekObjs[i] === this) {
            if (i === "0") {
                loadOrderAmount(week, true);
            }
            if (i === "1") {
                loadOrderRank(week);
            }
            if (i === "2") {
                loadOrderGoods(week);
            }
        }
    }
});
$("[name=month]").click(function () {
    var _this = $(this);
    if (_this.parent().parent().prev().html().substr(0, 2) === _this.html()) {
        return;
    }
    _this.parent().parent().prev().html(_this.html() + " <span class='caret'></span>");
    var month = getInterval(false, true, false), monthObjs = $("[name=month]");
    for (var i in monthObjs) {
        if (monthObjs[i] === this) {
            if (i === "0") {
                $("#orderAmount").html("");
                loadOrderAmount(month, true);
            }
            if (i === "1") {
                loadOrderRank(month);
            }
            if (i === "2") {
                loadOrderGoods(month);
            }
        }
    }
});
$("[name=quarter]").click(function () {
    var _this = $(this);
    if (_this.parent().parent().prev().html().substr(0, 2) === _this.html()) {
        return;
    }
    _this.parent().parent().prev().html(_this.html() + " <span class='caret'></span>");
    var quarter = getInterval(false, false, true), quarterObjs = $("[name=quarter]");
    for (var i in quarterObjs) {
        if (quarterObjs[i] === this) {
            if (i === "0") {
                $("#orderAmount").html("");
                loadOrderAmount(quarter, false);
            }
            if (i === "1") {
                loadOrderRank(quarter);
            }
            if (i === "2") {
                loadOrderGoods(quarter);
            }
        }
    }
});
$("[name=year]").click(function () {
    var _this = $(this);
    if (_this.parent().parent().prev().html().substr(0, 2) === _this.html()) {
        return;
    }
    _this.parent().parent().prev().html(_this.html() + " <span class='caret'></span>");
    var year = getInterval(false, false, false), yearObjs = $("[name=year]");
    for (var i in yearObjs) {
        if (yearObjs[i] === this) {
            if (i === "0") {
                $("#orderAmount").html("");
                loadOrderAmount(year, false);
            }
            if (i === "1") {
                loadOrderRank(year);
            }
            if (i === "2") {
                loadOrderGoods(year);
            }
        }
    }
});

function loadOrderAmount(dateInterval, isDay) {
    var amounts = [], existsDates = [], sums = [], timeTicket;
    /*加载日期样式*/
    layui.laydate.render({
        elem: document.getElementById('amountDate')
        , range: '~' //或 range: true 来自定义-分割字符
        , calendar: true
        , theme: '#E87C25'//主题颜色
        , done: function (value, date, enDate) {//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
            if (value !== '') {
                loadOrderAmount(value, !(enDate.year > date.year || enDate.month - date.month > 3))
            }
        }
    });
    $.ajax({
        url: "/order/orderInterval",
        data: JSON.stringify({
            isDay: isDay,
            dateInterval: dateInterval,
            staffId: staffId
        }),
        contentType: "application/json;charset=UTF-8",
        async: false,
        type: "post",
        success: function (result) {
            for (var i in result.data) {
                amounts.push(result.data[i].amount);
                existsDates.push(result.data[i].existsDate.replace("2018-", "").replace("-", "."));
                sums.push(result.data[i].sum);
            }
        }
    });
    var myChart;
    require(
        [
            'echarts',
            'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            myChart = ec.init(document.getElementById('orderAmount'));
            var option = {
                    title: {
                        text: '订单期限综合统计数据'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['金额', '数量']
                    },
                    toolbox: {
                        show: true,
                        orient: 'vertical',
                        y: 'center',
                        feature: {
                            mark: {show: true},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    calculable: true,
                    dataZoom: {
                        show: true,
                        realtime: true,
                        start: 0,
                        end: 100,
                        backgroundColor: 'rgba(0,0,0,0)',       // 背景颜色
                        dataBackgroundColor: '#ffbfa7',            // 数据背景颜色
                    },
                    xAxis: [{
                        type: 'category',
                        boundaryGap: false,
                        data: existsDates
                    }],
                    yAxis: [{
                        type: 'value'
                    }],
                    series: [
                        {
                            name: '金额',
                            type: 'line',
                            smooth: true,
                            itemStyle: {normal: {areaStyle: {type: 'default'}}},
                            data: amounts
                        }
                        ,
                        {
                            name: '数量',
                            type: 'line',
                            smooth: true,
                            itemStyle: {normal: {areaStyle: {type: 'default'}}},
                            data: sums
                        }
                    ]
                };

            myChart.setOption(option, true);

        }
    );

}

function loadOrderRank(dateInterval) {
    layui.laydate.render({
        elem: document.getElementById('rankDate')
        , range: '~' //或 range: true 来自定义-分割字符
        , calendar: true
        , theme: '#69FA65'//主题颜色
        /*,ready: function(){
            ins.hint('选择区间或双击某天后确认');
        }*/
        , done: function (value) {//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
            if (value !== '') {
                loadOrderRank(value)
            }
        }

    });
    var customerAmount = [], customerNames = [], staffAmount = [], staffNames = [], timeTicket;
    setData(dateInterval, true, false, customerAmount, customerNames);
    setData(dateInterval, false, true, staffAmount, staffNames);



    if (customerNames.length === 0 || customerAmount.length === 0) {
        $("#orderRank").html("");
        $("#orderRank").append(noDataStr)
    } else {
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('orderRank'));
                var option = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    toolbox: {
                        show: true,
                        orient: 'vertical',
                        y: 'center',
                        feature: {
                            mark: {show: true},
                            magicType: {show: true, type: ['line', 'bar']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    title: {
                        text: '订单期限统计排行数据',
                        subtext: ""
                    },
                    legend: {
                        data: ['客户订单总金额', '业务员销售金额'],
                        selected: {
                            '业务员销售金额': false
                        },
                        selectedMode: 'single'
                    },
                    calculable: true,
                    color: ['#fa3d6a', '#437afa'],
                    dataZoom: {
                        show: true,
                        realtime: true,
                        start: 0,
                        end: 100,
                        backgroundColor: 'rgba(0,0,0,0)',       // 背景颜色
                        dataBackgroundColor: '#fa3d6a',            // 数据背景颜色
                    },
                    xAxis: [
                        {
                            type: 'category',
                            splitLine: {
                                show: false
                            },
                            data: customerNames
                        }
                    ],
                    yAxis: [{
                        type: 'value',
                        axisLabel: {
                            show: true,
                            interval: 'auto',    // {number}
                            rotate: -15,
                            margin: 18,
                            formatter: '{value}￥',    // Template formatter!
                            textStyle: {
                                color: '#ff1e1a',
                                fontFamily: 'verdana',
                                fontSize: 10,
                                fontStyle: 'normal',
                                fontWeight: 'bold'
                            }
                        }
                    }],
                    series: [
                        {
                            name: '客户订单总金额',
                            type: 'bar',
                            // barWidth: 10,
                            stack: '总量',
                            itemStyle: {
                                normal: {
                                    label: {show: true}
                                }
                            },
                            data: (function () {
                                return customerAmount;
                            })()
                        }
                        , {
                            name: '业务员销售金额',
                            type: 'bar',
                            stack: '总量',
                            // barWidth: 10,
                            itemStyle: {
                                normal: {
                                    label: {show: true}
                                }
                            },
                            data: (function () {
                                return staffAmount;
                            })()
                        }
                    ]
                };

                //legend单击事件
                var ecConfig = require('echarts/config');
                //是否 客户订单总金额 series
                var flag = true;



                    //切换series
                    myChart.on(ecConfig.EVENT.LEGEND_SELECTED, function (param) {
                        var selected = param.selected;
                        if (selected['客户订单总金额']) {
                            flag = true;
                            var opt = myChart.getOption();
                            opt.xAxis[0]["data"] = customerNames;
                            myChart.setOption(opt, true);
                        }
                        if (selected['业务员销售金额']) {
                            flag = false;
                            var opt = myChart.getOption();
                            opt.xAxis[0]["data"] = staffNames;
                            myChart.setOption(opt, true);

                        }
                    });


                myChart.setOption(option);
            });
    }
}

function loadOrderGoods(dateInterval) {
    layui.laydate.render({
        elem: document.getElementById('goodsDate')
        , range: '~' //或 range: true 来自定义-分割字符
        , calendar: true
        , theme: '#e8485c'//主题颜色
        /*,ready: function(){
            ins.hint('选择区间或双击某天后确认');
        }*/
        , done: function (value) {//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
            if (value !== '') {
                loadOrderGoods(value)
            }
        }
    });

    var orderGoods = [], goodsSum = [], timeTicket;
    $.ajax({
        url: "/order/goodsTypeInterval",
        type: "post",
        async: false,
        data: JSON.stringify({
            dateInterval: dateInterval,
            staffId: staffId
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var goods = result.data;
            for (var i in goods) {
                orderGoods.push(goods[i].typeName);
                goodsSum.push(goods[i].sum);
            }
            if (orderGoods.length === 0 || goodsSum.length === 0) {
                $("#orderGoods").html("");
                $("#orderGoods").append(noDataStr)
            }
        }
    });
    require(
        [
            'echarts',
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('orderGoods'));
            var option = {

                title: {
                    x: 'center',
                    text: '订单期限货物销售量排行数据'
                    // , subtext: 'Rainbow bar example'
                },
                tooltip: {
                    trigger: 'item'
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    y: 'center',
                    feature: {
                        restore: {show: true},
                        magicType: {show: true, type: ['line', 'bar']},
                        saveAsImage: {show: true}
                    }
                },
                calculable: true,
                grid: {
                    borderWidth: 0,
                    y: 80,
                    y2: 60
                },
                xAxis: [
                    {
                        type: 'category',
                        show: false,
                        data: orderGoods
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        show: false
                    }
                ],
                dataZoom: {
                    show: true,
                    realtime: true,
                    start: 0,
                    end: 100,
                    backgroundColor: 'rgba(0,0,0,0)',       // 背景颜色
                    dataBackgroundColor: '#c1232b',            // 数据背景颜色
                },
                series: [
                    {
                        name: '该货物销售量',
                        type: 'bar',
                        itemStyle: {
                            normal: {
                                color: function (params) {
                                    // build a color map as your need.
                                    var colorList = [
                                        '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B',
                                        '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
                                        '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
                                    ];
                                    return colorList[params.dataIndex]
                                },
                                label: {
                                    show: true,
                                    position: 'top',
                                    formatter: '{b}\n{c}'
                                }
                            }
                        },
                        data: goodsSum,
                    }
                ]
            };
            myChart.setOption(option);
        });
}

function setData(dateInterval, isCustomerContribute, isTeamRank, amounts, names) {
    $.ajax({
        url: "/order/teamInterval",
        async: false,
        data: JSON.stringify({
            isTeamContribute: null,
            isCustomerContribute: isCustomerContribute,
            isTeamRank: isTeamRank,
            dateInterval: dateInterval,
            staffId: staffId
        }),
        contentType: "application/json;charset=UTF-8",
        type: "post",
        success: function (result) {
            for (var i in result.data) {
                amounts.push(result.data[i].amount);
                names.push(isTeamRank ? result.data[i].staffName : result.data[i].customerName);
            }
        }
    });
}


