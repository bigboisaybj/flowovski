<?php

if(isset($_POST['username'])){

  $userName = $_POST['username'];

  $data = array();

  array_push($data, $userName);

  $data = json_encode($data);

  echo "
      <form class='kJ_form'>
          <output id='kJ_form_output'>8700 kJ/day is between the recommended daily intake</output>
          <br>
          <input type='range' id='kj_form_slider' value='8700' min='4000' max='15000' oninput='updateKJRange(kj_form_slider.value, $data)'>
      </form>

      <div class='kJ_results_container'>
        <div class='filterResultsForKJCap' id='request_to_filter_kJ' onclick='adjustCaloryCap(".$data.")'>
          Filter results to meet this amount
        </div>
      </div>
        ";

}



?>
