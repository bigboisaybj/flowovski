<?php

include("includes/header.php");

?>
   <div class="dropdown_user_window" style="height:0px; border:none;"></div>
  		<input type="hidden" id="dropdown_data_type" value="">

      <input type="hidden" id="google_maps_type" value="">

    <div class="main_column column">

      <div class='user_present_orders'></div>

       <div class="posts_area"></div>
       <img id="loading" src="assets/images/icons/loading.gif">

    </div>

    <div class='merchant_data_container' style='visibility:hidden; height:0px'>

    </div>

    <script>

      var userLoggedIn = '<?php echo $userLoggedIn; ?>';

      $(document).ready(function() {

        $('#loading').show();

        //Original call for posts
        $.ajax({
          url: "includes/handlers/ajax_load_merchants.php",
          type: "POST",
          data: "page=1&userLoggedIn=" + userLoggedIn,
          cache: false,

          success: function(data) {
            $('#loading').hide();
            $('.posts_area').html(data);
          }
        });

        $.ajax({
          url: "includes/handlers/present_orders/ajax_load_present_orders.php",
          type: "POST",
          data: {
              userLoggedIn: userLoggedIn,
          },
          cache: false,

          success: function(data) {
            $('.user_present_orders').html(data);
          }
        });
      });

    </script>

</body>
</html>
