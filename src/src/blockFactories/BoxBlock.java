package blockFactories;

import ui.GameBlock;

public class BoxBlock implements BlockFactory{
    public GameBlock createBlock() {
        int[][] shape = {{1, 1}, {1, 1}};
        return new GameBlock(shape, RandomColorUtil.getRandomBlockColor());
    }
}
