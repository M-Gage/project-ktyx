var flag = false, /*表单校验是否成功*/
    goodsId;


$(function () {
    //初始化fileinput
    new FileInput().Init("goodsImage", "/goods/image");
    goodsId = window.location.href.split("goodsId=")[1];
    $.ajax({
        url: "/goods/info/" + goodsId,
        type: "get",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                var goods = result.data;
                $("[name=goodsNo]").val(goods.goodsNo);
                $("[name=goodsName]").val(goods.goodsName);
                $("[name=remark]").val(goods.remark);
                $("[name=stock]").val(goods.stock);
                $("[name=barcode]").val(goods.barCode);
                $("[name=unit]").val(goods.unit);
                $("[name=rrp]").val(goods.retailPrice);
                $("[name=stockWarning]").val(goods.stockWarning);
                loadType(goods.typeId);
            }
        }
    })
});


//初始化fileinput
var FileInput = function () {
    var oFile = {};

    //初始化fileinput控件（第一次初始化）
    oFile.Init = function (ctrlName, uploadUrl) {
        var control = $('#' + ctrlName);

        //初始化上传控件的样式
        control.fileinput({
            language: 'zh',                                 //设置语言
            uploadUrl: uploadUrl,                           //上传的地址
            allowedFileExtensions: ['jpg', 'gif', 'png'],   //接收的文件后缀
            removeFromPreviewOnError: true,                 //不匹配文件不显示
            showUpload: false,                              //是否显示上传按钮
            showCaption: false,                             //是否显示标题
            browseClass: "btn btn-primary",                 //按钮样式
            uploadAsync: false,                             //同步上传
            //dropZoneEnabled: false,                       //是否显示拖拽区域
            maxFileSize: 2048,                              //单位为kb，如果为0表示不限制文件大小
            //minFileCount: 0,                              //最小上传文件个数
            maxFileCount: 10,                               //表示允许同时上传的最大文件个数
            enctype: 'multipart/form-data',                 //必填
            validateInitialCount: true,                     //校验个数
            msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
            layoutTemplates: {
                actionUpload: ""                            //去除单个上传按钮
            },
            uploadExtraData: function (previewId, index) {   //额外参数 返回json数组
                return {
                    'id': goodsId
                };
            }
        }).on("filebatchuploadsuccess", function (event, data) {//当上传成功回调函数
            layer.msg("商品修改成功");
        });

    };
    return oFile;
};


/*只输入数字和字母校验*/
jQuery.validator.addMethod("alphanumeric", function (value, element) {
    var flag = true;
    if (!/^[0-9a-zA-Z]+$/.test(value)) {
        flag = false;
    }
    return flag;
}, "请输入字母或数字！");

/*价格校验*/
jQuery.validator.addMethod("price", function (value, element) {
    var flag = true;
    if (!/^[0-9]+(\.[0-9]{2})?$/.test(value)) {
        flag = false;
    }
    return flag;
}, "请输入正确价格ps：1.00或1！");

/*表单校验*/
$("#addGoodsForm").validate({
    // debug: true,
    submitHandler: function () {
        /*提交*/
        $.ajax({
            url: "/goods/update",
            type: "post",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({
                goodsId: goodsId,
                typeId: $("#comboTree").bootstrapCombotree("getValue").toString(),
                typeName: $("#comboTree")[0].innerText,
                goodsNo: $("[name=goodsNo]").val(),
                goodsName: $("[name=goodsName]").val(),
                remark: $("[name=remark]").val(),
                stock: $("[name=stock]").val(),
                barCode: $("[name=barcode]").val(),
                unit: $("[name=unit]").val(),
                retailPrice: $("[name=rrp]").val(),
                stockWarning: $("[name=stockWarning]").val()
            }),
            success: function (result) {
                if (result.code === 200) {
                    submitSuccess(result);
                } else {
                    layer.msg(result.message)
                }
            }
        });
    },
    rules: {
        goodsNo: {
            required: true,
            alphanumeric: true
        },
        goodsName: {
            required: true
        },
        stock: {
            required: true,
            digits: true,
            maxlength: 7,
            min: 0
        },
        barcode: {
            required: true,
            digits: true
        },
        unit: {
            required: true
        },
        rrp: {
            required: true,
            price: true
        },

    }
});

/*提交成功*/
function submitSuccess(result) {
    goodsId = result.data;      //先保存基本数据，返回数据库id
    if ($(".file-preview-thumbnails").children().length > 0) {
        $('#goodsImage').fileinput('upload');
    } else {
        layer.msg("商品修改成功");
    }
    flag = false;
}


/*加载分类树*/
function loadType(typeId) {
    $.ajax({
        url: "/type/get/" + typeId,
        type: "get",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                var typeId = result.data;
                $.ajax({
                    url: "/type",
                    type: "get",
                    contentType: "application/json;charset=UTF-8",
                    success: function (result) {
                        if (result.code === 200) {

                            var json = [],
                                root = result.data,
                                len = root.length;

                            for (var i = 0; i < len; i++) {
                                if (root[i].typeId.toString().length == 4) {
                                    root[i].text = root[i].typeName;
                                    root[i].id = root[i].typeId;
                                    json.push(root[i]);
                                    loadChildNode(result.data, root[i]);
                                }
                            }

                            $("#comboTree").bootstrapCombotree({
                                defaultLable: typeId,              //默认按钮上的文本
                                data: json,                                     //data应符合实例的data格式
                                showIcon: true,                                 //显示图标
                                width: 130,                                     //下拉列表宽度
                                minWidth: 130,
                                name: 'list',                                   //combotree值域的name，可以用在表单提交
                                maxItemsDisplay: 1,                             //按钮上最多显示多少项，如果超出这个数目，将会以‘XX项已被选中代替’
                                selectToCheck: true,                            //为了兼容移动设备，点击属性菜单项时自动选中节点
                                onCheck: function (node) {                      //树形菜单被选中时触发事件, node为选中的那个节点
                                    if (node.nodes == undefined) {
                                        $('.btn-group').attr('class', 'btn-group');
                                        $('.btn-group').find('button:first').attr('aria-expanded', false);
                                    }
                                }
                            });
                            $("#comboTree > .btn-group").css("width", "100%");
                            $("#comboTree > .btn-group > button").addClass("btn-block btn-white").removeClass("btn-default");
                            $("#comboTree > div > div").css("width", "100%");
                        } else {
                            layer.msg("暂无分类数据，请先到添加分类中添加");
                        }

                    }
                });
            }
        }
    });


}

/*为分类树加载子节点*/
function loadChildNode(root, node) {
    var reg = new RegExp("^" + node.typeId + "..$"),
        len = root.length;
    for (var i = 0; i < len; i++) {
        if (reg.test(root[i].typeId.toString())) {
            if (!node.nodes) {
                node.nodes = [];
            }
            node.nodes.push(root[i]);
            root[i].text = root[i].typeName;
            root[i].id = root[i].typeId.toString();
            loadChildNode(root, root[i]);
        }
    }
}


/*商品信息对象*/
/*function GoodsInfoObj(typeId, goodsNo, goodsName, unit, remark, barcode, rrp, stock, props, stockWarning) {
    this.typeId = typeId;
    this.goodsNo = goodsNo;
    this.goodsName = goodsName;
    this.unit = unit;
    this.remark = remark;
    this.barcode = barcode;
    this.rrp = rrp;
    this.stock = stock;
    this.props = props;
    this.stockWarning = stockWarning;
}*/





