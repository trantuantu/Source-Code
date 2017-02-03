<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
use Phalcon\Mvc\Model;
use Phalcon\Mvc\Model\Message;
use Phalcon\Mvc\Model\Validator\Uniqueness;
use Phalcon\Mvc\Model\Validator\InclusionIn;

class activityDataModel
{
    //Add some validation code
    public $activity_id;
    public $user_id;
    public $json_activity_info;
    public $json_data;
    public $json_comments;
    public $json_kudos;
    public $json_photos;
    public $json_laps;
    public $json_upload_status;
}
