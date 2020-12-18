var elems;
$(function () {
    deptTree.data.el = "#order_dept_tree";
    deptTree.data.id_el = "[name=dept]";
    deptTree.data.title_el = ".order_dept_tree_title";
    deptTree.initTree();

    typeTree.data.el = "#order_type_tree";
    typeTree.data.id_el = "[name=type]";
    typeTree.data.title_el = ".order_type_tree_title";
    typeTree.initTree();

    /*加载日期样式*/
    layui.laydate.render({
        elem: document.getElementById('date')
        , range: '~' //或 range: true 来自定义-分割字符
        , calendar: true
        , theme: "#1ab394"
    });
    elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch'));
    elems.forEach(function (html) {
        var switchery = new Switchery(html, {size: 'small'});
    });
    //1.初始化Table
    new orderTableInit('orderList', '/order').Init();
    $("[name=custoemr]").selectpicker();
    $("[name=staff]").selectpicker();
    // loadType();
});

$("#date").click(function () {
    unbindBody();
    $("#layui-laydate1").mouseover(function () {
        unbindBody();
    });
});

$("#menu_btn").click(function () {
    unbindBody();
    $(".menu-content").stop().slideToggle(400);
    $("#comboTree > .btn-group").css("width", "100%");
    $("#comboTree > .btn-group > button").addClass("btn-block btn-white").removeClass("btn-default");
    $("#comboTree > div > div").css("width", "100%");
});

$("#comboTree").click(function () {
    unbindBody();
    var tree = $(".treeview").eq(0);
    if (tree.find("input").length === 0) {
        tree.append("<input class='btn btn-sm btn-warning full-width' id='resetTree' readonly value='重置'/>");
    }
    $("#resetTree").click(function () {
        $("[name=list]").val('');
        $("#comboTree > .btn-group > button").html("请 选 择 商 品 分 类<span class='caret'></span>").click();
    });
});

$("#deptTree").click(function () {
    unbindBody();
    var tree = $(".treeview").eq(1);
    if (tree.find("input").length === 0) {
        tree.append("<input class='btn btn-sm btn-warning full-width' id='resetDept' readonly value='重置'/>");
    }
    $("#resetDept").click(function () {
        $("[name=dept]").val('');
        $("#deptTree").html("<span class='order_dept_tree_title'>请 选 择 部 门</span>\n" +
            "                <span class='caret'></span>").click();
    });
});

$(".menu-content").mouseleave(function () {
    unbindBody();
    $('body').click(function () {
        if ($(".menu-content").css("display") === "block") {
            $(".menu-content").slideToggle(400);
        }
        unbindBody();
    })
});

$("[name=btn_query]").click(function () {
    $("#orderList").bootstrapTable('destroy');
    unbindBody();
    if ($(".menu-content").css("display") === "block") {
        $(".menu-content").slideToggle(400);
    }


    var searchParam = [];
    searchParam.staffId = $('#staffInfo').val().split(" ")[0];
    searchParam.customerId = $('#customerInfo').val();
    searchParam.dateInterval = $('[name=dateInterval]').val();
    searchParam.level = $('[name=level]').val();
    searchParam.typeId = $("[name=type]").val();
    searchParam.deptId = $('[name=dept]').val();
    searchParam.condition = $('[name=condition]').val();
    searchParam.status = $('[name=orderStatus]').val();

    //1.初始化Table
    new orderTableInit('orderList', '/order/conditions', searchParam).Init();
});

$("[name=btn_reset]").click(function () {
    unbindBody();
    $("[name='orderStatus']").val("0");
    $("[name='level']").val("");
    $("[name='dateInterval']").val("");
    $("#resetTree").click();
    $("#resetDept").click();
    $("#deptTree").click();
    $("#comboTree").click();
});

function unbindBody() {
    $('body').off("click");
}

