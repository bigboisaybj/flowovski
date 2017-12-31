<?php

class OrderProcess {

  private $con;
  private $username;
  private $merchantName;
  private $productName;
  private $extras;
  private $itemPrice;

  public function __construct($con, $username, $merchantName, $productName, $extras, $itemPrice) {
    $this->con =$con;
    $this->username = $username;
    $this->merchantName = $merchantName;
    $this->productName = $productName;
    $this->itemPrice = $itemPrice;
    $this->extras = $extras;

  }

  public function sendToServer(){

    //Date
    $TimestampOfOrder = date("Y-m-d H:i:s");

    $jd = cal_to_jd(CAL_GREGORIAN,date("m"),date("d"),date("Y"));
    $day = (jddayofweek($jd,1));

    ?>

    <script>

    var day = '<?php echo $day; ?>';
    var timeStamp = '<?php echo $TimestampOfOrder; ?>';
    var userName = '<?php echo $this->username; ?>';
    var merchantName = '<?php echo $this->merchantName; ?>';
    var productName = '<?php echo $this->productName; ?>';
    var extras = '<?php echo $this->extras; ?>';
    var itemPrice = '<?php echo $this->itemPrice; ?>';


    submitForLocSTAT(day, timeStamp, userName, merchantName, productName, extras, itemPrice);

    </script>

    <?php
  }
}


 ?>

 <script>

 function submitForLocSTAT(day, orderTime, username, merchantName, productName, extras, itemPrice) {

   var day = day;
   var orderTime = orderTime;
   var username = username;
   var merchantName = merchantName;
   var itemID = itemID;
   var extras = extras;
   var itemPrice = itemPrice;

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

       $.ajax({

         url: "http://api.apixu.com/v1/current.json?key=be5e42eef0ae467d8db05839171812&q=" + latitude + "," + longitude,

         type: "GET",
         dataType: "json",
         success: function(data) {

           cloud = data.current.cloud;
           text_description = data.current.condition.text;
           feels_like_c = data.current.feelslike_c;
           humidity = data.current.humidity;
           precip_mm = data.current.precip_mm;
           pressure_mb = data.current.pressure_mb;
           temp_c = data.current.temp_c;
           vis_km = data.current.vis_km;
           wind_dir = data.current.wind_dir;
           wind_kph = data.current.wind_kph;
           localTime = data.location.localtime_epoch;

           str_cloud = cloud.toString();
           str_feels_like_c= feels_like_c.toString();
           str_humidity = humidity.toString();
           str_precip_mm = precip_mm.toString();
           str_pressure_mb = pressure_mb.toString();
           str_temp_c = temp_c.toString();
           str_vis_km = vis_km.toString();
           str_wind_kph = wind_kph.toString();
           str_localTime = localTime.toString();

           sendToServer(day, orderTime, username, merchantName, productName, extras, itemPrice, latitude, longitude);

           sendWeatherToServer(orderTime, str_cloud, text_description, str_feels_like_c, str_humidity, str_precip_mm, str_pressure_mb, str_temp_c, str_vis_km, wind_dir, str_wind_kph, str_localTime);

         }

       });

     });
   }

}

 function sendToServer(day, orderTime, username, merchantName, productName, extras, itemPrice, latitude, longitude){

   $.ajax({
     type: "POST",
     url: "submit_order.php",
     data: {
            day: day,
            orderTime : orderTime,
            username : username,
            merchantName : merchantName,
            productName : productName,
            extras : extras,
            itemPrice : itemPrice,
            latitude : latitude,
            longitude : longitude,
     },
   });

 }

 function sendWeatherToServer(timeStamp, cloud, text, feels, humidity, precip, pressure, temp, vis, wind_dir, wind_kph, localTime){

   $.ajax({
     url: "./set_weather",
     type: "POST",
     data: {

       timeStamp : timeStamp,
       cloud : cloud,
       text_description : text,
       feels_like_c : feels,
       humidity : humidity,
       precip_mm : precip,
       pressure_mb : pressure,
       temp_c : temp,
       vis_km : vis,
       wind_dir : wind_dir,
       wind_kph : wind_kph,
       localTime : localTime,

     },
   });

 }


 </script>
