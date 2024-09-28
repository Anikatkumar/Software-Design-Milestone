package blockFactories;

import ui.GameBlock;

public class ZBlock implements BlockFactory {
    public GameBlock createBlock() {
        int[][] shape = {{1, 1, 0}, {0, 1, 1}};
        return new GameBlock(shape, 1, RandomColorUtil.getRandomBlockColor());
    }
}
