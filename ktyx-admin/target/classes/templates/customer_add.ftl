
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>客通云销 - 客户添加</title>
    <link rel="stylesheet" href="../static/js/plugins/iview/iview.css">
    <link rel="stylesheet" href="../static/css/base.css">
    <script src="../static/js/vue.js"></script>
    <script src="http://webapi.amap.com/maps?v=1.4.6&key=85d5e0bd412ecfc92d29e3bdfc4b05e9&plugin=AMap.Geocoder"></script>
    <script src="../static/js/plugins/iview/iview.min.js"></script>
    <script src="../static/js/axios.min.js"></script>
    <script src="../static/js/axios-config.js"></script>
    <style>
        .ivu-radio-group{
            width: 100%;
        }
        .contact-item{
            border: 1px solid #dddee1;
            border-radius: 5px;
            padding: 5px 0;
            line-height: 24px;
            margin-bottom: 5px;
            cursor: pointer;
        }
        .contact-item:hover{
            background-color: #f1f1f1;
        }
        .contact-item:last-child{
            margin-bottom: 0;
        }
        .ivu-card-body{
            padding: 5px;
        }
        .ivu-card-head{
            padding:10px 16px;
        }
        .ivu-poptip-rel{
            width: 100%;
        }
    </style>
</head>
<body>
    <div id="app" v-cloak>
        <Row :gutter="30">
            <i-col span="10">
                <i-form ref="addCustomer" :label-width="80" :model="customer" :rules="validate.addCustomer">
                    <form-item label="客户名称" prop="customerName">
                        <i-input type="text" v-model="customer.customerName"></i-input>
                    </form-item>
                    <form-item label="客户编号" prop="customerNo">
                        <i-input type="text" v-model="customer.customerNo"></i-input>
                    </form-item>
                    <form-item label="客户级别" prop="level">
                        <i-select v-model="customer.level">
                            <i-option value="A级">A级</i-option>
                            <i-option value="B级">B级</i-option>
                            <i-option value="C级">C级</i-option>
                            <i-option value="D级">D级</i-option>
                        </i-select>
                    </form-item>
                    <form-item label="省市县">
                        <row>
                            <i-col span="8" class="clear-padding">
                                <Form-item>
                                    <i-select  @on-change="provinceChange" placeholder="省" v-model='customer.province'>
                                        <i-option v-for="item in address.data" :value="item.name" :key="item.name"></i-option>
                                    </i-select>
                                </Form-item>
                            </i-col>
                            <i-col span="7" offset="1" class="clear-padding">
                                <Form-item>
                                    <i-select  @on-change="cityChange" placeholder="市" v-model="customer.city">
                                        <i-option v-for="item in address.cityList" :value="item.name" :key="item.name"></i-option>
                                    </i-select>
                                </Form-item>
                            </i-col>
                            <i-col span="7" offset="1" class="clear-padding">
                                <Form-item>
                                    <i-select  placeholder="县" v-model="customer.district">
                                        <i-option v-for="item in address.districtList" :value="item" :key="item"></i-option>
                                    </i-select>
                                </Form-item>
                            </i-col>
                        </row>
                    </form-item>
                    <row>
                        <i-col span="19" class="clear-padding">
                            <form-item label="客户地址" prop="detailAddress">
                                <i-input type="text" v-model="customer.detailAddress"></i-input>
                            </form-item>
                        </i-col>
                        <i-col span="4" offset="1" class="clear-padding">
                            </form-item>
                                <i-button type="primary" icon="navigate" @click="searchMap" long></i-button>
                            </form-item>
                        </i-col>
                    </row>
                    <form-item label="客户标签" prop="labelList">
                        <checkbox-group v-model="customer.labelList">
                            <checkbox v-for="item in labelList" :key="item.labelId" :label="item.labelId">{{item.labelName}}</checkbox>
                        </checkbox-group>
                    </form-item>
                    <form-item label="备注信息" prop="contactRemark">
                        <i-input type="textarea" v-model="customer.contactRemark" :autosize="{minRows: 5,maxRows: 5}" placeholder="备注信息"></i-input>
                    </form-item>
                    <row style="margin-bottom: 24px">
                        <i-col span="24" class="clear-padding">
                            <i-button style="float: right" type="info" @click="addContact">添加联系人</i-button>
                        </i-col>
                    </row>
                    <Card dis-hover v-show="customer.contactList.length> 0" style="margin-bottom: 24px">
                        <p slot="title">联系人列表</p>
                        <ul>
                            <radio-group v-model="contactMainIndex">
                                <li v-for="(item,index) in customer.contactList" :key="index" class="contact-item">
                                    <row :gutter="16">
                                        <i-col span="13">
                                            <i-col span="12">{{item.contactName}}</i-col>
                                            <i-col span="12">{{item.contactPhone}}</i-col>
                                        </i-col>
                                        <i-col span="11" class="clear-padding">
                                            <i-col span="12">
                                                <Radio :label="index">主要联系人</Radio>
                                            </i-col>
                                            <i-col span="12">
                                                <div class="pull-right" style="padding-right: 8px;">
                                                    <i-button class="pull-left" style="margin-right: 15px;" icon="edit" size="small" type="dashed" @click="updateContact(index)"></i-button>
                                                    <Poptip class="text-left pull-left clear-margin-right" style="margin-right: 20px;height: 24px;"
                                                            confirm
                                                            title="确定要删除?"
                                                            @on-ok="deleteContact(index)">
                                                        <i-button class="pull-left" icon="trash-a" size="small" type="dashed"></i-button>
                                                    </Poptip>
                                                </div>
                                            </i-col>
                                        </i-col>
                                    </row>
                                </li>
                            </radio-group>
                        </ul>
                    </Card>
                    <form-item class="clear-margin-bottom">
                        <row :gutter="30">
                            <i-col span="6" offset="12">
                                <Poptip style="width: 100%"
                                    confirm
                                    title="确定要重置?"
                                    @on-ok="resetForm('addCustomer')">
                                    <i-button type="warning" long @click="">重置</i-button>
                                </Poptip>
                            </i-col>
                            <i-col span="6">
                                <i-button type="primary" :loading="btn.addCustomer" @click="addCustomer" long>提交</i-button>
                            </i-col>
                        </row>
                    </form-item>
                </i-form>
            </i-col>
            <i-col span="14">
                <i-col span="24" class="clear-padding">
                    <div id="map" ref="map" :style="{height:amap.height+'px'}"></div>
                </i-col>
            </i-col>
        </Row>
        <Modal
            v-model="modal.addContact"
            title="添加联系人"
            :mask-closable="false"
        >
            <div slot="footer">
                <div class="pull-right">
                    <Poptip class="text-left pull-left" style="margin-right: 20px;"
                            confirm
                            title="确定要清空?"
                            @on-ok="resetForm('addContact')">
                        <i-button type="warning">重置</i-button>
                    </Poptip>
                    <i-button type="primary" class="pull-left" :loading="btn.addContact" @click="addContactSubmit">提交</i-button>
                </div>
                <div class="clear"></div>
            </div>
            <i-form ref="addContact" :label-width="90" :model="contact" :rules="validate.contact">
                <form-item label="联系人名称" prop="contactName">
                    <i-input type="text" v-model="contact.contactName"></i-input>
                </form-item>
                <form-item label="联系人电话" prop="contactPhone">
                    <i-input type="text" v-model="contact.contactPhone"></i-input>
                </form-item>
                <form-item label="备注" class="clear-margin-bottom" prop="contactRemark">
                    <i-input type="textarea" v-model="contact.contactRemark" :autosize="{minRows: 5,maxRows: 5}"></i-input>
                </form-item>
            </i-form>
        </Modal>
        <Modal
            v-model="modal.updateContact"
            title="修改联系人"
            :mask-closable="false"
        >
            <div slot="footer">
                <div class="pull-right">
                    <Poptip class="text-left pull-left" style="margin-right: 20px;"
                            confirm
                            title="确定要清空?"
                            @on-ok="resetForm('updateContact')">
                        <i-button type="warning">重置</i-button>
                    </Poptip>
                    <i-button type="primary" class="pull-left" :loading="btn.updateContact" @click="updateContactSubmit">提交</i-button>
                </div>
                <div class="clear"></div>
            </div>
            <i-form ref="updateContact" :label-width="90" :model="contact" :rules="validate.contact">
                <form-item label="联系人名称" prop="contactName">
                    <i-input type="text" v-model="contact.contactName"></i-input>
                </form-item>
                <form-item label="联系人电话" prop="contactPhone">
                    <i-input type="text" v-model="contact.contactPhone"></i-input>
                </form-item>
                <form-item label="备注" class="clear-margin-bottom" prop="contactRemark">
                    <i-input type="textarea" v-model="contact.contactRemark" :autosize="{minRows: 5,maxRows: 5}"></i-input>
                </form-item>
            </i-form>
        </Modal>
    </div>
