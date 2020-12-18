<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>客通云销 - 跟进管理</title>
    <link href="../static/js/plugins/iview/iview.css" rel="stylesheet">
    <link href="../static/css/base.css" rel="stylesheet">
    <link rel="stylesheet" href="//at.alicdn.com/t/font_514640_bqisvw9som.css">
    <script src="../static/js/vue.js"></script>
    <script src="../static/js/plugins/iview/iview.min.js"></script>
    <script src="../static/js/axios.min.js"></script>
    <script src="../static/js/axios-config.js"></script>
    <style>
        #app{
            background-color: #f3f3f3;
        }
        .spin-icon-load{
            animation: ani-demo-spin 1s linear infinite;
        }
        .spin-container{
            display: inline-block;
            width: 100%;
            position: relative;
        }
        .img-box{
            display: inline-block;
            cursor: pointer;
        }
        .icons{
            font-size: 26px;
            cursor: pointer;
        }
        .icons:nth-child(2){
            margin: 0 30px;
        }
        .icons-active{
            color: deepskyblue;
        }
        .comment-icon{
            font-size: 16px;
            cursor: pointer;
            position: relative;
            text-indent: 10px;
            top: 2px;
        }
        .follow-box{
            padding: 20px;
            background-color: #fff;
        }
        .comment-icon{
            opacity: 0;
        }
        .comment:hover .comment-icon{
            opacity: 1;
        }
        .comment-time{
            color: #bfbfbf;
        }
        .preview-mask{
            position: fixed;
            z-index: 999;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            background-color: rgb(0,0,0,.7);
            text-align: center;
        }
        a:hover{
            text-decoration: underline;
        }
        .ivu-btn-text:focus{
            box-shadow: 0 0 0 transparent !important;
        }
        .ivu-btn-text{
            padding: 0;
        }
    </style>
</head>
<body>
    <div id="app" v-cloak>
        <transition name="fade">
            <div @click="previewPic.visible = false" v-show="previewPic.visible" class="preview-mask">
                <img :src="previewPic.src" alt="">
            </div>
        </transition>
        <back-top></back-top>
        <div class="follow-box" v-for="(f,followIndex) in followupList" :key="f.followId" style="margin-bottom: 20px">
            <p>{{f.staffName}}</p>
            <p>{{f.createTime}}</p>
            <p>{{f.customerName}}</p>
            <p>{{f.content}}</p>
            <div @click="previewImg(followIndex,imgIndex)" class="img-box" v-for="(img,imgIndex) in f.followPicList">
                <img :src="img.previewPath"/>
            </div>
            <div v-if="f.voicePath != null">
                <audio :src="f.voicePath" controls></audio>
            </div>
            <#-- 点赞踩 -->
            <div>
                <i-button :class="[f.like ? 'icons icons-active' : 'icons']" type="text" @click="like(followIndex,1)" icon="thumbsup" :disabled="f.likeDisabled"></i-button>
                <i-button :class="[f.disLike ? 'icons icons-active' : 'icons']" icon="thumbsdown" type="text" @click="like(followIndex,0)" :disabled="f.disLikeDisabled"></i-button>
                <i-button type="text" class='icons' icon="chatbox-working" @click="comment(followIndex)"></i-button>
            </div>
            <#-- 赞人数 -->
            <div v-if="f.likeCount > 0">
                <Icon v-show="f.likeCount > 0" type="thumbsup"></Icon>
                <div style="display: inline-block" v-for="fo in f.followOpinionList">
                    <span v-if="fo.opinion === 1">
                        <a :href="fo.staffId">{{fo.staffName}}</a>、
                    </span>
                </div>
                <span v-show="f.likeCount > 0">
                    等{{f.likeCount}}个人觉得很棒
                </span>
            </div>
            <#-- 踩人数 -->
            <div v-if="f.disLikeCount > 0">
                <Icon v-show="f.disLikeCount > 0" type="thumbsdown"></Icon>
                <div style="display: inline-block" v-for="fo in f.followOpinionList">
                    <span v-if="fo.opinion === 0">
                        <a :href="fo.staffId">{{fo.staffName}}</a>、
                    </span>
                </div>
                <span v-show="f.disLikeCount > 0">
                    等{{f.disLikeCount}}个人觉得不行
                </span>
            </div>
            <#-- 评论 -->
            <div v-for="(c,followCommentIndex) in f.followCommentList" :key="c.followCommentId">
                <span v-if="c.replyCommentId == null">
                    <div class="comment">
                        <a :href="c.staffId">{{c.staffName}}</a> : {{c.content}} <br>
                        <span class="comment-time" v-text="c.createTime"></span>
                        <Icon class='comment-icon' type="chatbox-working" @click.native="comment(followIndex,followCommentIndex)"></Icon>
                    </div>
                    <follow-comment
                        :data="f.followCommentList"
                        :follow-index="followIndex"
                        :follow-id="f.followId"
                        :id="c.followCommentId"
                        :staff-id="c.staffId"
                        :staff-name="c.staffName"
                        @comment="comment">
                    </follow-comment>
                </span>
            </div>
        </div>
        <div class="spin-container" v-show="loading">
            <Spin fix>
                <Icon type="load-c" size=18 class="spin-icon-load"></Icon>
                <div>数据加载中</div>
            </Spin>
        </div>
        <div style="text-align: center" v-show="bottomFlag">
            <span>数据全部加载完成！</span>
        </div>
        <Modal
            v-model="commentModal"
            title="回复"
            width="300"
            :mask-closable="false"
        >
            <i-input type="textarea" v-model="followComment.content"></i-input>
            <div slot="footer">
                <i-button type="primary" @click="commentSubmit">评论</i-button>
            </div>
        </Modal>
    </div>
