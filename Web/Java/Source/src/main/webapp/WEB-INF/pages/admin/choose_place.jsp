<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta charset=utf-8>
<title>Your Tour - Admin</title>
<meta name=description>
<meta name=viewport content="width=device-width">

<link rel=stylesheet href=<c:url value="/admin_styles/menu.css" />>
<link rel=stylesheet href=<c:url value="/admin_styles/bootstrap.min.css" />>
<link rel=stylesheet href=<c:url value="/admin_styles/font-awesome.min.css" />>
<link rel=stylesheet href=<c:url value="/admin_styles/output.css" />>
<link rel=stylesheet href=<c:url value="/admin_styles/style.css" />>
<link rel=stylesheet href=<c:url value="/admin_styles/bootstrap-datepicker.min.css" />>


<script src=<c:url value="/admin_scripts/jquery-3.1.1.js" />></script>
<script src=<c:url value="/admin_scripts/bootstrap.min.js" />></script>
<script src=<c:url value="/admin_scripts/output.js" />></script>
<script src=<c:url value="/admin_scripts/scripts.js" />></script>
<script src=<c:url value="/admin_scripts/bootstrap-datepicker.min.js" />></script>


<script>
$(function() {
    $('#userTableResultHotel').pageMe({pagerSelector:'#userTablePagerHotel',showPrevNext:true,hidePageNumbers:false,perPage:5});
    $('#userTableResult').pageMe({pagerSelector:'#userTablePager',showPrevNext:true,hidePageNumbers:false,perPage:5});
    $('#userTableResultRestaurant').pageMe({pagerSelector:'#userTablePagerRestaurant',showPrevNext:true,hidePageNumbers:false,perPage:5});
});
</script>
</head>
<body>
    <div id="modal_notification" class="modal fade" role="dialog">
        <div class="modal-dialog modal-sm" style="width: 400px !important; ">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Thông báo</h4>
                </div>
                <div class="modal-body">
                      <div class="row alert alert-danger" role="alert" style="margin-left: 0px !important;">
                        <div class="col-md-10 " >
                            <p id="error_messages_chooseplacetour" > </p>
                            
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-info" data-dismiss="modal">Ok</button>
                </div>
            </div>

        </div>
    </div>
    
	    <div id="modal_chooseRestaurantHotel" class="modal fade " role="dialog">
        <div class="modal-dialog modal-lg">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" style="text-align: left;">Nhà Hàng,Khách Sạn bạn cần chọn</h4>
                </div>
                <div class="modal-body">
                    <div class="row" style="margin-top: 20px;">
                        <div class="col-xs-12">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Danh sách Khách Sạn</h3>
                                    <span class="pull-right clickable"> <i
                                        class="glyphicon glyphicon-chevron-up"> </i>
                                    </span>
                                </div>
                                <div class="panel-collapse collapse in">
                                    <div class="panel-body">
                                    
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <table class="table table-striped table-condensed" id="table">
                                                    <thead>
                                                        <tr>
                                                            <th style="width: 20%;">Mã</th>
                                                            <th style="width: 20%;">Tên</th>
                                                            <th style="width: 20%;">Loại</th>
                                                            <th style="width: 20%;">Địa điểm</th>
                                                            
                                                            <th style="width: 20%;"></th>
                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody id="userTableResultHotel">
                                                        <%-- <c:if test = "${isEdited==true}">
                                                            <c:forEach var="place" items="${placeListbyId}">
                                                                <tr>
                                                                    <td>${place.ma}</td>
                                                                    <td>${place.ten}</td>
                                                                    <td>${place.gia}</td>
                                                                    <td>${place.dia_diem}</td>
                                                                    <td>${place.thong_tin}</td>
                                                                </tr>
                                                            </c:forEach> 
                                                        
                                                        </c:if> --%>
                                                        
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="col-md-12 text-center">
                                              <ul class="pagination" id="userTablePagerHotel"></ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>    
                        </div>
                    </div>
                </div>
                
                <div class="modal-body">
                    <div class="row" style="margin-top: 20px;">
                        <div class="col-xs-12">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Danh sách Nhà Hàng</h3>
                                    <span class="pull-right clickable"> <i
                                        class="glyphicon glyphicon-chevron-up"> </i>
                                    </span>
                                </div>
                                <div class="panel-collapse collapse in">
                                    <div class="panel-body">
                                    
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <table class="table table-striped table-condensed" id="table">
                                                    <thead>
                                                        <tr>
                                                            <th style="width: 20%;">Mã</th>
                                                            <th style="width: 30%;">Tên</th>
                                                            <th style="width: 20%;">Loại</th>
                                                            <th style="width: 20%;">Địa Chỉ</th>
                                                            <th style="width: 10%;"></th>
                                                            
                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody id="userTableResultRestaurant">
                                                        <%-- <c:if test = "${isEdited==true}">
                                                            <c:forEach var="place" items="${placeListbyId}">
                                                                <tr>
                                                                    <td>${place.ma}</td>
                                                                    <td>${place.ten}</td>
                                                                    <td>${place.gia}</td>
                                                                    <td>${place.dia_diem}</td>
                                                                    <td>${place.thong_tin}</td>
                                                                </tr>
                                                            </c:forEach> 
                                                        
                                                        </c:if> --%>
                                                        
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="col-md-12 text-center">
                                              <ul class="pagination" id="userTablePagerRestaurant"></ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>    
                        </div>
                    </div>
                </div>
                <div class="row">
					<div class="col-xs-12">
							<button  type="button"  class="btn btn-ref pull-right" data-dismiss="modal">Thoát</button>
					</div>
				</div>
				
            </div>
        </div>
    </div> 
	
	<div class="container-fluid" style="padding-left: 0px !important;">
		<div class="row">
			<!-- nav -->
			<jsp:include page="components/nav_manager.jsp" />


			<!-- content -->

			<div class="col-sm-10">

				<div class="row">
					<div class="container-fluid">
						<div class="row reset_height">
							<div class="main">
								<h1 class="page-header">QUẢN LÝ ${TITLE} </h1>
							</div>
						</div>
					</div>
				</div>

				<!--  
				<div class="row">
					<div class="col-md-10">
						<p>${errorMessages }</p>
					</div>
				</div>
				-->
				<form:form action="addTour" method="get" >
					<!-- pannel -->
					<div class="row">
						<div class="col-xs-12">
							<div class="panel panel-primary">
								<div class="panel-heading">
									<h3 class="panel-title">Điều kiện tìm kiếm</h3>
									<span class="pull-right clickable"> <i
										class="glyphicon glyphicon-chevron-up"> </i>
									</span>
								</div>
									<div class="panel-collapse collapse in">
										<div class="panel-body">
											<div class="row">
												<div class="col-xs-2">
													<div class="panel panel-info">
														<div class="panel-body bg-info">
															<h5>Tên<font color="red"></font></h5>
														</div>
													</div>
												</div>
												<div class="col-xs-8">
													<div class="panel panel-info">
														<div class="panel-body">
															 <input type="text" name="searchplace"class="form-control " >
														</div>
													</div>
												</div>
													<button style="margin-top: 5px" type="submit" name="action" value="searchplace" class="btn btn-primary"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
											</div>
										 
										</div>
								</div>
							</div>
						</div>
					</div>
				</form:form>
				<!-- result search -->
				<br>	
				<div class="row">
					<div class="col-xs-12">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h3 class="panel-title">Kết quả</h3>
								<span class="pull-right clickable"> <i
									class="glyphicon glyphicon-chevron-up"> </i>
								</span>
							</div>
							<div class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="row">
										<div class="col-xs-12">
											<span class="col-xs-6">Kết quả tìm kiếm： ${countHotel} record </span> <span class="col-xs-6"><p class="text-right">
													<font color="red"></font>
												</p> </span>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12">
											<table class="table table-striped table-condensed" id="table">
												<thead>
													<tr>
														<th style="width: 5%;">Mã</th>
														<th style="width: 10%;">Tên</th>
														<th style="width: 10%;">Giá</th>
														<th style="width: 10%;">Địa điểm</th>
														<th style="width: 60%;">Thông tin</th>
														
														<th style="width: 5%;"></th>
													</tr>
												</thead>
												<tbody id="userTableResult">
													<c:forEach var="place" items="${placeList}">
														<tr>
															<td>${place.ma}</td>
															<td>${place.ten}</td>
															<td>${place.gia}</td>
															<td>${place.dia_diem}</td>
															<td>${place.thong_tin}</td>
															<%-- <td>
																<a href="./getInformationRegistration?key_user=${registrationForm.key_user}&key_tour=${registrationForm.key_tour}&key_registration=${registrationForm.key}">
																	<button name="" class="btn btn-ref pull-right">Thông Tin
																	</button>
																</a>
															</td> --%>
															<td>	
																<input type="checkbox" name="chooseplace"  value = "${place.ma}" onclick="chooseRestaurantHotel(this,${place.ma})" >
																<%-- <button onclick="deleteRegistration(${registrationForm.key})" name="" class="btn btn-red pull-right" data-toggle="modal" data-target="#modal_deleteRegistration">Xóa
																</button> --%>
															</td>
												
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
										<div class="col-md-12 text-center">
									      <ul class="pagination" id="userTablePager"></ul>
									    </div>
									</div>
									
									<div class="row">
										<div class="col-xs-6">
												<button  type="button" onclick="checkbox()" name="" class="btn btn-ref pull-right" >Chọn
												</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>

