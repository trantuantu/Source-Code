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
				<form:form action="duyet_nghiphep" method="get" modelAttribute="userForm">
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
															Loại <font color="red">* </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<a class="btn btn-default btn-select"> 
														<form:input path="Loai" type="hidden" class="btn-select-input" id="search_HiddenLoaiUser" name="" value="3" /> 
														<span class="btn-select-value" id="search_ValueSelectorLoaiUser">Select an Item </span> 
														<span class="btn-select-arrow glyphicon glyphicon-chevron-down"></span>
															<ul>
															<li value="3" 
																<c:if test="${ userForm.getLoai() == 3 }"> class="selected"</c:if> >
															Tất cả </li>
																<li value="0" 
																<c:if test="${ userForm.getLoai() == 0 }"> class="selected"</c:if> >
															Quản lý </li>
															<li value="1" <c:if test="${ userForm.getLoai() == 1 }"> class="selected"</c:if>>
															Hướng dẫn viên</li>
															<li value="2" <c:if test="${ userForm.getLoai() == 2 }"> class="selected"</c:if>>
															Nhân viên</li>
															</ul>
														</a>
													</div>
												</div>
											</div>
											
											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Nghỉ phép</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body item-height">
														<div class="radio radio-margin">
															<label> 
																<c:if test="${userForm.getNghiPhep() == '1' }">
																	<form:radiobutton path="NghiPhep" value="1" name="NghiPhep" checked="checked" />
																</c:if>
																<c:if test="${userForm.getNghiPhep() != '1' }">
																	<form:radiobutton path="NghiPhep" value="1" name="NghiPhep" />
																</c:if>
																<span class="cr"> 
																	<i class="cr-icon glyphicon glyphicon-ok"> </i>
																</span>Có
															</label> 
															<label> 
																<c:if test="${userForm.getNghiPhep() == '0' }">
																	<form:radiobutton path="NghiPhep" value="0" name="NghiPhep" checked="checked" />
																</c:if>
																<c:if test="${userForm.getNghiPhep() != '0' }">
																	<form:radiobutton path="NghiPhep" value="0" name="NghiPhep" />
																</c:if>
																<span class="cr"> 
																	<i class="cr-icon glyphicon glyphicon-ok"> </i>
																</span>Không
															</label> 
														</div>
													</div>
												</div>
											</div>

										</div>

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
														<form:input type='text' class="form-control " path="Ma" />
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
														<form:input type='text' class="form-control "
															path="TaiKhoan" />
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
														<form:input type='text' class="form-control " path="HoTen" />
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
														<form:input type='text' class="form-control "
															path="DiaChi" />
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
														<form:input type='text' class="form-control datepicker"
															id="birthday" name="birthday" path="NamSinh" />
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
																
																	<form:radiobutton path="GioiTinh" value="2" name="gender" checked="checked" />
																
																<span class="cr"> 
																	<i class="cr-icon glyphicon glyphicon-ok"> </i>
																</span>Tất cả 
															</label>
															<label> 
																<c:if test="${userForm.getGioiTinh() == '1' }">
																	<form:radiobutton path="GioiTinh" value="1" name="gender" checked="checked" />
																</c:if>
																<c:if test="${userForm.getGioiTinh() != '1' }">
																	<form:radiobutton path="GioiTinh" value="1" name="gender" />
																</c:if>
																<span class="cr"> 
																	<i class="cr-icon glyphicon glyphicon-ok"> </i>
																</span>Nam
															</label> 
															<label> 
																<c:if test="${userForm.getGioiTinh() == '0' }">
																	<form:radiobutton path="GioiTinh" value="0" name="gender" checked="checked" />
																</c:if>
																<c:if test="${userForm.getGioiTinh() != '0' }">
																	<form:radiobutton path="GioiTinh" value="0" name="gender" />
																</c:if> 
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
											<span class="col-xs-6">Kết quả tìm kiếm： ${countUser}
												record </span> <span class="col-xs-6"><p class="text-right">
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
														<th style="width: 10%;">Họ tên</th>
														<th style="width: 10%;">Tài khoản</th>
														<th style="width: 10%;">Loại</th>
														<th style="width: 10%;">Năm sinh</th>
														<th style="width: 7%;">Giới tính</th>
														<th style="width: 8%;">CMND</th>
														<th style="width: 10%;">Số điện thoại</th>
														<th style="width: 10%;">Địa chỉ</th>
														<th style="width: 5%;"></th>
														<th style="width: 5%;"></th>
														<th style="width: 5%;"></th>
													</tr>
												</thead>
												<tbody id="userTableResult">
													<c:forEach var="user" items="${listUser}">
														<tr>
															<td>${user.getMa()}</td>
															<td>${user.getHoTen()}</td>
															<td>${user.getTaiKhoan()}</td>
															<td><c:if test="${user.getLoai() == '0'}">
																	<c:out value="Quản lý" />
																</c:if> <c:if test="${user.getLoai() == '1'}">
																	<c:out value="Hướng dẫn viên" />
																</c:if> <c:if test="${user.getLoai() == '2'}">
																	<c:out value="Nhân Viên" />
																</c:if></td>
															<td>${user.getNamSinh()}</td>
															<td><c:if test="${user.getGioiTinh() == '1'}">
																	<c:out value="Nam" />
																</c:if> <c:if test="${user.getGioiTinh() == '0'}">
																	<c:out value="Nữ" />
																</c:if>
															</td>
															<td>${user.getCMND()}</td>
															<td> ${ user.getSoDienThoai() }</td>
															<td>${user.getDiaChi()}</td>
															
															<td>
																<button onclick="addNghiPhep( ${user.getMa() }, '${user.getHoTen()}' )" name="" class="btn  pull-right btn-ref" data-toggle="modal" data-target="#modal_add">
																Thêm</button>
															</td>	
															
															<td>
																<button onclick="detailNghiPhep( ${user.getMa() }, '${user.getHoTen()}' )" name="" class="btn  pull-right btn-info" data-toggle="modal" data-target="#modal_detail">
																Chi tiết</button>
															</td>	
															<td>
																<button onclick="duyetNghiPhep( ${user.getMa() }, '${user.getHoTen()}' )" name="" class="btn btn-ref pull-right" data-toggle="modal" data-target="#modal_edit">
																Duyệt</button>
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
					<h4 class="modal-title" style="text-align: left;">Duyệt ngày nghỉ</h4>
				</div>
				
					<div class="modal-body">
						<div class="alert alert-success" id="msg-success">
						  <strong>Duyệt thành công!</strong>
						</div>
						<div class="alert alert-danger" id="msg-error"> 
						  <strong>Xảy ra lỗi, mời thử lại</strong>
						</div>
						<div class="alert alert-warning" id="msg-warning">
						  <strong>Không có ngày để duyệt</strong>
						</div>
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
															Mã <font color="red"> </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<input style="background-color: #fff;" disabled type='text' class="form-control " id="Ma_duyet"/>
													</div>
												</div>
											</div>

											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Họ tên</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<input style="background-color: #fff;" disabled type='text' class="form-control " id="HoTen_duyet" />
													</div>
												</div>
											</div>
										</div> 
										<br/>
										<div class="row">
											<div class="col-xs-12" style="padding-right: 0px !important;">
											
											
												<table class="table table-bordered" id="table_lnp">
												  <thead>
												    <tr>
												      <th style="text-align: center;">Ngày nghỉ</th>
												      <th style="text-align: center;">Ngày làm lại</th>
												      <th style="text-align: center;">Số ngày nghỉ</th>
												      <th style="text-align: center;"></th>
												    </tr>
												  </thead>
												  <tbody>
												   
												   
												    
												  </tbody>
												</table>
											</div>
										</div>

									</div>
								</div>
								</div>
							</div>
					</div>
					<div class="modal-footer">
						<input type="submit" onclick="saveNP(0)" class="btn btn-primary" value="Duyệt" class="btn btn-reg" />
						<button type="button" class="btn btn-default" data-dismiss="modal">Thoát</button>
					</div>
			</div>

		</div>
	</div>


	<div id="modal_detail" class="modal fade " role="dialog">
		<div class="modal-dialog modal-lg">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" style="text-align: left;">Chi tiết ngày nghỉ đã duyệt</h4>
				</div>
				
					<div class="modal-body">
						<div class="alert alert-success" id="msg-success-detail">
						  <strong>Xóa thành công!</strong>
						</div>
						<div class="alert alert-danger" id="msg-error-detail"> 
						  <strong>Xảy ra lỗi, mời thử lại</strong>
						</div>
						<div class="alert alert-warning" id="msg-warning-detail">
						  <strong>Không có ngày để xóa</strong>
						</div>
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
															Mã <font color="red"> </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<input style="background-color: #fff;" disabled type='text' class="form-control " id="Ma_detail"/>
													</div>
												</div>
											</div>

											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Họ tên</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<input style="background-color: #fff;" disabled type='text' class="form-control " id="HoTen_detail" />
													</div>
												</div>
											</div>
										</div> 
										<br/>
										<div class="row">
											<div class="col-xs-12" style="padding-right: 0px !important;">
											
											
												<table class="table table-bordered" id="table_denp">
												  <thead>
												    <tr>
												      <th style="text-align: center;">Ngày nghỉ</th>
												      <th style="text-align: center;">Ngày làm lại</th>
												      <th style="text-align: center;">Số ngày nghỉ</th>
												      <th style="text-align: center;">Xóa</th>
												      <th style="text-align: center;">Lưu thay đổi</th>
												    </tr>
												  </thead>
												  <tbody>
												   
												   
												    
												  </tbody>
												</table>
											</div>
										</div>

									</div>
								</div>
								</div>
							</div>
					</div>
					<div class="modal-footer">
						<input type="submit" onclick="saveNP(1)" class="btn btn-warning" value="Xóa các mục đã chọn" class="btn btn-reg" />
						<button type="button" class="btn btn-default" data-dismiss="modal">Thoát</button>
					</div>
				
		
			</div>

		</div>
	</div>


	<div id="modal_add" class="modal fade " role="dialog">
		<div class="modal-dialog modal-lg">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" style="text-align: left;">Thêm ngày nghỉ</h4>
				</div>
				
					<div class="modal-body">
						<div class="alert alert-success" id="msg-success-add">
						  <strong>Thêm thành công!</strong>
						</div>
						<div class="alert alert-danger" id="msg-error-add"> 
						  <strong>Xảy ra lỗi, mời thử lại</strong>
						</div>
						<div class="alert alert-warning" id="msg-warning-add">
						  <strong>Chưa chọn ngày</strong>
						</div>
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
															Mã <font color="red"> </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<input style="background-color: #fff;" disabled type='text' class="form-control " id="Ma_add"/>
													</div>
												</div>
											</div>

											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Họ tên</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<input style="background-color: #fff;" disabled type='text' class="form-control " id="HoTen_add" />
													</div>
												</div>
											</div>
										</div> 
										<br/>
										<div class="row">
											<div class="col-xs-12" style="padding-right: 0px !important;">
											
											
												<table class="table table-bordered" id="table_adnp">
												  <thead>
												    <tr>
												      <th style="text-align: center;">Ngày nghỉ</th>
												      <th style="text-align: center;">Ngày làm lại</th>
												      <th style="text-align: center;">Số ngày nghỉ</th>
												    </tr>
												  </thead>
												  <tbody>
												   	<tr>
														<td align='center' style='padding: 0 !important;'>
															<input type='text' id="add_ngNghi"  style="width: 100%;height: 30px;text-align: center;">
														</td>
														<td align='center' style='padding: 0 !important;'>
															<input type='text' id="add_ngLam"  style='width: 100%;height: 30px;text-align: center;'>
														</td>
														<td align='center' style='padding: 0 !important;' id="add_slngay">
															
														</td>
												   </tr>
												    
												  </tbody>
												</table>
											</div>
										</div>

									</div>
								</div>
								</div>
							</div>
					</div>
					<div class="modal-footer">
						<input type="submit" onclick="addNP()" class="btn btn-warning" value="Thêm" class="btn btn-reg" />
						<button type="button" class="btn btn-default" data-dismiss="modal">Thoát</button>
					</div>
				
		
			</div>

		</div>
	</div>

</body>
</html>
