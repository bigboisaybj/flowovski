var presentType = "";
var totalPrice;
var init = false;
var itemName = "";
var jsonData;
var placeSearch, autocomplete;
var componentForm = {
      street_number: 'short_name',
      route: 'long_name',
      locality: 'long_name',
      administrative_area_level_1: 'short_name',
      country: 'long_name',
      postal_code: 'short_name'
    };
var venueAddress = "";
var merchantRegoName = "";
var usr = "";
var allergenInWriting = "";
var lastTime = 0;
var hercules = new Array();
var date_reservation = "";
var time_reservation = "";



$(document).ready(function() {

  $('.button_holder').on('click', function() {
    document.search_form.submit();
  });

  $(window).scroll(function() {
    var height = $('.posts_area').height(); //div containing posts
    var scroll_top = $(this).scrollTop();
    var page = $('.posts_area').find('.nextPage').val();
    var noMorePosts = $('.posts_area').find('.noMorePosts').val();

    if((document.body.scrollHeight == document.body.scrollTop + window.innerHeight) && noMorePosts == 'false') {
      $('#loading').show();

      var ajaxReq = $.ajax({
        url: "includes/handlers/ajax_load_merchants.php",
        type: "POST",
        data: "page=" + page + "&userLoggedIn=" + userLoggedIn,
        cache: false,

        success: function(response) {
          $('.posts_area').find('.nextPage').remove(); //Removes current .nextPage
          $('.posts_area').find('.noMorePosts').remove(); //Removes current .nextPage

          $('#loading').hide();
          $('.posts_area').append(response);
        }
      });
    } //End if
    return false;
  }); //End (window).scroll(function())

});

function marquee(a, b) {
    var width = b.width();
    var start_pos = a.width();
    var end_pos = -width;

    function scroll() {

        if (b.position().left <= -width) {
            b.css('left', start_pos);
            scroll();
        }
        if (b.position().left > -width){
            time = (parseInt(b.position().left, 10) - end_pos) *
                (8000 / (start_pos - end_pos)); // Increase or decrease speed by changing value 10000
            b.animate({
                'left': -width
            }, time, 'linear', function() {
                scroll();
            });
        }
        else {
          return;

        }
    }

    b.css({
        'width': width,
        'left': start_pos
    });
    scroll(a, b);

    b.mouseenter(function() {     // Remove these lines
        b.stop();                 //
        b.clearQueue();           // if you don't want
    });                           //
    b.mouseleave(function() {     // marquee to pause
        scroll(a, b);             //
    });                           // on mouse over

}

function getNextSlide(data){

  $.ajax({
    url: "includes/classes/Slideshow/next_slide.php",
    type: "POST",
    data: {
      merchantID: data[0],
    },
    success: function(response){
      $("#merchant_photos_"+data[0]).replaceWith(response);
    }
  });
}

function getPreviousSlide(data){

  //TBC
}



function initAutocomplete() {
    // Create the autocomplete object, restricting the search to geographical
    // location types.
    autocomplete = new google.maps.places.Autocomplete(
        /** @type {!HTMLInputElement} */(document.getElementById('autocomplete')),
        {types: ['geocode']});

    // When the user selects an address from the dropdown, populate the address
    // fields in the form.
    autocomplete.addListener('place_changed', fillInAddress);
  }

function fillInAddress() {
    venueAddress = "";
    // Get the place details from the autocomplete object.
    var place = autocomplete.getPlace();

    // Get each component of the address from the place details
    // and fill the corresponding field on the form.
    for (var i = 0; i < place.address_components.length; i++) {
      var addressType = place.address_components[i].types[0];
      if (componentForm[addressType]) {
        var val = place.address_components[i][componentForm[addressType]];
        var temp = val + " ";
        venueAddress += temp ;
        //document.getElementById(addressType).value = val;
      }
    }
  }

  // Bias the autocomplete object to the user's geographical location,
  // as supplied by the browser's 'navigator.geolocation' object.
  function geolocate() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(function(position) {
        var geolocation = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };
        var circle = new google.maps.Circle({
          center: geolocation,
          radius: position.coords.accuracy
        });
        autocomplete.setBounds(circle.getBounds());
      });
    }
  }

function processUserInputForUserNameChange(data){

usrChange = document.getElementById('account_user_name_change_input').value;

if(usrChange != " "){
  usrChangeLogDetails = data;

  $.ajax({

    url: "includes/handlers/user_settings/ajax_update_username.php",
    type: "POST",
    data: {
          userName: usrChange,
          originalUser: data,
    },
  });
}
}

function animateDiv(id){
    var newq = makeNewPosition();
    var oldq = $(id).offset();
    var newH = newq[1] * -1;

    $(id).animate({ top: newq[0], left: newH }, 15000, function(){
      animateDiv();
    });

};

function makeNewPosition(){

    // Get viewport dimensions (remove the dimension of the div)
    var h = $(window).height() - 40;
    var w = $(window).width() - 40;

    var nh = Math.floor(Math.random() * h);
    w -= 10;

    while(nh > 80){
      var nh = Math.floor(Math.random() * h);
    }
    return [nh, w];

}

function messageFocus(elem){
  var x = window.scrollX, y = window.scrollY;
  elem.focus();
  window.scrollTo(x,y);
}

function openMerchantBooking(data){

      var merchantID = data[0];
      var userName = data[1];

      var ajaxreq = $.ajax({
      url: "booking.php",
      type: "POST",
      data: {
            merchantID: data[0],
            userName: data[1],
      },

      success: function(response) {
        $(".merchant_window_"+merchantID).html(response);
        $(".merchant_window_"+merchantID).css({"padding" : "0px", "height" : "360px"});
        $("#merchant_photos_"+merchantID).css({"padding" : "0px", "height" : "0px"});
        $("#chatButton_"+merchantID).css({"color" : "#6E6E7A"});
        $("#customers_"+merchantID).css({"color" : "#6E6E7A"});
        $("#share_"+merchantID).css({"color" : "#0070c9"});
        $("#gallery_controller_"+merchantID).css({"visibility" : "hidden"});
        $(".location_button_"+merchantID).css({"color" : "#6E6E7A"});
      }
    });


}


function openMessages(merchantID){

      var ajaxreq = $.ajax({
      url: "message.php",
      type: "POST",
      data: "merchantID=" + merchantID,

      success: function(response) {
        $(".merchant_window_"+merchantID).html(response);
        $(".merchant_window_"+merchantID).css({"padding" : "0px", "height" : "360px"});
        messageFocus(document.getElementById('message_textarea_'+merchantID));
        $("#merchant_photos_"+merchantID).css({"padding" : "0px", "height" : "0px"});
        $("#chatButton_"+merchantID).css({"color" : "#0070c9"});
        $("#customers_"+merchantID).css({"color" : "#6E6E7A"});
        $("#share_"+merchantID).css({"color" : "#6E6E7A"});
        $("#gallery_controller_"+merchantID).css({"visibility" : "hidden"});
        $(".location_button_"+merchantID).css({"color" : "#6E6E7A"});
      }
    });
}

function openMerchantItemsDisplay(merchantID){

  $(".merchant_window_"+merchantID).html("");
  $(".merchant_window_"+merchantID).css({"padding" : "0px", "height" : "0px"});
  $("#merchant_photos_"+merchantID).css({"padding" : "0px", "height" : "360px"});
  $("#chatButton_"+merchantID).css({"color" : "#6E6E7A"});
  $("#customers_"+merchantID).css({"color" : "#0070c9"});
  $("#share_"+merchantID).css({"color" : "#6E6E7A"});
  $("#gallery_controller_"+merchantID).css({"visibility" : "visible"});
  (".location_button_"+merchantID).css({"color" : "#6E6E7A"});

}

function enterKeySubmit(data, key, element){
  if(key.keyCode === 13) {
    sendMessage(data,element);
  }
}

