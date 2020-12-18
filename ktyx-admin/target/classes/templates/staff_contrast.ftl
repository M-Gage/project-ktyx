<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>跟进记录</title>
    <link href="../static/css/animate.css" rel="stylesheet">
    <link href="../static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="../static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="../static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="../static/js/plugins/layui/css/layui.css" rel="stylesheet">
    <link rel="stylesheet" href="../static/js/plugins/bootstrap-table/bootstrap-table.min.css">
    <link rel="stylesheet" href="../static/js/plugins/bootstrap-select/css/bootstrap-select.min.css">

    <link rel="stylesheet" type="text/css" href="../static/js/plugins/fullPage/jquery.fullPage.css">
    <!--<link href="../static/css/user/order-info.css" rel="stylesheet"/>-->
    <link href="../static/css/user/staff_contrast.css" rel="stylesheet"/>
<style>
h3,h4{font-weight: 600}
th{
    font-size: 10px;
}
</style>


</head>
<body class="gray-bg">
<div class="row">
    <div class="col-sm-12" id="searchBox">
        <div class="search_content">
            <form id="followupSearchForm" class="form-inline">
                <div class="row">
                    <div class="form-group col-xs-4">
                        <label class="col-xs-5 control-label text-right">选手一号</label>
                        <div class="col-xs-7">
                            <select name="staff" class="form-control selectpicker" data-live-search="true">
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group  col-xs-4 ">
                        <label class="col-xs-5 control-label text-right">选手二号</label>
                        <div class="col-xs-7">
                            <select name="staff" class="form-control selectpicker" data-live-search="true">
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-xs-1">
                        <button class="btn btn-primary">P K</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="row" id="pk" style="margin-top:50px;">
    <div id="introduce" style="text-align: center">
        说明
    </div>
    <div id="info" style="display: none">
        <button class="btn btn-danger btn-sm" style="float: right;position: fixed;z-index: 9999;left: 20px;top: 58px"
                id="staffStatistic1">查看员工的个人报表
        </button>
        <button class="btn btn-danger btn-sm" style="float: right;position: fixed;z-index: 9999;right: 20px;top: 58px"
                id="staffStatistic2">查看员工的个人报表
        </button>
        <div id="staffInfo">
            <div class="section ">
                <div class="center-wrap">
                    <div class="wrapper wrapper-content animated fadeInRight">
                        <div class="row">

                            <div class="ibox float-e-margins">
                                <div class="ibox-title text-center">
                                    <h3>职员总概</h3>
                                </div>
                            </div>

                            <div class="ibox-content">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="row">
                                            <div class="col-md-2"></div>
                                            <div class="col-md-8"
                                                 style="height: 40px;border-radius: 10px;text-align: center;border: grey 1px solid">
                                                <h2>职员<span class="beforeLastMonth"> </span>与<span
                                                        class="lastMonth"> </span>的数据对比</h2>
                                            </div>
                                            <div class="col-md-2"></div>
                                        </div>
                                        <div class="row" id="contrastBox1" style="padding: 30px 0 0 100px;">

                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="row">
                                            <div class="col-md-2"></div>
                                            <div class="col-md-8"
                                                 style="height: 40px;border-radius: 10px;text-align: center;border: grey 1px solid">
                                                <h2>职员<span class="beforeLastMonth"> </span>与<span
                                                        class="lastMonth"> </span>的数据对比</h2>
                                            </div>
                                            <div class="col-md-2"></div>
                                        </div>
                                        <div class="row" id="contrastBox2" style="padding: 30px 0 0 100px;">

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
                                <div class="ibox-title text-center">
                                    <h3>职员商品销售信息</h3>
                                </div>

                                <div class="ibox-content">

                                    <div class="row">
                                        <div class="col-md-6">
                                            <div id="orderGoods1"></div>
                                        </div>
                                        <div class="col-md-6">
                                            <div id="orderGoods2"></div>
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

-->
                                <div class="ibox-content" style="margin-top: -40px;">

                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="col-xs-1"></div>
                                            <div class="col-xs-10">
                                                <div class="row text-center">

                                                    <h3>客户一览表</h3>
                                                    <table class="layui-table text-center">
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
                                                        <tr id="month1">
                                                            <td>本月</td>
                                                        </tr>
                                                        <tr id="quarter1">
                                                            <td>本季</td>
                                                        </tr>
                                                        <tr id="year1">
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
                                                        <tr id="moneySchedule1">
                                                            <td>金额</td>
                                                        </tr>
                                                        </tbody>
                                                    </table>

                                                </div>
                                            </div>
                                            <div class="col-xs-1"></div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="col-xs-1"></div>
                                            <div class="col-xs-10">
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
                                                        <tr id="month2">
                                                            <td>本月</td>
                                                        </tr>
                                                        <tr id="quarter2">
                                                            <td>本季</td>
                                                        </tr>
                                                        <tr id="year2">
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
                                                        <tr id="moneySchedule2">
                                                            <td>金额</td>
                                                        </tr>
                                                        </tbody>
                                                    </table>

                                                </div>
                                            </div>
                                            <div class="col-xs-1"></div>
                                        </div>
                                    </div>

                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>


<!-- 全局js -->
<script src="../static/js/jquery.min.js?v=2.1.4"></script>
<script src="../static/js/bootstrap.min.js?v=3.3.6"></script>

<!-- bootstrap select -->
<script src="../static/js/plugins/bootstrap-select/js/bootstrap-select.min.js"></script>
<script src="../static/js/plugins/bootstrap-select/js/i18n/defaults-zh_CN.min.js"></script>
<script src="../static/js/plugins/fullPage/jquery.fullPage.js"></script>

<!-- echar2 -->
<script src="../static/js/plugins/echar2/echarts.js"></script>

<!--layui -->
<script src="../static/js/plugins/layui/layui.all.js"></script>

<!-- 自定义js -->
<script src="../static/js/content.js?v=1.0.0"></script>
<script src="../static/js/user/staff_select.js"></script>
<script src="../static/js/user/staff_contrast.js"></script>
</body>
</html>
