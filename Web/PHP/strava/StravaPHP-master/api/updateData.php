<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
if (empty($_SESSION['array'])){
        http_response_code(404);
}
else{
    $adapter = new Pest('https://www.strava.com/api/v3');
    $service = new REST($_SESSION['token'], $adapter);
    $client = new Client($service);

    //Safe update
    $_SESSION['info'] = getData($client, $_SESSION['token'], true, $_SESSION['array']["id"].$_SESSION['info']["id"]);
    $_SESSION['updateStt'] = "Updated";
}
