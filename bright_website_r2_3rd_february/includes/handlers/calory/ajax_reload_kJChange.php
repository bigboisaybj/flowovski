<?php

if(isset($_POST['userName'])){

  $userName = $_POST['userName'];

  $data = array();

  array_push($data, $userName);

  $data = json_encode($data);

  echo "

        <div class='filterResultsForKJCap' id='request_to_filter_kJ' onclick='adjustCaloryCap(".$data.")'>
          Filter results to meet this amount
        </div>

        ";

}

 ?>
