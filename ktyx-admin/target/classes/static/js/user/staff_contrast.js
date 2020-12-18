$(function () {
    $("[name=staff]").selectpicker();
    require.config({
        paths: {
            echarts: '../static/js/plugins/echar2'
        }
    });
});
var flag3 = true,
    noDataStr = "<div style='width: " + ($(window).width() / 2 - 20) + "px;height: " + ($(window).height() / 2 - 20) + "px;margin:0 auto;padding-top:80px;'>" +
        "<img  style='width: " + ($(window).width() / 2 - 20) + "px;height: " + ($(window).height() / 2 - 20) + "px;' src='../static/img/staff-no-data.jpg'/><br/>" +
        "</div>";
$("[name=staff]").parent().click(function () {
    staffList("[name=staff]");
});

$(".btn-primary").click(function () {
    var staff = $("[name=staff]"), staffId1 = staff.eq(0).val(), staffId2 = staff.eq(1).val();
    if (staffId1 !== '' && staffId2 !== '') {
        if (staffId1 === staffId2) {
            layer.msg("请选择不同业务员", {icon: 2});
            return false;
        }
        $("#info").css("display", "inline");
        $("#introduce").css("display", "none");
        loadDate(staffId1, 1);
        loadDate(staffId2, 2);
        var $mlNav = $('.ml-nav');
        var flag = true, flag2 = true;
        if (!flag3) {
            $('#staffInfo').fullpage.destroy('all');

        }
        flag3 = false;
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
                        loadOrderGoods(staffId1, 1);
                        loadOrderGoods(staffId2, 2);
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
                        loadSchedule1(staffId1, 1);
                        loadSchedule1(staffId2, 2);
                        loadSchedule2(staffId1, 1);
                        loadSchedule2(staffId2, 2);
                        flag2 = false;
                    }
                    $mlNav.animate({
                        top: -66
                    }, 400);
                }
            }
        });

        contrast(staffId1, 1);
        contrast(staffId2, 2);
    } else {
        layer.msg("请选择业务员", {icon: 2})
    }
    return false;
});

function contrast(staffId, int) {
    $("#staffStatistic" + int).click(function () {
        var index = layer.open({
            type: 2 //此处以iframe举例
            , title: "报表"
            , shade: 0
            , content: '/staffInfo?staff=' + staffId
            , maxmin: true
            , zIndex: layer.zIndex //重点1
            , success: function (layero) {
                layer.setTop(layero); //重点2
            }
        });
        layer.full(index);
    })
}

function loadDate(staffId, int) {
    $("#orderGoods" + int).css("height", $(window).height() - 200);

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

                    str += "<div class=\"col-xs-6\">\n" +
                        "      <div class=\"col-md-1\"></div>\n" +
                        "      <div class=\"col-md-10 contrast\" style='padding-top: 20px'>\n" +
                        "              <div style=\"width:" + (fullWidth / 5) + "px; height:" + (fullWidth / 5) / 2 + "px; background:url(" + (bm < lm ? imgArr[i][0] : imgArr[i][1]) + ");background-size:100% 100%;padding: 11% 0 0 25%;float: right\" class='text-center' >\n" +
                        "                  <h3>" + titleArr[i] + "</h3><br>\n" +
                        "                  <h4><span class=\"beforeLastMonth\"> </span>: <span\n" +
                        "                          id=\"beforeLastNewCustomerNum\">" + bm + "</span></h4>\n" +
                        "                  <h4><span class=\"lastMonth\"> </span>: <span\n" +
                        "                          id=\"lastNewCustomerNum\">" + lm + "</span>\n" +
                        "                  </h4>\n" +
                        "              </div>\n" +
                        "      </div>\n" +
                        "      <div class=\"col-md-1\"></div>\n" +
                        "  </div>";

                }
                $("#contrastBox" + int).html(str);
                $(".beforeLastMonth").html(getPreMonth(2));
                $(".lastMonth").html(getPreMonth(1));
            } else {
                layer.msg("未知错误，请与程序员联系", {icon: 2})
            }
        }
    });

};

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

function loadOrderGoods(staffId, int) {
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
                    goodsSums.push(goods[i].stock);
                }

                if (goodsNames.length === 0) {
                    $("#orderGoods" + int).html(noDataStr);
                } else {
                    require(
                        [
                            'echarts',
                            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
                        ],
                        function (ec) {
                            // 基于准备好的dom，初始化echarts图表
                            var myChart = ec.init(document.getElementById('orderGoods' + int));
                            var option = {

                                title: {
                                    x: 'center',
                                    text: getPreMonth(1) + '货物销售量排行数据'
                                    // , subtext: 'Rainbow bar example'
                                },
                                tooltip: {
                                    trigger: 'item'
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
                                        data: goodsSums,
                                        markPoint: {
                                            tooltip: {
                                                trigger: 'item',
                                                backgroundColor: 'rgba(0,0,0,0)'
                                            }
                                        }
                                    }
                                ]
                            };

                            myChart.setOption(option);
                        });
                }


            } else {
                layer.msg("未知错误，请与程序员联系")
            }
        }
    });


    // }
}

function loadSchedule1(staffId, int) {
    $.ajax({
        url: "/staff/schedule1/" + staffId,
        type: "get",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                var s = result.data, len = s.length, monthStr = "<td>本月</td>", quarterStr = "<td>本季</td>",
                    yearStr = "<td>本年</td>";
                for (var i = 0; i < len; i++) {
                    monthStr += "<td>" + s[i].monthData + "</td>";
                    quarterStr += "<td>" + s[i].quarterData + "</td>";
                    yearStr += "<td>" + s[i].yearData + "</td>";
                }
                $("#month" + int).html(monthStr);
                $("#quarter" + int).html(quarterStr);
                $("#year" + int).html(yearStr);
            } else {
                layer.msg("未知错误，请与程序员联系", {icon: 2})
            }
        }
    })
}

function loadSchedule2(staffId, int) {
    $.ajax({
        url: "/staff/schedule2/" + staffId,
        type: "get",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                var s = result.data;

                $("#moneySchedule" + int).html("<td>金额</td><td>" + ((s === null || s.monthData === null) ? 0 :s.monthData ) + "元</td>" +
                    "<td>" + ((s === null || s.quarterData === null) ? 0 :s.quarterData ) + "元</td>" +
                    "<td>" + ((s === null || s.yearData === null) ? 0 :s.yearData ) + "元</td>" +
                    "<td>" + ((s === null || s.historyData === null) ? 0 :s.historyData ) + "元</td>"
                );
            } else {
                layer.msg("未知错误，请与程序员联系", {icon: 2})
            }
        }
    })
}
