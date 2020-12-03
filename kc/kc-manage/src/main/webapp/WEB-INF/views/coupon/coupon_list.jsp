<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>优惠券数据</title>
    <style>
        .layui-table-main .layui-table-cell{
            height:35px!important;
        }
        .layui-table-fixed-r > .layui-table-body .layui-table-cell{
            height:35px!important;
        }
    </style>
</head>
<body>
<jsp:include page="../common/comm_css_js.jsp"/>

<div class="layui-form" action="" lay-filter="coupon-form-element" style="margin-top: 10px;">
    <div class="layui-form-item">
        <div class="layui-inline" >
            <label class="layui-form-label">优惠券代码：</label>
            <div class="layui-input-inline layui-input-search">
                <input class="layui-input" id="couponCode" placeholder="模糊查询" name="couponCode"/>
            </div>
        </div>
        <div class="layui-inline" >
            <label class="layui-form-label">状态：</label>
            <div class="layui-input-inline layui-input-search">
                <select name="status" id="status" lay-search="">
                    <option value="">全部</option>
                    <c:forEach items="${statusEnums}" var="status">
                        <option value="${status.key}" <c:if test="${status.key == domain.status}">selected</c:if> > ${status.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-inline" >
            <label class="layui-form-label">优惠券类型：</label>
            <div class="layui-input-inline layui-input-search">
                <select name="status" id="couponType" lay-search="">
                    <option value="">全部</option>
                    <c:forEach items="${couponTypeEnumsMap}" var="couponType">
                        <option value="${couponType.key}" <c:if test="${couponType.key == domain.status}">selected</c:if> > ${status.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <button class="layui-btn layui-btn-normal"  lay-filter="coupon-form-btn-sub" lay-submit="">搜索</button>
            <button class="layui-btn layui-btn-normal"  onclick="addCoupon()">添加优惠券</button>
        </div>
    </div>
</div>

<script type="text/html" id="barCoupon">
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<table class="layui-hide" id="couponList" lay-filter="couponList"></table>

<script type="text/javascript">
    layui.use(['table','form'], function(){
        layui.form.render(null, 'coupon-form-element');
        var table = layui.table;
        form = layui.form,
            _ajax_b = function(url,data,msg,index){
                jQuery.post('${rc.contextPath}'+url,data,function(data){
                    if(data.code == "00"){
                        layer.msg(msg, {
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
            },
            table.render({
                elem: '#couponList'
                ,url:'/coupon/list/page'
                ,title: '优惠券列表'
                ,limit:15
                ,loading:true
                ,size:'sm'
                ,page: {
                    limits:[15, 30, 40, 50,100,200,500,1000],
                    theme: '#1E9FFF'
                } //开启分页
                ,cols: [[
                    {field:'couponCode', title: '优惠券代码',width:90 }
                    ,{field:'couponName', title: '名称',width:120}
                    ,{field:'couponTypeDesc', title: '优惠券类型',width:90}
                    ,{field:'couponAmount', title: '面值金额',width:80}
                    ,{field:'fullAmount', title: '满减金额',width:100}
                    ,{field:'provideNum', title: '发放数量',width:80}
                    ,{field:'receiveNum', title: '领取数量/人',width:100}
                    ,{field:'statusDesc', title: '状态'}
                    ,{field:'sellStatus', title: '销售状态',width:90}
                    ,{field:'validStatus', title: '有效期状态',width:90}
                    ,{field:'sellStartTime', title: '销售时间', templet :function (row){
                            return "开始："+createTime(row.sellStartTime)+"<br/> 结束："+createTime(row.sellEndTime);
                        },width: 180 }
                    ,{field:'validStartTime', title: '有效时间', templet :function (row){
                            return "开始："+createTime(row.validStartTime)+"<br/> 结束："+createTime(row.validEndTime);
                        },width: 180 }
                    ,{fixed: 'right', title:'操作', toolbar: '#barCoupon', width:180}

                ]]
            });


        form.on('submit(coupon-form-btn-sub)', function(data) {
            table.reload('couponList', {
                where: data.field,
                method:'POST',
                pageNum: {curr:1},
            });
            return false;
        });
        //监听行工具事件
        table.on('tool(couponList)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                var id = data.id;
                debugger;
                editCoupon(id);

            }
            if(obj.event === 'del'){
                layer.confirm('确定删除该条数据吗', {
                    btn: ['确定', '取消',] //可以无限个按钮
                }, function(index, layero){
                    var id = data.id;
                    delCoupon(id);
                });
            }
        });

        var delCoupon = function (id) {
            var data = {"id":id}
            _ajax_b("/coupon/del",data,"删除成功",null);
        }
        var editCoupon = function (id) {
            layer.open({
                type: 2,
                area: ['65%','80%'],
                content: '/coupon/edit?id='+id //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
            });
        }


    });

    var addCoupon = function () {
        layer.open({
            title:'添加优惠券',
            type: 2,
            area: ['65%','80%'],
            content: '/coupon/edit' //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
    }
</script>
</body>
</html>
