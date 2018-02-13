<?php


if(isset($_POST['input'])){

  $zeus = mysqli_connect("localhost", "root", "aloysius2", "zeus");

  $query = $_POST['input'];
  $userName = $_POST['userName'];

  $scores = array();
  $scores_hashIds = array();

  if($query != "" || $query != " "){

    $search_last_five_clicks_query = mysqli_query($zeus, "SELECT user_first_result, user_second_result, user_third_result, user_fourth_result, user_fifth_result, user_first_time, user_second_time, user_third_time, user_fourth_time, user_fifth_time FROM user_athena WHERE user_name='$userName'") or die(mysqli_error($zeus));

    if(mysqli_num_rows($search_last_five_clicks_query) > 0){

      $last_five_data = mysqli_fetch_array($search_last_five_clicks_query);

      $user_first = $last_five_data['user_first_result'];
      $user_second = $last_five_data['user_second_result'];
      $user_third = $last_five_data['user_third_result'];
      $user_fourth = $last_five_data['user_fourth_result'];
      $user_fifth = $last_five_data['user_fifth_result'];

      $user_first_time = $last_five_data['user_first_time'];
      $user_second_time = $last_five_data['user_second_time'];
      $user_third_time = $last_five_data['user_third_time'];
      $user_fourth_time = $last_five_data['user_fourth_time'];
      $user_fifth_time = $last_five_data['user_fifth_time'];

      $user_clicked_array = array();
      $user_time_array = array();

      if($user_first_time != "TBC"){
        array_push($user_clicked_array, $user_first);
        $user_first_time = intval($user_first_time);
        array_push($user_time_array, $user_first_time);
      }
      if($user_second_time != "TBC"){
        array_push($user_clicked_array, $user_second);
        $user_second_time = intval($user_second_time);
        array_push($user_time_array, $user_second_time);
      }
      if($user_third_time != "TBC"){
        array_push($user_clicked_array, $user_third);
        $user_third_time = intval($user_third_time);
        array_push($user_time_array, $user_third_time);
      }
      if($user_fourth_time != "TBC"){
        array_push($user_clicked_array, $user_fourth);
        $user_fourth_time = intval($user_fourth_time);
        array_push($user_time_array, $user_fourth_time);
      }
      if($user_fifth_time != "TBC"){
        array_push($user_clicked_array, $user_fifth);
        $user_fifth_time = intval($user_fifth_time);
        array_push($user_time_array, $user_fifth_time);
      }

      $sorted_time_array = $user_time_array;
      asort($sorted_time_array);

      $sorted_clicked_array = array();

      for($i = 0; $i < sizeof($sorted_time_array); $i++){

        for($q = 0; $q < sizeof($user_time_array); $q++){
          if($sorted_time_array[$i] == $user_time_array[$q]){
            array_push($sorted_clicked_array, $user_clicked_array[$q]);
          }
        }
      }

      $reversed_array = array_reverse($sorted_clicked_array);

      $complete_hits = array();
      $sub_hits = array();
      //echo sizeof($reversed_array);
      for($t = 0; $t < sizeof($sorted_clicked_array); $t++){

        if($reversed_array[$t] != 'TBC'){

          if($reversed_array[$t] != 'adac9bba354ed200c0549665c3801d4e'){
            $segment = trim($reversed_array[$t]);

            $pembroke_query = mysqli_query($zeus, "SELECT crawler_text FROM pembroke WHERE `hash_id`='$segment'") or die(mysqli_error($zeus));

            if(mysqli_num_rows($pembroke_query) > 0){

              $pembroke_data = mysqli_fetch_array($pembroke_query);

              $crawler_text = $pembroke_data['crawler_text'];
              //echo $crawler_text;

              //Check if total word is found.
              $pattern = "/(?:^|[^a-zA-Z])" . preg_quote($query, '/') . "(?:$|[^a-zA-Z])/i";
              $result = preg_match($pattern, $crawler_text);

              if($result == 1){
                array_push($complete_hits, $reversed_array[$t]);
              }

              //Check if characters match found.
              $sub_query = "/".$query."/i";
              $sub_result = preg_match_all($sub_query, $crawler_text);
              if($sub_result){
                if(!(in_array($reversed_array[$t], $sub_hits))){
                  array_push($sub_hits, $reversed_array[$t]);
                }
              }
            }
          }
        }
      }

      //TODO: AFTER 30 SECONDS, USER HISTORY IS DELETED

      for($i = 0; $i < sizeof($complete_hits); $i++){
        for($q = 0; $q < sizeof($sorted_clicked_array); $q++){
          if(in_array($sorted_clicked_array[$q], $complete_hits)){
            $previous =  "";
            $value = "";
            $next = "";

            if($q == 0){
              $previous =  "NA";
              $value = $sorted_clicked_array[0];
              $next = $sorted_clicked_array[1];
            }
            elseif($q > 0){

              $index = $q;
              $previous_index = $q - 1;
              $next_index = $q + 1;

              $previous = $sorted_clicked_array[$previous_index];
              $value = $sorted_clicked_array[$q];
              $next = $sorted_clicked_array[$next_index];

              if($next == 'adac9bba354ed200c0549665c3801d4e'){
                $next = "END";
              }
            }

            $tf_idf_value_back = "";
            $tf_idf_value_front = "";

              if($previous == "NA"){

                $get_tf_idf = mysqli_query($zeus, "SELECT `$value` FROM tf_idf_results WHERE `hashID`='$value'") or die(mysqli_error($zeus));

                if(mysqli_num_rows($get_tf_idf) > 0){

                  $tf_row = mysqli_fetch_array($get_tf_idf);

                  $tf_idf_value_back = $tf_row[$value];
                }
              }

              if($previous != "NA"){

                $get_tf_idf = mysqli_query($zeus, "SELECT `$previous` FROM tf_idf_results WHERE `hashID`='$value'") or die(mysqli_error($zeus));

                if(mysqli_num_rows($get_tf_idf) > 0){

                  $tf_row = mysqli_fetch_array($get_tf_idf);

                  $tf_idf_value_back = $tf_row[$previous];
                }
              }

              if($next == "END"){

                  $tf_idf_value_front = $tf_idf_value_back;
              }

              if($next != "END"){

                $get_tf_idf = mysqli_query($zeus, "SELECT `$value` FROM tf_idf_results WHERE hashID='$next'");

                if(mysqli_num_rows($get_tf_idf) > 0){

                  $tf_row = mysqli_fetch_array($get_tf_idf);

                  $tf_idf_value_front = $tf_row[$value];
                }
              }

              $front_tv_float = floatval($tf_idf_value_front);
              $back_tv_float = floatval($tf_idf_value_back);

              $total_tf = ($front_tv_float/$back_tv_float) * $front_tv_float;

              $total_tf_final = 1 - $total_tf;

              $previous = strval($previous);
              $value = strval($value);
              $next = strval($next);

              $previous = trim($previous);
              $value = trim($value);
              $next = trim($next);

              if($previous != "NA"){

                $db_query_one = mysqli_query($zeus, "SELECT `weight` FROM session_graph WHERE `start`='$previous' AND `destination`='$value' AND `userName`='$userName'") or die(mysqli_error($zeus));

                if(mysqli_num_rows($db_query_one) == 0){

                  if($next != "END"){

                    $db_query_two = mysqli_query($zeus, "SELECT `weight` FROM session_graph WHERE `start`='$value' AND `destination`='$next' AND `userName`='$userName'") or die(mysqli_error($zeus));

                    if(mysqli_num_rows($db_query_two) > 0){
                      $db_data = mysqli_fetch_array($db_query_two);
                      $weight = $db_data['weight'];
                    }
                  }
                }

                elseif (mysqli_num_rows($db_query_one) > 0) {
                  $db_data = mysqli_fetch_array($db_query_one);
                  $weight = $db_data['weight'];
                }
              }

              if($previous == "NA"){
                $db_query_two = mysqli_query($zeus, "SELECT `weight` FROM session_graph WHERE `start`='58867dba526843ce9b3aae69a4f1d1e3' AND destination='b806ef95d125872d1e72133d96464938' AND userName='bryan_jordan'") or die(mysqli_error($zeus));

                if(mysqli_num_rows($db_query_two) > 0){
                  $db_data = mysqli_fetch_array($db_query_two);
                  $weight = $db_data['weight'];
                }

              }

              $weight = floatval($weight);

              $finalScore = $total_tf_final * $weight;
              array_push($scores, $finalScore);
              array_push($scores_hashIds, $value);
            }
          }
        }
      }
    }

    //TEST:
    array_push($scores, 0.1);
    array_push($scores, 0.3);


    $topMax = array_search(max($scores), $scores);
    $gold_score = $scores[$topMax];
    $gold_result = $scores_hashIds[$topMax];

    unset($scores[$topMax]);

    $secondMax = array_search(max($scores), $scores);
    $silver_score = $scores[$secondMax];
    $silver_result = $scores_hashIds[$secondMax];

    unset($scores[$secondMax]);

    $thirdMax = array_search(max($scores), $scores);
    $bronze_score = $scores[$thirdMax];
    $bronze_result = $scores_hashIds[$thirdMax];

    $normalized_base = $gold_score + $silver_score + $bronze_score;

    $normalised_gold = $gold_score/$normalized_base;
    $normalised_silver = $silver_score/$normalized_base;
    $normalised_bronze = $bronze_score/$normalized_base;

    $faq_db = mysqli_connect("localhost", "root", "aloysius2", "faq");

    $intGold = ceil($normalised_gold * 100);
    $intSilver = ceil($normalised_silver * 100);
    $intBronze = ceil($normalised_bronze * 100);

    $medals_array = array();
    array_push($medals_array, $intGold);
    array_push($medals_array, $intSilver);
    array_push($medals_array, $intBronze);

    $medal_hashId_array = array();
    array_push($medal_hashId_array, $gold_result);
    array_push($medal_hashId_array, $silver_result);
    array_push($medal_hashId_array, $bronze_result);

    $scale_query = mysqli_query($faq_db, "SELECT scale FROM faq_results");

    $scaleValues = array();

    if(mysqli_num_rows($scale_query) > 0){

      while($scale_data = mysqli_fetch_array($scale_query)){
        $scaleValue = $scale_data['scale'];
        array_push($scaleValues, $scaleValue);
      }
    }

    for($i = 0; $i < sizeof($medals_array); $i++){
      $temp = array();

      for($q = 0; $q < sizeof($scaleValues); $q++){

        $diff = $medals_array[$i] - $scaleValues[$q];
        $diff = abs($diff);
        array_push($temp, $diff);
      }

      $minValue = min($temp);

      $key = array_search($minValue, $temp);

      $medals_array[$i] = $scaleValues[$key];
    }

    $faq_db = mysqli_connect("localhost", "root", "aloysius2", "faq");



    for($v = 0; $v < sizeof($medals_array); $v++){

      $final_faq_query = mysqli_query($faq_db, "SELECT question, solution FROM faq_results WHERE hashID='$medal_hashId_array[$v]' AND scale='$medals_array[$v]'");

      if(mysqli_num_rows($final_faq_query) > 0){

        while($final_faq_data = mysqli_fetch_array($final_faq_query)){

            $question = $final_faq_data['question'];
            $solution = $final_faq_data['solution'];

          //echo "QUESTION: " . $question . "_____" . "SOLUTION: " . $solution . "______";

            echo "<div class='faq_search_result'>

                    <div class='faq_search_question'>
                    $question
                    </div>

                    <div class='faq_search_solution'>
                    $solution
                    </div>
                  </div>
                  ";
        }
      }
    }





  }

 ?>
