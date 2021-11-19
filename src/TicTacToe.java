import java.util.*;


public class TicTacToe {
    /* AI */

    private boolean moveDone;   //If the computer has placed a marker or not every turn

    /**
     * Checks if a certain position has a marker already placed or not
     *
     * @param c is character to be checked
     * @return true if open, false if taken
     */
    private boolean isFree(char c){
        if(c == '~'){       //~ is initial value, so space is open
            return true;
        }
        else{
            return false;
        }
    }

    public void compMove(){
        int indexToPlace;       //Current index of row, col or diag where a marker should be placed

        //Initializing lists that store values of rows, cols, diags of current board state
        ArrayList<Integer> rowVals = new ArrayList<Integer>();
        ArrayList<Integer> colVals = new ArrayList<Integer>();
        ArrayList<Integer> diagVals = new ArrayList<Integer>();

        //Marker hasn't been placed
        moveDone = false;

        //Iterating through all rows, cols, diags and adding their values to list of values
        //Rows and Cols of index x will be added to their respective lists at index x
        for(int i = 0; i < 3; i++){
            rowVals.add(getRowVal(i));
            colVals.add(getColVal(i));
            if(i > 0 && i < 3){
                diagVals.add(getDiagVal(i));
            }
        }

        //Highest value for a set of 3 cells
        //Will keep on decreasing, checking value of 3 as it's the highest priority then continuing
        //to decrease if none of the rows, cols, or diags have the value
        int lineVal = 3;

        do{

            //If one of the diag has a value of 3 (Diag checked first as they're usually the most targeted)
            if(diagVals.contains(lineVal)){
                indexToPlace = diagVals.indexOf(lineVal);             //Finds the index of the value
                if(indexToPlace == 0){                          //index 0 signifies top left to bottom right diag
                    //If the board is free, and a marker hasn't been placed,
                    //go through the diagnonal until there's an open spot and place a marker
                    for(int j = 0; j < 3; j++){
                        if(isFree(board[j][j]) && !moveDone){
                            board[j][j] = 'O';
                            moveDone = true;
                        }
                    }
                }
                //Index to place is 1 (max 2 values will be added), so go through other diagonal
                //and place a marker in an empty spot
                else{
                    int k = 2;
                    for(int l = 0; l < 3; l++){
                        if(isFree(board[l][k]) && !moveDone){
                            board[l][k] = 'O';
                            moveDone = true;
                        }
                        k--;
                    }
                }
            }

            //Check if any rows have the value, then placing a marker in the row if there's space
            else if(rowVals.contains(lineVal)){
                indexToPlace = rowVals.indexOf(lineVal);
                for(int m = 0; m < 3; m++){
                    if(isFree(board[indexToPlace][m]) && !moveDone){
                        board[indexToPlace][m] = 'O';
                        moveDone = true;
                    }
                }
            }

            //Check if any columns have the value, then placing a marker in the column if there's space
            else if(colVals.contains(lineVal)){
                indexToPlace = colVals.indexOf(lineVal);
                for(int n = 0; n < 3; n++){
                    if(isFree(board[n][indexToPlace]) && !moveDone){
                        board[n][indexToPlace] = 'O';
                        moveDone = true;
                    }
                }
            }
            else{
                //Decreases priority value, so next time around the AI checks if
                //any rows, cols, or diags have a value of 2
                lineVal--;
            }

            /*if(!moveDone){
            if((board[0][0] == board[2][2] && board[2][2] == 'X') ||
                (board[2][0] == board[0][2] && board[2][0] == 'X')
                ){
                do{
                    int num = getRandomNum(1, 4);
                    switch(num){
                        case 1:
                            if(isFree(board[0][1])){
                                board[0][1] = 'O';
                                moveDone = true;
                            }
                            break;
                        case 2:
                            if(isFree(board[1][2])){
                                board[1][2] = 'O';
                                moveDone = true;
                            }
                            break;
                        case 3:
                            if(isFree(board[1][0])){
                                board[1][0] = 'O';
                                moveDone = true;
                            }
                            break;
                        case 4:
                            if(isFree(board[2][1])){
                                board[2][1] = 'O';
                                moveDone = true;
                            }
                            break;
                    }
                }
                while(!moveDone);
            }
            }*/

            //If value gets to 0 and there's no priority moves, place a marker in a corner
            if(lineVal == 0){
                placeCorner();
                moveDone = true;
            }

        }
        while(!moveDone);       //Do above until a marker has been placed
    }

