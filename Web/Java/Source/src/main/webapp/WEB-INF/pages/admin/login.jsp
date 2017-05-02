<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html class=no-js>
	<head>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<meta charset=utf-8>
		<title>Your Tour - Admin</title>
		<meta name=description> <meta name=viewport content="width=device-width">
		<link rel=stylesheet href=<c:url value="/admin_scripts/oldieshim.js" />>

		<link rel=stylesheet href=<c:url value="/admin_styles/main.css" /> >	

	</head>
	<style>
		#logo {
			   position: absolute;
			   top: 10px;
			   left: 10px; 
			}
	</style>
	<body >
			<c:set var="userType" value="-1"
				 scope="session"/>
			 <c:set var="userId" value="-1"
			 scope="session"/>
		<div class=ui-base>
			<div class=login-page>
			    <div class=img-container>
			        <div class="text-center pull-right photo"><img src=<c:url value="/admin_images/logo.png" />
			                                                       class="user-avatar img-circle img-responsive">
			            <h1>YOUR TRIP<br> <span>TOUR DU LICH GIÁ RẺ</span> <br>
			                <small> <br> CÀNG ĐI CÀNG THÍCH !</small>
			            </h1>
			        </div>
			    </div>
			    <div class=form-content>
			    	
			    		<img id="logo" src=<c:url value="/user/images/logo.png"/>>
			    
			    	
			        <form role=form class=bottom-75 method="post" action="home">
			            <div class=table-form>
			                <div class=form-groups>
			                    <div class=form-group><input type=text class="form-control input-lg" name="username" placeholder=USERNAME></div>
			                    <div class=form-group><input type=password class="form-control input-lg" name="password" placeholder=PASSWORD></div>
			                </div>
			                <div class=button-container>
			                    <button type=submit class="btn btn-default login"><img src=<c:url value="/admin_images/arrow.png"/>></button>
			                </div>
			            </div>
			            <!-- <div class=remember><label class=checkbox1 for=Option> <input id=Option type=checkbox> <span></span>
			            </label> remember me <span class=pass> forgot password?</span></div> -->
			            <b style="color:red"><i><c:if test="${not empty error }">(*)Đăng nhập thất bại!</c:if></i></b>
			        </form>
			    </div>
			</div>
		</div>
	
		<script src=<c:url value="/admin_scripts/oldieshim.js" />></script>
		<script src=<c:url value="/admin_scripts/vendor.js" /> > </script>
		<script src=<c:url value="/admin_scripts/scripts.js" /> ></script>
	</body>
</html>

