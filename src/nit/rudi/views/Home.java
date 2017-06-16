package nit.rudi.views;

import nit.rudi.controller.SrankController;
import nit.rudi.controller.SrankConvertor;
import nit.rudi.controller.SrankCoreProcess;
import nit.rudi.helper.ReadExcel;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.Iterator;

public class Home {
    public JPanel homeView;
    private JButton exportFileButton;
    private JComboBox selectInputFile;
    private JButton clearAll;
    private JLabel resultLabel;
    private JFileChooser fileChooser;
    private final SrankConvertor sc;
    private boolean flags[] = {false,false,false,false};
    SrankController srankControl;

    public SrankConvertor getSc() {
        return sc;
    }

    public Home() {
        srankControl = new SrankController();
        this.sc = new SrankConvertor();
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        this.selectInputFile.addItem("هیچکدام");
        this.selectInputFile.addItem("لیست دانشجویان(درنهایت)");
        this.selectInputFile.addItem("لیست دانشگاه ها");
        this.selectInputFile.addItem("لیست رشته ها");
        this.selectInputFile.addItem("لیست گرایش ها(اختیاری)");
        this.homeView = new JPanel();
        homeView.add(selectInputFile);
        homeView.add(exportFileButton);
        homeView.add(clearAll);
        homeView.add(resultLabel);

        exportFileButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e:ActionEvent
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!areAllTrue()){
                    JOptionPane.showMessageDialog(null,
                            "تمام فایل ها بارگذاری نشده است. لطفا بررسی فرمایید.",
                            "پیام",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String os = System.getProperty("os.name");
                boolean answer = os.contains("Windows");
                if (os.contains("Mac"))
                    try {
                        SrankCoreProcess scp =
                                new SrankCoreProcess("Core/srank -i Core/input.txt -o Core/output.txt");
                    } catch (Exception ex){
                        System.out.print(ex.getMessage());
                    }
                fileChooser.setApproveButtonText("ذخیره");
                fileChooser.setDialogTitle("مقصد ذخیره سازی را انتخاب فرمایید");
                int result = fileChooser.showSaveDialog(homeView);
                File file;

                if (result == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
//                    filename.setText(c.getSelectedFile().getName());
//                    dir.setText(c.getCurrentDirectory().toString());
                    if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xlsx")) {
                        // filename is OK as-is
                    } else {
                        file = new File(file.toString() + ".xslx");  // append .xlsx if "foo.jpg.xlsx" is OK
                        file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName())+".xlsx"); // ALTERNATIVELY: remove the extension (if any) and replace it with ".xlsx"
                    }
                    srankControl.setModel("SrankResult");
                    srankControl.setPathToSave(file.getAbsolutePath());
                    srankControl.Convert();

                }
                if (result == JFileChooser.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(null,
                            "عملیات لغو گردید",
                            "پیام",
                            JOptionPane.ERROR_MESSAGE);
//                    filename.setText("You pressed cancel");
//                    dir.setText("");
                }
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
                     ReadExcel exl = new ReadExcel(selectedFile);
                     final Iterator<Row> rows = exl.getIterator();
                     srankControl.setIterator(rows);
                }else{
                    JOptionPane.showMessageDialog(null,
                            "از بارگیری فایل صرف نظر کرده اید.",
                            "پیام",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                flags[3] = true;//todo:temp
                switch (selectInputFile.getSelectedIndex()){
                    case 1:
                        flags[0] = true;
                        srankControl.setModel("Student");
                       break;
                    case 2:
                        flags[1] = true;
                        srankControl.setModel("University");
                        break;
                    case 3:
                        flags[2] = true;
                        srankControl.setModel("Major");
                        break;
                    case 4:
                        flags[3] = true;
                        srankControl.setModel("Faculty");
                        break;
                    default:
                        break;
                }
                srankControl.Convert();
            }
        });
        clearAll.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */

            @Override
            public void actionPerformed(ActionEvent e) {
                SrankConvertor sc = getSc();
                sc.clearFile();
                for(int i=0;i<flags.length;i++){
                    flags[i] = false;
                }
                JOptionPane.showMessageDialog(null,
                        "فایل ها بارگذاری شده پاک گشت.",
                        "پیام",
                        JOptionPane.QUESTION_MESSAGE);
            }
        });
    }

    public boolean areAllTrue()
    {
        for(boolean b : this.flags) if(!b) return false;
        return true;
    }
}
