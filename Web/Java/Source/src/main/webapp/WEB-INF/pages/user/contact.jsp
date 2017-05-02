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
								<li><a href="findtour">Tìm kiếm tour</a></li>	
								<li class="current"><a href="contact">Liên hệ</a></li>
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
				<div class="grid_5">
					<h3>Thông tin liên hệ</h3>
					<div class="map">
						<figure class="">
							<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.6343350735315!2d106.67983811480072!3d10.762639092330923!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752f1c06f4e1dd%3A0x43900f1d4539a3d!2sUniversity+of+Science%2C+Downtown+Campus!5e0!3m2!1sen!2s!4v1483266876775" width="600" height="450" frameborder="0" style="border:0" allowfullscreen></iframe>						</figure>
						<address>
							<dl>
								<dt>Trường Đại học Khoa học Tự nhiên <br>
									227 Nguyễn Văn Cừ,<br>
									Phường 4, Quận 5, Thành phố Hồ Chí Minh.
								</dt>
								<dd><span>Tổng đài điện thoại:</span> (08) 62 884 499 – (08) 73 089 899</dd>
								<dd><span>Điện thoại:</span> (08) 38 353 193</dd>
								<dd><span>FAX:</span> (08) 38 350 096</dd>
								<dd>E-mail: <a href="http://www.hcmus.edu.vn/" class="col1">http://www.hcmus.edu.vn/</a></dd>
							</dl>
						</address>
					</div>
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