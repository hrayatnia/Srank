package nit.rudi.helper;

import org.apache.poi.ss.usermodel.Row;


/**
 * Created by rayatnia on 2017-06-02.
 */
public interface ExcelRowInterface {
    public void setRowToModel(Row row);
    public void createRowFromModel(Row rowNo);
    public String getValueForSrank();
    public void setCell(Row row,int colNum,Object field);
}
