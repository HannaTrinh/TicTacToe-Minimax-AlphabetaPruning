import java.util.Queue;
import java.util.Scanner;
import java.util.LinkedList;

public class HannaTicTacToe {
    public class Node {
        char[][] board;
        int depth;
        String move;
        char colour;

        public Node(char[][] board, int depth, String move, char colour) {
            this.board = board;
            this.depth = depth;
            this.move = move;
            this.colour = colour;
        }
    }

    // private static char[][] board = new char[3][3];
    private static Queue<Node> queue = new LinkedList<>();
    private static char myColour;
    private static char opponentColour;

    // private static void initializeBoard() {
    // for (int i = 0; i < 3; i++) {
    // for (int j = 0; j < 3; j++) {
    // board[i][j] = ' ';
    // }
    // }
    // }

    private static void updateBoard(String position, char colour) {
        int row = position.charAt(0) - 'a'; // Convert 'a' to 0, 'b' to 1, 'c' to 2
        int col = position.charAt(1) - '1'; // Convert '1' to 0, '2' to 1, '3' to 2
        board[row][col] = colour;
    }

    private static boolean isWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' ') ||
                    (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != ' ')) { // Check rows
                                                                                                        // and columns
                return true;
            }
        }
        if ((board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ') ||
                (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' ')) { // Check diagonals
            return true;
        }
        return false;
    }

    private static boolean isDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private static int evaluate(char[][] board) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) { // Check rows
                if (board[i][0] == myColour) {
                    return 10;
                } else if (board[i][0] == opponentColour) {
                    return -10;
                }
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) { // Check columns
                if (board[0][i] == myColour) {
                    return 10;
                } else if (board[0][i] == opponentColour) {
                    return -10;
                }
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) { // Check diagonals
            if (board[0][0] == myColour) {
                return 10;
            } else if (board[0][0] == opponentColour) {
                return -10;
            }
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) { // Check diagonals
            if (board[0][2] == myColour) {
                return 10;
            } else if (board[0][2] == opponentColour) {
                return -10;
            }
        }
        return 0;
    }

    private static int findBestMove(char[][] board) {
        System.out.println("In findBestMove");
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1; // Initialize best move to an invalid position
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println("BOARD CURR: " + board[i][j]);
                if (board[i][j] == ' ') {
                    System.out.println("In findBestMove loop: " + i + " " + j);
                    board[i][j] = myColour;
                    int score = minimax(board, 0, false);
                    board[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = i * 3 + j; // Convert 2D array index to 1D array index
                        System.out.println("Best move is in findBestMove: " + bestMove);
                    }
                }
            }
        }
        return bestMove;
    }

    private static int minimax(char[][] board, int depth, boolean isMaximizing) {
        int utility = evaluate(board);
        System.out.println("Utility: " + utility);
        if (utility == 10 || utility == -10) {
            return utility;
        }

        if (isDraw()) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = myColour;
                    int val = minimax(board, depth + 1, !isMaximizing);
                    bestScore = Math.max(bestScore, val);
                    board[i][j] = ' ';
                }
            }
            return bestScore;
        } else { // Minimizing
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = opponentColour;
                    int val = minimax(board, depth + 1, !isMaximizing);
                    bestScore = Math.min(bestScore, val);
                    board[i][j] = ' ';
                }
            }
            return bestScore;
        }
    }

    public static void main(String[] args) {

        char[][] board = { { 'x', 'o', 'x' },
                { 'o', 'o', 'x' },
                { ' ', ' ', ' ' } };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        int bestMove = findBestMove(board);
        System.out.println("Best move is: " + bestMove);
        System.out.println("Best move is: " + bestMove / 3 + " " + bestMove % 3);
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