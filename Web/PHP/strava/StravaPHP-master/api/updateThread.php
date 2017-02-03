<?php 
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//require_once __DIR__."/../config.php";
//include SITE_ROOT . '/getData.php';
//include SITE_ROOT .  '/vendor/autoload.php';
//use Strava\API\OAuth;
//use Strava\API\Exception;
//use Strava\API\Factory;
//use Strava\API\Client;
//use Strava\API\Service\REST;
//
//class updateThread extends Threaded
//{
//    private $token;
// 
//    public function __construct($token)
//    {
//        $this->token = $token;
//    }
// 
//    public function run()
//    {
//        if (!session_id()){
//            session_start();
//        }
//        $adapter = new Pest('https://www.strava.com/api/v3');
//        $service = new REST($this->token, $adapter);
//        $client = new Client($service);
//
//        //Safe update
//        $_SESSION['info'] = getData($client, $_SESSION['token'], true, $_SESSION['array']["id"]);
//        $_SESSION['updateStt'] = "Updated";
//    }
//}