<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
			jQuery('#camera_wrap').camera({
				loader: false,
				pagination: false ,
				minHeight: '444',
				thumbnails: false,
				height: '48.375%',
				caption: true,
				navigation: true,
				fx: 'mosaic'
			});
			/*carousel*/
			var owl=$("#owl");
				owl.owlCarousel({
				items : 2, //10 items above 1000px browser width
				itemsDesktop : [995,2], //5 items between 1000px and 901px
				itemsDesktopSmall : [767, 2], // betweem 900px and 601px
				itemsTablet: [700, 2], //2 items between 600 and 0
				itemsMobile : [479, 1], // itemsMobile disabled - inherit from itemsTablet option
				navigation : true,
				pagination : false
				});
			$().UItoTop({ easingType: 'easeOutQuart' });
			});
		</script>
	</head>
	<body class="page1" id="top">
<!--==============================header=================================-->
		<header>
			<div class="container_12">
				<div class="grid_12">
					<div class="menu_block">
						<nav class="horizontal-nav full-width horizontalNav-notprocessed">
							<ul class="sf-menu">
								<li class="current"><a href="index">Trang chủ</a></li>	
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
		<div class="slider_wrapper">
			<div id="camera_wrap" class="">
				<c:forEach var="tour" items="${toursRD}" >
			        <div data-src="<%-- ${tour.getHinh_anh()} --%><c:url value="/user/images/slide.jpg"/>" >
						<div class="caption fadeIn">
							<h2>${tour.getTen()}</h2>
							<div class="price">
								${tour.getCo()} Ngày
								<span><fmt:formatNumber type="number" value="${tour.getGia()}"
											groupingUsed="true" /> VNĐ</span>
							</div>
							<a href="detailtour?id=${tour.getMa()}">Chi tiết</a>
						</div>
					</div>
		    	</c:forEach>
			</div>
		</div>
<!--==============================Content=================================-->
		<div class="content">
			<div class="container_12">
				<c:forEach var="tour" items="${toursShow}" >
					<div class="grid_4">
						<div class="banner">
							<img src="image/tour?id=${tour.getMa()}" alt=""  width="350" height="364" >
							<div class="label">
								<div class="title">
									<fmt:formatNumber type="number" value="${tour.getGia()}"
											groupingUsed="true" /> VNĐ
								</div>
								<div class="price">
									${tour.getCo()} Ngày
									<span>${tour.getTen()}</span>
								</div>
								<a href="detailtour?id=${tour.getMa()}">Chi tiết</a>
							</div>
						</div>
					</div>
		    	</c:forEach>
				
				
				
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
			$(function() {
				$('#bookingForm input, #bookingForm textarea').placeholder();
			});
		</script>
	</body>
</html>

