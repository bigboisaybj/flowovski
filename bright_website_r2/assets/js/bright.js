var presentType = "";

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

function openMessages(merchantID){

  if($(".merchant_messages_window").css("height") == "0px") {
      var ajaxreq = $.ajax({
      url: "message.php",
      type: "POST",
      data: "merchantID=" + merchantID,

      success: function(response) {
        $(".merchant_messages_window").html(response);
        $(".merchant_messages_window").css({"padding" : "0px", "height" : "360px"});
        $("#merchant_photos").css({"padding" : "0px", "height" : "0px"});
      }
    });
  }

  if($(".merchant_messages_window").css("height") != "0px") {
    $(".merchant_messages_window").html("");
    $(".merchant_messages_window").css({"padding" : "0px", "height" : "0px"});
    $("#merchant_photos").css({"padding" : "0px", "height" : "360px"});

  }
}

function showUserHeader(type, userLoggedIn) {

  if (userLoggedIn != "" && type == 'user_account') {
    showUserPage(type);
  }
  else {
    if(presentType != type) {
      typeOpener(type);
      presentType = type;
    }

    if($(".dropdown_user_window").css("height") == "0px") {
      typeOpener(type);
      presentType = type;
    }

    else {
      $(".dropdown_user_window").html("");
      $(".dropdown_user_window").css({"padding" : "0px", "height" : "0"});
    }
  }

}

function typeOpener(type) {
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
      $(".dropdown_user_window").css({"padding" : "0px", "height" : "800px"});
      $("#dropdown_data_type").val(type);
    }
  });
}

function showUserPage(type) {

  if(presentType != type) {

      var ajaxreq = $.ajax({
        url: "user_account.php",
        type: "POST",

        success: function(response) {
          $(".dropdown_user_window").html(response);
          $(".dropdown_user_window").css({"padding" : "0px", "height" : "800px"});
          $("#dropdown_data_type").val(type);
        }
      });
      presentType = type;
  }

  if($(".dropdown_user_window").css("height") == "0px") {
    var ajaxreq = $.ajax({
      url: "user_account.php",
      type: "POST",

      success: function(response) {
        $(".dropdown_user_window").html(response);
        $(".dropdown_user_window").css({"padding" : "0px", "height" : "800px"});
        $("#dropdown_data_type").val(type);
      }
    });
    presentType = type;
  }


  else {
    $(".dropdown_user_window").html("");
    $(".dropdown_user_window").css({"padding" : "0px", "height" : "0"});
  }

}

function getLiveSearchUsers(value, user) {

  $.post("includes/handlers/ajax_search.php", {query:value, userLoggedIn: user}, function(data) {

    if($(".search_results_footer_empty")[0]) {
      $(".search_results_footer_empty").toggleClass("search_results_footer");
      $(".search_results_footer_empty").toggleClass("search_results_footer_empty");
    }

    $('.search_results').html(data);
    //$('.search_results_footer').html("<a href='search.php?q=" + value + "'>See All Results</a>");

    if(data == "") {
      $('.search_results_footer').html("");
      $(".search_results_footer").toggleClass("search_results_footer_empty");
      $(".search_results_footer").toggleClass("search_results_footer");
    }
  });
}

function updateMapMarker(newLat, newLong) {
  var newMarker = {lat: newLat, lng: newLong};
  var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 4,
      center: newMarker
    });
  var marker = new google.maps.Marker({
      position: newMarker,
      map: map
    });
}

function showMerchantWindow(newLat, newLong){

  if($(".google_maps_window").css("height") == "0px") {
    changeMapMarker(newLat, newLong)
    $(".google_maps_window").css({"padding" : "0px", "height" : "360px"});
  }

  else {
    $(".google_maps_window").css({"padding" : "0px", "height" : "0"});
  }

}

function changeMapMarker(newLat, newLong) {

  var newMarker = {lat: newLat, lng: newLong};
  var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 4,
      center: newMarker
    });
  var marker = new google.maps.Marker({
      position: newMarker,
      map: map
    });
}

function getCurrentLocation(username) {
  //Current location
  var latitude = "";
  var longitude = "";

    // Try HTML5 geolocation.
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var pos = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };

      latitude = position.coords.latitude;
      longitude = position.coords.longitude;

      sendCoords(latitude, longitude, username);

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
