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
<link rel=stylesheet href=<c:url value="/admin_styles/sort/style.css" />>

<script src=<c:url value="/admin_scripts/jquery-3.1.1.js" />></script>
<script src=<c:url value="/admin_scripts/bootstrap.min.js" />></script>
<script src=<c:url value="/admin_scripts/output.js" />></script>
<script src=<c:url value="/admin_scripts/scripts.js" />></script>
<script src=<c:url value="/admin_scripts/scripts_admin.js" />></script>
<script src=<c:url value="/admin_scripts/bootstrap-datepicker.min.js" />></script>
<script src=<c:url value="/admin_scripts/jquery.tablesorter.min.js" />></script>

<script>
	$(function() {		
		
		$("#table").tablesorter({sortList: [[0,0]],  headers: { 
			4: { sorter: false }, 
            5: { sorter: false }
            } }); 
		
		

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
				<form:form action="searchDuyetTour" method="get" modelAttribute="searchTourForm">
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
														<h5>
															Mã tour<font color="red"> </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="ma" />
													</div>
												</div>
											</div>
											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Tên tour</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="ten" />
													</div>
												</div>
											</div>

										</div>
										
										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Mã Hướng dẫn viên <font color="red"> </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="ma_huong_dan_vien" />
													</div>
												</div>
											</div>

											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Tên Hướng dẫn viên</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="ten_huong_dan_vien" />
													</div>
												</div>
											</div>
										</div>	
									
										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Trạng thái <font color="red">* </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<a class="btn btn-default btn-select"> <form:input
																path="trang_thai" type="hidden" class="btn-select-input" id="search_HiddenLoaiUser"
																name="" value="2" /> <span class="btn-select-value" id="search_ValueSelectorLoaiUser">Tất cả </span> <span
															class="btn-select-arrow glyphicon glyphicon-chevron-down"></span>
															<ul>
															<li value="6" <c:if test="${ searchTourForm.getTrang_thai() == 6 }"> class="selected"</c:if> >Tất cả</li>
															<li value="2" <c:if test="${ searchTourForm.getTrang_thai() == 2 }"> class="selected"</c:if> >Nhân viên đã duyệt</li>
															<li value="3" <c:if test="${ searchTourForm.getTrang_thai() == 3 }"> class="selected"</c:if> >Tour đã duyệt</li>
															</ul>
														</a>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="row btn_center">
										<div class="col-xs-12" style="padding-right: 0px;">
											<div class="panel panel-info">
												<div class="panel-footer">

													<button  type="submit" name="action" value="search"
														class="btn btn-reg" >Tìm kiếm</button>
													<button  type="submit" name="action" value="reset"
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
											<span class="col-xs-6">Kết quả tìm kiếm： ${countTour} record </span> 
											<span class="col-xs-6">
												<p class="text-right">
													<font color="red"></font>
												</p> 
											</span>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12">
											<table class="table table-striped table-condensed tablesorter" id="table">
												<thead>
													<tr>
														<th style="width: 5%;">Mã</th>
														<th style="width: 20%;">Tên</th>
														<th style="width: 10%;">Trạng thái</th>
														<th style="width: 7%;">Vé</th>
														<th style="width: 8%;">Giá</th>
														<th style="width: 5%;"></th>
														<th style="width: 5%;"></th>
													</tr>
												</thead>
												<tbody id="userTableResult">
													<c:forEach var="tour" items="${listTour}">
														<tr>
															<td>${tour.getMa()}</td>
															<td>${tour.getTen()}</td>
															<td>
																<c:if test="${tour.getTrang_thai() == 2}">Nhân viên đã duyệt
																</c:if>
																<c:if test="${tour.getTrang_thai() == 3}">Tour đã duyệt
																</c:if>
															</td>
															<td>${tour.getSo_ve_con_lai()}/${tour.getSo_ve()}</td>
															<td>${tour.getGia()}</td>
															<td>
																<button onclick="huyTour(${tour.getMa()}, ${tour.getMa()})" name="" class="btn btn-warning pull-right" data-toggle="modal" data-target="#modal_huyTour">Hủy
																</button>
															</td>
															<td>		
																<button onclick="duyetTour_info(${tour.getMa() })" name="" class="btn btn-info pull-right" data-toggle="modal" data-target="#modal_infoTour">Chi tiết
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

	<input type="hidden" id="modal_edit_isShow" value='${isEdited}'/>
	<input type="hidden" id="userSelected" value=''/>
	
	<div id="modal_infoTour" class="modal fade " role="dialog">
		<div class="modal-dialog modal-lg">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" style="text-align: left;">Thông tin chi tiết </h4>
				</div>
				
				
					<div class="modal-body">
						<div class="alert alert-success" id="msg-success-duyettour" style="display: none;">
						  <strong>Duyệt thành công!</strong>
						</div>
						<div class="alert alert-danger" id="msg-error-duyettour" style="display: none;"> 
						  <strong>Xảy ra lỗi, mời thử lại</strong>
						</div>
						<div class="alert alert-warning" id="msg-warning-duyettour" style="display: none;">
						  <strong>Không có ngày để duyệt</strong>
						</div>
						
						<!-- pannel -->
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
										<div class="col-xs-12">
											<div class="panel-collapse collapse in">
												<div class="panel-body">
													<div class="row">
														<div class="col-xs-2">
															<div class="panel panel-info">
																<div class="panel-body bg-info">
																	<h5>
																		Mã tour
																	</h5>
																</div>
															</div>
														</div>
														<div class="col-xs-4">
															<div class="panel panel-info">
																<div class="panel-body">
																	<input style="background-color: #fff;" type='text' class="form-control " id="MaTour1" disabled/>
																</div>
															</div>
														</div>
														<div class="col-xs-2 padding_reset">
															<div class="panel panel-info">
																<div class="panel-body bg-info">
																	<h5>Tên tour</h5>
																</div>
															</div>
														</div>
														<div class="col-xs-4 reset_padding_right">
															<div class="panel panel-info">
																<div class="panel-body">
																	<input style="background-color: #fff;" type='text' class="form-control " id="TenTour" disabled/>
																</div>
															</div>
														</div>
			
													</div>
			
													<div class="row">
														<div class="col-xs-2">
															<div class="panel panel-info">
																<div class="panel-body bg-info">
																	<h5>
																		Ngày đi <font color="red"> </font>
																	</h5>
																</div>
															</div>
														</div>
														<div class="col-xs-4">
															<div class="panel panel-info">
																<div class="panel-body">
																	<input style="background-color: #fff;" type='text' class="form-control " id="NgayDi" disabled/>
																</div>
															</div>
														</div>
			
														<div class="col-xs-2 padding_reset">
															<div class="panel panel-info">
																<div class="panel-body bg-info">
																	<h5>Ngày về</h5>
																</div>
															</div>
														</div>
														<div class="col-xs-4 reset_padding_right">
															<div class="panel panel-info">
																<div class="panel-body">
																	<input style="background-color: #fff;" type='text' class="form-control " id="NgayVe" disabled/>
																</div>
															</div>
														</div>
													</div>
			
													<div class="row">
														<div class="col-xs-2">
															<div class="panel panel-info">
																<div class="panel-body bg-info">
																	<h5>
																		Số vé <font color="red"> </font>
																	</h5>
																</div>
															</div>
														</div>
														<div class="col-xs-4">
															<div class="panel panel-info">
																<div class="panel-body">
																	<input style="background-color: #fff;" type='text' class="form-control " id="SoVe" disabled/>
																</div>
															</div>
														</div>
			
														<div class="col-xs-2 padding_reset">
															<div class="panel panel-info">
																<div class="panel-body bg-info">
																	<h5>Số vé còn lại</h5>
																</div>
															</div>
														</div>
														<div class="col-xs-4 reset_padding_right">
															<div class="panel panel-info">
																<div class="panel-body">
																	<input style="background-color: #fff;" type='text' class="form-control " id="SoVeConLai" disabled/>
																</div>
															</div>
														</div>
													</div>
			
													<div class="row">
														<div class="col-xs-2">
															<div class="panel panel-info">
																<div class="panel-body bg-info">
																	<h5>
																		Phương tiện <font color="red"> </font>
																	</h5>
																</div>
															</div>
														</div>
														<div class="col-xs-4">
															<div class="panel panel-info">
																<div class="panel-body">
																	<input style="background-color: #fff;" type='text' class="form-control " id="PhuongTien" disabled/>
																</div>
															</div>
														</div>
														<div class="col-xs-2 padding_reset">
															<div class="panel panel-info">
																<div class="panel-body bg-info">
																	<h5>Giá</h5>
																</div>
															</div>
														</div>
														<div class="col-xs-4 reset_padding_right">
															<div class="panel panel-info">
																<div class="panel-body">
																	<input style="background-color: #fff;" type='text' class="form-control " id="Gia" disabled/>
																</div>
															</div>
														</div>
														
													</div>
			
													<div class="row">
														<div class="col-xs-2">
															<div class="panel panel-info">
																<div class="panel-body bg-info">
																	<h5>
																		Chương trình <font color="red"> </font>
																	</h5>
																</div>
															</div>
														</div>
														<div class="col-xs-10">
															<div class="panel panel-info">
																<div class="panel-body">
																	<textarea style="background-color: #fff;" id="ChuongTrinh" class="form-control" rows="3" disabled></textarea>
											
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

						<div class="panel panel-primary">
							<div class="panel-heading">
								<h3 class="panel-title">Thông tin hướng dẫn viên</h3>
								<span class="pull-right clickable"> <i
									class="glyphicon glyphicon-chevron-up"> </i>
								</span>
							</div>

							<div class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="row">
										<div class="col-xs-12">
											<div class="panel-collapse collapse in">
												<div class="panel-body">
													<div class="row">
														<div class="col-xs-2">
															<div class="panel panel-info">
																<div class="panel-body bg-info">
																	<h5>
																		Mã <font color="red"> </font>
																	</h5>
																</div>
															</div>
														</div>
														<div class="col-xs-4">
															<div class="panel panel-info">
																<div class="panel-body">
																	<input type='text' class="form-control " id="Ma_NV" disabled/>
																</div>
															</div>
														</div>
														<div class="col-xs-2 padding_reset">
															<div class="panel panel-info">
																<div class="panel-body bg-info">
																	<h5>Tên</h5>
																</div>
															</div>
														</div>
														<div class="col-xs-4 reset_padding_right">
															<div class="panel panel-info">
																<div class="panel-body">
																	<input type='text' class="form-control " id="Ten_NV" disabled/>
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
					
						<div class="row btn_center">
							<div class="col-xs-12" style="padding-right: 0px;">
								<div class="panel panel-info">
									<div class="panel-footer">

										<button  type="submit" name="action" value="search" data-toggle="modal" data-target="#modal_selectuser"
											class="btn btn-reg" >Chọn hướng dẫn viên</button>
									</div>
								</div>
							</div>
						</div>
					
					</div>
					<div class="modal-footer">
						
							<finput  type="hidden" class="btn-select-input " id="hidden_MaTour_Modal" name="" value="" />
							<input  type="hidden" class="btn-select-input " id="hidden_MaHDV_Modal" name="" value="" />
							<input type="submit" name="action"  class="btn btn-primary" value="Duyệt" class="btn btn-reg" onclick="duyetTour()"/>
			
						<button type="button" class="btn btn-default" data-dismiss="modal">Thoát</button>
						
						
					</div>
		
			</div>

		</div>
	</div>

	<div id="modal_huyTour" class="modal fade" role="dialog">
		<div class="modal-dialog modal-sm" style="width: 400px !important; ">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Xác nhận</h4>
				</div>
				<div class="modal-body">
					<p>Bạn có muốn hủy tour có mã là <span id="showId" ></span> không?</p>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Thoát</button>
        			
        			<form:form method="post" action="comfirmHuyTour" modelAttribute="confirmHuyTourForm" class="btn">
						<form:input path="Ma" type="hidden" class="btn-select-input " id="hidden_Ma" name="" value="" />
						<input type="submit" name="action" value="Xác nhận" class="btn btn-primary" />
					</form:form >
				</div>
			</div>

		</div>
	</div>

	<div id="modal_selectuser" class="modal fade " role="dialog">
		<div class="modal-dialog modal-lg" style="width:1100px !important;">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" style="text-align: left;">Chọn hướng dẫn viên </h4>
				</div>
					<div class="modal-body">
						
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
														<h5>
															Mã <font color="red"> </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<input type='text' class="form-control " id="Ma_User" />
													</div>
												</div>
											</div>

											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Tài khoản</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<input type='text' class="form-control " id="TaiKhoan_User" />
													</div>
												</div>
											</div>
										</div>

										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Họ tên <font color="red"> </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<input type='text' class="form-control " id="HoTen_User" />
													</div>
												</div>
											</div>

											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Địa chỉ</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<input type='text' class="form-control " id="DiaChi_User" />
													</div>
												</div>
											</div>
										</div>

										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Năm sinh <font color="red"> </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<input type='text' class="form-control datepicker" name="birthday" id="NamSinh_User" />
													</div>
												</div>
											</div>
											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Giới tính</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body item-height">
														<div class="radio radio-margin">
															<label> 
																
																	<input type="radio"  value="2" name="gender" checked="checked" />
																<span class="cr"> 
																	<i class="cr-icon glyphicon glyphicon-ok"> </i>
																</span>Tất cả 
															</label>
															<label> 
																<input type="radio"  value="1" name="gender" />
																<span class="cr"> 
																	<i class="cr-icon glyphicon glyphicon-ok"> </i>
																</span>Nam
															</label> 
															<label> 
																<input type="radio"  value="0" name="gender" />
																<span class="cr"> 
																	<i class="cr-icon glyphicon glyphicon-ok"> </i>
																</span>Nữ
															</label>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="row btn_center">
										<div class="col-xs-12" style="padding-right: 0px;">
											<div class="panel panel-info">
												<div class="panel-footer">

													<button class="btn btn-reg" onclick="searchUser()">Tìm kiếm</button>
													<button  type="submit" name="action" value="reset"
														class="btn btn-reg" >Reset</button>	
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
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
													<span class="col-xs-6" id="countUser">Kết quả tìm kiếm： ${countUser}
														record </span> <span class="col-xs-6"><p class="text-right">
															<font color="red"></font>
														</p> </span>
												</div>
											</div>
											<div class="row">
												<div class="col-xs-12">
													<table class="table table-striped table-condensed tablesorter" id="table_selectuser">
														<thead>
															<tr>
																<th style="width: 5%;">Mã</th>
																<th style="width: 15%;">Họ tên</th>
																<th style="width: 10%;">Tài khoản</th>
																<th style="width: 5%;">Năm sinh</th>
																<th style="width: 7%;">Giới tính</th>
																<th style="width: 8%;">CMND</th>
																<th style="width: 10%;">Số điện thoại</th>
																<th style="width: 15%;">Địa chỉ</th>
																<th style="width: 5%;"></th>
															</tr>
														</thead>
														<tbody id="selectUserTableResult">
															
														</tbody>
													</table>
												</div>
												<div class="col-md-12 text-center">
											      <ul class="pagination" id="selectUserTablePager"></ul>
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
		
			</div>

		</div>
	</div>

</body>
</html>

