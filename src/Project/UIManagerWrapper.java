package Project;

//File: UIManagerWrapper.java
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class UIManagerWrapper extends JFrame implements Serializable { 
	private static final long serialVersionUID = 1L;
    private JTextField commandField;
    private TurtleGraphics turtle;
    private CommandManager commandManager;
    private JLabel messageLabel;

    public UIManagerWrapper(JPanel panel) {
        messageLabel = new JLabel("Ready");  // Ensure messageLabel is initialized
        if (panel != null) {
            panel.add(messageLabel);  // Add to the passed panel if it's not null
        }
    }
    
    public UIManagerWrapper() {
        super("Turtle Graphics");
        setLayout(new BorderLayout());

        commandField = new JTextField();
        messageLabel = new JLabel("Welcome to Turtle Graphics");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        commandField = new JTextField();
        commandManager = new CommandManager(this);
        turtle = new TurtleGraphics(commandManager);

        commandField.addActionListener(_ -> handleCommand());

        JPanel inputPanel = new JPanel(new BorderLayout());
        add(turtle, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(messageLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        setupMenu();

        setSize(815, 461);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
    
    public void displayMessage(String message) {
        messageLabel.setText(message);
    }


    private void handleCommand() {
        String command = commandField.getText().trim();
        commandField.setText("");
        if (!command.isEmpty()) {
            turtle.processCommand(command);
        }
    }
    
    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        
        JMenuItem setBgImage = new JMenuItem("Set Background Image");
        setBgImage.addActionListener(_ -> turtle.processCommand("setbg"));
        fileMenu.add(setBgImage);
        
        JMenuItem undoCommand = new JMenuItem("Undo Last Command");
        undoCommand.addActionListener(_ -> turtle.processCommand("undo"));
        fileMenu.add(undoCommand);


        JMenuItem saveImage = new JMenuItem("Save Image");
        saveImage.addActionListener(_ -> turtle.processCommand("saveimage"));
        fileMenu.add(saveImage);

        JMenuItem loadImage = new JMenuItem("Load Image");
        loadImage.addActionListener(_ -> turtle.processCommand("loadimage"));
        fileMenu.add(loadImage);

        JMenuItem saveCommands = new JMenuItem("Save Commands");
        saveCommands.addActionListener(_ -> turtle.processCommand("savecommands"));
        fileMenu.add(saveCommands);

        JMenuItem loadCommands = new JMenuItem("Load Commands");
        loadCommands.addActionListener(_ -> turtle.processCommand("loadcommands"));
        fileMenu.add(loadCommands);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
}
