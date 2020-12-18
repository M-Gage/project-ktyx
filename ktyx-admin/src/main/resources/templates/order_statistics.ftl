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
            right: 40px;
        }


    </style>
</head>

<body onload="loadFirstStatistics();">

<div id="statistics">

    <div class="section ">
        <div class="center-wrap">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <!-- Single button -->
                    <div class="row _interval">
                        <div class="col-xs-3">
                            <div class="btn-group" style="float: right">
                                <button type="button" class="btn btn-sm btn-info dropdown-toggle" data-toggle="dropdown"
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
                            <input id="amountDate" placeholder="请选择日期区间"
                                   class="form-control">
                        </div>
                    </div>
                    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                    <div id="orderAmount" style="height: 600px"></div>
                </div>
            </div>
        </div>
    </div>

    <div class="section ">
        <div class="center-wrap">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="row _interval">
                        <div class="col-xs-3">
                            <div class="btn-group" style="float: right">
                                <button type="button" class="btn btn-sm btn-info dropdown-toggle" data-toggle="dropdown"
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
                            <input id="rankDate" placeholder="请选择日期区间" class="form-control">
                        </div>
                    </div>
                    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                    <div id="orderRank" style="height: 600px;"></div>
                </div>
            </div>
        </div>
    </div>

    <div class="section ">
        <div class="center-wrap">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="row _interval">
                        <div class="col-xs-3">
                            <div class="btn-group" style="float: right">
                                <button type="button" class="btn btn-sm btn-info dropdown-toggle" data-toggle="dropdown"
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
                            <input id="goodsDate" placeholder="请选择日期区间" class="form-control">
                        </div>
                    </div>
                    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                    <div id="orderGoods" style="height: 100%"></div>
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
<script src="../static/js/user/order_statistics.js"></script>
</body>
</html>