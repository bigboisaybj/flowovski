<?php

if(isset($_POST['hercules'])){

  $userName = $_POST['userName'];

  $time = time();

  $mysql_success = 0;

  $latest_value = $_POST['hercules'];

  $db = mysqli_connect("localhost", "root", "aloysius2", "zeus");

  $user_athena_results = mysqli_query($db, "SELECT * FROM user_athena WHERE user_name='$userName'");

  $values = array();

  if(mysqli_num_rows($user_athena_results) > 0){

    $athena_query = mysqli_fetch_array($user_athena_results);

    $first_value = $athena_query['user_first_result'];
    $second_value = $athena_query['user_second_result'];
    $third_value = $athena_query['user_third_result'];
    $fourth_value = $athena_query['user_fourth_result'];
    $fifth_value = $athena_query['user_fifth_result'];

    $pearson_original = $athena_query['pearson'];
    $latest_time = $athena_query['latest_time'];
    $timeNow = time();
    $timeDif = $timeNow - $latest_time;

    if($pearson_original != 0){
      $session_input = mysqli_query($db, "INSERT INTO sessions VALUES ('0', $timeNow, '$userName', '$latest_value', $pearson_original)");
    }

    if($timeDif > 30){
      $updateAthena = mysqli_query($db, "UPDATE user_athena SET user_first_result='$latest_value', user_second_result='TBC', user_third_result='TBC', user_fourth_result='TBC', user_fifth_result='TBC', user_first_time=$time, user_second_time=0, user_third_time=0, user_fourth_time=0, user_fifth_time=0, latest_time='$timeNow', user_first_percent=0, user_second_percent=0, user_third_percent=0, user_fourth_percent=0, user_fifth_percent=0, pearson=0, scaled_tf_idf_one='TBC', scaled_tf_idf_two='TBC', scaled_tf_idf_three='TBC', scaled_tf_idf_four='TBC', scaled_tf_idf_five='TBC' WHERE user_name='$userName'");

      $mysql_success = 1;

      return;
    }
    else if($timeDif < 30){
      $entry = "";
      $timeEntry = "";

      $values = array();

      array_push($values, $first_value);
      array_push($values, $second_value);
      array_push($values, $third_value);
      array_push($values, $fourth_value);
      array_push($values, $fifth_value);

      $emptyValues = array();

      for($i = 0; $i < sizeof($values); $i++){
        if($values[$i] == 'TBC'){
          array_push($emptyValues, $i);
        }
      }

      if(sizeof($emptyValues) == 0){

        $first_time = $athena_query['user_first_time'];
        $second_time = $athena_query['user_second_time'];
        $third_time = $athena_query['user_third_time'];
        $fourth_time = $athena_query['user_fourth_time'];
        $five_time = $athena_query['user_fifth_time'];

        $times_array = array();

        array_push($times_array, $first_time);
        array_push($times_array, $second_time);
        array_push($times_array, $third_time);
        array_push($times_array, $fourth_time);
        array_push($times_array, $five_time);

        $oldestTime = min($times_array);

        $oldestTime = array_search(min($times_array), $times_array);

        if($oldestTime == 0){
          $entry = "user_first_result";
          $timeEntry = "user_first_time";
        }

        if($oldestTime == 1){
          $entry = "user_second_result";
          $timeEntry = "user_second_time";
        }

        if($oldestTime == 2){
          $entry = "user_third_result";
          $timeEntry = "user_third_time";
        }

        if($oldestTime == 3){
          $entry = "user_fourth_result";
          $timeEntry = "user_fourth_time";
        }

        if($oldestTime == 4){
          $entry = "user_fifth_result";
          $timeEntry = "user_fifth_time";
        }


      }

      if(sizeof($emptyValues) != 0){

        $nextInsert = min($emptyValues);

        if($nextInsert == 0){
          $entry = "user_first_result";
          $timeEntry = "user_first_time";
        }

        if($nextInsert == 1){
          $entry = "user_second_result";
          $timeEntry = "user_second_time";
        }

        if($nextInsert == 2){
          $entry = "user_third_result";
          $timeEntry = "user_third_time";
        }

        if($nextInsert == 3){
          $entry = "user_fourth_result";
          $timeEntry = "user_fourth_time";
        }

        if($nextInsert == 4){
          $entry = "user_fifth_result";
          $timeEntry = "user_fifth_time";
        }

      }

    }

    if($entry != "" && $timeEntry != ""){

      $updateAthena = mysqli_query($db, "UPDATE user_athena SET $entry='$latest_value', $timeEntry='$time', latest_time='$time' WHERE user_name='$userName'");
      $mysql_success = 1;
    }

  }

  elseif (mysqli_num_rows($user_athena_results) == 0) {

    $insertIngredientSub = mysqli_query($db, "INSERT INTO user_athena VALUES ('0', '$userName', 'TBC', '$latest_value', 'TBC', 'TBC', 'TBC', 'TBC', $time, 0, 0, 0, 0, $time, 0, 0, 0, 0, 0, 0, 0.0)") or die(mysqli_error($db));
    $updateAthena = mysqli_query($db, "UPDATE user_athena SET user_first_precent=100 WHERE user_name='$userName'");

  }

  if($mysql_success == 1){

      $first_time = $athena_query['user_first_time'];
      $second_time = $athena_query['user_second_time'];
      $third_time = $athena_query['user_third_time'];
      $fourth_time = $athena_query['user_fourth_time'];
      $five_time = $athena_query['user_fifth_time'];

      $originalTime = array();

      array_push($originalTime, $first_time);
      array_push($originalTime, $second_time);
      array_push($originalTime, $third_time);
      array_push($originalTime, $fourth_time);
      array_push($originalTime, $five_time);

      $times_array = array();

      $intFirst_time = intval($first_time);
      $intSecond_time = intval($second_time);
      $intThree_time = intval($third_time);
      $intFourth_time = intval($fourth_time);
      $intFifth_time = intval($five_time);

      array_push($times_array, $intFirst_time);
      array_push($times_array, $intSecond_time);
      array_push($times_array, $intThree_time);
      array_push($times_array, $intFourth_time);
      array_push($times_array, $intFifth_time);

      $orderedTimeIndexes = array();;
      sort($times_array);

      for($i = 0; $i < sizeof($times_array); $i++){
        $temp = $times_array[$i];
        for($t = 0; $t < sizeof($originalTime); $t++){
          if($temp == $originalTime[$t]){
            array_push($orderedTimeIndexes, $t);
          }
        }
      }

      $mapOfUserAction = array();

      $first_result = $athena_query['user_first_result'];
      $second_result = $athena_query['user_second_result'];
      $third_result = $athena_query['user_third_result'];
      $fourth_result = $athena_query['user_fourth_result'];
      $fifth_result = $athena_query['user_fifth_result'];

      $result_array = array();

      array_push($result_array, $first_result);
      array_push($result_array, $second_result);
      array_push($result_array, $third_result);
      array_push($result_array, $fourth_result);
      array_push($result_array, $fifth_result);

      $hashValues = array();

      for($i = 0; $i < sizeof($orderedTimeIndexes); $i++){
        $index = $orderedTimeIndexes[$i];
        $presentNumber = $i;
        $presentNumber++;
        $nextIndex = $orderedTimeIndexes[$presentNumber];

        $hashFirst = $result_array[$index];
        $hashSecond = $result_array[$nextIndex];

        $hashFirst = trim($hashFirst);
        $hashSecond = trim($hashSecond);

        $hashFirst = strval($hashFirst);
        $hashSecond = strval($hashSecond);

        $zeus_query = mysqli_query($db, "SELECT `$hashFirst` FROM tf_idf_results WHERE hashID='$hashSecond'");

        if(mysqli_num_rows($zeus_query) > 0){

          $zeus = mysqli_fetch_array($zeus_query);

          $value = $zeus[$hashFirst];
          array_push($hashValues, $value);

        }

      }

    if($hashValues){

    if (sizeof($hashValues) > 0) {

      for($i = 0; $i < sizeof($hashValues); $i++){

        $percentName = "";
        $percentValue = "";

        if($i == 0){
          $percentName = "user_first_percent";
          $percentValue = $hashValues[$i];

        }
        elseif ($i == 1) {
          $percentName = "user_second_percent";
          $percentValue = $hashValues[$i];

        }
        elseif ($i == 2) {
          $percentName = "user_third_percent";
          $percentValue = $hashValues[$i];

        }
        elseif ($i == 3) {
          $percentName = "user_fourth_percent";
          $percentValue = $hashValues[$i];

        }
        elseif ($i == 4) {
          $percentName = "user_fifth_percent";
          $percentValue = $hashValues[$i];

        }

        $updatePercent = mysqli_query($db, "UPDATE user_athena SET $percentName='$percentValue' WHERE user_name='$userName'") or die(mysqli_error($db));
        }
      }
    }
    /*
    $command = escapeshellcmd('/Users/bryanjordan/Sites/zeus_maths/correlation_determination_calculator.py test');
    $output = shell_exec($command);
    echo $output;
    */

    $percent_values_query = mysqli_query($db, "SELECT user_first_percent, user_second_percent, user_third_percent, user_fourth_percent, user_fifth_percent FROM user_athena WHERE user_name='$userName'");

    $timeDifArray = array();

    if(mysqli_num_rows($percent_values_query) > 0){

      $firstDif = $times_array[1] - $times_array[0];
      $secondDif = $times_array[2] - $times_array[1];
      $thirdDif = $times_array[3] - $times_array[2];
      $fourthDif = $times_array[4] - $times_array[3];
      $fifthDif = $times_array[5] - $times_array[4];

      if($firstDif > 0 && $firstDif <= 30){
        array_push($timeDifArray, $firstDif);
      }
      if($secondDif > 0 && $secondDif <= 30){
        array_push($timeDifArray, $secondDif);
      }
      if($thirdDif > 0 && $thirdDif <= 30){
        array_push($timeDifArray, $thirdDif);
      }
      if($fourthDif > 0 && $fourthDif <= 30){
        array_push($timeDifArray, $fourthDif);
      }
      if($fifthDif > 0 && $fifthDif <= 30){
        array_push($timeDifArray, $fifthDif);
      }

      $percent_values = mysqli_fetch_array($percent_values_query);

      $first_percent = $percent_values['user_first_percent'];
      $second_percent = $percent_values['user_second_percent'];
      $third_percent = $percent_values['user_third_percent'];
      $fourth_percent = $percent_values['user_fourth_percent'];
      $fifth_percent = $percent_values['user_fifth_percent'];

      $valuesArray = array();

      if($first_percent != 0){
        array_push($valuesArray, $first_percent);
      }
      if($second_percent != 0){
        array_push($valuesArray, $second_percent);
      }
      if($third_percent != 0){
        array_push($valuesArray, $third_percent);
      }
      if($fourth_percent != 0){
        array_push($valuesArray, $fourth_percent);
      }
      if($fifth_percent != 0){
        array_push($valuesArray, $fifth_percent);
      }

      $normalisedArray = array();

      $min = min($valuesArray);
      $max = max($valuesArray);

      for($i = 0; $i < sizeof($valuesArray); $i++){
        $normalised = ($valuesArray[$i] - $min) / ($max - $min);
        array_push($normalisedArray, $normalised);
      }

      $first = $normalisedArray;
      $second = $timeDifArray;

      $results = array();
      $totalSum = 0;

      for($i = 0; $i < sizeof($first); $i++){

        $firstVal = $first[$i];
        $secondVal = floatval($second[$i]);

        $result = $firstVal * $secondVal;
        array_push($results, $result);
      }

      $totalSum = array_sum($results);

      $first_sum = array_sum($first);

      $second_sum = array_sum($second);

      $first_squared = array();
      $second_squared = array();

      for($i = 0; $i < sizeof($first); $i++){

        $firstSquare = pow($first[$i], 2);
        $secondSquare = pow($second[$i], 2);

        array_push($first_squared, $firstSquare);
        array_push($second_squared, $secondSquare);
      }

      $square_first_sum = array_sum($first_squared);

      $square_second_sum = array_sum($second_squared);

      $numerator_part_a = $totalSum;
      $numerator_part_b_one = $first_sum * $second_sum;
      $numberator_part_b_two = sizeof($first);

      $numerator_part_b_total = $numerator_part_b_one/$numberator_part_b_two;
      $numerator = $numerator_part_a - $numerator_part_b_total;

      $denominator_part_a_one = $square_first_sum;
      $denominator_part_a_two = pow($first_sum, 2)/sizeof($first);
      $denominator_a = $denominator_part_a_one - $denominator_part_a_two;

      $denominator_part_b_one = $square_second_sum;
      $denominator_part_b_two = pow($second_sum, 2)/sizeof($second);

      $denominator_b = $denominator_part_b_one - $denominator_part_b_two;
      $denominator_total = $denominator_a * $denominator_b;
      $denominator_sqrt = sqrt($denominator_total);

      $total = $numerator/$denominator_sqrt;
      $avgTimeDif = $second_sum/sizeof($second);

      if($total != null){
        $update_pearson_coefficient = mysqli_query($db, "UPDATE user_athena SET pearson='$total', avg_time='$avgTimeDif' WHERE user_name='$userName'") or die(mysqli_error($db));
      }

      for($i = 0; $i < sizeof($first); $i++){

        $tf_idf = $first[$i];
        $time_dif = $timeDifArray[$i];
        $scaledTime = log($time_dif, $avgTimeDif);
        $scaled_tf_idf = $tf_idf * $scaledTime;
        $scaled_value = 1 - $scaled_tf_idf;

        $columnName = "";
        if($i == 0){
          $columnName = 'scaled_tf_idf_one';
        }
        if($i == 1){
          $columnName = 'scaled_tf_idf_two';
        }
        if($i == 2){
          $columnName = 'scaled_tf_idf_three';
        }
        if($i == 3){
          $columnName = 'scaled_tf_idf_four';
        }
        if($i == 4){
          $columnName = 'scaled_tf_idf_five';
        }

        $update_scaled_values = mysqli_query($db, "UPDATE user_athena SET $columnName='$scaled_value' WHERE user_name='$userName'") or die(mysqli_error($db));
      }




    }


}

}




 ?>
