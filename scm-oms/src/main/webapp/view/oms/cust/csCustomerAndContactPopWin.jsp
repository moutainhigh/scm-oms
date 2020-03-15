<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<title>客户及默认联系人弹出框</title>
</head>
<body>
	<div class="container mb-0">
	<form class="layui-form form-search pd-0" action="" onsubmit="return false" method="get">
		<div class="row form-group">
			<div class="col-xs-4">
				<label class="control-label col-xs-4">客户代码</label>
	            <input type="text" id="customer_code" name="customer_code" autocomplete="off" class="layui-input col-xs-7">
			</div>
			<div class="col-xs-4">
				<label class="control-label col-xs-4">客户名称</label>
	            <input type="text" id="customer_name" name="customer_name" autocomplete="off" class="layui-input col-xs-7">
			</div>
			<div class="col-xs-4 f0 text-r">
	             <button class="layui-btn layui-btn-small" id="btn-query">查询</button>
	             <button class="layui-btn layui-btn-small" id="btn-reset" type="reset">重置</button>
	        </div>
		</div>	
	</form>
	<div class="layui-form">
			<table id="dateTable" class="layui-table" lay-even="" width="100%" lay-skin="line">
				<thead>
					<tr>
						<th width="10"><input type="checkbox" name="" lay-skin="primary" lay-filter="allChoose"></th>
						<th>客户代码</th>
						<th>客户名称</th>
						<th>默认联系人</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<script type="text/javascript">
        layui.config({base: '${base}/view/oms/cust/'}).use('csCustomerAndContactPopWin');
    </script>
</body>
</html>