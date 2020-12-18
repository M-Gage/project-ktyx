<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>日程管理</title>

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

        #external-events {
            float: left;
            width: 150px;
            padding: 0 10px;
            border: 1px solid #ccc;
            background: #eee;
            text-align: left;
        }

        .externalBox {
            float: left;
            width: 130px;
            height: 80px;
            padding: 10px 20px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 3px;
            background: #3b87ad;
            text-align: center;
            z-index: 9999;
        }

        .externalGap {
            height: 10px;
            width: 150px;
            border-top: 1px solid #ccc;
            border-bottom: 1px solid #ccc;
            margin-left: -11px;
            background: white;
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
            float: right;
            margin-right: 100px;
        }

        .dropdown-menu {
            max-height: 395px !important;
        }

        #searchBox > .search_content {
            width: 100%;
            height: 100%;
            background-color: #fff;
            border-bottom: 3px solid #f82c2b;
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
                            <select name="staff" data-style="btn-white" class="form-control selectpicker"
                                    data-live-search="true" style="max-height: 395px !important;"
                                    multiple>
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <!-- <div class="form-group col-xs-1">
                         <button class="btn btn-warning" id="clear">清 空</button>
                     </div>-->
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
                    <h5>日程管理</h5>
                </div>
                <div class="ibox-content">
                    <div id='wrap'>
                        <div id='external-events'>
                            <h4>可拖拽日程</h4>
                            <div id="events">

                            </div>
                            <button class='fc-event full-width' id="addEvent"><span class="fa fa-plus"></span></button>
                            <p>
                                <input type='checkbox' id='drop-remove'/>
                                <label for='drop-remove'>拖拽同时删除</label>
                            </p>
                            <div class="externalGap"></div>
                            <div class="externalBox" id="trash">
                                <span class="fa fa-trash fa-4x fa-inverse"></span>
                            </div>
                        </div>

                        <div id='calendar'></div>
                        <div style='clear:both'></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 添加日程 -->
<div class="modal fade" data-backdrop="static" id="scheduleModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close unUse" data-dismiss="modal">&times;</button>
                <h5>添加日程</h5>
            </div>
            <form id="scheduleForm">
                <div class="modal-body">

                    <div class="form-group">
                        <div class="row">
                            <div class="col-sm-6">
                                <label class="control-label">起始时间<span class="xing">*</span></label>
                                <input name="startDate" type="text" class="form-control "
                                       autocomplete="off">
                                <input name="startYMD" type="text" style="display:none;">
                            </div>
                            <div class="col-sm-6">
                                <label class="control-label">结束时间</label>
                                <input name="endDate" type="text" class="form-control scheduleDate"
                                       autocomplete="off">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-sm-6">
                                <label class="control-label">相关客户<span class="xing">*</span></label>
                                <select name="customer" class="form-control selectpicker" data-style="btn-white"
                                        data-live-search="true">
                                    <option value="">请选择</option>
                                </select>
                            </div>
                            <div class="col-sm-6">
                                <label class="control-label">提醒时间<span class="xing">*</span></label>
                                <input name="alarmDate" type="text" class="form-control scheduleDate"
                                       autocomplete="off">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-sm-12">
                                <label class="control-label">内容<span class="xing">*</span></label>
                                <textarea name="description" class="form-control " autocomplete="off"></textarea>
                            </div>

                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <input class="btn btn-danger unUse" type="button" data-dismiss="modal" readonly value="关闭">
                    <input class="btn btn-success" type="submit" readonly value="提交">
                </div>
            </form>
        </div>
    </div>
</div>
<!-- 更改日程 -->
<div class="modal fade" id="updateScheduleModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h5>修改日程</h5>
            </div>
            <form id="updateScheduleForm">
                <div class="modal-body">

                    <div class="form-group">
                        <div class="row">
                            <div class="col-sm-6">
                                <label class="control-label">起始时间<span class="xing">*</span></label>
                                <input name="newStartTime" type="text" class="form-control scheduleDate"
                                       autocomplete="off">
                                <input name="scheduleId" style="display: none">
                            </div>
                            <div class="col-sm-6">
                                <label class="control-label">结束时间</label>
                                <input name="newEndTime" type="text" class="form-control scheduleDate"
                                       autocomplete="off">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-sm-6">
                                <label class="control-label">相关客户<span class="xing">*</span></label>
                                <select name="newCustomer" class="form-control selectpicker" data-style="btn-white"
                                        data-live-search="true">
                                </select>
                            </div>
                            <div class="col-sm-6">
                                <label class="control-label">提醒时间<span class="xing">*</span></label>
                                <input name="newAlarmTime" type="text" class="form-control scheduleDate"
                                       autocomplete="off">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-sm-12">
                                <label class="control-label">内容<span class="xing">*</span></span></label>
                                <textarea name="newDescription" class="form-control " autocomplete="off"></textarea>
                            </div>

                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button class="btn btn-warning" type="button" data-dismiss="modal">关闭</button>
                    <button class="btn btn-success" type="submit">提交</button>
                </div>
            </form>
        </div>
    </div>

