<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>客通云销 - 等级管理</title>
    <link rel="stylesheet" href="../static/js/plugins/iview/iview.css">
    <link rel="stylesheet" href="../static/css/base.css">
    <script src="../static/js/vue.js"></script>
    <script src="../static/js/plugins/iview/iview.min.js"></script>
    <script src="../static/js/axios.min.js"></script>
    <script src="../static/js/axios-config.js"></script>
</head>
<body>
<div id="app" v-cloak>
    <row :gutter="30">
        <i-col span="6">
            <i-form :label-width="130" ref="updateExpConfig" :model="experienceConfig" :rules="validate.experienceConfig">
                <form-item label="添加客户1/个 : 得" prop="insertCustomer">
                    <i-input :number='true' v-model='experienceConfig.insertCustomer' type="text"></i-input>
                </form-item>
                <form-item label="报计划1/单 : 得" prop="insertOrder">
                    <i-input :number='true' v-model='experienceConfig.insertOrder' type="text"></i-input>
                </form-item>
                <form-item label="添加跟进1/条 : 得" prop="insertFollow">
                    <i-input :number='true' v-model='experienceConfig.insertFollow' type="text"></i-input>
                </form-item>
                <form-item label="跟进(获赞)1/条 : 得" prop="followLike">
                    <i-input :number='true' v-model='experienceConfig.followLike' type="text"></i-input>
                </form-item>
                <form-item label="跟进(获踩)1/条 : 扣" prop="followDislike">
                    <i-input :number='true' v-model='experienceConfig.followDislike' type="text"></i-input>
                </form-item>
                <form-item>
                    <i-button type="primary" @click="updateExpConfig" :loading="btn.updateExpConfig">提交</i-button>
                </form-item>
            </i-form>
        </i-col>
        <i-col span="18">
            <i-button type="primary" @click="modal.addGrade = true">添加</i-button>
            <i-table :columns="table.columns" :data="table.data"></i-table>
        </i-col>
        <Modal
            v-model="modal.addGrade"
            title="添加等级"
            width="400"
            :mask-closable="false"
        >
            <div slot="footer">
                <i-button type="primary" class="pull-right" @click="addGrade">提交</i-button>
                <div class="clear"></div>
            </div>
            <i-input type="text" v-model="grade.gradeExp" placeholder="请输入经验值"></i-input>
        </Modal>
    </row>
</div>
</body>
<script type="text/javascript">
    new Vue({
        el:"#app",
        data () {
            return {
                experienceConfig: {
                    insertCustomer: 0,
                    insertOrder: 0,
                    insertFollow: 0,
                    followLike: 0,
                    followDislike: 0
                },
                btn:{
                    updateExpConfig: false,
                    addGrade: false,
                },
                validate:{
                    experienceConfig:{
                        insertCustomer:[
                            {required: true, message: '必须填写整数！', type:'number', trigger: 'blur'},
                            {pattern: /^\d+$/, message: '必须为整数！'}
                        ],
                        insertOrder:[
                            {required: true, message: '必须填写整数！', type:'number', trigger: 'blur'},
                            {pattern: /^\d+$/, message: '必须为整数！'}
                        ],
                        insertFollow:[
                            {required: true, message: '必须填写整数！', type:'number', trigger: 'blur'},
                            {pattern: /^\d+$/, message: '必须为整数！'}
                        ],
                        followLike:[
                            {required: true, message: '必须填写整数！', type:'number', trigger: 'blur'},
                            {pattern: /^\d+$/, message: '必须为整数！'}
                        ],
                        followDislike:[
                            {required: true, message: '必须填写整数！', type:'number', trigger: 'blur'},
                            {pattern: /^\d+$/, message: '必须为整数！'}
                        ],
                    }
                },
                grade:{
                    gradeExp: "",
                },
                table: {
                    data: [],
                    columns: [
                        {
                            title: '等级',
                            align: 'center',
                            key: 'gradeName'
                        },
                        {
                            title: '经验值',
                            align: 'center',
                            key: 'gradeExp'
                        },
                        {
                            title: '创建时间',
                            align: 'center',
                            key: 'createTime'
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
                                                this.updateGrade(params.index)
                                            }
                                        }
                                    }, '编辑')
                                ]);
                            }
                        }
                    ]
                },
                modal:{
                    addGrade: false,
                    updateGrade: false
                }
            }
        },
        methods:{
            getTable(){
                axios.get("/grade").then(res=>{
                    if (res.code === 200) {
                        this.table.data = res.data;
                    } else {
                        this.$Message.error(res.message)
                    }
                }).catch(err=>{
                    this.$Message.error("获取等级数据失败！");
                })
            },
            getExpConfig(){
                axios.get("/experienceConfig").then(res=>{
                    if (res.code === 200) {
                        this.experienceConfig = res.data;
                    } else {
                        this.$Message.error(res.message)
                    }
                }).catch(err=>{
                    this.$Message.error("获取经验值数据失败！");
                })
            },
            updateExpConfig() {
                this.btn.updateExpConfig = true;
                this.$refs["updateExpConfig"].validate(result=>{
                    if (!result) {
                        return this.btn.updateExpConfig = false;
                    }
                    axios.put("/experienceConfig",this.experienceConfig).then(res=>{
                        if (res.code === 200) {
                            this.$Message.success("修改成功");
                        }else {
                            this.$Message.error(res.message);
                        }
                        this.btn.updateExpConfig = false;
                    }).catch(err=>{
                        this.$Message.error("经验值修改失败！");
                        this.btn.updateExpConfig = false;
                    })
                })
            },
            addGrade(){
                axios.post("/grade",this.grade).then(res=>{
                    if (res.code === 200) {
                        this.$Message.success("添加成功");
                        this.getTable();
                    } else {
                        this.$Message.error(res.message);
                    }
                })
            },
            updateGrade(index){
                console.log(this.table.data[index]);
            }
        },
        created(){
            this.getTable();
            this.getExpConfig();
        },
        mounted(){
        }
    })
</script>
</html>