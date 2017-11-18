<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!--
            	时间：2015-12-30
            	描述：菜单栏
            -->
			<div class="container-fluid">
				<div class="col-md-4">
					<img src="${pageContext.request.contextPath}/img/logo2.png" />
				</div>
				<div class="col-md-5">
					<img src="${pageContext.request.contextPath}/img/header.png" />
				</div>
				<div class="col-md-3" style="padding-top:20px">
					<ol class="list-inline">
					<c:if test="${empty user }">
						<li><a href="${pageContext.request.contextPath}/user?method=loginUI">登录</a></li>
						<li><a href="${pageContext.request.contextPath}/user?method=registerUI">注册</a></li>
				        <li><a href="#">购物车</a></li>
				</c:if>
				<c:if test="${!empty user }">
				        <li>欢迎你,${user.username }</li>
						<li><a href="${pageContext.request.contextPath }/user?method=logout">注销</a></li>
						<li><a href="#">购物车</a></li>
						<li><a href="${pageContext.request.contextPath }/order?method=myOrder&currentPage=1">我的订单</a></li>
				</c:if>
					</ol>
				</div>
			</div>
			<!--
            	时间：2015-12-30
            	描述：导航条
            -->
			<div class="container-fluid">
				<nav class="navbar navbar-inverse">
					<div class="container-fluid">
						<!-- Brand and toggle get grouped for better mobile display -->
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="#">首页</a>
						</div>

						<!-- Collect the nav links, forms, and other content for toggling -->
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav" id="categoryUI">
								<!-- <li class="active"><a href="product_list.htm">手机数码<span class="sr-only">(current)</span></a></li>
								<li><a href="#">电脑办公</a></li>
								<li><a href="#">电脑办公</a></li>
								<li><a href="#">电脑办公</a></li> -->
							</ul>
							<form class="navbar-form navbar-right" role="search">
								<div class="form-group">
									<input type="text" class="form-control" placeholder="Search">
								</div>
								<button type="submit" class="btn btn-default">Submit</button>
							</form>

						</div>
						<!-- /.navbar-collapse -->
					</div>
					<!-- /.container-fluid -->
				</nav>
			</div>
			<script type="text/javascript">
		   $.post(
			   "${pageContext.request.contextPath }/category",
			  // {"category_list":json},
			  {"method":"findAllCategory"},
			   function(data){//data就是返回过来的json数组
				  //动态添加分类商品
				   if(data.length>0){
					   $.each(data,function(i,category){
						  $("#categoryUI").append("<li><a href='${pageContext.request.contextPath }/product?method=showProductListByCid&currentPage=1&cid="+category.cid+"'>"+category.cname+"</a></li>")
					 //$("<li><a href='#'>"+category.cname+"</a></li>").appendTo($("#categoryUL"))
					   //${pageContext.request.contextPath }/product?method=productListByCid&cid="+category.cid +
					   })
				   }
			   },
			   "json"
			   
			   
			  )
			 </script> 
</body>
             
</html>