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
    public Game()
    {    
        super(10, 20, 32, false);
        prepare();
    }
    Block currentBlocks[] = new Block[4];
    int currentShape; // 0=I, 1=J, 2=L, 3=O 4=S, 5=T, 6=Z
    int rotation;
    boolean rotatedLeft = false;
    boolean rotatedRight = false;
    int time = 0;
    int delay = 0;
    int gravity = 48;
    int drop = 2;
    boolean dropped = false;
    boolean downHeld = false;
    int rows[] = new int[getHeight()];
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
        boolean collideRight = collideRight();
        boolean collideLeft = collideLeft();
        boolean collideBottomNoStop = collideBottomNoStop();
        
        time++;
        if (Greenfoot.isKeyDown("down") && !dropped && !downHeld)
        {
            time = gravity;
            dropped = true;
            downHeld = true;
        }
        else if (!Greenfoot.isKeyDown("down"))
        {
            dropped = false;
            downHeld = false;
        }
        
        if (Greenfoot.isKeyDown("left") && delay >= 10 && !collideLeft)
        {
            delay = 0;
            currentBlocks[0].setLocation(currentBlocks[0].getX() - 1, currentBlocks[0].getY());
            currentBlocks[1].setLocation(currentBlocks[1].getX() - 1, currentBlocks[1].getY());
            currentBlocks[2].setLocation(currentBlocks[2].getX() - 1, currentBlocks[2].getY());
            currentBlocks[3].setLocation(currentBlocks[3].getX() - 1, currentBlocks[3].getY());
        }
        else { delay++; }
        if (Greenfoot.isKeyDown("right") && delay >= 10 && !collideRight)
        {
            delay = 0;
            currentBlocks[0].setLocation(currentBlocks[0].getX() + 1, currentBlocks[0].getY());
            currentBlocks[1].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY());
            currentBlocks[2].setLocation(currentBlocks[2].getX() + 1, currentBlocks[2].getY());
            currentBlocks[3].setLocation(currentBlocks[3].getX() + 1, currentBlocks[3].getY());
        }
        else { delay++; }
        
        boolean shapeWillCollide = 
            (currentShape == 0 && rotation == 90 || //I
            (currentShape == 1 || currentShape == 2) && (rotation == 90 || rotation == 270) || //J L
            (currentShape == 4 || currentShape == 6) && (rotation == 90) || //S Z
            currentShape == 5 && (rotation == 90 || rotation == 270) //T
            );
        
        boolean stopRotateLeft = shapeWillCollide && collideLeft || currentShape == 0 && rotation == 90 && currentBlocks[0].getX() + 1 <= 2;
        boolean stopRotateRight = shapeWillCollide && collideRight || currentShape == 0 && rotation == 90 && currentBlocks[0].getX() + 1 >= getWidth();
        
        if (!rotatedLeft && Greenfoot.isKeyDown("x") && !(stopRotateLeft || stopRotateRight) && !collideBottomNoStop)
        {
            rotateLeft();
            rotatedLeft = true;
        }
        else if (!Greenfoot.isKeyDown("x"))
        {
            rotatedLeft = false;
        }
        if (!rotatedRight && Greenfoot.isKeyDown("z") && !(stopRotateRight || stopRotateLeft)&& !collideBottomNoStop)
        {
            rotateRight();
            rotatedRight = true;
        }
        else if (!Greenfoot.isKeyDown("z"))
        {
            rotatedRight = false;
        }
        
        if ((time >= gravity || (dropped && time > drop)) && !collideBottom())
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
        dropped = false;
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
            addObject(currentBlocks[0],4,1);
            currentBlocks[1] = new Block();
            addObject(currentBlocks[1],4,0);
            currentBlocks[2] = new Block();
            addObject(currentBlocks[2],5,0);
            currentBlocks[3] = new Block();
            addObject(currentBlocks[3],6,0);
        }
        else if (currentShape == 3) //O
        {
            currentBlocks[0] = new Block();
            addObject(currentBlocks[0],4,0);
            currentBlocks[1] = new Block();
            addObject(currentBlocks[1],5,0);
            currentBlocks[2] = new Block();
            addObject(currentBlocks[2],4,1);
            currentBlocks[3] = new Block();
            addObject(currentBlocks[3],5,1);
        }
        else if (currentShape == 4) //S
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
        else if (currentShape == 5) //T
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
        else if (currentShape == 6) //Z
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
        for (int row = 0; row < rows.length; row++)
        {
            if (rows[row] == 10)
            {
                for (int x = 0; x < getWidth(); x++)
                {
                    removeObjects(getObjectsAt(x, row, Block.class));
                }
                for (int y = row; y >= 0; y--)
                {
                    for (int x = 0; x < getWidth(); x++)
                    {
                        List blocks = getObjectsAt(x, y, Block.class);
                        for (Actor b : (List<Actor>) blocks)
                        {
                            b.setLocation(x, y + 1);
                        }
                    }
                    if (y != 0) { rows[y] = rows[y - 1]; }
                }
                rows[0] = 0;
            }
        }
    }
    
    public boolean collideBottom()
    {
        if (collideBottomNoStop())
        {
            if (currentBlocks[0].getY() == 0 || currentBlocks[1].getY() == 0 || currentBlocks[2].getY() == 0 || currentBlocks[3].getY() == 0) { Greenfoot.stop(); }
            rows[currentBlocks[0].getY()]++;
            rows[currentBlocks[1].getY()]++;
            rows[currentBlocks[2].getY()]++;
            rows[currentBlocks[3].getY()]++;
            checkRows();
            newBlocks();
            return true;
        }
        return false;
    }
    
    public boolean collideBottomNoStop()
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
                return true;
            }
        }
        return false;
    }
    
    public boolean collideLeft()
    {
        for (Block b : currentBlocks)
        {
            List objs = getObjectsAt(b.getX() - 1, b.getY(), Block.class);
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
        else if (currentShape == 4)
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
        else if (currentShape == 5)
        {
            if (rotation == 0)
            {
                currentBlocks[0].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() - 1);
                currentBlocks[2].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() + 1);
                currentBlocks[3].setLocation(currentBlocks[1].getX() - 1, currentBlocks[1].getY());
                rotation = 90;
            }
            else if (rotation == 90)
            {
                currentBlocks[0].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY());
                currentBlocks[2].setLocation(currentBlocks[1].getX() - 1, currentBlocks[1].getY());
                currentBlocks[3].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() - 1);
                rotation = 180;
            }
            else if (rotation == 180)
            {                
                currentBlocks[0].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() + 1);
                currentBlocks[2].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() - 1);
                currentBlocks[3].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY());
                rotation = 270;
            }
            else if (rotation == 270)
            {                
                currentBlocks[0].setLocation(currentBlocks[1].getX() - 1, currentBlocks[1].getY());
                currentBlocks[2].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY());
                currentBlocks[3].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() + 1);
                rotation = 0;
            }
        }
        else if (currentShape == 6)
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
                currentBlocks[0].setLocation(currentBlocks[2].getX() + 1, currentBlocks[2].getY() - 1);
                currentBlocks[1].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() + 1);
                currentBlocks[3].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() - 1);
                rotation = 270;
            }
            else if (rotation == 90)
            {
                currentBlocks[0].setLocation(currentBlocks[2].getX() + 1, currentBlocks[2].getY() + 1);
                currentBlocks[1].setLocation(currentBlocks[2].getX() - 1, currentBlocks[2].getY());
                currentBlocks[3].setLocation(currentBlocks[2].getX() + 1, currentBlocks[2].getY());
                rotation = 0;
            }
            else if (rotation == 180)
            {                
                currentBlocks[0].setLocation(currentBlocks[2].getX() - 1, currentBlocks[2].getY() + 1);
                currentBlocks[1].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() - 1);
                currentBlocks[3].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() + 1);
                rotation = 90;
            }
            else if (rotation == 270)
            {                
                currentBlocks[0].setLocation(currentBlocks[2].getX() - 1, currentBlocks[2].getY() - 1);
                currentBlocks[1].setLocation(currentBlocks[2].getX() + 1, currentBlocks[2].getY());
                currentBlocks[3].setLocation(currentBlocks[2].getX() - 1, currentBlocks[2].getY());
                rotation = 180;
            }
        }
        else if (currentShape == 2)
        {
            if (rotation == 0)
            {
                currentBlocks[0].setLocation(currentBlocks[2].getX() + 1, currentBlocks[2].getY() + 1);
                currentBlocks[1].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() + 1);
                currentBlocks[3].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() - 1);
                rotation = 270;
            }
            else if (rotation == 90)
            {
                currentBlocks[0].setLocation(currentBlocks[2].getX() - 1, currentBlocks[2].getY() + 1);
                currentBlocks[1].setLocation(currentBlocks[2].getX() - 1, currentBlocks[2].getY());
                currentBlocks[3].setLocation(currentBlocks[2].getX() + 1, currentBlocks[2].getY());
                rotation = 0;
            }
            else if (rotation == 180)
            {                
                currentBlocks[0].setLocation(currentBlocks[2].getX() - 1, currentBlocks[2].getY() - 1);
                currentBlocks[1].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() - 1);
                currentBlocks[3].setLocation(currentBlocks[2].getX(), currentBlocks[2].getY() + 1);
                rotation = 90;
            }
            else if (rotation == 270)
            {                
                currentBlocks[0].setLocation(currentBlocks[2].getX() + 1, currentBlocks[2].getY() - 1);
                currentBlocks[1].setLocation(currentBlocks[2].getX() + 1, currentBlocks[2].getY());
                currentBlocks[3].setLocation(currentBlocks[2].getX() - 1, currentBlocks[2].getY());
                rotation = 180;
            }
        }
        else if (currentShape == 4)
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
        else if (currentShape == 5)
        {
            if (rotation == 0)
            {
                currentBlocks[0].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() + 1);
                currentBlocks[2].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() - 1);
                currentBlocks[3].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY());
                rotation = 270;
            }
            else if (rotation == 90)
            {
                currentBlocks[0].setLocation(currentBlocks[1].getX() - 1, currentBlocks[1].getY());
                currentBlocks[2].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY());
                currentBlocks[3].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() + 1);
                rotation = 0;
            }
            else if (rotation == 180)
            {                
                currentBlocks[0].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() - 1);
                currentBlocks[2].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() + 1);
                currentBlocks[3].setLocation(currentBlocks[1].getX() - 1, currentBlocks[1].getY());
                rotation = 90;
            }
            else if (rotation == 270)
            {                
                currentBlocks[0].setLocation(currentBlocks[1].getX() + 1, currentBlocks[1].getY());
                currentBlocks[2].setLocation(currentBlocks[1].getX() - 1, currentBlocks[1].getY());
                currentBlocks[3].setLocation(currentBlocks[1].getX(), currentBlocks[1].getY() - 1);
                rotation = 180;
            }
        }
        else if (currentShape == 6)
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
}
