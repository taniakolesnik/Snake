import javax.swing.*;
import java.awt.*;

public class GameField extends JPanel {

    private final int FIELD_SIZE = 320;
    private final int DOT_SIZE = 16; //20items within 320
    private final int ALL_DOTS = 400; //20x20
    private Image dot, apple;
    private int appleX, appleY;
    private int[] x = new int[ALL_DOTS];//max possible
    private int[] y = new int[ALL_DOTS];//max possible
    private int snakeSize;
    private Timer timer;
    private boolean left = false, right=true, up = false, down = false;
    private boolean isInGame = true;

    public GameField() {
       setBackground(Color.BLACK);
       loadImages();
    }

    public void loadImages(){
        ImageIcon imageIconApple = new ImageIcon("apple.png");
        apple = imageIconApple.getImage();

        ImageIcon imageIconDot = new ImageIcon("dot.png");
        dot = imageIconDot.getImage();
    }
}
