<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>添加公告</title>
    <link href="../static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="../static/js/plugins/layui/css/layui.css" rel="stylesheet">
    <script src="../static/js/jquery.min.js?v=2.1.4"></script>
    <script src="../static/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="../static/js/plugins/layui/layui.all.js"></script>
    <script src="../static/js/plugins/editor/wangEditor.js"></script>
    <style>
        .title_row, #editor, p {
            margin: 10px;
        }

        p {
            color: #ada9a9;
            font-size: 14px;
        }

        label {
            font-size: 14px;
            font-weight: bold;
            letter-spacing: 3px;
            margin-top: 5px;
        }

        #menu, #editor {
            border: 1px solid #ccc;
        }

    </style>
</head>
<body>
<div id="app-body">

    <div class="row title_row">
        <div class="col-md-1"><label for="title">标题：</label></div>
        <div class="col-md-8">
            <input class="form-control" id="title" placeholder="在此输入标题…………" style="width: 90%"/>
        </div>
        <div class="col-md-2">
            <button class="btn btn-info" id="announce">发布</button>
        </div>
    </div>
    <p>公告内容：</p>
    <div id="menu"></div>
    <div style="padding: 2px 0; color: #ccc"></div>
    <div id="editor"></div>
</div>


</body>
<script>
    $(function () {
        $("#editor").css("height", $(window).height() - 200);
        var E = window.wangEditor, editor = new E('#menu', '#editor');
        editor.customConfig.menus = [
            'bold',  // 粗体
            'fontSize',  // 字号
            'fontName',  // 字体
            'italic',  // 斜体
            'underline',  // 下划线
            'strikeThrough',  // 删除线
            'foreColor',  // 文字颜色
            'backColor',  // 背景颜色
            'link',  // 插入链接
            'list',  // 列表
            'justify',  // 对齐方式
            'quote',  // 引用
            'emoticon',  // 表情
            'image',  // 插入图片
            'table',  // 表格
            'undo',  // 撤销
            'redo'  // 重复
        ];
        editor.customConfig.uploadFileName = 'img';
        editor.customConfig.uploadImgServer = '/announcement/img';
        editor.customConfig.zIndex = 1;
        editor.customConfig.uploadImgHooks = {
            success: function (xhr, editor, result) {
                if (result.errno === 0) {
                    layer.msg("图片已上传服务器，建议完成该公告", {icon: 1});
                } else {
                    layer.msg("图片上传失败请重试", {icon: 2});
                }
            }

        };
        editor.customConfig.customAlert = function (info) {
            // info 是需要提示的内容
            layer.msg("图片上传失败：" + info, {icon: 2});
        };
        editor.create();

        document.getElementById('announce').addEventListener('click', function () {
            $.ajax({
                type: 'POST',
                url: "/announcement",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({
                    title: $("#title").val(),
                    content: editor.txt.html()
                }),
                success: function (result) {
                    if (result.code === 200) {
                        $("#title").val('');
                        editor.txt.html('');
                        layer.msg("公告添加成功",{icon:1})
                    }else {
                        layer.msg("公告添加失败",{icon:2})
                    }
                }
            });
        });
    });
</script>
</html>