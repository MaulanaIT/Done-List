<?php
require_once "connector.php";
    
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $id = $_POST['id'];
    $aktivitas = $_POST['aktivitas'];
    
    $query = "INSERT INTO activity (id_user, activity) VALUES ('$id', '$aktivitas')";
    
    $result = mysqli_query($connection, $query);
    
    if ($result) {
        echo "Insert Successfully";
    } else {
        echo "Insert Failed";
    }
    
    mysqli_close($connection);
}    
?>