<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/comm_css_js.jsp"/>
<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
    </div>
    <input name="id" type="hidden" value="${videoLabel.id}">
    <div class="layui-form-item">
        <label class="layui-form-label">视频类型 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <select name="vodType" id="vodType" lay-verify="required" lay-search="">
                <option value="">--请选择--</option>
                <c:forEach items="${videoType}" var="type">
                    <option value="${type.key}" <c:if test="${type.key == videoLabel.vodType}">selected</c:if> > ${type.value}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">标签名称 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="labelName" lay-verify="required" value="${videoLabel.labelName}" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">标签描述 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="labelDesc" lay-verify="required" value="${videoLabel.labelDesc}" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">标签图片 <span style="color:red;">*</span></label>
        <div class="layui-input-block">
            <input type="text" name="imgUrl"  lay-verify="required" value="${videoLabel.imgUrl}" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="labelSave">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script>
    layui.use('form', function(){
        var form = layui.form;

        //监听提交
        form.on('submit(labelSave)', function(data){
            //var jsonData = JSON.stringify(data.field);
            var jsonData = data.field;
            parent._ajax_b("/videoLabel/save",jsonData,"保存成功",null);
            return false;
        });
    });
</script>