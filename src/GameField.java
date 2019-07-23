import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.TimerTask;

public class GameField extends JPanel implements ActionListener {

    private final int FIELD_SIZE = 320;
    private final int DOT_SIZE = 16; //20items within 320
    private final int ALL_DOTS = 400; //20x20
    private Image dot, apple;
    private int appleX, appleY;
    private int[] x = new int[ALL_DOTS];//max possible
    private int[] y = new int[ALL_DOTS];//max possible
    private int snakeSize;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = true;
    private boolean isInGame = true;
    private final int TIME_DELAY = 250;
    private Timer timer;
    private JButton button;

    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        if (isInGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < snakeSize; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
            //System.out.println("x[0] :" + x[0]);
        } else {
            String string = "Game Over";
            g.setColor(Color.WHITE);
            Font font = new Font("Arial", Font.BOLD, 14);
            g.setFont(font);
            g.drawString(string, 125, FIELD_SIZE/2-40);


            if (button==null){
                button = new JButton("Start new game");
            }

            button.setVisible(true);
            button.setBackground(Color.WHITE);
            button.setBounds(50, 150, 100, 30);
            button.setLocation(FIELD_SIZE/2, FIELD_SIZE/2);
            this.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resetGame();
                }
            });

        }
    }

    private void resetGame() {
        left=false;
        up=false;
        down=false;
        right=true;
        isInGame = true;
        button.setVisible(false);
        initGame();
    }


    public void initGame() {

        snakeSize = 6;
        System.out.println("initGame started");
        for (int i = 0; i <= snakeSize; i++) {
            int temp = snakeSize * DOT_SIZE;
            x[i] = temp - i * DOT_SIZE; // x[0] is head
            y[i] = temp;
            System.out.println(i + " is and x: " + x[i] + " and y: " + y[i] );
        }

        if (timer==null){
            timer = new Timer(TIME_DELAY, this);
        }

        timer.start();
        createApple();
    }

    private void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    private void loadImages() {
        ImageIcon imageIconApple = new ImageIcon("apple.png");
        apple = imageIconApple.getImage();

        ImageIcon imageIconDot = new ImageIcon("dot.png");
        dot = imageIconDot.getImage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isInGame) {
            checkApple();
            checkCollision();
            moveSnake();
        }
        repaint();
    }

    private void checkApple() {
        if (x[0] == appleX && y[0] == appleY){
            snakeSize++;
            createApple();
        }
    }

    private void checkCollision() {
         // check if I didn't hit myself - for snakes more than 4 dots
        for (int i = snakeSize; i > 0; i--) {
            if (i>4 && x[0] == x[i] && y[0] == y[i]){
                isInGame=false;
            }
        }

        if (x[0] > FIELD_SIZE) {
            isInGame=false;
        }

        if (x[0] < 0) {
            isInGame=false;
        }

        if (y[0] > FIELD_SIZE) {
            isInGame=false;
        }

        if (y[0] < 0) {
            isInGame=false;
        }


    }

    private void moveSnake(){
        for (int i = snakeSize; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
            System.out.println("moveSnake " + i + " is and x: " + x[i] + " and y: " + y[i] );
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        } if(up){
            y[0] -= DOT_SIZE;
        } if(down){
            y[0] += DOT_SIZE;
        }
    }

    class FieldKeyListener extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();

            if (key==KeyEvent.VK_LEFT && !right){
                left=true;
                up=false;
                down=false;
            }

            if (key==KeyEvent.VK_RIGHT && !left){
                right=true;
                up=false;
                down=false;
            }

            if (key==KeyEvent.VK_UP && !down){
                up=true;
                right=false;
                left=false;
            }

            if (key==KeyEvent.VK_DOWN && !up){
                down=true;
                right=false;
                left=false;
            }
        }
    }
}