function enterKeySubmit_help(data, key, element){
  if(key.keyCode === 13) {
    send_help_message(data,element);
  }
}

function send_help_message(data, element){

  var message = document.getElementById("message_textarea_help").value;

  element.value = '';

  $.ajax({
    url: "includes/handlers/help_chat/help_send_message.php",
    type: "POST",
    data: {
        userName: data,
        message: message,
    },
    success: function(response){
      $(".loaded_messages_help").append(response);

    }

  });
}

function sendMessage(data, element){

  var merchantID = data[2];
  var message = document.getElementById("message_textarea_"+merchantID).value;

  element.value = '';

  $.ajax({
    url: "includes/handlers/ajax_send_message.php",
    type: "POST",
    data: {
        userName: data[0],
        merchantName: data[1],
        merchantID: data[2],
        message: message,
    },
    success: function(response){
      $(".loaded_messages_"+merchantID).append(response);

    }

  });

}

function showUserHeader(type, userLoggedIn, merchantManager) {

  if (userLoggedIn != "" && type == 'user_account') {
    checkDropdownOrItemsWindows("dropdown");
    showUserPage(type, merchantManager);
  }
  else {
    if(presentType != type) {
      typeOpener(type);
      presentType = type;
      return;
    }

    if($(".dropdown_user_window").css("height") == "0px") {
      typeOpener(type);
      presentType = type;
      return;
    }

    else {
      $(".dropdown_user_window").html("");
      $(".dropdown_user_window").css({"padding" : "0px", "height" : "0"});
      $(".search_results").css({"margin-top" : "0px"})
      $(".posts_area").css({"visibility" : "visible"});
      $(".main_column").css({"height" : "655px"});
      return;
    }
  }

}

function typeOpener(type) {

  checkDropdownOrItemsWindows("dropdown");

  if(type == 'user_account') {
    pageName = "register.php";
  }
  else if (type == 'about') {
    pageName = "about.php";

  }
  else if (type == 'careers') {
    pageName = "careers.php";

  }

  var ajaxreq = $.ajax({
    url: pageName,
    type: "POST",

    success: function(response) {
      $(".dropdown_user_window").html(response);
      $(".dropdown_user_window").css({"padding" : "0px", "height" : "80px"});
      $("#dropdown_data_type").val(type);
      $(".posts_area").css({"visibility" : "hidden"});
      $(".main_column").css({"height" : "0px"});
    }
  });
}

function showUserPage(type, merchantManager) {

  //TODO: CHANGE TO '== no' --> build merchantDropDown.
  if(merchantManager != "yes"){

    if(presentType != type) {

        var ajaxreq = $.ajax({
          url: "user_account.php",
          type: "POST",

          success: function(response) {
            $(".dropdown_user_window").html(response);
            $(".dropdown_user_window").css({"padding" : "0px", "height" : "2000px"});
            $("#dropdown_data_type").val(type);
            $(".posts_area").css({"visibility" : "hidden"});
            $(".main_column").css({"height" : "0px"});
          }
        });
        presentType = type;
        return;
    }

    if($(".dropdown_user_window").css("height") == "0px") {
      var ajaxreq = $.ajax({
        url: "user_account.php",
        type: "POST",

        success: function(response) {
          $(".dropdown_user_window").html(response);
          $(".dropdown_user_window").css({"padding" : "0px", "height" : "800px"});
          $("#dropdown_data_type").val(type);
          $(".posts_area").css({"visibility" : "hidden"});
          $(".main_column").css({"height" : "0px"});
        }
      });
      presentType = type;
      return;
    }

    else {
      $(".dropdown_user_window").html("");
      $(".dropdown_user_window").css({"padding" : "0px", "height" : "0"});
      $(".posts_area").css({"visibility" : "visible"});
      $(".main_column").css({"height" : "655px"});
      return;
    }

  }
  else {
    //TODO: ADD MERCHANT WINDOW
  }

}

function uploadMenu(){

  var mondayOpening = document.getElementById("monday_opening").value;
  var mondayClosing = document.getElementById("monday_closing").value;
  var tuesdayOpening = document.getElementById("tuesday_opening").value;
  var tuesdayClosing = document.getElementById("tuesday_closing").value;
  var wednesdayOpening = document.getElementById("wednesday_opening").value;
  var wednesdayClosing = document.getElementById("wednesday_closing").value;
  var thursdayOpening = document.getElementById("thursday_opening").value;
  var thursdayClosing = document.getElementById("thursday_closing").value;
  var fridayOpening = document.getElementById("friday_opening").value;
  var fridayClosing = document.getElementById("friday_closing").value;
  var saturdayOpening = document.getElementById("saturday_opening").value;
  var saturdayClosing = document.getElementById("saturday_closing").value;
  var sundayOpening = document.getElementById("sunday_opening").value;
  var sundayClosing = document.getElementById("sunday_closing").value;

  var openingHours = new Array(mondayOpening, mondayClosing, tuesdayOpening, tuesdayClosing, wednesdayOpening, wednesdayClosing, thursdayOpening, thursdayClosing, fridayOpening, fridayClosing, saturdayOpening, saturdayClosing, sundayOpening, sundayClosing);

  for(i in openingHours){
    if((openingHours[i].includes(":")) && (openingHours[i].includes("am")) || (openingHours[i].includes("AM")) || (openingHours[i].includes("pm")) || (openingHours[i].includes("PM"))) {
      openingHours[i] = convertAmPmToTime(openingHours[i]);
    }

    else if (openingHours[i].includes(":")) {
      openingHours[i] = convertColonToTime(openingHours[i]);
    }

    else{
        if((openingHours[i].includes("am")) || (openingHours[i].includes("AM")) || (openingHours[i].includes("pm")) || (openingHours[i].includes("PM"))) {
          openingHours[i] = convertAmPmToTime(openingHours[i]);
        }
        else{
          openingHours[i] = convertTwentyFourToTime(openingHours[i]);
        }
    }
  }


  //Send details to server

  $.ajax({
    url: "includes/handlers/settings/ajax_business_files.php",
    type: "POST",
    data: {
          mondayOpening: mondayOpening,
          mondayClosing: mondayClosing,
          tuesdayOpening: tuesdayOpening,
          tuesdayClosing: tuesdayClosing,
          wednesdayOpening: wednesdayOpening,
          wednesdayClosing: wednesdayClosing,
          thursdayOpening: thursdayOpening,
          thursdayClosing: thursdayClosing,
          fridayOpening: fridayOpening,
          fridayClosing: fridayClosing,
          saturdayOpening: saturdayOpening,
          saturdayClosing: saturdayClosing,
          sundayOpening: sundayOpening,
          sundayClosing: sundayClosing,
          userName: getTempUser(),
          venueName: getMerchantRegoName(),
          },

      success: function(response) {
        $("#openingHours_input").replaceWith(response);
      }
  });
}

