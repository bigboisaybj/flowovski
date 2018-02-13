<?php

if(isset($_POST['ingredient'])){

  $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

  $ingredient = $_POST['ingredient'];
  $userName = $_POST['userName'];

  $deleteIngredient = mysqli_query($db, "DELETE FROM ingredient_subs WHERE username='$userName' and ingredient='$ingredient'") or die(mysqli_error($db));

}

 ?>
