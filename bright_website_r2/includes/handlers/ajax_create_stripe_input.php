<?php

include("../../config/config.php");
include("../classes/User.php");
include("../classes/Post.php");
include("../classes/stripe/Stripe.php");

$stripe = new Stripe($con, $_REQUEST['itemPrice']);
$stripe->createPaymentForm($_REQUEST);

 ?>
