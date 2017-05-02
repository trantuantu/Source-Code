<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<script src=<c:url value="/admin_scripts/jquery.tablesorter.min.js" />></script>
<link rel=stylesheet href=<c:url value="/admin_styles/sort/style.css" />>


<script>
$(function() {	
    $('#userTableLoadResultHotel').pageMe({pagerSelector:'#userTablePagerLoadResultHotel',showPrevNext:true,hidePageNumbers:false,perPage:5});
    $('#userTableResult').pageMe({pagerSelector:'#userTablePager',showPrevNext:true,hidePageNumbers:false,perPage:5});

    $('#userTableLoadResultRestaurant').pageMe({pagerSelector:'#userTablePagerLoadResultRestaurant',showPrevNext:true,hidePageNumbers:false,perPage:5});
    $("#table1").tablesorter({sortList: [[0,0]],  headers: { 
        6: { sorter: false }, 
        7: { sorter: false }
        } }); 
});
$(function() {	
	$('#userTableResult1').pageMe({pagerSelector:'#userTablePager1',showPrevNext:true,hidePageNumbers:false,perPage:5});
});
</script>
</head>
<body>
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
								<h1 class="page-header">QUẢN LÝ TOUR</h1>
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
                <form:form action="tour" method="get">
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
                                                     <input type="text" name="searchtour"class="form-control " >
												</div>
											</div>
										</div>
										 <button style="margin-top: 5px" type="submit" name="action" value="searchtour" class="btn btn-primary"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
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
											<span class="col-xs-6">Kết quả tìm kiếm： ${countTour} record </span> <span class="col-xs-6"><p class="text-right">
													<font color="red"></font>
												</p> </span>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12">
                                            <table class="table table-striped table-condensed tablesorter" id="table1">
												<thead>
													<tr>
														<th style="width: 5%;">Mã</th>
														<th style="width: 20%;">Tên</th>
														<th style="width: 20%;">Ngày đi</th>
														<th style="width: 20%;">Ngày về</th>
														<th style="width: 15%;">Số vé còn lại</th>
														<th style="width: 15%;">Giá tiền</th>
														<th style="width: 5%;"></th>
														<th style="width: 5%;"></th>
													</tr>
												</thead>
												<tbody id="userTableResult">
													<c:forEach var="tour" items="${listTour}">
														<tr>
															<td>${tour.ma}</td>
															<td>${tour.ten}</td>
															<td>${tour.ngay_di}</td>
															<td>${tour.ngay_ve}</td>
															<td>${tour.so_ve_con_lai}</td>
															<td><fmt:formatNumber type="number" value="${tour.gia}"
																groupingUsed="true" /> VNĐ</td>
															
															
															<td>
																<button  type = "button" onclick="loadTourById(${tour.ma})" class="btn btn-ref pull-right" data-toggle="modal" data-target="#modal_editTour">Chi Tiết
																</button>
															</td>
															<td>		
                                                                <button  onclick="deleteTour(${tour.ma})" class="btn btn-red pull-right" data-toggle="modal" data-target="#modal_deleteTour">Xóa
																</button>
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
								</div>
							</div>
						</div>
					</div>
				</div>
				<br>
			</div>
		</div>
	</div>
	
    <div id="modal_deleteTour" class="modal fade" role="dialog">
		<div class="modal-dialog modal-sm" style="width: 400px !important; ">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Xác nhận xóa</h4>
				</div>
				<div class="modal-body">
                    <p>Bạn có muốn xóa tour có mã là <span id="showId" ></span> không?</p>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info" data-dismiss="modal">Thoát</button>
        			
                    <form:form method="post" action="deleteTour" modelAttribute="tourFormDelete" class="btn">
                            <form:input path="ma" type="hidden" class="btn-select-input" id="hidden_idTour" name="" value="" />
						<input type="submit" name="action" value="Xác nhận" class="btn btn-danger" />
					</form:form >
				</div>
			</div>

		</div>
	</div>
    <input type="hidden" id="modal_editTour_isShow" value='${isEdited}'/> 
	<div id="modal_editTour" class="modal fade " role="dialog">
		<div class="modal-dialog modal-lg">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" style="text-align: left;">Thông tin chi tiết</h4>
				</div>
				<form:form action="editTourOrigion" method="post" modelAttribute="tourFormEdit">
					<div class="modal-body">
						<c:if test="${errorMessages != null && errorMessages != '' && errorMessages != 'successfully' }">
							<div class="row alert alert-danger" role="alert" style="margin-left: 0px !important;">
								<div class="col-md-10 " >
                                    <p id="error_messages_edittour" >${errorMessages }</p>
								</div>
							</div>
						</c:if>
						<c:if test="${errorMessages == 'successfully' }">
							<div class="row alert alert-success" role="alert" style="margin-left: 0px !important;">
								<div class="col-md-10 " >
                                    <p id="error_messages_edittour" >Sửa thành công</p>
								</div>
							</div>
						</c:if>
						
						<form:errors path="*" cssClass="errorblock"/>
						<!-- pannel -->
						<div class="row" >
							<div class="col-xs-12">
								<div class="panel panel-primary">
									<div class="panel-heading">
										<h3 class="panel-title">Thông tin tour</h3>
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
															<h5>
																Tên <font color="red"></font>
															</h5>
														</div>
													</div>
												</div>
												<div class="col-xs-4">
													<div class="panel panel-info">
														<div class="panel-body">
															<form:input type='text' class="form-control"  path="ten" id="name_tour"/>
															<form:hidden  class="form-control"  path="ma" id="ma"/>
														</div>
													</div>
												</div>
												<div class="col-xs-2 padding_reset">
													<div class="panel panel-info">
														<div class="panel-body bg-info">
															<h5>Tổng Vé<font color="red"></font></h5>
														</div>
													</div>
												</div>
												<div class="col-xs-4 reset_padding_right">
													<div class="panel panel-info">
														<div class="panel-body">
															<form:input type='text' class="form-control"  path="so_ve" id="total_ticket"/>
														</div>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-xs-2">
													<div class="panel panel-info">
														<div class="panel-body bg-info">
															<h5>
																Ngày đi
															</h5>
														</div>
													</div>
												</div>
												<div class="col-xs-4 ">
													<div class="panel panel-info">
														<div class="panel-body">
															<form:input path="ngay_di" type='text' class="form-control datepicker" id="date_start" /> 
														</div>
													</div>
												</div>
																	
												<div class="col-xs-2 padding_reset">
													<div class="panel panel-info">
														<div class="panel-body bg-info">
															<h5>
																Ngày về <font color="red"></font>
															</h5>
														</div>
													</div>
												</div>
										
												<div class="col-xs-4">
													<div class="panel panel-info">
														<div class="panel-body">
															<form:input type='text'  class="form-control datepicker" path="ngay_ve"   id="date_end"/>
														</div>
													</div>
												</div>
											</div>
											
												<div class="row">
													<div style="margin-top: 20px;" class="col-xs-2">
														<div class="panel panel-info">
															<div class="panel-body bg-info">
																<h5>
																	Giới thiệu <font color="red"></font>
																</h5>
															</div>
														</div>
													</div>
													<div class="col-xs-10" style="padding-left: 0px !important;">
														<div class="panel panel-info">
															<div class="panel-body" style="height: 80px;">
																<form:textarea  style="height: 80px;" class="form-control" path="gioi_thieu" id = "introduce" required=""/>
																<%-- <form:input type='textarea'  path = "introduce" class="form-control" id="introduce" /> --%>
															</div>
														</div>
													</div>
												</div>
											 
											<div class="row">
												<div style="margin-top: 35px;" class="col-xs-2">
													<div class="panel panel-info">
														<div class="panel-body bg-info">
															<h5>
																Chương Trình <font color="red"></font>
															</h5>
														</div>
													</div>
												</div>
												<div class="col-xs-10" style="padding-left: 0px !important;">
													<div class="panel panel-info">
														<div class="panel-body"  style="height: 120px;">
															<form:textarea style="height: 120px;" path= "chuong_trinh" class="form-control" id="program" required="" />
															<%-- <form:input type='text' class="form-control"  path="program" id ="hidden_program"/> --%>
														</div>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-xs-2">
													<div class="panel panel-info">
														<div class="panel-body bg-info">
															<h5>
																Phương Tiện <font color="red"></font>
															</h5>
														</div>
													</div>
												</div>
												<div class="col-xs-4" style="padding-left: 0px !important;">
													<div class="panel panel-info">
														<div class="panel-body">
															<form:input type='text' class="form-control" path="phuong_tien" id ="expediency"/>
														</div>
													</div>
												</div>
													<div class="col-xs-2 padding_reset">
													<div class="panel panel-info">
														<div class="panel-body bg-info">
															<h5>Giá Tiền<font color="red"></font></h5>
														</div>
													</div>
												</div>
												<div class="col-xs-4 reset_padding_right">
													<div class="panel panel-info">
														<div class="panel-body">
															<form:input type='text' class="form-control"  path="gia" id="prices"/>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<input type="submit"  class="btn btn-primary" value="Lưu" class="btn btn-reg" />
										<button type="button" class="btn btn-default" data-dismiss="modal">Thoát</button>
									</div>
								</div>	
							</div>
						</div>
					</div>
				</form:form>
				<div class="modal-body">
					<div class="row" style="margin-top: 20px;">
						<div class="col-xs-12">
							<div class="panel panel-primary">
								<div class="panel-heading">
									<h3 class="panel-title">Danh sách địa điểm du lịch</h3>
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
															<th style="width: 5%;">Mã</th>
															<th style="width: 10%;">Tên</th>
															<th style="width: 8%;">Giá</th>
															<th style="width: 10%;">Địa điểm</th>
															<th style="width: 57%;">Thông tin</th>
                                                            <th style="width: 10%;"></th>
																													
														</tr>
													</thead>
													<tbody id="userTableResult1">
		                                                 <c:if test = "${isEdited==true}">
                                                            <c:forEach var="place" items="${placeListbyId}">
                                                                <tr>
                                                                    <td>${place.ma}</td>
                                                                    <td>${place.ten}</td>
                                                                    <td>${place.gia}</td>
                                                                    <td>${place.dia_diem}</td>
                                                                    <td>${place.thong_tin}</td>
                                                                </tr>
                                                            </c:forEach>
                                                        </c:if>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="col-md-12 text-center">
                                              <ul class="pagination" id="userTablePager1"></ul>
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
    </div>
    
    <div id="modal_loadhotel" class="modal fade " role="dialog">
        <div class="modal-dialog modal-lg">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" style="text-align: left;">Thông tin chi tiết</h4>
                </div>
                <div class="modal-body">
                    <div class="row" style="margin-top: 20px;">
                        <div class="col-xs-12">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Danh sách các khách sạn</h3>
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
                                                            <th style="width: 30%;">Địa chỉ</th>
                                                            <th style="width: 30%;">Loại</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="userTableLoadResultHotel">
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
                                              <ul class="pagination" id="userTablePagerLoadResultHotel"></ul>
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
    </div>
    <div id="modal_loadRestaurant" class="modal fade " role="dialog">
        <div class="modal-dialog modal-lg">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" style="text-align: left;">Thông tin chi tiết</h4>
                </div>
                <div class="modal-body">
                    <div class="row" style="margin-top: 20px;">
                        <div class="col-xs-12">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Danh sách các nhà hàng</h3>
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
                                                            <th style="width: 30%;">Địa chỉ</th>
                                                            <th style="width: 30%;">Loại</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="userTableLoadResultRestaurant">
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
                                              <ul class="pagination" id="userTablePagerLoadResultRestaurant"></ul>
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
	</div>
</body>
</html>

