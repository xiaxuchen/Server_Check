package com.cxyz.check.util.excel.config.sub;

import com.cxyz.check.util.date.DateTime;
import com.cxyz.check.util.excel.config.Config;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class DefaultConfig extends Config {


    @Override
    public boolean createSheet(int curSheet, Sheet sheet, List<List<Object>> data, Config config) {
        return false;
    }

    @Override
    public int createRow(int curSheet, int curRow, Sheet sheet, List<Object> data, Config config) {
        return -1;
    }

    @Override
    public int createCell(int curSheet, int curRow, int curCol, Object data, Row mRow, Sheet sheet, Config config) {
        return -1;
    }

    @Override
    public void value(int curSheet, int curRow, int curCol, Object obj, Cell cell) {
        if(obj instanceof Double)
            cell.setCellValue((Double)obj);
        else if(obj instanceof Boolean)
            cell.setCellValue((Boolean)obj);
        else if(obj instanceof Byte)
            cell.setCellValue((Byte)obj);
        else if(obj instanceof Date)
        {
            cell.setCellValue((Date)obj);
            DateTime dateTime = DateTime.fromUDate((Date) obj);
            String time = dateTime.getDate();
            cell.setCellValue(time);
        }
        else if(obj instanceof Timestamp)
        {
            DateTime dateTime = DateTime.fromTS((Timestamp) obj);
            String time = dateTime.getTime();
            cell.setCellValue(time);
        }else if(obj == null)
            cell.setCellType(Cell.CELL_TYPE_BLANK);
        else
            cell.setCellValue(obj.toString());
    }

    @Override
    public void style(CellStyle style, int curSheet, int curRow, int curCol, Object data, Cell cell) {
        style.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        //style.setWrapText(true);//自动换行
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        //缩进
        //style.setIndention((short)5);
        //文本旋转，这里的取值是从-90到90，而不是0-180度。
        //style.setRotation((short)60);
        cell.setCellStyle(style);
    }

    @Override
    public void font(Font font, int curSheet, int curRow, int curCol, Object data, Cell cell) {
        font.setFontName("宋体");//设置字体名称
        font.setFontHeightInPoints((short)10);//设置字号
        font.setColor(HSSFColor.BLACK.index);//设置字体颜色
        //font.setUnderline(FontFormatting.U_SINGLE);//设置下划线
        //font.setTypeOffset(FontFormatting.SS_SUPER);//设置上标下标
        //font.setStrikeout(true);//设置删除线
    }

    @Override
    public float width(int curSheet, int curCol) {
        return 12;
    }

    @Override
    public float height(int curSheet, int curRow) {
        return 20;
    }
}