<?php

if(isset($_POST['ingredient'])){

  $ingredient = $_POST['ingredient'];
  $userName = $_POST['userName'];

  $data = array();

  array_push($data, $ingredient);
  array_push($data, $userName);

  $data = json_encode($data);

  echo "

        <div id='ingredient_subs_question'>

          <div class='delete_subs' onclick='deleteIngredient(".$data.")'>
            Delete
          </div>

          <div class='or_subs'>
            or
          </div>

          <div class='keep_subs' onclick='keepIngredient(".$data.")'>
            keep?
          </div>

        </div>
        ";


}

?>
