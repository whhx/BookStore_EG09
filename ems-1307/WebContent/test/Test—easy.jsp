<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/script/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/script/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery.easyui.min.js"></script>

</head>
<body>
	<ul class="easyui-tree" data-options="url:'tree_data1.json',method:'get',animate:true"></ul>
</body>
</html>