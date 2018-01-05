<?php

class Item {
  private $con;
  private $username;
  private $merchant;
  private $merchantID;
  private $totalCustomers;
  private $distance;
  private $waitingTime;
  private $long;
  private $lat;
  private $profile_pic;
  private $itemCounter;

  public function __construct($con, $username, $merchant, $merchantID, $totalCustomers, $distance, $waitingTime, $long, $lat,$profile_pic) {
    $this->con = $con;
    $this->username = $username;
    $this->merchant = $merchant;
    $this->merchantID = $merchantID;
    $this->totalCustomers = $totalCustomers;
    $this->distance = $distance;
    $this->waitingTime = $waitingTime;
    $this->long = $long;
    $this->lat = $lat;
    $this->profile_pic = $profile_pic;
    $this->itemCounter = 0;
  }

  public function get_preview_first_item($merchantID) {

    //Check if div exists...

    $postNumber = $this->postCounter();

    $str = "DEFAULT";

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $product_query = mysqli_query($db, "SELECT * FROM Dirt WHERE id='$postNumber'");

    if (mysqli_num_rows($product_query) > 0) {

      $product_row = mysqli_fetch_array($product_query);

      $item_title = $product_row['product_name'];

      $str = $item_title;
    }

    return $str;

  }

  public function get_preview_second_item($merchantID) {

    $postNumber = $this->postCounter();

    $str = "DEFAULT";

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $product_query = mysqli_query($db, "SELECT * FROM Dirt WHERE id='$postNumber'");

    if (mysqli_num_rows($product_query) > 0) {

      $product_row = mysqli_fetch_array($product_query);

      $item_title = $product_row['product_name'];

      $str = $item_title;
    }

    return $str;

  }

  public function get_preview_third_item($merchantID) {

    $postNumber = $this->postCounter();

    $str = "DEFAULT";

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $product_query = mysqli_query($db, "SELECT * FROM Dirt WHERE id='$postNumber'");

    if (mysqli_num_rows($product_query) > 0) {

      $product_row = mysqli_fetch_array($product_query);

      $item_title = $product_row['product_name'];

      $str = $item_title;
    }

    return $str;

  }

  public function construct_searched_first_item($first_item){

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $str = "";

    $itemQuery = mysqli_query($db, "SELECT * FROM Dirt WHERE product_name='$first_item'");

    if(mysqli_num_rows($itemQuery) > 0){

      $product_row = mysqli_fetch_array($itemQuery);

      $item_id = $product_row['id'];
      $item_title = $product_row['product_name'];
      $item_description = $product_row['product_description'];
      $item_health = $product_row['product_health'];
      $item_price = $product_row['product_price'];
      $item_picture = $product_row['product_picture'];

      $item_price_dollars = $item_price/100;
      $item_price_cents = $item_price%100;

      $item_price_formatted = number_format(($item_price /100), 2, '.', ' ');

      $description_array = array();

      array_push($description_array, $item_description);

      $dots = (strlen($description_array[0]) >= 90) ? "..." : "";
      $split = str_split($description_array[0], 90);
      $split = $split[0] . $dots;
      $item_description = $split;

      $arr = array('merchantID'=>$this->merchantID, 'totalCustomers'=>$this->totalCustomers, 'merchantName'=>$this->merchant, 'distance'=>$this->distance, 'waitingTime'=>$this->waitingTime, 'long'=>$this->long, 'lat'=>$this->lat, 'profile_pic'=>$item_picture, 'username'=>$this->username, 'itemPrice'=>$item_price_formatted, 'itemTitle'=>$item_title, 'itemDescription'=>$item_description, 'itemHealth'=>$item_health);

      $data_json = json_encode($arr);

      $str = "
            <div class='merchant_product_title'>

              <input type='submit' value='$item_title' onclick='createExtrasForm($data_json)' id='first_item_button'>

            </div>

            <div class='merchant_product_description'>
              $item_description
            </div>

            <div class='merchant_product_health'>
              $item_health kJ • $$item_price_formatted
            </div>
            ";

    }

    return $str;
  }

