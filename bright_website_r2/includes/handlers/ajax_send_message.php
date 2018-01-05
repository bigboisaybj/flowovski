<?php

$db = mysqli_connect("localhost", "root", "aloysius2", "bright");

if(isset($_POST['merchantName'])){

  $date = date("Y-m-d H:i:s");

  $merchantName = $_POST['merchantName'];
  $userLoggedIn = $_POST['userName'];
  $message = $_POST['message'];

  $body = strip_tags($message); //removes html tags
  $body = mysqli_real_escape_string($db, $body);
  $check_empty = preg_replace('/\s+/', '', $body);

  if($check_empty != "") {
    $query = mysqli_query($db, "INSERT INTO messages VALUES ('0', '$merchantName', '$userLoggedIn', '$body', '$date', 'no', 'no', 'no')") or die(mysqli_error($db));

    ?>

    <script>

    var div = document.getElementById("scroll_messages");
    div.scrollTop = div.scrollHeight;

    </script>

    <?php

    echo $data = "<div class='message_item' id='green'>" . $message . "</div><br><br>";

  }

}



 ?>
