<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>客户分析</title>
    <link rel="stylesheet" type="text/css" href="../static/js/plugins/fullPage/jquery.fullPage.css">
    <link href="../static/css/animate.css" rel="stylesheet">
    <link href="../static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="../static/js/plugins/layui/css/layui.css" rel="stylesheet">
    <link href="../static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="../static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="../static/css/user/order-info.css" rel="stylesheet"/>
    <link rel="stylesheet" href="../static/js/plugins/bootstrap-select/css/bootstrap-select.min.css">
    <style>
        ._interval {
            position: absolute;
            float: right;
            z-index: 9999;
            right: 40px;
        }

        ._interval_treeMap {
            position: absolute;
            float: left;
            z-index: 9999;
            left: 15%;
            width: 650px;
        }

        ._interval_line {
            position: absolute;
            float: left;
            z-index: 9999;
            left: 25%;
        }

    </style>
</head>

<body class="gray-bg">
<div id="staffInfo">
    <div class="section ">
        <div class="center-wrap">
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="row">
                    <div class="ibox float-e-margins"></div>

                    <div class="ibox-content">

                        <div class="row _interval_line">
                            <div class="col-xs-3">
                                <div class="btn-group" style="float: right">
                                    <button type="button" class="btn btn-sm btn-info dropdown-toggle"
                                            data-toggle="dropdown"
                                            aria-haspopup="true" aria-expanded="false">
                                        本周
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" style="min-width: 20px !important;">
                                        <li><a href="javascript:;" name="week">本周</a></li>
                                        <li><a href="javascript:;" name="month">本月</a></li>
                                        <li><a href="javascript:;" name="quarter">本季</a></li>
                                        <li><a href="javascript:;" name="year">本年</a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-xs-9">
                                <input id="firstDate" placeholder="请选择日期区间" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div id="orderFrequencyAndAmount"></div>
                        </div>
                        <div class="row" style="margin-top: 30px">
                            <div id="orderGoodsAndType"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="section ">
        <div class="center-wrap">
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="row">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title"></div>
                        <div class="ibox-content">
                            <div class="row _interval_treeMap">
                                <div class="col-md-4">
                                    <select name="label" class="form-control selectpicker" multiple
                                            data-live-search="true">
                                        <option>请选择</option>
                                    </select>
                                </div>
                                <div class="col-md-4"></div>
                                <div class="col-md-4 ">
                                    <input id="secondDate" placeholder="请选择日期区间" class="form-control">
                                </div>
                            </div>
                            <div class="row">
                                <div id="labelInfo" class="col-md-3"></div>
                                <div id="ageOrderAvg" class="col-md-9"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div>
    <script src="../static/js/jquery.min.js"></script>
    <script src="../static/js/plugins/fullPage/jquery.fullPage.js"></script>
    <script src="../static/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="../static/js/plugins/layui/layui.all.js"></script>

    <!-- bootstrap select -->
    <script src="../static/js/plugins/bootstrap-select/js/bootstrap-select.min.js"></script>
    <script src="../static/js/plugins/bootstrap-select/js/i18n/defaults-zh_CN.min.js"></script>

    <!-- echar2 -->
    <script src="../static/js/plugins/echar2/echarts.js"></script>

    <!-- 自定义js -->
    <script src="../static/js/content.js?v=1.0.0"></script>
