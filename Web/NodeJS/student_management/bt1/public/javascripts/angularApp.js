'use strict';
var app = angular.module('bt1', ['ui.router']);
var update = 1;
var insertError = 0;
function loadData(scope, http)
{
	  insertError = 0;
	  scope.listStudents = [];
	  http.get('/api/students')
	 .success(function(data) {
	 	for (var i = 0; i < data.length; i++) 
	 	{
	 		var item = {};
	 		item.id = data[i].id;
	 		item.name = data[i].name;
	 		item.birthday = data[i].birthday;
	 		item.gender = data[i].gender;
	 		item.address = data[i].address;
	 		scope.listStudents.push(item);
	 	}
	 })
	 .error(function(data, status) {
  			scope.text = "Error: " + data;
		});
}
app.controller('MainCtrl',['$scope', '$http', function($scope, $http)
{
	 
	  loadData($scope, $http);
	/* console.log('click');
	 $scope.click = function() {
	 	$scope.text= 'click';

     };*/

      $scope.delete = function(d) {
	 	 $http.delete('/api/students/' + d)
	 	 .success(function(data) {
	 	 	$scope.text = "Delete student with id = " + d + " successful";
	 	 	loadData($scope, $http);
	 	 })
	 	 .error(function(data, status) {
  			$scope.text = "Error: " + data;
		 });
	 };
	 $scope.fill = function(id)
	 {
	 	insertError = 0;
	 	$scope.title = "Update";
	 	update = 1;
	 	for (var i = 0; i < $scope.listStudents.length; i++)
	 		if ($scope.listStudents[i].id == id)
	 		{
	 			document.getElementById("id").value = $scope.listStudents[i].id;
	 			document.getElementById("name").value = $scope.listStudents[i].name;
	 			document.getElementById("birthday").value = $scope.listStudents[i].birthday;
	 			document.getElementById("gender").value = $scope.listStudents[i].gender;
	 			document.getElementById("address").value = $scope.listStudents[i].address;
	 			$scope.sid = $scope.listStudents[i].id;
	 			break;
	 		}
	 }

 	 $scope.update = function(id)
	 {
	 	if ($('#id').val() != '')
     	{
		 	var temp = {};
		 	temp.id = id;
		 	temp.uid = document.getElementById("id").value;
		 	temp.name = document.getElementById("name").value;
		 	temp.birthday = document.getElementById("birthday").value;
		 	temp.gender = document.getElementById("gender").value;
		 	temp.address = document.getElementById("address").value;
		 	$http.put('/api/students', temp)
		 	.success(function(data)
		 	{
		 		$scope.text = "Update successful";
		 		loadData($scope, $http);
		 	})
		 	.error(function(data, status) {
	  			$scope.text = "Error: " + data;
			});
	 	}
	 	else $scope.text = "ID can not be null";
	 }

	 $scope.fillInsert = function()
	 {
	 	update = 0;
	 	$scope.title = "Insert";
 		if (insertError == 0)
 		{
 			document.getElementById("id").value = "";
 			document.getElementById("name").value = "";
 			document.getElementById("birthday").value = "";
 			document.getElementById("gender").value = "";
 			document.getElementById("address").value = "";
 		}
	 }
	 $scope.click = function() {
	 	$scope.text= 'click';
     };

     
     $scope.isUpdate = function()
     {
     	if (update == 1) return true;
     	return false;
     };

     $scope.insert = function()
     {
     	if ($('#id').val() != '')
     	{

     		var temp = {};
		 	temp.id = document.getElementById("id").value;
		 	temp.name = document.getElementById("name").value;
		 	temp.birthday = document.getElementById("birthday").value;
		 	temp.gender = document.getElementById("gender").value;
		 	temp.address = document.getElementById("address").value;
		 	$http.post('/api/students', temp)
		 	.success(function(data)
		 	{
		 		$scope.text = "Insert successful";
		 		loadData($scope, $http);
		 	})
		 	.error(function(data, status) {
	  			$scope.text = "Error: " + data;
	  			insertError = 1;
			});
     	}
     	else
     	{
     		$scope.text = "ID can not be null";
     		insertError = 1;
     	} 
     };

     $(function(){
	  $('#id').keypress(function(e){
	    if((e.which >= 65 && e.which <= 90) || (e.which >= 97 && e.which <= 122) || (e.which >= 48 && e.which <= 57)) {
	    } else {
	      return false;
	    }
  	  });
	});
}]);
