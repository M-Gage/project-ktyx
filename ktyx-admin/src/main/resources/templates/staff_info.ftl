<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>职员信息</title>
    <link rel="stylesheet" type="text/css" href="../static/js/plugins/fullPage/jquery.fullPage.css">
    <link href="../static/css/animate.css" rel="stylesheet">
    <link href="../static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="../static/js/plugins/layui/css/layui.css" rel="stylesheet">
    <link href="../static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="../static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="../static/css/user/order-info.css" rel="stylesheet"/>
    <style>
        .contrast {
            height: 100%;
            text-align: center;
            /*border: darkred 2px solid;*/
            border-radius: 10px;
            padding-top: 30px;
            padding-bottom: 30px
        }

        ._interval {
            position: absolute;
            float: right;
            z-index: 9999;
            right: 210px;
        }
    </style>
</head>

<body class="gray-bg">
<#--<button class="btn btn-danger btn-sm" style="float: right;position: fixed;z-index: 9999;right: 5px;top: 8px"
        id="staffStatistic">查看该员工的个人报表
</button>-->
<div id="staffInfo">

    <div class="section ">
        <div class="center-wrap">
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="row">

                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>职员总概</h5>
                        </div>
                    </div>

                    <div class="ibox-content">
                        <div class="row" id="contrastDiv">
                            <div class="row">
                                <div class="col-md-2"></div>
                                <div class="col-md-8"
                                     style="height: 40px;border-radius: 10px;text-align: center;border: grey 1px solid">
                                    <h2>职员<span class="beforeLastMonth"> </span>与<span class="lastMonth"> </span>的数据对比
                                    </h2>
                                </div>
                                <div class="col-md-2"></div>
                            </div>
                            <div class="row" id="contrastBox"></div>
                        </div>
                        <div class="row" id="tables">
                            <div class="col-xs-12">
                                <div class="row" style="text-align: center">

                                    <h3>客户一览表</h3>
                                    <table class="layui-table" style="text-align: center">
                                        <thead>
                                        <tr>
                                            <th></th>
                                            <th style="text-align: center">新开客户数</th>
                                            <th style="text-align: center">已成交客户数</th>
                                            <th style="text-align: center">下单活跃客户数</th>
                                            <th style="text-align: center">跟进活跃客户数</th>
                                            <!--<th style="text-align: center">已停滞客户数</th>-->
                                            <th style="text-align: center">日程数量</th>
                                            <th style="text-align: center">跟进数量</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr id="month">
                                            <td>本月</td>
                                        </tr>
                                        <tr id="quarter">
                                            <td>本季</td>
                                        </tr>
                                        <tr id="year">
                                            <td>本年</td>
                                        </tr>
                                        </tbody>
                                    </table>

                                </div>
                                <div class="row" style="text-align: center">
                                    <h3>业绩一览表</h3>
                                    <table class="layui-table" style="text-align: center">
                                        <thead>
                                        <tr style="text-align: center">
                                            <th></th>
                                            <th style="text-align: center">本月</th>
                                            <th style="text-align: center">本季</th>
                                            <th style="text-align: center">本年</th>
                                            <th style="text-align: center">历史</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr id="moneySchedule">
                                            <td>金额</td>
                                        </tr>
                                        </tbody>
                                    </table>

                                </div>
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
                        <div class="ibox-title">
                            <h5>职员商品销售信息</h5>
                        </div>

                        <div class="ibox-content">

                            <div class="row">
                                <div id="orderGoods"></div>
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

                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>职员画像</h5>
                    </div>

                    <div class="ibox-content">
                        <div class="row _interval">
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
                                <input id="amountDate" placeholder="请选择日期区间" class="form-control">
                            </div>
                        </div>
                        <div class="row" id="staffAmount"></div>
                        <div class="row" id="newCustomer"></div>
                        <div class="row" id="newFollow"></div>
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
    <script src="../static/js/user/staff_info.js"></script>
</div>


</body>
</html>
