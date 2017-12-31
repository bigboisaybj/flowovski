<?php
require 'config/config.php';
require 'includes/form_handlers/register_handler.php';
require 'includes/form_handlers/login_handler.php';
?>

  <div class="registration_login_section">


    <!--Look into timers instead of buttons -->

  <div id=login_title>
    <h2>Login</h2>
        <a href="https://www.google.com" style="text-decoration:none">Forgot password?</a>
        <br>
        <br>

  </div>

  <form action="register.php" method="POST">
    <input type="email" name="log_email" placeholder="Email" value="<?php
      if(isset($_SESSION['log_email'])) {
        echo $_SESSION['log_email'];
      }
      ?>"<required>

    <input type="password" name="log_password" placeholder="Password">
    <input type="submit" name="login_button" value="Login">

  <br>
  <br>

  </form>

<div id=register_title>
  <h2>Register<h2>
    <h4 id=registration_subtitle>Free to all. </h4>

  <form action="register.php" method="POST">
    <input type="text" name="reg_fname" placeholder="First Name" value="<?php
      if(isset($_SESSION['reg_fname'])) {
        //echo $_SESSION['reg_fname'];
      }
      ?>"<required>

    <input type="text" name="reg_lname" placeholder="Last Name" value="<?php
      if(isset($_SESSION['reg_lname'])) {
        //echo $_SESSION['reg_lname'];
      }
      ?>"<required>
        <br>
        <br>

    <input type="email" name="reg_email" placeholder="Email" value="<?php
      if(isset($_SESSION['reg_email'])) {
        //echo $_SESSION['reg_email'];
      }
      ?>"<required>

    <input type="password" name="reg_password" placeholder="Password" required>

    <input type="submit" name="register_button" value="Register">
  </div>

    <br>
    <br>
    <br>

    <div class=error_block>

      <?php

      if(in_array("Email or password was incorrect<br>", $error_array)) {
        echo "Email or password was incorrect<br><br>";
      }
      if(in_array("Email already in use<br>", $error_array)) {
        echo "Email already in use<br><br>";
        }
      if(in_array("Invalid email format<br>", $error_array)) {
        echo "Invalid email format<br><br>";
      }

      ?>

   </div>

  </form>

</div>
