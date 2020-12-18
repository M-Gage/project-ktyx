<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>订单统计图表</title>
    <link rel="stylesheet" type="text/css" href="../static/js/plugins/fullPage/jquery.fullPage.css">
    <link href="../static/css/animate.css" rel="stylesheet">
    <link href="../static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">

    <link href="../static/js/plugins/layui/css/layui.css" rel="stylesheet">

    <link href="../static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="../static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="../static/css/user/order-info.css" rel="stylesheet"/>

    <style>
        ._interval {
            position: absolute;
            float: right;
            z-index: 9999;
            right: 130px;
            width: 860px;
        }

        ._interval_area {
            position: fixed;
            float: right;
            z-index: 9999;
            right: 200px;
            width: 600px;
            top: 15px;
            display: block;
        }

        label {
            margin-top: 6px
        }

    </style>
</head>

<body>
<div class="row _interval_area">
    <div class="col-md-6 form-group">
        <label class="col-md-4 control-label text-right">省份：</label>
        <div class="col-xs-8"><select name="province" class="form-control full-width"
                                      onchange="provinceChange(this)"
                                      id="param_province">
        </select></div>
    </div>
    <div class="col-md-6 form-group">
        <label class="col-md-4 control-label text-right">城市：</label>
        <div class="col-xs-8"><select name="city" class="form-control" id="param_city"
                                      onchange="citiesChange(this,false)">
            <option>请选择城市</option>
        </select></div>
    </div>
</div>
<div id="area">

    <div class="section ">
        <div class="center-wrap">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                    <div id="areaCustomerAvgAmount"></div>
                    <div id="areaGoodsAmount"></div>
                </div>
            </div>
        </div>
    </div>

    <div class="section">
        <div class="center-wrap">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="row _interval">
                        <div class="col-md-3">
                            <input id="areaAmountDate" placeholder="请选择日期区间" class="form-control">
                        </div>
                    </div>
                    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                    <div class="row">
                        <div id="areaAmount"></div>
                        <div id="areaType"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="../static/js/jquery.min.js"></script>
<script src="../static/js/plugins/fullPage/jquery.fullPage.js"></script>
<script src="../static/js/bootstrap.min.js?v=3.3.6"></script>
<script src="../static/js/plugins/layui/layui.all.js"></script>

<!-- echar2 -->
<script src="../static/js/plugins/echar2/echarts.js"></script>

<!-- 自定义js -->
<script src="../static/js/content.js?v=1.0.0"></script>
</body>

