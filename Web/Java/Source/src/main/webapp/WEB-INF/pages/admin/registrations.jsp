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
        8: { sorter: false }, 
        9: { sorter: false }
        } }); 
	$('#userTableResult').pageMe({pagerSelector:'#userTablePager',showPrevNext:true,hidePageNumbers:false,perPage:5});
});
</script>
</head>
<body>
	<div id="modal_deleteRegistration" class="modal fade" role="dialog">
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
        			
        			<form:form method="post" action="deleteRegistration" modelAttribute="deleteRegistration" class="btn">
							<form:input path="key_registration" type="hidden" class="btn-select-input" id="hidden_idRegistration" name="" value="" />
						<input type="submit" name="action" value="Xác nhận" class="btn btn-danger" />
					</form:form >
				</div>
			</div>

		</div>
	</div>
	<div id="modal_acceptRegistration" class="modal fade " role="dialog">
		<div class="modal-dialog modal-lg">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" style="text-align: left;">Sửa thông tin ${title} </h4>
				</div>
				<form:form action="acceptRegistration" method="post" modelAttribute="acceptRegistration">
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
															Tên Khách Hàng
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 ">
												<div class="panel panel-info">
													<div class="panel-body">
														
														<form:input
																path="name_user" type='text' class="form-control "  id="acceptRegistration_username"
																name="" value="" /> 
													</div>
												</div>
											</div>
											
											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Phone <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
				
											<div class="col-xs-4 ">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="phone_customer" id="acceptRegistration_phone"/>
													</div>
												</div>
											</div> 
											<!-- <div  >
												<input type="submit"   value="Lưu" class="btn btn-reg" style="position: absolute; right: 0;" />
											</div> -->
											<div>
												<button onclick="" name="" class="btn btn-ref pull-right" data-toggle="modal" data-target="#modal_acceptRegistration" style="position: absolute; right: 0;" >Duyệt
												</button>
											</div>
											
											

										</div>
										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Tên Tour<font color="red"></font></h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 ">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " id="acceptRegistration_guide"
															path="name_guides" />
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
														<form:input path="name_tour" type="hidden" id="acceptRegistration_tour" name="" value="" />
														<!-- <input type='text' disabled="disabled" class="form-control " id="edit_MaHotel"/> -->
													</div>
												</div>
											</div>
											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Hướng dẫn viên<font color="red"></font></h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 reset_padding_right">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " id="acceptRegistration_guide"
															path="name_guides" />
													</div>
												</div>
											</div>
											
										</div>

										
										
										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Tổng Tiền <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-10" style="padding-left: 0px !important;">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="total_money" id="acceptRegistration_money"/>
													</div>
												</div>
											</div>
										</div>
										
											<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Tiền đặt cọc <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-10" style="padding-left: 0px !important;">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="deposit" id="acceptRegistration_deposit"/>
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
				<form:form action="searchRegistration" method="get" modelAttribute="searchRegistrationForm">
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
																Phê Duyệt <font color="red">* </font>
															</h5>
														</div>
													</div>
												</div>
												<div class="col-xs-4">
													<div class="panel panel-info">
														<div class="panel-body">
															<a class="btn btn-default btn-select"> 
															<form:input path="approval" type="hidden" class="btn-select-input" id="search_HiddenApproval"
																	name="" value="" /> 
																<span class="btn-select-value" id="search_ValueSelectorApproval">Select an Item </span> <span
																class="btn-select-arrow glyphicon glyphicon-chevron-down"></span>
																<ul>
																<li value="2" <c:if test="${ searchRegistrationForm.getApproval() == 2 }"> class="selected"</c:if> >
																Tất cả</li>
																<li value="0" <c:if test="${ searchRegistrationForm.getApproval() == 0 }"> class="selected"</c:if> >
																Chưa Duyệt</li>
																<li value="1" <c:if test="${ searchRegistrationForm.getApproval() == 1 }"> class="selected"</c:if> >
																Đã Duyệt</li>
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
																Khách Hàng
															</h5>
														</div>
													</div>
												</div>
												<div class="col-xs-4">
													<div class="panel panel-info">
														<div class="panel-body">
															<a class="btn btn-default btn-select"> 
															<form:input path="condition_user" type="hidden" class="btn-select-input" id="search_HiddenCondition_user"
																	name="" value="" /> 
																<span class="btn-select-value" id="search_ValueSelectorCondition_user">Select an Item </span> <span
																class="btn-select-arrow glyphicon glyphicon-chevron-down"></span>
																<ul>
																<li value="0" <c:if test="${ searchRegistrationForm.getCondition_user() == 0 }"> class="selected"</c:if> >
																Mã Khách Hàng</li>
																<li value="1" <c:if test="${ searchRegistrationForm.getCondition_user() == 1 }"> class="selected"</c:if> >
																Tên Khách Hàng</li>
																</ul>
															</a>
														</div>
													</div>
												</div>
												<div class="col-xs-4">
													<div class="panel panel-info">
														<div class="panel-body">
															<form:input type='text' class="form-control " path="input_condition_user" />
														</div>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-xs-2">
													<div class="panel panel-info">
														<div class="panel-body bg-info">
															<h5>
																Tour
															</h5>
														</div>
													</div>
												</div>
												<div class="col-xs-4">
													<div class="panel panel-info">
														<div class="panel-body">
															<a class="btn btn-default btn-select"> 
															<form:input path="condition_tour" type="hidden" class="btn-select-input" id="search_Hiddencondition_tour"
																	name="" value="" /> 
																<span class="btn-select-value" id="search_ValueSelectorcondition_tour">Select an Item </span> <span
																class="btn-select-arrow glyphicon glyphicon-chevron-down"></span>
																<ul>
																<li value="0" <c:if test="${ searchRegistrationForm.getCondition_tour() == 0 }"> class="selected"</c:if> >
																Mã Tour</li>
																<li value="1" <c:if test="${ searchRegistrationForm.getCondition_tour() == 1 }"> class="selected"</c:if> >
																Tên Tourt</li>
															
																</ul>
															</a>
														</div>
													</div>
												</div>
												<div class="col-xs-4">
													<div class="panel panel-info">
														<div class="panel-body">
															<form:input type='text' class="form-control " path="input_condition_tour" />
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
														<th style="width: 8%;">Mã tour</th>
														<th style="width: 7%;">Mã KH</th>
														<th style="width: 15%;">Tổng tiền</th>
														<th style="width: 15%;">Tiền cọc</th>
														<th style="width: 15%;">Số lượng người</th>
														<th style="width: 15%;">Ghi chú</th>
														<th style="width: 10%;">Trạng thái</th>
														<th style="width: 5%;"></th>
														<th style="width: 5%;"></th>
													</tr>
												</thead>
												<tbody id="userTableResult">
													<c:forEach var="registrationForm" items="${registrationFormList}">
														<tr>
															<td>${registrationForm.key}</td>
															<td>${registrationForm.key_tour}</td>
															<td>${registrationForm.key_user}</td>
															<td>
															<fmt:formatNumber type="number" value="${registrationForm.total_money}"
																groupingUsed="true" /> VNĐ</td>
															<td><fmt:formatNumber type="number" value="${registrationForm.deposit}"
																groupingUsed="true" /> VNĐ</td>
															<td>${registrationForm.count_people}</td>
															<td>${registrationForm.note}</td>
                                                            <td>
                                                                <c:if test="${registrationForm.status == '0'}">
                                                                    <c:out value="Đặt" />
                                                                </c:if> 
                                                                <c:if test="${registrationForm.status == '1'}">
                                                                    <<c:out value="Đặt cọc" />
                                                                </c:if> 
                                                                <c:if test="${registrationForm.status == '2'}">
                                                                    <c:out value="Hoàn Tất" />
                                                                </c:if>
															</td>
															<td>
																<a href="./getInformationRegistration?key_user=${registrationForm.key_user}&key_tour=${registrationForm.key_tour}&key_registration=${registrationForm.key}">
																	<button name="" class="btn btn-ref pull-right">Thông Tin
																	</button>
																</a>
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
	
</body>
</html>

