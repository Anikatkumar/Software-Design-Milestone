package blockFactories;

import java.util.Random;

public class BlockFactoryProducer {
    private static final Random r = new Random();

    public static BlockFactory getRandomBlock() {
        int blockType = r.nextInt(7);
        switch (blockType) {
            case 0:
                return new LBlock();
            case 1:
                return new ZBlock();
            case 2:
                return new TBlock();
            case 3:
                return new BoxBlock();
            case 4:
                return new LineBlock();
            case 5:
                return new ReverseLBlock();
            case 6:
                return new ReverseZBlock();
            default:
                return new TBlock();  // Default to TBlockFactory
        }
    }
}
