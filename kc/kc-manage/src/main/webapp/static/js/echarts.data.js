
//初始化

$(function () {
    getChartsReg();

});


var charts_reg_url = "/charts/getChartsReg";

var sendPost = function(url,data){
    var result = null;
    $.ajaxSettings.async = false;
    jQuery.post(url,data,function(data){
        if(data.code == "00"){
            result =  data.data;
        }else{
            console.error("获取统计信息数据异常:requestUrl:"+url);
        }
    });
    return result;
}


//获取注册
function getChartsReg() {

    var result = sendPost(charts_reg_url,null);

    var chartsReg = echarts.init(document.getElementById('chartsReg'));
    // 指定图表的配置项和数据
    option = {
        title: {
            text: '当月注册信息统计'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['会员注册','会员注册']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: result.days
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '会员注册数量',
                type: 'line',
                stack: '总量',
                data: result.data
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    chartsReg.setOption(option);

    //regArr.day = ['01-01', '01-02', '01-03', '01-04', '01-05', '01-06', '01-07','01-08', '01-09', '01-10', '01-11', '01-12', '01-13', '01-14','01-15', '01-16', '01-17', '01-18', '01-19', '01-20', '01-21','01-22', '01-23', '01-24', '01-25', '01-26', '01-27', '01-28', '01-29', '01-30'];
    //regArr.data = [150, 232, 201, 154, 190, 330, 410,150, 232, 201, 154, 190, 330, 410,150, 232, 201, 154, 190, 330, 410,150, 232, 201, 154, 190, 330, 410,33,34];
}

