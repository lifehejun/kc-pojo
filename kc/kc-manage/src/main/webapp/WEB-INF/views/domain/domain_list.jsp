<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>域名数据</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div class="layui-form" action="" lay-filter="domain-form-element" style="margin-top: 10px;">
    <div class="layui-form-item">
        <div class="layui-inline" >
            <label class="layui-form-label">域名链接：</label>
            <div class="layui-input-inline">
                <input class="layui-input" id="domainName" placeholder="模糊查询" name="domainName"/>
            </div>
        </div>
        <div class="layui-inline" >
            <label class="layui-form-label">状态：</label>
            <div class="layui-input-inline">
                <select name="status" id="status" lay-search="">
                    <option value="">全部</option>
                    <c:forEach items="${statusEnums}" var="status">
                        <option value="${status.key}" <c:if test="${status.key == domain.status}">selected</c:if> > ${status.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
       <%-- <div class="layui-inline" >
            <label class="layui-form-label">域名类型：</label>
            <div class="layui-input-inline">
                <select name="status" id="domainType" lay-search="">
                    <c:forEach items="${domainTypeEnums}" var="type">
                        <option value="${type.key}" <c:if test="${type.key == domain.status}">selected</c:if> > ${type.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>--%>
        <div class="layui-inline">
            <button class="layui-btn layui-btn-normal"  lay-filter="domain-form-btn-sub" lay-submit="">搜索</button>
            <button class="layui-btn layui-btn-normal"  onclick="addDomain()">添加域名</button>
        </div>
    </div>
</div>

<script type="text/html" id="barDomain">
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<table class="layui-hide" id="domainList" lay-filter="domainList"></table>

<script type="text/javascript">
    layui.use(['table','form'], function(){
        layui.form.render(null, 'domain-form-element');
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
                elem: '#domainList'
                ,url:'/domain/list/page'
                ,title: '域名列表'
                ,limit:20
                ,loading:true
                ,size:'sm'
                ,page: {
                    limits:[20, 30, 40, 50,100,200,500,1000],
                    theme: '#1E9FFF'
                } //开启分页
                ,cols: [[
                    {field:'domainName', title: '域名链接', }
                    ,{field:'domainType', title: '域名类型', templet: function(d) {
                            return d.domainType == 0 ? '<font style="color: #FF5722">访问域名</font>': '<font style="color: #1E9FFF">推广域名</font>';
                        }}
                    ,{field:'status', title: '状态', templet: function(d) {
                            return d.status == 0 ? '<font style="color: #FF5722">无效</font>': '<font style="color: #1E9FFF">有效</font>';
                        }}
                    ,{field:'sortNo', title: '排序号'}
                    ,{field:'createTime', title: '创建时间', templet :function (row){
                            return createTime(row.createTime);
                        },width: 150 }
                    ,{fixed: 'right', title:'操作', toolbar: '#barDomain', width:180}

                ]]
            });


        form.on('submit(domain-form-btn-sub)', function(data) {
            table.reload('domainList', {
                where: data.field,
                method:'POST',
                pageNum: {curr:1},
            });
            return false;
        });
        //监听行工具事件
        table.on('tool(domainList)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                var id = data.id;
                editDomain(id);
            }
            if(obj.event === 'del'){
                layer.confirm('确定删除该条数据吗', {
                    btn: ['确定', '取消',] //可以无限个按钮
                }, function(index, layero){
                    var id = data.id;
                    delDomain(id);
                });
            }
        });

        var delDomain = function (id) {
            var data = {"id":id}
            _ajax_b("/domain/del",data,"删除成功",null);
        }
        var editDomain = function (id) {
            layer.open({
                type: 2,
                area: ['40%','70%'],
                content: '/domain/edit?id='+id //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
            });
        }

    });

    var addDomain = function () {
        layer.open({
            title:'添加域名',
            type: 2,
            area: ['40%','70%'],
            content: '/domain/edit' //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
    }
</script>
</body>
</html>
