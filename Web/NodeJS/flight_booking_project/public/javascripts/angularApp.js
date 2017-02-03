 'use strict';
 var app = angular.module('bt1', ['ui.router', 'monospaced.qrcode']);
 var update = 1;
 var sanbaydi = "";
 var sanbayden = "";
 var ngaybay1 = "";
 var ngaybay2 = "";
 var listFlighs_temp1 = [];
 var listFlighs_temp2 = [];
 var dia_danh_di = "";
 var dia_danh_den = "";
 var isKhuHoi = true;
 var stt = 0;
 var chooseFlight = [0, 0];
 var sohanhkhach = 0;
 var tongtien = 0;
 var luggagePriceTotal = 0;
 var madatcho;
 var lstPrices = [];
 var lst1Prices = [];
 var thong_tin_ve = [];
 var dat_ve = {};
 var adminMode = 0;
 var isBookLuggage = false;
 var luggagePrice1 = 0;
 var luggagePrice2 = 0;
 var luggageNumber1 = 0;
 var luggageNumber2 = 0;
 var passengers = [];
 var luggages = [];
 var count = 0;
 var step = 1;
 var preStep = 1;
 var r1 = '';
 var r2 = '';
 var activeStep;
 var listSeatBook1 = []; //danh sách ghế được chọn để đặt trước (chiều đi)
 var listSeatBook1_temp = [{}, {}, {}, {}, {}, {}, {}]; //danh sách ghế được chọn để đặt trước (chiều đi)
 var listSeatBook2 = []; //danh sách ghế được chọn để đặt trước (chiều về)
 var listSeatBook2_temp = [{}, {}, {}, {}, {}, {}, {}];
 var n7daysClick1 = 0; // số lần click lùi hay tiến tới 7 ngày chiều đi
 var n7daysClick2 = 0; // số lần click lùi hay tiến tới 7 ngày chiều về

 app.factory('auth', ['$http', '$window',
     function($http, $window) {
         var auth = {};
         auth.saveToken = function(token) {
             $window.localStorage['flight-booking-token'] = token;
         };

         auth.getToken = function() {
             return $window.localStorage['flight-booking-token'];
         }

         auth.isLoggedIn = function() {
             var token = auth.getToken();

             if (token) {
                 var payload = JSON.parse($window.atob(token.split('.')[1]));

                 return payload.exp > Date.now() / 1000;
             } else {
                 return false;
             }
         };

         auth.currentUser = function() {
             if (auth.isLoggedIn()) {
                 var token = auth.getToken();
                 var payload = JSON.parse($window.atob(token.split('.')[1]));

                 return payload.username;
             }
         };

         auth.register = function(user) {
             return $http.post('/register', user).success(function(data) {
                 auth.saveToken(data.token);
             });
         };

         auth.logIn = function(user) {
             return $http.post('/login', user).success(function(data) {
                 auth.saveToken(data.token);
             });
         };

         auth.logOut = function() {
             $window.localStorage.removeItem('flight-booking-token');
         };

         return auth;
     }
 ]);

 app.factory('socket', function() {
    var url = window.location.href;
    var arr = url.split("/");
    var result = arr[0] + "//" + arr[2];
    //var url = "http://localhost:" + result;
    var socket = io.connect(url);
     return socket;
 });

 app.directive('onFinishRender', function($timeout) {
     return {
         restrict: 'A',
         link: function(scope, element, attr) {
             if (scope.$last === true) {
                 $timeout(function() {
                     scope.$emit(attr.onFinishRender);
                 });
             }
         }
     }
 });

 app.config(['$stateProvider', '$urlRouterProvider',
     function($stateProvider, $urlRouterProvider) {
         $stateProvider
             .state('home', {
                 url: '/home',
                 templateUrl: '/templates/home.html',
                 controller: 'MainCtrl',
             })
             .state('listflighs', {
                 url: '/listflighs',
                 templateUrl: '/templates/list_flight.html',
                 controller: 'FlightCtrl',
             })
             .state('infoCustomer', {
                 url: '/infocustomer',
                 templateUrl: '/templates/passenger.html',
                 controller: 'PassengerCtrl',
             })
             .state('payment', {
                 url: '/payment',
                 templateUrl: '/templates/payment.html',
                 controller: 'paymentCtrl',
             })
             .state('confirmBooking', {
                 url: '/confirm',
                 templateUrl: '/templates/confirm.html',
                 controller: 'confirmCtrl',
             })
             .state('flightschedule', {
                 url: '/flightschedule',
                 templateUrl: '/templates/flightschedule.html',
                 controller: 'FlightScheduleCtrl',
             })
             .state('notify', {
                 url: '/notify',
                 templateUrl: '/templates/notify.html',
                 controller: 'NotifyCtrl',
             })
             .state('flight', {
                 url: '/flight',
                 templateUrl: '/templates/flight.html',
                 controller: 'FlightAdminCtrl'
             })
             .state('luggage', {
                 url: '/luggage',
                 templateUrl: '/templates/luggage.html',
                 controller: 'luggageCtrl',
             })
             .state('seats', {
                 url: '/seats',
                 templateUrl: '/templates/seat.html',
                 controller: 'SeatCtrl',
             })
             .state('review', {
                 url: '/review',
                 templateUrl: '/templates/review.html',
                 controller: 'reviewCtrl',
             })
             .state('ticketmanage', {
                 url: '/ticketmanage',
                 templateUrl: '/templates/ticketmanage.html',
                 controller: 'TicketCtrl',
             })
             .state('register', {
                 url: '/register',
                 templateUrl: '/templates/register.html',
                 controller: 'JAuthCtrl',
             })
             .state('login', {
                 url: '/login',
                 templateUrl: '/templates/login.html',
                 controller: 'JAuthCtrl',
             })
              .state('userlogin', {
                 url: '/userlogin',
                 templateUrl: '/templates/userlogin.html',
                 controller: 'UserLoginCtrl',
             })
             .state('lookup', {
                 url: '/lookup',
                 templateUrl: '/templates/lookup.html',
                 controller: 'LookupCtrl'
             })

         $urlRouterProvider.otherwise('home');
         // body...
     }
 ]);

 var isRefresh = false;

 function stepPanel(id, isSet) {
     if (step == 1) $('#stepPannel').hide();
     else {
         $('#stepPannel').show();
         $('#step' + id).css('background-color', '#dba511');
         $('#step' + id).css("color", "#ffffff");
         $('#step' + id).hover(function() {
             $(this).css("background-color", "#ffc014");
             $(this).css("color", "#ffffff");
         }, function() {
             $(this).css("background-color", "#dba511");
         });

         $('#step' + (id - 1)).css('background-color', '#006666');
         $('#step' + (id - 1)).css("color", "#ffffff");
         $('#step' + (id - 1)).hover(function() {
             $(this).css("background-color", "#018e8e");
             $(this).css("color", "#ffffff");
         }, function() {
             $(this).css("background-color", "#006666");
         });
     }
 }

 function restoreStepPanel(id) {
     if (step >= 2) {
         for (var i = 1; i <= step; i++) {
             if (i == id) {
                 $('#step' + i).css('background-color', '#07c1c1');
                 $('#step' + i).css("color", "#ffffff");
                 $('#step' + i).hover(function() {
                     $(this).css("background-color", "#018e8e");
                     $(this).css("color", "#ffffff");
                 }, function() {
                     $(this).css("background-color", "#07c1c1");
                 });
             }
             if (i == step && i != activeStep) {
                 $('#stepPannel').show();
                 $('#step' + i).css('background-color', '#dba511');
                 $('#step' + i).css("color", "#ffffff");
                 $('#step' + i).hover(function() {
                     $(this).css("background-color", "#ffc014");
                     $(this).css("color", "#ffffff");
                 }, function() {
                     $(this).css("background-color", "#dba511");
                 });
             }
             if (i != id && i != step) {
                 $('#step' + i).css('background-color', '#006666');
                 $('#step' + i).css("color", "#ffffff");
                 $('#step' + i).hover(function() {
                     $(this).css("background-color", "#018e8e");
                     $(this).css("color", "#ffffff");
                 }, function() {
                     $(this).css("background-color", "#006666");
                 });
             }
             document.getElementById("step" + i).disabled = false;
         }
         $('#stepPannel').show();
     }

 }

 function disableStep(s) {
     for (var i = s + 1; i <= step; i++) {
         document.getElementById("step" + i).disabled = true;
     }
 }

 function redirect() {
     var links = ['#/home', '#/listflighs', '#/infocustomer', '#/luggage', '#/seats', '#/review', '#/payment'];
     if (step > 7) step = 1;
     window.location.href = links[step - 1];
     if (step == 1) location.reload();
     //location.reload();
 }

 function loadData(scope, http) {
     if (!isRefresh) {

         isRefresh = true;
     }
     scope.listFlights = [];
     thong_tin_ve = [];
     chooseFlight = [];
     listFlighs_temp1 = [];
     listFlighs_temp2 = [];
     dat_ve = {};
     isKhuHoi = true;

     document.getElementById("onl_booking").style.display = "inline-block";
     http.get('/airports')
         .success(function(data) {
             for (var i = 0; i < data.length; i++) {
                 var item = {};
                 item.Ma = data[i].Ma;
                 item.TenDiaDanh = data[i].TenDiaDanh;
                 item.TenSanBay = data[i].TenSanBay;
                 item.SanBayDen = data[i].SanBayDen;
                 scope.listFlights.push(item);
             }
             scope.text = item;
             //console.log(data);
         })
         .error(function(data, status) {
             scope.text = "Error: " + data;
         });
 }

 function loadFlights(scope, http, auth) {
     scope.listFlights = [];
     http.get('/flights', {
             headers: { Authorization: 'Bearer ' + auth.getToken() }
         })
         .success(function(data) {
             for (var i = 0; i < data.length; i++) {
                 var item = {};
                 item.Ma = data[i].Ma;
                 item.Ngay = data[i].Ngay;
                 item.Gio = data[i].Gio;
                 item.ThoiGianBay = data[i].ThoiGianBay;
                 item.ThongTinVe = data[i].ThongTinVe;
                 scope.listFlights.push(item);
             }
         })
         .error(function(data, status) {
             //scope.text = "Error: " + data;
         });
 }

 function getSeatBooked(scope, http) {
     scope.placefrom1 = dia_danh_di;
     scope.placeto1 = dia_danh_den;
     scope.date1 = chooseFlight[0].Ngay;
     var date_chieudi = stringToDate(chooseFlight[0].Ngay, "dd-MM-yyyy", "-");
     http.get('/seats?maghe=' + chooseFlight[0].Ma + date_chieudi.getDate() + "-" + (date_chieudi.getMonth() + 1) + "-" + date_chieudi.getFullYear())
         .success(function(data) {
             if (data != null && data.length > 0) {
                 for (var i = 0; i < data.length; i++) {
                     document.getElementById(data[i].SoGhe + '1').classList.remove("btn-success");
                     document.getElementById(data[i].SoGhe + '1').className += " red_button";
                     document.getElementById(data[i].SoGhe + '1').disabled = true;
                 }
             }
             if (!isKhuHoi) $('#seatDialog').modal("hide");
         })
         .error(function(data, status) {
             $scope.text = "Error: " + data;
         });

     if (!isKhuHoi) {
         $('#flight2').hide();
     } else {
         scope.placefrom2 = dia_danh_den;
         scope.placeto2 = dia_danh_di;
         scope.date2 = chooseFlight[1].Ngay;

         var date_chieuve = stringToDate(chooseFlight[1].Ngay, "dd-MM-yyyy", "-");
         http.get('/seats?maghe=' + chooseFlight[1].Ma + date_chieuve.getDate() + "-" + (date_chieuve.getMonth() + 1) + "-" + date_chieuve.getFullYear())
             .success(function(data) {
                 if (data != null && data.length > 0) {
                     for (var i = 0; i < data.length; i++) {
                         document.getElementById(data[i].SoGhe + '2').classList.remove("btn-success");
                         document.getElementById(data[i].SoGhe + '2').className += " red_button";
                         document.getElementById(data[i].SoGhe + '2').disabled = true;
                     }
                 }
                 $('#seatDialog').modal("hide");
             })
             .error(function(data, status) {
                 $scope.text = "Error: " + data;
             });
     }
 }

 function loadFlightInfo(scope, http, auth) {
     scope.flightsInfo = [];
     http.get('/flights/info', {
             headers: { Authorization: 'Bearer ' + auth.getToken() }
         })
         .success(function(data) {
             for (var i = 0; i < data.length; i++) {
                 var item = {};
                 item.Ma = data[i].Ma;
                 item.NoiDi = data[i].NoiDi;
                 item.NoiDen = data[i].NoiDen;
                 item.QuaCanh = data[i].QuaCanh;
                 scope.flightsInfo.push(item);
             }
         })
         .error(function(data, status) {
             //scope.text = "Error: " + data;
         });
 }

 function loadTickets(scope, http, flight, date, auth) {
     scope.Tickets = [];
     http.get('/tickets?flight=' + flight + '&date=' + date, {
             headers: { Authorization: 'Bearer ' + auth.getToken() }
         })
         .success(function(data) {
             for (var i = 0; i < data.length; i++) {
                 var item = {};
                 item.MaThongTin = data[i].MaThongTin;
                 item.MaLoaiVe = data[i].MaLoaiVe;
                 item.LoaiVe = data[i].Hang;
                 item.MucGia = data[i].MucGia;
                 item.SoLuong = data[i].SoLuong;
                 item.GiaBan = data[i].GiaBan;
                 item.MaCB = flight;
                 item.Ngay = date;
                 scope.Tickets.push(item);
             }
         })
         .error(function(data, status) {
             var test = "test";
         });
 }

 function loadBooking(scope, http, auth) {
     scope.bookingList = [];
     http.get('/booking', {
             headers: { Authorization: 'Bearer ' + auth.getToken() }
         })
         .success(function(data) {
             for (var i = 0; i < data.length; i++) {
                 var item = {};
                 item.Ma = data[i].Ma;
                 item.ThoiGianDatCho = data[i].ThoiGianDatCho;
                 item.TongTien = data[i].TongTien;
                 item.TrangThai = data[i].TrangThai;
                 scope.bookingList.push(item);
             }
         });
 }

 function saveStateMainCtrl(window) {
     window.localStorage['sanbaydi'] = sanbaydi;
     window.localStorage['sanbayden'] = sanbayden;
     window.localStorage['dia_danh_di'] = dia_danh_di;
     window.localStorage['dia_danh_den'] = dia_danh_den;
     window.localStorage['listFlighs_temp1'] = JSON.stringify(listFlighs_temp1);
     window.localStorage['listFlighs_temp2'] = JSON.stringify(listFlighs_temp2);;
     window.localStorage['isKhuHoi'] = isKhuHoi;
     window.localStorage['ngaybay1'] = ngaybay1;
     window.localStorage['ngaybay2'] = ngaybay2;
     window.localStorage['sohanhkhach'] = sohanhkhach;
     window.localStorage['step'] = step;
 }

 function deleteAll(window) {
     window.localStorage.removeItem('sanbaydi');
     window.localStorage.removeItem('sanbayden');
     window.localStorage.removeItem('dia_danh_di');
     window.localStorage.removeItem('dia_danh_den');
     window.localStorage.removeItem('listFlighs_temp1');
     window.localStorage.removeItem('listFlighs_temp2');
     window.localStorage.removeItem('isKhuHoi');
     window.localStorage.removeItem('ngaybay1');
     window.localStorage.removeItem('ngaybay2');
     window.localStorage.removeItem('sohanhkhach');
     window.localStorage.removeItem('chooseFlight');
     window.localStorage.removeItem('step');
     window.localStorage.removeItem('r1');
     window.localStorage.removeItem('r2');
     window.localStorage.removeItem('passengers');
     window.localStorage.removeItem('isBookLuggage');
     window.localStorage.removeItem('luggagePriceTotal');
     window.localStorage.removeItem('luggageNumber1');
     window.localStorage.removeItem('luggageNumber2');
     window.localStorage.removeItem('luggages');
     window.localStorage.removeItem('luggagePrice1');
     window.localStorage.removeItem('luggagePrice2');
     window.localStorage.removeItem('tongtien');
     window.localStorage.removeItem('listSeatBook1');
     window.localStorage.removeItem('listSeatBook2');
     window.localStorage.removeItem('pass');
     window.localStorage.removeItem('madatcho');

     window.localStorage.removeItem('MaDatCho1');
     window.localStorage.removeItem('MaChuyenBay1');
     window.localStorage.removeItem('Ngay1');
     window.localStorage.removeItem('Hang1');
     window.localStorage.removeItem('GiaBan1');
     window.localStorage.removeItem('SoLuong1');
     window.localStorage.removeItem('DonGia1');
     window.localStorage.removeItem('Gio1');
     window.localStorage.removeItem('ThoiGianBay1');
     window.localStorage.removeItem('TongTien1');
     window.localStorage.removeItem('MaDatCho2');
     window.localStorage.removeItem('MaChuyenBay2');
     window.localStorage.removeItem('Ngay2');
     window.localStorage.removeItem('Hang2');
     window.localStorage.removeItem('GiaBan2');
     window.localStorage.removeItem('SoLuong2');
     window.localStorage.removeItem('DonGia2');
     window.localStorage.removeItem('Gio2');
     window.localStorage.removeItem('ThoiGianBay2');
     window.localStorage.removeItem('TongTien2');
     window.localStorage.removeItem('Ghe');
     window.localStorage.removeItem('HanhKhach');
     window.localStorage.removeItem('NoiDi');
     window.localStorage.removeItem('NoiDen');
     window.localStorage.removeItem('currentStep');
     window.localStorage.removeItem('redirect');
     window.localStorage.removeItem('isEnter');
 }
 // $(document).ready(function() {
 //     $('#camera_wrap').camera({
 //         loader: false,
 //         pagination: false,
 //         minHeight: '444',
 //         thumbnails: false,
 //         height: '48.375%',
 //         caption: true,
 //         navigation: true,
 //         fx: 'mosaic'
 //     });
 //     /*carousel*/
 //     var owl = $("#owl");
 //     owl.owlCarousel({
 //         items: 2, //10 items above 1000px browser width
 //         itemsDesktop: [995, 2], //5 items between 1000px and 901px
 //         itemsDesktopSmall: [767, 2], // betweem 900px and 601px
 //         itemsTablet: [700, 2], //2 items between 600 and 0
 //         itemsMobile: [479, 1], // itemsMobile disabled - inherit from itemsTablet option
 //         navigation: true,
 //         pagination: false
 //     });
 //     $().UItoTop({ easingType: 'easeOutQuart' });

 // });

 app.controller('MainCtrl', ['$scope', '$http', '$window', function($scope, $http, $window) {
     //document.getElementById("top").style.display = "block";
     //document.getElementById("top").setAttribute('class', null);
     // document.getElementById("top").removeAttribute('class', 'blur');
     adminMode = 0;
     if ($window.localStorage['step'] != null && $window.localStorage['step'] > 1) location.reload();
     deleteAll($window);
     loadData($scope, $http);
     $('#stepPannel').hide();
     /* console.log('click');
      $scope.click = function() {
         $scope.text= 'click';

      };*/


     var count1 = 0;
     var count2 = 0;
     $(document).ready(function() {

         $("#example1").datepicker({
             startDate: new Date()
         });
         $("#example2").datepicker({

             startDate: new Date()

         });

         jQuery('#example1').datepicker()
             .on('changeDate', function(e) {

                 count1++;
                 if (count1 == 1) {
                     var str = document.getElementById("example1").value;
                     var ngayDi = stringToDate(str, "dd-MM-yyyy", "-");
                     $("#example2").datepicker("remove");
                     $("#example2").datepicker({
                         startDate: new Date(ngayDi)
                     });
                     count1 = 0;
                 }
             });

         jQuery('#example2').datepicker()
             .on('changeDate', function(e) {
                 count2++;
                 if (count2 == 1) {

                     var str = document.getElementById("example2").value;
                     var ngayVe = stringToDate(str, "dd-MM-yyyy", "-");
                     $("#example1").datepicker("remove");
                     $("#example1").datepicker({
                         startDate: new Date(),
                         endDate: new Date(ngayVe)
                     });
                     count2 = 0;
                 }
             });
     });


     $scope.change_type = function(a) {
         if (a == 0) {
             document.getElementById("khuhoi").className += " btn-info";
             document.getElementById("motchieu").className -= " btn-info";
             document.getElementById("motchieu").className += " btn_in_form";
             document.getElementById("motchieu").className += " btn btn-default";

             document.getElementById("example2").style.display = "inline-block";
             document.getElementById("arrow").src = "images/arrow2.png";

             isKhuHoi = true;
         } else {
             document.getElementById("khuhoi").className -= " btn-info";
             document.getElementById("khuhoi").className += " btn_in_form";
             document.getElementById("khuhoi").className += " btn btn-default";
             document.getElementById("motchieu").className += " btn-info";
             document.getElementById("example2").required = false;
             document.getElementById("example2").style.display = "none";
             document.getElementById("arrow").src = "images/arrow1.png";

             isKhuHoi = false;
             //chooseFlight = [0];
         }

     }

     $scope.chooseSanBayDi = function(flight) {
         document.getElementById("menu1").value = flight.TenDiaDanh;
         $scope.listDesAirports = [];
         sanbaydi = flight.Ma;
         $window.localStorage['from'] = sanbaydi;
         dia_danh_di = flight.TenDiaDanh;
         $http.get('/airports/' + flight.Ma)
             .success(function(data) {
                 for (var i = 0; i < data.length; i++) {
                     var item = {};
                     item.NoiDen = data[i].NoiDen;
                     item.TenDiaDanh = data[i].TenDiaDanh;
                     $scope.listDesAirports.push(item);
                 }
                 $scope.text = item;
                 //console.log(data);
             })
             .error(function(data, status) {
                 $scope.text = "Error: " + data;
             });
     }

     $scope.display_name = function(flight, a) {
         document.getElementById("menu2").value = flight.TenDiaDanh;
         sanbayden = flight.NoiDen;
         dia_danh_den = flight.TenDiaDanh;
         $window.localStorage['to'] = sanbayden;
     }

     $scope.display_type = function(type) {
         document.getElementById("menu3").value = type;
     }

     $scope.find_ticket = function() {
         if ($('#code').val() == '' || $('#pass').val() == '') $scope.message = "Vui lòng nhập đầy đủ thông tin";
         else {
             var item = {};
             item.code = $('#code').val();
             item.pass = $('#pass').val();
             $window.localStorage['madatcho'] = $('#code').val();
             $window.localStorage['pass'] = $('#pass').val();
             $http.post('/ticketInfo/verify', item)
                 .success(function(data) {
                     if (data.length > 1) {
                         $window.localStorage['MaDatCho1'] = data[0].MaDatCho;
                         $window.localStorage['MaChuyenBay1'] = data[0].MaChuyenBay;
                         $window.localStorage['Ngay1'] = data[0].Ngay;
                         $window.localStorage['Hang1'] = data[0].Hang;
                         $window.localStorage['GiaBan1'] = data[0].GiaBan;
                         $window.localStorage['SoLuong1'] = data[0].SoLuong;
                         $window.localStorage['DonGia1'] = data[0].DonGia;
                         $window.localStorage['Gio1'] = data[0].Gio;
                         $window.localStorage['ThoiGianBay1'] = data[0].ThoiGianBay;
                         $window.localStorage['TongTien1'] = data[0].TongTien;

                         if (data.length == 6) {
                             $window.localStorage['MaDatCho2'] = data[1].MaDatCho;
                             $window.localStorage['MaChuyenBay2'] = data[1].MaChuyenBay;
                             $window.localStorage['Ngay2'] = data[1].Ngay;
                             $window.localStorage['Hang2'] = data[1].Hang;
                             $window.localStorage['GiaBan2'] = data[1].GiaBan;
                             $window.localStorage['SoLuong2'] = data[1].SoLuong;
                             $window.localStorage['DonGia2'] = data[1].DonGia;
                             $window.localStorage['Gio2'] = data[1].Gio;
                             $window.localStorage['ThoiGianBay2'] = data[1].ThoiGianBay;
                             $window.localStorage['TongTien2'] = data[1].TongTien;
                             $window.localStorage['Ghe'] = JSON.stringify(data[3]);
                             $window.localStorage['HanhKhach'] = JSON.stringify(data[2]);

                             $window.localStorage['NoiDi'] = data[4][0].TenDiaDanh;
                             $window.localStorage['NoiDen'] = data[5][0].TenDiaDanh;
                         } else {
                             $window.localStorage['Ghe'] = JSON.stringify(data[2]);
                             $window.localStorage['HanhKhach'] = JSON.stringify(data[1]);

                             $window.localStorage['NoiDi'] = data[3][0].TenDiaDanh;
                             $window.localStorage['NoiDen'] = data[4][0].TenDiaDanh;
                         }

                         $window.location.href = '#/lookup';
                         location.reload();
                     } else
                         $scope.message = data['message'];
                 });
         }
     }
     $scope.find_flights = function() {
         var empty = true;

         if (isKhuHoi) {
             if (document.getElementById("menu1").value != "" && document.getElementById("menu2").value != "" && document.getElementById("example1").value != "" && document.getElementById("example2").value != "" && document.getElementById("sohanhkhach").value != "") {
                 empty = false;
             }
         } else if (document.getElementById("menu1").value != '' && document.getElementById("menu2").value != '' && document.getElementById("example1").value != '' && document.getElementById("sohanhkhach").value != '') {
             empty = false
         }


         if (!empty) {
             console.log(document.getElementById("menu1").value);
             if (document.getElementById("menu1").value == "") {
                 var a = 1;
             }
             if (step < 2) step = 2;
             $window.localStorage['step'] = step;
             var str = document.getElementById("example1").value;
             sohanhkhach = document.getElementById("sohanhkhach").value;

             listFlighs_temp1 = [];
             listFlighs_temp2 = [];
             chooseFlight = [0, 0];
             var length1 = 0,
                 length2 = 0;
             //var result = str.split("-");
             ngaybay1 = str;

             $('#bookDialog').modal('show');
             $http.get('/flights/search/?from=' + sanbaydi + '&to=' + sanbayden + '&date=' + ngaybay1 + '&amount=' + sohanhkhach)
                 .success(function(data) {
                     document.getElementById("onl_booking").style.display = "none";
                     length1 = data.length;
                     if (length1 == 0) {
                         alert("Không còn vé cho chiều đi");
                         if (!isKhuHoi) {
                             //alert("Không còn vé cho chiều đi");
                             // window.location.href = "/";
                         }
                     }
                     for (var i = 0; i < data.length; i++) {
                         var item = {};
                         item.Ma = data[i].Ma;
                         item.Ngay = data[i].Ngay;
                         item.Gio = data[i].Gio;
                         item.ThoiGianBay = data[i].ThoiGianBay;
                         item.Hang = data[i].Hang;
                         item.MucGia = data[i].MucGia;
                         item.GiaBan = data[i].GiaBan;
                         item.SoLuong = data[i].SoLuong;
                         item.MaVe = data[i].MaVe;
                         listFlighs_temp1.push(item);
                     }
                     if (!isKhuHoi) {
                         saveStateMainCtrl($window);
                         $('#bookDialog').modal('hide');
                         $window.location.href = "#/listflighs";
                     }
                 })
                 .error(function(data, status) {
                     $scope.text = "Error: " + data;
                 });

             if (isKhuHoi) {
                 var str2 = document.getElementById("example2").value;
                 ngaybay2 = str2;

                 $http.get('/flights/search/?from=' + sanbayden + '&to=' + sanbaydi + '&date=' + ngaybay2 + '&amount=' + sohanhkhach)
                     .success(function(data) {
                         document.getElementById("onl_booking").style.display = "none";
                         console.log();
                         length2 = data.length;
                         if (length2 == 0) {
                             alert("Không còn vé cho chiều về");
                             if (length1 == 0) {
                                 // window.location.href = "/";
                             }
                         }

                         for (var i = 0; i < data.length; i++) {
                             var item = {};
                             console.log(data[i].Ngay);
                             item.Ma = data[i].Ma;
                             item.Ngay = data[i].Ngay;
                             item.Gio = data[i].Gio;
                             item.ThoiGianBay = data[i].ThoiGianBay;
                             item.Hang = data[i].Hang;
                             item.MucGia = data[i].MucGia;
                             item.GiaBan = data[i].GiaBan;
                             item.SoLuong = data[i].SoLuong;
                             item.MaVe = data[i].MaVe;
                             listFlighs_temp2.push(item);
                         }
                         saveStateMainCtrl($window);
                         $('#bookDialog').modal('hide');
                         $window.location.href = "#/listflighs";
                     })
                     .error(function(data, status) {
                         $scope.text = "Error: " + data;
                     });
             }
         }
     }
 }]);

 app.controller('LookupCtrl', ['$scope', '$http', '$window', function($scope, $http, $window) {
     document.getElementById("top").style.display = "none";
     $('#stepPanel').hide();
     $('#groupBtn').hide();
     if ($window.localStorage["TongTien1"] != null) {
         if ($window.localStorage["TongTien2"] == null) $('#returnFlight').hide();
         $scope.totalCost = addCommas($window.localStorage["TongTien1"]) + " VND";
         var passengers = JSON.parse($window.localStorage["HanhKhach"]);
         var strPassenger = "";
         for (var i = 0; i < passengers.length; i++) {
             if (i < passengers.length - 1) strPassenger += passengers[i].DanhXung + " " + passengers[i].Ho + " " + passengers[i].Ten + ", ";
             else strPassenger += passengers[i].DanhXung + " " + passengers[i].Ho + " " + passengers[i].Ten;
         }
         $scope.passengers = strPassenger;

         $scope.ticketLevel1 = $window.localStorage['Hang1'];
         $scope.flightCode1 = $window.localStorage['MaChuyenBay1'];
         $scope.from1 = $window.localStorage['NoiDi'];
         $scope.to1 = $window.localStorage['NoiDen'];
         $scope.startDate1 = $window.localStorage['Ngay1'];
         $scope.startTime1 = $window.localStorage['Gio1'];
         $scope.duration1 = $window.localStorage['ThoiGianBay1'];
         if ($window.localStorage['SoLuong1'] == 'null') $window.localStorage['SoLuong1'] = 0;
         $scope.luggageNumber1 = $window.localStorage['SoLuong1'];
         if ($window.localStorage['DonGia1'] == 'null') $window.localStorage['DonGia1'] = 0;
         $scope.luggagePrice1 = addCommas($window.localStorage['DonGia1']) + " VND";
         $scope.flightPrice1 = addCommas($window.localStorage['GiaBan1']) + " VND";

         if ($window.localStorage['Hang2'] != null) {
             $scope.ticketLevel2 = $window.localStorage['Hang2'];
             $scope.flightCode2 = $window.localStorage['MaChuyenBay2'];
             $scope.from2 = $window.localStorage['NoiDen'];
             $scope.to2 = $window.localStorage['NoiDi'];
             $scope.startDate2 = $window.localStorage['Ngay2'];
             $scope.startTime2 = $window.localStorage['Gio2'];
             $scope.duration2 = $window.localStorage['ThoiGianBay2'];
             if ($window.localStorage['SoLuong2'] == 'null') $window.localStorage['SoLuong2'] = 0;
             $scope.luggageNumber2 = $window.localStorage['SoLuong2'];
             if ($window.localStorage['DonGia2'] == 'null') $window.localStorage['DonGia2'] = 0;
             $scope.luggagePrice2 = addCommas($window.localStorage['DonGia2']) + " VND";
             $scope.flightPrice2 = addCommas($window.localStorage['GiaBan2']) + " VND";
         }

         var strSeat1 = '';
         var strSeat2 = '';
         if ($window.localStorage["Ghe"] != null) {
             var seats = JSON.parse($window.localStorage["Ghe"]);
             for (var i = 0; i < seats.length; i++) {
                 if (seats[i].MaGhe == $window.localStorage['MaChuyenBay1'] + converDateType($window.localStorage['Ngay1'])) {
                     strSeat1 += seats[i].SoGhe + "  ";
                     //else $scope.seat1 += seats[0].SoGhe;
                 } else {
                     strSeat2 += seats[i].SoGhe + "  ";
                 }
             }
         }
         $scope.seat1 = strSeat1;
         $scope.seat2 = strSeat2;
         $scope.url = $window.localStorage['madatcho'] + " " + $window.localStorage['pass'];
     } else {
         $window.localStorage['step'] = 1;
         redirect();
     }



 }]);

 function stringToDate(_date, _format, _delimiter) {
     var formatLowerCase = _format.toLowerCase();
     var formatItems = formatLowerCase.split(_delimiter);
     var dateItems = _date.split(_delimiter);
     var monthIndex = formatItems.indexOf("mm");
     var dayIndex = formatItems.indexOf("dd");
     var yearIndex = formatItems.indexOf("yyyy");
     var month = parseInt(dateItems[monthIndex]);
     month -= 1;
     var formatedDate = new Date(dateItems[yearIndex], month, dateItems[dayIndex]);
     return formatedDate;
 }

 function addCommas(n) {
     var rx = /(\d+)(\d{3})/;
     return String(n).replace(/^\d+/, function(w) {
         while (rx.test(w)) {
             w = w.replace(rx, '$1,$2');
         }
         return w;
     });
 }

 function converDateType(d) {
     var date1 = stringToDate(d, "dd-MM-yyyy", "-");
     var datetemp = new Date(date1);
     datetemp.setDate(datetemp.getDate());
     return datetemp.getDate() + "-" + (datetemp.getMonth() + 1).toString() + "-" + datetemp.getFullYear();
 }

 function GetData(scope, http) {
     if (isKhuHoi) {
         scope.data = [{}, {}];
     } else {
         scope.data = [{}];
     }
     var tam = [];
     var i = 1;

     tam.push(listFlighs_temp1);
     tam.push(dia_danh_di);
     tam.push(dia_danh_den);
     tam.push(ngaybay1);
     tam.push(i);

     var now = new Date();

     GetPrices7Days(scope, 0, http, ngaybay1, 1, sanbaydi, sanbayden, tam, "idTable1", "backwardbtn1", "towardbtn1", lstPrices);
     for (var i = 0; i < lstPrices.length; i++) {
         if (stringToDate(lstPrices[i].Ngay, "dd-MM-yyyy", "-") < now) {
             document.getElementById(lstPrices[i].Ngay + 't1').disabled = true;
         }
     }

     if (isKhuHoi) {
         var tam1 = [];
         i = 2;
         tam1.push(listFlighs_temp2);
         tam1.push(dia_danh_den);
         tam1.push(dia_danh_di);
         tam1.push(ngaybay2);
         tam1.push(i);

         GetPrices7Days(scope, 1, http, ngaybay2, 2, sanbayden, sanbaydi, tam1, "idTable2", "backwardbtn2", "towardbtn2", lst1Prices);


     }
 }


 Date.prototype.addDays = function(date, days) {
     var dat = new Date(this.valueOf());
     dat.setDate(date + days);
     return dat;
 }

 function loadReview(scope) {
     if (chooseFlight.length > 0) {
         scope.passengers = sohanhkhach;
         if (chooseFlight[0] == 0) {
             scope.ticketLevel1 = "";
             scope.flightCode1 = "";
             scope.from1 = "";
             scope.to1 = "";
             scope.startDate1 = "";
             scope.duration1 = "";
         } else {
             scope.ticketLevel1 = chooseFlight[0].Hang;
             scope.flightCode1 = chooseFlight[0].Ma;
             scope.from1 = dia_danh_di;
             scope.to1 = dia_danh_den;
             scope.startDate1 = chooseFlight[0].Ngay;
             scope.duration1 = chooseFlight[0].ThoiGianBay;
         }

         if (isKhuHoi) {
             if (chooseFlight[1] == 0) {
                 scope.ticketLevel2 = "";
                 scope.flightCode2 = "";
                 scope.from2 = "";
                 scope.to2 = "";
                 scope.startDate2 = "";
                 scope.duration2 = "";
             } else {
                 scope.ticketLevel2 = chooseFlight[1].Hang;
                 scope.flightCode2 = chooseFlight[1].Ma;
                 scope.from2 = dia_danh_den;
                 scope.to2 = dia_danh_di;
                 scope.startDate2 = chooseFlight[1].Ngay;
                 scope.duration2 = chooseFlight[1].ThoiGianBay;
             }

         }
     }
     tongtien = 0;
     for (var i = 0; i < chooseFlight.length; i++) {
         if (chooseFlight[i] != 0) tongtien += chooseFlight[i].GiaBan;
     }
     tongtien = tongtien * sohanhkhach;
     scope.totalCost = addCommas(tongtien.toString()) + " VND";
 }

 function restoreFlightCtrl(window, scope) {
     isKhuHoi = window.localStorage['isKhuHoi'];
     sanbaydi = window.localStorage['sanbaydi'];
     sanbayden = window.localStorage['sanbayden'];
     sohanhkhach = parseInt(window.localStorage['sohanhkhach']);
     dia_danh_di = window.localStorage['dia_danh_di'];
     dia_danh_den = window.localStorage['dia_danh_den'];
     ngaybay1 = window.localStorage['ngaybay1'];
     ngaybay2 = window.localStorage['ngaybay2'];
     if (window.localStorage['step'] != null)
         step = parseInt(window.localStorage['step']);
     else step = 1;

     if (isKhuHoi == "true") isKhuHoi = true;
     else isKhuHoi = false;

     if (window.localStorage['listFlighs_temp1'] != null)
         listFlighs_temp1 = JSON.parse(window.localStorage['listFlighs_temp1']);

     if (window.localStorage['listFlighs_temp2'] != null)
         listFlighs_temp2 = JSON.parse(window.localStorage['listFlighs_temp2']);

     if (window.localStorage['chooseFlight'] != null) {
         chooseFlight = JSON.parse(window.localStorage['chooseFlight']);
         loadReview(scope);
     }
 }

 function GetPrices7Days(scope, stt, http, ngaybay, type, srcAirport, desAirport, tam, idTable, idBackBtn, idToBtn, dsPrices) {
     var date1 = stringToDate(ngaybay, "dd-MM-yyyy", "-");
     var date1from = new Date(date1);
     date1from.setDate(date1from.getDate() - 3);
     var str_date1from = date1from.getDate() + "-" + (date1from.getMonth() + 1).toString() + "-" + date1from.getFullYear();

     var date1to = new Date(date1);
     date1to.setDate(date1to.getDate() + 3);
     var str_date1to = date1to.getDate() + "-" + (date1to.getMonth() + 1).toString() + "-" + date1to.getFullYear();

     dsPrices = [];

     for (var count = -3; count < 4; count++) {
         var datetemp = new Date(date1);
         datetemp.setDate(datetemp.getDate() + count);
         var str_datetemp = datetemp.getDate() + "-" + (datetemp.getMonth() + 1).toString() + "-" + datetemp.getFullYear();
         var obj_temp = {
             Ngay: str_datetemp,
             SmallestTicketsPrice: 'Hết vé',
             Type: type
         };

         dsPrices.push(obj_temp);
         if (type == 1) {
             listSeatBook1_temp[count + 3] = obj_temp;
         } else if (type == 2) {
             listSeatBook2_temp[count + 3] = obj_temp;
         }
     }

     http.get('/prices?from=' + srcAirport + '&to=' + desAirport + '&datefrom=' + str_date1from + '&dateto=' + str_date1to + '&amount=' + sohanhkhach)
         .success(function(data) {
             scope.text = "GetFlight7Days successful";

             var res = [];
             for (var i = 0; i < data.length; i++) {
                 var min = data[i];
                 for (var j = 0; j < data.length; j++) {
                     if (stringToDate(data[j].Ngay, "dd-MM-yyyy", "-") === stringToDate(min.Ngay, "dd-MM-yyyy", "-") && (data[j].SmallestTicketsPrice <= min.SmallestTicketsPrice)) {
                         min = data[j];
                     }
                 }

                 res.push(min);
             }

             for (var i = 0; i < res.length; i++) {
                 if (res[i].Ngay != null) {
                     // var minPrice = data[i].SmallestTicketsPrice;
                     for (var j = 0; j < dsPrices.length; j++) {
                         var s1 = stringToDate(dsPrices[j].Ngay, "dd-MM-yyyy", "-");
                         var s2 = stringToDate(res[i].Ngay, "dd-MM-yyyy", "-")
                         if (s1.getDate() === s2.getDate()) {
                             dsPrices[j].SmallestTicketsPrice = addCommas(res[i].SmallestTicketsPrice);
                             if (type == 1) {
                                 listSeatBook1_temp[j].SmallestTicketsPrice = addCommas(res[i].SmallestTicketsPrice);
                             } else if (type == 2) {
                                 listSeatBook2_temp[j].SmallestTicketsPrice = addCommas(res[i].SmallestTicketsPrice);
                             }
                             break;
                         }
                     }
                 }
             }
             // lstPrices.push(data);
             tam.push(dsPrices);
             tam.push(idTable);
             tam.push(idBackBtn);
             tam.push(idToBtn);
             scope.data[stt] = tam;
         })
         .error(function(data, status) {
             scope.text = "Error: " + data;
         });
 }

 function backward7days(scope, http, stt, ngaybay, type, idTable, idBackBtn, idToBtn, n7daysClick, listFlighs_temp, srcAirport, desAirport, dsPrices) {
     scope.data[stt] = {};

     var date1 = stringToDate(ngaybay, "dd-MM-yyyy", "-");
     var date1Before = new Date(date1);
     date1Before.setDate(date1.getDate() - 7 * n7daysClick);
     var date1Before_str = date1Before.getDate() + "-" + (date1Before.getMonth() + 1) + "-" + date1Before.getFullYear();
     var array_temp1 = [];


     var i = type;

     array_temp1.push(listFlighs_temp);
     array_temp1.push(dia_danh_di);
     array_temp1.push(dia_danh_den);
     array_temp1.push(date1Before_str);
     array_temp1.push(i);

     if (type == 2) {
         var _object = array_temp1[1];
         array_temp1[1] = array_temp1[2];
         array_temp1[2] = _object;
     }

     GetPrices7Days(scope, stt, http, date1Before_str, type, srcAirport, desAirport, array_temp1, idTable, idBackBtn, idToBtn, dsPrices);
 }

 var NgayDiChoose;
 var NgayVeChoose;

 function disable7days() {
     var ngaydi = stringToDate(ngaybay1, "dd-MM-yyyy", "-");
     if (isKhuHoi) {
         var ngayve = stringToDate(ngaybay2, "dd-MM-yyyy", "-");
     }

     if (chooseFlight[1] != 0) {
         if (chooseFlight[1].Ngay != null) {
             ngayve = stringToDate(chooseFlight[1].Ngay, "dd-MM-yyyy", "-")
         }
     } else if (NgayVeChoose != null) {
         ngayve = stringToDate(NgayVeChoose, "dd-MM-yyyy", "-")
     }

     if (chooseFlight[0] != 0) {
         if (chooseFlight[0].Ngay != null) {
             ngaydi = stringToDate(chooseFlight[0].Ngay, "dd-MM-yyyy", "-");
         }
     } else if (NgayDiChoose != null) {
         ngaydi = stringToDate(NgayDiChoose, "dd-MM-yyyy", "-");
     }


     var now = new Date();
     now.setDate(now.getDate() - 1);
     if (stringToDate(listSeatBook1_temp[0].Ngay, "dd-MM-yyyy", "-") <= now) {
         document.getElementById("backwardbtn1").setAttribute("disabled", "disabled");
     } else {
         document.getElementById("backwardbtn1").removeAttribute("disabled");;
     }

     if (ngayve != null) {
         if (stringToDate(listSeatBook1_temp[6].Ngay, "dd-MM-yyyy", "-") > ngayve) {
             document.getElementById("towardbtn1").setAttribute("disabled", "disabled");
         } else {
             document.getElementById("towardbtn1").removeAttribute("disabled");;
         }

     }

     if (listSeatBook2_temp[0].Ngay != null) {

         if (stringToDate(listSeatBook2_temp[0].Ngay, "dd-MM-yyyy", "-") <= ngaydi) {
             document.getElementById("backwardbtn2").setAttribute("disabled", "disabled");
         } else {
             document.getElementById("backwardbtn2").removeAttribute("disabled");;
         }

         if (ngaydi != null) {
             if (stringToDate(listSeatBook2_temp[6].Ngay, "dd-MM-yyyy", "-") <= ngaydi) {
                 document.getElementById("towardbtn2").setAttribute("disabled", "disabled");
             } else {
                 document.getElementById("towardbtn2").removeAttribute("disabled");;
             }

         }
     }

     for (var i = 0; i < listSeatBook1_temp.length; i++) {
         var hh = stringToDate(listSeatBook1_temp[i].Ngay, "dd-MM-yyyy", "-")
         var strId = listSeatBook1_temp[i].Ngay + 't1';
         if (hh < now || hh > ngayve) {

             if (document.getElementById(strId) != null) {
                 document.getElementById(strId).setAttribute("disabled", "disabled");
             }
         } else {
             document.getElementById(strId).removeAttribute("disabled");;
         }
     }

     for (var i = 0; i < listSeatBook2_temp.length; i++) {
         if (listSeatBook2_temp[i].Ngay != null) {
             var hh = stringToDate(listSeatBook2_temp[i].Ngay, "dd-MM-yyyy", "-")
             var strId = listSeatBook2_temp[i].Ngay + 't2';
             if (hh < ngaydi) {
                 if (document.getElementById(strId) != null) {
                     document.getElementById(strId).setAttribute("disabled", "disabled");
                 }
             } else {
                 document.getElementById(strId).removeAttribute("disabled");;
             }
         }
     }
 }
 var oldchoose = 0;
 app.controller('FlightCtrl', ['$scope', '$http', '$window', function($scope, $http, $window) {
     adminMode = 0;
     document.getElementById("top").style.display = "none";
     restoreFlightCtrl($window, $scope);
     if (step < 2 || step > 7) {
         redirect();
     } else {
         $('#groupBtn').hide();
         //chooseFlight = [0, 0];
         activeStep = 2;
         if ($window.localStorage['currentStep'] != null)
             $window.localStorage['step'] = $window.localStorage['currentStep'];
         restoreStepPanel(2);
         $('#link2').css('color', '#ffffff');
         $('#bookDialog').modal('show');
         GetData($scope, $http);
         $scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
             //you also get the actual event object
             //do stuff, execute functions -- whatever...
             disable7days();
             if (chooseFlight[0] == 0) {
                 var date1 = stringToDate($window.localStorage['ngaybay1'], "dd-MM-yyyy", "-");
                 var date1Before = new Date(date1);
                 date1Before.setDate(date1.getDate() - 7 * n7daysClick1);
                 var date1Before_str = date1Before.getDate() + "-" + (date1Before.getMonth() + 1) + "-" + date1Before.getFullYear();
                 if (chooseFlight[1] != 0) {
                     var date2 = stringToDate(chooseFlight[1].Ngay, "dd-MM-yyyy", "-");
                     if (date1Before > date2)
                         $("#" + converDateType(chooseFlight[1].Ngay) + "t1").trigger("click");
                     else $("#" + date1Before_str + "t1").trigger("click");
                 } else $("#" + date1Before_str + "t1").trigger("click");
             } else if (chooseFlight[0] != 0 && chooseFlight.length > 0) {
                 $window.localStorage['isEnter'] = '1';
                 $("#" + converDateType(chooseFlight[0].Ngay) + "t1").trigger("click");
             } else {
                 $('#' + converDateType(ngaybay1) + "t1").css('background-color', '#d3d3d3');
                 $('#' + converDateType(ngaybay1) + "t1").trigger("click");
             }

             if (chooseFlight[1] == 0) {
                 var date1 = stringToDate($window.localStorage['ngaybay2'], "dd-MM-yyyy", "-");
                 var date1Before = new Date(date1);
                 date1Before.setDate(date1.getDate() - 7 * n7daysClick2);
                 var date1Before_str = date1Before.getDate() + "-" + (date1Before.getMonth() + 1) + "-" + date1Before.getFullYear();
                 if (chooseFlight[0] != 0) {
                     var date2 = stringToDate(chooseFlight[0].Ngay, "dd-MM-yyyy", "-");
                     if (date1Before < date2)
                         $("#" + converDateType(chooseFlight[0].Ngay) + "t2").trigger("click");
                     else $("#" + date1Before_str + "t2").trigger("click");
                 } else $("#" + date1Before_str + "t2").trigger("click");
             } else if (chooseFlight[1] != 0 && chooseFlight.length > 1) {
                 $window.localStorage['isEnter'] = '1';
                 $("#" + converDateType(chooseFlight[1].Ngay) + "t2").trigger("click");
             } else {
                 $('#' + converDateType(ngaybay2) + "t2").css('background-color', '#d3d3d3');
                 $('#' + converDateType(ngaybay2) + "t2").trigger("click");
             }
             $('#bookDialog').modal('hide');
         });

         $scope.$on('ngRepeatFinished1', function(ngRepeatFinishedEvent) {
             if ($window.localStorage['r1'] != null)
                 $('#' + $window.localStorage['r1']).prop("checked", true);

             if ($window.localStorage['r2'] != null)
                 $('#' + $window.localStorage['r2']).prop("checked", true);

             //loadReview($scope);

             if ((!isKhuHoi && chooseFlight[0] != 0) || (isKhuHoi && chooseFlight[0] != 0 && chooseFlight[1] != 0)) {

                 document.getElementById("btn_dat_ve").disabled = false;

             } else {
                 document.getElementById("btn_dat_ve").disabled = true;
             }

         });

         var isChoose = false;
         var chooseItem = [];

         if (!isKhuHoi) $("#returnFlight").hide();
         var length1 = 0,
             length2 = 0;
         var preBtnId1, preBtnId2;

         $scope.backward7daysClick = function(type) {
             $('#bookDialog').modal('show');
             if (type == 1) {
                 n7daysClick1++;
                 backward7days($scope, $http, 0, ngaybay1, 1, "idTable1", "backwardbtn1", "towardbtn1", n7daysClick1, listFlighs_temp1, sanbaydi, sanbayden, lstPrices)
                     // $scope.data[0][5] = array_temp1[0];
             } else if (type == 2) {
                 n7daysClick2++;
                 backward7days($scope, $http, 1, ngaybay2, 2, "idTable2", "backwardbtn2", "towardbtn2", n7daysClick2, listFlighs_temp2, sanbayden, sanbaydi, lst1Prices)
             }
         }

         $scope.toward7daysClick = function(type) {
             $('#bookDialog').modal('show');
             if (type == 1) {
                 n7daysClick1--;
                 backward7days($scope, $http, 0, ngaybay1, 1, "idTable1", "backwardbtn1", "towardbtn1", n7daysClick1, listFlighs_temp1, sanbaydi, sanbayden, lstPrices)
                     // $scope.data[0][5] = array_temp1[0];
             } else if (type == 2) {
                 n7daysClick2--;
                 backward7days($scope, $http, 1, ngaybay2, 2, "idTable2", "backwardbtn2", "towardbtn2", n7daysClick2, listFlighs_temp2, sanbayden, sanbaydi, lst1Prices)
             }
         }

         $scope.chooseDate = function(infor) {

             if (infor.Type == 1) {
                 if (chooseFlight[0] != 0 && infor.Ngay != converDateType(chooseFlight[0].Ngay)) {
                     //chooseFlight[0] = 0;
                     //$window.localStorage.removeItem('r1');
                     //$window.localStorage['chooseFlight'] = JSON.stringify(chooseFlight);
                 }
                 if ($window.localStorage['isEnter'] != '1') {
                     chooseFlight[0] = 0;
                     $window.localStorage['chooseFlight'] = JSON.stringify(chooseFlight);
                     $('#' + $window.localStorage['r1']).prop("checked", false);
                     $window.localStorage.removeItem('r1');
                     if ($window.localStorage['currentStep'] == null) $window.localStorage['currentStep'] = step;
                     $window.localStorage['step'] = 2;
                     $window.localStorage.removeItem('listSeatBook1');
                     disableStep(2);
                     loadReview($scope);

                 }
                 $window.localStorage['isEnter'] = '0';
                 NgayDiChoose = infor.Ngay;

                 $('#' + converDateType(ngaybay1) + "t1").css('background-color', '');
                 $('#' + infor.Ngay + "t1").css('background-color', '#d3d3d3');
                 if (preBtnId1 != '#' + infor.Ngay + "t1") $(preBtnId1).css('background-color', '');
                 preBtnId1 = '#' + infor.Ngay + "t1";
                 $http.get('/flights/search/?from=' + sanbaydi + '&to=' + sanbayden + '&date=' + infor.Ngay + '&amount=' + sohanhkhach)
                     .success(function(data) {
                         $('#idTable1').hide();
                         listFlighs_temp1 = [];
                         document.getElementById("onl_booking").style.display = "none";
                         length1 = data.length;

                         listFlighs_temp1 = [];
                         for (var i = 0; i < data.length; i++) {
                             var item = {};
                             item.Ma = data[i].Ma;
                             item.Ngay = data[i].Ngay;
                             item.Gio = data[i].Gio;
                             item.ThoiGianBay = data[i].ThoiGianBay;
                             item.Hang = data[i].Hang;
                             item.MucGia = data[i].MucGia;
                             item.GiaBan = data[i].GiaBan;
                             item.SoLuong = data[i].SoLuong;
                             item.MaVe = data[i].MaVe;
                             listFlighs_temp1.push(item);
                         }
                         $scope.data[0][0] = [];
                         $scope.data[0][0] = listFlighs_temp1.slice();
                         $('#idTable1').show();


                     })
                     .error(function(data, status) {
                         $scope.data[0][0] = [];
                         $('#idTable1').hide();
                         $scope.text = "Error: " + data;
                     });

             } else {
                 if (chooseFlight[1] != 0 && infor.Ngay != converDateType(chooseFlight[1].Ngay)) {
                     //chooseFlight[1] = 0;
                     //$window.localStorage.removeItem('r2');
                     //$window.localStorage['chooseFlight'] = JSON.stringify(chooseFlight);
                 }
                 if ($window.localStorage['isEnter'] != '1') {
                     chooseFlight[1] = 0;
                     $window.localStorage['chooseFlight'] = JSON.stringify(chooseFlight);
                     $('#' + $window.localStorage['r2']).prop("checked", false);
                     $window.localStorage.removeItem('r2');
                     loadReview($scope);
                     if ($window.localStorage['currentStep'] == null) $window.localStorage['currentStep'] = step;
                     $window.localStorage['step'] = 2;
                     $window.localStorage.removeItem('listSeatBook2');
                     disableStep(2);

                 }
                 $window.localStorage['isEnter'] = '0';
                 NgayVeChoose = infor.Ngay;
                 $('#' + converDateType(ngaybay2) + "t2").css('background-color', '');
                 $('#' + infor.Ngay + "t2").css('background-color', '#D3D3D3');
                 if (preBtnId2 != '#' + infor.Ngay + "t2") $(preBtnId2).css('background-color', '');
                 preBtnId2 = '#' + infor.Ngay + "t2";
                 listFlighs_temp2 = [];
                 $http.get('/flights/search/?from=' + sanbayden + '&to=' + sanbaydi + '&date=' + infor.Ngay + '&amount=' + sohanhkhach)
                     .success(function(data) {
                         $('#idTable2').hide();
                         document.getElementById("onl_booking").style.display = "none";
                         length2 = data.length;

                         listFlighs_temp2 = [];
                         for (var i = 0; i < data.length; i++) {
                             var item = {};
                             item.Ma = data[i].Ma;
                             item.Ngay = data[i].Ngay;
                             item.Gio = data[i].Gio;
                             item.ThoiGianBay = data[i].ThoiGianBay;
                             item.Hang = data[i].Hang;
                             item.MucGia = data[i].MucGia;
                             item.GiaBan = data[i].GiaBan;
                             item.SoLuong = data[i].SoLuong;
                             item.MaVe = data[i].MaVe;
                             listFlighs_temp2.push(item);
                         }

                         $scope.data[1][0] = [];
                         $scope.data[1][0] = listFlighs_temp2.slice();
                         $('#idTable2').show();


                     })
                     .error(function(data, status) {
                         $scope.data[1][0] = [];
                         $('#idTable2').hide();
                         $scope.text = "Error: " + data;
                     });
             }
             disable7days();
         }

         $scope.choose = function(flight, iForm) {
             document.getElementById("btn_dat_ve").disabled = true;
             if (iForm == 1) {
                 chooseFlight[0] = flight;
                 r1 = "r" + flight.Ma + flight.Ngay + flight.Hang + flight.MucGia;
                 $window.localStorage['r1'] = r1;
             }
             if (iForm == 2) {
                 chooseFlight[1] = flight;
                 r2 = "r" + flight.Ma + flight.Ngay + flight.Hang + flight.MucGia;
                 $window.localStorage['r2'] = r2;
             }


             // if (chooseItem.length > 0) {
             //     for (var i = 0; i < chooseItem.length; i++) {
             //         if (chooseItem[i] == iForm) {
             //             chooseFlight.splice(chooseFlight.indexOf(flight), 1);
             //             chooseItem.splice(chooseItem.indexOf(iForm), 1);
             //         }
             //     }
             // }
             // oldchoose = iForm;
             // chooseItem.push(iForm);
             // chooseFlight.push(flight);
             // isChoose = false;
             $window.localStorage['chooseFlight'] = JSON.stringify(chooseFlight);


             if ((!isKhuHoi && chooseFlight[0] != 0) || (isKhuHoi && chooseFlight[0] != 0 && chooseFlight[1] != 0)) {
                 loadReview($scope);
                 document.getElementById("btn_dat_ve").disabled = false;
                 if ($window.localStorage['currentStep'] != null)
                     $window.localStorage['step'] = $window.localStorage['currentStep'];
                 restoreStepPanel(2);
                 $window.localStorage.removeItem('currentStep');
             }
         }
         $scope.changeToPassenger = function() {
             if (step < 3)
                 step = 3;
             //$window.localStorage['chooseFlight'] = JSON.stringify(chooseFlight);
             $window.localStorage['step'] = step;
             $window.localStorage['tongtien'] = tongtien;
             $window.location.href = "#/infocustomer";
             //$window.localStorage['r1'] = r1;
             //$window.localStorage['r2'] = r2;
         }
     }

 }]);

 function lpad(value, padding) {
     var zeroes = new Array(padding + 1).join("0");
     return (zeroes + value).slice(-padding);
 }

 function creationInfoForm() {
     var i = 1;
     while (i < sohanhkhach) {
         // $("#infomation").clone().appendTo(".tongquat");
         var r = "'" + 'danhxung' + i.toString() + "'";
         var str = "<div id='infomation" + i.toString() + "' style='margin-top: 20px;'>" + "<div style='position: relative; float: left; margin-left: 100px;'>" + "<p style='font-weight: bold;'>Danh xưng</p>" + "<div class='dropdown pull-left' style='position: relative;'>" + "<input type='text' class='btn btn-default dropdown-toggle' type='button' id='danhxung" + i.toString() + "' data-toggle='dropdown' readonly required style='width: 75px;'>" + "<span class='caret'></span>" + "<ul class='dropdown-menu' role='menu' aria-labelledby='danhxung" + i.toString() + "'>" + "<li role='presentation'><a role='menuitem' tabindex='-1' onclick= " + "\"chooseDanhXung(" + r + ", 'MR')\"" + ">MR</a></li>" + "<li role='presentation'><a role='menuitem' tabindex='-1' onclick=" + "\"chooseDanhXung(" + r + " , 'MS')\"" + ">MS</a></li>" + "</ul>" + "</div>" + "</div>" + "<div style='display: inline-block; margin-left: 10px;'>" + "<p style='font-weight: bold;'>Đệm và Tên</p>" + "<input type='text' id='demvaten" + i.toString() + "' data-toggle='dropdown' required style='height: 33px'>" + "</div>" + "<div style='display: inline-block; margin-left: 10px;'>" + "<p style='font-weight: bold;'>Họ</p>" + "<input type='text' id='ho" + i.toString() + "' data-toggle='dropdown' required style='height: 33px'>" + "</div>" + "</div>";
         var t = "#infomation" + (i - 1).toString();
         $(str).insertAfter(t);
         ++i;
     }
 }


 function restorePassengerCtrl(window, $scope) {
     sohanhkhach = parseInt(window.localStorage['sohanhkhach']);
     if (window.localStorage['step'] != null)
         step = parseInt(window.localStorage['step']);
     else step = 1;

     if (window.localStorage['passengers'] != null)
         passengers = JSON.parse(window.localStorage['passengers']);

     if (passengers != null && passengers.length > 0) {
         for (var i = 0; i < sohanhkhach; i++) {
             $("#danhxung" + i.toString()).val(passengers[i].DanhXung);
             $("#ho" + i.toString()).val(passengers[i].Ho);
             $("#demvaten" + i.toString()).val(passengers[i].Ten);
         }
     }
 }
 var iscall = false;

 function nextStepPassengerCtrl($window) {
     var flag = 0;
     for (var i = 0; i < sohanhkhach; i++) {
         if ($("#danhxung" + i.toString()).val() == "" || $("#ho" + i.toString()).val() == "" || $("#demvaten" + i.toString()).val() == "") {
             alert("Vui lòng điền đầy đủ thông tin hành khách");
             flag = 1;
             break;
         }
     }
     if (flag == 0) {

         passengers = [];
         for (var i = 0; i < sohanhkhach; i++) {
             var item = {};
             item.MaDatCho = madatcho;
             item.DanhXung = document.getElementById("danhxung" + i.toString()).value;
             item.Ho = document.getElementById("ho" + i.toString()).value;
             item.Ten = document.getElementById("demvaten" + i.toString()).value;
             passengers.push(item);
         }
         window.localStorage['passengers'] = JSON.stringify(passengers);
         window.location.href = "#/luggage";
     }
 }
 app.controller('PassengerCtrl', ['$scope', '$http', '$window', function($scope, $http, $window) {
     document.getElementById("top").style.display = "none";
     adminMode = 0;
     restorePassengerCtrl($window);
     if (step < 3 || step > 7) {
         redirect();
     } else {
         $('#groupBtn').hide();
         activeStep = 3;
         restoreStepPanel(3);
         document.getElementById("step3").disabled = false;
         $('#link3').css('color', '#ffffff');
         // if (step == 3)
         // stepPanel(step, false);
         if (step < 3) redirect();

         if (iscall == false) {
             creationInfoForm();
             iscall = true;
         }

         var listPassenger = [];
         $scope.insertPassenger = function() {
             if (step < 4) {
                 step = 4;
                 $window.localStorage['step'] = step;
             }
             nextStepPassengerCtrl($window);
         }
     }
 }]);

 function GetTongTien(scope) {
     scope.tong_tien = addCommas((tongtien + luggagePriceTotal).toString());
 }

 function restorePaymentCtrl($window) {
     step = parseInt($window.localStorage['step']);
     sanbaydi = $window.localStorage['sanbaydi'];
     sanbayden = $window.localStorage['sanbayden'];
     dia_danh_di = $window.localStorage['dia_danh_di'];
     dia_danh_den = $window.localStorage['dia_danh_den'];
     isKhuHoi = $window.localStorage['isKhuHoi'];
     ngaybay1 = $window.localStorage['ngaybay1'];
     ngaybay2 = $window.localStorage['ngaybay2'];
     sohanhkhach = parseInt($window.localStorage['sohanhkhach']);
     if (window.localStorage['step'] != null)
         step = parseInt(window.localStorage['step']);
     else step = 1;
     isBookLuggage = $window.localStorage['isBookLuggage'];
     luggagePriceTotal = parseInt($window.localStorage['luggagePriceTotal']);
     luggageNumber1 = parseInt($window.localStorage['luggageNumber1']);
     luggageNumber2 = parseInt($window.localStorage['luggageNumber2']);
     luggagePrice1 = parseInt($window.localStorage['luggagePrice1']);
     luggagePrice2 = parseInt($window.localStorage['luggagePrice2']);
     tongtien = parseInt($window.localStorage['tongtien']);


     if (isBookLuggage == "false") isBookLuggage = false;
     else isBookLuggage = true;

     if (isKhuHoi == "false") isKhuHoi = false;
     else isKhuHoi = true;

     if ($window.localStorage['chooseFlight'] != null)
         chooseFlight = JSON.parse(window.localStorage['chooseFlight']);

     if ($window.localStorage['passengers'] != null)
         passengers = JSON.parse(window.localStorage['passengers']);

     if ($window.localStorage['luggages'] != null)
         luggages = JSON.parse(window.localStorage['luggages']);

     if (window.localStorage['listSeatBook1'] != null) {
         listSeatBook1 = JSON.parse(window.localStorage['listSeatBook1']);
     }

     if (window.localStorage['listSeatBook2'] != null) {
         listSeatBook2 = JSON.parse(window.localStorage['listSeatBook2']);
     }
 }

 function isBookCompleted(num) {
     var a = 0;

     if (listSeatBook1.length > 0) a++;
     if (isKhuHoi && listSeatBook2.length > 0) a++;
     if (isBookLuggage) a++;
     if (isBookLuggage && isKhuHoi) a++;

     if (num == (a + passengers.length + thong_tin_ve.length + 1)) return true;
     return false;
 }

 function getRandom() {
     var text = "";
     var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

     for (var i = 0; i < 5; i++)
         text += possible.charAt(Math.floor(Math.random() * possible.length));

     return text;
 }

 app.controller('paymentCtrl', ['$scope', '$http', '$window', function($scope, $http, $window) {
     document.getElementById("top").style.display = "none";
     adminMode = 0;
     restorePaymentCtrl($window);
     if (step < 7 || step > 7) {
         redirect();
     } else {
         restoreStepPanel(7);
         count = 0;
         activeStep = 7;
         document.getElementById("step7").disabled = false;
         $('#link7').css('color', '#ffffff');
         GetTongTien($scope);

         $scope.booking = function() {
             tongtien = 0;
             $http.get('/tickets/info')
                 .success(function(data) {
                     stt = data[0].counter + 1;
                     madatcho = lpad(stt, 6);
                     var today = new Date();
                     dat_ve.Ma = madatcho;
                     $window.localStorage['madatcho'] = madatcho;
                     dat_ve.ThoiGianDatCho = today.toISOString().toString();
                     for (var i = 0; i < chooseFlight.length; i++) {
                         if (chooseFlight[i] != 0) {
                             tongtien += chooseFlight[i].GiaBan;
                             var temp = {};
                             temp.MaVe = chooseFlight[i].MaVe;
                             temp.MucGia = chooseFlight[i].MucGia;
                             temp.SoLuong = chooseFlight[i].SoLuong - sohanhkhach;
                             temp.GiaBan = chooseFlight[i].GiaBan;
                             thong_tin_ve.push(temp);
                         }
                     }
                     tongtien = tongtien * sohanhkhach;
                     dat_ve.TongTien = tongtien + luggagePriceTotal;
                     dat_ve.TrangThai = 0;
                     count = 0;

                     //--------------------------Insert Booking info--------------------------------
                     $http.put('/booking', dat_ve)
                         .success(function(data) {
                             //-----------------Insert Seat-----------------------------------     
                             var listSeatBook = [listSeatBook1, listSeatBook2];
                             if ($window.localStorage['pass'] == null) {
                                 $window.localStorage['pass'] = getRandom();;
                             }
                             var item = {};
                             item.code = madatcho;
                             item.pass = $window.localStorage['pass'];

                             $http.post('/ticketInfo', item)
                                 .success(function(data) {
                                     for (var i = 0; i < chooseFlight.length; i++) {
                                         if (chooseFlight[i] != 0) {
                                             var item1 = {};
                                             item1.MaDatCho = madatcho;
                                             item1.MaChuyenBay = chooseFlight[i].Ma;
                                             item1.Ngay = chooseFlight[i].Ngay;
                                             item1.Hang = chooseFlight[i].Hang;
                                             item1.MucGia = chooseFlight[i].MucGia;
                                             item1.GiaBan = chooseFlight[i].GiaBan;
                                             // if (listSeatBook[i][0] != null && listSeatBook[i].length > 0) {
                                             //     item1.MaGhe = listSeatBook[i][0].MaGhe;
                                             // }

                                             $http.post('/flights/detail', item1)
                                                 .success(function(data) {
                                                     count++;
                                                     var processCompleted = 0;
                                                     if (count == chooseFlight.length || (count == 1 && isKhuHoi == false)) {
                                                         //-----------------Insert TicketInfo----------------------------                                                 

                                                         //-----------------Insert Seat-----------------------------------
                                                         if (listSeatBook1.length > 0 || listSeatBook2.length > 0) {
                                                             if (listSeatBook1.length > 0) {
                                                                 for (var i = 0; i < listSeatBook1.length; i++) {
                                                                     listSeatBook1[i].MaDatCho = madatcho;
                                                                     var detail_temp = {};
                                                                     detail_temp.MaChuyenBay = chooseFlight[0].Ma;
                                                                     detail_temp.MaGhe = listSeatBook1[i].MaGhe;
                                                                     detail_temp.Ngay = chooseFlight[0].Ngay;
                                                                     detail_temp.SoGhe = listSeatBook1[i].SoGhe;
                                                                     detail_temp.MaDatCho = madatcho;
                                                                     $http.post('/seats', detail_temp)
                                                                         .success(function(data) {
                                                                             var test = "";
                                                                             processCompleted++;
                                                                             if (isBookCompleted(processCompleted)) {
                                                                                 $('#updateDialog').modal('hide');
                                                                                 step = 8;
                                                                                 $window.localStorage['step'] = 8;
                                                                                 $window.location.href = "#/confirm";
                                                                             }
                                                                         })
                                                                         .error(function(data, status) {
                                                                             $scope.text = "Error: " + data;
                                                                         });
                                                                 }
                                                             }
                                                             if (isKhuHoi && listSeatBook2.length > 0) {
                                                                 for (var i = 0; i < listSeatBook2.length; i++) {
                                                                     listSeatBook2[i].MaDatCho = madatcho;
                                                                     var detail_temp1 = {};
                                                                     detail_temp1.MaChuyenBay = chooseFlight[1].Ma;
                                                                     detail_temp1.MaGhe = listSeatBook2[i].MaGhe;
                                                                     detail_temp1.Ngay = chooseFlight[1].Ngay;
                                                                     detail_temp1.SoGhe = listSeatBook2[i].SoGhe;
                                                                     detail_temp1.MaDatCho = madatcho;
                                                                     $http.post('/seats', detail_temp1)
                                                                         .success(function(data) {
                                                                             var test = "";
                                                                             processCompleted++;
                                                                             if (isBookCompleted(processCompleted)) {
                                                                                 step = 8;
                                                                                 $window.localStorage['step'] = 8;
                                                                                 $('#updateDialog').modal('hide');
                                                                                 $window.location.href = "#/confirm";
                                                                             }
                                                                         })
                                                                         .error(function(data, status) {
                                                                             $scope.text = "Error: " + data;
                                                                         });
                                                                 }
                                                             }
                                                         }
                                                         //-----------------Insert Passenger info-------------------------
                                                         for (var i = 0; i < passengers.length; i++) {
                                                             passengers[i].MaDatCho = madatcho;
                                                             $http.put('/passengers', passengers[i])
                                                                 .success(function(data) {
                                                                     var test = "";
                                                                     processCompleted++;
                                                                     if (isBookCompleted(processCompleted)) {
                                                                         step = 8;
                                                                         $window.localStorage['step'] = 8;
                                                                         $('#updateDialog').modal('hide');
                                                                         $window.location.href = "#/confirm";
                                                                     }
                                                                 })
                                                                 .error(function(data, status) {
                                                                     $scope.text = "Error: " + data;
                                                                 });
                                                         }
                                                         //------------------Insert Luggage Info---------------------------
                                                         if (isBookLuggage == true) {
                                                             luggages[0].MaDatCho = madatcho;
                                                             $http.post('/luggage', luggages[0])
                                                                 .success(function(data) {
                                                                     processCompleted++;
                                                                     if (isBookCompleted(processCompleted)) {
                                                                         step = 8;
                                                                         $window.localStorage['step'] = 8;
                                                                         $('#updateDialog').modal('hide');
                                                                         $window.location.href = "#/confirm";
                                                                     }
                                                                     if (isKhuHoi) {
                                                                         luggages[1].MaDatCho = madatcho;
                                                                         $http.post('/luggage', luggages[1])
                                                                             .success(function(data) {
                                                                                 var test = "";
                                                                                 processCompleted++;
                                                                                 if (isBookCompleted(processCompleted)) {
                                                                                     step = 8;
                                                                                     $window.localStorage['step'] = 8;
                                                                                     $('#updateDialog').modal('hide');
                                                                                     $window.location.href = "#/confirm";
                                                                                 }
                                                                             })
                                                                             .error(function(data, status) {

                                                                             });
                                                                     }
                                                                 })
                                                                 .error(function(data, status) {
                                                                     $scope.text = "Error: " + data;
                                                                 });
                                                         }
                                                         //------------------Insert Status info--------------------------- 
                                                         $http.put('/booking/' + dat_ve.Ma, dat_ve)
                                                             .success(function(data) {
                                                                 $scope.text = "Update successful";
                                                                 processCompleted++;
                                                                 if (isBookCompleted(processCompleted)) {
                                                                     step = 8;
                                                                     $window.localStorage['step'] = 8;
                                                                     $('#updateDialog').modal('hide');
                                                                     $window.location.href = "#/confirm";
                                                                 }
                                                                 for (var i = 0; i < thong_tin_ve.length; i++) {
                                                                     var item = thong_tin_ve[i];
                                                                     $http.put('/tickets/info', item)
                                                                         .success(function(data) {
                                                                             processCompleted++;
                                                                             if (isBookCompleted(processCompleted)) {
                                                                                 step = 8;
                                                                                 $window.localStorage['step'] = 8;
                                                                                 $('#updateDialog').modal('hide');
                                                                                 $window.location.href = "#/confirm";
                                                                             }
                                                                             $scope.text = "Update thong_tin_ve successful";
                                                                         })
                                                                         .error(function(data, status) {
                                                                             $scope.text = "Error: " + data;
                                                                         });
                                                                 }
                                                             })
                                                             .error(function(data, status) {
                                                                 $scope.text = "Error: " + data;
                                                             });
                                                     }
                                                 })
                                                 .error(function(data, status) {
                                                     $scope.text = "Error: " + data;
                                                 });
                                         }
                                     }
                                 })
                                 .error(function(data, status) {

                                 });

                         })
                         .error(function(data, status) {
                             $scope.text = "Error: " + data;
                         });
                 })
                 .error(function(data, status) {
                     $scope.text = "Error: " + data;
                 })

         }
     }
 }]);

 app.controller('confirmCtrl', ['$scope', '$http', '$window', function($scope, $http, $window) {
     document.getElementById("top").style.display = "none";
     adminMode = 0;
     var pass = "";
     if ($window.localStorage['step'] == null || ($window.localStorage['step'] != null && $window.localStorage['step'] == 1)) {
         step = 1;
         redirect();
     } else if ($window.localStorage['step'] != null && $window.localStorage['step'] < 8) redirect();

     if ($window.localStorage['pass'] == null) {
         pass = getRandom();
         $window.localStorage['pass'] = pass;
     }
     $('#groupBtn').hide();
     $scope.code = $window.localStorage['madatcho'];
     $scope.pass = $window.localStorage['pass'];
     $scope.link = "http://cnm-flight-booking.herokuapp.com";
     $scope.url = $window.localStorage['madatcho'] + " " + $window.localStorage['pass'];
     $('#stepPannel').hide();
     $scope.RefreshPage = function() {
         //deleteAll($window);
         //isRefresh = false;
         //location.reload();
     }
 }]);

 //-------------------------------------------------------------------------------------

 app.controller('AdminCtrl', ['$scope', '$http', 'auth', '$window', 'socket', function($scope, $http, auth, $window, socket) {
     //document.getElementById("top").style.display = "none";
     var currentTab = 'tab1';
     //$window.localStorage.removeItem('chooseFlight');
     stepPanel(step, false);
     $scope.changeState = function(s) {
         $('#' + currentTab).removeClass("active");
         $('#' + s).addClass("active");
         currentTab = s;
     }

     $scope.isUpdate = function() {
         if (update == 1) return true;
         return false;
     };

     $scope.dismissModal = function(s) {
         $('#' + s).modal('hide');
     };
     $scope.isAdmin = function() {
         if (adminMode == 1) return true;
         return false;
     }

     $scope.isLoggedIn = function() {
         return false;
     }
     $scope.setStep = function(id) {
         var links = ['#/home', '#/listflighs', '#/infocustomer', '#/luggage', '#/seats', '#/review', '#/payment'];

         // if (id != step)
         // {
         //     $('#step'+ id).css('background-color', '#07c1c1');
         //     $('#step'+ id).css("color", "#ffffff");
         //     $('#step'+ id).hover(function(){
         //         $(this).css("background-color", "#018e8e");
         //         $(this).css("color", "#ffffff");
         //     }, function(){
         //         $(this).css("background-color", "#07c1c1");
         //     });

         //     if (preStep != id)
         //     {
         //         $('#step'+ preStep).css('background-color', '#006666');
         //         $('#step'+ preStep).css("color", "#ffffff");
         //         $('#step'+ preStep).hover(function(){
         //             $(this).css("background-color", "#018e8e");
         //             $(this).css("color", "#ffffff");
         //         }, function(){
         //             $(this).css("background-color", "#006666");
         //         }); 
         //         preStep = id;
         //     }
         // }
         if (id == 1) {
             $('#stepPannel').hide();

             //$window.localStorage.removeItem('chooseFlight');
         }
         if (activeStep == 3) {
             nextStepPassengerCtrl($window);
         } else if (activeStep == 4) {
             nextStepLuggageCtrl($window);
         }
         window.location.href = links[id - 1];
         if (id == 1 || id == 5) location.reload();
     }

     socket.on('notify everyone', function(data) {
         notify(data.message);
     });

       if (!Notification) {
        alert('Desktop notifications not available in your browser. Try Chromium.'); 
        return;
        }


      if (Notification.permission !== "granted")
        Notification.requestPermission();
 }]);

 function notify(message) {
  if (Notification.permission !== "granted")
    Notification.requestPermission();
  else {
    var notification = new Notification('Notification', {
      icon: 'https://lh3.googleusercontent.com/-KKEjBreAiJs/AAAAAAAAAAI/AAAAAAAAAAA/UdAozv04--Y/photo.jpg',
      body: message,
    });

    notification.onclick = function () {
      window.open("http://cnm-flight-booking.herokuapp.com/#/home");      
    };
  }
}

 app.controller('TicketCtrl', ['$scope', '$http', 'auth', '$state', '$window', function($scope, $http, auth, $state, $window) {
     document.getElementById("top").style.display = "none";
     if (auth.isLoggedIn() == false) $state.go('login');
     else {
         deleteAll($window);
         $('#flight').hide();
         $('#stepPannel').hide();
         adminMode = 1;
         $('#groupBtn').hide();
         $('#viewMode').on('change', function() {

             if (this.value == 'Xem theo mã đặt chỗ') {

                 $('#flight').hide();
                 $('#code').show();
             } else {

                 $('#flight').show();
                 $('#code').hide();

                 loadFlights($scope, $http, auth);
             }
         })

         loadBooking($scope, $http, auth);
         $scope.viewTicket = function(id) {
             $scope.Tickets = [];
             $http.get('/booking/info?id=' + id, {
                     headers: { Authorization: 'Bearer ' + auth.getToken() }
                 })
                 .success(function(data) {
                     for (var i = 0; i < data.length; i++) {
                         var item = {};
                         item.MaChuyenBay = data[i].MaChuyenBay;
                         item.Ngay = data[i].Ngay;
                         item.Hang = data[i].Hang;
                         item.MucGia = data[i].MucGia;
                         item.GiaBan = data[i].GiaBan;
                         item.MaHanhLy = data[i].MaHanhLy;
                         item.MaGhe = data[i].MaGhe;
                         item.MaDatCho = data[i].MaDatCho;
                         $scope.Tickets.push(item);
                     }


                 })
                 .error(function(data, status) {
                     var test = "test";
                 });
         }

         $scope.viewPassenger = function(id) {
             $scope.Passengers = [];
             $http.get('/passengers?id=' + id, {
                     headers: { Authorization: 'Bearer ' + auth.getToken() }
                 })
                 .success(function(data) {
                     for (var i = 0; i < data.length; i++) {
                         var item = {};
                         item.DanhXung = data[i].DanhXung;
                         item.Ho = data[i].Ho;
                         item.Ten = data[i].Ten;
                         $scope.Passengers.push(item);
                     }
                 })
                 .error(function(data, status) {
                     var test = "test";
                 });
         }

         $scope.viewSeat = function(seatCode, bookingCode) {
             $scope.Seats = [];
             $http.get('/seats/listSeat?seatCode=' + seatCode + '&' + 'bookingCode=' + bookingCode, {
                     headers: { Authorization: 'Bearer ' + auth.getToken() }
                 })
                 .success(function(data) {
                     for (var i = 0; i < data.length; i++) {
                         $scope.Seats.push(data[i].SoGhe);
                     }
                 })
                 .error(function(data, status) {

                 });
         }
         $scope.viewLuggage = function(id) {
             $http.get('/luggage?luggageCode=' + id, {
                     headers: { Authorization: 'Bearer ' + auth.getToken() }
                 })
                 .success(function(data) {
                     $scope.number = data[0].SoLuong;
                     $scope.totalPrice = data[0].DonGia;
                 })
                 .error(function(data, status) {

                 });
         }
         $scope.viewTicketByFlight = function(flight, date) {
             $scope.Tickets = [];
             $http.get('/booking/infobyflight?flight=' + flight + '&date=' + date, {
                     headers: { Authorization: 'Bearer ' + auth.getToken() }
                 })
                 .success(function(data) {
                     for (var i = 0; i < data.length; i++) {
                         var item = {};
                         item.MaDatCho = data[i].MaDatCho;
                         item.Hang = data[i].Hang;
                         item.MucGia = data[i].MucGia;
                         item.GiaBan = data[i].GiaBan;
                         item.MaHanhLy = data[i].MaHanhLy;
                         item.MaGhe = data[i].MaGhe;
                         $scope.Tickets.push(item);
                     }
                 })
                 .error(function(data, status) {
                     var test = "test";
                 });
         }
     }
 }]);
 app.controller('NotifyCtrl', ['$scope', '$http', 'auth', '$state', '$window', 'socket', function($scope, $http, auth, $state, $window, socket) {
     document.getElementById("top").style.display = "none";
     if (auth.isLoggedIn() == false) $state.go('login');
     else {
         $('#groupBtn').hide();
         $('#stepPannel').hide();
         $scope.sendMessage = function() {
             socket.emit('notify', { message: $('#comment').val() });
         }
     }
 }]);
 app.controller('FlightAdminCtrl', ['$scope', '$http', 'auth', '$state', '$window', function($scope, $http, auth, $state, $window) {
     document.getElementById("top").style.display = "none";

     if (auth.isLoggedIn() == false) $state.go('login');
     else {
         deleteAll($window);
         adminMode = 1;
         $('#groupBtn').hide();
         $('#stepPannel').hide();
         loadFlightInfo($scope, $http, auth);
         $scope.fillUpdate = function(id) {
             $scope.title = "Chỉnh sửa";
             update = 1;
             document.getElementById("id").value = $scope.flightsInfo[id].Ma;
             document.getElementById("start").value = $scope.flightsInfo[id].NoiDi;
             document.getElementById("end").value = $scope.flightsInfo[id].NoiDen;
             $scope.sid = $scope.flightsInfo[id].Ma;
         }

         $scope.updateFlight = function(id) {
             var temp = {};
             temp.flightCode = id;
             temp.newFlightCode = document.getElementById("id").value;
             temp.start = document.getElementById("start").value;
             temp.end = document.getElementById("end").value;
             $http.put('/flights/info', temp, {
                     headers: { Authorization: 'Bearer ' + auth.getToken() }
                 })
                 .success(function(data) {
                     //$scope.text = "Update successful";
                     if (data['status'] == 0) {
                         loadFlightInfo($scope, $http, auth);
                         alert('Cập nhật thành công');
                     } else alert('Có lỗi xảy ra khi cập nhật chuyến bay. Có thể do chuyến bay này đã tồn tại hoặc mã sân bay không tồn tại.');

                 })
                 .error(function(data, status) {
                     //$scope.text = "Error: " + data;
                     var test = "dfsdfs";
                 });
         }

         $scope.fillInsert = function() {
             update = 0;
             $scope.title = "Thêm chuyến bay";

             document.getElementById("id").value = "";
             document.getElementById("start").value = "";
             document.getElementById("end").value = "";

         }

         $scope.insertFlight = function() {
             if (document.getElementById("id").value != '') {
                 var temp = {};
                 temp.Ma = document.getElementById("id").value;
                 temp.NoiDi = document.getElementById("start").value;
                 temp.NoiDen = document.getElementById("end").value;
                 $http.post('/flights/info', temp, {
                         headers: { Authorization: 'Bearer ' + auth.getToken() }
                     })
                     .success(function(data) {
                         //$scope.text = "Update successful";
                         if (data['status'] == 0) {
                             loadFlightInfo($scope, $http, auth);
                             alert('Thêm mới thành công');
                         } else alert('Có lỗi xảy ra khi thêm chuyến bay. Có thể do mã sân bay đi (đến) không đúng hoặc do chuyến bay này đã tồn tại.');

                     })
                     .error(function(data, status) {
                         //$scope.text = "Error: " + data;
                         var test = "dfsdfs";
                     });
             } else {
                 //$scope.text = "ID can not be null";

             }
         };

         $scope.deleteFlight = function(flight) {
             $http.delete('/flights/info?flight=' + flight, {
                     headers: { Authorization: 'Bearer ' + auth.getToken() }
                 })
                 .success(function(data) {
                     //$scope.text = "Delete student with id = " + d + " successful";
                     if (data['status'] == 0) {

                         loadFlightInfo($scope, $http, auth);
                         alert('Xóa thành công');
                     } else alert("Có lỗi xảy ra khi xóa chuyến bay này. Chuyến bay này đã được lên lịch bay.");
                 })
                 .error(function(data, status) {
                     //$scope.text = "Error: " + data;
                     var test = "";
                 });
         };
     }
 }]);

 app.controller('FlightScheduleCtrl', ['$scope', '$state', '$http', 'auth', '$window', function($scope, $state, $http, auth, $window) {
     document.getElementById("top").style.display = "none";
     if (auth.isLoggedIn() == false) $state.go('login');
     else {
         $('#groupBtn').hide();
         $('#stepPannel').hide();
         deleteAll($window);
         loadFlights($scope, $http, auth);
         adminMode = 1;

         $scope.deleteFlight = function(flight, date) {
             $http.delete('/flights?flight=' + flight + "&date=" + date, {
                     headers: { Authorization: 'Bearer ' + auth.getToken() }
                 })
                 .success(function(data) {
                     //$scope.text = "Delete student with id = " + d + " successful";
                     if (data['status'] == 0) {
                         loadFlights($scope, $http, auth);
                         alert('Xóa thành công');
                     } else alert("Có lỗi xảy ra trong khi xóa chuyến bay này. Có thể do chuyến bay đã có hành khách đặt vé. Để đảm bảo quyền lợi cho hành khách, vui lòng hủy các vé đã đặt trước khi xóa chuyến bay này.");
                     //loadFlights($scope, $http);
                 })
                 .error(function(data, status) {
                     //$scope.text = "Error: " + data;
                     var test = "test";
                 });
         }

         $scope.fillUpdate = function(id) {
             loadFlightInfo($scope, $http, auth);
             $scope.title = "Chỉnh sửa";
             update = 1;
             $scope.ex = $scope.listFlights[id].Ma;
             document.getElementById("date").value = $scope.listFlights[id].Ngay;
             document.getElementById("time").value = $scope.listFlights[id].Gio;
             document.getElementById("flightTime").value = $scope.listFlights[id].ThoiGianBay;
             $scope.sid = $scope.listFlights[id].Ma;
             $scope.sdate = $scope.listFlights[id].Ngay;
         }


         $scope.updateFlight = function(id, date) {
             var temp = {};
             temp.flightCode = id;
             temp.oldDate = date;
             temp.newFlightCode = $('#flightCode').find(":selected").text();
             temp.date = document.getElementById("date").value;
             temp.time = document.getElementById("time").value;
             temp.flightTime = document.getElementById("flightTime").value;

             $http.put('/flights', temp, {
                     headers: { Authorization: 'Bearer ' + auth.getToken() }
                 })
                 .success(function(data) {
                     //$scope.text = "Update successful";
                     //loadFlights($scope, $http);
                     if (data['status'] == 0) {
                         alert('Cập nhật thành công');
                         loadFlights($scope, $http, auth);
                         $http.put('/tickets/fkey', temp)
                             .success(function(data) {
                                 var test = "";
                             })
                             .error(function(data, status) {
                                 //$scope.text = "Error: " + data;
                                 var test = "";
                             });
                     } else alert("Có lỗi xảy ra khi cập nhật chuyến bay. Xin đảm bảo rằng trong cùng một ngày không thể có 2 chuyến bay có cùng mã chuyến bay với nhau.");
                 })
                 .error(function(data, status) {
                     //$scope.text = "Error: " + data;
                 });
         }

         $scope.fillInsert = function() {
             update = 0;
             loadFlightInfo($scope, $http, auth);
             $scope.ex = '';
             $scope.title = "Thêm chuyến bay";
             document.getElementById("date").value = "";
             document.getElementById("time").value = "";
             document.getElementById("flightTime").value = "";

         }

         $scope.insertFlight = function() {
             if ($('#date').val() != '') {
                 var temp = {};
                 temp.Ma = $('#flightCode').find(":selected").text();
                 temp.Ngay = document.getElementById("date").value;
                 temp.Gio = document.getElementById("time").value;
                 temp.ThoiGianBay = document.getElementById("flightTime").value;
                 $http.post('/flights', temp, {
                         headers: { Authorization: 'Bearer ' + auth.getToken() }
                     })
                     .success(function(data) {
                         //$scope.text = "Insert successful";
                         if (data['status'] == 0) {
                             $('#myModal').modal('hide');
                             loadFlights($scope, $http, auth);
                             alert('Thêm thành công');
                         } else alert('Có lỗi xảy ra khi thêm chuyến bay. Xin đảm bảo rằng trong cùng một ngày không thể có 2 chuyến bay có cùng mã chuyến bay với nhau.');
                     })
                     .error(function(data, status) {
                         //$scope.text = "Error: " + data;

                     });
             } else {
                 //$scope.text = "ID can not be null";
                 alert("Vui lòng nhập đủ thông tin về ngày bay và mã chuyến bay");

             }
         }

         $scope.fillUpdateTicket = function(id) {
             update = 1;
             $scope.title1 = "Chỉnh sửa vé";
             document.getElementById("priceLevel").value = $scope.Tickets[id].MucGia;
             document.getElementById("number").value = $scope.Tickets[id].SoLuong;
             document.getElementById("price").value = $scope.Tickets[id].GiaBan;
             $scope.sid = $scope.Tickets[id].MaThongTin;
             $scope.ticketType = $scope.Tickets[id].LoaiVe;
             $scope.pLevel = document.getElementById("priceLevel").value;
         }

         $scope.fillInsertTicket = function() {
             update = 0;
             $scope.title1 = "Thêm vé";
             document.getElementById("type").value = "";
             document.getElementById("priceLevel").value = "";
             document.getElementById("number").value = "";
             document.getElementById("price").value = "";
         }

         $scope.updateTicket = function(id, type, pLevel, flight, date) {
             var temp = {};
             temp.code = id;
             temp.oldPriceLevel = pLevel;
             temp.priceLevel = document.getElementById("priceLevel").value;
             temp.number = document.getElementById("number").value;
             temp.price = document.getElementById("price").value;
             $http.put('/tickets', temp, {
                     headers: { Authorization: 'Bearer ' + auth.getToken() }
                 })
                 .success(function(data) {
                     //$scope.text = "Update successful";
                     if (data['status'] == 0) {
                         $('#myModal2').modal('hide');
                         loadTickets($scope, $http, flight, date, auth);
                         alert('Cập nhật thành công');
                     } else alert("Có lỗi xảy ra khi cập nhật vé. Xin đảm bảo rằng không có hai vé nào cùng hạng và cùng mức giá.");
                 })
                 .error(function(data, status) {
                     //$scope.text = "Error: " + data;
                 });
         }

         $scope.insertTicket = function(flight, date) {
             if ((document.getElementById("type").value == 'Y' || document.getElementById("type").value == 'C') && document.getElementById("priceLevel").value != "") {
                 var temp = {};
                 temp.flightCode = flight;
                 temp.date = date;
                 temp.code = flight + date;
                 temp.ticketType = document.getElementById("type").value;
                 temp.infoCode = temp.code + temp.ticketType;
                 temp.priceLevel = document.getElementById("priceLevel").value;
                 temp.number = document.getElementById("number").value;
                 temp.price = document.getElementById("price").value;
                 $http.post('/tickets', temp, {
                         headers: { Authorization: 'Bearer ' + auth.getToken() }
                     })
                     .success(function(data) {
                         if (data['status'] == 0) {
                             $('#myModal2').modal('hide');
                             loadTickets($scope, $http, flight, date, auth);
                             alert('Thêm thành công');
                         } else alert("Có lỗi xảy ra khi thêm vé mới. Xin đảm bảo rằng không có hai vé nào cùng hạng và cùng mức giá.");

                     })
                     .error(function(data, status) {

                     });
             } else {
                 //$scope.text = "ID can not be null";
                 alert("Lỗi xảy ra. Vui lòng đảm bảo rằng loại vé chỉ là Y hoặc C");
             }
         }

         $(function() {
             $('#id').keypress(function(e) {
                 if ((e.which >= 65 && e.which <= 90) || (e.which >= 97 && e.which <= 122) || (e.which >= 48 && e.which <= 57)) {} else {
                     return false;
                 }
             });
         });


         $scope.loadTicketTypeInfo = function(flight, date) {
             $scope.f = flight;
             $scope.d = date;
             $scope.Tickets = [];
             $http.get('/tickets?flight=' + flight + '&date=' + date, {
                     headers: { Authorization: 'Bearer ' + auth.getToken() }
                 })
                 .success(function(data) {
                     for (var i = 0; i < data.length; i++) {
                         var item = {};
                         item.MaThongTin = data[i].MaThongTin;
                         item.MaLoaiVe = data[i].MaLoaiVe;
                         item.LoaiVe = data[i].Hang;
                         item.MucGia = data[i].MucGia;
                         item.SoLuong = data[i].SoLuong;
                         item.GiaBan = data[i].GiaBan;
                         item.MaCB = flight;
                         item.Ngay = date;
                         $scope.Tickets.push(item);
                     }
                 })
                 .error(function(data, status) {
                     $scope.text = "Error: " + data;
                 });
         }

         $scope.loadSeatInfo = function(flight, date) {
             $scope.Seats = [];
             $http.get('/seats/seatInfo?flight=' + flight + converDateType(date), {
                     headers: { Authorization: 'Bearer ' + auth.getToken() }
                 })
                 .success(function(data) {
                     $scope.Seats = [];
                     for (var i = 0; i < data.length; i++) {
                         var item = {};
                         item.MaDatCho = data[i].MaDatCho;
                         item.SoGhe = data[i].SoGhe;
                         $scope.Seats.push(item);
                     }
                 })
                 .error(function(data, status) {
                     $scope.text = "Error: " + data;
                 });
         }

         $scope.deleteTicket = function(typeCode, type, priceLevel, flight, date) {
             $http.delete('/tickets?typeCode=' + typeCode + '&type=' + type + '&priceLevel=' + priceLevel + '&flight=' + flight + '&date=' + date, {
                     headers: { Authorization: 'Bearer ' + auth.getToken() }
                 })
                 .success(function(data) {
                     if (data['status'] == 0)
                         loadTickets($scope, $http, flight, date, auth);
                 });
         }
     }

 }]);

