<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>违禁词数据</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div class="layui-form" action="" lay-filter="forbidWord-form-element" style="margin-top: 10px;">
    <div class="layui-form-item">
        <div class="layui-inline" >
            <label class="layui-form-label">违禁词：</label>
            <div class="layui-input-inline">
                <input class="layui-input" id="keyWord" placeholder="模糊查询" name="keyWord"/>
            </div>
        </div>
        <div class="layui-inline">
            <button class="layui-btn layui-btn-normal"  lay-filter="forbidWord-form-btn-sub" lay-submit="">搜索</button>
            <button class="layui-btn layui-btn-normal"  onclick="addForbidWord()">添加违禁词</button>
        </div>
    </div>
</div>

<script type="text/html" id="barForbidWord">
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<table class="layui-hide" id="forbidWordList" lay-filter="forbidWordList"></table>

<script type="text/javascript">
    layui.use(['table','form'], function(){
        layui.form.render(null, 'forbidWord-form-element');
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
                elem: '#forbidWordList'
                ,url:'/forbidWord/list/page'
                ,title: '业务参数列表'
                ,limit:20
                ,loading:true
                ,size:'sm'
                ,page: {
                    limits:[20, 30, 40, 50,100,200,500,1000],
                    theme: '#1E9FFF'
                } //开启分页
                ,cols: [[
                    {field:'keyWord', title: '违禁词',width:250 }
                    ,{field:'createTime', title: '创建时间', templet :function (row){
                            return createTime(row.createTime);
                        },width: 150 }
                    ,{fixed: 'right', title:'操作', toolbar: '#barForbidWord', width:180}

                ]]
            });


        form.on('submit(forbidWord-form-btn-sub)', function(data) {
            table.reload('forbidWordList', {
                where: data.field,
                method:'POST',
                pageNum: {curr:1},
            });
            return false;
        });
        //监听行工具事件
        table.on('tool(forbidWordList)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                var id = data.id;
                editForbidWord(id);

            }
            if(obj.event === 'del'){
                layer.confirm('确定删除该条数据吗', {
                    btn: ['确定', '取消',] //可以无限个按钮
                }, function(index, layero){
                    var id = data.id;
                    delForbidWord(id);
                });
            }
        });

        var delForbidWord = function (id) {
            var data = {"id":id}
            _ajax_b("/forbidWord/del",data,"删除成功",null);
        }
        var editForbidWord = function (id) {
            layer.open({
                type: 2,
                area: ['550','500'],
                content: '/forbidWord/edit?id='+id //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
            });
        }


    });

    var addForbidWord = function () {
        layer.open({
            title:'添加违禁词',
            type: 2,
            area: ['50%','40%'],
            content: '/forbidWord/edit' //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
    }
</script>
</body>
</html>
