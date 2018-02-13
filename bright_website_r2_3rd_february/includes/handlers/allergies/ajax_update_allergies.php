<?php

if(isset($_POST['username'])){

  $userName = $_POST['username'];

  $data = array();

  $data = json_encode($userName);

  $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

  $allergen_query = mysqli_query($db, "SELECT * FROM allergies WHERE username='$userName'");

  $blockedAllergies = array();
  array_push($blockedAllergies, $userName);

  if(mysqli_num_rows($allergen_query) > 0){

    $str = "";
    $end = "";
    $count = 0;

    $total = mysqli_num_rows($allergen_query) - 1;

    while($row = mysqli_fetch_array($allergen_query)) {

      $allergen = $row['allergen'];

      array_push($blockedAllergies, $allergen);

      if($count != $total){
        $end = ", ";
      }
      elseif ($count == $total) {
        $end = ".";
      }

      $str .= $allergen . $end;
      $count++;
    }

  }

  $blockedAllergies = json_encode($blockedAllergies);

  echo "<div id='diet_selection'>

        <div class='diet_allergies_title'>

          <form id='diet_form'>
          <div class='diet_allergies_input'>
            <input type='text' id='diet_allergies' placeholder='Allergy' oninput='allergenInProcess(".$data.")'>
          </div>


          </form>



        </div>

        <br>
        <br>

        <div class='diet_allergies_list_container'>

          <div class='diet_allergies_list_container_input'>
            <div id='allergies_list' onclick='openAllergiesFilter(".$blockedAllergies.")'>
            Show filtered allergens
            </div>
          </div>

        </div>

        <div class='allergy_response'>
          <div class='addAllergy_container' onclick='addAllergen(".$data.")'>
            Add allergen
          </div>
        </div>

        <br>

        </div>

      ";
}

 ?>
