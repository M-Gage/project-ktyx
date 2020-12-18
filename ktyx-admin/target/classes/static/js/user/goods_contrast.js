$(function () {
    $("[name=goods]").selectpicker();
    layui.laydate.render({
        elem: document.getElementById('date1')
        , range: '~' //或 range: true 来自定义-分割字符
        , theme: "#f8ac59"
        , zIndex: 999
        , calendar: true
    });
    layui.laydate.render({
        elem: document.getElementById('date2')
        , range: '~' //或 range: true 来自定义-分割字符
        , theme: "#f8ac59"
        , zIndex: 999
        , calendar: true
    });
    var f = true;
    $("#followupSearchForm").find("> div > div.form-group.col-xs-4 > div > div > div > div > input").keyup(function () {
        if (f && $(this).val() !== "" && $("option").length > 2) {
            $("[name=goods]").html('');
            f = false;
        } else {
            f = true;
        }
        loadName(0, 10, $(this).val());
    });


    var firstNum = 10
        , lastNum = 10
        , count = 1, ct = 1;

    $("#followupSearchForm").find("> div > div.form-group.col-xs-4 > div > div > div").scroll(function () {
        //h:div可视区域的高度
        //sh:滚动的高度，$(this)指代jQuery对象，而$(this)[0]指代的是dom节点
        //st:滚动条的高度，即滚动条的当前位置到div顶部的距离
        var h = $(this).height()
            , sh = $(this)[0].scrollHeight
            , st = $(this)[0].scrollTop
            ,
            search = $("#followupSearchForm").find("> div > div.form-group.col-xs-4 > div > div > div > div > input").val();

        //判断滚动条滑到底部
        if (h + st >= sh) {
            if (search !== "") {
                count = ct;
                ct++;
            } else {
                ct = 1
            }
            loadName(firstNum * count, lastNum, search);
            count++;
        }
        //$("scrollTest")[0].scrollTop=0;
    });
});


function loadName(firstNum, lastNum, search) {
    var f = true;
    $.ajax({
        url: "/goods/goodsName",
        type: "get",
        data: {
            firstNum: firstNum,
            lastNum: lastNum,
            search: search
        },
        success: function (result) {
            if (result.code === 200) {
                var goods = $("[name=goods]"), goodsList = result.data, len = goodsList.length;
                if ($("option").length < 2) {
                    goods.html('');
                }

                for (var i = 0; i < len; i++) {
                    goods.append("<option value='" + goodsList[i].goodsId + "'>" + goodsList[i].goodsName + "</option>")
                }
                goods.selectpicker("refresh");
                var flag = false;
                $("#followupSearchForm").find("> div > div.form-group.col-xs-4 > div > div > div > ul > li").mouseover(function () {

                    if (flag) {
                        return;
                    }
                    var index = $(this).attr("data-original-index"),
                        id = $("[name=goods] > option").eq(index).val(), _this = $(this);
                    $.ajax({
                        url: "/goods/attr/" + id,
                        type: 'get',
                        success: function (result) {
                            layer.tips("<span style='color:#000'>" + (result.code === 200 ? result.data : "无属性") + "</span>", _this, {
                                tips: [4, '#fff'],
                                time: 0
                            });
                        }
                    });
                    flag = true;
                }).mouseout(function () {
                    layer.closeAll();
                    flag = false;
                })
            } else {
                layer.msg("系统异常，加载商品名称列表失败", {icon: 2});
            }
        }
    })
}

$("[name=goods]").parent().click(function () {
    if ($("option").length < 2) {
        loadName(0, 10, "");
    }
});

$("#submit").click(function () {
    $("#schedule").css("display", "inline");
    $("#declare").css("display", "none");
    $("tbody").html('');
    var goods = $("[name=goods]"), date1 = $("#date1").val(), date2 = $("#date2").val();
    $("#interval1").html(date1);
    $("#interval2").html(date2);
    if (date1 !== '' || date2 !== '') {
        if (goods.val() !== null) {
            $.ajax({
                url: "/order/goodsContrast",
                type: 'post',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({
                    date1: date1,
                    date2: date2,
                    goods: goods.val()
                }),
                success: function (result) {
                    if (result.code === 200) {
                        var g1 = result.data[0], g2 = result.data[1];
                        if (g2.length === 0 && g1.length === 0) {
                            $("tbody").append("<tr><td colspan='6' rowspan='2' style='color: red'>两个日期均无数值，无法进行比较</td></tr>");
                            return false;
                        }
                        var len = g1.length !== 0 ? g1.length : g2.length;
                        for (var i = 0; i < len; i++) {
                            var str
                                , goodsAmount1 = g1[i] !== undefined ? g1[i].goodsAmount : 0
                                , goodsAmount2 = g2[i] !== undefined ? g2[i].goodsAmount : 0
                                , quantity1 = g1[i] !== undefined ? g1[i].quantity : 0
                                , quantity2 = g2[i] !== undefined ? g2[i].quantity : 0
                                , bate
                                , name = g1[i] !== undefined ? g1[i].goodsName +
                                "<br/>" + (g1[i].standardAttribute !== '' && g1[i].standardAttribute !== null ? g1[i].standardAttribute : "无属性") : g2[i].goodsName +
                                "<br/>" + (g2[i].standardAttribute !== '' && g2[i].standardAttribute !== null ? g2[i].standardAttribute : "无属性");

                            if (goodsAmount1 >= goodsAmount2) {
                                str = "<span class='fa fa-arrow-down' style='color: green'></span>";
                                bate = (((goodsAmount1 - goodsAmount2) / goodsAmount1) * 100).toFixed(0);
                            } else {
                                bate = (((goodsAmount2 - goodsAmount1) / goodsAmount2) * 100).toFixed(0);
                                str = "<span class='fa fa-arrow-up' style='color: red'></span>"
                            }
                            $("tbody").append("<tr>" +
                                "<td>" + name + "</td>" +
                                "<td>" + goodsAmount1 + "</td>" +
                                "<td>" + quantity1 + "</td>" +
                                "<td>" + goodsAmount2 + "</td>" +
                                "<td>" + quantity2 + "</td>" +
                                "<td>" + str + bate + "%</td>" +
                                "</tr>")
                        }
                    } else {
                        layer.msg("未知错误，请与程序员联系", {icon: 2});
                    }
                }
            })

        } else {
            layer.msg("请选择一个或多个商品进行对比", {icon: 2})
        }
    } else {
        layer.msg("请选两个时间区间", {icon: 2})
    }
    return false;
});
