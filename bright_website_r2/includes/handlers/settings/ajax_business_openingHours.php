<?php

if(isset($_POST['venueName'])){

  echo "

  <div class='openingHours' id='openingHours_input'>

  <div class='openingHours_Table'>

    <table>
      <tr>
        <th>Monday</th>
        <th>Tuesday</th>
        <th>Wednesday</th>
        <th>Thursday</th>
        <th>Friday</th>
        <th>Saturday</th>
        <th>Sunday</th>
      </tr>
        <tr>
        <td><input type='text' class='registration_time_input' id='monday_opening' onfocus='checkInput_Files()' required></td>
        <td><input type='text' class='registration_time_input' id='tuesday_opening' onfocus='checkInput_Files()' required></td>
        <td><input type='text' class='registration_time_input' id='wednesday_opening' onfocus='checkInput_Files()' required></td>
        <td><input type='text' class='registration_time_input' id='thursday_opening' onfocus='checkInput_Files()' required></td>
        <td><input type='text' class='registration_time_input' id='friday_opening' onfocus='checkInput_Files()' required></td>
        <td><input type='text' class='registration_time_input' id='saturday_opening' onfocus='checkInput_Files()' required></td>
        <td><input type='text' class='registration_time_input' id='sunday_opening' onfocus='checkInput_Files()' required></td>
        </tr>
        <tr>
        <td><input type='text' class='registration_time_input' id='monday_closing' required></td>
        <td><input type='text' class='registration_time_input' id='tuesday_closing' required></td>
        <td><input type='text' class='registration_time_input' id='wednesday_closing' required></td>
        <td><input type='text' class='registration_time_input' id='thursday_closing' required></td>
        <td><input type='text' class='registration_time_input' id='friday_closing' required></td>
        <td><input type='text' class='registration_time_input' id='saturday_closing' required></td>
        <td><input type='text' class='registration_time_input' id='sunday_closing' required></td>
        </tr>
    </table>
  </div>

  </div>

        ";

}


 ?>
