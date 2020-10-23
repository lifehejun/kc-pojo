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
                ,time: 2000
            }, function(){
                location.reload();
                layer.closeAll();
            });
        }else{
            layer.msg(data.msg, {icon:3,time:1000});
        }
    });
}