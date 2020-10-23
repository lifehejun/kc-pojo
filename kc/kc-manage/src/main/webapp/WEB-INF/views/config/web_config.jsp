<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>网站设置</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div style="padding: 20px; background-color: #F2F2F2;">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md6">
            <div class="layui-card">
                <div class="layui-card-header">app设置</div>
                <div class="layui-card-body">
                    <div class="layui-form-item">
                    <c:forEach items="${webConfigList}" var="webConfig" varStatus="index">
                            <div class="layui-form-item">
                                        <label class="layui-form-label">${webConfig.remark} <span style="color:red;">*</span></label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="${webConfig.name}" id="${webConfig.name}"   value="${webConfig.val}" autocomplete="off" class="layui-input">
                                        </div>
                                        <div class="layui-input-inline">
                                            <button class="layui-btn layui-btn-normal"  onclick="updateWebConfig($(this),${webConfig.id},'${webConfig.remark}')">修改</button>
                                        </div>
                            </div>
                    </c:forEach>

                </div>
            </div>
        </div>
        <%--<div class="layui-col-md6">
            <div class="layui-card">
                <div class="layui-card-header">快捷操作</div>
                <div class="layui-card-body">

                </div>
            </div>
        </div>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">标题</div>
                <div class="layui-card-body">
                    内容
                </div>
            </div>
        </div>--%>
    </div>
</div>


<script>
    layui.use('form', function(){
        var form = layui.form;

        //监听提交
        form.on('submit(transSubmit)', function(data){
            //var jsonData = JSON.stringify(data.field);
            var phone = $("#phone").val();
            data.field.phone = phone;
            var jsonData = data.field;
            _ajax_b("/trans/submitManualTrans/json",jsonData,"操作成功",null);
            return false;
        });
        form.on('submit(manualUserRegSubmit)', function(data){
            //var jsonData = JSON.stringify(data.field);
            var jsonData = data.field;
            _ajax_b("/user/submitManualUserReg/json",jsonData,"注册成功",null);
            return false;
        });


    });

    //修改
    function updateWebConfig(e,id,remark) {
        var val = $(e).parent().prev().children("input").val();
        if(val == null || val == "" || val == undefined){
            layer.msg("请输入要修改的内容", {icon:3,time:2000});
            return;
        }
        layer.confirm('确定修改['+remark+']吗', {
            btn: ['确定', '取消',] //可以无限个按钮
        }, function(index, layero){
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
        });


        return;
    }
</script>
</body>
</html>
