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
public class Minor implements ExcelRowInterface {
    private String name ;
    private String cap;
    private ArrayList<String> req;

    public Minor(){}

    public Minor(String name, String cap, ArrayList<String> req) {
        this.name = name;
        this.cap = cap;
        this.req = req;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public ArrayList<String> getReq() {
        return req;
    }

    public void setReq(ArrayList<String> req) {
        this.req = req;
    }
    public void setReq(String req) {
        this.req = this.breakString(req);
    }

    @Override
    public void createRowFromModel(Row rowNo) {
        return ;
    }

    @Override
    public String getValueForSrank() {
        String result = "";
        result += "minor\t"+this.getName()+"\n";
        result += "minor_msc\t"+this.getName()+"\t"+this.getCap()+"\n";
        for (String item :this.req){
            result += "minor_req\t"+this.getName()+"\t"+item+"\n";
        }
        return result;
    }

    @Override
    public void setRowToModel(Row row) {
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
    private ArrayList<String> breakString(String val){
        String[] words = val.split("\n",0);
        ArrayList<String> output = new ArrayList<>();
        for(String w:words){
            output.add(w);
        }
        return output;
    }

    private boolean setVal(String val,int cc){
        switch(cc){
            case 0:
                this.setName(val);
                return true;
            case 1:
                this.setReq(val);
                return true;
            case 2:
                this.setCap(val);
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
