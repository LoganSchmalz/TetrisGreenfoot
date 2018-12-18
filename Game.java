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
    int currentShape; // 0=I, 1=J, 2=L, 3=S, 4=T, 5=Z
    int rotation;
    boolean rotated = false;
    int time = 0;
    int delay = 0;
    int gravity = 120;//30;
    int rows[] = new int[getHeight()];
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

        newBlocks();
    }

    public void act()
    {
        time++;
        if (Greenfoot.isKeyDown("down"))
        {
            time = gravity;
        }
        if (!rotated && Greenfoot.isKeyDown("z") && !collideLeft())
        {
            rotateLeft();
            rotated = true;
        }
        else if (!rotated && Greenfoot.isKeyDown("x") && !collideRight())
        {
            rotateRight();
            rotated = true;
        }
        else if (!Greenfoot.isKeyDown("z") && !Greenfoot.isKeyDown("x"))
        {
            rotated = false;
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
        }
    }
    
    public void newBlocks()
    {
        currentShape = Greenfoot.getRandomNumber(6); //0=I, 1=J, 2=L, 3=S, 4=T, 5=Z
        rotation = 0;
        if (currentShape == 0) //I
        {
            currentBlocks[0] = new Block();
            addObject(currentBlocks[0],3,0);
            currentBlocks[1] = new Block();
            addObject(currentBlocks[1],4,0);
            currentBlocks[2] = new Block();
            addObject(currentBlocks[2],5,0);
            currentBlocks[3] = new Block();
            addObject(currentBlocks[3],6,0);
        }
        else if (currentShape == 1) //J
        {
            currentBlocks[0] = new Block();
            addObject(currentBlocks[0],6,1);
            currentBlocks[1] = new Block();
            addObject(currentBlocks[1],4,0);
            currentBlocks[2] = new Block();
            addObject(currentBlocks[2],5,0);
            currentBlocks[3] = new Block();
            addObject(currentBlocks[3],6,0);
        }
        else if (currentShape == 2) //L
        {
            currentBlocks[0] = new Block();
            addObject(currentBlocks[0],4,0);
            currentBlocks[1] = new Block();
            addObject(currentBlocks[1],5,0);
            currentBlocks[2] = new Block();
            addObject(currentBlocks[2],6,0);
            currentBlocks[3] = new Block();
            addObject(currentBlocks[3],4,1);
        }
        else if (currentShape == 3) //S
        {
            currentBlocks[0] = new Block();
            addObject(currentBlocks[0],5,0);
            currentBlocks[1] = new Block();
            addObject(currentBlocks[1],6,0);
            currentBlocks[2] = new Block();
            addObject(currentBlocks[2],4,1);
            currentBlocks[3] = new Block();
            addObject(currentBlocks[3],5,1);
        }
        else if (currentShape == 4) //T
        {
            currentBlocks[0] = new Block();
            addObject(currentBlocks[0],4,0);
            currentBlocks[1] = new Block();
            addObject(currentBlocks[1],5,0);
            currentBlocks[2] = new Block();
            addObject(currentBlocks[2],6,0);
            currentBlocks[3] = new Block();
            addObject(currentBlocks[3],5,1);
        }
        else if (currentShape == 5) //Z
        {
            currentBlocks[0] = new Block();
            addObject(currentBlocks[0],4,0);
            currentBlocks[1] = new Block();
            addObject(currentBlocks[1],5,0);
            currentBlocks[2] = new Block();
            addObject(currentBlocks[2],5,1);
            currentBlocks[3] = new Block();
            addObject(currentBlocks[3],6,1);
        }
    }
    
    public void checkRows()
    {
        for (int row : rows)
        {
            if (row >= 10)
            {
                for (int i = 0; i < getWidth(); i++)
                {
                    List objs = getObjectsAt(i, row, Block.class);
                    for (Object obj : objs)
                    {
                        removeObject((Actor) obj);
                    }
                }
                for (int i = 0; i < row; i++)
                {
                    for (int j = 0; j < getWidth(); j++)
                    {
                        List objs = getObjectsAt(j, i, Block.class);
                        for (Object obj : objs)
                        {
                            Actor act = (Actor) obj;
                            act.setLocation(act.getX(), act.getY() + 1);
                        }
                    }
                }
                row = 0;
            }
        }
    }
    
    public void rotateLeft()
    {
        if (currentShape == 0 )
        {
            if (rotation == 0)
            {
                currentBlocks[0].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() - 2);
                currentBlocks[1].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() - 1);
                currentBlocks[3].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() + 1);
                rotation = 90;
            }
            else if (rotation == 90)
            {
                currentBlocks[0].setLocation(currentBlocks[2].getX() - 2, currentBlocks[2].getY());
                currentBlocks[1].setLocation(currentBlocks[2].getX() - 1, currentBlocks[2].getY());
                currentBlocks[3].setLocation(currentBlocks[2].getX() + 1, currentBlocks[2].getY());
                rotation = 0;
            }
        }
        else if (currentShape == 1)
        {
            if (rotation == 0)
            {
                currentBlocks[0].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() + 1);
                currentBlocks[1].setLocation(currentBlocks[2].getX() - 1, currentBlocks[2].getY() + 1);
                currentBlocks[3].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() - 1);
                rotation = 90;
            }
            else if (rotation == 90)
            {
                currentBlocks[0].setLocation(currentBlocks[2].getX() - 1, currentBlocks[2].getY() - 1);
                currentBlocks[1].setLocation(currentBlocks[2].getX() - 1, currentBlocks[2].getY());
                currentBlocks[3].setLocation(currentBlocks[2].getX() + 1, currentBlocks[2].getY());
                rotation = 180;
            }
            else if (rotation == 180)
            {                
                currentBlocks[0].setLocation(currentBlocks[2].getX() + 1, currentBlocks[2].getY() - 1);
                currentBlocks[1].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() - 1);
                currentBlocks[3].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() + 1);
                rotation = 270;
            }
            else if (rotation == 270)
            {                
                currentBlocks[0].setLocation(currentBlocks[2].getX() + 1, currentBlocks[2].getY() + 1);
                currentBlocks[1].setLocation(currentBlocks[2].getX() + 1, currentBlocks[2].getY());
                currentBlocks[3].setLocation(currentBlocks[2].getX() - 1, currentBlocks[2].getY());
                rotation = 0;
            }
        }
        else if (currentShape == 2)
        {
            if (rotation == 0)
            {
                currentBlocks[0].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() - 1);
                currentBlocks[2].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() + 1);
                currentBlocks[3].setLocation(currentBlocks[1].getX() - 1, currentBlocks[1].getY() - 1);
                rotation = 90;
            }
            else if (rotation == 90)
            {
                currentBlocks[0].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY());
                currentBlocks[2].setLocation(currentBlocks[1].getX() - 1, currentBlocks[1].getY());
                currentBlocks[3].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY() - 1);
                rotation = 180;
            }
            else if (rotation == 180)
            {                
                currentBlocks[0].setLocation(currentBlocks[1].getX() , currentBlocks[1].getY() + 1);
                currentBlocks[2].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() - 1);
                currentBlocks[3].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY() + 1);
                rotation = 270;
            }
            else if (rotation == 270)
            {                
                currentBlocks[0].setLocation(currentBlocks[1].getX() - 1, currentBlocks[1].getY());
                currentBlocks[2].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY());
                currentBlocks[3].setLocation(currentBlocks[1].getX() - 1, currentBlocks[1].getY() + 1);
                rotation = 0;
            }
        }
        else if (currentShape == 3)
        {
            if (rotation == 0)
            {
                currentBlocks[1].setLocation(currentBlocks[0].getX(), currentBlocks[0].getY() - 1);
                currentBlocks[2].setLocation(currentBlocks[0].getX() + 1, currentBlocks[0].getY());
                currentBlocks[3].setLocation(currentBlocks[0].getX() + 1, currentBlocks[0].getY() + 1);
                rotation = 90;
            }
            else if (rotation == 90)
            {
                currentBlocks[1].setLocation(currentBlocks[0].getX() + 1, currentBlocks[0].getY());
                currentBlocks[2].setLocation(currentBlocks[0].getX() - 1, currentBlocks[0].getY() + 1);
                currentBlocks[3].setLocation(currentBlocks[0].getX(), currentBlocks[0].getY() + 1);
                rotation = 0;
            }
        }
        else if (currentShape == 4)
        {
            if (rotation == 0)
            {
                currentBlocks[2].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() - 1);
                rotation = 90;
            }
            else if (rotation == 90)
            {
                currentBlocks[3].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY());
                rotation = 180;
            }
            else if (rotation == 180)
            {                
                currentBlocks[0].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() + 1);
                rotation = 270;
            }
            else if (rotation == 270)
            {                
                currentBlocks[0].setLocation(currentBlocks[1].getX() -1, currentBlocks[1].getY());
                currentBlocks[2].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY());
                currentBlocks[3].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() + 1);
                rotation = 0;
            }
        }
        else if (currentShape == 5)
        {
            if (rotation == 0)
            {
                currentBlocks[0].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() + 1);
                currentBlocks[2].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY());
                currentBlocks[3].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY() - 1);
                rotation = 90;
            }
            else if (rotation == 90)
            {
                currentBlocks[0].setLocation(currentBlocks[1].getX() - 1, currentBlocks[1].getY());
                currentBlocks[2].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() + 1);
                currentBlocks[3].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY() + 1);
                rotation = 0;
            }
        }
    }
    
    public void rotateRight()
    {
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
                rows[currentBlocks[0].getY()]++;
                rows[currentBlocks[1].getY()]++;
                rows[currentBlocks[2].getY()]++;
                rows[currentBlocks[3].getY()]++;
                checkRows();
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
