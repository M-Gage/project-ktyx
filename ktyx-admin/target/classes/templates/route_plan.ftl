<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>日程规划</title>

    <link href="../static/css/animate.css" rel="stylesheet">
    <link href="../static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="../static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="../static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="../static/js/plugins/layui/css/layui.css" rel="stylesheet">
    <link rel="stylesheet" href="../static/js/plugins/bootstrap-table/bootstrap-table.min.css">
    <link href='../static/js/plugins/fullcalendar/fullcalendar.min.css' rel='stylesheet'/>
    <link href='../static/js/plugins/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print'/>
    <link rel="stylesheet" href="../static/js/plugins/bootstrap-select/css/bootstrap-select.min.css">
    <style>
        body {
            padding: 0;
            font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
            font-size: 14px;
        }

        #wrap {
            margin: 0 auto;
        }

        .wrapper {
            margin-top: 35px;
        }

        #external-events h4 {
            font-size: 16px;
            margin-top: 0;
            padding-top: 1em;
        }

        #external-events .fc-event {
            margin: 10px 0;
            cursor: pointer;
        }

        #external-events p {
            margin: 1.5em 0;
            font-size: 11px;
            color: #666;
        }

        #external-events p input {
            margin: 0;
            vertical-align: middle;
        }

        #calendar {
            margin: 0 auto;
        }

        #searchBox > .search_content {
            width: 100%;
            height: 100%;
            background-color: #fff;
            border-bottom: 3px solid #f824f4;
        }

        #searchBox {
            position: fixed;
            left: 0;
            right: 0;
            padding: 0 20px;
            height: 50px;
            z-index: 20;
        }

        label {
            margin-top: 7px;
        }

        #staffForm {
            padding-top: 6px;
        }

        input::-webkit-input-placeholder {
            color: #c3c2b9;
            opacity: 1;
        }

        .dropdown-menu {
            max-height: 395px !important;
        }
    </style>
</head>
<body class="gray-bg">
<!-- 员工选择 -->
<div class="row">
    <div class="col-sm-12" id="searchBox">
        <div class="search_content">
            <form id="staffForm" class="form-inline">
                <div class="row">
                    <div class="form-group  col-xs-4 ">
                        <label class="col-xs-5 control-label text-right">业务员</label>
                        <div class="col-xs-7">
                            <select name="staff" data-style="btn-white" class="form-control"
                                    data-live-search="true">
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-xs-1">
                        <button type="button" class="btn btn-warning" id="my">我的</button>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>
<!-- 日程主界面 -->
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>路线规划</h5>
                </div>
                <div class="ibox-content">
                    <div id='wrap'>
                        <div id='calendar'></div>
                        <div style='clear:both'></div>
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

<!-- bootstrap Table -->
<script src="../static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="../static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

<!-- fullcalendar 日程 -->
<script src='../static/js/plugins/fullcalendar/lib/moment.min.js'></script>
<script src='../static/js/plugins/fullcalendar/fullcalendar.js'></script>
<script src='../static/js/plugins/fullcalendar/locale/zh-cn.js'></script>
<script src='../static/js/plugins/fullcalendar/lib/jquery-ui.min.js'></script>

<!-- validate -->
<script src="../static/js/plugins/validatetion/jquery.validate.js"></script>
<script src="../static/js/plugins/validatetion/localization/messages_zh.min.js"></script>

<!-- bootstrap select -->
<script src="../static/js/plugins/bootstrap-select/js/bootstrap-select.min.js"></script>
<script src="../static/js/plugins/bootstrap-select/js/i18n/defaults-zh_CN.min.js"></script>

<!-- 自定义js -->
<script src="../static/js/content.js?v=1.0.0"></script>
<script src="../static/js/user/staff_select.js"></script>

