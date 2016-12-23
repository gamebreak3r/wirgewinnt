package com.uebung_schule.wirgewinnt;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Andreas on 11/11/2016.
 */

public class GameBoard {

    //region variables
    private Cell[][] gameBoard;
    //endregion

    public GameBoard(int boardSize, View table) {
        gameBoard = new Cell[boardSize][boardSize];
        for (int x = 0; x < gameBoard.length; x++) {
            for (int y = 0; y < gameBoard[x].length; y++) {
                gameBoard[x][y] = new Cell(x, y, 0, table);
            }
        }
    }


    public int stonesInColumn(int column) {
        int result = 0;
        for (Cell cell : gameBoard[column]) {
            if (cell.status != 0) result++;
        }
        return result;
    }

    public void putStone(int column, int row, boolean currentPlayer, View view) {
        ImageView table = (ImageView) view.findViewById(1000+column*10+row);
        if (currentPlayer) {
            gameBoard[column][row].status = 1;
            table.setBackgroundColor(Color.RED);
            }
         else {
            gameBoard[column][row].status = 2;
            table.setBackgroundColor(Color.BLUE);
        }
    }

    /*
    _ _ _ _ _ _ _
    _ _ _ _ _ _ _
    _ _ _ _ _ _ _
    _ _ _ X X _ _
    _ _ _ O X _ _
    _ _ O O X _ _
    _ _ X O X _ _

        try {
        //code
        //exception
        //more code
    } catch (Exception e) {
        Log.e("test", "checkIfWon: fehler", e);
    } finally {
        //code
    }

     */

    public boolean checkIfWon(int column, int row) {
        int vertical = 1;
        int horizontal = 1;
        int diagonal1 = 1;
        int diagonal2 = 1;

        boolean checkIfWon = false;
        int status = gameBoard[column][row].status;



            // region vertical
            for (int i = 1; i <= 4; i++) {
                if (row - i >= 0 ) {
                    if (gameBoard[column][row - i].status == status) {
                        vertical++;
                        if (vertical >= 4) checkIfWon = true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            } // endregion

            //region horizontal

            //links
            for (int i = 1; i <= 4; i++) {
                if (column - i >= 0 ) {
                    if (gameBoard[column-i][row].status == status) {
                        horizontal++;
                        if (horizontal >= 4) checkIfWon = true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }

            //rechts
            for (int i = 1; i <= 4; i++) {
                if (column + i <= 6 ) {
                    if (gameBoard[column+i][row].status == status) {
                        horizontal++;
                        if (horizontal >= 4) checkIfWon = true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }// endregion





        //gameBoard[column - i][row].status == status
        System.out.println(checkIfWon);
        return checkIfWon;

    }



}

