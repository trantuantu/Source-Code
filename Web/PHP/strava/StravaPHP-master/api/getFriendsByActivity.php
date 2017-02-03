<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
require_once __DIR__."/../config.php";
include SITE_ROOT . '/vendor/autoload.php';
include SITE_ROOT . '/dbConnection.php';
include SITE_ROOT . '/controller/users.php';
include SITE_ROOT . '/controller/userData.php';
include SITE_ROOT . '/vendor/autoload.php';
use Strava\API\OAuth;
use Strava\API\Exception;
use Strava\API\Factory;
use Strava\API\Client;
use Strava\API\Service\REST;

$method = $_SERVER['REQUEST_METHOD'];
$parameters = $_SERVER['QUERY_STRING'];
parse_str($parameters, $output);
$userId = $output['user'];
$activityId = $output['activity'];

if (!session_id()){
        session_start();
}
if (empty($_SESSION['array'])){
        http_response_code(404);
}
else{
    ini_set('max_execution_time', 3600);
    
    $userData = new userData();
    $users = new users();
    
    //Get facebook friend
    $facebookFriends = $userData->getFacebookFriends($userId);
    if (count($facebookFriends) > 0)
    {
        $listFbFriends = json_decode(str_replace("'", '', $facebookFriends[0]['json_facebook_friends']), true);
        $facebookFriendIds = array();

        for ($i = 0; $i < count($listFbFriends["friends"]); $i++)
        {
            array_push($facebookFriendIds, $listFbFriends["friends"][$i]['id']);
        }

        //Get activity's friend
        $adapter = new Pest('https://www.strava.com/api/v3');
        $service = new REST($_SESSION['token'], $adapter);  // Define your user token here..
        $client = new Client($service);
        $activityFriends = $client->getActivityRelated($activityId);
        $activityFriendIds = array();
        $queryArray = '';

        for ($i = 0; $i < count($activityFriends); $i++)
        {
            if ($i < count($activityFriends) - 1) $queryArray = $queryArray . $activityFriends[$i]['athlete']['id'] . ',';
            else  $queryArray = $queryArray . $activityFriends[$i]['athlete']['id'];
            array_push($activityFriendIds, $activityFriends[$i]['athlete']['id']);
        }

        $queryArray = '(' . $queryArray . ')';
        if ($queryArray == '()')
        {
            $queryArray = '';
            echo json_encode(array("mutualFriends" => []));
        }
        else 
        {
            $activityUsers = $users->getUserByStravaId($queryArray);
            $activityUsersStravaIds = array();
            $activityUserIndex = array();
            $mutualFriends = array();

            for ($i = 0; $i < count($activityUsers); $i++) array_push($activityUsersStravaIds, $activityUsers[$i]['strava_id']);

            //Find corresponding Index of $activityUsers in $activityFriendIds
            for ($i = 0; $i < count($activityUsersStravaIds); $i++)
            {
                $k = array_search($activityUsersStravaIds[$i], $activityFriendIds);
                array_push($activityUserIndex, $k);
            }

            for ($i = 0; $i < count ($activityUsers); $i++)
            {
                $item = array("id" => "", "name" => "", "avatar" => "", "activity_id" => "");
                if (in_array(substr($activityUsers[$i]['id'], 0, strlen($activityUsers[$i]['id']) - strlen($activityUsers[$i]['strava_id'])), $facebookFriendIds))
                {
                    $item["id"] = $activityUsers[$i]['id'];
                    $item["name"] = $activityUsers[$i]['name'];
                    $item["avatar"] = json_decode(str_replace("'", '', $activityUsers[$i]['info']), true)['profile_medium'];
                    $item["activity_id"] = $activityFriends[$activityUserIndex[$i]]['id'];

                    array_push($mutualFriends, $item);
                }
            }

            echo json_encode(array("mutualFriends" => $mutualFriends));
        }
    }else  echo json_encode(array("mutualFriends" => []));
}