function processReservationDate(data){

  var result = "";

  var merchantId = data[0];

  var reservationDate = document.getElementsByClassName("booking_where_"+merchantId)[0].value;

  var today = new Date();
  var dd = today.getDate();
  var mm = today.getMonth()+1;
  var yyyy = today.getFullYear();

  if(dd<10) {
      dd = '0'+dd;
  }

  if(mm<10) {
      mm = '0'+mm;
  }

  dd = parseInt(dd);
  mm = parseInt(mm);
  yyyy = parseInt(yyyy);

  today = dd + '/' + mm + '/' + yyyy;

  if(mm == 01 || mm == 03 || mm == 05 || mm == 07 || mm == 08 || mm == 10 || mm == 12){
    if(dd == 31){
      tomorrow = 01 + '/' + mm+1 + '/' + yyyy;
    }
  }

  if(mm == 04 || mm == 06 || mm == 09 || mm ==11){
    if(dd == 30){
      tomorrow = 01 + '/' + mm+1 + '/' + yyyy;
    }
  }

  if(mm == 02){
    if(dd == 28){
      tomorrow = 01 + '/' + mm+1 + '/' + yyyy;
    }
  }

  tomorrow = dd+1 +'/' + mm +'/' +yyyy;


  //Today
  if(reservationDate.includes("Today") || reservationDate.includes("today")){
    result = today;
  }
  //"Tomorrow"
  if(reservationDate.includes("Tomorrow") || reservationDate.includes("tomorrow")){
    result = tomorrow;
  }

  //"Date" eg. "24/1/17"
  if(reservationDate.includes("/")){
    var dateIndex = reservationDate.indexOf("/");
    var dateInput = reservationDate.substring(0, dateIndex);

    var monthInput = reservationDate.substring(dateIndex+1, dateIndex+3);

    if(monthInput.includes("/")){
      monthInput = monthInput.substring(0, 1);

      //var yearInput = reservationDate.substring(dateIndex+3);
    }
    /*
    else{
      var yearInput = reservationDate.substring(dateIndex+4);
    }
    */
    var yearInput = 2018;

    dateInput = parseInt(dateInput);
    monthInput = parseInt(monthInput);

    if(dateInput<10) {
        dateInput = '0'+dateInput;
    }

    if(monthInput<10) {
        monthInput = '0'+monthInput;
    }

    if((1 <= dateInput && dateInput < 32) && (1 <= monthInput && monthInput < 13) && (yearInput == 2018)){
      result = dateInput + "/" + monthInput + "/" + "2018";
    }

  }
  if(result != ""){
    date_reservation = result;
    getWeatherForecastForReservation(result, data[1], data[2], merchantId);
  }
}

function getWeatherForecastForReservation(date, latitude, longitude, merchantId){

  //This is limited to only the forecast of PRESENT DAY!!!!

  var temperature = "";
  var rain = "";

  $.ajax({

    url: "http://api.apixu.com/v1/forecast.json?key=be5e42eef0ae467d8db05839171812&q=" + latitude + "," + longitude,
    type: "GET",
    dataType: "json",
    success:function(data){
      temperature = data.forecast.forecastday[0].day.avgtemp_c;
      rain = data.forecast.forecastday[0].day.totalprecip_mm;

      report = temperature + "-degrees Celsius with " + rain + "mm of rain";
      $("#booking_weather_"+merchantId).html(report);
    }
  });
}

function processReservationTime(data){

  var merchantId = data[0];
  var longitude = data[1];
  var latitude = data[2];

  var reservationTime = document.getElementsByClassName("booking_time_"+merchantId)[0].value;

    if((reservationTime.includes(":")) && (reservationTime.includes("am")) || (reservationTime.includes("AM")) || (reservationTime.includes("pm")) || (reservationTime.includes("PM"))) {
      reservationTime = convertAmPmToTime(reservationTime);
    }

    else if (reservationTime.includes(":")) {
      reservationTime = convertColonToTime(reservationTime);
    }

    else{
        if((reservationTime.includes("am")) || (reservationTime.includes("AM")) || (reservationTime.includes("pm")) || (reservationTime.includes("PM"))) {
          reservationTime = convertAmPmToTime(reservationTime);
        }
        else{
          reservationTime = convertTwentyFourToTime(reservationTime);
        }
    }

    if(reservationTime != null){
      getBusyReport(reservationTime, merchantId);
    }
}

function getBusyReport(time, merchantId){

  var date = date_reservation.substring(0,2);
  var month= date_reservation.substring(3,5);
  var year = date_reservation.substring(6);

  var d = new Date('2018-01-24');
  var days = ['sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday'];

  dayOfReservation = days[d.getDay()];

  var hour = time.substring(0,2);
  var minutes = time.substring(3);

  if(hour == "00"){
    hour = "zero";
  }
  if(hour == "01"){
    hour = "one";
  }
  if(hour == "02"){
    hour = "two";
  }
  if(hour == "03"){
    hour = "three";
  }
  if(hour == "04"){
    hour = "four";
  }
  if(hour == "05"){
    hour = "five";
  }
  if(hour == "06"){
    hour = "six";
  }
  if(hour == "07"){
    hour = "seven";
  }
  if(hour == "08"){
    hour = "eight";
  }
  if(hour == "09"){
    hour = "nine";
  }
  if(hour == "10"){
    hour = "ten";
  }
  if(hour == "11"){
    hour = "eleven";
  }
  if(hour == "12"){
    hour = "twelve";
  }
  if(hour == "13"){
    hour = "thirteen";
  }
  if(hour == "14"){
    hour = "fourteen";
  }
  if(hour == "15"){
    hour = "fifteen";
  }
  if(hour == "16"){
    hour = "sixteen";
  }
  if(hour == "17"){
    hour = "seventeen";
  }
  if(hour == "18"){
    hour = "eighteen";
  }
  if(hour == "19"){
    hour = "nineteen";
  }
  if(hour == "20"){
    hour = "twenty";
  }
  if(hour == "21"){
    hour = "twenty_one";
  }
  if(hour == "22"){
    hour = "twenty_two";
  }
  if(hour == "23"){
    hour = "twenty_three";
  }

  minutes = minutes.trim();

  tempMin = parseInt(minutes);

  if(0 <= tempMin && tempMin <= 15){
    min = "_first_quarter";
  }
  if(16 <= tempMin && tempMin <= 30){
    min = "_second_quarter";
  }
  if(31 <= tempMin && tempMin <= 45){
    min = "_third_quarter";
  }
  if(46 <= tempMin && tempMin < 60){
    min = "_zero";
  }

  var parsed_time = hour + min;

  if(parsed_time){

    time_reservation = time;

    $.ajax({
      url: "includes/handlers/booking/get_busy.php",
      type: "POST",
      data: {
        time: parsed_time,
        id: merchantId,
        day: dayOfReservation,
      },
      success: function(response){
        $("#booking_time_"+merchantId).html(response);
      }
    });
  }

}

function processReservationSeats(data){

  console.log("CALLED");

  var merchantID = data[0];

  var seats = document.getElementsByClassName("booking_seats_"+merchantID)[0].value;

  if(seats != "" || seats != " "){
    $.ajax({
      url: "includes/handlers/booking/get_availability.php",
      type: "POST",
      data: {

        merchantID: merchantID,
        date: date_reservation,
        time: time_reservation,

      },
      success: function(response){
        $("#booking_seats_"+merchantID).html(response);
      }
    });
  }
}

function processReservation(data){

  var merchantID = data[0];
  var userName = data[1];

  console.log(merchantID);

  var seats = document.getElementsByClassName("booking_seats_"+merchantID)[0].value;

  if(seats != "" || seats != " "){
    $.ajax({
      url: "includes/handlers/booking/submit_reservation.php",
      type: "POST",
      data: {

        merchantID: merchantID,
        date: date_reservation,
        time: time_reservation,
        seats: seats,
        userName: userName,

      },
      success: function(response){
        $(".merchant_window_"+merchantID).html(response);
      }
    });
  }

}


function showSubmitRegistration(){

  if($("#menu_uploader").css("visibility") == "hidden"){
    $("#menu_uploader").css({"visibility" : "visible"});
  }
}

