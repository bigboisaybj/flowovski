<?php

if(isset($_POST['lat'])){

  $lat = $_POST['lat'];
  $long = $_POST['long'];

  echo "
  <div class='google_maps_window'>
        <div id='map'></div>
        <script>
          function initMap() {
            var uluru = {lat: -25.363, lng: 131.044};
            var map = new google.maps.Map(document.getElementById('map'), {
              zoom: 8,
              center: uluru
            });
            var marker = new google.maps.Marker({
              position: uluru,
              map: map
            });

          }
        </script>

        <hr>
        Address and URL link 
  </div>

        ";
}


?>
<script>
function initMap() {

  var lat = '<?php echo $lat; ?>';
  var long = '<?php echo $long; ?>';

  lat = parseFloat(lat);
  long = parseFloat(long);

  var merchant = {lat: lat, lng: long};
  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 17,
    center: merchant
  });
  var marker = new google.maps.Marker({
    position: merchant,
    map: map
  });
}
</script>
<script async defer
src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCAhmgLuZye36igU8P237oeUIy7y2Iyhog&callback=initMap">
</script>
