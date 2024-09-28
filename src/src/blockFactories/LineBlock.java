package blockFactories;

import ui.GameBlock;

public class LineBlock implements BlockFactory {
    public GameBlock createBlock() {
        int[][] shape = {{1, 1, 1}};
        return new GameBlock(shape, 3, RandomColorUtil.getRandomBlockColor());
    }
}
