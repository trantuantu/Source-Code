/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';
var app = angular.module('live_score', ['ui.router']);
var listUserId = [];
var listActivityId = [];
var mapData;
var userId, activityId;
var isPaused = true;
var count = 0;
var seekslider;
var isboole = true;
var numberMutualFriend = 0;
var line = [];
var iterator = 0;
var total = 0;
var timeline;
var time;

//Install FB SDK
window.fbAsyncInit = function() {
    FB.init({
      appId      : '601200633406434',
      xfbml      : true,
      version    : 'v2.8'
    });
    FB.AppEvents.logPageView();
};

(function(d, s, id){
   var js, fjs = d.getElementsByTagName(s)[0];
   if (d.getElementById(id)) {return;}
   js = d.createElement(s); js.id = id;
   js.src = "//connect.facebook.net/en_US/sdk.js";
   fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
   
function loadMutualFriends(userId, activityId, scope, http){
    scope.listFriend = [];
    http.get('/api/getFriendsByActivity.php?user=' + userId + '&activity=' + activityId)
    .success(function (data) {
        numberMutualFriend = data['mutualFriends'].length;
        for (var i = 0; i < data['mutualFriends'].length; i++)
        {
            var item = {};
            item.name = data['mutualFriends'][i]['name'];
            item.id = data['mutualFriends'][i]['id'];
            item.avatar = data['mutualFriends'][i]['avatar'];
            item.activity_id = data['mutualFriends'][i]['activity_id'];
            scope.listFriend.push(item);
        }
      
        if (numberMutualFriend != 0)
        {
            $('#selectAll').removeClass("disabled");
            scope.noti = "";
        }
        else scope.noti = "No mutual friends for this activity";
    })
    .error(function(data){
        window.location.href = "/public/html/loginStrava.html";
    })  
}

function vidSeek() {
    for(var i = 0; i< line.length ; i++){
        animate(line[i], timeline.value);
    }
}

// This example adds an animated symbol to a polyline.
function initMap(pathData,scope) {
    var map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 40.756189, lng: -73.994181},
        zoom: 15
        
    });
    
    loadListUser(pathData,scope);
    var listUser =  document.getElementById('tableList');
    map.controls[google.maps.ControlPosition.TOP_RIGHT].push(listUser);
    
    var audioplayer = document.getElementById('audioplayer');
    map.controls[google.maps.ControlPosition.BOTTOM_CENTER].push(audioplayer);
    
    var seeker = document.getElementById('seeker');
    var pButton = document.getElementById('pButton');
    pButton.addEventListener('click', function (e) {
        e.preventDefault();
        if (isPaused) {
            isPaused = false;
            pButton.setAttribute("class", "play");
        } else {
            isPaused = true;
            pButton.setAttribute("class", "pause");
        }
    });

    timeline = document.getElementById('timeline');
    timeline.addEventListener("mousedown", function () {
        if (isboole)
            isboole = false;
    });

    timeline.addEventListener("mouseup", function () {
        isboole = true;
    });

    timeline.addEventListener("change", vidSeek, false);
    
    for (var i = 0; i < pathData.length; i++) {
        var temp_line = new google.maps.Polyline({
            path: angular.fromJson(pathData[i].data),
            map: map
        });
        line.push(temp_line);  
    }
    
    for(var i = 0; i <line.length; i++)
        animateCircle(map,line[i],line[i].getPath().length,pathData[i].profile_image);
}
// Use the DOM setInterval() function to change the offset of the symbol
// at fixed intervals.
function animateCircle(map,line,lenth,link) {
    var infowindow = new google.maps.InfoWindow();
    var marker;
    window.setInterval(function () {
        if (isPaused) {
            count = (count + 1) % lenth;

            if (isboole)
                timeline.value = count;

            var position = line.getPath().getAt(count);
            
            if (!marker) {
                var avartar = {
                    url: link,
                    scaledSize: new google.maps.Size(30, 30), // scaled size
                    origin: new google.maps.Point(0,0), // origin
                    anchor: new google.maps.Point(0, 0) // anchor
               };
                marker = new google.maps.Marker({
                    position: position,
                    map: map,
                    icon:avartar
                });
                
                infowindow.setContent(marker.getPosition().toUrlValue(6));
                infowindow.open(map, marker);
            } else {
                marker.setPosition(position);
            }
            infowindow.setContent(marker.getPosition().toUrlValue(6));
        }
    }, 100);
}

function loadListUser(array,scope){
    scope.listUser = [];
    
    for (var i = 0; i < array.length; i++)
    {
        var item = {};
        item.name = array[i].name;
        item.profile_image = array[i].profile_image;
        item.id = array[i].id;
        scope.listUser.push(item);
    }
    
}

function animate(line, time) {
    count = time % line.getPath().length;
}

app.controller('mapCtrl', ['$scope', '$http', function ($scope, $http)
{
    $http.get('/api/map.php')
    .success(function (data) {
        initMap(data, $scope);
    })
    .error(function(data){
        window.location.href = "/public/html/loginStrava.html";
    })
}]);
    
app.controller('mainCtrl', ['$scope', '$http', function ($scope, $http)
{
    $http.get('/api/main.php')
    .success(function (data) {
        if (data == "Authenticated")  window.location.href = "/public/html/dashboard.html";
        $("#btnStrava").attr("href", data["authLink"]);
    });
}]);

