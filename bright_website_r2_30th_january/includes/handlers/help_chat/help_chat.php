<?php

$zeus = mysqli_connect("localhost", "root", "aloysius2", "faq");

$userLoggedIn = "";
$merchantName = "";

if(isset($_POST['username'])){

  $userLoggedIn = $_POST['username'];
}

$messages_loaded = "";

//$query = mysqli_query($this->con, "UPDATE messages SET opened='yes' WHERE user_from='$userLoggedIn' AND merchantName='$merchantName'");

$get_help_messages_query = mysqli_query($zeus, "SELECT body, user_from FROM help_messages WHERE user_from='$userLoggedIn' AND user_to='Bright'") or die(mysqli_error($zeus));

if(mysqli_num_rows($get_help_messages_query) > 0){

  while($row = mysqli_fetch_array($get_help_messages_query)){
    $userName = $row['user_from'];
    $body = $row['body'];

    $div_top = ($userName == $userLoggedIn) ? "<div class='message_item' id='green'>" : "<div class='message_item' id='blue'>";

    $message = $data . $div_top . $body . "</div><br><br>";

    $messages_loaded .= $message;
  }

}

else{
  $messages_loaded = "";
}

$data_json = json_encode($userLoggedIn);

$str =  "<div class='messages_help'>

					<div class='loaded_messages_help' id='scroll_messages_help' style='height:80px; overflow:scroll'>".
						$messages_loaded
					."</div>

					<hr>

				   <div class='message_post'>

						 <textarea name='message_body' class='message_input_help message_textarea' id='message_textarea_help' placeholder='Write your message ...' onkeyup='enterKeySubmit_help($data_json, ".event.", ".this.")'></textarea>

				</div>";

        ?>


				<script>

				var div = document.getElementById("scroll_messages_help");
				div.scrollTop = div.scrollHeight;

				</script>

				<?php

echo $str;
