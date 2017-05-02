<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Your Tour</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" href=<c:url value="/user/booking/css/booking.css"/> >
		<link rel="stylesheet" href=<c:url value="/user/css/camera.css"/> >
		<link rel="stylesheet" href=<c:url value="/user/css/owl.carousel.css"/> >
		<link rel="stylesheet" href=<c:url value="/user/css/style.css"/> >
		<script src=<c:url value="/user/js/jquery.js" /> ></script>
		<script src=<c:url value="/user/js/jquery-migrate-1.2.1.js"/> ></script>
		<script src=<c:url value="/user/js/script.js"/> ></script>
		<script src=<c:url value="/user/js/superfish.js"/> ></script>
		<script src=<c:url value="/user/js/jquery.ui.totop.js"/> ></script>
		<script src=<c:url value="/user/js/jquery.equalheights.js"/> ></script>
		<script src=<c:url value="/user/js/jquery.mobilemenu.js"/> ></script>
		<script src=<c:url value="/user/js/jquery.easing.1.3.js"/> ></script>
		<script src=<c:url value="/user/js/owl.carousel.js"/> ></script>
		<script src=<c:url value="/user/js/camera.js"/> ></script>
		<script src=<c:url value="/user/js/jquery.mobile.customized.min.js"/> ></script>
		<script src=<c:url value="/user/booking/js/jquery-ui-1.10.3.custom.min.js"/> ></script>
		<script src=<c:url value="/user/booking/js/jquery.fancyform.js"/> ></script>
		<script src=<c:url value="/user/booking/js/jquery.placeholder.js"/> ></script>
		<script src=<c:url value="/user/booking/js/regula.js"/> ></script>
		<script src=<c:url value="/user/booking/js/booking.js"/> ></script>

		<script>
		$(document).ready(function(){
			$().UItoTop({ easingType: 'easeOutQuart' });
		});
		</script>
		<style>
		.error-message {
		   color: red;
		   font-size:90%;
		   font-style: italic;
		}
		</style>
	</head>
	<body>
<!--==============================header=================================-->
		<header>
			<div class="container_12">
				<div class="grid_12">
					<div class="menu_block">
						<nav class="horizontal-nav full-width horizontalNav-notprocessed">
							<ul class="sf-menu">
								<li><a href="index">Trang chủ</a></li>	
								<li><a href="listtour">Danh sách Tour</a></li>
								<li><a href="findtour">Tìm kiếm tour</a></li>
								<li><a href="contact">Liên hệ</a></li>
							</ul>	
						</nav>
						<div class="clear"></div>
					</div>
				</div>
				<div class="grid_12">
					<h1>
						<a href="index">
							<img src=<c:url value="/user/images/logo.png"/> alt="Your Happy Family" >
						</a>
					</h1>
				</div>
			</div>
		</header>
<!--==============================Content=================================-->
		<div class="content">
			<div class="container_12">
				<div class="grid_6">
					<h3>Đăng kí tour:</h3>
					<form:form action="pushRegister" method="POST"
      					 modelAttribute="bookingForm">
      					<div class="grid_8">
	      					<div class="fl2">	
	      						<div class="tmInput">
									<form:errors path="ma_tour" class="error-message" />		
								</div>	
							</div>
						</div>
						<div class="clear"></div>
 						<div class="fl1">
 							<div class="tmInput">
								<i>Mã Tour:</i>
							</div>	
 							<div class="tmInput"> 
								<form:input path="ma_tour" placeHolder="" value="${TourId}" readonly="true"/>	
							</div>
						</div>
						<div class="fl1">
							<div class="tmInput">
								<i>Tên Tour:</i>
							</div>	
							<div class="tmInput"> 
								<form:input path="ten_tour" placeHolder="" value="${TourName}" readonly="true"/>
							</div>
						</div>
 						<div class="clear"></div>
 						<div class="fl1">
 							<div class="tmInput">
								<i>Số lượng vé:</i>
							</div>
							<div class="tmInput">
								<form:input path="so_luong" placeHolder=""/>
							</div>
						</div>
						<div class="fl1">
							<div class="tmInput">
								<form:errors path="so_luong" class="error-message" />
							</div>
						</div>
						<div class="clear"></div>
 						<div class="fl1">
 							<div class="tmInput">
								<i>Tên khách hàng:</i>
							</div>
							<div class="tmInput">
								<form:input path="ten_khach_hang" placeHolder=""/>
							</div>
						</div>
						<div class="fl1">
							<div class="tmInput">
								<form:errors path="ten_khach_hang" class="error-message" />
							</div>
						</div>
						<div class="clear"></div>
 						<div class="fl1">
 							<div class="tmInput">
								<i>Số điện thoại:</i>
							</div>
							<div class="tmInput">
								<form:input path="sdt" placeHolder=""/>
							</div>
						</div>
						<div class="fl1">
							<div class="tmInput">
								<form:errors path="sdt" class="error-message" />
							</div>
						</div>
						<div class="clear"></div>
 						<div class="fl1">
 							<div class="tmInput">
								<i>Số CMND:</i>
							</div>
							<div class="tmInput">
								<form:input path="cmnd" placeHolder=""/>
							</div>
						</div>
						<div class="fl1">
							<div class="tmInput">
								<form:errors path="cmnd" class="error-message" />
							</div>
						</div>
						<div class="clear"></div>
 						<div class="fl1">
 							<div class="tmInput">
								<i>Địa chỉ:</i>
							</div>
							<div class="tmInput">
								<form:input path="dia_chi" placeHolder=""/>
							</div>
						</div>
						<div class="fl1">
							<div class="tmInput">
								<form:errors path="dia_chi" class="error-message" />
							</div>
						</div>
						<div class="clear"></div>
						<div class="fl1">
							<br>
							<div class="tmCheckbox">
								<form:checkbox path="chinh_sua" value="chinh_sua" label="Chỉnh sửa Tour"/>
							</div>
						</div>	
						<div class="fl1">
							<div class="tmInput">
								<form:errors path="ghi_chu" class="error-message" />
							</div>
						</div>
						<div class="clear"></div>
 						<div class="tmTextarea">
							<form:textarea path="ghi_chu" placeHolder="Ghi chú:"/>
						</div>
						<div class="clear"></div>				
						<input class="btn" type="submit" value="Đăng ký"/>
   					</form:form>
				</div> 
			</div>
		</div>
<!--==============================footer=================================-->
		<footer>
			<div class="container_12">
				<div class="grid_12">
					<div class="socials">
						<a href="#" class="fa fa-facebook"></a>
						<a href="#" class="fa fa-twitter"></a>
						<a href="#" class="fa fa-google-plus"></a>     
					</div>
					<div class="copy">
						Your Trip (c) 2016 | Tour du lich giá rẻ | Càng đi càng thích !
					</div>
				</div>
			</div>
		</footer>
		<script>
		$(function (){
			$('#bookingForm').bookingForm({
				ownerEmail: '#'
			});
		})
		</script>
	</body>
</html>