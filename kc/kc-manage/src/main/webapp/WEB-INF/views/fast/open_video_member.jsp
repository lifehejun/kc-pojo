<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/comm_css_js.jsp"/>

    <div class="layui-form-item">
        <label class="layui-form-label"></label>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">手机号 <span style="color:red;">*</span></label>
        <div class="layui-input-inline">
            <input type="text" name="phone" id="phone" lay-verify="required|phone"  value="" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-input-inline">
            <button class="layui-btn layui-btn-normal"  onclick="findByPhone()">查询信息</button>
        </div>
    </div>

    <div class="layui-form" style="width: 52%;margin-left: 50px">
        <table class="layui-table" lay-size="sm">
            <colgroup>
                <col width="100">
                <col width="150">
                <col>
            </colgroup>
            <thead>
            </thead>
            <tbody>
            <tr>
                <td>会员等级</td>
                <td id="gradeName"></td>
            </tr>
            <tr>
                <td>会员到期日</td>
                <td id="vipEndTimeStr"></td>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="postSave">立即开通</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>


<script>
    layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(postSave)', function(data){
            //var jsonData = JSON.stringify(data.field);
            var arr = new Array();
            $("input:checkbox[name='topicCodeList']:checked").each(function(i){
                arr[i] = ($(this).val());
            });
            if(arr.length <=0 ){
                layer.msg("至少选择一个标签", {icon: 3,time: 2000});
                return false;
            }
            if(arr.length>3){
                layer.msg("最大选择3个标签", {icon: 3,time: 2000});
                return false;
            }
            data.field.topicCodeList = arr.join(",");
            var jsonData = data.field;
            parent._ajax_b("/post/publish",jsonData,"保存成功",null);
            return false;
        });
    });

    function findByPhone() {
        var phone = $("#phone").val();
        var data = {
            phone:phone
        }
        jQuery.post('/trans/findByPhone/json',data,function(data){
            if(data.code == "00"){
                $("#gradeName").html(data.data.user.vodGradeName);
                var vipEndTime = data.data.user.videoVipEndTime;
                if(vipEndTime == "" || vipEndTime == undefined || vipEndTime == null){
                    $("#vipEndTimeStr").html("--");
                }else{
                    $("#vipEndTimeStr").html(createTime(vipEndTime));
                }

            }else{
                layer.msg(data.msg, {icon:3,time:2000});
                $("#gradeName").html("");
                $("#vipEndTimeStr").html("");
            }
        });
        return;
    }
</script>