</div>
<script>
    var $mlNav = $('.ml-nav'), flag = true, flag2 = true, DI = preDate(-6);
    $(function () {

        require.config({
            paths: {
                echarts: '../static/js/plugins/echar2',
                'echarts/theme/macarons': '../static/js/plugins/echar2/macaons.js' //主题
            }
        });
        $("#orderFrequencyAndAmount").css("height", $(window).height() / 2 - 50);
        $("#orderGoodsAndType").css("height", $(window).height() / 2 - 50);
        $("#labelInfo").css("height", $(window).height() - 100);
        $("#ageOrderAvg").css("height", $(window).height() - 100);

        $("[name=label]").selectpicker();
        //第一页报表
        layui.laydate.render({
            elem: document.getElementById('firstDate')
            , range: '~' //或 range: true 来自定义-分割字符
            , type: 'date'
            , value: DI
            // , trigger: 'mouseover'//定义鼠标悬停时弹出控件
            , theme: '#dae81b'//主题颜色
            , done: function (value, date, enDate) {//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象

                if (value !== '') {
                    loadOrderFrequencyAndAmount(value,!(enDate.year > date.year || enDate.month - date.month > 3));
                    loadOrderGoodsAndType(value)
                }
            }
        });
        //第二页报表
        layui.laydate.render({
            elem: document.getElementById('secondDate')
            , range: '~' //或 range: true 来自定义-分割字符
            , type: 'date'
            , value: DI
            // , trigger: 'mouseover'//定义鼠标悬停时弹出控件
            , theme: '#0ae806'//主题颜色
            , done: function (value, date, enDate) {//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象

                if (value !== '') {
                    loadLabelInfo(value, $("[name=label]").val());
                    loadAgeOrderAvg(value)
                }
            }
        });
        loadOrderFrequencyAndAmount(DI,true);
        loadOrderGoodsAndType(DI);

    });
    $("[name=label]").change(function () {
        loadLabelInfo($("#secondDate").val(), $("[name=label]").val())
    });
    $("[name=label]").parent().click(function () {
        if ($("[name=label]").find("option").length > 1) {
            return;
        }
        $.ajax({
            url: "/label/company",
            type: "get",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result.code === 200) {
                    var labelList = result.data, List = $("[name=label]");
                    List.html("");
                    for (var i in labelList) {
                        List.append("<option value='" + labelList[i].labelId + "'>" + labelList[i].labelName + "</option>")
                    }
                    List.selectpicker("refresh");
                }
            }
        });
    });

    $("[name=week]").click(function () {
        var _this = $(this);
        if (_this.parent().parent().prev().html().substr(0, 2) === _this.html()) {
            return;
        }
        _this.parent().parent().prev().html(_this.html() + " <span class='caret'></span>");
        var week = getInterval(true, false, false);
        loadOrderFrequencyAndAmount(week,true);
        loadOrderGoodsAndType(week);
    });
    $("[name=month]").click(function () {
        var _this = $(this);
        if (_this.parent().parent().prev().html().substr(0, 2) === _this.html()) {
            return;
        }
        _this.parent().parent().prev().html(_this.html() + " <span class='caret'></span>");
        var month = getInterval(false, true, false);
        loadOrderFrequencyAndAmount(month,true);
        loadOrderGoodsAndType(month);
    });
    $("[name=quarter]").click(function () {
        var _this = $(this);
        if (_this.parent().parent().prev().html().substr(0, 2) === _this.html()) {
            return;
        }
        _this.parent().parent().prev().html(_this.html() + " <span class='caret'></span>");
        var quarter = getInterval(false, false, true);
        loadOrderFrequencyAndAmount(quarter,true);
        loadOrderGoodsAndType(quarter);
    });
    $("[name=year]").click(function () {
        var _this = $(this);
        if (_this.parent().parent().prev().html().substr(0, 2) === _this.html()) {
            return;
        }
        _this.parent().parent().prev().html(_this.html() + " <span class='caret'></span>");
        var year = getInterval(false, false, false);
        loadOrderFrequencyAndAmount(year,false);
        loadOrderGoodsAndType(year);
    });


    function loadOrderFrequencyAndAmount(value,isDay) {
        $.ajax({
            url: "/customer/faa",
            data: JSON.stringify({
                dateInterval: value,
                isDay:isDay
            }),
            type: "Post",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result.code === 200) {

                    var staff = result.data, len = staff.length, avgSum = [], avgAmount = [], existsDate = [];
                    for (var i = 0; i < len; i++) {
                        avgSum.push(staff[i].sum);
                        avgAmount.push(staff[i].amount);
                        existsDate.push(staff[i].existsDate);
                    }
                    require([
                                'echarts',
                                'echarts/theme/macarons',
                                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
                            ],
                            function (ec, theme) {
                                // 基于准备好的dom，初始化echarts图表
                                var myChart = ec.init(document.getElementById('orderFrequencyAndAmount'), theme);

                                var option = {
                                    title: {
                                        text: '客户平均下单金额和频率报表',
                                        x: 50
                                    },
                                    tooltip: {
                                        trigger: 'axis'
                                    },
                                    legend: {
                                        data: ['客户平均下单金额', '客户平均下单频率'],
                                        selected: {
                                            '客户平均下单频率': false
                                        },
                                        selectedMode: 'single'
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
                                    xAxis: [
                                        {
                                            type: 'category',
                                            data: existsDate
                                        }
                                    ],
                                    yAxis: [
                                        {
                                            type: 'value'
                                        }
                                    ],
                                    series: [

                                        {
                                            name: '客户平均下单金额',
                                            type: 'line',
                                            data: avgAmount

                                        },

                                        {
                                            name: '客户平均下单频率',
                                            type: 'line',
                                            data: avgSum

                                        }
                                    ]
                                };

                                myChart.setOption(option, true);
                            });


                } else {
                    layer.msg("未知错误，请与程序员联系")
                }
            }
        });
    }

    function loadOrderGoodsAndType(value) {
        $.ajax({
            url: "/customer/gat",
            data: JSON.stringify({
                dateInterval: value
            }),
            type: "post",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result.code === 200) {

                    var goods = result.data.goods, type = result.data.type, goodsAvg = result.data.goodsAvg,
                            goodsAmount = [], goodsName = [],
                            goodsAvgSum = [], goodsAvgName = [],
                            typeAmount = [], typeName = [];
                    for (var i in goods) {
                        goodsAmount.push(goods[i].amount);
                        goodsName.push(goods[i].goodsName);
                    }
                    for (var i in type) {
                        typeAmount.push(type[i].amount);
                        typeName.push(type[i].typeName)
                    }
                    for (var i in goodsAvg) {
                        goodsAvgSum.push(goodsAvg[i].sum);
                        goodsAvgName.push(goodsAvg[i].goodsName)
                    }
                    require(
                            [
                                'echarts',
                                'echarts/theme/macarons',
                                'echarts/chart/bar',// 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                            ],
                            function (ec, theme) {
                                // 基于准备好的dom，初始化echarts图表
                                var myChart = ec.init(document.getElementById('orderGoodsAndType'), theme);

                                var option = {
                                    title: {
                                        text: '客户购买商品行为分析',
                                        x: 50
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
                                    legend: {
                                        data: ['客户商品品种销售额', '客户商品分类销售额', '客户商品平均每单销售数量'],
                                        selected: {
                                            '客户商品分类销售额': false,
                                            '客户商品平均每单销售数量': false
                                        },
                                        selectedMode: 'single'
                                    },
                                    calculable: true,
                                    xAxis: [
                                        {
                                            type: 'category',
                                            data: goodsName
                                        }
                                    ],
                                    yAxis: [
                                        {
                                            type: 'value'
                                        }
                                    ],
                                    series: [
                                        {
                                            name: '客户商品品种销售额',
                                            type: 'bar',
                                            data: goodsAmount
                                        },
                                        {
                                            name: '客户商品分类销售额',
                                            type: 'bar',
                                            data: typeAmount
                                        },
                                        {
                                            name: '客户商品平均每单销售数量',
                                            type: 'bar',
                                            data: goodsAvgSum
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
                                    if (selected['客户商品品种销售额']) {
                                        flag = true;
                                        var opt = myChart.getOption();
                                        opt.xAxis[0]["data"] = goodsName;
                                        myChart.setOption(opt, true);
                                    }
                                    if (selected['客户商品分类销售额']) {
                                        flag = false;
                                        var opt = myChart.getOption();
                                        opt.xAxis[0]["data"] = typeName;
                                        myChart.setOption(opt, true);

                                    }
                                    if (selected['客户商品平均每单销售数量']) {
                                        flag = false;
                                        var opt = myChart.getOption();
                                        opt.xAxis[0]["data"] = goodsAvgName;
                                        myChart.setOption(opt, true);

                                    }
                                });


                                myChart.setOption(option, true);
                            });


                } else {
                    layer.msg("未知错误，请与程序员联系")
                }
            }
        });
    }


    function loadLabelInfo(val, labelList) {
        $.ajax({
            url: "/customer/labelInfo",
            data: JSON.stringify({dateInterval: val, labelList: labelList}),
            type: "post",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result.code === 200) {
                    var customer = result.data;
                    require(
                            [
                                'echarts',
                                'echarts/theme/macarons',
                                'echarts/chart/treemap'
                            ],
                            function (ec, theme) {
                                // 基于准备好的dom，初始化echarts图表
                                var myChart = ec.init(document.getElementById('labelInfo'), theme);

                                var option = {
                                    title: {
                                        text: '客户标签销售额占有率',
                                        subtext: '仅为公司提供的标签'
                                    },
                                    tooltip: {
                                        trigger: 'item',
                                        formatter: "{b} : {c}"
                                    },
                                    toolbox: {
                                        show: true,
                                        feature: {
                                            restore: {show: true},
                                            saveAsImage: {show: true}
                                        },
                                        y: 60
                                    },
                                    calculable: false,
                                    series: [
                                        {
                                            name: '份额越大占面积越大',
                                            type: 'treemap',
                                            itemStyle: {
                                                normal: {
                                                    label: {
                                                        show: true,
                                                        formatter: "{b}: {c}"
                                                    },
                                                    borderWidth: 1
                                                },
                                                emphasis: {
                                                    label: {
                                                        show: true
                                                    }
                                                }
                                            },
                                            data: customer
                                        }
                                    ]
                                };
                                myChart.setOption(option, true);
                            });
                } else {
                    layer.msg("未知错误，请与程序员联系")
                }
            }
        });
    }

    function loadAgeOrderAvg(val) {
        $.ajax({
            url: "/customer/aoa",
            type: "post",
            data: JSON.stringify({dateInterval: val}),
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result.code === 200) {

                    var customer = result.data, ageInterval = [], amount = [], addAge = 20;

                    for (var i in customer) {
                        amount.push(customer[i].sum);
                        if (customer[i].existsDate === '0') {
                            ageInterval.push("< 20岁")
                        } else if (customer[i].existsDate === '9') {
                            ageInterval.push("> 60岁")
                        } else if(customer[i].existsDate === '-1'){
                            ageInterval.push("其他")
                        }else {
                            ageInterval.push((Number(customer[i].existsDate) - 1) * 5 + addAge + " ~ " + (Number(customer[i].existsDate) * 5 + addAge) + "岁")
                        }
                    }
                    require(
                            [
                                'echarts',
                                'echarts/theme/macarons',
                                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
                            ],
                            function (ec) {
                                // 基于准备好的dom，初始化echarts图表
                                var myChart = ec.init(document.getElementById('ageOrderAvg'));

                                var option = {
                                    title: {
                                        text: '客户年龄销售额报表',
                                        subtext: '各年龄段平均销售额',
                                        x: 'center'
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
                                    xAxis: [
                                        {
                                            type: 'category',
                                            boundaryGap: false,
                                            data: ageInterval
                                        }
                                    ],
                                    yAxis: [
                                        {
                                            type: 'value',
                                            axisLabel: {
                                                formatter: '{value} ￥'
                                            }
                                        }
                                    ],
                                    series: [
                                        {
                                            name: '年龄段销售额',
                                            type: 'line',
                                            data: amount,
                                            itemStyle: {normal: {areaStyle: {type: 'default'}}},
                                            smooth: true,
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

                                myChart.setOption(option, true);
                            });


                } else {
                    layer.msg("未知错误，请与程序员联系")
                }
            }
        });
    }

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
                    loadLabelInfo(DI, $("[name=label]").val());
                    loadAgeOrderAvg(DI);
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

                    flag2 = false;
                }
                $mlNav.animate({
                    top: -66
                }, 400);
            }
        }
    });

</script>

</body>
</html>
