package AI;

import ui.GameBlock;
import ui.GameBoard;

public class TetrisGameWithAI {
    private final TetrisAI ai = new TetrisAI();
    private GameBoard gameBoard;
    private GameBlock currentBlock;

    public TetrisGameWithAI(GameBoard gameBoard) {
        if (gameBoard == null) {
            throw new IllegalArgumentException("GameBoard cannot be null");  // Handle null input
        }
        this.gameBoard = gameBoard;
        System.out.println("1st");
        this.currentBlock = gameBoard.createNewBlock();

    }
    public boolean isGameOver() {
        return gameBoard.maximumHeightReached();  // Or whatever logic you use to check game over
    }

    // Render the game state
    public void render() {
        System.out.println("RENDER CALL");
        gameBoard.repaint();  // Refresh the game board
    }


    public void dropPiece() {
        if (currentBlock == null) {
            currentBlock = gameBoard.createNewBlock();  // Ensure a new block is created if none exists
        }

        // Let the AI decide the best move for the current block
//        Move bestMove = ai.findBestMove(gameBoard, currentBlock);
        Move bestMove = new Move(4,3);

        // Rotate the piece according to AI's decision
        for (int i = 0; i < bestMove.rotation; i++) {
            currentBlock.rotateBlock();
        }

        // Move the piece to the correct column based on AI
        while (gameBoard.getBlockXGridPosition() < 9) {
            gameBoard.moveBlockRight();
        }
        while (gameBoard.getBlockXGridPosition() > bestMove.column) {
            gameBoard.moveBlockLeft();
        }

        // Drop the block down fast
        while(gameBoard.moveBlockDown()) {
            System.out.println("Move Down!!");

            // Let the block fall down fully
        }

        // After the block has moved, check if it has settled
        if (!gameBoard.moveBlockDown()) {
            // Merge the block into the game board and clear lines if necessary
            gameBoard.mergeBlock(currentBlock.getBlockColor());
            int scoreIncrement = gameBoard.clearOutCompletedLines();

            if (scoreIncrement > 0) {
                System.out.println("Score increased by " + (scoreIncrement * 100));
            }

            // Create a new block after the current one is settled
            if (!isGameOver()) {
                currentBlock = gameBoard.createNewBlock();  // Generate a new block
            } else {
                System.out.println("Game Over");
            }
        }
    }


}
