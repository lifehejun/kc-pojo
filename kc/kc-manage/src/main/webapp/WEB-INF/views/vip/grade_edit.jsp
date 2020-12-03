<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/comm_css_js.jsp"/>
<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
    </div>
    <input name="id" type="hidden" value="${vipGrade.id}">
    <div class="layui-form-item">
        <label class="layui-form-label">会员等级 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <select name="grade" id="grade" lay-verify="required" lay-search="">
                <option value="">--请选择--</option>
                <c:forEach items="${vipGradeEnumsMap}" var="type">
                    <option value="${type.key}" <c:if test="${type.key == vipGrade.grade}">selected</c:if> > ${type.value}</option>
                </c:forEach>
            </select>
        </div>
        <label class="layui-form-label">提示信息</label>
        <div class="layui-input-inline">
            <textarea type="text" name="tips"  value="${vipGrade.tips}" autocomplete="off" class="layui-input"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">原价金额 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="oldMoney" lay-verify="required" value="${vipGrade.oldMoney}" autocomplete="off" class="layui-input">
        </div>
        <label class="layui-form-label">折扣金额 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="money" lay-verify="required" value="${vipGrade.money}" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">会员卡名称 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="vipName" lay-verify="required" value="${vipGrade.vipName}" autocomplete="off" class="layui-input">
        </div>
        <label class="layui-form-label">会员卡描述 </label>
        <div class="layui-input-inline">
            <input type="text" name="vipDesc"  value="${vipGrade.vipDesc}" autocomplete="off" class="layui-input">
        </div>
    </div>


    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="vipGradeSave">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script>
    layui.use(['form','upload'], function(){
        var form = layui.form
            ,upload = layui.upload;

        //监听提交
        form.on('submit(vipGradeSave)', function(data){
            //var jsonData = JSON.stringify(data.field);
            var jsonData = data.field;
            parent._ajax_b("/vipGrade/save",jsonData,"保存成功",null);
            return false;
        });
    });
</script>