<?php

$str = "";

  if(isset($_POST['merchantID'])) {
    
    $merchant_id = $_POST['merchantID'];
    $total_customers_commas = $_POST['totalCustomers'];
    $merchant_name = $_POST['merchantName'];
    $distance = $_POST['distance'];
    $waitingTime = $_POST['waitingTime'];
    $long = $_POST['long'];
    $lat = $_POST['lat'];
    $item_picture = $_POST['profile_pic'];
    $username = $_POST['username'];
    $itemTitle = $_POST['itemTitle'];
    $itemDescription = $_POST['itemDescription'];
    $itemHealth = $_POST['itemHealth'];
    $itemPrice = $_POST['itemPrice'];

    $str .=

            "
              <div class='merchant_$merchant_id'>

                <div class='templateSHARE'>
                  Share
                </div>

                <div class='templateCHAT'>
                  <input type='submit' value='Message' onclick='openMessages($merchant_id)' style='text-decoration: none'>
                </div>

                <div class='templateTOTALCUSTOMERS'>
                  $total_customers_commas<br>customers
                </div>

              </div>
              <div class='merchant_title'>
                $merchant_name
              </div>

                <div class='templateLOCSTAT'>
                <input type='submit' value='".$distance."km away with ".$waitingTime." minute wait' onclick='showMerchantWindow($long, $lat)' style='text-decoration: none' id='locationButton'>
                </div>

                <br>

                <div class='liveQueue'>
                </div>

                <div class='merchant_gallery'>
                  <img src=$item_picture width='640px' height='360px' id='merchant_photos'>
                  <div class='merchant_messages_window' style='height:0px; border:none;'></div>
                    <input type='hidden' id='merchant_message_type'>

                </div>

            <div class='items'>
                <div class='current_item'>

                <div class='merchant_product_title'>

                  <input type='submit' value='$itemTitle' onclick='createExtrasForm($data_json)' id='first_item_button'>

                </div>

                <div class='merchant_product_description'>
                  $itemDescription
                </div>

                <div class='merchant_product_health'>
                  $itemHealth kJ • $$itemPrice
                </div>

                </div>

                <div class='extras_$merchant_id'>

                  <div class='extras_summary'>
                    Extras

                    <div class='extras_item_list_$merchant_id' id='extra_list'>

                    </div>

                    <div class='extras_titles_$merchant_id' id='extra_title_list'>

                    </div>

                  </div>
                </div>

                <div class='total_$merchant_id' id='total_item_title'>

                Total
                <br>

                  <div class='total_price_$merchant_id' id='total_price_description'>

                  $$itemPrice

                  </div

                </div>

              </div>

              </div>


            ";

    }

    echo $str;

?>
