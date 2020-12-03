<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>${busConfig.webConfig.backTitle.val}</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="stylesheet" href="../../static/theme/css/font.css">
    <link rel="stylesheet" href="../../static/theme/css/xadmin.css">
    <link rel="stylesheet" href="../../static/theme/css/theme2.css">
    <%--<script src="../../static/theme/lib/layui/layui.js" charset="utf-8"></script>--%>
    <script src="../../static/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="../../static/theme/js/xadmin.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
        // 是否开启刷新记忆tab功能
        // var is_remember = false;
    </script>
</head>
<body class="index">
<!-- 顶部开始 -->
<div class="container">
    <div class="logo">
        <a href="./index.html">${busConfig.webConfig.backTitle.val}</a></div>
    <div class="left_open">
        <a><i title="展开左侧栏" class="iconfont">&#xe699;</i></a>
    </div>
    <ul class="layui-nav right" lay-filter="">
        <li class="layui-nav-item">
            <a href="javascript:;">${user.userName}</a>
            <dl class="layui-nav-child">
                <!-- 二级菜单 -->
                <%--<dd>
                    <a onclick="xadmin.open('个人信息','http://www.baidu.com')">个人信息</a></dd>
                <dd>
                    <a onclick="xadmin.open('切换帐号','http://www.baidu.com')">切换帐号</a></dd>--%>
                <dd>
                    <a href="/logout">退出</a></dd>
            </dl>
        </li>

    </ul>
</div>
<!-- 顶部结束 -->
<!-- 中部开始 -->
<!-- 左侧菜单开始 -->
<div class="left-nav">
    <div id="side-nav">
        <ul id="nav">
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="系统设置">&#59054;</i>
                    <cite>系统设置</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('网站设置','/webConfig/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>网站设置</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('开关设置','/webSwitch/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>开关设置</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('域名配置','/domain/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>域名配置</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('基础数据设置','/busconfig/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>基础数据设置</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('缓存配置','/cache/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>缓存配置</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('黑/白名单配置','/filterRule/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>黑/白名单配置</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('域名配置','/domain/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>域名配置</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="会员管理">&#xe6b8;</i>
                    <cite>会员管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('会员数据','/user/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>会员数据</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="交易管理">&#xe723;</i>
                    <cite>交易管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('充值交易','/trans/rechargeRecord')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>充值交易</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('提现交易','/trans/cashRecord')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>提现交易</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('交易综合查询','/trans/allRecord')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>交易综合查询</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="视频管理">&#59424;</i>
                    <cite>视频管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('视频数据','/video/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>视频数据</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('视频标签','/videoLabel/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>视频标签</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('VIP等级配置','/vipGrade/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>VIP等级配置</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="社区管理">&#xe6ce;</i>
                    <cite>社区管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('话题管理','/topic/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>话题管理</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('帖子管理','/post/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>帖子管理</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('违禁词管理','/forbidWord/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>违禁词管理</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="营销管理">&#xe6ce;</i>
                    <cite>营销管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('优惠券','/coupon/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>优惠券</cite></a>
                    </li>

                    <li>
                        <a onclick="xadmin.add_tab('广告主','/adv/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>广告主</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="快捷操作">&#xe6b4;</i>
                    <cite>快捷操作</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('人工操作','/fast/manualOperation')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>人工操作</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('注册IP查询','/ip/list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>注册IP查询</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="管理员设置">&#xe726;</i>
                    <cite>管理员设置</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('管理员列表','admin-list.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>管理员列表</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('角色管理','admin-role.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>角色管理</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('权限分类','admin-cate.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>权限分类</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('权限管理','admin-rule.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>权限管理</cite></a>
                    </li>
                </ul>
            </li>

        </ul>
    </div>
</div>
<!-- <div class="x-slide_left"></div> -->
<!-- 左侧菜单结束 -->
<!-- 右侧主体开始 -->
<div class="page-content">
    <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
        <ul class="layui-tab-title">
            <li class="home">
                <i class="layui-icon">&#xe68e;</i>我的桌面</li></ul>
        <div class="layui-unselect layui-form-select layui-form-selected" id="tab_right">
            <dl>
                <dd data-type="this">关闭当前</dd>
                <dd data-type="other">关闭其它</dd>
                <dd data-type="all">关闭全部</dd></dl>
        </div>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe src='/admin/welcome' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
            </div>
        </div>
        <div id="tab_show"></div>
    </div>
</div>
<div class="page-content-bg"></div>
<style id="theme_style"></style>
<!-- 右侧主体结束 -->
<!-- 中部结束 -->
</body>

</html>