import java.awt.Color;//these are the packages which are imported to help set up the render panel
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
@SuppressWarnings("serial")
public class RenderPanel extends JPanel
{
      public static final Color BLACK = new Color(000000);//this sets the color of the window background, I can change it to other decimal values
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);//this paints the window and allows me to paint my snake as well as all of the food
		Snake snake = Snake.snake;
		g.setColor(BLACK); //g is set to the color that black was originally set to 		
		g.fillRect(0, 0, 800, 700);
		g.setColor(Color.WHITE);//this g sets the color of the snake, I could set it to blue, or green or red etc
		for (Point point : snake.snakeParts)
		{
			g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);//this colors in the parts of the snake
		}		
		g.fillRect(snake.head.x * Snake.SCALE, snake.head.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);		
		g.setColor(Color.RED);	//sets the color of the apple, the rest of these do the same thing by setting the color of the food
		g.fillRect(snake.apple.x * Snake.SCALE, snake.apple.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);	
		g.setColor(Color.BLUE);
		g.fillRect(snake.blueberry.x * Snake.SCALE, snake.blueberry.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		g.setColor(Color.GREEN);
		g.fillRect(snake.poisonedApple.x * Snake.SCALE, snake.poisonedApple.y * Snake.SCALE, Snake.SCALE, Snake.SCALE); 
		g.setColor(Color.YELLOW);
		g.fillRect(snake.goldenApple.x * Snake.SCALE, snake.goldenApple.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		String header = "Score: " + snake.score + ", Length: " + snake.tailLength + ", Time: " + snake.time / 20 + ", Apples: " + snake.appleCount + ", Extra Life: " + snake.extraLife;		
		g.setColor(Color.white);//this shows the length, time, number of apples eaten, and the extra life 		
		g.drawString(header, (int) (getWidth() / 2 - header.length() * 2.5f), 10);
		String s = "Game Over! Press SPACE to restart";//this string is drawn when the game ends
		if (snake.over)
		{
			g.drawString(s, (int) (getWidth() / 2 - s.length() * 2.5f), (int) snake.dim.getHeight() / 4);
		}
		String p = "Paused!";
		if (snake.paused && !snake.over)
		{
			g.drawString(p, (int) (getWidth() / 2 - p.length() * 2.5f), (int) snake.dim.getHeight() / 4);
		}
		String h = "HELP MENU";//add later if there is time
	}
}