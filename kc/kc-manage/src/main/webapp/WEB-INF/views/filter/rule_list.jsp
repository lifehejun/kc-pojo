<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>过滤规则数据</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div class="layui-form" action="" lay-filter="filterRule-form-element" style="margin-top: 10px;">
    <div class="layui-form-item">
        <%--<div class="layui-inline">
            <button class="layui-btn layui-btn-normal"  onclick="openExcelDialog()">Excel导入</button>
        </div>--%>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline" >
            <label class="layui-form-label">规则类型：</label>
            <div class="layui-input-inline layui-input-search">
                <select name="ruleType" id="ruleType" lay-search="">
                    <option value="">全部</option>
                    <c:forEach items="${filterRuleTypeMap}" var="ruleType">
                        <option value="${ruleType.key}">${ruleType.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-inline" >
            <label class="layui-form-label">业务类型：</label>
            <div class="layui-input-inline layui-input-search">
                <select name="bizType" id="bizType" lay-search="">
                    <option value="">全部</option>
                    <c:forEach items="${filterBizTypeMap}" var="bizType">
                        <option value="${bizType.key}">${bizType.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <%--<div class="layui-inline" >
            <label class="layui-form-label">过滤属性：</label>
            <div class="layui-input-inline layui-input-search">
                <select name="filterItem" id="filterItem" lay-search="">
                    <option value="">全部</option>
                    <c:forEach items="${filterItemEnumsMap}" var="filterItem">
                        <option value="${filterItem.key}">${filterItem.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>--%>
        <div class="layui-inline" >
            <label class="layui-form-label">过滤值：</label>
            <div class="layui-input-inline layui-input-search">
                <input class="layui-input" id="filterValue" name="filterValue"/>
            </div>
        </div>
        <div class="layui-inline">
            <button class="layui-btn layui-btn-normal"  lay-filter="filterRule-form-btn-sub" lay-submit="">搜索</button>
            <button class="layui-btn layui-btn-normal"  onclick="openAddRuleDialog()">添加规则</button>
            <button class="layui-btn layui-btn-normal"  onclick="openExcelDialog()">Excel导入</button>
        </div>
    </div>
</div>

<script type="text/html" id="barFilterRule">
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<table class="layui-hide" id="filterRuleList" lay-filter="filterRuleList"></table>

<script type="text/javascript">
    layui.use(['table','form'], function(){
        layui.form.render(null, 'filterRule-form-element');
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
                elem: '#filterRuleList'
                ,url:'/filterRule/list/page'
                ,title: '过滤规则列表'
                ,limit:20
                ,loading:true
                ,size:'sm'
                ,page: {
                    limits:[20, 30, 40, 50,100,200,500,1000],
                    theme: '#1E9FFF'
                } //开启分页
                ,cols: [[
                    {field:'ruleTypeDesc', title: '过滤规则类型',width:130 }
                    ,{field:'bizTypeDesc', title: '业务类型',width:150}
                    ,{field:'filterItemDesc', title: '过滤属性',width:150}
                    ,{field:'filterValue', title: '过滤值',width:300}
                    ,{field:'createTime', title: '创建时间', templet :function (row){
                            return createTime(row.createTime);
                        },width: 180 }
                    ,{fixed: 'right', title:'操作', toolbar: '#barFilterRule', width:180}

                ]]
            });

        //监听行工具事件
        table.on('tool(filterRuleList)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                var id = data.id;
                editFilterRule(id);

            }
            if(obj.event === 'del'){
                layer.confirm('确定删除该条数据吗', {
                    btn: ['确定', '取消',] //可以无限个按钮
                }, function(index, layero){
                    var id = data.id;
                    delFilterRule(id);
                });
            }
        });

        form.on('submit(filterRule-form-btn-sub)', function(data) {
            table.reload('filterRuleList', {
                where: data.field,
                method:'POST',
                pageNum: {curr:1},
            });
            return false;
        });

        var delFilterRule = function (id) {
            var data = {"id":id}
            _ajax_b("/filterRule/del",data,"删除成功",null);
        }

        var editFilterRule = function (id) {
            layer.open({
                type: 2,
                area: ['500','400'],
                content: '/filterRule/edit?id='+id
            });
        }

    });

    var openAddRuleDialog = function () {
        layer.open({
            type: 2,
            area: ['500','400'],
            content: '/filterRule/edit'
        });
    }

    var openExcelDialog = function () {
        layer.open({
            type: 2,
            title:"导入规则",
            area: ['550','300'],
            content: '/filterRule/openExcelDialog'
        });
    }
</script>
</body>
</html>
