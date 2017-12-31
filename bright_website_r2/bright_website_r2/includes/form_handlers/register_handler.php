<?php

$fname = "";
$lname = "";
$em = "";
$password = "";
$date = "";
$error_array = array();

if(isset($_POST['register_button'])){

  //First name
  $fname = strip_tags($_POST['reg_fname']); //remove HTML tags
  $fname = str_replace(' ', ' ', $fname); //remove spaces
  $fname = ucfirst(strtolower($fname)); //everything lowercase except first letter.
  $_SESSION['reg_fname'] = $fname;

  //Last name
  $lname = strip_tags($_POST['reg_lname']); //remove HTML tags
  $lname = str_replace(' ', ' ', $lname); //remove spaces
  $lname = ucfirst(strtolower($lname)); //everything lowercase except first letter.
  $_SESSION['reg_lname'] = $lname;

  //email
  $em = strip_tags($_POST['reg_email']); //remove HTML tags
  $em = str_replace(' ', ' ', $em); //remove spaces
  $_SESSION['reg_email'] = $em;

  //Password
  $password = strip_tags($_POST['reg_password']); //remove HTML tags

  $date = date("Y-m-d");  //Current date

    //Check if emails are in valid format
    if(filter_var($em, FILTER_VALIDATE_EMAIL)) {
      $em = filter_var($em, FILTER_VALIDATE_EMAIL);

      //Check if email already exists
      $e_check = mysqli_query($con, "SELECT email FROM users WHERE email='$em'");

      //Count number of rows returned
      $num_rows = mysqli_num_rows($e_check);

      if ($num_rows > 0) {
        array_push($error_array, "Email already in use<br>");
      }

    }
    else {
      array_push($error_array, "Invalid email format<br>");
    }

  if(empty($error_array)) {
    $password = password_hash($password, PASSWORD_DEFAULT); //Encrypt password before sending to database

    //Generate username by concatenating first & last name
    $username = strtolower($fname . "_" . $lname);
    $check_username_query = mysqli_query($con, "SELECT username FROM users WHERE username='$username'");

    $i = 0;
    //if username exists add number to username
    while(mysqli_num_rows($check_username_query) != 0) {
      $i++;
      $username = $username . "_" . $i;
      $check_username_query = mysqli_query($con, "SELECT username FROM users WHERE username='$username'");
    }

    //Default profile picture assignment
    $profile_pic = "assets/images/profile_pics/defaults/male_silhouette_headshot.png";

    $query = mysqli_query($con, "INSERT INTO users VALUES ('0', '$fname', '$lname', '$username', '$em', '$password', '$date', '$profile_pic', ',', 'no', '2017', '5', '301', '1111222233334444')");

    //Clear session variables
    $_SESSION['reg_fname'] = "";
    $_SESSION['reg_lname'] = "";
    $_SESSION['reg_email'] = "";

    header("Location: index.php");

  }

}


 ?>
