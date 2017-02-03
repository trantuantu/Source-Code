<?php
/**
 * Created by PhpStorm.
 * User: Tuan Tu
 * Date: 12/6/2016
 * Time: 11:21 AM
 */
require_once "config.php";
require_once 'controller/users.php';
require_once 'controller/userData.php';
require_once 'controller/activityData.php';
include 'vendor/autoload.php';
use Strava\API\Exception;
use Phalcon\Db\Adapter\Pdo\Mysql;

function buildMultiPartRequest($ch, $boundary, $fields, $files, $token) {
    $delimiter = '-------------' . $boundary;
    $data = '';
    foreach ($fields as $name => $content) {
        $data .= "--" . $delimiter . "\r\n"
            . 'Content-Disposition: form-data; name="' . $name . "\"\r\n\r\n"
            . $content . "\r\n";
    }
    foreach ($files as $name => $content) {
        $data .= "--" . $delimiter . "\r\n"
            . 'Content-Disposition: form-data; name="' . $name . '"; contents="' . fopen("ride.gpx", "r") . '"; filename="' . $content . '"' . "\r\n" . "Content-Type: application/gpx+xml" . "\r\n" . "\r\n\r\n";
    }
    $data .= "--" . $delimiter . "--\r\n";
    curl_setopt_array($ch, [
        CURLOPT_URL => "https://www.strava.com/api/v3/uploads",
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_ENCODING => "",
        CURLOPT_MAXREDIRS => 10,
        CURLOPT_TIMEOUT => 30,
        CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
        CURLOPT_CUSTOMREQUEST => "POST",
        CURLOPT_COOKIEFILE => $token,
        CURLOPT_POST => true,
        CURLOPT_HTTPHEADER => [
            "authorization: Bearer " . $token,
            'Content-Type: multipart/form-data; boundary=' . $delimiter
        ],
        CURLOPT_POSTFIELDS => $data,
        CURLOPT_SSL_VERIFYHOST => 0,
        CURLOPT_SSL_VERIFYPEER => 0
    ]);
    return $ch;
}

function uploadMultiPartFile ($token)
{
    $client = new GuzzleHttp\Client([
        // Base URI is used with relative requests
        'base_uri' => 'https://www.strava.com/api/v3/uploads',
        // You can set any number of default request options.
        'timeout'  => 10.0,
    ]);
    $client->request('POST', 'https://www.strava.com/api/v3/uploads', [
        'headers' => [
            "authorization:" => "Bearer " . $token,
        ],
        'multipart' => [
            [
                'name'     => 'activity_type',
                'contents' => 'ride',
            ],
            [
                'name'     => 'data_type',
                'contents' => 'gpx',
            ],
            [
                'name'     => 'file',
                'contents' => fopen('ride.gpx', 'r'),
                'filename' => 'ride.gpx'
            ],
        ]
    ]);
}

