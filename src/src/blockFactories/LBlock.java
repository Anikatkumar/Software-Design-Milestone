package blockFactories;

import ui.GameBlock;

public class LBlock implements BlockFactory{
    public GameBlock createBlock() {
        int[][] shape = {{1, 0}, {1, 0}, {1, 1}};
        return new GameBlock(shape, RandomColorUtil.getRandomBlockColor());
    }
}
