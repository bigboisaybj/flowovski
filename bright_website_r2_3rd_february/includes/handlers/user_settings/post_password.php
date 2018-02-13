<?php

$both = 0;

$passwordOne = "";
$passwordTwo = "";
$userName = "";

if(isset($_POST['userName'])){
  $userName = $_POST['userName'];
}

if(isset($_POST['passwordOne'])){
  $passwordOne = strip_tags($_POST['passwordOne']);
  $ogPassword = $passwordOne;
  $both++;
}

if(isset($_POST['passwordTwo'])){
  $passwordTwo = strip_tags($_POST['passwordTwo']);
  $both++;
}

if($both == 2){

  $passwordOne = password_hash($passwordOne, PASSWORD_DEFAULT);
  $passwordTwo = password_hash($passwordTwo, PASSWORD_DEFAULT);

  if (password_verify($ogPassword, $passwordTwo)) {
    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $updateEmail = mysqli_query($db, "UPDATE users SET password='$passwordTwo' WHERE username='$userName'");

  }

}

header("Location: ../../../index.php"); /* Redirect browser */
exit();

 ?>
