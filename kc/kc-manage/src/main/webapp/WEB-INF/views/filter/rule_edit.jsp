<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/comm_css_js.jsp"/>
<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
    </div>
    <input name="id" type="hidden" value="${filterRule.id}">
    <div class="layui-form-item">
        <label class="layui-form-label">规则类型 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <select name="ruleType" id="ruleType"  lay-verify="required"  lay-search="">
                <option value="">全部</option>
                <c:forEach items="${filterRuleTypeMap}" var="ruleType">
                    <option value="${ruleType.key}" <c:if test="${ruleType.key == filterRule.ruleType}">selected</c:if>  >${ruleType.value}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">业务类型 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <select name="bizType" id="bizType" lay-verify="required"  lay-search="">
                <option value="">全部</option>
                <c:forEach items="${filterBizTypeMap}" var="bizType">
                    <option value="${bizType.key}" <c:if test="${bizType.key == filterRule.bizType}">selected</c:if> >${bizType.value}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">过滤属性 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <select name="filterItem" id="filterItem" lay-verify="required"  lay-search="">
                <option value="">全部</option>
                <c:forEach items="${filterItemEnumsMap}" var="filterItem">
                    <option value="${filterItem.key}" <c:if test="${filterItem.key == filterRule.filterItem}">selected</c:if> >${filterItem.value}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">过滤值 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input class="layui-input" id="filterValue" lay-verify="required"  name="filterValue" value="${filterRule.filterValue}"/>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="filterRuleSave">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script>
    layui.use('form', function(){
        var form = layui.form;

        //监听提交
        form.on('submit(filterRuleSave)', function(data){
            //var jsonData = JSON.stringify(data.field);
            var jsonData = data.field;
            parent._ajax_b("/filterRule/save",jsonData,"保存成功",null);
            return false;
        });
    });
</script>