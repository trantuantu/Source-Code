<?php
class DataObject
{
    public $id;
    public $bib;
    public $name;
    public $age;
    public $age_range;
    public $gender;
    public $place_id;
    public $place;
    public $div_rank;
    public $gender_rank;
    public $overall_rank;
    public $swim;
    public $bike;
    public $run;
    public $finish;
    public $points;
}
include 'simple_html_dom.php';
use Phalcon\Db\Adapter\Pdo\Mysql;

$config = [
    'host'     => 'localhost',
    'dbname'   => 'mydb',
    'port'     => 3306,
    'username' => 'root',
    'password' => ''
];

$connection = new Mysql($config);
$link = "http://ap.ironman.com/triathlon/events/asiapac/ironman-70.3/vietnam/results.aspx?rd=20160508#axzz4OothzLUb";
$gender = array("Male", "Female");
$age_range = array();
$places_id = array();
$places = array();
$html = "";
do{
    $html = file_get_html($link);
}while ($html == false);


$res = $html->find("select#categorySelect option");
//Get age categories
foreach ($res as $key=>$value) {
    if ($value->getAttribute('value') == "90 Plus") array_push($age_range, "90+Plus");
    else if ($key != 0) array_push($age_range, $value->getAttribute('value'));
}
//Get countries
$res = $html->find("select#rankingSelect option");
foreach ($res as $key=>$value) {
    if ($key != 0)
    {
        array_push($places_id, $value->getAttribute('value'));
        array_push($places, $value->plaintext);
    }
}

$resultArray = array();
foreach ($gender as $key1=>$value1)
    foreach ($age_range as $key2=>$value2)
        foreach ($places_id as $key3=>$value3)
        {
                $templateLink = "http://ap.ironman.com/triathlon/events/asiapac/ironman-70.3/vietnam/results.aspx?race=Vietnam70.3&rd=20160508";
                if ($value1 == "Male") $templateLink = $templateLink . "&sex=M";
                else if ($value1 == "Female") $templateLink = $templateLink . "&sex=F";
                $templateLink = $templateLink . "&agegroup=" . $value2 . "&loc=" . $value3;
                $html = "";
                $html1 = "";
                do {
                    $html = file_get_html($templateLink);
                } while ($html == false);
                //Get the content
                do {
                    $res = $html->find("table#eventResults tr");
                    foreach ($res as $key4 => $value4) {
                        $dtObj = new DataObject();
                        if ($key4 > 0) {
                            $dtObj->name = $value4->children(0)->plaintext;
                            $dtObj->place_id = $value4->children(1)->plaintext;
                            $dtObj->place = $places[$key3];
                            $dtObj->div_rank = $value4->children(2)->plaintext;
                            $dtObj->gender_rank = $value4->children(3)->plaintext;
                            $dtObj->overall_rank = $value4->children(4)->plaintext;
                            $dtObj->swim = $value4->children(5)->plaintext;
                            $dtObj->bike = $value4->children(6)->plaintext;
                            $dtObj->run = $value4->children(7)->plaintext;
                            $dtObj->finish = $value4->children(8)->plaintext;
                            $dtObj->points = $value4->children(9)->plaintext;
                            if ($value1 == "Male") $dtObj->gender = "Male";
                            else if ($value1 == "Female") $dtObj->gender = "Female";
                            $dtObj->id = 1;
                            $dtObj->age_range = $value2;
                            //Get age and bid
                            $detailLink = "http://ap.ironman.com/triathlon/events/asiapac/ironman-70.3/vietnam/results.aspx";
                            $tLink = str_replace("&amp;", "&", $value4->children(0)->children(0)->getAttribute("href"));
                            $detailLink = $detailLink . $tLink;
                            do {
                                $html1 = file_get_html($detailLink);
                            } while ($html1 == false);
                            $detailRows = $html1->find("table#general-info tr");
                            $bib = $detailRows[1]->children(1)->plaintext;
                            $age = $detailRows[3]->children(1)->plaintext;
                            //-----------------------------
                            $dtObj->bib = $bib;
                            $dtObj->age = $age;

                            $queryString = "INSERT INTO raw_data VALUES (\"" . $dtObj->id . "\",\"" . $dtObj->bib . "\",\"" . $dtObj->name . "\",\"" . $dtObj->age . "\",\"" . $dtObj->age_range . "\",\"" . $dtObj->gender . "\",\"" . $dtObj->place_id . "\",\"" . $dtObj->place . "\",\"" . $dtObj->div_rank . "\",\"" . $dtObj->gender_rank . "\",\"" . $dtObj->overall_rank . "\",\"" . $dtObj->swim . "\",\"" . $dtObj->bike . "\",\"" . $dtObj->run . "\",\"" . $dtObj->finish . "\",\"" . $dtObj->points . "\")";
                            try {


                                $success = $connection->execute($queryString);
                            } catch (Exception $e) {
                                echo 'Caught exception: ',  $e->getMessage(), "\n";
                            }

                            array_push($resultArray, $dtObj);
                        }
                    }
                    $nextPageLink = '';
                    $nextPageLinkArr = $html->find("a.nextPage");
                    if (count($nextPageLinkArr) != 0) {
                        $nextPageLink = str_replace("&amp;", "&", $nextPageLinkArr[0]->getAttribute('href'));
                        $html = file_get_html($nextPageLink);
                    }
                } while ($nextPageLink != '');
        }
?>