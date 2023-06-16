<!--
   客户贡献分析页面
-->
<!DOCTYPE html>
<html>
<head>
    <title>客户贡献报表</title>
    <#include "../common.ftl">
</head>
<body class="childrenBody">

<form class="layui-form" >
    <blockquote class="layui-elem-quote quoteBox">
        <form class="layui-form">
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input type="text" name="serviceProcePeople"
                           class="layui-input
					searchVal" placeholder="处理人" />
                </div>
                <div class="layui-input-inline">
                    <select name="state" id="state">
                        <option value="">请选择服务进度</option>
                        <option value="fw_001">创建</option>
                        <option value="fw_002">分配</option>
                        <option value="fw_003">处理</option>
                        <option value="fw_004">反馈</option>
                        <option value="fw_005">归档</option>
                    </select>
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="customer" id="customer"
                           class="layui-input
					searchVal" placeholder="客户名"  />
                </div>
                <div class="layui-input-inline">
                    <select name="serveType" id="serveType">
                        <option value="">请选择服务类型</option>
                        <option value="6">咨询</option>
                        <option value="7">建议</option>
                        <option value="8">投诉</option>
                    </select>
                </div>
                <a class="layui-btn search_btn" data-type="reload"><i
                            class="layui-icon">&#xe615;</i> 搜索</a>
            </div>
        </form>
    </blockquote>
    <table id="customerServeList" class="layui-table"  lay-filter="contris"></table>
</form>
<div class="layui-card">
    <div class="layui-card-header">服务图表显示</div>
    <div class="layui-card-body" id="customerServeInfo" style="width: 100%;min-height:500px"></div>

    <#--<div class="layui-card-body" id="state" style="width: 100%;min-height:500px"></div>-->

</div>
<script type="text/javascript" src="${ctx}/js/report/customer.serve.js"></script>
<script type="text/javascript" src=""

</body>
</html>