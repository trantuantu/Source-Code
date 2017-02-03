'use strict';
var app = angular.module('lotte', ['ui.router']);
var update = 1;
var insertError = 0;
function loadData(scope, http)
{
    insertError = 0;
    //scope.listStudents = [];

//API1
http.get('/filmSchedule/cinema?date=20161024&film=The Accountant')
   .success(function(data) {
    for (var i = 0; i < data.length; i++) 
    {
      var s = "test";
    }
   })
   .error(function(data, status) {
        scope.text = "Error: " + data;
    });
//API2
    http.get('/filmSchedule?date=20161024&city=HCM&cinema=Landmark')
   .success(function(data) {
    for (var i = 0; i < data.length; i++) 
    {
      var s = "test";
    }
   })
   .error(function(data, status) {
        scope.text = "Error: " + data;
    });
//API3
    http.get('/films')
   .success(function(data) {
    for (var i = 0; i < data.length; i++) 
    {
      var s = "test";
    }
   })
   .error(function(data, status) {
        scope.text = "Error: " + data;
    });
//API4
     http.get('/cinemas')
   .success(function(data) {
    for (var i = 0; i < data.length; i++) 
    {
      var s = "test";
    }
   })
   .error(function(data, status) {
        scope.text = "Error: " + data;
    });
}
app.controller('MainCtrl',['$scope', '$http', function($scope, $http)
{
  loadData($scope, $http);
}]);