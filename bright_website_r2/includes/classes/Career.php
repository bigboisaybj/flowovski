<?php

class Career {
  private $user_obj;
  private $con;

  public function __construct($con, $user){
    $this->con = $con;
    $this->user_obj = new User($con, $user);
  }

  public function loadCareers() {

    $str = "";

    $query = mysqli_query($this->con, "SELECT * FROM careers WHERE available='yes'");

    if(mysqli_num_rows($query) > 0) {

      while($row = mysqli_fetch_array($query)) {

        $job_title = $row['job_title'];
        $job_suburb = $row['job_location_suburb'];
        $job_country = $row['job_location_country'];
        $job_summary = $row['job_summary'];
        $job_about_one = $row['job_about_one'];
        $job_about_two = $row['job_about_two'];
        $job_about_three = $row['job_about_three'];
        $job_commitment = $row['job_commitment'];
        $job_salary = $row['job_salary'];
        $job_start = $row['job_start'];

        $job_salary = number_format($job_salary);

        $str .= "
                <div class='career_post'>
                  <br>

                  <div class='career_title'>
                  <a href='#' type='text' id='apply_career'>
                  $job_title
                  </a>
                  </div>

                  <div class='career_location'>
                  $job_suburb, $job_country
                  </div>

                  <div class='career_summary'>
                  $job_summary
                  </div>


                  <div class='career_about'>
                  <ol>
                    <li>$job_about_one</li>
                    <br>
                    <li>$job_about_two</li>
                    <br>
                    <li>$job_about_three</li>
                  <ol>
                  </div>

                  <br>

                  <div class='commitment'>
                  $job_commitment. $job_start start. $$job_salary p/a.
                  </div>

                </div>


                ";
      }
    }
    echo $str;
  }
}



 ?>
