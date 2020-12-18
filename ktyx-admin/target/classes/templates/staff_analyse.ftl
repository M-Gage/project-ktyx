<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>职员分析</title>
    <link rel="stylesheet" type="text/css" href="../static/js/plugins/fullPage/jquery.fullPage.css">
    <link href="../static/css/animate.css" rel="stylesheet">
    <link href="../static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="../static/js/plugins/layui/css/layui.css" rel="stylesheet">
    <link href="../static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="../static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="../static/css/user/order-info.css" rel="stylesheet"/>
    <style>
        ._interval_join {
            position: absolute;
            float: left;
            z-index: 9999;
            left: 40px;
            top: 30px;
        }

        ._interval_operate, ._interval_age {
            position: absolute;
            float: left;
            z-index: 9999;
            left: 40px;
            margin-top: 30px;
        }

        ._interval_pie {
            position: absolute;
            float: left;
            z-index: 9999;
            left: 45%;

        }

        ._interval_age {
            position: absolute;
            float: left;
            z-index: 9999;
            left: 40px;
            top: 50px;
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
                        <div class="row">
                            <div class="row _interval_join">
                                <div class="col-xs-6 ">

                                    <input id="yearDate" placeholder="请选择年份区间" class="form-control">
                                </div>
                                <div class="col-xs-6">
                                    <input id="monthDate" placeholder="请选择月份区间" class="form-control">
                                </div>
                            </div>
                            <div class="row">
                                <div id="staffJoin"></div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="row _interval_operate">
                                <div class="col-xs-12">
                                    <input id="bigDate" placeholder="请选择日期区间" class="form-control">
                                </div>
                            </div>
                            <div class="row" style="margin-top: 30px">
                                <div id="staffOperate"></div>
                            </div>
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
                            <div class="row _interval_pie">
                                <div class="col-xs-12 ">
                                    <input id="pieDate" placeholder="请选择日期区间" class="form-control">
                                </div>
                            </div>

                            <div class="row">
                                <div id="deptInfo" class="col-md-6"></div>
                                <div id="sexInfo" class="col-md-6"></div>
                            </div>

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
                            <div class="row _interval_age">
                                <div class="col-xs-12 ">
                                    <input id="ageDate" placeholder="请选择日期区间" class="form-control">
                                </div>
                            </div>

                            <div class="row">
                                <div id="staffAgeAnalyse" class="col-md-12"></div>

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

    <!-- echar2 -->
    <script src="../static/js/plugins/echar2/echarts.js"></script>

    <!-- 自定义js -->
    <script src="../static/js/content.js?v=1.0.0"></script>
</div>
<script>
    $(function () {
        require.config({
            paths: {
                echarts: '../static/js/plugins/echar2',
                'echarts/theme/macarons': '../static/js/plugins/echar2/macaons.js' //主题
            }
        });
        $("#staffJoin").css("height", $(window).height() / 2 - 50);
        $("#staffOperate").css("height", $(window).height() / 2 - 50);
        $("#deptInfo").css("height", $(window).height() - 100);
        $("#sexInfo").css("height", $(window).height() - 100);
        $("#staffAgeAnalyse").css("height", $(window).height() - 100);
        //入职
        layui.laydate.render({
            elem: document.getElementById('yearDate')
            , range: '~' //或 range: true 来自定义-分割字符
            , type: 'year'
            // , trigger: 'mouseover'//定义鼠标悬停时弹出控件
            , theme: '#e84a28'//主题颜色
            /*,ready: function(){
                ins.hint('选择区间或双击某天后确认');
            }*/
            , done: function (value, date, enDate) {//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
                if (value !== '') {
                    loadJoin(date.year + "-01-01 ~ " + enDate.year + "-01-01", false)
                }
            }
        });
        layui.laydate.render({
            elem: document.getElementById('monthDate')
            , range: '~' //或 range: true 来自定义-分割字符
            , type: 'month'
            , value: '2018-01 ~ 2018-06'
            // , trigger: 'mouseover'//定义鼠标悬停时弹出控件
            , theme: '#e84a28'//主题颜色
            /*,ready: function(){
                ins.hint('选择区间或双击某天后确认');
            }*/
            , done: function (value, date, enDate) {//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
                if (value !== '') {
                    var start = date.year + "-" + (date.month < 10 ? ("0" + date.month) : date.month) + "-01",
                            end = enDate.year + "-" + (enDate.month < 10 ? ("0" + enDate.month) : enDate.month) + "-01";
                    loadJoin(start + " ~ " + end, true);
                }
            }
        });
        //操作
        layui.laydate.render({
            elem: document.getElementById('bigDate')
            , range: '~' //或 range: true 来自定义-分割字符
            , type: 'date'
            , value: '2018-06-13 ~ 2018-06-30'
            // , trigger: 'mouseover'//定义鼠标悬停时弹出控件
            , theme: '#e8630b'//主题颜色
            , done: function (value, date, enDate) {//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
                if (value !== '') {
                    loadOperate(value)
                }
            }
        });
        /*layui.laydate.render({
            elem: document.getElementById('smallDate')
            , range: '~' //或 range: true 来自定义-分割字符
            , type: 'time'
            , value: '08:00:00 ~ 20:00:00'
            // , trigger: 'mouseover'//定义鼠标悬停时弹出控件
            , theme: '#e8630b'//主题颜色
            /!*,ready: function(){
                ins.hint('选择区间或双击某天后确认');
            }*!/
            , done: function (value, date, enDate) {//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象

                if (value !== '' && $("#bigDate").val() !== '') {
                    loadOperate($("#bigDate").val(), value)
                } else {
                    lay.msg("请先选择日期", {icon: 2})
                }
            }
        });*/
        //饼图
        layui.laydate.render({
            elem: document.getElementById('pieDate')
            , range: '~' //或 range: true 来自定义-分割字符
            , type: 'date'
            , value: '2018-06-13 ~ 2018-06-30'
            // , trigger: 'mouseover'//定义鼠标悬停时弹出控件
            , theme: '#3f1ce8'//主题颜色
            , done: function (value, date, enDate) {//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象

                if (value !== '') {
                    loadDeptInfo(value);
                    loadSexInfo(value)
                }
            }
        });
        //年龄
        layui.laydate.render({
            elem: document.getElementById('ageDate')
            , range: '~' //或 range: true 来自定义-分割字符
            , type: 'date'
            , value: '2018-06-13 ~ 2018-06-30'
            // , trigger: 'mouseover'//定义鼠标悬停时弹出控件
            , theme: '#19ac00'//主题颜色
            , done: function (value, date, enDate) {//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
                if (value !== '') {
                    loadStaffAgeAnalyse(value);
                }
            }
        });
        loadJoin('2018-01-01 ~ 2018-06-01', 'true');
        loadOperate('2018-06-13 ~ 2018-06-30');
    });

    function loadJoin(value, b) {
        $.ajax({
            url: "/staff/join",
            data: JSON.stringify({
                dateInterval: value,
                isMonth: b.toString()
            }),
            type: "post",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result.code === 200) {
                    var staff = result.data, len = staff.length, joinSum = [], joinDate = [];
                    for (var i = 0; i < len; i++) {
                        joinSum.push(staff[i].sum);
                        joinDate.push(staff[i].existsDate);
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
                                var myChart = ec.init(document.getElementById('staffJoin'), theme);
                                var option = {
                                    title: {
                                        text: '员工入职时间分析',
                                        x: "center"
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
                                    xAxis: [{
                                            type: 'category',
                                            data: joinDate
                                        }],
                                    yAxis: [{
                                            type: 'value'
                                        }],
                                    series: [{
                                            name: '入职人数',
                                            type: 'bar',
                                            data: joinSum
                                        }]
                                };

                                myChart.setOption(option, true);
                            });
                } else {
                    layer.msg("未知错误，请与程序员联系")
                }
            }
        });
    }

    function loadOperate(bigVal) {
        $.ajax({
            url: "/staff/operate",
            data: {
                bigInterval: bigVal
            },
            type: "get",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result.code === 200) {

                    var staff = result.data, len = staff.length, operateSum = [], operateDate = [];
                    for (var i = 0; i < len; i++) {
                        operateSum.push(staff[i].sum);
                        operateDate.push(staff[i].existsDate);
                    }
                    require(
                            [
                                'echarts',
                                'echarts/theme/macarons',
                                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
                            ],
                            function (ec, theme) {
                                // 基于准备好的dom，初始化echarts图表
                                var myChart = ec.init(document.getElementById('staffOperate'), theme);

                                var option = {
                                    title: {
                                        text: '员工时间段首次操作习惯分析',
                                        x: "center"
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
                                    xAxis: [{
                                            type: 'category',
                                            data: operateDate
                                        }],
                                    yAxis: [{
                                            type: 'value'
                                        }],
                                    series: [{
                                            name: '首次操作人数',
                                            type: 'line',
                                            data: operateSum
                                        }]
                                };

                                myChart.setOption(option, true);
                            });
                } else {
                    layer.msg("未知错误，请与程序员联系")
                }
            }
        });
    }

    function loadDeptInfo(val) {
        $.ajax({
            url: "/staff/deptInfo/" + val,
            type: "get",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result.code === 200) {
                    var staff = result.data, deptName = [];
                    for (var i in staff) {
                        deptName.push(staff[i].name)
                    }
                    require(
                            [
                                'echarts',
                                'echarts/theme/macarons',
                                'echarts/chart/pie', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/funnel' // 使用柱状图就加载bar模块，按需加载
                            ],
                            function (ec, theme) {
                                // 基于准备好的dom，初始化echarts图表
                                var myChart = ec.init(document.getElementById('deptInfo'), theme);

                                var option = {
                                    title: {
                                        text: '部门销售图',
                                        subtext: '时间段部门销售额',
                                        x: 'center'
                                    },
                                    tooltip: {
                                        trigger: 'item',
                                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                                    },
                                    legend: {
                                        x: 'center',
                                        y: 'bottom',
                                        data: deptName
                                    },
                                    toolbox: {
                                        show: true,
                                        x: 'left',
                                        feature: {
                                            magicType: {
                                                show: true,
                                                type: ['pie', 'funnel']
                                            },
                                            restore: {show: true},
                                            saveAsImage: {show: true}
                                        }
                                    },
                                    calculable: true,
                                    series: [{
                                            name: '该部门销售额度及其占比为：',
                                            type: 'pie',
                                            radius: ['30%'],
                                            roseType: 'radius',
                                            sort: 'ascending',     // for funnel
                                            data: staff
                                        }
                                    ]};

                                myChart.setOption(option, true);
                            });
                } else {
                    layer.msg("未知错误，请与程序员联系")
                }
            }
        });
    }

    function loadSexInfo(val) {
        $.ajax({
            url: "/staff/sexInfo/" + val,
            type: "get",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result.code === 200) {

                    var staff = result.data, deptName = [];
                    for (var i in staff) {
                        deptName.push(staff[i].name)
                    }
                    require(
                            [
                                'echarts',
                                'echarts/theme/macarons',
                                'echarts/chart/pie', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/funnel' // 使用柱状图就加载bar模块，按需加载
                            ],
                            function (ec, theme) {
                                // 基于准备好的dom，初始化echarts图表
                                var myChart = ec.init(document.getElementById('sexInfo'), theme);

                                var option = {
                                    title: {
                                        text: '员工性别单笔销售图',
                                        subtext: '平均值',
                                        x: 'center'
                                    },
                                    tooltip: {
                                        trigger: 'item',
                                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                                    },
                                    legend: {
                                        x: 'center',
                                        y: 'bottom',
                                        data: deptName
                                    },
                                    toolbox: {
                                        show: true,
                                        feature: {
                                            magicType: {
                                                show: true,
                                                type: ['pie', 'funnel']
                                            },
                                            restore: {show: true},
                                            saveAsImage: {show: true}
                                        }
                                    },
                                    calculable: true,
                                    series: [
                                        {
                                            name: '平均数值',
                                            type: 'pie',
                                            radius: ['30%'],
                                            roseType: 'radius',
                                            sort: 'ascending',     // for funnel
                                            data: staff
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

    function loadStaffAgeAnalyse(dateVal) {
        $.ajax({
            url: "/staff/ageAnalyse",
            type: "Post",
            data: JSON.stringify({dateInterval: dateVal}),
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result.code === 200) {

                    var staff = result.data, ageInterval = [], amount = [], addAge = 20;
                    for (var i in staff) {
                        amount.push(staff[i].sum);
                        if (staff[i].existsDate === '0') {
                            ageInterval.push("< 20岁")
                        } else if (staff[i].existsDate === '7') {
                            ageInterval.push("> 50岁")
                        } else {
                            ageInterval.push((Number(staff[i].existsDate) - 1) * 5 + addAge + " ~ " + (Number(staff[i].existsDate) * 5 + addAge) + "岁")
                        }
                    }
                    require(
                            [
                                'echarts',
                                'echarts/theme/macarons',
                                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
                            ],
                            function (ec, theme) {
                                // 基于准备好的dom，初始化echarts图表
                                var myChart = ec.init(document.getElementById('staffAgeAnalyse'), theme);

                                var option = {
                                    title: {
                                        text: '员工年龄销售额报表',
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

    var $mlNav = $('.ml-nav'), flag = true, flag2 = true;
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
                    loadDeptInfo('2018-06-13 ~ 2018-06-30');
                    loadSexInfo('2018-06-13 ~ 2018-06-30');

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
                    loadStaffAgeAnalyse('2018-06-13 ~ 2018-06-30');
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
