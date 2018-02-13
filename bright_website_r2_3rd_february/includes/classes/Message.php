<?php

class Message {
  private $user_obj;
  private $con;

  public function __construct($con, $user) {
    $this->con = $con;
    $this->user_obj = new User($con, $user);
  }

  public function getMessages($merchantName) {

    $userLoggedIn = $this->user_obj->getUsername();

    $data = "";

    $query = mysqli_query($this->con, "UPDATE messages SET opened='yes' WHERE user_from='$userLoggedIn' AND merchantName='$merchantName'");

    $get_messages_query = mysqli_query($this->con, "SELECT * FROM messages WHERE user_from='$userLoggedIn' AND merchantName='$merchantName'");

    while($row = mysqli_fetch_array($get_messages_query)){
      $merchantName = $row['merchantName'];
      $userName = $row['user_from'];
      $body = $row['body'];

      $div_top = ($userName == $userLoggedIn) ? "<div class='message_item' id='green'>" : "<div class='message_item' id='blue'>";

      $data = $data . $div_top . $body . "</div><br><br>";
    }
    return $data;

  }


}

 ?>
