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
			<c:if test="${not empty authen}">
				<c:set var="userType" value="${authen.getLoai() }"
					 scope="session"/>
				 <c:set var="userId" value="${authen.getMa() }"
			 scope="session"/>
			 </c:if>
			<jsp:include page="components/nav_manager.jsp" />
			
		</div>
	</div>

</body>
</html>