function convertAmPmToTime(time){

  var result = "";

  if(time.includes("AM") || time.includes("am")){

    var timeLength = time.length;

    var timeWithoutPeriod = time.substring(0, timeLength-2);

    if(timeWithoutPeriod.includes(" ")){
      var temp = timeWithoutPeriod.indexOf(" ");
      timeWithoutPeriod = timeWithoutPeriod.substring(0, temp);
    }

    var timeInt = parseInt(timeWithoutPeriod);

    if( -1 < timeInt && timeInt <= 12){

      if(timeWithoutPeriod.length == 1){
        result = "0" + timeWithoutPeriod + ":00";
      }
      if(timeWithoutPeriod.length == 2){
        result = timeWithoutPeriod + ":00";
      }
      if(timeWithoutPeriod.length == 3){
        var minute = timeWithoutPeriod.substring(2,3);
        var hour = timeWithoutPeriod.substring(0,2);

        result = hour + ":" + minute +"0";
      }
      if(timeWithoutPeriod.length == 4){
        var minute = timeWithoutPeriod.substring(2,4);
        var hour = timeWithoutPeriod.substring(0,2);

        result = hour + ":" + minute;
      }
      return result;
    }
    else{
      //Handle screw ups
    }

  }
  else if (time.includes("PM") || time.includes("pm")) {

    var pmHour = "";
    var pmMin = "";

    var timeLength = time.length;

    var timeWithoutPeriod = time.substring(0, timeLength-2);

    if(timeWithoutPeriod.includes(" ")){
      var temp = timeWithoutPeriod.indexOf(" ");
      timeWithoutPeriod = timeWithoutPeriod.substring(0, temp);
    }

    var timeInt = parseInt(timeWithoutPeriod);

    if(timeWithoutPeriod.length <= 2){
      timeInt += 12;
      timeWithoutPeriod = timeInt.toString();
    }
    if(timeWithoutPeriod.length == 3){
      pmHour = timeWithoutPeriod.substring(0,2);
      var tempHour = parseInt(pmHour);
      tempHour += 12;

      timeInt = tempHour;
      pmHour = tempHour.toString();
      pmMin = timeWithoutPeriod.substring(2,3);
      timeWithoutPeriod = pmHour + pmMin;

    }
    if(timeWithoutPeriod.length == 4){
      pmHour = timeWithoutPeriod.substring(0,2);
      var tempHour = parseInt(pmHour);
      tempHour += 12;

      timeInt = tempHour;
      pmHour = tempHour.toString();
      pmMin = timeWithoutPeriod.substring(2,4);
      timeWithoutPeriod = pmHour + pmMin;
    }

    if( 12 < timeInt && timeInt <= 24){

      if(timeWithoutPeriod.length == 2){
        result = timeWithoutPeriod + ":00";
      }
      if(timeWithoutPeriod.length == 3){
        var minute = timeWithoutPeriod.substring(2,3);
        var hour = timeWithoutPeriod.substring(0,2);

        result = hour + ":" + minute +"0";
      }
      if(timeWithoutPeriod.length == 4){
        var minute = timeWithoutPeriod.substring(2,4);
        var hour = timeWithoutPeriod.substring(0,2);

        result = hour + ":" + minute;
      }
      return result;
    }
  }
}

function convertTwentyFourToTime(time){

  if(time.length == 4){

    var hour = time.substring(0,2);

    var minutes = time.substring(2,4);

    var result = hour + ":" + minutes;
    return result;

  }
}

function convertColonToTime(time){

  var hourCheck = 0;
  var minCheck = 0;

  var colon = time.indexOf(":");

  var hour = time.substring(0, colon);

  if(hour.length == 1){
    hour = "0" + hour;
  }

  var hourInt = parseInt(hour);

  var minutes = time.substring(colon+1);

  var minutesInt = parseInt(minutes);

  if(-1 < hourInt && hourInt <=24){
    hourCheck = 1;
  }

  if(-1 < minutesInt && minutesInt <=59){
    minCheck = 1;
  }

  if(hourCheck == "1" && minCheck == "1"){
    result = hour + ":" + minutes;
    return result;

  }


}

function checkInput_Files(){
  var confirmRegoStepTwo = "<div class='business_registration_hours_confirm' onclick='uploadMenu()' id='confirmHours'>Add menu</div>";

  if(document.getElementById("confirmHours") == null){
    $(".openingHours_Table").append(confirmRegoStepTwo);
  }
}

function tempUser(data){
  usr = data;
}

function getTempUser(data){
  return usr;
}

function updateUserDetails(data){
  tempUser(data);

  var confirmRegoStepOne = "<div class='user_update_confirmation' onclick='confirmUserUpdateSubmit()' id='confirmUserUpdate'>Update</div>";

  if(document.getElementById("confirmUserUpdate") == null){
    $("#settings_selection_Account").append(confirmRegoStepOne);
  }

}

function confirmUserUpdateSubmit(){

  var userNameUpdate;

  if(document.getElementById("account_user_name_change_input") != null){

    userNameUpdate = document.getElementById("account_user_name_change_input").value;

    $.ajax({
      url: "includes/handlers/user_settings/ajax_update_username.php",
      type: "POST",
      data: {

        originalUser: getTempUser(),
        userName: userNameUpdate,

      },

      success: function(response) {
        $(".user_update_confirmation").remove();
        $("#settings_selection_Account").append(response);
      }
    });
  }
}

function checkInput(data){

  tempUser(data);

  var confirmRegoStepOne = "<div class='business_registration_venue_confirm' onclick='registrationOpeningHours()' id='confirmRego'>Add opening hours</div>";

  if(document.getElementById("confirmRego") == null){
    $(".business_registration_venue_address_input").append(confirmRegoStepOne);
  }

}

function showSettingsHidden(){

  $(".showOthers").remove();

  if($(".settings_business_category").css("visibility") == "hidden") {
    $(".settings_business_category").css({"visibility":"visible"});
  }

  if($(".settings_account_category").css("visibility") == "hidden") {
    $(".settings_account_category").css({"visibility":"visible"});
  }

  if($(".settings_diet_category").css("visibility") == "hidden") {
    $(".settings_diet_category").css({"visibility":"visible"});
  }

  if($(".settings_payment_category").css("visibility") == "hidden") {
    $(".settings_payment_category").css({"visibility":"visible"});
  }

  if($(".settings_help_category").css("visibility") == "hidden") {
    $(".settings_help_category").css({"visibility":"visible"});
  }

  if($(".settings_about_category").css("visibility") == "hidden") {
    $(".settings_about_category").css({"visibility":"visible"});
  }

  if($(".logout").css("visibility") == "hidden") {
    $(".logout").css({"visibility":"visible"});
  }
}

function addMerchantRegoName(merchantName){
  merchantRegoName = merchantName;
}

function getMerchantRegoName(){
  return merchantRegoName;
}

function registrationOpeningHours(){

  venueName = document.getElementById("settings_business_venueName_input").value;

  //Send details to server
  addMerchantRegoName(venueName);

  $.ajax({
    url: "includes/handlers/settings/ajax_business_openingHours.php",
    type: "POST",
    data: {
          venueName: venueName,
            },
    success: function(response){
      $("#business_selection_first").replaceWith(response);
    }

  });

}

