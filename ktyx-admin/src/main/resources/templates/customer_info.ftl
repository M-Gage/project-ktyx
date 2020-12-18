<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>客户信息</title>
    <link rel="stylesheet" type="text/css" href="../static/css/fullPage/jquery.fullPage.css">
    <link href="../static/css/animate.css" rel="stylesheet">
    <link href="../static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="../static/js/layui/css/layui.css" rel="stylesheet">
    <link href="../static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="../static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <style>
        th{
            width: 50%;
        }
        #map{
            height: 200px;
        }
        #lastFollowup > span,
        #lastOrder > span{
            cursor: pointer;
            color: #0e9aef;
        }
    </style>
</head>

<body class="gray-bg">

<div class="row">
    <div class="col-sm-3">
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>客户详情</h5>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-xs-12" id="staff_info">
                            <p>客户名称：<span id="customer_name"></span></p>
                            <p>客户等级：<span id="customer_level"></span></p>
                            <ul class="list-group" id="contact_info"></ul>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div id="map"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
     </div>
    <div id="statistics" class="col-xs-9">
        <div class="section ">
            <div class="center-wrap">
                <div class="wrapper wrapper-content animated fadeInRight">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <h5>总概</h5>
                                </div>
                                <div class="ibox-content">
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>最后一次下单</th>
                                            <th>最后一次跟进</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td id="lastOrder"></td>
                                            <td id="lastFollowup"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>平均十笔订单金额</th>
                                            <th>平均十笔订单商品数量</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td id="avgAmount"></td>
                                            <td id="avgQuantity"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>总金额同比上月</th>
                                            <th>总订单数量同比上月</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>上月：<span id="lastTotal"></span><br>上上月：<span id="beforeLastTotal"></span></td>
                                            <td>上月：<span id="lastCount"></span><br>上上月：<span id="beforeLastCount"></span></td>
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
        <div class="section ">
            <div class="center-wrap">
                <div class="wrapper wrapper-content animated fadeInRight">
                    <div class="row">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>商品种类统计数量排行</h5>
                            </div>
                            <div class="ibox-content">

                                <div id="main"></div>
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
    <script src="../static/js/layui/layui.js"></script>
    <!-- echar2 -->
    <script src="../static/js/plugins/echar2/echarts.js"></script>
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.4.3&key=85d5e0bd412ecfc92d29e3bdfc4b05e9&plugin=AMap.Geocoder"></script>
    <script src="../static/js/user/map.js"></script>
    <script src="../static/js/user/customer_Info.js"></script>
    <!-- 自定义js -->
    <script src="../static/js/content.js?v=1.0.0"></script>
</div>
</body>
</html>

