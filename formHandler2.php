<?php
session_start();
?>
<!DOCTYPE html>
<html>
<head>
  <title>Simple form handler</title>
</head>

<body bgcolor="#EEEEEE">
  <center><h2>Simple Form Handler</h2></center>
  <p>
    The following table lists all parameter names and their values that were submitted from your form.
  </p>

  <table cellSpacing=1 cellPadding=1 width="75%" border=1 bgColor="lavender">
    <tr bgcolor="#FFFFFF">
      <td align="center"><strong>Parameter</strong></td>
      <td align="center"><string>Value</string></td>
    </tr>
      <?php 
      foreach( $_POST as $key => $val ) {
        $_SESSION[$key] = $val;
        if( is_array( $val ) ) {
          echo "<tr><td>$key</td><td>";
          foreach( $val as $current ) {
            echo "$current, ";
          }
          echo "</td></tr>";
        } 
        else {
          echo "<tr><td>$key</td><td>$val</td></tr>";
        }
      }
      /*
      if(isset($_REQUEST["destination"])) {
        header("Location: {$_REQUEST["destination"]}");
      }
      else if(isset($_SERVER["HTTP_REFERER"])) {
        header("Location: {$_SERVER["HTTP_REFERER"]}");
      }
      else {}*/
      ?>
  
   
  </table>

<input type="button" onclick="location.href='assignment4.php';" value="Return" />
</body>

</html> 