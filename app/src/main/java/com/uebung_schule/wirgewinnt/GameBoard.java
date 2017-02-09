package com.uebung_schule.wirgewinnt;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

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

    public boolean checkfull(){ // check für unentschieden
        boolean checkfull = true;

        for (int i = 0; i <= 6; i++){
            if(gameBoard[i][6].status == 0 ) {
                checkfull = false;
                break;
            }

        }
        return checkfull;
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

    private void markStones(int mode, int column, int row, int status){
        int i = 0;
        int j = 1;
        switch (mode){
            case 1:  //vertical
                for (; i <4; i++) gameBoard[column][row-i].status = 3;
                break;
            case 2: //horizontal
                 while (column - i >= 0 && gameBoard[column-i][row].status == status) {
                    gameBoard[column-i][row].status = 3;
                    i++;
                }
                for (;i<4; i++) if (column + j <7 &&gameBoard[column+j][row].status == status) gameBoard[column+j][row].status = 3;
                break;


            case 3: //digonal1
                while (column - i >= 0 && row -i >= 0 && gameBoard[column-i][row-i].status == status) {
                    gameBoard[column-i][row-i].status = 3;
                    i++;
                }
                for (;i<4; i++) if (column + j <7 && row + j < 7 &&gameBoard[column+j][row+j].status == status) gameBoard[column+j][row+j].status = 3;
                break;
            case 4:
                while (column - i >= 0 && row +i <= 6 && gameBoard[column-i][row+i].status == status) {
                    gameBoard[column-i][row+i].status = 3;
                    i++;
                }
                for (;i<4; i++) if (column + j <7 && row - j >= 0 &&gameBoard[column+j][row-j].status == status) gameBoard[column+j][row-j].status = 3;

                break;
            default:

                break;


        }

    }
    public boolean checkIfWon(int column, int row, View v) {
        int vertical = 1;
        int horizontal = 1;
        int diagonal1 = 1;
        int diagonal2 = 1;
        int status = gameBoard[column][row].status;


        for (int i = 1; i <= 4; i++) {
            if (row - i >= 0 ) {// region vertical
                if (gameBoard[column][row - i].status == status) {
                    vertical++;
                    if (vertical >= 4) {
                        markStones(1,column, row, status);
                        return true;
                    }
                }
            }// endregion

            if (column - i >= 0 ) {//region links horizontal
                if (gameBoard[column - i][row].status == status) {
                    horizontal++;
                    if (horizontal >= 4) {
                        markStones(2,column, row, status);
                        return true;
                    }
                }
            } //endregion

            if (column + i <= 6 ) {//region rechts horizontal
                if (gameBoard[column + i][row].status == status) {
                    horizontal++;
                    if (horizontal >= 4) {
                        markStones(2,column, row, status);
                        return true;
                    }
                }
            }// endregion

            if (column - i >= 0 && row - i >=0) {// region diagonal1 links
                if (gameBoard[column - i][row - i].status == status) {
                    diagonal1++;
                    if (diagonal1 >= 4) {
                        markStones (3,column,row,status);
                        return true;
                    }
                }
            }//endregion

            if (column + i <= 6 && row + i <= 6) {//region diagonal1 rechts
                if (gameBoard[column+i][row+i].status == status) {
                    diagonal1++;
                    if (diagonal1 >= 4) {
                        markStones (3,column,row,status);
                        return true;
                    }
                }
            }// endregion

            if (column - i >= 0 && row + i <= 6) {//region diagonal2 links
                if (gameBoard[column-i][row+i].status == status) {
                    diagonal2++;
                    if (diagonal2 >= 4) {
                        markStones (4,column,row,status);
                        return true;
                    }
                }
            }//endregion

            if (column + i <= 6 && row - i >=0) {//region diagonal2 rechts runter
                if (gameBoard[column+i][row-i].status == status) {
                    diagonal2++;
                    if (diagonal2 >= 4) {
                        markStones (4,column,row,status);
                        return true;
                    }
                }
            }//endregion
        }


        return false;

    }

    public Cell[][] getGameBorad ()
    {
        return gameBoard;
    }



}

