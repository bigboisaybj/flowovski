<?php

$db = mysqli_connect("localhost", "root", "aloysius2", "bright");

if(isset($_POST['day'])){

  $tempDay = mysqli_real_escape_string($db, $_POST['day']);
  $tempTime = mysqli_real_escape_string($db, $_POST['orderTime']);
  $tempUser = mysqli_real_escape_string($db, $_POST['username']);
  $tempMerchant = mysqli_real_escape_string($db, $_POST['merchantName']);
  $tempProduct = mysqli_real_escape_string($db, $_POST['productName']);
  $tempPrice = mysqli_real_escape_string($db, $_POST['itemPrice']);
  $tempLat = mysqli_real_escape_string($db, $_POST['latitude']);
  $tempLong = mysqli_real_escape_string($db, $_POST['longitude']);
  $tempExtras = mysqli_real_escape_string($db, $_POST['extras']);

  $orderInput = mysqli_query($db, "INSERT INTO sales VALUES ('0', '$tempDay', '$tempTime', '$tempUser', '$tempMerchant', '$tempProduct', '$tempPrice', '$tempLat', '$tempLong', '$tempExtras' ,'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC')");


}

?>
