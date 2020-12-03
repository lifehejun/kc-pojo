<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>充值记录</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div class="layui-form" action="" lay-filter="recharge-edit-form-element" style="margin-top: 10px;">
    <div class="layui-form-item">
        <div class="layui-inline" >
            <label class="layui-form-label">手机号：</label>
            <div class="layui-input-inline layui-input-search">
                <input class="layui-input" id="phone" name="phone"/>
            </div>
        </div>
        <div class="layui-inline" >
            <label class="layui-form-label">交易单号：</label>
            <div class="layui-input-inline layui-input-search">
                <input class="layui-input" id="transNo" name="transNo"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">交易状态：</label>
            <div class="layui-input-inline layui-input-search">
                <select name="status" id="status" lay-search="">
                    <option value="">全部</option>
                    <option value="0">待交易</option>
                    <option value="1">交易成功</option>
                    <option value="2">交易失败</option>

                </select>
            </div>
        </div>
        <div class="layui-inline">
            <button class="layui-btn layui-btn-normal"  lay-filter="recharge-query-btn" lay-submit="">搜索</button>
        </div>

        <div class="layui-inline">
            <span style="color: red">备注：</span>
        </div>
    </div>
</div>
<script type="text/html" id="barRecharge">
    {{#  if(d.status == 0){ }}
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="confirm" data-trans-id="{{transNo}}">确认到账</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="return" data-trans-id="{{transNo}}">充值失败</a>
    {{#  } }}
</script>

<table class="layui-hide" id="rechargeRecordList" lay-filter="rechargeRecordList"></table>
<script type="text/javascript">
    layui.use(['table','form'], function(){
        layui.form.render(null, 'recharge-edit-form-element');
        var table = layui.table;
        form = layui.form,
            _ajax_b = function(url,data,msg,index){
                jQuery.post('${rc.contextPath}'+url,data,function(data){
                    if(data.code == "00"){
                        layer.msg(msg, {
                            icon: 1
                            ,time: 1000
                        }, function(){
                            location.reload();
                            layer.closeAll();
                        });
                    }else{
                        layer.msg(data.msg, {icon:3,time:1000});
                    }
                });
            },
            table.render({
                elem: '#rechargeRecordList'
                ,url:'/trans/rechargeRecord/json'
                ,title: '充值记录列表'
                ,limit:20
                ,loading:true
                ,size:'sm'
                ,where:{
                    status : $("#status").val(),
                    userName : $("#userName").val(),
                    batchNo : $("#batchNo").val()
                }
                ,page: {
                    limits:[20, 30, 40, 50,100,200,500,1000]
                } //开启分页
                ,cols: [[
                     {field:'phone', title: '手机号', width: 120}
                    ,{field:'userName', title: '用户名', width: 120}
                    ,{field:'transNo', title: '交易号', width: 130,sort: true}
                    ,{field:'money', title: '充值金额', width: 120,sort: true}
                    ,{field:'transTypeDesc', title: '交易类型', width: 120,sort: true}
                    ,{field:'beforeMoney', title: '交易前余额', width: 120,sort: true}
                    ,{field:'afterMoney', title: '交易后余额', width: 120,sort: true}
                    ,{field:'remark', title: '备注', width: 140,sort: true}
                    ,{field:'status', title: '交易状态',width: 100,sort: true, templet: function(d) {
                            if(d.status == 0){
                                return '待交易';
                            }else if(d.status == 1){
                                return '交易成功';
                            }else{
                                return '交易失败';
                            }
                        }},
                    ,{field:'createTime', title: '创建时间', width: 150, sort: true, templet :function (row){
                            return createTime(row.createTime);
                        } }
                    ,{field:'transTime', title: '交易时间', width: 150, sort: true, templet :function (row){
                            return createTime(row.transTime);
                        } }
                    ,{fixed: 'right', title:'操作', toolbar: '#barRecharge', width:180}

                ]]
            });

        form.on('submit(recharge-query-btn)', function(data) {
            table.reload('rechargeRecordList', {
                where: data.field,
                method:'POST',
                pageNum: {curr:1},
            });
            return false;
        });


        //监听行工具事件
        table.on('tool(rechargeRecordList)', function(obj){
            var data = obj.data;
            //console.log(obj)
            if(obj.event === 'confirm'){

                layer.confirm('确认该交易处理到账吗', {
                    btn: ['确定', '取消',] //可以无限个按钮
                }, function(index, layero){
                    var transNo = obj.data.transNo;
                    var userId = obj.data.userId;
                    confirmRecharge(transNo,userId);
                });


            } else if(obj.event === 'return'){
                var transNo = obj.data.transNo;
                var userId = obj.data.userId;
                returnRecharge(transNo,userId);
            }
        });


        var confirmRecharge = function (transNo,userId) {

            var url = "/trans/recharge/confirm";
            var data = {
                transNo : transNo,
                userId : userId
            }
            _ajax_b(url,data,"充值确认操作成功");

        }

        var returnRecharge = function (transNo,userId) {

            var url = "/trans/recharge/return";
            var data = {
                transNo : transNo,
                userId : userId
            }
            _ajax_b(url,data,"充值失败操作成功");

        }


    });
</script>
</body>
</html>
