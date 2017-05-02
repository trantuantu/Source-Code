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
<link rel=stylesheet href=<c:url value="/admin_styles/chartist.min.css" />>
<link rel=stylesheet href=<c:url value="/admin_styles/bootstrap-toggle.min.css" />>

<script src=<c:url value="/admin_scripts/jquery-3.1.1.js" />></script>
<script src=<c:url value="/admin_scripts/bootstrap.min.js" />></script>
<script src=<c:url value="/admin_scripts/output.js" />></script>
<script src=<c:url value="/admin_scripts/chartist.min.js" />></script>
<script src=<c:url value="/admin_scripts/scripts.js" />></script>
<script src=<c:url value="/admin_scripts/scripts_admin.js" />></script>
<script src=<c:url value="/admin_scripts/bootstrap-datepicker.min.js" />></script>
<script src=<c:url value="/admin_scripts/chart.js" />></script>
<script src=<c:url value="/admin_scripts/bootstrap-toggle.min.js" />></script>

<script>
	$(function() {
		$('#start_year').datepicker({
			format : " yyyy", // Notice the Extra space at the beginning
			viewMode : "years",
			minViewMode : "years",
			autoclose : true
		});
		$('#end_year').datepicker({
			format : " yyyy", // Notice the Extra space at the beginning
			viewMode : "years",
			minViewMode : "years",
			autoclose : true
		});
		$('#year_detail').datepicker({
			format : " yyyy", // Notice the Extra space at the beginning
			viewMode : "years",
			minViewMode : "years",
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

				<div class="alert alert-warning" id="messages_warning">
				  <strong>Warning!</strong>
				</div>
					<!-- pannel -->
					<div class="row">
						<div class="col-xs-12">
							<div class="panel panel-primary">
								<div class="panel-heading">
									<h3 class="panel-title">Điều kiện thống kê</h3>
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
															<input path="Loai" type="hidden" class="btn-select-input" id="type_1" name="" value="0" /> 
															<span class="btn-select-value" id="search_ValueSelectorLoaiUser"> Select an Item </span> 
															<span class="btn-select-arrow glyphicon glyphicon-chevron-down"></span>
															<ul>
																<li value="0" class="selected">Tour</li>
																<li value="1">Khách du lịch</li>
																<li value="2">Doanh thu</li>
																<li value="3">Lợi nhuận</li>
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
														<h5>Theo<font color="red">* </font></h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<a class="btn btn-default btn-select"> 
														<input path="Loai" type="hidden" class="btn-select-input" id="type_2" name="" value="0" /> 
														<span class="btn-select-value" id="search_ValueSelectorLoaiUser">Select an Item </span> 
														<span class="btn-select-arrow glyphicon glyphicon-chevron-down"></span>
														<ul>
															<li value="0" class="selected">Năm</li>
															<li value="1" >Nhiều năm</li>
														</ul>
														</a>
													</div>
												</div>
											</div>
											<div id="selector_nam">
												<div class="col-xs-2 padding_reset">
													<div class="panel panel-info">
														<div class="panel-body bg-info">
															<h5>Năm<font color="red">* </font></h5>
														</div>
													</div>
												</div>
												<div class="col-xs-4 reset_padding_right">
													<div class="panel panel-info">
														<div class="panel-body">
															<input type='text' class="form-control " id="year_detail" name="birthday" path="NamSinh" />
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row" id="selector_nam_chitiet">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Chi tiết <font color="red">* </font></h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<a class="btn btn-default btn-select"> 
															<input path="Loai" type="hidden" class="btn-select-input" id="type_3" name="" value="0" /> 
															<span class="btn-select-value" id="search_ValueSelectorLoaiUser"> Select an Item </span> 
															<span class="btn-select-arrow glyphicon glyphicon-chevron-down"></span>
															<ul>
																<li value="0" class="selected">Tháng</li>
																<li value="1">Quí</li>
															</ul>
														</a>
													</div>
												</div>
											</div>
											
										</div>
										
										<div class="row" id="selector_nhieunam">
											<div class="col-xs-2">
												<div class="panel panel-info">
													<div class="panel-body bg-info">
														<h5>Năm bắt đầu <font color="red">* </font></h5>
													</div>
												</div>
											</div>
											<div class="col-xs-4">
												<div class="panel panel-info">
													<div class="panel-body">
														<input type='text' class="form-control " id="start_year" name="birthday" path="NamSinh" />
													</div>
												</div>
											</div>
											<div id="selector_nam">
												<div class="col-xs-2 padding_reset">
													<div class="panel panel-info">
														<div class="panel-body bg-info">
															<h5>Năm kết thúc<font color="red">* </font></h5>
														</div>
													</div>
												</div>
												<div class="col-xs-4 reset_padding_right">
													<div class="panel panel-info">
														<div class="panel-body">
															<input type='text' class="form-control " id="end_year" name="birthday" path="NamSinh" />
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

													<button  type="submit" name="action" value="search" onclick="xem()"
														class="btn btn-reg" >Xem</button>
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
				<!-- result search -->
				<br>
				<div><strong>Xem đồ thị</strong>
					<input id="toggle-event" type="checkbox" data-toggle="toggle" checked>
				</div>
				<div>
					<div id="chart_chuthich">
						<table>
							<tr>
								<td bgcolor="#d70206" style="width: 40px;"></td>
								<td >Quí 1</td>
								<td style="width: 40px;"></td>
								<td bgcolor="#f05b4f" style="width: 40px;"></td>
								<td>Quí 2</td>
								<td style="width: 40px;"></td>
								<td bgcolor="#f4c63d" style="width: 40px;"></td>
								<td>Quí 3</td>
								<td style="width: 40px;"></td>
								<td bgcolor="#d17905" style="width: 40px;"></td>
								<td>Quí 4</td>
								<td style="width: 40px;"></td>
							</tr>
						</table>
					</div>
					<div id="chart">
					</div>
					
						<table class="table table-bordered" id ="table">
						  <thead class="thead-default">
						    <tr>
						      
						    </tr>
						  </thead>
						  <tbody>
						    
						   
						  </tbody>
						</table>
					
				</div>
				<br>
			</div>
		</div>
	</div>
	<div id="modal_detailTour" class="modal fade" role="dialog">
		<div class="modal-dialog modal-lg" style="width: 600px !important; ">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Chi tiết các tour trong tháng</h4>
				</div>
				<div class="modal-body">
					<table class="table table-bordered" id ="table_detailTour">
						  <thead class="thead-default">
						    <tr>
						      <th style="text-align:center;">STT</th>
						      <th style="text-align:center;">Mã tour</th>
						      <th style="text-align:center;">Tên tour</th>
						      <th style="text-align:center;">Tổng tiền</th>
						    </tr>
						  </thead>
						  <tbody>
						    
						   
						  </tbody>
						</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info" data-dismiss="modal">Thoát</button>
				
				</div>
			</div>

		</div>
	</div>
</body>
</html>

