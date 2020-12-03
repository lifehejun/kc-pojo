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
            <input type="text" name="topicTitle"  maxlength="10" lay-verify="required" value="${topic.topicTitle}" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">话题描述 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="topicDesc" lay-verify="required" value="${topic.topicDesc}" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">话题图片 <span style="color:red;">*</span></label>
        <div class="layui-input-block">
            <div class="layui-upload">
                <button type="button" class="layui-btn layui-btn-normal" id="uploadImg">上传图片</button>
                <div class="layui-upload-list" id="imgUrlView">
                    <%--<img class="layui-upload-img" id="imgUrlView">--%>
                    <p id="reUpload"></p>
                </div>
            </div>
            <input type="hidden" name="topicImgUrl" id="topicImgUrl" >
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
    layui.use(['form','upload'], function(){
        var form = layui.form
        ,upload = layui.upload;

        //普通图片上传
        var uploadInst = upload.render({
            elem: '#uploadImg'
            ,url: '/common/cos/uploadFile' //改成您自己的上传接口
            ,accept:'images'
            ,size: 1024*10
            ,number:1
            ,data: {moduleName: 'post'}
            ,before: function(obj){
                layer.load(); //上传loading
                //预读本地文件示例，不支持ie8
                /*obj.preview(function(index, file, result){
                    $('#demo1').attr('src', result); //图片链接（base64）
                });*/
            }
            ,done: function(res){
                layer.closeAll('loading'); //关闭loading
                var data = res.data;
                if(res.code == "00"){
                    $("#topicImgUrl").val(data.fileUrl);
                    $('#imgUrlView').append('<img src="'+ data.fileUrl +'" class="layui-upload-img">')
                }else{
                    layer.msg(res.msg, {icon:2,time:3000});
                }
            }
            ,error: function(){
                layer.closeAll('loading'); //关闭loading
                //演示失败状态，并实现重传
                var reUpload = $('#reUpload');
                reUpload.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                reUpload.find('.demo-reload').on('click', function(){
                    uploadInst.upload();
                });
            }
        });
        //监听提交
        form.on('submit(topicSave)', function(data){
            //var jsonData = JSON.stringify(data.field);
            var jsonData = data.field;
            parent._ajax_b("/topic/save",jsonData,"保存成功",null);
            return false;
        });

    });
</script>