<?php

class Message {
  private $user_obj;
  private $con;

  public function __construct($con, $user) {
    $this->con = $con;
    $this->user_obj = new User($con, $user);
  }

  public function sendMessage($merchantName, $userLoggedIn, $body, $date ) {

    if($body != "") {
      $query = mysqli_query($this->con, "INSERT INTO messages VALUES('0', '$merchantName', '$userLoggedIn', '$body', '$date', 'no', 'no', 'no')");

    }


  }


}

 ?>
