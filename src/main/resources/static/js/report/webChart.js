

function  createRadarChart(total,radarData) {
    var radar_chart = echarts.init(document.getElementById('radar_chart'));
    radar_option = {
        title: {
            text: total.toFixed(2),
            x: "center",
            y: "center",
            textStyle: {
                fontSize: 30,
                color: "white",
                fontWeight: '800'
            }
        },
        backgroundColor: 'rgba(255,255,255,.7)',
        radar: {
            splitNumber: 3,
            radius: "55%",
            //字体颜色
            name: {
                fontSize: 30,
                textStyle: { color: '#030303' }
            },
            //斜角分割线颜色
            axisLine: {
                lineStyle: {
                    color: 'rgba(27,181,226,.2)'
                }
            },
            //雷达图中的背景色透明度
            splitArea: {
                areaStyle: {
                    opacity: 0
                }
            },
            //最外层边框颜色
            splitLine: {
                lineStyle: {
                    width: 1,
                    color: 'rgba(135,135,135,.3)'
                }
            },
            indicator: [
                { text: '消费体量', max: 5 },
                { text: '客群结构', max: 5 },
                { text: '竞品分析', max: 5 },
                { text: '消费便利性', max: 5 },
                { text: '开店成本', max: 5 }
            ]
        },

        series: [
            {
                type: 'radar',
                symbolSize: 0,
                //填充区域颜色
                areaStyle: {
                    normal: {
                        color: 'rgba(44,217,197,.5)'
                    }
                },
                data: [{
                    value: radarData,
                    //数据边框样式
                    lineStyle: {
                        normal: {
                            width: 1,
                            color: 'rgba(44,217,197,1)'
                        }
                    }
                }]
            }
        ]
    }
    radar_chart.setOption(radar_option);
}


function createCircle(map,cpoint) {

    var circle = new AMap.Circle({
        center: new AMap.LngLat(cpoint[0], cpoint[1]), // 圆心位置
        radius: 1500,  //半径
        strokeColor: "#a4d5f0",  //线颜色
        strokeOpacity: 1,  //线透明度
        strokeWeight: 2,  //线粗细度
        fillColor: "#a4d5f0",  //填充颜色
        fillOpacity: 0.3,//填充透明度
        autoFitView: true // 是否自动调整地图视野使绘制的 Marker点都处于视口的可见范围
    });
    map.add(circle);



}


function createShangquan_map(cpoint,mapList) {
    //地图
    var map = new AMap.Map('shangquan_map', {
        resizeEnable: true,
        zoom: 15,
        center: cpoint
        // scrollWheel: false
    });

    mapList.forEach(function (val, index, arr) {
        // console.log(val)
        var  icon="/img/report/marker1.png"
        if (val.typeTemp=="residential"){
            icon="/img/report/marker1.png"

            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }
        if (val.typeTemp=="school"){
            icon="/img/report/marker2.png"

            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }
        if (val.typeTemp=="hospital"){
            icon="/img/report/marker3.png"

            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }

        if (val.typeTemp=="commercial_street"){
            icon="/img/report/marker4.png"

            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }

        if (val.typeTemp=="business"){
            icon="/img/report/marker5.png"

            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }

        if (val.typeTemp=="raliway_station"||val.typeTemp=="coach_station"||val.typeTemp=="subway_station"||val.typeTemp=="bus_station"){
            icon="/img/report/marker6.png"

            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }

    })
    createCircle(map,cpoint)
}





function ripeMap(cpoint,mapList) {
    //地图
    var map = new AMap.Map('shangquanMap', {
        resizeEnable: true,
        zoom: 15,
        center: cpoint
        // scrollWheel: false
    });

    mapList.forEach(function (val, index, arr) {
        // console.log(val)
        var  icon="/img/report/marker1.png"
        if (val.typeTemp=="hotel"){
            icon="/img/report/marker1.png"

            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }
        if (val.typeTemp=="restaurant"){
            icon="/img/report/marker2.png"

            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }
        if (val.typeTemp=="supermarket"){
            icon="/img/report/marker3.png"

            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }

        if (val.typeTemp=="convenience_store"){
            icon="/img/report/marker4.png"

            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }

        if (val.typeTemp=="mall"){
            icon="/img/report/marker5.png"

            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }

        if (val.typeTemp=="commercial_street"){
            icon="/img/report/marker6.png"

            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }

    })
    createCircle(map,cpoint)
}




function  createShangquan_heat_chart(nameDataArr,dataArr) {


    var shangquan_heat_chart = echarts.init(document.getElementById('shangquan_heat_chart'));

    shangquan_heat_chart_option = {

        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'horizontal',
            x: 'center',
            textStyle: {
                fontSize: '30',
                fontWeight: 'bold'
            },
            data: nameDataArr
        },
        series: [
            {
                name: '商业类型',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                color: ['#689EF8', '#826AF9', '#5862FF', '#2CD9C5', '#F89D2E', '#FFE700'],
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:dataArr
            }
        ]
    };
    shangquan_heat_chart.setOption(shangquan_heat_chart_option)


}