</body>
<script type="text/javascript">
    new Vue({
        el:"#app",
        data: function () {
            const validateName = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('必须输入客户姓名!'));
                }
                if (value.length > 20) {
                    return callback(new Error('客户名称不能大于20位!'));
                }
                axios.get('/customer/findName', {params:{customerName:value}}).then(res => {
                    if (res.data) {
                        callback(new Error('客户姓名已经存在!'));
                    } else {
                        callback();
                    }
                });
            };
            const validateNo = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('必须输入客户编号!'));
                }
                if (value.length >= 20) {
                    return callback(new Error('编号最多20位哦！'));
                }
                if (!new RegExp(/^[0-9A-Za-z]+$/).test(value)) {
                    return callback(new Error('编号只可以是字母和数字哦！'));
                }
                axios.get('/customer/findNo', {params:{customerNo:value}}).then(res => {
                    if (res.data) {
                        callback(new Error('客户编号已经存在!'));
                    } else {
                        callback();
                    }
                });
            };
            return {
                amap: {
                    map: '',
                    height:'',
                    geocoder:'',
                    markers:[],
                },
                labelList: [],
                customer: {
                    customerName:'',
                    customerNo:'',
                    longitude:'',
                    latitude:'',
                    city:'',
                    province:'',
                    district:'',
                    detailAddress:'',
                    level:'A级',
                    remark:'',
                    contactList:[],
                    labelList:[],
                    customerLabelList:[]
                },
                contactMainIndex: 0,
                contactIndex: '',
                contact:{
                    contactName: '',
                    contactPhone: '',
                    contactRemark: '',
                    isMain: '0'
                },
                address:{
                    data: [],
                    cityList: [],
                    districtList: [],
                },
                validate:{
                    addCustomer: {
                        customerName: [
                            {required: true, validator: validateName, trigger: 'blur'}
                        ],
                        customerNo: [
                            {required: true, validator: validateNo, trigger: 'blur'}
                        ],
                        level: [
                            {required: true, message: '等级必须选择', trigger: 'change'}
                        ],
                        detailAddress: [
                            {required: true, message: '客户地址必须填写！', trigger: 'blur'}
                        ],
                    },
                    contact: {
                        contactName:[
                            {required: true, message: '联系人名称必须填写！', trigger: 'blur'}
                        ],
                        contactPhone:[
                            {required: true, message: '联系人电话必须填写！', trigger: 'blur'}
                        ]
                    }
                },
                btn:{
                    addCustomer: false,
                    addContact: false,
                    updateContact: false
                },
                modal:{
                    addContact: false,
                    updateContact: false
                }
            }
        },
        methods: {
            initAddress () {
                axios.get('/static/js/address.json').then(res => {
                    this.address.data = res;
                    this.address.cityList = res[0].city;
                    this.address.districtList = res[0].city[0].area;
                }).catch(err => {
                    this.$Message.error("初始化省市县数据失败！")
                });
            },
            getLabel(){
                axios.get('/label/list').then(res => {
                    if (res.code === 200) {
                        this.labelList = res.data;
                    } else {
                        this.$Message.error(res.message);
                    }
                }).catch(err => {
                    this.$Message.error("标签列表获取失败");
                })
            },
            initMap () {
                const map = new AMap.Map('map', {
                    resizeEnable: true,
                    zoom: 16,
                });
                //延迟加载(确保地址数据已经渲染)
                setTimeout(() => {
                    //获取当前定位的城市
                    map.getCity(res => {
                        this.customer.province = res.province;
                        this.customer.city = res.city;
                        this.customer.district = res.district;
                    });
                }, 1000);
                this.amap.map = map;
                //初始化逆向地理位置
                this.amap.geocoder = new AMap.Geocoder({
                    radius: 1000,
                    extensions: "base"
                });
            },
            addMarker(position) {
                this.removeMarker();
                const icon = "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png";
                let marker = new AMap.Marker({
                    icon: icon,
                    position: position
                });
                marker.setMap(this.amap.map);
                this.amap.markers.push(marker);
            },
            removeMarker(){
                //删除点标记
                if (this.amap.markers.length !== 0) {
                    this.amap.markers.forEach(m => {m.setMap(null)})
                }
            },
            searchMap () {
                this.amap.map.clearMap();
                const _this = this;
                if (this.customer.detailAddress.trim() !== '') {
                    AMap.service("AMap.PlaceSearch", function () {
                        let placeSearch = new AMap.PlaceSearch({
                            pageSize: 50,
                            pageIndex: 1,
                            citylimit: true,                 //限制当前城市
                            city: _this.customer.city,       //城市
                            map: _this.amap.map,             //地图对象
                        });
                        placeSearch.clear();
                        //关键字查询
                        placeSearch.search(_this.customer.detailAddress);
                        AMap.event.addListener(placeSearch,"markerClick",function(e){
                            let position =  [e.data.location.lng,e.data.location.lat];
                            _this.customer.longitude = position[0];
                            _this.customer.latitude = position[1];
                            //逆向地理编码
                            _this.mapRegeocoder(position);
                        })
                    });
                } else {
                    this.$Notice.error({title: '您还未输入地址！请输入地址后在尝试点击'});
                    //验证单个字段
                    this.$refs['addCustomer'].validateField('detailAddress');
                }
            },
            mapClick() {
                let _this = this;
                AMap.event.addListener(this.amap.map, 'click', function (e) {
                    let position =  [e.lnglat.getLng(), e.lnglat.getLat()];
                    _this.customer.longitude = position[0];
                    _this.customer.latitude = position[1];
                    //点标记
                    _this.addMarker(position);
                    //逆向地理编码
                    _this.mapRegeocoder(position);
                });
            },
            mapRegeocoder(position) {
                let _this = this;
                this.amap.geocoder.getAddress(position, function (status, result) {
                    if (status === 'complete' && result.info === 'OK') {
                        let _address = result.regeocode.addressComponent;
                        console.log(_address);
                        _this.customer.province = _address.province;
                        //处理市和省是一样的城市
                        _this.customer.city = _address.city === "" ?_address.province : _address.city;
                        _this.customer.district = _address.district;
                        if (_address.building !== "") {
                            _this.customer.detailAddress = _address.township + _address.building;
                        } else {
                            _this.customer.detailAddress = _address.township + _address.street + _address.streetNumber;
                        }
                    }
                });
            },
            provinceChange (value) {
                console.log("provinceChange",value);
                let flag = true;
                this.address.data.forEach(o => {
                    if (o.name === value) {
                        this.customer.province = o.name;
                        this.address.cityList = o.city;
                        o.city.forEach(c => {
                            //如果当前城市在现在省份的列表中即不改变不存在则为第一个
                            if (this.customer.city === c.name) {
                                flag = false;
                            }
                        });
                        if (flag) {
                            this.customer.city = o.city[0].name;
                        }
                    }
                });
            },
            cityChange (value) {
                let flag = true;
                this.address.cityList.forEach((o) => {
                    if (o.name === value) {
                        this.address.districtList = o.area;
                        o.area.forEach(a => {
                            //如果当前城市在现在省份的列表中即不改变不存在则为第一个
                            if (this.customer.district === a.trim()) {
                                flag = false;
                            }
                        });
                        if (flag) {
                            this.customer.district = o.area[0];
                        }
                    }
                })
            },
            addContact () {
                this.resetForm('addContact');
                this.modal.addContact = true;
            },
            addContactSubmit () {
                this.btn.addContact = true;
                this.$refs['addContact'].validate(result => {
                    if (!result) {
                        return this.btn.addContact = false;
                    }
                    this.customer.contactList.push(JSON.parse(JSON.stringify(this.contact)));
                    this.btn.addContact = false;
                    this.resetForm('addContact');
                    this.modal.addContact = false;
                    this.$Notice.success({title: '添加成功！'});
                })
            },
            updateContact (index) {
                //标记当前联系人位置
                this.contactIndex = index;
                this.contact = JSON.parse(JSON.stringify(this.customer.contactList[index]));
                this.modal.updateContact = true;
            },
            updateContactSubmit () {
                this.btn.updateContact = true;
                this.$refs['updateContact'].validate(flag => {
                    if (!flag) {
                        return this.btn.updateContact = false;
                    }
                    this.customer.contactList.splice(this.contactIndex, 1, JSON.parse(JSON.stringify(this.contact)));
                    this.btn.updateContact = false;
                    this.modal.updateContact = false;
                    this.$Notice.success({title: '修改成功！'})
                });
            },
            deleteContact (index) {
                //如果当前删除的联系人为主要联系人则设置为0;
                if (index === this.contactMainIndex || this.contactMainIndex > this.customer.contactList.length - 2) {
                    this.contactMainIndex = 0;
                }
                this.customer.contactList.splice(index, 1);
            },
            addCustomer () {
                this.btn.addCustomer = true;
                this.$refs['addCustomer'].validate(result => {
                    if (!result) {
                        return this.btn.addCustomer = false;
                    }
                    if (this.customer.longitude === '' || this.customer.latitude === '') {
                        this.$Notice.error({title: '请在地图上标注位置哦！'});
                        return this.btn.addCustomer = false;
                    }
                    axios.get('/customer/findCoordinate', {
                        params: {
                            lon: this.customer.longitude,
                            lat: this.customer.latitude
                        }
                    }).then(res => {
                        if (res.code === 200) {
                            if (res.data) {
                                this.$Notice.error({title: '客户经纬度重复哦！'});
                            } else {
                                //判断是否添加联系人
                                if (this.customer.contactList.length > 0) {
                                    //设置为主要联系人
                                    this.customer.contactList[this.contactMainIndex].isMain = '1';
                                }
                                //计算标签
                                if (this.customer.labelList.length > 0){
                                    this.customer.customerLabelList = [];
                                    this.customer.labelList.forEach(cl => {
                                        let customerLabel = {labelId: cl};
                                        this.labelList.forEach(l => {
                                            if (cl === l.labelId) {
                                                customerLabel['labelName'] = l.labelName;
                                                this.customer.customerLabelList.push(customerLabel);
                                            }
                                        })
                                    })
                                }
                                axios.post('/customer',this.customer).then(res => {
                                    if (res.code === 200) {
                                        this.resetForm('addCustomer');
                                        this.$Notice.success({title: '添加成功！'})
                                    } else {
                                        this.$Message.error(res.message);
                                    }
                                }).catch(err => {
                                    this.$Notice.error({title: '添加客户失败！'})
                                });
                            }
                        } else {
                            this.$Notice.error({title: res.message});
                        }
                        return this.btn.addCustomer = false;
                    }).catch(err => {
                        this.$Notice.error({title: "客户经纬度校验失败！"});
                        return this.btn.addCustomer = false;
                    });
                })
            },
            resetForm (formName) {
                if (formName === 'addCustomer') {
                    this.removeMarker();
                    this.customer.longitude = '';
                    this.customer.latitude = '';
                    this.customer.contactList.length = 0;
                    this.contactMainIndex = 0;
                }
                this.$refs[formName].resetFields()
            }
        },
        created(){
            this.initAddress();
            this.getLabel();
        },
        mounted(){
            this.amap.height = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 40;
            this.initMap();
            this.mapClick();
        }
    })
</script>
</html>