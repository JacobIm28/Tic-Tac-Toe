import java.util.*;
public class Game extends ConsoleProgram
{
    public void run()
    {
        Scanner in = new Scanner(System.in);

        //Creating game board and printing the starting state of board
        TicTacToe game = new TicTacToe();
        game.printBoard();

        //Alternate between asking user for their move and letting AI do their turn
        do{
            //User input
            System.out.println("Enter the row where you would like to place your marker: ");
            int row = in.nextInt() -1;
            System.out.println("Enter the column where you would like to place your marker: ");
            int column = in.nextInt() -1;

            //While a marker hasn't been placed, ask for a spot to place a marker
            while(!game.userMove(row, column)){
                System.out.println("This position is taken. Choose another spot:");
                System.out.println("Row:");
                row = in.nextInt() -1;
                System.out.println("Column:");
                column = in.nextInt() -1;
            }

            //Once there is valid input, marker is placed and new board state is printed
            game.userMove(row, column);
            System.out.println();
            game.printBoard();



            //If game isn't over, user is informed it's their turn
            if(!game.isBoardFull() && !game.isWinner()){
                System.out.println("Your turn.");
                //AI places a marker, then new board state is printed
                System.out.println("\nComputer's move:");
                game.compMove();
                System.out.println();
                game.printBoard();
                System.out.println();
            }
        }
        //Do until the board is full or there's a winner
        while(!game.isBoardFull() && !game.isWinner());

        //If the board is full and there's no winner, it's a tie
        if(game.isBoardFull() && !game.isWinner()){
            System.out.println("You tied");
        }
        //If there's a winner, print out the winner
        else if(!game.isBoardFull() && game.isWinner()){
            System.out.println("\n" + game.getWinner() + " wins!");
        }
    }

}
