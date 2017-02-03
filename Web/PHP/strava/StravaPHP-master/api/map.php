<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
if (!session_id()){
        session_start();
}
if (empty($_SESSION['array'])){
        http_response_code(404);
}
else{
    $res =  $_SESSION['mapData'];
    echo ($res);
}
