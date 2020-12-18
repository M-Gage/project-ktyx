<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>客通云销 - 部门树</title>
    <link rel="stylesheet" href="../static/js/plugins/iview/iview.css">
    <link rel="stylesheet" href="../static/css/base.css">
    <script src="../static/js/vue.js"></script>
    <script src="../static/js/plugins/iview/iview.min.js"></script>
    <script src="../static/js/axios.min.js"></script>
    <script src="../static/js/axios-config.js"></script>
    <style>
        #app{
            padding: 0 20px 0 0;
        }
        .ivu-tree-container{
            padding: 0 20px;
            background-color: #fff;
            border: 1px solid #dfdfdf;
            min-height: 550px;
        }
        .ivu-tree-tiles{
            height: 50px;
            line-height: 50px;
            border-bottom: 1px solid #dfdfdf;
        }
        .ivu-deptManager{
            height: 50px;
            padding: 15px 0 10px 20px;
            line-height: 31px;
        }
        .ivu-dept-tree{
            padding-left: 30px;
        }
        .ivu-tree-option{
            display: none;
        }
        .tree-content{
            padding-left: 25px;
        }
    </style>
</head>
<body>
    <div id="app" v-cloak>
        <row>
            <i-col span="4">
                <div class="ivu-tree-container" :style="{height: windowHeight+'px'}">
                    <div class="ivu-tree-tiles">
                        <div class="pull-left">
                            <Icon type="network"></Icon>
                            部门树
                        </div>
                        <div class="pull-right" style="text-align: right">
                            <a href="#" @click="addDept">
                                <Icon type="plus-round"></Icon>
                                添加部门
                            </a>
                        </div>
                    </div>
                    <Spin fix v-show="tree.loading"></Spin>
                    <Tree :data="tree.data" @on-select-change="treeSelect"></Tree>
                    <input type="hidden" ref="deptId" value='${staff.deptId}'>
                </div>
            </i-col>
            <i-col span="20">
                <row>
                    <i-col span="24">
                        <div class="ivu-deptManager">
                            <div class="pull-left">
                                <span style="margin-right: 30px">
                                    部门名称 <tag color="blue">{{deptName}}</tag>
                                </span>
                                <span v-if="deptManagerNameList.length > 0">
                                    部门负责人 <tag color="blue" v-for="(item,index) in deptManagerNameList" :key="index">{{item.staffName}}</tag>
                                </span>
                            </div>
                            <div class="pull-right">
                                <i-button class="pull-left" style="margin-right: 10px" type="primary" @click="distributionDept">部门分配</i-button>
                                <i-button class="pull-left" @click="uploadStaff" type="info">导入职员</i-button>
                            </div>
                        </div>
                    </i-col>
                    <i-col span="24" style="margin-top: 15px">
                        <i-table
                            border
                            @on-selection-change="tableSelect"
                            size="small"
                            width="100%"
                            :height="windowHeight-120"
                            :columns="table.columns"
                            :data="table.data"
                            :loading="table.loading"
                            no-data-text="暂无数据！请选择左侧的部门"
                        ></i-table>
                        <div style="float: right;margin-top: 10px">
                            <Page ref="pages" :total="table.total" :page-size="20" :page-size-opts="[20,40,60]" placement="top"
                                  @on-change="changePage" @on-page-size-change="changePageSize" show-sizer show-total></Page>
                        </div>
                    </i-col>
                </row>
            </i-col>
        </row>
        <Modal
            v-model="modal.addDept"
            title="添加部门"
            :mask-closable="false"
        >
            <div slot="footer">
                <i-button type="primary" class="pull-right" :loading="btn.addDept" @click="addDeptSubmit">提交</i-button>
                <div class="clear"></div>
            </div>
            <i-form :label-width="80" ref="addDept" :model="dept" :rules="validate.addDept">
                <Form-item label="部门名称" prop="deptName">
                    <i-input type="text" v-model="dept.deptName" clearable placeholder="部门名称"></i-input>
                </Form-item>
            </i-form>
        </Modal>
        <Modal
            v-model="modal.distributionDept"
            title="部门分配"
            width="400"
            :mask-closable="false"
        >
            <div slot="footer">
                <i-button type="primary" class="pull-right" :loading="btn.distributionDept"  @click="distributionDeptSubmit">提交</i-button>
                <div class="clear"></div>
            </div>
            <i-form :label-width="80" ref="distributionDept" :model="distribution">
                <Form-item prop="deptId" label="部门" :rules="{required: true, message: '必须选择部门', trigger: 'change'}">
                    <i-select v-model="distribution.deptId">
                        <i-option v-for="item in tree.optionList" :value="item.deptId" :key="item.deptId" class="ivu-tree-option">{{item.deptName}}</i-option>
                        <Tree class="ivu-dept-tree" :data="tree.selectData" @on-select-change="distributionTreeSelect"></Tree>
                    </i-select>
                </Form-item>
            </i-form>
        </Modal>
        <Modal
            v-model="modal.uploadExcel"
            title="职员导入"
            width="300"
            :mask-closable="false"
        >
            <div slot="footer">
                <a href="/file/downFile/staff.xls" style="display: block;margin-bottom: 10px">下载职员导入模板</a>
            </div>
            <h3>导入说明:</h3>
            <p style="text-indent: 10px">导入的职员都是归属在选择的部门下</p>
            <p style="text-indent: 10px">导入模板数据列都是必填项</p>
            <p style="text-indent: 10px">请务必填写完整，以确保数据完整性</p>
            <Upload
                type="drag"
                :format="['xls','xlsx']"
                :data="uploadData"
                :show-upload-list="false"
                :on-success="uploadStaffSuccess"
                :on-format-error="formatError"
                style="margin-top: 10px"
                action="/staff/uploadExcel">
                <div style="padding: 20px 0">
                    <icon type="ios-cloud-upload" size="52" style="color: #3399ff"></icon>
                    <p>点击或拖动文件来上传</p>
                </div>
            </Upload>
        </Modal>
    </div>
