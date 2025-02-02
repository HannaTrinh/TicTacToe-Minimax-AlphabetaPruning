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

    private static char[][] board = new char[3][3];
    private static Queue<Node> queue = new LinkedList<>();
    private static char myColour;
    private static char opponentColour;
    private static int utility;

    private static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

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

    private static int findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestMove;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = myColour;
                    int score = minimax(board, 9, false);
                    board[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = i * 3 + j;
                    }
                }
            }
        }
    }

    private static String minimax(char[][] board, int depth, boolean isMaximizing) {
        if (depth == 0) {
            return currentPos;
        }
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < currentPos.length(); i++) {
                if (currentPos.charAt(i) == ' ') {
                    String newPos = currentPos.substring(0, i) + 'X' + currentPos.substring(i + 1);
                    int score = minimax(newPos, depth - 1, false);
                    bestScore = Math.max(bestScore, score);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < currentPos.length(); i++) {
                if (currentPos.charAt(i) == ' ') {
                    String newPos = currentPos.substring(0, i) + 'O' + currentPos.substring(i + 1);
                    int score = minimax(newPos, depth - 1, true);
                    bestScore = Math.min(bestScore, score);
                }
            }
            return bestScore;
        }
        return "";
    }

    public static void main(String[] args) {
        initializeBoard();
        Scanner scanner = new Scanner(System.in);
        myColour = scanner.nextLine().charAt(0);
        opponentColour = (myColour == 'b') ? 'o' : 'b';

        if (myColour == 'b') { // First move
            queue.add(board, 0, null, myColour);

            String bestMove = minimax("         ", 9, true);
            System.out.println(bestMove);
            queue.add(bestMove);
            updateBoard(bestMove, myColour);
            System.out.flush();
        } else { // Wait for opponent to move
            String input = scanner.nextLine().trim();
            queue.add(input);
            updateBoard(input, opponentColour);
            String bestMove = minimax(input, 9, true);
            System.out.println(bestMove);
            queue.add(bestMove);
            updateBoard(bestMove, myColour);
            System.out.flush();
        }

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            String bestMove = minimax(input, 9, true);
            System.out.println(bestMove);
            queue.add(bestMove);
            updateBoard(bestMove, myColour);
            System.out.flush();
        }
        scanner.close();
    }
}