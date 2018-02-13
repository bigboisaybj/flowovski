<?php
  require_once('./stripe_config.php');
  include("./OrderProcess.php");

  $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

  if(isset($_POST['amount'])) {
    if($amountToCharge == 0 || $amountToCharge <= 0) {
      $amountToCharge = "888";
      $userName = $_POST['userName'];
      $merchantName = $_POST['merchantName'];
      $itemTitle = $_POST['itemTitle'];
      $itemPrice = $_POST['itemPrice'];
      $extras = $_POST['extras'];
      $customer_id = $_POST['customer_id'];
    }
    else {
      $amountToCharge = $_POST['amount'];
      $userName = $_POST['userName'];
      $merchantName = $_POST['merchantName'];
      $itemTitle = $_POST['itemTitle'];
      $itemPrice = $_POST['itemPrice'];
      $extras = $_POST['extras'];
      $customer_id = $_POST['customer_id'];
    }
  }

  $token  = $_POST['stripeToken'];

  $charge = \Stripe\Charge::create(array(
  "amount" => $amountToCharge, // $15.00 this time
  "currency" => "aud",
  "customer" => $customer_id,
  ));

  $order = new OrderProcess($db, $userName, $merchantName, $itemTitle, $extras, $itemPrice);
  $order->sendToServer();

  echo "Processing...";

  ?>

  
