<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>书城首页</title>
<%@ include file="/WEB-INF/include/Base.jsp" %>

<c:set value="client/bookCliectServlet?method=getBookPage&pageNo=" var="methodUri"></c:set>
<!-- 由于查询之后，servlet会将请求转发到当前页面,保持同一个查询条件，所以可以从请求参数中获取之前填写的查询条件 -->
<c:set value="&minPrice=${param.minPrice }&maxPrice=${param.maxPrice}" var="params"></c:set>


<script type="text/javascript" src="static/script/jquery-1.7.2.js"></script>

<script type="text/javascript">

	$(function(){
		$("#go").click(function(){
			
			var $goto = $("#goto");
			//获取输入的文本框，获取页码值
			var targetPageNo = $.trim($goto.val());
			
			if(isNaN(targetPageNo)){
				$("#goto").val($goto[0].defaultValue);
				return;
			}
			
			//跳到指定的页面（IE不支持base路径，所以要用绝对路径，避免IE调试不正确）
			var targetUrl = "${pageContext.request.contextPath}/" + "${methodUri }"+ targetPageNo+"${params}";
			//var targetUrl = "/BookStore_EG03/client/bookCliectServlet?method=getBookPage&pageNo=" + targetPageNo;
			window.location.href = targetUrl;
		});
		
		$(".book_add_btn").click(function(){
			//alert("bb");
			
			//获取对象的id
			var bookId = this.id;
			//?method=add2Cart
			var url = "${pageContext.request.contextPath }/client/CartItemServlet";
			var paramDate = {"method":"add2Cart","bookId":bookId,"time":new Date()};
			var myCallBack = function(responseDate){
				
				//解析json字符
				var bookName = responseDate.bookName;
				var totalCount = responseDate.totalCount;
				
				//在指定的地方显示
				$(".shoeTotalCount").text("您的购物车中有"+totalCount+"件商品");
				$(".showBookName").text("您刚刚将"+bookName+"加入到购物车");
			};
			var type = "json";
			//发送Ajax请求
			$.post(url,paramDate,myCallBack,type);
			
		});
		
		
	});

</script>
</head>
<body>
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">网上书城</span>
			<%@include file="/WEB-INF/include/client-header.jsp" %>
	</div>
	
	<div id="main">
		<div id="book">
			<div class="book_cond">
				<form action="client/bookCliectServlet?method=getBookPage" method="post">
					价格：<input type="text" name="minPrice"> 元 - <input type="text" name="maxPrice"> 元 <button>查询</button>
				</form>
				
			</div>
			<div style="text-align: center">
				<span class="shoeTotalCount">
				
					<c:if test="${sessionScope.cart == null || empty sessionScope.cart.map }">
						您的购物车中还没有商品！！
					</c:if>
					
					<c:if test="${sessionScope.cart != null && !empty sessionScope.cart.map }">
						您的购物车中有${sessionScope.cart.totalCount }件商品！！
					</c:if>
				
				</span>
				<div class ="showBookName">
					<c:if test="${requestScope.bookName !=null }">
						您刚刚将<span style="color: red">${requestScope.bookName }</span>加入到了购物车中
					</c:if>
					
				</div>
			</div>
			
			<!--判断当前页面是否取得了数据  -->
			<c:if test="${empty page.list || empty page }">
				<div style="text" align="center">没有要显示的数据</div>
			</c:if>
			
			<c:if test="${!empty page && !empty page.list  }">
				<c:forEach items="${page.list }" var="book">
					<div class="b_list">
						<div class="img_div">
							<img class="book_img" src="${book.imgPath }" />
						</div>
						<div class="book_info">
							<div class="book_name">
								<span class="sp1">书名:</span> <span class="sp2">${book.bookName }</span>
							</div>
							<div class="book_author">
								<span class="sp1">作者:</span> <span class="sp2">${book.author }</span>
							</div>
							<div class="book_price">
								<span class="sp1">价格:</span> <span class="sp2">￥${book.price }</span>
							</div>
							<div class="book_sales">
								<span class="sp1">销量:</span> <span class="sp2">${book.salseAmount }</span>
							</div>
							<div class="book_amount">
								<span class="sp1">库存:</span> <span class="sp2">${book.storeNum }</span>
							</div>
							<div class="book_add">
								<%-- <a href="${pageContext.request.contextPath }/client/CartItemServlet?method=add2Cart&bookId=${book.bookId }${params}&pageNo=${page.pageNo}">加入购物车</a> --%>
								<button class="book_add_btn" id="${book.bookId }">添入购物车</button>
							</div>
						</div>
					</div>

				</c:forEach>
			</c:if>
		</div>
		
		<div id="page_nav">
			<!-- 判断是否有上一页。首页 -->
			<!--此处的方法在page对象中的方法名isHasPrec。如果返回值类型是boolean，并且命名的方式是is开头
			    在EL表达式调用时，可以把is省略  -->
			<c:if test="${page.hasPrev }">
				<a href="${methodUri }1${params}">首页</a>
				<a
					href="${methodUri }${page.prev }${params}">上一页</a>
			</c:if>
			
			<!-- 当前页 -->
			<%-- ${page.pageNo } --%>
			<c:forEach begin="1" end="${page.totalPageNo }" var="current">

				<!-- 显示当前页前面的两页 -->
				<c:if test="${current<page.pageNo && current >= page.pageNo-2 }">
					<a href="${methodUri }${current }${params}">${current }</a> 
				</c:if>

				<!--标记当前页  -->
				<c:if test="${page.pageNo==current }">
					[${current }]
				</c:if>

				<!-- 显示当前页后面的两页 -->
				<c:if test="${current>page.pageNo && current <= page.pageNo+2 }">
					<a href="${methodUri }${current }${params}">${current }</a> 
				</c:if>
			</c:forEach>
			
			<!-- 判断是否有下一页。末页 -->
			<c:if test="${page.hasNext }">
				<a
					href="${methodUri }${page.next }${params}">下一页</a>
				<a
					href="${methodUri }${page.totalPageNo }${params}">末页</a>
			</c:if>
			共${page.totalPageNo }页
			
			 共${page.totalRecordNo }条记录 
			 <!-- 跳转到指定的页面 -->
			 到第<input value="${page.pageNo }" name="pn" id="goto" />页 
			 <input id="go"	type="button" value="确定">
		</div>
	
	</div>
	
	<div id="bottom">
		<span>
			尚硅谷书城.Copyright &copy;2015
		</span>
	</div>
</body>
</html>