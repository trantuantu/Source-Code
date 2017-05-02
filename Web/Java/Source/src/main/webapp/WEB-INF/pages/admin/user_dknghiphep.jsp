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
		
		$('#msg-error-add').hide();
		
		
		
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

				<!--  
				<div class="row">
					<div class="col-md-10">
						<p>${errorMessages }</p>
					</div>
				</div>
				-->
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
							<p id="error_messages_edit" >Đăng ký thành công</p>
						</div>
					</div>
				</c:if>
				<form:form action="tao_nghiphep" method="get" modelAttribute="userNghiPhepForm">
					<!-- pannel -->
					<div class="row">
						<div class="col-xs-12">
							<div class="panel panel-primary">
								<div class="panel-heading">
									<h3 class="panel-title">Thông tin nghỉ phép</h3>
									<span class="pull-right clickable"> <i
										class="glyphicon glyphicon-chevron-up"> </i>
									</span>
								</div>

								<div class="panel-collapse collapse in">
									<div class="panel-body">
									<div class="alert alert-danger" id="msg-error-add"> 
									</div>
										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Ngày nghỉ <font color="red">*</font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control" id="add_ngNghi" path="NgayNghi" />
													</div>
												</div>
											</div>

											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Ngày đi làm <font color="red">*</font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control" id="add_ngLam" path="NgayDiLam" />
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
														class="btn btn-reg" >Đăng ký</button>
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
								<h3 class="panel-title">Danh sách</h3>
								<span class="pull-right clickable"> <i
									class="glyphicon glyphicon-chevron-up"> </i>
								</span>
							</div>
							<div class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="row">
										<div class="col-xs-12">
											<span class="col-xs-6">Số lượng： ${countNghiPhep}
											 </span> 
											 <span class="col-xs-6"><p class="text-right">
													<font color="red"></font>
												</p> </span>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12">
											<table class="table table-striped table-condensed tablesorter" id="table">
												<thead>
													<tr>
														<th style="width: 5%;">STT</th>
														<th style="width: 20%;">Ngày nghỉ</th>
														<th style="width: 20%;">Ngày đi làm</th>
														<th style="width: 10%;">Số ngày nghỉ</th>
														<th style="width: 15%;">Trạng thái</th>
														<th style="width: 30%;"></th>
													</tr>
												</thead>
												<tbody id="userTableResult">
													<c:forEach var="np" items="${listNghiPhep}">
														<tr>
															<td>${listNghiPhep.indexOf(np) + 1}</td>
															<td>${np.getNgayNghi()}</td>
															<td>${np.getNgayDiLam()}</td>
															<td>${np.getMaNV()}</td>
															<td><c:if test="${np.isXacNhan()}">
																	<c:out value="Đã duyệt" />
																</c:if> <c:if test="${!np.isXacNhan()}">
																	<c:out value="Chưa duyệt" />
																</c:if>
															</td>
															<td>
																<c:if test="${!np.isXacNhan()}">
																	<button onclick="deleteNghiPhep(${listNghiPhep.indexOf(np) + 1}, ${np.getId()})" name="" class="btn btn-red pull-left" data-toggle="modal" data-target="#modal_delete">
																Xóa</button>
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

	<div id="modal_delete" class="modal fade" role="dialog">
		<div class="modal-dialog modal-sm" style="width: 400px !important; ">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Xác nhận xóa</h4>
				</div>
				<div class="modal-body">
					<p>Bạn có muốn xóa ${title} có STT là <span id="showId" ></span> không?</p>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info" data-dismiss="modal">Thoát</button>
        			
        			<form:form method="post" action="deleteNghiPhep" modelAttribute="userNghiPhepFormDelete" class="btn">
							<form:input path="Ma" type="hidden" class="btn-select-input" id="hidden_idNghiPhep" name="" value="" />
						
							<button  type="submit" name="action" value="${userId}" class="btn btn-dange" >Xác nhận</button>
					</form:form >
				</div>
			</div>

		</div>
	</div>

</body>
</html>

