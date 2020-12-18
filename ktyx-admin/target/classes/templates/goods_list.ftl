<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">


    <title>添加商品</title>

    <link href="../static/css/animate.css" rel="stylesheet">
    <link href="../static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="../static/css/style.css?v=4.1.0" rel="stylesheet">

    <link href="../static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="../static/js/plugins/switchery/switchery.min.css" rel="stylesheet"/>
    <link href="../static/js/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="panel-body" style="padding-bottom:0px;">

                        <div id="toolbar" class="btn-group">
                            <div class="row">
                                <div class="col-md-7">

                                        <div class="form-group">
                                            <div class="col-sm-3">
                                                <label class="text-left"
                                                       for="goodsCondition">搜索商品:</label>
                                            </div>
                                            <div class="col-sm-7">
                                                <input type="text" class="form-control full-width" placeholder="输入商品名称、编号、条形码或助记码" id="goodsCondition">
                                            </div>
                                            <div class="col-sm-2">
                                                <button type="button" id="btn_query"
                                                        class="btn btn-primary">查询
                                                </button>
                                            </div>
                                           <!-- <div class="col-sm-1 col-sm-offset-1">
                                                <button type="button" id="btn_sync"
                                                        class="btn btn-primary">同步
                                                </button>
                                            </div>-->
                                        </div>
                                </div>
                                <div class="col-md-5">
                                    <div class="col-sm-3 col-sm-offset-1">
                                        <button type="button"
                                                class="btn btn-primary" id="upload">导入
                                        </button>
                                        <input type="file" id="fileInput" style="display: none">
                                    </div>
                                    <div class="col-sm-3 col-sm-offset-1">
                                        <a href="/file/export/goods" class="btn btn-primary">导出</a>
                                    </div>
                                    <div class="col-sm-3 col-sm-offset-1">
                                        <a href="/file/downFile/goods.xls" class="btn btn-primary">下载模板</a>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <table id="goodsList"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div>
    <!-- 全局js -->
    <script src="../static/js/jquery.min.js?v=2.1.4"></script>
    <script src="../static/js/bootstrap.min.js?v=3.3.6"></script>

    <!-- bootstrap-table -->
    <script src="../static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="../static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

    <!-- validate -->
    <script src="../static/js/plugins/validatetion/jquery.validate.min.js"></script>
    <script src="../static/js/plugins/validatetion/localization/messages_zh.min.js"></script>


    <!--layUI-->
    <script src="../static/js/plugins/layui/layui.all.js"></script>

    <!-- 自定义js -->
    <script src="../static/js/content.js?v=1.0.0"></script>
    <script src="../static/js/user/goods_list.js"></script>
</div>
</body>
</html>
