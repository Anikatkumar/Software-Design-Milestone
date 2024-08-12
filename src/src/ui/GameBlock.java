package ui;

import java.awt.*;

public class GameBlock {
    private int [][] blockShape;
    private Color blockColor;

    public GameBlock(int [][] blockShape , Color blockColor){
        this.blockShape = blockShape;
        this.blockColor = blockColor;
    }
    public int[][] getBlockShape(){
        return blockShape;
    }
    public Color getBlockColor(){
        return blockColor;
    }
    public void setBlockShape(int [][] blockShape){
        this.blockShape = blockShape;
    }
    public void setBlockColor(Color blockColor){
        this.blockColor = blockColor;
    }
}
