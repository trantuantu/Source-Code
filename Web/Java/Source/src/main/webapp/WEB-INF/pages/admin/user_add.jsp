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
				<form:form action="addUser" method="get" modelAttribute="userFormAdd">
					<!-- pannel -->
					<div class="row">
						<div class="col-xs-12">
							<div class="panel panel-primary">
								<div class="panel-heading">
									<h3 class="panel-title">Thêm nhân viên mới</h3>
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
														<a class="btn btn-default btn-select"> <form:input
																path="Loai" type="hidden" class="btn-select-input" id="edit_HiddenLoaiUser"
																name="" value="" /> <span class="btn-select-value" id="edit_ValueSelectorLoaiUser">Select
																an Item </span> <span
															class="btn-select-arrow glyphicon glyphicon-chevron-down"></span>
															<ul>
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
														<h5>Họ tên <font color="red">* </font></h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control "
															path="HoTen" />
													</div>
												</div>
											</div>
		
										</div>

										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Tài khoản <font color="red">* </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control" path="TaiKhoan" />
													</div>
												</div>
											</div>

											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>CMND<font color="red">* </font></h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control "
															path="CMND" />
													</div>
												</div>
											</div>
										</div>

										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Số ĐT <font color="red"> </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="SoDT" />
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

													<button  type="submit" name="action" value="add"
														class="btn btn-reg" >Thêm</button>
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
				
				
				<br>
			</div>
		</div>
	</div>

	

</body>
</html>
