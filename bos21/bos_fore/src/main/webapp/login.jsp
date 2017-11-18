<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>我的速运-登录</title>
		<link rel="stylesheet" type="text/css" href="plugins/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="css/public.css">
		<link rel="stylesheet" type="text/css" href="css/styleloginsign.css">
		<script src="plugins/jquery.min.js" type="text/javascript"></script>
		<script src="plugins/bootstrap.min.js" type="text/javascript"></script>
		<script src="plugins/jquery.validate.js" type="text/javascript"></script>
		<script src="js/loginsignup.js" type="text/javascript"></script>
	</head>
	<body style="background-color: #f2f2f2;">
		<div class="logincontent">
			<div class="loginnav">
				<nav class="nav navbar-default">
					<div class="container">
						<div class="navbar-header">
							<a class="navbar-brand" href="#"><img src="img/icon/logo.png"></a>
							<span class="logintitle">用户登录</span>
						</div>
					</div>
				</nav>
			</div>

			<section class="mainlogin">
				<div class="container">
					<div class="col-md-4 col-md-offset-7 loginbox">
						<h4>用户登录</h4>
						<form class="form-horizontal" id="loginForm" action="${pageContext.request.contextPath }/customerAction_login.action" method="post">
							<div id="format1">
								<div class="form-group">
									<label for="inputaccount" class="col-sm-3 control-label">手机号</label>
									<div class="col-sm-8">
										<input type="text" name="telephone" class="form-control" required placeholder="请输入手机号" 
											   data-msg-required="请输入手机号"  minlength="11"  data-msg-minlength="至少输入11个字符">
									</div>
								</div>
								<div class="form-group">
									<label for="inputpassword" class="col-sm-3 control-label">密码</label>
									<div class="col-sm-8">
										<input class="form-control" type="password" name="password" required placeholder="请输入密码"   
											data-msg-required="请输入密码"  minlength="6" data-msg-minlength="至少输入6个字符">
									</div>
								</div>
								<div class="form-group" style="margin-bottom: 0;">
									<label for="inputvalidate" class="col-sm-3 control-label">验证码</label>
									<div class="col-sm-5">
										<input type="text" name="checkcode" class="form-control" id="inputaccount" placeholder="请输入验证码">
									</div>
									<div class="col-sm-3">
										<img id="vcode"src="validatecode.jsp">
									</div>
								</div>
								<script type="text/javascript">
								    $("#vcode").click(function(){
								    	$("#vcode").attr("src","validatecode.jsp?"+new Date())
								    })
								</script>
								<div class="form-group">
									<div class="col-sm-offset-4 col-sm-4">
										<div class="checkbox">
											<input type="checkbox"><span class="size12">　记住用户名</span>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-offset-3 col-md-8">
								<a class="btn btn-danger"  href="javascript:$('#loginForm').submit();" type="submit">登录</a>
							</div>
							<%-- <div align="right">
					                <font color="red"><s:actionerror/></font>
					        </div> --%>
							<p class="text-right clearfix">还不是我们的会员？<b><a href="signup.html">立即注册</a></b></p>
						</form>
					</div>
				</div>
			</section>

			<footer>
				<section class="copyright size12">
					<div class="container">
						<p class="text-center">地址：北京市昌平区建材城西路金燕龙办公楼一层 邮编：100096 电话：400-618-4000 传真：010-82935100 </p>
						<p class="text-center">京ICP备08001421号京公网安备110108007702</p>
					</div>
				</section>
			</footer>
		</div>
	</body>
</html>