//词云
function createWordCloudChart(wordCloudData) {
    var wordCloud_chart = echarts.init(document.getElementById('wordCloud_chart'));
    wordCloud_option = {

        tooltip: {
            show: true
        },
        series: [{
            type: "wordCloud",
            gridSize: 100,
            shape: 'circle',
            sizeRange: [12, 60],
            width: 800,
            height: 500,
            textStyle: {
                normal: {
                    color: function () {
                        return 'rgb(' + [
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160)
                        ].join(',') + ')';
                    }
                },
                emphasis: {
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            },
            data: cloudData,
        }]

    };

    wordCloud_chart.setOption(wordCloud_option)
}




function createtiliang_map(cpoint,mapList) {
    //地图
    var map = new AMap.Map('tiliang_map', {
        resizeEnable: true,
        zoom: 15,
        center: cpoint
        // scrollWheel: false
    });

    mapList.forEach(function (val, index, arr) {

        if (val.typeTemp=="residential"){
            icon="/img/report/marker1.png"
            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }
        if (val.typeTemp=="school"){
           var icon="/img/report/marker2.png"
            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }


        if (val.typeTemp=="business"){
            var icon="/img/report/marker5.png"
            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }
    })
    createCircle(map,cpoint)

}









//折线图
// var keliu_chart = echarts.init(document.getElementById('keliu_chart'));
// keliu_option = {
//     tooltip: {
//         trigger: 'axis'
//     },
//     legend: {
//         data: ['weekday', 'weekend'],
//         bottom: 'bottom',
//         textStyle: {
//             fontSize: '30',
//             fontWeight: 'bold'
//         },
//     },
//     grid: {
//         left: '3%',
//         right: '4%',
//         bottom: '10%',
//         containLabel: true
//     },
//     toolbox: {
//         feature: {
//             saveAsImage: {}
//         }
//     },
//     xAxis: {
//         type: 'category',
//         boundaryGap: false,
//         data: ['06', '08', '10', '12', '14', '16', '18', '20', '22', '24', '02', '04'],
//         nameTextStyle: {
//             fontSize: 30
//         }
//     },
//     yAxis: {
//         type: 'value',
//         splitLine: {    //网格线
//             lineStyle: {
//                 type: 'dashed'    //设置网格线类型 dotted：虚线   solid:实线
//             },
//             show: true //隐藏或显示
//         },
//         nameTextStyle: {
//             fontSize: 30
//         }
//     },
//     series: [
//         {
//             name: 'weekday',
//             type: 'line',
//             symbol: 'circle',
//             symbolSize: function (val) {
//                 return 8;
//             },
//             data: [120, 132, 101, 134, 90, 230, 210, 101, 134, 90, 230, 210],
//             itemStyle: {
//                 color: "#59CC74",
//             }
//         },
//         {
//             name: 'weekend',
//             type: 'line',
//             symbol: 'circle',
//             symbolSize: function (val) {
//                 return 8;
//             },
//             data: [220, 182, 191, 234, 290, 330, 310, 70, 234, 290, 330, 134],
//             itemStyle: {
//                 color: "#47A1FF",
//             }
//         }
//     ]
// };
// keliu_chart.setOption(keliu_option)




//
// var kequnjiegou_chart1 = echarts.init(document.getElementById('kequnjiegou_chart1'));

function  createkequnjiegou_chart(xData,vData){
    var kequnjiegou_chart2 = echarts.init(document.getElementById('kequnjiegou_chart2'));
    kequnjiegou_option = {
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: '{a0}:{c0}%'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: xData,
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '收入水平',
                type: 'bar',
                barWidth: '10%',
                data: vData,
                itemStyle: {
                    normal: {
                        color: "#2CD9C5"
                    }
                }
            }
        ]
    };
    kequnjiegou_chart2.setOption(kequnjiegou_option)
}






// kequnjiegou_chart1.setOption(kequnjiegou_option)
//


//客流特征kequntezheng_chart