<script>
    var $mlNav = $('.ml-nav'), flag = true, flag2 = true, DI = preDate(-6), provinces = [], cities = [];
    $(function () {
        require.config({
            paths: {
                echarts: '../static/js/plugins/echar2',
                'echarts/theme/macarons': '../static/js/plugins/echar2/macaons.js' //主题
            }
        });
        $("#areaAmount").css("height", $(window).height() / 2 - 50);
        $("#areaType").css("height", $(window).height() / 2 - 50);
        $("#areaCustomerAvgAmount").css("height", $(window).height() / 2 - 50);
        $("#areaGoodsAmount").css("height", $(window).height() / 2 - 50);

        $.ajax({
            url: '/static/js/address.json',
            type: 'get',
            async: false,
            success: function (result) {
                provinces.push('默认江西省');
                cities.push(['请选择城市']);
                for (var i in result) {
                    provinces.push(result[i].name);
                    if (result[i].city.length === 1) {
                        cities.push(result[i].city[0].area);
                    } else {
                        var city = [];
                        for (var j in result[i].city) {
                            city.push(result[i].city[j].name);
                        }
                        cities.push(city)
                    }
                }
            }
        });

        initArea();
        //第一页报表
        layui.laydate.render({
            elem: document.getElementById('areaAmountDate')
            , range: '~' //或 range: true 来自定义-分割字符
            , type: 'date'
            , theme: '#3fe898'//主题颜色
            , value: DI
            , done: function (value, date, enDate) {//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
                if (value !== '') {
                    var area, city = $('[name=city]').val(), province = $('[name=province]').val();
                    if (city === '请选择城市') {
                        if (province === '默认江西省') {
                            area = '江西省';
                        } else {
                            area = province;
                        }
                    } else {
                        area = city;
                    }
                    loadAreaAmount(value, area);
                    loadAreaType(value, area);
                }

            }
        });

        loadAreaCustomerAvgAmount('江西省',true);
        loadAreaGoodsAmount('江西省');
    });

    function loadAreaAmount(dateInterval, area) {
        $.ajax({
            url: "/order/area/amount",
            data: JSON.stringify({dateInterval: dateInterval, area: area}),
            type: 'post',
            contentType: 'application/json;charset=UTF-8',
            success: function (result) {
                if (result.code === 200) {
                    var area = result.data, amount = [], existsDate = [];
                    for (var i in area) {
                        amount.push(area[i].amount);
                        existsDate.push(area[i].existsDate);
                    }
                    require([
                                'echarts',
                                'echarts/theme/macarons',
                                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
                            ],
                            function (ec, theme) {
                                // 基于准备好的dom，初始化echarts图表
                                var myChart = ec.init(document.getElementById('areaAmount'), theme);

                                var option = {
                                    title: {
                                        text: '区域时间段销售趋势图',
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
                                    calculable: true,
                                    xAxis: [{
                                        type: 'category',
                                        data: existsDate
                                    }],
                                    yAxis: [{
                                        type: 'value'
                                    }],
                                    series: [{
                                        name: '当天销售额',
                                        type: 'line',
                                        data: amount

                                    }]
                                };
                                myChart.setOption(option, true);
                            });
                } else {
                    layer.msg("未知错误，请与程序员联系")
                }
            }
        })

    }

    function loadAreaType(dateInterval, area) {
        $.ajax({
            url: "/order/area/type",
            data: JSON.stringify({dateInterval: dateInterval, area: area}),
            type: 'post',
            contentType: 'application/json;charset=UTF-8',
            success: function (result) {
                if (result.code === 200) {
                    var area = result.data, amount = [], typeName = [];
                    for (var i in area) {
                        amount.push(area[i].amount);
                        typeName.push(area[i].typeName);
                    }
                    require([
                                'echarts',
                                'echarts/theme/macarons',
                                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
                            ],
                            function (ec, theme) {
                                // 基于准备好的dom，初始化echarts图表
                                var myChart = ec.init(document.getElementById('areaType'), theme);

                                var option = {
                                    title: {
                                        text: '区域时间段销售趋势图',
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
                                    calculable: true,
                                    xAxis: [{
                                        type: 'category',
                                        data: typeName
                                    }],
                                    yAxis: [{
                                        type: 'value'
                                    }],
                                    series: [{
                                        name: '当天销售额',
                                        type: 'bar',
                                        data: amount

                                    }]
                                };
                                myChart.setOption(option, true);
                            });
                } else {
                    layer.msg("未知错误，请与程序员联系")
                }
            }
        })

    }
    
    function loadAreaCustomerAvgAmount(area,isProvince) {
        $.ajax({
            url: "/order/area/acaa",
            data: JSON.stringify({area: area,isProvince:isProvince}),
            type: 'post',
            contentType: 'application/json;charset=UTF-8',
            success: function (result) {
                if (result.code === 200) {
                    var area = result.data, amount = [], areaName = [],total=[];
                    for (var i in area) {
                        amount.push(area[i].amount);
                        areaName.push(area[i].areaName);
                        total.push(area[i].total);
                    }
                    require([
                                'echarts',
                                'echarts/theme/macarons',
                                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
                            ],
                            function (ec, theme) {
                                // 基于准备好的dom，初始化echarts图表
                                var myChart = ec.init(document.getElementById('areaCustomerAvgAmount'), theme);

                                var option = {
                                    title: {
                                        text: '区域客户销售图',
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
                                        data: ['区域销售总金额',  '客户平均每单金额']
                                    },
                                    calculable: true,
                                    xAxis: [{
                                        type: 'category',
                                        data: areaName
                                    }],
                                    yAxis: [{
                                        type: 'value'
                                    }],
                                    series: [{
                                        name: '区域销售总金额',
                                        type: 'bar',
                                        data: total,
                                        markLine : {
                                            data : [
                                                {type : 'average', name : '平均值'}
                                            ]
                                        }
                                    },{
                                        name: '客户平均每单金额',
                                        type: 'bar',
                                        data: amount,
                                        markLine : {
                                            data : [
                                                {type : 'average', name : '平均值'}
                                            ]
                                        }
                                    }]
                                };
                                myChart.setOption(option, true);
                            });
                } else {
                    layer.msg("未知错误，请与程序员联系")
                }
            }
        })
    }

    function loadAreaGoodsAmount(area) {
        $.ajax({
            url: "/order/area/aga",
            data: JSON.stringify({area: area}),
            type: 'post',
            contentType: 'application/json;charset=UTF-8',
            success: function (result) {
                if (result.code === 200) {
                    var area = result.data, amount = [], goodsName = [];
                    for (var i in area) {
                        amount.push(area[i].amount);
                        goodsName.push(area[i].goodsName);
                    }
                    require([
                                'echarts',
                                'echarts/theme/macarons',
                                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
                            ],
                            function (ec, theme) {
                                // 基于准备好的dom，初始化echarts图表
                                var myChart = ec.init(document.getElementById('areaGoodsAmount'), theme);

                                var option = {
                                    title: {
                                        text: '区域商品报表',
                                        x: 50
                                    },
                                    tooltip: {
                                        trigger: 'item'
                                    },
                                    toolbox: {
                                        show: true,
                                        feature: {
                                            dataView: {show: true, readOnly: false},
                                            restore: {show: true},
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
                                            data: goodsName
                                        }
                                    ],
                                    yAxis: [
                                        {
                                            type: 'value',
                                            show: false
                                        }
                                    ],
                                    series: [
                                        {
                                            name: 'ECharts例子个数统计',
                                            type: 'bar',
                                            itemStyle: {
                                                normal: {
                                                    color: function(params) {
                                                        // build a color map as your need.
                                                        var colorList = [
                                                            '#e50c09', '#fdad0c',
                                                            '#fcf036', '#0ad11d',
                                                            '#52f1ff', '#131dfe',
                                                            '#d510cd', '#fa6bc9',
                                                            '#b8dc48', '#4cb8dd'
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
                                            data: amount
                                        }
                                    ]
                                };
                                myChart.setOption(option, true);
                            });
                } else {
                    layer.msg("未知错误，请与程序员联系")
                }
            }
        })
    }

    function initArea() {
        var province = document.getElementById('param_province');
        //给选择框一个高度，可直接写进数据，不然要先创建dom元素option再录值
        province.length = provinces.length;
        for (var i = 0; i < provinces.length; i++) {
            province.options[i].text = provinces[i];
            province.options[i].value = provinces[i];
        }
    }

    function citiesChange(obj,isProvince) {
        var dateInterval = $("#areaAmountDate").val(), area = $(obj).val();
        if (dateInterval === '') {
            lay.msg("请选择时间", {icon: 2});
            return;
        }
        loadAreaAmount(dateInterval, area);
        loadAreaType(dateInterval, area);
        loadAreaCustomerAvgAmount(area,isProvince);
        loadAreaGoodsAmount(area);
    }

    function provinceChange(obj) {
        citiesChange(obj,true);
        var n = obj.selectedIndex;
        var city = document.getElementById('param_city');
        var citiesTemp = cities[n];
        city.length = citiesTemp.length + 1;
        city.options[0].text = '请选择城市';
        for (var i = 1; i < citiesTemp.length + 1; i++) {
            city.options[i].text = citiesTemp[i - 1];
            city.options[i].value = citiesTemp[i - 1];
        }
        city.options[0].selected = true;
    }

    $('#area').fullpage({
        verticalCentered: !1,
        navigation: !0,
        onLeave: function (index, nextIndex, direction) {
            if (index === 2 && direction === 'up') {
                $mlNav.animate({
                    top: 80
                }, 680);
            } else if (index === 1 && direction === 'down') {
                if (flag) {
                    loadAreaAmount(DI, '江西省');
                    loadAreaType(DI, '江西省');
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
</html>