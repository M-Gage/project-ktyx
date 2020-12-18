<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">


    <title>订单详情</title>

    <link href="../static/css/animate.css" rel="stylesheet">
    <link href="../static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">

    <link href="../static/js/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet"/>


    <link href="../static/css/style.css?v=4.1.0" rel="stylesheet">

    <link href="../static/js/plugins/layui/css/layui.css" rel="stylesheet">

    <link href="../static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="../static/css/user/order-info.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>订单详情</h5>
                </div>

                <div class="ibox-content">

                    <div class="row">
                        <div class="col-sm-2"></div>
                        <div class="col-sm-8">
                            <div class="row fa-border">
                                <ul class="list-group" style="margin-top: 20px">
                                    <li>
                                        <div class="row">
                                            <label class="col-sm-2 project-actions">订单编号 :</label>
                                            <div class="col-sm-9" id="orderNo">
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="row">
                                            <label class="col-sm-2 project-actions">客户名称 :</label>
                                            <div class="col-sm-9" id="customerName">
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="row">
                                            <label class="col-sm-2 project-actions">订单金额 :</label>
                                            <div class="col-sm-9" id="amount">
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="row">
                                            <label class="col-sm-2 project-actions">订单日期 :</label>
                                            <div class="col-sm-9" id="orderTime">
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="row">
                                            <label class="col-sm-2 project-actions">订单地址 :</label>
                                            <div class="col-sm-9" id="orderAddress">
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="row">
                                            <label class="col-sm-2 project-actions">业务员 :</label>
                                            <div class="col-sm-9" id="staffName">
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                            <div class="row goods_title">
                                ———— <i class="fa fa-shopping-cart"></i> 商品列表 ————
                            </div>
                            <div id="goodsList"></div>
                        </div>
                        <div class="col-sm-2"></div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<div>
    <!-- 全局js -->
    <script src="../static/js/jquery.min.js?v=2.1.4"></script>
    <script src="../static/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="../static/js/plugins/layui/layui.all.js"></script>

    <!-- 自定义js -->
    <script src="../static/js/content.js?v=1.0.0"></script>
    <!--<script src="../static/js/user/order.js"></script>-->
</div>

<script>
    $(function () {
        loadGoodsInfo();
    });
    function loadGoodsInfo() {
        $.ajax({
            url: "/order/info/" + window.location.pathname.split("/")[2],
            type: "get",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result.code === 200) {
                    var root = result.data,
                        len = root.goodsAndImage.length,
                        goods = root.goodsAndImage,
                        od = root.orderDetail,
                        str = '';
                    // print(root);

                    $("#orderNo").html(root.orderId);
                    $("#customerName").html(root.customerName);
                    /\./.test(root.amount) ? $("#amount").html(root.amount) : $("#amount").html(root.amount + ".00");
                    $("#orderTime").html(root.submitTime);
                    $("#staffName").html(root.staffName);
                    $("#orderAddress").html(root.province+root.city+root.district+root.detailAddress);

                    for (var i = 0; i < len; i++) {
                        var total = (od[i].goodsPrice * od[i].sum).toFixed(2),imagePath = "";

                        if (!/\./.test(goods[i].retailPrice)) {
                            goods[i].retailPrice += '.00';
                        }
                        console.log(goods[i]);
                        imagePath = goods[i].goodsImages[0] === undefined?"":goods[i].goodsImages[0].goodsImagePath;
                        goods[i].props = goods[i].props == null ? "无属性" : goods[i].props;
                        str += "<div class=\"row fa-border\">\n" +
                            "       <div class=\"col-sm-2 fa-border img_border\">\n" +
                            "           <img src='/images/" + imagePath + "'/>\n" +
                            "       </div>\n" +
                            "       <div class=\"col-sm-9\">\n" +
                            "           <div class=\"col-sm-4 border_right\">\n" +
                            "               <div class=\"row goods_name\">" + goods[i].goodsName +
                            "               </div>\n" +
                            "               <div class=\"row goods_attr\">" + goods[i].standardAttribute + "</div>\n" +
                            "               <div class=\"row goods_attrs\">" + (/\:/.test(goods[i].nonstandardAttribute) ? goods[i].nonstandardAttribute :"")+ "</div>\n" +
                            "               <div class=\"row goods_attrs\">" + goods[i].assistAttribute + "</div>\n" +
                            "           </div>\n" +
                            "           <div class=\"col-sm-3 border_right price\">单价:" + goods[i].retailPrice +
                            "           </div>\n" +
                            "           <div class=\"col-sm-2 border_right number\"> X" + od[i].sum + " " + goods[i].unit + "</div>\n" +
                            "           <div class=\"col-sm-3 border_right price\">" + total + "\n" +
                            "           </div>\n" +
                            "       </div>\n" +
                            "       <div class=\"col-sm-1\">\n" +
                            "           <div class='remark'>备注：<br/>"+goods[i].remark+"</div>\n" +
                            "       </div>\n" +
                            "   </div>"
                    }
                    $("#goodsList").html(str);
                } else {
                    layer.msg(result.message)
                }
            }
        });
    }
</script>
</body>
</html>
