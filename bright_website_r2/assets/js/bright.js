var presentType = "";
var totalPrice;
var init = false;
var itemName = "";
var jsonData;

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

function messageFocus(elem){
  var x = window.scrollX, y = window.scrollY;
  elem.focus();
  window.scrollTo(x,y);
}

function openMessages(merchantID){

  if($(".merchant_window_"+merchantID).css("height") == "0px") {
      var ajaxreq = $.ajax({
      url: "message.php",
      type: "POST",
      data: "merchantID=" + merchantID,

      success: function(response) {
        $(".merchant_window_"+merchantID).html(response);
        $(".merchant_window_"+merchantID).css({"padding" : "0px", "height" : "360px"});
        messageFocus(document.getElementById('message_textarea'));
        $("#merchant_photos_"+merchantID).css({"padding" : "0px", "height" : "0px"});
        $("#chatButton_"+merchantID).css({"color" : "#0070c9"});

        if($("#customers_"+merchantID).css("color") != "#6E6E7A"){
          $("#customers_"+merchantID).css({"color" : "#6E6E7A"});
        }
      }
    });
  }

  if($(".merchant_window_"+merchantID).css("height") != "0px") {
    $(".merchant_window_"+merchantID).html("");
    $(".merchant_window_"+merchantID).css({"padding" : "0px", "height" : "0px"});
    $("#merchant_photos_"+merchantID).css({"padding" : "0px", "height" : "360px"});
    $("#chatButton_"+merchantID).css({"color" : "#6E6E7A"});

    if($("#customers_"+merchantID).css("color") != "#0070c9"){
      $("#customers_"+merchantID).css({"color" : "#0070c9"});
    }

  }
}

function openMerchantItemsDisplay(merchantID){

  if($(".merchant_window_"+merchantID).css("height") != "0px") {
    $(".merchant_window_"+merchantID).html("");
    $(".merchant_window_"+merchantID).css({"padding" : "0px", "height" : "0px"});
    $("#merchant_photos_"+merchantID).css({"padding" : "0px", "height" : "360px"});
    $("#chatButton_"+merchantID).css({"color" : "#6E6E7A"});

    if($("#customers_"+merchantID).css("color") != "#0070c9"){
      $("#customers_"+merchantID).css({"color" : "#0070c9"});
    }

  }

}

function enterKeySubmit(data, key, element){
  if(key.keyCode === 13) {
    sendMessage(data,element);
  }
}

function sendMessage(data, element){
  var merchantName = data[1];
  var message = document.getElementById("message_textarea").value;
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
      $(".loaded_messages_"+merchantName).append(response);

    }

  });

}

function showUserHeader(type, userLoggedIn) {

  if (userLoggedIn != "" && type == 'user_account') {
    checkDropdownOrItemsWindows("dropdown");
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

    if(data != ""){
      $('.search_results').html(data);
      if($(".search_results").css("height") != "0px") {
        $(".search_results").css({"margin-top" : "80px"})
        $(".posts_area").css({"visibility" : "hidden"});
        $(".main_column").css({"height" : "0px"});

      }
      else{
        if($(".posts_area").css("visibility") == "hidden"){
          $(".posts_area").css({"visibility" : "visible"});
          $(".search_results").css({"margin-top" : "0px"})
          $(".main_column").css({"height" : "655px"});
        }
      }
    }

    //$('.search_results_footer').html("<a href='search.php?q=" + value + "'>See All Results</a>");

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

function expandExtraArea() {

  $("#extras_area").css({"padding" : "0px", "width" : "300px"});
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
    $("#extras_area").css({"padding" : "0px", "width" : "200px"});
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

    var extraTitleInspection = document.getElementsByClassName("extrasTitle_"+extraName)[0].innerHTML;

    var trimmedExtraTitleInspection = extraTitleInspection.trim();

    if(trimmedExtraTitleInspection.charAt(0) == '+'){
      addExtra(data);
    }
    else if(trimmedExtraTitleInspection.charAt(0) == '-'){
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
      //check extras
      $("#extras_area").html("");
      $("#extras_area").css({"padding" : "0px", "height" : "0px"});

    }

    else if(type === "extras"){
      //check dropDown

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

    //console.log(data);
    //console.log(type);

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
          $(".posts_area").css({"visibility" : "visible"});
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
          $(".main_column").css({"height" : "655px"});
          $(".posts_area").html(response);

        }
      }

    });
  }
