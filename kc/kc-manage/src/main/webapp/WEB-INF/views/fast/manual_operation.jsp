<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>人工操作</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div style="padding: 20px; background-color: #F2F2F2;">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md7" style="display: none">
            <div class="layui-card">
                <div class="layui-card-header">人工交易</div>
                <div class="layui-card-body">
                        <div class="layui-form-item">
                            <label class="layui-form-label"></label>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">手机号 <span style="color:red;">*</span></label>
                            <div class="layui-input-inline">
                                <input type="text" name="phone" id="phone" lay-verify="required|phone"  value="" autocomplete="off" class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn-normal"  onclick="findPhone()">查询</button>
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">钱包余额</label>
                            <div class="layui-input-inline">
                                <input type="text" name="coreBalance" id="coreBalance"  readonly autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">金币余额</label>
                            <div class="layui-input-inline">
                                <input type="text" name="goldCoin" id="goldCoin"  readonly autocomplete="off" class="layui-input">
                            </div>
                        </div>
                    <form class="layui-form" action="">
                        <div class="layui-form-item">
                            <label class="layui-form-label">交易类型 <span style="color:red;">*</span></label>
                            <div class="layui-input-inline">
                                <select name="transType" lay-verify="required">
                                    <option value="">请选择交易类型</option>
                                    <optgroup label="金币类交易">
                                        <c:forEach items="${goldCoinTransTypeMap}" var="goldCoinTransType">
                                            <option value="${goldCoinTransType.key}">${goldCoinTransType.value}</option>
                                        </c:forEach>

                                    </optgroup>
                                    <optgroup label="现金类交易">
                                        <c:forEach items="${moneyTransTypeMap}" var="transType">
                                            <option value="${transType.key}">${transType.value}</option>
                                        </c:forEach>
                                    </optgroup>
                                </select>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">交易金额 <span style="color:red;">*</span></label>
                            <div class="layui-input-inline">
                                <input type="text" name="transValue" id="transValue"  lay-verify="required|number"    autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn layui-btn-normal" lay-submit lay-filter="transSubmit">提交</button>
                                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="layui-col-md5">
            <div class="layui-card" style="display: none">
                <div class="layui-card-header">人工注册用户</div>
                <div class="layui-card-body">
                    <form class="layui-form" action="">
                        <div class="layui-form-item">
                            <label class="layui-form-label">手机号 <span style="color:red;">*</span></label>
                            <div class="layui-input-inline">
                                <input type="text" name="regPhone" id="regPhone"  lay-verify="required|phone"   autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">用户类型 <span style="color:red;">*</span></label>
                            <div class="layui-input-inline">
                                <select name="userType" lay-verify="required">
                                    <c:forEach items="${userTypeMap}" var="userType">
                                        <option value="${userType.key}">${userType.value}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn layui-btn-normal" lay-submit lay-filter="manualUserRegSubmit">提交注册</button>
                                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <!--快捷操作-->
            <div class="layui-card">
                <div class="layui-card-header">快捷业务</div>
                <div class="layui-card-body">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn-normal"  onclick="openVideoMember()">开通视频会员</button>
                            </div>
                        </div>
                </div>
            </div>
            <!---->
        </div>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">标题</div>
                <div class="layui-card-body">
                    内容
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    layui.use('form', function(){
        var form = layui.form;

        //监听提交
        form.on('submit(transSubmit)', function(data){
            //var jsonData = JSON.stringify(data.field);

            layer.confirm('确定提交该操作吗', {
                btn: ['确定', '取消',] //可以无限个按钮
            }, function(index, layero){
                var phone = $("#phone").val();
                data.field.phone = phone;
                var jsonData = data.field;
                _ajax_b("/trans/submitManualTrans/json",jsonData,"操作成功",null);
            });
            return false;
        });
        form.on('submit(manualUserRegSubmit)', function(data){
            //var jsonData = JSON.stringify(data.field);
            layer.confirm('确定提交该操作吗', {
                btn: ['确定', '取消',] //可以无限个按钮
            }, function(index, layero){
                var jsonData = data.field;
                _ajax_b("/user/submitManualUserReg/json",jsonData,"注册成功",null);
            });
            return false;
        });


    });
    
    function findPhone() {
        var phone = $("#phone").val();
        var data = {
            phone:phone
        }
        jQuery.post('/trans/findByPhone/json',data,function(data){
            if(data.code == "00"){
                $("#coreBalance").val(data.data.user.coreBalance);
                $("#goldCoin").val(data.data.user.goldCoin);
            }else{
                layer.msg(data.msg, {icon:3,time:2000});
            }
        });
        return;
    }

    //弹窗开通视频会员
    function openVideoMember() {
        layer.open({
            title:'开通视频会员',
            type: 2,
            area: ['60%','65%'],
            content: '/fast/openVideoMember' //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
        return;
    }
</script>
</body>
</html>
