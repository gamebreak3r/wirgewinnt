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
        //creates the gameboard object for the game
        gameBoard = new Cell[boardSize][boardSize];
        for (int x = 0; x < gameBoard.length; x++) {
            for (int y = 0; y < gameBoard[x].length; y++) {
                gameBoard[x][y] = new Cell(x, y, 0, table);
            }
        }
    }

    public boolean checkfull(){ // checks the game for available spots to place a stone


        for (int i = 0; i <= 6; i++){
            if(gameBoard[i][6].status == 0 ) {
                return false;
            }

        }
        return true;
    }


    public int stonesInColumn(int column) { // returns the amount of stones in the current column
        int result = 0;
        for (Cell cell : gameBoard[column]) {
            if (cell.status != 0) result++;
        }
        return result;
    }

    public void putStone(int column, int row, boolean currentPlayer, View view) { //sets the status in the array and sets the backgroundcolor of the the imageview (depending on currentplayer)
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

        //marks the deciding stones with status 3 which won't be reseted

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
            case 4: //digonal2
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
        int vertical = 1; // counts the stones which next to each other in a vertical line
        int horizontallinks = 1; //counts the stones which next to each other in a horizontal line (going left from original stone)
        int horizontalrechts =1; // counts the stones which next to each other in a horizontal line (going right form original stone)
        int diagonallinks1 = 1; // counts the stones which next to each other in a diagonal line (going left down form original stone)
        int diagonalrechts1 = 1; // counts the stones which next to each other in a diagonal line (going right up form original stone)
        int diagonallinks2 = 1; // counts the stones which next to each other in a diagonal line (going left up form original stone)
        int diagonalrechts2 = 1; // counts the stones which next to each other in a diagonal line (going right down form original stone)
        int status = gameBoard[column][row].status; // saves status from current stone ( 1 / 2 depending on player) for following events


        for (int i = 1; i < 4; i++) {
            // region vertical
            if (row - i >= 0 ) {
                if (gameBoard[column][row - i].status == status) {
                    if (vertical == i) vertical++;
                    if (vertical >= 4) {
                        markStones(1,column, row, status);
                        return true;
                    }
                }
            }// endregion
            //region links horizontal
            if (column - i >= 0 ) {
                if (gameBoard[column - i][row].status == status) {
                    if (horizontallinks == i) horizontallinks++;
                    if (horizontallinks + horizontalrechts -1>= 4) {
                        markStones(2,column, row, status);
                        return true;
                    }
                }
            } //endregion
            //region rechts horizontal
            if (column + i <= 6 ) {
                if (gameBoard[column + i][row].status == status) {
                    if (horizontalrechts == i) horizontalrechts++;
                    if (horizontalrechts + horizontallinks-1 >= 4) {
                        markStones(2,column, row, status);
                        return true;
                    }
                }
            }// endregion
            // region diagonal1 links
            if (column - i >= 0 && row - i >=0) {
                if (gameBoard[column - i][row - i].status == status) {
                    if (diagonallinks1 == i) diagonallinks1++;
                    if (diagonallinks1 + diagonalrechts1 -1>= 4) {
                        markStones (3,column,row,status);
                        return true;
                    }
                }
            }//endregion
            //region diagonal1 rechts
            if (column + i <= 6 && row + i <= 6) {
                if (gameBoard[column+i][row+i].status == status) {
                    if (diagonalrechts1 == i)diagonalrechts1++;
                    if (diagonalrechts1 + diagonallinks1 -1>= 4) {
                        markStones (3,column,row,status);
                        return true;
                    }
                }
            }// endregion
            //region diagonal2 links
            if (column - i >= 0 && row + i <= 6) {
                if (gameBoard[column-i][row+i].status == status) {
                    if (diagonallinks2 == i) diagonallinks2++;
                    if (diagonallinks2 + diagonalrechts2 -1>= 4) {
                        markStones (4,column,row,status);
                        return true;
                    }
                }
            }//endregion
            //region diagonal2 rechts runter
            if (column + i <= 6 && row - i >=0) {
                if (gameBoard[column+i][row-i].status == status) {
                    if (diagonalrechts2 == i) diagonalrechts2++;
                    if (diagonalrechts2 + diagonallinks2 -1 >= 4) {
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

