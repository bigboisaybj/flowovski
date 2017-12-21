<?php
include("../../config/config.php");
include("../../includes/classes/User.php");

$query = $_POST['query'];
$userLoggedIn = $_POST['userLoggedIn'];

$names = explode(" ", $query);


//If query contains an underscore, assume user is searching for usernames
if(strpos($query, '_') !== false)
    $usersReturnedQuery = mysqli_query($con, "SELECT * FROM merchants WHERE merchant_name LIKE '$query%'");

//If there are two words, assume they are first & last names
else if(count($names) == 2)
    $usersReturnedQuery = mysqli_query($con, "SELECT * FROM merchants WHERE (merchant_name LIKE '$names[0]%' AND merchant_name LIKE '$names[1]%')");

//if query has one word only, search first or last names
else
    $usersReturnedQuery = mysqli_query($con, "SELECT * FROM merchants WHERE (merchant_name LIKE '$names[0]%' OR merchant_name LIKE '$names[0]%')");

if($query != ""){

    while($row = mysqli_fetch_array($usersReturnedQuery)) {
      $user = new User($con, $userLoggedIn);

      $mutual_friends = 0;

      echo "<div class='resultDisplay'>
              <a href='" . $row['merchant_name'] . "' style='color: $1485BD'>
                  <div class='liveSearchProfilePic'>
                      <img src='" . $row['profile_pic'] . "'>
                  </div>

                  <div class='liveSearchText'>
                      " . $row['merchant_name'] . " " . $row['manager_first_name'] . "
                      <p>" . $row['username'] . "</p>
                      <p id='grey'>" . $mutual_friends . "</p>

                  </div>
              </a>
              </div>";
              }
    }
    ?>