var orderTableInit = function (ctrlName, url, searchParam) {
    var oTableInit = {};
    //初始化Table
    oTableInit.Init = function () {

        $('#' + ctrlName).bootstrapTable({
            url: url,                               //请求后台的URL（*）
            responseHandler: function (res) {
                return {
                    "rows": res.data.rows,
                    "total": res.data.total
                };
            },
            contentType: "application/json;charset=UTF-8",
            method: 'post',                          //请求方式（*）
            toolbar: '#toolbar',                    //工具按钮用哪个容器
            locale: 'zh-CN',                        //中文支持
            striped: true,                          //是否显示行间隔色
            cache: false,                           //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                       //是否显示分页（*）
            sortable: false,                        //是否启用排序
            sortOrder: "asc",                       //排序方式
            queryParamsType: '',                    //去除默认limit
            queryParams: oTableInit.queryParams,    //传递参数（*）
            sidePagination: "server",               //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                          //初始化加载第一页，默认第一页
            pageSize: 20,                            //每页的记录行数（*）
            pageList: [10, 25, 50, 50],             //可供选择的每页的行数（*）
            search: false,                          //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: false,                    //是否严格搜索
            showColumns: true,                      //是否显示所有的列
            showRefresh: true,                      //是否显示刷新按钮
            minimumCountColumns: 2,                 //最少允许的列数
            clickToSelect: true,                    //是否启用点击选中行
            height: $(window).height() - 130,         //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "goodsNo",                    //每一行的唯一标识，一般为主键列
            showToggle: true,                       //是否显示详细视图和列表视图的切换按钮
            cardView: false,                        //是否显示详细视图
            detailView: false,                      //是否显示父子表
            columns: [                              //表头
                // {checkbox: true}                 //启用多选
                {
                    field: 'orderId',
                    title: '订单编号'
                },
                {
                    field: 'customerName',
                    title: '客户名称',
                },{
                    field: 'submitTime',
                    title: '下单时间',
                    formatter:function (value, row, index) {
                        var d = new Date(value);
                        return d.toLocaleString();
                    }
                },
                {
                    field: 'staffName',
                    title: '业务员名称'
                }, {
                    field: 'amount',
                    title: '交易金额'
                }, {
                    field: 'address',
                    title: '下单地址',
                    formatter:function (value, row, index) {
                        return row.province+row.city+row.district+row.detailAddress;
                    }
                },
                {
                    field: 'status',
                    title: '订单状态',
                    formatter: function (value, row, index) {
                        var str = "<select onchange=changeOrderState('" + row.orderId + "',this)>"
                            , opt0 = "<option value='0'>未处理</option>"
                            , opt1 = "<option value='1'>已处理</option>"
                            , opt2 = "<option value='2'>审核中</option>"
                            , opt3 = "<option value='3'>作废</option>";

                        if (value === 0) {
                            str += opt0 + opt1 + opt2 + opt3;
                        } else if (value === 1) {
                            str += opt1;
                        } else if (value === 2) {
                            str += opt2 + opt1 + opt3;
                        } else {
                            str += opt3;
                        }
                        str += "</select>";
                        return str;
                    }
                },
                {
                    field: 'tool', title: '操作', align: 'center',
                    formatter: function (value, row, index) {
                        return "<a target='_blank' href='/order/" + row.orderId + "' class='btn btn-xs btn-bg upd' >" +
                            "       <i class='glyphicon glyphicon-eye-open'/>" +
                            "</a> ";

                    }
                }
            ]
        });
    };


    oTableInit.queryParams = function queryParams(params) {
        var param = {
            pageNumber: params.pageNumber,
            pageSize: params.pageSize
        };
        for (var key in searchParam) {
            param[key] = searchParam[key]
        }
        return JSON.stringify(param);
    };

    return oTableInit;
};


function changeOrderState(row, obj) {
    console.log($(obj).val());
    $.ajax({
        url: "/order/status",
        type: "get",
        data: {
            status: $(obj).val(),
            orderId: row
        },
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                layer.msg(row + "已更改状态", {icon: 1})
            } else {
                layer.msg("更改状态失败", {icon: 2})
            }
        }

    });
}

var customerFlag = true, staffFlag = true;
$("#customerInfo").parent().click(function () {
    if (customerFlag) {
        var info = $('#staffInfo').val().split(" ");
        customerList("#customerInfo",info[0],info[1],info[2]);
        customerFlag = false;
    }
});
$("#staffInfo").parent().click(function () {
    if (staffFlag) {
        staffList("#staffInfo");
        staffFlag = false;
    }
    customerFlag = true;
});



$(".tree_content").on("click", function (e) {
    e.stopPropagation();
});