<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>视频标签配置</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div class="layui-form" action="" lay-filter="label-form-element" style="margin-top: 10px;">
    <div class="layui-form-item">
        <div class="layui-inline" >
            <label class="layui-form-label">标签名称：</label>
            <div class="layui-input-inline layui-input-search">
                <input class="layui-input" id="labelName" placeholder="模糊查询" name="labelName"/>
            </div>
        </div>
        <div class="layui-inline" >
            <label class="layui-form-label">视频类型：</label>
            <div class="layui-input-inline layui-input-search">
                <select name="vodType" id="vodType" lay-search="">
                    <option value="">--请选择--</option>
                    <c:forEach items="${videoType}" var="type">
                        <option value="${type.key}" <c:if test="${type.key == video.vodType}">selected</c:if> > ${type.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <button class="layui-btn layui-btn-normal"  lay-filter="label-form-btn-sub" lay-submit="">搜索</button>
            <button class="layui-btn layui-btn-normal"  onclick="addVideoLabel()">添加视频标签</button>
        </div>
    </div>
</div>

<script type="text/html" id="barLabel">
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<table class="layui-hide" id="labelList" lay-filter="labelList"></table>

<script type="text/javascript">
    layui.use(['table','form'], function(){
        layui.form.render(null, 'label-form-element');
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
                elem: '#labelList'
                ,url:'/videoLabel/list/page'
                ,title: '视频标签列表'
                ,limit:20
                ,loading:true
                ,size:'sm'
                ,page: {
                    limits:[20, 30, 40, 50,100,200,500,1000],
                    theme: '#1E9FFF'
                } //开启分页
                ,cols: [[
                    {field:'vodType', title: '标签类型',width: 150,sort: true, templet: function(d) {
                            if(d.vodType == "erect"){
                                return '小视频(竖屏)';
                            }else if(d.vodType == "sidelong"){
                                return '普通视频(横屏)';
                            }
                        }}
                    ,{field:'labelName', title: '标签名称',width: 150}
                    ,{field:'labelDesc', title: '标签描述',width: 150}
                    ,{field:'imgUrl', title: '标签图标',width: 150,templet:function (d) {
                            var imgUrl = d.imgUrl;
                            return '<img src= "'+imgUrl+'" height ="20"  onclick="openImgView(\''+imgUrl+'\')"/>';
                        }}
                    ,{field:'createTime', title: '创建时间', templet :function (row){
                            return createTime(row.createTime);
                        },width: 140 }
                    ,{fixed: 'right', title:'操作', toolbar: '#barLabel', width:180}

                ]]
            });

        form.on('submit(label-form-btn-sub)', function(data) {
            table.reload('labelList', {
                where: data.field,
                method:'POST',
                pageNum: {curr:1},
            });
            return false;
        });
        //监听行工具事件
        table.on('tool(labelList)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                var id = data.id;
                editLabel(id);
            }
            if(obj.event === 'del'){
                layer.confirm('确定删除该条数据吗', {
                    btn: ['确定', '取消',] //可以无限个按钮
                }, function(index, layero){
                    var id = data.id;
                    delLabel(id);
                });
            }
        });

        var delLabel = function (id) {
            var data = {"id":id}
            _ajax_b("/videoLabel/del",data,"删除成功",null);
        }
        var editLabel = function (id) {
            layer.open({
                type: 2,
                area: ['50%','80%'],
                content: '/videoLabel/edit?id='+id //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
            });

        }
    });

    var addVideoLabel = function () {
        layer.open({
            type: 2,
            area: ['50%','80%'],
            content: '/videoLabel/edit' //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
    }
</script>
</body>
</html>
