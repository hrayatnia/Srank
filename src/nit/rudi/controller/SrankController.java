package nit.rudi.controller;


import nit.rudi.helper.ExcelRowInterface;
import nit.rudi.helper.WriteExcel;
import nit.rudi.models.Minor;
import nit.rudi.models.SrankResult;
import nit.rudi.models.Student;
import nit.rudi.models.University;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.*;
import java.util.*;

/**
 * Created by rayatnia on 2017-05-31.
 *
 */
public class SrankController {
    private String model;
    private ListIterator<Row> ListIterator;
    private String[] selectedHeader;//or we can use ArrayList
    private String pathToSave;
    private Row header;
    private HashMap<Integer,String> headerIndex;

    public SrankController(String model, ListIterator<Row> ListIterator,String[] selectedHeader){}
    public SrankController(){
        this.headerIndex = new HashMap<>();
    }
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
        if (this.ListIterator != null)
            if(this.ListIterator.hasNext()) {
                this.header = this.ListIterator.next();
                this.headerIndex = this.extractHeaderIndex();
            }

        String r="";
        SrankConvertor sc = new SrankConvertor();//todo:optimize this (DRY IN HOME CLASS) :|
        switch(model){
            case "University":
                University uni = new University();
                r = this.getSrankValueFromObject(uni,this.ListIterator);
                break;
            case "Student":
                Student student = new Student();
                r = this.getSrankValueFromObject(student,this.ListIterator);
                break;
            case "Major":
                Minor min = new Minor();
                r = this.getSrankValueFromObject(min,this.ListIterator);
                break;
            case "SrankResult":
                processOutput();
                return;
            case "Faculty":
                break;

        }
        sc.wirteStringInputFile(r);
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public ListIterator<Row> getListIterator() {
        return ListIterator;
    }
    public void setIterator(ListIterator<Row> ListIterator) {
        this.ListIterator = ListIterator;
    }
    public String[] getSelectedHeader() {
        return selectedHeader;
    }


    private String getSrankValueFromObject(ExcelRowInterface o, ListIterator<Row> row){
        String result="";
        o.setHeaderIndex(this.headerIndex);
        boolean is_primary = true;
        boolean is_first = true;
        while(row.hasNext()) {
            Row rowVal = row.next();
            o.setRowToModel(rowVal,is_primary);
            if(row.hasNext()){
                is_primary = checkPrimary(row);
                if (is_primary )
                    is_first = true;
            }else{
                is_first = true;
            }
            if (is_first && is_primary)
                result += o.getValueForSrank();
            is_first = false;

        }
        return result;
    }
    private boolean checkPrimary(ListIterator<Row> row){
        Row rowval = row.next();
        boolean temp=false;
        Iterator<Cell> cellIterator = rowval.iterator();
        //todo: check primary key
        if (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
                temp = false;
            else
                temp = true;
        }
        if (row.hasPrevious())
            row.previous();
        return temp;

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

    private HashMap<Integer,String> extractHeaderIndex(){
        HashMap<Integer,String> result = new HashMap<>();
        Iterator<Cell> cellIterator = header.iterator();
        while(cellIterator.hasNext()){
            Cell cell = cellIterator.next();
            if (cell.getCellTypeEnum() == CellType.STRING) {
                //todo: fix this junk. :|
                // we can use array list at all. or write an lambda or everything else
                List<String> temp = Arrays.asList(selectedHeader);
                if (temp.contains(cell.getStringCellValue())){
                    result.put(cell.getColumnIndex(),cell.getStringCellValue());
                }
            }

        }
        return result;
    }
}


