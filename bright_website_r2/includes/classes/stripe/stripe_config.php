<?php
require_once('vendor/autoload.php');

$stripe = array(
  "secret_key"      => "sk_test_fskcJJT3wUbOw4MamPZV8GN7",
  "publishable_key" => "pk_test_Tx3Oto71997X7nuR5VpgXNu6"
);

\Stripe\Stripe::setApiKey($stripe['secret_key']);
?>
