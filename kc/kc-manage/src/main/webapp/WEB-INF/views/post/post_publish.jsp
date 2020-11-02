<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/comm_css_js.jsp"/>

<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">内部用户</label>
        <div class="layui-input-inline">
            <select name="userId"   lay-search="">
                <option value="">--请选择--</option>
                <c:forEach items="${testUserList}" var="user">
                    <option value="${user.userId}">${user.phone}</option>
                </c:forEach>
            </select>
        </div>
        <div class="layui-inline" style="margin-top: 10px">
            <span style="color: red"> &若不选择,系统将随机设置一个内部账户</span>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">主题标签 <span style="color:red;">*</span></label>
        <div class="layui-input-block" >
            <c:forEach items="${topicList}" var="topic" varStatus="index">
                <input type="checkbox" name="topicCodeList" value="${topic.topicCode}" title="${topic.topicTitle}">
            </c:forEach>
            <%--<input type="checkbox" name="like[write]" title="写作">
            <input type="checkbox" name="like[read]" title="阅读" checked="">
            <input type="checkbox" name="like[game]" title="游戏">--%>
        </div>
        <div class="layui-inline" style="margin-top: 10px">
            <span style="color: red"> &至少选一个标签</span>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">帖子内容 <span style="color:red;">*</span></label>
        <div class="layui-input-block">
            <textarea placeholder="请输入内容" lay-verify="required"   name="postTitle" class="layui-textarea" style="width: 85%">${post.postTitle}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">帖子图片</label>
        <div class="layui-input-block post-img post-images-input">
            <%--<input type="hidden" name="postImageListInput" value=""/>--%>
            <div class="layui-upload">
                <button type="button" class="layui-btn layui-btn-normal" id="postImagesUpload">多图片上传</button>
                <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;width: 80%">
                    预览图：
                    <div class="layui-upload-list post-img" id="postImagesView"></div>
                </blockquote>
            </div>
        </div>
    </div>
   <%-- <div class="layui-form-item">
        <label class="layui-form-label">帖子视频</label>
        <div class="layui-input-block">
            <img width="80px" height="80px" src="https://dss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2043091788,724368588&fm=58"/>
        </div>
    </div>--%>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="postSave">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script>
    layui.use(['form','upload'], function(){
        var form = layui.form;
        var upload = layui.upload;
        //多图片上传
        upload.render({
            elem: '#postImagesUpload'
            ,url: '/common/cos/uploadFile' //改成您自己的上传接口
            ,multiple: true
            ,accept:'images'
            ,size: 1024*10
            ,number:9
            ,data: {moduleName: 'post'}
            ,before: function(obj){
                layer.load(); //上传loading
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    //$('#postImagesView').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">')
                });
            }
            ,done: function(res,index){
                layer.closeAll('loading'); //关闭loading
                //上传完毕
                var data = res.data;
                if(res.code == "00"){
                    $(".post-images-input").append('<input type="hidden" name="postImageListInput" value="'+data.fileUrl+'"/>')
                    $('#postImagesView').append('<img src="'+ data.fileUrl +'" class="layui-upload-img">')
                }else{
                    layer.msg(res.msg, {icon:2,time:3000});
                }
            }
            ,error: function(index, upload){
                layer.closeAll('loading'); //关闭loading
                layer.msg("网络异常,上传失败", {icon:2,time:3000});
            }
        });

        //监听提交
        form.on('submit(postSave)', function(data){
            //var jsonData = JSON.stringify(data.field);
            var arr = new Array();
            $("input:checkbox[name='topicCodeList']:checked").each(function(i){
                arr[i] = ($(this).val());
            });
            if(arr.length < 1 ){
                layer.msg("至少选择一个标签", {icon: 3,time: 2000});
                return false;
            }
            if(arr.length>3){
                layer.msg("最多选择3个标签", {icon: 3,time: 2000});
                return false;
            }
            data.field.topicCodeList = arr.join(",");
            var postImages = new Array();
            $("input[name='postImageListInput']").each(function (index) {
                postImages.push($(this).val());
            });

            if(postImages.length>9){
                layer.msg("最多上传9个图片", {icon: 3,time: 2000});
                return false;
            }
            if(postImages.length < 1){
                layer.msg("至少选择一个图片", {icon: 3,time: 2000});
                return false;
            }

            data.field.postImages = postImages.join(",");
            var jsonData = data.field;
            parent._ajax_b("/post/publish",jsonData,"发布成功",null);
            return false;
        });
    });
</script>