    <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>客通云销 - 客户管理</title>
    <link rel="stylesheet" href="../static/js/plugins/iview/iview.css">
    <link rel="stylesheet" href="../static/css/base.css">
    <script src="../static/js/vue.js"></script>
    <script src="http://webapi.amap.com/maps?v=1.4.6&key=85d5e0bd412ecfc92d29e3bdfc4b05e9&plugin=AMap.Geocoder"></script>
    <script src="../static/js/plugins/iview/iview.min.js"></script>
    <script src="../static/js/axios.min.js"></script>
    <script src="../static/js/axios-config.js"></script>
    <style>
        .ivu-page-options-sizer{
            margin-right: 0;
        }
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
        .input-append > i{
            margin-right: 25px;
        }
    </style>
</head>
<body>
    <div id="app" v-cloak>
        <Row>
            <i-col span="24" style="margin-bottom: 5px">
                <i-col span="10">
                    <row :gutter="16">
                        <i-col span="10">
                            <i-input type="text" v-model="table.searchParam.keyWord" clearable placeholder="关键字"></i-input>
                        </i-col>
                        <i-col span="5">
                            <i-button long type="primary" :loading="btn.tableSearch" @click="tableSearch">搜索</i-button>
                        </i-col>
                        <i-col span="5">
                            <i-button long type="dashed" @click="complexSearch">高级查询</i-button>
                        </i-col>
                    </row>
                </i-col>
                <div class="pull-right">
                    <div class="pull-left">
                        <i-button type="primary" long @click="allocationCustomer">分配客户</i-button>
                    </div>
                    <div  class="pull-left" style="margin: 0 20px">
                        <i-button  type="info" long icon="ios-cloud-upload-outline" @click="modal.uploadExcel = true">导入</i-button>
                    </div>
                    <div  class="pull-left">
                        <i-button  type="info" :loading="btn.exportExcel" long icon="ios-cloud-download-outline" @click="exportExcel">导出</i-button>
                    </div>
                </div>
            </i-col>
            <i-col span="24" style="overflow: hidden;margin-bottom: 5px">
                <div style="width: 3000px">
                    <i-button v-show="table.searchParamText.length > 0" @click="tableSearchReset" style="margin-right: 10px" type="warning" size="small">全部清空</i-button>
                    <Tag v-for="(item,index) in table.searchParamText" @on-close="tagClose" type="border" :key="index" :name="item.key" closable color="blue">{{item.name}} : {{item.value}}</Tag>
                </div>
            </i-col>
            <i-col span="24">
                <i-table
                    :loading="table.loading"
                    border
                    size="small"
                    width="100%"
                    :height="table.height"
                    :columns="table.columns"
                    :data="table.data"
                    @on-selection-change="tableSelect"
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
            v-model="modal.allocationCustomer"
            title="分配客户"
            :mask-closable="false"
        >
            <div slot="footer">
                <i-button type="primary" :loading="btn.allocationCustomer" @click="allocationCustomerSubmit">提交</i-button>
            </div>
            <i-form ref="allocationCustomer" :model="subordinate" :label-width="80">
                <form-item label="下属职员" prop="staffId" :rules="{required: true, message: '请选择下属职员', trigger: 'blur'}">
                    <i-select v-model="subordinate.staffId" filterable clearable>
                        <i-option v-for="item in subordinate.data" :value="item.staffId" :key="item.staffId">{{item.staffName}}</i-option>
                    </i-select>
                </form-item>
            </i-form>
        </Modal>
        <Modal
            v-model="modal.updateCustomer"
            width="60%"
            title="修改客户"
            :mask-closable="false"
        >
            <div slot="footer">
                <i-button type="primary" :loading="btn.updateCustomer" @click="updateCustomerSubmit">提交</i-button>
            </div>
            <row :gutter="30">
                <i-col span="12">
                    <i-form ref="updateCustomer" :model="customer" :label-width="80" :rules="validate.customer">
                        <row>
                            <i-col span="12" class="clear-padding">
                                <form-item label="客户名称" prop="customerName">
                                    <i-input type="text" v-model="customer.customerName"></i-input>
                                </form-item>
                            </i-col>
                            <i-col span="12" class="clear-padding">
                                <form-item label="客户编号" prop="customerNo">
                                    <i-input type="text" v-model="customer.customerNo"></i-input>
                                </form-item>
                            </i-col>
                        </row>
                        <form-item label="客户等级" prop="level">
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
                        <form-item label="备注信息" prop="remark">
                            <i-input type="textarea" v-model="customer.remark" :autosize="{minRows: 2,maxRows: 2}" placeholder="备注信息"></i-input>
                        </form-item>
                        <row>
                            <i-col span="18" class="clear-padding">
                                <Form-item label="修改联系人">
                                    <i-switch :disabled="customer.contactList.length === 0" v-model="updateContactFlag"></i-switch>
                                </Form-item>
                            </i-col>
                            <i-col span="6" class="clear-padding">
                                <i-button  type="info" long @click="addContact">添加联系人</i-button>
                            </i-col>
                        </row>
                    </i-form>
                    <transition name="fade">
                        <Card v-show="updateContactFlag" dis-hover>
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
                    </transition>
                </i-col>
                <i-col span="12">
                    <div id="map" :style="{height:amap.height + 'px'}"></div>
                </i-col>
            </row>
        </Modal>
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
        <Modal
            v-model="modal.complexSearch"
            title="高级搜索"
            :styles="{width: '700px'}"
            :mask-closable="false"
        >
            <div slot="footer">
                <div class="pull-right">
                    <i-button type="primary" :loading="btn.tableSearch" class="pull-left" @click="tableSearch">搜索</i-button>
                </div>
                <div class="clear"></div>
            </div>
            <i-form ref="tableSearch" :label-width="110" :model="table.searchParam" :rules="validate.tableSearch">
                <row :gutter="20">
                    <i-col span="12">
                        <form-item label="关键字" prop="keyWord">
                            <i-input type="text" v-model="table.searchParam.keyWord" clearable placeholder="关键字"></i-input>
                        </form-item>
                    </i-col>
                    <i-col span="12" >
                        <form-item label="创建时间区间" prop="createTimeArray">
                            <date-picker type="daterange" v-model="table.searchParam.createTimeArray" :options="dateOptions" placeholder="请选择" placement="bottom-end" :editable="false" style="width: 100%"></date-picker>
                        </form-item>
                    </i-col>
                    <i-col span="12">
                        <form-item label="最后下单时间区间" prop="orderTimeArray">
                            <date-picker type="daterange" v-model="table.searchParam.orderTimeArray" :options="dateOptions" placeholder="请选择" placement="bottom-end" :editable="false" style="width: 100%"></date-picker>
                        </form-item>
                    </i-col>
                    <i-col span="12">
                        <form-item label="最后跟进时间区间" prop="followTimeArray">
                            <date-picker type="daterange" v-model="table.searchParam.followTimeArray" :options="dateOptions" placeholder="请选择" placement="bottom-end" :editable="false" style="width: 100%"></date-picker>
                        </form-item>
                    </i-col>
                    <i-col span="12">
                        <form-item label="归属职员" prop="chiefId">
                            <i-select v-model="table.searchParam.chiefId" filterable clearable>
                                <i-option v-for="item in subordinate.data" :value="item.staffId" :key="item.staffId">{{item.staffName}}</i-option>
                            </i-select>
                        </form-item>
                    </i-col>
                    <i-col span="12">
                        <form-item label="归属部门" prop="deptId">
                            <input type="hidden" ref="deptId" value='${staff.deptId}'>
                            <i-select v-model="table.searchParam.deptId" clearable>
                                <i-option v-for="item in dept.optionList" :value="item.deptId" class="hidden" :key="item.deptId">{{item.deptName}}</i-option>
                                <Tree class="ivu-tree" :data="dept.data" @on-select-change="deptTreeSelect" style="padding-left: 20px"></Tree>
                            </i-select>
                        </form-item>
                    </i-col>
                    <i-col span="12">
                        <form-item label="跟进次数区间">
                            <row>
                                <i-col span="10" class="clear-padding">
                                    <form-item prop="minFollowCount">
                                        <i-input type="text" @on-change="betweenChange('followCount')" placeholder="最小" clearable v-model="table.searchParam.minFollowCount"></i-input>
                                    </form-item>
                                </i-col>
                                <i-col span="4" class="text-center">
                                    <span>至</span>
                                </i-col>
                                <i-col span="10" class="clear-padding">
                                    <form-item prop="maxFollowCount">
                                        <i-input type="text" @on-change="betweenChange('followCount')" placeholder="最大" clearable v-model="table.searchParam.maxFollowCount"></i-input>
                                    </form-item>
                                </i-col>
                            </row>
                        </form-item>
                    </i-col>
                    <i-col span="12">
                        <form-item label="下单次数区间">
                            <row>
                                <i-col span="10" class="clear-padding">
                                    <form-item prop="minOrderCount">
                                        <i-input type="text" @on-change="betweenChange('orderCount')" placeholder="最小" clearable v-model="table.searchParam.minOrderCount"></i-input>
                                    </form-item>
                                </i-col>
                                <i-col span="4" class="text-center">
                                    <span>至</span>
                                </i-col>
                                <i-col span="10" class="clear-padding">
                                    <form-item prop="maxOrderCount">
                                        <i-input type="text" @on-change="betweenChange('orderCount')" placeholder="最大" clearable v-model="table.searchParam.maxOrderCount"></i-input>
                                    </form-item>
                                </i-col>
                            </row>
                        </form-item>
                    </i-col>
                    <i-col span="24">
                        <form-item label="总订单金额区间">
                            <row>
                                <i-col span="11" class="clear-padding">
                                    <form-item prop="minOrderMoney">
                                        <i-input type="text" class="input-append" @on-change="betweenChange('orderMoney')" placeholder="最小"  clearable v-model="table.searchParam.minOrderMoney">
                                            <span slot="append">千</span>
                                        </i-input>
                                    </form-item>
                                </i-col>
                                <i-col span="2" class="text-center">
                                    <span>至</span>
                                </i-col>
                                <i-col span="11" class="clear-padding">
                                    <form-item prop="maxOrderMoney">
                                        <i-input type="text" class="input-append" @on-change="betweenChange('orderMoney')" placeholder="最大"  clearable v-model="table.searchParam.maxOrderMoney">
                                            <span slot="append">千</span>
                                        </i-input>
                                    </form-item>
                                </i-col>
                            </row>
                        </form-item>
                    </i-col>
                    <i-col span="24">
                        <form-item label="区域">
                            <row>
                                <i-col span="8" class="clear-padding">
                                    <form-item prop="province">
                                        <i-select placeholder="省" @on-change="provinceChange" v-model="table.searchParam.province" clearable>
                                            <i-option v-for="item in address.data" :value="item.name" :key="item.name"></i-option>
                                        </i-select>
                                    </form-item>
                                </i-col>
                                <i-col span="8">
                                    <form-item prop="city">
                                        <i-select placeholder="市" :disabled="table.searchParam.province === ''" @on-change="cityChange" v-model="table.searchParam.city" clearable>
                                            <i-option v-for="item in address.searchCityList" :value="item.name" :key="item.name"></i-option>
                                        </i-select>
                                    </form-item>
                                </i-col>
                                <i-col span="8" class="clear-padding">
                                    <form-item prop="district">
                                        <i-select  placeholder="县" :disabled="table.searchParam.city === ''" v-model="table.searchParam.district" clearable>
                                            <i-option v-for="item in address.searchdistrictList" :value="item" :key="item"></i-option>
                                        </i-select>
                                    </form-item>
                                </i-col>
                            </row>
                        </form-item>
                    </i-col>
                    <i-col span="24">
                        <form-item label="客户级别" prop="levelArray">
                            <checkbox-group v-model="table.searchParam.levelArray">
                                <Checkbox label="A级"></Checkbox>
                                <Checkbox label="B级"></Checkbox>
                                <Checkbox label="C级"></Checkbox>
                                <Checkbox label="D级"></Checkbox>
                            </checkbox-group>
                        </form-item>
                    </i-col>
                    <i-col span="24">
                        <form-item label="客户标签" prop="labelArray">
                            <checkbox-group v-model="table.searchParam.labelArray">
                                <checkbox v-for="item in labelList" :key="item.labelId" :label="item.labelId">{{item.labelName}}</checkbox>
                            </checkbox-group>
                        </form-item>
                    </i-col>
                </row>
            </i-form>
        </Modal>
        <Modal
            v-model="modal.uploadExcel"
            title="客户导入"
            width="300"
            :mask-closable="false"
        >
            <div slot="footer">
                <a href="/file/downFile/customer.xls" style="display: block;margin-bottom: 10px">下载客户导入模板</a>
            </div>
            <h3>导入说明:</h3>
            <p style="text-indent: 10px">导入的客户归属于操作人(导入人)</p>
            <p style="text-indent: 10px">导入模板数据列都是必填项，</p>
            <p style="text-indent: 10px">请务必填写完整，以确保数据完整性</p>
            <Upload
                type="drag"
                style="margin-top: 10px"
                :format="['xls','xlsx']"
                :show-upload-list="false"
                :on-success="uploadCustomerSuccess"
                :on-format-error="formatError"
                action="/customer/uploadExcel">
                <div style="padding: 20px 0">
                    <icon type="ios-cloud-upload" size="52" style="color: #3399ff"></icon>
                    <p>点击或拖动文件来上传</p>
                </div>
            </Upload>
        </Modal>
    </div>
