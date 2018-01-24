<?php

$db = mysqli_connect("localhost", "root", "aloysius2", "bright");

$numOfUsers = mysqli_query($db, "SELECT username FROM users");

$userNameArray = array();

while($row = mysqli_fetch_array($numOfUsers)) {
  $userName =  $row['username'];
  array_push($userNameArray, $userName);
}

$sessionsDB = mysqli_connect("localhost", "root", "aloysius2", "zeus");

$usersName_sessions = mysqli_query($sessionsDB, "SELECT userName FROM sessions");

$usersWithSession = array();

while($row = mysqli_fetch_array($usersName_sessions)) {

  $userName =  $row['userName'];

  if(in_array($userName, $userNameArray)){
    if(!(in_array($userName, $usersWithSession))){
      array_push($usersWithSession, $userName);
    }
  }
}

for($i = 0; $i < sizeof($usersWithSession); $i++){

  $user = $usersWithSession[$i];

  $userSessionArray = array();

  $data_from_user = mysqli_query($sessionsDB, "SELECT time_input, clicked, pearson FROM sessions WHERE userName='$user'");

  if(mysqli_num_rows($data_from_user) > 0){

    $individual_user_session = array();
    $latest_time = "";
    $presentArray = $individual_user_session;

    $i = 0;
    while($row = mysqli_fetch_array($data_from_user)){

      $time = $row['time_input'];
      $clicked = $row['clicked'];
      $pearson = $row['pearson'];
      $input = "& " . $clicked . "? " . $pearson . ", ";
      $header = $user . "___START___";

      if($i == 0){
        array_push($presentArray, $header);
        array_push($presentArray, $input);
        $latest_time = $time;
      }

      if($i > 0){

        if($time - $latest_time > 30){
          $latest_time = $time;
          $newArray = array();
          array_push($userSessionArray, $presentArray);
          array_push($newArray, $input);
          $presentArray = array();
        }
        else{
          $latest_time = $time;
          array_push($presentArray, $input);
        }
      }
      $i++;
    }

    array_push($userSessionArray, $presentArray);

    $javaInput = json_encode($userSessionArray);

    $result =  shell_exec('java graph_parser '. $javaInput);

    $values_for_db = explode("***", $result);

    for($i = 0; $i < sizeof($values_for_db)-1; $i++){

      $temp = $values_for_db[$i];

      if($i == 0){
        $source = substr($temp, 0, 32);
        $destination = substr($temp, 43, 32);
        $weight = substr($temp, 93);
      }
      elseif ($i > 0) {
        $source = substr($temp, 1, 32);
        $source = str_replace("_", "", $source);
        $source = str_replace("\n", "", $source);
        $destination = substr($temp, 43, 34);
        $destination = str_replace("_", "", $destination);
        $destination = str_replace("\n", "", $destination);
        $weight = substr($temp, 95);
        $weight = str_replace("_", "", $weight);
        $weight = str_replace("\n", "", $weight);
        $weight = floatval($weight);
      }

      $date = date("Y-m-d H:i:s");

      echo $source;
      echo "_______";
      echo $destination;
      echo "________";
      echo "$weight";
      
      //$update_sessions_graph = mysqli_query($sessionsDB, "INSERT INTO session_graph VALUES('0', '$user', '$source', '$destination', $weight, '$date')") or die(mysqli_error($sessionsDB));

    }

  }
}


 ?>
