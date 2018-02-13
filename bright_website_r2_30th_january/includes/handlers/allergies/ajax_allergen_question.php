<?php

if(isset($_POST['allergen'])){

  $allergen = $_POST['allergen'];
  $userName = $_POST['userName'];

  $data = array();

  array_push($data, $allergen);
  array_push($data, $userName);

  $data = json_encode($data);

  echo "

        <div id='allergen_removal_question'>

          <div class='delete_subs' id='delete_allergen' onclick='deleteAllergen(".$data.")'>
            Delete
          </div>

          <div class='or_subs'>
            or
          </div>

          <div class='keep_subs' onclick='keepAllergen(".$data.")'>
            keep?
          </div>

        </div>
        ";


}

?>
