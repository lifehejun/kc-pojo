<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/comm_css_js.jsp"/>
<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
    </div>
    <input name="id" type="hidden" value="${adv.id}">
    <div class="layui-form-item">
        <label class="layui-form-label">广告主名称 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="advCustomer"  maxlength="30" lay-verify="required" value="${adv.advCustomer}" autocomplete="off" class="layui-input">
        </div>

        <label class="layui-form-label">广告位置 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <select name="advPosition" lay-verify="required"   lay-search="">
                <option value="">--请选择--</option>
                <c:forEach items="${advPositionEnumsMap}" var="position">
                    <option value="${position.key}" <c:if test="${position.key == adv.advPosition}">selected</c:if> > ${position.value}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">广告链接 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="linkUrl"  lay-verify="required" value="${adv.linkUrl}" autocomplete="off" placeholder="跳转到外部浏览器地址" class="layui-input">
        </div>

        <label class="layui-form-label">广告图片 <span style="color:red;">*</span></label>
        <div class="layui-input-block">
            <div class="layui-upload">
                <button type="button" class="layui-btn layui-btn-normal" id="uploadAdvImg">上传图片</button>
                <div class="layui-upload-list" id="advImgUrlView">
                    <%--<img class="layui-upload-img" id="imgUrlView">--%>
                    <p id="reAdvUpload"></p>
                </div>
            </div>
            <input type="hidden" name="advImgUrl" id="advImgUrl" >
        </div>
    </div>

    <div class="layui-form-item" style="display: block">
        <label class="layui-form-label">app名称 </label>
        <div class="layui-input-inline">
            <input type="text" name="appName"  lay-verify="required" value="${adv.appName}" autocomplete="off"  class="layui-input">
        </div>

        <label class="layui-form-label">app描述 </label>
        <div class="layui-input-inline">
            <input type="text" name="appDesc"  lay-verify="required" value="${adv.appDesc}" autocomplete="off"  class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: block">
        <label class="layui-form-label">app图标icon </label>
        <div class="layui-input-block">
            <div class="layui-upload">
                <button type="button" class="layui-btn layui-btn-normal" id="uploadAppIconImg">上传图片</button>
                <div class="layui-upload-list" id="appIconImgView">
                    <%--<img class="layui-upload-img" id="imgUrlView">--%>
                    <p id="reIconUpload"></p>
                </div>
            </div>
            <input type="hidden" name="appIcon" id="appIcon" >
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">状态 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <select name="status"  lay-verify="required"  lay-search="">
                <option value="">--请选择--</option>
                <c:forEach items="${statusEnums}" var="status">
                    <option value="${status.key}" <c:if test="${status.key == adv.status}">selected</c:if> > ${status.value}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="saveAdv">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script>
    layui.use(['form','upload'], function(){
        var form = layui.form
        ,upload = layui.upload;

        //上传广告
        var uploadAdvImg = upload.render({
            elem: '#uploadAdvImg'
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
                    $("#advImgUrl").val(data.fileUrl);
                    $('#advImgUrlView').append('<img src="'+ data.fileUrl +'" class="layui-upload-img">')
                }else{
                    layer.msg(res.msg, {icon:2,time:3000});
                }
            }
            ,error: function(){
                layer.closeAll('loading'); //关闭loading
                //演示失败状态，并实现重传
                var reAdvUpload = $('#reAdvUpload');
                reAdvUpload.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                reAdvUpload.find('.demo-reload').on('click', function(){
                    uploadAdvImg.upload();
                });
            }
        });


        //上传app图标
        var uploadAppIcon = upload.render({
            elem: '#uploadAppIconImg'
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
                    $("#appIcon").val(data.fileUrl);
                    $('#appIconImgView').append('<img src="'+ data.fileUrl +'" class="layui-upload-img">')
                }else{
                    layer.msg(res.msg, {icon:2,time:3000});
                }
            }
            ,error: function(){
                layer.closeAll('loading'); //关闭loading
                //演示失败状态，并实现重传
                var reIconUpload = $('#reIconUpload');
                reIconUpload.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                reIconUpload.find('.demo-reload').on('click', function(){
                    uploadAppIcon.upload();
                });
            }
        });
        //监听提交
        form.on('submit(saveAdv)', function(data){
            //var jsonData = JSON.stringify(data.field);
            var jsonData = data.field;
            parent._ajax_b("/adv/save",jsonData,"保存成功",null);
            return false;
        });

    });
</script>