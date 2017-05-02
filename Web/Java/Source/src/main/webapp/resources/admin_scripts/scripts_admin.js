// S-admin - user//
$(document).on('click', '.btn-select', function (e) {
    e.preventDefault();
    var ul = $(this).find("ul");
    if (! $(this).hasClass("active")) {
    	if($('#type_2').val() == 0){ // năm
    		$('#selector_nhieunam').hide();
    		$('#selector_nam').show();
    		$('#selector_nam_chitiet').show();
    	}
    	if($('#type_2').val() == 1){ // nhiều năm
    		$('#selector_nhieunam').show();
    		$('#selector_nam').hide();
    		$('#selector_nam_chitiet').hide();
    	}
        
    }
});


var deleteNghiPhep = function(stt, id) {
	$('#hidden_idNghiPhep').val(id);
	$('#showId').html(stt);

}



var duyetNghiPhep = function(userID, userName){
	$('#table_lnp > tbody > tr').remove();
	$('#msg-success').hide();
	$('#msg-error').hide();
	$('#msg-warning').hide();
	$('#Ma_duyet').val(userID);
	$('#HoTen_duyet').val(userName);
	$.ajax({
		url : "getNgayNghiByIdNV",
		type : "get",
		data : {
			'userId' : userID
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(result) {		
			console.log(result);
			if(result.length > 0){
				for(var i = 0; i < result.length; i++){
					$("#table_lnp > tbody").append(
							"<tr class='item" + result[i].id + "'>" +
							"<td align='center'>" + result[i].ngayNghi + "</td>" +
							"<td align='center'>" + result[i].ngayDiLam + "</td>" +
							"<td align='center'>" + getNumberDate(result[i].ngayNghi, result[i].ngayDiLam) + "</td>" +
							"<td align='center'>" +
					      	"<div class='checkbox radio-margin'>" +
								"<label>" +
									"<input type='checkbox' value='" + result[i].id + "'>" +
									"<span class='cr'>" +
										"<i class='cr-icon glyphicon glyphicon-ok'></i>" +
									"</span>" +
								"</label>" +
							"</div>" +
					      "</td>" +
					    "</tr>");
				}
				
			}
		}
	});
}

var detailNghiPhep = function(userID, userName){
	$('#table_denp > tbody > tr').remove();
	$('#msg-success-detail').hide();
	$('#msg-error-detail').hide();
	$('#msg-warning-detail').hide();
	$('#Ma_detail').val(userID);
	$('#HoTen_detail').val(userName);
	$.ajax({
		url : "getNgayNghiDaDuyetByIdNV",
		type : "get",
		data : {
			'userId' : userID
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(result) {		
			console.log(result);
			if(result.length > 0){
				for(var i = 0; i < result.length; i++){
					$("#table_denp > tbody").append(
							"<tr class='item" + result[i].id + "'>" +
							"<td align='center' style='padding: 0 !important;'>" +
								"<input type='text' id='ngnghi" + result[i].id + "' value='" + result[i].ngayNghi +  "' style='width: 100%;height: 110%;text-align: center;'>" +
							"</td>" +
							"<td align='center' style='padding: 0 !important;'>" + 
								"<input type='text' id='nglam" + result[i].id + "' value='" + result[i].ngayDiLam + "' style='width: 100%;height: 110%;text-align: center;'>" +
							"</td>" +
							"<td align='center'>" + getNumberDate(result[i].ngayNghi, result[i].ngayDiLam) + "</td>" +
							"<td align='center'>" +
					      	"<div class='checkbox radio-margin'>" +
								"<label>" +
									"<input type='checkbox' value='" + result[i].id + "'>" +
									"<span class='cr'>" +
										"<i class='cr-icon glyphicon glyphicon-ok'></i>" +
									"</span>" +
								"</label>" +
							"</div>" +
					      "</td>" +
					      "<td align='center'>" +
					      "<button type='button' class='btn btn-primary' onclick='saveEditNP(" + result[i].id +  ")'>Lưu</button>" +
					      "</td>" +
					    "</tr>");
				}
				
			}
		}
	});
}

var addNghiPhep = function(userID, userName) {
	$('#msg-success-add').hide();
	$('#msg-error-add').hide();
	$('#msg-warning-add').hide();
	$('#Ma_add').val(userID);
	$('#HoTen_add').val(userName);
	
}

var addNP = function() {
	var n1 = $('#add_ngNghi').val();
	var n2 = $('#add_ngLam').val();
	var x = new Date(n1.split("/")[2], n1.split("/")[1], n1.split("/")[0]);
	var y = new Date(n2.split("/")[2], n2.split("/")[1], n2.split("/")[0]);
	if(n1 == "" || n2 == ""){
		$('#msg-error-add').html("Chưa chọn ngày.");
		$('#msg-error-add').show();
	}
	if( x < y){
		$('#msg-error-add').hide();
		var urltemp = "";
		urltemp = "?userId=" + $('#Ma_add').val() + "&ngNghi=" + n1 + "&ngLam=" + n2 ;
		$.ajax({
			url : "themNNP" + urltemp,
			type : "get",
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(result) {
				if(result == true){
					$('#msg-success-add').show();
				}else{
					$('#msg-error-add').show();
				}
			}});
	}
}

var getNumberDate = function (s1,s2){
	var oneDay = 24*60*60*1000;
	var res = s1.split("/");
	var firstDate = new Date(res[2],res[1],res[0]);
	res = s2.split("/");
	var secondDate = new Date(res[2],res[1],res[0]);
	return Math.round(Math.abs((firstDate.getTime() - secondDate.getTime())/(oneDay)));
}



var saveNP = function (type){
	$('#msg-success').hide();
	$('#msg-error').hide();
	$('#msg-warning').hide();
	$('#msg-success-detail').hide();
	$('#msg-error-detail').hide();
	$('#msg-warning-detail').hide();
	if(type == 0){
		var searchIDs = $("input:checkbox:checked").map(function(){
		      return {value : $(this).val()};
		    }).get();
		if(searchIDs.length > 0){
			var urltemp = "?", i = 0;
			searchIDs.forEach(function(e){
				urltemp += "value=" + e.value;
				if(i < searchIDs.length - 1)
					urltemp += "&";
			   i++;
			});
			console.log(urltemp);
			$.ajax({
				url : "duyetNNP" + urltemp,
				type : "get",
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept", "application/json");
					xhr.setRequestHeader("Content-Type", "application/json");
				},
				success : function(result) {
					if(result == true){
						searchIDs.forEach(function(e){
							$('#table_lnp > tbody > tr[class=item' + e.value + ']').remove();
						});
						$('#msg-success').show();
					}else{
						$('#msg-error').show();
					}
				}});
		}else{
			$('#msg-warning').show();
		}
	}else{ // xóa các mục đã chọn
		var searchIDs = $("input:checkbox:checked").map(function(){
		      return {value : $(this).val()};
		    }).get();
		if(searchIDs.length > 0){
			var urltemp = "?", i = 0;
			searchIDs.forEach(function(e){
				urltemp += "value=" + e.value;
				if(i < searchIDs.length - 1)
					urltemp += "&";
			   i++;
			});
			console.log(urltemp);
			$.ajax({
				url : "xoaNNP" + urltemp,
				type : "get",
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept", "application/json");
					xhr.setRequestHeader("Content-Type", "application/json");
				},
				success : function(result) {
					if(result == true){
						searchIDs.forEach(function(e){
							$('#table_denp > tbody > tr[class=item' + e.value + ']').remove();
						});
						$('#msg-success-detail').html("Xóa thành công");
						$('#msg-success-detail').show();
					}else{
						$('#msg-success-detail').hide();
					}
				}});
		}else{
			$('#msg-warning-detail').show();
		}
	}
	
}

