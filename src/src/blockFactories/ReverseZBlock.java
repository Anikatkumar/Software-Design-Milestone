package blockFactories;

import ui.GameBlock;

public class ReverseZBlock implements BlockFactory{
    public GameBlock createBlock() {
        int[][] shape = {{0, 1, 1}, {1, 1, 0}};
        return new GameBlock(shape, RandomColorUtil.getRandomBlockColor());
    }
}
