<?php
require_once __DIR__."/../config.php";
include SITE_ROOT . '/getData.php';
include SITE_ROOT .  '/vendor/autoload.php';
use Strava\API\OAuth;
use Strava\API\Exception;
use Strava\API\Factory;
use Strava\API\Client;
use Strava\API\Service\REST;


if (!session_id()){
            session_start();
}   
if (!empty($_SESSION['array']))
{
    echo "Authenticated";
}
else
{
    try {
        $options = array(
            'clientId'     => 14746,
            'clientSecret' => 'e8e40034ab469b65abaf013d9ea06da3d319828e',
            'redirectUri'  => 'http://localhost:8080/api/main.php'
        );
        $oauth = new OAuth($options);
        
        if (!isset($_GET['code'])){
            echo(json_encode(array("authLink" => $oauth->getAuthorizationUrl())));
        } else {
            $token = $oauth->getAccessToken('authorization_code', array(
                'code' => $_GET['code']
            ));
            try {
                //Initial
                $adapter = new Pest('https://www.strava.com/api/v3');
                $service = new REST($token, $adapter);
                $client = new Client($service);      
                $_SESSION['info'] = getData($client, $token, false, "");
                $_SESSION['token'] = $token;
                $_SESSION["updateStt"] = "";
                
                
                header("LOCATION: http://localhost:8080/public/html/loginFacebook.html");
            } catch(Exception $e) {
            }
        }
    } catch(Exception $e) {
    }
}
?>