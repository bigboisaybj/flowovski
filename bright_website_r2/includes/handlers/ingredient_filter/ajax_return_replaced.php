<?php

if(isset($_POST['userName'])){

  $userName = $_POST['userName'];

  $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

  $replaced_ingred_query = mysqli_query($db, "SELECT * FROM ingredient_subs WHERE username='$userName'");

  if(mysqli_num_rows($replaced_ingred_query) > 0){

    $str = "";

    $data = array();

    array_push($data, $userName);

    $data = json_encode($data);

    while($row = mysqli_fetch_array($replaced_ingred_query)) {

      $ingredient = $row['ingredient'];
      $first = $row['first_sub'];
      $second = $row['second_sub'];
      $third = $row['third_sub'];

      $ingredientJSON = array();

      array_push($ingredientJSON, $ingredient);
      array_push($ingredientJSON, $userName);

      $ingredientJSON = json_encode($ingredientJSON);

      $str .= "
                  <tr id='ingredient_$ingredient'>
                  <td id='ingredient_primary' onclick='deleteIngredientReplacement(".$ingredientJSON.")'>$ingredient</td>
                  <td class='ingredient_alt' id='ingredient_specific'>$first</td>
                  <td class='ingredient_alt' id='ingredient_specific'>$second</td>
                  <td class='ingredient_alt' id='ingredient_specific'>$third</td>
                  </tr>
              ";
    }

    $top = "

            <div class='user_ingredient_subs'>

            <table>
              <tr>
                <th class='ingredient_header'>Ingredient</th>
                <th class='ingredient_header'>First Pick</th>
                <th class='ingredient_header'>Second Pick</th>
                <th class='ingredient_header'>Third Pick</th>
              </tr>

            ";

    $bottom = "
                </table>

                </div>
              ";

    $hr = "<div class='addReplacements' onclick='openIngredients(".$data.")'>
              Add replacement
          </div>
          ";

    echo $top . $str . $bottom . $hr;
  }

}

 ?>
