<?php

class Extras{
  private $con;
  private $itemPrice;
  private $extrasCount;
  private $extraSelection;

  public function __construct($con, $itemPrice) {
    $this->con =$con;
    $this->itemPrice = $itemPrice;
    $this->extrasCount = 0;
  }

  public function createExtrasForm($data) {

    $merchantID = $data['merchantID'];
    $totalCustomers = $data['totalCustomers'];
    $merchantName = $data['merchantName'];
    $distance = $data['distance'];
    $waitingTime = $data['waitingTime'];
    $long = $data['long'];
    $lat = $data['lat'];
    $profile_pic = $data['itemPicture'];
    $username = $data['username'];
    $itemPrice = $data['itemPrice'];
    $itemTitle = $data['itemTitle'];
    $itemDescription = $data['itemDescription'];
    $itemHealth = $data['itemHealth'];

    $str .= $this->showExtra($merchantID, $totalCustomers, $merchantName, $distance, $waitingTime, $long, $lat, $profile_pic, $username, $itemPrice, $itemTitle, $itemDescription, $itemHealth);

    echo $str;
  }

  public function showExtra($merchantID, $totalCustomers, $merchantName, $distance, $waitingTime, $long, $lat, $profile_pic, $username, $itemPrice, $itemTitle, $itemDescription, $itemHealth) {

    ?>
    <script>

    expandExtraArea();

    </script>

    <?php

    $str = "";

    $query = mysqli_query($this->con, "SELECT * FROM extras WHERE merchant_name='$merchantName'");

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

      $str .= "

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

      $str .= "

              <br>

              <div class='confirmToOpenStripe' onclick=createStripeForm($stripe_data_json) id='orderExtrasForm'>

              Order

              </div>

              <br>

            ";


    echo $str;
  }


}

?>