var isLogOutFB = true;
app.controller('UserLoginCtrl', ['$scope', '$window', '$state',
function($scope, $window, $state) {
    adminMode = 0;
    $('#stepPannel').hide();
    $('#groupBtn').hide();
    document.getElementById("top").style.display = "none";

      $scope.facebook = {
             username: "",
             fb_img: ""
         }

         if ($window.localStorage['facebook-token-username'] != null && $window.localStorage['facebook-token-fb_img'] != null) {
             $scope.facebook.username = $window.localStorage['facebook-token-username'];
             $scope.facebook.fb_img = $window.localStorage['facebook-token-fb_img'];
             if (document.getElementById("imgfb") != null) document.getElementById("imgfb").style.display = "block";
             if (document.getElementById("fbname") != null) document.getElementById("fbname").style.display = "block";
             $("#facebook").html('Log out with Facebook');
         }

        $scope.facebookLogin = function() {
        if (isLogOutFB) {
             if ($window.localStorage['facebook-token-username'] != null && $window.localStorage['facebook-token-fb_img'] != null) {
                 $scope.facebook.username = $window.localStorage['facebook-token-username'];
                 $scope.facebook.fb_img = $window.localStorage['facebook-token-fb_img'];
                 document.getElementById("imgfb").style.display = "block";
                 document.getElementById("fbname").style.display = "block";
                 $("#facebook").html('Log out with Facebook');
                 isLogOutFB = false;
             } else {
                 FB.login(function(response) {
                     if (response.authResponse) {
                         FB.api('/me', 'GET', { fields: 'email, name, id, picture' }, function(response) {
                             $scope.$apply(function() {
                                 $scope.facebook.username = response.name;
                                 $scope.facebook.fb_img = response.picture.data.url;
                                 $window.localStorage['facebook-token-username'] = $scope.facebook.username;
                                 $window.localStorage['facebook-token-fb_img'] = $scope.facebook.fb_img;
                                document.getElementById("imgfb").style.display = "block";
                                 document.getElementById("fbname").style.display = "block";
                                 $("#facebook").html('Log out with Facebook');
                                 isLogOutFB = false;
                             });
                         });
                        

                     } else {

                     }
                 }, {
                     scope: 'email, user_likes',
                     return_scopes: true
                 })
             }

         } else if (!isLogOutFB) {
             $("#facebook").html('Log in with Facebook');
             document.getElementById("imgfb").style.display = "none";
             document.getElementById("fbname").style.display = "none";
             $window.localStorage.removeItem('facebook-token-username');
             $window.localStorage.removeItem('facebook-token-fb_img');
             isLogOutFB = true;
         }
     }
}]);





 app.controller('JAuthCtrl', ['$scope', '$window', '$state', 'auth',

     function($scope, $window, $state, auth) {
         //document.getElementById("top").style.display = "none";
         adminMode = 1;
         $('#stepPannel').hide();
         $('#groupBtn').hide();
         document.getElementById("top").style.display = "none";
         deleteAll($window);
         $scope.user = {};
       
         $scope.register = function() {
             auth.register($scope.user).error(function(error) {
                 $scope.error = error;
             }).then(function() {
                 $state.go('home');
             });
         };

         $scope.logIn = function() {
             auth.logIn($scope.user).error(function(error) {
                 $scope.error = error;
             }).then(function() {
                 $state.go('flightschedule');
             });
         };

         
     }
 ]);

 app.controller('NavCtrl', ['$scope', '$state', 'auth',
     function($scope, $state, auth) {
         //document.getElementById("top").style.display = "none";
         $scope.isLoggedIn = auth.isLoggedIn;
         $scope.currentUser = auth.currentUser;
         $scope.logOut = function() {
             auth.logOut();
             $state.go('login');
         }
     }
 ]);

 function restoreLuggageCtrl(window) {

     isKhuHoi = window.localStorage['isKhuHoi'];
     if (isKhuHoi == "true") isKhuHoi = true;
     else isKhuHoi = false;

     dia_danh_di = window.localStorage['dia_danh_di'];
     dia_danh_den = window.localStorage['dia_danh_den'];
     ngaybay1 = window.localStorage['ngaybay1'];
     ngaybay2 = window.localStorage['ngaybay2'];
     if (window.localStorage['step'] != null)
         step = parseInt(window.localStorage['step']);
     else step = 1;
     isBookLuggage = window.localStorage['isBookLuggage'];
     if (isBookLuggage != null) {
         if (isBookLuggage == "false") isBookLuggage = false;
         else isBookLuggage = true;
         if (isBookLuggage) {
             luggagePriceTotal = parseInt(window.localStorage['luggagePriceTotal']);
             luggageNumber1 = parseInt(window.localStorage['luggageNumber1']);
             if (isKhuHoi) luggageNumber2 = parseInt(window.localStorage['luggageNumber2']);
         }
     } else isBookLuggage = false;

     if (window.localStorage['chooseFlight'] != null) {
         chooseFlight = JSON.parse(window.localStorage['chooseFlight']);
     }
 }

 function nextStepLuggageCtrl($window) {

     if (isBookLuggage) {
         var item = {};
         item.MaCB = chooseFlight[0].Ma;
         item.MaDatCho = madatcho;
         item.Ngay = ngaybay1
         item.SoLuong = $("#numLuggage1").val();
         item.DonGia = parseInt($("#numLuggage1").val()) * parseInt(luggagePrice1);
         luggages.push(item);
         luggagePriceTotal = 0;
         luggagePriceTotal = luggagePriceTotal + parseInt($("#numLuggage1").val()) * parseInt(luggagePrice1);
         luggageNumber1 = $("#numLuggage1").val();
         $window.localStorage['luggageNumber1'] = luggageNumber1;

         if (isKhuHoi) {
             var item = {};
             item.MaCB = chooseFlight[1].Ma;
             item.MaDatCho = madatcho;
             item.Ngay = ngaybay2;
             item.SoLuong = $("#numLuggage2").val();
             item.DonGia = parseInt($("#numLuggage2").val()) * parseInt(luggagePrice2);
             luggages.push(item);
             luggagePriceTotal = luggagePriceTotal + parseInt($("#numLuggage2").val()) * parseInt(luggagePrice2);
             luggageNumber2 = $("#numLuggage2").val();
             $window.localStorage['luggageNumber2'] = luggageNumber2;
         }

         if (luggagePriceTotal == 0) {
             $window.localStorage['luggagePrice1'] = 0;
             $window.localStorage['luggagePrice2'] = 0;
             isBookLuggage = false;
         }

         $window.localStorage['luggagePriceTotal'] = luggagePriceTotal;
         $window.localStorage['luggages'] = JSON.stringify(luggages);
     } else {
         $window.localStorage['luggagePriceTotal'] = 0;
         $window.localStorage['luggageNumber1'] = 0;
         $window.localStorage['luggageNumber2'] = 0;
         $window.localStorage['luggagePrice1'] = 0;
         $window.localStorage['luggagePrice2'] = 0;
     }

     $window.localStorage['isBookLuggage'] = isBookLuggage;
 }
 app.controller('luggageCtrl', ['$scope', '$http', 'auth', '$window',
     function($scope, $http, auth, $window) {
         document.getElementById("top").style.display = "none";
         adminMode = 0;
         restoreLuggageCtrl($window);
         if (step < 4 || step > 7) {
             redirect();
         } else {
             $('#groupBtn').hide();
             $('#luggageDialog').modal("show");
             activeStep = 4;
             restoreStepPanel(4);
             document.getElementById("step4").disabled = false;
             $('#link4').css('color', '#ffffff');
             $('#flight1').hide();
             $('#flight2').hide();
             $scope.bookBtn = "Đặt hành lý";

             $http.get('/flights/luggage?code=' + chooseFlight[0].Ma + '&date=' + ngaybay1)
                 .success(function(data) {
                     luggagePrice1 = data[0].GiaHanhLy;
                     $window.localStorage['luggagePrice1'] = luggagePrice1;
                     if (isKhuHoi) {
                         $http.get('/flights/luggage?code=' + chooseFlight[1].Ma + '&date=' + ngaybay2)
                             .success(function(data) {
                                 luggagePrice2 = data[0].GiaHanhLy;
                                 $window.localStorage['luggagePrice2'] = luggagePrice2;
                                 $('#luggageDialog').modal("hide");
                             })
                             .error(function(data, status) {

                             });
                     } else $('#luggageDialog').modal("hide");
                 })
                 .error(function(data, status) {});

             $scope.bookLuggage = function() {
                 if (isBookLuggage == false) {
                     $scope.from1 = dia_danh_di;
                     $scope.to1 = dia_danh_den;
                     $scope.date1 = chooseFlight[0].Ngay;
                     isBookLuggage = true;
                     $scope.bookBtn = "Hủy đặt hành lý";

                     if (step > 4 && luggagePriceTotal != 0) {
                         $("#totalCost").text(addCommas(luggagePriceTotal.toString()) + " VND");
                         if (isKhuHoi) {
                             //$("#totalCost").text((parseInt($("#numLuggage1").val()) + parseInt($("#numLuggage2").val())) * 180000 + " VND");
                             $scope.from2 = dia_danh_den;
                             $scope.to2 = dia_danh_di;
                             $scope.date2 = chooseFlight[1].Ngay;
                             $('#numLuggage1').val(luggageNumber1);
                             $('#numLuggage2').val(luggageNumber2);
                             $('#flight1').show();
                             $('#flight2').show();
                         } else {
                             $('#flight1').val(luggageNumber1);
                             $('#flight1').show();
                         }
                     } else {
                         $('#numLuggage1').val(1);
                         if (isKhuHoi) {
                             $('#numLuggage2').val(1);
                             $("#totalCost").text(addCommas((parseInt($("#numLuggage1").val()) * parseInt(luggagePrice1) + parseInt($("#numLuggage2").val()) * parseInt(luggagePrice2)).toString()) + " VND");
                             //$("#totalCost").text((parseInt($("#numLuggage1").val()) + parseInt($("#numLuggage2").val())) * 180000 + " VND");
                             $scope.from2 = dia_danh_den;
                             $scope.to2 = dia_danh_di;
                             $scope.date2 = chooseFlight[1].Ngay;
                             $('#flight1').show();
                             $('#flight2').show();
                         } else {
                             $("#totalCost").text(addCommas((parseInt($("#numLuggage1").val()) * luggagePrice1).toString()) + " VND");
                             $('#flight1').show();
                             //TODO: Remove luggage
                         }
                     }
                     //$("#totalCost").text((parseInt($("#numLuggage1").val()) + parseInt($("#numLuggage2").val())) * 180000 + " VND");

                 } else {
                     $scope.bookBtn = "Đặt hành lý";
                     isBookLuggage = false;
                     $('#flight1').hide();
                     $('#flight2').hide();
                     $("#totalCost").text("0 VND");
                     document.getElementById("next").disabled = false;
                 }
             }

             $scope.addLuggage = function() {
                 if (step < 5) {
                     step = 5;
                     $window.localStorage['step'] = step;
                 }
                 nextStepLuggageCtrl($window);
                 $window.location.href = "#/seats";
             }

             $("#numLuggage1").change(function() {
                 if ($("#numLuggage1").val() < 0) $("#numLuggage1").val(0);
                 if (isKhuHoi) $("#totalCost").text(addCommas((parseInt($("#numLuggage1").val()) * parseInt(luggagePrice1) + parseInt($("#numLuggage2").val()) * parseInt(luggagePrice2)).toString()) + " VND");
                 else $("#totalCost").text(addCommas((parseInt($("#numLuggage1").val()) * luggagePrice1).toString()) + " VND");
                 if ($("#totalCost").text() == "0 VND" && isBookLuggage == true) document.getElementById("next").disabled = true;
                 else document.getElementById("next").disabled = false;
             });

             $("#numLuggage2").change(function() {
                 if ($("#numLuggage2").val() < 0) $("#numLuggage2").val(0);
                 if (isKhuHoi) $("#totalCost").text(addCommas((parseInt($("#numLuggage1").val()) * parseInt(luggagePrice1) + parseInt($("#numLuggage2").val()) * parseInt(luggagePrice2)).toString()) + " VND");
                 else $("#totalCost").text(addCommas((parseInt($("#numLuggage1").val()) * luggagePrice1).toString()) + " VND");
                 if ($("#totalCost").text() == "0 VND" && isBookLuggage == true) document.getElementById("next").disabled = true;
                 else document.getElementById("next").disabled = false;

             });

             if (isBookLuggage) {
                 $scope.from1 = dia_danh_di;
                 $scope.to1 = dia_danh_den;
                 $scope.date1 = chooseFlight[0].Ngay;
                 isBookLuggage = true;
                 $scope.bookBtn = "Hủy đặt hành lý";

                 $("#totalCost").text(addCommas(luggagePriceTotal.toString()) + " VND");
                 if (isKhuHoi) {
                     //$("#totalCost").text((parseInt($("#numLuggage1").val()) + parseInt($("#numLuggage2").val())) * 180000 + " VND");
                     $scope.from2 = dia_danh_den;
                     $scope.to2 = dia_danh_di;
                     $scope.date2 = chooseFlight[1].Ngay;
                     $('#numLuggage1').val(luggageNumber1);
                     $('#numLuggage2').val(luggageNumber2);
                     $('#flight1').show();
                     $('#flight2').show();
                 } else {
                     $('#flight1').val(luggageNumber1);
                     $('#flight1').show();
                 }
             } else {
                 luggagePriceTotal = 0;
                 luggageNumber1 = 0;
                 luggageNumber2 = 0;
             }
         }
     }
 ]);

 function restoreSeatCtrl(window) {
     dia_danh_di = window.localStorage['dia_danh_di'];
     dia_danh_den = window.localStorage['dia_danh_den'];
     sohanhkhach = window.localStorage['sohanhkhach'];

     isKhuHoi = window.localStorage['isKhuHoi'];
     if (isKhuHoi == "true") isKhuHoi = true;
     else isKhuHoi = false;
     
     if (window.localStorage['step'] != null)
         step = parseInt(window.localStorage['step']);
     else step = 1;

     if (window.localStorage['listSeatBook1'] != null) {
         listSeatBook1 = JSON.parse(window.localStorage['listSeatBook1']);
         for (var i = 0; i < listSeatBook1.length; i++) {
             document.getElementById(listSeatBook1[i].SoGhe + '1').className += " btn-warning";
         }
     }
     if (window.localStorage['listSeatBook2'] != null) {
         listSeatBook2 = JSON.parse(window.localStorage['listSeatBook2']);
         for (var i = 0; i < listSeatBook2.length; i++) {
             document.getElementById(listSeatBook2[i].SoGhe + '2').className += " btn-warning";
         }
     }
     if (window.localStorage['chooseFlight'] != null) {
         chooseFlight = JSON.parse(window.localStorage['chooseFlight']);
     }
 }
 app.controller('SeatCtrl', ['$scope', '$http', '$window', function($scope, $http, $window) {
     document.getElementById("top").style.display = "none";
     adminMode = 0;
     restoreSeatCtrl($window);
     if (step < 5 || step > 7) {
         redirect();
     } else {
         $('#groupBtn').hide();
         $('#seatDialog').modal("show");
         getSeatBooked($scope, $http);
         activeStep = 5;
         restoreStepPanel(5);
         document.getElementById("step5").disabled = false;
         $scope.chooseSeat = function(id) {
             var type = id.slice(-1);
             var vitrighe = id.slice(0, -1);

             if (type == 1) {
                 if ($("#" + id).hasClass("btn-warning")) {
                     document.getElementById(id).classList.remove("btn-warning");
                     document.getElementById(id).className += " btn-success";
                     var seat_temp = {};
                     var date_chieudi = stringToDate(chooseFlight[0].Ngay, "dd-MM-yyyy", "-");
                     seat_temp.MaGhe = chooseFlight[0].Ma + date_chieudi.getDate() + "-" + (date_chieudi.getMonth() + 1) + "-" + date_chieudi.getFullYear();
                     seat_temp.SoGhe = vitrighe;
                     seat_temp.MaDatCho = -1;
                     listSeatBook1.splice(listSeatBook1.indexOf(seat_temp), 1);
                     $window.localStorage['listSeatBook1'] = JSON.stringify(listSeatBook1);

                 } else if (listSeatBook1.length == sohanhkhach) {
                     alert("Bạn đã đặt đủ vé cho chiều đi");
                 } else if (listSeatBook1.length < sohanhkhach) {
                     document.getElementById(id).className += " btn-warning";
                     var seat_temp = {};
                     var date_chieudi = stringToDate(chooseFlight[0].Ngay, "dd-MM-yyyy", "-");
                     seat_temp.MaGhe = chooseFlight[0].Ma + date_chieudi.getDate() + "-" + (date_chieudi.getMonth() + 1) + "-" + date_chieudi.getFullYear();
                     seat_temp.SoGhe = vitrighe;
                     seat_temp.MaDatCho = -1;
                     listSeatBook1.push(seat_temp);
                     $window.localStorage['listSeatBook1'] = JSON.stringify(listSeatBook1);
                 }
             }
             if (type == 2) {
                 if ($("#" + id).hasClass("btn-warning")) {
                     document.getElementById(id).classList.remove("btn-warning");
                     document.getElementById(id).className += " btn-success";
                     var seat_temp = {};
                     var date_chieuve = stringToDate(chooseFlight[1].Ngay, "dd-MM-yyyy", "-");
                     seat_temp.MaGhe = chooseFlight[0].Ma + date_chieuve.getDate() + "-" + (date_chieuve.getMonth() + 1) + "-" + date_chieuve.getFullYear();
                     seat_temp.SoGhe = vitrighe;
                     seat_temp.MaDatCho = -1;
                     listSeatBook2.splice(listSeatBook2.indexOf(seat_temp), 1);
                     $window.localStorage['listSeatBook2'] = JSON.stringify(listSeatBook2);
                 } else if (listSeatBook2.length == sohanhkhach) {
                     alert("Bạn đã đặt đủ vé cho chiều về");
                 } else if (listSeatBook2.length < sohanhkhach) {
                     document.getElementById(id).className += " btn-warning";
                     var seat_temp = {};
                     var date_chieuve = stringToDate(chooseFlight[1].Ngay, "dd-MM-yyyy", "-");
                     seat_temp.MaGhe = chooseFlight[1].Ma + date_chieuve.getDate() + "-" + (date_chieuve.getMonth() + 1) + "-" + date_chieuve.getFullYear();
                     seat_temp.SoGhe = vitrighe;
                     seat_temp.MaDatCho = -1;
                     listSeatBook2.push(seat_temp)
                     $window.localStorage['listSeatBook2'] = JSON.stringify(listSeatBook2);
                 }
             }
         }
         $scope.next = function() {
             if (step < 6) {
                 step = 6;
                 $window.localStorage['step'] = step;
             }
             $window.location.href = "#/review";
         }
     }

 }]);

 function restoreReviewCtrl($window) {
     sohanhkhach = parseInt($window.localStorage['sohanhkhach']);
     luggagePrice1 = parseInt($window.localStorage['luggagePrice1']);
     luggagePrice2 = parseInt($window.localStorage['luggagePrice2']);
     luggageNumber1 = parseInt($window.localStorage['luggageNumber1']);
     luggageNumber2 = parseInt($window.localStorage['luggageNumber2']);
     luggagePriceTotal = parseInt($window.localStorage['luggagePriceTotal']);
     tongtien = parseInt($window.localStorage['tongtien']);
     if (window.localStorage['step'] != null)
         step = parseInt(window.localStorage['step']);
     else step = 1;
     dia_danh_di = $window.localStorage['dia_danh_di'];
     dia_danh_den = $window.localStorage['dia_danh_den'];
     isKhuHoi = $window.localStorage['isKhuHoi'];

     if (isKhuHoi == "false") isKhuHoi = false;
     else isKhuHoi = true;

     if (window.localStorage['chooseFlight'] != null) {
         chooseFlight = JSON.parse(window.localStorage['chooseFlight']);
     }

     if (window.localStorage['listSeatBook1'] != null) {
         listSeatBook1 = JSON.parse(window.localStorage['listSeatBook1']);
     }

     if (window.localStorage['listSeatBook2'] != null) {
         listSeatBook2 = JSON.parse(window.localStorage['listSeatBook2']);
     }
 }

 function nextStepReviewCtrl($window) {
     if (step < 7) {
         step = 7;
         $window.localStorage['step'] = step;
     }
 }
 app.controller('reviewCtrl', ['$scope', '$http', '$window',
     function($scope, $http, $window) {
         document.getElementById("top").style.display = "none";
         adminMode = 0;
         restoreReviewCtrl($window);
         if (step < 6 || step > 7) {
             redirect();
         } else {
             $('#groupBtn').hide();
             activeStep = 6;
             restoreStepPanel(6);
             document.getElementById("step6").disabled = false;
             if (!isKhuHoi) $("#returnFlight").hide();
             $scope.passengers = sohanhkhach;
             $scope.totalCost = addCommas(tongtien + luggagePriceTotal) + " VND";

             $scope.ticketLevel1 = chooseFlight[0].Hang;
             $scope.flightCode1 = chooseFlight[0].Ma;
             $scope.from1 = dia_danh_di;
             $scope.to1 = dia_danh_den;
             $scope.startDate1 = chooseFlight[0].Ngay;
             $scope.duration1 = chooseFlight[0].ThoiGianBay;
             $scope.luggageNumber1 = luggageNumber1;
             $scope.luggagePrice1 = addCommas(luggagePrice1.toString()) + " VND";
             $scope.flightPrice1 = addCommas(chooseFlight[0].GiaBan.toString()) + " VND";

             var seat1 = "";
             for (var i = 0; i < listSeatBook1.length; i++) {
                 seat1 = seat1 + listSeatBook1[i].SoGhe + " ";
             }
             $scope.seat1 = seat1;

             if (isKhuHoi) {
                 $scope.ticketLevel2 = chooseFlight[1].Hang;
                 $scope.flightCode2 = chooseFlight[1].Ma;
                 $scope.from2 = dia_danh_den;
                 $scope.to2 = dia_danh_di;
                 $scope.startDate2 = chooseFlight[1].Ngay;
                 $scope.duration2 = chooseFlight[1].ThoiGianBay;
                 $scope.luggageNumber2 = luggageNumber2;
                 $scope.luggagePrice2 = addCommas(luggagePrice2.toString()) + " VND";
                 $scope.flightPrice2 = addCommas(chooseFlight[1].GiaBan.toString()) + " VND";

                 var seat2 = "";
                 for (var i = 0; i < listSeatBook2.length; i++) {
                     seat2 = seat2 + listSeatBook2[i].SoGhe + " ";
                 }
                 $scope.seat2 = seat2;
             }
             $scope.changeToPay = function() {
                 nextStepReviewCtrl($window);
                 $window.location.href = "#/payment";
             }
         }
     }
 ]);
