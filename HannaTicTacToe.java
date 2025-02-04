import java.util.Scanner;

public class HannaTicTacToe {
    private static void initializeBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    private static void updateBoard(String position, char colour, char[][] board) {
        if (position.charAt(0) != 'a' && position.charAt(0) != 'b' && position.charAt(0) != 'c') {
            int row = (int) position.charAt(0) - '0';
            int col = (int) position.charAt(1) - '0';
            System.out.println("Updating position: row: " + row + ", col: " + col);
            board[row][col] = colour;
            printBoard(board);
            return;
        }

        int col = position.charAt(0) - 'a';
        int row = 3 - (position.charAt(1) - '0');
        board[row][col] = colour;
        printBoard(board);
    }

    private static void printBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean isWin(char[][] board) {
        // Check rows, columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '-') { // Check rows
                return true;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '-') { // Check columns
                return true;
            }
        }
        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') {
            return true;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '-') {
            return true;
        }
        return false;
    }

    private static boolean isDraw(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private static int evaluate(char[][] board, char currentPlayer, char opponentColour) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '-') { // Check rows
                return (board[i][0] == currentPlayer) ? 10 : -10;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '-') { // Check columns
                return (board[0][i] == currentPlayer) ? 10 : -10;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') { // Check diagonals
            return (board[0][0] == currentPlayer) ? 10 : -10;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '-') { // Check diagonals
            return (board[0][2] == currentPlayer) ? 10 : -10;
        }
        return 0;
    }

    private static String findBestMove(char[][] board, char myColour, char opponentColour) {
        int bestScore = Integer.MIN_VALUE;
        String bestMove = ""; // Initialize best move to an invalid position
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = opponentColour;
                    int score = minimax(board, 0, false, myColour, opponentColour);
                    board[i][j] = '-';
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = i + "" + j;
                    }
                }
            }
        }
        return bestMove;
    }

    private static int minimax(char[][] board, int depth, boolean isMaximizing, char myColour, char opponentColour) {
        if (isWin(board)) {
            return (isMaximizing ? -10 : 10) + (isMaximizing ? depth : -depth);
        }

        if (isDraw(board)) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = opponentColour;
                        int val = minimax(board, depth + 1, false, myColour, opponentColour);
                        board[i][j] = '-';
                        bestScore = Math.max(bestScore, val);
                    }
                }
            }
            return bestScore;
        } else { // Minimizing
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = myColour;
                        int val = minimax(board, depth + 1, true, myColour, opponentColour);
                        board[i][j] = '-';
                        bestScore = Math.min(bestScore, val);
                    }
                }
            }
            return bestScore;
        }
    }

    public static void main(String[] args) {

        char[][] board = new char[3][3];
        initializeBoard(board);
        printBoard(board);

        Scanner scanner = new Scanner(System.in);
        char myColour = 'b';
        char opponentColour = (myColour == 'b') ? 'o' : 'b';
        while (true) {
            System.out.println("Enter your move:");
            String input = scanner.nextLine().trim();
            System.out.println("Your move is: " + input);
            updateBoard(input, myColour, board);
            if (isWin(board)) {
                System.out.println("You win!");
                break;
            }
            if (isDraw(board)) {
                System.out.println("Draw!");
                break;
            }
            String bestMove = findBestMove(board, myColour, opponentColour);
            System.out.println("Best move is: " + bestMove);
            updateBoard(bestMove, opponentColour, board);
            if (isWin(board)) {
                System.out.println("You lose!");
                break;
            }
            if (isDraw(board)) {
                System.out.println("Draw!");
                break;
            }
            System.out.flush();
        }
        scanner.close();

        // System.out.println("Best move is: " + bestMove);
        // System.out.println("Best move is: " + bestMove / 3 + " " + bestMove % 3);
        // // Convert move to a,b,c and 1,2,3
        // char row = (char) (bestMove / 3 + 'a');
        // char col = (char) (bestMove % 3 + '1');
        // System.out.println("Best move is: " + row + col);
        // initializeBoard();
        // Scanner scanner = new Scanner(System.in);
        // myColour = scanner.nextLine().charAt(0);
        // opponentColour = (myColour == 'b') ? 'o' : 'b';

        // if (myColour == 'b') { // First move
        // queue.add(board, 0, null, myColour);

        // String bestMove = minimax(" ", 9, true);
        // System.out.println(bestMove);
        // queue.add(bestMove);
        // updateBoard(bestMove, myColour);
        // System.out.flush();
        // } else { // Wait for opponent to move
        // String input = scanner.nextLine().trim();
        // queue.add(input);
        // updateBoard(input, opponentColour);
        // String bestMove = minimax(input, 9, true);
        // System.out.println(bestMove);
        // queue.add(bestMove);
        // updateBoard(bestMove, myColour);
        // System.out.flush();
        // }

        // while (scanner.hasNextLine()) {
        // String input = scanner.nextLine().trim();
        // String bestMove = minimax(input, 9, true);
        // System.out.println(bestMove);
        // queue.add(bestMove);
        // updateBoard(bestMove, myColour);
        // System.out.flush();
        // }
        // scanner.close();
    }
}