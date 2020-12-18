var $mlNav = $('.ml-nav'), staffId, flag = true, flag2 = true;
$(function () {

    staffId = window.location.search.substr(7,32);
    $("#orderGoods").css("height", $(window).height() - 100);
    $("#staffAmount").css("height", $(window).height() / 3-25);
    $("#newCustomer").css("height", $(window).height() / 3-50);
    $("#newFollow").css("height", $(window).height() / 3-50);
    $("#contrastDiv").css("height", $(window).height() / 2 - 100);
    $("#tables").css("height", $(window).height() / 2 + 50);

    $('#staffInfo').fullpage({
        verticalCentered: !1,
        navigation: !0,
        onLeave: function (index, nextIndex, direction) {
            if (index === 2 && direction === 'up') {
                $mlNav.animate({
                    top: 80
                }, 680);
            } else if (index === 1 && direction === 'down') {
                if (flag) {
                    loadOrderGoods(staffId);
                    flag = false;
                }
                $mlNav.animate({
                    top: 0
                }, 400);
            } else if (index === 3 && direction === 'up') {
                $mlNav.animate({
                    top: 0
                }, 400);
            } else {
                if (flag2) {
                    loadStaffAmount(staffId, preDate(-6));
                    loadStaffNewCustomer(staffId, preDate(-6));
                    loadStaffNewFollow(staffId, preDate(-6));
                    flag2 = false;
                }
                $mlNav.animate({
                    top: -66
                }, 400);
            }
        }
    });

    require.config({
        paths: {
            echarts: '../static/js/plugins/echar2'
        }
    });

    //总金额
    layui.laydate.render({
        elem: document.getElementById('amountDate')
        , range: '~' //或 range: true 来自定义-分割字符
        , type: 'date'
        // , trigger: 'mouseover'//定义鼠标悬停时弹出控件
        , theme: '#24c6c8'//主题颜色
        , done: function (value, date, enDate) {//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象

            if (value !== '') {
                loadStaffAmount(staffId, value);
                loadStaffNewCustomer(staffId, value);
                loadStaffNewFollow(staffId, value);
            }
        }
    });

    var imgArr = [
        ['../static/img/contrast-new-up.png', '../static/img/contrast-new-down.png'],
        ['../static/img/contrast-deal-up.png', '../static/img/contrast-deal-down.png'],
        ['../static/img/contrast-achievement-up.png', '../static/img/contrast-achievement-down.png'],
        ['../static/img/contrast-followup-up.png', '../static/img/contrast-followup-down.png']
    ], titleArr = ['新增客户数量', '成交客户数量', '同比业绩', '客户跟进统计'];
    $.ajax({
        url: "/staff/statistics/" + staffId,
        type: "get",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {

            if (result.code === 200) {
                var fullWidth = $(window).width(), contrast = result.data, str = "";
                for (var i = 0; i < 4; i++) {
                    var bm = contrast[i].beforeLastMonthCount, lm = contrast[i].lastMonthCount;

                    str += "<div class=\"col-xs-3\">\n" +
                        "      <div class=\"col-md-1\"></div>\n" +
                        "      <div class=\"col-md-10 contrast\">\n" +
                        "              <div style=\"width:" + (fullWidth / 5) + "px; height:" + (fullWidth / 5) / 2 + "px; background:url(" + (bm < lm ? imgArr[i][0] : imgArr[i][1]) + ");background-size:100% 100%;padding: 11% 0 0 15%;float: right\">\n" +
                        "                  <h3>" + titleArr[i] + "</h3><br>\n" +
                        "                  <h4><span class=\"beforeLastMonth\"> </span>: <span\n" +
                        "                          id=\"beforeLastNewCustomerNum\">" + bm + "</span></h4>\n" +
                        "                  <h4><span class=\"lastMonth\"> </span>: <span\n" +
                        "                          id=\"lastNewCustomerNum\">" + lm + "</span>\n" +
                        "                  </h4>\n" +
                        "              </div>\n" +
                        "      </div>\n" +
                        "      <div class=\"col-md-1\"></div>\n" +
                        "  </div>"
                }
                $("#contrastBox").html(str);
                $(".beforeLastMonth").html(getPreMonth(2));
                $(".lastMonth").html(getPreMonth(1));
            } else {
                layer.msg("未知错误，请与程序员联系", {icon: 2})
            }
        }
    });
    loadSchedule1(staffId);
    loadSchedule2(staffId);



});

function getPreMonth(int) {
    var date = new Date();
    var nowMonth = date.getMonth() + 1; //获取当前日期的月份
    var month = nowMonth - int;
    if (month === 0) {//如果是1月份，则取上一年的12月份
        return "十二月份"
    }
    if (month < 0) {//如果是1月份，则取上一年的12月份
        return "十一月份"
    }
    var cn = ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一"];
    return cn[month - 1] + "月份";
}

