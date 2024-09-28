package blockFactories;

import ui.GameBlock;

public class TBlock implements BlockFactory {
    public GameBlock createBlock() {
        int[][] shape = {{1, 1, 1}, {0, 1, 0}};
        return new GameBlock(shape, 3, RandomColorUtil.getRandomBlockColor());
    }
}
