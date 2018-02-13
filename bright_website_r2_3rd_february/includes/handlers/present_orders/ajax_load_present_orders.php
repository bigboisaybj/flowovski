<?php

if(isset($_POST['userLoggedIn'])){

  $userName = $_POST['userLoggedIn'];

  $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

  $result = "";

  $orders_query = mysqli_query($db, "SELECT * FROM sales WHERE userName='$userName' AND live='Yes'") or die(mysqli_error($db));

  if(mysqli_num_rows($orders_query) > 0){

    while($row = mysqli_fetch_array($orders_query)){

      $productName = $row['productName'];
      $price = $row['price'];
      $extras = $row['extras'];
      $merchantName = $row['merchantName'];
      $eta = $row['total_waiting_time'];

      $lengthOfPrice = strlen($price);

      $formatted_cents = substr($price, $lengthOfPrice - 2);

      $formatted_dollars = substr($price, 0, $lengthOfPrice - 2);

      $formated_price = $formatted_dollars . "." . $formatted_cents;

      if($extras == "None"){
        $extras = "No extras";
      }

      if($eta == "TBC"){
        $eta = "ASAP";
      }

      $result .= "<div class='order_box'>

                      <div class='order_details'>

                        <div class='order_extras'>
                          $extras
                        </div>

                        <div class='order_price'>
                          $$formated_price
                        </div>

                        <div class='order_eta'>
                          $eta
                        </div>

                      </div>

                    <div class='order_title'>
                      Order at $merchantName
                    </div>

                    <div class='order_product'>
                      of $productName with:
                    </div>




                  </div>
                  ";

    }

    $result .= "<br><br><div class='hr_order'><hr></div>";
  }

  echo $result;

}

 ?>
