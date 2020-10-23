<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>视频数据</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div class="layui-form" action="" lay-filter="video-form-element" style="margin-top: 10px;">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">视频类型：</label>
            <div class="layui-input-inline">
                <select name="vodType" lay-search="">
                    <option value="">--请选择--</option>
                    <c:forEach items="${videoType}" var="type">
                        <option value="${type.key}" <c:if test="${type.key == video.vodType}">selected</c:if> > ${type.value}</option>
                    </c:forEach>
                </select>
            </div>
            <label class="layui-form-label">视频标签：</label>
            <div class="layui-input-inline">
                <select name="labelCode" id="labelCode" lay-search="">
                    <option value="">全部</option>
                    <c:forEach items="${videoLabelGroupMap}" var="labelGroup">
                        <optgroup label="${labelGroup.key}">
                            <c:forEach items="${labelGroup.value}" var="label">
                                <option value="${label.labelCode}">${label.labelName}</option>
                            </c:forEach>
                        </optgroup>
                    </c:forEach>
                </select>
            </div>

            <div class="layui-inline" >
                <label class="layui-form-label">视频名称：</label>
                <div class="layui-input-inline">
                    <input class="layui-input" id="vodName" name="vodName" placeholder="模糊查询"/>
                </div>
            </div>

            <div class="layui-inline">
                <button class="layui-btn layui-btn-normal"  lay-filter="video-form-btn-sub" lay-submit="">搜索</button>
            </div>
            <div class="layui-inline">
                <button class="layui-btn layui-btn-normal" id="videoPublishBtn" onclick="videoPublish()" >视频发布</button>
            </div>
        </div>
    </div>
</div>

<script type="text/html" id="barVideo">
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs layui-btn-normal" lay-event="del">删除</a>
</script>
<table class="layui-hide" id="videoList" lay-filter="videoList"></table>

<script type="text/javascript">
    layui.use(['table','form'], function(){
        layui.form.render(null, 'video-form-element');
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
                elem: '#videoList'
                ,url:'/video/list/page'
                ,title: '视频列表'
                ,limit:20
                ,loading:true
                ,size:'sm'
                ,page: {
                    limits:[20, 30, 40, 50,100,200,500,1000],
                    theme: '#1E9FFF'
                } //开启分页
                ,cols: [[
                    {field:'vodType', title: '视频类型',width: 150,sort: true, templet: function(d) {
                            if(d.vodType == "erect"){
                                return '小视频(竖屏)';
                            }else if(d.vodType == "sidelong"){
                                return '普通视频(横屏)';
                            }
                        }}
                    ,{field:'vodName', title: '视频名称',width:200}
                    ,{field:'vodImgUrl', title: '视频封面',templet:function (d) {
                            return '<img src="'+d.vodImgUrl+'" width="100px" height="40px"/>';
                        },width:140}
                    ,{field:'vodLabel', title: '视频标签',width:300,templet:function (d) {
                            return getLabelNameList(d.labelNameList);
                        }}
                    ,{field:'likeNum', title: '点赞量',sort: true,width: 100}
                    ,{field:'commentNum', title: '评论量',sort: true,width: 100}
                    ,{field:'status', title: '审核状态',sort: true,width: 100,templet :function (d){
                            if(d.status = 0 || d.status == null || d.status == ''){
                                return "未审核";
                            }else if(d.status == 1){
                                return "审核通过";
                            }else if(d.status == 2){
                                return "审核未通过";
                            }
                        }}
                    ,{field:'createTime', title: '创建时间', templet :function (row){
                            return createTime(row.createTime);
                        },width: 160 }
                    ,{fixed: 'right', title:'操作', toolbar: '#barVideo', width:180}

                ]]
            });

        //搜索
        form.on('submit(video-form-btn-sub)', function(data) {
            table.reload('videoList', {
                where: data.field,
                method:'POST',
                pageNum: {curr:1},
            });
            return false;
        });



        //监听行工具事件
        table.on('tool(videoList)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                var id = data.id;
                editLabel(id);
            }
        });

        form.on('button(videoPublishBtn)',function () {

            return false;
        });

        //获取视频标签名称
        var getLabelNameList = function (labelNameList) {
            debugger;
            if(labelNameList == null || labelNameList == "" || labelNameList == undefined){
                return "";
            }
            var labelHtml = "";
            var strs = labelNameList.split(","); //字符分割
            var btnSty = ["layui-bg-orange","layui-bg-green","layui-bg-cyan","layui-bg-blue","layui-bg-black","layui-bg-gray"]
            for (var i=0; i<strs.length; i++ ){
                labelHtml += '<span class="layui-badge '+btnSty[i]+'">'+strs[i]+'</span>&nbsp;&nbsp;&nbsp;';
            }
            return labelHtml;
        }

        var editLabel = function (id) {
            layer.open({
                type: 2,
                area: ['480','400'],
                content: '/video/edit?id='+id //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
            });

        }
    });

    var videoPublish = function () {
        layer.open({
            type: 2,
            area: ['60%','80%'],
            content: '/video/edit' //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
    }
</script>
</body>
</html>
