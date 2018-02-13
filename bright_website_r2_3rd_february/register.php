<?php
require 'config/config.php';
require 'includes/form_handlers/register_handler.php';
require 'includes/form_handlers/login_handler.php';
?>

  <div class="registration_login_section">

    <div id=login_title>
      <div class='connect_title_text'>
        Connect
      </div>
      <div id="fb-root">
    </div>
    <div class="fb-login-button" scope="public_profile, email, user_birthday" onlogin="checkLoginState();" data-max-rows="1" data-size="medium" data-button-type="continue_with" data-show-faces="false" data-auto-logout-link="false" data-use-continue-as="true"></div>
    </div>
    <br>
    <br>
    <br>
    <br>
    <div id=login_title>
      <div class='login_title_text'>
        Login
      </div>
    </div>
    <form action="register.php" method="POST">
      <input type="email" name="log_email" placeholder="Email" value="<?php
        if(isset($_SESSION['log_email'])) {
          echo $_SESSION['log_email'];
        }
        ?>"<required>
      <input type="password" name="log_password" placeholder="Password">
      <input type="submit" name="login_button" value="Login">
    <br>
    <br>
    </form>

  <div id=register_title>
    <div class='login_title_text'>
      Register
  </div>
    <form action="register.php" method="POST">
      <input type="text" name="reg_fname" placeholder="First Name" value="<?php
        if(isset($_SESSION['reg_fname'])) {
        }
        ?>"<required>
      <input type="text" name="reg_lname" placeholder="Last Name" value="<?php
        if(isset($_SESSION['reg_lname'])) {
        }
        ?>"<required>
          <br>
          <br>
      <input type="email" name="reg_email" placeholder="Email" value="<?php
        if(isset($_SESSION['reg_email'])) {
        }
        ?>"<required>
      <input type="password" name="reg_password" placeholder="Password" required>
      <input type="submit" name="register_button" value="Register">
    </div>
      <br>
      <br>
      <br>
      <div class=error_block>
        <?php
        if(in_array("Email or password was incorrect<br>", $error_array)) {
          echo "Email or password was incorrect<br><br>";
        }
        if(in_array("Email already in use<br>", $error_array)) {
          echo "Email already in use<br><br>";
          }
        if(in_array("Invalid email format<br>", $error_array)) {
          echo "Invalid email format<br><br>";
        }
        ?>
     </div>
    </form>
  </div>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script>

window.fbAsyncInit = function() {
  FB.init({
    appId      : '146623669379807',
    cookie     : true,
    xfbml      : true,
    version    : 'v2.11'
  });

  FB.AppEvents.logPageView();

};

(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = 'https://connect.facebook.net/en_GB/sdk.js#xfbml=1&autoLogAppEvents=1&version=v2.11&appId=146623669379807';
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function checkLoginState() {
  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });
}

function statusChangeCallback(response) {
 console.log('statusChangeCallback');

 if (response.status === 'connected') {
   var accessToken = response.authResponse.accessToken;
   login_with_facebook(accessToken);
  }
 else {
   document.getElementById('status').innerHTML = 'Please log ' +
     'into this app.';
 }
}


window.fbAsyncInit = function() {
  FB.init({
    appId      : '146623669379807',
    cookie     : true,
    xfbml      : true,
    version    : 'v2.11'
  });

  FB.AppEvents.logPageView();

};

 function login_with_facebook(token) {
  FB.api('/me', 'get', { access_token: token, fields: 'id, first_name, last_name, email, picture, birthday' }, function(response) {

    console.log(response.picture.data.url);

  $.ajax({
    url: "includes/form_handlers/facebook_login.php",
    type: "POST",
    data: {
        first_name: response.first_name,
        last_name: response.last_name,
        email: response.email,
        photo: response.picture.data.url,
        birthday: response.birthday,
    },
    success: function(response){
      window.location = 'index.php';
    }
  });
});


}

</script>
