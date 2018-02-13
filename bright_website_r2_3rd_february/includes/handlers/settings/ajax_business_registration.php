<?php

if(isset($_POST['firstName'])){

  $userName = $_POST['username'];

  $data = array();

  $data = json_encode($userName);

  echo "<div id='business_selection_first'>

        <div class='business_registration_venue_title'>

          <div class='isLocatedAt'>
          is located at
          </div>

          <div class='business_registration_venue_title_input'>
            <input type='text' id='settings_business_venueName_input' placeholder='Venue name'>
          </div>



        </div>

        <br>
        <br>

        <div class='business_registration_venue_address'>

          <div class='business_registration_venue_address_input'>
          <div id='locationField'>
              <input id='autocomplete' placeholder='Venue address' onFocus='geolocate()'' type='text' onfocus='checkInput()'></input>
            </div>
          </div>

          <div class='business_registration_venue_confirm' onclick='registrationOpeningHours()' id='confirmRego'>
            Add opening hours
          </div>

        </div>

        <br>

        </div>

      ";
}

 ?>


 <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCAhmgLuZye36igU8P237oeUIy7y2Iyhog&libraries=places&callback=initAutocomplete"
 	 async defer></script>
