package com.uebung_schule.wirgewinnt;

/**
 * Created by Andreas on 11/11/2016.
 */

public class GameBoard {

    //region variables
    private Cell[][] gameBoard;
    //endregion

    public GameBoard(int boardSize) {
        gameBoard = new Cell[boardSize][boardSize];
        for (int x = 0; x < gameBoard.length; x++) {
            for (int y = 0; y < gameBoard[x].length; y++) {
                gameBoard[x][y] = new Cell(x, y, 0);
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

    public void putStone(int column, int row, boolean currentPlayer) {
        if (currentPlayer) {
            gameBoard[column][row].status = 1;

        } else {
            gameBoard[column][row].status = 2;
        }
        //TODO: setStatus(player)
    }

    public void checkStatus(int column, int row, int status) {

        int count_x = 0;
        int count_y = 0;
        int count_xy_up = 0;
        int go_right = 6 - column;
        int go_up = 6 - row;
        int go_up_right = 0;
        int go_down_left = 0;
        int go_down_right = 0;
        int go_up_left = 0;

        //int go_left = column; wird nicht gebraucht da beide das selebe ist und nix hochgezählt wird
        //int go_down = row; ""

        //check was kleiner ist
        if (go_up > go_right){
            go_up_right = go_right;
        } else {
            go_up_right = go_up;
        }

        //check was kleiner ist
        if (row > column){
            go_down_left = column;
        } else {
            go_down_left = row;
        }

        //check was kleiner ist
        if (row > go_right){
            go_down_right = go_right;
        } else {
            go_down_right = row;
        }

        //TODO: letzter check was größer ist



        int i = 1;
        //Rechts
        while (i <= go_right) {
            if (gameBoard[column + i][row].status == status) {
                count_x++;
                i++;
            }
            else {
                break;
            }
        }

        i = 1; //Zurücksetzen
        // Links
        while (i <= column) {
            if (gameBoard[column - i][row].status == status) {
                count_x++;
                i++;
            }
            else {
                break;
            }
        }

        i = 1; //Zurücksetzen
        // Hoch
        while (i <= go_up){
            if (gameBoard[column][row+i].status == status){
                count_y++;
                i++;
            }
            else {
                break;
            }
        }

        i = 1; //Zurücksetzen
        // Runter
        while (i <= row){
            if (gameBoard[column][row-i].status == status){
                count_y++;
                i++;
            }
            else{
                break;
            }

        }

        i = 1; //Zurücksetzen

        while (i <= go_up_right){
            if (gameBoard[column+i][row+i].status == status){
                count_xy_up++;
                i++;
            } else {
                break;
            }
        }
        i = 1; // Zurücksetzen
        while (i <= go_down_left){
            if (gameBoard[column-i][row-i].status == status){
                count_xy_up++;
                i++;
            } else {
                break;
            }
        }

        i = 1; // Zurücksetzen

    }
}

