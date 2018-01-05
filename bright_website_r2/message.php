<?php
include("config/config.php");
include("includes/classes/User.php");
include("includes/classes/Post.php");
include("includes/classes/Item.php");
include("includes/classes/Message.php");

$userLoggedIn = "";
$merchantName = "";

if (isset($_SESSION['username'])) {
	$userLoggedIn = $_SESSION['username'];
	$user_details_query = mysqli_query($con, "SELECT * FROM users WHERE username='$userLoggedIn'");
	$user = mysqli_fetch_array($user_details_query);

}

$message_obj = new Message($con, $userLoggedIn);

if(isset($_POST['merchantID'])){

	$merchantID = $_POST['merchantID'];

	$merchantQuery = mysqli_query($con, "SELECT merchant_name FROM merchants WHERE id='$merchantID'");

	if(mysqli_num_rows($merchantQuery) > 0) {

		$fetchMerchantName = mysqli_fetch_array($merchantQuery);

		$merchantName = $fetchMerchantName['merchant_name'];
	}

}

$data = array();

array_push($data, $userLoggedIn);
array_push($data, $merchantName);
array_push($data, $merchantID);

$data_json = json_encode($data);

$str =  "<div class='messages_$merchantName' id='message_column'>

					<div class='loaded_messages_$merchantName' id='scroll_messages' style='height:300px; overflow:scroll'>".
						$message_obj->getMessages($merchantName)
					."</div>

					<hr>

				   <div class='message_post'>

						 <textarea name='message_body' class='message_input_$merchantName' id='message_textarea' placeholder='Write your message ...' onkeyup='enterKeySubmit($data_json, ".event.", ".this.")'></textarea>

				</div>";


				?>

				<script>

				var div = document.getElementById("scroll_messages");
				div.scrollTop = div.scrollHeight;

				</script>

				<?php

echo $str;