<script>
    Vue.component('follow-comment', {
        props:['data','id','staffId','staffName','followId','followIndex'],
        methods:{
            comment(followIndex,commentIndex){
                this.$emit('comment', followIndex,commentIndex)
            },
        },
        template: `<div style="margin-left: 25px">
                    <div v-for="(item,index) in data">
                        <span v-if="id == item.replyCommentId">
                            <div class="comment">
                                <a :href="item.staffId">{{item.staffName}}</a> 回复 <a :href="staffId">{{staffName}}</a> : {{item.content}}
                                <div>
                                    <span class="comment-time" v-text='item.createTime'></span>
                                    <Icon @click.native="comment(followIndex,index)"  class='comment-icon' type="chatbox-working"></Icon>
                                </div>
                            </div>
                            <span v-if='item.replyCommentId != null'>
                               <follow-comment
                                    :data="data"
                                    :id="item.followCommentId"
                                    :staff-id="item.staffId"
                                    :follow-index="followIndex"
                                    :follow-id="followId"
                                    :staff-name="item.staffName"
                                    @comment="comment">
                                </follow-comment>
                            </span>
                        </span>
                    </div>
                   </div>`
    });
    new Vue({
        el:"#app",
        data(){
            return {
                commentModal:false,
                spinShow:false,
                pageNumber: 1,
                pageSize: 10,
                totalPage: 0,
                windowHeight:"",
                followupList:[],
                loading: false,
                followIndex:"",
                followComment:{
                    replyCommentId:"",
                    followId:"",
                    content:""
                },
                previewPic:{
                    list:[],
                    index: '',
                    src:"",
                    visible : false
                },
                cookieStaffId:"",
                dataLoadingSuccess: false,
                bottomFlag:false
            }
        },
        methods:{
            handleScroll () {
                let scrollTop = Math.ceil(document.documentElement.scrollTop || document.body.scrollTop);
                let scrollHeight = document.documentElement.scrollHeight;
                let windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
                if (scrollTop + windowHeight >= scrollHeight) {
                    if (this.dataLoadingSuccess && this.pageNumber === this.totalPage) {
                        this.bottomFlag = true;
                        return;
                    }
                    if (this.dataLoadingSuccess) {
                        this.loading = true;
                        this.pageNumber = this.pageNumber + 1;
                        this.getData();
                    }
                }
            },
            getData(){
                this.dataLoadingSuccess = false;
                axios.get("/follow/list",{
                    params:{
                        pageNumber: this.pageNumber,
                        pageSize: this.pageSize
                    }
                }).then((res)=>{
                    if (res.code === 200) {
                        this.totalPage = Math.ceil(res.data.total / this.pageSize);
                        res.data.rows.forEach(item => {
                            item.like = false;
                            item.disLike = false;
                            item.disLikeDisabled = false;
                            item.likeDisabled = false;
                            item.likeCount = 0;
                            item.disLikeCount = 0;
                            this.followupList.push(item);
                            // 点赞列表
                            item.followOpinionList.forEach(fo => {
                                if (fo.staffId === this.cookieStaffId) {
                                    switch (fo.opinion) {
                                        case 0:
                                            item.like = false;
                                            item.disLike = true;
                                            item.likeDisabled = true;
                                            break;
                                        case 1:
                                            item.like = true;
                                            item.disLike = false;
                                            item.disLikeDisabled = true;
                                            break;
                                    }
                                }
                                //计算数量
                                switch (fo.opinion) {
                                    case 0:
                                        item.disLikeCount++;
                                        break;
                                    case 1:
                                        item.likeCount++;
                                        break;
                                }
                            });
                            //如果是自己发的跟进
                            if (item.staffId === this.cookieStaffId) {
                                item.likeDisabled = true;
                                item.disLikeDisabled = true;
                            }
                        });
                        this.loading = false;
                        this.dataLoadingSuccess = true;
                    } else {
                        this.$Message.error(res.message)
                    }
                }).catch(err=>{
                    this.$Message.error("获取跟进列表失败")
                })
            },
            previewImg(followIndex,imgIndex){
                this.previewPic.list = this.followupList[followIndex].followPicList;
                this.previewPic.index = imgIndex;
                this.previewPic.src = this.followupList[followIndex].followPicList[imgIndex].picPath;
                this.previewPic.visible = true;
            },
            like(followIndex,opinion){
                this.$Spin.show();
                setTimeout(() => {
                    this.$Spin.hide();
                }, 1000);
                let follow = this.followupList[followIndex];
                let followId = follow.followId;
                let followStaffId = follow.staffId;
                let opinionFlag = "";
                follow.followOpinionList.forEach(fo => {
                    //找到自己操作的条数
                    if (fo.staffId === this.cookieStaffId) {
                        opinionFlag = fo.opinion;
                    }
                });
                console.log(opinionFlag);
                if (opinionFlag === opinion) {
                    console.log("重复",opinion);
                    console.log("删除",opinion);
                    switch (opinion){
                        case 0:
                            this.followupList[followIndex].likeDisabled = false;
                            this.followupList[followIndex].disLike = false;
                            this.followupList[followIndex].disLikeCount = this.followupList[followIndex].disLikeCount -1;
                            break;
                        case 1:
                            this.followupList[followIndex].disLikeDisabled = false;
                            this.followupList[followIndex].like = false;
                            this.followupList[followIndex].likeCount = this.followupList[followIndex].likeCount -1;
                            break;
                    }
                    axios.delete("/followOpinion/" + followId,{
                        params:{
                            opinion: opinion,
                            followStaffId: followStaffId
                        }
                    }).then(res => {
                        if (res.code === 200) {
                            axios.get("/followOpinion/" + followId).then(res => {
                                if (res.code === 200) {
                                    this.followupList[followIndex].followOpinionList = res.data;
                                }
                            })
                        }
                    });
                    return;
                }
                axios.post("/followOpinion",{
                    followId: followId,
                    opinion: opinion,
                    followStaffId: followStaffId
                }).then(res=>{
                    if (res.code === 200) {
                        switch (opinion){
                            case 0:
                                this.followupList[followIndex].likeDisabled = true;
                                this.followupList[followIndex].disLike = true;
                                this.followupList[followIndex].disLikeCount = this.followupList[followIndex].disLikeCount +1;
                                break;
                            case 1:
                                this.followupList[followIndex].disLikeDisabled = true;
                                this.followupList[followIndex].like = true;
                                this.followupList[followIndex].likeCount = this.followupList[followIndex].likeCount +1;
                                break;
                        }
                        axios.get("/followOpinion/" + followId).then(res => {
                            if (res.code === 200) {
                                this.followupList[followIndex].followOpinionList = res.data;
                            }
                        })
                    }
                })
            },
            comment(followIndex,commentIndex){
                this.followComment.content = '';
                this.followIndex = followIndex;
                this.followComment.followId = this.followupList[followIndex].followId;
                if (commentIndex !== undefined) {
                    this.followComment.replyCommentId = this.followupList[followIndex].followCommentList[commentIndex].followCommentId;
                    if (this.followupList[followIndex].followCommentList[commentIndex].staffId === this.cookieStaffId){
                        this.$Message.error("不能自己回复自己！");
                        return;
                    }
                }else {
                    this.followComment.replyCommentId = '';
                }
                console.log(JSON.stringify(this.followComment));
                this.commentModal = true;
            },
            commentSubmit(){
                axios.post("/followComment",this.followComment).then(res=>{
                    if (res.code === 200) {
                        axios.get("/followComment/"+this.followComment.followId).then(res=>{
                            if (res.code === 200) {
                                this.commentModal = false;
                                this.followupList[this.followIndex].followCommentList = res.data;
                            } else {
                                this.$Message.error(res.message)
                            }
                        }).catch(err => {
                            this.$Message.error("获取评论列表失败！")
                        })
                    } else {
                        this.$Message.error(res.message)
                    }
                }).catch(err => {
                    this.$Message.error("回复失败！")
                })
            },
        },
        created(){
            this.getData();
            this.cookieStaffId = document.cookie.split(";").filter(c => c.split("=")[0] === "staffId")[0].split("=")[1];
        },
        mounted(){
            this.windowHeight = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight) - 40;
            window.addEventListener('scroll',this.handleScroll)
        },
        watch:{
            'previewPic.visible'(value){
                if (value) {
                    document.getElementsByTagName('body')[0].style.overflow = 'hidden';
                } else {
                    document.getElementsByTagName('body')[0].style.overflow = 'auto';
                }
            }
        }
    })
</script>
</body>
</html>
