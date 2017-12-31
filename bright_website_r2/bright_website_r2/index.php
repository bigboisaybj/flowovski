<?php

include("includes/header.php");

?>

   <div class="dropdown_user_window" style="height:0px; border:none;"></div>
  		<input type="hidden" id="dropdown_data_type" value="">

    <div class="google_maps_window" style="height:0; border:none">
          <div id="map"></div>
          <script>
            function initMap() {
              var uluru = {lat: -25.363, lng: 131.044};
              var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 4,
                center: uluru
              });
              var marker = new google.maps.Marker({
                position: uluru,
                map: map
              });
            }
          </script>
          <script async defer
          src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCAhmgLuZye36igU8P237oeUIy7y2Iyhog&callback=initMap">
          </script>
    </div>
      <input type="hidden" id="google_maps_type" value="">

    <div class="main_column column">

       <div class="posts_area"></div>
       <img id="loading" src="assets/images/icons/loading.gif">

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
      });


    </script>

</body>
</html>
