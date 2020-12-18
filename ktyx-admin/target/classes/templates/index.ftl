<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>客通云销 - 主页</title>
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->
    <link href="../static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="../static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="../static/css/animate.css" rel="stylesheet">
    <link href="../static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="../static/css/layui.css" rel="stylesheet">
    <style>
        .modal-dialog{
            margin-top: 200px;
        }
        .modal-header{
            text-align: center;
        }
    </style>
</head>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<li id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close">
            <i class="fa fa-times-circle"></i>
        </div>
        <li class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <span><img alt="image" class="img-circle" src="../static/img/profile_small.jpg"/></span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="clear">
                            <span class="block m-t-xs"><strong class="font-bold">${staff.staffName}</strong></span>
                            <span class="text-muted text-xs block">${staff.roleName}<b class="caret"></b></span>
                            </span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a href="#" data-toggle="modal" data-target="#myModal">修改密码</a></li>
                            <li><a href="#" data-toggle="modal" data-target="#userInfo">修改个人信息</a></li>
                        </ul>
                    </div>
                    <div class="logo-element">CRM</div>
                </li>
                <#if menu?exists && menu?size gt 0>
                    <#list menu as roleMenu>
                        <#if roleMenu.menuId?length == 4>
                            <li>
                                <a href="#">
                                    <i class="fa fa-${roleMenu.menuIcon}"></i>
                                    <span class="nav-label">${roleMenu.menuName}</span>
                                    <span class="fa arrow"></span>
                                </a>
                                <ul class="nav nav-second-level">
                                    <#list menu as childMenu>
                                        <#if childMenu.menuId?length != 4 && childMenu.menuId?substring(0,4) == roleMenu.menuId>
                                            <#if childMenu.menuId = '100602'>
                                                <li><a class="J_menuItem" href="${childMenu.menuUrl}" point="0">计划单期限综合统计</a></li>
                                                <li><a class="J_menuItem" href="${childMenu.menuUrl}" point="1">计划单期限统计排行</a></li>
                                                <li><a class="J_menuItem" href="${childMenu.menuUrl}" point="2">计划单期限货物排销</a></li>
                                            <#--<#elseif childMenu.menuId = '100702'>
                                                <li><a class="J_menuItem" href="${childMenu.menuUrl}" point="0">出库单期限综合统计</a></li>
                                                <li><a class="J_menuItem" href="${childMenu.menuUrl}" point="1">出库单期限统计排行</a></li>
                                                <li><a class="J_menuItem" href="${childMenu.menuUrl}" point="2">出库单期限货物排销</a></li>-->
                                            <#else>
                                                <li><a class="J_menuItem" href="${childMenu.menuUrl}">${childMenu.menuName}</a></li>
                                            </#if>
                                        </#if>
                                    </#list>
                                </ul>
                            </li>
                        </#if>
                    </#list>
                </#if>
            </ul>
        </li>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <ul class="nav navbar-top-links navbar-right">
                    <li class="dropdown hidden-xs">
                        <a class="right-sidebar-toggle" aria-expanded="false">
                            <i class="fa fa-tasks"></i> 主题
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="/home">首页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i></button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span></button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a></li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
                </ul>
            </div>
            <span id="logout" class="roll-nav roll-right J_tabExit exit"><i class="fa fa fa-sign-out"></i>退出</span>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="/home" frameborder="0" data-id="/home" seamless></iframe>
        </div>
        <div class="footer">
            <div class="pull-right">&copy; 2017-2018 <a href="#" target="_blank">R&D By lwh and mgz</a>
            </div>
        </div>
    </div>
    <!--右侧部分结束-->
    <!--右侧边栏开始-->
    <div id="right-sidebar">
        <div class="sidebar-container">
            <ul class="nav nav-tabs navs-3">
                <li class="active">
                    <a data-toggle="tab" href="#tab-1">
                        <i class="fa fa-gear"></i> 主题
                    </a>
                </li>
            </ul>
            <div class="tab-content">
                <div id="tab-1" class="tab-pane active">
                    <div class="skin-setttings">
                        <div class="title">皮肤选择</div>
                        <div class="setings-item default-skin nb">
                            <span class="skin-name ">
                             <a href="#" class="s-skin-0">
                                 默认皮肤
                             </a>
                            </span>
                        </div>
                        <div class="setings-item blue-skin nb">
                            <span class="skin-name ">
                            <a href="#" class="s-skin-1">
                                蓝色主题
                            </a>
                        </span>
                        </div>
                        <div class="setings-item yellow-skin nb">
                            <span class="skin-name ">
                            <a href="#" class="s-skin-3">
                                黄色/紫色主题
                            </a>
                        </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--右侧边栏结束-->
    <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                    </button>
                    <span class="modal-title">修改密码</span>
                </div>
                <div class="modal-body">
                    <form id="updatePwd">
                        <div class="form-group">
                            <input type="text" name="oldPwd" autocomplete="off" class="form-control" placeholder="旧密码">
                        </div>
                        <div class="form-group">
                            <input type="password" name="newPwd" id="newPwd" autocomplete="off" class="form-control" placeholder="新密码">
                        </div>
                        <div class="form-group">
                            <input type="password" name="repeatPwd" autocomplete="off" class="form-control" placeholder="重复密码">
                        </div>
                        <button type="submit" class="btn btn-block btn-primary">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="modal inmodal fade" id="userInfo" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                    </button>
                    <span class="modal-title">修改个人信息</span>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <input type="text" name="oldPwd" autocomplete="off" class="form-control" value="${staff.staffName}" placeholder="名称">
                        </div>
                        <div class="form-group">
                            <input type="text" name="newPwd" autocomplete="off" value="${staff.birthday?string('yyyy-MM-dd')}" class="form-control" placeholder="生日">
                        </div>
                        <div class="form-group">
                            <input type="text" name="repeatPwd" autocomplete="off" value="${staff.sex}" class="form-control"  placeholder="性别">
                        </div>
                        <button type="submit" class="btn btn-block btn-primary">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
