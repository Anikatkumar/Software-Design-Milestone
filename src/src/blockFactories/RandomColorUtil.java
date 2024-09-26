package blockFactories;

import java.awt.*;
import java.util.Random;

public class RandomColorUtil {
    private static Color[] colors = {
            Color.RED,
            Color.CYAN,
            Color.DARK_GRAY,
            Color.GREEN,
            Color.ORANGE,
            Color.PINK,
            Color.YELLOW
    };

    public static Color getRandomBlockColor(){
        Random rColor =  new Random();
        int randomColor = rColor.nextInt(colors.length);
        return colors[randomColor];
    }
}
