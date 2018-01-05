<?php
ob_start(); //Turns on output buffering
session_start();

$timezone = date_default_timezone_set("Australia/Sydney");

$con = mysqli_connect("localhost", "root", "aloysius2", "bright");
if(mysqli_connect_errno())
{
	echo "Failed to connect: " . mysqli_connect_errno();
}

?>
