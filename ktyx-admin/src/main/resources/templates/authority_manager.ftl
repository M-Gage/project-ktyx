<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>客通云销 - 权限管理</title>
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
            <i-col span="12">
                <row>
                    <i-col span="24">
                        <row>
                            <i-col span="18">
                                <i-form :label-width="80" :model="role" :rules="validate.updateRoleMenu" ref="updateRoleMenu" label-position="left">
                                    <form-item prop="roleId" label="角色">
                                        <i-select v-model="role.roleId" placeholder="请选择角色" @on-change="changeRole">
                                            <i-option v-for="(item,index) in roleList" :value="item.roleId" :key="index">{{ item.roleName }}</i-option>
                                        </i-select>
                                    </form-item>
                                </i-form>
                            </i-col>
                            <i-col span="4" offset="2">
                                <i-button type="primary" long @click="addRole">添加</i-button>
                            </i-col>
                        </row>
                    </i-col>
                    <i-col span="24">
                        <Spin fix v-show="tree.loading"></Spin>
                        <Tree :data="tree.roleMenu" empty-text="暂无数据！请选择角色！" ref="updateRoleTree" @on-check-change="treeSelect" show-checkbox></Tree>
                        <i-button type="primary" :loading="btn.updateRoleMenu"  long @click="updateRoleMenuSubmit">提交</i-button>
                    </i-col>
                </row>
            </i-col>
            <i-col span="11" offset="1">
                <i-form ref="company" :rules="validate.updateCompany" :model="company" label-position="left" :label-width="80">
                    <form-Item prop="poiNumber" label="类别">
                        <i-select v-model="company.poiNumber" multiple>
                            <i-option v-for="item in poi" :value="item.value" :key="item.value">{{ item.name }}</i-option>
                        </i-select>
                    </form-Item>
                    <form-Item prop="mapKeywork" label="关键字">
                        <i-input v-model="company.mapKeywork" placeholder="多个关键字以逗号分割"></i-input>
                    </form-Item>
                    <i-button long type="primary" :loading="btn.company" @click="updateCompany">提交修改</i-button>
                </i-form>
            </i-col>
        </row>
        <Modal
            v-model="modal.addRole"
            title="添加角色"
            :mask-closable="false"
            :styles="{top:'10px',width: '300px'}"
        >
            <div slot="footer">
                <i-button type="primary" class="pull-right" :loading="btn.addRole"  @click="addRoleSubmit">提交</i-button>
                <div class="clear"></div>
            </div>
            <i-form :label-width="80" ref="addRole" :model="role" :rules="validate.addRole">
                <Form-item label="角色名称" prop="roleName">
                    <i-input type="text" v-model="role.roleName" placeholder="角色名称"></i-input>
                </Form-item>
                <Tree style="padding-left: 50px;" :data="tree.addRoleMenu" @on-check-change="addTreeCheckChange" show-checkbox ></Tree>
            </i-form>
        </Modal>
    </div>
