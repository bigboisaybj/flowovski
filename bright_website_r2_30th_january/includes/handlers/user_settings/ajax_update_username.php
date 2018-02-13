<?php

$db = mysqli_connect("localhost", "root", "aloysius2", "bright");

$goodResponse = 0;

if(isset($_POST['userName'])){

  $newName = $_POST['userName'];

  echo "NewG " . $newName;

  $originalUser = $_POST['originalUser'];

  echo "OG: " . $originalUser;

  $names = explode(" ", $newName);

  if(sizeof($names) == 2){

    $userFirst = $names[0];

    $userSecond = $names[1];

    $updateScreenName = mysqli_query($db, "UPDATE users SET first_name='$userFirst', last_name='$userSecond' WHERE username='$originalUser'");

    $result = mysqli_query($updateScreenName) or trigger_error("Query Failed! SQL: $updateScreenName - Error: ".mysqli_error(), E_USER_ERROR);

    $goodResponse = 1;

    echo $result;

  }

  if(sizeof($names) == 1 || sizeof($names) > 2){

    $secondDull = " ";

    $updateScreenName = mysqli_query($db, "UPDATE users SET first_name='$newName', last_name='$secondDull' WHERE username='$originalUser'");

    $result = mysqli_query($updateScreenName) or trigger_error("Query Failed! SQL: $updateScreenName - Error: ".mysqli_error(), E_USER_ERROR);

    $goodResponse = 1;

    echo $result;
  }
}


 ?>
