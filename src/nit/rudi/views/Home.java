package nit.rudi.views;

import nit.rudi.helper.ReadExcel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;

public class Home {
    public JPanel homeView;
    private JButton exportFileButton;
    private JComboBox selectInputFile;
    private JFileChooser fileChooser;


    public Home() {
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        this.selectInputFile.addItem("None");
        this.selectInputFile.addItem("Student List");
        this.selectInputFile.addItem("University List");
        this.selectInputFile.addItem("Third List");
        this.homeView = new JPanel();
        homeView.add(selectInputFile);

        homeView.add(exportFileButton);


        exportFileButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e:ActionEvent
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        selectInputFile.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(homeView);
                File selectedFile;
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    ReadExcel exl = new ReadExcel(selectedFile);
                }
                switch (selectInputFile.getSelectedIndex()){
                    case 1:
                       break;
                    default:
                        break;
                }
            }
        });
    }
}
