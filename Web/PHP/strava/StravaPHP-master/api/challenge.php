<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//STEP 2: Challenge.php will store infomation about challengeId and userId and redirect to loginStrava for user authentication (see STEP 3 in dashboard.php)
if (!empty($_SESSION['array']))
{
    echo "Authenticated";
}
else {
    if (empty($_SESSION['token'])){
    http_response_code(404);
    }
    else{
        $verb = $_SERVER['REQUEST_METHOD'];

        if($verb == 'GET'){
            if(isset($_GET['token']))
            {
                $parameters = $_SERVER['QUERY_STRING'];
                parse_str($parameters, $output);
                $_SESSION['id'] = $output['id'];
                $_SESSION['user_id'] = $output['user_id'];
                //TODO: Redirect to acceptPage.html (you can code acceptPage.html) to ask user whether or not accept the challenge before redirect to loginStrava.html.
                header("LOCATION: http://localhost:8080/public/html/loginStrava.html");
            }
        }
    }
}