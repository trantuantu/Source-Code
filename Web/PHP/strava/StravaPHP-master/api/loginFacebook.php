<?php
/**
 * Created by PhpStorm.
 * User: Tuan Tu
 * Date: 12/6/2016
 * Time: 11:15 AM
 */
require_once __DIR__."/../config.php";
include SITE_ROOT . '/vendor/autoload.php';

if (!session_id()){
    session_start();
}

if (!empty($_SESSION['array']))
{
    echo "Authenticated";
}
else {
    if (empty($_SESSION['token'])){
    http_response_code(404);
    }
    else{
        $permissions = ['email', 'user_friends'];
        $fb = new Facebook\Facebook([
            'app_id' => '601200633406434',
            'app_secret' => '4431d4ba66da7e30b75847be8d009f2c',
            'default_graph_version' => 'v2.8',
        ]);
        $helper = $fb->getRedirectLoginHelper();
        $loginUrl = $helper->getLoginUrl('http://localhost:8080/api/loginFacebook.php', $permissions);
        $verb = $_SERVER['REQUEST_METHOD'];
        
        if($verb == 'GET'){
            if(isset($_GET['token']))
            {
                $parameters = $_SERVER['QUERY_STRING'];
                parse_str($parameters, $output);
                $token = $output['token'];
            }
        }

        if (!isset($_GET['code'])) {
             echo(json_encode(array("authLink" => $loginUrl)));
        } else { 
            $helper = $fb->getRedirectLoginHelper();
            try {
                $accessToken = $helper->getAccessToken();
            } catch (Facebook\Exceptions\FacebookResponseException $e) {
                // When Graph returns an error
                echo 'Graph returned an error: ' . $e->getMessage();
                exit;
            } catch (Facebook\Exceptions\FacebookSDKException $e) {
                // When validation fails or other local issues
                echo 'Facebook SDK returned an error: ' . $e->getMessage();
                exit;
            }
            
            if (isset($accessToken)) {
                $oAuth2Client = $fb->getOAuth2Client();
                $longLivedAccessToken = $oAuth2Client->getLongLivedAccessToken($accessToken);
                $_SESSION['facebook_access_token'] = (string)$longLivedAccessToken;
                $fb->setDefaultAccessToken($_SESSION['facebook_access_token']);
                $res = $fb->get('/me', $longLivedAccessToken);
                $me = $res->getGraphUser()->getId(); 

                $response = $fb->get('/me/friends?limit=5000', $longLivedAccessToken, null, "v2.8");
                $graphEdge = $response->getGraphEdge()->asArray();
                $resJSON = array("id" => "", "friends" => "");
                $resJSON["id"] = $me;
                $resJSON["friends"] = $graphEdge;
                $_SESSION['array'] = array();
                $_SESSION['array'] = $resJSON;

                header("LOCATION: http://localhost:8080/public/html/dashboard.html");
            }
        }
    }
}

  

