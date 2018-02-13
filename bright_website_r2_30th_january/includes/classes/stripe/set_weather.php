<?php

$db = mysqli_connect("localhost", "root", "aloysius2", "bright");

$timeStamp = "";
$cloud = "";
$text_description = "";
$feels_like_c = "";
$humidity = "";
$precip_mm = "";
$pressure_mb = "";
$temp_c = "";
$vis_km = "";
$wind_dir = "";
$wind_kph = "";
$localTime = "";

if(isset($_POST['timeStamp'])) {

  $timeStamp = $_POST['timeStamp'];
  $cloud = $_POST['cloud'];
  $text_description = $_POST['text_description'];
  $feels_like_c = $_POST['feels_like_c'];
  $humidity = $_POST['humidity'];
  $precip_mm = $_POST['precip_mm'];
  $pressure_mb = $_POST['pressure_mb'];
  $temp_c = $_POST['temp_c'];
  $vis_km = $_POST['vis_km'];
  $wind_dir = $_POST['wind_dir'];
  $wind_kph = $_POST['wind_kph'];
  $localTime = $_POST['localTime'];

  $update_Weather = mysqli_query($db, "UPDATE sales SET cloud='$cloud', weather_description='$text_description', feels_like_c='$feels_like_c', humidity='$humidity', precip_mm='$precip_mm', pressure_mb='$pressure_mb', temp_c='$temp_c', vis_km='$vis_km', wind_dir='$wind_dir', wind_kph='$wind_kph', local_time='$localTime' WHERE time_placed='$timeStamp'");

}


 ?>

 
