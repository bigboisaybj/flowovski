<?php

$db = mysqli_connect("localhost", "root", "aloysius2", "bright");

if(isset($_POST['userName'])){

  $userName = $_POST['userName'];

  $allergen = $_POST['allergen'];

  $date = date("Y-m-d H:i:s");

  $addAllergy_query = mysqli_query($db, "INSERT INTO allergies VALUES('0', '$allergen', '$userName', '$date')");

  //ECHO "DONE". ONCHANGE -> BACK TO "ADD ALLERGEN".
  echo "
        <div class='completedkJUpload' id='upload_allergen_confirmed'>
        Done
        </div>
        ";

  //BLUE FOR ALLERGENS IN STR. CLICK -> DELETE --> DELETE OR KEEP?

}
 ?>
