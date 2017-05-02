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
				
				<form:form action="addHotel" method="get" modelAttribute="hotelFormAdd">
					<!-- pannel -->
					<div class="row">
						<div class="col-xs-12">
							<div class="panel panel-primary">
								<div class="panel-heading">
									<h3 class="panel-title">Thêm khách sạn mới</h3>
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
														<form:input path="Loai" type="hidden" class="btn-select-input" id="search_HiddenLoai"
																name="" value="" /> 
															<span class="btn-select-value" id="search_ValueSelectorLoai">Select an Item </span> <span
															class="btn-select-arrow glyphicon glyphicon-chevron-down"></span>
															<ul>
															<li value="0" <c:if test="${ hotelForm.getLoai() == 0 }"> class="selected"</c:if> >
															Thường</li>
															<li value="1" <c:if test="${ hotelForm.getLoai() == 1 }"> class="selected"</c:if> >
															* (1 Sao)</li>
															<li value="2" <c:if test="${ hotelForm.getLoai() == 2 }"> class="selected"</c:if>>
															** (2 Sao)</li>
															<li value="3" <c:if test="${ hotelForm.getLoai() == 3 }"> class="selected"</c:if>>
															*** (3 Sao)</li>
															<li value="4" <c:if test="${ hotelForm.getLoai() == 4 }"> class="selected"</c:if>>
															**** (4 Sao)</li>
															<li value="5" <c:if test="${ hotelForm.getLoai() == 5 }"> class="selected"</c:if>>
															***** (5 Sao)</li>
															</ul>
														</a>
													</div>
												</div>
											</div>
											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Tên<font color="red"></font></h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control "
															path="Ten" />
													</div>
												</div>
											</div>

										</div>

										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Địa chỉ <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-10" style="padding-left: 0px !important;">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="DiaChi" />
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
													<button  onclick="addHotelClear()"
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

