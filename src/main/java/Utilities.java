package src.main.java;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Utilities {
    public static void append(String line, JTextPane jTextPane) {
        try {
            Document doc = jTextPane.getDocument();
            doc.insertString(doc.getLength(), line, null);
        } catch (BadLocationException exc) {
            exc.printStackTrace();
        }
    }
}
