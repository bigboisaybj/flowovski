<?php

$str = "";

if(isset($_POST['totalPrice'])) {

  $totalPrice = $_POST['totalPrice'];

  $str .= "
          $$totalPrice
          ";


}

echo $str;

?>
