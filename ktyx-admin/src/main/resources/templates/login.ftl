<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>客通云销 - 登录</title>
    <link href="../static/css/bootstrap.min.css" rel="stylesheet">
    <link href="../static/css/font-awesome.min.css" rel="stylesheet">
    <link href="../static/css/animate.css" rel="stylesheet">
    <link href="../static/css/style.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>
</head>

<body class="gray-bg">
    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div>
                <h1 class="logo-name">KT</h1>
            </div>
            <h3>欢迎使用 客通云销</h3>
            <form class="m-t" role="form" id="loginFrom">
                <div class="form-group">
                    <input type="text" name="phone" class="form-control" placeholder="手机号">
                </div>
                <div class="form-group">
                    <input type="password" name="pwd" class="form-control" placeholder="密码">
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">登 录</button>
            </form>
        </div>
    </div>

    <!-- 全局js -->
    <script src="../static/js/jquery.min.js"></script>
    <script src="../static/js/bootstrap.min.js"></script>
    <!--layui -->
    <script src="../static/js/plugins/layui/layui.all.js"></script>
    <script src="../static/js/user/form.js"></script>

    <script>
        validateForm("loginFrom")
    </script>
</body>

</html>
