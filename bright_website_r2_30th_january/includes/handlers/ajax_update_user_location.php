<?php

$db = mysqli_connect("localhost", "root", "aloysius2", "bright");

if(isset($_POST['userName'])){

  $userName = $_POST['userName'];
  $city = $_POST['city'];
  $country = $_POST['country'];
  $county = $_POST['county'];
  $postcode = $_POST['postcode'];
  $state = $_POST['state'];
  $suburb = $_POST['suburb'];

  $date = date("Y-m-d H:i:s");

  $updateTime = mysqli_query($db, "UPDATE users SET current_city='$city', current_country='$country', current_county='$county', current_postcode='$postcode', current_state='$state', current_suburb='$suburb', last_location_update='$date' WHERE username='$userName'");

  $result = mysqli_query($updateTime) or trigger_error("Query Failed! SQL: $updateTime - Error: ".mysqli_error(), E_USER_ERROR);

}


?>
