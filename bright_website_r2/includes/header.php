<?php
require 'config/config.php';
include("includes/classes/User.php");
include("includes/classes/Post.php");
include("includes/classes/Item.php");
include("includes/classes/Distance.php");
include("includes/classes/Message.php");
include("includes/classes/stripe/OrderProcess.php");

$userLoggedIn = "";

if (isset($_SESSION['username'])) {
	$userLoggedIn = $_SESSION['username'];
	$user_details_query = mysqli_query($con, "SELECT * FROM users WHERE username='$userLoggedIn'");
	$user = mysqli_fetch_array($user_details_query);
}

?>

<html>
<head>
  <title>Bright</title>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
  <script src="assets/js/bright.js"></script>
	<script src="https://js.stripe.com/v3/"></script>
	<script>

	var username = '<?php echo $userLoggedIn; ?>';

	getCurrentLocation(username);

	</script>

  <link rel="stylesheet" type="text/css" href="assets/css/style.css">

</head>

<body>
  <div class="container">
    <div id="inner">
      <div class="header_title child" id='bright_top_left_text'>
				<a href='#' style="text-decoration: none">
          Bright
				</a>
      </div>
      <div class="header_input child">

				<div class="search">

        <form action="search.php" method="GET" name="search_form" class="input" style="height: 0px">
					<input id="search_text_input" type="text" onkeyup="getLiveSearchUsers(this.value, '<?php echo $userLoggedIn; ?>')" name="q" autocomplete="off" id="search_text_input" autofocus>
				</form>
			</div>
      </div>

      <div class="header_tabs child">
        <ul>
          <li id="user_header_title">
            <a href="javascript:void(0);" onclick="showUserHeader('user_account', '<?php echo $userLoggedIn; ?>')" style="text-decoration: none">
            <?php
            if ($userLoggedIn != "") {
              echo $user['first_name'];
            }
            else {
              echo "Sign-Up";
            }
             ?>
            </a>
           </li>
          <li id="about_header_title">
						<a href="javascript:void(0);" onclick="showUserHeader('about')" style="text-decoration: none">
            About
					</a>
          <li id="careers_header_title">
						<a href="javascript:void(0);" onclick="showUserHeader('careers')" style="text-decoration: none">
            Careers
          </a>
          </li>
        </ul>
      </div>

    </div>
  </div>

	<div class="accuracy_measure">
		<?php

			$prediction_accuracy = 0;
			$prediction_numerator = 0;
			$prediction_denominator = 0;

			echo "$prediction_accuracy% accurate in $prediction_numerator out of $prediction_denominator conditions";

		 ?>
	</div>

	<div class="search_results"></div>

	<div class="search_results_footer_empty"></div>
