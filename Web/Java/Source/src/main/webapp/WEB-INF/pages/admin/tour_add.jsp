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
		$('.datepicker').datepicker({
			format : "dd/mm/yyyy", // Notice the Extra space at the beginning
			
			autoclose : true
		});

		
		$('#userTableResult').pageMe({pagerSelector:'#userTablePager',showPrevNext:true,hidePageNumbers:false,perPage:5});
	});
</script>

</head>
<body>
	<input type="hidden" id="modal_notification_isShow" value='${flag_error}'/>
    <div id="modal_notification1" class="modal fade" role="dialog">
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
                            <p id="error_messages_addtour" >${error_input_add_tour} </p>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-info" data-dismiss="modal">Ok</button>
                </div>
            </div>
        </div>
    </div>
	<div id="modal_choosePlace" class="modal fade " role="dialog">
		<div class="modal-dialog modal-lg">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" style="text-align: left;">Địa điểm du lịch </h4>
				</div>
				<div style="margin-left: 15px;" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-12">
								<div class="panel-collapse collapse in">
									<div class="panel-body">
											<div class="row">
												<div class="col-xs-12">
													<div class="panel panel-primary">
														<div class="panel-heading">
															<h3 class="panel-title">Kết quả</h3>
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
																						
																						<td>
																							<%-- <a href="./getInformationRegistration?key_user=${registrationForm.key_user}&key_tour=${registrationForm.key_tour}&key_registration=${registrationForm.key}">
																								<button name="" class="btn btn-ref pull-right">Thông Tin
																								</button>
																							</a> --%>
																						</td>
																						<td>		
																							<button onclick="deleteRegistration(${registrationForm.key})" name="" class="btn btn-red pull-right" data-toggle="modal" data-target="#modal_deleteRegistration">Xóa
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
										
									</div>
								</div>
								</div>
							</div>
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
								<h1 class="page-header">QUẢN LÝ TOUR</h1>
							</div>
						</div>
					</div>
				</div>

				<c:if test="${errorMessages != null && errorMessages != '' && errorMessages != 'successfully' }">
					<div class="row alert alert-danger" role="alert" style="margin-left: 0px !important;">
						<div class="col-md-10 " >
							<p id="error_messages_edit" >${errorMessages }</p>
						</div>
					</div>
				</c:if>
				<c:if test="${errorMessages == 'successfully' }">
					<div class="row alert alert-success" role="alert" style="margin-left: 0px !important;">
						<div class="col-md-10 " >
							<p id="error_messages_edit" >Thêm thành công</p>
						</div>
					</div>
				</c:if>
				
				<form:form action="addTour" method="post" enctype="multipart/form-data"  modelAttribute="tourFormAdd" id= "addTourForm">
					<!-- pannel -->
					<div class="row">
						<div class="col-xs-12">
							<div class="panel panel-primary">
								<div class="panel-heading">
									<h3 class="panel-title">Thêm tour mới</h3>
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
                                                        <form:input path="ngay_di" type='text' class="form-control datepicker" id="add_date_start_tour" /> 
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
                                                        <form:input type='text'  class="form-control datepicker" path="ngay_ve"   id="add_date_end_tour"/>
													</div>
												</div>
											</div>
										</div>
										
											<div class="row">
												<div style="margin-top: 15px;" class="col-xs-2">
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
														<div class="panel-body">
															<form:textarea   path="gioi_thieu" id = "introduce" class="form-control" required=""/>
															<%-- <form:input type='textarea'  path = "introduce" class="form-control" id="introduce" /> --%>
														</div>
													</div>
												</div>
											</div>
										 
										<div class="row">
											<div style="margin-top: 10px;" class="col-xs-2">
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
													<div class="panel-body">
                                                        <form:textarea  path= "chuong_trinh" class="form-control"  id="program" required="" />
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
											<div class="row">
												<div style="margin-top: 10px;" class="col-xs-2">
													<div class="panel panel-info">
														<div class="panel-body bg-info">
															<h5>
																Hình ảnh
															</h5>
														</div>
													</div>
												</div>
												<div class="col-xs-4" style="padding-left: 0px !important; margin-top: 15px;">
													<div class="panel panel-info">
														<div class="panel-body">
                                                             <input  type="file"   name="fileUpload" size="50" />    
															<%-- <form:textarea  class="col-xs-12" path="image" id ="image" /> --%>
														</div>
													</div>
												</div>				
											</div>
											
									</div>
									<div class="row btn_center">
										<div class="col-xs-12" style="padding-right: 0px;">
											<div class="panel panel-info">
												<div class="panel-footer">

													<button type="submit" name="action" value="chooseplace"
														class="btn btn-reg" >Địa Điểm du lịch</button>
													<button type="button" onclick="addTourClear()"
														class="btn btn-reg" >Reset</button>	
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form:form>
				<!-- result search -->
				<br>
				
				<br>
			</div>
		</div>
	</div>	
</body>
</html>