</body>
<script>
    new Vue({
        el: "#app",
        data() {
            const validateName = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('必须输入部门名称'));
                }
                if (value.length > 20) {
                    return callback(new Error('部门名称不能超过20位！'));
                }
                axios.get('/dept/findName/' + this.dept.deptId + '/' + this.dept.deptName).then(res => {
                    if (res.data) {
                        callback(new Error('该部门下已存在该名称的部门！'));
                    } else {
                        callback();
                    }
                });
            };
            return {
                tree: {
                    initData: [],
                    data: [],
                    selectData: [],
                    optionList: [],
                    loading: false
                },
                table: {
                    data:[],
                    columns:[
                        {
                            type: 'index',
                            width: 60,
                            align: 'center',
                        },
                        {
                            type:"selection",
                            width: 60
                        },
                        {
                            title:"职员姓名",
                            align: 'center',
                            key:"staffName",
                        },
                        {
                            title:"职员电话",
                            align: 'center',
                            key:"phone",
                        },
                        {
                            title:"部门",
                            align: 'center',
                            key:"deptName",
                        },
                        {
                            title:"角色",
                            align: 'center',
                            key:"roleName"
                        },
                        {
                            title:"是否管理员",
                            width: 100,
                            align: 'center',
                            render:(h,params) => {
                                return h('div', params.row.isDeptManager === 1 ? "是" : "否");
                            }
                        }
                    ],
                    loading: false,
                    total: 0,
                    param:{
                        pageSize: 20,
                        pageNumber: 1,
                    },
                    selected:[]
                },
                deptManagerNameList: [],
                deptName: '暂未选择部门',
                uploadData:{
                    deptId: "",
                    deptName:"",
                },
                modal:{
                    addDept:false,
                    distributionDept:false,
                    uploadExcel:false
                },
                dept:{
                    deptId:'',
                    deptName:'',
                },
                distribution:{
                    deptId:''
                },
                validate:{
                    addDept:{
                        deptName:[
                            { required: true, validator: validateName, trigger: 'blur' }
                        ]
                    }
                },
                btn:{
                    addDept: false,
                    distributionDept: false
                },
                windowHeight: ''
            }
        },
        methods: {
            getTreeData() {
                this.tree.loading = true;
                axios.get("/dept/list").then(res => {
                    if (res.code === 200) {
                        this.tree.optionList = res.data;
                        let root = [];
                        const data = JSON.parse(JSON.stringify(res.data).replace(/deptName/g, 'title'));
                        data.forEach(item => {
                            if (item.deptId.length === this.$refs['deptId'].value.length) {
                                item.expand = true;
                                root.push(item);
                                loadChildNode(data, item)
                            }
                        });
                        function loadChildNode(data, node) {
                            let reg = new RegExp("^" + node.deptId + "..$");
                            data.forEach((item, index) => {
                                if (reg.test(item.deptId)) {
                                    if (!node.children) {
                                        node.children = [];
                                    }
                                    item.expand = true;
                                    node.children.push(item);
                                    loadChildNode(data, item);
                                }
                            });
                        }
                        this.tree.data = JSON.parse(JSON.stringify(root));
                        this.tree.initData = JSON.parse(JSON.stringify(root));
                        this.tree.selectData = JSON.parse(JSON.stringify(root));
                        this.tree.loading = false;
                    } else {
                        this.$Message.error(res.message);
                        this.tree.loading = false;
                    }
                }).catch(err => {
                    this.$Message.error('获取部门列表失败');
                    this.tree.loading = false;
                })
            },
            treeSelect(node) {
                if (node.length !== 0) {
                    this.table.param.pageNumber = 1;
                    this.$refs["pages"].currentPage = 1;
                    this.dept.deptId = node[0].deptId;
                    this.deptName = node[0].title;
                    //导入职员需要的参数
                    this.uploadData.deptId = node[0].deptId;
                    let deptNames = this.deptNames(this.uploadData.deptId);
                    this.uploadData.deptName = deptNames.indexOf(">") === -1 ? deptNames: deptNames.substring(0 , deptNames.length-1);
                    this.getTable();
                } else {
                    this.dept.deptId = '';
                    this.deptName = '暂未选择部门';
                    this.uploadData.deptId = '';
                    this.uploadData.deptName = '';
                    this.table.data = [];
                    this.deptManagerNameList = [];
                }
            },
            getTable() {
                this.table.loading = true;
                if (this.table.selected.length !== 0) {
                    this.table.selected = [];
                }
                axios.get('/staff/list/' + this.dept.deptId,{params:this.table.param}).then(res => {
                    if (res.code === 200) {
                        this.deptManagerNameList = res.data.rows.filter(item => item.deptId === this.dept.deptId && item.isDeptManager === 1);
                        this.table.data = res.data.rows;
                        this.table.total = res.data.total;
                        this.table.loading = false;
                    } else {
                        this.$Message.error(res.message);
                        this.table.loading = false;
                    }
                }).catch(err => {
                    this.$Message.error('获取职员列表数据失败');
                    this.table.loading = false;
                })
            },
            tableSelect(data) {
                this.table.selected = data;
            },
            changePage (num) {
                this.table.param.pageNumber = num;
                this.getTable();
            },
            changePageSize(size){
                this.table.param.pageSize = size;
                this.getTable();
            },
            addDept(){
                if (this.dept.deptId !=='') {
                    this.modal.addDept = true;
                } else {
                    this.$Notice.error({title: '添加部门必须先选择上级部门！'})
                }
            },
            addDeptSubmit() {
                this.btn.addDept = true;
                this.$refs['addDept'].validate(result => {
                    if (!result) {
                        return this.btn.addDept = false;
                    }
                    axios.post('/dept',this.dept).then(res => {
                        if (res.code === 200) {
                            this.modal.addDept = false;
                            this.$Notice.success({title: '添加成功'});
                            this.btn.addDept = false;
                            this.resetForm('addDept');
                            this.getTreeData();
                        } else {
                            this.$Notice.error({title: res.message})
                        }
                    }).catch(err => {
                        this.$Notice.error({title: '添加部门失败！'})
                    })
                });
            },
            distributionDept () {
                if (this.table.selected.length !== 0) {
                    //每次都重新加载数据
                    this.tree.selectData = JSON.parse(JSON.stringify(this.tree.initData));
                    this.distribution.deptId = '';
                    this.resetForm('distributionDept');
                    this.modal.distributionDept = true;
                } else {
                    this.$Notice.error({title: '必须要选择职员！'})
                }
            },
            distributionTreeSelect (node) {
                if (node.length !== 0) {
                    this.distribution.deptId = node[0].deptId;
                } else {
                    this.distribution.deptId = '';
                }
            },
            deptNames (deptId){
                let name = '';
                let parentId = '';
                let data = this.tree.optionList;
                if (deptId === 4) {
                    name = this.tree.optionList.filter(value => value.deptId === deptId)[0].deptName
                } else {
                    findParent(deptId)
                }
                function findParent (deptId) {
                    data.forEach(item => {
                        if (item.deptId === deptId && item.deptId.length !== 4) {
                            parentId = deptId.substring(0, deptId.length - 2);
                            name += '>' + item.deptName;
                            findParent(parentId)
                        }
                    })
                }
                return name.split('>').reverse().join('>');
            },
            distributionDeptSubmit () {
                this.btn.distributionDept = true;
                this.$refs['distributionDept'].validate(result => {
                    if (!result) {
                        return this.btn.distributionDept = false;
                    }
                    let staffIdArray = [];
                    let deptNames = this.deptNames(this.distribution.deptId);
                    //计算属性
                    let deptName = deptNames.indexOf('>') === -1 ? deptNames : deptNames.substring(0, deptNames.length - 1);
                    this.table.selected.forEach(s => {
                        staffIdArray.push(s.staffId)
                    });
                    axios.put('/staff/list/' + this.distribution.deptId + '/' + deptName, staffIdArray).then(res => {
                        if (res.code === 200) {
                            this.btn.distributionDept = false;
                            this.$Notice.success({title: '职员分配成功！'});
                            this.getTable();
                            this.modal.distributionDept = false;
                        } else {
                            this.$Notice.error({title: res.message})
                        }
                    }).catch(err => {
                        this.$Notice.error({title: '职员分配失败！'})
                    });
                });
            },
            uploadStaff () {
                if (this.uploadData.deptId !=='' && this.uploadData.deptName !== '') {
                    this.modal.uploadExcel = true;
                } else {
                    this.$Notice.error({title: '必须选择部门后再点击导入！'})
                }
            },
            uploadStaffSuccess(response, file, fileList){
                if (response.code === 200) {
                    this.modal.uploadExcel = false;
                    this.getTable();
                    setTimeout(()=>{
                        this.$Notice.success({title: response.message})
                    },500)
                } else {
                    this.$Notice.error({title: response.message})
                }
            },
            formatError(file, fileList){
                this.$Notice.error({title: "文件格式不正确！请选择excel文件"})
            },
            resetForm (formName) {
                this.$refs[formName].resetFields();
            },
        },
        mounted(){
            this.windowHeight = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight);
        },
        created(){
            this.getTreeData();
        }
    })
</script>
</body>
</html>