function createtezhengChart(m2R2Data) {

    var kequntezheng_chart = echarts.init(document.getElementById('kequntezheng_chart'));

    kequntezheng_option = {

        tooltip: {
            trigger: 'item',
            formatter: function (parms) {
                var str = parms.seriesName + "</br>" +
                    parms.marker + "" + parms.data.legendname + "</br>" +
                    "数量：" + parms.data.value + "</br>" +
                    "占比：" + parms.percent + "%";
                return str;
            }
        },
        legend: {
            itemWidth: 30,
            itemHeight: 20,
            type: "scroll",
            orient: 'vertical',
            left: '60%',
            align: 'left',
            top: 'middle',
            textStyle: {
                fontSize: 24,
                color: '#8C8C8C'
            },
            height: 300
        },
        series: [
            {
                name: '客群结构',
                type: 'pie',
                center: ['35%', '50%'],
                radius: ['40%', '65%'],
                clockwise: false, //饼图的扇区是否是顺时针排布
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: true,
                        position: 'outter',
                        formatter: function (parms) {
                            return parms.data.legendname
                        },
                        textStyle: {
                            fontSize: 20,
                            color: '#8C8C8C'
                        },
                    }
                },
                labelLine: {
                    normal: {
                        length: 6,
                        length2: 3,
                        smooth: true,

                    }
                },
                data: m2R2Data
            }
        ]
    };

    kequntezheng_chart.setOption(kequntezheng_option)
}








function createxiaofeibianli_map(cpoint,mapList){

    var map = new AMap.Map('xiaofeibianli_map', {
        resizeEnable: true,
        zoom: 15,
        center: cpoint
        // scrollWheel: false
    });

    mapList.forEach(function (val, index, arr) {

        if (val.typeTemp=="bus_station"){
            var icon="/img/report/marker1.png"
            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }

        if (val.typeTemp=="subway_station"){
            var icon="/img/report/marker3.png"
            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }


    })
    createCircle(map,cpoint)
}



//停车
function createParkingMap(cpoint,mapList) {
    var map = new AMap.Map('xiaofeibianliDiv2_map', {
        resizeEnable: true,
        zoom: 15,
        center: cpoint
    });

    mapList.forEach(function (val, index, arr) {

        if (val.typeTemp=="parking"){
            var icon="/img/report/marker5.png"
            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: icon,
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);
        }

    })
    createCircle(map,cpoint)
}


// //客流便利性地图2
// var map = new AMap.Map('xiaofeibianliDiv2_map', {
//     resizeEnable: true,
//     zoom: 16,
//     center: [114.26199, 30.623342]
// });



//竞品分析地图
function jingpinfenxMap(cpoint,mapList) {
    var map = new AMap.Map('jingpinfenxi_map', {
        resizeEnable: true,
        zoom: 15,
        center: cpoint
    });

    mapList.forEach(function (val, index, arr) {
            var marker = new AMap.Marker({
                icon:new AMap.Icon({
                    image: "/img/report/marker1.png",
                    size: new AMap.Size(26, 34),  //图标大小
                    imageSize: new AMap.Size(26,34)
                }),
                title: val.name,
                position: val.location.split(',')
            });
            map.add(marker);


    })
    createCircle(map,cpoint)


}
//竞品分析地图
//
// var map = new AMap.Map('jingpinfenxi_map', {
//     resizeEnable: true,
//     zoom: 16,
//     center: [114.26199, 30.623342]
// });




function  createjingpinfenxi_chart(yData,vData) {
    //竞品分析图表
    var jingpinfenxi_chart = echarts.init(document.getElementById('jingpinfenxi_chart'));
    jingpinfenxi_option = {

        legend: {
            show: false
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: '竞争门店：{c0}个'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            data:yData
        },
        series: [
            {
                type: 'bar',
                data: vData,
                barWidth: '25%',
                itemStyle: {
                    color: "#2D99FF",
                    emphasis: {
                        barBorderRadius: 30
                    },
                    normal: {
                        //柱形图圆角，初始化效果
                        barBorderRadius: [0, 10, 10, 0],
                        color: "#2D99FF",
                    }
                },

            }
        ]
    };

    jingpinfenxi_chart.setOption(jingpinfenxi_option)
}



//气泡图
//
// var avgXiaofei_chart = echarts.init(document.getElementById('avgXiaofei_chart'), null, {
//     renderer: 'canvas'
// });
// avgXiaofei_option = {
//     xAxis: {
//         type: 'category',
//         data: ['7/12', '8/12', '9/12', '10/12', '11/12', '12/12', '13/12', '14/12', '15/12', '16/12', '17/12', '18/12', '19/12', '20/12']
//     },
//     yAxis: {
//         type: 'value'
//     },
//     series: [{
//         data: [520, 932, 901, 734, 1290, 1330, 1320, 945, 843, 674, 543, 390, 330, 320],
//         type: 'scatter',
//         symbolSize: function (val) {
//             return val * 0.02;
//         },
//         itemStyle: {
//             color: "#826AF9",
//         }
//
//
//     },
//     {
//         data: [620, 1032, 701, 914, 990, 1130, 745, 745, 321, 474, 643, 432, 210, 200],
//         type: 'scatter',
//         symbolSize: function (val) {
//             return val * 0.02;
//         },
//
//         itemStyle: {
//             color: "#FFE700",
//         }
//
//     }]
// };
//
//
// avgXiaofei_chart.setOption(avgXiaofei_option)



