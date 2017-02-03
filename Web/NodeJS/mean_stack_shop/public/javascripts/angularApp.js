'use strict';
var app = angular.module('bAuction', ['ui.router', 'ngRoute', 'ngMessages', 'store-directives']);
var countOnline = 0;
var st1 = false;
var st2 = false;
var st3 = false;

app.config(['$stateProvider', '$urlRouterProvider',
	function($stateProvider, $urlRouterProvider) {
		$stateProvider
		.state('home', {
			url : '/home',
			templateUrl : '/home.html',
			controller : 'MainCtrl',
		})
		.state('404', {
			url : '/404',
			templateUrl : '/404.html',
			controller : 'ErrorCtrl',
			
		})
		.state('account', {
			url : '/account',
			templateUrl : '/account.html',
			controller : 'AccountCtrl',
			
		})
		.state('contact', {
			url : '/contact',
			templateUrl : '/contact.html',
			controller : 'ContactCtrl',
			
		})
		.state('editprofile', {
			url : '/editprofile',
			templateUrl : '/editprofile.html',
			controller : 'EditProfileCtrl',
			
		})
		.state('product-detail', {
			url : '/product-detail/{id}',
			templateUrl : '/product-detail.html',
			controller : 'ProductDetailCtrl',
			
		})
		.state('product', {
			url : '/product',
			templateUrl : '/product.html',
			controller : 'ProductCtrl',
			
		})
		.state('profile', {
			url : '/profile',
			templateUrl : '/profile.html',
			controller : 'ProfileCtrl',
			
		});

		$urlRouterProvider.otherwise('home');
	}]);

/*--------------------------------------CONTROLLER--------------------------------------*/
app.controller('MainCtrl',['$scope', 'socket', '$http', function($scope, socket, $http){
  angular.element(document).ready(function() {
    $('#slider').nivoSlider(); 
  });

  $scope.allProduct = [];

  $scope.refresh = function() {
    $scope.allProduct = [];

    $http.get('/api/producttypes').success(function(data) {
      for (var i = 0; i < data.length; i++) {
        var item = {};
        item.type = data[i];
        
        $scope.allProduct.push(item);

        $http.get('/api/product/type', {params: {type: item.type.id}}).success(function(data){
          for (var j = 0; j < $scope.allProduct.length; j++) {
            if ($scope.allProduct[j].type.id == data[0].producttype) {
              $scope.allProduct[j].products = [];
              
              angular.copy(data, $scope.allProduct[j].products);

              break;
            }
          }
        });
      }
    });
  };

  $scope.refresh();

  socket.on('auction success', function(data) {
    console.log('MainCtrl: auction success');

    $scope.$apply(function() {
      $scope.refresh();
    });
  });
}]);

app.controller('ErrorCtrl', [
'$scope',
function($scope){
  $scope.test = 'Hello world!';
}]);

app.controller('AccountCtrl', [
'$scope','$state','auth',
function($scope, $state, auth){
  $scope.submit = function() {
      console.log('submitting', $scope.signup.username, $scope.signup.password);
  };

  $scope.user = {};
  //Code for register submit form in account page, when click register
  $scope.register = function() {
    //Get value in ng-model ($scope.user) in regiser form and send it to authentication
    auth.register($scope.user).error(function(error) {
      $scope.error = error;
    }).then(function() {
      $state.go('home');
    });
  };

  $scope.logIn = function() {
    auth.logIn($scope.login).error(function(error) {
      $scope.error = error;
    }).then(function() {
      $state.go('home');
    });
  };
}]);

app.controller('ContactCtrl', [
'$scope',
function($scope){
  $scope.test = 'Hello world!';
}]);

app.controller('EditProfileCtrl', [
'$scope',
function($scope){
  $scope.test = 'Hello world!';
}]);

