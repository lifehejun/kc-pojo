<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/comm_css_js.jsp"/>
<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
    </div>
    <input name="id" type="hidden" value="${coupon.id}">
    <div class="layui-form-item">
        <label class="layui-form-label">名称 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="couponName"  lay-verify="required" value="${coupon.couponName}" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">前端显示名称 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="showName"  lay-verify="required" value="${coupon.showName}" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">优惠券类型 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <select name="couponType" id="couponType" lay-verify="required"  lay-search="">
                <option value="">全部</option>
                <c:forEach items="${couponTypeEnumsMap}" var="couponType">
                    <option value="${couponType.key}" <c:if test="${couponType.key == coupon.couponType}">selected</c:if> >${couponType.value}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">业务类型 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <select name="busType" id="busType" lay-verify="required"  lay-search="">
                <option value="">全部</option>
                <c:forEach items="${couponBusTypeEnumsMap}" var="busType">
                    <option value="${busType.key}" <c:if test="${busType.key == coupon.busType}">selected</c:if> >${busType.value}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">发放方式 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <select name="giveType" id="giveType" lay-verify="required"  lay-search="">
                <option value="">全部</option>
                <c:forEach items="${couponGiveTypeEnumsMap}" var="giveType">
                    <option value="${giveType.key}" <c:if test="${giveType.key == coupon.giveType}">selected</c:if> >${giveType.value}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">前端显示名称 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="showName"  lay-verify="required" value="${coupon.showName}" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">面值金额 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="faceValue" lay-verify="required" value="${coupon.faceValue}" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">补贴金额 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="subsidyAmount" lay-verify="required" value="${coupon.subsidyAmount}" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">发放数量 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="num" lay-verify="required" value="${coupon.num}" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">状态 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <select name="status" id="status" lay-verify="required"  lay-search="">
                <option value="">全部</option>
                <c:forEach items="${statusEnums}" var="status">
                    <option value="${status.key}" <c:if test="${status.key == coupon.status}">selected</c:if> >${status.value}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="saveCoupon">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script>
    layui.use('form', function(){
        var form = layui.form;

        //监听提交
        form.on('submit(saveCoupon)', function(data){
            //var jsonData = JSON.stringify(data.field);
            var jsonData = data.field;
            parent._ajax_b("/coupon/save",jsonData,"保存成功",null);
            return false;
        });
    });
</script>