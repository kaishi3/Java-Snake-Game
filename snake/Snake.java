import java.awt.Dimension;//these are the packages that are needed to make the main snake class
import java.awt.Point;//the point class is especially important for creating the snake as well as adding on parts to the snake when it eats food
import java.awt.Toolkit;
import java.awt.event.ActionEvent;//the actionevent and action listener as well as key event and key listener class allow the snake to move given user input
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener, KeyListener
{    
    public static Snake snake;
    public JFrame jframe;
    public RenderPanel renderPanel;
    public Timer timer = new Timer(20, this); //this implements the timer class which allows me to keep track of the time in the game
    public ArrayList<Point> snakeParts = new ArrayList<Point>();
    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
    public int ticks = 0, direction = DOWN, score, tailLength = 10, time, appleCount;
    public Point head, apple, blueberry, poisonedApple, goldenApple;
    public Random random;
    public boolean over = false, paused;
    public boolean extraLife = false; 
    public Dimension dim;//these are all the variables needed to code the main snake class

    public Snake()
    {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        jframe = new JFrame("Snake");
        jframe.setVisible(true);
        jframe.setSize(805, 700);
        jframe.setResizable(false);//this means the window that is created isn't able to be resized
        jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
        jframe.add(renderPanel = new RenderPanel()); //this adds the renderpanel item to the jframe
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//this allows the window to be closed when the x button is pressed
        jframe.addKeyListener(this);
        startGame();//these set up the window for snake and starts the game 
    }
    public void startGame()
    {
        over = false;
        paused = false;
        extraLife = false; 
        time = 0;
        score = 0;
        tailLength = 1;
        ticks = 0;
        appleCount = 0;
        direction = DOWN;
        head = new Point(0, -1);
        random = new Random();
        snakeParts.clear();
        apple = new Point(random.nextInt(79), random.nextInt(66));
        blueberry = new Point(10000,10000);
        poisonedApple = new Point(100000,100000);
        goldenApple = new Point(10000,10000);
        timer.start();//these create food and set them off the screen so that the snake doesnt encounter them before certain conditions are met
    }    
    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        renderPanel.repaint();
        ticks++;        
        if (ticks % 2 == 0 && head != null && !over && !paused)
        {
            time++;//this checks that as long as the game isn't over or paused the time will keep increasing

            snakeParts.add(new Point(head.x, head.y));

            if (direction == UP)//these are the conditions which occur given the direction that's inputted by the user
            {
                if (head.y - 1 >= 0 && noTailAt(head.x, head.y - 1))
                {
                    head = new Point(head.x, head.y - 1);
                }
                if (((head.y - 1 >= 0 && noTailAt(head.x, head.y - 1)) == false) && (extraLife == false))
                {
                    over = true;
                }  
                if (((head.y - 1 >= 0 && noTailAt(head.x, head.y - 1)) == false) && (extraLife == true))
                {
                    extraLife = false;  
                    over = false; 
                    head = new Point(head.x, head.y - 1);
                }
            }
            if (direction == DOWN)
            {
                if (head.y + 1 < 68 && noTailAt(head.x, head.y + 1))
                {
                    head = new Point(head.x, head.y + 1);
                }
                if(((head.y + 1 < 68 && noTailAt(head.x, head.y + 1)) == false) && (extraLife == false))
                {
                    over = true;
                }  
                if(((head.y + 1 < 68 && noTailAt(head.x, head.y + 1)) == false) && (extraLife == true))
                {
                    extraLife = false; 
                    over = false; 
                    head = new Point(head.x, head.y + 1);
                }           
            }
            if (direction == LEFT)
            {
                if (head.x - 1 >= 0 && noTailAt(head.x - 1, head.y))
                {
                    head = new Point(head.x - 1, head.y);
                }
                if (((head.x - 1 >= 0 && noTailAt(head.x - 1, head.y)) == false) && (extraLife == false))
                {
                    over = true;
                }  
                if (((head.x - 1 >= 0 && noTailAt(head.x - 1, head.y)) == false) && (extraLife == true))
                {
                    extraLife = false;
                    over = false; 
                    head = new Point(head.x - 1, head.y);
                } 
            }
            if (direction == RIGHT)
            {
                if (head.x + 1 < 81 && noTailAt(head.x + 1, head.y))
                {
                    head = new Point(head.x + 1, head.y);
                }                       
                if (((head.x + 1 < 81 && noTailAt(head.x + 1, head.y)) == false) && (extraLife == false))
                {
                    over = true;
                }  
                if (((head.x + 1 < 81 && noTailAt(head.x + 1, head.y)) == false) && (extraLife == true))
                {
                    extraLife = false;
                    over = false;
                    head = new Point(head.x + 1, head.y);
                }
            }
            if (snakeParts.size() > tailLength)
            {
                snakeParts.remove(0);
            }
            if (apple != null)
            {
                if (head.equals(apple)) //this means that if the head's position is at the same position of the cherry on the jframe, 
                //then the tail length will increase as well as the score, the apple is then able to randomize its location again
                  {
                score += 10;
                tailLength += 5;
                generateFood(1);
                appleCount++;//when there is an apple count of 5, a blue berry will appear, when the blueberry is eaten the snake
                //receives an extra life so that if he bites his tail or runs into the wall, the player is able to restart the game
                //with their current snake length and points. The blueberry stays for 7 seconds before disappearing. 
                if (((appleCount / 5 >= 1) && (appleCount % 5 == 0)) && (blueberry != null))
                    {
                       generateFood(2);//this is for generating blueberries
                    }
                if (((appleCount / 7 >= 1) && (appleCount % 7 == 0)) && (poisonedApple != null))
                    {
                        generateFood(3);//this is for generating poisonedApples,
                    }//the integer inside of the generateFood() method determines which food to generate
                if(((appleCount / appleCount) * Math.random()) >= .9)
                   {
                    generateFood(4);//this is for generating the golden apples
                   }
                }             
                if (head.equals(blueberry))
                      {                         
                           score += 50;
                          tailLength += 20;        
                           blueberry.setLocation(10000,10000);
                      }
                if ((head.equals(poisonedApple)) && (extraLife == true))
                {
                    extraLife = false; 
                    poisonedApple.setLocation(10000,10000);
                }
                if ((head.equals(poisonedApple)) && (extraLife == false))
                {
                    over = true;
                }
                if (head.equals(goldenApple) && (goldenApple !=null))
                {
                    //insert extra life code 
                    score += 500;
                    tailLength += 100; 
                    goldenApple.setLocation(10000,10000);
                    extraLife = true; 
                }
                    }                            
              }
        }   
    public boolean noTailAt(int x, int y)
    {
        for (Point point : snakeParts)
        {
            if (point.equals(new Point(x, y)))
            {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args)
    {
        snake = new Snake();
    }
    @Override
    public void keyPressed(KeyEvent e)
    {
        int i = e.getKeyCode();
        if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && direction != RIGHT) 
        //this sets the direction that the snake is moving, given that the snake isn't moving right (the opposite direction)
        {
            direction = LEFT;
        }
        if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && direction != LEFT)
        {
            direction = RIGHT;
        }
        if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && direction != DOWN)
        {
            direction = UP;
        }
        if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && direction != UP)
        {
            direction = DOWN;
        }
        if (i == KeyEvent.VK_SPACE)
        {
            if (over)
            //if the game is over pressing space restarts the game
            {
                startGame();
            }
            else
            //if the game isn't over yet, hitting space pauses the game
            {
                paused = !paused;
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e)
    {
    }
    @Override
    public void keyTyped(KeyEvent e)
    {
    }
    private void generateFood(int x)
    {
        int x1 = random.nextInt(79);
        int y1 = random.nextInt(66);
        if((x == 1) && (noTailAt(x1,y1)))
                {
                apple.setLocation(x1, y1);
                }
        if((x == 2) && (noTailAt(x1,y1)))
        {
            blueberry.setLocation(x1,y1);
        }
         if((x == 3) && (noTailAt(x1,y1)))
        {
            poisonedApple.setLocation(x1,y1);
        }
         if((x == 4) && (noTailAt(x1,y1)))
        {
            goldenApple.setLocation(x1,y1);
        }
        if((noTailAt(x1,y1)) == false)
                {
                     generateFood(x);
                }
    }
}
