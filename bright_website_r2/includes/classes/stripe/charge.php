<?php
  require_once('./stripe_config.php');
  include("./OrderProcess.php");

  ?>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

  <?php

  $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

  $amountToCharge = "999";

  if(isset($_POST['amount'])) {
    if($amountToCharge == 0 || $amountToCharge <= 0) {
      $amountToCharge = "888";
    }
    else {
      $amountToCharge = $_POST['amount'];
      $userName = $_POST['userName'];
      $merchantName = $_POST['merchantName'];
      $itemTitle = $_POST['itemTitle'];
      $itemPrice = $_POST['itemPrice'];
      $extras = $_POST['extras'];
    }
  }

  $token  = $_POST['stripeToken'];

  $customer = \Stripe\Customer::create(array(
      'email' => 'customer@example.com',
      'source'  => $token
  ));

  $charge = \Stripe\Charge::create(array(
      'customer' => $customer->id,
      'amount'   => $amountToCharge,
      'currency' => 'aud'
  ));

  $order = new OrderProcess($db, $userName, $merchantName, $itemTitle, $extras, $itemPrice);
  $order->sendToServer();

  echo '<h1>Successfully charged $ ' . $amountToCharge . ' !</h1>';

?>
