package ui;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {
    private final int noOfRows;
    private final int noOfColumns;
    int xAxis;
    int yAxis;
    int blockXGridInitialPosition = 3;
    int blockYGridInitialPosition = 1;
    private final int blockSize;
    private final int[][] shapeLBlock = {{1,0},{1,0},{1,1}};
    private final Color[][] settledBlocks;

    public GameBoard(int noOfColumns){
        this.noOfColumns = noOfColumns;
        int boardHeight = 450;
        int boardWidth = 400;
        this.setBounds(150,60, boardWidth, boardHeight);
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        blockSize = boardWidth / noOfColumns;
        noOfRows = boardHeight / blockSize;
    }

    public void moveBlockDown(){
        blockYGridInitialPosition++;
        System.out.println("Moving Down Y-Axis: " + blockYGridInitialPosition);
        repaint();
    }
    public void paintBlock(Graphics g){
        System.out.println("\n - Painting Block - ");
        for (int i = 0; i < shapeLBlock.length; i++){
            for (int j = 0; j < shapeLBlock[0].length; j++){
                if(shapeLBlock[i][j] == 1){
                    xAxis = (blockXGridInitialPosition + j) * blockSize;
                    yAxis = (blockYGridInitialPosition + i) * blockSize;
                    g.setColor(Color.green);
                    g.fillRect(xAxis, yAxis, blockSize, blockSize);
                    g.setColor(Color.black);
                    g.drawRect(xAxis,yAxis,blockSize,blockSize);
                }
            }

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        paintBlock(g);
    }

    public void moveBlockLeft(){
        blockXGridInitialPosition--;
        repaint();
    }
    public void moveBlockRight(){
        blockXGridInitialPosition++;
        repaint();
    }

    private void fillInTheLastBlock(Graphics g) {
//        System.out.println("Filling block");
        Color color;
        for (int i = 0; i < noOfRows; i++) {
            for (int j = 0; j < noOfColumns; j++) {
                color = settledBlocks[i][j];
                if (color != null) {
                    int horizontalPosition = j * blockSize;
                    int verticalPosition = i * blockSize;
                    g.setColor(color);
                    g.fillRect(horizontalPosition, verticalPosition, blockSize, blockSize);
                    g.setColor(Color.black);
                    g.drawRect(horizontalPosition, verticalPosition, blockSize, blockSize);

                }
            }
        }
    }


}
