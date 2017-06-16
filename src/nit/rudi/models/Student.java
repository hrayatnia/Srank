package nit.rudi.models;

import nit.rudi.helper.ExcelRowInterface;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by rayatnia on 2017-05-31.
 */
public class Student implements ExcelRowInterface{
    // todo : we can use Faculty class but we should create search Algorithm
    private String faculty;
    private String id;
    private String name;
    private String lastname;
    private String grade;
    // todo: we can use University class
    private String uni;
    // todo : we can use Minor class
    private ArrayList<String> pref;

    public Student(){}

    public Student(String faculty, String id, String name, String lastname, String grade, String uni, ArrayList<String> pref) {
        this.faculty = faculty;
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.grade = grade;
        this.uni = uni;
        this.pref = pref;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getUni() {
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getLastname() {

        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGrade() {

        return grade;
    }

    public void setGrade(String grade) {

        this.grade = grade;
    }
    public ArrayList<String> getPref() {
        return pref;
    }

    public void setPref(ArrayList<String> pref) {
        this.pref = pref;
    }
    public void setPref(String pref) {
        this.pref = this.breakString(pref);
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public void setRowToModel(Row row) {
        Iterator<Cell> cellIterator = row.iterator();
        int cc = 0;
        boolean v = true;
        while(cellIterator.hasNext())
        {
            Cell currentCell = cellIterator.next();
            if (currentCell.getCellTypeEnum() == CellType.STRING) {
                v = this.setVal(currentCell.getStringCellValue(), cc);
            }else if (currentCell.getCellTypeEnum() == CellType.NUMERIC){
                int val = (int) currentCell.getNumericCellValue();
                v = this.setVal(Integer.toString(val), cc);
            }
            if (!v) {
                break;
            }
            cc++;
        }

    }

    @Override
    public String getValueForSrank() {
        String result = "";
        result += "student\t"+this.getId()+"\n";
        result+= "student_bsc\t"+this.getId()+"\t"+this.faculty+"\n";
        result+= "student_bscgpa\t"+this.getId()+"\t"+this.getGrade()+"\n";
        result+= "student_bscuni\t"+this.getId()+"\t"+this.uni+"\n";
        for (String item:this.pref){
            result+= "student_pref\t"+this.getId()+"\t"+item+"\n";
        }
        return result+"\n";
    }

    private ArrayList<String> breakString(String val){
        String[] words = val.split("\n",0);
        ArrayList<String> output = new ArrayList<>();
        for(String w:words){
            output.add(w);
        }
        return output;
    }
    @Override
    public void createRowFromModel(Row rowNo) {
        //
    }

    private boolean setVal(String val,int cc){
        switch(cc){
            case 0:
                this.setId(val);
                return true;
            case 1:
                this.setName(val);
                return true;
            case 2:
                this.setLastname(val);
                return true;
            case 3:
                this.setFaculty(val);
                return true;
            case 4:
                this.setUni(val);
                return true;
            case 5:
                this.setGrade(val);
                return true;
            case 6:
                this.setPref(val);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void setCell(Row row,int colNum,Object field) {
        Cell cell = row.createCell(colNum);
        if (field instanceof String) {
            cell.setCellValue((String) field);
        }
    }
}
