package nit.rudi.models;

import nit.rudi.helper.ExcelRowInterface;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * Created by rayatnia on 2017-05-31.
 */
public class Faculty implements ExcelRowInterface {
    private String id;
    private String name;
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

    @Override
    public void setRowToModel(Row row) {

    }

    @Override
    public String getValueForSrank() {
        return null;
    }

    @Override
    public void createRowFromModel(Row rowNo) {
        return ;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void setCell(Row row,int colNum,Object field) {
        Cell cell = row.createCell(colNum);
        if (field instanceof String) {
            cell.setCellValue((String) field);
        }
    }
}
