<?php

class Live_Queue{
  private $con;
  private $merchantName;
  private $merchantID;
  private $queue_product_array;
  private $queue_name_array;

  public function __construct($con, $merchantName, $merchantID){
    $this->con = $con;
    $this->merchantName = $merchantName;
    $this->merchantID = $merchantID;
    $this->queue_product_array = array();
    $this->queue_name_array = array();

  }

  public function initialiseArray(){

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $queue_details_query = mysqli_query($db, "SELECT userName, productName FROM sales WHERE merchantName='$this->merchantName'");

    while($row = mysqli_fetch_array($queue_details_query)){

      $userName = $row['userName'];
      $productName = $row['productName'];
      $og = $productName;

      $check_vowel = str_split($productName);
      $vowel_found = false;

      if($productName[0] == 'a' || $productName[0] == 'A'){
        $productName = "an " . $productName;
        $vowel_found = true;
      }

      if($productName[0] == 'e' || $productName[0] == 'E'){
        $productName = "an " . $productName;
        $vowel_found = true;
      }

      if($productName[0] == 'i' || $productName[0] == 'I'){
        $productName = "an " . $productName;
        $vowel_found = true;
      }

      if($productName[0] == 'o' || $productName[0] == 'O'){
        $productName = "an " . $productName;
        $vowel_found = true;
      }

      if($productName[0] == 'u' || $productName[0] == 'U'){
        $productName = "an " . $productName;
        $vowel_found = true;
      }

      if(!$vowel_found){
        $sizeArray = strlen($og);
        $lastEl = $productName[$sizeArray-1];

        if($lastEl != 's'){
          $productName = "a " . $productName;

        }
      }

      $userName_query = mysqli_query($db, "SELECT first_name FROM users WHERE username='$userName'");

      $userName_first = "";

      if(mysqli_num_rows($userName_query) > 0){

        $userName_data = mysqli_fetch_array($userName_query);

        $userName_first = $userName_data['first_name'];
      }

      array_push($this->queue_product_array, $productName);
      array_push($this->queue_name_array, $userName_first);
    }
  }

  public function animate(){

    $str = "";

    for($i = 0; $i < sizeof($this->queue_product_array); $i++){


      $str .= "

                <div class='test_queue' id='test_queue_$merchant_id'>

                  <div id='queue_picture'>
                    <img src='includes/classes/fb_photo.jpg' id='queue_picture'>
                  </div>

                  <div id='queue_name'>".
                  $this->queue_name_array[$i] ." ordered
                  </div>

                  <div id='queue_event'>".
                  $this->queue_product_array[$i]
                  ."</div>

                </div>

              ";

            }

            ?>
            <script>

            var elements = document.querySelectorAll(".test_queue");

            for (var i = 0; i < elements.length; i++) {
                  load(elements[i], i);
             }

             function load(elem, i) {
                  setTimeout(function() {
                      marquee($('#display'), $(elem));
                  },10000 * i);
              }

            </script>

            <?php

            return $str;


  }
}

?>