</div>


<!-- 全局js -->
<script src="../static/js/jquery.min.js?v=2.1.4"></script>
<script src="../static/js/bootstrap.min.js?v=3.3.6"></script>

<!--layui -->
<script src="../static/js/plugins/layui/layui.all.js"></script>

<!-- jquery form -->
<#--<script src="../static/js/jquery.form.min.js"></script>-->

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
<script src="../static/js/user/customer_select.js"></script>
<script src="../static/js/user/staff_select.js"></script>

<script>

    $(function () {
        $("#wrap").css("width", $(window).width() - 50);
        $("#calendar").css("width", $(window).width() - 400);
        loadData();
        loadEventList();
        // initFullCalendar();
        lay(".scheduleDate").each(function () {
            layui.laydate.render({
                elem: this
                , type: "datetime"
            });
        });
        layui.laydate.render({
            elem: "[name=startDate]"
            , type: "time"
        });

        $("[name=customer]").selectpicker();
        $("[name=staff]").selectpicker();

        customerList("[name=customer]");
        customerList("[name=newCustomer ]")
    });
    $("[name=staff]").parent().click(function () {
        if (staffList("[name=staff]")) {
            var options = $("#staffForm").find("> div > div.form-group.col-xs-4 > div > div > div > ul > li")
                    , myName = "${Session['staff'].staffName}";
            for (var i = 0; i < options.length; i++) {
                if ($(options[i]).find("a > span.text").html() === myName) {
                    $(options[i]).addClass("selected");
                }
            }
            options.click(function () {
                var flag = $(this).attr("class") === "selected"
                        , opt = $("[name=staff] > option")
                        , name = $(this).find("a > span.text").html();
                for (var i = 0; i < opt.length; i++) {
                    if ($(opt[i]).html() === name) {
                        $.ajax({
                            url: "/schedule/list",
                            type: "get",
                            data: {
                                staffId: $(opt[i]).val()
                            },
                            success: function (result) {
                                if (result.code === 200) {
                                    var schedule = result.data, dataSource = [];
                                    for (var i = 0; i < schedule.length; i++) {
                                        if (schedule[i].customerName !== undefined) {
                                            if (flag) {
                                                $("#calendar").fullCalendar("removeEvents", schedule[i].scheduleId)

                                            } else {
                                                var event = {
                                                    id: schedule[i].scheduleId,
                                                    title: schedule[i].customerName,
                                                    start: schedule[i].startTime,
                                                    end: schedule[i].endTime,
                                                    color: new Date(schedule[i].endTime).getTime() < new Date().getTime() ? '#ff3847' : '#25ff30'
                                                };
                                                dataSource.push(event);
                                            }
                                        }
                                    }

                                    if (!flag) $("#calendar").fullCalendar("addEventSource", dataSource);

                                } else {
                                    layer.msg("未知错误，请与程序员联系");
                                }
                            }
                        });
                        return;
                    }
                }

            });
        }
    });

    //页面加载数据完初始化日历
    function loadData() {
        $.ajax({
            url: "/schedule/list",
            type: "get",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result.code === 200) {
                    var schedule = result.data, dataSource = [];
                    dataIsNull(result);
                    for (var i = 0; i < schedule.length; i++) {
                        if (schedule[i].customerName !== undefined) {
                            var event = {
                                id: schedule[i].scheduleId,
                                title: schedule[i].customerName,
                                start: schedule[i].startTime,
                                end: schedule[i].endTime,
                                color: new Date(schedule[i].endTime).getTime() < new Date().getTime() ? '#ff3847' : '#25ff30'
                            };
                            dataSource.push(event);
                        }
                    }
                    initFullCalendar(dataSource);
                } else {
                    layer.msg("未知错误，请与程序员联系");
                }
            }
        });
    }

    //初始化日程视图
    function initFullCalendar(events) {
        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay prev,next'
            },
            editable: true,                                     //可以拖动
            firstDay: 2,
            aspectRatio: 1.2,
            height: $(window).height() - 150,
            timeFormat: 'H:mm',
            axisFormat: 'H:mm',
            events: events,
            dragOpacity: 0.5,
            selectable: true,
            droppable: true,                                    // 是否允许日程被拖进日历
            drop: function (date, jsEvent, ui) {

                if (new Date(date.format('YYYY-MM-DD')).getTime() < new Date().getTime()) {
                    $('#calendar').fullCalendar('removeEvents', function (clientEvents) {
                        console.log(clientEvents);
                        if (clientEvents.id === undefined) {
                            return clientEvents;
                        }
                    });
                    layer.msg("开始时间不对");
                    return;
                }
                var staff = $("[name=staff]");
                if (staff.val() !== null && staff.val().length > 0) {
                    $('#calendar').fullCalendar('removeEvents', function (clientEvents) {
                        console.log(clientEvents);
                        if (clientEvents.id === undefined) {
                            return clientEvents;
                        }
                    });
                    layer.msg("给其他员工添加日程正在研发中…………");
                    return;
                }

                var _this = $(this), event = _this.html();

                $("[name=startYMD]").val(date.format('YYYY-MM-DD'));
                $("[name=startDate]").val(date.format('HH:mm:ss'));
                $("[name=description]").val(event);
                $("#scheduleModal").modal('show');

                $(".unUse").click(function () {
                    $('#calendar').fullCalendar('removeEvents', function (clientEvents) {
                        console.log(clientEvents);
                        if (clientEvents.id === undefined) {
                            return clientEvents;
                        }
                    });
                });
                if ($('#drop-remove').is(':checked')) {         //拖动并删除是否勾选
                    _this.remove();                           //勾选的话删除被拖动的日程
                    $.ajax({
                        type: "delete",
                        url: "/schedule/event/" + event,
                        success: function (result) {
                            if (result.code !== 200) {
                                layer.msg("便捷日程删除失败，请刷新页面", {icon: 2})
                            }
                        }
                    });
                }
            },
            dayClick: function (date, allDay, jsEvent, view) {  //当单击日历中的某一天时，触发callback
                var staff = $("[name=staff]");
                if (staff.val() !== null && staff.val().length > 0) {
                    layer.msg("给其他员工添加日程正在研发中…………");
                    return;
                }
                date = new Date(date);
                layui.laydate.render({
                    elem: "[name=startDate]"
                    , type: "datetime"
                });
                $("#scheduleModal").modal('show');
                $("[name=startYMD]").val(date.getFullYear() + '-' + ((date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1))) + '-' + (date.getDate() > 9 ? date.getDate() : ('0' + date.getDate())));
                $("[name=startDate]").val(date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds())
            },
            eventClick: function (event, jsEvent, view) {       //当点击日历中的某一日程（事件）时，触发此操作
                var schedule = scheduleDetail(event.id);
                $("[name=newStartTime]").val(schedule.startTime);
                $("[name=scheduleId]").val(event.id);
                $("[name=newEndTime]").val(schedule.endTime);
                $("#updateScheduleForm").find("> div.modal-body > div:nth-child(2) > div > div:nth-child(1) > div > button > span.filter-option.pull-left").html(schedule.customerName);
                $("[name=newAlarmTime]").val((schedule.remindTime === undefined ? "" : schedule.remindTime));
                $("[name=newDescription]").val(schedule.content);
                $("#updateScheduleModal").modal('show');
            },
            eventResize: function (event, dayDelta, revertFunc) {
                if (dayDelta._days !== 0 || dayDelta._milliseconds !== 0) {
                    updateSchedule(event.id, event.start.format('YYYY-MM-DD HH:mm:ss'),
                            event.end.format('YYYY-MM-DD HH:mm:ss'), null, null, null)
                } else {
                    revertFunc();
                }
            },
            eventDragStart: function (event) {
                var d = $('#calendar').fullCalendar('clientEvents'), deleConfirm;
                $("body").mousemove(function (even) {
                    var obj = document.getElementById("trash"), x = GetObjPos(obj)['x'], y = GetObjPos(obj)['y'],
                            cx = even.clientX, cy = even.clientY, calendar = $('#calendar');

                    if ((x < cx && cx < (x + 130)) && (y < cy && cy < (y + 80))) {
                        if (deleConfirm === undefined) {
                            calendar.fullCalendar('removeEvents', event.id);
                            deleConfirm = layer.confirm('您确定删除该日程吗?', function () {
                                        $.ajax({
                                            type: "delete",
                                            url: "/schedule/" + event.id,
                                            success: function (result) {
                                                if (result.code === 200) {
                                                    // layer.closeAll();
                                                    layer.msg("日程已删除", {time: 2000});
                                                } else {
                                                    layer.msg("未知错误，请与程序员联系")
                                                }
                                            }
                                        });
                                    },
                                    function () {
                                        calendar.fullCalendar('removeEvents')
                                        calendar.fullCalendar('addEventSource', d);
                                    });
                        }
                    }
                });
            },
            eventDrop: function (event, dayDelta, revertFunc) {
                if (dayDelta._days !== 0 || dayDelta._milliseconds !== 0) {
                    updateSchedule(event.id, event.start.format('YYYY-MM-DD HH:mm:ss'),
                            event.end === null ? event.start.format('YYYY-MM-DD HH:mm:ss') : event.end.format('YYYY-MM-DD HH:mm:ss'),
                            null, null, null)
                } else {
                    revertFunc();
                }
            },
            eventMouseover: function (event, jsEvent, view) {   //鼠标划过的事件
                var schedule = scheduleDetail(event.id)
                        , str = "开始时间：" + schedule.startTime +
                        "<br/>结束时间：" + schedule.endTime +
                        "<br/>客户：" + schedule.customerName +
                        "<br/>内容：" + schedule.content +
                        "<br/>业务员：" + schedule.staffName +
                        "<br/>提醒时间：" + (schedule.remindTime === undefined ? "无" : schedule.remindTime);

                layer.tips(str, this, {
                    tips: [1, event.color],
                    time: 0
                });
            },
            eventMouseout: function (event, jsEvent, view) { //鼠标离开的事件
                layer.closeAll();
            }
        });
    }

    $("#addEvent").click(function () {
        $("#external-events > #events").append(
                "<label class='fc-event'>" +
                "<input type='text' id='newEvent' onblur='confirmEvent(this);' style=' border:0;width: 115px;background:#3b87ad;' autofocus='autofocus' placeholder='请输入便捷日程' >" +
                "</label>");
    });

    function loadEventList() {
        $.ajax({
            type: "get",
            url: "/schedule/event",
            success: function (result) {
                if (result.code !== 200) {
                    layer.msg("便捷日程暂时没有")
                } else {
                    var event = result.data, len = event.length, str = '';
                    event.forEach(e => {
                        str += "<label class='fc-event'>" + e + "</label>"
                    });
                    $("#events").html(str);
                    loadDraggle();
                }
            }
        });
    }

    function confirmEvent(obj) {
        var eventName = $(obj).val();
        layer.confirm('您确定添加该便捷日程吗?', function () {
            if (eventName === '') {
                layer.msg("没有信息", {icon: 2})
            } else {
                $.ajax({
                    type: "post",
                    url: "/schedule/" + eventName,
                    success: function (result) {
                        if (result.code !== 200) {
                            layer.msg("便捷日程添加失败,系统发生异常", {icon: 2})
                        } else {
                            $(obj).parent().html($(obj).val());
                            $(obj).remove();
                            loadDraggle();
                            layer.closeAll();
                        }
                    }
                });
            }

        }, function () {
            $(obj).parent().remove();
        });
    }

    function loadDraggle() {
        $('#external-events .fc-event').each(function () {
            $(this).data('event', {
                title: $.trim($(this).text()),
                stick: true
            });
            $(this).draggable({
                zIndex: 999,
                revert: true,
                revertDuration: 0
            });
        });
    }

    function CPos(x, y) {
        this.x = x;
        this.y = y;
    }

    function GetObjPos(ATarget) {
        var target = ATarget,
                pos = new CPos(target.offsetLeft, target.offsetTop),
                target = target.offsetParent;
        while (target) {
            pos.x += target.offsetLeft;
            pos.y += target.offsetTop;
            target = target.offsetParent
        }
        return pos;
    }

    function scheduleDetail(id) {
        var schedule = {};
        $.ajax({
            url: "/schedule/detail",
            type: "get",
            async: false,
            data: {
                scheduleId: id
            },
            success: function (result) {
                schedule = result.data;
            }
        });
        return schedule;
    }

    function updateSchedule(id, startTime, endTime, title, customerId, remindTime, customerName) {
        var schedule = {};
        schedule.scheduleId = id;
        schedule.content = title;
        schedule.remindTime = remindTime;
        schedule.startTime = startTime;
        //TODO kehu
        schedule.customerId = customerId;
        schedule.customerName = customerName;
        schedule.endTime = endTime;
        $.ajax({
            url: "/schedule",
            type: "put",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(schedule),
            success: function (result) {
                if (result.code === 200) {
                    layer.msg("日程已修改", {time: 500});
                } else {
                    layer.msg("未知错误，请与程序员联系");
                }
            }
        })
    }

    $("#removeSchedule").click(function () {
        layer.confirm('您确定删除该日程吗?', function () {
            $.ajax({
                type: "delete",
                url: "/schedule/" + $("[name=scheduleId]").val(),
                success: function (result) {
                    if (result.code === 200) {
                        $('#calendar').fullCalendar('removeEvents', $("[name=scheduleId]").val());
                        layer.closeAll();
                        layer.msg("日程已删除", {time: 2000});
                    } else {
                        layer.msg("未知错误，请与程序员联系")
                    }
                }
            });
        });
    });

    $("#scheduleForm").validate({
        submitHandler: function () {
            var schedule = {};
            schedule.content = $("[name=description]").val();
            schedule.remindTime = $("[name=alarmDate]").val();
            schedule.startTime = $("[name=startYMD]").val() + " " + $("[name=startDate]").val();
            schedule.customerId = $("[name=customer]").val();
            schedule.customerName = $("#scheduleForm > div.modal-body > div:nth-child(2) > div > div:nth-child(1) > div > button > span.filter-option.pull-left").text();
            schedule.endTime = $("[name=endDate]").val() === '' ? schedule.startTime : $("[name=endDate]").val();
            $.ajax({
                url: "/schedule",
                type: "post",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(schedule),
                success: function (result) {
                    if (result.code === 200) {

                        var schedule = result.data, datas = [];
                        var event = {
                            id: schedule.scheduleId,
                            title: schedule.customerName,
                            start: schedule.startTime,
                            end: schedule.endTime,
                            color: new Date(schedule.endTime).getTime() < new Date().getTime() ? '#ff3847' : '#25ff30'
                        };
                        datas.push(event);
                        $('#calendar').fullCalendar('removeEvents', function (clientEvents) {
                            if (clientEvents.id === undefined) {
                                return clientEvents;
                            }
                        });
                        $('#calendar').fullCalendar('addEventSource', datas);

                        $("#scheduleModal").modal('hide');
                        layer.msg("添加日程成功", {time: 2000});
                    } else {
                        layer.msg("失败，请联系程序员", {time: 2});
                    }

                }

            })
        },
        rules: {
            startDate: {
                required: true
            },
            customer: {
                required: true
            },
            alarmDate: {
                required: true
            },
            description: {
                required: true
            }
        }
    });

    $("#updateScheduleForm").validate({
        submitHandler: function () {
            var content = $("[name=newDescription]").val()
                    , scheduleId = $("[name=scheduleId]").val()
                    , remindTime = $("[name=newAlarmTime]").val()
                    , startTime = $("[name=newStartTime]").val()
                    , customerId = $("[name=newCustomer]").val()
                    ,
                    customerName = $("#updateScheduleForm > div.modal-body > div:nth-child(2) > div > div:nth-child(1) > div > button > span.filter-option.pull-left").text()
                    , endTime = $("[name=newEndTime]").val();
            updateSchedule(scheduleId, startTime, endTime, content, customerId, remindTime, customerName);
            $("#updateScheduleModal").modal('hide');
        },
        rules: {
            newStartTime: {
                required: true
            },
            newDescription: {
                required: true
            },
            newCustomer: {
                required: true
            },
            newAlarmTime: {
                required: true
            }
        }
    });

</script>
</body>
</html>
