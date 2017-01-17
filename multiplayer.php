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
        //Datenbank
        $db_link = mysqli_connect (localhost, wirgewinnt, ABCD,  wirgewinnt);

        if ($createGame != "")
        {
            //Create new Game:
            //http://wirgewinnt.square7.ch/html/multiplayer.php?createGame=new
                $sql = "INSERT INTO multiplayer (player1) values ('')";
                $db_erg = mysqli_query( $db_link, $sql );
                if ($db_erg)
                {
                    $sql2 = "SELECT MAX(gameid) from multiplayer";
                    $db_erg2 = mysqli_query( $db_link, $sql2 );
                    while ($zeile = mysqli_fetch_array( $db_erg2, MYSQL_ASSOC))
                     {
                        $id = intval($zeile['MAX(gameid)']);
                        echo "GameID:" . $id;
                     }
            	}
               	else{
  		        	echo "Fehler GameID";
            	}
        }
        else if ($getPlayer != "" AND $gameID != "")
        {
            //http://wirgewinnt.square7.ch/html/multiplayer.php?getPlayer=1&
                $sql = "SELECT MAX(gameid) from multiplayer";
                $db_erg = mysqli_query( $db_link, $sql );

                    while ($zeile = mysqli_fetch_array( $db_erg2, MYSQL_ASSOC))
                     {
                        $id = intval($zeile['MAX(gameid)']);
                        echo "GameID:" . $id;
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
        else{
            echo "Fehler";
        }
?>
</body>
</html>