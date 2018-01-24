<?php

$result = file_get_contents("/Users/bryanjordan/Sites/server_upload/crawler_nlp_results/five.txt");

$hashId = strrpos($result, "HASH_ID");
$crawlerPos = strrpos($result, "TEXT_FROM_CRAWLER");
$tagsPos = strrpos($result, "TEXT_POG_TAGS");
$synonymPos = strrpos($result, "TEXT_SINGLE_SYNONYMS");

$hashId = substr($result, $hashId+9, $crawlerPos-11);

$crawler_temp = substr($result, $crawlerPos);
$secondCheck_crawler = strrpos($crawler_temp, "TEXT_POG_TAGS");
$crawlerText = substr($result, $crawlerPos, $secondCheck_crawler);
$crawlerText = substr($crawlerText, 17);

$pogsText = substr($result, $tagsPos);
$secondCheckSynonyms = strrpos($pogsText, "TEXT_SINGLE_SYNONYMS");
$pogsText = substr($pogsText, 0, $secondCheckSynonyms);
$pogsText = substr($pogsText, 13);

$synonymText = substr($result, $synonymPos, $synonymPos-1);
$synonymText = substr($synonymText, 20);

if($hashId && $crawlerText && $pogsText && $synonymText){

  $con = mysqli_connect("localhost", "root", "aloysius2", "zeus");

  if(mysqli_connect_errno())
  {
  	echo "Failed to connect: " . mysqli_connect_errno();
  }

  $query = mysqli_query($con, "INSERT INTO pembroke VALUES ('0', '$hashId', '$crawlerText', '$pogsText', '$synonymText')");

  if($query){
    echo "Upload successful";
  }
  else{
    echo "Upload failure";
  }


}



 ?>
