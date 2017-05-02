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
				<form:form action="searchHotel" method="get" modelAttribute="hotelForm">
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
														<form:input path="Loai" type="hidden" class="btn-select-input" id="search_HiddenLoai"
																name="" value="" /> 
															<span class="btn-select-value" id="search_ValueSelectorLoai">Select an Item </span> <span
															class="btn-select-arrow glyphicon glyphicon-chevron-down"></span>
															<ul>
															<li value="6" <c:if test="${ hotelForm.getLoai() == 6 }"> class="selected"</c:if> >
															Tất cả</li>
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


										</div>

										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Mã <font color="red"></font>
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
											<span class="col-xs-6">Kết quả tìm kiếm： ${countHotel} record </span> <span class="col-xs-6"><p class="text-right">
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
														<th style="width: 20%;">Loại</th>
														<th style="width: 45%;">Địa chỉ</th>
														<th style="width: 5%;"></th>
														<th style="width: 5%;"></th>
													</tr>
												</thead>
												<tbody id="userTableResult">
													<c:forEach var="hotel" items="${listHotel}">
														<tr>
															<td>${hotel.getMa()}</td>
															<td>${hotel.getTen()}</td>
															<td>
																<c:if test="${hotel.getLoai() == '0'}">
																	<c:out value="Thường" />
																</c:if> 
																<c:if test="${hotel.getLoai() == '1'}">
																	<i class="fa fa-star fa-2x"></i>
																</c:if> 
																<c:if test="${hotel.getLoai() == '2'}">
																	<i class="fa fa-star fa-2x"></i>
																	<i class="fa fa-star fa-2x"></i>
																</c:if>
																<c:if test="${hotel.getLoai() == '3'}">
																	<i class="fa fa-star fa-2x"></i>
																	<i class="fa fa-star fa-2x"></i>
																	<i class="fa fa-star fa-2x"></i>
																</c:if> 
																<c:if test="${hotel.getLoai() == '4'}">
																	<i class="fa fa-star fa-2x"></i>
																	<i class="fa fa-star fa-2x"></i>
																	<i class="fa fa-star fa-2x"></i>
																	<i class="fa fa-star fa-2x"></i>
																</c:if> 
																<c:if test="${hotel.getLoai() == '5'}">
																	<i class="fa fa-star fa-2x"></i>
																	<i class="fa fa-star fa-2x"></i>
																	<i class="fa fa-star fa-2x"></i>
																	<i class="fa fa-star fa-2x"></i>
																	<i class="fa fa-star fa-2x"></i>
																</c:if>
															</td>
															<td>${hotel.getDiaChi()}</td>
															
															<td>
																<button onclick="editHotel(${hotel.getMa() })" name="" class="btn btn-ref pull-right" data-toggle="modal" data-target="#modal_editHotel">Sửa
																</button>
															</td>
															<td>		
																<button onclick="deleteHotel(${hotel.getMa() })" name="" class="btn btn-red pull-right" data-toggle="modal" data-target="#modal_deleteHotel">Xóa
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
	
	<div id="modal_deleteHotel" class="modal fade" role="dialog">
		<div class="modal-dialog modal-sm" style="width: 400px !important; ">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Xác nhận xóa</h4>
				</div>
				<div class="modal-body">
					<p>Bạn có muốn xóa ${title} có mã là <span id="showId" ></span> không?</p>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info" data-dismiss="modal">Thoát</button>
        			
        			<form:form method="post" action="deleteHotel" modelAttribute="hotelFormDelete" class="btn">
							<form:input path="Ma" type="hidden" class="btn-select-input" id="hidden_idHotel" name="" value="" />
						<input type="submit" name="action" value="Xác nhận" class="btn btn-danger" />
					</form:form >
				</div>
			</div>

		</div>
	</div>
	<input type="hidden" id="modal_edit_isShow" value='${isEdited}'/>
	<div id="modal_editHotel" class="modal fade " role="dialog">
		<div class="modal-dialog modal-lg">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" style="text-align: left;">Sửa thông tin ${title} </h4>
				</div>
				<form:form action="editHotel" method="post" modelAttribute="hotelFormEdit">
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
															Loại <font color="red">* </font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<a class="btn btn-default btn-select"> 
														<form:input
																path="Loai" type="hidden" class="btn-select-input" id="edit_HiddenLoaiHotel"
																name="" value="" /> <span class="btn-select-value" id="edit_ValueSelectorLoaiHotel">Chọn loại </span> <span
															class="btn-select-arrow glyphicon glyphicon-chevron-down"></span>
															<ul>
															<li value="0">Thường</li>
															<li value="1">* (1 Sao)</li>
															<li value="2">** (2 Sao)</li>
															<li value="3">*** (3 Sao)</li>
															<li value="4">**** (4 Sao)</li>
															<li value="5">***** (5 Sao)</li>
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
															Mã <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input path="Ma" type="hidden" class="btn-select-input" id="hidden_edit_MaHotel" name="" value="" />
														<input type='text' disabled="disabled" class="form-control " id="edit_MaHotel"/>
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
														<form:input type='text' class="form-control " id="edit_TenHotel"
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
														<form:input type='text' class="form-control " path="DiaChi" id="edit_DiaChiHotel"/>
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

