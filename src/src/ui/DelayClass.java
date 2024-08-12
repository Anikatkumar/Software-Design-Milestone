package ui;

public class DelayClass extends Thread{
    private GameBoard gameBoard;
    public DelayClass(GameBoard gameBoard){
            this.gameBoard = gameBoard;
    }

    @Override
    public void run() {
        while(true) {
            try {
                gameBoard.moveBlockDown();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
