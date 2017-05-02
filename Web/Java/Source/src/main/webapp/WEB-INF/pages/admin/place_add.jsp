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
								<h1 class="page-header">QUẢN LÝ ĐỊA ĐIỂM </h1>
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
				
				<form:form action="addPlace" method="post" enctype="multipart/form-data" modelAttribute="placeFormAdd">
					<!-- pannel -->
					<div class="row">
						<div class="col-xs-12">
							<div class="panel panel-primary">
								<div class="panel-heading">
									<h3 class="panel-title">Thêm địa điểm mới</h3>
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
											<div class="col-xs-10" style="padding-left: 0px !important;">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="ten" id="name_place" />
													</div>
												</div>
											</div>
										</div>

										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Địa điểm <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-10" style="padding-left: 0px !important;">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="dia_diem" id="place" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Giá <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-10" style="padding-left: 0px !important;">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="gia" id="price_place" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="row">
											<div class="col-xs-2" style="margin-top: 15px;">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Thông tin <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-10" style="padding-left: 0px !important;">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:textarea type='text' class="form-control " path="thong_tin" id="thong_tin"/>
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

													<button  type="submit" name="action" value="add"
														class="btn btn-reg" >Thêm</button>
													<button  type="button" onclick="addPlaceClear()"
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

