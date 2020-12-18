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
        #comboTree > div > div{
            max-height: 380px;
            overflow-y: auto;
        }
        label{
            white-space:nowrap;
            text-overflow:ellipsis;
            overflow: hidden;
            margin-bottom: -5px;
        }
    </style>

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>添加商品</h5>
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


                        <div class="form-group">
                            <label class="col-sm-3 control-label">商品图片：</label>
                            <div class="col-sm-9">
                                <input id="goodsImage" name="goods_file" type="file" multiple class="file-loading"
                                       data-show-upload="false" data-show-caption="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">商品属性：</label>
                            <div class="col-sm-9">
                                <input type="checkbox" id="useSetAttr" class="js-switch"/> <b>启用商品属性设置</b>
                            </div>
                        </div>
                        <div class="form-group" id="attrBlock" style="display: none">
                            <label class="col-sm-3 control-label"></label>
                            <div class="col-sm-9">
                                <b>设置商品属性：&nbsp; &nbsp;</b>
                                <label id="goodsAttributes">
                                </label>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" type="button"
                                        data-target="#goodsAttributeModal" readonly name="addAttr"><span
                                        class="fa fa-plus"></span>添加属性
                                </button>
                                <br/>
                                <div id="goodsAttributeValues" style='display: none'></div>
                                <div><!--style="display: none"-->
                                    <input id="combination" type="button" class="btn btn-warning btn-xs" value="一键生成组合"
                                           name="combination" readonly/>

                                    <div class="ibox float-e-margins">
                                        <div class="ibox-title">
                                            <h3>具体商品详情</h3>
                                        </div>
                                        <div>
                                            <table class="layui-table" id="goodsInfo" lay-filter="goodsInfo"></table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                        <label class="col-sm-3 control-label"></label>
                        <div class="col-sm-9">
                            <input type="checkbox" class="js-switch" id="useNonstandard"/> <b>启用非标准属性</b>
                        </div>
                    </div>
                        <div class="form-group" id="nonstandard" style="display: none">
                            <label class="col-sm-3 control-label"></label>
                            <div class="col-sm-9">
                                <b>设置商品的非标准属性：&nbsp; &nbsp;</b>
                                <label id="nonstandardAttr"></label>&nbsp; &nbsp;
                                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal"
                                        data-target="#nonstandardAttrModal" readonly name="addAttr"><span class="fa fa-plus"></span> 添加非标属性
                                </button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label"></label>
                            <div class="col-sm-9">
                                <input type="checkbox" class="js-switch" id="assistBtn"/> <b>启用辅助属性</b>
                            </div>
                        </div>
                        <div class="form-group" id="assistBox" style="display: none">
                            <label class="col-sm-3 control-label"></label>
                            <div class="col-sm-9">
                                <b style="color: red">辅助属性不参与任何互动，仅为商品页面显示额外信息,本次添加商品辅助属性均相同（建议：不宜过多设置）！</b><br/>
                                <b>设置辅助属性：</b>
                                <label id="assists"></label>&nbsp; &nbsp;
                                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal"
                                        data-target="#assistModal" readonly name="addAttr"><span class="fa fa-plus"></span> 添加辅助属性
                                </button><br/>
                                <div id="assistAttrValues" style='display: none'></div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">库存预警：</label>
                            <div class="col-sm-9">
                                <input type="checkbox" class="js-switch" id="useStorage"/> <b>启用低库存预警</b>
                            </div>
                        </div>
                        <div class="form-group" id="storage" style="display: none">
                            <label class="col-sm-3 control-label"></label>
                            <div class="col-sm-3">
                                <b>货物库存预警线：&nbsp; &nbsp;</b>
                                <input class="form-control" type="text" aria-required="true"
                                       aria-invalid="true" name="stockWarning">
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
<!-- 添加属性模态框 -->
<div class="modal fade" tabindex="-1" role="dialog" id="goodsAttributeModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">添加属性</h4>
            </div>
            <div class="modal-body row">
                <div class="form-group">
                    <label class="col-sm-3 control-label">属性名称：</label>
                    <div class="col-sm-8">
                        <input name="goodsAttributesName" class="form-control" type="text"
                               aria-required="true"
                               aria-invalid="true" class="error">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <input type="button" class="btn btn-default " data-dismiss="modal" value="关闭"/>
                <button onclick="addGoodsAttr();" class="btn btn-primary">添加</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- 添加非标准属性模态框 -->
