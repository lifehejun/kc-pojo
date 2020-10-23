<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/comm_css_js.jsp"/>
<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
    </div>
    <input name="id" type="hidden" value="${topic.id}">
    <div class="layui-form-item">
        <label class="layui-form-label">标题 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="topicTitle"  lay-verify="required" value="${topic.topicTitle}" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">话题描述 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="topicDesc" lay-verify="required" value="${topic.topicDesc}" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">话题图片路径 <span style="color:red;">*</span></label>
        <div class="layui-input-block">
            <input type="text" name="topicImgUrl" lay-verify="required" value="${topic.topicImgUrl}" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="topicSave">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script>
    layui.use('form', function(){
        var form = layui.form;

        //监听提交
        form.on('submit(topicSave)', function(data){
            //var jsonData = JSON.stringify(data.field);
            var jsonData = data.field;
            parent._ajax_b("/topic/save",jsonData,"保存成功",null);
            return false;
        });
    });
</script>