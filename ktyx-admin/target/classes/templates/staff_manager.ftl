<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>客通云销 - 职员管理</title>
    <link rel="stylesheet" href="../static/js/plugins/iview/iview.css">
    <link rel="stylesheet" href="../static/css/base.css">
    <script src="../static/js/vue.js"></script>
    <script src="../static/js/plugins/iview/iview.min.js"></script>
    <script src="../static/js/axios.min.js"></script>
    <script src="../static/js/axios-config.js"></script>
    <style>
        .table-top{
            margin-bottom: 5px;
        }
        .ivu-page-options-sizer{
            margin-right: 0;
        }
    </style>
</head>
<body>
<div id="app" v-cloak>
    <Row>
        <i-col span="24" class="table-top">
            <i-col span="10">
                <row :gutter="16">
                    <i-col span="10">
                        <i-input type="text" v-model="table.searchParam.keyWord" clearable placeholder="关键字"></i-input>
                    </i-col>
                    <i-col span="5">
                        <i-button long type="primary" @click="tableSearch">查询</i-button>
                    </i-col>
                    <i-col span="5">
                        <i-button long type="dashed" @click="complexSearch">高级查询</i-button>
                    </i-col>
                </row>
            </i-col>
            <div class="pull-right">
                <i-button type="primary" class="pull-left" @click="addStaffInit">添加职员</i-button>
                <i-button type="warning" class="pull-left" @click="resetPwd" style="margin: 0 20px">重置密码</i-button>
                <i-button type="info" class="pull-left" :loading="btn.exportExcel" icon="ios-cloud-download-outline" @click="exportExcel">导出</i-button>
            </div>
        </i-col>
        <i-col span="24" style="overflow: hidden;margin-bottom: 5px">
            <div style="width: 3000px">
                <i-button v-show="table.searchParamText.length > 0" @click="tableSearchReset" style="margin-right: 10px" type="warning" size="small">全部清空</i-button>
                <Tag v-for="(item,index) in table.searchParamText" @on-close="tagClose" type="border" :key="index" :name="item.key" closable color="blue">{{item.name}} : {{item.value}}</Tag>
            </div>
        </i-col>
        <i-col span="24" class="table-cell">
            <i-table
                :loading="table.loading"
                @on-selection-change="tableSelect"
                border
                size="small"
                width="100%"
                :height="table.height"
                :columns="table.columns"
                :data="table.data"
                @on-sort-change="tableSort"
            ></i-table>
        </i-col>
        <Page ref="pages" style="float: right; margin-top: 15px"
            :total="table.total"
            :page-size="30"
            :page-size-opts="[30,50,80]"
            placement="top"
            @on-change="changePage"
            @on-page-size-change="changePageSize"
            show-sizer
            show-total
        ></Page>
    </Row>
    <Modal
        v-model="modal.addStaff"
        title="添加职员"
        width="700"
        :mask-closable="false"
    >
        <div slot="footer">
            <row :gutter="16">
                <i-col span="4" offset="14">
                    <Checkbox style="line-height: 32px" v-model="openAdds" @on-change="continuousAdds"> 连续添加</Checkbox>
                </i-col>
                <i-col span="3">
                    <Poptip class="text-left"
                        confirm
                        title="确定要清空?"
                        @on-ok="resetForm('addStaff')">
                        <i-button type="warning">重置</i-button>
                    </Poptip>
                </i-col>
                <i-col span="3">
                    <i-button type="primary" :loading="btn.addStaff" @click="addStaffSubmit">提交</i-button>
                </i-col>
            </row>
        </div>
        <i-form ref="addStaff" :label-width="80" :model="staff" :rules="validate.addStaff">
            <Row :gutter="20">
                <i-col span="12">
                    <Form-item label="职员名称" prop="staffName">
                        <i-input type="text" v-model="staff.staffName" clearable placeholder="职员名称"></i-input>
                    </Form-item>
                </i-col>
                <i-col span="12">
                    <Form-item label="职员编号" prop="staffNo">
                        <i-input type="text" v-model="staff.staffNo" clearable placeholder="职员编号"></i-input>
                    </Form-item>
                </i-col>
                <i-col span="12">
                    <Form-item label="电话号码" prop="phone">
                        <i-input type="text" v-model="staff.phone" clearable placeholder="电话号码"></i-input>
                    </Form-item>
                </i-col>
                <i-col span="12">
                    <Form-item label="登录密码" prop="password">
                        <i-input type="password" v-model="staff.password" clearable placeholder="登录密码"></i-input>
                    </Form-item>
                </i-col>
                <i-col span="12">
                    <Form-item label="性别" prop="sex">
                        <i-select v-model="staff.sex">
                            <i-option value="男">男</i-option>
                            <i-option value="女">女</i-option>
                        </i-select>
                    </Form-item>
                </i-col>
                <i-col span="12">
                    <Form-item label="重复密码" prop="checkPassword">
                        <i-input type="password" v-model="staff.checkPassword" clearable placeholder="重复密码"></i-input>
                    </Form-item>
                </i-col>
                <i-col span="12">
                    <Form-item label="生日" prop="birthday">
                        <Date-Picker type="date" style="width: 100%" placeholder="生日"  v-model="staff.birthday"></Date-Picker>
                    </Form-item>
                </i-col>
                <i-col span="12">
                    <Form-item label="角色" prop="roleId">
                        <i-select v-model="staff.roleId" clearable>
                            <i-option v-for="item in roleList" :value="item.roleId" :key="item.roleId">{{item.roleName}}</i-option>
                        </i-select>
                    </Form-item>
                </i-col>
                <i-col span="12">
                    <Form-item label="部门" prop="deptId">
                        <input type="hidden" ref="deptId" value='${Session["staff"].deptId}'>
                        <i-select v-model="staff.deptId" clearable>
                            <i-option v-for="item in dept.optionList" :value="item.deptId" class="hidden" :key="item.deptId">{{item.deptName}}</i-option>
                            <Tree class="ivu-tree" :data="dept.data" @on-select-change="deptTreeSelect" style="padding-left: 20px"></Tree>
                        </i-select>
                    </Form-item>
                </i-col>
                <i-col span="12">
                    <Form-item label="部门管理员">
                        <i-switch v-model="staff.isDeptManager" :true-value="parseInt(1)" :false-value="parseInt(0)"></i-switch>
                    </Form-item>
                </i-col>
            </Row>
        </i-form>
    </Modal>
    <Modal
        v-model="modal.updateStaff"
        title="修改职员"
        ok-text="保存"
        width="700"
        :mask-closable="false"
    >
        <div slot="footer">
            <div class="pull-right">
                <i-button type="primary" class="pull-left" :loading="btn.updateStaff" @click="updateStaff">提交</i-button>
            </div>
            <div class="clear"></div>
        </div>
        <i-form ref="updateStaff" :label-width="80" :model="staff" :rules="validate.updateStaff">
            <row :gutter="20">
                <i-col span="12">
                    <form-item label="职员名称"  prop="staffName">
                        <i-input type="text" clearable v-model="staff.staffName" placeholder="职员名称"></i-input>
                    </form-item>
                </i-col>
                <i-col span="12">
                    <form-item label="职员编号" prop="staffNo">
                        <i-input type="text" clearable v-model="staff.staffNo" placeholder="职员编号"></i-input>
                    </form-item>
                </i-col>
                <i-col span="12">
                    <form-item label="电话号码" prop="phone">
                        <i-input type="text" clearable v-model="staff.phone" placeholder="电话号码"></i-input>
                    </form-item>
                </i-col>
                <i-col span="12">
                    <form-item label="性别" prop="sex">
                        <i-select v-model="staff.sex">
                            <i-option value="男">男</i-option>
                            <i-option value="女">女</i-option>
                        </i-select>
                    </form-item>
                </i-col>
                <i-col span="12">
                    <form-item label="生日" prop="birthday">
                        <Date-Picker type="date" style="width: 100%" placeholder="生日" v-model="staff.birthday"></Date-Picker>
                    </form-item>
                </i-col>
                <i-col span="12">
                    <form-item label="角色" prop="roleId">
                        <i-select clearable v-model="staff.roleId">
                            <i-option v-for="item in roleList" :value="item.roleId" :key="item.roleId">{{item.roleName}}</i-option>
                        </i-select>
                    </form-item>
                </i-col>
                <i-col span="12">
                    <form-item label="部门" prop="deptId">
                        <input type="hidden" ref="deptId" value='${staff.deptId}'>
                        <i-select clearable v-model="staff.deptId">
                            <i-option v-for="item in dept.optionList" :value="item.deptId" class="hidden" :key="item.deptId">{{item.deptName}}</i-option>
                            <Tree class="ivu-tree" :data="dept.data" @on-select-change="deptTreeSelect" style="padding-left: 20px"></Tree>
                        </i-select>
                    </form-item>
                </i-col>
                <i-col span="12">
                    <Form-item label="部门管理员">
                        <i-switch v-model="staff.isDeptManager" :true-value="parseInt(1)" :false-value="parseInt(0)"></i-switch>
                    </Form-item>
                </i-col>
            </row>
        </i-form>
    </Modal>
    <Modal
        v-model="modal.complexSearch"
        title="高级查询"
        width="650"
        :mask-closable="false"
    >
        <div slot="footer">
            <div class="pull-right">
                <i-button type="primary" class="pull-left" :loading="btn.tableSearch" @click="tableSearch">提交</i-button>
            </div>
            <div class="clear"></div>
        </div>
        <i-form ref="tableSearch" :label-width="90" :model="table.searchParam">
            <row :gutter="20">
                <i-col span="12">
                    <form-item label="关键字" prop="keyWord">
                        <i-input type="text" v-model="table.searchParam.keyWord" clearable placeholder="关键字"></i-input>
                    </form-item>
                </i-col>
                <i-col span="12">
                    <form-item label="性别" prop="sex">
                        <i-select v-model="table.searchParam.sex" clearable>
                            <i-option value="男">男</i-option>
                            <i-option value="女">女</i-option>
                        </i-select>
                    </form-item>
                </i-col>
                <i-col span="12">
                    <form-item label="生日区间" prop="birthdayArray">
                        <Date-Picker type="daterange" style="width: 100%" placeholder="生日" v-model="table.searchParam.birthdayArray"></Date-Picker>
                    </form-item>
                </i-col>
                <i-col span="12">
                    <form-item label="角色" prop="roleId">
                        <i-select v-model="table.searchParam.roleId" clearable>
                            <i-option v-for="item in roleList" :value="item.roleId" :key="item.roleId">{{item.roleName}}</i-option>
                        </i-select>
                    </form-item>
                </i-col>
                <i-col span="12">
                    <form-item label="创建时间区间" prop="createTimeArray">
                        <date-picker type="daterange" :options="dateOptions" v-model="table.searchParam.createTimeArray" placeholder="请选择" placement="bottom-end" :editable="false" style="width: 100%"></date-picker>
                    </form-item>
                </i-col>
                <i-col span="12">
                    <form-item label="部门管理员" >
                        <i-select v-model="table.searchParam.isAdmin" clearable>
                            <i-option value="0">否</i-option>
                            <i-option value="1">是</i-option>
                        </i-select>
                    </form-item>
                </i-col>
            </row>
        </i-form>
    </Modal>
    <Modal
        v-model="modal.resetPwd"
        title="重置密码"
        :mask-closable="false"
    >
        <div slot="footer">
            <div class="pull-right">
                <i-button type="primary" class="pull-left" :loading="btn.resetPwd" @click="resetPwdSubmit">提交</i-button>
            </div>
            <div class="clear"></div>
        </div>
        <i-form ref="resetPwd" :model="password" :label-width="90" :rules="validate.password">
            <form-item label="新密码" prop="value">
                <i-input type="text" v-model="password.value" placeholder="新密码"></i-input>
            </form-item>
        </i-form>
    </Modal>