function loadOrderGoods(staffId) {
    var goodsNames = [], goodsSums = [];
    $.ajax({
        url: "/staff/goods/" + staffId,
        type: "get",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                var goods = result.data, len = goods.length;
                for (var i = 0; i < len; i++) {
                    goodsNames.push(goods[i].goodsName);
                    goodsSums.push(goods[i].quantity);
                }
            } else {
                layer.msg("未知错误，请与程序员联系", {icon: 2})
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
                    text: getPreMonth(1) + '货物销售量排行数据'
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
                        data: goodsNames
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
                    end: 100
                },
                series: [
                    {
                        name: '上月该商品销售量',
                        type: 'bar',
                        itemStyle: {
                            normal: {
                                color: function (params) {
                                    // build a color map as your need.
                                    var colorList = [
                                        '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B',
                                        '#AE5D49', '#83af5b', '#ae9644', '#9c7120', '#42889c',
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
                        data: goodsSums

                    }
                ]
            };

            myChart.setOption(option);
        });
    // }
}

function loadSchedule1(staffId) {

    $.ajax({
        url: "/staff/schedule1/" + staffId,
        type: "get",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                var s = result.data, len = s.length;
                for (var i = 0; i < len; i++) {
                    $("#month").append("<td>" + s[i].monthData + "</td>");
                    $("#quarter").append("<td>" + s[i].quarterData + "</td>");
                    $("#year").append("<td>" + s[i].yearData + "</td>");
                }
            } else {
                layer.msg("未知错误，请与程序员联系", {icon: 2})
            }
        }
    })
}

function loadSchedule2(staffId) {
    $.ajax({
        url: "/staff/schedule2/" + staffId,
        type: "get",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                var s = result.data;
                $("#moneySchedule").html("<td>金额</td><td>" + ((s === undefined) ? 0 : s.monthData) + "元</td>" +
                    "<td>" + ((s === undefined) ? 0 : s.quarterData) + "元</td>" +
                    "<td>" + ((s === undefined) ? 0 : s.yearData) + "元</td>" +
                    "<td>" + ((s === undefined) ? 0 : s.historyData) + "元</td>"
                );
            } else {
                layer.msg("未知错误，请与程序员联系", {icon: 2})
            }
        }
    })
}

