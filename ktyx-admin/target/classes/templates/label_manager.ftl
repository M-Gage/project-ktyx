<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>客通云销 - 标签管理</title>
    <link rel="stylesheet" href="../static/js/plugins/iview/iview.css">
    <link rel="stylesheet" href="../static/css/base.css">
    <script src="../static/js/vue.js"></script>
    <script src="../static/js/plugins/iview/iview.min.js"></script>
    <script src="../static/js/axios.min.js"></script>
    <script src="../static/js/axios-config.js"></script>
</head>
<body>
    <div id="app" v-cloak>
        <row>
            <i-col span="24" style="margin-bottom: 10px">
                <i-button type="primary" @click="modal.addLabel = true">添加标签</i-button>
            </i-col>
            <i-col span="24">
                <i-table
                    border
                    :columns="table.columns"
                    :data="table.data"
                    :height="windowHeight - 130"
                    size="small"
                    :loading="table.loading"
                ></i-table>
            </i-col>
            <i-col span="24">
                <div style="float: right;margin-top: 10px">
                    <Page ref="pages" :total="table.total" :page-size="20" :page-size-opts="[20,40,60]" placement="top"
                          @on-change="changePage" @on-page-size-change="changePageSize" show-sizer show-total></Page>
                </div>
            </i-col>
        </row>
        <Modal
            v-model="modal.addLabel"
            title="添加标签"
            width="400"
            :mask-closable="false"
        >
            <div slot="footer">
                <i-button type="primary" class="pull-right" :loading="btn.addLabel"  @click="addLabel">提交</i-button>
                <div class="clear"></div>
            </div>
            <div style="margin-bottom: 10px">
                <h3><span style="color: red">* </span>说明</h3>
                <p style="text-indent: 20px">添加的默认为公有标签</p>
                <p style="text-indent: 20px">如果需要添加的标签名称已经在私有标签中存在</p>
                <p style="text-indent: 20px">继续添加的话私有标签将会升级为公有标签</p>
            </div>
            <i-form ref="addLabel" :model="label" :label-width="80" :rules="validate.addLabel">
                <form-item label="标签名称" prop="labelName">
                    <i-input type="text" v-model="label.labelName"></i-input>
                </form-item>
            </i-form>
        </Modal>
        <Modal
            v-model="modal.updateLabel"
            title="修改标签"
            width="400"
            :mask-closable="false"
        >
            <div slot="footer">
                <i-button type="primary" class="pull-right" :loading="btn.updateLabel"  @click="updateLabelSubmit">提交</i-button>
                <div class="clear"></div>
            </div>
            <i-form ref="updateLabel" :model="updateLabel" :label-width="80" :rules="validate.addLabel">
                <form-item label="标签名称" prop="labelName">
                    <i-input type="text" v-model="updateLabel.labelName"></i-input>
                </form-item>
            </i-form>
        </Modal>
    </div>
