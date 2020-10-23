<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/comm_css_js.jsp"/>
<form class="layui-form" action="">
    <input name="id" id="id" type="hidden" value="${user.id}">
    <input name="userId" id="userId" type="hidden" value="${user.userId}">
    <div class="layui-tab">
        <ul class="layui-tab-title">
            <li class="layui-this">会员基本信息</li>
            <li>会员视频</li>
            <li>银行卡信息</li>
            <li>商品管理</li>
            <li>订单管理</li>
        </ul>
        <div class="layui-tab-content">
            <!--基本信息-->
            <div class="layui-tab-item">基本信息</div>

            <!--会员视频-->
            <div class="layui-tab-item">
                <div class="layui-tab-item layui-show">
                  <table class="layui-hide" id="userVideoList" lay-filter="userVideoList"></table>
                </div>
            </div>
            <!--银行卡信息-->
            <div class="layui-tab-item">
                <c:if test="${empty bankCard}">
                    暂未绑定银行卡
                </c:if>
                <c:if test="${!empty bankCard}">
                    已绑定银行卡
                </c:if>
                <div class="layui-card">
                    <div class="layui-card-body">
                        <form class="layui-form" action="">
                            <div class="layui-form-item">
                                <label class="layui-form-label">开户人名 <span style="color:red;">*</span></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="receiver" id="receiver" value="${bankCard.receiver}"  lay-verify="required"   autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">开户行 <span style="color:red;">*</span></label>
                                <div class="layui-input-inline">
                                    <select name="bankCode" lay-verify="required">
                                        <c:forEach items="${bankCardMap}" var="bank">
                                            <option value="<c:if test="${bank.key == bankCard.bankCode}">selected</c:if>">${bank.value}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">卡号 <span style="color:red;">*</span></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="cardNo" id="cardNo" value="${bankCard.cardNo}"  lay-verify="required"   autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">支行地址 <span style="color:red;">*</span></label>
                                <div class="layui-input-inline">
                                    <input type="text" name="subBranch" id="subBranch" value="${bankCard.subBranch}"  lay-verify="required"   autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-input-block">
                                    <button class="layui-btn layui-btn-normal" lay-submit lay-filter="bandBankCard">提交绑定</button>
                                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="layui-tab-item">内容4</div>
            <div class="layui-tab-item">内容5</div>
        </div>
    </div>
</form>

<script>
    layui.use(['table','form','element'], function(){
        var form = layui.form;
        var table = layui.table;

        table.render({
            elem: '#userVideoList'
            ,url:'/video/list/page'
            ,title: '视频列表'
            ,limit:10
            ,loading:true
            ,where:{
                userId:$("#userId").val()
            }
            ,size:'sm'
            ,page: {
                limits:[10,20, 30, 40, 50,100,200,500,1000],
                theme: '#1E9FFF'
            } //开启分页
            ,cols: [[
                {field:'vodType', title: '标签类型',width: 100,sort: true, templet: function(d) {
                        if(d.vodType == "ordinary"){
                            return '普通视频';
                        }else if(d.vodType == "match"){
                            return '赛事视频';
                        }
                    }}
                ,{field:'vodName', title: '视频名称',width:350}
                ,{field:'vodImgUrl', title: '视频封面',templet:function (d) {
                        return '<img src="'+d.vodImgUrl+'" width="100px" height="40px"/>';
                    },width:140}
                ,{field:'vodLabel', title: '视频标签',width:300,templet:function (d) {
                        return getLabelNameList(d.labelNameList);
                    }}
                /*,{field:'vodLabel', title: '视频标签',templet:function (d) {
                            return '<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="viewLabel">查看标签</a>';
                        }}*/
                ,{field:'likeNum', title: '点赞量',sort: true,width: 100}
                ,{field:'commentNum', title: '评论量',sort: true,width: 100}
                ,{field:'status', title: '审核状态',sort: true,width: 100,templet :function (row){
                        if(row.status = 0 || row.status == null || row.status == ''){
                            return "未审核";
                        }else if(row.status == 1){
                            return "审核通过";
                        }else if(row.status == 2){
                            return "审核未通过";
                        }
                    }}
                ,{field:'createTime', title: '创建时间', templet :function (row){
                        return createTime(row.createTime);
                    },width: 160 }

            ]]
        });
        //监听提交
        form.on('submit(labelSave)', function(data){
            //var jsonData = JSON.stringify(data.field);
            var jsonData = data.field;
            parent._ajax_b("/videoLabel/save",jsonData,"保存成功",null);
            return false;
        });

        //获取视频标签名称
        var getLabelNameList = function (labelNameList) {
            if(labelNameList == null || labelNameList == "" || labelNameList == undefined){
                return "";
            }
            var labelHtml = "";
            var strs = labelNameList.split(","); //字符分割
            var btnSty = ["layui-bg-orange","layui-bg-green","layui-bg-cyan","layui-bg-blue","layui-bg-black","layui-bg-gray"]
            for (var i=0; i<strs.length; i++ ){
                labelHtml += '<span class="layui-badge '+btnSty[i]+'">'+strs[i]+'</span>&nbsp;&nbsp;&nbsp;';
            }
            return labelHtml;
        }
    });
</script>