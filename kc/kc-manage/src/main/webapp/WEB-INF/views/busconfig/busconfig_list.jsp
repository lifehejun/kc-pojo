<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>基础数据设置</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div class="layui-form" action="" lay-filter="bus-config-form-element" style="margin-top: 10px;">
    <div class="layui-form-item">
        <%--<div class="layui-inline" >--%>
            <%--<label class="layui-form-label">类型：</label>--%>
            <%--<div class="layui-input-inline">--%>
                <%--<input class="layui-input" id="userName" name="userName"/>--%>
            <%--</div>--%>
        <%--</div>--%>
    </div>
</div>

<script type="text/html" id="barBusConfig">
    <%--<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>--%>
</script>
<table class="layui-hide" id="busConfigList"></table>

<script type="text/javascript">
    layui.use(['table','form'], function(){
        layui.form.render(null, 'bus-config-form-element');
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
                elem: '#busConfigList'
                ,url:'/busconfig/list/page'
                ,title: '业务参数列表'
                ,limit:20
                ,loading:true
                ,size:'sm'
                ,page: {
                    limits:[20, 30, 40, 50,100,200,500,1000],
                    theme: '#1E9FFF'
                } //开启分页
                ,cols: [[
                    {field:'busType', title: '字典类型',width: 150 }
                    ,{field:'name', title: '值',width: 150}
                    ,{field:'val', title: '值描述',width: 300}
                    ,{field:'remark', title: '备注',width: 150}
                    ,{field:'createTime', title: '创建时间', templet :function (row){
                            return createTime(row.createTime);
                        },width: 150 }
                    ,{fixed: 'right', title:'操作', toolbar: '#barBusConfig', width:180}

                ]]
            });

        //监听行工具事件
        table.on('tool(busConfigList)', function(obj){
            var data = obj.data;
            debugger;
            //console.log(obj)
            if(obj.event === 'edit'){

                debugger;
                var id = obj.data.id;
                editBusConfig(id);

            }
        });

        var editBusConfig = function (id) {

            layer.open({
                type: 2,
                content: 'http://sentsin.com' //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
            });

        }



    });
</script>
</body>
</html>
