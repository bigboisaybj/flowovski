<?php

if(isset($_POST['firstName'])){

  $userName = $_POST['username'];

  //UserEmail

  //UserPhone

  //$data = array();

  $data = json_encode($oldUserName);

  echo "<div id='account_selection_first'>
        <form action='includes/handlers/user_settings/post_password.php' method='post' enctype='multipart/form-data'>


        <div class='account_user_change_password'>

          <div class='account_user_change_password_input'>
            <input type='password' id='account_user_change_password' placeholder='New password' name='passwordOne'>
          </div>



        </div>

        <br>
        <br>

        <div class='account_user_confirm_change_password'>

        <div class='account_user_confirm_change_password_input'>
          <input type='password' id='account_user_change_password' placeholder='Confirm password' name='passwordTwo'>
        </div>

          <div class='submit_user_contact'>
            <input type='submit' value='Submit' name='submit' id='user_contact_uploader'>
            <input type='hidden' name='userName' value='$userName'>
          </div>

        <br>

        </form>
        </div>

      ";
}

 ?>