app.controller('ProductDetailCtrl', [
'$scope',
'$http',
'$stateParams',
'auth',
'socket',
function($scope, $http, $stateParams, auth, socket) {
  $scope.isLoggedIn = auth.isLoggedIn;
  $scope.timeLeft = 'N/A';
  $scope.errorMessage = '';
  $scope.minStep = 10000;
  $scope.history = [];

  $scope.refresh = function() {
    $scope.isLoggedIn = auth.isLoggedIn;
    $scope.timeLeft = 'N/A';
    $scope.errorMessage = '';
    $scope.minStep = 10000;
    $scope.history = [];

    $http.get('/api/auction/' + $stateParams.id).success(function(data) {
      angular.copy(data, $scope.history);
    });

    $http.get('/api/product/' + $stateParams.id).success(function(data){
      $scope.product = data;

      var t = Date.parse($scope.product.expriretime) - Date.parse(new Date());
      
      var seconds = Math.floor((t / 1000) % 60);
      if (seconds < 0)
        seconds = 0;

      var minutes = Math.floor((t / 1000 / 60) % 60);
      if (minutes < 0)
        minutes = 0;

      var hours = Math.floor((t / (1000 * 60 * 60)) % 24);
      if (hours < 0)
        hours = 0;

      var days = Math.floor(t / (1000 * 60 * 60 * 24));
      if (days < 0)
        days = 0;

      var timeLeft = days + ' day, ' + hours + ' hour, ' + minutes + ' minute, ' + seconds + ' second';
      $scope.timeLeft = timeLeft;
    });

    $http.get('/api/getproductimages/'+ $stateParams.id).success(function(data){
      $scope.images = data;
    });

    $http.get('/api/getproductreviews/'+ $stateParams.id).success(function(data){
      $scope.reviews = data;
    });
  };

  $scope.refresh();

  $scope.submitAution = function() {
    if (!auth.isLoggedIn()) {
      $scope.errorMessage = "You must login to bid new price";
      return;
    }

    console.log('ProductDetailCtrl: submitAuction(): newPrice: ' + $scope.newPrice);
    if ($scope.newPrice == undefined || $scope.newPrice < ($scope.product.currentprice + $scope.minStep)
        || $scope.newPrice < ($scope.product.startingbid + $scope.minStep)) {
      $scope.errorMessage = "Your price must be greater than current price at least " + $scope.minStep;
      return;
    }

    console.log('ProductDetailCtrl: emit: new auction');
    socket.emit('new auction', {newPrice: $scope.newPrice, product: $scope.product});
  }

  socket.on('auction fail', function(data) {
    console.log('ProductDetailCtrl: on: auction fail');

    $scope.$apply(function() {
      $scope.errorMessage = "Auction failed";
    });
  });

  socket.on('temp step', function(data) {
    console.log('ProductDetailCtrl: on: temp step');    

    var product = data.updatedProduct;

    console.log('ProductDetailCtrl: updatedProduct: ' + product.startingbid)

    $http.put('/api/product/' + product.id, product);

    console.log('ProductDetailCtrl: emit: temp step result');
    socket.emit('temp step result', {});
  });

  socket.on('auction success', function(data) {
    console.log('ProductDetailCtrl: on: auction success');

    $scope.$apply(function() {
      $scope.refresh();
      $scope.errorMessage = 'Auction success';
    });
  });

  Share.init(document.getElementsByClassName('J-share'), {
    title: 'social share',
    url: 'http://abc.com/#/product-detail/' + $stateParams.id
  });
}]);

