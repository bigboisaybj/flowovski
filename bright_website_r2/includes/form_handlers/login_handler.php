<?php

if(isset($_POST['login_button'])) {

  $email = filter_var($_POST['log_email'], FILTER_SANITIZE_EMAIL);

  $_SESSION['log_email'] = $email;
  $password = $_POST['log_password'];
  $password = strip_tags($_POST['log_password']); //remove HTML tags

  $check_database_query = mysqli_query($con, "SELECT * FROM users WHERE email='$email'");
  $check_login_query = mysqli_num_rows($check_database_query);

  if($check_login_query == 1) {

    $row = mysqli_fetch_array($check_database_query);
    $username = $row['username'];
    $dbPassword = $row['password'];

    if(password_verify($password, $dbPassword)) {
      $_SESSION['username'] = $username;
      header("Location: index.php");
      exit();
    }

  }
  else {
    array_push($error_array, "Email or password was incorrect<br>");
  }
}

 ?>
