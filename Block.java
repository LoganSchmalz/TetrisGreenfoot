import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Block here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Block extends Actor
{
    public Block()
    {
        GreenfootImage image = new GreenfootImage(32, 32);
        image.setColor(Color.WHITE);
        image.fillRect(0, 0, 31, 31);
        this.setImage(image);
    }
    /**
     * Act - do whatever the Block wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
}
