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

    //Check whether customerID. If so, return just "Order"

    //else{

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $customer_id_query = mysqli_query($db, "SELECT stripe_customer_id FROM users WHERE username='$userName'");

    if(mysqli_num_rows($customer_id_query) == 1){

      $data = mysqli_fetch_array($customer_id_query);

      $customer_id = $data['stripe_customer_id'];

      $btn = "<div class='previous_stripe_order'>

                <form action='includes/classes/stripe/previous_user_order.php' method='post' id='past_stripe_form'>
                  <input type='submit' value='Pick-up and pay with previous card details' id='previous_order_submit'>

                  <br>
                  <br>
                  <hr id='order_line_break'>
                  <br>
                  <div class='order_add_to_booking order_choice' onclick='createStripeForm($data)'>
                    Add to a reserveration
                  </div>

                  <div class='order_pre_order_for_booking order_choice' onclick='createStripeForm($data)'>
                    Pre-Order for a future reservation
                  </div>


                  <input type='hidden' value='$customer_id' name='customer_id'>
                  <input type='hidden' name='amount' value='$this->itemPrice'>
                  <input type='hidden' name='userName' value='$userName'>
                  <input type='hidden' name='merchantName' value='$merchantName'>
                  <input type='hidden' name='itemTitle' value='$itemTitle'>
                  <input type='hidden' name='itemPrice' value='$itemPrice'>
                  <input type='hidden' name='extras' value='$extras'>
                </form>
              </div>

              ";

      echo $btn;
    }
    else{
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
                    </div>

                    <div id='card-errors' role='alert'></div>
                   </div>

                 <button id='stripe_button'>Confirm</button>
               </form>
            ";

            echo $str;
    }

  }


}

 ?>