function closeSettingsOptions(settingSelected){

  var showRest = "<div class='showOthers' id='showOthersOption' onclick='showSettingsHidden()'>Show options</div>";

  if(settingSelected == "Business"){
    if(document.getElementById("showOthersOption") == null){
      $(".settings_business_category").append(showRest);
    }
    $(".settings_account_category").css({"visibility":"hidden"});
    $(".settings_diet_category").css({"visibility":"hidden"});
    $(".settings_payment_category").css({"visibility":"hidden"});
    $(".settings_help_category").css({"visibility":"hidden"});
    $(".settings_about_category").css({"visibility":"hidden"});
    $(".logout").css({"visibility":"hidden"});
  }

  if(settingSelected == "Account"){
    if(document.getElementById("showOthersOption") == null){
      $(".settings_account_category").append(showRest);
    }
    $(".settings_business_category").css({"visibility":"hidden"});
    $(".settings_diet_category").css({"visibility":"hidden"});
    $(".settings_payment_category").css({"visibility":"hidden"});
    $(".settings_help_category").css({"visibility":"hidden"});
    $(".settings_about_category").css({"visibility":"hidden"});
    $(".logout").css({"visibility":"hidden"});
  }

  if(settingSelected == "Diet"){
    if(document.getElementById("showOthersOption") == null){
      $(".settings_diet_category").append(showRest);
    }
    $(".settings_business_category").css({"visibility":"hidden"});
    $(".settings_account_category").css({"visibility":"hidden"});
    $(".settings_payment_category").css({"visibility":"hidden"});
    $(".settings_help_category").css({"visibility":"hidden"});
    $(".settings_about_category").css({"visibility":"hidden"});
    $(".logout").css({"visibility":"hidden"});
  }

  if(settingSelected == "Help"){
    if(document.getElementById("showOthersOption") == null){
      $(".settings_help_category").append(showRest);
    }
    $(".settings_business_category").css({"visibility":"hidden"});
    $(".settings_account_category").css({"visibility":"hidden"});
    $(".settings_payment_category").css({"visibility":"hidden"});
    $(".settings_diet_category").css({"visibility":"hidden"});
    $(".settings_about_category").css({"visibility":"hidden"});
    $(".logout").css({"visibility":"hidden"});
  }

  if(settingSelected == "About"){
    if(document.getElementById("showOthersOption") == null){
      $(".settings_about_category").append(showRest);
    }
    $(".settings_business_category").css({"visibility":"hidden"});
    $(".settings_account_category").css({"visibility":"hidden"});
    $(".settings_payment_category").css({"visibility":"hidden"});
    $(".settings_diet_category").css({"visibility":"hidden"});
    $(".settings_help_category").css({"visibility":"hidden"});
    $(".logout").css({"visibility":"hidden"});
  }

}

function liveFAQSearch(data, userName){

  var pathName = "includes/handlers/faq/ajax_search_faq.php";

  $.ajax({

    url: pathName,
    type: "POST",
    data: {
        input: data,
        userName: userName,
    },
    success: function(response){
      $("#settings_selection_Help").html(response);
    }

  });


}

function openAllergiesFilter(data){

  var lengthOfData = data.length;

  pathName = "includes/handlers/allergies/ajax_show_allergens.php";

  $.ajax({
    url: pathName,
    type: "POST",
    data: {
        lengthOfData: lengthOfData,
        allergens: data,
    },
    success: function(response){
      $('#settings_selection_Diet').html(response);
    }
  });
}

function allergenInProcess(userName){
  allergenInWriting = document.getElementById("diet_allergies").value;

  if($("#upload_allergen_confirmed") != null ){
    $("#upload_allergen_confirmed").remove();

    var pathName = "includes/handlers/allergies/ajax_onfocus_allergen.php";

    $.ajax({
      url: pathName,
      type: "POST",
      data: {

        userName: userName,
      },
      success: function(response){
        $(".allergy_response").html(response);
      }

    });

  }
}

function addAllergen(data){

  pathName = "includes/handlers/allergies/ajax_add_allergen.php";

  $.ajax({
    url: pathName,
    type: "POST",
    data: {
        userName: data[0],
        allergen: allergenInWriting,
    },
    success: function(response){
      document.getElementById("diet_form").reset();
      $(".allergy_response").html(response);
    }


  });

}



function openUserContactWindow(userName, email, phone){

  userTracker_Settings("Account");

  pathName = "includes/handlers/user_settings/ajax_update_contact_details.php";

    $.ajax({
      url: pathName,
      type: "POST",
      data: {
            email: email,
            phone: phone,
            userName: userName,
            },

      success: function(response){
        $("#settings_selection_Account").html(response);
      }

    });
}


function userTracker_Settings(identifier, userName){

  console.log(identifier);
  console.log(userName);

  var user_context = "";

  if(identifier == "Business"){
    user_context = "58867dba526843ce9b3aae69a4f1d1e3";
  }
  else if(identifier == "Account"){
    user_context = "b806ef95d125872d1e72133d96464938";
  }
  else if(identifier == "Diet"){
    user_context = "1311f004aa1a268e4309afbb26dcaf1b";
  }
  else if(identifier == "Payment"){
    user_context = "067980ba0b78d4f6a134fb324d2e5b23";
  }
  else if(identifier == "Help"){
    user_context = "adac9bba354ed200c0549665c3801d4e";
  }
  else if(identifier == "About"){
    user_context = "6f58344db84efc46ba42efac4a5de557";
  }

  $.ajax({
    url: "includes/zeus/hercules_stack.php",
    type: "POST",
    data: {
          userName: userName,
          hercules: user_context,
    },
    success: function(response){
      console.log(response);
    }

  });
}

function openSettingsWindow(setting, firstName, selection, userName, lastName){

  closeSettingsOptions(selection);
  userTracker_Settings(selection, userName);

  var pathName = "";

  if(setting == 'Registration'){
    pathName = "includes/handlers/settings/ajax_business_registration.php";
  }
  else if(setting == 'Data_Analytics'){
    pathName = "includes/handlers/data_analytics/ajax_settings_data_overview.php";
  }
  else if(setting == 'Features'){
    pathName = "includes/handlers/features/ajax_settings_features_overview.php";
  }
  else if(setting == 'User'){
    pathName = "includes/handlers/user_settings/ajax_update_user.php";
  }
  else if(setting == 'Password'){
    pathName = "includes/handlers/user_settings/ajax_update_password.php";
  }
  else if(setting == 'Ingredient'){
    pathName = "includes/handlers/ingredient_filter/ajax_update_user_ingredients.php";
  }
  else if(setting == 'Calory'){
    pathName = "includes/handlers/calory/ajax_update_calory_cap.php";
  }
  else if(setting == 'Allergies'){
    pathName = "includes/handlers/allergies/ajax_update_allergies.php";
  }
  else if(setting == 'FAQ'){
    pathName = "includes/handlers/faq/ajax_cache.php";
  }
  else if(setting == 'Chat'){
    pathName = "includes/handlers/help_chat/help_chat.php";
  }
  else if(setting == 'Privacy'){
    pathName = "includes/handlers/privacy/privacy_home.php";
  }
  else if(setting == 'Team'){
    pathName = "includes/handlers/team/bigboisaybj.php";
  }
  else if(setting == 'Goal'){
    pathName = "includes/handlers/goal/goal.php";
  }
  else if(setting == 'Chapters'){
    pathName = "includes/handlers/chapters/one.php";
  }

  $.ajax({
    url: pathName,
    type: "POST",
    data: {
          firstName: firstName,
          lastName: lastName,
          username: userName,
          },

    success: function(response){
      $("#settings_selection_"+selection).html(response);
    }

  });
}

function adjustCaloryCap(userName){

  pathName = "includes/handlers/calory/ajax_post_kJ.php";

  var kJCap = document.getElementById('kJ_form_output').value;

  var result = kJCap.indexOf(" ");

  var kJCap = kJCap.substring(0, result);

  $.ajax({
    url: pathName,
    type: "POST",
    data: {
      userName: userName[0],
      kJ: kJCap,
    },
    success: function(response){
      $('#request_to_filter_kJ').remove();
      $('.kJ_results_container').html(response);
    }


  });

}

function updateKJRange(rangeValue, data){

  var rangeInt = parseInt(rangeValue);
  var response = "";

  if(7800 < rangeInt && rangeInt < 9700){

    response = " is between the recommended daily intake."
  }

  if(rangeInt < 7800){

    response = " is less than the recommended daily intake."
  }

  if(rangeInt > 9700){

    response = " is greater than the recommended daily intake."
  }

  document.getElementById("kJ_form_output").innerHTML = rangeValue + " kJ/day" + response;

  if(document.getElementById("upload_kJ_confirmed") != null){

    $.ajax({

      url: "includes/handlers/calory/ajax_reload_kJChange.php",
      type: "POST",
      data: {

        userName: data[0],

      },
      success: function(response){
        $('.kJ_results_container').html(response);

      }
    });

  }

}

