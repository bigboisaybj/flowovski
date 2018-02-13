<?php

class AjaxSearchData{
  private $username;
  private $merchantName;
  private $longitude;
  private $latitude;

  public function __construct(){
    $this->merchantName = "DEFAULT";
    $this->longitude = "DEFAULT";
    $this->latitude = "DEFAULT";
  }

  public function getDistanceForSearchResults($merchantName, $longitude, $latitude, $username){

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $User_lat = "";
    $User_long = "";
    $Merchant_lat = $longitude;
    $Merchant_long = $latitude;

    $userLoggedIn = $username;

    if ($userLoggedIn == "") {

      //COMPLETE FUNCTION FOR NON-LOGGED IN USERS
      return "NOT LOGGED IN";

    }

    else {

      $latAndLongQuery_User = mysqli_query($db, "SELECT current_longitude, current_latitude FROM users WHERE username='$userLoggedIn'");

      if(mysqli_num_rows($latAndLongQuery_User) > 0) {

        $dataQuery = mysqli_fetch_array($latAndLongQuery_User);

        $User_lat = $dataQuery['current_latitude'];
        $User_long = $dataQuery['current_longitude'];

      }

      $totalDistance = $this->getDistanceVincentyForSearchResults($User_lat, $User_long, $Merchant_lat, $Merchant_long);

      $roundedDistance = number_format(($totalDistance /1000), 2, '.', ' ');

      return $roundedDistance;
    }

  }

  public function getDistanceVincentyForSearchResults($latitudeFrom, $longitudeFrom, $latitudeTo, $longitudeTo) {

    $tempLatFrom = floatval($latitudeFrom);
    $tempLongFrom = floatval($longitudeFrom);

    $earthRadius = 6371000;

    $latFrom = deg2rad($tempLatFrom);
    $lonFrom = deg2rad($tempLongFrom);
    $latTo = deg2rad($latitudeTo);
    $lonTo = deg2rad($longitudeTo);

    $lonDelta = $lonTo - $lonFrom;
    $a = pow(cos($latTo) * sin($lonDelta), 2) +
      pow(cos($latFrom) * sin($latTo) - sin($latFrom) * cos($latTo) * cos($lonDelta), 2);
    $b = sin($latFrom) * sin($latTo) + cos($latFrom) * cos($latTo) * cos($lonDelta);

    $angle = atan2(sqrt($a), $b);

    return $angle * $earthRadius;
  }

  public function checkWaitingTimeForUpdate($merchantName){

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $jd = cal_to_jd(CAL_GREGORIAN,date("m"),date("d"),date("Y"));
    $todayIs = (jddayofweek($jd,1));

    $tableDay = strtolower($todayIs);

    $tableName = "waiting_time" . "_" . $tableDay;

    $latestUpdate = mysqli_query($db, "SELECT last_updated_waiting_time FROM global_records WHERE status='LATEST'");

    //If there is a latestUpdate column...
    if(mysqli_num_rows($latestUpdate) == 1) {

      $fetchWaitingTime = mysqli_fetch_array($latestUpdate);

      $lastTime = $fetchWaitingTime['last_updated_waiting_time'];

      //If the latestUpdate is today's date...
      if($todayIs === $lastTime) {

        $date = date("Y-m-d H:i:s");

        $timeOfQuery = $this->timeCategory($date);

        $time_query = mysqli_query($db, "SELECT $timeOfQuery FROM $tableName WHERE merchant_name='$merchantName'") or die(mysqli_error($db));

        if(mysqli_num_rows($time_query) > 0) {

          $timeRow = mysqli_fetch_array($time_query);

          $timeResult = $timeRow[$timeOfQuery];

          if($timeResult == 'TBC') {

            //NEED TO ENSURE MERCHANT IS OPEN OTHERWISE TBC COULD BE MISLEADING -> SHOWING WAITING TIME WHEN CLOSED

            $meanResult = $this->findMeanResult($merchantName, $tableName);

            $roundedResult = round($meanResult, 0);

            return $roundedResult;
          }

          else{

            $roundedResult = round($timeResult, 0);

            return $roundedResult;

          }
        }
        else {
          //This means no results. At all.

          //No results added until sale.

          return "5";
        }

      }

      //If today's date doesn't match with result...
      else {

        $merchant_time_query = mysqli_query($db, "SELECT total_waiting_time, time_placed FROM sales WHERE merchantName='$merchantName'");

        $total = 0;
        $instances = 0;
        $averageCategory = 0;
        $mean = 0;

        $updateTime = false;

        while($row = mysqli_fetch_array($merchant_time_query)) {

              $totalWaitingTime = $row['total_waiting_time'];
              $timeFromTable = $row['time_placed'];

              $mean += $totalWaitingTime;

              $tempVal = intval($totalWaitingTime);
              $total += $tempVal;
              $instances++;

              $timeCategory = $this->timeCategory($timeFromTable);

              $averageWaitingTime = $mean/$instances;

              $merchantName_query = mysqli_query($db, "SELECT merchant_name, '$timeCategory' FROM $tableName WHERE merchant_name='$merchantName'");

              if(mysqli_num_rows($merchantName_query) == 0) {

                $addMerchant = mysqli_query($db, "INSERT INTO $tableName VALUES ('0', '$merchantName', 'TBC', 'TBC', 'TBC',
                'TBC', 'TBC', 'TBC',
                'TBC', 'TBC', 'TBC', 'TBC',
                'TBC', 'TBC', 'TBC', 'TBC', 'TBC',
                'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC',
                'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC',
                'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC',
                'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC',
                'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC',
                'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC',
                'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC',
                'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC',
                'TBC', 'TBC', 'TBC', 'TBC', 'TBC', 'TBC')");

                $updateTime = true;

              }

              elseif(mysqli_num_rows($merchantName_query) == 1){

                $updateTime = true;

              }

              elseif(mysqli_num_rows($merchantName_query) == 0){

              }
        }

        $updateMean = $mean/$instances;

        if($updateTime){

          $updateTime = mysqli_query($db, "UPDATE $tableName SET $timeCategory='$updateMean' WHERE merchant_name='$merchantName'");

          $updateLatestChange = mysqli_query($db, "UPDATE global_records SET last_updated_waiting_time='$todayIs' WHERE status='LATEST'");
        }

      }

    }
  }

