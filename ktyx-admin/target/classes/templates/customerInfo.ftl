<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>客通云销 - 标签管理</title>
    <link rel="stylesheet" href="../static/js/plugins/iview/iview.css">
    <link rel="stylesheet" href="../static/css/base.css">
    <link rel="stylesheet" href="//at.alicdn.com/t/font_514640_bqisvw9som.css">
    <script src="../static/js/vue.js"></script>
    <script src="../static/js/plugins/iview/iview.min.js"></script>
    <script src="../static/js/axios.min.js"></script>
    <script src="../static/js/axios-config.js"></script>
    <style>
        .iconfont{
            font-size: 30px;
        }
    </style>
</head>
<body>
<div id="app" v-cloak>
    <row :gutter="30">
        <i-col span="6">
            <i-form :label-width="120" >
                <form-item label="添加客户1/个 : 得">
                    <i-input type="text"></i-input>
                </form-item>
                <form-item label="报计划1/单 : 得">
                    <i-input type="text"></i-input>
                </form-item>
                <form-item label="添加跟进1/条 : 得">
                    <i-input type="text"></i-input>
                </form-item>
                <form-item label="跟进(获赞)1/条 : 得">
                    <i-input type="text"></i-input>
                </form-item>
                <form-item label="跟进(获踩)1/条 : 扣">
                    <i-input type="text"></i-input>
                </form-item>
                <form-item>
                    <i-button type="primary">提交</i-button>
                </form-item>
            </i-form>
        </i-col>
        <i-col span="18">
            <i-button type="primary">添加</i-button>
            <i-table :columns="columns1" :data="data1"></i-table>
        </i-col>
    </row>
</div>
</body>
<script type="text/javascript">
    new Vue({
        el:"#app",
        data () {
            return {
                columns1: [
                    {
                        title: '等级',
                        key: 'name'
                    },
                    {
                        title: '经验值',
                        key: 'age'
                    },
                    {
                        title: '图标',
                        key: 'address',
                        render: (h, params) => {
                            return h('i', {
                                class : "iconfont icon-level-1"
                            })
                        }
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
                                        }
                                    }
                                }, '编辑')
                            ]);
                        }
                    },
                ],
                data1: [
                    {
                        name: 'LV0',
                        age: 18,
                        date: '2016-10-03'
                    },
                    {
                        name: 'LV1',
                        age: 18,
                        address: '100',
                        date: '2016-10-03'
                    },
                    {
                        name: 'LV2',
                        age: 24,
                        address: '300',
                        date: '2016-10-01'
                    },
                    {
                        name: 'LV3',
                        age: 30,
                        address: '800',
                        date: '2016-10-02'
                    },
                    {
                        name: 'LV4',
                        age: 26,
                        address: '1500',
                        date: '2016-10-04'
                    }
                ]
            }
        },
        computed: {

        },
        methods:{
        },
        created(){
        },
        mounted(){
        }
    })
</script>
</html>