<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
%>
<link rel="stylesheet" href="/static/css/common.css?t=201800202-1" media="all">
<link rel="stylesheet" href="/static/css/layui.css"  media="all">
<body>
<ul class="yyui_menu1">
    <li><a href="#">视频管理</a>
        <ul class="one">
            <li><a href="/video/list">视频数据</a></li>
            <li><a href="/videoLabel/list">视频标签</a></li>
            <li><a href="/vipGrade/list">VIP等级配置</a></li>
        </ul>
    </li>
    <li><a href="#">会员管理</a>
        <ul class="one">
            <li><a href="/user/list">会员数据</a></li>
        </ul>
    </li>
    <%--<li><a href="#">zij管理</a>
        <ul class="one">
            <li><a href="/trans/rechargeRecord">充值</a></li>
            <li><a href="/trans/cashRecord">提现</a></li>
            <li><a href="/trans/cashRecord">rg存提</a></li>
        </ul>
    </li>--%>
    <li><a href="#">社区管理</a>
        <ul class="one">
            <li><a href="/topic/list">话题管理</a></li>
            <li>
                <a href="#">帖子管理</a>
                <ul class="one">
                    <li><a href="/post/list">待审核</a></li>
                    <li><a href="/post/list">已审核</a></li>
                </ul>
            </li>
        </ul>
    </li>
    <li><a href="#">系统设置</a>
        <ul class="one">
            <li><a href="/busconfig/list">基础数据设置</a></li>
            <li><a href="#">菜单二</a></li>
            <li><a href="#">菜单三</a></li>
        </ul>
    </li>
    <li style="float: right"><a href="#" >张三</a>
        <ul class="one">
            <li><a href="/logout">退出登录</a></li>
        </ul>
    </li>
</ul>
<script src="/static/jquery-1.9.1.min.js" charset="utf-8"></script>
<script src="/static/layui.js" charset="utf-8"></script>
<script src="/static/js/common.js" charset="utf-8"></script>
<script type="text/javascript">
    yyui_menu('.yyui_menu1');

    function yyui_menu(ulclass){
        $(document).ready(function(){
            $(ulclass+' li').hover(function(){
                $(this).children("ul").show(); //mouseover
            },function(){
                $(this).children("ul").hide(); //mouseout
            });
        });
    }
</script>

</body>