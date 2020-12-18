<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>客通云销 - Home</title>
    <link rel="stylesheet" href="../static/js/plugins/iview/iview.css">
    <link rel="stylesheet" href="../static/css/base.css">
    <script src="../static/js/vue.js"></script>
    <script src="../static/js/plugins/iview/iview.min.js"></script>
    <script src="../static/js/axios.min.js"></script>
    <script src="../static/js/axios-config.js"></script>
    <script src="../static/js/echarts-all.js"></script>
    <style>
        #app {
            background-color: #f3f3f3;
        }

        .home-left {
            padding: 0;
        }

        .home-left .home-left-top {
            height: 190px;
            padding: 0 !important;
        }

        .home-left .home-left-top .top-box {
            cursor: pointer;
        }

        .home-left .home-left-top .top-box .top-box-container:after {
            content: '';
            display: block;
            clear: both;
        }

        .home-left .home-left-top .top-box .top-box-container {
            box-shadow: 1px 1px 40px #e6e6e6;
            width: 100%;
            padding: 15px 25px;
            background-color: #fff;
        }

        .home-left .home-left-top .top-box .top-box-container .box-icon {
            float: left;
            font-size: 45px;
            width: 60px;
            height: 60px;
            border-radius: 10px;
            text-align: center;
        }

        .home-left .home-left-top .top-box .top-box-container .top-box-message {
            float: right;
        }

        .home-left .home-left-top .top-box .top-box-container .top-box-message .box-title {
            font-size: 17px;
            font-weight: bold;
            text-align: right;
            color: rgba(0, 0, 0, 0.45);
            letter-spacing: 1.5px;
        }

        .home-left .home-left-top .top-box .top-box-container .top-box-message .box-content {
            font-size: 25px;
            font-weight: bold;
            text-align: right;
        }

        .home-left .home-left-top .top-box .ivu-col:nth-child(1),
        .home-left .home-left-top .top-box .ivu-col:nth-child(2),
        .home-left .home-left-top .top-box .ivu-col:nth-child(3) {
            margin-bottom: 10px;
        }

        .home-left .home-left-top .top-box .ivu-col:nth-child(1) .top-box-container .box-icon,
        .home-left .home-left-top .top-box .ivu-col:nth-child(4) .top-box-container .box-icon {
            color: #f4516c;
            transition: all 0.5s;
        }

        .home-left .home-left-top .top-box .ivu-col:nth-child(1):hover .box-icon,
        .home-left .home-left-top .top-box .ivu-col:nth-child(4):hover .box-icon {
            color: white;
            background-color: #f4516c;
        }

        .home-left .home-left-top .top-box .ivu-col:nth-child(2) .top-box-container .box-icon,
        .home-left .home-left-top .top-box .ivu-col:nth-child(5) .top-box-container .box-icon {
            color: #36a3f7;
            transition: all 0.5s;
        }

        .home-left .home-left-top .top-box .ivu-col:nth-child(2):hover .box-icon,
        .home-left .home-left-top .top-box .ivu-col:nth-child(5):hover .box-icon {
            color: white;
            background-color: #36a3f7;
        }

        .home-left .home-left-top .top-box .ivu-col:nth-child(3) .top-box-container .box-icon,
        .home-left .home-left-top .top-box .ivu-col:nth-child(6) .top-box-container .box-icon {
            color: #2ec7c9;
            transition: all 0.5s;
        }

        .home-left .home-left-top .top-box .ivu-col:nth-child(3):hover .box-icon,
        .home-left .home-left-top .top-box .ivu-col:nth-child(6):hover .box-icon {
            color: white;
            background-color: #2ec7c9;
        }

        .home-left .home-left-bottom {
            margin-top: 20px;
            /*height: 600px;*/
        }

        .home-left .home-left-bottom .chart-title {
            background-color: #fff;
            border-bottom: 1px solid #d2d2d2;
            line-height: 40px;
            padding-left: 10px;
            font-size: 12px;
        }

        .line-box,
        .pie-box {
            box-shadow: 0 0 40px #e7e7e7;
        }

        #line,
        #pie {
            background-color: #fff !important;
        }

        .home-right .home-right-top {
            height: 250px;
        }

        .home-right .home-right-bottom {
            margin-top: 20px;
            background-color: #FFFFFF;
        }

        .ivu-ranking-li{
            line-height: 30px;
        }

        .ivu-card-body {
            height: 200px;
            overflow-x: auto;
        }

        .ivu-card-body::-webkit-scrollbar { /*滚动条整体样式*/
            width: 10px; /*高宽分别对应横竖滚动条的尺寸*/
            height: 1px;
        }

        .ivu-card-body::-webkit-scrollbar-thumb { /*滚动条里面小方块*/
            border-radius: 10px;
            -webkit-box-shadow: inset 0 0 5px rgba(83, 83, 83, 0.2);
            background: #a9a5aa;
        }

        .ivu-card-body::-webkit-scrollbar-track { /*滚动条里面轨道*/
            -webkit-box-shadow: inset 0 0 5px rgba(113, 113, 113, 0.2);
            border-radius: 10px;
            background: #EDEDED;
        }

        .title_list {
            border: solid 1px #0e9fef24;
            border-radius: 10px;
            margin: 6px;
            padding: 4px 4px 0 12px;
        }
        .no_announcement{
            margin: 10% 50%;
            color: red;
            font-size: 14px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div id="app" v-cloak>
    <Row :gutter="20">
        <i-col span="19" class="home-left">
            <i-col span="24" class="home-left-top">
                <Row class="top-box" :gutter="20">
                    <i-col span="8">
                        <div class="top-box-container">
                            <div class="box-icon">
                                <Icon type="social-yen"></Icon>
                            </div>
                            <div class="top-box-message">
                                <div class="box-title">今日成交金额</div>
                                <div class="box-content">{{singleData.one}}</div>
                            </div>
                        </div>
                    </i-col>
                    <i-col span="8">
                        <div class="top-box-container">
                            <div class="box-icon">
                                <Icon type="stats-bars"></Icon>
                            </div>
                            <div class="top-box-message">
                                <div class="box-title">本月成交金额</div>
                                <div class="box-content">{{singleData.two}}</div>
                            </div>
                        </div>
                    </i-col>
                    <i-col span="8">
                        <div class="top-box-container">
                            <div class="box-icon">
                                <Icon type="connection-bars"></Icon>
                            </div>
                            <div class="top-box-message">
                                <div class="box-title">上月成交金额</div>
                                <div class="box-content">{{singleData.three}}</div>
                            </div>
                        </div>
                    </i-col>
                    <i-col span="8">
                        <div class="top-box-container">
                            <div class="box-icon">
                                <Icon type="person"></Icon>
                            </div>
                            <div class="top-box-message">
                                <div class="box-title">今日新增客户</div>
                                <div class="box-content">{{singleData.four}}</div>
                            </div>
                        </div>
                    </i-col>
                    <i-col span="8">
                        <div class="top-box-container">
                            <div class="box-icon">
                                <Icon type="flag"></Icon>
                            </div>
                            <div class="top-box-message">
                                <div class="box-title">今日新增跟进</div>
                                <div class="box-content">{{singleData.five}}</div>
                            </div>
                        </div>
                    </i-col>
                    <i-col span="8">
                        <div class="top-box-container">
                            <div class="box-icon">
                                <Icon type="clipboard"></Icon>
                            </div>
                            <div class="top-box-message">
                                <div class="box-title">今日新增路程</div>
                                <div class="box-content">{{singleData.six}}</div>
                            </div>
                        </div>
                    </i-col>
                </Row>
            </i-col>
            <i-col span="24" class="home-left-bottom">
                <Row class="chart-container" :gutter="40">
                    <i-col span="14">
                        <div class="line-box">
                            <div class="chart-title">最近七天成交数据</div>
                            <div id="line" :style="{height: chartHeight +'px'}"></div>
                        </div>
                    </i-col>
                    <i-col span="10">
                        <div class="pie-box">
                            <div class="chart-title">最近七天商品种类销售分析</div>
                            <div id="pie" :style="{height: chartHeight +'px'}"></div>
                        </div>
                    </i-col>
                </Row>
            </i-col>
        </i-col>
        <i-col span="5" class="home-right">
            <i-col span="24" class="home-right-top clear-padding">
                <card dis-hover>
                    <p slot="title">
                        公告
                    </p>
                    <div v-if="announcementList.length==0" >
                        <p class="no_announcement">暂无公告</p>
                    </div>
                    <div v-else >
                        <ul v-for="(a,index) in announcementList">
                            <div class="title_list" @click="announcementInfo(a.announcementId)">
                                <h5>{{a.title}}</h5>
                            </div>
                        </ul>
                    </div>

                </card>
            </i-col>
            <i-col span="24" :style="{height: chartHeight-20 +'px'}"  class="home-right-bottom">
                <tabs>
                    <tab-pane label="商品榜">
                        <ul>
                            <li v-for="(item,index) in ranking.goodsList" :key="index">
                                <i-col span="24" class="ivu-ranking-li">
                                    <i-col span="18">
                                        <h3 class="text-ellipsis">{{item.goodsName}}</h3>
                                    </i-col>
                                    <i-col span="6">
                                        <h3> {{item.amount}}</h3>
                                    </i-col>
                                </i-col>
                            </li>
                        </ul>
                    </tab-pane>
                    <tab-pane label="业绩榜">
                        <ul>
                            <li v-for="(item,index) in ranking.staffList" :key="index">
                                <i-col span="24" class="ivu-ranking-li">
                                    <i-col span="18" class="text-ellipsis">
                                        <h3>{{item.staffName}}</h3>
                                    </i-col>
                                    <i-col span="6">
                                        <h3> {{item.amount}}</h3>
                                    </i-col>
                                </i-col>
                            </li>
                        </ul>
                    </tab-pane>
                </tabs>
            </i-col>
        </i-col>
    </Row>
    <Modal
            v-model="announcementModal"
            :title="announcementTitle">
        <div v-html="announcementContent">

        </div>
    </Modal>
</div>

</body>
<script type="text/javascript">
    new Vue({
        el: '#app',
        data() {
            return {
                goodsTypeData: [],
                singleData: {
                    one: 0,
                    two: 0,
                    three: 0,
                    four: 0,
                    five: 0,
                    six: 0
                },
                sevenDate: [],
                sevenDateInfo: [],
                windowHeight: "",
                announcementList: [],
                announcementTitle: "",
                announcementModal: false,
                announcementContent: false,
                ranking: {
                    staffList: [],
                    goodsList: []
                }
            }
        },
        methods: {
            loadData() {
                let _this = this;
                axios.all([
                    axios.get('/order/home/chart'),
                    axios.get('/order/home/list'),
                    axios.get('/order/home/bulletin')
                ]).then(axios.spread(function (chart, list,bulletin) {
                    if (chart.code === 200) {
                        chart.data.sevenDateData.forEach((v) => {
                            _this.sevenDateInfo.push(v.amount);
                            _this.sevenDate.push(v.existsDate);
                        });
                        chart.data.GoodsTypeData.forEach((v) => {
                            let gt = {
                                value: v.amount,
                                name: v.typeName
                            };
                            _this.goodsTypeData.push(gt)
                        });
                        _this.initLine();
                        _this.initPie();
                    } else {
                        _this.$Message.error(chart.message)
                    }
                    if (list.code === 200) {
                        _this.ranking.goodsList = list.data.GoodsData;
                        _this.ranking.staffList = list.data.StaffData;
                    } else {
                        _this.$Message.error(chart.message)
                    }
                    if (bulletin.code === 200) {
                        _this.singleData = bulletin.data;
                    } else {
                        _this.$Message.error(chart.message)
                    }
                })).catch(err => {
                    _this.$Message.error('首页数据加载失败！')
                });
            },
            initLine() {
                let myChart = echarts.init(document.getElementById('line'));
                const _this = this;
                option = {
                    title: {
                        // text: '最近七天成交数据',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    calculable: true,
                    xAxis: [
                        {
                            type: 'category',
                            boundaryGap: false,
                            data: _this.sevenDate,
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            type: 'line',
                            smooth: true,
                            itemStyle: {normal: {areaStyle: {type: 'default'}}},
                            data: _this.sevenDateInfo
                        }
                    ]
                };
                // 为echarts对象加载数据
                myChart.setOption(option);
            },
            initPie() {
                let myChart = echarts.init(document.getElementById('pie'));
                const _this = this;
                option = {
                    title: {
                        // text: '最近七天商品种类销售分析',
                        x: 'center'
                    },
                    calculable: true,
                    series: [
                        {
                            name: '面积模式',
                            type: 'pie',
                            radius: [30, 100],
                            center: ['50%', '50%'],
                            roseType: 'area',
                            x: '45%',               // for funnel
                            max: 40,                // for funnel
                            sort: 'ascending',     // for funnel
                            data: _this.goodsTypeData
                        }
                    ]
                };
                // 为echarts对象加载数据
                myChart.setOption(option);
            },
            initAnnouncement() {
                axios.get('/announcement').then(res => {
                    this.announcementList = res.data;
                })
            },
            announcementInfo(id) {
                console.log(id);
                console.log(this.announcementList);
                this.announcementModal = true;
                this.announcementList.forEach(a => {
                    console.log(a.announcementId == id);
                    if (a.announcementId == id) {
                        this.announcementTitle = a.title;
                        this.announcementContent = a.content;
                    }
                })
            },
            ok() {
                this.$Message.info('Clicked ok');
            },
            cancel() {
                this.$Message.info('Clicked cancel');
            }

        },
        computed: {
            chartHeight() {
                return this.windowHeight - 190 - 30 - 41 - 30;
            }
        },
        created() {
            this.loadData();
            this.initAnnouncement();
        },
        mounted() {
            this.windowHeight = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight);
        }
    })

</script>
</html>
