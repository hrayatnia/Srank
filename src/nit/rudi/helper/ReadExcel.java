package nit.rudi.helper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class ReadExcel {

    private File inputFile;
    private Iterator<Row> iterator;

    public ReadExcel(File inputFile)
    {
        this.inputFile = inputFile;
        try {

            FileInputStream excelFile = new FileInputStream(this.inputFile);
            String filename = this.inputFile.getName();
            String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
            Workbook workbook =  new XSSFWorkbook(excelFile);

//            if(extension.equals("xlsx")) {
//                workbook = ;
//            }else{
//                System.out.println("Extension Error");
//            }

            if(inputFile.isFile() && inputFile.exists())
            {
                System.out.println(
                        "Excel file open successfully.");
            }
            else
            {
                System.out.println(
                        "Error to open Excel file.");
            }
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            this.iterator = iterator;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Iterator<Row> getIterator() {
        return iterator;
    }

    public void setIterator(Iterator<Row> iterator) {
        this.iterator = iterator;
    }

}