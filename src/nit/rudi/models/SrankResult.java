package nit.rudi.models;

import nit.rudi.helper.ExcelRowInterface;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;

/**
 * Created by rayatnia on 2017-05-31.
 */
public class SrankResult implements ExcelRowInterface {
    private String str;

    public SrankResult(){}

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public void createRowFromModel(Row row) {
        int colNo = 0;
        ArrayList<String> result = this.breakString(this.str);
        for(Object i:result){
            this.setCell(row,colNo,i);
            ++colNo;
        }
    }

    @Override
    public void setRowToModel(Row row) {
        return ;
    }

    @Override
    public String getValueForSrank() {
        return null;
    }

    @Override
    public void setCell(Row row,int colNum,Object field) {
        Cell cell = row.createCell(colNum);
        if (field instanceof String) {
            cell.setCellValue((String) field);
        }
    }

    private ArrayList<String> breakString(String val){
        String[] words = val.split("\t",0);
        ArrayList<String> output = new ArrayList<>();
        for(String w:words){
            output.add(w);
        }
        return output;
    }
}
