<?php

$db = mysqli_connect("localhost", "root", "aloysius2", "bright");

if(isset($_POST['submit'])){

  $ingredient = $_POST['ingredient_name'];
  $first = $_POST['first_alt'];
  $second = $_POST['second_alt'];
  $third = $_POST['third_alt'];

  $userName = $_POST['userName'];

  $ingredient = strip_tags($ingredient);
  $first = strip_tags($first);
  $second = strip_tags($second);
  $third = strip_tags($third);

  $date = date("Y-m-d H:i:s");

  $insertIngredientSub = mysqli_query($db, "INSERT INTO ingredient_subs VALUES ('0', '$userName', '$ingredient', '$first', '$second', '$third', '$date')") or die(mysqli_error($db));

  header("Location: ../../../index.php"); /* Redirect browser */
  exit();
}

 ?>
