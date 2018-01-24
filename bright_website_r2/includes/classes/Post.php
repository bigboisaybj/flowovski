<?php

class Post {
  private $user_obj;
  private $con;

  public function __construct($con, $user){
    $this->con = $con;
    $this->user_obj = new User($con, $user);
  }

  public function loadMerchants($data, $limit) {

    $page = $data['page'];
    $userLoggedIn = $this->user_obj->getUsername();

    if($page == 1)
        $start = 0;
    else {
        $start = ($page - 1) * $limit;
    }

    $str = "";
    $data_query = mysqli_query($this->con, "SELECT * FROM merchants ORDER BY id DESC");

    if(mysqli_num_rows($data_query) > 0) {

      $num_iterations = 0;
      $count = 1;

      while($row = mysqli_fetch_array($data_query)) {

        $merchant_id = $row['id'];
        $merchant_name = $row['merchant_name'];
        $manager_first_name = $row['manager_first_name'];
        $manager_last_name = $row['manager_last_name'];
        $profile_pic = $row['profile_pic'];
        $total_customers = $row['total_customers'];
        $latitude = $row['latitude'];
        $longitude = $row['longitude'];

        $item_id = "";
        $item_title = "DEFAULT";
        $item_description = "DEFAULT";
        $item_price = "DEFAULT";
        $item_health = "kJ";

        $total_customers_commas = number_format($total_customers);

        $waitingTime = $this->checkWaitingTimeForUpdate($merchant_name);

            if($num_iterations++ < $start)
              continue;

            //Once 10 posts have been loaded, break
            if($count > $limit)
              break;
            else {
              $count++;
            }

            $distance = $this->getDistance($merchant_name, $longitude, $latitude);

            $currentItem = new Item($this->con, $userLoggedIn, $merchant_name, $merchant_id, $total_customers_commas, $distance, $waitingTime, $longitude, $latitude, $profile_pic);

            $str .=

                  "

                  <div class='templateCard'>

                  <div class=merchant_$merchant_id>

                    <div class='merchant_tabs'>

                      <div class='templateBOOK' id='share_$merchant_id' onclick='openMerchantBooking($merchant_id)'>
                        Reserve
                      </div>

                      <div class='templateCHAT'>
                        <input type='submit' class='chatButton' id='chatButton_$merchant_id' value='Message' onclick='openMessages($merchant_id)' style='color: none'>
                      </div>

                      <div class='templateTOTALCUSTOMERS' id='customers_$merchant_id' onclick='openMerchantItemsDisplay($merchant_id)'>
                        $total_customers_commas<br>customers
                      </div>

                    </div>

                    <div class='merchant_title'>
                      $merchant_name
                    </div>

                      <div class='templateLOCSTAT'>
                      <input type='submit' value='".$distance." km away with ".$waitingTime." minute wait' onclick='showMerchantWindow($long, $lat)' style='text-decoration: none' id='locationButton'>
                      </div>

                      <br>

                      <div class='liveQueue'>
                      </div>

                      <div class='merchant_gallery'>
                      <img src=$profile_pic width='640px' height='360px' id='merchant_photos_$merchant_id'>
                        <div id='merchant_messages_window' class='merchant_window_$merchant_id' style='height:0px; border:none;'></div>
                          <input type='hidden' id='merchant_message_type'>

                      </div>

                  <div class='items'>
                      <div class='current_item'>" .

                      $currentItem->construct_first_item()

                      . "

                      </div>

                      <div class='second_item'>" .

                      $currentItem->construct_second_item()

                      . " </div>

                      <div class='extras_$merchant_id'>

                        <div class='extras_item_list_$merchant_id'>
                        </div>

                      </div>

                      <div class='third_item'>" .

                      $currentItem->construct_third_item()

                      . " </div>

                    </div>

                    </div>

                    </div>

                    <div class='extras_area_$merchant_id' id='extras_area'>

                    </div>

                    <div class='purchase_area'>

                    </div>

                    </div>

                  ";


                }

            if($count > $limit)
              $str .= "<input type='hidden' class='nextPage' value='" . ($page + 1) . "'>
                        <input type='hidden' class='noMorePosts' value='false'>";
            else
              $str .= "<input type='hidden' class='noMorePosts' value='true'>";

          }

          echo $str;

        }

