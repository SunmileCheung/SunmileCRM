<!DOCTYPE html>
<html>
<head>
	<title>客户订单查看</title>
	<#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
	<div class="layui-col-md12">
		<div class="layui-card">
			<div class="layui-card-body">
				<#--<form class="layui-form" >-->
					<input name="id" type="hidden" value="${(customerlinkman.id)!}"/>
					<div class="layui-form-item layui-row">
						<div class="layui-col-xs6">
							<label class="layui-form-label">客户名称</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input"
									   name="linkName" id="linkName"  value="${(customerlinkman.linkName)!}" >
							</div>
						</div>
						<div class="layui-col-xs6">
							<label class="layui-form-label">性别</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input"
									   name="sex" id="sex" value="${(customerlinkman.sex)!}" >
							</div>
						</div>
					</div>

					<div class="layui-form-item layui-row">
						<div class="layui-col-xs6">
							<label class="layui-form-label">职位</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input"
									   name="zhiwei"   value="${(customerlinkman.zhiwei)!}" >
							</div>
						</div>
						<div class="layui-col-xs6">
							<label class="layui-form-label">联系电话</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input"
									    name="phone" value="${(customerlinkman.phone)!}" id="phone" >
							</div>
						</div>
					</div>
					<div class="layui-form-item layui-row">
						<div class="layui-col-xs6">
							<label class="layui-form-label">创建时间</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input"
									   name="ceateDate"   value="${(customerlinkman.ceateDate?string('yyyy-MM-dd HH:mm:ss'))!}" id="ceateDate" readonly="readonly">
							</div>
						</div>
						<div class="layui-col-xs6">
							<label class="layui-form-label">办公室电话</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input"
									   name="officePhone" value="${(customerlinkman.officePhone)!}" id="officePhone" >
							</div>
						</div>
					</div>
				<#--</form>-->
			</div>
		</div>
	</div>
	<br/>
	<div class="layui-form-item layui-row layui-col-xs12">
		<div class="layui-input-block">
			<button class="layui-btn layui-btn-lg" lay-submit=""
					lay-filter="addOrUpdateLinkManInfo">确认
			</button>
			<button class="layui-btn layui-btn-lg layui-btn-normal" id="cancleBtn">取消</button>
		</div>
	</div>
</form>

	<script type="text/javascript" src="${ctx}/js/customer/link.man.info.js"></script>
</body>
</html>