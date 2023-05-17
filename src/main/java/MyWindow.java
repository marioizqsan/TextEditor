package src.main.java;

import javax.swing.*;
import java.awt.*;

public class MyWindow extends JFrame {

    public MyWindow(){
        setTitle("MyNotepad");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(600,600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
