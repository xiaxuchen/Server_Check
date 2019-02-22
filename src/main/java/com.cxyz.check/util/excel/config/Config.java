package com.cxyz.check.util.excel.config;

import com.cxyz.check.util.excel.WorkBookUtil;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.util.List;

/**
 * 创建Excel的配置,继承DefaultConfig重写就好
 */
public abstract class Config{

    private Workbook workbook;

    private List<List<List<Object>>> data;//数据

    public List<List<List<Object>>> getData() {
        return data;
    }

    public void setData(List<List<List<Object>>> data) {
        this.data = data;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    /**
     * 获取页的数据
     * @param sheet 页码
     * @return
     */
    public List<List<Object>> getSheetData(int sheet)
    {
        if(data!=null)
            return data.get(sheet);

        return null;
    }

    /**
     * 获取行数据
     * @param sheet 页码
     * @param row 行号
     * @return
     */
    public List<Object> getRowData(int sheet,int row)
    {
        List<List<Object>> sheetData = getSheetData(sheet);
        if(sheetData!=null)
            return sheetData.get(row);

        return null;
    }

    /**
     * 获取行数据
     * @param sheet 页码
     * @param row 行号
     * @param col 列号
     * @return
     */
    public Object getColData(int sheet,int row,int col)
    {
        List<Object> rowData = getRowData(sheet,row);
        if(rowData!=null)
            return rowData.get(col);

        return null;
    }

    /**
     * 创建自定义的sheet
     * @param curSheet 当前页
     * @param sheet 当前页
     * @param data 数据
     * @param config 配置
     * @return
     */
    public abstract boolean createSheet(int curSheet,Sheet sheet,List<List<Object>> data,Config config);

    /**
     * 自定义row,一般用于标题或特殊的行
     * @param curSheet 当前页
     * @param curRow 当前行
     * @param config 配置
     * @param data 该行的数据
     * @return 当前尾行
     */
    public abstract int createRow(int curSheet,int curRow, Sheet sheet, List<Object> data, Config config);

    /**
     * 自定义cell,一般用于特殊的单元格,比如需要合并的单元格
     * @param curSheet 当前页
     * @param curRow 当前行
     * @param curCol 当前列
     * @param data 当前传人数据
     * @param config 配置
     * @return 当前尾列
     */
    public abstract int createCell(int curSheet,int curRow, int curCol, Object data, Row mRow, Sheet sheet, Config config);

    /**
     * 格式化数据
     * @param curRow 当前行
     * @param curCol 当前列
     * @param data 当前传人数据
     * @param cell 当前单元格
     * @return 返回字符串填入格子中，若返回null则使用默认的处理方式
     */
    public abstract void value(int curSheet,int curRow, int curCol, Object data, Cell cell);

    /**
     * 获取cell的style
     * @param curRow 当前行
     * @param curCol 当前列
     * @param data 当前传人数据
     * @param cell 单元格
     * @return
     */
    public abstract void style(CellStyle style,int curSheet, int curRow, int curCol, Object data, Cell cell);

    /**
     * 获取当前cell的font
     * @param font 字体
     * @param curSheet 当前页
     * @param curRow 当前行
     * @param curCol 当前列
     * @param data 数据
     * @param cell 当前单元格
     */
    public abstract void font(Font font,int curSheet,int curRow, int curCol, Object data, Cell cell);

    /**
     * 设置单元格的宽度
     * @param curSheet 当前页
     * @param curCol 当前列
     * @return 单位:字符个数
     */
    public abstract float width(int curSheet,int curCol);

    /**
     * 设置单元格的高度
     * @param curSheet 当前页
     * @param curRow 当前行
     * @return 单位:点数
     */
    public abstract float height(int curSheet,int curRow);

    /**
     * 添加合并
     * @param sheet 页
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    public void addMerged(Sheet sheet,int firstRow, int lastRow, int firstCol, int lastCol)
    {
        sheet.addMergedRegion(new CellRangeAddress( firstRow,  lastRow,  firstCol,  lastCol));
    }

    /**
     * 字体
     * @param size 字体大小
     * @param fontName 字体名
     * @param isBold 是否粗体
     * @param color 颜色
     */
    public Font createFont(int size, String fontName, Boolean isBold,Short color) {
        Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints((short) size);
        if(color != null)
            font.setColor(color);
        if(isBold)
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        return font;
    }

    /**
     * 字体
     * @param size 字体大小
     * @param fontName 字体名
     * @param color 字体颜色
     */
    public Font createFont(int size, String fontName, Short color) {
        return createFont(size,fontName,false,color);
    }


    /**
     * 字体
     * @param size 字体大小
     * @param fontName 字体名
     * @param isBold 是否粗体
     */
    public Font createFont(int size, String fontName, Boolean isBold) {
        return createFont(size,fontName,isBold,null);
    }

    /**
     * 字体
     * @param size 字体大小
     * @param fontName 字体名
     */
    public Font createFont(int size, String fontName) {
        return createFont(size,fontName,false,null);
    }

    /**
     * 字体
     * @param size 字体大小
     */
    public Font createFont(int size) {
        return createFont(size,"宋体",false,null);
    }



    /**
     * 四个方向边框
     * @param style
     */
    public void setBorder(CellStyle style) {
        WorkBookUtil.setBorder(style);
    }

    /**
     * 四个方向边框
     * @param style
     */
    public void setBorder(boolean top,boolean bottom,boolean left,boolean right,CellStyle style)
    {
        WorkBookUtil.setBorder(top,bottom,left,right,style);
    }

    /**
     * 居中
     * @param style
     */
    public void center(CellStyle style) {
        if (style == null)
            return;
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
    }

    /**
     * @param text
     * @return
     */
    public RichTextString getRichTextString(String text)
    {
        if(getWorkbook() instanceof HSSFWorkbook)
            return new HSSFRichTextString(text);
        else
            return new XSSFRichTextString(text);
    }

    /**
     * 获取默认样式:居中,边框,默认字体
     * @return
     */
    public CellStyle getDefaultStyle() {
        return createStyle(true,true,null);
    }

    /**
     * 创建默认cell
     * @param cell
     * @param value
     * @return
     */
    public CellStyle createDefaultCell(Cell cell,String value)
    {
        cell.setCellValue(value);
        CellStyle style = getDefaultStyle();
        cell.setCellStyle(style);
        return style;
    }

    /**
     * 创建style
     * @param isCenter
     * @param hasBorder
     * @param font
     * @return
     */
    public CellStyle createStyle(boolean isCenter,boolean hasBorder,Font font)
    {
        if(workbook == null)
            return null;
        CellStyle style = workbook.createCellStyle();

        if(isCenter)
            center(style);
        if(hasBorder)
            setBorder(style);
        if(font == null)
            font = createFont(10);
        style.setFont(font);
        return style;
    }

}