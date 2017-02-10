<?php
#Login Username
$Lusername = $_GET['Lusername'];
#Login Password
$Lpasswort = $_GET['Lpasswort'];
#Register Username
$Rusername = $_GET['Rusername'];
#Register Password
$Rpasswort = $_GET['Rpasswort'];
#DB Link
$db_link = mysqli_connect (localhost, wirgewinnt, ABCD,  wirgewinnt);
#Check if User uses the Login funktion
if ($Lusername != "" AND $Lpasswort != "")
{
	#Selects the user
	$sql = "SELECT * FROM user where username='" . $Lusername . "'";
	$db_erg = mysqli_query( $db_link, $sql );
	#If user isn't in the DB
	if ( ! $db_erg )
		{
  			die('Ungültige Abfrage: ' . mysqli_error());
		}
		
	while ($zeile = mysqli_fetch_array( $db_erg, MYSQL_ASSOC))
	{
		#Checks the User password with a hash sha25
		if ($zeile['passwort'] == hash('sha256', $Lpasswort))
		{
			#userdata is correct
			echo "true";
		}
		else{
			echo "false";
		}
	}
	mysqli_free_result( $db_erg );
}

#Check if User registers
else if ($Rusername != "" AND $Rpasswort != "")
{
	#hash the password
	$hash = hash('sha256', $Rpasswort);
	#insert user into db
	$sql = "INSERT INTO user (username, passwort) values ('" . $Rusername . "', '" . $hash . "')";
	$db_erg = mysqli_query( $db_link, $sql );
	#insert is ok
	if ( ! $db_erg )
		{
  			echo "false";
		}
	else{
  			echo "true";
	}
}
#counterfeiter php call
else{
echo "Error";
}
?>