
	
$(document).ready(function () {
	$('#chart_chuthich').hide();
	$('#messages_warning').hide();
	$('#table').hide();
	$('#toggle-event').change(function() {
	      if($(this).prop('checked') == true){
	    	  $('#chart').show();
	    	  $('#table').hide();
	      }else{
	    	  $('#table').show();
	    	  $('#chart').hide();
	      }
    });
});

var xem = function() {
	$('#chart_chuthich').hide();
	$('#messages_warning').hide();
	$('#toggle-event').bootstrapToggle('on')
	var type_1 = $('#type_1').val();
	var type_2 = $('#type_2').val();
	var type_3 = $('#type_3').val();
	if(type_1 == 0 && type_2 == 0 && type_3 == 0){
		thongkeSLTour_Nam_Thang(type_1,type_2, type_3);
	}
	if(type_1 == 0 && type_2 == 0 && type_3 == 1){
		thongkeSLTour_Nam_Qui(type_1,type_2, type_3);
		$('#chart_chuthich').show();
	}
	if(type_1 == 0 && type_2 == 1){
		thongkeSLTour_NhieuNam(type_1,type_2, type_3);
	}

	if(type_1 == 2 && type_2 == 0 && type_3 == 0){
		thongkeDoanhThu_Nam_Thang(type_1,type_2, type_3);
	}
	if(type_1 == 2 && type_2 == 0 && type_3 == 1){
		thongkeDoanhThu_Nam_Qui(type_1,type_2, type_3);
		$('#chart_chuthich').show();
	}
	if(type_1 == 2 && type_2 == 1){
		thongkeDoanhThu_NhieuNam(type_1,type_2, type_3);
	}
	
	if(type_1 == 1 && type_2 == 0 && type_3 == 0){
		thongkeKhach_Nam_Thang(type_1,type_2, type_3);
	}
	if(type_1 == 1 && type_2 == 0 && type_3 == 1){
		thongkeKhach_Nam_Qui(type_1,type_2, type_3);
		$('#chart_chuthich').show();
	}
	if(type_1 == 1 && type_2 == 1){
		thongkeKhach_NhieuNam(type_1,type_2, type_3);
	}
	
//	//////////////////////////////////////
	if(type_1 == 3 && type_2 == 0 && type_3 == 0){
		thongkeLoiNhuan_Nam_Thang(type_1,type_2, type_3);
	}
	if(type_1 == 3 && type_2 == 0 && type_3 == 1){
		thongkeLoiNhuan_Nam_Qui(type_1,type_2, type_3);
	}
	if(type_1 == 3 && type_2 == 1){
		thongkeLoiNhuan_NhieuNam(type_1,type_2, type_3);
	}

}

