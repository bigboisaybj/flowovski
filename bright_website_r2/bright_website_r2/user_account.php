<?php

require 'config/config.php';

if (isset($_SESSION['username'])) {
	$userLoggedIn = $_SESSION['username'];
	$user_details_query = mysqli_query($con, "SELECT * FROM users WHERE username='$userLoggedIn'");
	$user = mysqli_fetch_array($user_details_query);
}

 ?>

<div class="user_account_page">

<div class="title">
Account
</div>

<h4>Business</h4>
<br>
<h4>Advertise</h4>
<br>
<h4>Account</h4>
<br>
<h4>Dietary Concerns</h4>
<br>
<h4>Banking Details</h4>
<br>
<h4>Purchase History</h4>
<br>
<h4>Help</h4>
<br>
<h4>
<a href="includes/handlers/logout.php">
  Log Out
</a>
</h4>
<br>
<?php

echo $userLoggedIn;

 ?>
</div>
