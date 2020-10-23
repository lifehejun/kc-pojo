<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>缓存管理</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div class="layui-form" action="" lay-filter="cache-form-element" style="margin-top: 10px;">
    <%--<div class="layui-form-item">
        <div class="layui-inline" >
            <div class="layui-input-inline">
                <button class="layui-btn layui-btn-normal"  lay-filter="recharge-query-btn" lay-submit="">添加缓存</button>
            </div>
        </div>
    </div>--%>
</div>

<script type="text/html" id="barCache">
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="refresh">刷新缓存</a>
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
</script>
<table class="layui-hide" id="cacheList" lay-filter="cacheList"></table>

<script type="text/javascript">
    layui.use(['table','form'], function(){
        layui.form.render(null, 'cache-form-element');
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
                elem: '#cacheList'
                ,url:'/cache/list/page'
                ,title: '缓存数据列表'
                ,limit:20
                ,loading:true
                ,size:'sm'
                ,page: {
                    limits:[20, 30, 40, 50,100,200,500,1000],
                    theme: '#1E9FFF'
                } //开启分页
                ,cols: [[
                    {field:'remark', title: '缓存名称',width: 200}
                    ,{field:'val', title: '缓存刷新路径',width: 500}
                    ,{field:'createTime', title: '创建时间', templet :function (row){
                            return createTime(row.createTime);
                        },width: 150 }
                    ,{fixed: 'right', title:'操作', toolbar: '#barCache', width:280}

                ]]
            });

        //监听行工具事件
        table.on('tool(cacheList)', function(obj){
            var data = obj.data;
            //console.log(obj)
            if(obj.event === 'edit'){
                var id = data.id;
                var val = data.val;
                editCache(id,val);
            }
            if(obj.event === 'refresh'){s
                var url = data.val;
                _ajax_b(url,null,"缓存刷新成功",null);
            }
        });

        var editCache = function (id,val) {
            layer.prompt({
                formType: 0,
                value: val,
                title: '请输入值',
                area: ['600px', '350px'] //自定义文本域宽高
            }, function(value, index, elem){
                alert(id+":"+value); //得到value
                var dataJson = {
                    id:id,
                    val:value
                }
                jQuery.post('/busconfig/save',dataJson,function(data){
                    if(data.code == "00"){
                        layer.msg(data.msg, {
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

            });


        }



    });
</script>
</body>
</html>
