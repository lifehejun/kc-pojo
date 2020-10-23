<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/comm_css_js.jsp"/>
<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
    </div>
    <input name="id" type="hidden" value="${domain.id}">
    <div class="layui-form-item">
        <label class="layui-form-label">域名链接 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="domainName"  lay-verify="required" value="${domain.domainName}" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">域名类型 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <select name="domainType" lay-verify="required"   lay-search="">
                <option value="">--请选择--</option>
                <c:forEach items="${domainTypeEnums}" var="type">
                    <option value="${type.key}" <c:if test="${type.key == domain.status}">selected</c:if> > ${type.value}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">状态 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <select name="status"  lay-verify="required"  lay-search="">
                <option value="">--请选择--</option>
                <c:forEach items="${statusEnums}" var="status">
                    <option value="${status.key}" <c:if test="${status.key == domain.status}">selected</c:if> > ${status.value}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">排序号 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="sortNo" lay-verify="required" value="${domain.sortNo}" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="domainSave">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script>
    layui.use('form', function(){
        var form = layui.form;

        //监听提交
        form.on('submit(domainSave)', function(data){
            //var jsonData = JSON.stringify(data.field);
            var jsonData = data.field;
            parent._ajax_b("/domain/save",jsonData,"保存成功",null);
            return false;
        });
    });
</script>