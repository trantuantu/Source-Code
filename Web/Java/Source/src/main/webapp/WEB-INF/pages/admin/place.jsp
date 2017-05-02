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
<script src=<c:url value="/admin_scripts/jquery.tablesorter.min.js" />></script>
<link rel=stylesheet href=<c:url value="/admin_styles/sort/style.css" />>

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
								<h1 class="page-header">QUẢN LÝ ĐỊA ĐIỂM</h1>
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
				<form:form action="searchPlace" method="get" modelAttribute="searchForm">
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
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														 <form:input type="text" path="ten" class="form-control "/>
													</div>
												</div>
											</div>
												<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Địa Điểm<font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
				
											<div class="col-xs-3">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' path="dia_diem" class="form-control" />
													</div>
												</div>
											</div> 
										<button style="margin-top: 5px" type="submit" name="action" value="searchplace" class="btn btn-primary"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
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
											<span class="col-xs-6">Kết quả tìm kiếm： ${countPlace} record </span> <span class="col-xs-6"><p class="text-right">
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
														<th style="width: 15%;">Địa Điểm</th>
														<th style="width: 50%;">Thông tin</th>
														<th style="width: 5%;"></th>
														<th style="width: 5%;"></th>
													</tr>
												</thead>
												<tbody id="userTableResult">
													<c:forEach var="place" items="${listPlace}">
														<tr>
															<td>${place.ma}</td>
															<td>${place.ten}</td>
															<td>${place.dia_diem}</td>
															<td>${place.thong_tin}</td>
					
															<td>
																<button onclick="editPlace(${place.ma})" name="" class="btn btn-ref pull-right" data-toggle="modal" data-target="#modal_editPlace">Sửa
																</button>
															</td>
															<td>		
																<button type="button" onclick="deletePlace(${place.ma})" name="" class="btn btn-red pull-right" data-toggle="modal" data-target="#modal_deletePlace">Xóa
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
	
	<div id="modal_deletePlace" class="modal fade" role="dialog">
		<div class="modal-dialog modal-sm" style="width: 400px !important; ">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Xác nhận xóa</h4>
				</div>
				<div class="modal-body">
					<p>Bạn có muốn xóa địa điểm có mã là <span id="showId" ></span> không?</p>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info" data-dismiss="modal">Thoát</button>
        			
        			<form:form method="post" action="deletePlace" modelAttribute="placeFormDelete" class="btn">
							<form:input path="ma" type="hidden" class="btn-select-input" id="hidden_idPlace" name="" value="" />
						<input type="submit" name="action" value="Xác nhận" class="btn btn-danger" />
					</form:form >
				</div>
			</div>

		</div>
	</div>
	<input type="hidden" id="modal_editPlace_isShow" value='${isEdited}'/>
	<div id="modal_editPlace" class="modal fade " role="dialog">
		<div class="modal-dialog modal-lg">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" style="text-align: left;">Sửa thông tin ${title} </h4>
				</div>
				<form:form action="editPlace" method="post" enctype="multipart/form-data"  modelAttribute="placeFormEdit">
					<div class="modal-body">
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
									<p id="error_messages_edit" >Sửa thành công</p>
								</div>
							</div>
						</c:if>
						
						
						<form:errors path="*" cssClass="errorblock"/>
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
															Tên <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-10" style="padding-left: 0px !important;">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="ten" id="name_place" />
														<form:hidden  class="form-control"  path="ma" id="ma"/>
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
											<div class="col-xs-2">
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
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<input type="submit"  class="btn btn-primary" value="Lưu" class="btn btn-reg" />
						<button type="button" class="btn btn-default" data-dismiss="modal">Thoát</button>
					</div>
				</form:form>
		
			</div>

		</div>
	</div>
</body>
</html>

