<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../../static/theme/css/font.css">
<link rel="stylesheet" href="../../static/theme/css/xadmin.css">
<link rel="stylesheet" href="../../static/theme/css/theme2.css">
<jsp:include page="common/comm_css_js.jsp"/>
<script src="/static/js/echarts.min.js" charset="utf-8"></script>
<script src="/static/js/echarts.data.js" charset="utf-8"></script>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body ">
                    <blockquote class="layui-elem-quote1">欢迎管理员：
                        <span class="x-red">${user.userName}</span>！当前时间 : <span id="curTime"></span>
                    </blockquote>
                </div>
            </div>
        </div>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">今日数据 (30分钟更新一次)</div>
                <div class="layui-card-body ">
                    <ul class="layui-row layui-col-space10 layui-this x-admin-carousel x-admin-backlog">
                        <li class="layui-col-md2 layui-col-xs6">
                            <a href="javascript:;" class="x-admin-backlog-body">
                                <h3><i class="layui-icon">&#xe66f;</i> 注册</h3>
                                <p>
                                    <cite style="color: #1E9FFF">${statisticsVo.todayReg}</cite>
                                </p>
                            </a>
                        </li>
                        <li class="layui-col-md2 layui-col-xs6">
                            <a href="javascript:;" class="x-admin-backlog-body">
                                <h3><i class="layui-icon">&#xe65e;</i> 充值</h3>
                                <p>
                                    <cite style="color: #1E9FFF">${statisticsVo.todayRecharge}</cite>
                                </p>
                            </a>
                        </li>
                        <li class="layui-col-md2 layui-col-xs6">
                            <a href="javascript:;" class="x-admin-backlog-body">
                                <h3><i class="layui-icon">&#xe6ed;</i> 开通视频会员数</h3>
                                <p>
                                    <cite style="color: #1E9FFF">34</cite>
                                </p>
                            </a>
                        </li>
                        <li class="layui-col-md2 layui-col-xs6">
                            <a href="javascript:;" class="x-admin-backlog-body">
                                <h3><i class="layui-icon">&#58890;</i> 帖子数</h3>
                                <p>
                                    <cite style="color: #1E9FFF">${statisticsVo.todayPostNum}</cite></p>
                            </a>
                        </li>
                        <li class="layui-col-md2 layui-col-xs6">
                            <a href="javascript:;" class="x-admin-backlog-body">
                                <h3>文章数</h3>
                                <p>
                                    <cite style="color: #1E9FFF">67</cite></p>
                            </a>
                        </li>
                        <li class="layui-col-md2 layui-col-xs6 ">
                            <a href="javascript:;" class="x-admin-backlog-body">
                                <h3>文章数</h3>
                                <p>
                                    <cite style="color: #1E9FFF">6766</cite></p>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md3">
            <div class="layui-card">
                <div class="layui-card-header">下载
                    <span class="layui-badge layui-bg-cyan layuiadmin-badge">月</span></div>
                <div class="layui-card-body  ">
                    <p class="layuiadmin-big-font">33,555</p>
                    <p>新下载
                        <span class="layuiadmin-span-color">10%
                                    <i class="layui-inline layui-icon layui-icon-face-smile-b"></i></span>
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md3">
            <div class="layui-card">
                <div class="layui-card-header">下载
                    <span class="layui-badge layui-bg-cyan layuiadmin-badge">月</span></div>
                <div class="layui-card-body ">
                    <p class="layuiadmin-big-font">33,555</p>
                    <p>新下载
                        <span class="layuiadmin-span-color">10%
                                    <i class="layui-inline layui-icon layui-icon-face-smile-b"></i></span>
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md3">
            <div class="layui-card">
                <div class="layui-card-header">下载
                    <span class="layui-badge layui-bg-cyan layuiadmin-badge">月</span></div>
                <div class="layui-card-body ">
                    <p class="layuiadmin-big-font">33,555</p>
                    <p>新下载
                        <span class="layuiadmin-span-color">10%
                                    <i class="layui-inline layui-icon layui-icon-face-smile-b"></i></span>
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md3">
            <div class="layui-card">
                <div class="layui-card-header">下载
                    <span class="layui-badge layui-bg-cyan layuiadmin-badge">月</span></div>
                <div class="layui-card-body ">
                    <p class="layuiadmin-big-font">33,555</p>
                    <p>新下载
                        <span class="layuiadmin-span-color">10%
                                    <i class="layui-inline layui-icon layui-icon-face-smile-b"></i></span>
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">统计信息</div>
                <div class="layui-card-body ">
                    <form class="layui-form" action="">
                        <div class="layui-tab">
                            <ul class="layui-tab-title">
                                <li class="layui-this">注册信息</li>
                                <li>充值交易</li>
                                <li>视频会员</li>
                                <li>商品管理</li>
                                <li>订单管理</li>
                            </ul>
                            <div class="layui-tab-content">
                                <!--注册信息统计-->
                                <div class="layui-tab-item layui-show">
                                    <div id="chartsReg" style="width: 90%;height:400px;"></div>
                                </div>

                                <!--会员视频-->
                                <div class="layui-tab-item">

                                </div>
                                <div class="layui-tab-item">内容3</div>
                                <div class="layui-tab-item">内容4</div>
                                <div class="layui-tab-item">内容5</div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>
</div>

<script>
    layui.use(['table','form','element'], function(){
        var form = layui.form;
        var table = layui.table;
    });

    var $timeWrapper = $('#curTime');
    setInterval(function() {
        $timeWrapper.html(getCurTime());
    }, 1000);
    function getCurTime() {
        var oDate = new Date();
        var weekArr = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
        var y = oDate.getFullYear(),
            m = oDate.getMonth() + 1,
            d = oDate.getDate(),
            hour = oDate.getHours(),
            min = oDate.getMinutes(),
            sec = oDate.getSeconds(),
            weekIndex = oDate.getDay(),
            week = weekArr[weekIndex];
        var curTime = y + '-' + preZero(m, 2) + '-' + preZero(d, 2) + ' ' + week + ' ' + preZero(hour, 2) + ':' + preZero(min, 2) + ':' + preZero(sec, 2);
        return curTime;
    }
    //位数不够前补零   num:被操作数 n: 指定位数
    function preZero(num, n) {
        return (Array(n).join(0) + num).slice(-n);
    }
</script>

<script type="text/javascript">

</script>