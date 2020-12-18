var flag = false, /*表单校验是否成功*/
    resultGoods,
    table;
/*layui table 对象*/

$(function () {
    loadType();
    loadGoodsAttributes();

    //0.初始化fileinput
    new FileInput().Init("goodsImage", "/goods/image");

    //初始化switchery
    var elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch'));
    elems.forEach(function (html) {
        var switchery = new Switchery(html, {size: 'small'});
    });
    //switchery改变事件
    $(elems).change(function () {
        //是否使用属性
        if ($("#useSetAttr").is(':checked')) {
            $("#attrBlock").css("display", "inline")
        } else {
            $("#attrBlock").css("display", "none")
        }
        //是否使用库存警告
        if ($("#useStorage").is(':checked')) {
            $("#storage").css("display", "inline")
        } else {
            $("#storage").css("display", "none")
        }

        //是否使用非标准属性
        if ($("#useNonstandard").is(':checked')) {
            $("#nonstandard").css("display", "inline")
        } else {
            $("#nonstandard").css("display", "none")
        }
        //是否使用辅助属性
        if ($("#assistBtn").is(':checked')) {
            $("#assistBox").css("display", "inline")
        } else {
            $("#assistBox").css("display", "none")
        }

    });
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
                    'id': resultGoods
                };
            }
        }).on("filebatchuploadsuccess", function (event, data) {//当上传成功回调函数
            layer.msg("商品录入成功");
        });

    };
    return oFile;
};

/*加载i-checks的点击事件*/
function loadChecked() {
    $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green'
    });
    $('#goodsAttributes').find("input").on('ifChecked', function () {
        var attributeName = $(this).attr("attrId");
        $("#goodsAttributeValues").fadeIn(500);
        $("#" + attributeName).fadeIn(500);
    }).on('ifUnchecked', function () {
        var attributeName = $(this).attr("attrId");
        $("#" + attributeName).fadeOut(300);
    });
    $('#assists').find("input").on('ifChecked', function () {
        var attributeName = $(this).attr("attrId");
        $("#assistAttrValues").fadeIn(500);
        $("#" + attributeName).fadeIn(500);
    }).on('ifUnchecked', function () {
        var attributeName = $(this).attr("attrId");
        $("#" + attributeName).fadeOut(300);
    });
}


