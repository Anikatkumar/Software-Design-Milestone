package blockFactories;

import ui.GameBlock;

public class ReverseLBlock implements BlockFactory{
    public GameBlock createBlock() {
        int[][] shape = {{0, 1}, {0, 1}, {1, 1}};
        return new GameBlock(shape, RandomColorUtil.getRandomBlockColor());
    }
}
