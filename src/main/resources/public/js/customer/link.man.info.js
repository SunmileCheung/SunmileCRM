layui.use(['form', 'layer',"laydate"], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    $("#cancleBtn").click(function () {
        var index = parent.layer.getFrameIndex(window.name); // 先得到当前 iframe 层的索引
        parent.layer.close(index); // 再执行关闭
    });
    /*// 设置时间
    laydate.render({
        elem: '#ceateDate'
    });*/
    form.on('submit(addOrUpdateLinkManInfo)',function (data) {
        var index= top.layer.msg("数据提交中,请稍后...",{icon:16,time:false,shade:0.8});
        var url = ctx+"/customer/saveLinkManInfo";
        if($("input[name='id']").val()){
            url=ctx+"/customer/updateLinkManInfo";
        }
        $.post(url  ,data.field,function (res) {
            if(res.code==200){
                top.layer.msg("操作成功");
                top.layer.close(index);
                layer.closeAll("iframe");
                // 刷新父页面
                parent.location.reload();
            }else{
                layer.msg(res.msg);
            }
        });
        return false;
    });



});