    /**Note: When calculating values of rows, columns, diagonals, a value of:
     * 0 = row is full
     * 1 = low priority move --> only one marker in the row, no markers in the row, one of each marker
     * 2 = Must make a move in the row or opponent will win next turn
     * 3 = Make a move in this row to win
     */

    /**
     * Calculates a value of the row based on markers placed in the row
     *
     * @param index of row to be checked
     * @return value of the row
     */
    private int getRowVal(int row){
        int val = 0;        //Value of row based on markers placed
        int counter = 0;    //Num of markers in the row

        //Iterate through all elements in indicated row
        for(int i = 0; i < 3; i++){
            if(board[row][i] == 'X'){       //If there's an opponent marker
                val--;                      //Decrease value by 1
                counter++;
            }
            else if(board[row][i] == 'O'){  //If there's an AI marker
                val++;                      //Increase value by 1
                counter++;
            }
        }

        if(counter == 3){       //If row is full
            return 0;
        }
        else if(counter == 2){  //If there are two markers in row, next move is important
            switch(val){
                case 0:         //If value is 0, row is empty, or one of each marker, so return 1
                    return 1;
                case 2:         //If value is 2, place a marker here to win
                    return 3;
                case -2:        //If vaue is -2, opponent can win next turn, so block them
                    return 2;
            }
        }
        return 1;               //Return 1, as there are no high priority moves
    }

    /**
     * Calculates a value of each column based on markers placed in the column
     * //It follows the same logic as getRowVal, but checks columns
     *
     * @param index of column to be checked
     * @return value of the column
     */
    private int getColVal(int col){
        int val = 0;        //AI
        int counter = 0;

        for(int i = 0; i < 3; i++){
            //Checks elements of column for markers
            if(board[i][col] == 'X'){
                val--;
                counter++;
            }
            else if(board[i][col] == 'O'){
                val++;
                counter++;
            }
        }

        if(counter == 3){
            return 0;
        }
        else if(counter == 2){
            switch(val){
                case 0:
                    return 1;
                case 2:
                    return 3;
                case -2:
                    return 2;
            }
        }
        return 1;
    }

    /**
     * Calculates a value of each diagonal based on markers placed in the diagonal
     * Follows similar logic to getRowVal and getColVal
     * Changes will commented
     *
     * @param diag is the diagonal that should be checked
     * @return value of the diagonal
     */
    private int getDiagVal(int diag){
        int val = 0;
        int counter = 0;

        //Diag 1 indicates top left to bottom right diagonal
        if(diag == 1){
            //Checks elements of this diagonal for markers
            for(int i = 0; i < 3; i++){
                if(board[i][i] == 'X'){
                    val--;
                    counter++;
                }
                else if(board[i][i] == 'O'){
                    val++;
                    counter++;
                }
            }
        }
        //Diag 2 indicates top right to bottom left diagonal
        else if(diag == 2){
            //Checks elements of this diagonal for markers
            int j = 2;
            for(int i = 0; i < 3; i++){
                if(board[i][j] == 'X'){
                    val--;
                    counter++;
                    j--;
                }
                else if(board[i][j] == 'O'){
                    val++;
                    counter++;
                    j--;
                }
            }
        }

        if(counter == 3){
            return 0;
        }
        else if(counter == 2){
            switch(val){
                case 0:
                    return 1;
                case 2:
                    return 3;
                case -2:
                    return 2;
            }
        }
        return 1;
    }

