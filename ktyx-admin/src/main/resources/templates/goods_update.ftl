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
    <link href="../static/js/plugins/layui/css/layui.css" rel="stylesheet">
    <!--<link href="../static/js/layui/css/modules/layer/default/layer.css" rel="stylesheet">-->

    <link href="../static/js/plugins/bootstrap-fileInput/css/fileinput.min.css" rel="stylesheet"/>
    <link href="../static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="../static/js/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="../static/js/plugins/switchery/switchery.min.css" rel="stylesheet">

    <style>

    </style>

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="addGoodsForm" method="post" action="/goods">

                        <div class="form-group">
                            <label class="col-sm-3 control-label">分类：</label>
                            <div class="col-sm-9 form-group">

                                <div class="col-sm-6" id="comboTree"></div>

                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">商品编号：</label>
                            <div class="col-sm-9">
                                <input name="goodsNo" class="form-control" type="text" aria-required="true"
                                       aria-invalid="true" class="error">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">商品名称：</label>
                            <div class="col-sm-9">
                                <input name="goodsName" class="form-control" type="text" aria-required="true"
                                       aria-invalid="true" class="error">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-9">
                                <input name="remark" class="form-control" type="text" aria-required="true"
                                       aria-invalid="true" class="error">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">库存数 ：</label>
                            <div class="col-sm-9">
                                <input name="stock" class="form-control" type="text" aria-required="true"
                                       aria-invalid="true" class="error">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">商品条形码：</label>
                            <div class="col-sm-9">
                                <input name="barcode" class="form-control" type="text" aria-required="true"
                                       aria-invalid="true" class="error">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">商品单位：</label>
                            <div class="col-sm-9">
                                <input name="unit" class="form-control" type="text" aria-required="true"
                                       aria-invalid="true" class="error">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">建议零售价：</label>
                            <div class="col-sm-9">
                                <input name="rrp" class="form-control" type="text" aria-required="true"
                                       aria-invalid="true" class="error">
                            </div>
                        </div>

                        <div class="form-group" >
                            <label class="col-sm-3 control-label">库存预警:</label>
                            <div class="col-sm-3">
                                <input class="form-control" type="text" aria-required="true"
                                       aria-invalid="true" name="stockWarning">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">商品图片：</label>
                            <div class="col-sm-9">
                                <input id="goodsImage" name="goods_file" type="file" multiple class="file-loading"
                                       data-show-upload="false" data-show-caption="true">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-6 col-sm-offset-3">
                                <button class="btn btn-primary" type="submit">提交</button>
                                <input class="btn btn-danger" id="res" type="reset" value="重置">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<div>
    <!-- 全局js -->
    <script src="../static/js/jquery.min.js?v=2.1.4"></script>
    <script src="../static/js/bootstrap.min.js?v=3.3.6"></script>

    <!--layUI-->
    <script src="../static/js/plugins/layui/layui.all.js"></script>

    <!-- validate -->
    <script src="../static/js/plugins/validatetion/jquery.validate.js"></script>
    <script src="../static/js/plugins/validatetion/localization/messages_zh.min.js"></script>

    <!-- iCheck -->
    <script src="../static/js/plugins/iCheck/icheck.min.js"></script>

    <!-- bootstrap-table -->
    <script src="../static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="../static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

    <!-- treeview -->
    <script src="../static/js/plugins/treeview/bootstrap-treeview.js"></script>
    <script src="../static/js/plugins/treeview/bootstrap-combotree.js"></script>

    <!-- Switchery -->
    <script src="../static/js/plugins/switchery/switchery.js"></script>

    <!-- bootstrap-fileInput -->
    <script src="../static/js/plugins/bootstrap-fileInput/js/fileinput.min.js"></script>
    <script src="../static/js/plugins/bootstrap-fileInput/js/locales/zh.js"></script>

    <!-- 自定义js -->
    <script src="../static/js/content.js?v=1.0.0"></script>
    <script src="../static/js/user/goods_update.js"></script>
</div>
</body>
</html>
