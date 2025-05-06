package Project;

import uk.ac.leedsbeckett.oop.LBUGraphics;
import java.io.Serializable;
import java.awt.Point; 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class TurtleGraphics extends LBUGraphics implements Serializable { 
	private static final long serialVersionUID = 1L;
    private CommandManager commandManager;
    private final Point initialPosition = new Point(0, 0);
    private final int initialDirection = 0; 
    private Point position;
    private Image backgroundImage = null;

       
    public TurtleGraphics(CommandManager commandManager) {
        this.commandManager = commandManager;
        reset();
        System.out.println("Initializing LBUGraphics V6.0");
    }
    
    @Override
    public void processCommand(String command) {
        System.out.println("Processing command: " + command);
        commandManager.executeCommand(this, command);
    }
    
    public Point getPosition() {
        return new Point(position); 
    }
    
    public void setDirection(int newDirection) {
        this.direction = Math.floorMod(newDirection, 360); 
    }

    public void backward(int distance) {
        forward(-distance);
    }
    
    public void penUp() {
        setPenState(false);
    }

    public void penDown() {
        setPenState(true);
    }

    public void turnLeft(int degrees) {
        left(degrees);
    }

    public void turnRight(int degrees) {
        right(degrees);
    }

    public void penColour(Color color) {
        setPenColour(color);
    }

    public void penColour(int r, int g, int b) {
        setPenColour(new Color(r, g, b));
    }
    
    public void home() 
    {
        this.position = new Point(initialPosition);
        this.direction = initialDirection;
    }
    
    @Override
    public void reset() {
        super.reset();  
        this.position = new Point(initialPosition);
        this.direction = initialDirection;
        backgroundImage = null;
        setPenColour(Color.BLUE);
        setStroke(1);
        penDown();
        clear();
        home();  
        repaint();
    }

    
    public void setBackgroundImage(Image img) {
        this.backgroundImage = img;
        repaint(); // Redraw with the background
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

}