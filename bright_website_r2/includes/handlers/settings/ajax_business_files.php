<?php

if(isset($_POST['userName'])){


  $userName = $_POST['userName'];
  $merchantName = $_POST['venueName'];

  echo "

  <div class='fileSubmit'>
    <form action='includes/handlers/settings/post_menus.php' method='post' enctype='multipart/form-data'>

    <div class='processMenu_title'>
    We'll automatically process your menu, including pricing and extras. PDF, JPG, JPEG and PNG supported.
    </div>

      <label class='custom_file_upload'>
        <div class='file_upload_title'>
        Upload menu
        </div>
        <input type='file' name='fileToUpload' id='fileToUpload'>
      </label>

      <div class='submitMenu_container'>
        <input type='submit' value='Submit' name='submit' id='menu_uploader'>
        <input type='hidden' name='userRegoHolder' value='$userName'>
      </div>
    </form>

  </div>

      ";
}

 ?>
