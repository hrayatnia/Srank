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
public class University implements ExcelRowInterface{
    private String id;
    private String name;
    private String grade;
    private HashMap<Integer,String> headerIndex;

    public University(){}

    @Override
    public void setHeaderIndex(HashMap<Integer,String> index){
        this.headerIndex = index;
    }

    public University(String id,String name,String Grade){
        this.id = id;
        this.name = name;
        this.grade = Grade;
    }
    public ArrayList<Minor> getPrefList() {
        return prefList;
    }

    public void setPrefList(ArrayList<Minor> prefList) {
        this.prefList = prefList;
    }

    private ArrayList<Minor> prefList;

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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public void createRowFromModel(Row rowNo) {


    }

    @Override
    public String getValueForSrank() {
        String result = "";
        result += "university\t"+this.getName()+"\n";
        result += "university_grade\t"+this.getName()+"\t"+this.getGrade();
        return result+"\n";
    }

    @Override
    public void setRowToModel(Row row,boolean is_primary) {
        Iterator<Cell> cellIterator = row.iterator();
        int cc = 0;
        boolean v = true;
        while (cellIterator.hasNext()) {
            Cell currentCell = cellIterator.next();
            if (currentCell.getCellTypeEnum() == CellType.STRING) {
                 v = this.setVal(currentCell.getStringCellValue(), cc);
            }else if (currentCell.getCellTypeEnum() == CellType.NUMERIC){
                int val = (int) currentCell.getNumericCellValue();
                 v = this.setVal(Integer.toString(val), cc);
            }
            if (!v)
                break;
            ++cc;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private boolean setVal(String val,int cc){
        String attr = this.headerIndex.get(cc);
        if(attr == null)
            return true;
        switch (attr){
            case "id":
                this.setId(val);
                return true;
            case "name":
                this.setName(val);
                return true;
            case "grade":
                this.setGrade(val);
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
}