app.controller('fbCtrl', ['$scope', '$http', function ($scope, $http)
{
    $http.get('/api/loginFacebook.php')
    .success(function (data) {
        if (data == "Authenticated")  window.location.href = "/public/html/dashboard.html";
        $("#btnFacebook").attr("href", data["authLink"]);
    })
    .error(function(data){
        window.location.href = "/public/html/loginStrava.html";
    })
}]);

app.controller('dashboardCtrl', ['$scope', '$http', function ($scope, $http)
{    
    document.getElementById('shareBtn').onclick = function() {
        var currentdate = new Date(); 
        //Auto-generate challenge link
        //TODO: Upload to host to upload this link to Facebook, Facebook will prevent this Link
        //STEP 1: User id and challenge id will be send to challenge.php (see STEP 2 in challenge.php
        var id = "" + currentdate.getFullYear() + currentdate.getMonth() + currentdate.getDate() +  currentdate.getHours() +  currentdate.getMinutes() +  currentdate.getSeconds();    
        var initLink = "http://localhost:8080/public/api/challenge.php";
        initLink  = initLink + "?id=" + id + "&user_id=" + userId;
        console.log(initLink);
        FB.ui({
          method: 'share',
          mobile_iframe: true,
          href: initLink,
        }, function(response){});
    }

    $http.get('/api/dashboard.php')
    .success(function (data) {
        $("#profilePicture").attr("src", data["info"]["profile_medium"]);
        $("#userName").text(data["info"]["firstname"] + " " + data["info"]["lastname"]);
        
        //Load list activity
        $scope.listActivity = [];
        
        for (var i = 0; i < data["listActivity"].length; i++)
        {
            var item = {};
            item.id = data["listActivity"][i]["id"];
            item.name = data["listActivity"][i]["name"];
            item.date = (data["listActivity"][i]["date"].replace("T", "    ")).replace("Z", "");
            $scope.listActivity.push(item);
           
        }
        
        $scope.updatedStt = data["updateStt"];
        userId = data["graphEdge"]["id"] + data["info"]["id"];
        var token = data["token"]["access_token"];
    })
    .error(function(data){
        window.location.href = "/public/html/loginStrava.html";
    })
        
    $scope.select = function (id, activity, index)
    {
        if (listUserId.indexOf(id) == -1)
        {
            listUserId.push(id);
            listActivityId.push(activity);
            $("#" + index).css('background-color', 'red');
            if (listUserId.length == 1) $('#compare').removeClass("disabled");
        } else
        {
            listUserId.splice(listUserId.indexOf(id), 1);
            listActivityId.splice(listActivityId.indexOf(activity), 1);
            $("#" + index).css('background-color', 'green');
            if (listUserId.length == 0) $('#compare').addClass("disabled");
        }
    }
    
    $scope.selectAll = function()
    {
        listUserId = [];
        listActivityId = [];
        
        for (var i = 0; i < $scope.listFriend.length; i++)
        {
            listUserId.push($scope.listFriend[i].id);
            listActivityId.push($scope.listFriend[i].activity_id);
             $("#" + i).css('background-color', 'red');
        }
        
        $('#compare').removeClass("disabled");
    }
    
    $scope.logout = function()
    {
        $http.get('/api/logout.php')
        .success(function (data) {
            window.location.href = '/public/html/loginStrava.html';
         })  
    }
    
    $('#activityDropBox').on('change', function () {

        if (this.value != "Choose Activity")
        {
            activityId = '';
            $scope.noti = "Loading...";
            activityId = this.value;
            loadMutualFriends(userId, this.value, $scope, $http);
            listUserId = [];
            listActivityId = [];
            $scope.listFriend = [];
            $('#selectAll').addClass("disabled");
            $('#compare').addClass("disabled");
            numberMutualFriend = 0;
        }else location.reload();
    })

    $scope.requestRender = function ()
    {
        if (listUserId.length > 0)
        {
            if (listUserId.indexOf(userId) == -1)
            {
                listUserId.push(userId);
                listActivityId.push(activityId);
            }
            
            var userIdQueryString = "";
            var activityQueryString = "";

            for (var i = 0; i < listUserId.length; i++)
            {
                if (i < listUserId.length - 1)
                {
                    userIdQueryString = userIdQueryString + listUserId[i] + ',';
                    activityQueryString = activityQueryString + listActivityId[i] + ',';
                }
                else
                {
                    userIdQueryString = userIdQueryString + listUserId[i];
                    activityQueryString = activityQueryString + listActivityId[i];
                }
            }

            if (listUserId.length > 1)
            {
                //Request maps
                $http({
                    url: "/api/renderMap.php",
                    method: "GET",
                    params: {user_id: userIdQueryString, activity_id: activityQueryString}
                }).success(function (data) {
                    mapData = data;
                    window.location.href = "/public/html/map.html";
                })
                 .error(function(data){
                    window.location.href = "/public/html/loginStrava.html";
                })
            }
        }
    }

    $scope.updateData = function ()
    {
        $scope.updateStt = "Updating... Please wait for a moment...";
        $http.get('/api/updateData.php')
        .success(function (data) {
            location.reload();           
        })
        .error(function(data){
           window.location.href = "/public/html/loginStrava.html";
        })
    }
    $scope.createChallenge = function()
    {
        
    }
}]);