<script>
    $(function () {
        $("#wrap").css("width", $(window).width() - 70);
        $("#calendar").css("width", $(window).width() - 400);
        $("[name=staff]").selectpicker();
        loadData();
    });

    //页面加载数据完初始化日历
    function loadData() {
        $.ajax({
            url: "/route",
            type: "get",
            success: function (result) {
                if (result.code === 200) {
                    var routePLan = result.data, dataSource = [];
                    // print(routePlan);
                    for (var i = 0; i < routePLan.length; i++) {
                        var event = {
                            id: routePLan[i].routePlanId,
                            start: routePLan[i].routePlanDate,
                            title: routePLan[i].customerName,
                            color: new Date(routePLan[i].routePlanDate).getTime() < new Date().getTime() ? '#ffb00c' : '#10c233'
                        };
                        if (routePLan[i].status === 1 && new Date(routePLan[i].routePlanDate).getTime() < new Date().getTime()) {
                            event.color = '#ff1e26'
                        }
                        dataSource.push(event);
                    }
                    initFullCalendar(dataSource);
                } else {
                    layer.msg("未知错误，请与程序员联系");
                }
            }
        });
    }

    //初始化日程视图
    function initFullCalendar(event) {
        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay prev,next'
            },
            firstDay: 2,
            aspectRatio: 1.2,
            height: $(window).height() - 160,
            timeFormat: 'H:mm',
            axisFormat: 'H:mm',
            events: event,
            dragOpacity: 0.5,
            eventMouseover: function (event, jsEvent, view) {   //鼠标划过的事件
                print(event.id)
                var routePlan = routePlanDetail(event.id)
                        , str = "执行日期：" + routePlan.routePlanDate.substr(0, 10) +
                        "<br/>客户：" + routePlan.customerName +
                        "<br/>业务员：" + routePlan.staffName +
                        "<br/>是否完成：" + (routePlan.status === 1 ? "完成" : "未完成");

                layer.tips(str, this, {
                    tips: [1, event.color],
                    time: 0
                });
            }, eventMouseout: function (event, jsEvent, view) { //鼠标离开的事件
                layer.closeAll();
            }
        });
    }

    $("#my").click(function () {
        getData("${Session['staff'].staffId}");
    });


    $("[name=staff]").parent().click(function () {
        if (staffList("[name=staff]")) {
            var options = $("#staffForm").find("> div > div.form-group.col-xs-4 > div > div > div > ul > li");
            options.click(function () {
                var opt = $("[name=staff] > option")
                        , name = $(this).find("a > span.text").html();
                for (var i = 0; i < opt.length; i++) {
                    if ($(opt[i]).html() === name) {
                       getData($(opt[i]).val());
                    }
                }

            });
        }
    });

    function getData(staffId) {
        $.ajax({
            url: "/route",
            type: "get",
            data: {
                staffId:staffId
            },
            success: function (result) {
                if (result.code === 200) {
                    var routePLan = result.data, dataSource = [];
                    // print(routePlan);
                    for (var i = 0; i < routePLan.length; i++) {
                        var event = {
                            id: routePLan[i].routePlanId,
                            start: routePLan[i].routePlanDate,
                            title: routePLan[i].customerName,
                            color: new Date(routePLan[i].routePlanDate).getTime() < new Date().getTime() ? '#ffb00c' : '#10c233'
                        };
                        if (routePLan[i].status === 1 && new Date(routePLan[i].routePlanDate).getTime() < new Date().getTime()) {
                            event.color = '#ff1e26'
                        }
                        dataSource.push(event);
                    }
                    $("#calendar").fullCalendar("removeEvents")
                    $("#calendar").fullCalendar("addEventSource", dataSource);
                } else {
                    layer.msg("系统异常，员工路线规划加载失败",{icon:2});
                }
            }
        });

    }

    function routePlanDetail(id) {
        var routePlan = {};
        $.ajax({
            url: "/route/detail",
            type: "get",
            async: false,
            data: {
                routePlanId: id
            },
            success: function (result) {
                routePlan = result.data;
            }
        });
        return routePlan;
    }

</script>
</body>
</html>
