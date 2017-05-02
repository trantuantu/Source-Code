// S-admin - user//

var _array_place = [];
var _array_hotel= [];
var _array_restaurant = [];

var deleteUser = function(userId) {
	$('#hidden_idUser').val(userId);
	$('#showId').html(userId);

}

var acceptRegistration = function(key_user,key_tour) {
	$.ajax({
		url : "informationRegistration",
		type : "get",
		data : {
			'key_user' : key_user,
			'key_tour': key_tour	
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(result) {
			location.href ="./getInformationRegistration";
			alert(result)
			var user = JSON.parse(result[1]);
			var tour = JSON.parse(result[0]);
			$('#acceptRegistration_username').val(user.name);
			$('#acceptRegistration_phone').val(user.phone);
			$('#acceptRegistration_guide').val(tour.name);
			
			console.log(result[1]);
		},
		error : function(data) {
			console.log(data);
			
		}
		
	});
	
}
var editCustomer = function() {
	var data = $("#editCustomer").serialize().split("&");
    console.log(data);
    var obj={};
    for(var key in data)
    {
        console.log(data[key]);
        obj[data[key].split("=")[0]] = data[key].split("=")[1];
    }

    console.log(obj);
    var url = location.href;
  
    //var param = "?"+'key_user='+key_user + '&key_tour='+key_tour + '&key_registration='+key_registration;
	$.ajax({
		type:"post",
		crossDomain: true,
		data:obj,
	    url:"editCustomer",
	    success: function(data){
	    	var string = getParameterByName("action",url);
	    	if(string != null){
	    		console.log("abc");
	    		url = url.substring(0,url.indexOf("&action"));
	    		
	    	}		
	    	location.href = url + "&action=editCustomer";	
	    },
	    error: function(response) {
	        console.log(response.status + " " + response.statusText);
	    }
	});
	
}
function getParameterByName(name, url) {
    if (!url) {
      url = window.location.href;
    }
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
var loadTour = function(key_tour,key_registration) {
	$.ajax({
		url : "informationTour",
		type : "get",
		data : {
			'key_tour' : key_tour,
			'key_registration':key_registration
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(result) {
			
			$('#hidden_key_Tour').val(result.ma);
			$('#hidden_name_tour').val(result.ten);
			$('#name_tour').val(result.ten);
			$('#key_Tour').val(result.ma);
			$('#hidden_date_start').val(result.ngay_di);
			$('#date_start').val(result.ngay_di);
			$('#date_end').val(result.ngay_ve);
			$('#hidden_date_end').val(result.ngay_ve);
			$('#introduce').val(result.gioi_thieu);
			$('#hidden_introduce').val(result.gioi_thieu);
			$('#program').val(result.chuong_trinh);
			$('#hidden_program').val(result.chuong_trinh);
			$('#expediency').val(result.phuong_tien);
			$('#hidden_expediency').val(result.phuong_tien);
			$('#total_ticket').val(result.so_ve);
			$('#hidden_total_ticket').val(result.so_ve);
			$('#remain_ticket').val(result.so_ve_con_lai);
			$('#hidden_remain_ticket').val(result.so_ve_con_lai);
			$('#hidden_gia').val(result.gia);
			$('#gia').val(result.gia);
			
			//$('#display_none').val(key_registration);
			console.log(result);
		},
		error : function(data) {
			console.log(data);
			
		}
		
	});
	
}

var loadHotel = function(id_place){
    $.ajax({
        url : "informationHotel",
        type : "get",
        data : {
            'id_place' : id_place
        },
        beforeSend : function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success : function(result) {
            console.log(result);
            //var objtemp = JSON.parse(result);
            $('.listloadHotel').remove();
            for(var j = 0; j< result.length;j++){
                
                $('#userTableLoadResultHotel').append(
                        "<tr class='listloadHotel'>" +
                            "<td>"+result[j].ma+ "</td>" +
                            "<td>"+result[j].ten+ "</td>" +
                            "<td>"+result[j].diaChi+ "</td>" +
                            "<td>"+result[j].loai+ "</td>" +
                        "<tr>"
                );
            }
        },
        error : function(data) {
            console.log(data);
            
        }
        
    });
    
}

var loadRestaurant = function(id_place){
    $.ajax({
        url : "informationRestaurant",
        type : "get",
        data : {
            'id_place' : id_place
        },
        beforeSend : function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success : function(result) {
            console.log(result);
            //var objtemp = JSON.parse(result);
            $('.listloadRestaurant').remove();
            for(var j = 0; j< result.length;j++){
                
                $('#userTableLoadResultRestaurant').append(
                        "<tr class='listloadRestaurant'>" +
                            "<td>"+result[j].ma+ "</td>" +
                            "<td>"+result[j].ten+ "</td>" +
                            "<td>"+result[j].diaChi+ "</td>" +
                            "<td>"+result[j].loai+ "</td>" +
                        "<tr>"
                );
            }
        },
        error : function(data) {
            console.log(data);
            
        }
        
    });
    
}

var loadTourById = function(idTour){
	$.ajax({
		url : "getInformationTour",
		type : "get",
		data : {
			'idTour' : idTour
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(result) {
			var obj = JSON.parse(result[1]);
			var objtemp = JSON.parse(result[0]);
			console.log(objtemp);
			$('#name_tour').val(obj.ten);
			$('#total_ticket').val(obj.so_ve);
			$('#date_start').val(obj.ngay_di);
			$('#date_end').val(obj.ngay_ve);
			$('#introduce').val(obj.gioi_thieu);
			$('#program').val(obj.chuong_trinh);
			$('#expediency').val(obj.phuong_tien);
			$('#prices').val(obj.gia);
			$('#ma').val(obj.ma);
			
            $('.listDanhSachDiaDiem').remove();
			for(var i = 0; i < objtemp.length; i++){
				$('#userTableResult1').append(
                        "<tr class='listDanhSachDiaDiem'>" +
							"<td>"+objtemp[i].ma+ "</td>" +
							"<td>"+objtemp[i].ten+ "</td>" +
							"<td>"+objtemp[i].gia+ "</td>" +
							"<td>"+objtemp[i].dia_diem+ "</td>" +
							"<td>"+objtemp[i].thong_tin+ "</td>" +
                            "<td>" +
                            	"<button type='button' class='btn btn-ref pull-right' data-toggle='modal' data-target='#modal_loadhotel'" +"onclick='loadHotel("+objtemp[i].ma+")'>Kh.Sạn</button>"+
	                        "<td>"+
	                        "<td>" +
                            	"<button type='button' class='btn btn-ref pull-right' data-toggle='modal' data-target='#modal_loadRestaurant'" +"onclick='loadRestaurant("+objtemp[i].ma+")'>N.Hàng</button>"+
                            "<td>"+
						"<tr>"
				);
			}
			
		},
		error : function(data) {
			console.log(data);
		}
	});
}

var loadCustomer = function(key_user) {
	$.ajax({
		url : "informationCustomer",
		type : "get",
		data : {
			'key_user' : key_user
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(result) {
			
			$('#username').val(result.name);
			$('#phone').val(result.phone);
			$('#CMND').val(result.cmnd);
			$('#address').val(result.address);
			$('#edit_MaHotel').val(result.key);
			$('#edit_Ma').val(result.key);
			$('#hidden_edit_Ma').val(result.key);
			console.log(result);
		},
		error : function(data) {
			console.log(data);
			
		}
		
	});
	
}
function removeA(arr) {
    var what, a = arguments, L = a.length, ax;
    while (L > 1 && arr.length) {
        what = a[--L];
        while ((ax= arr.indexOf(what)) !== -1) {
            arr.splice(ax, 1);
        }
    }
    return arr;
}

var chooseHotel = function(checkbox,id_hotel,id_place){
    var choose = checkbox.checked;
    var element = {}
    element.idplace = id_place;
    element.idhotel = id_hotel;
    if(choose==true){
        
        _array_hotel.push(element);
        
    }
    else{
        _array_hotel = _array_hotel.filter(function(el) {
            return el.idhotel != id_hotel && el.idplace == id_place;
        });
        
    }
}
var chooseRestaurant = function(checkbox,id_restaurant,id_place){
    var choose = checkbox.checked;
    var element = {}
    element.idplace = id_place;
    element.idrestaurant = id_restaurant;
    if(choose==true){
        
        _array_restaurant.push(element);
        
    }
    else{
        _array_restaurant = _array_restaurant.filter(function(el) {
            return el.idrestaurant != id_restaurant && el.idplace == id_place;
        });
        
    }
}

var chooseRestaurantHotel = function(checkbox,id_place){
    var choose = checkbox.checked;
    if(choose==true){
        _array_place.push(id_place);
        $.ajax({
            url : "getlistHotelRetaurant",
            type : "get",
            data : {
                'id_place' : id_place
            },
            beforeSend : function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success : function(result) {
                
                var obj = JSON.parse(result[1]);
                var objtemp = JSON.parse(result[0]);
                for(var i = 0 ; i <result.length; i++){
                    var objtemp = JSON.parse(result[i]);
                    if(i==0){
                        $('.listHotel').remove();
                        for(var j = 0; j< objtemp.length;j++){
                            
                            $('#userTableResultHotel').append(
                                    "<tr class='listHotel'>" +
                                        "<td>"+objtemp[j].ma+ "</td>" +
                                        "<td>"+objtemp[j].ten+ "</td>" +
                                        "<td>"+objtemp[j].loai+ "</td>" +
                                        "<td>"+objtemp[j].diaChi+ "</td>" +
                                        "<td>" +
                                            "<input type='checkbox' name='chooseHotel'  value = '"+objtemp[j].ma+"'" +"onclick='chooseHotel(this,"+objtemp[j].ma +"," +id_place+")'>"+
                                        "<td>"+
                                    "<tr>"
                            );
                        }
                    }
                    else
                    {
                        $('.listRestaurant').remove();
                        for(var j = 0; j< objtemp.length;j++){
                            
                            $('#userTableResultRestaurant').append(
                                    "<tr class='listRestaurant'>" +
                                        "<td>"+objtemp[j].ma+ "</td>" +
                                        "<td>"+objtemp[j].ten+ "</td>" +
                                        "<td>"+objtemp[j].loai+ "</td>" +
                                        "<td>"+objtemp[j].diaChi+ "</td>" +
                                        "<td>" +
                                            "<input type='checkbox' name='chooseRestaurant'  value = '"+objtemp[j].ma+"'" +"onclick='chooseRestaurant(this,"+objtemp[j].ma +"," +id_place+")'>"+
                                        "<td>"+
                                    "<tr>"
                            );
                        }
                        
                    }
                }
                $('#modal_chooseRestaurantHotel').modal('show');
            },
            error : function(data) {
                console.log(data);
                
            }
            
        });
        
    }else{
        removeA(_array_place,id_place);
    }
    
}
var checkbox = function(){
	var arr_checked = [];
    $("input[name='chooseplace']:checked").each(function (){
		arr_checked.push($(this).val());
		
	});
    if(arr_checked.length <= 0){
        $('#modal_notification').modal('show');
    }else{
        $.ajax({
            url : "addTour",
            type : "get",
            data : {
                'checked': arr_checked.join(","),    
                'hotel': JSON.stringify(_array_hotel),
                'restaurant': JSON.stringify(_array_restaurant),
                'action': 'add'
            },
            beforeSend : function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success : function(result) {
                var string = location.href;
                var index = location.href.indexOf('?');
                if(index >-1)
                    string = location.href.substring(0,index);
                location.href = string;
            },
            error : function(data) {
                console.log(data);
                
            }
        
        });
    }
}
var userData = {
		 storeUserDataInSession: function(userData) {
		     var userObjectString = JSON.stringify(userData);
		     window.sessionStorage.setItem('userObject',userObjectString)
		 },
		 getUserDataFromSession: function() {
		     var userData = window.sessionStorage.getItem('userObject')
		     return JSON.parse(userData);
		 }
		}
var editUser = function(userId) {

	$.ajax({
		url : "getUserById",
		type : "get",
		data : {
			'userId' : userId
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(result) {
			console.log(result);
			$('#edit_CMNDUser').val(result.cmnd);
			$('#edit_MaUser').val(result.ma);
			$('#edit_TaiKhoanUser').val(result.taiKhoan);
			$('#edit_HoTenUser').val(result.hoTen);
			$('#edit_DiaChiUser').val(result.diaChi);
			$('#edit_SoDTUser').val(result.soDienThoai);
			$('#edit_NamSinhUser').val(result.namSinh);
			$('#edit_LoaiUser').val(result.loai);
			$('#hidden_edit_MaUser').val(result.ma);
			
			if (result.gioiTinh == "1") {
//				$("#edit_Nam").attr('checked', 'checked');
				$('input:radio[name=GioiTinh][value=1]').click();
			}
			if (result.gioiTinh == "0") {
//				$("#edit_Nu").attr('checked', 'checked');
				$('input:radio[name=GioiTinh][value=0]').click();
			}

			if (result.loai == "0") {
				$("#edit_HiddenLoaiUser").val(result.loai);
				$("#edit_ValueSelectorLoaiUser").html("Quản lý	");
			}else if (result.loai == "1") {
				$("#edit_HiddenLoaiUser").val(result.loai);
				$("#edit_ValueSelectorLoaiUser").html("Hướng dẫn viên");
			}else if (result.loai == "2") {
				$("#edit_HiddenLoaiUser").val(result.loai);
				$("#edit_ValueSelectorLoaiUser").html("Nhân viên");
			}else{
				$("#edit_HiddenLoaiUser").val(result.loai);
				$("#edit_ValueSelectorLoaiUser").html("Chọn loại");
			}

		},
		error : function(data) {
			console.log(data);
		}
	});

}

var resetPassword_1 = function(userId) {
	$('#showId_reset').html(userId);
}
var resetPassword_2 = function() {
	var userId = $('#showId_reset').html();
	$.ajax({
		url : "resetPassword?id=" + userId,
		type : "get",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(result) {
			if(result == true){
				$('#msg-success-reset').show();
				$('#modal_reset').modal('toggle'); 
			}else{
				$('#msg-success-reset').hide();
			}
		}});
}
$(function() {
	$('#msg-success-reset').hide();
	$('#selector_nhieunam').hide(); // man hinh thong ke
	$('#selector_nam').show(); // man hinh thong ke
	
	$('#modal_edit').on('hidden.bs.modal', function(e)
    { 
        $(this).removeData();
        $("#error_messages_edit" ).empty();
        
    });
	
	
	$('#modal_edit').on('shown.bs.modal', function(e)
    { 
        $("#edit_MaUser" ).val($('#hidden_edit_MaUser').val());
        
        if ($("#edit_HiddenLoaiUser").val() == "0") {
			$("#edit_ValueSelectorLoaiUser").html("Quản lý	");
		}else if ($("#edit_HiddenLoaiUser").val() == "1") {
			$("#edit_ValueSelectorLoaiUser").html("Hướng dẫn viên");
		}else if ($("#edit_HiddenLoaiUser").val() == "2") {
			$("#edit_ValueSelectorLoaiUser").html("Nhân viên");
		}else{
			$("#edit_ValueSelectorLoaiUser").html("Chọn loại");
		}
        if($('#error_messages_edit').html() ==  "")
        	$('#error_messages_edit').parent().parent().hide();
    });
	
	if($('#modal_edit_isShow').val() == "true"){
		$('#modal_edit').modal('show'); 
		$('#modal_editHotel').modal('show'); 
		$('#modal_editRestaurant').modal('show');
		$('#modal_editCustomer').modal('show');

	}
	if($('#modal_editTour_isShow').val() == "true"){
		$('#modal_editTour').modal('show');	
	}
	if($('#modal_approva_isShow').val() == "true"){
		$('#modal_notification').modal('show');	
	}
    if($('#modal_editPlace_isShow').val() == "true"){
        $('#modal_editPlace').modal('show');    
    }
    
    if($('#modal_notification_isShow').val() == "true"){
        $('#modal_notification1').modal('show');    
    }

});

$(function() {
    $('#modal_editPlace').on('hidden.bs.modal', function(e)
    { 
        $(this).removeData();
        $("#error_messages_edit" ).empty();  
    });
    
    $('#modal_editPlace').on('shown.bs.modal', function(e)
    { 
        if($('#error_messages_edit').html() ==  "")
            $('#error_messages_edit').parent().parent().hide();
    });    
});
$(function() {
	$('#modal_editCustomer').on('hidden.bs.modal', function(e)
    { 
        $(this).removeData();
        $("#error_messages_edit" ).empty();
        $("#error_messages_edittour" ).empty();
        
    });
	
	$('#modal_editCustomer').on('shown.bs.modal', function(e)
    { 
        $("#edit_Ma" ).val($('#hidden_edit_Ma').val());
        if($('#error_messages_edit').html() ==  "")
        	$('#error_messages_edit').parent().parent().hide();
    });	
});

$(function() {
	$('#modal_editTour').on('hidden.bs.modal', function(e)
    { 
        $(this).removeData();
        $("#error_messages_edittour" ).empty();
        $("#error_messages_edit" ).empty();
        
    });
	
	$('#modal_editTour').on('shown.bs.modal', function(e)
    { 
        $("#key_Tour" ).val($('#hidden_key_Tour').val());
        if($('#error_messages_edittour').html() ==  "")
        	$('#error_messages_edittour').parent().parent().hide();
    });	
});

$(function() {
	$('#modal_editHotel').on('hidden.bs.modal', function(e)
    { 
        $(this).removeData();
        $("#error_messages_edit" ).empty();
    });
	$('#modal_editHotel').on('shown.bs.modal', function(e)
    { 
        $("#edit_MaHotel" ).val($('#hidden_edit_MaHotel').val());
        
        if ($("#edit_HiddenLoaiHotel").val() == "0") {
			$("#edit_ValueSelectorLoaiHotel").html("Thường");
		}else if ($("#edit_HiddenLoaiHotel").val() == "1") {
			$("#edit_ValueSelectorLoaiHotel").html("* (1 sao)");
		}else if ($("#edit_HiddenLoaiHotel").val() == "2") {
			$("#edit_ValueSelectorLoaiHotel").html("** (2 sao)n");
		}else if ($("#edit_HiddenLoaiHotel").val() == "3") {
			$("#edit_ValueSelectorLoaiHotel").html("*** (3 sao)");
		}else if ($("#edit_HiddenLoaiHotel").val() == "4") {
			$("#edit_ValueSelectorLoaiHotel").html("**** (4 sao)");
		}else if ($("#edit_HiddenLoaiHotel").val() == "5") {
			$("#edit_ValueSelectorLoaiHotel").html("***** (5 sao)");
		}
        
        if($('#error_messages_edit').html() ==  "")
        	$('#error_messages_edit').parent().parent().hide();
    });	
});

var addUserClear = function(){
	$('#HoTen').val("");
	$('#TaiKhoan').val("");
	$('#SoDT').val("");
	$('#birthday').val("");
	$('#CMND').val("");
	$('#DiaChi').val("");
	$("#edit_HiddenLoaiUser").val(0);
	$("#edit_ValueSelectorLoaiUser").html("Quản lý	");
	$('input:radio[name=GioiTinh][value=1]').click();
}
var searchUserClear = function(){
	$('#HoTen').val("");
	$('#TaiKhoan').val("");
	$('#SoDT').val("");
	$('#birthday').val("");
	$('#CMND').val("");
	$('#DiaChi').val("");
	$('#Ma').val("");
	$("#search_HiddenLoaiUser").val(3);
	$("#search_ValueSelectorLoaiUser").html("Tất cả");
	$('input:radio[name=GioiTinh][value=2]').click();
}
///////////////////////////////////////
var editHotel = function(hotelId) {

	$.ajax({
		url : "getHotelById",
		type : "get",
		data : {
			'hotelId' : hotelId
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(result) {
			console.log(result);
			$('#edit_MaHotel').val(result.ma);
			$('#edit_TenHotel').val(result.ten);
			$('#edit_DiaChiHotel').val(result.diaChi);
			$('#edit_LoaiHotel').val(result.loai);
			$('#hidden_edit_MaHotel').val(result.ma);
			


			if (result.loai == "0") {
				$("#edit_HiddenLoaiHotel").val(result.loai);
				$("#edit_ValueSelectorLoaiHotel").html("Thường");
			}else if (result.loai == "1") {
				$("#edit_HiddenLoaiHotel").val(result.loai);
				$("#edit_ValueSelectorLoaiHotel").html("* (1 sao)");
			}else if (result.loai == "2") {
				$("#edit_HiddenLoaiHotel").val(result.loai);
				$("#edit_ValueSelectorLoaiHotel").html("** (2 sao)n");
			}else if (result.loai == "3") {
				$("#edit_HiddenLoaiHotel").val(result.loai);
				$("#edit_ValueSelectorLoaiHotel").html("*** (3 sao)");
			}else if (result.loai == "4") {
				$("#edit_HiddenLoaiHotel").val(result.loai);
				$("#edit_ValueSelectorLoaiHotel").html("**** (4 sao)");
			}else if (result.loai == "5") {
				$("#edit_HiddenLoaiHotel").val(result.loai);
				$("#edit_ValueSelectorLoaiHotel").html("***** (5 sao)");
			}

		},
		error : function(data) {
			console.log(data);
		}
	});

}

var editPlace = function(placeId){
    $.ajax({
        url : "getPlaceById",
        type : "get",
        data : {
            'placeId' : placeId
        },
        beforeSend : function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success : function(result) {
            console.log(result);
            $('#name_place').val(result.ten);
            $('#ma').val(result.ma);
            $('#place').val(result.dia_diem);
            $('#price_place').val(result.gia);
            $('#thong_tin').val(result.thong_tin);
        
        },
        error : function(data) {
            console.log(data);
        }
    });
}

var deleteHotel = function(hotelId) {
	$('#hidden_idHotel').val(hotelId);
	$('#showId').html(hotelId);

}
var deletePlace = function(placeId){
    $('#hidden_idPlace').val(placeId);
    $('#showId').html(placeId);
}
var deleteTour = function(tourId){
    $('#hidden_idTour').val(tourId);
    $('#showId').html(tourId);
}
var deleteRegistration = function(key_registration){
	$('#hidden_idRegistration').val(key_registration);
	$('#showId').html(key_registration);
}
var addHotelClear = function(){
	$('#Ten').val("");
	$('#DiaChi').val("");
	$("#search_HiddenLoai").val(0);
}
var addPlaceClear = function(){
    $('#name_place').val("");
    $('#place').val("");
    $("#price_place").val("");
    $("#thong_tin").val("");
    
}
var searchHotelClear = function(){
	$('#Ten').val("");
	$('#DiaChi').val("");
	$('#Ma').val("");
	$("#search_HiddenLoai").val(6);
}
var addTourClear = function(){
	$('#name_tour').val("");
	$('#total_ticket').val("");
	$('#date_start').val("");
	$('#date_end').val("");
	$('#introduce').val("");
	$('#program').val("");
	$('#expediency').val("");
	$('#prices').val("");
	$('#image').val("");	
}
//////////////////////////////////////////////
var editRestaurant = function(restaurantId) {

	$.ajax({
		url : "getRestaurantById",
		type : "get",
		data : {
			'restaurantId' : restaurantId
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(result) {
			console.log(result);
			$('#edit_MaRestaurant').val(result.ma);
			$('#edit_TenRestaurant').val(result.ten);
			$('#edit_DiaChiRestaurant').val(result.diaChi);
			$('#edit_LoaiRestaurant').val(result.loai);
			$('#hidden_edit_MaRestaurant').val(result.ma);
			
			if (result.loai == "0") {
				$("#edit_HiddenLoaiRestaurant").val(result.loai);
				$("#edit_ValueSelectorLoaiRestaurant").html("Bình dân");
			}else if (result.loai == "1") {
				$("#edit_HiddenLoaiRestaurant").val(result.loai);
				$("#edit_ValueSelectorLoaiRestaurant").html("Trung cấp");
			}else if (result.loai == "2") {
				$("#edit_HiddenLoaiRestaurant").val(result.loai);
				$("#edit_ValueSelectorLoaiRestaurant").html("Cao cấp");
			}else if (result.loai == "3") {
				$("#edit_HiddenLoaiRestaurant").val(result.loai);
				$("#edit_ValueSelectorLoaiRestaurant").html("Rất sang trọng");
			}

		},
		error : function(data) {
			console.log(data);
		}
	});

}

var deleteRestaurant = function(restaurantId) {
	$('#hidden_idRestaurant').val(restaurantId);
	$('#showId').html(restaurantId);

}

$(function() {
	$('#modal_editRestaurant').on('hidden.bs.modal', function(e)
    { 
        $(this).removeData();
        $("#error_messages_edit" ).empty();
        
    });
	
	$('#modal_editRestaurant').on('shown.bs.modal', function(e)
    { 
        $("#edit_MaRestaurant" ).val($('#hidden_edit_MaRestaurant').val());
        
        if ($("#edit_HiddenLoaiRestaurant").val() == "0") {
			$("#edit_ValueSelectorLoaiRestaurant").html("Thường");
		}else if ($("#edit_HiddenLoaiRestaurant").val() == "1") {
			$("#edit_ValueSelectorLoaiRestaurant").html("Trung cấp");
		}else if ($("#edit_HiddenLoaiRestaurant").val() == "2") {
			$("#edit_ValueSelectorLoaiRestaurant").html("Cao cấp");
		}else if ($("#edit_HiddenLoaiRestaurant").val() == "3") {
			$("#edit_ValueSelectorLoaiRestaurant").html("Rất sang trọng");
		}
        if($('#error_messages_edit').html() ==  "")
        	$('#error_messages_edit').parent().parent().hide();
    });	
});



$(function() {
	
	$('#add_date_start_tour').datepicker({
		format : "dd/mm/yyyy", // Notice the Extra space at the beginning
		autoclose : true
	});
	
	$('#add_date_end_tour').datepicker({
		format : "dd/mm/yyyy", // Notice the Extra space at the beginning
		autoclose : true
	});
	
	$('#add_date_start_tour').datepicker().on('changeDate', function(e) {
		var q = new Date();
		var m = q.getMonth()+1;
		var d = q.getDate();
		var y = q.getFullYear();
		
		var date = new Date(y,m,d);
		var n1 = $('#add_date_start_tour').val();
		//var n2 = $('#add_ngLam').val();
		var x = new Date(n1.split("/")[2], n1.split("/")[1], n1.split("/")[0]);
		if(x < date){
			$('#error_messages_addtour').html("Ngày nhập phải lớn hơn ngày hiện tại");
			$('#modal_notification1').modal('show');
			$('#add_date_start_tour').val("");
		}
	});
	
	$('#add_date_end_tour').datepicker().on('changeDate', function(e) {
		
		var n1 = $('#add_date_start_tour').val();
		if(n1==""){
			$('#add_date_end_tour').val("");
			$('#error_messages_addtour').html("Nhập ngày đi trước");
			$('#modal_notification1').modal('show');
		}
		else{
			var q = new Date();
			var m = q.getMonth()+1;
			var d = q.getDate();
			var y = q.getFullYear();
			
			var date = new Date(y,m,d);
			var n2 = $('#add_date_end_tour').val();
			var y = new Date(n2.split("/")[2], n2.split("/")[1], n2.split("/")[0]);
			if(y < date){
				$('#error_messages_addtour').html("Ngày nhập phải lớn ngày hiện tại");
				$('#modal_notification1').modal('show');
				$('#add_date_end_tour').val("");
			}
			else{
				var x = new Date(n1.split("/")[2], n1.split("/")[1], n1.split("/")[0]);
				if(y <= x){
					$('#error_messages_addtour').html("Ngày về phải lớn hơn ngày đi :((");
					$('#modal_notification1').modal('show');
					$('#add_date_end_tour').val("");
					return;
				}
			}
			
		}
		
	});
	
});


var updateChiPhi = function(dichuyen, anuong, o, phatsinh) {
	$('#chi_phi_di_chuyen_txt').val(dichuyen);
	$('#chi_phi_an_uong_txt').val(anuong);
	$('#chi_phi_o_txt').val(o);
	$('#chi_phi_phat_sinh_txt').val(phatsinh);
}


$(function() {

	$('#modal_loadhotel').on('shown.bs.modal', function(e)
    { 
		$('#modal_editTour').modal('hide');
    });	
	 	
	$('#modal_loadhotel').on('hidden.bs.modal', function(e)
    { 
		$('#modal_editTour').modal('show');
    });	

	
});




// E-admin - user//
