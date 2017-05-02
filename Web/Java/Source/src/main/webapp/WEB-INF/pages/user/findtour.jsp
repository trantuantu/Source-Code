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
								<li class="current"><a href="findtour">Tìm kiếm tour</a></li>	
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
					<h3>Tìm kiếm tour:</h3>
					<i>Lưu ý: Bỏ trống các thông tin không quan tâm.</i><br><br>
					<form id="bookingForm" action="listtour" method="post">
						<div class="fl1">
							<div class="tmInput">
								<i>Tên Tour:</i>
							</div>
							<div class="tmInput">
								<input name="name" placeHolder="Vd: Nha Trang - Đà Lạt" type="text" >
							</div>
						</div>
						<div class="fl1">
							<div class="tmInput">
								<i>Số ngày:</i>
							</div>
							<div class="tmInput">
								<input name="duration" placeHolder="Vd: 5" type="text" >
							</div>
						</div>
						<div class="clear"></div>
						<div class="tmInput">
							<i>Ngày đi:</i>
						</div>
						<label class="tmDatepicker">
							<input type="text" name="check-in" placeHolder="MM/DD/YYYY">
						</label>
						<div class="clear"></div>
						<div class="fl1">
							<div class="tmInput">
								<i>Giá:</i>
							</div>
							<div class="tmInput mr0">
								<input name="cost" placeHolder="Vd: 1000000" type="text" >
							</div>				
						</div>
						<div class="clear"></div>
						<div class="tmRadio">
							<input name="filter" type="radio" id="tmRadio0" checked/ value="max">
							<span>Cao nhất</span>
							<input name="filter" type="radio" id="tmRadio1" value="equal" />
							<span>Đúng bằng</span>
							<input name="filter" type="radio" id="tmRadio2" value="min"/>
							<span>Thấp nhất</span>
						</div>	
						<div class="clear"></div>
						<input class="btn" type="submit" value="Tìm"/>
					</form>
			
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