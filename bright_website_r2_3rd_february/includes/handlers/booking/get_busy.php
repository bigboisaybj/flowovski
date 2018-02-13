<?php

if(isset($_POST['time'])){

  $con = mysqli_connect("localhost", "root", "aloysius2", "bright");

  $merchantID = $_POST['id'];

  $merchantName_query = mysqli_query($con, "SELECT merchant_name FROM merchants WHERE id='$merchantID'");

  $merchantName_result = "";

  if(mysqli_num_rows($merchantName_query) > 0){

    $merchantName = mysqli_fetch_array($merchantName_query);

    $merchantName_result = $merchantName['merchant_name'];

  }

  $time = $_POST['time'];
  $day = $_POST['day'];

  $day_title = "waiting_time_".$day;

  $time_query = mysqli_query($con, "SELECT `$time` FROM $day_title WHERE merchant_name='$merchantName_result'") or die(mysqli_error($con));

  if(mysqli_num_rows($time_query) > 0){

    $time_data = mysqli_fetch_array($time_query);

    $result = $time_data[$time];

    if($result == "TBC"){

      $mean_query = mysqli_query($con, "SELECT `mean` FROM $day_title WHERE merchant_name='$merchantName_result'") or die(mysqli_error($con));

      if(mysqli_num_rows($mean_query) > 0){

        $mean_data = mysqli_fetch_array($mean_query);

        $result = $mean_data['mean'];

        echo "Expected wait of " . round($result, 0) . " minutes";
      }
    }
    else{
      echo "Expected wait of " . round($result, 0) . " minutes";
    }

  }

  else{
    echo "FAIL";
  }
}

 ?>
