<?php

include("config/config.php");
include("includes/classes/User.php");
include("includes/classes/Post.php");
include("includes/classes/Career.php");
 ?>

<div class="careers_section">
  <div class="title">
    Towards artificial consciousness...
  </div>
  <div class='career_subheading'>
    Bright believes in creating a new type of dimensionality in life that
    will expand the capability of each individual. In the past few decades
    we've been witness to the power of personal computers. The next step is
    to amalgamate modern and future functionality into an intangible process
    that is readily available to everyone.
  </div>

    <?php

    $career = new Career($con, $userLoggedIn);
    $career->loadCareers();


     ?>
</div>
