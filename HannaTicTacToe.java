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

        int row = 3 - (position.charAt(1) - '0');
        int col = position.charAt(0) - 'a';
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

    private static String findBestMove(char[][] board, char myColour, char opponentColour) {
        int bestScore = Integer.MIN_VALUE;
        String bestMove = ""; // Initialize best move to an invalid position
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = opponentColour;
                    int score = minimax(board, 0, false, myColour, opponentColour, Integer.MIN_VALUE,
                            Integer.MAX_VALUE);
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

    private static int minimax(char[][] board, int depth, boolean isMaximizing, char myColour, char opponentColour,
            int alpha, int beta) {
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
                        board[i][j] = myColour;
                        int val = minimax(board, depth + 1, false, myColour, opponentColour, alpha, beta);
                        board[i][j] = '-';
                        bestScore = Math.max(bestScore, val);
                        alpha = Math.max(alpha, bestScore);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return bestScore;
        } else { // Minimizing
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = opponentColour;
                        int val = minimax(board, depth + 1, true, myColour, opponentColour, alpha, beta);
                        board[i][j] = '-';
                        bestScore = Math.min(bestScore, val);
                        beta = Math.min(beta, bestScore);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return bestScore;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] board = new char[3][3];
        String bestMove = "";
        String bestMoveConverted = "";

        // Initialize board
        initializeBoard(board);
        // printBoard(board);

        // Get colour assignment
        System.out.println("Assign Colour: b or o");
        String input = scanner.nextLine().trim();
        char hanna = input.charAt(0); // Get's colour assignment
        input = scanner.nextLine().trim();
        char opponentColour = input.charAt(0); // Assigns opponent colour

        if (hanna == 'b') {
            bestMove = findBestMove(board, hanna, opponentColour);
            bestMoveConverted = (char) ('a' + (bestMove.charAt(1) - '0')) + "" + (3 - (bestMove.charAt(0) - '0'));
            System.out.println(bestMoveConverted);
            updateBoard(input, hanna, board);
        } else {
            input = scanner.nextLine().trim();
            updateBoard(input, opponentColour, board);
        }

        while (scanner.hasNextLine()) {
            input = scanner.nextLine().trim();
            updateBoard(input, opponentColour, board);
            if (scanner.hasNext("END")) {
                System.out.println(input);
                break;
            }
            bestMove = findBestMove(board, hanna, opponentColour);
            bestMoveConverted = (char) ('a' + (bestMove.charAt(1) - '0')) + "" + (3 - (bestMove.charAt(0) - '0'));
            System.out.println(bestMoveConverted);
            updateBoard(bestMove, hanna, board);
            if (scanner.hasNext("END")) {
                System.out.println(input);
                break;
            }
            System.out.flush();
        }
        scanner.close();
    }
}