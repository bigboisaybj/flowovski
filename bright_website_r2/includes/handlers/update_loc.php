<?php

$db = mysqli_connect("localhost", "root", "aloysius2", "bright");

$lat = "";
$long = "";
$userName = "";

if(isset($_POST['lat'])){

  $lat = $_POST['lat'];
  $long = $_POST['long'];
  $userName = $_POST['username'];

  $updateUserLong = mysqli_query($db, "UPDATE users SET current_longitude='$long' WHERE username='$userName'");
  $updateUserLat = mysqli_query($db, "UPDATE users SET current_latitude='$lat' WHERE username='$userName'");

}

?>
