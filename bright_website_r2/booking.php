<?php
require 'config/config.php';

if(isset($_POST['merchantID'])){

  $merchantID = $_POST['merchantID'];

  $get_coorindates_query = mysqli_query($con, "SELECT longitude, latitude FROM merchants WHERE id='$merchantID'");

  $longitude = 0.0;
  $latitude = 0.0;

  if(mysqli_num_rows($get_coorindates_query) > 0){

    $get_coors_data = mysqli_fetch_array($get_coorindates_query);

    $longitude = $get_coors_data['longitude'];
    $latitude = $get_coors_data['latitude'];
  }

  $data = array();

  array_push($data, $merchantID);
  array_push($data, $longitude);
  array_push($data, $latitude);

  $data_json = json_encode($data);

  //!!!!!SWITCH TIME & DATE!!!!! -> NEED TO HAVE DATE = 'NEXT MONDAY -> AUTOCOMPLETE'

  $str = "
        <div class='reserve_input_container'>

          <div class='booking_where'>
            <input placeholder='Date' class='booking_where_$merchantID' id='booking_where_input'>
          </div>

          <div class='booking_time' onkeyup='processReservationTime($data_json)'>
            <input placeholder='Time' class='booking_time_$merchantID' id='booking_where_input'>
          </div>

          <div class='booking_people'>
            <input placeholder='How many seats' class='booking_time_$merchantID' id='booking_where_input'>
          </div>

        </div>

        <div class='reserve_result_container'>

          <div class='booking_where_result' id='booking_weather_$merchantID''>
            Weather report
          </div>

          <div class='booking_time_result'>
            Busy report
          </div>

          <div class='booking_people_result'>
            Seated near
          </div>

          </div>
        </div>

        <div class='booking_action'>
        Pre-order - Reserve
        </div>

        ";


  echo $str;

}

 ?>
