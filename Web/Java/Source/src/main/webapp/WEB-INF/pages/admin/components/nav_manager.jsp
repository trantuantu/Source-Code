<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-sm-2"> <!-- nav -->
		<div class="nav-side-menu">
		    <div class="brand">
			    <c:choose>
				    <c:when test="${userType == 0}">
				       QUẢN LÝ
				    </c:when>
				    <c:when test="${userType == 1}">
				        HƯỚNG DẪN VIÊN
				    </c:when>
				    <c:otherwise>
				        NHÂN VIÊN
				    </c:otherwise>
				</c:choose>
			</div>
		    <i class="fa fa-bars fa-2x toggle-btn" data-toggle="collapse" data-target="#menu-content"></i>
	       <div class="menu-list">
		  
		            <ul id="menu-content" class="menu-content collapse out">
		                <li>
		                  <a href="home">
		                  <i class="fa fa-dashboard fa-lg"></i>TRANG CHỦ
		                  </a>
		                </li>
						<!-- Quản lý nhân viên -->
						<c:if test="${userType == 0}">
			                <li data-toggle="collapse" data-target="#nhanvien" class="collapsed">
			                  <a href="user"><i class="fa fa-users fa-lg"></i> QUẢN LÝ NHÂN VIÊN</a> <span class="arrow"></span>
			                </li>  
			                <ul class="sub-menu collapse" id="nhanvien">
			                  <li ><a href="addUser">Thêm</a></li>
			                  <li ><a href="user">Xóa</a></li>
			                  <li ><a href="user">Sửa</a></li>
			                  <li ><a href="duyet_nghiphep">Duyệt nghỉ phép</a></li>
			                </ul>
		                </c:if>
		                
		                <!-- Quản lý tour -->
		                <c:if test="${userType == 0 || userType == 2}">
			                <li data-toggle="collapse" data-target="#tour" class="collapsed">
			                  <a href="tour"><i class="fa fa-lg fa-suitcase"></i>QUẢN LÝ TOUR</a><span class="arrow"></span>
			                </li>
			                <ul class="sub-menu collapse" id="tour">
			                  <li><a href="addTour">Thêm</li>
			                  <li><a href="tour">Xóa</li>
			                  <li><a href="tour">Sửa</li>
			                </ul>
			            </c:if>
			            
                         <!-- Quản lý địa điểm-->
                         <c:if test="${userType == 0 || userType == 2}">
	                        <li data-toggle="collapse" data-target="#diadiem" class="collapsed">
	                          <a href="place"><i class="fa fa-lg fa-bed"></i><span>QUẢN LÝ ĐỊA ĐIỂM</span></a> <span class="arrow"></span>
	                        </li>
	                        <ul class="sub-menu collapse" id="diadiem">
	                          <li ><a href="addPlace">Thêm</a></li>
	                          <li ><a href="place">Xóa</a></li>
	                          <li ><a href="place">Sửa</a></li>
	                        </ul>
                        </c:if>
			            

		                <!-- Quản lý nhà hàng -->
		                <c:if test="${userType == 0 || userType == 2}">
			                <li data-toggle="collapse" data-target="#nhahang" class="collapsed">
			                  <a href="restaurant"><i class="fa fa-lg fa-cutlery"></i>QUẢN LÝ NHÀ HÀNG</a> <span class="arrow"></span>
			                </li>
			                <ul class="sub-menu collapse" id="nhahang">
			                 <li ><a href="addRestaurant">Thêm</a></li>
			                  <li ><a href="restaurant">Xóa</a></li>
			                  <li ><a href="restaurant">Sửa</a></li>
			                </ul>
			            </c:if>
		                <!-- Quản lý khách sạn -->
		                <c:if test="${userType == 0 || userType == 2}">
			                <li data-toggle="collapse" data-target="#khachsan" class="collapsed">
			                 <a href="hotel"><i class="fa fa-lg fa-bed"></i>QUẢN LÝ KHÁCH SẠN</a> <span class="arrow"></span>
			                </li>
			                <ul class="sub-menu collapse" id="khachsan">
			                  <li ><a href="addHotel">Thêm</a></li>
			                  <li ><a href="hotel">Xóa</a></li>
			                  <li ><a href="hotel">Sửa</a></li>
			                </ul>
			             </c:if>
		                <!-- Quản lý xác nhận -->
		                <c:if test="${userType == 0 || userType == 2}">
			                <li data-toggle="collapse" data-target="#xacnhan" class="collapsed">
			                  <a><i class="fa fa-pencil-square-o fa-lg"></i>XÁC NHẬN</a><span class="arrow"></span>
			                </li>
			                <ul class="sub-menu collapse" id="xacnhan">
			                	<c:if test="${userType == 0}">
			                  		<li ><a href="duyet_tour">Duyệt tour</a></li>
					            </c:if>
					            <c:if test="${userType == 2}">
			                  		<li ><a href="registrations">Duyệt phiếu đăng ký</a></li>
			                  	</c:if>
			                </ul>
			            </c:if>
		                <!-- Quản lý thống kê -->
		                <c:if test="${userType == 0}">
			                <li data-toggle="collapse" data-target="#thongke" class="collapsed">
			                  <a href="thongketour"><i class="fa fa-bar-chart fa-lg"></i>THỐNG KÊ</a>
			                </li>
		               </c:if>
		               
		                <!-- Đăng ký nghỉ phép -->
		                <c:if test="${userType == 1 || userType == 2}">
			                 <li data-toggle="collapse" data-target="#nghiphep" class="collapsed">
			                  <a href="dangky_nghiphep"><i class="fa fa-pencil-square-o fa-lg"></i> ĐĂNG KÝ NGHỈ PHÉP</a>
		                  	</li>
		                 </c:if>
		                 
		                  <!-- Xem lịch hướng dẫn và nhập chi phí thực tế -->
		                  <c:if test="${userType == 1}">
			                 <li>
			                  <a href="tour_updatechiphi">
			                  <i class="fa fa-book fa-lg"></i> LỊCH HƯỚNG DẪN
			                  </a>
			                  </li>
		                  </c:if>
		                 <li>
		                  <a href="login">
		                  <i class="fa fa-sign-out fa-lg"></i> ĐĂNG XUẤT
		                  </a>
		                </li>
		            </ul>
		    </div>
		</div>
	</div>