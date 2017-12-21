<?php

class Extras{
  private $con;
  private $itemPrice;

  public function __construct($con, $itemPrice) {
    $this->con =$con;
    $this->itemPrice = $itemPrice;
  }

  public function createExtrasForm($data) {

    $username = $data['userName'];
    $merchantName = $data['merchantName'];
    $itemTitle = $data['itemTitle'];
    $itemPrice = $data['itemPrice'];
    $itemPicture = $data['product_picture'];
    $merchantID = $data['merchantID'];
    $itemID = $data['itemID'];


    $data = array();

    array_push($data, $username);
    array_push($data, $merchantName);
    array_push($data, $itemTitle);
    array_push($data, $itemPrice);
    array_push($data, $itemPicture);
    array_push($data, $merchantID);
    array_push($data, $itemID);

    $data_json = json_encode($data);

    $str .= $this->showExtra($merchantName);
    $this->updateItem($merchantID, $itemID);

    echo $str;
  }

  public function showExtra($merchantName){

    $str = "";

    $query = mysqli_query($this->con, "SELECT * FROM extras WHERE merchant_name='$merchantName'");

    $valueArray = array();
    $columnArray = array();

    $count = 0;

    while ($row = $query->fetch_assoc()) {
        foreach($row as $key => $value) {
          $count++;
          $finfo = $query->fetch_fields();
          if($count > 2) {
            array_push($valueArray, $value);

            }
          }
        $count = 0;

        foreach($finfo as $val) {
          $nameOfColumn = $val->name;
          $count++;
          if($count > 2) {
            array_push($columnArray, $nameOfColumn);

            }
          }

      }

      $completeValues = array();
      $completeColumns = array();

      $healthArray = array();

      for ($i = 0; $i < sizeof($valueArray); $i++) {

        for ($t = 0; $t < strlen($valueArray[$i]); $t++) {

          if ($valueArray[$i]{$t} == ",") {

            $healthResult = substr($valueArray[$i], $t+1);

            array_push($healthArray, $healthResult);

            $healthResult = substr($valueArray[$i], 0, $t);

            $valueArray[$i] = $healthResult;

          }
        }

      }

      for($i = 0; $i < sizeof($valueArray); $i++) {

        if($valueArray[$i] != "0") {

          $roundedValue = number_format(($valueArray[$i] /100), 2, '.', ' ');
          $valueWithDollar = "$" . $roundedValue;

          $toCapitalize = substr($columnArray[$i],0,1);
          $toCapitalize = strtoupper($toCapitalize);
          $remainder = substr($columnArray[$i],1);

          $capitalized = $toCapitalize . $remainder;

          array_push($completeValues,$valueWithDollar);
          array_push($completeColumns,$capitalized);
        }

      }

      for ($i = 0; $i < sizeof($completeColumns); $i++) {

        for ($t = 0; $t < strlen($completeColumns[$i]); $t++) {

          if ($completeColumns[$i]{$t} === "_") {
            $completeColumns[$i]{$t} = " ";
          }

      }
    }

    $str = "";

    for($v = 0; $v < sizeof($completeColumns); $v++){
      $str .= "

              <div class='extraArea'>

                <div class='extrasTitle'>
                  $completeColumns[$v]
                </div>

                <div class='extrasHealthAndPrice'>
                  $healthArray[$v] kJ • $completeValues[$v]
                </div>

              </div>

              <br>

              ";

    }

    echo $str . $hr;
  }

  public function updateItem($merchantID, $itemID) {

    //Get postId -> ID of post.

    //Go through array of posts shown.

    //Search for ID in array.

    //If match, update required element. eg. Photo/distance

    ?>

    <script>

    var temp = '<?php echo $merchantID; ?>';
    var temp2 = '<?php echo $itemID; ?>';

    console.log(temp);
    console.log(temp2);

    </script>

    <?php

    
    //Can get postID by calling the merchantPost for each post. calling each div a name that's titled by the postID

    //Place postID around entire divArea --> not styled, just a classifier

    //To update --> output to div area that has the same div ID

    //This means function takes in all variables, so anything changed is already inserted.

    //Then output to that specific divID




    //Change picture to relevant Pic


    //First option = selected item
    //Second option = extras selection
    //Third option = Total price

  }


}

?>

<script>

  function createStripeForm(data) {

    $.ajax({
      type: "POST",
      url: "includes/handlers/ajax_create_stripe_input.php",
      data: {
          userName: data[0],
          merchantName: data[1],
          itemTitle: data[2],
          itemPrice: data[3],
          extras: "None",
      },
      success: function(response) {
        $(".purchase_area").html(response);
      }

    });
  }

</script>
