<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>商品对比</title>
    <link href="../static/css/animate.css" rel="stylesheet">
    <link href="../static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link rel="stylesheet" href="../static/js/plugins/bootstrap-table/bootstrap-table.min.css">
    <link rel="stylesheet" href="../static/js/plugins/bootstrap-select/css/bootstrap-select.min.css">
    <link href="../static/js/plugins/layui/css/layui.css" rel="stylesheet">
    <link href="../static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="../static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="../static/css/user/goods_contrast.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="row">
    <div class="col-sm-12" id="searchBox">
        <div class="search_content">
            <form id="followupSearchForm" class="form-inline">
                <div class="row">
                    <div class="form-group col-xs-3">
                        <label class="col-xs-5 control-label text-right">时间区间(1)</label>
                        <div class="col-xs-7">
                            <input id="date1" placeholder="请选择日期区间"
                                   class="form-control full-width">
                        </div>
                    </div>
                    <div class="form-group  col-xs-3 ">
                        <label class="col-xs-5 control-label text-right">时间区间(2)</label>
                        <div class="col-xs-7">
                            <input id="date2"
                                   placeholder="请选择日期区间"
                                   class="form-control  full-width">
                        </div>
                    </div>
                    <div class="form-group  col-xs-4 ">
                        <label class="col-xs-5 control-label text-right">对比商品</label>
                        <div class="col-xs-7">
                            <select name="goods" data-style="btn-white" class="form-control selectpicker"
                                    data-live-search="true" data-size="5"
                                    multiple>
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-xs-1">
                        <button class="btn btn-warning" id="submit">P K</button>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>
<div class="center-wrap" style="margin:50px 10px 0 10px;">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="ibox float-e-margins">

                <div class="ibox-title text-center">

                </div>
            </div>
            <div class="ibox-content">
                <div class="row" id="pk">
                    <div style="text-align: center" id="declare">
                        说明
                    </div>
                    <div class="col-md-12" style="text-align: center;display: none" id="schedule">
                        <table class="layui-table" style="text-align: center">
                            <thead>
                            <tr>
                                <th style="text-align: center" rowspan="2">商品名称</th>
                                <th style="text-align: center" colspan="2" id="interval1">日期</th>
                                <th style="text-align: center" colspan="2" id="interval2">日期</th>
                                <th style="text-align: center" rowspan="2">比率</th>
                                <!--<th lay-data="{fixed: 'right', width: 160, align: 'center', toolbar: '#barDemo'}" rowspan="2">操作</th>-->
                            </tr>
                            <tr>
                                <th style="text-align: center">总金额</th>
                                <th style="text-align: center">总数量</th>
                                <th style="text-align: center">总金额</th>
                                <th style="text-align: center">总数量</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
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

<!--layui -->
<script src="../static/js/plugins/layui/layui.all.js"></script>

<!-- bootstrap select -->
<script src="../static/js/plugins/bootstrap-select/js/bootstrap-select.min.js"></script>
<script src="../static/js/plugins/bootstrap-select/js/i18n/defaults-zh_CN.min.js"></script>

<!-- 自定义js -->
<script src="../static/js/content.js?v=1.0.0"></script>
<script src="../static/js/user/goods_contrast.js"></script>
</body>
</html>
