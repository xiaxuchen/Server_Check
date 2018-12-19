package com.cxyz.check.util.excel;

import com.cxyz.check.entity.Times;
import com.cxyz.check.util.date.DateTime;
import com.sun.corba.se.spi.orbutil.threadpool.Work;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontFormatting;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.xml.bind.annotation.XmlType;


public class POIUtil {

    private final static String excel2003L =".xls";    //2003- 版本的excel
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     * @param in,fileName
     * @return
     * @throws IOException
     */
    public  static List<List<String>> getBankListByExcel(InputStream in,String fileName) throws Exception{
        List<List<String>> list = null;

        //创建Excel工作薄
        Workbook work = getWorkbook(in,fileName);
        if(null == work){
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        list = new ArrayList<>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if(sheet==null){continue;}

            //遍历当前sheet中的所有行
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if(row==null){continue;}
                System.err.println(j);
                //遍历所有的列
                List<String> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    if(cell != null)
                        li.add(getCellValue(cell));
                    else
                        li.add("");
                }
                System.err.println(li);
                list.add(li);
            }
        }
        in.close();
        return list;
    }

    /**
     * 获取List<Map>对象
     * @param in 文件流
     * @param fileName 文件名
     * @param header 表头所在的行
     * @return
     */
    public static List<Map<String,String>> getMap(InputStream in,String fileName,int header) throws Exception {
        List<List<String>> list = getBankListByExcel(in,fileName);
        List<String> head = list.get(header);
        List<Map<String,String>> mapList = new ArrayList<>();
        Map<String,String> map;
        for(List<String> l:list)
        {
            map = new HashMap<>();
            int i = 0;
            for(String str:l)
            {
                map.put(head.get(i),str);
                i++;
            }
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(excel2003L.equals(fileType)){
            wb = new HSSFWorkbook(inStr);  //2003-
        }else if(excel2007U.equals(fileType)){
            wb = new XSSFWorkbook(inStr);  //2007+
        }else{
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell){
        String value = "";
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if("General".equals(cell.getCellStyle().getDataFormatString())){
                    value = df.format(cell.getNumericCellValue());
                }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){
                    value = sdf.format(cell.getDateCellValue());
                }else{
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * 创建excel文件
     * @param data 数据
     * @param isExcel2007 是否使用2007的excel
     * @param config 配置
     * @return
     */
    public static Workbook createExcel(List<List<Object>> data, boolean isExcel2007,Config config)
    {
        Workbook workbook;
        if(isExcel2007)
            workbook = new HSSFWorkbook();
        else
            workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        if(config == null)
            config = new DefaultConfig();
        int i = 0;
        int colCount = 0;
        for(List<Object> list:data)
        {
            Row row = config.createRow(i,sheet,list,config);
            if(row != null)
            continue;
            row = sheet.createRow(i);
            row.setHeightInPoints(config.height(i));
            int j = 0;
            for(Object obj:list)
            {
                Cell cell = config.createCell(i,j,obj,row,config);
                if(cell!=null)
                    continue;
                cell = row.createCell(j);
                CellStyle style = workbook.createCellStyle();
                config.style(style,i,j,obj,cell);
                Font font = workbook.createFont();
                config.font(font,i,j,obj,cell);
                style.setFont(font);
                cell.setCellStyle(style);
                //至少保证有数据，不至于跳过单元格
                cell.setCellValue("");
                config.value(i,j,obj,cell);
                j++;
            }
            if(colCount<j)
                colCount = j;
            i++;
        }

        //设置单元格width
        for(int k = 0;k<colCount;k++)
        {
            sheet.setColumnWidth(k,config.width(k)*256);
        }

        return workbook;
    }

    /**
     * 使用默认的处理方式，通过data生成2007的excel
     * @param data
     * @return
     */
    public static Workbook createExcel(List<List<Object>> data)
    {
        return createExcel(data,null);
    }

    /**
     * 通过format指定各类对象的格式生成excel2007
     * @param data 数据
     * @param config 配置
     * @return
     */
    public static Workbook createExcel(List<List<Object>>data,Config config)
    {
        return createExcel(data,true,config);
    }

    /**
     * 创建Excel的配置,继承DefaultConfig重写就好
     */
    public interface Config{

        /**
         * 自定义row,一般用于标题或特殊的行
         * @param row 行数
         * @param config 配置
         * @param data 该行的数据
         * @return 行,若为null则采用默认机制
         */
        Row createRow(int row,Sheet sheet,List<Object> data,Config config);

        /**
         * 自定义cell,一般用于特殊的单元格,比如需要合并的单元格
         * @param row 行
         * @param col 列
         * @param data 当前传人数据
         * @param config 配置
         * @return 若为null则采用默认机制
         */
        Cell createCell(int row,int col,Object data,Row mRow,Config config);

        /**
         * 格式化数据
         * @param row 行
         * @param col 列
         * @param data 当前传人数据
         * @param cell 单元格
         * @return 返回字符串填入格子中，若返回null则使用默认的处理方式
         */
        void value(int row,int col,Object data,Cell cell);

        /**
         * 获取cell的style
         * @param row 行
         * @param col 列
         * @param data 当前传人数据
         * @param cell 单元格
         * @return
         */
        void style(CellStyle style,int row,int col,Object data,Cell cell);

        void font(Font font,int row,int col,Object data,Cell cell);

        /**
         * 设置单元格的宽度
         * @param col 列
         * @return 单位:字符个数
         */
        int width(int col);

        /**
         * 设置单元格的高度
         * @param row 行
         * @return 单位:点数
         */
        float height(int row);
    }

    static class DefaultConfig implements Config{

        @Override
        public Row createRow(int row,Sheet sheet,List<Object> data,Config config) {
            return null;
        }

        @Override
        public Cell createCell(int row, int col, Object data,Row mRow,Config config) {
            return null;
        }

        @Override
        public void value(int row, int col, Object obj, Cell cell) {
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
        public void style(CellStyle style,int row, int col, Object data, Cell cell) {
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
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
        public void font(Font font, int row, int col, Object data, Cell cell) {
            font.setFontName("宋体");//设置字体名称
            font.setFontHeightInPoints((short)12);//设置字号
            font.setColor(HSSFColor.BLACK.index);//设置字体颜色
            //font.setUnderline(FontFormatting.U_SINGLE);//设置下划线
            //font.setTypeOffset(FontFormatting.SS_SUPER);//设置上标下标
            //font.setStrikeout(true);//设置删除线
        }

        @Override
        public int width(int col) {
            return 12;
        }

        @Override
        public float height(int row) {
            return 20;
        }
    };


}