var saveEditNP = function (id){
	$('#msg-success-detail').hide();
	$('#msg-error-detail').hide();
	$('#msg-warning-detail').hide();
	var ngNgi = $('#ngnghi' + id).val();
	var ngLam =$('#nglam' + id).val();
	var pattern =/^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
	var flag = true;
	if(!pattern.test(ngNgi)){
		flag = false;
		$('#msg-error-detail').html("Ngày nghỉ không đúng format(dd/MM/yyyy)");
		$('#msg-error-detail').show();
		
	}
	if(!pattern.test(ngLam)){
		flag = false;
		$('#msg-error-detail').html("Ngày làm lại không đúng format(dd/MM/yyyy)");
		$('#msg-error-detail').show();
	}
	if(flag){
		$.ajax({
			url : "getNgayNghiByIdNV",
			type : "get",
			data : {
				'id' : userID,
				'ngNghi' : ngNgi,
				'ngLam' : ngLam
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(result) {		
				$('#msg-success-detail').html("Sửa thành công");
				$('#msg-success-detail').show();
			}
		});
	}
}


$(function() {
	$('#add_ngNghi').datepicker({
		format : "dd/mm/yyyy", // Notice the Extra space at the beginning
		autoclose : true
	});
	$('#add_ngLam').datepicker({
		format : "dd/mm/yyyy", // Notice the Extra space at the beginning
		autoclose : true
	});
	$('#add_ngNghi').datepicker().on('changeDate', function(e) {
		var n1 = $('#add_ngNghi').val();
		var n2 = $('#add_ngLam').val();
		var x = new Date(n1.split("/")[2], n1.split("/")[1], n1.split("/")[0]);
		var y = new Date(n2.split("/")[2], n2.split("/")[1], n2.split("/")[0]);
		if( x >= y){
			$('#msg-error-add').html("Ngày nghỉ phải nhỏ hơn ngày làm lại");
			$('#msg-error-add').show();
		}else{
			$('#msg-error-add').hide();
			$('#add_slngay').html(getNumberDate(n1, n2));
		}
	});
	$('#add_ngLam').datepicker().on('changeDate', function(e) {
		var n1 = $('#add_ngNghi').val();
		var n2 = $('#add_ngLam').val();
		var x = new Date(n1.split("/")[2], n1.split("/")[1], n1.split("/")[0]);
		var y = new Date(n2.split("/")[2], n2.split("/")[1], n2.split("/")[0]);
		if( x >= y){
			$('#msg-error-add').html("Ngày nghỉ phải nhỏ hơn ngày làm lại");
			$('#msg-error-add').show();
		}else{
			$('#msg-error-add').hide();
			$('#add_slngay').html(getNumberDate(n1, n2));
		}
	});
});


/////////////////////////Duyệt tour//////////////////////////////
$(function() {
	$('.datepicker').datepicker({
		format : " yyyy", // Notice the Extra space at the beginning
		viewMode : "years",
		minViewMode : "years",
		autoclose : true
	});
	$('#modal_selectuser').on('shown.bs.modal', function(e){
		//$('#table_selectuser > tbody > tr').remove();
		$('#modal_infoTour').modal('hide');
	});
	$('#modal_selectuser').on('hide.bs.modal', function(e){
		$('#table_selectuser > tbody > tr').remove();
		$('#selectUserTablePager > li').remove();
		
		$('#modal_infoTour').modal('toggle');
	});
	$('#modal_infoTour').on('hide.bs.modal', function(e){
		$('#msg-success-duyettour').hide();
		$('#msg-error-duyettour').hide();
		$('#msg-warning-duyettour').hide();
	});
});
var searchUser = function(){
	$("#selectUserTablePager").remove();
	var gt = $("input[name=gender]:checked").val();
	var ma = $("#Ma_User").val();
	var tk = $("#TaiKhoan_User").val();
	var hoten = $("#HoTen_User").val();
	var diachi = $("#DiaChi_User").val();
	var namsinh = $("#NamSinh_User").val();
	
	$.ajax({
		url : "getListUser?Ma=" + ma + "&GioiTinh=" + gt + "&TaiKhoan=" + tk + "&HoTen" + hoten + "&DiaChi" + diachi + "&NamSinh" + namsinh,
		type : "get",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(result) {
			console.log(result);
			$('#table_selectuser > tbody > tr').remove();
			var count = result.length;
			$("#countUser").text("Kết quả tìm kiếm： " + count + " record");
			for(var i = 0; i < count; i++){
				var gt = (result[i].gioiTinh == "1")? "Nam" : "Nữ";
				$("#table_selectuser > tbody").append(
					"<tr>" + 
						"<td>" + result[i].ma + "</td>" +
						"<td>" + result[i].hoTen + "</td>" +
						"<td>" + result[i].taiKhoan + "</td>" +
						"<td>" + result[i].namSinh + "</td>" +
						"<td>" + gt + "</td>" +
						"<td>" + result[i].cmnd + "</td>" +
						"<td>" + result[i].soDienThoai + "</td>" +
						"<td>" + result[i].diaChi + "</td>" +
						"<td>" + "<button onclick='selectAUser(" + result[i].ma + ") '" + " class='btn btn-ref pull-right' >Chọn</button>" + "</td>" +
						"<input type='hidden' id='nameUserSelected" + result[i].ma + "' value='" + result[i].hoTen + "'/>" +
					"</tr>"	
						);
				
			}
//			$("#table_selectuser").tablesorter({sortList: [[0,0]],  headers: { 
//	            9: { sorter: false }
//	        }});
			$('#selectUserTableResult').pageMe({pagerSelector:'#selectUserTablePager',showPrevNext:true,hidePageNumbers:false,perPage:5});
		}});
}

var selectAUser = function(id_user){
	 var name = $('#nameUserSelected' + id_user).val();
	 $('#userSelected').val(id_user);
	 $('#modal_selectuser').modal('hide');
	 $('#Ma_NV').val(id_user);
	 $('#Ten_NV').val(name);

}

var huyTour = function(tourID) {
	$('#showId').html(tourID);
	$('#hidden_Ma').val(tourID);
	
}

var duyetTour = function() {
	var idhdv = $('#Ma_NV').val();
	var idtour = $('#hidden_MaTour_Modal').val();
	if(idhdv == "" || idtour == ""){
		$('#msg-error-duyettour').html("Chưa chọn nhân viên.");
		$('#msg-error-duyettour').show();
	}else{
		$.ajax({
		url : "comfirmDuyetTour?IdTour=" + idtour + "&IdHDV=" + idhdv,
		type : "get",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(result) {
			$('#msg-error-duyettour').hide();
			$('#msg-warning-duyettour').hide();
			$('#msg-success-duyettour').show();
			location.reload();
		}});
	}

}

var duyetTour_info = function(idTour) {
	//$("#hidden_MaTour_Modal").val(idTour);
	$.ajax({
		url : "getTourById",
		type : "get",
		data : {
			'Id' : idTour
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(result) {
			console.log(result);
			if(result.ma == null)
				$('#MaTour1').val(result.ma_tour_goc);
			else
				$('#MaTour1').val(result.ma);
			$('#TenTour').val(result.ten);
			$('#NgayDi').val(result.ngay_di);
			$('#NgayVe').val(result.ngay_ve);
			$('#SoVe').val(result.so_ve);
			$('#SoVeConLai').val(result.so_ve_con_lai);
			$('#PhuongTien').val(result.phuong_tien);
			$('#ChuongTrinh').val(result.chuong_trinh);
			$('#Gia').val(result.gia);
			$('#Ma_NV').val(result.ma_huong_dan_vien);
			$('#Ten_NV').val(result.ten_huong_dan_vien);
			if(result.ma == null)
				$('#hidden_MaTour_Modal').val(result.ma_tour_goc);
			else
				$('#hidden_MaTour_Modal').val(result.ma);
			$('#hidden_MaHDV_Modal').val($('#Ma_NV').val());
		}});

}























