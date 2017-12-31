<?php

class Price {
  private $con;
  private $username;
  private $totalPrice;

  public function __construct($con, $username) {
    $this->con = $con;
    $this->username = $username;
    $this->totalPrice = 0;
  }

  public function getTotalPrice() {
    return $this->totalPrice;
  }

  public function addToTotalPrice($addition) {
    $this->totalPrice += $addition;
  }

  public function subtractFromTotalPrice($subtract) {
    $this->totalPrice -= $subtract;
  }

}


?>
