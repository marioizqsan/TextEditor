package src.main.java;

import javax.swing.*;
import java.awt.*;

public class MyWindow extends JFrame {

    private JPanel mainPanel;
    private JTabbedPane jTabbedPane;

    public MyWindow() {
        setTitle("MyNotepad");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        mainPanel = new JPanel();
        jTabbedPane = new JTabbedPane();

        createTextAreaTab();

        mainPanel.add(jTabbedPane);

        this.add(mainPanel);
    }

    private void createTextAreaTab(){
        JPanel tab = new JPanel();
        JTextPane textArea = new JTextPane();

        tab.add(textArea);

        jTabbedPane.addTab("Tab", tab);
    }
}
