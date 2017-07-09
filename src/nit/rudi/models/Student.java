package nit.rudi.models;

import nit.rudi.helper.ExcelRowInterface;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.HashMap;
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
    private HashMap<Integer,String> headerIndex;
    private boolean is_primary = true ;


    public Student(){
        this.pref = new ArrayList<>();
    }

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
//        this.pref = this.breakString(pref);
        System.out.println(pref);
        this.pref.add(pref);
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public void setRowToModel(Row row,boolean is_primary) {
        this.is_primary = is_primary;
        Iterator<Cell> cellIterator = row.iterator();
        int cc = 0;
        boolean v = true;
        while(cellIterator.hasNext())
        {
            if (this.is_primary) {
                Cell currentCell = cellIterator.next();
                if (currentCell.getCellTypeEnum() == CellType.STRING) {
                    v = this.setVal(currentCell.getStringCellValue(), cc);
                } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                    float val = (float) currentCell.getNumericCellValue();
                    v = this.setVal(Float.toString(val), cc);
                }
                if (!v) {
                    break;
                }
            }else{
                Cell currentCell = cellIterator.next();
                String val = this.headerIndex.get(cc);
                if (val == null){
                    cc++;
                    continue;
                }

                if (val.equals("preference")){
                    System.out.print(cc);
                    System.out.println(val);
                    System.out.print(currentCell.getStringCellValue());
                    this.setVal(currentCell.getStringCellValue(),cc);
                }
            }
            cc++;
        }

    }

    @Override
    public String getValueForSrank() {
        String result = "";
        result += "student\t"+this.getId()+"\n";
        result += "student_name\t"+this.getId()+"\t"+this.getName()+"\t"+this.getLastname()+"\n";
        result+= "student_bsc\t"+this.getId()+"\t"+this.faculty+"\n";
        result+= "student_bscgpa\t"+this.getId()+"\t"+this.getGrade()+"\n";
        result+= "student_bscuni\t"+this.getId()+"\t"+this.uni+"\n";
        for (String item: this.pref){
            result += "student_pref\t"+this.getId()+"\t"+item+"\n";
        }
        return result+"\n";
    }

//    private ArrayList<String> breakString(String val){
//        String[] words = val.split("\n",0);
//        ArrayList<String> output = new ArrayList<>();
//        for(String w:words){
//            output.add(w);
//        }
//        return output;
//    }

    @Override
    public void createRowFromModel(Row rowNo) {
        //
    }

    private boolean setVal(String val,int cc){
        String attr = this.headerIndex.get(cc);
        if(attr == null)
            return true;
        switch(attr){
            case "id":
                this.setId(val);
                return true;
            case "name":
                this.setName(val);
                return true;
            case "lastname":
                this.setLastname(val);
                return true;
            case "faculty":
                this.setFaculty(val);
                return true;
            case "university":
                this.setUni(val);
                return true;
            case "grade":
                this.setGrade(val);
                return true;
            case "preference":
                if(this.is_primary)
                    this.pref.clear();
                this.setPref(val);
                return true;
            default:
                return true;
        }
    }

    @Override
    public void setCell(Row row,int colNum,Object field) {
        Cell cell = row.createCell(colNum);
        if (field instanceof String) {
            cell.setCellValue((String) field);
        }
    }
    @Override
    public void setHeaderIndex(HashMap<Integer,String> index){
        this.headerIndex = index;
    }
}
