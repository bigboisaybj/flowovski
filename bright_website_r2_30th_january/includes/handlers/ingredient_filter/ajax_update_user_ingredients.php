<?php

if(isset($_POST['username'])){

  $userName = $_POST['username'];

  $data = array();

  array_push($data, $userName);

  $data = json_encode($data);


  echo "
      <div class='table_ingredient_alternative'>

      <form action='includes/handlers/ingredient_filter/post_ingredient_alternatives.php' method='post' enctype='multipart/form-data'>

        <table>
          <tr>
            <th class='ingredient_header'>Ingredient</th>
            <th class='ingredient_header'>First Pick</th>
            <th class='ingredient_header'>Second Pick</th>
            <th class='ingredient_header'>Third Pick</th>
          </tr>
          <tr>
            <td class='ingredient_alt'><input type='text' id='first_ing' name='ingredient_name'></td>
            <td class='ingredient_alt'><input type='text' id='second_ing'name='first_alt'></td>
            <td class='ingredient_alt'><input type='text' id='third_ing' name='second_alt'></td>
            <td class='ingredient_alt'><input type='text' id='fourth_ing' name='third_alt'></td>
          </tr>
        </table>

        <div class='showUserReplacedIngredients' onclick='showReplacedIngredients(".$data.")'>
        Show replaced ingredients
        </div>

        <div class='addReplacement'>
          <input type='submit' value='Replace' name='submit' id='user_replace_ingredient'>
          <input type='hidden' name='userName' value='$userName'>
        </div>

        </form>

      </div>


      ";

}
 ?>
