<?php

include("../../config/config.php");
include("../classes/User.php");
include("../classes/Post.php");
include("../classes/Distance.php");
include("../classes/Item.php");
include("../classes/Price.php");
include("../classes/Extras.php");
include("../classes/AjaxSearchData.php");
include("../classes/SearchHistory.php");
include("../classes/stripe/OrderProcess.php");

$merchantPostFromSearch = new Post($con, $_REQUEST['username']);
$merchantPostFromSearch->loadMerchantFromSearch($_REQUEST);

?>
