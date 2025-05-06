// File: Main.java
package Project;

import javax.swing.*;

public class Main 
{
    public static void main(String[] args) 
    {
    	SwingUtilities.invokeLater(() -> {
            new UIManagerWrapper();
        });
        
    }
}