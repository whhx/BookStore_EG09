<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/content.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/input.css">

<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery.validate.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath }/script/messages_cn.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery.bloc.contexkUI.js" ></script>

<script type="text/javascript">
	$(function(){
		//权限的级联选取
		$(":checkbox").click(function(){
			//被选中：则关联的也需要被选中
			if($(this).is(":checked")){
				//获得的是class属性对应的属性值，是字符串类型的一系列数字和逗点组成
				var relateAuthorites = $(this).attr("class");
				//将这些字符数字进行拆分修改
				var relatedAuthorityIds = relateAuthorites.split(",");
				
				for(var i=0;i<relatedAuthorityIds.length;i++){
					var relatedAuthorityId = relatedAuthorityIds[i];
					relatedAuthorityId = $.trim(relatedAuthorityId);
					
					//若 id 为不为空串, 则把 value 值等于 id 的那个 checkbox 也级联选择
					if(relatedAuthorityId != ""){
						$(":checkbox[value='"+relatedAuthorityId+"']").attr("checked",true);
					}
					
				}
				
			}//被取消选中
			else{
				//检查哪些 checkbox 和当前的 checkbox 相关联. 
				//关联的标准为: 那个 checkbox 的 class 属性中包含当前 checkbox 的 ,id,!
				var id = $(this).val();
				$(":checkbox[class*=',"+id+",']").attr("checked",false);
				
			}
			
		});
		
		//点击显示相应的子元素
		$("select").change(function(){
			$("p[class^=authority_]").hide();
			
			var val = $(this).val();
			$(".authority_"+val).show();
		});
	})

</script>

</head>
<body>
	<br>
	<s:debug/>
	<s:form id="employeeForm" action="/role-save"  cssClass="employeeForm">
		<input type="hidden" value="${roleId }" name="roleId">
		<fieldset>
			<p>
				<label for="name">角色名(必填*)</label> 
				<s:textfield name="roleName" cssClass="required"></s:textfield>
			</p>

			<p>
				<label for="authority">授予权限(必选)</label>
				<input type="checkbox" name="authorities2" class="required" style="display: none" />
			</p>

			<p>
				<label>权限名称(必填)</label>
				<!-- 父权限 -->
				<s:select list="#request.parentAuthorities" listKey="id" 
				listValue="displayName" headerKey="" headerValue="请选择...">
				</s:select>
			</p>
			
			<s:checkboxlist list="#request.authroities" listKey="id"
							name="authorities2" listCssClass="relatedAuthorites" 
							listValue="displayName" cssStyle="border:none" 
							templateDir="ems/template">
			
			</s:checkboxlist> 
			
			<!--  
			<s:iterator value="#request.authroities">
				<p style="display: none" class="authority_${parentAuthority.id }" >
					<label>&nbsp;</label> 
					<s:checkbox name="authorities2" fieldValue="%{id}" cssClass="%{relatedAuthorites}"></s:checkbox>${displayName }
				</p>
			</s:iterator>
			-->

			<p>
				<input class="submit" type="submit" value="Submit" />
			</p>

		</fieldset>

	</s:form>
</body>
</html>