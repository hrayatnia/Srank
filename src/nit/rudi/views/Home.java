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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

public class Home {
    /**
     * Home class
     * @since 0.0.1
     * @author Amirhesam Rayatnia rayatnia@stu.nit.ac.ir
     * <p style="color:gray">this class used for creating Swing UI that comminucate with srank core
     * and show warn and error message.
     * in addtion it use to multiplex input file to Data model
     * </p>
     * */
    public JPanel homeView;
    private JButton exportFileButton;
    private JComboBox selectInputFile;
    private JButton clearAll;
    private JLabel resultLabel;
    private JFileChooser fileChooser;
    private final SrankConvertor sc;
    private boolean flags[] = {false,false,false,false};
    private HashMap<Integer,String> methods;
    private HashMap<String,String[]> headers;
    SrankController srankControl;

    public SrankConvertor getSc() {
        return sc;
    }

    public Home() {

        this.methods = new HashMap<>();
        this.headers = new HashMap<>();
        this.headers.put("Student",new String[]{
                "شماره پرونده داوطلب",
                "گرايش (هاي) انتخابي",
                "نام خانوادگي","نام",
                "دانشگاه محل اخذ مدرك كارشناسي",
                "رشته تحصيلي كارشناسي",
                "معدل تا پايان نيمسال ششم"});
        this.headers.put("University",new String[]{"id","name","grade"});
        this.headers.put("Major",new String[]{"name","capacity","requirement"});
        this.headers.put("Faculty",new String[]{""});
        this.methods.put(1,"Student");
        this.methods.put(2,"University");
        this.methods.put(3,"Major");
        this.methods.put(4,"Faculty");

        srankControl = new SrankController();//create srank Controller object controll and convert
        this.sc = new SrankConvertor();//srank Convertor Object that convert srank output
        fileChooser = new JFileChooser();//select input and output file
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));//set user dir as base dir
        //todo: create ArrayList for adding input file
        // and fix hardcode problem
        this.selectInputFile.addItem("هیچکدام");
        this.selectInputFile.addItem("لیست دانشجویان(درنهایت)");
        this.selectInputFile.addItem("لیست دانشگاه ها");
        this.selectInputFile.addItem("لیست رشته ها");
        this.selectInputFile.addItem("لیست گرایش ها(اختیاری)");
        this.homeView = new JPanel();
        // append component to our home view
        homeView.add(selectInputFile);
        homeView.add(exportFileButton);
        homeView.add(clearAll);
        homeView.add(resultLabel);
        //create output action listener
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

                    try {
                        SrankCoreProcess scp;
                    if (isUnix())
                        scp = new SrankCoreProcess("Core/srank -i Core/input.txt -o  Core/output.txt -f");
                    else
                        scp = new SrankCoreProcess("Core/srank.exe -i Core/input.txt -o  Core/output.txt -f");
                        String msg = scp.getLines();

                        if(msg!="" || msg!=null)
                            JOptionPane.showMessageDialog(null,msg,"warns",JOptionPane.INFORMATION_MESSAGE);
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
                    Iterator<Row> row = exl.getIterator();
                    ArrayList<Row> listOfRows = new ArrayList<Row>();
                    while (row.hasNext()) {
                        listOfRows.add(row.next());
                    }

                    final ListIterator<Row> rows = listOfRows.listIterator();
                    srankControl.setIterator(rows);
                }else{
                    JOptionPane.showMessageDialog(null,
                            "از بارگیری فایل صرف نظر کرده اید.",
                            "پیام",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                flags[3] = true;//todo:temp

                int index = selectInputFile.getSelectedIndex();
                String method = methods.get(index);
                if(method!= null) {
                    flags[index-1]=true;
                    srankControl.setSelectedHeader(headers.get(method));
                    srankControl.setModel(method);
                    srankControl.Convert();
                }else{}
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

    private boolean isUnix(){
       String os = System.getProperty("os.name");
       if (os.contains("Windows") || os.contains("windows"))
           return false;
       return true;
    }
}