function exportToGPX($activity_id)
{
    $base_url = "https://www.strava.com/stream/" . $activity_id . "?streams%5B%5D=latlng&streams%5B%5D=distance&streams%5B%5D=altitude&streams%5B%5D=time&streams%5B%5D=moving";
    $content = file_get_contents ($base_url, NULL, NULL, 0);
    $hander = curl_init("https://www.strava.com/api/v3/activities/783985368/streams/latlng");
    $fields = array('Authorization: Bearer' => "22d3386ad8ac2c1cd48747b548f55250c72c7d77");
    curl_setopt($hander, CURLOPT_HTTPHEADER, $fields);
    curl_setopt($hander, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt($hander, CURLOPT_SSL_VERIFYHOST, 0);
    curl_setopt($hander, CURLOPT_SSL_VERIFYPEER, 0);
    $res = curl_exec($hander);

    //GPX header
    $file_header = file_get_contents("gpx/gpx_header.txt");
    $time_created = "<metadata><time>2016-11-16T00:12:21Z</time></metadata>";
    $name = "Actvity Name";
}

function getData($client, $token, $isUpdate, $uId)
{
    ini_set('max_execution_time', 3600);
    $athleteInfo = $client->getAthlete();
    
    //Get Name
    $first_name = $athleteInfo['firstname'];
    $last_name = $athleteInfo['lastname'];
    
    //Get stravaID
    $created_at = $athleteInfo['created_at'];
    $strava_id = $athleteInfo['id'];
    $id = $strava_id;
    $user = new users();
    $userData = new userData();
    $activityData = new activityData();
    
    try
    {
        //Check if user existed
        $resUser = $user->getUser($id);
        if (count($resUser) == 0 || $isUpdate == true) {
            //Get user info
            $userObj = new usersModel();
            $userObj->id = $id;
            $userObj->name = $first_name . ' ' . $last_name;
            $userObj->info = $user->getEscapeString(json_encode($athleteInfo));
            $userObj->created_at = $created_at;
            $userObj->strava_id = $id;
            $userObj->access_token = $token;
            if ($isUpdate == true) $user->deleteAll($uId);
            $user->insert($userObj);
            
            //Get user data
            $user_data = getAthleteData($client, $strava_id);
            $userDataObj = new userDataModel();
            $userDataObj->user_id = $id;
            $userDataObj->json_stats = $userData->getEscapeString($user_data["stats"]);
            $userDataObj->json_clubs = $userData->getEscapeString($user_data["clubs"]);
            $userDataObj->json_friends = $userData->getEscapeString($user_data["friends"]);
            $userDataObj->json_followers = $userData->getEscapeString($user_data["followers"]);
            $userDataObj->json_both_following = $userData->getEscapeString($user_data["bothFollowing"]);
            $userDataObj->json_kom = $userData->getEscapeString($user_data["kom"]);
            $userDataObj->json_starred_segments = $userData->getEscapeString($user_data["starredSegments"]);
            if ($isUpdate == true) $userData->deleteAll($uId);
            $userData->insert($userDataObj);
   
            //Get all activites of user             
            $activity_data = getActivitesData($client);
            $activityDataObj = new activityDataModel();
            
            for ($i = 0; $i < count($activity_data); $i++) {
                $activityDataObj->activity_id =  $activity_data[$i]["id"];
                $activityDataObj->user_id =  $id;
                $activityDataObj->json_activity_info =  $activityData->getEscapeString($activity_data[$i]["info"]);
                $activityDataObj->json_data =   $activityData->getEscapeString($activity_data[$i]["data"]);
                $activityDataObj->json_comments =  $activityData->getEscapeString($activity_data[$i]["comment"]);
                $activityDataObj->json_kudos =  $activityData->getEscapeString($activity_data[$i]["kudo"]);
                $activityDataObj->json_photos =  $activityData->getEscapeString($activity_data[$i]["photo"]);
                $activityDataObj->json_laps =  $activityData->getEscapeString($activity_data[$i]["lap"]);
                $activityDataObj->json_upload_status =  $activityData->getEscapeString($activity_data[$i]["uploadStatus"]);
                if ($isUpdate == true) $activityData->deleteAll($uId);
                $activityData->insert($activityDataObj);
            }
            
        }
    } catch (Exception $e) {
    }
    
    return $athleteInfo;
}

function getAthleteData($client, $id)
{
    $result = array("stats" => "" ,"clubs" => "", "friends" => "", "followers" => "", "bothFollowing" => "", "kom" => "", "starredSegments" => "");
    $stats = $client->getAthleteStats($id);
    $clubs = $client->getAthleteClubs();
    $friends = $client->getAthleteFriends($id);
    $follower = $client->getAthleteFollowers($id);
    $bothFollowing = $client->getAthleteBothFollowing($id);
    $kom = $client->getAthleteKom($id);
    $starredSegments = $client->getAthleteStarredSegments($id);
   
    $result["stats"] = json_encode($stats);
    $result["clubs"] = json_encode($clubs);
    $result["friends"] = json_encode($friends);
    $result["followers"] = json_encode($follower);
    $result["bothFollowing"] = json_encode($bothFollowing);
    $result["kom"] = json_encode($kom);
    $result["starredSegments"] = json_encode($starredSegments);

    return $result;
}

function getActivitesData($client)
{
    $result = array("id" => "" ,"info" => "", "data" => "", "comment" => "", "kudo" => "", "photo" => "", "lap" => "", "uploadStatus" => "");
    $results = array();
    //Get all activity list
    $response =  $client->getAthleteActivities();
    
    for ($i = 0; $i < count($response); $i++)
    {
        $info = $response[$i];
        $streamData = $client->getStreamsActivity($info["id"], "latlng,altitude");
        $comment = $client->getActivityComments($info["id"]);
        $kudo = $client->getActivityKudos($info["id"]);
        $photo = $client->getActivityPhotos($info["id"]);
        $lap = $client->getActivityLaps($info["id"]);
        $uploadStatus = $client->getActivityUploadStatus($info["id"]);

        $result["id"] = $info["id"];
        $result["info"] = json_encode($info);
        $result["data"] = json_encode($streamData);
        $result["comment"] = json_encode($comment);
        $result["kudo"] = json_encode($kudo);
        $result["photo"] = json_encode($photo);
        $result["lap"] = json_encode($lap);
        $result["uploadStatus"] = json_encode($uploadStatus);
        array_push($results, $result);
    }
    return $results;
}

function getBasicInfo($userID)
{
    $connection = createDBConnection();
    $user = $connection->fetchAll("SELECT info FROM users WHERE id = " . "\"" . $userID . "\"", Phalcon\Db::FETCH_ASSOC);
    return $user[0]['info'];
}
?>