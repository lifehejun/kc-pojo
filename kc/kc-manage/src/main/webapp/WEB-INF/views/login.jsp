<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>后台登陆</title>
    <link rel="stylesheet" href="/static/css/login.css?t=201800202-1" media="all">
    <script src="../../static/layui.js" charset="utf-8"></script>
</head>
<body style="background-image: url('/static/images/customize/idea_white.png')">
<form class="layui-form" action="">
    <div class="login_box" >
        <div class="login_l_img"><%--<img src="/static/images/customize/idea_white.png" />--%></div>
        <div class="login">
            <div class="login_logo"><a href="#"><img src="/static/images/customize/login_logo.png" /></a></div>
            <div class="login_name">
                <p>后台登陆</p>
            </div>
            <div method="post">
                <input name="userName" id="userName" lay-verify="required" type="text"  placeholder="用户名">
                <input name="userPwd" id="userPwd" lay-verify="required" type="password" placeholder="密码"/>
                <input value="登录" style="width:100%;" type="button" lay-submit lay-filter="login">
            </div>
        </div>
    </div>
</form>
<script src="/static/jquery-1.9.1.min.js" charset="utf-8"></script>

<script>
    layui.use('form', function(){
        var form = layui.form;

        //监听提交
        form.on('submit(login)', function(data){
            //var jsonData = JSON.stringify(data.field);
            var jsonData = data.field;
            var url = "/login/json";
            jQuery.post(url,jsonData,function(data){
                if(data.code == "00"){
                    layer.msg(data.msg, {
                        icon: 1
                        ,time: 2000
                    }, function(){
                        window.location.href = "/admin/index";
                    });
                }else{
                    layer.msg(data.msg, {icon:3,time:1000});
                }
            });
            return false;
        });
    });
</script>
</body>
</html>
