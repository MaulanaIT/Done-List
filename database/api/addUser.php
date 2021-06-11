<?php
require_once "connector.php";
    
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    
    $password_hash = password_hash($password, PASSWORD_DEFAULT);
    
    $query = "INSERT INTO user (username, email, password) VALUES ('$username', '$email', '$password_hash')";
    
    $result = mysqli_query($connection, $query);
    
    if ($result) {
        echo "Success";
    } else {
        echo "Failed";
    }
    
    mysqli_close($connection);
}    
?>