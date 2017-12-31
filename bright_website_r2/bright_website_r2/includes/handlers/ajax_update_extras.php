<?php

include("../classes/Selection.php");

$selection = new Selection();

$str = "";

if(isset($_POST['merchantID'])) {

  $merchantID = $_POST['merchantID'];
  $extraName = $_POST['extraName'];
  $extraPrice = $_POST['extraPrice'];
  $extraHealth = $_POST['extraHealth'];

  $nameWithSpaces = str_replace("_", " ", $extraName);

  $selection->addToSelection($nameWithSpaces, $extraPrice, $extraHealth);

  $intSize = $selection->numberOfSelections();
  $extraAdditions = $selection->getSelections();

  $name = "";
  $price = "";
  $health = "";

  for($i = 0; $i < $selection->numberOfSelections(); $i++) {

    for($t = 0; $t < sizeof($extraAdditions[$i]); $t++) {

      if($t == 0) {
        $name = $extraAdditions[$i][$t];
      }

      if($t == 1) {
        $price = $extraAdditions[$i][$t];
      }

      if($t == 2) {
        $health = $extraAdditions[$i][$t];
      }

    }

    $str .= "
              <div class='extra_item'>".
                $name
                ."<br>".
                $health ."kJ • ". $price
              ."</div>
              <br>

            ";

  }

}

?>

<script>

var merchID = '<?php echo $merchantID; ?>';

$(".extras_item_list_"+merchID).append("Some appended text1.");

</script>

<?php

//echo $str;

 ?>
