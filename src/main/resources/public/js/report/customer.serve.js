layui.use(['table','layer','echarts',"form","laydate"],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        echarts = layui.echarts,
        table = layui.table,
        laydate = layui.laydate;


      // 设置时间
        laydate.render({
            elem: '#time'
        });
    //用户列表展示
    var  tableIns = table.render({
        elem: '#customerServeList',
        url : ctx+'/customer_serve/queryCustomerServeInfo',//查询客户服务统计聚合结果
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        id : "customerContriListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'customer', title: '客户名', minWidth:50, align:"center"},
            {field: 'state',templet:function (data) {
                    if (data.state == 'fw_001'){ return '创建';}
                    if (data.state == 'fw_002'){ return '分配';}
                    if (data.state == 'fw_003'){ return '处理';}
                    if (data.state == 'fw_004'){ return '反馈';}
                    if (data.state == 'fw_005'){ return '归档';}
                }, title: '服务状态', minWidth:50, align:"center"},
            {field: 'serviceProcePeople', title: '服务处理人', minWidth:50, align:"center"},
            {field: 'serveType', title: '服务类型', minWidth:50, align:"center"},
            {field: 'totalNum', title: '服务数量', minWidth:50, align:"center"}
        ]]
    });

    // 多条件搜索
    $(".search_btn").on("click",function () {
        table.reload("customerContriListTable",{
            page:{
                curr:1
            },
            where:{
                customer:$("input[name='customer']").val(),// 客户名
                state:$("#state").val(),// 服务进度
                serviceProcePeople:$("input[name='serviceProcePeople']").val(),    //处理人
                serveType:$("#serveType").val() //服务类型
            }
        })
    });

    $.ajax({
        type:"post",
        url:ctx+"/customer_serve/customerServeInfo",
        dataType:'json',
        success:function (data) {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('customerServeInfo'));
            // 指定图表的配置项和数据
            option = {
                xAxis: {
                    type: 'category',
                    data: data.data1
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    type: 'line',
                    data: data.data2
                }]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    });



});
