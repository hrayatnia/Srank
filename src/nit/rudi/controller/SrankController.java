package nit.rudi.controller;


import nit.rudi.helper.ExcelRowInterface;
import nit.rudi.helper.WriteExcel;
import nit.rudi.models.Minor;
import nit.rudi.models.SrankResult;
import nit.rudi.models.Student;
import nit.rudi.models.University;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.*;
import java.util.Iterator;

/**
 * Created by rayatnia on 2017-05-31.
 */
public class SrankController {
    private String model;
    private Iterator<Row> iterator;
    private String[] selectedHeader;
    private String pathToSave;

    public SrankController(String model, Iterator<Row> iterator,String[] selectedHeader){}
    public SrankController(){}

    public void setSelectedHeader(String[] selectedHeader) {
        this.selectedHeader = selectedHeader;
    }

    public String getPathToSave() {
        return pathToSave;
    }

    public void setPathToSave(String pathToSave) {
        this.pathToSave = pathToSave;
    }

    public void Convert(){
        if (this.iterator != null)
            if(this.iterator.hasNext())
                this.iterator.next();

        String r="";
        SrankConvertor sc = new SrankConvertor();//todo:optimize this (DRY IN HOME CLASS) :|
        switch(model){
            case "University":
                University uni = new University();
                r = this.getSrankValueFromObject(uni,this.iterator);
                break;
            case "Student":
                Student student = new Student();
                r = this.getSrankValueFromObject(student,this.iterator);
                break;
            case "Major":
                Minor min = new Minor();
                r = this.getSrankValueFromObject(min,this.iterator);
                break;
            case "SrankResult":
                processOutput();
                return;
            case "Faculty":
                break;

        }
        sc.wirteStringInputFile(r);
    }
    private void processOutput(){
        WriteExcel writeExcel = new WriteExcel(this.pathToSave);
        int rowCount = 0;
        XSSFSheet sheet = writeExcel.getSheet();
        BufferedReader in = this.openOutPutFile();
        SrankResult srankResult = new SrankResult();
        String str;
        try {
            while ((str = in.readLine()) != null) {
                srankResult.setStr(str);
                rowCount++;
                Row row = sheet.createRow(rowCount);
                srankResult.createRowFromModel(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeExcel.saveExcel();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Iterator<Row> getIterator() {
        return iterator;
    }

    public void setIterator(Iterator<Row> iterator) {
        this.iterator = iterator;
    }

    public String[] getSelectedHeader() {
        return selectedHeader;
    }

    private String getSrankValueFromObject(ExcelRowInterface o, Iterator<Row> row){
        String result="";
        while(row.hasNext()) {
            o.setRowToModel(row.next());
            result += o.getValueForSrank();
        }
        return result;
    }

    private BufferedReader openOutPutFile(){
        try {
            File fileDir = new File("./Core/output.txt");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));

            return in;
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
}



//todo
//class Convertor<T>{
//    public String Convert(){
//        return "";
//    }
//}

