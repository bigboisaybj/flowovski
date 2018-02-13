<?php

if(isset($_POST['time'])){

  $time = $_POST['time'];
  $date = $_POST['date'];
  $merchantID = $_POST['merchantID'];

  $merchantName = "";

  $result = "";

  $con = mysqli_connect("localhost", "root", "aloysius2", "bright");

  $merchantName_query = mysqli_query($con, "SELECT merchant_name FROM merchants WHERE id='$merchantID'");

  if(mysqli_num_rows($merchantName_query) > 0){

    $merchant_name_data = mysqli_fetch_array($merchantName_query);

    $merchantName = $merchant_name_data['merchant_name'];
  }

  $check_availability_query = mysqli_query($con, "SELECT `id` FROM reservations WHERE `date_reservation`='$date' AND `time_reservation`='$time' AND `merchantName`='$merchantName'");

  $count_availability = 0;

  if(mysqli_num_rows($check_availability_query) > 0){

    while($row = mysqli_fetch_array($check_availability_query)){

      $count_availability++;

      $result = "Available";

      //Check merchant capacity. + A lot of other things. Specific timing.
    }
  }

  if(mysqli_num_rows($check_availability_query) == 0){

    $result = "Available";

  }

  echo $result;

}


 ?>
