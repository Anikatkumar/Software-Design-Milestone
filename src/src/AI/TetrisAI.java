package AI;

import AI.BoardEvaluator;
import AI.Move;
import ui.GameBlock;
import ui.GameBoard;
import java.util.List;  // Correct List interface
import java.util.ArrayList;  // If you need to create a List instance
import java.awt.Point;

import java.awt.*;

public class TetrisAI {

    private BoardEvaluator evaluator = new BoardEvaluator();
    private GameBlock gameBlock;


    public Move findBestMove(GameBoard board, GameBlock block) {
        System.out.println("Finding best move: \n");
        int[][] boardState = board.getBinaryBoard();
        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        // Iterate over possible moves
        for (int rotation = 0; rotation < 4; rotation++) {
//            for (int col = 0; col < 4; col++) {
//                // Check if the current column + block width is within the bounds
//                if (col + block.getBlockShape().length <= board.getNoOfColumns()) {
//                    int[][] simulatedBoard = simulateDrop(boardState, block.getBlockShape(), col);
//                    int score = evaluator.evaluateBoard(simulatedBoard);
//
//                    if (score > bestScore) {
//                        bestScore = score;
//                        bestMove = new Move(rotation, col);
//                    }
//                }
//            }
            bestMove = new Move(6, 4);
            System.out.println("Finding best move and rotating: \n");
            block.rotateBlock();  // Rotate block for next iteration
        }

        return bestMove;
    }


    private int[][] simulateDrop(int[][] board, int[][] piece, int col) {
        int[][] boardCopy = copyBoard(board);

        // Convert the piece (int[][]) to a list of points
        List<Point> shapePoints = convertToPoints(piece);

        int dropRow = getDropRow(boardCopy, shapePoints, col);

        // Check if the calculated dropRow is within the valid range
        if (dropRow < 0 || col < 0 || col + piece[0].length > board[0].length) {
            System.out.println("Invalid move attempted by AI.");
            return boardCopy;  // Return the board unchanged in case of invalid move
        }

        placePiece(boardCopy, piece, dropRow, col);
        return boardCopy;
    }

    // Convert int[][] piece into a List<Point> representing its shape
    private List<Point> convertToPoints(int[][] piece) {
        List<Point> points = new ArrayList<>();
        for (int y = 0; y < piece.length; y++) {
            for (int x = 0; x < piece[y].length; x++) {
                if (piece[y][x] == 1) {  // If it's part of the block
                    points.add(new Point(x, y));  // Add the (x, y) coordinates to the list
                }
            }
        }
        return points;
    }


    private int getDropRow(int[][] board, List<Point> shapePoints, int col) {
   // Find the row where the piece would land if dropped in the given column
        int row = 0;
        while (canPlacePiece(board, shapePoints, col, row)) {
            row++;
        }
        return row - 1; // Return the last valid row
    }
    private boolean canPlacePiece(int[][] board, List<Point> shapePoints, int col, int row) {
        for (Point p : shapePoints) {
            int x = col + p.x;
            int y = row + p.y;

            if (x < 0 || x >= board[0].length || y < 0 || y >= board.length || board[y][x] != 0) {
                return false;
            }
        }
        return true;
    }

    private void placePiece(int[][] board, int[][] piece, int row, int col) {
        int pieceHeight = piece.length;
        int pieceWidth = piece[0].length;

        for (int i = 0; i < pieceHeight; i++) {
            for (int j = 0; j < pieceWidth; j++) {
                if (piece[i][j] != 0) {
                    // Ensure we are not placing the piece outside of the board boundaries
                    if (row + i >= 0 && row + i < board.length && col + j >= 0 && col + j < board[0].length) {
                        board[row + i][col + j] = piece[i][j];
                    } else {
                        System.out.println("Attempting to place piece outside of bounds.");
                    }
                }
            }
        }
    }
    private int[][] copyBoard(int[][] board) {
        int[][] newBoard = new int[board.length][board[0].length];
        for (int y = 0; y < board.length; y++) {
            System.arraycopy(board[y], 0, newBoard[y], 0,
                    board[0].length);
        }
        return newBoard;
    }
}
