$(function () {
    //1.初始化Table
    new goodsTableInit('/goods').Init();
});


$("#btn_query"). click(function () {
    var searchParam = [];
    searchParam.condition = $("#goodsCondition").val();
    $('#goodsList').bootstrapTable('destroy');
    var oTable = new goodsTableInit('/goods', searchParam);
    oTable.Init();

});
/**
 * 文件框发生改变
 */
$("#fileInput").change(function(){
    var values = $(this).val();
    var prefix = values.substring(values.lastIndexOf('.')+1,values.length);
    if(prefix === 'xlsx' || prefix === 'xls'){
        var formData = new FormData();
        formData.append("file", $(this).get(0).files[0]);
        $.ajax({
            url: '/goods/uploadExcel',
            type: 'POST',
            data: formData,                    // 上传formdata封装的数据
            cache: false,
            processData: false,                // jQuery不要去处理发送的数据
            contentType: false,                // jQuery不要去设置Content-Type请求头
            success: function (res) {
                if (res.code === 200) {
                    layer.msg(res.message, {icon: 1});
                } else {
                    layer.msg(res.message, {icon: 2});
                }
            }
        })

    }else{
        layer.msg("请上传excel文件",{icon:2});
    }
    $(this).val('');
});
/**
 * 上传文件点击按钮
 */
$("#upload").click(function(){
    $("#fileInput").click();
});

var goodsTableInit = function (url, searchParam) {
    var oTableInit = {};
    //初始化Table
    oTableInit.Init = function () {

        $('#goodsList').bootstrapTable({
            url: url,                               //请求后台的URL（*）
            responseHandler: function (res) {
                return {
                    "rows": res.data.rows,
                    "total": res.data.total
                };
            },
            method: 'get',                          //请求方式（*）
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
            pageSize: 20,                           //每页的记录行数（*）
            pageList: [20, 25, 50, 50],             //可供选择的每页的行数（*）
            search: false,                          //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: false,                    //是否严格搜索
            showColumns: true,                      //是否显示所有的列
            showRefresh: true,                      //是否显示刷新按钮
            minimumCountColumns: 2,                 //最少允许的列数
            clickToSelect: true,                    //是否启用点击选中行
            height: $(window).height() - 100,         //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                         //每一行的唯一标识，一般为主键列
            showToggle: true,                       //是否显示详细视图和列表视图的切换按钮
            cardView: false,                        //是否显示详细视图
            detailView: false,                      //是否显示父子表
            columns: [                              //表头
                // {checkbox: true}                 //启用多选
                {
                    field: 'goodsNo',
                    title: '商品编号'
                },
                {
                    field: 'goodsId',
                    title: '商品编号',
                    visible: false,
                },
                {
                    field: 'goodsName',
                    title: '商品名称'
                },
                {
                    field: 'barCode',
                    title: '条形码'
                },
               {
                    field: 'typeName',
                    title: '分类'
                },
                {
                    field: 'retailPrice',
                    title: '建议零售价'
                },
                {
                    field: 'remark',
                    title: '备注'
                },
                {
                    field: 'stock',
                    title: '库存'
                },
                {
                    field: 'unit',
                    title: '单位'
                },
                {
                    field: 'standardAttribute',
                    title: '标准属性'
                },
                {
                    field: 'nonstandardAttribute',
                    title: '非标属性'
                },
                {
                    field: 'assistAttribute',
                    title: '辅助属性'
                },
                {
                    field: 'stockWarning',
                    title: '库存预警'
                },
                {
                    field: 'tool', title: '操作', align: 'center',
                    formatter: function (value, row, index) {
                        return "<a  href=\"javascript:;\" onclick=\"updateGoods('" + row.goodsId + "')\" class='btn btn-xs btn-bg upd' >" +
                            "       <i class='glyphicon glyphicon-pencil'/>" +
                            "</a>";
                        /*"<a target='_blank' href='/goods/" + row.goodsId + "' class='btn btn-xs btn-bg upd' >" +
                        "       <i class='glyphicon glyphicon-eye-open'/>" +
                        "</a> "+*/

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
        return param;
    };
    return oTableInit;
};
function updateGoods(goodsId) {

    var index = layer.open({
        type: 2 //此处以iframe举例
        , title: "修改商品"
        , area: ['600px', '600px']
        , shade: 0
        , content: '/goodsUpdate?goodsId=' + goodsId
        , maxmin: true
        , zIndex: layer.zIndex //重点1
        , success: function (layero) {
            layer.setTop(layero); //重点2
        }
    });
    layer.full(index);
    
}


/*
$("#btn_sync").click(function () {
    $.ajax({
        url: "/sync/goods",
        success: function (result) {
            if (result.state === "success") {
                layer.msg("数据已同步");
            }else {
                layer.msg(result.message);
            }
        }
    })
});*/
