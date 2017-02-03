<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
use Phalcon\Db\Adapter\Pdo\Mysql;
class dbConnection
{
    public function getConnection()
    {
        $config = [
            'host' => 'localhost',
            'dbname'   => 'strava',
            'port'     => 3306,
            'username' => 'root',
            'password' => '',
            'charset'   =>'utf8'
        ];
        return new Mysql($config);
    }
}
