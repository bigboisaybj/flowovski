<?php

class Distance {
    private $latitude;
    private $longitude;

    public function __construct($latitude, $longitude) {

        $tempLat = floatval($latitude);
        $tempLong = floatval($longitude);

        $this->latitude = deg2rad($tempLat);
        $this->longitude = deg2rad($tempLong);
    }

    public function getLatitude(){
      return $this->latitude;
    }

    public function getLongitude(){
       return $this->longitude;
    }

    public function degreesToRadians($degrees) {
        return $degrees * Math.PI / 180;
    }

    public function getDistanceInMetersTo($lat1, $long1, $lat2, $long2) {

      $earthRadiusKm = 6371;

      $dLat = $this->degreesToRadians($lat2-$lat1);
      $dLon = $this->degreesToRadians($lon2-$lon1);

      $lat1 = $this->degreesToRadians($lat1);
      $lat2 = $this->degreesToRadians($lat2);

      $a = Math.sin(dLat/2) * Math.sin(dLat/2) +
              Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
      $c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

      return $earthRadiusKm * $c;
    }
}


 ?>