</body>
<script type="text/javascript">
    new Vue({
        el:"#app",
        data () {
            const isDouble = (value) => {
                return new RegExp(/^\d{0,4}\.?\d{0,3}$/).test(value);
            };
            const isNumber = (value) => {
                return new RegExp(/^\d{0,5}$/).test(value);
            };
            const validateName = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('必须输入客户姓名!'));
                }
                axios.get('/customer/findName', {params:{customerName:value,customerId:this.customer.customerId}}).then(res => {
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
                if (value.length > 20) {
                    return callback(new Error('编号最多20位哦！'));
                }
                if (!new RegExp(/^\w+$/).test(value)) {
                    return callback(new Error('编号只可以是字母和数字哦！'));
                }
                axios.get('/customer/findNo', {params:{customerNo:value,customerId:this.customer.customerId}}).then(res => {
                    if (res.data) {
                        callback(new Error('客户编号已经存在!'));
                    } else {
                        callback();
                    }
                });
            };
            const validateMinFollowCount= (rule, value, callback) => {
                if (value.length > 4) {
                    return callback(new Error('长度超过四位'));
                }
                if (!isNumber(value)) {
                    return callback(new Error('必须为数字!'));
                }
                callback();
            };
            const validateMaxFollowCount= (rule, value, callback) => {
                if (value.length > 4) {
                    return callback(new Error('长度超过四位'));
                }
                if (!isNumber(value)) {
                    return callback(new Error('必须为数字!'));
                }
                //如果最小的值不是空
                if (this.table.searchParam.minFollowCount !== '' && value === '') {
                    return callback(new Error('必须填写最大'));
                }
                if (this.table.searchParam.minFollowCount === '' && value !== '') {
                    return callback(new Error('必须填写完整'));
                }
                if (parseInt(this.table.searchParam.minFollowCount) > parseInt(value)) {
                    return callback(new Error('必须大于最小'));
                }
                callback();
            };
            const validateMinOrderCount= (rule, value, callback) => {
                if (value.toString().length > 4) {
                    return callback(new Error('长度超过四位'));
                }
                if (!isNumber(value)) {
                    return callback(new Error('必须为数字!'));
                }
                callback();
            };
            const validateMaxOrderCount= (rule, value, callback) => {
                if (value.toString().length > 4) {
                    return callback(new Error('长度超过四位'));
                }
                if (!isNumber(value)) {
                    return callback(new Error('必须为数字!'));
                }
                //如果最小的值不是空
                if (this.table.searchParam.minOrderCount !== '' && value === '') {
                    return callback(new Error('必须填写最大'));
                }
                if (this.table.searchParam.minOrderCount === '' && value !== '') {
                    return callback(new Error('必须填写完整'));
                }
                if (parseInt(this.table.searchParam.minOrderCount) > parseInt(value)) {
                    return callback(new Error('必须大于最小'));
                }
                callback();
            };
            const validateMinOrderMoney= (rule, value, callback) => {
                if (value.indexOf('.') !== -1) {
                    if (value.length > 8) {
                        return callback(new Error('加上小数点不能超过八位'));
                    }
                    if(value.split(".")[0].length > 4){
                        return callback(new Error('小数点之前不能超过四位'));
                    }
                    if(value.split(".")[1].length > 3){
                        return callback(new Error('小数点之后不能超过三位'));
                    }
                }
                if (value.indexOf('.') === -1) {
                    if (value.length > 7) {
                        return callback(new Error('加上小数点不能超过八位'));
                    }
                }
                if (value.indexOf('.') === 0) {
                    return callback(new Error('小数点不能再最前面'));
                }
                if (!isDouble(value)) {
                    return callback(new Error('请输入正常的数字或者小数'));
                }
                callback();
            };
            const validateMaxOrderMoney= (rule, value, callback) => {
                if (value.indexOf('.') !== -1) {
                    if (value.length > 8) {
                        return callback(new Error('加上小数点不能超过八位'));
                    }
                    if(value.split(".")[0].length > 4){
                        return callback(new Error('小数点之前不能超过四位'));
                    }
                    if(value.split(".")[1].length > 3){
                        return callback(new Error('小数点之后不能超过三位'));
                    }
                }
                if (value.indexOf('.') === -1) {
                    if (value.length > 7) {
                        return callback(new Error('加上小数点不能超过八位'));
                    }
                }
                if (value.indexOf('.') === 0) {
                    return callback(new Error('小数点不能再最前面'));
                }
                if (!isDouble(value)) {
                    return callback(new Error('请输入正常的数字或者小数'));
                }
                //如果最小的值不是空
                if (this.table.searchParam.minOrderMoney !== '' && value === '') {
                    return callback(new Error('必须填写最大'));
                }
                if (this.table.searchParam.minOrderMoney === '' && value !== '') {
                    return callback(new Error('必须填写完整'));
                }
                if (parseInt(this.table.searchParam.minOrderMoney) > parseInt(value)) {
                    return callback(new Error('必须大于最小'));
                }
                callback();
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
                            type: "selection",
                            fixed: "left",
                            width: 60
                        },
                        {
                            title: "客户姓名",
                            align: 'center',
                            key: "customerName",
                            fixed: "left",
                            width: 250
                        },
                        {
                            title: "归属业务员",
                            width: 150,
                            fixed: "left",
                            key: "staffName",
                            align: 'center',
                        },
                        {
                            title: "客户编号",
                            key: "customerNo",
                            align: 'center',
                            sortable: 'custom',
                            width: 150,
                            render: (h, params) => {
                                return h('div', params.row.customerNo === undefined ? "暂无添加" : params.row.customerNo)
                            }
                        },
                        {
                            title: "创建时间",
                            key: "createTime",
                            align: 'center',
                            sortable: 'custom',
                            width: 180
                        },
                        {
                            title: "客户级别",
                            key: "level",
                            width: 120,
                            sortable: 'custom',
                            align: 'center'
                        },
                        {
                            title: "主要联系人",
                            width: 150,
                            align: 'center',
                            render: (h, params) => {
                                return h('div', params.row.contactList.length === 0 ?"暂无":params.row.contactList[0].contactName)
                            }
                        },
                        {
                            title: "主要联系人电话",
                            width: 150,
                            align: 'center',
                            render: (h, params) => {
                                return h('div', params.row.contactList.length === 0 ?"暂无":params.row.contactList[0].contactPhone)
                            }
                        },
                        {
                            title: "订单数",
                            key: "orderCount",
                            sortable: 'custom',
                            width: 100,
                            align: 'center'
                        },
                        {
                            title: "最后下单时间",
                            width: 150,
                            sortable: 'custom',
                            align: 'center',
                            render: (h, params) => {
                                return h('div', params.row.lastOrderTime === undefined ? "从未下单" : params.row.lastOrderTime);
                            }
                        },
                        {
                            title: "跟进次数",
                            key: "followCount",
                            sortable: 'custom',
                            width: 120,
                            align: 'center'
                        },
                        {
                            title: "最后跟进时间",
                            width: 150,
                            sortable: 'custom',
                            align: 'center',
                            render: (h, params) => {
                                return h('div', params.row.lastFollowTime === undefined ? "从未跟进" : params.row.lastFollowTime);
                            }
                        },
                        {
                            title: "客户标签",
                            key: "labels",
                            width: 350,
                            align: 'center',
                        },
                        {
                            title: "详细地址",
                            width: 650,
                            render: (h, params) => {
                                return h('div', params.row.province + params.row.city + params.row.district + params.row.detailAddress)
                            }
                        },
                        {
                            title: "备注",
                            key: "remark",
                            width: 300
                        },
                        {
                            title: "操作",
                            fixed: "right",
                            align: 'center',
                            width: 70,
                            render: (h, params) => {
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'primary',
                                            size: 'small'
                                        },
                                        style: {
                                            marginRight: '5px'
                                        },
                                        on: {
                                            click: () => {
                                                this.getCustomer(params.row.customerId);
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
                            width: 100,
                            render: (h, params) => {
                                return h('div', [
                                    h('i-switch', {
                                        props: {
                                            value: params.row.customerStatus === 1
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
                        sortColumn: 'lastModifyTime'
                    },
                    searchParam:{
                        keyWord: '',
                        createTimeArray: [],
                        orderTimeArray:[],
                        followTimeArray:[],
                        levelArray: [],
                        labelArray: [],
                        deptId: '',
                        chiefId: '',
                        minFollowCount: '',
                        maxFollowCount: '',
                        minOrderCount: '',
                        maxOrderCount: '',
                        minOrderMoney: '',
                        maxOrderMoney: '',
                        province:'',
                        city:'',
                        district:'',
                    },
                    searchParamText:[],
                    loading: false,
                    selected:[],
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
                customer:{
                    city: '',
                    contactList: [],
                    customerId: '',
                    labelList: [],
                    customerLabelList: [],
                    customerName: '',
                    customerNo: '',
                    detailAddress: '',
                    district: '',
                    latitude: '',
                    level: '',
                    longitude: '',
                    province: '',
                    remark: ''
                },
                labelList:[],
                dept:{
                    optionList:[],
                    data:[]
                },
                contactMainIndex: 0,
                modal: {
                    complexSearch: false,
                    allocationCustomer: false,
                    updateCustomer: false,
                    addContact: false,
                    updateContact: false,
                    uploadExcel: false
                },
                subordinate: {
                    staffId: '',
                    data:[],
                    keyWord: ''
                },
                btn: {
                    tableSearch: false,
                    allocationCustomer: false,
                    addContact: false,
                    updateCustomer:false,
                    updateContact: false,
                    exportExcel:false
                },
                address:{
                    data: [],
                    cityList: [],
                    searchCityList: [],
                    districtList: [],
                    searchdistrictList: [],
                },
                amap: {
                    map: '',
                    height: '455',
                    geocoder: '',
                    markers:[]
                },
                contact:{
                    contactName: '',
                    contactPhone: '',
                    contactRemark: '',
                    isMain: '0'
                },
                contactIndex:'',
                updateContactFlag: false,
                validate:{
                    customer:{
                        customerName: [
                            {required: true, validator: validateName, trigger: 'blur'}
                        ],
                        customerNo: [
                            {required: true, validator: validateNo, trigger: 'blur'}
                        ],
                        level: [
                            {required: true, message: '客户等级为必选', trigger: 'blur'}
                        ],
                        detailAddress: [
                            {required: true, message: '请输入详细地址 ', trigger: 'blur'}
                        ]
                    },
                    contact: {
                        contactName:[
                            {required: true, message: '联系人名称必须填写！', trigger: 'blur'}
                        ],
                        contactPhone:[
                            {required: true, message: '联系人电话必须填写！', trigger: 'blur'}
                        ]
                    },
                    tableSearch:{
                        minFollowCount:[
                            {validator: validateMinFollowCount, trigger: 'blur'}
                        ],
                        maxFollowCount:[
                            {validator: validateMaxFollowCount, trigger: 'blur'}
                        ],
                        minOrderCount:[
                            {validator: validateMinOrderCount, trigger: 'blur'}
                        ],
                        maxOrderCount:[
                            {validator: validateMaxOrderCount, trigger: 'blur'}
                        ],
                        minOrderMoney:[
                            {validator: validateMinOrderMoney, trigger: 'blur'}
                        ],
                        maxOrderMoney:[
                            {validator: validateMaxOrderMoney, trigger: 'blur'}
                        ]
                    }
                },
            }
        },
        computed: {
            labels() {
                let labelStr = '';
                this.customer.labelList.forEach(cl => {
                    this.labelList.forEach(l => {
                        if (l.labelId === cl) {
                            labelStr += ',' + l.labelName;
                        }
                    })
                });
                return labelStr.substring(1)
            },
            customerLabelList () {
                let customerLabelList = [];
                this.customer.labelList.forEach(l => {customerLabelList.push({labelId:l})});
                return customerLabelList
            }
        },
        methods:{
            getTableData (searchParam) {
                this.table.loading = true;
                if (this.table.selected.length !== 0) {
                    this.table.selected = [];
                }
                //基本参数
                let param = JSON.parse(JSON.stringify(this.table.param));
                if (searchParam){
                    for (let item in searchParam){
                        let o =  searchParam[item];
                        //忽略值
                        switch (item) {
                            case "minFollowCount":
                            case "maxFollowCount":
                            case "minOrderCount":
                            case "maxOrderCount":
                            case "minOrderMoney":
                            case "maxOrderMoney":
                                continue;
                            default:
                                if (o === '') {
                                    continue
                                }
                        }
                        if (o instanceof Array) {
                            if (o.length === 0 || o[0] === '' || o[1] === '') {
                                continue;
                            }
                        }
                        param[item] = o;
                    }
                    //跟进次数数组
                    if (searchParam.minFollowCount !== '' && searchParam.maxFollowCount !== '') {
                        param.followCountArray = [searchParam.minFollowCount, searchParam.maxFollowCount];
                    }
                    //下单次数数组
                    if (searchParam.minOrderCount !== '' && searchParam.maxOrderCount !== '') {
                        param.orderCountArray = [searchParam.minOrderCount, searchParam.maxOrderCount];
                    }
                    //总订单金额数组
                    if (searchParam.minOrderMoney !== '' && searchParam.maxOrderMoney !== '') {
                        param.orderMoneyArray = [searchParam.minOrderMoney, searchParam.maxOrderMoney];
                    }
                    console.log(JSON.stringify(param));
                    this.table.searchParamText = [];
                    //获取参数
                    for (let p in param){
                        switch (p){
                            case 'keyWord':
                                this.table.searchParamText.push({
                                    name: "关键字",
                                    key:"keyWord",
                                    value: param[p]
                                });
                                break;
                            case 'createTimeArray':
                                this.table.searchParamText.push({
                                    name: "创建时间区间",
                                    key:"createTimeArray",
                                    value: new Date(param[p][0]).format('yyyy-MM-dd') + " 至 " + new Date(param[p][1]).format('yyyy-MM-dd')
                                });
                                break;
                            case 'orderTimeArray':
                                this.table.searchParamText.push({
                                    name: "最后下单时间区间",
                                    key:"orderTimeArray",
                                    value: new Date(param[p][0]).format('yyyy-MM-dd') + " 至 " + new Date(param[p][1]).format('yyyy-MM-dd')
                                });
                                break;
                            case 'followTimeArray':
                                this.table.searchParamText.push({
                                    name: "最后跟进时间区间",
                                    key:"followTimeArray",
                                    value: new Date(param[p][0]).format('yyyy-MM-dd') + " 至 " + new Date(param[p][1]).format('yyyy-MM-dd')
                                });
                                break;
                            case 'levelArray':
                                this.table.searchParamText.push({
                                    name: "等级",
                                    key:"levelArray",
                                    value: param[p].join(",")
                                });
                                break;
                            case 'labelArray':
                                let labelstr = '';
                                this.labelList.forEach(l => {
                                    param[p].forEach(i => {
                                        if (l.labelId === i) {
                                            labelstr += l.labelName + ",";
                                        }
                                    })
                                });
                                console.log(labelstr);
                                this.table.searchParamText.push({
                                    name: "标签",
                                    key:"labelArray",
                                    value: labelstr.substring(0,labelstr.length - 1)
                                });
                                break;
                            case 'deptId':
                                this.table.searchParamText.push({
                                    name: "部门",
                                    key:"deptId",
                                    value: this.dept.optionList.filter(d => d.deptId === param[p])[0].deptName
                                });
                                break;
                            case 'chiefId':
                                this.table.searchParamText.push({
                                    name: "下属",
                                    key:"chiefId",
                                    value: this.subordinate.data.filter(s => s.staffId === param[p])[0].staffName
                                });
                                break;
                            case 'followCountArray':
                                this.table.searchParamText.push({
                                    name: "跟进数量区间",
                                    key:"followCountArray",
                                    value: param[p][0] + " 至 " + param[p][1]
                                });
                                break;
                            case 'orderCountArray':
                                this.table.searchParamText.push({
                                    name: "订单数量区间",
                                    key:"orderCountArray",
                                    value: param[p][0] + " 至 " + param[p][1]
                                });
                                break;
                            case 'orderMoneyArray':
                                this.table.searchParamText.push({
                                    name: "总订单金额区间",
                                    key:"orderMoneyArray",
                                    value: param[p][0] * 1000 + "元 至 " + param[p][1] * 1000 + "元"
                                });
                                break;
                            case 'province':
                                this.table.searchParamText.push({
                                    name: "省",
                                    key:"province",
                                    value: param[p]
                                });
                                break;
                            case 'city':
                                this.table.searchParamText.push({
                                    name: "市",
                                    key:"city",
                                    value: param[p]
                                });
                                break;
                            case 'district':
                                this.table.searchParamText.push({
                                    name: "县",
                                    key:"district",
                                    value: param[p]
                                });
                                break;
                        }
                    }
                }
                axios.get('/customer/list',{params:{param: encodeURI(JSON.stringify(param))}}).then((res) => {
                    if (res.code === 200){
                        this.table.data = res.data.rows;
                        this.table.total = res.data.total;
                        this.table.loading = false;
                    } else {
                        this.$Message.error(res.message);
                        this.table.loading = false;
                    }
                }).catch((err)=>{
                    this.$Message.error('获取客户列表数据失败');
                    this.table.loading = false;
                })
            },
            tagClose(event,name){
                switch (name){
                    case "followCountArray":
                        this.table.searchParam.minFollowCount = '';
                        this.table.searchParam.maxFollowCount = '';
                        break;
                    case "orderCountArray":
                        this.table.searchParam.minOrderCount = '';
                        this.table.searchParam.maxOrderCount = '';
                        break;
                    case "orderMoneyArray":
                        this.table.searchParam.minOrderMoney = '';
                        this.table.searchParam.maxOrderMoney = '';
                        break;
                    case "levelArray":
                        this.table.searchParam.levelArray = [];
                        break;
                    case "labelArray":
                        this.table.searchParam.labelArray = [];
                        break;
                    case "createTimeArray":
                        this.table.searchParam.createTimeArray = [];
                        break;
                    case "orderTimeArray":
                        this.table.searchParam.orderTimeArray = [];
                        break;
                    case "followTimeArray":
                        this.table.searchParam.followTimeArray = [];
                        break;
                    case "province":
                        this.table.searchParam.province = '';
                        this.table.searchParam.city = '';
                        this.table.searchParam.district = '';
                        break;
                    case "city":
                        this.table.searchParam.city = '';
                        this.table.searchParam.district = '';
                        break;
                    default:
                        this.table.searchParam[name] = '';
                }
                this.getTableData(this.table.searchParam);
            },
            tableSort (value) {
                if (value.order === 'normal') {
                    this.table.param.sortOrder = 'desc';
                    this.table.param.sortColumn = 'lastModifyTime';
                } else {
                    this.table.param.sortOrder = value.order;
                    this.table.param.sortColumn = value.key;
                }
                this.getTableData(this.table.searchParam);
            },
            tableSelect(data) {
                this.table.selected = data;
            },
            changePage (num) {
                this.table.param.pageNumber = num;
                this.getTableData(this.table.searchParam);
            },
            changePageSize (size) {
                this.table.param.pageSize = size;
                this.getTableData(this.table.searchParam);
            },
            complexSearch(){
                this.getDeptData();
                this.getLabel();
                this.getSubordinates();
                this.modal.complexSearch = true;
            },
            tableSearchReset() {
                this.resetForm('tableSearch');
                this.getTableData(this.table.searchParam);
            },
            betweenChange(value){
                //校验区间 偷懒写法
                const name =  value.substring(0, 1).toUpperCase() + value.substring(1, value.length);
                this.$refs['tableSearch'].validateField('min' + name);
                this.$refs['tableSearch'].validateField('max' + name);
            },
            tableSearch(){
                this.btn.tableSearch = true;
                this.$refs['tableSearch'].validate(result => {
                    if (!result) {
                        return this.btn.tableSearch = false;
                    }
                    this.btn.tableSearch = false;
                    //默认第一页
                    this.table.param.pageNumber = 1;
                    this.$refs["pages"].currentPage = 1;
                    this.getTableData(this.table.searchParam);
                    this.modal.complexSearch = false;
                });
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
            deptTreeSelect(node){
                if (node.length !== 0) {
                    this.table.searchParam.deptId = node[0].deptId;
                } else {
                    this.table.searchParam.deptId = '';
                }
            },
            getLabel(){
                if (this.labelList.length === 0) {
                    axios.get('/label/list').then(res => {
                        if (res.code === 200) {
                            this.labelList = res.data;
                        } else {
                            this.$Message.error(res.message);
                        }
                    }).catch(err => {
                        this.$Message.error("标签列表获取失败");
                    })
                }
            },
            changeState (index) {
                let customer = this.table.data[index];
                let status = customer.customerStatus === 0 ? 1 : 0;
                this.table.loading = true;
                axios.put('/customer/' + customer.customerId + "/" + status).then(res => {
                    if (res.code === 200) {
                        this.$Message.success('修改成功！');
                        setTimeout(()=>{
                            this.table.data[index].customerStatus = status;
                            this.table.loading = false;
                        },400)
                    } else {
                        this.$Notice.error({title: res.message});
                        this.table.loading = false;
                    }
                }).catch(err => {
                    this.$Message.error("客户状态修改失败！");
                    this.table.loading = false;
                })
            },
            getSubordinates() {
                if (this.subordinate.data.length === 0) {
                    axios.get('/staff/subordinates').then(res => {
                        if (res.code === 200) {
                            this.subordinate.data = res.data;
                        } else {
                            this.$Notice.error({title: res.message});
                        }
                    }).catch(err => {
                        this.$Notice.error({title: "获取下级职员列表失败！"});
                    });
                }
            },
            allocationCustomer() {
                if (this.table.selected.length === 0) {
                    this.$Message.error("必须选中一个客户哦！");
                } else {
                    this.getSubordinates();
                    this.modal.allocationCustomer = true;
                }
            },
            allocationCustomerSubmit() {
                this.btn.allocationCustomer = true;
                this.$refs['allocationCustomer'].validate((result) => {
                    if (!result) {
                        return this.btn.allocationCustomer = false;
                    }
                    let staffId = this.subordinate.staffId;
                    let staffName = '';
                    this.subordinate.data.forEach(s => {
                        if (s.staffId === staffId) {
                            staffName = s.staffName;
                        }
                    });
                    let customerIdArray = [];
                    this.table.selected.forEach(s => {customerIdArray.push(s.customerId);});
                    axios.put('/customer/' + staffId, {customerIdArray:customerIdArray,staffName:staffName}).then(res => {
                        if (res.code === 200) {
                            this.modal.allocationCustomer = false;
                            this.$refs['allocationCustomer'].resetFields();
                            this.btn.allocationCustomer = false;
                            this.getTableData();
                            setTimeout(()=>{
                                this.$Notice.success({title: "提交成功"});
                            },400)
                        } else {
                            this.$Notice.error({title: res.message});
                            this.btn.allocationCustomer = false;
                        }
                    }).catch(err => {
                        this.$Notice.error({title: '分配失败！'});
                        this.btn.allocationCustomer = false;
                    })
                });
            },
            getCustomer (customerId) {
                this.getLabel();
                axios.get('/customer/' + customerId).then(res => {
                    if (res.code === 200) {
                        this.updateContactFlag = false;
                        this.customer = res.data;
                        this.initMap();
                        //添加点标记
                        this.addMarker([res.data.longitude, res.data.latitude]);
                        this.modal.updateCustomer = true;
                        this.$set(this.customer,'labelList', []);
                        this.$set(this.customer,'deleteContactIdArray', []);
                        //检索出主要联系人的下标
                        if (res.data.contactList.length !== 0) {
                            this.contactMainIndex = res.data.contactList.findIndex(value => {return value.isMain === 1});
                        } else {
                            this.contactMainIndex = 0;
                        }
                        //默认选中标签
                        if (res.data.customerLabelList.length !== 0) {
                            res.data.customerLabelList.forEach(cl => {this.customer.labelList.push(cl.labelId)});
                        }
                    } else {
                        this.$Notice.error({title: res.message});
                    }
                }).catch(err => {
                    this.$Notice.error({title: '获取客户信息失败！'});
                });
            },
            initMap(){
                let position = [this.customer.longitude, this.customer.latitude];
                this.amap.map = new AMap.Map('map', {
                    resizeEnable: true,
                    zoom: 16,
                    center: position
                });
                //初始化逆向地理位置
                this.amap.geocoder = new AMap.Geocoder({
                    radius: 1000,
                    extensions: "base"
                });
                this.mapClick();
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
            initAddress () {
                axios.get('/static/js/address.json').then(res => {
                    this.address.data = res;
                    this.address.cityList = res[0].city;
                    this.address.searchCityList = res[0].city;
                    //搜索一份
                    this.address.districtList = res[0].city[0].area;
                    this.address.searchdistrictList = res[0].city[0].area;
                }).catch(err => {
                    this.$Message.error("初始化省市县数据失败！")
                });
            },
            provinceChange (value) {
                let flag = true;
                let isTableSearch = this.modal.complexSearch;
                //不为空
                if (value) {
                    this.address.data.forEach(o => {
                        if (o.name === value) {
                            //判断是否表格高级搜索
                            if (isTableSearch) {
                                this.address.searchCityList = o.city;
                                this.table.searchParam.province = o.name;
                                this.table.searchParam.city = o.city[0].name;
                            } else {
                                this.address.cityList = o.city;
                                this.customer.province = o.name;
                                o.city.forEach(c => {
                                    //如果当前城市在现在省份的列表中即不改变不存在则为第一个
                                    if (this.customer.city === c.name) {
                                        flag = false;
                                    }
                                });
                            }
                            //判断是否要设置为第一个
                            if (flag && !isTableSearch) {
                                this.customer.city = o.city[0].name;
                            }
                        }
                    });
                } else {
                    if (isTableSearch) {
                        this.table.searchParam.city = '';
                    }
                }
            },
            cityChange (value) {
                let flag = true;
                let isTableSearch = this.modal.complexSearch;
                //不为空
                if (value) {
                    if (isTableSearch) {
                        this.address.searchCityList.forEach((o) => {
                            if (o.name === value) {
                                this.address.searchdistrictList = o.area;
                                this.table.searchParam.district = o.area[0];
                            }
                        })
                    } else {
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
                    }
                }else {
                    if (isTableSearch) {
                        this.table.searchParam.district = '';
                    }
                }
            },
            addContact() {
                this.resetForm('addContact');
                this.modal.addContact = true
            },
            addContactSubmit(){
                this.btn.addContact = true;
                this.$refs['addContact'].validate(result => {
                    if (!result) {
                        return this.btn.addContact = false;
                    }
                    this.customer.contactList.push(JSON.parse(JSON.stringify(this.contact)));
                    this.btn.addContact = false;
                    //添加客户时标记要修改联系人
                    this.updateContactFlag = true;
                    this.resetForm('addContact');
                    this.modal.addContact = false;
                    this.$Notice.success({title: '添加成功，提交后生效哦！'});
                })
            },
            updateContact(index) {
                this.contactIndex = index;
                this.modal.updateContact = true;
                this.contact = JSON.parse(JSON.stringify(this.customer.contactList[index]));
            },
            updateContactSubmit(){
                this.btn.updateContact = true;
                this.$refs['updateContact'].validate(flag => {
                    if (!flag) {
                        return this.btn.updateContact = false;
                    }
                    this.customer.contactList.splice(this.contactIndex, 1, JSON.parse(JSON.stringify(this.contact)));
                    this.btn.updateContact = false;
                    this.modal.updateContact = false;
                    this.$Notice.success({title: '修改成功，提交后生效哦！'})
                });
            },
            deleteContact(index) {
                let contactId = this.customer.contactList[index].contactId;
                if (contactId) {
                    this.customer.deleteContactIdArray.push(contactId);
                }
                //删除了主要联系人 主要联系人默认为第一个
                if (this.contactMainIndex === index || this.contactMainIndex > this.customer.contactList.length - 2) {
                    this.contactMainIndex = 0;
                }
                this.customer.contactList.splice(index, 1);
                //如果都删除了标示不用修改联系人
                if (this.customer.contactList.length === 0) {
                    this.updateContactFlag = false;
                }
                this.$Notice.success({title: '删除成功，提交后生效哦！'})
            },
            updateCustomerSubmit() {
                this.btn.updateCustomer = true;
                this.$refs['updateCustomer'].validate(flag => {
                    if (!flag) {
                        return this.btn.updateCustomer = false;
                    }
                    //校验经纬度
                    axios.get('/customer/findCoordinate', {
                        params: {
                            lon: this.customer.longitude,
                            lat: this.customer.latitude,
                            customerId: this.customer.customerId
                        }
                    }).then(res => {
                        if (res.code === 200) {
                            if (res.data) {
                                this.$Notice.error({title: '经纬度重复哦！'});
                            } else {
                                //如果不修改联系人就删除联系人属性
                                if (!this.updateContactFlag) {
                                    this.customer.contactList = [];
                                } else {
                                    this.customer.contactList.forEach((c,index) => {
                                        if (index === this.contactMainIndex) {
                                            this.customer.contactList[index].isMain = 1;
                                        } else {
                                            this.customer.contactList[index].isMain = 0;
                                        }
                                    })
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
                                axios.put('/customer',this.customer).then(res => {
                                    if (res.code === 200) {
                                        this.getTableData();
                                        this.btn.updateCustomer = false;
                                        this.modal.updateCustomer = false;
                                        this.updateContactFlag = false;
                                        setTimeout(()=>{
                                            this.$Notice.success({title: '职员更新成功！'});
                                        },400)
                                    } else {
                                        this.$Notice.error({title: res.message})
                                    }
                                }).catch(err => {
                                    this.$Notice.error({title: '客户更新失败！'})
                                })
                            }
                        } else {
                            this.$Notice.error({title: res.message});
                        }
                        this.btn.updateCustomer = false;
                    }).catch(err => {
                        this.$Notice.error({title: "客户经纬度校验失败！"});
                    });
                });
            },
            resetForm (formName) {
                this.$refs[formName].resetFields()
            },
            exportExcel(){
                this.btn.exportExcel = true;
                window.location.href = "/file/export/customer";
                setTimeout(()=>{
                    this.btn.exportExcel = false;
                },1000)
            },
            uploadCustomerSuccess(response, file, fileList){
                if (response.code === 200) {
                    this.modal.uploadExcel = false;
                    this.getTableData();
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
            this.initAddress();
        },
        mounted(){
            this.table.height = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 158;
        }
    })
</script>
</html>