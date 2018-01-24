<?php

$first = array(1,3,4,4);
$second = array(2,5,5,8);

$results = array();
$totalSum = 0;

for($i = 0; $i < sizeof($first); $i++){

  $firstVal = $first[$i];
  $secondVal = $second[$i];

  $result = $firstVal * $secondVal;
  array_push($results, $result);
}

$totalSum = array_sum($results);

$first_sum = array_sum($first);

$second_sum = array_sum($second);

$first_squared = array();
$second_squared = array();

for($i = 0; $i < sizeof($first); $i++){

  $firstSquare = pow($first[$i], 2);
  $secondSquare = pow($second[$i], 2);

  array_push($first_squared, $firstSquare);
  array_push($second_squared, $secondSquare);
}

$square_first_sum = array_sum($first_squared);

$square_second_sum = array_sum($second_squared);

$numerator_part_a = $totalSum;
$numerator_part_b_one = $first_sum * $second_sum;
$numberator_part_b_two = sizeof($first);

$numerator_part_b_total = $numerator_part_b_one/$numberator_part_b_two;
$numerator = $numerator_part_a - $numerator_part_b_total;

$denominator_part_a_one = $square_first_sum;
$denominator_part_a_two = pow($first_sum, 2)/sizeof($first);
$denominator_a = $denominator_part_a_one - $denominator_part_a_two;

$denominator_part_b_one = $square_second_sum;
$denominator_part_b_two = pow($second_sum, 2)/sizeof($second);
$denominator_b = $denominator_part_b_one - $denominator_part_b_two;
$denominator_total = $denominator_a * $denominator_b;
$denominator_sqrt = sqrt($denominator_total);
//9
//sqrt(6 * 18)
$total = $numerator/$denominator_sqrt;
echo $total;
 ?>
