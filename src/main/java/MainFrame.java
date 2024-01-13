package src.main.java;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private JPanel mainPanel;
    private JTabbedPane jTabbedPane;
    private JPanel menuPanel;
    private JMenuBar menu;
    private JMenu file, edit, select, view, appearance;
    private JMenuItem menuItem;
    private List<JTextPane> textAreas;
    private List<JScrollPane> scrolls;
    private List<File> files;
    private int panelCounter = 0; //It tells us how many panels exist
    private boolean existPanel = false; //It tells us if there are panels already created
    JFileChooser jFileChooser;

    public MainFrame() {
        setTitle("MyNotepad");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        jTabbedPane = new JTabbedPane();

        //---------- Menu ----------
        createMenu();

        //---------- Text Area ---------
        files = new ArrayList<File>();
        textAreas = new ArrayList<JTextPane>();
        scrolls = new ArrayList<JScrollPane>();

        mainPanel.add(menuPanel, BorderLayout.NORTH);
        mainPanel.add(jTabbedPane, BorderLayout.CENTER);

        this.add(mainPanel);
    }

    private void createTextAreaTab() {
        JPanel tab = new JPanel();

        files.add(new File(""));
        textAreas.add(new JTextPane());
        scrolls.add(new JScrollPane((Component) textAreas.get(panelCounter)));

        tab.add((Component) scrolls.get(panelCounter));

        jTabbedPane.addTab("New", tab);
        jTabbedPane.setSelectedIndex(panelCounter);

        panelCounter++;
        existPanel = true;
    }

    private void createMenu() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());

        menu = new JMenuBar();

        file = new JMenu("File");
        edit = new JMenu("Edit");
        select = new JMenu("Select");
        view = new JMenu("View");
        appearance = new JMenu("Appearance");

        //File option items
        addMenuItem("New", "File", "New");
        addMenuItem("Open", "File", "Open");
        file.addSeparator();
        addMenuItem("Save", "File", "Save");
        addMenuItem("Save as", "File", "Save as");

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
        jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        switch (menuOption) {
            case "File":
                file.add(menuItem);
                if ("New".equals(action)) {
                    menuItem.addActionListener(e -> {
                        createTextAreaTab();
                    });
                } else if ("Open".equals(action)) {
                    menuItem.addActionListener(e -> {
                        if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                            File file = jFileChooser.getSelectedFile();
                            boolean fileOpen = false;

                            //We check if the selected file is already open and, in that case, we open the tab with the file content
                            for (int i = 0; i < jTabbedPane.getTabCount(); i++) {
                                if (files.get(i).getPath().equals(file.getPath())) {
                                    fileOpen = true;
                                    jTabbedPane.setSelectedIndex(i);
                                    break;
                                }
                            }

                            if (!fileOpen) {
                                createTextAreaTab();

                                files.set(jTabbedPane.getSelectedIndex(), file);

                                try {
                                    FileReader fileReader = new FileReader(file.getPath());
                                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                                    String line = "";

                                    jTabbedPane.setTitleAt(jTabbedPane.getSelectedIndex(), file.getName());

                                    while (line != null) {
                                        line = bufferedReader.readLine();
                                        if (line != null) {
                                            Utilities.append(line.concat("\n"), (JTextPane) textAreas.get(jTabbedPane.getSelectedIndex()));
                                        }
                                    }
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    });
                } else if ("Save".equals(action)) {
                    menuItem.addActionListener(e -> {
                        //If there is no one file open in the system
                        if (jTabbedPane.getTabCount() == 0) {
                            menuItem.setEnabled(false);
                        } else {
                            //The file doesn't exist in the system
                            if (files.get(jTabbedPane.getSelectedIndex()).getPath().isEmpty()) {
                                if (jFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                                    File file = jFileChooser.getSelectedFile();

                                    files.set(jTabbedPane.getSelectedIndex(), file);

                                    jTabbedPane.setTitleAt(jTabbedPane.getSelectedIndex(), file.getName());

                                    try {
                                        FileWriter fileWriter = new FileWriter(files.get(jTabbedPane.getSelectedIndex()).getPath());

                                        String text = textAreas.get(jTabbedPane.getSelectedIndex()).getText();

                                        for (int i = 0; i < text.length(); i++) {
                                            fileWriter.write(text.charAt(i));
                                        }

                                        fileWriter.close();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }

                            } else {
                                //If the file already exists in the system, it must save automatically without asking us
                                try {
                                    FileWriter fileWriter = new FileWriter(files.get(jTabbedPane.getSelectedIndex()).getPath());

                                    String text = textAreas.get(jTabbedPane.getSelectedIndex()).getText();

                                    for (int i = 0; i < text.length(); i++) {
                                        fileWriter.write(text.charAt(i));
                                    }

                                    fileWriter.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    });
                } else if ("Save as".equals(action)) {
                    menuItem.addActionListener(e -> {
                        //If there is no one file open in the system
                        if (jTabbedPane.getTabCount() == 0) {
                            menuItem.setEnabled(false);
                        } else {
                            if (jFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                                File file = jFileChooser.getSelectedFile();

                                files.set(jTabbedPane.getSelectedIndex(), file);

                                jTabbedPane.setTitleAt(jTabbedPane.getSelectedIndex(), file.getName());

                                try {
                                    FileWriter fileWriter = new FileWriter(files.get(jTabbedPane.getSelectedIndex()).getPath());

                                    String text = textAreas.get(jTabbedPane.getSelectedIndex()).getText();

                                    for (int i = 0; i < text.length(); i++) {
                                        fileWriter.write(text.charAt(i));
                                    }

                                    fileWriter.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    });
                }
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
