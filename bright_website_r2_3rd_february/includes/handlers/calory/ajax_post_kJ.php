<?php

if(isset($_POST['userName'])){

  $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

  $userName = $_POST['userName'];
  $kJ = $_POST['kJ'];

  $kJ = intval($kJ);

  $update_calory_cap = mysqli_query($db, "UPDATE users SET calory_cap=$kJ WHERE username='$userName'");

  echo "
        <div class='completedkJUpload' id='upload_kJ_confirmed'>
        Done
        </div>
        ";

}

 ?>