</body>
<script type="text/javascript">
    new Vue({
        el:"#app",
        data () {
            const validateName = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('必须输入标签名称'));
                }
                //修改私人标签
                if (this.modal.updateLabel && this.updateLabel.isPrivate === 1) {
                    axios.get('/label/findPrivateName',{
                        params: {
                                    labelName: value,
                                    staffId: this.updateLabel.staffId
                                }
                    }).then(res => {
                        if (res.data) {
                            callback(new Error('标签名称已存在！'));
                        } else {
                            callback();
                        }
                    });
                } else {
                    axios.get('/label/findName',{params:{labelName:value}}).then(res => {
                        if (res.data) {
                            callback(new Error('标签名称已存在！'));
                        } else {
                            callback();
                        }
                    });
                }
            };
            return {
                table:{
                    data:[],
                    columns: [
                        {
                            type: 'index',
                            align: 'center',
                            width: 60
                        },
                        {
                            title: '标签名称',
                            key: 'labelName',
                            align: 'center'
                        },
                        {
                            title: '类型',
                            key: 'isPrivate',
                            align: 'center',
                            render:(h,params) => {
                                let color = 'blue';
                                let text = "公有";
                                if (params.row.isPrivate === 1) {
                                    color = 'green';
                                    text = "私有";
                                }
                                return h('Tag', {
                                    props:{
                                        color: color
                                    }
                                },text);
                            }
                        },
                        {
                            title: '创建人',
                            key: 'staffName',
                            align: 'center'
                        },
                        {
                            title: '创建时间',
                            key: 'createTime',
                            align: 'center'
                        },
                        {
                            title: "操作",
                            align: 'center',
                            width: 90,
                            render: (h, params) => {
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'primary',
                                            size: 'small'
                                        },
                                        on: {
                                            click: () => {
                                                this.getLabel(params.index);
                                            }
                                        }
                                    }, '编辑')
                                ]);
                            }
                        },
                    ],
                    loading: false,
                    total: 0,
                    param:{
                        pageSize: 20,
                        pageNumber: 1,
                    },
                },
                label:{
                    labelName:''
                },
                updateLabel:{},
                windowHeight:'',
                modal:{
                    addLabel: false,
                    updateLabel: false
                },
                btn:{
                    addLabel:false,
                    updateLabel:false
                },
                validate:{
                    addLabel: {
                        labelName:[
                            {required: true, validator: validateName, trigger: 'blur' }
                        ]
                    }
                }
            }
        },
        methods:{
            getTable(){
                this.table.loading = true;
                axios.get("/label/detailList",{params:this.table.param}).then(res => {
                    if (res.code === 200) {
                        this.table.data = res.data.rows;
                        this.table.total = res.data.total;
                    } else {
                        this.$Notice.error({title: res.message});
                    }
                    this.table.loading = false;
                }).catch(err => {
                    this.$Notice.error({title: "获取标签列表失败！"});
                    this.table.loading = false;
                });
            },
            changePage (num) {
                this.table.param.pageNumber = num;
                this.getTable();
            },
            changePageSize(size){
                this.table.param.pageSize = size;
                this.getTable();
            },
            addLabel(){
                this.btn.addLabel = true;
                this.$refs['addLabel'].validate(result => {
                    if (!result) {
                        return this.btn.addLabel = false;
                    }
                    axios.post("/label", this.label).then(res => {
                        if (res.code === 200) {
                            setTimeout(() => {
                                this.$Notice.success({title: "添加成功"});
                            }, 400);
                            this.modal.addLabel = false;
                            this.$refs['addLabel'].resetFields();
                            this.getTable();
                        } else {
                            this.$Notice.error({title: res.message});
                        }
                        this.btn.addLabel = false;
                    }).catch(err => {
                        this.$Notice.error({title: "添加失败！"});
                        this.btn.addLabel = false;
                    });
                })
            },
            getLabel(value){
                this.$refs['updateLabel'].resetFields();
                this.modal.updateLabel = true;
                this.updateLabel = JSON.parse(JSON.stringify(this.table.data[value]));
            },
            updateLabelSubmit(){
                this.btn.updateLabel = true;
                this.$refs['updateLabel'].validate(result => {
                    if (!result){
                        return this.btn.updateLabel = false;
                    }
                    axios.put('/label',this.updateLabel).then(res =>{
                        if (res.code === 200){
                            this.modal.updateLabel = false;
                            this.getTable();
                            setTimeout(() => {
                                this.$Notice.success({title: "修改成功"});
                            }, 400);
                        } else {
                            this.$Notice.error({title: res.message});
                        }
                        this.btn.updateLabel = false;
                    }).catch(err=>{
                        this.$Notice.error({title: "修改失败！"});
                    })
                })
            }
        },
        created(){
            this.getTable();
        },
        mounted(){
            this.windowHeight = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight);
        }
    })
</script>
</html>