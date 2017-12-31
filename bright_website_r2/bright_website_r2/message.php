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

if(isset($_POST['post_message'])) {

	if(isset($_POST['message_body'])) {
		$body = mysqli_real_escape_string($con, $_POST['message_body']);
		$date = date("Y-m-d H:i:s");
		$message_obj->sendMessage($merchantName, $userLoggedIn, $body, $date);
	}
}

?>

<div class="messages" id="message_column">

  <?php

	echo "<h4>$merchantName</h4><hr><br>";

   ?>

   <div class="loaded_messages">
     <form action="#" method="POST">

			<textarea name='message_body' id='message_textarea' placeholder='Write your message ...'></textarea>
			<input type='submit' name='post_message' class='info' id='message_submit' value='Send'>


		</form>

</div>
