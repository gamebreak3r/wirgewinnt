<!DOCTYPE html>
<html>
<head>
  <title>Wirgewinnt</title>
</head>
<body>
<?php
        //Create new game
        $createGame = $_GET['createGame'];
        //If there is a active game
        $gameID = $_GET['gameID'];
        $player = $_GET['player'];
		#setStone in game
        $putStone = $_GET['putStone'];
		//returns a list of the active games
		$getActiveGames = $_GET['getActiveGames'];
		//get stones of the game
		$getStones = $_GET['getStones'];
		//Remove aktive Game
		$removeGame = $_GET['removeGame'];
		//add the player name Join
		$joinGameName = $_GET['joinGameName'];
		//set the player win
		$winPlayer = $_GET['winPlayer'];
		//set the player lose
		$losePlayer = $_GET['losePlayer'];
		//check if someone has won
		$hasWon = $_GET['hasWon'];
        //DB Link
        $db_link = mysqli_connect (localhost, wirgewinnt, ABCD,  wirgewinnt);

		#resets the active games, if now one sets a stone after some time.
		$sqltime = "update multiplayer set active = 0 where date < date('d.m.Y H:i:S',time()-5*60)";
        mysqli_query( $db_link, $sqltime );
		
		//Create new Game and returns the gameID
        if ($createGame != "")
        {
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
		#adds the player name for the player2 to a active game
		else if ($joinGameName != "" AND $gameID != "")
		{
                $sql = "UPDATE multiplayer SET playerName2='" . $joinGameName . "' where gameid=" . $gameID;
                $db_erg = mysqli_query( $db_link, $sql );
                if ($db_erg)
		        {
  		        	echo "true";
	        	}
               	else{
  		        	echo "false";
            	}
		}
		#set a stone for a player in a game
        else if ($gameID != "" AND $player != "" AND $putStone != "")
        {
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
		#returns the sones from the gameID
		else if ($getStones != "")
		{
			 $sql = "Select player1, player2 from multiplayer where gameid=" . $getStones;
             foreach ($db_link->query($sql) as $zeile)
             {
                echo "##" . $zeile['player1']. "##" .$zeile['player2'] . "##";
             }
		}
		#set a active game inactive
		else if ($removeGame != "")
		{
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
		#retuns a list of active games
		else if ($getActiveGames != "")
		{
			 $sql = "Select gameid, playerName from multiplayer where active=1";
             foreach ($db_link->query($sql) as $zeile)
             {
                $id = intval($zeile['gameid']);
                echo ";" . $id . "#" . $zeile['playerName'] . ";";
             }
		}
		#checks if a player has already won the game
		else if($gameID != "" AND $hasWon != "")
		{
				$sqlSel = "select count(*) as anzahl from multiplayer where gameid=" . $gameID . " and (win='' or win IS NULL)";
				foreach ($db_link->query($sqlSel) as $zeile)
				{
					if ($zeile['anzahl'] == 1)
					{
						echo "false";
					}
					else {
						echo "true";
					}
				}
		}
		#set the win Player for the gameID
		else if ($gameID != "" AND $winPlayer != "")
		{
				$sqlSel = "select count(*) as anzahl from multiplayer where gameid=" . $gameID . " and (win='' or win IS NULL)";
				foreach ($db_link->query($sqlSel) as $zeile)
				{
					if ($zeile['anzahl'] == 1)
					{
						$sql = "UPDATE multiplayer SET win='" . $winPlayer . "' where gameid=" . $gameID . " and (win='' or win IS NULL)";
						$db_erg = mysqli_query( $db_link, $sql );
						if ($db_erg)
						{
							echo "true";
						}
					}
					else{
						echo "false";
					}
				}
		}
		#set the lose player for the gameID
		else if ($gameID != "" AND $losePlayer != "")
		{
                $sql = "UPDATE multiplayer SET lose='" . $losePlayer . "' where gameid=" . $gameID;
                $db_erg = mysqli_query( $db_link, $sql );
                if ($db_erg)
		        {
  		        	echo "true";
	        	}
               	else{
  		        	echo "false";
            	}
		}
		?>
</body>
</html>