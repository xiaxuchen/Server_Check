package com.cxyz.check.util.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;


public class WorkBookUtil {

    /**
     * 字体
     * @param size
     * @param fontName
     * @param isBold
     * @param color
     * @param font
     */
    public static void setFont(int size, String fontName, Boolean isBold,Short color, Font font) {
        if(font == null)
            return;
        font.setFontName(fontName);
        font.setFontHeightInPoints((short) size);
        if(color != null)
            font.setColor(color);
        if(isBold)
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
    }

    /**
     * 四个方向边框
     * @param style
     */
    public static void setBorder(CellStyle style) {
        if (style == null)
            return;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
    }

    /**
     * 四个方向边框
     * @param style
     */
    public static void setBorder(boolean top,boolean bottom,boolean left,boolean right,CellStyle style)
    {
        if (style == null)
            return;
        if(top)
            style.setBorderTop(CellStyle.BORDER_THIN);//上边框
        else
            style.setBorderTop(CellStyle.BORDER_NONE);
        if(bottom)
            style.setBorderBottom(CellStyle.BORDER_THIN);//下边框
        else
            style.setBorderTop(CellStyle.BORDER_NONE);
        if(left)
            style.setBorderLeft(CellStyle.BORDER_THIN);//左边框
        else
            style.setBorderTop(CellStyle.BORDER_NONE);
        if(right)
            style.setBorderRight(CellStyle.BORDER_THIN);//右边框
        else
            style.setBorderTop(CellStyle.BORDER_NONE);
    }

    /**
     * 居中
     * @param style
     */
    public static void center(CellStyle style) {
        if (style == null)
            return;
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
    }

    /**
     * 获取默认样式
     * @param workbook
     * @return
     */
    public static CellStyle getDefaultStyle(Workbook workbook) {
        if(workbook == null)
            return null;
        CellStyle style = workbook.createCellStyle();
        center(style);
        setBorder(style);
        Font font = workbook.createFont();
        style.setFont(font);
        setFont(10,"宋体",false,null,font);
        return style;
    }

    public static CellStyle createCell(Workbook workbook, Cell cell,String value)
    {
        cell.setCellValue(value);
        CellStyle style = getDefaultStyle(workbook);
        cell.setCellStyle(style);
        return style;
    }
}
