<?php
require_once __DIR__."/../config.php";
include SITE_ROOT . '/controller/users.php';
include SITE_ROOT . '/controller/activityData.php';

if (!session_id()){
    session_start();
}
if (empty($_SESSION['array'])){
    http_response_code(404);
}
else{
    $users = new users();
    $activityData = new activityData();
    
    $verb = $_SERVER['REQUEST_METHOD'];
    if($verb == 'GET'){
        if(isset($_GET['activity_id']) && isset($_GET['user_id'])){
            $parameters = $_SERVER['QUERY_STRING'];
            parse_str($parameters, $output);
            $arr_activity_id = explode(',', $output['activity_id']);
            $arr_user_id = explode(',', $output['user_id']);
            $res = array();
            
            for($i = 0; $i < count($arr_user_id); $i++){
                $result = $activityData->getStreamData($arr_user_id[$i], $arr_activity_id[$i]);
                $result[0]['json_data'] = trim( $result[0]['json_data'],"'");
                $json = json_decode($result[0]['json_data'],true);
                $lat = '{"lat": ';
                $lng = ',"lng": ';
                $strdata = "";
                $strtempt = "";
              
                if (is_array($json[0]['data']) || is_object($json[0]['data'])){
                    foreach( $json[0]['data'] as $dataitem){
                        //echo $dataitem[0];
                        $strtempt = $lat.$dataitem[0].$lng.$dataitem[1]."},";
                        $strdata = $strdata.$strtempt;
                    }
                }
                
                $strdata = substr($strdata, 0, strlen($strdata) - 1);
                $obj = new stdClass();
                $obj->id = $arr_user_id[$i];
                $resUser = $users->getUserByFbId('(' . $arr_user_id[$i] . ')');
                $obj->name = $resUser[0]["name"];
                $obj->profile_image = json_decode(str_replace("'", '', $resUser[0]["info"]), true)["profile_medium"];
                $obj->data = "[".$strdata."]"; 
                array_push($res, $obj);
            }
            
            $_SESSION['mapData'] = json_encode($res);
           
        }  else {
            die("Error: Request parameters not given!"); 
        }
    }
}