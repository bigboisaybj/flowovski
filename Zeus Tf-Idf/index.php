<?php

$result = file_get_contents("/Users/bryanjordan/Sites/server_upload/total_tf_idf/total_tf_idf_results.txt");

$hashesPos = strrpos($result, "__CONTENT__");

$resultList = substr($result, $hashesPos+11);

$listOfResultsPlz = explode(')', $resultList);

$hashesPos = strrpos($result, "__CONTENT__");

$hashes = substr($result, 0, $hashesPos);

$hashesList = str_split($hashes);

$lengthHashes = sizeof($hashesList);

$hashes_results = array();

for($i = 0; $i < $lengthHashes; $i++){
  if($hashesList[$i] == " "){
    $temp = $i;
    array_push($hashes_results, $temp);
  }
}

$lengthArray = sizeof($hashes_results);
$individual_hashes = array();

for($x = 0; $x < $lengthArray; $x++) {
    if($x < 1){
      $temp = substr($hashes, 0, $hashes_results[$x]);
      array_push($individual_hashes, $temp);
    }

    if($x >= 0){
      $temp = substr($hashes, $hashes_results[$x], 33);
      array_push($individual_hashes, $temp);
    }
}

//Check if all values are there -> columns/rows
$con = mysqli_connect("localhost", "root", "aloysius2", "zeus");

$size = sizeof($listOfResultsPlz);

for($i = 0; $i < $size; $i++){
  $temp = $listOfResultsPlz[$i];
  $charSplit = str_split($temp);
  $tempSize = sizeof($charSplit);

  $value = "";
  $valuePos = 0;
  $x_co = "";
  $xPos = 0;
  $y_co = "";
  $yPost = 0;
  $tempContainer = "";

  for($y = 0; $y < $tempSize; $y++){

    if($charSplit[$y] == ','){

      $valuePos = $y;
      $value = substr($temp, 1, $valuePos);
      $newValue = str_replace('(', '', $value);
      $newValue = str_replace(',', '', $newValue);
      $value = $newValue;
      break;
    }

  }

  if($value != ""){

    if($value)
    $x_copos = substr($temp, $valuePos+3, 32);
    $tempContainer = substr($temp, $valuePos+3);
    $x_copos = str_replace('\'', '', $x_copos);
    $x_copos = str_replace('(', '', $x_copos);
    $x_copos = str_replace(',', '', $x_copos);

    $lengthContainer = strlen($tempContainer);

    for($t = 0; $t < $lengthContainer; $t++){

      if($tempContainer[$t] == ','){
        $tempContainer = substr($tempContainer, $t, 35);
        $tempContainer = str_replace('\'', '', $tempContainer);
        $tempContainer = str_replace('(', '', $tempContainer);
        $y_copos = str_replace(',', '', $tempContainer);
      }
    }

  }

  if($value != "" && $x_copos != "" && $y_copos != ""){

    $value = trim($value);
    $x_copos = trim($x_copos);
    $y_copos = trim($y_copos);

    $update_tf_idf = mysqli_query($con, "UPDATE tf_idf_results SET `$x_copos`='$value' WHERE hashID='$y_copos'");


  }

}


?>