<div class="modal fade" tabindex="-1" role="dialog" id="nonstandardAttrModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">添加非标准属性</h4>
            </div>
            <div class="modal-body row">
                <div class="form-group">
                    <label class="col-sm-3 control-label">非标准属性名称：</label>
                    <div class="col-sm-8">
                        <input name="nonstandardAttrName" class="form-control" type="text"
                               aria-required="true"
                               aria-invalid="true" class="error">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <input type="button" class="btn btn-default " data-dismiss="modal" value="关闭"/>
                <button onclick="addNonstandardAttr();" class="btn btn-primary">添加</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div class="modal fade" tabindex="-1" role="dialog" id="assistModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">添加辅助属性</h4>
            </div>
            <div class="modal-body row">
                <div class="form-group">
                    <label class="col-sm-3 control-label">辅助属性名称：</label>
                    <div class="col-sm-8">
                        <input name="assistAttrName" class="form-control" type="text"
                               aria-required="true"
                               aria-invalid="true" class="error">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <input type="button" class="btn btn-default " data-dismiss="modal" value="关闭"/>
                <button onclick="addAssistAttr();" class="btn btn-primary">添加</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- 添加属性值模态框 -->
<div class="modal fade" id="attributeModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="attributeModalLabel">添加属性值</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="attrName" class="control-label">属性:</label>
                        <input type="text" class="form-control" id="attrName" readonly>
                        <input type="text" class="form-control" id="attr" readonly style="display: none">
                    </div>
                    <div class="form-group">
                        <label class="control-label">属性值:</label>
                        <input class="form-control" name="attributeValue">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" onclick="addAttrValue();" class="btn btn-primary">添加</button>
            </div>
        </div>
    </div>
</div>
<!--<input type="file" name='file'>
<script type="text/html" id="barDemo">
    <div style="width: 30px;height: 30px;background:#ffff00;" onclick="uploadImage(this)"></div>
</script>-->
<div>
    <!-- 全局js -->
    <script src="../static/js/jquery.min.js?v=2.1.4"></script>
    <script src="../static/js/bootstrap.min.js?v=3.3.6"></script>

    <!--layUI-->
    <script src="../static/js/plugins/layui/layui.all.js"></script>

    <!-- validate -->
    <script src="../static/js/plugins/validatetion/jquery.validate.js"></script>
    <script src="../static/js/plugins/validatetion/localization/messages_zh.min.js"></script>

    <!-- treeview -->
    <script src="../static/js/plugins/treeview/bootstrap-treeview.js"></script>
    <script src="../static/js/plugins/treeview/bootstrap-combotree.js"></script>

    <!-- iCheck -->
    <script src="../static/js/plugins/iCheck/icheck.min.js"></script>

    <!-- Switchery -->
    <script src="../static/js/plugins/switchery/switchery.js"></script>

    <!-- bootstrap-fileInput -->
    <script src="../static/js/plugins/bootstrap-fileInput/js/fileinput.min.js"></script>
    <script src="../static/js/plugins/bootstrap-fileInput/js/locales/zh.js"></script>

    <!-- 自定义js -->
    <script src="../static/js/content.js?v=1.0.0"></script>
    <script src="../static/js/user/goods.js"></script>
</div>
<!--<script>
   function uploadImage() {
       $("[name=file]").click();
   }

   $("[name=file]").change(function () {
       console.log($(this).val());
   })

</script>-->
</body>
</html>
