<!DOCTYPE html>
<html>
<head>
  <title>Wirgewinnt</title>
</head>
<body>
<?php
	//DB Link
    $db_link = mysqli_connect (localhost, wirgewinnt, ABCD,  wirgewinnt);
	//User name
	$player = $_GET['player'];
	//Win / Lose
	$winLose = $_GET['winLose'];
	
	#If the winLose is = win player gets a return of his multiplayer wins
	if ($player != "" AND $winLose == "win")
	{
		$sql = "select playerName, count(playername) as wins from multiplayer where win='". $player . "' group by playerName";
		$db_erg = mysqli_query( $db_link, $sql );
        foreach ($db_link->query($sql) as $zeile)
        {
           echo "##" . $zeile['wins'] . "##";
        }	
	}
	
	#If the winLose is = lose player gets a return of his multiplayer loses
	else if ($player != "" AND $winLose == "lose")
	{
		$sql = "select playerName, count(playername) as lose from multiplayer where lose='". $player . "' group by playerName";
		$db_erg = mysqli_query( $db_link, $sql );
        foreach ($db_link->query($sql) as $zeile)
        {
           echo "##" . $zeile['lose'] . "##";
        }	
	}
?>
</body>
</html>