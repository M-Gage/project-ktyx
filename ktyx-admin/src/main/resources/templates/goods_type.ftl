<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>添加分类</title>
    <link rel="shortcut icon" href="favicon.ico">
    <link href="../static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="../static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="../static/css/animate.css" rel="stylesheet">
    <link href="../static/css/style.css?v=4.1.0" rel="stylesheet">

    <style>
        #typeTree{
            max-height: 400px;
            overflow-y: auto;
        }
        label{
            white-space:nowrap;
            text-overflow:ellipsis;
            overflow: hidden;
        }
        #firstTypeList,#secondTypeList{
            max-height: 400px;
            overflow-y: auto;
        }
    </style>
</head>

<body class="gray-bg">

<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h2>商品分类管理</h2>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-sm-2">
                                <button type="button"
                                        class="btn btn-primary" id="upload">商品类型导入
                                </button>
                                <input type="file" id="fileInput" style="display: none">
                            </div>
                            <div class="col-sm-3">
                                <a href="/file/downFile/classify.xls" class="btn btn-primary">下载商品类型导入模板</a>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <!-- 分类录入 -->
                            <form class="form-horizontal m-t" id="addTypeForm">
                                <div class="form-group">
                                    <div class="col-xs-11">
                                        <label class="col-xs-1 control-label text-right" for="firstType">一级类目:</label>
                                        <div class="col-xs-3">
                                            <div class="input-group">
                                                <input type="text" class="form-control" id="firstType"
                                                       placeholder="输入一级目录" aria-label="...">
                                                <div class="input-group-btn">
                                                    <button type="button" id="firstTypeButton"
                                                            class="btn btn-default dropdown-toggle"
                                                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                        <span class="caret"></span></button>
                                                    <ul class="dropdown-menu dropdown-menu-right full-width"
                                                        id="firstTypeList">
                                                    </ul>
                                                </div><!-- /btn-group -->
                                            </div><!-- /input-group -->
                                        </div>
                                        <label class="col-xs-1 text-right control-label" for="secondType">二级类目:</label>
                                        <div class="col-xs-3">
                                            <div class="input-group">
                                                <input type="text" class="form-control" id="secondType"
                                                       placeholder="输入二级目录" aria-label="...">
                                                <div class="input-group-btn">
                                                    <button type="button" id="secondTypeButton"
                                                            class="btn btn-default dropdown-toggle"
                                                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                        <span class="caret"></span></button>
                                                    <ul class="dropdown-menu dropdown-menu-right full-width"
                                                        id="secondTypeList">
                                                    </ul>
                                                </div><!-- /btn-group -->
                                            </div><!-- /input-group -->
                                        </div>

                                        <label class="col-xs-1 text-right control-label" for="thirdType">三级类目:</label>
                                        <div class="col-xs-3">
                                            <input type="text" class="form-control" id="thirdType"
                                                   placeholder="输入三级目录（前面目录填满）">
                                        </div>
                                    </div>
                                    <div class="col-xs-1">
                                        <button class="btn btn-primary" type="submit">添加</button>
                                    </div>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-6">
            <div class="ibox-title">
                <h5>分类列表</h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <div id="typeTree" class="test"></div>
            </div>
        </div>


        <div class="col-sm-6">
            <div class="ibox-title">
                <h5>分类管理</h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>

            <div class="ibox-content">
                <div class="row" style="padding-top: 10px">
                    <label class="col-xs-3 text-right control-label" for="TypeName">类目名称:</label>
                    <div class="col-xs-5">
                        <input type="text" class="form-control" id="TypeName" placeholder="输入分类名称">
                    </div>
                </div>
                <div class="row" style="padding-top: 10px">
                    <label class="col-xs-3 text-right control-label" for="NewTypeName">新类目名称:</label>
                    <div class="col-xs-5">
                        <input type="text" class="form-control" id="NewTypeName"
                               placeholder="输入新的分类名称" onkeyup="change()">
                    </div>
                </div>
                <div class="row" style="padding-top: 10px">
                    <div class="col-xs-offset-6 col-xs-2 " >
                        <button id="updateType" class="btn btn-danger" onclick="updateType()">删除</button>
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

    <!-- treeview -->
    <script src="../static/js/plugins/treeview/bootstrap-treeview.js"></script>
    <script src="../static/js/plugins/treeview/bootstrap-combotree.js"></script>

    <!--layUI-->
    <script src="../static/js/plugins/layui/layui.all.js"></script>

    <!-- 自定义js -->
    <script src="../static/js/content.js?v=1.0.0"></script>
    <script src="../static/js/user/type.js"></script>
    <script>
        function change() {
            var ntn = $("#NewTypeName").val();
            $("#updateType").html("修改");
            if ( ntn.length < 1||ntn === ""  ) {
                $("#updateType").html("删除");
            }
        }
        function updateType() {
            $.ajax({
                url: '/type',
                type: 'PUT',
                data: {
                    typeName:$("#TypeName").val(),
                    newTypeName:$("#NewTypeName").val()
                },
                success:function (res) {
                    if(res.code=== 200){
                        if (res.data === 1) {
                            layer.msg("修改或删除成功",{icon:1});
                        }else {
                            layer.msg("修改或删除失败",{icon:2});
                        }
                    }else{
                        layer.msg("分类已经被关联不能删除",{icon:2});
                    }
                    type.loadTree();
                }
            });
        }
    </script>
