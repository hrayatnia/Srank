package nit.rudi.helper;

/**
 * Created by rayatnia on 2017-06-02.
 */
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class WriteExcel {
    private String PathToSave;
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    private int rowNum = 0;
    public WriteExcel(String PathToSave) {
        this.PathToSave = PathToSave;
        System.out.print(PathToSave);
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet("results");


    }
    public void saveExcel(){
        try {
            FileOutputStream outputStream = new FileOutputStream(this.PathToSave);
            this.workbook.write(outputStream);
            this.workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }

    public String getPathToSave() {
        return PathToSave;
    }

    public void setPathToSave(String pathToSave) {
        PathToSave = pathToSave;
    }

    public static XSSFWorkbook getWorkbook() {
        return workbook;
    }

    public static void setWorkbook(XSSFWorkbook workbook) {
        WriteExcel.workbook = workbook;
    }

    public static XSSFSheet getSheet() {
        return sheet;
    }

    public static void setSheet(XSSFSheet sheet) {
        WriteExcel.sheet = sheet;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }
}