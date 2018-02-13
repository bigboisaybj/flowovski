<?php
include("../../config/config.php");
include("../../includes/classes/User.php");
include("../../includes/classes/Item.php");
include("../../includes/classes/AjaxSearchData.php");
include("../../includes/classes/SearchHistory.php");

$query = $_POST['query'];
$userLoggedIn = $_POST['userLoggedIn'];

$names = explode(" ", $query);

//If query contains an underscore, assume user is searching for usernames
if(strpos($query, '_') !== false)
    $usersReturnedQuery = mysqli_query($con, "SELECT * FROM merchants WHERE merchant_name LIKE '$query%'");

//If there are two words, assume they are first & last names
else if(count($names) == 2)
    $usersReturnedQuery = mysqli_query($con, "SELECT * FROM merchants WHERE (merchant_name LIKE '$names[0]%' AND merchant_name LIKE '$names[1]%')");

//if query has one word only, search first or last names
else
    $usersReturnedQuery = mysqli_query($con, "SELECT * FROM merchants WHERE (merchant_name LIKE '$names[0]%' OR merchant_name LIKE '$names[0]%')");

$str = "";

if($query != ""){

  if(mysqli_num_rows($usersReturnedQuery) == 0) {

    $str .= "
              <div class='noSearchMatchings'>

                <div class='no_search_results_title'>
                  Your search - '$query' - did not return any results.
                </div>

                <br>
                <br>
                <br>

                <div class='no_search_results_details'>
                  Make sure all words are correct.
                </div>

              </div>";

  }

  if(mysqli_num_rows($usersReturnedQuery) > 0) {

        while($row = mysqli_fetch_array($usersReturnedQuery)) {

          $merchant_id = $row['id'];
          $merchant_name = $row['merchant_name'];
          $manager_first_name = $row['manager_first_name'];
          $manager_last_name = $row['manager_last_name'];
          $profile_pic = $row['profile_pic'];
          $total_customers = $row['total_customers'];
          $latitude = $row['latitude'];
          $longitude = $row['longitude'];

          $ajaxData = new AjaxSearchData();
          $distance = $ajaxData->getDistanceForSearchResults($merchant_name, $longitude, $latitude, $userLoggedIn);
          $waitingTime = $ajaxData->checkWaitingTimeForUpdate($merchant_name);

          $currentItem = new Item($con, $userLoggedIn, $merchant_name, $merchant_id, $total_customers_commas, $distance, $waitingTime, $longitude, $latitude, $profile_pic);

          $searchHistory = new SearchHistory($userLoggedIn);
          $lastSearch = $searchHistory->getLastSearch();

          if($lastSearch == NULL || $lastSearch != $merchant_name){

            $originalPreviewThird = $currentItem->get_preview_third_item($merchant_id);
            $originalPreviewSecond = $currentItem->get_preview_second_item($merchant_id);
            $originalPreviewOne = $currentItem->get_preview_first_item($merchant_id);

            $previewOneArray = array();

            array_push($previewOneArray, $originalPreviewOne);

            $dots = (strlen($previewOneArray[0]) >= 15) ? "..." : "";
            $split = str_split($previewOneArray[0], 15);
            $split = $split[0] . $dots;
            $searchPreviewOne = $split;

            $previewTwoArray = array();

            array_push($previewTwoArray, $originalPreviewSecond);

            $dots = (strlen($previewTwoArray[0]) >= 15) ? "..." : "";
            $split = str_split($previewTwoArray[0], 15);
            $split = $split[0] . $dots;
            $searchPreviewTwo = $split;

            $previewThreeArray = array();

            array_push($previewThreeArray, $originalPreviewThird);

            $dots = (strlen($previewThreeArray[0]) >= 15) ? "..." : "";
            $split = str_split($previewThreeArray[0], 15);
            $split = $split[0] . $dots;
            $searchPreviewThree = $split;

            $arr = array('merchantID'=>$row['id'], 'totalCustomers'=>$row['total_customers'], 'merchantName'=>$row['merchant_name'], 'distance'=>$distance, 'waitingTime'=>$waitingTime, 'long'=>$row['longitude'], 'lat'=>$row['latitude'], 'profile_pic'=>$row['profile_pic'], 'username'=>$userLoggedIn, 'itemOne'=>$originalPreviewOne, 'itemTwo'=>$originalPreviewSecond, 'itemThree'=>$originalPreviewThird);

            $merchantData_json = json_encode($arr);

            $str .=

                    "
                    <div class='search_item'>

                    <div class='merchant_$merchant_id'>

                      <div class='merchant_tabs'>

                        <div id='search_preview_third' onclick='searchResultExtraForm_three($merchantData_json)' class='search_third_$merchant_id'>".

                        $searchPreviewThree

                        ."</div>

                        <div id='search_preview_second' onclick='searchResultExtraForm_two($merchantData_json)' class='search_second_$merchant_id'>".

                        $searchPreviewTwo

                        ."</div>

                        <div id='search_preview_first' onclick='searchResultExtraForm_one($merchantData_json)'class='search_first_$merchant_id'>".

                        $searchPreviewOne

                        ."</div>

                      </div>
                      <div class='search_merchant_title' onclick='openMerchant($merchantData_json)'>
                        $merchant_name
                      </div>

                      <div class='templateLOCSTAT'>
                      <input type='submit' value='".$distance." km away with ".$waitingTime." minute wait' onclick='showMerchantWindow($long, $lat)' style='text-decoration: none' id='locationButton'>
                      </div>

                      <div class='search_liveQueue'>
                      </div>

                    </div>
                    ";

                    $searchHistory->addToLedger($query, $merchant_name);
                    $searchHistory->updateLastPreviews($originalPreviewOne, $originalPreviewSecond, $originalPreviewThird);

                  }
                  else {

                    $lastPreviewsQuery = mysqli_query($con, "SELECT search_preview_one, search_preview_two, search_preview_three FROM users WHERE username='$userLoggedIn'");

                    if(mysqli_num_rows($lastPreviewsQuery) == 1){

                      $lastPreviewsRows = mysqli_fetch_array($lastPreviewsQuery);

                      $previewOne = $lastPreviewsRows['search_preview_one'];
                      $previewTwo = $lastPreviewsRows['search_preview_two'];
                      $previewThreee = $lastPreviewsRows['search_preview_three'];

                      $previewOneArray = array();

                      array_push($previewOneArray, $previewOne);

                      $dots = (strlen($previewOneArray[0]) >= 15) ? "..." : "";
                      $split = str_split($previewOneArray[0], 15);
                      $split = $split[0] . $dots;
                      $searchPreviewOne = $split;

                      $previewTwoArray = array();

                      array_push($previewTwoArray, $previewTwo);

                      $dots = (strlen($previewTwoArray[0]) >= 15) ? "..." : "";
                      $split = str_split($previewTwoArray[0], 15);
                      $split = $split[0] . $dots;
                      $searchPreviewTwo = $split;

                      $previewThreeArray = array();

                      array_push($previewThreeArray, $previewThreee);

                      $dots = (strlen($previewThreeArray[0]) >= 15) ? "..." : "";
                      $split = str_split($previewThreeArray[0], 15);
                      $split = $split[0] . $dots;
                      $searchPreviewThree = $split;

                      $arr = array('merchantID'=>$row['id'], 'totalCustomers'=>$row['total_customers'], 'merchantName'=>$row['merchant_name'], 'distance'=>$distance, 'waitingTime'=>$waitingTime, 'long'=>$row['longitude'], 'lat'=>$row['latitude'], 'profile_pic'=>$row['profile_pic'], 'username'=>$userLoggedIn, 'itemOne'=>$previewOne, 'itemTwo'=>$previewTwo, 'itemThree'=>$previewThreee);

                      $merchantData_json = json_encode($arr);

                      $str .=

                              "
                              <div class='search_item'>

                              <div class='merchant_$merchant_id'>

                                <div class='merchant_tabs'>

                                  <div id='search_preview_third' onclick='searchResultExtraForm_three($merchantData_json)' class='search_third_$merchant_id'>".

                                  $searchPreviewOne

                                  ."</div>

                                  <div id='search_preview_second' onclick='searchResultExtraForm_two($merchantData_json)' class='search_second_$merchant_id'>".

                                  $searchPreviewTwo

                                  ."</div>

                                  <div id='search_preview_first' onclick='searchResultExtraForm_one($merchantData_json)' class='search_first_$merchant_id'>".

                                  $searchPreviewThree

                                  ."</div>

                                </div>
                                <div class='search_merchant_title' onclick='openMerchant($merchantData_json)'>
                                  $merchant_name
                                </div>

                                <div class='templateLOCSTAT'>
                                <input type='submit' value='".$distance." km away with ".$waitingTime." minute wait' onclick='showMerchantWindow($long, $lat)' style='text-decoration: none' id='locationButton'>
                                </div>

                                <div class='search_liveQueue'>
                                </div>

                              </div>
                              ";

                    }

                  }

              }

            }


    }

    echo $str;


  ?>
