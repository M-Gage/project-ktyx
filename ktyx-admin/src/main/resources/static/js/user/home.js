var home = {
    initData:{
        url:"order/home"
    },
    init: function () {
        $.ajax({
            url: home.initData.url,
            type: "get",
            success: function (res) {
                if (res.state === "success") {
                    var money = res.data.statementPojos, amount = [], date = [];



                   /* $("#addCustomerCount").text(res.data.addCustomerCount);
                    $("#dealCustomerCount").text(res.data.dealCustomerCount);
                    $("#dealOrderAmount").text(res.data.dealOrderAmount);
                    $("#dealOrderCount").text(res.data.dealOrderCount);*/



                    /*for (var i = 0; i < money.length; i++) {
                        amount.push(money[i].amount);
                        date.push(money[i].existsDate);
                    }*/
                    // 路径配置
                    /*require.config(
                        {
                        paths: {
                            echarts: '../static/js/plugins/echar2'
                        }
                    });
                    require([
                            'echarts',
                            'echarts/chart/line', 			//加载地图
                            'echarts/chart/pie' 			//加载地图
                        ],
                        function (ec) {
                            // 基于准备好的dom，初始化echarts图表
                            var myChart = ec.init(document.getElementById('main'));
                            option = {
                                title: {
                                    text: '销售金额图',
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
                                        data: date
                                    }
                                ],
                                grid: {
                                    x: 55
                                },
                                yAxis: [
                                    {
                                        type: 'value'
                                    }
                                ],
                                series: [
                                    {
                                        name: '成交',
                                        type: 'line',
                                        smooth: true,
                                        data: amount
                                    }
                                ]
                            };
                            myChart.setOption(option);
                        }
                    );*/
                } else {
                    layer.msg("未知错误，请与程序员联系")
                }
            }
        });
    }
};

home.init();