<!DOCTYPE html>
<html>
<head>
  <title>Wirgewinnt</title>
</head>
<body>
<?php
        //Neues Spiel anelegen
        $createGame = $_GET['createGame'];
        //Wenn bereits ein Spiel existiert
        $gameID = $_GET['gameID'];
        $player = $_GET['player'];
        $putStone = $_GET['putStone'];
        //Aufruf der aktuellen Steine
        $getPlayer = $_GET['getPlayer'];
		//Aufruf der Aktiven Spiele
		$getActiveGames = $_GET['getActiveGames'];
		//Get Stones in aktive Game
		$getStones = $_GET['getStones'];
		//Remove aktive Game
		$removeGame = $_GET['removeGame'];
        //Datenbank
        $db_link = mysqli_connect (localhost, wirgewinnt, ABCD,  wirgewinnt);

        if ($createGame != "")
        {
            //Create new Game:
            //http://wirgewinnt.square7.ch/html/multiplayer.php?createGame=new
                $sql = "INSERT INTO multiplayer (playerName) values ('". $createGame ."')";
                $db_erg = mysqli_query( $db_link, $sql );
                if ($db_erg)
                {
                    $sql2 = "SELECT MAX(gameid) from multiplayer";
                    $db_erg2 = mysqli_query( $db_link, $sql2 );
                    while ($zeile = mysqli_fetch_array( $db_erg2, MYSQL_ASSOC))
                     {
                        $id = intval($zeile['MAX(gameid)']);
                        echo "GameID;" . $id . ";";
                     }
            	}
               	else{
  		        	echo "Fehler GameID";
            	}
        }
        else if ($gameID != "" AND $player != "" AND $putStone != "")
        {
            //Befehl zum hinzufügen eines Steins:
            //http://wirgewinnt.square7.ch/html/multiplayer.php?gameID=1&player=1&putStone=11
            if ($player == "1") //Player 1 = true
            {
                 $sql = "UPDATE multiplayer SET player1=CONCAT(player1, ';'," . $putStone . ") where gameid=" . $gameID;
                 $db_erg = mysqli_query( $db_link, $sql );
                if ($db_erg)
		        {
  		        	echo "true";
	        	}
               	else{
  		        	echo "false";
            	}
            }
            else if ($player == "2") //Player 2 = false
            {
                 $sql = "UPDATE multiplayer SET player2=CONCAT(player2, ';'," . $putStone . ") where gameid=" . $gameID;
                 $db_erg = mysqli_query( $db_link, $sql );
                if ($db_erg)
		        {
  		        	echo "true";
	        	}
               	else{
  		        	echo "false";
            	}
            }
        }
		else if ($getStones != "")
		{
			//http://wirgewinnt.square7.ch/html/multiplayer.php?getStones=16
			 $sql = "Select player1, player2 from multiplayer where gameid=" . $getStones;
             foreach ($db_link->query($sql) as $zeile)
             {
                echo "##" . $zeile['player1']. "##" .$zeile['player2'] . "##";
             }
		}
		else if ($removeGame != "")
		{
			//http://wirgewinnt.square7.ch/html/multiplayer.php?removeGame=16
			 $sql = "UPDATE multiplayer SET active=0 where gameid=" . $removeGame;
                 $db_erg = mysqli_query( $db_link, $sql );
                if ($db_erg)
		        {
  		        	echo "true";
	        	}
               	else{
  		        	echo "false";
            	}
		}
		else if ($getActiveGames != "")
		{
			//http://wirgewinnt.square7.ch/html/multiplayer.php?getActiveGames=true
			 $sql = "Select gameid, playerName from multiplayer where active=1";
             foreach ($db_link->query($sql) as $zeile)
             {
                $id = intval($zeile['gameid']);
                echo ";" . $id . "#" . $zeile['playerName'] . ";";
             }
		}
		//Clear Old Games
		/*$sqltime = "Select UNIX_TIMESTAMP(DATE_ADD(date, INTERVAL 5 MINUTE)) as date, gameid, UNIX_TIMESTAMP(CURDATE()) as nowdate from multiplayer where active=1";
             foreach ($db_link->query($sqltime) as $zeileTime)
             {
                $id = intval($zeileTime['gameid']);
				echo $zeileTime['date'] . ' TEST ' . $zeileTime['nowdate'];
				if ($zeileTime['date'] <= $zeileTime['nowdate']) 
				{
					$sql = "UPDATE multiplayer SET active=0 where gameid=" . $id;
					$db_erg = mysqli_query( $db_link, $sql );
				}
             }*/
		?>
</body>
</html>