  public function construct_searched_second_item($second_item){

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $str = "";

    $itemQuery = mysqli_query($db, "SELECT * FROM Dirt WHERE product_name='$second_item'");

    if(mysqli_num_rows($itemQuery) > 0){

      $product_row = mysqli_fetch_array($itemQuery);

      $item_id = $product_row['id'];
      $item_title = $product_row['product_name'];
      $item_description = $product_row['product_description'];
      $item_health = $product_row['product_health'];
      $item_price = $product_row['product_price'];
      $item_picture = $product_row['product_picture'];

      $item_price_dollars = $item_price/100;
      $item_price_cents = $item_price%100;

      $item_price_formatted = number_format(($item_price /100), 2, '.', ' ');

      $description_array = array();
      array_push($description_array, $item_description);

      $dots = (strlen($description_array[0]) >= 45) ? "..." : "";
      $split = str_split($description_array[0], 45);
      $split = $split[0] . $dots;
      $item_description = $split;

      $title_array = array();
      array_push($title_array, $item_title);

      $dotsTitle = (strlen($title_array[0]) >= 15) ? "..." : "";
      $splitTitle = str_split($title_array[0], 15);
      $splitTitle = $splitTitle[0] . $dotsTitle;
      $item_title = $splitTitle;

      $arr = array('merchantID'=>$this->merchantID, 'totalCustomers'=>$this->totalCustomers, 'merchantName'=>$this->merchant, 'distance'=>$this->distance, 'waitingTime'=>$this->waitingTime, 'long'=>$this->long, 'lat'=>$this->lat, 'profile_pic'=>$item_picture, 'username'=>$this->username, 'itemPrice'=>$item_price_formatted, 'itemTitle'=>$item_title, 'itemDescription'=>$item_description, 'itemHealth'=>$item_health);

      $data_json = json_encode($arr);

      $str = "
            <div class='merchant_product_second_title'>

              <input type='submit' value='$item_title' onclick='createExtrasForm($data_json)' id='first_item_button'>

            </div>

            <div class='merchant_product_second_description'>
              $item_description
            </div>

            <div class='merchant_product_second_health'>
              $item_health kJ • $$item_price_formatted
            </div>
            ";

    }

    return $str;
  }

  public function construct_searched_third_item($third_item){

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $str = "";

    $itemQuery = mysqli_query($db, "SELECT * FROM Dirt WHERE product_name='$third_item'");

    if(mysqli_num_rows($itemQuery) > 0){

      $product_row = mysqli_fetch_array($itemQuery);

      $item_id = $product_row['id'];
      $item_title = $product_row['product_name'];
      $item_description = $product_row['product_description'];
      $item_health = $product_row['product_health'];
      $item_price = $product_row['product_price'];
      $item_picture = $product_row['product_picture'];

      $item_price_dollars = $item_price/100;
      $item_price_cents = $item_price%100;

      $item_price_formatted = number_format(($item_price /100), 2, '.', ' ');

      $title_array = array();

      array_push($title_array, $item_title);

      $dots = (strlen($title_array[0]) >= 12) ? "..." : "";
      $split = str_split($title_array[0], 12);
      $split = $split[0] . $dots;
      $item_title = $split;

      $description_array = array();

      array_push($description_array, $item_description);

      $dots = (strlen($description_array[0]) >= 30) ? "..." : "";
      $split = str_split($description_array[0], 30);
      $split = $split[0] . $dots;
      $item_description = $split;

      $arr = array('merchantID'=>$this->merchantID, 'totalCustomers'=>$this->totalCustomers, 'merchantName'=>$this->merchant, 'distance'=>$this->distance, 'waitingTime'=>$this->waitingTime, 'long'=>$this->long, 'lat'=>$this->lat, 'profile_pic'=>$item_picture, 'username'=>$this->username, 'itemPrice'=>$item_price_formatted, 'itemTitle'=>$item_title, 'itemDescription'=>$item_description, 'itemHealth'=>$item_health);

      $data_json = json_encode($arr);

      $str = "
            <div class='merchant_product_third_title'>

              <input type='submit' value='$item_title' onclick='createExtrasForm($data_json)' id='first_item_button'>

            </div>

            <div class='merchant_product_third_description'>
              $item_description
            </div>

            <div class='merchant_product_third_health'>
              $item_health kJ • $$item_price_formatted
            </div>
            ";

    }

    return $str;
  }

  public function construct_first_item() {

    $postNumber = $this->postCounter();

    $str = "DEFAULT";

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $product_query = mysqli_query($db, "SELECT * FROM Dirt WHERE id='$postNumber'");

    if (mysqli_num_rows($product_query) > 0) {

        $product_row = mysqli_fetch_array($product_query);

        $item_id = $product_row['id'];
        $item_title = $product_row['product_name'];
        $item_description = $product_row['product_description'];
        $item_health = $product_row['product_health'];
        $item_price = $product_row['product_price'];
        $item_picture = $product_row['product_picture'];

        $item_price_dollars = $item_price/100;
        $item_price_cents = $item_price%100;

        $item_price_formatted = number_format(($item_price /100), 2, '.', ' ');

        $description_array = array();

        array_push($description_array, $item_description);

        $dots = (strlen($description_array[0]) >= 90) ? "..." : "";
        $split = str_split($description_array[0], 90);
        $split = $split[0] . $dots;
        $item_description = $split;

        $arr = array('merchantID'=>$this->merchantID, 'totalCustomers'=>$this->totalCustomers, 'merchantName'=>$this->merchant, 'distance'=>$this->distance, 'waitingTime'=>$this->waitingTime, 'long'=>$this->long, 'lat'=>$this->lat, 'profile_pic'=>$item_picture, 'username'=>$this->username, 'itemPrice'=>$item_price_formatted, 'itemTitle'=>$item_title, 'itemDescription'=>$item_description, 'itemHealth'=>$item_health);

        $data_json = json_encode($arr);

        $str = "
              <div class='merchant_product_title'>

                <input type='submit' value='$item_title' onclick='createExtrasForm($data_json)' id='first_item_button'>

              </div>

              <div class='merchant_product_description'>
                $item_description
              </div>

              <div class='merchant_product_health'>
                $item_health kJ • $$item_price_formatted
              </div>
              ";

    }
    return $str;

  }

