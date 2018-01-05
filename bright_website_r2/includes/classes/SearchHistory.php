<?php

class SearchHistory{
  private $username;

  public function __construct($username){
    $this->username = $username;

  }

  public function addToLedger($enquiry, $merchant){

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");
    $updateEnquiry = mysqli_query($db, "UPDATE users SET latest_search_enquiry='$enquiry' WHERE username='$this->username'");
    $updateResult = mysqli_query($db, "UPDATE users SET latest_searched_merchant_result='$merchant' WHERE username='$this->username'");

  }

  public function updateLastPreviews($preview_one, $preview_two, $preview_three){
    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");
    $updatePreviewOne = mysqli_query($db, "UPDATE users SET search_preview_three='$preview_one' WHERE username='$this->username'");
    $updatePreviewTwo = mysqli_query($db, "UPDATE users SET search_preview_two='$preview_two' WHERE username='$this->username'");
    $updatePreviewThree = mysqli_query($db, "UPDATE users SET search_preview_one='$preview_three' WHERE username='$this->username'");

  }

  public function getLastSearch(){

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $lastSearchQuery = mysqli_query($db, "SELECT latest_searched_merchant_result FROM users WHERE username='$this->username'");

    if(mysqli_num_rows($lastSearchQuery) == 1){

      $queryRow = mysqli_fetch_array($lastSearchQuery);

      $lastQuery = $queryRow['latest_searched_merchant_result'];

      return $lastQuery;
    }

  }




}


?>
