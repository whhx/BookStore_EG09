<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath }/css/global.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/content.css">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/script/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/script/themes/icon.css">


<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery.easyui.min.js"></script>

<script type="text/javascript">
	
	$(function(){
		$('#navigate').tree({
			onClick: function(node){
				//alert(node.text);  // 在用户点击的时候提示
				if(node.url){
					alert(node.url);
					if(node.url == '/ems-1306/security-logout'){
						window.parent.parent.location.href = node.url;
					}else{
						window.parent.document.getElementById("content").src=node.url;
					}
				}
			}
		});


	})
	
</script>
	
</head>
<body>
	
	<br>
	<table cellpadding=0 cellspacing=0>
      <tr>
      	<td>
      		&nbsp;&nbsp;&nbsp;
      	</td>
        <td>
			<ul id="navigate" class="easyui-tree" data-options="url:'emp-navigate?a=13',method:'get',animate:true,dnd:true"></ul>
        </td>
      </tr>
    </table>
	
</body>
</html>