<?php

if(isset($_POST['allergen'])){

  $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

  $allergen = $_POST['allergen'];
  $userName = $_POST['userName'];

  $deleteAllergy = mysqli_query($db, "DELETE FROM allergies WHERE username='$userName' and allergen='$allergen'");

  echo "
        <div class='completedAllergenChange' id='allergen_upload_completed'>
        Done
        </div>
        ";
}

 ?>
