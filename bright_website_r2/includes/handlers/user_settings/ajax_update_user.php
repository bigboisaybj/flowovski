<?php

if(isset($_POST['firstName'])){

  $userName = $_POST['username'];

  $userFirstName = $_POST['firstName'];

  $userLastName = $_POST['lastName'];

  $oldUserName = $userName;

  $data = array();

  $data = json_encode($oldUserName);

  echo "<div id='account_selection_first'>

        <div class='account_user_change_profile_picture_title'>

          <div class='account_user_name_title_input'>
            <input type='text' id='account_user_name_change_input' placeholder='$userFirstName $userLastName' oninput='processUserInputForUserNameChange(".$data.")'>
          </div>



        </div>

        <br>
        <br>

        <div class='account_user_change_profile_picture'>

        <form action='includes/handlers/user_settings/ajax_update_user_photo.php' method='post' enctype='multipart/form-data'>


          <label class='custom_file_upload'>
            <div class='file_upload_title'>
            Change photo
            </div>
            <input type='file' name='fileToUpload' id='fileToUpload'>
          </label>

          <div class='submitUser_Photo_container'>
            <input type='submit' value='Submit' name='submit' id='user_photo_uploader'>
            <input type='hidden' name='userRegoHolder' value='$userName'>
          </div>

        </form>

        <br>

        </div>

      ";
}

 ?>