    /**
     * Places an AI marker in a random corner
     */
    private void placeCorner(){
        //Depending on the random number, a marker is placed in that corner if it's open
        switch(getRandomNum(4, 1)){
            case 1:
                if(isFree(board[0][0])){
                    board[0][0] = 'O';
                    moveDone = true;
                }
                break;
            case 2:
                if(isFree(board[0][2])){
                    board[0][2] = 'O';
                    moveDone = true;
                }
                break;
            case 3:
                if(isFree(board[2][0])){
                    board[2][0] = 'O';
                    moveDone = true;
                }
                break;
            case 4:
                if(isFree(board[2][2])){
                    board[2][2] = 'O';
                    moveDone = true;
                }
                break;
        }
    }


    /*Game*/
    public char[][] board;     //Current board
    public char winner;        //Winner of game


    //Constructor
    public TicTacToe(){
        board = new char[3][3];     //New 3x3 2D array as a board
        newBoard(board);            //Initializes empty board
    }

    public char[][] getBoard() {
        return this.board;
    }

    //Getter method for winner
    public char getWinner(){
        return winner;
    }

    /**
     * Checks if a there's a winner
     *
     * @return true if someone has 3 markers in a row, false if no winner
     */
    public boolean isWinner(){
        //If any row, col or diag has 3 of the same markers in a row, there's a winner
        if(checkRows() || checkColumns() || checkDiag()){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Checks to see if any rows have three of the same char
     *
     * @return true if there's a winner
     */
    private boolean checkRows(){
        //Iterates through every row and checks if any row has 3 identical markers
        for(int i = 0; i < 3; i++){
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '~'){
                winner = board[i][0];       //Winner is the marker char in the winning row
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if any columns have three of the same char
     *
     * @return true if there's a winner
     */
    private boolean checkColumns(){
        //Iterates through every column and checks if any column has 3 identical markers
        for(int i = 0; i < 3; i++){
            if(board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '~'){
                winner = board[0][i];       //Winner is the marker char in the winning row
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if any diagonals have three of the same char
     *
     * @return true if there's a winner
     */
    private boolean checkDiag(){
        //Iterates through top left diagonal and checks if there's a winner
        if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '~'){
            winner = board[0][0];           //Winner is the marker char in this diagonal
            return true;
        }
        //Same thing, but other diagonal
        if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '~'){
            winner = board[0][2];
            return true;
        }
        //If no winner, return false
        else{
            return false;
        }
    }

    /**
     * Checks to see if the board is full
     *
     * @return true if no more open spaces
     */
    public boolean isBoardFull(){
        //Iterates through entire board, and if any space is empty (~), it returns false
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] == '~'){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Places a marker for the user in given position
     *
     * @param row is the row index
     * @param column is the column index
     * @return true if the spot is open and marker was placed
     */
    public boolean userMove(int row, int column){
        if(isFree(board[row][column])){
            board[row][column] = 'X';
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * Initializes the board with all empty spaces
     *
     * @param board is a 2D array tictactoe board
     */
    private void newBoard(char[][] board){
        //Iterates through entire board and assigns (~) to every cell, indicating it's empty
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = '~';
            }
        }
    }

    /**
     * Prints current board state by getting every element of the 2D board and printing
     * it with lines
     */
    public void printBoard(){
        System.out.println("    1   2   3");
        for(int i = 0; i < 3; i++){
            System.out.print((i+1) + "  ");
            for(int j = 0; j < 2; j++){
                System.out.print(" " + board[i][j] + " |");
            }
            System.out.println(" " + board[i][2]);
            if(i < 2){
                System.out.println("  ----+---+----");
            }
        }
    }


    public static int getRandomNum(int max, int min){
        return (int)(Math.random() * ((max-min) + 1)) + min;
    }

}
