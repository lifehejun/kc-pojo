<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>注册ip管理</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div class="layui-form" action="" lay-filter="regip-form-element" style="margin-top: 10px;">
    <div class="layui-form-item">
        <div class="layui-inline" >
            <label class="layui-form-label">手机号：</label>
            <div class="layui-input-inline layui-input-search">
                <input class="layui-input" id="phone" name="phone"/>
            </div>
        </div>
        <div class="layui-inline" >
            <label class="layui-form-label">IP地址：</label>
            <div class="layui-input-inline layui-input-search">
                <input class="layui-input" id="regIp" name="regIp"/>
            </div>
        </div>
        <div class="layui-inline">
            <button class="layui-btn layui-btn-normal"  lay-filter="regip-form-btn-sub" lay-submit="">搜索</button>
        </div>
    </div>
</div>

<table class="layui-hide" id="regIpList" lay-filter="regIpList"></table>

<script type="text/javascript">
    layui.use(['table','form'], function(){
        layui.form.render(null, 'regip-form-element');
        var table = layui.table;
        form = layui.form,
            _ajax_b = function(url,data,msg,index){
                jQuery.post('${rc.contextPath}'+url,data,function(data){
                    if(data.code == "00"){
                        layer.msg(msg, {
                            icon: 1
                            ,time: 2000
                        }, function(){
                            location.reload();
                            layer.closeAll();
                        });
                    }else{
                        layer.msg(data.msg, {icon:3,time:2000});
                    }
                });
            },
            table.render({
                elem: '#regIpList'
                ,url:'/ip/list/page'
                ,title: '注册IP数据列表'
                ,limit:20
                ,loading:true
                ,size:'sm'
                ,page: {
                    limits:[20, 30, 40, 50,100,200,500,1000],
                    theme: '#1E9FFF'
                } //开启分页
                ,cols: [[
                    {field:'regIp', title: '注册ip',width: 200}
                    ,{field:'phoneList', title: '手机号',width: 500}
                ]]
            });


            form.on('submit(regip-form-btn-sub)', function(data) {
                table.reload('regIpList', {
                    where: data.field,
                    method:'POST',
                    pageNum: {curr:1},
                });
                return false;
            });

    });
</script>
</body>
</html>