function loadStaffAmount(staffId, dateInterval) {
    $.ajax({
        url: "/staff/amount",
        type: "post",
        data: JSON.stringify({staffId: staffId, dateInterval: dateInterval}),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                var staff = result.data, amount = [], existsDate = [];
                for (var i in staff) {
                    amount.push(staff[i].amount);
                    existsDate.push(staff[i].existsDate);
                }

                require([
                        'echarts',
                        'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                        'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                    ],
                    function (ec) {
                        // 基于准备好的dom，初始化echarts图表
                        var myChart = ec.init(document.getElementById('staffAmount'));
                        var option = {
                            title: {
                                text: '员工销售趋势图',
                                x:30
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    magicType: {show: true, type: ['line', 'bar']},
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            calculable: true,
                            dataZoom: {
                                show: true,
                                realtime: true,
                                start: 0,
                                end: 100
                            },
                            xAxis: [
                                {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: existsDate
                                }
                            ],
                            yAxis: [
                                {
                                    type: 'value',
                                    axisLabel: {
                                        formatter: '{value}￥'
                                    }
                                }
                            ],
                            series: [
                                {
                                    name: '销售金额',
                                    type: 'line',
                                    data: amount,
                                    markPoint: {
                                        data: [
                                            {type: 'max', name: '最大值'},
                                            {type: 'min', name: '最小值'}
                                        ]
                                    },
                                    markLine: {
                                        data: [
                                            {type: 'average', name: '平均值'}
                                        ]
                                    }
                                }
                            ]
                        };


                        myChart.setOption(option);
                    });
                require([
                        'echarts',
                        'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                        'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                    ],
                    function (ec) {
                        // 基于准备好的dom，初始化echarts图表
                        var myChart = ec.init(document.getElementById('newCustomer'));
                        var option = {
                            title: {
                                text: '员工新增客户趋势图',
                                x:30
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    magicType: {show: true, type: ['line', 'bar']},
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            calculable: true,
                            dataZoom: {
                                show: true,
                                realtime: true,
                                start: 0,
                                end: 100
                            },
                            xAxis: [
                                {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: existsDate
                                }
                            ],
                            yAxis: [
                                {
                                    type: 'value',
                                    axisLabel: {
                                        formatter: '{value}￥'
                                    }
                                }
                            ],
                            series: [
                                {
                                    name: '销售金额',
                                    type: 'line',
                                    data: amount,
                                    markPoint: {
                                        data: [
                                            {type: 'max', name: '最大值'},
                                            {type: 'min', name: '最小值'}
                                        ]
                                    },
                                    markLine: {
                                        data: [
                                            {type: 'average', name: '平均值'}
                                        ]
                                    }
                                }
                            ]
                        };


                        myChart.setOption(option);
                    });
                require([
                        'echarts',
                        'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                        'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                    ],
                    function (ec) {
                        // 基于准备好的dom，初始化echarts图表
                        var myChart = ec.init(document.getElementById('newFollow'));
                        var option = {
                            title: {
                                text: '员工客户更进趋势图',
                                x:30
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    magicType: {show: true, type: ['line', 'bar']},
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            calculable: true,
                            dataZoom: {
                                show: true,
                                realtime: true,
                                start: 0,
                                end: 100
                            },
                            xAxis: [
                                {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: existsDate
                                }
                            ],
                            yAxis: [
                                {
                                    type: 'value',
                                    axisLabel: {
                                        formatter: '{value}￥'
                                    }
                                }
                            ],
                            series: [
                                {
                                    name: '销售金额',
                                    type: 'line',
                                    data: amount,
                                    markPoint: {
                                        data: [
                                            {type: 'max', name: '最大值'},
                                            {type: 'min', name: '最小值'}
                                        ]
                                    },
                                    markLine: {
                                        data: [
                                            {type: 'average', name: '平均值'}
                                        ]
                                    }
                                }
                            ]
                        };


                        myChart.setOption(option);
                    });
            } else {
                layer.msg("未知错误，请与程序员联系", {icon: 2})
            }
        }
    });
}
function loadStaffNewCustomer(staffId, dateInterval) {
    $.ajax({
        url: "/staff/nc",
        type: "post",
        data: JSON.stringify({staffId: staffId, dateInterval: dateInterval}),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                var staff = result.data, sum = [], existsDate = [];
                for (var i in staff) {
                    sum.push(staff[i].sum);
                    existsDate.push(staff[i].existsDate);
                }
                require([
                        'echarts',
                        'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                        'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                    ], function (ec) {
                        // 基于准备好的dom，初始化echarts图表
                        var myChart = ec.init(document.getElementById('newCustomer'));
                        var option = {
                            title: {
                                text: '员工新增客户趋势图',
                                x:30
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    magicType: {show: true, type: ['line', 'bar']},
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            calculable: true,
                            dataZoom: {
                                show: true,
                                realtime: true,
                                start: 0,
                                end: 100
                            },
                            xAxis: [
                                {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: existsDate
                                }
                            ],
                            yAxis: [
                                {
                                    type: 'value',
                                    axisLabel: {
                                        formatter: '{value}个'
                                    }
                                }
                            ],
                            series: [
                                {
                                    name: '新增客户数量',
                                    type: 'line',
                                    data: sum,
                                    markPoint: {
                                        data: [
                                            {type: 'max', name: '最大值'},
                                            {type: 'min', name: '最小值'}
                                        ]
                                    },
                                    markLine: {
                                        data: [
                                            {type: 'average', name: '平均值'}
                                        ]
                                    }
                                }
                            ]
                        };


                        myChart.setOption(option);
                    });
            } else {
                layer.msg("未知错误，请与程序员联系", {icon: 2})
            }
        }
    });
}
function loadStaffNewFollow(staffId, dateInterval) {
    $.ajax({
        url: "/staff/nf",
        type: "post",
        data: JSON.stringify({staffId: staffId, dateInterval: dateInterval}),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                var staff = result.data, sum = [], existsDate = [];
                for (var i in staff) {
                    sum.push(staff[i].sum);
                    existsDate.push(staff[i].existsDate);
                }
                require([
                        'echarts',
                        'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                        'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                    ],
                    function (ec) {
                        // 基于准备好的dom，初始化echarts图表
                        var myChart = ec.init(document.getElementById('newFollow'));
                        var option = {
                            title: {
                                text: '员工客户更进趋势图',
                                x:30
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    magicType: {show: true, type: ['line', 'bar']},
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            calculable: true,
                            dataZoom: {
                                show: true,
                                realtime: true,
                                start: 0,
                                end: 100
                            },
                            xAxis: [
                                {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: existsDate
                                }
                            ],
                            yAxis: [
                                {
                                    type: 'value',
                                    axisLabel: {
                                        formatter: '{value}条'
                                    }
                                }
                            ],
                            series: [
                                {
                                    name: '新增跟进数量',
                                    type: 'line',
                                    data: sum,
                                    markPoint: {
                                        data: [
                                            {type: 'max', name: '最大值'},
                                            {type: 'min', name: '最小值'}
                                        ]
                                    },
                                    markLine: {
                                        data: [
                                            {type: 'average', name: '平均值'}
                                        ]
                                    }
                                }
                            ]
                        };


                        myChart.setOption(option);
                    });
            } else {
                layer.msg("未知错误，请与程序员联系", {icon: 2})
            }
        }
    });
}

