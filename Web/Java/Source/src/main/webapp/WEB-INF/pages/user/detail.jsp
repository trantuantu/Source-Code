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
			$().UItoTop({ easingType: 'easeOutQuart' });
		});
		</script>
		<style>
			.message  {
	        font-size:90%;
	        color:blue;
	        font-style:italic;
        </style>
   }
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
				<div class="grid_7">
					<h3>${tour.getTen()}</h3>
					<div class="blog">
						<time>${tour.getNgay_di().substring(0,2)}<span>Th ${tour.getNgay_di().substring(3,5)}</span></time>
						<div class="extra_wrapper">
							<div class="text1 col1"> 
								Mã Tour: ${tour.getMa()}
							</div> 
							Phương tiện: ${tour.getPhuong_tien()}<br>
							Ngày về: ${tour.getNgay_ve()}<br>
							Giá vé: <fmt:formatNumber type="number" value="${tour.getGia()}" groupingUsed="true" /> VNĐ <br>
							Còn: ${tour.getSo_ve_con_lai()} vé 
							<c:choose>
							    <c:when test="${not empty message}">
							        <div class="message">${message}</div>
							    </c:when>    
							    <c:otherwise>
							        - <a href="registerTour?id=${tour.getMa()}" style="color:blue"><i>Đăng ký ngay</i></a>
							    </c:otherwise>
							</c:choose>			
						</div>
						<div class="clear"></div>
						<img src="image/tour?id=${tour.getMa()}" alt="" class="img_inner">
						<div class="text1 col1">Giới thiệu:</div><br>
						<p align="justify">
						${tour.getGioi_thieu()}
						</p>
						<div class="text1 col1">Chương trình:</div><br>
						<c:forEach var="i" begin="0" end="${places.size() - 1}" step="1" >
							<h6>Ngày ${i + 1}: ${places.get(i).getTen()} - ${places.get(i).getDia_diem()}</h6>
							<p align="justify">
							${places.get(i).getThong_tin()}
							</p>
							<p align="justify">
							${ct[i + 1]}
							</p>
							Giá vé: <fmt:formatNumber type="number" value="${places.get(i).getGia()}"
											groupingUsed="true" /> VNĐ <br>
							Nhà hàng: <c:forEach var="rest" items="${rests.get(i)}" >
										${rest.getTen()} ${rest.getLoai()}*<c:if test="${rests.get(i).indexOf(rest) < rests.get(i).size() - 1}">, </c:if>
									</c:forEach><br>
							Khách sạn: <c:forEach var="hotel" items="${hotels.get(i)}" >
										${hotel.getTen()} ${hotel.getLoai()}*<c:if test="${hotels.get(i).indexOf(hotel) < hotels.get(i).size() - 1}">, </c:if>
									</c:forEach>
							<img src="image/place?id=${places.get(i).getMa()}" alt="" class="img_inner">
						</c:forEach> 
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