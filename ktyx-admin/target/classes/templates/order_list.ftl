<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <title>订单查询</title>
    <!-- 全局css -->
    <link href="../static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="../static/css/animate.css" rel="stylesheet">
    <link href="../static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="../static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="../static/js/plugins/layui/css/layui.css" rel="stylesheet">

    <!-- bootstrap 插件css -->
    <link href="../static/js/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet"/>
    <link rel="stylesheet" href="../static/js/plugins/bootstrap-select/css/bootstrap-select.min.css">

    <!-- 自定义css -->
    <link href="../static/css/user/order.css" rel="stylesheet"/>


</head>

<body class="gray-bg">
<div class="box">
    <div class="row">
        <form id="formSearch" class="form-inline">

            <div class="col-xs-3 form-group ">
                <div class="row ">
                    <div class="col-xs-3 text-right">
                        <label for="staffInfo">业务员:</label>
                    </div>
                    <div class="col-xs-9">
                        <select name="staff" data-style="btn-white" id="staffInfo" class="form-control selectpicker"
                                data-live-search="true" style="max-height: 395px !important;">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="col-xs-3 form-group ">
                <div class="row">
                    <div class="col-xs-3 text-right">
                        <label for="customerInfo">客户:</label>
                    </div>
                    <div class="col-xs-9">
                        <select name="customer" data-style="btn-white" id="customerInfo"
                                class="form-control selectpicker"
                                data-live-search="true" style="max-height: 395px !important;">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 form-group ">
                <div id="orderInfo">
                    <div class="menu pull-right">
                        <div class="row">
                            <div class="col-sm-4">
                                <a id="menu_btn" class="btn btn-block btn-info">订单信息
                                    <span class="caret"></span>
                                </a>
                            </div>
                            <div class="col-sm-4 text-center">
                            </div>
                            <div class="col-sm-4">
                                <!--<button type="button" id="btn_sync" class="btn btn-info">同步</button>-->
                                <button type="button" name="btn_query" class="btn btn-primary">查询</button>
                            </div>
                        </div>
                        <div class="menu-content">
                            <ul class="list-group">
                                <li class="list-group-item">
                                    <div class="row">
                                        <label class="col-sm-3 text-center">时间区间:</label>
                                        <div class="col-sm-9">
                                            <input name="dateInterval" id="date"
                                                   placeholder="请选择日期区间"
                                                   class="form-control  full-width">
                                        </div>
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="row">
                                        <label class="col-sm-3 text-center">商品分类:</label>
                                        <div class="col-sm-9 dropdown">
                                            <button class="btn btn-white btn-block dropdown-toggle" type="button"
                                                    id="typeTree" data-toggle="dropdown">
                                                <span class="order_type_tree_title">请选择分类</span>
                                                <span class="caret"></span>
                                            </button>
                                            <div class="dropdown-menu tree_content" style="left: 15px;right: 15px">
                                                <div id="order_type_tree"></div>
                                            </div>
                                            <input type="hidden" name="type">
                                        </div>
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="row">
                                        <label class="col-sm-3 text-center">部门选择:</label>
                                        <div class="col-sm-9 dropdown">
                                            <button class="btn btn-white btn-block dropdown-toggle" type="button"
                                                    id="deptTree" data-toggle="dropdown">
                                                <span class="order_dept_tree_title">请选择部门</span>
                                                <input type="hidden" id="userDeptID"
                                                       value='${Session["staff"].deptId}'>
                                                <span class="caret"></span>
                                            </button>
                                            <div class="dropdown-menu tree_content" style="left: 15px;right: 15px">
                                                <div id="order_dept_tree"></div>
                                            </div>
                                            <input type="hidden" name="dept">
                                        </div>
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="row">
                                        <label class="col-sm-3 text-center">客户级别:</label>
                                        <div class="col-sm-9">
                                            <select name="level"
                                                    class="form-control full-width ">
                                                <option value="" class=""><== 请选择 ==>
                                                </option>
                                                <option value="A级" class="">A级</option>
                                                <option value="B级" class="">B级</option>
                                                <option value="C级" class="">C级</option>
                                                <option value="D级" class="">D级</option>
                                            </select>
                                        </div>
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="row">
                                        <label class="col-sm-3 text-center">订单状态:</label>
                                        <div class="col-sm-9">
                                            <select name="orderStatus"
                                                    class="form-control full-width ">
                                                <option value="" class=""><== 默认全部 ==>
                                                </option>
                                                <option value="0" class="">未处理</option>
                                                <option value="1" class="">已处理</option>
                                                <option value="2" class="">审核中</option>
                                                <option value="3" class="">作废</option>
                                            </select>
                                        </div>
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <input name="condition"
                                                   placeholder="输入模糊订单号或订单地址"
                                                   class="form-control  full-width">
                                        </div>
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <button type="button" name="btn_reset"
                                                    class="btn btn-danger full-width">
                                                重置
                                            </button>
                                        </div>
                                        <div class="col-md-8">
                                            <input name="btn_query"
                                                   class="btn btn-primary full-width" readonly value="查询"/>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content ibox-content-order">
                    <div class="row">
                        <table id="orderList"></table>
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
    <script src="../static/js/plugins/layui/layui.all.js?v2.2.45"></script>

<#--<script src="../static/js/layui/lay/modules/laydate.js"></script>-->

    <!-- bootstrap-table -->
    <script src="../static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="../static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

    <!-- validate -->
    <script src="../static/js/plugins/validatetion/jquery.validate.min.js"></script>
    <script src="../static/js/plugins/validatetion/localization/messages_zh.min.js"></script>


    <!-- bootstrap select -->
    <script src="../static/js/plugins/bootstrap-select/js/bootstrap-select.min.js"></script>
    <script src="../static/js/plugins/bootstrap-select/js/i18n/defaults-zh_CN.min.js"></script>

    <!-- iCheck -->
    <script src="../static/js/plugins/iCheck/icheck.min.js"></script>

    <!-- treeview -->
    <script src="../static/js/plugins/treeview/bootstrap-treeview.js"></script>
    <script src="../static/js/plugins/treeview/bootstrap-combotree.js"></script>


    <!-- 自定义js -->
    <script src="../static/js/user/deptTree.js"></script>
    <script src="../static/js/user/staff_select.js"></script>
    <script src="../static/js/user/type_select.js"></script>
    <script src="../static/js/user/customer_select.js"></script>
    <script src="../static/js/content.js?v=1.0.0"></script>
    <script src="../static/js/user/order.js"></script>
</div>

</body>
</html>
