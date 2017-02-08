<?php
$Lusername = $_GET['Lusername'];
$Lpasswort = $_GET['Lpasswort'];
$Rusername = $_GET['Rusername'];
$Rpasswort = $_GET['Rpasswort'];
$db_link = mysqli_connect (localhost, wirgewinnt, ABCD,  wirgewinnt);
if ($Lusername != "" AND $Lpasswort != "")
{
	$sql = "SELECT * FROM user where username='" . $Lusername . "'";
	$db_erg = mysqli_query( $db_link, $sql );
	if ( ! $db_erg )
		{
  			die('Ungültige Abfrage: ' . mysqli_error());
		}
	while ($zeile = mysqli_fetch_array( $db_erg, MYSQL_ASSOC))
	{
		if ($zeile['passwort'] == hash('sha256', $Lpasswort))
		{
			echo "true";
		}
		else{
			echo "false";
		}
	}
	mysqli_free_result( $db_erg );
}
else if ($Rusername != "" AND $Rpasswort != "")
{
	$hash = hash('sha256', $Rpasswort);
	$sql = "INSERT INTO user (username, passwort) values ('" . $Rusername . "', '" . $hash . "')";
	$db_erg = mysqli_query( $db_link, $sql );
	if ( ! $db_erg )
		{
  			echo "false";
		}
	else{
  			echo "true";
	}
}
else{
echo "Fehler";
}
?>