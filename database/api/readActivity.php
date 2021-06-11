<?php
require_once "connector.php";
    
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $id = $_POST['id'];
    
    $query = "SELECT * FROM activity WHERE id_user='$id'";
    
    $result = mysqli_query($connection, $query);
    
    if (mysqli_num_rows($result) > 0) {
        $data = array();
        while($row = mysqli_fetch_assoc($result)) {
            $data[] = $row;
        }
                
        echo json_encode($data);
    } else {
        echo "Failed";
    }
    
    mysqli_close($connection);
}    
?>