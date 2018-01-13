<?php

require 'config/config.php';

if (isset($_SESSION['username'])) {
	$userLoggedIn = $_SESSION['username'];
	$user_details_query = mysqli_query($con, "SELECT * FROM users WHERE username='$userLoggedIn'");
	$user = mysqli_fetch_array($user_details_query);

	$userFirstName = $user['first_name'];
	$userLastName = $user['last_name'];

	$lat = $user['current_latitude'];
	$long = $user['current_longitude'];

	$dob = $user['dob'];

	$suburb = $user['current_suburb'];
	$city = $user['current_city'];

	$userPic = $user['profile_pic'];

	$userFullName = $userFirstName . " " . $userLastName;

	$userEmail = $user['email'];
	$dataEmail = $userEmail;

	$user_email = array();
	array_push($user_email, $userEmail);

	$dots = (strlen($user_email[0]) >= 15) ? "..." : "";
	$split = str_split($user_email[0], 15);
	$split = $split[0] . $dots;
	$userEmail = $split;

	$userMobile = $user['mobile'];
	$userPic = $user['profile_pic'];

}

 ?>

<div class="user_account_page">

<br>
<br>

<div class='settings_business_category'>
<div class="settings_subtitle">
	<h4>Business</h4>
</div>
<div class='settings_detail'>
	<div class='settings_title_one' onclick="openSettingsWindow('Registration', '<?php echo $userFirstName; ?>', 'Business', '<?php echo $userLoggedIn; ?>')">
		Registration
		<div class='settings_one_description'>
			Reach new, regular and old customers with Bright!
		</div>
	</div>
	<div class='settings_title_two' onclick="openSettingsWindow('Data_Analytics', '<?php echo $userFirstName; ?>', 'Business', '<?php echo $userLoggedIn; ?>')">
		Data Analytics
		<div class='settings_two_description'>
			The who, what, where, when, why and how.
		</div>
	</div>
	<div class='settings_title_three' onclick="openSettingsWindow('Features', '<?php echo $userFirstName; ?>', 'Business', '<?php echo $userLoggedIn; ?>')">
		Features
		<div class='settings_three_description'>
			Sell your products in new ways!
		</div>
	</div>
</div>
<br>
<hr>
<div class='settings_selection' id='settings_selection_Business'>
	<!--Default region -->


</div>
</div>
<br>
<div class='settings_account_category'>
<div class="settings_subtitle">
	<h4>Account</h4>
</div>
<div class='settings_detail'>
	<div class='settings_title_one' onclick="openSettingsWindow('User', '<?php echo $userFirstName; ?>', 'Account', '<?php echo $userLoggedIn; ?>', '<?php echo $userLastName; ?>')">
		User
		<div class='settings_one_description'>
			<div class='settings_one_user_photo'>
				<img class='image_settings' src='<?php echo $userPic; ?>'>
				<div class='settings_one_user_name'>
					<?php echo $userFullName; ?>
					<div class='settings_one_user_location_top_tier'>
						<?php echo $suburb; ?>
					</div>
				</div>
			</div>


		</div>
	</div>
	<div class='settings_title_two' onclick="openUserContactWindow('<?php echo $userLoggedIn; ?>', '<?php echo $dataEmail; ?>', '<?php echo $userMobile; ?>')">
		Contact details
		<div class='settings_two_description'>

			<div class=settings_two_description_a>
			<?php echo $userEmail; ?>
			</div>

			<div class=settings_two_description_b>
			<?php echo $userMobile; ?>
			</div>

		</div>
	</div>
	<div class='settings_title_three' onclick="openSettingsWindow('Password', '<?php echo $userFirstName; ?>', 'Account', '<?php echo $userLoggedIn; ?>', '<?php echo $userEmail; ?>')">
		Password
		<div class='settings_three_description'>
			Click to open.
		</div>
	</div>
</div>
<br>
<hr>
<div class='settings_selection' id='settings_selection_Account'>
	<!--Default region -->