</body>
<script type="text/javascript">
    new Vue({
        el:"#app",
        data(){
            const validatePoi = (rule, value, callback) => {
                if (value.length === 0) {
                    return callback(new Error('必须选择类别'));
                }
                if (value.length > 5 ) {
                    return callback(new Error('不能超过五类'));
                }
                callback();
            };
            const validateKeyword = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('关键字必须填写'));
                }
                //处理中文逗号
                let str = value.replace(/[，]/g, ',');
                if (str.substring(str.length - 1,str.length).indexOf(',') !== -1) {
                    str = str.substring(0, str.length - 1);
                }
                this.company.mapKeywork = str;
                callback();
            };
            const validateRoleName = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('角色名称必须填写'));
                }
                axios.get('/role/findName', {params:{roleName:value}}).then(res => {
                    if (res.data) {
                        callback(new Error('角色名称已经存在!'));
                    } else {
                        callback();
                    }
                });
            };
            return {
                poi: [
                    {"value": "010000", "name": "汽车服务"},
                    {"value": "020000", "name": "汽车销售"},
                    {"value": "030000", "name": "汽车维修"},
                    {"value": "040000", "name": "摩托车服务"},
                    {"value": "050000", "name": "餐饮服务"},
                    {"value": "060000", "name": "购物服务"},
                    {"value": "070000", "name": "生活服务"},
                    {"value": "080000", "name": "体育休闲服务"},
                    {"value": "090000", "name": "医疗保健服务"},
                    {"value": "100000", "name": "住宿服务"},
                    {"value": "110000", "name": "风景名胜"},
                    {"value": "120000", "name": "商务住宅"},
                    {"value": "130000", "name": "政府机构及社会团体"},
                    {"value": "140000", "name": "科教文化服务"},
                    {"value": "150000", "name": "交通设施服务"},
                    {"value": "160000", "name": "金融保险服务"},
                    {"value": "170000", "name": "公司企业"},
                    {"value": "180000", "name": "道路附属设施"},
                    {"value": "190000", "name": "地名地址信息"},
                    {"value": "200000", "name": "公共设施"},
                    {"value": "220000", "name": "事件活动"},
                    {"value": "970000", "name": "室内设施"},
                    {"value": "990000", "name": "通行设施"}
                ],
                roleList: [],
                tree:{
                    data:[],
                    addRoleMenu:[],
                    roleMenu:[],
                    loading: false,
                    updateRoleSelected:[],
                    addRoleSelected:[]
                },
                modal:{
                    addRole: false
                },
                btn: {
                    addRole: false,
                    updateRoleMenu:false,
                    updateCompany: false
                },
                role: {
                    roleId: "",
                    roleName: ""
                },
                validate:{
                    updateRoleMenu:{
                        roleId: [
                            {type:"number", required: true, message: '必须选择角色', trigger: 'change'}
                        ]
                    },
                    addRole:{
                        roleName: [
                            {required: true, validator: validateRoleName, trigger: 'blur'}
                        ]
                    },
                    updateCompany:{
                        poiNumber: [
                            {required: true, validator: validatePoi, trigger: 'change'}
                        ],
                        mapKeywork: [
                            {required: true, validator: validateKeyword, trigger: 'blur'}
                        ]
                    }
                },
                addMenuData: [],
                company: {
                    poiNumber: [],
                    mapKeywork: "",
                }
            };
        },
        methods:{
            getCompany(){
                axios.get('/company/keyword').then((res)=>{
                    if(res.code === 200){
                        if (res.data.poiNumber.indexOf("|") === -1) {
                            this.company.poiNumber.push(res.data.poiNumber);
                        } else {
                            this.company.poiNumber = res.data.poiNumber.split("|");
                        }
                        if (res.data.mapKeyword.indexOf('|') === -1) {
                            this.company.mapKeywork = res.data.mapKeyword;
                        } else {
                            this.company.mapKeywork = res.data.mapKeyword.replace(/\|/g,',')
                        }
                    }else{
                        this.$Message.error(res.message)
                    }
                }).catch(err=>{
                    this.$Message.error("获取数据失败")
                })
            },
            getTreeData(roleId){
                let url = '/menu';
                let flag = false;
                if(roleId){
                    url += '/' + roleId;
                    flag = true;
                }
                this.tree.loading = true;
                axios.get(url).then((res)=>{
                    if (res.code === 200) {
                        let root = [];
                        const data = JSON.parse(JSON.stringify(res.data).replace(/menuName/g, 'title'));
                        data.forEach(item => {
                            if (item.menuId.length === 4) {
                                if (flag) {
                                    item.expand = true;
                                }
                                root.push(item);
                                loadChildNode(data, item)
                            }
                        });
                        function loadChildNode(data, node) {
                            let reg = new RegExp("^" + node.menuId + "..$");
                            data.forEach((item, index) => {
                                if (reg.test(item.menuId)) {
                                    if (!node.children) {
                                        node.children = [];
                                    }
                                    if(item.flag === "1" && flag){
                                        item.checked = true;
                                    }
                                    node.children.push(item);
                                    loadChildNode(data, item);
                                }
                            });
                        }
                        if(flag){
                            this.tree.roleMenu = root;
                            setTimeout(() => {
                                //获取被选中的节点
                                this.tree.updateRoleSelected = this.$refs['updateRoleTree'].getCheckedNodes();
                                this.tree.loading = false;
                            }, 1000);
                        } else {
                            this.tree.data = root;
                        }
                        this.tree.loading = false;
                    } else {
                        this.$Message.error(res.message);
                        this.tree.loading = false;
                    }
                }).catch((err)=>{
                    this.$Message.error("获取菜单数据失败");
                    this.tree.loading = false;
                });
            },
            treeSelect(value){
                this.tree.updateRoleSelected = value;
            },
            changeRole(value){
                this.getTreeData(value);
            },
            updateRoleMenuSubmit(){
                this.btn.updateRoleMenu = true;
                this.$refs['updateRoleMenu'].validate((valid) => {
                    if (!valid) {
                        return this.btn.updateRoleMenu = false;
                    }
                    let menuIdArray = [];
                    let _addMenuList = this.tree.updateRoleSelected;
                    for (let i = 0; i < _addMenuList.length; i++) {
                        let menuId = _addMenuList[i].menuId;
                        menuIdArray.push(menuId);
                        let parentId = menuId.toString().substring(0, 4);
                        let flag = true;
                        for (let j = 0; j < menuIdArray.length; j++) {
                            if (parentId === menuIdArray[j]) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            menuIdArray.push(parentId);
                        }
                    }
                    axios.post('/roleMenu/' + this.role.roleId, menuIdArray).then((res) => {
                        if (res.code === 200) {
                            this.$Message.success("提交成功");
                            this.btn.updateRoleMenu = false;
                        } else {
                            this.$Message.error(res.message);
                            this.btn.updateRoleMenu = false;
                        }
                    }).catch((err) => {
                        this.$Message.error("更新失败！");
                        this.btn.updateRoleMenu = false;
                    });
                });
            },
            addRole(){
                this.$refs['addRole'].resetFields();
                this.modal.addRole = true;
                this.tree.addRoleMenu = JSON.parse(JSON.stringify(this.tree.data));
            },
            addRoleSubmit(){
                this.btn.addRole = true;
                this.$refs['addRole'].validate((valid) => {
                    if (!valid) {
                        return this.btn.addRole = false;
                    }
                    let menuIdArray = [];
                    let _addMenuList = this.tree.addRoleSelected;
                    for (let i = 0; i < _addMenuList.length; i++) {
                        let menuId = _addMenuList[i].menuId;
                        menuIdArray.push(menuId);
                        let parentId = menuId.toString().substring(0, 4);
                        let flag = true;
                        for (let j = 0; j < menuIdArray.length; j++) {
                            if(parentId === menuIdArray[j]){
                                flag = false;
                                break;
                            }
                        }
                        if(flag){
                            menuIdArray.push(parentId);
                        }
                    }
                    let param = {
                        role: {
                            roleName: this.role.roleName
                        },
                        menuIdArray: menuIdArray,
                    };
                    axios.post('/role', param).then((res)=>{
                        if(res.code === 200){
                            this.getRole();
                            setTimeout(()=>{
                                this.$Message.success("添加成功");
                                this.btn.addRole = false;
                                this.modal.addRole = false;
                                this.$refs['addRole'].resetFields();
                            },1000)
                        }else{
                            this.$Message.error(res.message);
                            this.modal.addRole = false;
                        }
                    }).catch((err)=>{
                        this.$Message.error("角色添加失败");
                        this.modal.addRole = false;
                    })
                });
            },
            addTreeCheckChange(value){
                this.tree.addRoleSelected = value;
            },
            getRole(){
                axios.get('/role/list').then((res)=>{
                    if(res.code === 200){
                        this.roleList = res.data;
                    }else{
                        this.$Message.error(res.message)
                    }
                }).catch((err)=>{
                    this.$Message.error("获取数据失败")
                });
            },
            updateCompany(){
                this.btn.updateCompany = true;
                this.$refs['company'].validate((valid) => {
                    if (!valid) {
                        this.btn.updateCompany = false;
                    }else{
                        let company = {
                            poi: '',
                            keyWord: ''
                        };
                        company.keyWord = this.company.mapKeywork.replace(/,/g, '|');
                        company.poi = this.company.poiNumber.join("|")
                        axios.put('/company/keyword',company).then((res)=>{
                            if(res.code === 200){
                                this.$Message.success("修改成功");
                                this.btn.updateCompany = false;
                            }else{
                                this.$Message.error(res.message);
                                this.btn.updateCompany = false;
                            }
                        }).catch((err)=>{
                            this.$Message.error("修改失败");
                            this.btn.updateCompany = false;
                        });
                    }
                });
            },
        },
        created(){
            this.getTreeData();
            this.getRole();
            this.getCompany();
        }
    })
</script>
</html>