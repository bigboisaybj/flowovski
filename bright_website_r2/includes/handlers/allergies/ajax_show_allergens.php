<?php

if(isset($_POST['allergens'])){

  $lengthOfData = $_POST['lengthOfData'];

  $allergens = $_POST['allergens'];

  $userName = $allergens[0];

  $data = array();

  array_push($data, $userName);

  $data = json_encode($data);

  $top = "
          <div class='user_allergen'>
          <table>
            <tr>
              <th class='ingredient_header'> </th>
              <th class='ingredient_header'> </th>
              <th class='ingredient_header'> </th>
              <th class='ingredient_header'> </th>
            </tr>

          ";

  if($lengthOfData == 1){
    $td = "
            <div class='tempHolderAllergens'>
            </div>
            ";
  }

  for($i = 1; $i < $lengthOfData; $i++){

    if($i == 0 || $i == 5){
      $td .= "<tr>";
    }

    $allergenData = array();

    array_push($allergenData, $allergens[$i]);
    array_push($allergenData, $userName);

    $allergenData = json_encode($allergenData);

    if($allergenData[$i] == " " ){
      $allergenData[$i] = "No allergens added";
    }

    $td .= "

            <td class='allergen_specific' id='ingredient_specific_$allergens[$i]' onclick='allergenQuestion(".$allergenData.")'>$allergens[$i]</td>

            ";

    if($i == 4 || $i == 7){
      $td .= "</tr>";
    }

  }

  $bottom = "
              </table>

              </div>
            ";

  $hr = "<div class='addAllergen' onclick='openAllergies(".$data.")'>
            Add allergen
        </div>
        ";

  echo $top . $td . $bottom . $hr;
}

 ?>
