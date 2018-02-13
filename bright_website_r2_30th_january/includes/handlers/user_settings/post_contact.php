<?php

$db = mysqli_connect("localhost", "root", "aloysius2", "bright");

$goodEmail = 0;
$goodPhone = 0;
$userName = "";

if(isset($_POST['userName'])){
  $userName = $_POST['userName'];
}

if(isset($_POST['email'])){

  $email = $_POST['email'];

  if(filter_var($email, FILTER_VALIDATE_EMAIL)) {

    $email = filter_var($email, FILTER_VALIDATE_EMAIL);

    $email_check = mysqli_query($db, "SELECT email from users WHERE email='$email'");

    $num_rows = mysqli_num_rows($email_check);

    if ($num_rows == 0) {
      $goodEmail = 1;
    }

  }


  if($goodEmail == 1){

    $updateEmail = mysqli_query($db, "UPDATE users SET email='$email' WHERE username='$userName'");

  }

}

if(isset($_POST['phone'])){

  $phone = $_POST['phone'];

  if(ctype_digit($phone)){

    $phone_check = mysqli_query($db, "SELECT mobile from users WHERE mobile='$phone'");

    $num_rows = mysqli_num_rows($phone_check);

    if ($num_rows == 0) {
      $goodPhone = 1;
    }


  }

  if($goodPhone == 1){

    $updatePhone = mysqli_query($db, "UPDATE users SET mobile='$phone' WHERE username='$userName'");

  }

}

header("Location: ../../../index.php"); /* Redirect browser */
exit();

 ?>
