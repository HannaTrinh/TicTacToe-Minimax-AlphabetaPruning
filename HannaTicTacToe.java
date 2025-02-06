import java.util.HashSet;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class HannaTicTacToe {
    private static PrintWriter debugOutput;
    private static HashSet<String> moves = new HashSet<String>();

    public static void setupLogger() {
        try {
            debugOutput = new PrintWriter(new FileWriter("debug.log", false), true);
        } catch (IOException e) {
            System.err.println("Could not open debug log file for writing.");
            e.printStackTrace();
        }
    }

    public static void log(String message) {
        if (debugOutput != null) {
            debugOutput.println(message);
        }
    }

    public static void closeLogger() {
        if (debugOutput != null) {
            debugOutput.close();
        }
    }

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
            // System.out.println("Updating position: row: " + row + ", col: " + col);
            board[row][col] = colour;
            // printBoard(board);
            return;
        }

        int row = 3 - (position.charAt(1) - '0');
        int col = position.charAt(0) - 'a';
        board[row][col] = colour;
        // printBoard(board);
    }

    private static void printBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static String printBoardToString(char[][] board) {
        log("Printing board");
        String boardString = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardString += board[i][j] + " ";
            }
            boardString += "\n";
        }
        return boardString;
    }

    private static boolean isValidMove(String position) {
        if (position.charAt(0) != 'a' && position.charAt(0) != 'b' && position.charAt(0) != 'c') {
            int row = (int) position.charAt(0) - '0';
            int col = (int) position.charAt(1) - '0';
            if (row < 0 || row > 2 || col < 0 || col > 2) {
                return false;
            }
            return !moves.contains(row + "" + col);
        }

        // int row = 3 - (position.charAt(1) - '0');
        // int col = position.charAt(0) - 'a';
        if (!moves.contains(position)) {
            moves.add(position);
            return true;
        } else {
            return false;
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

    private static String isTerminate(char[][] board, char currentPlayer, char opponentColour) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) { // Check rows
                if (board[i][0] == currentPlayer) {
                    return "End: Player " + currentPlayer + " wins";
                } else if (board[i][0] == opponentColour) {
                    return "End: Player " + opponentColour + " wins";
                }
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) { // Check columns
                if (board[0][i] == currentPlayer) {
                    return "End: Player " + currentPlayer + " wins";
                } else if (board[0][i] == opponentColour) {
                    return "End: Player " + opponentColour + " wins";
                }
            }
        }
        if (isDraw(board)) {
            return "End: Draw";
        }
        return "";
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
                    board[i][j] = myColour;
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

    private static int minimax(char[][] board, int depth, boolean isMaximizing, char currentPlayer, char opponentColour,
            int alpha, int beta) {
        // printBoard(board);
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
                        board[i][j] = currentPlayer;
                        int val = minimax(board, depth + 1, false, currentPlayer, opponentColour, alpha, beta);
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
                        int val = minimax(board, depth + 1, true, currentPlayer, opponentColour, alpha, beta);
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
        // tail -f debug.log
        setupLogger();

        char[][] board = new char[3][3];
        initializeBoard(board);
        log(printBoardToString(board));
        log("Starting game, waiting for referee colour assignment");
        Scanner scanner = new Scanner(System.in);

        char myColour = '\0';
        char opponentColour = '\0';
        boolean isMyturn = false;

        if (scanner.hasNextLine()) {
            myColour = scanner.nextLine().trim().charAt(0);
            opponentColour = myColour == 'b' ? 'o' : 'b';
            isMyturn = myColour == 'b' ? true : false;
            log("My colour: " + myColour + ", Opponent colour: " + opponentColour);
            log("Waiting for Player " + myColour + " first move");
            if (isMyturn) {
                String bestMove = findBestMove(board, myColour, opponentColour);
                String bestMoveConverted = (char) ('a' + (bestMove.charAt(1) - '0')) + ""
                        + (3 - (bestMove.charAt(0) - '0'));
                System.out.println(bestMoveConverted);
                System.out.flush();
                log("Player " + myColour + " move: " + bestMoveConverted);
                updateBoard(bestMoveConverted, myColour, board);
                isMyturn = false;
            }
        }

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (input.length() >= 3) { // End of game
                log(input);
                break;
            }

            if (!isMyturn) {
                log("Waiting for Player " + opponentColour + " move");
                if (isValidMove(input)) {
                    updateBoard(input, opponentColour, board);
                    log("Player " + opponentColour + " move: " + input);
                    log(printBoardToString(board));
                    if (isTerminate(board, myColour, opponentColour).length() > 1) {
                        log(isTerminate(board, myColour, opponentColour));
                        break;
                    }
                    String bestMove = findBestMove(board, myColour, opponentColour);
                    String bestMoveConverted = (char) ('a' + (bestMove.charAt(1) - '0')) + ""
                            + (3 - (bestMove.charAt(0) - '0'));
                    System.out.println(bestMoveConverted);
                    System.out.flush();
                    updateBoard(bestMoveConverted, myColour, board);
                    log("Player " + myColour + " move: " + bestMoveConverted);
                    log(printBoardToString(board));
                    isMyturn = false;
                } else {
                    log("Invalid move: " + input);
                    break;
                }
            } else { // My turn
                String bestMove = findBestMove(board, myColour, opponentColour);
                String bestMoveConverted = (char) ('a' + (bestMove.charAt(1) - '0')) + ""
                        + (3 - (bestMove.charAt(0) - '0'));
                System.out.println(bestMoveConverted);
                System.out.flush();
                updateBoard(bestMoveConverted, myColour, board);
                log("Player " + myColour + " move: " + bestMoveConverted);
                log(printBoardToString(board));
                isMyturn = false;
            }
            if (isTerminate(board, myColour, opponentColour).length() > 1) {
                log(isTerminate(board, myColour, opponentColour));
                break;
            }
        }
        scanner.close();
        closeLogger();
    }
}