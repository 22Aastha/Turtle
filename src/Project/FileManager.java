// File: FileManager.java
package Project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class FileManager {
    public void saveImage(Component comp) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(comp) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                BufferedImage image = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_ARGB);
                comp.paint(image.getGraphics());
                ImageIO.write(image, "PNG", file);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(comp, "Failed to save image");
            }
        }
    }

    public void loadImage(Component comp, boolean warn) {
        if (warn && JOptionPane.showConfirmDialog(comp, "Unsaved changes. Continue?", "Warning", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(comp) == JFileChooser.APPROVE_OPTION) {
            try {
                Image img = ImageIO.read(chooser.getSelectedFile());
                comp.getGraphics().drawImage(img, 0, 0, comp);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(comp, "Failed to load image");
            }
        }
    }
    
    public void setBackgroundImage(TurtleGraphics turtle) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                Image img = ImageIO.read(chooser.getSelectedFile());
                turtle.setBackgroundImage(img);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Failed to set background image");
            }
        }
    }


    public void saveCommands(List<String> history) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }
            
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println("Turtle Graphics Command History");
                for (String cmd : history)
                {
                    writer.println(cmd);
                }
                
                writer.println();
                writer.println("End of command history");
                writer.println("Saved on: " + new java.util.Date());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Failed to save commands: " + ex.getMessage());
            }
        }
    }

    public void loadCommands(CommandManager manager, TurtleGraphics turtle) 
    {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()))) {
                String line;
                while ((line = reader.readLine()) != null) 
                {
                    if (!line.trim().isEmpty() && 
                        !line.startsWith("Turtle Graphics") && 
                        !line.startsWith("===") && 
                        !line.startsWith("End of command") && 
                        !line.startsWith("Saved on:")) {
                        // Add false if you don't want to add to history
                        manager.executeCommand(turtle, line.trim(), true); 
                    }
                }
            } 
            catch (IOException ex) 
            {
                JOptionPane.showMessageDialog(null, "Failed to load commands: " + ex.getMessage());
            }
        }
    }

}

