function createTime(v){
    if(v == "" || v == null || v == undefined){
        return "-";
    }
    var date = new Date(v*1000);
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    m = m<10?'0'+m:m;
    var d = date.getDate();
    d = d<10?("0"+d):d;
    var h = date.getHours();
    h = h<10?("0"+h):h;
    var M = date.getMinutes();
    M = M<10?("0"+M):M;
    var S = date.getSeconds();
    S = S<10?("0"+S):S;
    var str = y+"-"+m+"-"+d+" "+h+":"+M +":"+S;
    return str;
}

var _ajax_b = function(url,data,msg,index){
    jQuery.post(url,data,function(data){
        if(data.code == "00"){
            layer.msg(msg, {
                icon: 1
                ,time: 3000
            }, function(){
                location.reload();
                layer.closeAll();
            });
        }else{
            layer.msg(data.msg, {icon:3,time:3000});
        }
    });
}

function openImgView(imgUrl) {
    //图片元素
    var img = '<img src="' + imgUrl + '" style="max-width: 300px;max-height: 300px;">';
    layer.open({
        type: 1,// 页面层
        title: false,//关闭标题
        closeBtn: 0,//不显示关闭按钮
        shadeClose: true,//点击遮罩层关闭弹窗
        content: img //弹窗显示内容
    });
}


function openVideoView(videoPlayUrl) {
    //视频元素
    var videoPlayAddress = '<video width="300px" height="300px"  controls="controls" autobuffer="autobuffer"  autoplay="autoplay" loop="loop"><source src="'+videoPlayUrl+'" type="video/mp4"></source></video>';
    layer.open({
        type: 1,// 页面层
        title: false,//关闭标题
        closeBtn: 0,//不显示关闭按钮
        shadeClose: true,//点击遮罩层关闭弹窗
        content: videoPlayAddress //弹窗显示内容
    });
}