</div>
</body>
</html>
<!--
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>通客云销-商品类型管理</title>
    <link rel="stylesheet" href="../static/js/iview/iview.css">
    <style>
        [v-cloak]{
            display:none
        }
        ::-webkit-scrollbar {
            width: 3px;
            background-color: #F5F5F5;
        }
        ::-webkit-scrollbar-thumb {
            background-color: #2f4050;
        }
        .ivu-type-tree-content{
            border-right: 1px solid #dfdfdf;
            padding: 0 0 0 20px;
        }
        .ivu-type-tree {
            overflow-y: auto;
        }
        .ivu-type-title{
            height: 40px;
            line-height: 40px;
            border-bottom: 1px solid #dfdfdf;
            padding: 0 15px 0 15px;
        }
    </style>
</head>
<body>
<div id="admin" v-cloak>
    <row>
        <i-col span="4" class="ivu-type-tree-content">
            <row>
                <i-col span="24">
                    <div class="ivu-type-title">
                        <span><Icon type="network"></Icon>&nbsp;&nbsp;商品分类树</span>
                        <div style="float: right;">
                            <a href="#">导入</a>
                            <a href="#" style="margin-left: 10px;">导出</a>
                        </div>
                    </div>
                </i-col>
                <i-col span="24">
                    <tree class="ivu-type-tree"
                          show-checkbox
                          :data="typeData"
                          @on-check-change="typeTreeSelect"
                          :style="{maxHeight: (winHeight-50)+'px'}"
                    ></tree>
                </i-col>
            </row>
        </i-col>
        <i-col span="20" style="padding: 0 20px">
            <row>
                <i-col span="24" style="padding-top: 20px;">
                    <i-form label-position="right" :label-width="80">
                        <row>
                            <i-col span="6">
                                <Form-Item label="一级">
                                    <i-input></i-input>
                                </Form-Item>
                            </i-col>
                            <i-col span="6">
                                <Form-Item label="二级">
                                    <i-input></i-input>
                                </Form-Item>
                            </i-col>
                            <i-col span="6">
                                <Form-Item label="三级">
                                    <i-input></i-input>
                                </Form-Item>
                            </i-col>
                            <i-col span="6">
                                <Form-Item>
                                    <i-button type="primary">添加</i-button>
                                </Form-Item>
                            </i-col>
                        </row>
                    </i-form>
                </i-col>
                <i-col span="24">
                    <Transfer
                        :data="transferData"
                        :target-keys="targetKeys"
                        :list-style="listStyle"
                        filterable
                        @on-change="onChange">
                    </Transfer>
                </i-col>
            </row>
        </i-col>
    </row>
</div>
</body>
&lt;!&ndash; 全局js &ndash;&gt;
<script src="../static/js/vue.js"></script>
<script src="../static/js/iview/iview.min.js"></script>
<script src="../static/js/axios.min.js"></script>
<script type="text/javascript">
    new Vue({
        el:"#admin",
        data(){
            return{
                winHeight:'',
                typeData:[],
                listStyle:{
                    width: '47.91%',
                    height: '',
                },
                transferData: [],
                targetKeys: [],
                staffList:[],
            }
        },
        methods:{
            getTypeTree(){
                axios.get("/type").then(res=>{
                    let json = [],
                        root = res.data.data,
                        len = root.length;
                    for (let i = 0; i < len; i++) {
                        if(root[i].typeId.toString().length === 4){
                            root[i].title = root[i].typeName;
                            root[i].expand = true;
                            json.push(root[i]);
                            loadChildNode(res.data.data, root[i]);
                        }
                    }
                    function loadChildNode (root, node) {
                        let reg = new RegExp("^" + node.typeId + "..$"),
                            len = root.length;
                        for (let i = 0; i < len; i++) {
                            if (reg.admin(root[i].typeId.toString())) {
                                if (!node.children) {
                                    node.children = [];
                                }
                                node.children.push(root[i]);
                                root[i].title = root[i].typeName;
                                root[i].expand = true;
                                loadChildNode(root, root[i]);
                            }
                        }
                    }
                    this.typeData = JSON.parse(JSON.stringify(json));
                })
            },


            typeTreeSelect(value){
                if(value.length !== 0){
                    this.transferData = JSON.parse(JSON.stringify(this.staffList));
                    this.targetKeys = [];
                    this.staffList.forEach((k) => {
                        if (k['staffTypes'].length !== 0) {
                            k['staffTypes'].forEach((o) => {
                                value.forEach(v=>{
                                    if (o.typeId === v.typeId) {
                                        this.targetKeys.push(k.key)
                                    }
                                });
                            })
                        }
                    });
                }else{
                    this.transferData = [];
                    this.targetKeys = [];
                }
            },
            getStaffList(){
                axios.get("/staff/subordinates",{params:{typeFlag:true}}).then(res=>{
                    let s = JSON.stringify(res.data.data).replace(/staffId/g,'key').replace(/staffName/g,'label');
                    this.staffList = JSON.parse(s);
                })
            },
            onChange (newTargetKeys) {
                this.targetKeys = newTargetKeys;
            }
        },
        created(){
            this.getTypeTree();
            this.getStaffList();
            this.winHeight = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight);
            this.listStyle.height = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 77 +"px";
        },
        watch:{

        }
    })
</script>
</html>-->