//开店成本
// var map = new AMap.Map('kaidianchengben_map', {
//     resizeEnable: true,
//     zoom: 16,
//     center: [114.26199, 30.623342]
// });



//'水费', '电费', '燃气费 等。。
function  chenbensuan_chart(nameData,valueData) {
    var chenbenhesuan_chart1 = echarts.init(document.getElementById('chenbenhesuan_chart1'));

    chenbenhesuan_chart1_option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'horizontal',
            x: 'center',
            bottom: 'bottom',
            textStyle: {
                fontSize: '30',
                fontWeight: 'bold'
            },
            data: ['水费', '电费', '燃气费']
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:valueData
            }
        ]
    };
    chenbenhesuan_chart1.setOption(chenbenhesuan_chart1_option)

}

//成本 折线图

function createchenbenhesuan(xData,vData) {
    var chenbenhesuan_chart2 = echarts.init(document.getElementById('chenbenhesuan_chart2'));
    chenbenhesuan_chart2_option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function (params) {
                console.log(params[0].data)
                if (params[0].data==undefined){
                    return ""
                }
                return parseInt(params[0].data)
            }
        },
        xAxis: {
            type: 'category',
            data:xData
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: vData,
            type: 'line',
            symbol: 'circle',
            symbolSize: function (val) {
                return 10;
            },
            itemStyle: {
                color: "#45A6FF",
            }
        }]
    };


    chenbenhesuan_chart2.setOption(chenbenhesuan_chart2_option)
}




//后厨 服务 管理人员
// var chenbenDi3_left_chart = echarts.init(document.getElementById('chenbenDi3_left_chart'));
// chenbenDi3_left_chart_option = {
//     tooltip: {
//         trigger: 'item',
//         formatter: "{a} <br/>{b}: {c} ({d}%)"
//     },
//     legend: {
//         orient: 'horizontal',
//         x: 'center',
//         bottom: 'bottom',
//         textStyle: {
//             fontSize: '30',
//             fontWeight: 'bold'
//         },
//         data: ['后厨', '服务员', '管理人员']
//     },
//     series: [
//         {
//             name: '访问来源',
//             type: 'pie',
//             radius: ['50%', '70%'],
//             avoidLabelOverlap: false,
//             label: {
//                 normal: {
//                     show: false,
//                     position: 'center'
//                 },
//                 emphasis: {
//                     show: true,
//                     textStyle: {
//                         fontSize: '30',
//                         fontWeight: 'bold'
//                     }
//                 }
//             },
//             labelLine: {
//                 normal: {
//                     show: false
//                 }
//             },
//             data: [
//                 { value: 335, name: '后厨', itemStyle: { color: "#59CC74" } },
//                 { value: 310, name: '服务员', itemStyle: { color: "#47A1FF" } },
//                 { value: 234, name: '管理人员', itemStyle: { color: "#4FCBCB" } }
//             ]
//         }
//     ]
// };
//
// chenbenDi3_left_chart.setOption(chenbenDi3_left_chart_option)
//


//水费 电费 燃气费
// var chenbenDi3_fight_chart = echarts.init(document.getElementById('chenbenDi3_fight_chart'));
// chenbenDi3_fight_chart_option = {
//     tooltip: {
//         trigger: 'item',
//         formatter: "{a} <br/>{b}: {c} ({d}%)"
//     },
//     legend: {
//         orient: 'horizontal',
//         x: 'center',
//         bottom: 'bottom',
//         textStyle: {
//             fontSize: '30',
//             fontWeight: 'bold'
//         },
//         data: ['水费', '电费', '燃气费']
//     },
//     series: [
//         {
//             name: '访问来源',
//             type: 'pie',
//             radius: ['50%', '70%'],
//             avoidLabelOverlap: false,
//             label: {
//                 normal: {
//                     show: false,
//                     position: 'center'
//                 },
//                 emphasis: {
//                     show: true,
//                     textStyle: {
//                         fontSize: '30',
//                         fontWeight: 'bold'
//                     }
//                 }
//             },
//             labelLine: {
//                 normal: {
//                     show: false
//                 }
//             },
//             data: [
//                 { value: 335, name: '水费', itemStyle: { color: "#59CC74" } },
//                 { value: 310, name: '电费', itemStyle: { color: "#47A1FF" } },
//                 { value: 234, name: '燃气费', itemStyle: { color: "#4FCBCB" } }
//             ]
//         }
//     ]
// };
//
// chenbenDi3_fight_chart.setOption(chenbenDi3_fight_chart_option)
