<!DOCTYPE html>
<html>
<head>
	<title>客户订单查看</title>
	<#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
	<#--<div class="layui-col-md12">
		<div class="layui-card">
			<div class="layui-card-body">
					<input name="id" type="hidden" value="${(customerContact.id)!}"/>
					<div class="layui-form-item layui-row">
						<div class="layui-col-xs6">
							<label class="layui-form-label">来往时间</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input"
									   name="contactTime" id="contactTime"  value="${(customerContact.contact_time)!}" >
							</div>
						</div>
						<div class="layui-col-xs6">
							<label class="layui-form-label">地址</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input"
									   name="address" id="address" value="${(customerContact.address)!}" >
							</div>
						</div>
					</div>

					<div class="layui-form-item layui-row">
						<div class="layui-col-xs6">
							<label class="layui-form-label">预览</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input"
									   name="overview"   value="${(customerContact.overview)!}" >
							</div>
						</div>

					</div>

			</div>
		</div>
	</div>
	<br/>
	<div class="layui-form-item layui-row layui-col-xs12">
		<div class="layui-input-block">
			<button class="layui-btn layui-btn-lg" lay-submit=""
					lay-filter="addOrUpdateContact">确认
			</button>
			<button class="layui-btn layui-btn-lg layui-btn-normal" id="cancleBtn">取消</button>
		</div>
	</div>-->

	<blockquote class="layui-elem-quote quoteBox">
		<form class="layui-form">
			<div class="layui-inline">
				<div class="layui-input-inline">
					<input type="text" name="cusName"
						   class="layui-input
					searchVal" placeholder="客户名" />
				</div>
				<div class="layui-input-inline">
					<input type="text" name="contactTime" id="contactTime"
						   class="layui-input
					searchVal" placeholder="下单时间"  />
				</div>
				<a class="layui-btn search_btn" data-type="reload"><i
							class="layui-icon">&#xe615;</i> 搜索</a>
			</div>
		</form>
	</blockquote>
	<input type="hidden" name="cid" value="${(cid)!}">
	<script type="text/html" id="toolbarDemo">
		<div class="layui-btn-container">
			<a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
				<i class="layui-icon">&#xe608;</i>
				添加
			</a>
		</div>
	</script>

	<div class="layui-col-md12">
		<table id="customerConcatList" class="layui-table"  lay-filter="customerConcats"></table>
	</div>

	<!--操作-->
	<script id="customerConcatListBar" type="text/html">
		<a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">编辑</a>
		<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
	</script>
</form>

	<script type="text/javascript" src="${ctx}/js/customer/customer.contact.js"></script>
</body>
</html>