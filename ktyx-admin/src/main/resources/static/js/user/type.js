/**
 *
 * 分类
 *
 */
var type = {
    flag: {
        flag1: true,
        flag2: true
    },
    addTypeForm: {
        el: "#addTypeForm",
        ul: "/type",
        form_firstType: "#firstType",
        form_thirdType: "#thirdType",
        form_secondType: "#secondType"
    },
    ul: "/type/check/",
    typeId: {
        el: "[name=typeId]"
    },
    typeName: {
        el: "[name=typeName]"
    },
    firstType: {
        el: "#firstTypeButton",
        ul: "/type/parentType"
    },
    secondType: {
        el: "#secondTypeButton",
        ul: "/type/parentType"
    },
    typeFormSubmit: function () {
        $(this.addTypeForm.el).submit(function () {
            var firstType = $(type.addTypeForm.form_firstType).val()
                , secondType = $(type.addTypeForm.form_secondType).val()
                , thirdType = $(type.addTypeForm.form_thirdType).val();
            if (firstType === "" || (thirdType !== "" && secondType === "")) {
                layer.msg("信息不为空");
                return false;
            }

            if (firstType !== "" && secondType !== "") {
                if (firstType === thirdType
                    || thirdType === secondType
                    || secondType === firstType) {
                    layer.msg("录入信息有相同的");
                    return false;
                }
            }

            if (type.flag.flag1) {
                var typeId
                    , ft = $("[name=" + firstType + "]")
                    , typeName = [];
                if (secondType !== "") var st = $("[name=" + secondType + "]");

                if ((ft.val() !== undefined && secondType === "") || (st !== undefined && st.val() !== undefined && thirdType === "")) {
                    layer.msg(" 请输入要录入的值");
                    return false;
                }
                if (ft.val() !== undefined) typeId = ft.val();
                if (ft.val() !== undefined && st.val() !== undefined) typeId = st.val();
                typeName.push(firstType);
                typeName.push(secondType);
                typeName.push(thirdType);
                $.ajax({
                    url: type.addTypeForm.ul,
                    type: "post",
                    contentType: "application/json;charset=UTF-8",
                    data: JSON.stringify({
                        typeId: typeId,
                        typeName: typeName
                    }),
                    success: function (result) {
                        if (result.code === 500) {
                            layer.msg("未知错误，联系技术人员");
                        } else {
                            layer.msg("分类树已刷新");
                            $(type.addTypeForm.form_firstType).val('');
                            $(type.addTypeForm.form_secondType).val('');
                            $(type.addTypeForm.form_thirdType).val('');
                            type.loadTree();
                        }
                    }
                });
            } else {
                layer.msg("录入提示有错误，请改正");
            }
            return false;
        });
        return this;
    },
    checkTypeName: function () {
        $(type.addTypeForm.form_firstType).blur(function () {
            if ($(this).val() === "" || $("[name=" + $(this).val() + "]").val() !== undefined) {
                type.flag.flag1 = true;
                $(this).removeClass("error");
                return;
            }
            check($(this));
        });
        $(type.addTypeForm.form_secondType).blur(function () {
            if ($(this).val() === "" || $("[name=" + $(this).val() + "]").val() !== undefined) {
                type.flag.flag1 = true;
                $(this).removeClass("error");
                return;
            }
            check($(this));
        });
        $(type.addTypeForm.form_thirdType).blur(function () {
            check($(this));
        });

        function check(obj) {
            var typeName = obj.val();
            if (typeName != null && typeName !== '') {
                obj.removeClass("error");
                if (obj.length > 10) {
                    layer.msg("分类名称过长，请限制在10字以内！");
                    type.flag.flag1 = false;
                    obj.addClass("error");
                    return;
                }
                $.ajax({
                    url: type.ul + typeName,
                    type: "post",
                    contentType: "application/json;charset=UTF-8",
                    success: function (result) {
                        if (result.code === 500) {
                            layer.msg("类型名称存在");
                            type.flag.flag1 = false;
                            obj.addClass("error");
                        } else {
                            type.flag.flag1 = true;
                            obj.removeClass("error");
                        }
                    }
                });
            }
        }

        return this;
    },
    loadChildNode: function (root, node) {
        var reg = new RegExp("^" + node.typeId + "..$"),
            len = root.length;

        for (var i = 0; i < len; i++) {
            if (reg.test(root[i].typeId.toString())) {
                if (!node.nodes) {
                    node.nodes = [];
                }
                node.nodes.push(root[i]);
                root[i].text = root[i].typeId.toString() + ' —— ' + root[i].typeName;
                type.loadChildNode(root, root[i]);
            }
        }
        return this;
    },
    loadTree: function () {
        $.ajax({
            url: type.addTypeForm.ul,
            type: "get",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                var json = [],
                    root = result.data,
                    len = root.length;
                for (var i = 0; i < len; i++) {
                    if (root[i].typeId.toString().length === 4) {
                        root[i].text = root[i].typeId.toString() + ' —— ' + root[i].typeName;
                        json.push(root[i]);
                        type.loadChildNode(result.data, root[i]);
                    }
                }
                $('#typeTree').treeview({
                    color: "#ca1f16",
                    data: json
                });
            }
        });
        return this;
    },
    selectFirstType: function () {
        $(type.firstType.el).click(function () {
            $.ajax({
                url: type.firstType.ul,
                type: "get",
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    if (result.code === 200) {
                        var firstTypeList = result.data, List = $("#firstTypeList");
                        List.html("");
                        for (var i in firstTypeList) {
                            List.append("<li><a href='javascript:;'>" + firstTypeList[i].typeName + "</a></li>")
                            List.append("<input value='" + firstTypeList[i].typeId + "' style='display: none' name='" + firstTypeList[i].typeName + "'>")
                        }
                        $("#firstTypeList > li > a").click(function () {
                            $("#firstType").val($(this).html())
                        })
                    }
                }
            })
        });
        return this;
    },
    loadFile:function(){
        //todo 类型文件上传 ajax
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
                    url: '/type/uploadExcel',
                    type: 'POST',
                    data: formData,                    // 上传formdata封装的数据
                    cache: false,
                    processData: false,                // jQuery不要去处理发送的数据
                    contentType: false,                // jQuery不要去设置Content-Type请求头
                    success:function (res) {
                        if(res.code === 200){
                            layer.msg(res.message,{icon:1});
                            type.loadTree();
                        }else{
                            layer.msg(res.message,{icon:2});
                        }
                    }
                });
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
    },
    selectSecondType: function () {
        $(type.secondType.el).click(function () {
            var parentType = $("#firstType").val(), List = $("#secondTypeList");
            if (parentType !== undefined && parentType !== "") var parentId = $("[name=" + parentType + "]").val();
            if (parentId === undefined) {
                layer.msg("请选择父类");
                List.html("");
                return;
            }
            $.ajax({
                url: type.addTypeForm.ul + "/" + parentId,
                type: "get",
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    if (result.code === 200) {
                        var secondTypeList = result.data;
                        List.html("");
                        for (var i in secondTypeList) {
                            List.append("<li><a href='javascript:;'>" + secondTypeList[i].typeName + "</a></li>")
                            List.append("<input value='" + secondTypeList[i].typeId + "' style='display: none' name='" + secondTypeList[i].typeName + "'>")
                        }
                        $("#secondTypeList > li > a").click(function () {
                            $("#secondType").val($(this).html())
                        })
                    }
                }
            });
        });
        return this;
    }

};
type.loadTree().checkTypeName().typeFormSubmit().selectFirstType().selectSecondType().loadFile();