app.controller('ProductCtrl', [
'$scope',
'$http',
'socket',
function ($scope, $http, socket) {
  $scope.maxProduct = 3;
  $scope.nPage = 0;

  $scope.allProduct = [];
  $scope.visibleProduct = [];
  $scope.pageProduct = [];

  $scope.refresh = function() {
    $scope.maxProduct = 3;
    $scope.nPage = 0;

    $scope.allProduct = [];
    $scope.visibleProduct = [];
    $scope.pageProduct = [];

    $http.get('/api/producttypes').success(function(data) {
      for (var i = 0; i < data.length; i++) {
        var item = {};
        item.type = data[i];
        
        $scope.allProduct.push(item);

        $http.get('/api/product/type', {params: {type: item.type.id}}).success(function(data){
          for (var j = 0; j < $scope.allProduct.length; j++) {          
            if ($scope.allProduct[j].type.id == data[0].producttype) {
              $scope.allProduct[j].products = [];
              
              angular.copy(data, $scope.allProduct[j].products);

              for (var t = 0; t < data.length; t++) {
                $scope.visibleProduct.push(data[t]);
                $scope.pageProduct.push(data[t]);
              }

              // Update number of page
              $scope.nPage = Math.floor($scope.visibleProduct.length / $scope.maxProduct);
              if (($scope.visibleProduct.length % $scope.maxProduct) != 0)
                $scope.nPage = $scope.nPage + 1;

              $scope.updatePage(0);

              break;
            }
          }
        });
      }
    });
  };

  $scope.refresh();

  $scope.getPage = function() {
    return new Array($scope.nPage);
  };

  $scope.updatePage = function(page) {
    if (page >= $scope.nPage)
      return;

    var tmp = [];

    var top = (page + 1) * $scope.maxProduct;
    if (top > $scope.visibleProduct.length)
      top = $scope.visibleProduct.length;
    for (var i = page * $scope.maxProduct; i < top; i++) {
      tmp.push($scope.visibleProduct[i]);
    }

    $scope.pageProduct = tmp;
  }

  $scope.selectCategory = function(type) {
    var tmp = [];

    for (var i = 0; i < $scope.allProduct.length; i++) {
      if ($scope.allProduct[i].type.id == type) {
        angular.copy($scope.allProduct[i].products, tmp);
      }
    }

    $scope.visibleProduct = tmp;
    $scope.pageProduct = tmp;

    // Update number of page
    $scope.nPage = Math.floor($scope.visibleProduct.length / $scope.maxProduct);
    if (($scope.visibleProduct.length % $scope.maxProduct) != 0)
      $scope.nPage = $scope.nPage + 1;

    console.log('nPage:');
    console.log($scope.nPage);

    $scope.updatePage(0);
  };

  socket.on('auction success', function(data) {
    console.log('ProductCtrl: auction success');

    $scope.$apply(function() {
      $scope.refresh();
    });
  });
}]);

app.controller('ReviewController',['$scope','$stateParams', '$http', function($scope,$stateParams,$http) {
  this.review = {};

  this.addReview = function() {
    this.review.time = new Date();
    this.review.productid = $stateParams.id;
    $http.post('/api/review',this.review);
    this.review = {};
  };
}]);

app.controller('ProfileCtrl', [
'$scope',
'auth',
function($scope, auth){
  $scope.username = auth.currentUser;
}]);

app.controller('NavCtrl', ['$scope', 'auth',
function($scope, auth) {
  $scope.isLoggedIn = auth.isLoggedIn;
  $scope.currentUser = auth.currentUser;
  $scope.logOut = auth.logOut;
}]);

/*--------------------------------------DIRECTIVE--------------------------------------*/
app.directive("w3TestDirective1", function() {
  $('#pass').keyup(function(e) {
    var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
    var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
    var enoughRegex = new RegExp("(?=.{6,}).*", "g");
    if (false == enoughRegex.test($(this).val())) {
      $('#passstrength').html('More Characters');
      st2 = false;
      document.getElementById("regSubmit").disabled = true;
    } else if (strongRegex.test($(this).val())) {
      st2 = true;
       if (st1 == true && st2 == true && st3 == true) document.getElementById("regSubmit").disabled = false;
      $('#passstrength').className = 'ok';
      $('#passstrength').html('Strong!');
    } else if (mediumRegex.test($(this).val())) {
      st2 = true;
       if (st1 == true && st2 == true && st3 == true) document.getElementById("regSubmit").disabled = false;
      $('#passstrength').className = 'alert';
      $('#passstrength').html('Medium!');
    } else {
      st2 = true;
      if (st1 == true && st2 == true && st3 == true) document.getElementById("regSubmit").disabled = false;
      $('#passstrength').className = 'error';
      $('#passstrength').html('Weak!');
    }
    return true;
  });
});

