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
                <Page ref="pages" :total="table.total" :page-size="10" :page-size-opts="[10,20,40]" placement="top"
                      @on-change="changePage" @on-page-size-change="changePageSize" show-sizer show-total></Page>
            </div>
        </i-col>
    </row>
</div>
</body>
<script type="text/javascript">
    new Vue({
        el: "#app",
        data() {
            return {
                table: {
                    data: [],
                    columns: [
                        {
                            type: 'index',
                            align: 'center',
                            width: 60
                        },
                        {
                            title: '公告主题',
                            key: 'title',
                            align: 'center'
                        },
                        {
                            title: '公告内容',
                            type:'html',
                            key: 'content',
                            align: 'center',
                            height: 500
                        },
                        {
                            title: '创建时间',
                            key: 'createTime',
                            align: 'center'
                        }, {
                            title: '状态',
                            key: 'state',
                            align: 'center',
                            render: (h, params) => {
                                let color = 'blue';
                                let text = "使用";
                                if (params.row.state === 1) {
                                    color = 'red';
                                    text = "禁用";
                                }
                                return h('Tag', {
                                    props: {
                                        color: color
                                    }
                                }, text);
                            }
                        }, {
                            title: "操作",
                            align: 'center',
                            width: 180,
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
                                                this.updateState(params.index);
                                            }
                                        }
                                    }, '更改状态'),
                                    h('Button', {
                                        props: {
                                            type: 'error',
                                            size: 'small'
                                        },
                                        on: {
                                            click: () => {
                                                this.deleteAnnouncement(params.index);
                                            }
                                        }
                                    }, '删除')
                                ]);
                            }
                        },
                    ],
                    loading: false,
                    total: 0,
                    param: {
                        pageSize: 10,
                        pageNumber: 1,
                    },
                },
                label: {
                    labelName: ''
                },
                updateLabel: {},
                windowHeight: '',
                modal: {
                    addLabel: false,
                    updateLabel: false
                },
                btn: {
                    addLabel: false,
                    updateLabel: false
                }
            }
        },
        methods: {
            getTable() {
                this.table.loading = true;
                axios.get("/announcement/table", {params: this.table.param}).then(res => {
                    if (res.code === 200) {
                        this.table.data = res.data.rows;
                        this.table.total = res.data.total;
                    } else {
                        this.$Notice.error({title: res.message});
                    }
                    this.table.loading = false;
                }).catch(err => {
                    this.$Notice.error({title: "获取公告列表失败！"});
                    this.table.loading = false;
                });
            },
            changePage(num) {
                this.table.param.pageNumber = num;
                this.getTable();
            },
            changePageSize(size) {
                this.table.param.pageSize = size;
                this.getTable();
            },
            updateState(index) {
                let state = this.table.data[index].state,a = this.table.data[index];
                a.state=(state == 0?1:0);
                axios.get("/announcement/update",{params:this.table.data[index]}).then(res => {
                    if (res.code === 200) {
                        this.$Notice.info({title: this.table.data[index].title+"：状态修改成功！"});
                    }else {
                        this.$Notice.error({title: "状态修改失败！"});
                    }
                })
            },
            deleteAnnouncement(index) {
                console.log(this.table.data[index].announcementId);
                axios.delete("/announcement/"+this.table.data[index].announcementId).then(res => {
                    if (res.code === 200) {
                        this.$Notice.info({title: "真的删除了"});
                    }else {
                        this.$Notice.error({title: "这个很顽强，后台删不掉"});
                    }
                });
                this.table.data.splice(index, 1);
            }
        },
        created() {
            this.getTable();
        },
        mounted() {
            this.windowHeight = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight);
        }
    })
</script>
</html>