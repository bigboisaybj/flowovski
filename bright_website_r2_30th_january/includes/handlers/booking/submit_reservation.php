<?php

if(isset($_POST['userName'])){

  $userName = $_POST['userName'];
  $merchantID = $_POST['merchantID'];
  $dateReservation = $_POST['date'];
  $timeReservation = $_POST['time'];
  $seats = $_POST['seats'];

  $seats = intval($seats);

  $merchantName = "";

  $con = mysqli_connect("localhost", "root", "aloysius2", "bright");

  $merchantName_query = mysqli_query($con, "SELECT merchant_name FROM merchants WHERE id='$merchantID'");

  if(mysqli_num_rows($merchantName_query) > 0){

    $merchant_name_data = mysqli_fetch_array($merchantName_query);

    $merchantName = $merchant_name_data['merchant_name'];
  }

  $date = date("Y-m-d H:i:s");

  $insertReservation = mysqli_query($con, "INSERT INTO reservations VALUES('0', '$merchantName', '$date', '$dateReservation', '$timeReservation', $seats, '$userName')") or die(mysqli_error($con));

  if($insertReservation){
    echo "Reservation placed!";
  }
  else{
    echo "Fail whale";
  }
}


 ?>