app.directive("w3TestDirective1", function() {
   $(document).ready(function() {
      $('#register_form').submit(function() {
        $(this).ajaxSubmit({
          error: function(xhr) {
            status('Error: ' + xhr.status);
          },
         success: function(response) {
          console.log(response);
         }
        });
        //Very important line, it disable the page refresh.
        return false;
      });
    });
});

app.directive('usernameValidator', function($http, $q, $timeout) {
  return {
    require: 'ngModel',
    link: function(scope, element, attrs, ngModel) {
      ngModel.$asyncValidators.username = function(modelValue, viewValue) {
        if (!viewValue) {
          return $q.when(true);
        }
        var deferred = $q.defer();
        $timeout(function() {
          if (viewValue) {
            console.log(viewValue);
            $http.get('/api/user/'+ viewValue).success(function(data){
              var userdata = data;
              console.log(userdata);
              if(userdata.length >0)
              {
                deferred.reject();
                st1 = false;
                document.getElementById("regSubmit").disabled = true;
              }
              else
              {
                st1 = true;
                deferred.resolve();
                if (st1 == true && st2 == true && st3 == true) document.getElementById("regSubmit").disabled = false;
              }
            });
          }
          else
          {
            st1 = true;
            deferred.resolve();
            if (st1 == true && st2 == true && st3 == true) document.getElementById("regSubmit").disabled = false;
          }
        }, 2000);
        return deferred.promise;
      };
    }
  };
});
app.directive('passwordValidator', function($http, $q, $timeout) {
  return {
    require: 'ngModel',
    link: function(scope, element, attrs, ngModel) {
      ngModel.$asyncValidators.confirm = function(modelValue, viewValue) {
        if (!viewValue) {
          return $q.when(true);
        }
        var deferred = $q.defer();
        
        $timeout(function() {
          if (viewValue) {
            if (viewValue != document.getElementById('pass').value)
            {
              deferred.reject();
              st3 = false;
              document.getElementById("regSubmit").disabled = true;
            }
            else
            {
              deferred.resolve();
              st3 = true;
              if (st1 == true && st2 == true && st3 == true) document.getElementById("regSubmit").disabled = false;
            }
          }
          else
          {
            deferred.resolve();
            st3 = true;
            if (st1 == true && st2 == true && st3 == true) document.getElementById("regSubmit").disabled = false;
          }
        }, 2000);
        return deferred.promise;
      };
    }
  };
});

/*--------------------------------------FACTORY--------------------------------------*/
app.factory('socket', function() {
  var socket = io.connect('http://localhost:3000/#/home');
  return socket;
});

app.factory('auth', ['$http', '$window',
function($http, $window) {
  var auth = {};

  if(isNaN($window.localStorage['number-view'])){
    $window.localStorage['number-view'] = 0;
    $window.localStorage['number-online'] = 0;
  }

  $window.localStorage['number-view'] =  parseInt($window.localStorage['number-view']) + 1;
  document.getElementById("view").innerHTML = "View: " + $window.localStorage['number-view'];
  document.getElementById("online").innerHTML = "Online: " + $window.localStorage['number-online'];
  auth.saveToken = function(token) {
    $window.localStorage['flapper-news-token'] = token;
    $window.localStorage['number-online'] =  parseInt($window.localStorage['number-online']) + 1;
    document.getElementById("online").innerHTML = "Online: " +  $window.localStorage['number-online'];
  };

  auth.getToken = function() {
    
    return $window.localStorage['flapper-news-token'];
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

  //Post user info to account page and get the token retured (see on index.js)
  auth.register = function(user) {
    return $http.post('/account', user).success(function(data) {
      window.myFunction();
      auth.saveToken(data.token);
    });
  };

  auth.logIn = function(user) {
    return $http.post('/login', user).success(function(data) {
      auth.saveToken(data.token);
    });
  };

  auth.logOut = function() {
    $window.localStorage.removeItem('flapper-news-token');
    $window.localStorage['number-online'] = parseInt($window.localStorage['number-online']) - 1;
    document.getElementById("online").innerHTML = "Online: " + $window.localStorage['number-online'];

    $http.get('/', {});
  };
 
  return auth;
}]);