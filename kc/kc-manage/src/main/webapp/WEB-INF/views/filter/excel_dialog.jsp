<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/comm_css_js.jsp"/>
<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
    </div>
    <input name="id" type="hidden" value="${filterRule.id}">
    <div class="layui-form-item">
        <label class="layui-form-label">规则模板 <span style="color:red;">*</span></label>
        <div class="layui-upload-drag" id="ruleFlag">
            <i class="layui-icon"></i>
            <p>点击上传，或将文件拖拽到此处(excel文件)</p>
            <div class="layui-hide" id="importRuleExcelView">
                <hr>
                <img src="" alt="上传成功后渲染" style="max-width: 196px">
            </div>
        </div>

    </div>
    <div class="layui-form-item progress-upload" style="margin-left: 110px;">
        <div class="layui-input-inline">
            <div class="layui-progress layui-progress-big" lay-filter="demo" lay-showPercent="true">
                <div class="layui-progress-bar layui-bg-red" lay-percent="0%"></div>
            </div>
        </div>
    </div>
</form>

<script>
    layui.use(['form','upload'], function(){
        var form = layui.form;
       var upload = layui.upload;

        //监听提交
        form.on('submit(filterRuleSave)', function(data){
            //var jsonData = JSON.stringify(data.field);
            var jsonData = data.field;
            parent._ajax_b("/filterRule/save",jsonData,"保存成功",null);
            return false;
        });
        //选完文件后不自动上传
       /* upload.render({
            elem: '#ruleFlag'
            ,url: '/filterRule/importRuleExcel' //改成您自己的上传接口
            ,auto: false
            ,accept: 'file' //普通文件
            //,multiple: true
            //,exts: 'xlsx|xls' //只允许上传excel文件
            ,bindAction: '#importRuleExcel'
            ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                layer.load(); //上传loading
            }
            ,progress: function(n, elem){
                layui.use('element', function(){
                    var element = layui.element;
                    var $ = layui.jquery;
                    var percent = n + '%' //获取进度百分比
                    //进行动态绑
                    layui.element.init();
                    //$('#demo1').attr('lay-percent',percent);
                    element.progress('demo', percent);
                });

            }
            ,done: function(data){
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

            }
            ,error:function (res) {
                layer.msg("网络异常,导入失败..请稍后重试", {icon: 2,time: 2000},function () {
                    layer.closeAll('loading'); //关闭loading
                    layer.closeAll()
                });
            }
        });*/
    });

    layui.use('element', function(){
        var element = layui.element;
        var $ = layui.jquery;
        layui.use('upload', function(){
            var upload = layui.upload;
            var thisProgress;
            //控制器，可以对回调的次数进行控制
            var come = 0;

            //执行实例
            var uploadInst = upload.render({
                elem: '#ruleFlag' //绑定元素
                //,url: '/filterRule/importRuleExcel' //改成您自己的上传接口
                ,url:'/filterRule/importRuleExcel'
                ,accept: 'file' //允许上传的文件类型
                ,auto:true
                ,bindAction:'#importRuleExcel'
                // ,multiple:'true'
                ,drag:true
                ,done: function(data){
                    debugger;
                    if(data.code == "00"){
                        layer.msg(data.msg, {
                            icon: 1
                            ,time: 2000
                        }, function(){
                            parent.location.reload();
                            parent.layer.closeAll();
                        });
                }else{
                    layer.msg(data.msg, {icon:3,time:2000});
                }
                }
                ,error: function(){
                    layer.msg("网络异常,导入失败..请稍后重试", {icon: 2,time: 2000},function () {
                        layer.closeAll('loading'); //关闭loading
                        layer.closeAll()
                    });
                }
                ,progress: function(n, elem){
                    //第一次执行进度回调函数的时候，对进度条进行初始化
                    if(come == 0){
                        layui.element.init();
                        come ++;
                    }
                    //之后直接执行下面代码，
                    var percent = n + '%' //获取进度百分比
                    // $('#demo1').attr('lay-percent',percent);
                    element.progress('demo', percent);
                }
            });
        });
    });
</script>