function deleteIngredient(data){

  pathName = "includes/handlers/ingredient_filter/ajax_delete_sub.php";

  $.ajax({
    url: pathName,
    type: "POST",
    data: {

        ingredient: data[0],
        userName: data[1],
    },
    success: function(response){
      $("#ingredient_"+data[0]).remove();
      $("#ingredient_subs_question").remove();
      $(".user_ingredient_subs").append(response);
    }

  });
}

function allergenQuestion(data){

  pathName = "includes/handlers/allergies/ajax_allergen_question.php";

  if(document.getElementById("allergen_removal_question") == null){

  $.ajax({
    url: pathName,
    type: "POST",
    data: {
        allergen: data[0],
        userName: data[1],
    },

    success: function(response){
        $(".addReplacements").remove();
        $("#settings_selection_Diet").append(response);
        }
      });

    }

}

function deleteAllergen(data){

  pathName = "includes/handlers/allergies/ajax_delete_allergen.php";

  $.ajax({
    url: pathName,
    type: "POST",
    data: {
        allergen: data[0],
        userName: data[1],
    },

    success: function(response){
      $("#ingredient_specific_"+data[0]).remove();
      $("#allergen_removal_question").html(response);
    }
  });
}

function keepIngredient(data){

  pathName = "includes/handlers/ingredient_filter/ajax_keep_sub.php";

  $.ajax({
    url: pathName,
    type: "POST",
    data: {
        userName: data[1],
    },

    success: function(response){
      $("#ingredient_subs_question").remove();
      $("#settings_selection_Diet").append(response);
    }

  });

}

function keepAllergen(data){

  pathName = "includes/handlers/allergies/ajax_keep_allergen.php";

  $.ajax({
    url: pathName,
    type: "POST",
    data: {
        userName: data[1],
    },

    success: function(response){
      $("#ingredient_specific_"+data[0]).remove();
      $("#allergen_removal_question").html(response);
    }

  });

}

function openAllergies(data){

pathName = "includes/handlers/allergies/ajax_update_allergies.php";

$.ajax({
  url: pathName,
  type: "POST",
  data: {
        username: data[0],
        },

  success: function(response){
    $("#settings_selection_Diet").html(response);
  }

});
}

function deleteIngredientReplacement(data){

  pathName = "includes/handlers/ingredient_filter/ajax_ingredient_question.php";

  if(document.getElementById("ingredient_subs_question") == null){

      $.ajax({
        url: pathName,
        type: "POST",
        data: {
              ingredient: data[0],
              userName: data[1],
              },

        success: function(response){
          $(".addReplacements").remove();
          $(".user_ingredient_subs").append(response);
        }

      });

  }

}

function openIngredients(data){

  pathName = "includes/handlers/ingredient_filter/ajax_update_user_ingredients.php";

  $.ajax({
    url: pathName,
    type: "POST",
    data: {
          username: data[0],
          },

    success: function(response){
      $("#settings_selection_Diet").html(response);
    }

  });
}

function showReplacedIngredients(data){

  $.ajax({
    url: "includes/handlers/ingredient_filter/ajax_return_replaced.php",
    type: "POST",
    data: {
          userName: data[0],
    },
    success: function(response){
      $("#settings_selection_Diet").html(response);
    }

  });

}

function getLiveSearchUsers(value, user) {

  $.post("includes/handlers/ajax_search.php", {query:value, userLoggedIn: user}, function(data) {

    if(data != ""){
      $('.search_results').html(data);
      if($(".search_results").css("height") != "0px") {
        $(".search_results").css({"margin-top" : "80px"})
        $(".posts_area").css({"visibility" : "hidden"});
        $(".user_present_orders").css({"visibility" : "hidden"});
        $(".main_column").css({"height" : "0px"});
        return;
      }
      if($(".dropdown_user_window").css("height") != "0px") {
        $(".dropdown_user_window").html("");
        $(".dropdown_user_window").css({"padding" : "0px", "height" : "0"});
        $(".posts_area").css({"visibility" : "visible"});
        $(".user_present_orders").css({"visibility" : "visible"});
        $(".search_results").css({"margin-top" : "0px"})
        return;
      }
      else{
        if($(".posts_area").css("visibility") == "hidden"){
          $(".posts_area").css({"visibility" : "visible"});
          $(".user_present_orders").css({"visibility" : "visible"});
          $(".search_results").css({"margin-top" : "0px"})
          $(".main_column").css({"height" : "655px"});
          return;
        }
      }
    }
  });
}

function updateMapMarker(newLat, newLong) {

  console.log("update");
  var newMarker = {lat: newLat, lng: newLong};
  var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 8,
      center: newMarker
    });
  var marker = new google.maps.Marker({
      position: newMarker,
      map: map
    });
}

function showMerchantWindow(data){

  var newLat = parseFloat(data[0]);
  var newLong = parseFloat(data[1]);
  var merchantID = parseFloat(data[2]);

  if($(".google_maps_window").css("visibility") == "visible"){
    var parent = $(".google_maps_window").parent()[0].className;
    $(".google_maps_window").remove();
    var str = "merchant_window_"
    var temp = str.length;

    var old_merchant_id = parent.substring(16);
    $("#merchant_photos_"+old_merchant_id).css({"padding" : "0px", "height" : "360px"});
    $("#customers_"+old_merchant_id).css({"color" : "#0070c9"});
    $(".location_button_"+old_merchant_id).css({"color" : "#6E6E7A"});

  }


  $.ajax({
    url: "includes/handlers/maps/map.php",
    type: "POST",
    data: {
          lat: newLat,
          long: newLong,
    },
    success: function(response){
      if($(".google_maps_window").css("visibility") != "visible"){
        $(".merchant_window_"+merchantID).html(response);
        $("#merchant_photos_"+merchantID).css({"padding" : "0px", "height" : "0px"});
        $("#chatButton_"+merchantID).css({"color" : "#6E6E7A"});
        $("#customers_"+merchantID).css({"color" : "#6E6E7A"});
        $("#share_"+merchantID).css({"color" : "#6E6E7A"});
        $(".location_button_"+merchantID).css({"color" : "#0070c9"});
      }

    }

  });

  /*
  if($(".google_maps_window").css("height") == "0px") {
    changeMapMarker(newLat, newLong);
    $(".google_maps_window").css({"padding" : "0px", "height" : "360px"});
    $(".merchant_window_"+merchantID).appendTo('#map');
  }

  else {
    $(".google_maps_window").css({"padding" : "0px", "height" : "0"});
  }
  */

}

function reverseGeoCoding(latitude, longitude, username){

  var latitude = latitude.toString();
  var longitude = longitude.toString();

  var city, country, county, postcode, state, suburb;

  $.getJSON('https://nominatim.openstreetmap.org/reverse', {
    lat: latitude,
    lon: longitude,
    format: 'json',
    },
    function (result) {
      city = result.address.city;
      country = result.address.country;
      county = result.address.county;
      postcode = result.address.postcode;
      state = result.address.state
      suburb = result.address.suburb;
      updateUserLocation(username, city, country, county, postcode, state, suburb);

    });

}

function updateUserLocation(userName, city, country, county, postcode, state, suburb){

  $.ajax({
    url: "includes/handlers/ajax_update_user_location.php",
    type: "POST",
    data: {

      userName: userName,
      city: city,
      country: country,
      county: county,
      postcode: postcode,
      state: state,
      suburb: suburb,
    },

  });

}

function changeMapMarker(newLat, newLong) {

  console.log("change");

  var newMarker = {lat: newLat, lng: newLong};
  var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 8,
      center: newMarker
    });
  var marker = new google.maps.Marker({
      position: newMarker,
      map: map
    });

}

