<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>提现记录</title>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div class="layui-form" action="" lay-filter="cash-form-element" style="margin-top: 10px;">
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
            <button class="layui-btn layui-btn-normal"  lay-filter="cash-query-btn" lay-submit="">搜索</button>
        </div>

        <div class="layui-inline">
            <span style="color: red">备注：</span>
        </div>
    </div>
</div>
<table class="layui-hide" id="cashRecordList"></table>
<script type="text/javascript">
    layui.use(['table','form'], function(){
        layui.form.render(null, 'cash-form-element');
        var table = layui.table;
        form = layui.form,
            _ajax_b = function(url,data,msg,index){
                jQuery.post('${rc.contextPath}'+url,data,function(data){
                    if(data.code == "200"){
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
                elem: '#cashRecordList'
                ,url:'/trans/cashRecord/json'
                ,title: '体现记录列表'
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
                    {field:'userName', title: '用户名', width: 120}
                    ,{field:'phone', title: '手机号', width: 130}
                    ,{field:'transNo', title: '交易号', width: 130}
                    ,{field:'money', title: '提现金额', width: 120}
                    ,{field:'transTypeDesc', title: '交易类型', width: 120,sort: true}
                    ,{field:'beforeBalance', title: '交易前余额', width: 120}
                    ,{field:'afterBalance', title: '交易后余额', width: 120}
                    ,{field:'remark', title: '备注', width: 140}
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

                ]]
            });

        form.on('submit(cash-query-btn)', function(data) {
            table.reload('cashRecordList', {
                where: data.field,
                pageNum: {curr:1},
            });
            return false;
        });


    });
</script>
</body>
</html>
