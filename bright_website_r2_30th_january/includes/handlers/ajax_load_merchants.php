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
include("../classes/Live_Queue.php");

$limit = 2;

$posts = new Post($con, $_REQUEST['userLoggedIn']);
$posts->loadMerchants($_REQUEST, $limit);

?>
