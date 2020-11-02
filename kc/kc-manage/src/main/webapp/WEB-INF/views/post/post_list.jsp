<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>帖子数据</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div class="layui-form" action="" lay-filter="post-form-element" style="margin-top: 10px;">
    <div class="layui-form-item">
        <div class="layui-inline" >
            <label class="layui-form-label">帖子主题：</label>
            <div class="layui-input-inline layui-input-search">
                <select name="topicCode"   lay-search="">
                    <option value="">--请选择--</option>
                    <c:forEach items="${topicList}" var="topic">
                        <option value="${topic.topicCode}">${topic.topicTitle}</option>
                    </c:forEach>
                </select>
            </div>

            <%--<div class="layui-input-inline layui-input-search">

                <input class="layui-input" id="topicCode" name="topicCode"/>
            </div>--%>
        </div>
        <div class="layui-inline" >
            <label class="layui-form-label">帖子内容：</label>
            <div class="layui-input-inline layui-input-search">
                <input class="layui-input" id="postTitle" name="postTitle" placeholder="模糊查询"/>
            </div>
        </div>
        <div class="layui-inline" >
            <label class="layui-form-label">审核状态：</label>
            <div class="layui-input-inline layui-input-search">
                <select name="status" id="status" lay-search="">
                    <option value="">全部</option>
                    <option value="0">未审核</option>
                    <option value="1">已审核</option>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <button class="layui-btn layui-btn-normal"  lay-filter="post-form-btn-sub" lay-submit="">搜索</button>
            <button class="layui-btn layui-btn-normal"  onclick="openPublishDialog()">人工发帖</button>
        </div>
    </div>
</div>

<script type="text/html" id="barPost">
    {{#  if(d.status == 0){ }}
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="checkSuccess">审核通过</a>
    {{#  } }}
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<table class="layui-hide" id="postList" lay-filter="postList"></table>

<script type="text/javascript">
    layui.use(['table','form'], function(){
        layui.form.render(null, 'post-form-element');
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
                elem: '#postList'
                ,url:'/post/list/page'
                ,title: '帖子列表'
                ,limit:20
                ,loading:true
                ,size:'sm'
                ,page: {
                    limits:[20, 30, 40, 50,100,200,500,1000],
                    theme: '#1E9FFF'
                } //开启分页
                ,cols: [[
                    {field:'phone', title: '手机号(用户)',width:120 }
                    ,{field:'postTitle', title: '帖子内容',width:180}
                    ,{field:'vodLabel', title: '主题标签',width:200,templet:function (d) {
                            return getTopicTitleList(d.topicTitleList);
                        }}
                    ,{field:'likeNum', title: '点赞量'}
                    ,{field:'viewNum', title: '浏览量'}
                    ,{field:'commentNum', title: '评论量'}
                    ,{field:'rewardAmount', title: '打赏量'}

                    ,{field:'status', title: '审核状态', templet: function(d) {
                            return d.status == 0 ? '<font style="color: #FF5722">待审核</font>': '<font style="color: #1E9FFF">已审核</font>';
                        }}
                    ,{field:'createTime', title: '创建时间', templet :function (row){
                            return createTime(row.createTime);
                        },width: 150 }
                    ,{fixed: 'right', title:'操作', toolbar: '#barPost', width:180}

                ]]
            });

        //监听行工具事件
        table.on('tool(postList)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                var id = data.id;
                editPost(id);

            }
            if(obj.event === 'del'){
                layer.confirm('确定删除该条数据吗', {
                    btn: ['确定', '取消',] //可以无限个按钮
                }, function(index, layero){
                    var id = data.id;
                    delPost(id);
                });
            }
            if(obj.event === 'checkSuccess'){
                layer.confirm('确定审核通过该帖子吗', {
                    btn: ['确定', '取消',] //可以无限个按钮
                }, function(index, layero){
                    var id = data.id;
                    checkSuccessPost(id);
                });
            }

            if(obj.event === 'openPublishDialog'){
                openPublishDialog();
            }

        });

        form.on('submit(post-form-btn-sub)', function(data) {
            table.reload('postList', {
                where: data.field,
                method:'POST',
                pageNum: {curr:1},
            });
            return false;
        });


        //获取视频标签名称
        var getTopicTitleList = function (topicTitleList) {
            if(topicTitleList == null || topicTitleList == "" || topicTitleList == undefined){
                return "";
            }
            var topicTitleHtml = "";
            var strs = topicTitleList.split(","); //字符分割
            var btnSty = ["layui-bg-orange","layui-bg-green","layui-bg-cyan","layui-bg-blue","layui-bg-black","layui-bg-gray"]
            for (var i=0; i<strs.length; i++ ){
                topicTitleHtml += '<span class="layui-badge '+btnSty[i]+'">'+strs[i]+'</span>&nbsp;&nbsp;&nbsp;';
            }
            return topicTitleHtml;
        }
        var delPost = function (id) {
            var data = {"id":id}
            _ajax_b("/post/del",data,"删除成功",null);
        }
        var checkSuccessPost = function (id) {
            var data = {"id":id}
            _ajax_b("/post/checkSuccess",data,"审核通过",null);
        }
        var editPost = function (id) {
            layer.open({
                type: 2,
                area: ['80%','85%'],
                content: '/post/edit?id='+id //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
            });
        }

    });

    var openPublishDialog = function () {
        layer.open({
            title:'发布帖子',
            type: 2,
            area: ['80%','85%'],
            content: '/post/openPublishPost' //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
    }
</script>
</body>
</html>
