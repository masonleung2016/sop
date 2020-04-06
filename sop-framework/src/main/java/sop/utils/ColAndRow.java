package sop.utils;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:26
 * @Package: sop.utils
 */

public class ColAndRow {

    private int startCol;
    
    private int startRow;
    
    private int endCol;
    
    private int endRow;
    
    public ColAndRow() {

    }
    public ColAndRow(int startCol, int startRow, int endCol, int endRow) {
        this.startCol = startCol;
        this.startRow = startRow;
        this.endCol = endCol;
        this.endRow = endRow;
    }
    public ColAndRow(int col, int row) {
        this.startCol = col;
        this.startRow = row;
        this.endCol = col;
        this.endRow = row;
    }

    public int getStartCol() {
        return startCol;
    }

    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndCol() {
        return endCol;
    }

    public void setEndCol(int endCol) {
        this.endCol = endCol;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }
}
