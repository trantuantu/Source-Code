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

class userDataModel
{
    //Add some validation code
    public $user_id;
    public $json_stats;
    public $json_clubs;
    public $json_friends;
    public $json_followers;
    public $json_both_following;
    public $json_kom;
    public $json_starred_segments;
    public $json_facebook_friends;
}
