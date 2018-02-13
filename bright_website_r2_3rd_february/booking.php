<?php
require 'config/config.php';

if(isset($_POST['merchantID'])){

  $merchantID = $_POST['merchantID'];
  $userName = $_POST['userName'];

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

  $merchantUser = array();
  array_push($merchantUser, $merchantID);
  array_push($merchantUser, $userName);
  $merchantUser_data = json_encode($merchantUser);

  $str = "
        <div class='reserve_input_container'>

          <div class='booking_where' onkeyup='processReservationDate($data_json)'>
            <input placeholder='Date' class='booking_where_$merchantID' id='booking_where_input'>
          </div>

          <div class='booking_time' onkeyup='processReservationTime($data_json)'>
            <input placeholder='Arrival time' class='booking_time_$merchantID' id='booking_where_input'>
          </div>

          <div class='booking_people' onkeyup='processReservationSeats($data_json)'>
            <input placeholder='Number of seats' class='booking_seats_$merchantID' id='booking_where_input'>
          </div>

          <div class='booking_pre_order'>
          Pre-Order
          </div>

        </div>

        <div class='reserve_result_container'>

          <div class='booking_where_result' id='booking_weather_$merchantID''>

          </div>

          <div class='booking_time_result' id='booking_time_$merchantID'>
          </div>

          <div class='booking_people_result' id='booking_seats_$merchantID'>
          </div>

          </div>
        </div>

        <div class='divider_reserve'>
          <hr>
        </div>

        <div class='booking_action' onclick='processReservation($merchantUser_data)'>
        Reserve
        </div>

        ";


  echo $str;

}

 ?>
