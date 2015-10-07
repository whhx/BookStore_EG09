<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>
<%@ include file="/WEB-INF/include/Base.jsp" %>

<script type="text/javascript">
	$(function(){
		
		$("[name=itemCount]").change(function(){
			//获取Id的值
			var bookId = this.id;
			
			//获取value的值
			var count = this.value;
			
			//对count值进行校验
			var reg = /^[1-9][0-9]*$/;
			//alert(reg.test(count));
			if(!reg.test(count)){
				this.value = this.defaultValue;
				return;
			}
			//发送请求
			//window.location.href ="${pageContext.request.contextPath}/client/CartItemServlet?method=updatCartItem&bookId="+bookId+"&count="+count;
			
			//在函数的外面获得相应的文本对象
			var $amountTd = $(this).parents("tr").children("td:eq(3)");
			
			//发送Ajax请求
			var url = "${pageContext.request.contextPath}/client/CartItemServlet";
			var paramData = {"method":"updatCartItem","bookId":bookId,"count":count,"time":new Date()};
			var myCallBack = function(responseData){
				//根据响应数据更新页面上的指定位置
				var amount = responseData.amount;
				var totalCount = responseData.totalCount;
				var totalAmount = responseData.totalAmount;
				
				//①总数量
				$(".b_count").text(totalCount);
				
				//②总金额
				$(".b_price").text(totalAmount);
				
				//③当前小计金额
				//获取显示小计金额的元素
				//当前this关键字引用的不是当前正在操作的文本框
				//在函数中的this引用的是调用当前函数的对象，所以当前函数作为Ajax操作的回调函数，不是由文本框元素对象调用的
				//这样写不能成功，需要在当前函数外面获取要修改的td：$(this).parents("tr").children("td:eq(3)").text(amount);
				
				//修改文本值（在函数的外面获取文本操作对象）
				$amountTd.text(amount);
			};
			
			var type = "json";
			
			$.post(url,paramData,myCallBack,type);
			
			
		});
		
		$("#clearId").click(function(){
			
			//弹出确认框
			var flag = confirm("真的要清空购物车吗？");
			if(!flag){
				return false;
			}
		});
		
		$(".cleracartItem").click(function(){
			//获得要删除的图书的名字
			var bookName = $(this).parents("tr").children("td:eq(0)").text();
			var flag = confirm("确定删除["+bookName+"]?吗");
			
			if(!flag){
				return false;
			}else{
				//获得要删除的tr
				var $tr=$(this).parents("tr");
				//获取当前对象的id
				var bookId = this.id;
				var url = "${pageContext.request.contextPath}/client/CartItemServlet";
				var paramDate = {"method":"clearCartIteam","bookId":bookId,"time":new Date()};
				var myCallBack = function(responseDate){
					//解析json字符
					var totalAmount = responseDate.totalAmount;
					var totalCount = responseDate.totalCount;
					
					//将数据显示到指定的位置(先判断购物车是否为空)
					if(totalCount>0){
						//删除指定的tr
						$tr.remove();
						//①总数量
						$(".b_count").text(totalCount);
						
						//②总金额
						$(".b_price").text(totalAmount);
						
					}else{
						//删除整个table，提示相应的信息
						$("table").empty().append("<tr><td>您的购物车中还没有商品！</td></tr>");
						
						//删除显示总金额和总数量的div
						$(".cart_info").remove();
					}
				};
				
				var type = "json";
				$.post(url,paramDate,myCallBack,type);
			}
		});
	});
	

</script>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">购物车</span>
			<%@ include file="/WEB-INF/include/client-header.jsp" %>
	</div>
	
	<div id="main">
	
		<table>
			<tr>
				<td>商品名称</td>
				<td>数量</td>
				<td>单价</td>
				<td>金额</td>
				<td>操作</td>
			</tr>
			<!--判断当前的购物车是否为空  -->
			<c:if test="${sessionScope.cart==null || empty sessionScope.cart.map }">
				<tr>
					<td colspan="5">当前购物车中没有商品</td>
				</tr>
			</c:if>	
			
			<c:if test="${sessionScope.cart != null && !empty sessionScope.cart.map }">
				<!-- 遍历map得到是entry类型的数据，bookId是键值，cartItem为value值 -->
				<c:forEach items="${sessionScope.cart.map }" var="entry">
					<!--entry.value得到的是cartItem  -->
					<!--entry.value.book得到的是cartItem中的book对象  -->
					<!--entry.value.count得到的是数量  -->
					<tr>
						<td>${entry.value.book.bookName }</td>
						<td><input id="${entry.key }" style="width: 70px; text-align: center" name="itemCount" value="${entry.value.count }" /> </td>
						<td>${entry.value.book.price }</td>
						<td>${entry.value.amount }</td>
						<%-- <td><a class="cleracartItem" href="client/CartItemServlet?method=clearCartIteam&bookId=${entry.value.book.bookId }">删除</a></td> --%>
						<td>
							<button class="cleracartItem" id="${entry.key }">删除</button>
						</td>
					</tr>
				</c:forEach>
			</c:if>	
		</table>
		<c:if test="${sessionScope.cart != null && !empty sessionScope.cart.map }">
			<div class="cart_info">
				<span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.totalCount }</span>件商品
				</span> <span class="cart_span">总金额<span class="b_price">${sessionScope.cart.totalAmount }</span>元
				
				<a id="clearId" href="client/CartItemServlet?method=clearCart">清空购物车</a>
				
				</span> <span class="cart_span"><a href="client/OrderClientServlet?method=doCash">去结账</a></span>
			</div>
		</c:if>
		
	
	</div>
	
	<div id="bottom">
		<span>
			尚硅谷书城.Copyright &copy;2015
		</span>
	</div>
</body>
</html>