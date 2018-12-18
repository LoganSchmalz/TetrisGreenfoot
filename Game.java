import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Game extends World
{
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public Game()
    {    
        super(10, 20, 32);
        prepare();
    }
    Block currentBlocks[] = new Block[4];
    int currentShape; //0=I, 1=J, 2=L, 3=S, 4=T, 5=Z};
    int time = 0;
    int delay = 0;
    int gravity = 30;
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Greenfoot.setSpeed(50);
        GreenfootImage image = new GreenfootImage(getWidth(), getHeight());
        image.setColor(Color.BLACK);
        image.fillRect(0, 0, getWidth(), getHeight());
        this.setBackground(image);

        currentBlocks[0] = new Block();
        addObject(currentBlocks[0],3,0);
        currentBlocks[1] = new Block();
        addObject(currentBlocks[1],4,0);
        currentBlocks[2] = new Block();
        addObject(currentBlocks[2],5,0);
        currentBlocks[3] = new Block();
        addObject(currentBlocks[3],6,0);
    }

    public void act()
    {
        time++;
        if (Greenfoot.isKeyDown("down"))
        {
            time = gravity;
        }
        if (Greenfoot.isKeyDown("left") && delay >= 10 && !collideLeft())
        {
            delay = 0;
            currentBlocks[0].setLocation(currentBlocks[0].getX() - 1, currentBlocks[0].getY());
            currentBlocks[1].setLocation(currentBlocks[1].getX() - 1, currentBlocks[1].getY());
            currentBlocks[2].setLocation(currentBlocks[2].getX() - 1, currentBlocks[2].getY());
            currentBlocks[3].setLocation(currentBlocks[3].getX() - 1, currentBlocks[3].getY());
        }
        else { delay++; }
        if (Greenfoot.isKeyDown("right") && delay >= 10 && !collideRight())
        {
            delay = 0;
            currentBlocks[0].setLocation(currentBlocks[0].getX() + 1, currentBlocks[0].getY());
            currentBlocks[1].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY());
            currentBlocks[2].setLocation(currentBlocks[2].getX() + 1, currentBlocks[2].getY());
            currentBlocks[3].setLocation(currentBlocks[3].getX() + 1, currentBlocks[3].getY());
        }
        else { delay++; }
        if ((time >= gravity || (Greenfoot.isKeyDown("down") && time > gravity / 2)) && !collideBottom())
        {
            time = 0;
            currentBlocks[0].setLocation(currentBlocks[0].getX(), currentBlocks[0].getY() + 1);
            currentBlocks[1].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() + 1);
            currentBlocks[2].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() + 1);
            currentBlocks[3].setLocation(currentBlocks[3].getX(), currentBlocks[3].getY() + 1);
            /*if (tetromino == 'I') {}
            else if (tetromino == 'J') {}
            else if (tetromino == 'L') {}
            else if (tetromino == 'S') {}
            else if (tetromino == 'T') {}
            else if (tetromino == 'Z') {}*/
        }
    }
    
    public void newBlocks()
    {
        currentShape = Greenfoot.getRandomNumber(6);
        currentBlocks[0] = new Block();
        addObject(currentBlocks[0],3,0);
        currentBlocks[1] = new Block();
        addObject(currentBlocks[1],4,0);
        currentBlocks[2] = new Block();
        addObject(currentBlocks[2],5,0);
        currentBlocks[3] = new Block();
        addObject(currentBlocks[3],6,0);
    }
    
    public boolean collideBottom()
    {
        for (Block b : currentBlocks)
        {
            List objs = getObjectsAt(b.getX(), b.getY() + 1, Block.class);
            objs.remove(currentBlocks[0]);
            objs.remove(currentBlocks[1]);
            objs.remove(currentBlocks[2]);
            objs.remove(currentBlocks[3]);
            if (b.getY() + 1 == getHeight() || !objs.isEmpty())
            {
                newBlocks();
                return true;
            }
        }
        return false;
    }
    
    public boolean collideLeft()
    {
        for (Block b : currentBlocks)
        {
            List objs = getObjectsAt(b.getX(), b.getY(), Block.class);
            objs.remove(currentBlocks[0]);
            objs.remove(currentBlocks[1]);
            objs.remove(currentBlocks[2]);
            objs.remove(currentBlocks[3]);
            if (b.getX() == 0 || !objs.isEmpty())
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean collideRight()
    {
        for (Block b : currentBlocks)
        {
            List objs = getObjectsAt(b.getX() + 1, b.getY(), Block.class);
            objs.remove(currentBlocks[0]);
            objs.remove(currentBlocks[1]);
            objs.remove(currentBlocks[2]);
            objs.remove(currentBlocks[3]);
            if (b.getX() + 1 == getWidth() || !objs.isEmpty())
            {
                return true;
            }
        }
        return false;
    }
}
