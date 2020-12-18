<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>客通云销 - 地图趋势报表</title>
    <link rel="stylesheet" href="../static/js/plugins/iview/iview.css">
    <link rel="stylesheet" href="../static/css/base.css">
    <link rel="stylesheet" href="../static/css/iconfont.css">
    <script src="../static/js/vue.js"></script>
    <script src="../static/js/axios.min.js"></script>
    <script src="../static/js/axios-config.js"></script>
    <script src="../static/js/plugins/iview/iview.min.js"></script>
    <script src="../static/js/plugins/echar2/echarts.js"></script>
</head>
    <body>
        <div id="app">
            <div ref="map" :style="{height: windowHeight + 'px'}" id="map"></div>
        </div>
    </body>
<script>
    new Vue({
        el:'#app',
        data() {
            return {
                customer:{
                    count: [],
                    flag: true
                },
                order:{
                    amount: [],
                    flag: false
                },
                windowHeight:""
            }
        },
        methods:{
            getData(){
                axios.get("/chart/map").then(res => {
                    if (res.code === 200) {
                        res.data.forEach(d => {
                            this.customer.count.push({
                                name: d.name,
                                value: d.customerCount
                            });
                            this.order.amount.push({
                                name: d.name,
                                value: d.orderAmount
                            })
                        });
                        this.initMap();
                    } else {
                        this.$Message.error(res.message)
                    }
                }).catch(err => {
                    this.$Message.error("地图数据加载失败！")
                })
            },
            initMap(){
                let index = 0;
                let _this = this;
                const mapType = [
                    'china',
                    // 23个省
                    '广东', '青海', '四川', '海南', '陕西',
                    '甘肃', '云南', '湖南', '湖北', '黑龙江',
                    '贵州', '山东', '江西', '河南', '河北',
                    '山西', '安徽', '福建', '浙江', '江苏',
                    '吉林', '辽宁', '台湾',
                    // 5个自治区
                    '新疆', '广西', '宁夏', '内蒙古', '西藏',
                    // 4个直辖市
                    '北京', '天津', '上海', '重庆',
                    // 2个特别行政区
                    '香港', '澳门'
                ];
                // 路径配置
                require.config({
                    paths: {
                        echarts: '../static/js/plugins/echar2'
                    }
                });
                // 入口
                require(
                    [
                        'echarts',
                        'echarts/chart/map' 			//加载地图
                    ],
                    function (ec) {
                        // 基于准备好的dom，初始化echarts图表
                        let myChart = ec.init(document.getElementById('map'));
                        //挂载事件
                        let ecConfig = require('echarts/config');
                        //legend点击事件
                        myChart.on(ecConfig.EVENT.LEGEND_SELECTED, function (param) {
                            _this.order.flag = param.selected.地区金额;
                            _this.customer.flag = param.selected.客户数量;
                        });
                        //地图选择事件
                        myChart.on(ecConfig.EVENT.MAP_SELECTED, function (param) {
                            let len = mapType.length;
                            let mt = mapType[index % len];
                            if (mt === 'china') {
                                // 全国选择时指定到选中的省份
                                let selected = param.selected;
                                for (let i in selected) {
                                    if (selected[i]) {
                                        mt = i;
                                        while (len--) {
                                            if (mapType[len] === mt) {
                                                index = len;
                                            }
                                        }
                                        break;
                                    }
                                }
                            } else {
                                index = 0;
                                mt = 'china';
                            }
                            option.series[0].mapType = mt;
                            option.series[1].mapType = mt;
                            option.title.subtext = mt + ' （点击切换）';
                            myChart.setOption(option, true);
                        });
                        option = {
                            title: {
                                text: '销售热点分布图',
                                x: 'left'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: function (params, ticket, callback) {
                                    let res = '' + params[1];
                                    let customer_count = "";
                                    let order_amount = "";
                                    if (_this.customer.flag) {
                                        for (let i = 0; i < option.series[0].data.length; i++) {
                                            if (option.series[0].data[i].name === params[1]) {
                                                customer_count = option.series[0].data[i].value;
                                            }
                                        }
                                        res += '<br/>' + "客户数量  :  " + (customer_count !== "" ? customer_count : 0)
                                    }
                                    if (_this.order.flag) {
                                        for (let i = 0; i < option.series[1].data.length; i++) {
                                            if (option.series[1].data[i].name === params[1]) {
                                                order_amount = option.series[1].data[i].value;
                                            }
                                        }
                                        res += '<br/>' + "地区金额  :  " + (order_amount !== "" ? order_amount : "0.00")
                                    }
                                    setTimeout(function () {
                                        // 仅为了模拟异步回调,每隔1000毫秒刷新一次
                                        callback(ticket, res);
                                    }, 1000);
                                    return 'loading';
                                }
                            },
                            legend: {
                                orient: 'vertical',
                                x: 'right',
                                data: ['客户数量', '地区金额'],
                                selected: {
                                    "地区金额": false
                                },
                                selectedMode: 'single'
                            },
                            dataRange: {
                                min: 0,
                                max: 1000000,
                                color: ['orange', 'yellow'],
                                text: ['高', '低'], // 文本，默认为数值文本
                                calculable: true
                            },
                            series: [
                                {
                                    name: '客户数量',
                                    type: 'map',
                                    mapType: 'china',
                                    selectedMode: 'single',
                                    itemStyle: {
                                        normal: {
                                            label: {
                                                show: true
                                            }
                                        },
                                        emphasis: {
                                            label: {
                                                show: true
                                            }
                                        }
                                    },
                                    data: _this.customer.count
                                },
                                {
                                    name: '地区金额',
                                    type: 'map',
                                    mapType: 'china',
                                    selectedMode: 'single',
                                    itemStyle: {
                                        normal: {
                                            label: {
                                                show: true
                                            }
                                        },
                                        emphasis: {
                                            label: {
                                                show: true
                                            }
                                        }
                                    },
                                    data: _this.order.amount
                                }
                            ]
                        };
                        myChart.setOption(option);
                    }
                );
            }
        },
        created () {
            this.getData();
        },
        mounted() {
            this.windowHeight = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 40;
        }
    })
</script>
</html>
