<?php

if(isset($_POST['userName'])){

  $userName = $_POST['userName'];

  $data = array();

  array_push($data, $userName);

  $data = json_encode($data);

  echo "

      <div class='addAllergy_container' onclick='addAllergen($data)'>

      Add allergen

      </div>

        ";

}
 ?>
