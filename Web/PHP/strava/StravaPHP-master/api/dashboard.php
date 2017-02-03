<?php
/**
 * Created by PhpStorm.
 * User: Tuan Tu
 * Date: 12/6/2016
 * Time: 2:33 PM
 */
require_once __DIR__."/../config.php";
include SITE_ROOT . "/getData.php";
    
if (!session_id()){
    session_start();
}
if (empty($_SESSION['array'])){
    http_response_code(404);
}
else{
    //STEP 3: Get info (challengeId and userId) and process challenge (save challenge to database)
    if (!empty($_SESSION['id']))
    {
        //Notify to client
        $_SESSION["updateStt"] = "Challenge Accepted";
        //Remove challenge, wail for the next time
        unset($_SESSION['id']);
        unset($_SESSION['user_id']);
    }
    
    $graphEdge = $_SESSION['array'];
    $info = $_SESSION['info'];
    
    //Update list facebook friend to user_data
    $userData = new userData();
    $userData->updateFacebookFriends($info["id"], $userData->getEscapeString(json_encode($graphEdge)));
    $userData->updateId($info["id"], $graphEdge["id"]);
    $activityData = new activityData();
    $activityData->updateId($info["id"], $graphEdge["id"]);
    $users = new users();
    $users->updateId($info["id"], $graphEdge["id"]);

    //Get activity info
    $activityData = new activityData();
    $activities = $activityData->getActivityByUser($graphEdge["id"] . $info["id"]);
    $activitiesInfo = array();

    for ($i = 0; $i < count($activities); $i++)
    {
        $item = array("name" => "", "date" => "");
        $res_json = str_replace("'", '', str_replace('\\', '', $activities[$i]["json_activity_info"]));
        $item["id"] = json_decode($res_json, true)["id"];
        $item["name"] = json_decode($res_json, true)["name"];
        $item["date"] = json_decode($res_json, true)["start_date_local"];
        array_push($activitiesInfo, $item);
    }
    
    echo(json_encode(array("graphEdge" => $graphEdge, "info" => $info, "listActivity" => $activitiesInfo, "token" => $_SESSION['token'], "updateStt" => $_SESSION["updateStt"])));
    $_SESSION["updateStt"] = "";
}