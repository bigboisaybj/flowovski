<?php

class Item {
  private $con;
  private $username;
  private $merchant;
  private $merchantID;
  private $itemCounter;

  public function __construct($con, $username, $merchant, $merchantID) {
    $this->con = $con;
    $this->username = $username;
    $this->merchant = $merchant;
    $this->merchantID = $merchantID;
    $this->itemCounter = 0;
  }

  public function construct_first_item($item_count) {

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

        $data = array();


        array_push($data, $this->username);
        array_push($data, $this->merchant);
        array_push($data, $item_title);
        array_push($data, $item_price);
        array_push($data, $item_picture);
        array_push($data, $this->merchantID);
        array_push($data, $item_id);

        $data_json = json_encode($data);

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
        array_push($data, $item_price);

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
        array_push($data, $item_price);

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

<script type="text/javascript">

function createExtrasForm(data) {

  $.ajax({
    type: "POST",
    url: "includes/handlers/ajax_create_extras_input.php",
    data: {
        userName: data[0],
        merchantName: data[1],
        itemTitle: data[2],
        itemPrice: data[3],
        product_picture: data[4],
        merchantID: data[5],
        itemID: data[6],
    },
    success: function(response) {
      $(".extras_area").html(response);
    }

  });
}


</script>
