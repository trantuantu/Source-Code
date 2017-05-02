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
});
</script>
</head>
<body>
	
	<input type="hidden" id="modal_approva_isShow" value='${isApproval}'/>
	<div id="modal_notification" class="modal fade" role="dialog">
		<div class="modal-dialog modal-sm" style="width: 400px !important; ">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Thông báo</h4>
				</div>
				<div class="modal-body">
					<c:choose>
					  <c:when test="${registration.is_edit_tour==1}">
					  	<div class="row alert alert-danger" role="alert" style="margin-left: 0px !important;">
							<div class="col-md-10 " >
								<p>Bạn phải chỉnh sữa tour theo yêu cầu của khách hàng trước khi duyệt đăng kí<span id="showId" ></span></p>
							</div>
						</div>
					  </c:when>
					  <c:otherwise>
					   <c:if test="${errorMessagesApprova != null && errorMessagesApprova != '' && errorMessagesApprova != 'successfully' }">
							<div class="row alert alert-danger" role="alert" style="margin-left: 0px !important;">
								<div class="col-md-10 " >
									<p id="error_messages_edit" >${errorMessagesApprova }</p>
								</div>
							</div>
						</c:if>
					  </c:otherwise>
					</c:choose>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info" data-dismiss="modal">Ok</button>
				</div>
			</div>

		</div>
	</div>
	
	<input type="hidden" id="modal_edit_isShow" value='${isEdited}'/>
	<div id="modal_editCustomer" class="modal fade " role="dialog">
		<div class="modal-dialog modal-lg">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" style="text-align: left;">Thông tin khách hàng </h4>
				</div>
				<form:form action="editCustomer" method="post" modelAttribute="editCustomer">
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
							<div class="col-xs-2">
								<div class="panel panel-info">
									<div class="panel-body bg-info">
										<h5>
											Mã <font color="red"></font>
										</h5>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="panel panel-info">
									<div class="panel-body">
										<form:input path="Ma" type="hidden" class="btn-select-input" id="hidden_edit_Ma" name="" value="" />
										<input type='text' disabled="disabled" class="form-control " id="edit_Ma"  />
									</div>
								</div>
							</div>
						</div>
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
																path="Name" type='text' class="form-control "  id="username"
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
														<form:input type='text' class="form-control " path="phone" id="phone"/>
													</div>
												</div>
											</div> 
											<!-- <div  >
												<input type="submit"   value="Lưu" class="btn btn-reg" style="position: absolute; right: 0;" />
											</div> -->
											
										</div>
											<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Địa chỉ
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 ">
												<div class="panel panel-info">
													<div class="panel-body">
														
														<form:input
																path="address" type='text' class="form-control "  id="address"
																name="" value="" /> 
													</div>
												</div>
											</div>
											
											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															CMND <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
				
											<div class="col-xs-4 ">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="CMND" id="CMND"/>
													</div>
												</div>
											</div> 
											<!-- <div  >
												<input type="submit"   value="Lưu" class="btn btn-reg" style="position: absolute; right: 0;" />
											</div> -->
											
										</div>
									</div>
								</div>
								</div>
							</div>
					</div>
					<div class="modal-footer">
						
						

						<button type="button" onclick="editCustomer()" name="" class="btn1 btn-ref" data-toggle="modal" data-target="#modal_editCustomer" >Lưu
						</button>
						
						<button type="button" class="btn1 btn-default" data-dismiss="modal">Thoát</button>
					</div>
				</form:form> 
		
			</div>

		</div>
	</div>
	<input type="hidden" id="modal_editTour_isShow" value='${isEditedTour}'/>
	<div id="modal_editTour" class="modal fade " role="dialog">
		<div class="modal-dialog modal-lg">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" style="text-align: left;">Thông tin tour </h4>
				</div>
				<form:form action="editTourReality" method="post" modelAttribute="editTourReality">
					<div class="modal-body">
						<c:if test="${errorMessagesTour != null && errorMessagesTour != '' && errorMessagesTour != 'successfully' }">
							<div class="row alert alert-danger" role="alert" style="margin-left: 0px !important;">
								<div class="col-md-10 " >
									<p id="error_messages_edittour" >${errorMessagesTour}</p>
								</div>
							</div>
						</c:if>
						<c:if test="${errorMessagesTour == 'successfully' }">
							<div class="row alert alert-success" role="alert" style="margin-left: 0px !important;">
								<div class="col-md-10 " >
									<p id="error_messages_edittour" >Tour được sửa thành công</p>
								</div>
							</div>
						</c:if>
						
						
						<form:errors path="*" cssClass="errorblock"/>
						<!-- pannel -->
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
										<form:input path="ma_tour_goc" type="hidden" class="btn-select-input" id="hidden_key_Tour" name="" value="" />
										<input type='text' disabled="disabled" class="form-control " id="key_Tour"  />
										<form:hidden path="ma_phieu_dang_ki"  style="display:none" value="${registration.key}"/>
										
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
										<c:choose>
										  <c:when test="${registration.is_edit_tour==0}">
										   	 	<form:input type='hidden' class="btn-select-input" path="ten" id="hidden_name_tour"/>
												<input type='text' disabled="disabled"  class="form-control " id="name_tour" />
										  	</c:when>
										    <c:otherwise>
											    <form:input type='text' class="form-control"  path="ten" id="hidden_name_tour"/>
											</c:otherwise>
										</c:choose>
										
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-2">
								<div class="panel panel-info">
									<div class="panel-body bg-info">
										<h5>
											Ngày đi
										</h5>
									</div>
								</div>
							</div>
							<div class="col-xs-4 ">
								<div class="panel panel-info">
									<div class="panel-body">
										<c:choose>
										  <c:when test="${registration.is_edit_tour==0}">
										   	<form:input path="ngay_di" type='hidden' class="btn-select-input" id="hidden_date_start" /> 
                                            <input type='text' disabled="disabled" class="form-control" id="date_start" />
										  </c:when>
										  <c:otherwise>
                                               <form:input path="ngay_di" type='text' class="form-control datepicker" id="hidden_date_start" /> 
										  </c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
												
							<div class="col-xs-2 padding_reset">
								<div class="panel panel-info">
									<div class="panel-body bg-info">
										<h5>
											Ngày về <font color="red"></font>
										</h5>
									</div>
								</div>
							</div>
					
							<div class="col-xs-3 ">
								<div class="panel panel-info">
									<div class="panel-body">
										<c:choose>
										  <c:when test="${registration.is_edit_tour==0}">
										   	<form:input type='hidden' class="btn-select-input" path="ngay_ve"   id="hidden_date_end"/>
                                            <input type='text'  disabled="disabled" class="form-control" id="date_end" />
										  </c:when>
										  <c:otherwise>
										  		<form:input  id="hidden_date_end" type='text'  class="form-control datepicker" path="ngay_ve"/>
                                           <%--  <form:input type='text'  class="form-control datepicker" path="ngay_ve"/> --%>
										  </c:otherwise>
										</c:choose>
									</div>
								</div>
							</div> 
						</div>
						<c:if test="${registration.is_edit_tour != 2}">
							<div class="row">
								<div class="col-xs-2">
									<div class="panel panel-info">
										<div class="panel-body bg-info">
											<h5>
												Giới thiệu <font color="red"></font>
											</h5>
										</div>
									</div>
								</div>
								<div class="col-xs-10" style="padding-left: 0px !important;">
									<div class="panel panel-info">
										<div class="panel-body">
											<form:input type='hidden' class="btn-select-input" path="gioi_thieu" id ="hidden_introduce"/>
											<input type='text'  <c:if test="${registration.is_edit_tour==0}">
																	<c:out value="disabled" />
																</c:if>  class="form-control " id="introduce" />
										</div>
									</div>
								</div>
							</div>
						 </c:if>
						<div class="row">
							<c:choose>
							  <c:when test="${registration.is_edit_tour==0}">
							   	<div  class="col-xs-2">
									<div class="panel panel-info">
										<div class="panel-body bg-info">
											<h5>
												Chương Trình <font color="red"></font>
											</h5>
										</div>
									</div>
								</div>
							  </c:when>
							  <c:otherwise>
							  	<div style="margin-top: 10px;" class="col-xs-2">
									<div class="panel panel-info">
										<div class="panel-body bg-info">
											<h5>
												Chương Trình <font color="red"></font>
											</h5>
										</div>
									</div>
								</div>	
							  </c:otherwise>
							</c:choose>

							<div class="col-xs-10" style="padding-left: 0px !important;">
								<div class="panel panel-info">
									<div class="panel-body">
										<c:choose>
										  <c:when test="${registration.is_edit_tour==0}">
										     <%-- <form:textarea type='hidden' disabled="disabled" class="form-control" path="chuong_trinh" id ="hidden_program"/> --%>
                                             <textarea   disabled="disabled" class="form-control" id="program"> </textarea>
										  </c:when>
										  <c:otherwise>
                                                  <form:textarea  class="form-control" path="chuong_trinh" id = "hidden_program" required=""/>    
										  </c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<c:choose>
							  <c:when test="${registration.is_edit_tour==0}">
							   	<div  class="col-xs-2">
									<div class="panel panel-info">
										<div class="panel-body bg-info">
											<h5>
												Phương Tiện<font color="red"></font>
											</h5>
										</div>
									</div>
								</div>
							  </c:when>
							  <c:otherwise>
							  	<div style="margin-top: 10px;" class="col-xs-2">
									<div class="panel panel-info">
										<div class="panel-body bg-info">
											<h5>
												Phương Tiện <font color="red"></font>
											</h5>
										</div>
									</div>
								</div>	
							  </c:otherwise>
							</c:choose>
							<div class="col-xs-10" style="padding-left: 0px !important;">
								<div class="panel panel-info">
									<div class="panel-body">
										<c:choose>
										  <c:when test="${registration.is_edit_tour==0}">
										  	<%-- <form:input type='hidden' class="btn-select-input" path="phuong_tien" id ="hidden_expediency"/> --%>
                                            <textarea class="form-control"  disabled="disabled" class="form-control " id="expediency" ></textarea>
										  </c:when>
										  <c:otherwise>
                                              <form:textarea  class="form-control"  path="phuong_tien" id = "hidden_expediency" required=""/>
										  </c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
						  	<div  class="col-xs-2">
								<div class="panel panel-info">
									<div class="panel-body bg-info">
										<h5>
											Giá<font color="red"></font>
										</h5>
									</div>
								</div>
							</div>
							<div class="col-xs-3 ">
								<div class="panel panel-info">
									<div class="panel-body">
										<c:choose>
										  <c:when test="${registration.is_edit_tour==0}">
										   	<form:input type='hidden' class="btn-select-input" path="gia" id="hidden_gia"/>
											<input type='text'  disabled="disabled" class="form-control " id="gia" />
										  </c:when>
										  <c:otherwise>
										    <form:input type='text'  class="form-control" path="gia" id="hidden_gia"/>
										  </c:otherwise>
										</c:choose>
									</div>
								</div>
							</div> 
							
						</div>
						<c:if test="${registration.is_edit_tour != 2}">
							<div class="row">
								<div class="col-xs-2">
									<div class="panel panel-info">
										<div class="panel-body bg-info">
											<h5>
												Tổng vé
											</h5>
										</div>
									</div>
								</div>
								<div class="col-xs-4 ">
									<div class="panel panel-info">
										<div class="panel-body">
											
											<form:input
													path="so_ve" type='hidden' class="btn-select-input" 
													id="hidden_total_ticket" /> 
											<input  <c:if test="${registration.is_edit_tour==0}">
																	<c:out value="disabled"  />
																</c:if>  class="form-control " id="total_ticket" />
										</div>
									</div>
								</div>				
							<div class="col-xs-2 padding_reset">
								<div class="panel panel-info">
									<div class="panel-body bg-info">
										<h5>
											Vé còn lại <font color="red"></font>
										</h5>
									</div>
								</div>
							</div>
					
							<div class="col-xs-3 ">
								<div class="panel panel-info">
									<div class="panel-body">
										<form:input type='hidden' class="btn-select-input"  path="so_ve_con_lai"   id="hidden_remain_ticket"/>
										<input type='text'  <c:if test="${registration.is_edit_tour==0}">
																<c:out value="disabled" />
															</c:if>  class="form-control " id="remain_ticket" />
									</div>
								</div>
							</div> 
						</div>
						</c:if> 
					</div>
					<div class="modal-footer">
						
						<c:if test="${registration.is_edit_tour !=0}">
						
							<button type="submit"    class="btn1 btn-ref" data-toggle="modal" data-target="#modal_editTour" >Lưu
							</button>
						</c:if>

						<button type="button" class="btn1 btn-default" data-dismiss="modal">Thoát</button>
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
				<form:form action="approvalRegistration" method="post" modelAttribute="acceptRegistration">
					<c:if test="${registration.is_edit_tour == 1}">
						<div class="row alert alert-danger" role="alert" style="margin-left: 0px !important;">
							<div class="col-md-10" >
								<p id="error_messages_editTour" >(*) Bạn cần chỉnh sữa tour lại theo yêu cầu của khách hàng</p>
							</div>
						</div>
					</c:if>
					<!-- pannel -->
					<div class="row">
						<div class="col-xs-12">
							<div class="panel panel-primary">
								<div class="panel-heading">
									<h3 class="panel-title">Thông tin phiếu đăng kí</h3>
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
															Tên Khách Hàng
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 ">
												<div class="panel panel-info">
													<div class="panel-body">
														
														<form:input
                                                                path="name_user" type='hidden' class="form-control " 
																name="" value="${customer.name}" /> 
														<input type='text' disabled="disabled" class="form-control " value="${customer.name}"  />
																
													</div>
												</div>
											</div>
											
											<div class="col-xs-1 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Phone <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
				
											<div class="col-xs-3 ">
												<div class="panel panel-info">
													<div class="panel-body">
                                                        <form:input type='hidden' class="form-control " path="phone_customer"   value="${customer.phone}"/>
                                                        <input type='text' disabled="disabled" class="form-control " value="${customer.phone}"  />
													</div>
												</div>
											</div> 

											<div >
												<button type="button" onclick="loadCustomer(${customer.key})" name="" class="btn1 btn-ref" data-toggle="modal" data-target="#modal_editCustomer">Khách Hàng
												</button>
											</div>

	
										</div>
										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Tên Tour
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 ">
												<div class="panel panel-info">
													<div class="panel-body">
														
														<form:input
	                                                       	path="name_tour" type='hidden' class="form-control"  id ="accept_name_tour"
                                                            name="" value="${tour.ten}"/> 
                                                            <input type='text' disabled="disabled" class="form-control " id="accept_name_tour" value="${tour.ten}"  />
														<form:hidden path="key_tour" style="display:none" value="${tour.ma}"/>	
														<form:hidden path="key_registration"  style="display:none" value="${registration.key}"/>	
													</div>
												</div>
											</div>
											
											<div class="col-xs-1 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Giới Thiệu <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
											
											<div class="col-xs-4 ">
												<div class="panel panel-info">
													<div class="panel-body">
													
                                                        <form:input id="accept_introduce" type='hidden' class="form-control " path="introduce" name="" value="${tour.gioi_thieu}"/>
                                                        <input type='text' disabled="disabled" class="form-control " id="accept_introduce" value="${tour.gioi_thieu}"  />
													</div>
												</div>
											</div> 
											<div >
                                                <button type = "button" onclick="loadTour(${key_tour_goc},${registration.key})" name="" class="btn1 btn-ref" data-toggle="modal" data-target="#modal_editTour" >Tour
												</button>
											</div>


										</div>
											
										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Ngày đi
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 ">
												<div class="panel panel-info">
													<div class="panel-body">
														
														<form:input
                                                                path="date_start" type='hidden' class="form-control " 
																name="" value="${tour.ngay_di}" /> 
	                                                    <input type='text' disabled="disabled" class="form-control " value="${tour.ngay_di}"  />        															
													</div>
												</div>
											</div>
											
											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Ngày về <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
				
											<div class="col-xs-3 ">
												<div class="panel panel-info">
													<div class="panel-body">
                                                        <form:input type='hidden' class="form-control " path="date_end"   value="${tour.ngay_ve}"/>
                                                        <input type='text' disabled="disabled" class="form-control " value="${tour.ngay_ve}"  />
													</div>
												</div>
											</div> 
										</div>
										
										<div class="row">
										<div class="col-xs-2">
											<div class="panel panel-info">
												<div class="panel-body bg-info">
													<h5>
														Giá Tiền/Người 
													</h5>
												</div>
											</div>
										</div>
										<div class="col-xs-6" style="padding-left: 0px !important;">
											<div class="panel panel-info">
												<div class="panel-body">
                                                    <form:input type='hidden' class="form-control " path="prices" value ="${tour.gia}"/>
                                                    <input type='text' disabled="disabled" class="form-control " value="${tour.gia}"  />
												</div>
											</div>
										</div>
										</div>
										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Tiền đặt cọc
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 ">
												<div class="panel panel-info">
													<div class="panel-body">
														
														<form:input
																type='text' class="form-control " 
																path="deposit" value="${registration.deposit}" /> 
													</div>
												</div>
											</div>
											
											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Tổng tiền <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
				
											<div class="col-xs-3 ">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="total_money" value ="${registration.total_money}"/>
													</div>
												</div>
											</div> 
										</div>
											
										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Trạng thái
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4 ">
												<div class="panel panel-info">
													<div class="panel-body">
														<a class="btn btn-default btn-select"> 
														
														<form:input
																type="hidden" class="btn-select-input" id="edit_HiddenStatus"
																path="status" value="${registration.status}" />
																<span class="btn-select-value" id="edit_ValueSelectorStatus">Chọn loại </span> <span
															class="btn-select-arrow glyphicon glyphicon-chevron-down"></span>
															
															<ul>
															<li value="0" <c:if test="${registration.status==0}"> class="selected"</c:if> >Đặt</li>
															<c:if test="${registration.is_edit_tour != 0}">
																<li value="1" <c:if test="${registration.status==1}"> class="selected"</c:if> >Đặt cọc</li>
															</c:if>
															<li value="2" <c:if test="${registration.status==2}"> class="selected"</c:if> >Hoàn Tất</li>
															</ul>
														</a>		 
													</div>
												</div>
											</div>
											
											<div class="col-xs-2 padding_reset">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Số lượng người <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
				
											<div class="col-xs-3 ">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="count_people" value ="${registration.count_people}"/>
													</div>
												</div>
											</div> 
										</div>

										<div class="row">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>
															Ghi chú <font color="red"></font>
														</h5>
													</div>
												</div>
											</div>
											<div class="col-xs-10" style="padding-left: 0px !important;">
												<div class="panel panel-info">
													<div class="panel-body">
														<form:input type='text' class="form-control " path="note" value ="${registration.note}"/>
													</div>
												</div>
											</div>
										</div>
									</div>
										<div class="row btn_center">
											<div class="col-xs-12" style="padding-right: 0px;">
												<div class="panel panel-info">
													<div class="panel-footer">
														<c:choose>
															<c:when test="${registration.status!=2}">
																<c:choose>
																  	<c:when test="${registration.is_edit_tour==1 && registration.status!=2}">
																  		<button  type = "button" name="" class="btn btn-ref"  data-toggle="modal" data-target="#modal_notification">Duyệt
																		</button>
																  	</c:when>
																  	<c:otherwise>
																   		<button type="submit" name="" class="btn btn-ref" >Duyệt
																		</button>
																	</c:otherwise>
																</c:choose>
															</c:when>	  
														</c:choose>
														<input type="button" class="btn btn-ref"  onclick="location.href='./registrations'" value="Thoát" >
													</div>
												</div>
											</div>
										</div>
								</div>
							</div>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>

