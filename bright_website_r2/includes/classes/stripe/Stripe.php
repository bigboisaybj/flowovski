<script src="https://js.stripe.com/v3/"></script>
<script src="includes/classes/stripe/stripe_handler.js"></script>

<?php

require('stripe_config.php');

class Stripe {
  private $con;
  private $itemPrice;

  public function __construct($con, $itemPrice) {
    $this->con =$con;
    $this->itemPrice =$itemPrice;
  }

  public function createPaymentForm($data) {

    $userName = $data['userName'];
    $merchantName = $data['merchantName'];
    $itemTitle = $data['itemTitle'];
    $itemPrice = $data['itemPrice'];
    $extras = $data['extras'];

    $str = "
            <form action='includes/classes/stripe/charge.php' method='post' id='payment-form'>
              <input type='hidden' name='amount' value='$this->itemPrice'>
              <input type='hidden' name='userName' value='$userName'>
              <input type='hidden' name='merchantName' value='$merchantName'>
              <input type='hidden' name='itemTitle' value='$itemTitle'>
              <input type='hidden' name='itemPrice' value='$itemPrice'>
              <input type='hidden' name='extras' value='$extras'>

               <div class='form-row'>
                  <div id='card-element'>
                    <!-- a Stripe Element will be inserted here. -->
                  </div>

                  <!-- Used to display form errors -->
                  <div id='card-errors' role='alert'></div>
                 </div>

               <button id='stripe_button'>Confirm</button>
             </form>
          ";

          echo $str;

  }


}

 ?>
