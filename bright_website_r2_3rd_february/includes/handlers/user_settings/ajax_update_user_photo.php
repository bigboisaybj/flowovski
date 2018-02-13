<?php

$target_dir = "uploads/user_photos/";
$target_for_path = "uploads/user_photos";
$target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
$uploadOk = 1;
$imageFileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));
// Check if image file is a actual image or fake image

$userLoggedIn = "";

if(isset($_POST['userRegoHolder'])){
  $userLoggedIn = $_POST['userRegoHolder'];
}

if(isset($_POST["submit"])) {

    $check = getimagesize($_FILES["fileToUpload"]["tmp_name"]);
    if($check !== false) {
        $uploadOk = 1;
    } else {
        $uploadOk = 0;
    }
}

// Allow certain file formats
if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg"
&& $imageFileType != "gif" ) {
    $uploadOk = 0;
}

if ($uploadOk == 0) {
// if everything is ok, try to upload file

} else {

    if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {

        $db = mysqli_connect("localhost", "root", "aloysius2", "bright");

        $absolutePath = realpath(dirname(__FILE__));

        $finalPath = $absolutePath . "/" . $target_for_path;

        $fileName = basename( $_FILES["fileToUpload"]["name"]);

        $beginOfPath = "http://localhost/~bryanjordan/";

        $midOfPath = substr($absolutePath, 25);

        $midOfPath = $midOfPath . "/";

        $endOfPath = $target_for_path . "/";

        $completePath = $beginOfPath . $midOfPath . $endOfPath . $fileName;

        $toBeHashed = $userLoggedIn . $fileName;

        $hashValue = hash('ripemd160', $toBeHashed);

        $date = date("Y-m-d H:i:s");

        //$insertMerchantDetails = mysqli_query($db, "INSERT INTO menus VALUES ('0', '$hashValue', '$fileName', '$absolutePath', '$userLoggedIn', '$date')") or die(mysqli_error($db));

        $updateUserPhoto = mysqli_query($db, "UPDATE users SET profile_pic='$completePath' WHERE username='$userLoggedIn'");

        $updateUserToManager = mysqli_query($db, "UPDATE users SET merchant_manager='yes' WHERE username='$userLoggedIn'");

        //echo "The file ". basename( $_FILES["fileToUpload"]["name"]). " has been uploaded.";

    } else {
        echo "Sorry, there was an error uploading your file.";
    }

}

header("Location: ../../../index.php"); /* Redirect browser */
exit();

?>
