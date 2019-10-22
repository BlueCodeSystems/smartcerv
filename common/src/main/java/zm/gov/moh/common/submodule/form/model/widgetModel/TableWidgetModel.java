package zm.gov.moh.common.submodule.form.model.widgetModel;

import zm.gov.moh.common.submodule.form.model.TableData;

public class TableWidgetModel extends AbstractWidgetModel {

    protected int rowSize;
    protected int colSize;
    protected TableData[] tableData;

    public int getRowSize() {
        return rowSize;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public int getColSize() {
        return colSize;
    }

    public void setColSize(int colSize) {
        this.colSize = colSize;
    }

    public TableData[] getTableData() {
        return tableData;
    }

    public void setTableData(TableData[] tableData) {
        this.tableData = tableData;
    }
}
