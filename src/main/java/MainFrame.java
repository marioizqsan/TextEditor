package src.main.java;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel mainPanel;
    private JTabbedPane jTabbedPane;
    private JPanel menuPanel;
    private JMenuBar menu;
    private JMenu file, edit, select, view, appearance;
    private JMenuItem menuItem;

    public MainFrame() {
        setTitle("MyNotepad");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        createMenu();
        createTextAreaTab();

        mainPanel.add(menuPanel, BorderLayout.NORTH);
        mainPanel.add(jTabbedPane, BorderLayout.CENTER);

        this.add(mainPanel);
    }

    private void createTextAreaTab() {
        jTabbedPane = new JTabbedPane();
        JPanel tab = new JPanel();
        JTextPane textArea = new JTextPane();

        tab.add(textArea);

        jTabbedPane.addTab("Tab", tab);
    }

    private void createMenu(){
        menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());

        menu = new JMenuBar();

        file = new JMenu("File");
        edit = new JMenu("Edit");
        select = new JMenu("Select");
        view = new JMenu("View");
        appearance = new JMenu("Appearance");

        //File option items
        addMenuItem("New", "File", "");
        addMenuItem("Open", "File", "");
        file.addSeparator();
        addMenuItem("Save", "File", "");
        addMenuItem("Save as", "File", "");

        //Edit option items
        addMenuItem("Undo", "Edit", "");
        addMenuItem("Redo", "Edit", "");
        edit.addSeparator();
        addMenuItem("Cut", "Edit", "");
        addMenuItem("Copy", "Edit", "");
        addMenuItem("Paste", "Edit", "");

        //Select option items
        addMenuItem("Select all", "Select", "");

        //View option items
        addMenuItem("Numeration", "View", "");
        view.add(appearance);
        addMenuItem("Light", "Appearance", "");
        addMenuItem("Dark", "Appearance", "");

        menu.add(file);
        menu.add(edit);
        menu.add(select);
        menu.add(view);

        menuPanel.add(menu, BorderLayout.CENTER);
    }

    private void addMenuItem(String itemName, String menuOption, String action) {
        menuItem = new JMenuItem(itemName);

        switch (menuOption) {
            case "File":
                file.add(menuItem);
                break;

            case "Edit":
                edit.add(itemName);
                break;

            case "Select":
                select.add(itemName);
                break;

            case "View":
                view.add(itemName);
                break;

            default:
                appearance.add(itemName);
                break;
        }
    }


}
