<?php
$html = "";
$nextPageLink = "http://ap.ironman.com/triathlon/events/asiapac/ironman-70.3/vietnam/results.aspx?rd=20160508#axzz4OothzLUb";
do{
    do {
        $html = file_get_html($nextPageLink);
    }while ($html == false);
    $nextPageLink = '';
    $nextPageLinkArr = $html->find("a.nextPage");
    if (count($nextPageLinkArr) != 0)
        $nextPageLink = $nextPageLinkArr[0].getAttribute('href');
}
while ($nextPageLink != '');
?>