<?php

if(isset($_POST['merchantID'])){

  $str = "";

  $merchantID = $_POST['merchantID'];

  $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

  $int_ID = intval($merchantID);

  $nextHit_query = mysqli_query($db, "SELECT last_hit FROM merchants WHERE id=$int_ID") or die(mysqli_error($db));

  $nextHit = "";

  if(mysqli_num_rows($nextHit_query) > 0){

    $nextHit_data = mysqli_fetch_array($nextHit_query);

    $Hit = $nextHit_data['last_hit'];

    $max_id = 0;

    $max_id_query = mysqli_query($db, "SELECT id FROM Dirt ORDER BY id DESC LIMIT 1");

      if(mysqli_num_rows($max_id_query) > 0) {

        $id_row = mysqli_fetch_array($max_id_query);

        $max_id = $id_row['id'];
      }

      $Hit++;

      $product_query = mysqli_query($db, "SELECT * FROM Dirt WHERE id='$Hit'");

      while (mysqli_num_rows($product_query) != 1) {

        $Hit++;

        $product_query = mysqli_query($db, "SELECT * FROM Dirt WHERE id='$Hit'");

        if ($Hit > $max_id) {

          $Hit = 0;

        }

      }

      if(mysqli_num_rows($product_query) == 1) {

        $imgUrl_query = mysqli_query($db, "SELECT product_picture FROM Dirt where id='$Hit'");

        if(mysqli_num_rows($imgUrl_query) > 0){

          $imgUrl_data = mysqli_fetch_array($imgUrl_query);

          $imgUrl = $imgUrl_data['product_picture'];

          $str = "<img src='$imgUrl' width=640px height='360px' id='merchant_photos_$merchantID'>";

          $update_query = mysqli_query($db, "UPDATE merchants SET last_hit='$Hit' WHERE merchant_name='Dirt'");

        }
    }

    echo $str;

  }


}

 ?>