/*商品表单*/
/*一键生成商品信息表*/
$("#combination").click(function () {
    /*表单校验后的生成*/
    if (flag) {
        flag = false;
        $("[type=submit]").click();
    } else {
        $("[type=submit]").click();
    }
    if (flag) {
        getGoodsInformation();


        /* 初始化DataTables */
        layui.use("table", function () {
            table = layui.table;
            table.reload("listReload", {});
            table.render({
                elem: "#goodsInfo",
                data: getGoodsInformation(),
                id: 'listReload',
                skin: 'row',
                height: 300,
                limit: 10000,
                cellMinWidth: 80,
                cols: [[
                    {field: "goodsName", title: "名称", align: "center", edit: "text", fixed: 'left'},
                    {field: "typeId", title: "分类", align: "center", sort: true},
                    {field: "goodsNo", title: "编号", align: "center", edit: "text", sort: true},
                    {field: "unit", title: "单位", align: "center", sort: true},
                    {field: "remark", title: "备注", align: "center", edit: "text"},
                    {field: "barCode", title: "条形码", align: "center", edit: "text", sort: true},
                    {field: "retailPrice", title: "零售价", align: "center", edit: "text", sort: true},
                    {field: "stock", title: "库存", align: "center", edit: "text", sort: true},
                    {field: "standardAttribute", title: "属性", align: "center", width: "30%", fixed: 'right'},
                    {fixed: 'right', title: "删除", align: 'center', toolbar: '#barDemo'}
                ]],
                page: false, //开启分页
                even: true
            });
            //监听单元格编辑
            table.on('edit(goodsInfo)', function (obj) {
                var value = obj.value; //得到修改后的值
                layer.msg('字段更改为：' + value);
            });

            //监听工具条
            table.on('tool(goodsInfo)', function (obj) {
                if (obj.event === 'del') {
                    layer.confirm('真的删除行么', function (index) {
                        obj.del();
                        layer.close(index);
                    });
                }
            });
        })
    } else {
        layer.msg("请填完基本信息");
    }
});
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
        //库存预警
        var stockWarning = null
            , nonPropsStr = ''
            , nonProps
            , assistList = "";
        if ($("#useStorage").is(':checked')) {
            stockWarning = $("[name=stockWarning]").val();
            if (stockWarning != null && stockWarning !== '' && stockWarning > $("[name=stock]").val()) {
                layer.msg("预警大于库存");
                return;
            }
        }

        if ($("#useNonstandard").is(':checked')) {
            nonProps = $("#nonstandardAttr").find(".checked").find("input");//获取非标准属性;
            if (nonProps != null && nonProps.length > 0) {
                for (var k = 0; k < nonProps.length; k++) {
                    nonPropsStr += $(nonProps[k]).val() + ", "
                }
            }
        }

        if ($("#assistBtn").is(':checked')) {
            var assist = $("[name=assistValue]");

            if (assist != null && assist.length > 0) {
                for (var k = 0; k < assist.length; k++) {
                    if ($(assist[k]).val() !== "") {
                        assistList += $(assist[k]).parent().attr("attrName") + ":" + $(assist[k]).val() + " ";
                    }

                }
            }
        }

        /*设置属性打开*/
        if ($("#useSetAttr").is(':checked')) {
            /*有选择属性*/
            if ($("#goodsAttributes").find(".checked").find("input").length > 0) {
                if (!flag) {
                    layer.msg("请核对商品后提交");
                    flag = true;
                    return;
                } else {

                    if (table === undefined || table.cache.listReload === undefined || table.cache.listReload.length === 0) {
                        flag = false;
                        layer.msg("请生成商品核对后再提交")
                        return;
                    }
                    var goods = [];                 //将要传给后台的数组
                    var nTrs = table.cache.listReload;//得到表格所有数据
                    for (var i = 0; i < nTrs.length; i++) {
                        var column = nTrs[i],                   //获取一行的数据
                            goodsInfo;                       //商品信息实体
                        console.log(column);
                        if (column.length === 0) {
                            continue;
                        }
                        goodsInfo = new GoodsInfoObj(column.goodsNo, column.goodsName, column.retailPrice, column.unit, column.typeId,$("#comboTree")[0].innerText,
                            column.stock, stockWarning, column.remark, column.barCode, column.standardAttribute, nonPropsStr, assistList);
                        goods.push(goodsInfo);
                    }

                    if (goods.length === 0) {
                        layer.msg("表格没有数据可以提交");
                        return;
                    }
                    console.log(JSON.stringify(goods));
                    $.ajax({
                        url: "/goods/attr",
                        type: "post",
                        contentType: "application/json;charset=UTF-8",
                        data: JSON.stringify(goods),
                        success: function (result) {
                            if (result.code === 200) {
                                submitSuccess(result);
                            } else {
                                layer.msg(result.data)
                            }
                        }
                    });
                }
            } else {
                layer.msg("请选择属性");
            }
        } else {
            /*无属性提交*/
            $.ajax({
                url: "/goods",
                type: "post",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(new GoodsInfoObj(
                    $("[name=goodsNo]").val(),
                    $("[name=goodsName]").val(),
                    $("[name=rrp]").val(),
                    $("[name=unit]").val(),
                    $("#comboTree").bootstrapCombotree("getValue").toString(),
                    $("#comboTree")[0].innerText,
                    $("[name=stock]").val(),
                    stockWarning,
                    $("[name=remark]").val(),
                    $("[name=barcode]").val(),
                    null,
                    nonPropsStr,
                    assistList)),
                success: function (result) {
                    if (result.code === 200) {
                        submitSuccess(result);
                    } else {
                        layer.msg(result.message)
                    }
                }
            });
        }
    },
    rules: {
        typeId: {
            required: true
        },
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
        list: {
            required: true
        }
    }
});