</div>
</div>
<br>
<div class='settings_diet_category'>
<div class="settings_subtitle">
	<h4>Diet Filters</h4>
</div>
<div class='settings_detail'>
	<div class='settings_title_one' onclick="openSettingsWindow('Ingredient', '<?php echo $userFirstName; ?>', 'Diet', '<?php echo $userLoggedIn; ?>', '<?php echo $userEmail; ?>')">
		Ingredient filters
		<div class='settings_one_description'>
			Automatically replace ingredients with available substitutes.
		</div>
	</div>
	<div class='settings_title_two' onclick="openSettingsWindow('Calory', '<?php echo $userFirstName; ?>', 'Diet', '<?php echo $userLoggedIn; ?>', '<?php echo $userEmail; ?>')">
		Daily kJ cap
		<div class='settings_two_description'>
			Filter results to meet your kJ daily-limit.
		</div>
	</div>
	<div class='settings_title_three' onclick="openSettingsWindow('Allergies', '<?php echo $userFirstName; ?>', 'Diet', '<?php echo $userLoggedIn; ?>', '<?php echo $userEmail; ?>')">
		Allergies
		<div class='settings_three_description'>
			Block results that contain allergens.
		</div>
	</div>
</div>
<br>
<hr>
<div class='settings_selection' id='settings_selection_Diet'>
	<!--Default region -->
</div>
</div>
<br>
<div class='settings_payment_category'>
<div class="settings_subtitle">
	<h4>Payment</h4>
</div>
<div class='settings_detail'>
	<div class='settings_title_one'>
		Card details
		<div class='settings_one_description'>
			Click to open.
		</div>
	</div>
	<div class='settings_title_two'>
		Purchase history
		<div class='settings_two_description'>
			Click to open.
		</div>
	</div>
	<div class='settings_title_three'>
		Report
		<div class='settings_three_description'>
			Resolve payment issues.
		</div>
	</div>
</div>
<br>
<hr>
<div class='settings_selection'>
	<!--Default region -->
</div>
</div>
<br>
<div class='settings_help_category'>
<div class="settings_subtitle">
	<h4>Help</h4>
</div>
<div class='settings_detail'>
	<div class='settings_title_one' onclick="openSettingsWindow('FAQ', '<?php echo $userFirstName; ?>', 'Help', '<?php echo $userLoggedIn; ?>', '<?php echo $userEmail; ?>')">
		FAQ
		<div class='settings_one_description'>

			<input id="search_faq_input" placeholder="Search" type="text" onkeyup="liveFAQSearch(this.value, '<?php echo $userLoggedIn; ?>')" name="q" autocomplete="off" autofocus>

		</div>
	</div>
	<div class='settings_title_two'>
		Chat
		<div class='settings_two_description'>
			To: Bright
			<br>
			From: <?php echo $userFirstName; ?>
			Message:
		</div>
	</div>
	<div class='settings_title_three'>
		Legal
		<div class='settings_three_description'>
			Security and privacy are important. Learn how we protect you.
		</div>
	</div>
</div>
<br>
<hr>
<div class='settings_selection_Help'>
	<!--Default region -->
</div>
</div>
<br>
<div class='settings_about_category'>
<div class="settings_subtitle">
	<h4>About</h4>
</div>
<div class='settings_detail'>
	<div class='settings_title_one'>
		Team
		<div class='settings_one_description'>
			Just one engineer.
		</div>
	</div>
	<div class='settings_title_two'>
		Goal
		<div class='settings_two_description'>
			Human-Level Machine Intelligence
		</div>
	</div>
	<div class='settings_title_three'>
		Chapter One: Launch
		<div class='settings_three_description'>
			Ladies and gentlemen...
		</div>
	</div>
</div>
<br>
<hr>
<div class='settings_selection'>
	<!--Default region -->
</div>
</div>
<br>
<h4>
<a class='logout' href="includes/handlers/logout.php">
  Log Out • More
</a>
</h4>
<br>
