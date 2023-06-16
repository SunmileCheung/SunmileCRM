layui.use(['table','layer',"form","laydate"],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table,
        laydate = layui.laydate;

    // 设置时间
    laydate.render({
        elem: '#time'
    });
    $("#cancleBtn").click(function () {
        var index = parent.layer.getFrameIndex(window.name); // 先得到当前 iframe 层的索引
        parent.layer.close(index); // 再执行关闭
    });
    /*// 设置时间
    laydate.render({
        elem: '#ceateDate'
    });*/

    var cid = $("input[name='cid']").val();
    console.log(cid);
    //客户列表展示
    var  tableIns = table.render({
        elem: '#customerConcatList',
        url : ctx+'/customer_concat/list?cid='+cid,
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "customerListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: "cusId", title:'客户编号',fixed:"true"},
            {field: 'cusName', title: '客户名',align:"center"},
            {field: 'contactTime', title: '来往时间',  align:'center'},
            {field: 'address', title: '地址', align:'center'},
            {field: 'overview', title: '预览', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center'},
            {field: 'updateDate', title: '更新时间', align:'center'},
            {title: '操作', templet:'#customerConcatListBar',fixed:"right",align:"center", minWidth:150}
        ]]
    });

    // 多条件搜索
    $(".search_btn").on("click",function () {
        table.reload("customerListTable",{
            page:{
                curr:1
            },
            where:{
                cusName:$("input[name='customerName']").val(),// 客户名
                contactTime:$("input[name='contactTime']").val()    //下单时间
            }
        })
    });

    table.on('toolbar(customerConcats)',function (obj) {
        var layEvent = obj.event;
        if (layEvent === "add"){
            openAddOrUpdateCustomerDialog();
        }
    })

    table.on('tool(customerConcats)',function (obj) {
        var layEvent =obj.event;
        if(layEvent === "edit"){
            openAddOrUpdateCustomerDialog(obj.data.id);
        }else if(layEvent === "del"){
            layer.confirm("确认删除当前记录?",{icon: 3, title: "客户管理"},function (index) {
                $.post(ctx+"/customer_concat/delete",{id:obj.data.id},function (data) {
                    if(data.code==200){
                        layer.msg("删除成功");
                        tableIns.reload();
                    }else{
                        layer.msg(data.msg);
                    }
                })
            })
        }
    });

    function openAddOrUpdateCustomerDialog(id) {
        var title="客户管理-客户交往记录编辑";
        var url=ctx+"/customer_concat/addOrUpdateCustomerContact?cusId="+cid;
        if(id){
            title="客户管理-客户更新";
            url=url+"&id="+id;
        }
        layui.layer.open({
            title:title,
            type:2,
            area:["700px","500px"],
            maxmin:true,
            content:url
        })
    }

});