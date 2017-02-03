<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
require_once __DIR__."/../config.php";
require_once SITE_ROOT . '/models/usersModel.php';
require_once SITE_ROOT . '/dbConnection.php';

class users
{
    public $connection;
    function __construct() {
        $temp = new dbConnection();
        $this->connection = $temp->getConnection(); 
    }
    public function insert(usersModel $obj)
    {
      $this->connection->execute("INSERT INTO users VALUES (\"" . $obj->id . "\",\"" . $obj->name . "\",\"" . $obj->info . "\",\"" . $obj->created_at . "\",\"" . $obj->strava_id . "\",\"" . $obj->access_token . "\")"); 
    }
    public function getUser($id)
    {
        return $this->connection->fetchAll("SELECT * FROM users WHERE strava_id = " . "\"" . $id . "\"", Phalcon\Db::FETCH_ASSOC);
    }
    public function update()
    {
        
    }
    public function getUserByFbId($ids)
    {
        return $this->connection->fetchAll("SELECT * FROM users WHERE id IN" . $ids, Phalcon\Db::FETCH_ASSOC);
    }
    public function getUserByStravaId($ids)
    {
        return $this->connection->fetchAll("SELECT * FROM users WHERE strava_id IN " . $ids, Phalcon\Db::FETCH_ASSOC);
    }
    public function getStravaId($arrFbId)
    {
        return $this->connection->fetchAll("SELECT strava_id FROM users WHERE id IN " . $arrFbId, Phalcon\Db::FETCH_ASSOC);
    }
    public function getFbId($stravaId)
    {
        return $this->connection->fetchAll("SELECT id FROM users WHERE strava_id IN " . $stravaId, Phalcon\Db::FETCH_ASSOC);
    }
    public function updateId($id, $fbId)
    {
        $this->connection->execute("UPDATE users SET id = \"" . $fbId . $id . "\" WHERE id = " . "\"" . $id . "\"");
    }
    public function getEscapeString($str)
    {
        return $this->connection->escapeString($str);
    }
    
    public function deleteAll($userId)
    {
        $this->connection->execute("DELETE FROM users WHERE id = " . $userId);
    }
}