function getCurrentLocation(username) {

  var latitude = "";
  var longitude = "";

  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var pos = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };

      latitude = position.coords.latitude;
      longitude = position.coords.longitude;

      sendCoords(latitude, longitude, username);
      reverseGeoCoding(latitude, longitude, username);

    });
  }

}

function sendCoords(latitude, longitude, username){

  $.ajax({
    type: "POST",
    url: "includes/handlers/update_loc.php",
    data: "lat=" + latitude + "&long=" + longitude + "&username=" + username,
  });
}

function expandExtraArea() {

  $("#extras_area").css({"padding-top" : "110px", "width" : "300px"});
  $(".purchase_area").css({"padding" : "0px", "width" : "0px"});

}

function updateValues(data) {

  if(itemName != "") {
    var temp = document.getElementsByClassName("originalExtrasValues_"+itemName)[0].innerHTML;
  }

  var tempTrim = temp.trim();

  var indexMid = tempTrim.indexOf("•");

  var health = tempTrim.substring(0, indexMid);

  var tempHealth = health.search("&nbsp");

  if(tempHealth != -1){
    health = health.substring(tempHealth+6);

    tempHealth = health.search("&nbsp");

    if(tempHealth != -1){
      tempHealth = health.substring(tempHealth+6);
      health = tempHealth;
    }
  }

  var indexCurrency = tempTrim.indexOf("$");

  var price = tempTrim.substring(indexCurrency+1);

  var healthInt = parseInt(health);

  var healthInt = data * healthInt;

  var healthString = healthInt.toString();

  if(healthString.length > 2){
    healthString = healthString.substring(1);
  }

  var priceFloat = parseFloat(price);

  var priceFloat = data * priceFloat;

  var priceString = priceFloat.toFixed(2);

  var indexOfCents = price.indexOf(".");

  var subPrice = priceString.substring(indexOfCents+1);

  if(subPrice.length == 1) {
    priceString += "0";
  }

  if(subPrice.length == 0){
    priceString += ".00";
  }

  document.getElementById("extrasHealthPrice_"+itemName).innerHTML = healthString + " kJ • $" + priceString + " • x"

}

function nameOfItem(data) {
  itemName = data[0];
}

