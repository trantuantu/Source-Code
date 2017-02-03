<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
require_once __DIR__."/../config.php";
require_once SITE_ROOT . '/dbConnection.php';
require_once SITE_ROOT . '/models/activityDataModel.php';

class activityData
{
    public $connection;
    function __construct() {
        $temp = new dbConnection();
        $this->connection = $temp->getConnection(); 
    }
    public function insert(activityDataModel $obj)
    {
        $this->connection->execute("INSERT INTO activity_data VALUES (\"" . $obj->activity_id . "\",\"" . $obj->user_id . "\",\"" . $obj->json_activity_info . "\",\"" . $obj->json_data . "\",\"" . $obj->json_comments . "\",\"" . $obj->json_kudos . "\",\"" . $obj->json_photos . "\",\"" . $obj->json_laps . "\",\"" . $obj->json_upload_status . "\")");
    }
    public function deleteAll($userId)
    {
        $this->connection->execute("DELETE FROM activity_data WHERE user_id = " . $userId);
    }
    public function updateId($id, $fbId)
    {
        $this->connection->execute("UPDATE activity_data SET user_id = \"" . $fbId . $id . "\" WHERE user_id = " . "\"" . $id . "\"");
    }
    public function getActivityByUser($userId)
    {
        return $this->connection->fetchAll("SELECT * FROM activity_data WHERE user_id = " . "\"" . $userId . "\"", Phalcon\Db::FETCH_NAMED);
    }
    public function getStreamData($userId, $activityId)
    {
        return $this->connection->fetchAll("SELECT json_data FROM activity_data WHERE activity_id = \"" . $activityId . "\" and user_id = \"" . $userId . "\"", Phalcon\Db::FETCH_NAMED);
    }
    public function getEscapeString($str)
    {
        return $this->connection->escapeString($str);
    }
}