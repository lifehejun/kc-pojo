<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>后台用户列表</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div class="layui-form" action="" lay-filter="user-form-element" style="margin-top: 10px;">
    <div class="layui-form-item">
        <div class="layui-inline" >
            <label class="layui-form-label">手机号：</label>
            <div class="layui-input-inline">
                <input class="layui-input" id="phone" name="phone" placeholder="精确查询"/>
            </div>
        </div>
        <div class="layui-inline" >
            <label class="layui-form-label">用户名：</label>
            <div class="layui-input-inline">
                <input class="layui-input" id="userName" name="userName"/>
            </div>
        </div>
        <div class="layui-inline" >
            <label class="layui-form-label">微信：</label>
            <div class="layui-input-inline">
                <input class="layui-input" id="wechat" name="wechat"/>
            </div>
        </div>
        <div class="layui-inline" >
            <label class="layui-form-label">QQ：</label>
            <div class="layui-input-inline">
                <input class="layui-input" id="qq" name="qq"/>
            </div>
        </div>
        <div class="layui-inline" >
            <label class="layui-form-label">推广码：</label>
            <div class="layui-input-inline">
                <input class="layui-input" id="agentCode" name="agentCode"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">用户等级：</label>
            <div class="layui-input-inline">
                <select name="grade" id="grade" lay-search="">
                    <option value="">全部</option>
                    <option value="0">普通用户</option>
                    <option value="1">白银VIP</option>
                    <option value="2">黄金VIP</option>
                    <option value="3">钻石VIP</option>
                    <option value="4">至尊VIP</option>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">用户状态：</label>
            <div class="layui-input-inline">
                <select name="status" id="status" lay-search="">
                    <option value="">全部</option>
                    <option value="1">正常</option>
                    <option value="0">冻结</option>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">用户类型：</label>
            <div class="layui-input-inline">
                <select name="userType" id="userType" lay-search="">
                    <option value="">全部</option>
                    <c:forEach items="${userTypeMap}" var="userType">
                        <option value="${userType.key}">${userType.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <button class="layui-btn layui-btn-normal"  lay-filter="user-form-btn-sub" lay-submit="">搜索</button>
        </div>

        <div class="layui-inline">
            <span style="color: red">备注：</span>
        </div>
    </div>
</div>

<script type="text/html" id="barUser">
    <a class="layui-btn layui-btn-xs layui-btn-normal"  lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-xs layui-btn-normal"  lay-event="view">查看</a>
    {{#  if(d.status == 1){ }}
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="frozen">冻结</a>
    {{#  } }}
    {{#  if(d.status == 0){ }}
    <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="unFrozen">解冻</a>
    {{#  } }}
</script>
<table class="layui-hide" id="userList" lay-filter="userList"></table>
<script type="text/javascript">
    layui.use(['table','form'], function(){
        layui.form.render(null, 'user-form-element');
        var table = layui.table;
        form = layui.form,
            _ajax_b = function(url,data,msg,index){
                jQuery.post('${rc.contextPath}'+url,data,function(data){
                    if(data.code == "00"){
                        layer.msg(msg, {
                            icon: 1
                            ,time: 1000
                        }, function(){
                            location.reload();
                            layer.closeAll();
                        });
                    }else{
                        layer.msg(data.msg, {icon:3,time:1000});
                    }
                });
            },
            table.render({
                elem: '#userList'
                ,url:'/user/list/page'
                ,title: '用户列表'
                ,limit:20
                ,loading:true
                ,size:'sm'
                ,where:{
                    status : $("#status").val()
                }
                ,page: {
                    limits:[20, 30, 40, 50,100,200,500,1000],
                    theme: '#1E9FFF'
                } //开启分页
                ,cols: [[
                    {field:'phone', title: '手机号(登录名)',style:"color:#1E9FFF"}
                    ,{field:'userName', title: '用户名(昵称)', }
                    ,{field:'coreBalance', title: '主账户余额'}
                    ,{field:'goldCoin', title: '金币余额'}
                    ,{field:'vodGradeName', title: '视频会员等级',templet:function (d) {
                            if(d.grade == 0){
                                return "普通用户";
                            }else{
                                return '<div><img style="height:20px;width:60px;" src="/static/images/vip/ico_vip'+d.grade+'.png"></div>';
                            }
                     }}
                    ,{field:'bonusRatio', title: '佣金比例'}
                    ,{field:'realName', title: '真实姓名'}
                    ,{field:'agentCode', title: '推广码'}
                    ,{field:'userTypeDesc', title: '用户类型'}
                    ,{field:'status', title: '用户状态', templet: function(d) {
                            return d.status == 0 ? '<font style="color: #FF5722">冻结</font>': '<font style="color: #1E9FFF">正常</font>';
                        }},
                    ,{field:'createTime', title: '创建时间', templet :function (row){
                            return createTime(row.createTime);
                        },width: 140 }
                    ,{fixed: 'right', title:'操作', toolbar: '#barUser', width:180}
                ]]
            });

        //监听行工具事件
        table.on('tool(userList)', function(obj){
            var data = obj.data;
            if(obj.event === 'view'){
                var id = data.id;

            }
            if(obj.event === 'edit'){
                var id = data.id;
                editUser(id);
            }
            if(obj.event === 'frozen'){
                layer.confirm('确定冻结该用户吗', {
                    btn: ['确定', '取消',] //可以无限个按钮
                }, function(index, layero){
                    var id = data.id;
                    frozenUser(id);
                });
            }
            if(obj.event === 'unFrozen'){
                layer.confirm('确定解冻该用户吗', {
                    btn: ['确定', '取消',] //可以无限个按钮
                }, function(index, layero){
                    var id = data.id;
                    unFrozenUser(id);
                });
            }
        });

        var editUser = function (id) {
            layer.open({
                type: 2,
                title:"会员信息",
                area: ['80%','80%'],
                content: '/user/edit?id='+id //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
            });

        }

        var frozenUser = function (id) {
            var data = {"id":id}
            _ajax_b("/user/frozenUser",data,"用户冻结成功",null);
        }
        var unFrozenUser = function (id) {
            var data = {"id":id}
            _ajax_b("/user/unFrozenUser",data,"用户解冻成功",null);
        }

        form.on('submit(user-form-btn-sub)', function(data) {
            table.reload('userList', {
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
