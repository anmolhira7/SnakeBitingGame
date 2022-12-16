import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

//action listener for listening to events
public class MyPanel extends JPanel implements KeyListener, ActionListener {
    //used for image
    //get the path for that image
    ImageIcon snakeTitle = new ImageIcon(getClass().getResource("media/snaketitle.jpg"));
    ImageIcon rightMouth = new ImageIcon(getClass().getResource("media/rightmouth.png"));
    ImageIcon snakeImage = new ImageIcon(getClass().getResource("media/snakeimage.png"));
    //icons for up, down , left ,right mouth for moving snake
    ImageIcon up = new ImageIcon(getClass().getResource("media/upmouth.png"));
    ImageIcon down = new ImageIcon(getClass().getResource("media/downmouth.png"));
    ImageIcon right = new ImageIcon(getClass().getResource("media/rightmouth.png"));
    ImageIcon left = new ImageIcon(getClass().getResource("media/leftmouth.png"));
    ImageIcon food = new ImageIcon(getClass().getResource("media/enemy.png"));

    boolean isUp = false;
    boolean isDown = false;
    boolean isRight = true;
    boolean isLeft = false;
    int score = 0;

    //snake food
    int[] xpos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850};
    int[] ypos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};

    //placing food at random cordinates
    Random random = new Random();
    //1.inital food pos
    int foodx = 150;
    int foody = 150;

    //x cordintes of snake
    int[] snakeX = new int[750];
    //y cordinates of snake
    int[] snakeY = new int[750];
    //initial move
    int move = 0;
    //initial len 3
    int lengthOfSnake = 3;
    Timer time;
    //status of game whether over or not
    boolean GameOver = false;

    MyPanel() {
        //allow to control game through keyboard
        //so we can control snake through key press
        //also override 3 methods of addKeyListener
        addKeyListener(this);
        setFocusable(true);
        //delay of 150ms
        time = new Timer(150, this);
        time.start();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 851, 55);
        g.drawRect(24, 74, 851, 576);
        //draw image on panel with cordinates
        //this is current reference, g on which panel
        //25 islie as 1 pixel is for border
        snakeTitle.paintIcon(this, g, 25, 11);
        //setting color for the rect note by just setColor it does not set the color
        //you need to use fillRect and specify the cordinates
        g.setColor(Color.BLACK);
        //x - 25 we leave 1 pixel for border
        g.fillRect(25, 74, 850, 575);

        //printing starts here
        if (move == 0) {
            //note 100 75 50 there is a gap of 25 pixel between each image
            //pos of head of snake
            snakeX[0] = 100;
            //distance behind head part ie body of snake
            snakeX[1] = 75;
            //subsequent body part
            snakeX[2] = 50;

            //position of head
            //snake will start from x=100 and y = 100 on 2d plane
            snakeY[0] = 100;
            snakeY[1] = 100;
            snakeY[2] = 100;

            //note y se 100px rhega for 3 images but for x it will be 100 ,75,50 resp
        }

        //print head
        //here x,y point towards pos of head
        if (isRight) {
            rightMouth.paintIcon(this, g, snakeX[0], snakeY[0]);
        }
        if (isDown) {
            down.paintIcon(this, g, snakeX[0], snakeY[0]);
        }
        if (isLeft) {
            left.paintIcon(this, g, snakeX[0], snakeY[0]);
        }
        if (isUp) {
            up.paintIcon(this, g, snakeX[0], snakeY[0]);
        }
        //for printing body parts
        //i=1 as 0 is used as head so we need body
        for (int i = 1; i < lengthOfSnake; i++) {
            //body part
            snakeImage.paintIcon(this, g, snakeX[i], snakeY[i]);
        }
        food.paintIcon(this, g, foodx, foody);

        //is game over is true
        if (GameOver) {
            //set color of screen
            g.setColor(Color.WHITE);
            //set color of string style and size
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
            //draw string with location
            g.drawString("Game Over", 300, 300);
            //new font for restarting the game
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
            g.drawString("Press the Space Key to restart", 330, 360);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("ITALIC", Font.PLAIN, 15));
        g.drawString("Score " + score, 750, 30);
        g.drawString("Length :" + lengthOfSnake, 750, 50);
        g.dispose();
    }

    //addKeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //for restart event
        if (e.getKeyCode() == KeyEvent.VK_SPACE && GameOver) {
            restart();
        }
        //how to move the snake
        //clicked right arrow key
        //&& !isLeft so that if we want to go opp we can not move in same axis
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && (!isLeft)) {
            isUp = false;
            isLeft = false;
            isDown = false;
            isRight = true;
            move++;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && (!isRight)) {
            isUp = false;
            isLeft = true;
            isDown = false;
            isRight = false;
            move++;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && !isDown) {
            isUp = true;
            isLeft = false;
            isDown = false;
            isRight = false;
            move++;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && !isUp) {
            isUp = false;
            isLeft = false;
            isDown = true;
            isRight = false;
            move++;
        }
        //as snake should move on increment move repaint it
    }

    //restarts the game
    private void restart() {
        //to restart reset the params of game
        GameOver = false;
        move = 0;
        score = 0;
        lengthOfSnake = 3;
        isLeft = false;
        isRight = true;
        isUp = false;
        isDown = false;
        time.start();
        newFood();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = lengthOfSnake - 1; i > 0; i--) {
            //prev loc is copied to new loc
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        if (isLeft) {
            //sub 25 pixel from cur location
            //here x changes y remains same
            snakeX[0] = snakeX[0] - 25;
        }
        if (isRight) {
            snakeX[0] = snakeX[0] + 25;
        }
        if (isUp) {
            //distance from top decreases
            snakeY[0] = snakeY[0] - 25;
        }
        if (isDown) {
            snakeY[0] = snakeY[0] + 25;
        }

        //if you are going outside the bounday we need to get back from opp direction
        if (snakeX[0] > 850) snakeX[0] = 25;
        if (snakeX[0] < 25) snakeX[0] = 850;
        //agar snake 625 se par ho jaye
        //so reset it to 75
        if (snakeY[0] > 625) snakeY[0] = 75;
        if (snakeY[0] < 75) snakeY[0] = 625;

        //Collision with food
        CollisionWithFood();
        //if snake eat itself
        CollisionWithBody();
        repaint();
    }

    //when snake eat food
    private void CollisionWithFood() {
        if (snakeX[0] == foodx && snakeY[0] == foody) {
            //snaked collided with food
            //3 ->2
            newFood();
            lengthOfSnake++;
            score++;
            //assigning prev length
            snakeX[lengthOfSnake - 1] = snakeX[lengthOfSnake - 2];
            snakeY[lengthOfSnake - 1] = snakeY[lengthOfSnake - 2];
        }

    }

    //when snake try to eat itself
    private void CollisionWithBody() {
        for (int i = lengthOfSnake - 1; i > 0; i--) {
            //if snake is eating itself
            if (snakeX[i] == snakeX[0] && snakeY[i] == snakeY[0]) {
                //we want that game should stop
                time.stop();
                GameOver = true;
            }
        }
    }

    private void newFood() {
        //gen food at random pos
        foodx = xpos[random.nextInt(xpos.length - 1)];
        foody = ypos[random.nextInt(ypos.length - 1)];

        for (int i = lengthOfSnake - 1; i >= 0; i--) {
            //if food is printed over snake body
            if (snakeX[i] == foodx && snakeY[i] == foody) {
                //is this happends recall newFood operation
                newFood();
            }
        }
    }


}