  public function timeCategory($timeInput) {

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $hourCategory = "";
    $minuteCategory = "";

    $timeHour = substr($timeInput, 11, 2);
    $timeMinute = substr($timeInput, 14, 2);

    $timeMinuteVal = intval($timeMinute);

    if($timeHour === '00') {
      $hourCategory = 'zero';
    }
    elseif($timeHour === '01'){
      $hourCategory = 'one';

    }
    elseif($timeHour === '02'){
      $hourCategory = 'two';

    }
    elseif($timeHour === '03'){
      $hourCategory = 'three';

    }
    elseif($timeHour === '04'){
      $hourCategory = 'four';

    }
    elseif($timeHour === '05'){
      $hourCategory = 'five';

    }
    elseif($timeHour === '06'){
      $hourCategory = 'six';

    }
    elseif($timeHour === '07'){
      $hourCategory = 'seven';

    }
    elseif($timeHour === '08'){
      $hourCategory = 'eight';

    }
    elseif($timeHour === '09'){
      $hourCategory = 'nine';

    }
    elseif($timeHour === '10'){
      $hourCategory = 'ten';

    }
    elseif($timeHour === '11'){
      $hourCategory = 'eleven';

    }
    elseif($timeHour === '12'){
      $hourCategory = 'twelve';

    }
    elseif($timeHour === '13'){
      $hourCategory = 'thirteen';

    }
    elseif($timeHour === '14'){
      $hourCategory = 'fourteen';

    }
    elseif($timeHour === '15'){
      $hourCategory = 'fifteen';

    }
    elseif($timeHour === '16'){
      $hourCategory = 'sixteen';

    }
    elseif($timeHour === '17'){
      $hourCategory = 'seventeen';

    }
    elseif($timeHour === '18'){
      $hourCategory = 'eighteen';

    }
    elseif($timeHour === '19'){
      $hourCategory = 'nineteen';

    }
    elseif($timeHour === '20'){
      $hourCategory = 'twenty';

    }
    elseif($timeHour === '21'){
      $hourCategory = 'twenty_one';

    }
    elseif($timeHour === '22'){
      $hourCategory = 'twenty_two';

    }
    elseif($timeHour === '23'){
      $hourCategory = 'twenty_three';

    }

    if((0 <= $timeMinuteVal) && ($timeMinuteVal <= 15)) {
      $minuteCategory = '_first_quarter';
    }
    elseif((16 <= $timeMinuteVal) && ($timeMinuteVal <= 30)) {
      $minuteCategory = '_second_quarter';

    }
    elseif((31 <= $timeMinuteVal) && ($timeMinuteVal <= 45)) {
      $minuteCategory = '_third_quarter';

    }
    elseif((46 <= $timeMinuteVal) && ($timeMinuteVal <= 59)) {
      $minuteCategory = '_zero';

    }

    $timeCategory = $hourCategory . $minuteCategory;

    return $timeCategory;

  }

  public function findMeanResult($merchantName, $tableName) {

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $total = 0;
    $instances = 0;
    $mean = 0;

    $salesForMerchant = mysqli_query($db, "SELECT total_waiting_time FROM sales WHERE merchantName='$merchantName'");

    while($timeRow = mysqli_fetch_array($salesForMerchant)) {

      $timeForOrder = $timeRow['total_waiting_time'];

      $timeForOrder = floatval($timeForOrder);

      $total += $timeForOrder;
      $instances++;

    }

    $mean = $total/$instances;

    $meanCeil = ceil($mean);

    $updateMean = mysqli_query($db, "UPDATE $tableName SET mean='$meanCeil' WHERE merchant_name='$merchantName'");

    return $meanCeil;

  }



}

?>