function animateSlider(data) {

      if($("#extraVolumeInputID_"+data[0]).css("width") == "100px")
      {
          $("#extraVolumeOutputID_"+data[0]).animate({ width: 10}, 250);
          $("#extraVolumeInputID_"+data[0]).animate({ width: 10}, 250);
          $("#extraVolumeInputID_"+data[0]).css({"visibility" : "hidden"});

      }
      if($("#extraVolumeInputID_"+data[0]).css("width") == "0px")
      {
          $("#extraVolumeOutputID_"+data[0]).animate({ width: 10}, 250);
          $("#extraVolumeInputID_"+data[0]).animate({ width: 100}, 250);
          $("#extraVolumeInputID_"+data[0]).css({"visibility" : "visible"});

      }
      if($("#extraVolumeInputID_"+data[0]).css("width") == "10px")
      {
          $("#extraVolumeOutputID_"+data[0]).animate({ width: 10}, 250);
          $("#extraVolumeInputID_"+data[0]).animate({ width: 100}, 250);
          $("#extraVolumeInputID_"+data[0]).css({"visibility" : "visible"});

      }
    }

  function createStripeForm(data){

    document.getElementById('orderExtrasForm').style.display = 'none';

    $(".extraSliderInput").css({"padding" : "0px", "width" : "10px"});
    $("#extras_area").css({"width" : "200px"});
    $(".purchase_area").css({"padding" : "0px", "width" : "400px"});

    var presentCost = document.getElementsByClassName("total_price_"+data[4])[0].innerHTML;

    var result = presentCost.indexOf("$");

    var presentCost = presentCost.substring(result+1, result+5);

    var temp = parseFloat(presentCost);

    integerValue = temp * 100;

    integerValue = Math.round(integerValue);

    $.ajax({
      type: "POST",
      url: "includes/handlers/ajax_create_stripe_input.php",
      data: {
          userName: data[0],
          merchantName: data[1],
          itemTitle: data[2],
          itemPrice: integerValue,
          extras: "None",
          merchantID: data[4],

      },
      success: function(response) {
        $(".purchase_area").html(response);
      }

    });

  }

  function updateItem(data){

    $.ajax({

      url: "includes/handlers/ajax_update_post.php",
      type: "POST",
      data: {

            merchantID:  data[0],
            totalCustomers: data[1],
            merchantName: data[2],
            distance: data[3],
            waitingTime: data[4],
            long: data[5],
            lat: data[6],
            profile_pic: data[7],
            username: data[8],
            itemPrice: data[9],
            itemTitle: data[10],
            itemDescription: data[11],
            itemHealth: data[12],

      },
      success: function(response) {
        $(".merchant_"+data[0]).html(response);
      }

    });
  }

  function removeExtra(data){

    var merchantID = data[0];
    var extraName = data[1];
    var extraPrice = data[2];
    var extraHealth = data[3];
    var itemPrice = data[4];

    var divNumber = "";

    var newName = extraName.replace("_", " ");
    $(".extrasTitle_"+extraName).html("+  "+newName);

    var extraList = document.getElementsByClassName("extras_titles_"+merchantID)[0].innerHTML;
    var trimmedList = extraList.trim();

    var searchResult = trimmedList.search(newName);

    if(searchResult != -1){
      var divNumber = trimmedList.charAt(searchResult-2);

      var extraFloat = parseFloat(extraPrice);

      var priceList = document.getElementsByClassName("extra_item_"+divNumber)[0].innerHTML;

      var priceListTrimmed = priceList.trim();

      var dollars = priceListTrimmed.indexOf("$");

      var cents = priceListTrimmed.indexOf(".");

      var dollarsAmount = priceListTrimmed.substring(dollars+1, cents);

      var centsAmount = priceListTrimmed.substring(cents+1, cents+3);

      var totalAmount = dollarsAmount + "." + centsAmount;

      totalAmount = parseFloat(totalAmount);

      updatePrice(totalAmount,merchantID, '-');

      $(".extra_item_"+divNumber).remove();

      $(".extrasTitle_"+extraName).css("color", "#0070c9");

    }

  }

  function addExtra(data){

        var merchantID = data[0];
        var extraName = data[1];
        var extraPrice = data[2];
        var extraHealth = data[3];
        var itemPrice = data[4];

        $(".extrasTitle_"+extraName).css("color", "#6E6E7A");

        var tempHealth = extraHealth.search("&nbsp");

        if(tempHealth != -1){
          extraHealth = extraHealth.substring(tempHealth+5);
        }

        var originalExtraName = extraName;

        originalExtraName = originalExtraName.replace(/ /g, "_");

        var outputVolume = document.getElementById("extraVolumeOutputID_"+originalExtraName).value;

        var tempHealth = parseInt(extraHealth);
        extraHealth = outputVolume * tempHealth;

        tempExtraString = extraHealth.toString();

        if(tempExtraString.length >2){
          extraHealth = tempExtraString.substring(1);
        }

        var extraPrice = extraPrice.substring(1);

        var extraFloat = parseFloat(extraPrice);

        var extraTemp = outputVolume * extraFloat;
        extraTemp = extraTemp.toFixed(2);

        extraFloat = extraTemp;

        totalPrice = itemPrice;

        var totalFloat = parseFloat(totalPrice);

        totalPrice = totalFloat + extraTemp;

        var newName = extraName.replace("_", " ");
        $(".extrasTitle_"+extraName).html("- "+newName);

        var extraList = document.getElementsByClassName("extras_titles_"+merchantID)[0].innerHTML;
        var trimmedList = extraList.trim();

        if(isNaN(extraHealth)){
          extraHealth = data[3];
        }
        if(isNaN(extraTemp)){
          stringTemp = data[2];
          stringTemp = stringTemp.substring(1);
          extraTemp = parseFloat(stringTemp);
          extraFloat = extraTemp;
        }
        if(isNaN(outputVolume)){
          outputVolume = 1;

        }

        if(trimmedList.length == 0) {
          var str = "<div class='extra_item_1'> " + newName + " <br> " + extraHealth + " kJ • $" + extraTemp + " • x" + outputVolume + " </div> <br>";
          var extraListStr = newName + " ";

          $(".extras_item_list_"+merchantID).append(str);
          $(".extras_titles_"+merchantID).append("•1 "+extraListStr);
        }

        else if (trimmedList.length > 0) {

          var itemNumber = (extraList.match(/•/g) || []).length;

          itemNumber++;

          var searchResult = trimmedList.search(newName);

          if(searchResult == -1){
            var str = "<div class='extra_item_"+itemNumber+"'> " + newName + " <br> " + extraHealth + " kJ • $" + extraTemp + " • x" + outputVolume + " </div> <br>";
            var extraListStr = newName + " ";

            $(".extras_item_list_"+merchantID).append(str);
            $(".extras_titles_"+merchantID).append("•"+itemNumber+" "+extraListStr);

          }
          else {

            var divNumber = trimmedList.charAt(searchResult-2);

            var divNumberString = divNumber.toString();

            var divName = "extra_item_"+divNumberString;

            var str = "<div class="+divName+"> " + newName + " <br> " + extraHealth + " kJ • $" + extraTemp + " • x" + outputVolume + " </div>";

            document.getElementsByClassName(divName)[0].innerHTML = str;

          }

        }
        updatePrice(extraFloat,merchantID, '+');

  }

  function updateExtras(data) {

    var extraName = data[1];

    console.log(extraName);

    var extraTitleInspection = document.getElementsByClassName("extrasTitle_"+extraName)[0].innerHTML;

    var trimmedExtraTitleInspection = extraTitleInspection.trim();

    console.log(trimmedExtraTitleInspection.charAt(0));

    if(trimmedExtraTitleInspection.charAt(0) == '+'){
      console.log("Add");
      addExtra(data);
    }
    else if(trimmedExtraTitleInspection.charAt(0) == '-'){
      console.log("Add");
      removeExtra(data);
    }

  }

  function updatePrice(extraPrice, merchant_id, operator) {

    var presentCost = document.getElementsByClassName("total_price_"+merchant_id)[0].innerHTML;
    var result = presentCost.indexOf("$");

    var presentCost = presentCost.substring(result+1, result+5);

    var temp = parseFloat(presentCost);

    var extraPrice = parseFloat(extraPrice);

    if(operator == '+'){

      var total = temp + extraPrice;

      total = total.toFixed(2);

      var result = total.toString();

      result = "$"+result;

      document.getElementsByClassName("total_price_"+merchant_id)[0].innerHTML = result;
    }

    else if(operator == '-'){

      var total = temp - extraPrice;

      total = '$'+total.toFixed(2);

      document.getElementsByClassName("total_price_"+merchant_id)[0].innerHTML = total;
    }

  }

  function checkDropdownOrItemsWindows(type){

    if(type === "dropdown"){
      $("#extras_area").html("");
      $("#extras_area").css({"padding" : "0px", "height" : "0px"});

    }

    else if(type === "extras"){
      $(".dropdown_user_window").html("");
      $(".dropdown_user_window").css({"padding" : "0px", "height" : "0px"});

    }

  }

  function addExtraData(data){
    jsonData = data;
  }

  function searchResultExtraForm_one(data){
    searchResultExtra(data, "one");
  }

  function searchResultExtraForm_two(data){
    searchResultExtra(data, "two");
  }

  function searchResultExtraForm_three(data){
    searchResultExtra(data, "three");
  }

  function searchResultExtra(data, type){

    var itemFromSearch;
    var merchant_id = data['merchantID'];

    if(type == 'one'){
      itemFromSearch = document.getElementsByClassName("search_first_"+merchant_id)[0].innerHTML;
    }

    if(type == 'two'){
      itemFromSearch = document.getElementsByClassName("search_second_"+merchant_id)[0].innerHTML;
    }

    if(type == 'three'){
      itemFromSearch = document.getElementsByClassName("search_third_"+merchant_id)[0].innerHTML;
    }

    document.getElementById("search_form").reset();

    $.ajax({
      url: "includes/handlers/ajax_search_item_selection.php",
      type: "POST",
      data: {

        merchantID:  data['merchantID'],
        totalCustomers: data['totalCustomers'],
        merchantName: data['merchantName'],
        distance: data['distance'],
        waitingTime: data['waitingTime'],
        long: data['long'],
        lat: data['lat'],
        profile_pic: data['profile_pic'],
        username: data['username'],
        item: itemFromSearch,

      },

      success: function(response) {
        if($(".posts_area").css("visibility") == "hidden"){
          $(".search_item").remove();
          $(".search_results").css({"margin-top" : "0px"})
          $(".posts_area").css({"visibility" : "visible"});
          $(".user_present_orders").css({"visibility" : "visible"});
          $(".main_column").css({"height" : "655px"});
          $(".posts_area").html(response);

        }
      }

    });
  }

  function createExtrasForm(data) {

    if(data == null){
      data = jsonData;
    }
    else {
      addExtraData(data);
    }

    checkDropdownOrItemsWindows("extras");

    $.ajax({

      url: "includes/handlers/ajax_update_post.php",
      type: "POST",
      data: {

            merchantID:  data['merchantID'],
            totalCustomers: data['totalCustomers'],
            merchantName: data['merchantName'],
            distance: data['distance'],
            waitingTime: data['waitingTime'],
            long: data['long'],
            lat: data['lat'],
            profile_pic: data['profile_pic'],
            username: data['username'],
            itemPrice: data['itemPrice'],
            itemTitle: data['itemTitle'],
            itemDescription: data['itemDescription'],
            itemHealth: data['itemHealth'],

      },
      success: function(response) {
        $(".merchant_"+data['merchantID']).html(response);
      }

    });

    $.ajax({
      type: "POST",
      url: "includes/handlers/ajax_create_extras_input.php",
      data: {

        merchantID:  data['merchantID'],
        totalCustomers: data['totalCustomers'],
        merchantName: data['merchantName'],
        distance: data['distance'],
        waitingTime: data['waitingTime'],
        long: data['long'],
        lat: data['lat'],
        profile_pic: data['profile_pic'],
        username: data['username'],
        itemPrice: data['itemPrice'],
        itemTitle: data['itemTitle'],
        itemDescription: data['itemDescription'],
        itemHealth: data['itemHealth'],
      },
      success: function(response) {
        $(".extras_area_"+data['merchantID']).html(response);
      }

    });

  }

  function openMerchant(data){

    document.getElementById("search_form").reset();

    $.ajax({
      type: "POST",
      url: "includes/handlers/ajax_create_searched_merchant.php",
      data: {
        merchantID:  data['merchantID'],
        totalCustomers: data['totalCustomers'],
        merchantName: data['merchantName'],
        distance: data['distance'],
        waitingTime: data['waitingTime'],
        long: data['long'],
        lat: data['lat'],
        profile_pic: data['profile_pic'],
        username: data['username'],
        itemOne: data['itemOne'],
        itemTwo: data['itemTwo'],
        itemThree: data['itemThree'],
      },
      success: function(response) {
        if($(".posts_area").css("visibility") == "hidden"){
          $(".search_item").remove();
          $(".search_results").css({"margin-top" : "0px"})
          $(".posts_area").css({"visibility" : "visible"});
          $(".user_present_orders").css({"visibility" : "visible"});
          $(".main_column").css({"height" : "655px"});
          $(".posts_area").html(response);

        }
      }

    });
  }
