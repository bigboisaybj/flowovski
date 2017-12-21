<?php

include("../../config/config.php");
include("../classes/User.php");
include("../classes/Post.php");
include("../classes/Extras.php");
include("../classes/stripe/Stripe.php");

$extras = new Extras($con, $_REQUEST['itemPrice']);
$extras->createExtrasForm($_REQUEST);

?>
