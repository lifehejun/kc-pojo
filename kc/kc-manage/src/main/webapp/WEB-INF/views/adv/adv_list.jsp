<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>广告数据</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div class="layui-form" action="" lay-filter="adv-form-element" style="margin-top: 10px;">
    <div class="layui-form-item">
        <div class="layui-inline" >
            <label class="layui-form-label">广告主名称：</label>
            <div class="layui-input-inline layui-input-search">
                <input class="layui-input" id="advCustomer" placeholder="模糊查询" name="advCustomer"/>
            </div>
        </div>
        <div class="layui-inline" >
            <label class="layui-form-label">状态：</label>
            <div class="layui-input-inline layui-input-search">
                <select name="status" id="status" lay-search="">
                    <option value="">全部</option>
                    <c:forEach items="${statusEnums}" var="status">
                        <option value="${status.key}"  > ${status.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="layui-inline" >
            <label class="layui-form-label">广告位置：</label>
            <div class="layui-input-inline layui-input-search">
                <select name="advPosition" id="advPosition" lay-search="">
                    <option value="">全部</option>
                    <c:forEach items="${advPositionEnumsMap}" var="position">
                        <option value="${position.key}"  > ${position.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <button class="layui-btn layui-btn-normal"  lay-filter="adv-form-btn-sub" lay-submit="">搜索</button>
            <button class="layui-btn layui-btn-normal"  onclick="addAdv()">添加广告</button>
        </div>
    </div>
</div>

<script type="text/html" id="barAdv">
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<table class="layui-hide" id="advList" lay-filter="advList"></table>

<script type="text/javascript">
    layui.use(['table','form'], function(){
        layui.form.render(null, 'adv-form-element');
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
                elem: '#advList'
                ,url:'/adv/list/page'
                ,title: '广告列表'
                ,limit:20
                ,loading:true
                ,size:'sm'
                ,page: {
                    limits:[20, 30, 40, 50,100,200,500,1000],
                    theme: '#1E9FFF'
                } //开启分页
                ,cols: [[
                    {field:'advCode', title: '广告Code', }
                    ,{field:'advCustomer', title: '广告主名称'}
                    ,{field:'advPositionDesc', title: '广告位置'}
                    ,{field:'advImgUrl', title: '广告图片路径',width: 150,templet:function (d) {
                        var advImgUrl = d.advImgUrl;
                        return '<img src= "'+advImgUrl+'" height ="20" onclick="openImgView(\''+advImgUrl+'\')"/>';
                        }}
                    ,{field:'appName', title: 'app名称'}
                    ,{field:'status', title: '状态', templet: function(d) {
                            return d.status == 0 ? '<font style="color: #FF5722">无效</font>': '<font style="color: #1E9FFF">有效</font>';
                        }}
                    ,{field:'startTime', title: '开始时间', templet :function (row){
                            return createTime(row.startTime);
                        },width: 150 }
                    ,{field:'endTime', title: '结束时间', templet :function (row){
                            return createTime(row.endTime);
                        },width: 150 }
                    ,{fixed: 'right', title:'操作', toolbar: '#barAdv', width:180}

                ]]
            });


        form.on('submit(adv-form-btn-sub)', function(data) {
            table.reload('advList', {
                where: data.field,
                method:'POST',
                pageNum: {curr:1},
            });
            return false;
        });
        //监听行工具事件
        table.on('tool(advList)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                var id = data.id;
                editAdv(id);

            }
            if(obj.event === 'del'){
                layer.confirm('确定删除该条数据吗', {
                    btn: ['确定', '取消',] //可以无限个按钮
                }, function(index, layero){
                    var id = data.id;
                    delAdv(id);
                });
            }
        });

        var delAdv = function (id) {
            var data = {"id":id}
            _ajax_b("/adv/del",data,"删除成功",null);
        }
        var editAdv = function (id) {
            layer.open({
                type: 2,
                area: ['70%','70%'],
                content: '/adv/edit?id='+id //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
            });
        }


    });

    var addAdv = function () {
        layer.open({
            title:'添加广告',
            type: 2,
            area: ['70%','70%'],
            content: '/adv/edit' //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
    }
</script>
</body>
</html>