        public function getDistance($merchantName, $longitude, $latitude){

          $User_lat = "";
          $User_long = "";
          $Merchant_lat = $longitude;
          $Merchant_long = $latitude;

          $userLoggedIn = $this->user_obj->getUsername();

          if ($userLoggedIn == "") {

            //COMPLETE FUNCTION FOR NON-LOGGED IN USERS
            return "NOT LOGGED IN";

          }

          else {

            $latAndLongQuery_User = mysqli_query($this->con, "SELECT current_longitude, current_latitude FROM users WHERE username='$userLoggedIn'");

            if(mysqli_num_rows($latAndLongQuery_User) > 0) {


              $dataQuery = mysqli_fetch_array($latAndLongQuery_User);

              $User_lat = $dataQuery['current_latitude'];
              $User_long = $dataQuery['current_longitude'];

            }

            $totalDistance = $this->getDistanceVincenty($User_lat, $User_long, $Merchant_lat, $Merchant_long);

            $roundedDistance = number_format(($totalDistance /1000), 2, '.', ' ');

            return $roundedDistance;
          }

        }

        public function getDistanceVincenty($latitudeFrom, $longitudeFrom, $latitudeTo, $longitudeTo) {

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

          $jd = cal_to_jd(CAL_GREGORIAN,date("m"),date("d"),date("Y"));
          $todayIs = (jddayofweek($jd,1));

          $tableDay = strtolower($todayIs);

          $tableName = "waiting_time" . "_" . $tableDay;

          $latestUpdate = mysqli_query($this->con, "SELECT last_updated_waiting_time FROM global_records WHERE status='LATEST'");

          //If there is a latestUpdate column...
          if(mysqli_num_rows($latestUpdate) == 1) {

            $fetchWaitingTime = mysqli_fetch_array($latestUpdate);

            $lastTime = $fetchWaitingTime['last_updated_waiting_time'];

            //If the latestUpdate is today's date...
            if($todayIs === $lastTime) {

              $date = date("Y-m-d H:i:s");

              $timeOfQuery = $this->timeCategory($date);

              $time_query = mysqli_query($this->con, "SELECT $timeOfQuery FROM $tableName WHERE merchant_name='$merchantName'") or die(mysqli_error($this->con));


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

            }

            //If today's date doesn't match with result...
            else {

              $merchant_time_query = mysqli_query($this->con, "SELECT total_waiting_time, time_placed FROM sales WHERE merchantName='$merchantName'");

              $total = 0;
              $instances = 0;
              $averageCategory = 0;
              $mean = 0;

              $updateTime = false;

              while($row = mysqli_fetch_array($merchant_time_query)) {

                    $totalWaitingTime = $row['total_waiting_time'];
                    $timeFromTable = $row['time_placed'];

                    $totalWaitingTime = floatval($totalWaitingTime);

                    $mean += $totalWaitingTime;

                    $tempVal = intval($totalWaitingTime);
                    $total += $tempVal;
                    $instances++;

                    $timeCategory = $this->timeCategory($timeFromTable);

                    $averageWaitingTime = $mean/$instances;

                    $merchantName_query = mysqli_query($this->con, "SELECT merchant_name, '$timeCategory' FROM $tableName WHERE merchant_name='$merchantName'");

                    if(mysqli_num_rows($merchantName_query) == 0) {

                      $addMerchant = mysqli_query($this->con, "INSERT INTO $tableName VALUES ('0', '$merchantName', 'TBC', 'TBC', 'TBC',
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
              }

              $updateMean = $mean/$instances;

              if($updateTime){

                $updateTime = mysqli_query($this->con, "UPDATE $tableName SET $timeCategory='$updateMean' WHERE merchant_name='$merchantName'");

                $updateLatestChange = mysqli_query($this->con, "UPDATE global_records SET last_updated_waiting_time='$todayIs' WHERE status='LATEST'");
              }

            }

          }
        }

        public function timeCategory($timeInput) {

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

          $total = 0;
          $instances = 0;
          $mean = 0;

          $salesForMerchant = mysqli_query($this->con, "SELECT total_waiting_time FROM sales WHERE merchantName='$merchantName'");

          while($timeRow = mysqli_fetch_array($salesForMerchant)) {

            $timeForOrder = $timeRow['total_waiting_time'];

            $timeForOrder = floatval($timeForOrder);

            $total += $timeForOrder;
            $instances++;

          }

          $mean = $total/$instances;

          $meanCeil = ceil($mean);

          $updateMean = mysqli_query($this->con, "UPDATE $tableName SET mean='$meanCeil' WHERE merchant_name='$merchantName'");

          return $meanCeil;

        }

        public function loadMerchantFromSearch($data){

          if(isset($_POST['merchantID'])){

            $merchant_id = $_POST['merchantID'];
            $total_customers_commas = $_POST['totalCustomers'];
            $merchant_name = $_POST['merchantName'];
            $distance = $_POST['distance'];
            $waitingTime = $_POST['waitingTime'];
            $long = $_POST['long'];
            $lat = $_POST['lat'];
            $profile_pic = $_POST['profile_pic'];
            $userLoggedIn = $_POST['username'];
            $profile_pic = $_POST['profile_pic'];
            $itemOne = $_POST['itemOne'];
            $itemTwo = $_POST['itemTwo'];
            $itemThree = $_POST['itemThree'];

            $searchedMerchantItems = new Item($this->con, $userLoggedIn, $merchant_name, $merchant_id, $total_customers_commas, $distance, $waitingTime, $long, $lat, $profile_pic);

            $total_customers_commas = number_format($total_customers_commas);

            $str .=

                  "

                  <div class='templateCard'>

                  <div class='merchant_$merchant_id'>

                    <div class='merchant_tabs'>

                      <div class='templateBOOK'>
                        Reserve
                      </div>

                      <div class='templateCHAT'>
                        <input type='submit' class='chatButton' id='chatButton_$merchant_id' value='Message' onclick='openMessages($merchant_id)' style='text-decoration: none'>
                      </div>

                      <div class='templateTOTALCUSTOMERS' id='customers_$merchant_id' onclick='openMerchantItemsDisplay($merchant_id)'>
                        $total_customers_commas<br>customers
                      </div>

                    </div>

                    <div class='merchant_title'>
                      $merchant_name
                    </div>

                      <div class='templateLOCSTAT'>
                      <input type='submit' value='".$distance." km away with ".$waitingTime." minute wait' onclick='showMerchantWindow($long, $lat)' style='text-decoration: none' id='locationButton'>
                      </div>

                      <br>

                      <div class='liveQueue'>
                      </div>

                      <div class='merchant_gallery'>
                      <img src=$profile_pic width='640px' height='360px' id='merchant_photos_$merchant_id'>
                        <div id='merchant_messages_window' class='merchant_window_$merchant_id' style='height:0px; border:none;'></div>
                          <input type='hidden' id='merchant_message_type'>

                      </div>

                  <div class='items'>
                      <div class='current_item'>".
                      $searchedMerchantItems->construct_searched_first_item($itemOne)
                      ."</div>

                      <div class='second_item'>".
                      $searchedMerchantItems->construct_searched_second_item($itemTwo)
                      ."</div>

                      <div class='extras_$merchant_id'>

                        <div class='extras_item_list_$merchant_id'>
                        </div>

                      </div>

                      <div class='third_item'>".

                      $searchedMerchantItems->construct_searched_third_item($itemThree)
                      ."</div>

                    </div>

                    </div>

                    </div>

                    <div class='extras_area_$merchant_id' id='extras_area'>

                    </div>

                    <div class='purchase_area'>

                    </div>

                    </div>

                  ";

                  echo $str;
              }


        }

}




?>
