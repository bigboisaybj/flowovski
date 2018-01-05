<?php

if(isset($_POST['merchantID'])){

//Need to get item details.
//Then pass onto another ajax call that is the usual updatePost/extrasGenerator.


$itemName = $_POST['item'];
$merchantID = $_POST['merchantID'];
$totalCustomers = $_POST['totalCustomers'];
$merchantName = $_POST['merchantName'];
$distance = $_POST['distance'];
$waitingTime = $_POST['waitingTime'];
$long = $_POST['long'];
$lat = $_POST['lat'];
$item_picture = $_POST['profile_pic'];
$username = $_POST['username'];

//Account for results with "..."

$db = mysqli_connect("localhost", "root", "aloysius2", "bright");

$product_query = mysqli_query($db, "SELECT * FROM Dirt WHERE product_name='$itemName'");

if (mysqli_num_rows($product_query) > 0) {

  $product_row = mysqli_fetch_array($product_query);

  $item_id = $product_row['id'];
  $item_title = $product_row['product_name'];
  $item_description = $product_row['product_description'];
  $item_health = $product_row['product_health'];
  $item_price = $product_row['product_price'];
  $item_picture = $product_row['product_picture'];

  $item_price_dollars = $item_price/100;
  $item_price_cents = $item_price%100;

  $item_price_formatted = number_format(($item_price /100), 2, '.', ' ');

  $description_array = array();

  array_push($description_array, $item_description);

  $dots = (strlen($description_array[0]) >= 90) ? "..." : "";
  $split = str_split($description_array[0], 90);
  $split = $split[0] . $dots;
  $item_description = $split;

  //$arr = array('merchantID'=>$merchantID, 'totalCustomers'=>$totalCustomers, 'merchantName'=>$merchantName, 'distance'=>$distance, 'waitingTime'=>$waitingTime, 'long'=>$long, 'lat'=>$lat, 'profile_pic'=>$item_picture, 'username'=>$username, 'itemPrice'=>$item_price_formatted, 'itemTitle'=>$item_title, 'itemDescription'=>$item_description, 'itemHealth'=>$item_health);

  ?>
  <script>

  expandExtraArea();

  </script>

  <?php

  $extrasStr = "";

  $query = mysqli_query($db, "SELECT * FROM extras WHERE merchant_name='$merchantName'");

  $valueArray = array();
  $columnArray = array();

  $count = 0;

  while ($row = $query->fetch_assoc()) {
      foreach($row as $key => $value) {
        $count++;
        $finfo = $query->fetch_fields();
        if($count > 2) {
          array_push($valueArray, $value);

          }
        }
      $count = 0;

      foreach($finfo as $val) {
        $nameOfColumn = $val->name;
        $count++;
        if($count > 2) {
          array_push($columnArray, $nameOfColumn);

          }
        }

    }

    $completeValues = array();
    $completeColumns = array();

    $healthArray = array();

    for ($i = 0; $i < sizeof($valueArray); $i++) {

      for ($t = 0; $t < strlen($valueArray[$i]); $t++) {

        if ($valueArray[$i]{$t} == ",") {

          $healthResult = substr($valueArray[$i], $t+1);

          if(strlen($healthResult) == 1) {

            $healthResult = '&nbsp' . '&nbsp' . $healthResult;

          }

          array_push($healthArray, $healthResult);

          $healthResult = substr($valueArray[$i], 0, $t);

          $valueArray[$i] = $healthResult;

        }
      }

    }

    for($i = 0; $i < sizeof($valueArray); $i++) {

      if($valueArray[$i] != "0") {

        $roundedValue = number_format(($valueArray[$i] /100), 2, '.', ' ');
        $valueWithDollar = "$" . $roundedValue;

        $toCapitalize = substr($columnArray[$i],0,1);
        $toCapitalize = strtoupper($toCapitalize);
        $remainder = substr($columnArray[$i],1);

        $capitalized = $toCapitalize . $remainder;

        array_push($completeValues,$valueWithDollar);
        array_push($completeColumns,$capitalized);
      }

    }

    for ($i = 0; $i < sizeof($completeColumns); $i++) {

      for ($t = 0; $t < strlen($completeColumns[$i]); $t++) {

        if ($completeColumns[$i]{$t} === "_") {
          $completeColumns[$i]{$t} = " ";
        }

    }
  }

  $tempTitle = preg_replace('/\s+/', '', $itemTitle);
  $tempHealth = preg_replace('/\s+/', '', $itemHealth);

  $stripe_data = array();

  array_push($stripe_data, $username);
  array_push($stripe_data, $merchantName);
  array_push($stripe_data, $tempTitle);
  array_push($stripe_data, $itemPrice);
  array_push($stripe_data, $merchantID);

  $stripe_data_json = json_encode($stripe_data);

  for($v = 0; $v < sizeof($completeColumns); $v++) {

    $extra_data = array();

    $tempID = preg_replace('/\s+/', '', $merchantID);
    $tempName = preg_replace('/\s+/', '_', $completeColumns[$v]);
    $tempValue = preg_replace('/\s+/', '', $completeValues[$v]);
    $tempHealth = preg_replace('/\s+/', '', $healthArray[$v]);

    array_push($extra_data, $tempID);
    array_push($extra_data, $tempName);
    array_push($extra_data, $tempValue);
    array_push($extra_data, $tempHealth);
    array_push($extra_data, $itemPrice);

    $extra_data_json = json_encode($extra_data);

    $sliderData = array();
    array_push($sliderData, $tempName);

    $slider_data_json = json_encode($sliderData);

    $extrasStr .= "

            <div class='extraArea'>

            <div class='extrasTitle_$tempName' id='extrasTitle' onclick=updateExtras($extra_data_json)>
              + $completeColumns[$v]
            </div>

            <div class='extrasFeatures'>

            <div class='extrasHealthAndPrice' id='extrasHealthPrice_$tempName'>
              $healthArray[$v] kJ • $completeValues[$v] •&nbsp
            </div>

            <div class='originalExtrasValues_$tempName' id='originalExtrasContainer'>
            $healthArray[$v] • $completeValues[$v] •
            </div>

            <div class='sliderExtra'>
            <form name='extraForm' id='extraVolumeForm'>
                <output name='extraVolumeOutput' class='extraSliderOutput' id='extraVolumeOutputID_$tempName' onclick=animateSlider($slider_data_json)>x1</output>
                <input type='range' name='extraVolumeInput' class='extraSliderInput' id='extraVolumeInputID_$tempName' value='1' min='1' max='10' oninput='extraVolumeOutputID_$tempName.value = extraVolumeInputID_$tempName.value, nameOfItem($slider_data_json), updateValues(extraVolumeOutputID_$tempName.value)' style='width: 0; visibility: hidden'>
            </form>
            </div>
            </div>

            <br>

            ";
    }

    $extrasStr .= "

            <br>

            <div class='confirmToOpenStripe' onclick=createStripeForm($stripe_data_json) id='orderExtrasForm'>

            Order

            </div>

            <br>

          ";

  $str .=

          "
          <div class='templateCard'>

            <div class='merchant_$merchantID'>

              <div class='templateSHARE'>
                Share
              </div>

              <div class='templateCHAT'>
                <input type='submit' value='Message' onclick='openMessages($merchant_id)' style='text-decoration: none'>
              </div>

              <div class='templateTOTALCUSTOMERS'>
                $totalCustomers<br>customers
              </div>

            </div>
            <div class='merchant_title'>
              $merchantName
            </div>

              <div class='templateLOCSTAT'>
              <input type='submit' value='".$distance."km away with ".$waitingTime." minute wait' onclick='showMerchantWindow($long, $lat)' style='text-decoration: none' id='locationButton'>
              </div>

              <br>

              <div class='liveQueue'>
              </div>

              <div class='merchant_gallery'>
                <img src=$item_picture width='640px' height='360px' id='merchant_photos'>
                <div id='merchant_messages_window' class='merchant_window_$merchant_id' style='height:0px; border:none;'></div>
                  <input type='hidden' id='merchant_message_type'>

              </div>

          <div class='items'>
              <div class='current_item'>

              <div class='merchant_product_title'>

                <input type='submit' value='$item_title' id='first_item_button'>

              </div>

              <div class='merchant_product_description'>
                $item_description
              </div>

              <div class='merchant_product_health'>
                $item_health kJ • $$item_price_formatted
              </div>

              </div>

              <div class='extras_$merchantID'>

                <div class='extras_summary'>
                  Extras

                  <div class='extras_item_list_$merchantID' id='extra_list'>

                  </div>

                  <div class='extras_titles_$merchantID' id='extra_title_list'>

                  </div>

                </div>
              </div>

              <div class='total_$merchantID' id='total_item_title'>

              Total
              <br>

                <div class='total_price_$merchantID' id='total_price_description'>

                $$item_price_formatted

                </div

              </div>

            </div>

            </div>

            </div>

            <div class='extras_area_$merchantID' id='extras_area'>";


      $str .= $extrasStr;


      $str .= "

            </div>

            <div class='purchase_area'>

            </div>

            </div>


          ";

  echo $str;

}

}

?>
