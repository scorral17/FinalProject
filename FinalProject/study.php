<?php
   function My_login($username, $password){
      $con= mysqli_connect("localhost","root","root","hw1_db");

      if (mysqli_connect_errno($con)) {
         echo "Failed to connect to MySQL: " . mysqli_connect_error();
      }
      $sql_command = "SELECT user_name, status, update_frequency FROM tb_Users WHERE user_name='{$username}' and password='{$password}'";
      $result = mysqli_query($con, $sql_command);
      if($result->num_rows>0){
         $row = $result->fetch_assoc();
         $username = $row['user_name'];
         $status = $row['status'];
	 $frequency = $row['update_frequency'];
         echo 'Succeed;' . $username . ';' . $status . ';' . $frequency;
      }
      else{
         echo mysqli_connect_error();
      }
      $con->close();   
   }

   function register($username, $password, $locmac){
      $con= mysqli_connect("localhost","root","root","hw1_db");

      if (mysqli_connect_errno($con)) {
         echo "Failed to connect to MySQL: " . mysqli_connect_error();
      }

      $sql_command = "INSERT INTO tb_Users(user_name,password,device_mac,status) VALUES('{$username}','{$password}', '{$locmac}', 'online')";

      if (mysqli_query($con, $sql_command)) {
         echo "New record created successfully";
      } 
      else {
         echo "Failed";
      }

   }

  
   function search($building){

      $con= mysqli_connect("localhost","root","root","hw1_db");

      if (mysqli_connect_errno($con)) {
         echo "Failed to connect to MySQL: " . mysqli_connect_error();
      }

      $sql_command = "select device_mac, floor from tb_Users join tb_Location on connected_mac=mac where building = '{$building}' and status = 'online' order by floor";
      $result = mysqli_query($con, $sql_command);

      if($result->num_rows>0){
         while($row = $result->fetch_assoc()){
            $user = $row['device_mac'];
            $floor = $row['floor'];
            echo $user . ';' . $floor . '\\';
         }
      }

      else {
         echo "Failed";
      }

   }

   function region($building, $floor){

      $con= mysqli_connect("localhost","root","root","hw1_db");

      if (mysqli_connect_errno($con)) {
         echo "Failed to connect to MySQL: " . mysqli_connect_error();
      }

      $sql_command = "select region, count(region) as total from tb_Users join tb_Location on connected_mac=mac where building = '{$building}' and status = 'online' and floor = '{$floor}' group by region
";
      $result = mysqli_query($con, $sql_command);

      if($result->num_rows>0){
         while($row = $result->fetch_assoc()){
            $region = $row['region'];
            $total = $row['total'];
            echo $region . ';' . $total . '\\';
         }
      }

      else {
         echo "Failed";
      }

   }


   function online($username, $password, $mac){

      $con= mysqli_connect("localhost","root","root","hw1_db");

      if (mysqli_connect_errno($con)) {
         echo "Failed to connect to MySQL: " . mysqli_connect_error();
      }

      $sql_command = "select building, floor, region from tb_Location WHERE mac='{$mac}'";
      $result = mysqli_query($con, $sql_command);
      
      if($result->num_rows>0){
         $row = $result->fetch_assoc();
	 $building = $row['building'];
         $floor = $row['floor'];
	 $region = $row['region'];
         $sql_command = "update tb_Users set status = 'online' WHERE user_name='{$username}' and password='{$password}'";
         mysqli_query($con, $sql_command);
         echo $building . ';' . $floor . ';' . $region;
       }
       else{
          echo "Failed";
       }

   }

   function logout($username, $password){

      $con= mysqli_connect("localhost","root","root","hw1_db");

      if (mysqli_connect_errno($con)) {
         echo "Failed to connect to MySQL: " . mysqli_connect_error();
      }

      $sql_command = "update tb_Users set status = 'offline' WHERE user_name='{$username}' and password='{$password}'";

      if (mysqli_query($con, $sql_command)) {
         echo "Record updated successfully";
      } 
      else {
         echo "Failed";
      }

   }

   function update($mac, $region){
     
      $con= mysqli_connect("localhost","root","root","hw1_db");

      if (mysqli_connect_errno($con)) {
         echo "Failed to connect to MySQL: " . mysqli_connect_error();
      }

      $sql_command = "update tb_Location set region = '{$region}' WHERE mac='{$mac}'";

      if (mysqli_query($con, $sql_command)) {
         echo "Record updated successfully";
      } 
      else {
         echo "Failed";
      }


   }

   function add($building, $floor, $mac, $region){
      $con= mysqli_connect("localhost","root","root","hw1_db");

      if (mysqli_connect_errno($con)) {
         echo "Failed to connect to MySQL: " . mysqli_connect_error();
      }

      $sql_command = "INSERT INTO tb_Location(building,floor,mac,region) VALUES('{$building}','{$floor}', '{$mac}', '{$region}')";

      if (mysqli_query($con, $sql_command)) {
         echo "New record created successfully";
      } 
      else {
         echo "Failed";
      }
   }


   $method = $_POST['method'];
   switch($method){
      case 'login':
         $username = $_POST['username'];
         $password = $_POST['password'];
	 My_login($username,$password);
         break;
      case 'register':
         $username = $_POST['username'];
         $password = $_POST['password'];
	 $locmac = $_POST['locmac'];
	 register($username,$password,$locmac);
         break;
      case 'search':
         $building = $_POST['building'];
	 search($building);
         break;
      case 'logout':
         $username = $_POST['username'];
         $password = $_POST['password'];
	 logout($username,$password);
         break;
      case 'online':
         $username = $_POST['username'];
         $password = $_POST['password'];
         $mac = $_POST['mac'];
	 online($username,$password,$mac);
         break;
      case 'update':
         $mac = $_POST['mac'];
         $region = $_POST['region'];
	 update($mac,$region);
         break;
      case 'region':
         $building = $_POST['building'];
         $floor = $_POST['floor'];
	 region($building,$floor);
         break;
      case 'add':
         $building = $_POST['building'];
         $floor = $_POST['floor'];
         $mac = $_POST['mac'];
         $region = $_POST['region'];
	 add($building,$floor,$mac,$region);
         break;
      default: break;
   }



 
?>