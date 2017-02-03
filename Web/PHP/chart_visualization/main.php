<?php
use Phalcon\Db;
use Phalcon\Db\Adapter\Pdo\Mysql;

function convertToSeconds($v)
{
    $arr = explode(':', $v);
    return intval($arr[0]) * 3600 + intval($arr[1]) * 60 + intval($arr[2]);
}

$config = [
    'host'     => 'localhost',
    'dbname'   => 'live_score',
    'port'     => 3306,
    'username' => 'root',
    'password' => ''
];
$connection = new Mysql($config);

$method = $_SERVER['REQUEST_METHOD'];
$parameters = $_SERVER['QUERY_STRING'];
parse_str($parameters, $output);
$arr = explode(',', $output['bib']);
$id = $output['id'];


if ($id == 4)
{
    $resultArr1 = array();
    $resultArr2 = array();
    $resultArr3 = array();
    $jsonArr = array();
    $params1 = array();
    $params2 = array();
    $params3 = array();

    $char1 = array("chartType" => "ColumnChart", "title" => "Time", "params" => "", "data" => "");
    $char2 = array("chartType" => "ColumnChart", "title" => "Rank", "params" => "", "data" => "");
    $char3 = array("chartType" => "ColumnChart", "title" => "Point", "params" => "", "data" => "");

    array_push($params1, "Sport");
    array_push($params2, "Rank");
    array_push($params3, "Point");

    $temp = array();
    array_push($temp, "Div Rank");
    array_push($resultArr2, $temp);
    $temp = array();
    array_push($temp, "Gender Rank");
    array_push($resultArr2, $temp);
    $temp = array();
    array_push($temp, "Overall Rank");
    array_push($resultArr2, $temp);

    $temp = array();
    array_push($temp, "Swim");
    array_push($resultArr1, $temp);
    $temp = array();
    array_push($temp, "Bike");
    array_push($resultArr1, $temp);
    $temp = array();
    array_push($temp, "Run");
    array_push($resultArr1, $temp);
    $temp = array();
    array_push($temp, "Finish");
    array_push($resultArr1, $temp);

    $temp = array();
    array_push($temp, "Points");
    array_push($resultArr3, $temp);

    for ($i = 0; $i < count($arr); $i++)
    {
        $sqlCmd = "SELECT * FROM raw_data WHERE id = '" . $id . "'and bib = '" . $arr[$i] . "'";
        $result = $connection->query($sqlCmd);
        $result->setFetchMode(Db::FETCH_NAMED);

        while ($record = $result->fetch()) {
            array_push($params1, $record['name']);
            array_push($params2, $record['name']);
            array_push($params3, $record['name']);

            array_push($resultArr1[0], convertToSeconds($record['time_swim']));
            array_push($resultArr1[1], convertToSeconds($record['time_bike']));
            array_push($resultArr1[2], convertToSeconds($record['time_run']));
            array_push($resultArr1[3], convertToSeconds($record['finish']));

            array_push($resultArr2[0], $record['div_rank']);
            array_push($resultArr2[1], $record['gender_rank']);
            array_push($resultArr2[2], $record['overall_rank']);

            array_push($resultArr3[0], $record['points']);
        }
    }
    $char1["params"] = $params1;
    $char2["params"] = $params2;
    $char3["params"] = $params3;

    $char1["data"] = $resultArr1;
    $char2["data"] = $resultArr2;
    $char3["data"] = $resultArr3;

    array_push($jsonArr, $char1);
    array_push($jsonArr, $char2);
    array_push($jsonArr, $char3);
    echo json_encode($jsonArr);

}else if ($id == 2)
{

}


?>