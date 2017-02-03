<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
require_once __DIR__."/../config.php";
require_once SITE_ROOT . '/dbConnection.php';
require_once SITE_ROOT . '/models/userDataModel.php';

class userData
{
    public $connection;
    function __construct() {
       $temp = new dbConnection();
       $this->connection = $temp->getConnection(); 
    }
    public function insert(userDataModel $obj)
    {
      $this->connection->execute("INSERT INTO user_data VALUES (\"" . $obj->user_id . "\",\"" . $obj->json_stats . "\",\"" . $obj->json_clubs . "\",\"" . $obj->json_friends . "\",\"" . $obj->json_followers . "\",\"" . $obj->json_both_following .  "\",\"" .$obj->json_kom . "\",\"" .$obj->json_starred_segments . "\",\"[]" . "\")");
    }
    public function getUserData($id)
    {
    }
    public function getFacebookFriends($userId)
    {
        return $this->connection->fetchAll("SELECT json_facebook_friends FROM user_data WHERE user_id = " . "\"" . $userId . "\"", Phalcon\Db::FETCH_NAMED);
    }
    public function updateFacebookFriends($id, $data)
    {
        $this->connection->execute("UPDATE user_data SET json_facebook_friends = \"" . $data . "\" WHERE user_id = " . "\"" . $id . "\"");
    }
    public function updateId($id, $fbId)
    {
        $this->connection->execute("UPDATE user_data SET user_id = \"" . $fbId . $id ."\" WHERE user_id = " . "\"" . $id . "\"");
    }
    public function getEscapeString($str)
    {
        return $this->connection->escapeString($str);
    }
    public function deleteAll($userId)
    {
        $this->connection->execute("DELETE FROM user_data WHERE user_id = " . $userId);
    }
}