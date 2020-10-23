<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>网站开关设置</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div style="padding: 20px; background-color: #F2F2F2;">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">app设置</div>
                <div class="layui-card-body">

                        <c:forEach items="${webSwitchList}" var="webSwitch">
                            <form class="layui-form" action="">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">${webSwitch.remark} :</label>
                                    <div class="layui-input-block">
                                        <input type="checkbox" <c:if test="${webSwitch.val == 'true'}">checked</c:if>  id="${webSwitch.id}" remark="${webSwitch.remark}" name="close" lay-skin="switch" lay-filter="switchWeb" lay-text="开|关">
                                        <%--<input type="text" name="${webSwitch.name}" id="${webSwitch.name}"   value="${webSwitch.val}" autocomplete="off" class="layui-input">--%>
                                    </div>
                                </div>
                            </form>
                        </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    layui.use('form', function(){
        var form = layui.form;

        //监听指定开关
        form.on('switch(switchWeb)', function(data){
            var val = this.checked ? 'true' : 'false';
            var id = $(this).attr("id");
            var dataJson = {
                id:id,
                val:val
            }

            jQuery.post('/webConfig/update/json',dataJson,function(data){
                if(data.code == "00"){
                    layer.msg(data.msg, {
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
            //layer.tips('温馨提示：请注意开关状态的文字可以随意定义，而不仅仅是ON|OFF', data.othis)

        });

    });

</script>
</body>
</html>
