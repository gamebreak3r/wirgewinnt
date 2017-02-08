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
    private int status = 0;
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


    public boolean checkIfWon(int column, int row, View v) {
        int vertical = 1;
        int horizontal = 1;
        int diagonal1 = 1;
        int diagonal2 = 1;
        //status 3 = vertical
        boolean linkshorizontal = true; //status 4
        boolean rechtshorizontal = true; //status 5
        boolean linksdiagonal1 = true; //status 6
        boolean rechtsdiagonal1 = true; //status 7
        boolean linksdiagonal2 = true; //status 8
        boolean rechtsdiagonal2 = true; //status 9


        status = gameBoard[column][row].status;


        for (int i = 1; i <= 4; i++) {
            if (row - i >= 0 ) {//region vertical
                if (gameBoard[column][row - i].status == status) {
                    vertical++;

                    if (vertical >= 4) {
                        gameBoard[column][row].status = 3;
                        gameBoard[column][row-1].status = 3;
                        gameBoard[column][row-2].status = 3;
                        gameBoard[column][row-3].status = 3;
                        reset(3,99);
                        return true;
                    }
                }
            }// endregion

            if (column - i >= 0 ) {//region links horizontal
                if (gameBoard[column - i][row].status == status && linkshorizontal) {
                    horizontal++;
                    if (horizontal >= 4) {
                        gameBoard[column][row].status = 4;
                        gameBoard[column-1][row].status =4;
                        gameBoard[column-2][row].status =4;
                        gameBoard[column-3][row].status =4;
                        reset(4,5);
                        return true;
                    }
                } else{
                    linkshorizontal = false;
                }
            } //endregion

            if (column + i <= 6 ) {//region rechts horizontal
                if (gameBoard[column + i][row].status == status && rechtshorizontal) {
                    horizontal++;
                    if (horizontal >= 4) {
                        gameBoard[column][row].status = 5;
                        gameBoard[column-1][row].status =5;
                        gameBoard[column-2][row].status =5;
                        gameBoard[column-3][row].status =5;
                        reset(4,5);
                        return true;
                    }
                } else
                    rechtshorizontal = false;
            }// endregion

            if (column - i >= 0 && row - i >=0) {// region diagonal1 links
                if (gameBoard[column - i][row - i].status == status && linksdiagonal1) {
                    diagonal1++;
                    if (diagonal1 >= 4) {
                        gameBoard[column][row].status = 6;
                        gameBoard[column-1][row].status =6;
                        gameBoard[column-2][row].status =6;
                        gameBoard[column-3][row].status =6;
                        reset(6,7);
                        return true;
                    }
                } else{
                  linksdiagonal1 = false;
                }
            }//endregion

            if (column + i <= 6 && row + i <= 6) {//region diagonal1 rechts
                if (gameBoard[column+i][row+i].status == status && rechtsdiagonal1) {
                    diagonal1++;
                    if (diagonal1 >= 4) {
                        gameBoard[column][row].status = 7;
                        gameBoard[column-1][row].status =7;
                        gameBoard[column-2][row].status =7;
                        gameBoard[column-3][row].status =7;
                        reset(6,7);
                        return true;
                    }
                }else {
                    rechtsdiagonal1 = false;
                }
            }// endregion

            if (column - i >= 0 && row + i <= 6) {//region diagonal2 links
                if (gameBoard[column-i][row+i].status == status && linksdiagonal2) {
                    diagonal2++;
                    if (diagonal2 >= 4) {
                        gameBoard[column][row].status = 8;
                        gameBoard[column-1][row].status =8;
                        gameBoard[column-2][row].status =8;
                        gameBoard[column-3][row].status =8;
                        reset(8,9);
                        return true;
                    }
                } else {
                    linksdiagonal2 = false;
                }
            }//endregion

            if (column + i <= 6 && row - i >=0) {//region diagonal2 rechts
                if (gameBoard[column+i][row-i].status == status && rechtsdiagonal2) {
                    diagonal2++;
                    if (diagonal2 >= 4) {
                        gameBoard[column][row].status = 9;
                        gameBoard[column-1][row].status =9;
                        gameBoard[column-2][row].status =9;
                        gameBoard[column-3][row].status =9;
                        reset(8,9);
                        return true;
                    }
                } else {
                    rechtsdiagonal2 = false;
                }
            }//endregion
        }


        return false;

    }
    private void reset (int status1, int status2){
        for (int i = 0; i <= 6; i++)
        {
            for (int a = 0; a <= 6; a++) {
                if(gameBoard[i][a].status != status1 && gameBoard[i][a].status != status2) gameBoard[i][a].status = status;
            }
        }
    }

    public Cell[][] getGameBorad ()
    {
        return gameBoard;
    }



}