</div>
</body>
<script type="text/javascript">
    new Vue({
        el:"#app",
        data: function () {
            const validatePhone = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('必须输入职员手机号'));
                }
                let params = {};
                if (!this.modal.updateStaff) {
                    params = {
                        phone: this.staff.phone
                    }
                } else {
                    params = {
                        phone: this.staff.phone,
                        staffId: this.staff.staffId
                    }
                }
                axios.get('/staff/findPhone', {params}).then(res => {
                    if (res.data) {
                        callback(new Error('职员手机号已经存在'));
                    } else {
                        callback();
                    }
                });
            };
            const validateNo = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('必须输入职员编号'));
                }
                if (!new RegExp(/^\w{4,20}$/).test(value)) {
                    return callback(new Error('编号只可以是字母和数字哦！最多20位'));
                }
                let params = {};
                if (!this.modal.updateStaff) {
                    params = {
                        staffNo: this.staff.staffNo
                    }
                } else {
                    params = {
                        staffNo: this.staff.staffNo,
                        staffId: this.staff.staffId
                    }
                }
                axios.get('/staff/findNo', {params}).then(res => {
                    if (res.data) {
                        callback(new Error('职员编号已经存在'));
                    } else {
                        callback();
                    }
                });
            };
            const validatePassword = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('请输入密码'));
                } else {
                    if (!new RegExp(/^\w{6,20}$/).test(value)) {
                        return callback(new Error('密码最少六位，只能是字母和数字'));
                    } else {
                        //验证单个字段
                        if (this.staff.checkPassword !== '') {
                            this.$refs.addStaff.validateField('checkPassword');
                        }
                        callback();
                    }
                }
            };
            const validateRepeatPassword = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('两次密码不一样'));
                } else {
                    if (this.staff.password !== value) {
                        return callback(new Error('两次密码不一样'));
                    } else {
                        callback();
                    }
                }
            };
            return {
                table: {
                    height: "",
                    data: [],
                    columns: [
                        {
                            type: 'index',
                            width: 60,
                            align: 'center',
                            fixed: "left",
                        },
                        {
                            type: 'selection',
                            width: 60,
                            align: 'center',
                            fixed: "left"
                        },
                        {
                            title: "职员编号",
                            key: "staffNo",
                            align: 'center',
                            sortable: 'custom',
                            width: 160,
                            fixed: "left",
                            ellipsis: "true"
                        },
                        {
                            title: "职员名称",
                            align: 'center',
                            fixed: "left",
                            key: "staffName",
                            width: 160,
                            ellipsis: "true"
                        },
                        {
                            title: "性别",
                            align: 'center',
                            key: "sex",
                            sortable: 'custom',
                            width: 80
                        },
                        {
                            title: "电话",
                            key: "phone",
                            align: 'center',
                            width: 200,
                            ellipsis: "true"
                        },
                        {
                            title: "经验值",
                            key: "experience",
                            align: 'center',
                            width: 80,
                        },
                        {
                            title: "部门",
                            key: "deptName",
                            align: 'center',
                            sortable: 'custom',
                            width: 300,
                            ellipsis: "true"
                        },
                        {
                            title: "部门管理员",
                            key: "isDeptManager",
                            align: 'center',
                            sortable: 'custom',
                            width: 120,
                            render: (h, params) => {
                                return h('div', params.row.isDeptManager === 1 ? "是" : "否")
                            }
                        },
                        {
                            title: "角色",
                            align: 'center',
                            key: 'roleName',
                            width: 150,
                            sortable: 'custom',
                            ellipsis: "true",
                        },
                        {
                            title: "生日",
                            align: 'center',
                            key: 'birthday',
                            sortable: 'custom',
                            width: 100
                        },
                        {
                            title: "添加时间",
                            align: 'center',
                            key: 'createTime',
                            sortable: 'custom',
                            width: 150
                        },
                        {
                            title: "操作",
                            fixed: "right",
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
                                                this.getStaff(params.row.staffId);
                                            }
                                        }
                                    }, '编辑')
                                ]);
                            }
                        },
                        {
                            title: "是否停用",
                            fixed: "right",
                            align: 'center',
                            width: 90,
                            render: (h, params) => {
                                return h('div', [
                                    h('i-switch', {
                                        props: {
                                            value: params.row.staffStatus === 1
                                        },
                                        on: {
                                            'on-change': () => {
                                                this.changeState(params.index)
                                            }
                                        }
                                    }, [
                                        h('Icon', {
                                            props: {
                                                type: 'android-done',
                                            },
                                            slot: 'open'
                                        }),
                                        h('Icon', {
                                            props: {
                                                type: 'android-close',
                                            },
                                            slot: 'close'
                                        })
                                    ])
                                ]);
                            }
                        }
                    ],
                    total: 0,
                    param: {
                        pageNumber: 1,
                        pageSize: 30,
                        sortOrder: 'desc',
                        sortColumn: 'createTime'
                    },
                    searchParam:{
                        keyWord: '',
                        sex:'',
                        roleId:'',
                        birthdayArray:[],
                        createTimeArray:[],
                        isAdmin:''
                    },
                    searchParamText:[],
                    loading: false,
                    selected:[]
                },
                dateOptions: {
                    shortcuts: [
                        {
                            text: '三天',
                            value () {
                                const end = new Date();
                                const start = new Date();
                                start.setTime(start.getTime() - 3600 * 1000 * 24 * 3);
                                return [start, end];
                            }
                        },
                        {
                            text: '七天',
                            value () {
                                const end = new Date();
                                const start = new Date();
                                start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                                return [start, end];
                            }
                        },
                        {
                            text: '十五天',
                            value () {
                                const end = new Date();
                                const start = new Date();
                                start.setTime(start.getTime() - 3600 * 1000 * 24 * 15);
                                return [start, end];
                            }
                        },
                        {
                            text: '三十天',
                            value () {
                                const end = new Date();
                                const start = new Date();
                                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                                return [start, end];
                            }
                        },
                        {
                            text: '九十天',
                            value () {
                                const end = new Date();
                                const start = new Date();
                                start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                                return [start, end];
                            }
                        },
                        {
                            text: '一百八十天',
                            value () {
                                const end = new Date();
                                const start = new Date();
                                start.setTime(start.getTime() - 3600 * 1000 * 24 * 160);
                                return [start, end];
                            }
                        }
                    ]
                },
                modal: {
                    addStaff: false,
                    updateStaff: false,
                    complexSearch: false,
                    resetPwd: false
                },
                password:{
                    value:""
                },
                staff: {
                    deptId: '',
                    deptName: '',
                    staffName: '',
                    password: '',
                    phone: '',
                    isDeptManager: '0',
                    sex: '男',
                    birthday: '',
                    staffNo: '',
                    roleId: '',
                    roleName: '',
                    checkPassword: '',
                },
                btn: {
                    addStaff: false,
                    updateStaff: false,
                    tableSearch: false,
                    exportExcel:false,
                    resetPwd: false
                },
                dept: {
                    optionList: [],
                    data: []
                },
                validate: {
                    addStaff: {
                        staffName: [
                            {required: true, message: '职员姓名不可为空', trigger: 'blur'}
                        ],
                        staffNo: [
                            {required: true, validator: validateNo, trigger: 'blur'}
                        ],
                        phone: [
                            {required: true, validator: validatePhone, trigger: 'blur'}
                        ],
                        password: [
                            {required: true, validator: validatePassword, trigger: 'blur'},
                        ],
                        checkPassword: [
                            {required: true, validator: validateRepeatPassword, trigger: 'blur'}
                        ],
                        sex: [
                            {required: true, message: '性别必须选择', trigger: 'change'}
                        ],
                        deptId: [
                            {required: true, message: '部门必须选择', trigger: 'change'}
                        ],
                        roleId: [
                            {required: true, type: 'number', message: '角色必须选择', trigger: 'change'}
                        ]
                    },
                    updateStaff: {
                        staffName: [
                            {required: true, message: '职员姓名不可为空', trigger: 'blur'}
                        ],
                        staffNo: [
                            {required: true, validator: validateNo, trigger: 'blur'}
                        ],
                        phone: [
                            {required: true, validator: validatePhone, trigger: 'blur'}
                        ],
                        sex: [
                            {required: true, message: '性别必须选择', trigger: 'change'}
                        ],
                        deptId: [
                            {required: true, message: '部门必须选择', trigger: 'change'}
                        ],
                        roleId: [
                            {required: true, type: 'number', message: '角色必须选择', trigger: 'change'}
                        ]
                    },
                    password: {
                        value: [
                            {required: true, message: '密码必须填写', trigger: 'blur'},
                            {pattern: /^\w{6,20}$/, message: '密码最少六位，只能是字母和数字'},
                        ],
                    }
                },
                roleList: [],
                openAdds: false,
            }
        },
        computed: {
            roleName (){
                return this.roleList.filter(value => value.roleId === this.staff.roleId)[0].roleName
            },
            deptName (){
                let name = '';
                let parentId = '';
                let deptId = this.staff.deptId;
                let data = this.dept.optionList;
                if (deptId.length === 4) {
                    name = this.dept.optionList.filter(value => value.deptId === deptId)[0].deptName
                } else {
                    findParent(deptId)
                }
                function findParent (deptId) {
                    data.forEach(item => {
                        if (item.deptId === deptId && item.deptId.length !== 4) {
                            parentId = deptId.substring(0, deptId.length-2);
                            name += '>'+ item.deptName;
                            findParent(parentId)
                        }
                    })
                }
                return name.split('>').reverse().join('>');
            }
        },
        methods:{
            getTableData (searchParam) {
                if (this.table.selected.length !== 0) {
                    this.table.selected = [];
                }
                this.table.loading = true;
                //基本参数
                let param = JSON.parse(JSON.stringify(this.table.param));
                if (searchParam){
                    for(let item in searchParam){
                        let o =  searchParam[item];
                        if (o === '') {
                            continue
                        }
                        if (o instanceof Array) {
                            if (o.length === 0 || o[0] === '' || o[1] === '') {
                                continue;
                            }
                        }
                        param[item] = o;
                    }
                    console.log(JSON.stringify(param));
                    this.table.searchParamText = [];
                    for (let p in param){
                        switch (p){
                            case "keyWord":
                                this.table.searchParamText.push({
                                    name: "关键字",
                                    key:"keyWord",
                                    value: param[p]
                                });
                                break;
                            case "sex":
                                this.table.searchParamText.push({
                                    name: "性别",
                                    key:"sex",
                                    value: param[p]
                                });
                                break;
                            case "roleId":
                                this.table.searchParamText.push({
                                    name: "角色",
                                    key:"roleId",
                                    value: this.roleList.filter(r => r.roleId === param[p])[0].roleName
                                });
                                break;
                            case "isAdmin":
                                this.table.searchParamText.push({
                                    name: "部门管理员",
                                    key:"isAdmin",
                                    value: param[p] === "0" ? "否" : "是"
                                });
                                break;
                            case "createTimeArray":
                                this.table.searchParamText.push({
                                    name: "创建时间区间",
                                    key:"createTimeArray",
                                    value: new Date(param[p][0]).format("yyyy-MM-dd") + "至" + new Date(param[p][1]).format("yyyy-MM-dd")
                                });
                                break;
                            case "birthdayArray":
                                this.table.searchParamText.push({
                                    name: "生日区间",
                                    key:"birthdayArray",
                                    value: new Date(param[p][0]).format("yyyy-MM-dd") + "至" + new Date(param[p][1]).format("yyyy-MM-dd")
                                });
                                break;
                        }
                    }
                }
                axios.get('/staff/list',{params:{param: encodeURI(JSON.stringify(param))}}).then((res) => {
                    if (res.code === 200){
                        this.table.data = res.data.rows;
                        this.table.total = res.data.total;
                        this.table.loading = false;
                    } else {
                        this.$Message.error(res.message);
                        this.table.loading = false;
                    }
                }).catch((err)=>{
                    this.$Message.error('获取职员列表数据失败');
                    this.table.loading = false;
                })
            },
            tableSelect(data) {
                this.table.selected = data;
            },
            complexSearch(){
                this.getRoleData();
                this.modal.complexSearch = true;
            },
            tableSearch(){
                this.btn.tableSearch = true;
                this.table.param.pageNumber = 1;
                this.$refs["pages"].currentPage = 1;
                this.getTableData(this.table.searchParam);
                this.modal.complexSearch = false;
                this.btn.tableSearch = false;
            },
            tagClose(event,name){
                switch (name){
                    case "createTimeArray":
                        this.table.searchParam.createTimeArray = [];
                        break;
                    case "birthdayArray":
                        this.table.searchParam.birthdayArray = [];
                        break;
                    default:
                        this.table.searchParam[name] = "";
                }
                this.getTableData(this.table.searchParam);
            },
            tableSearchReset() {
                this.resetForm('tableSearch');
                this.getTableData(this.table.searchParam);
            },
            tableSort (value) {
                if (value.order === 'normal') {
                    this.table.param.sortOrder = 'desc';
                    this.table.param.sortColumn = 'createTime';
                } else {
                    this.table.param.sortOrder = value.order;
                    if (value.key === 'deptName') {
                        this.table.param.sortColumn = "deptId";
                    }else if (value.key === 'roleName'){
                        this.table.param.sortColumn = "roleId";
                    }else {
                        this.table.param.sortColumn = value.key;
                    }
                }
                this.getTableData(this.table.searchParam);
            },
            changePage (num) {
                this.table.param.pageNumber = num;
                this.getTableData(this.table.searchParam);
            },
            changePageSize (size) {
                this.table.param.pageSize = size;
                this.getTableData(this.table.searchParam);
            },
            addStaffInit () {
                this.resetForm("addStaff");
                this.staff.isDeptManager = 0;
                this.modal.addStaff= true;
                this.getRoleData();
                this.getDeptData();
            },
            getRoleData () {
                if (this.roleList.length === 0) {
                    axios.get("/role/list").then(res => {
                        if (res.code === 200) {
                            this.roleList = res.data;
                        } else {
                            this.$Message.error(res.message);
                        }
                    }).catch(err => {
                        this.$Message.error('获取角色列表失败');
                    })
                }
            },
            getDeptData () {
                if (this.dept.data.length === 0) {
                    axios.get("/dept/list").then(res => {
                        if (res.code === 200) {
                            this.dept.optionList = res.data;
                            let root = [];
                            const data = JSON.parse(JSON.stringify(res.data).replace(/deptName/g,'title'));
                            data.forEach(item => {
                                 if (item.deptId.length === this.$refs['deptId'].value.length) {
                                     item.expand = true;
                                     root.push(item);
                                     loadChildNode(data, item)
                                 }
                            });
                            function loadChildNode (data, node) {
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
                            this.dept.data = root;
                        } else {
                            this.$Message.error(res.message);
                        }
                    }).catch(err => {
                        this.$Message.error('获取部门列表失败');
                    })
                }
            },
            deptTreeSelect (value) {
                if (value.length !== 0) {
                    this.staff.deptId = value[0].deptId;
                } else {
                    this.staff.deptId = "";
                }
            },
            computeName () {
                //计算名称
                this.staff.roleName = this.roleName;
                this.staff.deptName = this.deptName.indexOf('>') === -1? this.deptName : this.deptName.substring(0,this.deptName.length-1);
            },
            addStaffSubmit () {
                this.btn.addStaff = true;
                this.$refs['addStaff'].validate((valid) => {
                    if (!valid) {
                        return this.btn.addStaff = false;
                    }
                    this.computeName();
                    if(this.staff.birthday !== ''){
                        this.staff.birthday = new Date(this.staff.birthday).format('yyyy-MM-dd');
                    }
                    axios.post("/staff",this.staff).then(res => {
                        if(res.code === 200){
                            if (!this.openAdds) {
                                this.$refs['addStaff'].resetFields();
                                this.modal.addStaff = false;
                            }
                            this.btn.addStaff = false;
                            this.getTableData();
                            setTimeout(() => {
                                this.$Notice.success({title:"添加成功"});
                            },400)
                        }else{
                            this.$Notice.error({title:res.message})
                        }
                    }).catch((err)=>{
                        this.$Message.error("添加失败")
                    });
                });
            },
            resetForm (formName) {
                this.$refs[formName].resetFields();
            },
            continuousAdds () {
                if (this.openAdds) {
                    this.$Message.warning({
                        content: '连续添加打开，添加完成不会隐藏添加界面！',
                        duration: 3
                    });
                } else {
                    this.$Message.success("连续添加关闭");
                }
            },
            getStaff (staffId) {
                this.resetForm('updateStaff');
                this.getRoleData();
                this.getDeptData();
                axios.get('/staff/' + staffId).then(res => {
                    if (res.code === 200) {
                        this.staff = res.data;
                        this.modal.updateStaff = true;
                    } else {
                        this.$Message.error(res.message);
                    }
                }).catch(err => {
                    this.$Message.error("职员信息获取失败");
                });
            },
            updateStaff () {
                this.btn.updateStaff = true;
                this.$refs['updateStaff'].validate(flag => {
                    if (!flag) {
                        return this.btn.updateStaff = false;
                    }
                    this.computeName();
                    if(this.staff.birthday !== ''){
                        this.staff.birthday = new Date(this.staff.birthday).format('yyyy-MM-dd');
                    }
                    axios.put('/staff',this.staff).then(res => {
                        if (res.code === 200) {
                            this.modal.updateStaff = false;
                            this.btn.updateStaff = false;
                            this.getTableData();
                            setTimeout(() => {
                                this.$Notice.success({title: "修改成功"});
                            },400)
                        } else {
                            this.$Message.error(res.message);
                            this.btn.updateStaff = false;
                        }
                    }).catch(err => {
                        this.$Message.error("职员信息更新失败");
                        this.btn.updateStaff = false;
                    })
                })
            },
            changeState (index) {
                let staff = this.table.data[index];
                let state = staff.staffStatus === 0 ? 1 : 0;
                this.table.loading = true;
                axios.put('/staff/'+staff.staffId+'/'+state).then(res => {
                    if (res.code === 200) {
                        this.$Message.success("修改成功");
                        setTimeout(()=>{
                            this.table.data[index].staffStatus = state;
                            this.table.loading = false;
                        },400)
                    } else {
                        this.$Message.error(res.message);
                        this.table.loading = false;
                    }
                }).catch(err => {
                    this.$Message.error("职员状态更新失败");
                    this.table.loading = false;
                })
            },
            exportExcel(){
                this.btn.exportExcel = true;
                window.location.href = "/file/export/staff";
                setTimeout(()=>{
                    this.btn.exportExcel = false;
                },1000)
            },
            resetPwd(){
                if (this.table.selected.length === 0) {
                    this.$Message.error("最少选择一个职员！");
                } else {
                    this.resetForm("resetPwd");
                    this.modal.resetPwd = true;
                }
            },
            resetPwdSubmit() {
                this.btn.resetPwd = true;
                this.$refs['resetPwd'].validate(result => {
                    if (!result) {
                        return this.btn.resetPwd = false;
                    }
                    let param = {
                        pwd : this.password.value,
                        staffIdArray: []
                    };
                    this.table.selected.forEach(s => {param.staffIdArray.push(s.staffId)});
                    axios.put('/staff/resetPwd', param).then(res => {
                        if (res.code === 200) {
                            this.$Message.success("密码重置成功");
                            this.modal.resetPwd = false;
                        } else {
                            this.$Notice.error({title: res.message});
                        }
                    }).catch(err => {
                        this.$Message.error("密码重置失败");
                    });
                    this.btn.resetPwd = false;
                });
            },
            dateFormat() {
                Date.prototype.format = function(format)
                {
                    let o = {
                        "M+": this.getMonth() + 1, //month
                        "d+": this.getDate(),    //day
                        "h+": this.getHours(),   //hour
                        "m+": this.getMinutes(), //minute
                        "s+": this.getSeconds(), //second
                        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
                        "S": this.getMilliseconds() //millisecond
                    };
                    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
                    for(let k in o)
                        if(new RegExp("("+ k +")").test(format))
                        format = format.replace(RegExp.$1, RegExp.$1.length===1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
                    return format;
                }
            }
        },
        created(){
            this.dateFormat();
            this.getTableData();
        },
        mounted(){
            this.table.height = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 160;
        }
    })
</script>
</html>