var thongkeSLTour_Nam_Thang = function(type_1,type_2, type_3) {
	var year_detail = $('#year_detail').val();
	console.log(year_detail);
	if( year_detail == null || year_detail == ""){
		$('#messages_warning').show();
		$('#messages_warning').html("<strong>Mời chọn năm</strong>");
		console.log($('#messages_warning'));
	}else{
		$.ajax({
			url : "thongke_sltour_Nam_Thang",
			type : "get",
			data : {
				'type_1' : type_1,
				'type_2' : type_2,
				'type_3' : type_3,
				'year' : year_detail
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(result) {
				console.log(result);
				//setup chart
				var temp = new Chartist.Line('#chart', {
					  labels: result.thang,
					  series: [
					      result.sltour_thang
					  ]
					}, {
					  low: 0,
					  showArea: true
					});
				temp.options.height = 300;
				temp.options.chartPadding.left = 35;
				// setup table
				$('#table').find('thead').empty();
				$('#table').find('thead').append( "<tr><th style='text-align:center;'>Tháng</th><th style='text-align:center;'>Số lượng tour</th><th style='text-align:center;'>Chi tiết</th></tr>" );
				$('#table').find('tbody').empty();
				var tong = 0;
				for(var i = 0; i < 12; i++){
					var str = "<tr>";
					str += "<td style='text-align:center;'>" + result.thang[i] + "</td>";
					str += "<td style='text-align:center;'>" + result.sltour_thang[i] + "</td>";
					str += "<td style='text-align:center;'>" + "<button type='button' class='btn btn-info' onclick='XemChiTiet(" + (i + 1)+ ", " + year_detail +  ")'>Xem</button>" + "</td>";
					str += "</tr";
					$('#table').find('tbody').append(str);
					tong += result.sltour_thang[i];
				}
				var str = "<tr>";
				str += "<td style='text-align:center;'><strong>" + "Tổng" + "</strong></td>";
				str += "<td style='text-align:center;'><strong>" + tong + "</strong></td>";
				str += "</tr";
				$('#table').find('tbody').append(str);
			}});
	}
}

var thongkeSLTour_Nam_Qui = function(type_1,type_2, type_3) {
	var year_detail = $('#year_detail').val();
	console.log(year_detail);
	if( year_detail == null || year_detail == ""){
		$('#messages_warning').show();
		$('#messages_warning').html("<strong>Mời chọn năm</strong>");
		console.log($('#messages_warning'));
	}else{
		$.ajax({
			url : "thongke_sltour_Nam_Thang",
			type : "get",
			data : {
				'type_1' : type_1,
				'type_2' : type_2,
				'type_3' : type_3,
				'year' : year_detail
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(result) {
				console.log(result);
				//setup chart
				var data = {
					  series: result.sltour_qui
					};

					var sum = function(a, b) { return a + b };

					var temp = new Chartist.Pie('#chart', data, {
					  labelInterpolationFnc: function(value) {
					    return Math.round(value / data.series.reduce(sum) * 100) + '%';
					  }
					});
					temp.options.height = 300;
					temp.options.chartPadding.left = 35;
					// setup table
					$('#table').find('thead').empty();
					$('#table').find('thead').append( "<tr><th style='text-align:center;'>Quí</th><th style='text-align:center;'>Số lượng tour</th></tr>" );
					$('#table').find('tbody').empty();
					var tong = 0;
					for(var i = 0; i < 4; i++){
						var str = "<tr>";
						str += "<td style='text-align:center;'>" + result.qui[i] + "</td>";
						str += "<td style='text-align:center;'>" + result.sltour_qui[i] + "</td>";
						str += "</tr";
						$('#table').find('tbody').append(str);
						tong += result.sltour_qui[i];
					}
					var str = "<tr>";
					str += "<td style='text-align:center;'><strong>" + "Tổng" + "</strong></td>";
					str += "<td style='text-align:center;'><strong>" + tong + "</strong></td>";
					str += "</tr";
					$('#table').find('tbody').append(str);
			}});
	}
}

var thongkeSLTour_NhieuNam = function(type_1,type_2, type_3) {
	var start_year = $('#start_year').val();
	var end_year = $('#end_year').val();
	start_year.replace(" ", "");
	end_year.replace(" ", "");
	if( start_year == null || start_year == "" || end_year == null || end_year == ""  || end_year - start_year <= 0
			|| end_year - start_year >= 10){
		$('#messages_warning').show();
		$('#messages_warning').html("<strong>Mời chọn năm bắt đầu, kết thúc<br>Năm bắt đầu phải nhỏ hơn năm kết thúc" +
				"<br>Khoảng cách 2 mốc tối đa là 10</strong>");
		console.log($('#messages_warning'));
	}else{
		$.ajax({
			url : "thongke_sltour_Nam_Thang",
			type : "get",
			data : {
				'type_1' : type_1,
				'type_2' : type_2,
				'type_3' : type_3,
				'start_year' : start_year,
				'end_year' : end_year
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(result) {
				console.log(result);
				//setup chart
				var temp1 = [];
				var temp2 = [];
				for(var i = 0; i < end_year - start_year + 1; i++){
					if(result.nhieunam[i] != null){
						temp1.push(result.nhieunam[i]);
						temp2.push(result.sltour_nhieunam[i]);
					}
				}
				var temp = new Chartist.Bar('#chart', {
					  labels: temp1,
					  series: temp2
					}, {
					  distributeSeries: true
					});
					temp.options.height = 300;
					temp.options.chartPadding.left = 35;
					// setup table
					$('#table').find('thead').empty();
					$('#table').find('thead').append( "<tr><th style='text-align:center;'>Năm</th><th style='text-align:center;'>Số lượng tour</th></tr>" );
					$('#table').find('tbody').empty();
					var tong = 0;
					for(var i = 0; i < 10; i++){
						if(result.nhieunam[i] != null){
							var str = "<tr>";
							str += "<td style='text-align:center;'>" + result.nhieunam[i] + "</td>";
							str += "<td style='text-align:center;'>" + result.sltour_nhieunam[i] + "</td>";
							str += "</tr";
							$('#table').find('tbody').append(str);
						}
						
					}
			}});
	}
}

var thongkeDoanhThu_Nam_Thang = function(type_1,type_2, type_3) {
	var year_detail = $('#year_detail').val();
	console.log(year_detail);
	if( year_detail == null || year_detail == ""){
		$('#messages_warning').show();
		$('#messages_warning').html("<strong>Mời chọn năm</strong>");
		console.log($('#messages_warning'));
	}else{
		$.ajax({
			url : "thongke_sltour_Nam_Thang",
			type : "get",
			data : {
				'type_1' : type_1,
				'type_2' : type_2,
				'type_3' : type_3,
				'year' : year_detail
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(result) {
				console.log(result);
				//setup chart
				var temp = new Chartist.Line('#chart', {
					  labels: result.thang,
					  series: [
					      result.doanhThu_thang
					  ]
					}, {
					  low: 0,
					  showArea: true
					});
				temp.options.height = 300;
				temp.options.chartPadding.left = 35;
//				// setup table
				$('#table').find('thead').empty();
				$('#table').find('thead').append( "<tr><th style='text-align:center;'>Tháng</th><th style='text-align:center;'>Doanh thu</th></tr>" );
				$('#table').find('tbody').empty();
				var tong = 0;
				for(var i = 0; i < 12; i++){
					var str = "<tr>";
					str += "<td style='text-align:center;'>" + result.thang[i] + "</td>";
					str += "<td style='text-align:center;'>" + result.doanhThu_thang[i] + "</td>";
					str += "</tr";
					$('#table').find('tbody').append(str);
					tong += result.doanhThu_thang[i];
				}
				var str = "<tr>";
				str += "<td style='text-align:center;'><strong>" + "Tổng" + "</strong></td>";
				str += "<td style='text-align:center;'><strong>" + tong + "</strong></td>";
				str += "</tr";
				$('#table').find('tbody').append(str);
			}});
	}
}

var thongkeDoanhThu_Nam_Qui = function(type_1,type_2, type_3) {
	var year_detail = $('#year_detail').val();
	console.log(year_detail);
	if( year_detail == null || year_detail == ""){
		$('#messages_warning').show();
		$('#messages_warning').html("<strong>Mời chọn năm</strong>");
		console.log($('#messages_warning'));
	}else{
		$.ajax({
			url : "thongke_sltour_Nam_Thang",
			type : "get",
			data : {
				'type_1' : type_1,
				'type_2' : type_2,
				'type_3' : type_3,
				'year' : year_detail
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(result) {
				console.log(result);
				//setup chart
				var data = {
						  series: result.doanhThu_qui
						};

				var sum = function(a, b) { return a + b };

				var temp = new Chartist.Pie('#chart', data, {
				  labelInterpolationFnc: function(value) {
				    return Math.round(value / data.series.reduce(sum) * 100) + '%';
				  }
				});
				
				temp.options.height = 300;
				temp.options.chartPadding.left = 35;
//				// setup table
				$('#table').find('thead').empty();
				$('#table').find('thead').append( "<tr><th style='text-align:center;'>Tháng</th><th style='text-align:center;'>Doanh thu</th></tr>" );
				$('#table').find('tbody').empty();
				var tong = 0;
				for(var i = 0; i < 4; i++){
					var str = "<tr>";
					str += "<td style='text-align:center;'>" + result.qui[i] + "</td>";
					str += "<td style='text-align:center;'>" + result.doanhThu_qui[i] + "</td>";
					str += "</tr";
					$('#table').find('tbody').append(str);
					tong += result.doanhThu_qui[i];
				}
				var str = "<tr>";
				str += "<td style='text-align:center;'><strong>" + "Tổng" + "</strong></td>";
				str += "<td style='text-align:center;'><strong>" + tong + "</strong></td>";
				str += "</tr";
				$('#table').find('tbody').append(str);
			}});
	}
}

var thongkeDoanhThu_NhieuNam = function(type_1,type_2, type_3) {
	var start_year = $('#start_year').val();
	var end_year = $('#end_year').val();
	start_year.replace(" ", "");
	end_year.replace(" ", "");
	if( start_year == null || start_year == "" || end_year == null || end_year == ""  || end_year - start_year <= 0
			|| end_year - start_year >= 10){
		$('#messages_warning').show();
		$('#messages_warning').html("<strong>Mời chọn năm bắt đầu, kết thúc<br>Năm bắt đầu phải nhỏ hơn năm kết thúc" +
				"<br>Khoảng cách 2 mốc tối đa là 10</strong>");
		console.log($('#messages_warning'));
	}else{
		$.ajax({
			url : "thongke_sltour_Nam_Thang",
			type : "get",
			data : {
				'type_1' : type_1,
				'type_2' : type_2,
				'type_3' : type_3,
				'start_year' : start_year,
				'end_year' : end_year
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(result) {
				console.log(result);
				//setup chart
				var temp1 = [];
				var temp2 = [];
				for(var i = 0; i < 10; i++){
					if(result.nhieunam[i] != null){
						temp1.push(result.nhieunam[i]);
						temp2.push(result.doanhThu_nhieunam[i]);
					}
				}
				var temp = new Chartist.Bar('#chart', {
					  labels: temp1,
					  series: temp2
					}, {
					  distributeSeries: true
					});
					temp.options.height = 300;
					temp.options.chartPadding.left = 35;
					// setup table
					$('#table').find('thead').empty();
					$('#table').find('thead').append( "<tr><th style='text-align:center;'>Năm</th><th style='text-align:center;'>Doanh thu</th></tr>" );
					$('#table').find('tbody').empty();
					var tong = 0;
					for(var i = 0; i < 10; i++){
						if(result.nhieunam[i] != null){
							var str = "<tr>";
							str += "<td style='text-align:center;'>" + result.nhieunam[i] + "</td>";
							str += "<td style='text-align:center;'>" + result.doanhThu_nhieunam[i] + "</td>";
							str += "</tr";
							$('#table').find('tbody').append(str);
						}
						
					}
			}});
	}
}

var thongkeKhach_Nam_Thang = function(type_1,type_2, type_3) {
	var year_detail = $('#year_detail').val();
	console.log(year_detail);
	if( year_detail == null || year_detail == ""){
		$('#messages_warning').show();
		$('#messages_warning').html("<strong>Mời chọn năm</strong>");
		console.log($('#messages_warning'));
	}else{
		$.ajax({
			url : "thongke_sltour_Nam_Thang",
			type : "get",
			data : {
				'type_1' : type_1,
				'type_2' : type_2,
				'type_3' : type_3,
				'year' : year_detail
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(result) {
				console.log(result);
				//setup chart
				var temp = new Chartist.Line('#chart', {
					  labels: result.thang,
					  series: [
					      result.khach_thang
					  ]
					}, {
					  low: 0,
					  showArea: true
					});
				temp.options.height = 300;
				temp.options.chartPadding.left = 35;
//				// setup table
				$('#table').find('thead').empty();
				$('#table').find('thead').append( "<tr><th style='text-align:center;'>Tháng</th><th style='text-align:center;'>Số lượng khách</th></tr>" );
				$('#table').find('tbody').empty();
				var tong = 0;
				for(var i = 0; i < 12; i++){
					var str = "<tr>";
					str += "<td style='text-align:center;'>" + result.thang[i] + "</td>";
					str += "<td style='text-align:center;'>" + result.khach_thang[i] + "</td>";
					str += "</tr";
					$('#table').find('tbody').append(str);
					tong += result.khach_thang[i];
				}
				var str = "<tr>";
				str += "<td style='text-align:center;'><strong>" + "Tổng" + "</strong></td>";
				str += "<td style='text-align:center;'><strong>" + tong + "</strong></td>";
				str += "</tr";
				$('#table').find('tbody').append(str);
			}});
	}
}

var thongkeKhach_Nam_Qui = function(type_1,type_2, type_3) {
	var year_detail = $('#year_detail').val();
	console.log(year_detail);
	if( year_detail == null || year_detail == ""){
		$('#messages_warning').show();
		$('#messages_warning').html("<strong>Mời chọn năm</strong>");
		console.log($('#messages_warning'));
	}else{
		$.ajax({
			url : "thongke_sltour_Nam_Thang",
			type : "get",
			data : {
				'type_1' : type_1,
				'type_2' : type_2,
				'type_3' : type_3,
				'year' : year_detail
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(result) {
				console.log(result);
				//setup chart
				var data = {
						  series: result.khach_qui
						};

				var sum = function(a, b) { return a + b };

				var temp = new Chartist.Pie('#chart', data, {
				  labelInterpolationFnc: function(value) {
				    return Math.round(value / data.series.reduce(sum) * 100) + '%';
				  }
				});
				temp.options.height = 300;
				temp.options.chartPadding.left = 35;
//				// setup table
				$('#table').find('thead').empty();
				$('#table').find('thead').append( "<tr><th style='text-align:center;'>Tháng</th><th style='text-align:center;'>Số lượng khách</th></tr>" );
				$('#table').find('tbody').empty();
				var tong = 0;
				for(var i = 0; i < 4; i++){
					var str = "<tr>";
					str += "<td style='text-align:center;'>" + result.qui[i] + "</td>";
					str += "<td style='text-align:center;'>" + result.khach_qui[i] + "</td>";
					str += "</tr";
					$('#table').find('tbody').append(str);
					tong += result.khach_qui[i];
				}
				var str = "<tr>";
				str += "<td style='text-align:center;'><strong>" + "Tổng" + "</strong></td>";
				str += "<td style='text-align:center;'><strong>" + tong + "</strong></td>";
				str += "</tr";
				$('#table').find('tbody').append(str);
			}});
	}
}

var thongkeKhach_NhieuNam = function(type_1,type_2, type_3) {
	var start_year = $('#start_year').val();
	var end_year = $('#end_year').val();
	start_year.replace(" ", "");
	end_year.replace(" ", "");
	if( start_year == null || start_year == "" || end_year == null || end_year == ""  || end_year - start_year <= 0
			|| end_year - start_year >= 10){
		$('#messages_warning').show();
		$('#messages_warning').html("<strong>Mời chọn năm bắt đầu, kết thúc<br>Năm bắt đầu phải nhỏ hơn năm kết thúc" +
				"<br>Khoảng cách 2 mốc tối đa là 10</strong>");
		console.log($('#messages_warning'));
	}else{
		$.ajax({
			url : "thongke_sltour_Nam_Thang",
			type : "get",
			data : {
				'type_1' : type_1,
				'type_2' : type_2,
				'type_3' : type_3,
				'start_year' : start_year,
				'end_year' : end_year
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(result) {
				console.log(result);
				//setup chart
				var temp1 = [];
				var temp2 = [];
				for(var i = 0; i < 10; i++){
					if(result.nhieunam[i] != null){
						temp1.push(result.nhieunam[i]);
						temp2.push(result.khach_nhieunam[i]);
					}
				}
				var temp = new Chartist.Bar('#chart', {
					  labels: temp1,
					  series: temp2
					}, {
					  distributeSeries: true
					});
					temp.options.height = 300;
					temp.options.chartPadding.left = 35;
					// setup table
					$('#table').find('thead').empty();
					$('#table').find('thead').append( "<tr><th style='text-align:center;'>Năm</th><th style='text-align:center;'>Số lượng khách</th></tr>" );
					$('#table').find('tbody').empty();
					var tong = 0;
					for(var i = 0; i < 10; i++){
						if(result.nhieunam[i] != null){
							var str = "<tr>";
							str += "<td style='text-align:center;'>" + result.nhieunam[i] + "</td>";
							str += "<td style='text-align:center;'>" + result.khach_nhieunam[i] + "</td>";
							str += "</tr";
							$('#table').find('tbody').append(str);
						}
						
					}
			}});
	}
}

var XemChiTiet = function(month, year) {
	$.ajax({
	url : "tour_In_Thang",
	type : "get",
	data : {
		'month' : month,
		'year' : year
	},
	beforeSend : function(xhr) {
		xhr.setRequestHeader("Accept", "application/json");
		xhr.setRequestHeader("Content-Type", "application/json");
	},
	success : function(result) {
		console.log(result);
			$('#table_detailTour').find('tbody').empty();
			var tong = 0;
			for(var i = 0; i < result.length; i++){
				var str = "<tr>";
				str += "<td style='text-align:center;'>" + (i + 1) + "</td>";
				str += "<td style='text-align:center;'>" + result[i].ma + "</td>";
				str += "<td style='text-align:center;'>" + result[i].ten + "</td>";
				str += "<td style='text-align:center;'>" + result[i].tongtien.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"); + "</td>";
				str += "</tr";
				$('#table_detailTour').find('tbody').append(str);
				tong += parseInt(result[i].tongtien);
			}
			tong = tong.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
			var str = "<tr>";
			str += "<td style='text-align:center;'><strong>" + "Tổng" + "</strong></td>";
			str += "<td style='text-align:center;' colspan='3'><strong>" + tong + "</strong></td>";
			str += "</tr";
			$('#table_detailTour').find('tbody').append(str);
			$('#modal_detailTour').modal('show');
		
	}});
}

var thongkeLoiNhuan_Nam_Thang = function(type_1,type_2, type_3) {
	var year_detail = $('#year_detail').val();
	console.log(year_detail);
	if( year_detail == null || year_detail == ""){
		$('#messages_warning').show();
		$('#messages_warning').html("<strong>Mời chọn năm</strong>");
		console.log($('#messages_warning'));
	}else{
		$.ajax({
			url : "thongke_sltour_Nam_Thang",
			type : "get",
			data : {
				'type_1' : type_1,
				'type_2' : type_2,
				'type_3' : type_3,
				'year' : year_detail
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(result) {
				console.log(result);
				//setup chart
				var temp = new Chartist.Line('#chart', {
					  labels: result.thang,
					  series: [
				           	result.loiNhuan_thang
					  ]
					}, {
					  
					  showArea: true
					});
				temp.options.height = 300;
				temp.options.chartPadding.left = 35;
//				// setup table
				$('#table').find('thead').empty();
				$('#table').find('thead').append( "<tr><th style='text-align:center;'>Tháng</th><th style='text-align:center;'>Lợi nhuận</th></tr>" );
				$('#table').find('tbody').empty();
				var tong = 0;
				for(var i = 0; i < 12; i++){
					var str = "<tr>";
					str += "<td style='text-align:center;'>" + result.thang[i] + "</td>";
					str += "<td style='text-align:center;'>" + result.loiNhuan_thang[i] + "</td>";
					str += "</tr";
					$('#table').find('tbody').append(str);
					tong += result.loiNhuan_thang[i];
				}
				var str = "<tr>";
				str += "<td style='text-align:center;'><strong>" + "Tổng" + "</strong></td>";
				str += "<td style='text-align:center;'><strong>" + tong + "</strong></td>";
				str += "</tr";
				$('#table').find('tbody').append(str);
			}});
	}
}

var thongkeLoiNhuan_Nam_Qui = function(type_1,type_2, type_3) {
	var year_detail = $('#year_detail').val();
	console.log(year_detail);
	if( year_detail == null || year_detail == ""){
		$('#messages_warning').show();
		$('#messages_warning').html("<strong>Mời chọn năm</strong>");
		console.log($('#messages_warning'));
	}else{
		$.ajax({
			url : "thongke_sltour_Nam_Thang",
			type : "get",
			data : {
				'type_1' : type_1,
				'type_2' : type_2,
				'type_3' : type_3,
				'year' : year_detail
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(result) {
				console.log(result);
				//setup chart
				var temp = new Chartist.Line('#chart', {
					  labels: result.qui,
					  series: [
				           	result.loiNhuan_qui
					  ]
					}, {
					  
					  showArea: true
					});
				temp.options.height = 300;
				temp.options.chartPadding.left = 35;
//				// setup table
				$('#table').find('thead').empty();
				$('#table').find('thead').append( "<tr><th style='text-align:center;'>Tháng</th><th style='text-align:center;'>Lợi nhuận</th></tr>" );
				$('#table').find('tbody').empty();
				var tong = 0;
				for(var i = 0; i < 4; i++){
					var str = "<tr>";
					str += "<td style='text-align:center;'>" + result.qui[i] + "</td>";
					str += "<td style='text-align:center;'>" + result.loiNhuan_qui[i] + "</td>";
					str += "</tr";
					$('#table').find('tbody').append(str);
					tong += result.loiNhuan_qui[i];
				}
				var str = "<tr>";
				str += "<td style='text-align:center;'><strong>" + "Tổng" + "</strong></td>";
				str += "<td style='text-align:center;'><strong>" + tong + "</strong></td>";
				str += "</tr";
				$('#table').find('tbody').append(str);
			}});
	}
}

var thongkeLoiNhuan_NhieuNam = function(type_1,type_2, type_3) {
	var start_year = $('#start_year').val();
	var end_year = $('#end_year').val();
	start_year.replace(" ", "");
	end_year.replace(" ", "");
	if( start_year == null || start_year == "" || end_year == null || end_year == ""  || end_year - start_year <= 0
			|| end_year - start_year >= 10){
		$('#messages_warning').show();
		$('#messages_warning').html("<strong>Mời chọn năm bắt đầu, kết thúc<br>Năm bắt đầu phải nhỏ hơn năm kết thúc" +
				"<br>Khoảng cách 2 mốc tối đa là 10</strong>");
		console.log($('#messages_warning'));
	}else{
		$.ajax({
			url : "thongke_sltour_Nam_Thang",
			type : "get",
			data : {
				'type_1' : type_1,
				'type_2' : type_2,
				'type_3' : type_3,
				'start_year' : start_year,
				'end_year' : end_year
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(result) {
				console.log(result);
				//setup chart
				var temp1 = [];
				var temp2 = [];
				for(var i = 0; i < 10; i++){
					if(result.nhieunam[i] != null){
						temp1.push(result.nhieunam[i]);
						temp2.push(result.loiNhuan_nhieunam[i]);
					}
				}
				var temp = new Chartist.Bar('#chart', {
					  labels: temp1,
					  series: temp2
					}, {
					  distributeSeries: true
					});
					temp.options.height = 300;
					temp.options.chartPadding.left = 35;
					// setup table
					$('#table').find('thead').empty();
					$('#table').find('thead').append( "<tr><th style='text-align:center;'>Năm</th><th style='text-align:center;'>Lợi nhuận</th></tr>" );
					$('#table').find('tbody').empty();
					var tong = 0;
					for(var i = 0; i < 10; i++){
						if(result.nhieunam[i] != null){
							var str = "<tr>";
							str += "<td style='text-align:center;'>" + result.nhieunam[i] + "</td>";
							str += "<td style='text-align:center;'>" + result.loiNhuan_nhieunam[i] + "</td>";
							str += "</tr";
							$('#table').find('tbody').append(str);
						}
						
					}
			}});
	}
}






