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
<link rel=stylesheet href=<c:url value="/admin_styles/sort/style.css" />>


<script src=<c:url value="/admin_scripts/jquery-3.1.1.js" />></script>
<script src=<c:url value="/admin_scripts/bootstrap.min.js" />></script>
<script src=<c:url value="/admin_scripts/output.js" />></script>
<script src=<c:url value="/admin_scripts/scripts.js" />></script>
<script src=<c:url value="/admin_scripts/bootstrap-datepicker.min.js" />></script>
<script src=<c:url value="/admin_scripts/jquery.tablesorter.min.js" />></script>

<script>
	$(function() {
		$("#table").tablesorter({sortList: [[0,0]],  headers: { 
            9: { sorter: false }, 
            10: { sorter: false },
            11: { sorter: false }
            } }); 
		$('.datepicker').datepicker({
			format : " yyyy", // Notice the Extra space at the beginning
			viewMode : "years",
			minViewMode : "years",
			autoclose : true
		});
		$('#edit_NamSinhUser').datepicker({
			format : "dd/mm/yyyy", // Notice the Extra space at the beginning
			
			autoclose : true
		});
		
		$('#userTableResult').pageMe({pagerSelector:'#userTablePager',showPrevNext:true,hidePageNumbers:false,perPage:5});
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
								<h1 class="page-header">${TITLE} </h1>
							</div>
						</div>
					</div>
				</div>
				
				<form:form action="them_chiphi" method="get" modelAttribute="chiphiForm">
					<!-- pannel -->
					<div class="row">
						<div class="col-xs-12">
							<div class="panel panel-primary">
								<div class="panel-heading">
									<h3 class="panel-title">Nhập chi phí đi Tour</h3>
									<span class="pull-right clickable"> <i
										class="glyphicon glyphicon-chevron-up"> </i>
									</span>
								</div>

								<div class="panel-collapse collapse in">
									<div class="panel-body">
									<c:if test="${errorMessages != 'successfully' && errorMessages != '' }">
										<div class="row alert alert-danger" role="alert" style="margin-left: 0px !important;">
											<div class="col-md-10 " >
												<p id="error_messages_edit" >${errorMessages}</p>
											</div>
										</div>
									</c:if>
									<c:if test="${errorMessages == 'successfully' }">
										<div class="row alert alert-success" role="alert" style="margin-left: 0px !important;">
											<div class="col-md-10 " >
												<p id="error_messages_edit" >Nhập thành công</p>
											</div>
										</div>
									</c:if>
										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Mã Tour <font color="red">* </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<a class="btn btn-default btn-select"> <form:input
																path="ma_tour" type="hidden" class="btn-select-input" 
																name="" value="0" /> <span class="btn-select-value" >Chọn mã Tour </span> 
																<span class="btn-select-arrow glyphicon glyphicon-chevron-down"></span>
															<ul>
															<c:forEach var="tour" items="${tourHDV}">
																<c:if test="${tourChiPhis.get(tourHDV.indexOf(tour)) == null}">
																	<li value="${tour.getMa()}" 
																		<c:if test="${ chiphiForm.getMa_tour() == tour.getMa() }"> class="selected"</c:if> >
																	${tour.getMa()}
																	</li>
																</c:if>
															</c:forEach>
															</ul>
														</a>
													</div>
												</div>
											</div>


										</div>
										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Chi phí di chuyển <font color="red">*</font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control" path="chi_phi_di_chuyen" />
													</div>
												</div>
											</div>

											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Chi phí ăn uống <font color="red">*</font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control" path="chi_phi_an_uong" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Chi phí ở <font color="red">*</font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control" path="chi_phi_o" />
													</div>
												</div>
											</div>

											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Chi phí phát sinh <font color="red">*</font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control" path="chi_phi_phat_sinh" />
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="row btn_center">
										<div class="col-xs-12" style="padding-right: 0px;">
											<div class="panel panel-info">
												<div class="panel-footer">

													<button  type="submit" name="action" value="${userId}"
														class="btn btn-reg" >Nhập</button>
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
				<div class="row">
					<div class="col-xs-12">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h3 class="panel-title">Lịch hướng dẫn Tour</h3>
								<span class="pull-right clickable"> <i
									class="glyphicon glyphicon-chevron-up"> </i>
								</span>
							</div>
							<div class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="row">
										<div class="col-xs-12">
											<span class="col-xs-6">Số lượng： ${countTour}
											</span> <span class="col-xs-6"><p class="text-right">
													<font color="red"></font>
												</p> </span>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12">
											<table class="table table-striped table-condensed tablesorter" id="table">
												<thead>
													<tr>
														<th style="width: 5%;">Mã</th>
														<th style="width: 20%;">Tên</th>
														<th style="width: 10%;">Ngày đi</th>
														<th style="width: 10%;">Ngày vê</th>
														<th style="width: 10%;">Số vé</th>
														<th style="width: 15%;">Giá</th>
														<th style="width: 15%;">Phương tiện</th>
														<th style="width: 10%;">Trạng thái</th>
														<th style="width: 5%;"></th>
													</tr>
												</thead>
												<tbody id="userTableResult">
													<c:forEach var="tour" items="${tourHDV}">
														<tr>
															<td>${tour.getMa()}</td>
															<td>${tour.getTen()}</td>
															<td>${tour.getNgay_di()}</td>
															<td>${tour.getNgay_ve()}</td>		
															<td>${tour.getSo_ve()}</td>
															<td><fmt:formatNumber type="number" value="${tour.getGia()}"
																groupingUsed="true" /> VNĐ</td>	
															<td>${tour.getPhuong_tien()}</td>	
															<td>
																<c:if test="${tourChiPhis.get(tourHDV.indexOf(tour)) != null}">
																	<c:out value="Đã Nhập"></c:out>
																</c:if> 
																<c:if test="${tourChiPhis.get(tourHDV.indexOf(tour)) == null}">
																	<c:out value="Chưa Nhập"></c:out>
																</c:if> 
															</td>												
															<td>
																<c:if test="${tourChiPhis.get(tourHDV.indexOf(tour)) != null}">
																	<button onclick="updateChiPhi(${tourChiPhis.get(tourHDV.indexOf(tour)).getChiPhiDiChuyen()},
																					${tourChiPhis.get(tourHDV.indexOf(tour)).getChiPhiAnUong()},
																					${tourChiPhis.get(tourHDV.indexOf(tour)).getChiPhiO()},
																					${tourChiPhis.get(tourHDV.indexOf(tour)).getChiPhiPhatSinh()})" 
																					name="" class="btn btn-info pull-right" data-toggle="modal" data-target="#modal_edit">
																	Chi tiết
																	</button>
																</c:if> 
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

	<input type="hidden" id="modal_edit_isShow" value='${isEdited}'/>
	<div id="modal_edit" class="modal fade " role="dialog">
		<div class="modal-dialog modal-lg">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" style="text-align: left;">Chi phí đi Tour </h4>
				</div>
				<form:form >
					<div class="modal-body">
						<!-- pannel -->
						<div class="row">
							<div class="col-xs-12">
								<div class="panel-collapse collapse in">
									<div class="panel-body">

										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Chi phí di chuyển <font color="red"> </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<input type='text' disabled="disabled" class="form-control" id="chi_phi_di_chuyen_txt"/>
													</div>
												</div>
											</div>

											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Chi phí ăn uống</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<input type='text' disabled="disabled" class="form-control" id="chi_phi_an_uong_txt" >
													</div>
												</div>
											</div>
										</div>

										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Chi phí ở <font color="red"> </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<input type='text' disabled="disabled" class="form-control" id="chi_phi_o_txt"/>
													</div>
												</div>
											</div>

											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Chi phí phát sinh</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<input type='text' disabled="disabled" class="form-control" id="chi_phi_phat_sinh_txt"/>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								</div>
							</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Thoát</button>
					</div>
				</form:form>
		
			</div>

		</div>
	</div>
</body>
</html>