<!-- 全局js -->
<script src="../static/js/jquery.min.js"></script>
<script src="../static/js/bootstrap.min.js"></script>
<script src="../static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="../static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!--layui -->
<script src="../static/js/plugins/layui/layui.all.js"></script>

<!-- validate -->
<script src="../static/js/plugins/validatetion/jquery.validate.js"></script>
<script src="../static/js/plugins/validatetion/localization/messages_zh.min.js"></script>

<!-- 第三方插件 -->
<script src="../static/js/plugins/pace/pace.min.js"></script>

<!-- 自定义js -->
<script src="../static/js/hplus.js?v=4.1.0"></script>
<script type="text/javascript" src="../static/js/contabs.js"></script>

<script>
    $("#updatePwd").validate({
        submitHandler: function () {
            $.ajax({
                url: "/staff/pwd",
                type: "put",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({
                    oldPwd: $("[name=oldPwd]").val(),
                    newPwd: $("[name=newPwd]").val()
                }),
                success: function (res) {
                    if (res.code === 200) {
                        $("#myModal").modal("hide");
                        $('#updatePwd')[0].reset();
                        layer.msg("修改成功，3秒后回到登录页面！请使用新密码重新登录!");
                        setTimeout(function(){
                            $("#logout").click();
                        },3000)
                    } else {
                        layer.msg(res.message);
                    }
                }
            })
        },
        rules: {
            oldPwd: {
                required: true,
                minlength: 6
            },
            newPwd: {
                required: true,
                minlength: 6
            },
            repeatPwd: {
                required: true,
                minlength: 6,
                equalTo: "#newPwd"
            },
        },
        messages: {
            oldPwd: {
                required: "请输入旧密码",
                minlength: "密码长度不能小于6位"
            },
            newPwd: {
                required: "请输入新密码",
                minlength: "密码长度不能小于6位"
            },
            repeatPwd: {
                required: "请输入重复密码",
                minlength: "密码长度不能小于6位",
                equalTo: "两次密码输入不一致"
            },
        }
    });
    $("#logout").click(function(){
        document.cookie = "staffId=''";
        $.ajax({
            url:"/staff/logout",
            type:"get",
            success:function (result) {
                if(result.code === 200){
                    window.location.href="/";
                }else{
                    layer.msg(result.message,{icon:2});
                }
            }
        });
        window.location.href="/login";
    })
</script>
</body>

</html>
