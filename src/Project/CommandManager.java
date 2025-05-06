// File: CommandManager.java
package Project;

import java.awt.*;
import java.util.*;


public class CommandManager {
    private java.util.List<String> history = new ArrayList<>();
    private boolean unsavedChanges = false;
    private FileManager fileManager = new FileManager();
    private UIManagerWrapper uiManager;

    
    public CommandManager(UIManagerWrapper uiManager) {
        this.uiManager = uiManager;
    }
    
    public void executeCommand(TurtleGraphics turtle, String command) {
        executeCommand(turtle, command, true);
    }

    public void executeCommand(TurtleGraphics turtle, String command, boolean recordHistory) {
        if (recordHistory) {
            history.add(command);
        }

        System.out.println(">>> " + command); 
        unsavedChanges = true;

        try {
            String[] parts = command.toLowerCase().split("[ ,]+");
            switch (parts[0]) {
                case "about":
                    turtle.about();
                    System.out.println("Created by Aastha - Command Executed Successfully");
                    uiManager.displayMessage("Created by Aastha");
                    break;
                case "penup":
                    turtle.penUp();
                    System.out.println("Pen lifted");
                    uiManager.displayMessage("Pen lifted");
                    break;
                case "pendown":
                    turtle.penDown();
                    System.out.println("Pen lowered");
                    uiManager.displayMessage("Pen lowered");
                    break;
                case "left":
                    turtle.turnLeft(Integer.parseInt(parts[1]));
                    System.out.println("Turned left by " + parts[1] + " degrees");
                    uiManager.displayMessage("Turned left by " + parts[1] + "°");
                    break;
                case "right":
                    turtle.turnRight(Integer.parseInt(parts[1]));
                    System.out.println("Turned right by " + parts[1] + " degrees");
                    uiManager.displayMessage("Turned right by " + parts[1] + "°");
                    break;
                case "move":
                    turtle.forward(Integer.parseInt(parts[1]));
                    System.out.println("Moved forward by " + parts[1] + " units");
                    uiManager.displayMessage("Moved forward " + parts[1] + " units");
                    break;
                case "reverse":
                    turtle.backward(Integer.parseInt(parts[1]));
                    System.out.println("Moved backward by " + parts[1] + " units");
                    uiManager.displayMessage("Moved backward " + parts[1] + " units");
                    break;
                case "black":
                    turtle.penColour(Color.BLACK);
                    System.out.println("Pen color changed to black");
                    uiManager.displayMessage("Pen color: Black");
                    break;
                case "red":
                    turtle.penColour(Color.RED);
                    System.out.println("Pen color changed to red");
                    uiManager.displayMessage("Pen color: Red");
                    break;
                case "green":
                    turtle.penColour(Color.GREEN);
                    System.out.println("Pen color changed to green");
                    uiManager.displayMessage("Pen color: Green");
                    break;
                case "white":
                    turtle.penColour(Color.WHITE);
                    System.out.println("Pen color changed to white");
                    uiManager.displayMessage("Pen color: White");
                    break;
                case "reset":
                    turtle.reset();
                    System.out.println("Turtle reset to default position");
                    uiManager.displayMessage("Turtle reset");
                    break;
                case "clear":
                    turtle.clear();
                    System.out.println("Canvas cleared");
                    uiManager.displayMessage("Canvas cleared");
                    break;
                case "square":
                    int len = Integer.parseInt(parts[1]);
                    drawSquare(turtle, len);
                    System.out.println("Square drawn with side " + len);
                    uiManager.displayMessage("Square drawn (side: " + len + ")");
                    break;
                case "triangle":
                    drawTriangle(turtle, parts);
                    System.out.println("Triangle drawn");
                    uiManager.displayMessage("Triangle drawn");
                    break;
                case "pencolour":
                    int r = Integer.parseInt(parts[1]);
                    int g = Integer.parseInt(parts[2]);
                    int b = Integer.parseInt(parts[3]);
                    turtle.penColour(new Color(r, g, b));
                    System.out.println("Pen color changed to RGB(" + r + "," + g + "," + b + ")");
                    uiManager.displayMessage("Pen color: RGB(" + r + "," + g + "," + b + ")");
                    break;
                case "penwidth":
                    turtle.setStroke(Integer.parseInt(parts[1]));
                    System.out.println("Pen width set to " + parts[1]);
                    uiManager.displayMessage("Pen width: " + parts[1]);
                    break;
                case "undo":
                    undoLastCommand(turtle);
                    uiManager.displayMessage("Last command undone");
                    return;
                case "saveimage":
                    fileManager.saveImage(turtle);
                    System.out.println("Image saved");
                    uiManager.displayMessage("Image saved");
                    break;
                case "loadimage":
                    fileManager.loadImage(turtle, unsavedChanges);
                    System.out.println("Image loaded");
                    uiManager.displayMessage("Image loaded");
                    break;
                case "setbg":
                    fileManager.setBackgroundImage(turtle);
                    System.out.println("Custom background image set");
                    uiManager.displayMessage("Background image set");
                    break;
                case "savecommands":
                    fileManager.saveCommands(history);
                    System.out.println("Commands saved");
                    uiManager.displayMessage("Commands saved");
                    break;
                case "loadcommands":
                    fileManager.loadCommands(this, turtle);
                    System.out.println("Commands loaded");
                    uiManager.displayMessage("Commands loaded");
                    break;
                default:
                    System.out.println("Error: Unknown command - " + parts[0]);
                    uiManager.displayMessage("Unknown command: " + parts[0]);
            }

            System.out.println("Command executed successfully: " + command);
        } catch (Exception e) {
            System.out.println("Error executing command: " + e.getMessage());
            uiManager.displayMessage("Error: " + e.getMessage());
        }

        System.out.println("\nCommand History:");
        history.forEach(cmd -> System.out.println("- " + cmd));
    }

    private void drawSquare(TurtleGraphics turtle, int len) 
    {
        for (int i = 0; i < 4; i++) {
            turtle.forward(len);
            turtle.turnRight(90);
        }
    }

    private void drawTriangle(TurtleGraphics turtle, String[] parts) {
        if (parts.length == 2) {
            int len = Integer.parseInt(parts[1]);
            for (int i = 0; i < 3; i++) {
                turtle.forward(len);
                turtle.turnRight(120);
            }
        } else if (parts.length == 4) {
            int a = Integer.parseInt(parts[1]);
            int b = Integer.parseInt(parts[2]);
            int c = Integer.parseInt(parts[3]);
            turtle.forward(a); turtle.turnRight(120);
            turtle.forward(b); turtle.turnRight(120);
            turtle.forward(c); turtle.turnRight(120);
        }
    }
    
    public void undoLastCommand(TurtleGraphics turtle) {
        if (history.isEmpty()) {
            System.out.println("No commands to undo.");
            return;
        }

        // Remove the last command from history
        String lastCommand = history.remove(history.size() - 1);
        System.out.println("Undoing last command: " + lastCommand);

        // Mark that there are unsaved changes after the undo
        unsavedChanges = true;

        // Reset the turtle to undo the last action (you can also add any specific undo logic if necessary)
        turtle.reset();

        // Only re-execute the most recent valid command (before the last one)
        if (!history.isEmpty()) {
            String previousCommand = history.get(history.size() - 1);
            executeCommand(turtle, previousCommand, false); // Execute the previous command again
        }

        System.out.println("Command history after undo:");
        history.forEach(command -> System.out.println("- " + command));
    }
}