/*获取商品属性信息*/
function getGoodsInformation() {
    var attributes = [],
        goodsInfos = [];

    //获取选中的属性
    var props = $("#goodsAttributes").find(".checked").find("input");

    /*获得属性数组*/
    for (var i = 0; i < props.length; i++) {
        var attrAndAttrValue = [],
            attrId = $(props[i]).attr("attrId"),
            attrName = $(props[i]).val();
        //获取选中的属性值
        var objAttr = $("#" + attrId).find(".checked").find("input");
        if (objAttr == null || objAttr.length < 1) {
            layer.msg("选择属性后请选择对应的属性值");
            return;
        }
        for (var j = 0; j < objAttr.length; j++) {
            attrAndAttrValue.push(attrName + ":" + $(objAttr[j]).val() + " ");
        }
        attributes.push(attrAndAttrValue);
    }

    /*笛卡尔积各属性*/
    var combination = DescartesUtils.descartes(attributes),
        len = combination.length;

    /*插入多行数据到一个数组*/
    for (var i = 0; i < len; i++) {
        var attr = combination[i], str = "";

        for (var j = 0; j < attr.length; j++) {
            str += attr[j];
        }
        var goods = new GoodsInfoObj(
            $("[name=goodsNo]").val(),
            $("[name=goodsName]").val(),
            $("[name=rrp]").val(),
            $("[name=unit]").val(),
            $("#comboTree").bootstrapCombotree("getValue").toString(),
            $("#comboTree")[0].innerText,
            $("[name=stock]").val(),
            null,
            $("[name=remark]").val(),
            $("[name=barcode]").val(),
            str,
            null,
            null
        );

        goodsInfos.splice(i, 0, goods);
    }
    return goodsInfos;
}

/*商品信息对象*/

//TODO purchasePrice,expiryDate,productDate
function GoodsInfoObj(goodsNo, goodsName, retailPrice, unit, typeId, typeName,stock, stockWarning, remark,
                      barCode, standardAttribute, nonstandardAttribute, assistAttribute) {
    this.goodsNo = goodsNo;
    this.goodsName = goodsName;
    this.retailPrice = retailPrice;
    this.unit = unit;
    this.typeId = typeId;
    this.typeName = typeName;
    this.stock = stock;
    this.stockWarning = stockWarning;
    this.remark = remark;
    this.barCode = barCode;
    this.standardAttribute = standardAttribute;
    this.nonstandardAttribute = nonstandardAttribute;
    this.assistAttribute = assistAttribute;
}

/*提交成功*/
function submitSuccess(result) {
    resultGoods = result.data;      //先保存基本数据，返回数据库id
    if ($(".file-preview-thumbnails").children().length > 0) {
        $('#goodsImage').fileinput('upload');
    } else {
        layer.msg("商品录入成功");
    }
    flag = false;
    $("[name=goodsNo]").val("");
    $("[name=goodsName]").val("");
    $("[name=remark]").val("");
    $("[name=stock]").val("");
    $("[name=barcode]").val("");
    $("[name=unit]").val("");
    // $("[name=goodsBid]").val("");
    $("[name=rrp]").val("");
    $("[name=stockWarning]").val("");
    $("[name=assistValue]").val("");
    $(".fileinput-remove-button").click()
}


/*商品分类*/

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

