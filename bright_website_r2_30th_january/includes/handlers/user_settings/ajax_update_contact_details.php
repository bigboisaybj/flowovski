<?php

if(isset($_POST['email'])){

  $userName = $_POST['userName'];

  $email = $_POST['email'];

  $mobile = $_POST['phone'];

  $data = array();

  $data = json_encode($oldUserName);

  echo "<div id='account_selection_first'>
        <form action='includes/handlers/user_settings/post_contact.php' method='post' enctype='multipart/form-data'>

        <div class='account_user_change_email'>

          <div class='account_user_change_email_input'>
            <input type='text' id='account_user_change_email' name='email' placeholder='$email'>
          </div>



        </div>

        <br>
        <br>

        <div class='account_user_change_phone'>

        <div class='account_user_name_title_input'>
          <input type='text' id='account_user_name_change_input' name='phone' placeholder='$mobile'>
        </div>

          <div class='submitUser_Photo_container'>
            <input type='submit' value='Submit' name='submit' id='user_contact_uploader'>
            <input type='hidden' name='userName' value='$userName'>
          </div>

        <br>

        </div>

      ";
}

 ?>
