import javax.swing.*;
import java.awt.*;

public class SnakeGame {
    JFrame frame;

    SnakeGame() {
        frame = new JFrame("Snake Game");
        frame.setBounds(10, 10, 905, 700);
        MyPanel panel = new MyPanel();
        panel.setBackground(Color.gray);
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


    public static void main(String[] args) {
        SnakeGame ob = new SnakeGame();
    }
}
