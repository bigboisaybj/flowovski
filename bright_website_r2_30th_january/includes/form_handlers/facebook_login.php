<?php

if(isset($_POST['email'])) {

  session_start();

  echo "OPENED";

  $email = $_POST['email'];
  $con = mysqli_connect("localhost", "root", "aloysius2", "bright");

  $check_database_query = mysqli_query($con, "SELECT * FROM users WHERE email='$email'") or die(mysqli_error($con));
  $check_login_query = mysqli_num_rows($check_database_query);

  if(mysqli_num_rows($check_database_query) == 1) {

    $row = mysqli_fetch_array($check_database_query);
    $username = $row['username'];

    $_SESSION['username'] = $username;

    /*
    $_SESSION['username'] = $username;
    header("Location: ../../index.php");
    exit();
    */
  }

  if(mysqli_num_rows($check_database_query) != 1) {

    $dob = $_POST['birthday'];
    $first_name = $_POST['first_name'];
    $last_name = $_POST['last_name'];
    $profile_pic = $_POST['photo'];
    $username = $first_name . "_" . $last_name;

    $dob_array = explode("/", $dob);
    $month = $dob_array[0];
    $date = $dob_array[1];
    $year = $dob_array[2];

    $upload_date = date("Y-m-d H:i:s");

    $formatted_birthday = $year . "-" . $month . "-" . $date;

    $check_username_query = mysqli_query($con, "SELECT username FROM users WHERE username='$username'");

    $num = mysqli_num_rows($check_username_query);
    $num++;

    $username = $username . "_" . $num;

    $insert_users = mysqli_query($con, "INSERT INTO users (id, first_name, last_name, username, email, mobile, dob, signup_date, profile_pic ) VALUES ('0', '$first_name', '$last_name', '$username', '$email', 'mobileTest', '$formatted_birthday', '$upload_date', '$profile_pic')") or die(mysqli_error($con));
    //$_SESSION['username'] = "bryan_jordan";
    $_SESSION['log_email'] = $email;

  }
  else {
    array_push($error_array, "Email or password was incorrect<br>");
  }
}


 ?>