/*加载分类树*/
function loadType() {
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
                    defaultLable: '请 选 择 商 品 分 类',              //默认按钮上的文本
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


/*商品属性管理*/

/*添加标准属性*/
function addGoodsAttr() {
    var goodsAttribute = $("[name=goodsAttributesName]").val();
    if (goodsAttribute !== '') {
        $.ajax({
            url: "/attribute",
            type: "post",
            data: {
                goodsAttribute: goodsAttribute
            },
            success: function (result) {
                if (result.code === 200) {

                    $("#goodsAttributeModal").modal("hide");
                    $("#goodsAttributes").append("<label><input type=\"checkbox\" name='goodsAttr' attrId=\"" + result.data + "\" value='" + goodsAttribute
                        + "' class=\"i-checks\">" + goodsAttribute + "</label> &nbsp; &nbsp; ")
                    layer.msg("属性添加成功");
                    $("[name=goodsAttributesName]").val('');
                    $("#goodsAttributeValues").append("<div id='" + result.data + "' style='display:none;margin-bottom: 5px'>" + goodsAttribute +
                        ":&nbsp; <button type='button' class='btn btn-xs btn-primary' name='tagert' data-whatever='" + goodsAttribute + "' data-toggle='modal' data-target='#attributeModal' ><span class='fa fa-plus'></span></button>" +
                        "</div>");
                    loadChecked();
                } else {
                    layer.msg(result.message)
                }
            }
        });
    }
    else {
        layer.msg("属性名称为空");
    }
}

/*添加非标准属性*/
function addNonstandardAttr() {
    var goodsAttribute = $("[name=nonstandardAttrName]").val();
    if (goodsAttribute !== '') {
        $.ajax({
            url: "/attribute",
            type: "post",
            data: {
                goodsAttribute: goodsAttribute,
                isNonstandard: 1
            },
            success: function (result) {
                if (result.code === 200) {
                    $("#nonstandardAttrModal").modal("hide");
                    $("#nonstandardAttr").append("<label><input type=\"checkbox\" name='nonstandardAttrId' id=\"" + result.data + "\" value='" + goodsAttribute
                        + "' class=\"i-checks\">" + goodsAttribute + "</label>&nbsp; &nbsp;\n")
                    loadChecked();
                    layer.msg("非标准属性添加成功")
                    $("[name=nonstandardAttrName]").val('')
                } else {
                    layer.msg(result.message)
                }
            }
        });
    }
    else {
        layer.msg("属性名称为空");
    }
}

/*添加辅助属性*/
function addAssistAttr() {
    var assistAttr = $("[name=assistAttrName]").val();
    if (assistAttr !== '') {
        $.ajax({
            url: "/attribute",
            type: "post",
            data: {
                goodsAttribute: assistAttr,
                isNonstandard: 2
            },
            success: function (result) {
                if (result.code === 200) {
                    $("#assistModal").modal("hide");
                    $("#assists").append("<label><input type=\"checkbox\" name='assistAttrId' attrId=\"" + result.data + "\" value='" + assistAttr
                        + "' class=\"i-checks\">" + assistAttr + "</label>&nbsp; &nbsp;\n");
                    $("#assistAttrValues").append("<div id='" + assistAttr + "' style='display:none;margin-bottom: 5px'><b>" + assistAttr + " :</b>" +
                        "&nbsp; <input class='form-control' type='text' aria-required='true' aria-invalid='true' name='assistValue'>&nbsp; " +
                        "</div>");
                    loadChecked();
                    layer.msg("辅助属性添加成功");
                    $("[name=assistAttrName]").val('')
                } else {
                    layer.msg(result.message)
                }
            }
        });
    }
    else {
        layer.msg("属性名称为空");
    }
};

/*添加属性值*/
function addAttrValue() {
    var attributeValue = $("[name=attributeValue]").val(),
        attributeId = $('#attr').val(),
        attributeName = $('#attrName').val();
    if (attributeValue !== '' && attributeId !== '') {
        $.ajax({
            url: "/attributeValue",
            type: "post",
            data: {
                attributeValue: attributeValue,
                attributeId: attributeId
            },
            success: function (result) {
                if (result.code === 200) {
                    $("#" + attributeId + "> button").remove();
                    $("#" + attributeId).append("&nbsp;" + attributeValue + " <input type='checkbox' attrValId='" + result.data + "' value='" + attributeValue + "' class='i-checks' name='attributeValues'>" +
                        "&nbsp; <button type='button' class='btn btn-xs btn-primary' name='target'  data-attrId ='" + attributeId + "' data-attrName='" + attributeName + "' data-toggle='modal' data-target='#attributeModal'><span class='fa fa-plus'></span></button>");
                    loadChecked();
                    $("#attributeModal").modal("hide");
                    layer.msg("属性值添加成功");
                    $("[name=attributeValue]").val('')
                } else {
                    layer.msg(result.message)
                }
            }
        });
    } else {
        layer.msg("属性值为空");
    }
};

/*加载属性*/
function loadGoodsAttributes() {
    $.ajax({
        url: "/attribute",
        type: "get",
        async: false,
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                var root = result.data,
                    len = root.length;
                var str = "";
                for (var i = 0; i < len; i++) {
                    var attributeName = root[i].attributeName,
                        attributeId = root[i].attributeId;
                    str += "<label>" +
                        "       <input type='checkbox' name='goodsAttrId' attrId=\"" + attributeId + "\" value='" + attributeName + "' class=\"i-checks\">" + attributeName +
                        "</label>&nbsp; &nbsp;\n";
                    var attrVal = "";

                    $.ajax({
                        url: "/attributeValue",
                        data: {
                            attributeId: attributeId
                        },
                        type: "get",
                        async: false,
                        contentType: "application/json;charset=UTF-8",
                        success: function (res) {
                            var attr = res.data;
                            if (attr !== undefined && attr.length > 0) {
                                for (var i = 0; i < attr.length; i++) {
                                    attrVal += "&nbsp; &nbsp;" + attr[i].attributeValue + " <input type='checkbox' attrValId ='" + attr[i].attributeValueId + "' value='" + attr[i].attributeValue + "' class='i-checks' name='attributeValues'>";
                                }
                            }
                        }
                    });
                    $("#goodsAttributeValues").append("<div id='" + attributeId + "' style='display:none;margin-bottom: 5px'>" + attributeName +
                        ":&nbsp;" + attrVal + " <button type='button' class='btn btn-xs btn-primary' name='tagert' data-attrId ='" + attributeId + "' data-attrName='" + attributeName + "' data-toggle='modal' data-target='#attributeModal' ><span class='fa fa-plus'></span></button>" +
                        "</div>");
                }
                $("#goodsAttributes").html(str);
            }
            loadChecked();
        }
    });
    $.ajax({
        url: "/attribute",
        type: "get",
        data: {
            isNonstandard: 1
        },
        async: false,
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                var root = result.data,
                    len = root.length;
                var str = "";
                for (var i = 0; i < len; i++) {
                    var attributeName = root[i].attributeName,
                        attributeId = root[i].attributeId;
                    str += "<label><input type='checkbox' name='nonstandardAttrId' id=\"" + attributeId + "\" value='" + attributeName + "' class=\"i-checks\">" + attributeName +
                        "</label>&nbsp; &nbsp;\n";
                }
                str += "";
                $("#nonstandardAttr").html(str);

            }
            loadChecked();
        }
    });
    $.ajax({
        url: "/attribute",
        type: "get",
        data: {
            isNonstandard: 2
        },
        async: false,
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                var root = result.data,
                    len = root.length
                    , str = "";
                for (var i = 0; i < len; i++) {
                    var attributeName = root[i].attributeName,
                        attributeId = root[i].attributeId;
                    str += "<label><input type='checkbox' name='assistAttrId' attrId=\"" + attributeId + "\" value='" + attributeName + "' class=\"i-checks\">" + attributeName +
                        "</label>&nbsp; &nbsp;\n";

                    $("#assistAttrValues").append("<div id='" + attributeId + "' attrName='" + attributeName + "' style='display:none;margin-bottom: 5px'><b>" + attributeName + " :</b>" +
                        "&nbsp; <input class='form-control' type='text' aria-required='true' aria-invalid='true' name='assistValue'>&nbsp; " +
                        "</div>")

                }
                str += "";
                $("#assists").html(str);

            }
            loadChecked();
        }
    });
}

/*给模态框传值*/
$('#attributeModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget), modal = $(this); // Button that triggered the modal
    var attrName = button.context.dataset.attrname, attrId = button.context.dataset.attrid // Extract info from data-* attributes
    modal.find('#attrName').val(attrName);
    modal.find('#attr').val(attrId);
});

/*商品同步*/
/*$("#btn_sync").click(function () {
    $.ajax({
        url: "/sync/goods",
        success: function (result) {
            if (result.code === 200) {
                layer.msg("数据已同步");
            } else {
                layer.msg(result.message);
            }
        }
    })
});*/