  public function construct_second_item() {

    $postNumber = $this->postCounter();

    $str = "DEFAULT";

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $product_query = mysqli_query($db, "SELECT * FROM Dirt WHERE id='$postNumber'");

    if (mysqli_num_rows($product_query) > 0) {

        $product_row = mysqli_fetch_array($product_query);

        $item_id = $product_row['id'];
        $item_title = $product_row['product_name'];
        $item_description = $product_row['product_description'];
        $item_health = $product_row['product_health'];
        $item_price = $product_row['product_price'];

        $item_price_dollars = $item_price/100;
        $item_price_cents = $item_price%100;

        $item_price_formatted = number_format(($item_price /100), 2, '.', ' ');

        $description_array = array();
        array_push($description_array, $item_description);

        $dots = (strlen($description_array[0]) >= 45) ? "..." : "";
        $split = str_split($description_array[0], 45);
        $split = $split[0] . $dots;
        $item_description = $split;

        $title_array = array();
        array_push($title_array, $item_title);

        $dotsTitle = (strlen($title_array[0]) >= 15) ? "..." : "";
        $splitTitle = str_split($title_array[0], 15);
        $splitTitle = $splitTitle[0] . $dotsTitle;
        $item_title = $splitTitle;

        $data = array();

        array_push($data, $this->username);
        array_push($data, $this->merchant);
        array_push($data, $item_title);
        array_push($data, $item_price_formatted);

        $data_json = json_encode($data);

        $str = "
              <div class='merchant_product_second_title'>

                <input type='submit' value='$item_title' onclick='createExtrasForm($data_json)' id='first_item_button'>

              </div>

              <div class='merchant_product_second_description'>
                $item_description
              </div>

              <div class='merchant_product_second_health'>
                $item_health kJ • $$item_price_formatted
              </div>
              ";


    }
    return $str;

  }

  public function construct_third_item() {

    $postNumber = $this->postCounter();

    $str = "DEFAULT";

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $product_query = mysqli_query($db, "SELECT * FROM Dirt WHERE id='$postNumber'");

    if (mysqli_num_rows($product_query) > 0) {

        $product_row = mysqli_fetch_array($product_query);

        $item_id = $product_row['id'];
        $item_title = $product_row['product_name'];
        $item_description = $product_row['product_description'];
        $item_health = $product_row['product_health'];
        $item_price = $product_row['product_price'];

        $item_price_formatted = number_format(($item_price /100), 2, '.', ' ');

        $title_array = array();

        array_push($title_array, $item_title);

        $dots = (strlen($title_array[0]) >= 12) ? "..." : "";
        $split = str_split($title_array[0], 12);
        $split = $split[0] . $dots;
        $item_title = $split;

        $description_array = array();

        array_push($description_array, $item_description);

        $dots = (strlen($description_array[0]) >= 30) ? "..." : "";
        $split = str_split($description_array[0], 30);
        $split = $split[0] . $dots;
        $item_description = $split;

        $data = array();

        array_push($data, $this->username);
        array_push($data, $this->merchant);
        array_push($data, $item_title);
        array_push($data, $item_price_formatted);

        $data_json = json_encode($data);

        $str = "
              <div class='merchant_product_third_title'>

                <input type='submit' value='$item_title' onclick='createExtrasForm($data_json)' id='first_item_button'>

              </div>

              <div class='merchant_product_third_description'>
                $item_description
              </div>

              <div class='merchant_product_third_health'>
                $item_health kJ • $$item_price_formatted
              </div>
              ";

    }
    return $str;

  }

  public function postCounter() {

    $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

    $Hit = $this->itemCounter;

    $tempHit = mysqli_query($db, "SELECT last_hit FROM merchants WHERE merchant_name='Dirt'");

    if(mysqli_num_rows($tempHit) > 0) {

      $hitHolder = mysqli_fetch_array($tempHit);

      $Hit = $hitHolder['last_hit'];

    }

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

      $this->itemCounter = $Hit;

      $update_query = mysqli_query($db, "UPDATE merchants SET last_hit='$Hit' WHERE merchant_name='Dirt'");

    }

      return $this->itemCounter;
    }
}

?>
