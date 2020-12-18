/**
 * 验证登录表单
 */
function validateForm(obj){
    $("#"+obj).submit(function () {
        var phone = $("[name=phone]"),
            pwd = $("[name=pwd]");
        $.ajax({
            url:"/staff/login",
            type:"post",
            contentType: "application/json;charset=UTF-8",
            data:JSON.stringify({
                phone:phone.val(),
                password:pwd.val()
            }),
            success:function (result) {
                if(result.code === 200){
                    document.cookie ="staffId =" + result.data.staffId;
                    window.location.href="/";
                }else{
                    layer.msg(result.message,{icon:2});
                }
            }
        });
        return false;
    })
}






