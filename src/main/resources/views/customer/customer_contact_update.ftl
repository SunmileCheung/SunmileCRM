<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
    <div class="layui-col-md12">
        <div class="layui-card">
            <div class="layui-card-body">
                <input name="id" type="hidden" value="${(customerContact.id)!}"/>
                <input name="cusId" type="hidden" value="${(cusId)!}"/>
                <div class="layui-form-item layui-row">
                    <div class="layui-col-xs6">
                        <label class="layui-form-label">来往时间</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input"
                                   name="contactTime" id="contactTime" value="${(customerContact.contact_time)!}">
                        </div>
                    </div>
                    <div class="layui-col-xs6">
                        <label class="layui-form-label">地址</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input"
                                   name="address" id="address" value="${(customerContact.address)!}">
                        </div>
                    </div>
                </div>

                <div class="layui-form-item layui-row">
                    <div class="layui-col-xs6">
                        <label class="layui-form-label">预览</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input"
                                   name="overview" value="${(customerContact.overview)!}">
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
    </div>
</form>
</body>
<script type="text/javascript" src="${ctx}/js/customer/customer.contact.update.js"></script>
</html>