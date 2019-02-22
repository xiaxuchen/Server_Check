package com.cxyz.check.util.excel;

import com.cxyz.check.util.date.DateTime;
import com.cxyz.check.util.excel.config.Config;
import com.cxyz.check.util.excel.config.sub.DefaultConfig;

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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class POIUtil {

    private final static String excel2003L =".xls";    //2003- 版本的excel
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel


    public  static List<List<String>> getBankListByExcel(InputStream in,String fileName) throws Exception
    {
        return getBankListByExcel(in,fileName,null,null);
    }

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     * @param in excel的流
     * @param fileName 文件名
     * @param colCount 列数
     * @param startRow 开始行数
     * @return
     * @throws IOException
     */
    public  static List<List<String>> getBankListByExcel(InputStream in,String fileName,Integer colCount,Integer startRow) throws Exception{
        List<List<String>> list = null;

        //创建Excel工作薄
        Workbook work = getWorkbook(in,fileName);
        if(null == work){
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        if(startRow == null)
            startRow = 0;

        list = new ArrayList<>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if(sheet==null){continue;}

            //遍历当前sheet中的所有行
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                if(j<startRow)
                    continue;
                row = sheet.getRow(j);
                if(row==null){continue;}
                //遍历所有的列
                List<String> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < (colCount==null?row.getLastCellNum():colCount); y++) {
                    cell = row.getCell(y);
                    if(cell != null)
                        li.add(getCellValue(cell));
                    else
                        li.add("");
                }
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
            if(l == head)
                continue;
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
     * @param datas 数据
     * @param isExcel2007 是否使用2007的excel
     * @param config 配置
     * @return
     */
    public static Workbook createMultiExcel(List<List<List<Object>>> datas, boolean isExcel2007, Config config) {

        if(datas == null || datas.isEmpty())
            throw new RuntimeException("数据为空");

        Workbook workbook;
        //创建workbook
        if (isExcel2007)
            workbook = new XSSFWorkbook();
        else
            workbook = new HSSFWorkbook();
        if (config == null)
            config = new DefaultConfig();
        config.setWorkbook(workbook);
        config.setData(datas);

        Integer currentSheet = 0;


        for (List<List<Object>> data : datas) {
            Sheet sheet = workbook.createSheet("第"+(currentSheet+1)+"张");
            if(config.createSheet(currentSheet,sheet,data,config))
                continue;
            int i = 0;
            int colCount = 0;
            int currentRow = 0; //当前行
            for (List<Object> list : data) {
                int currentCol = 0; //当前列
                int newRow = config.createRow(currentSheet,currentRow, sheet, list, config);
                colCount = Math.max(list.size(), colCount);
                if (newRow >= currentRow) {
                    currentRow = newRow + 1;
                    continue;
                }
                Row row = sheet.createRow(currentRow);
                row.setHeightInPoints(config.height(currentSheet,i));
                int j = 0;
                for (Object obj : list) {
                    int newCol = config.createCell(currentSheet,currentRow, currentCol, obj, row, sheet, config);
                    if (newCol >= currentCol) {
                        currentCol = newCol + 1;
                        continue;
                    }
                    Cell cell = row.createCell(currentCol);
                    CellStyle style = workbook.createCellStyle();
                    config.style(style,currentSheet, i, j, obj, cell);
                    Font font = workbook.createFont();
                    config.font(font,currentSheet,i, j, obj, cell);
                    style.setFont(font);
                    cell.setCellStyle(style);
                    //至少保证有数据，不至于跳过单元格
                    cell.setCellValue(Cell.CELL_TYPE_BLANK);
                    config.value(currentSheet,i, j, obj, cell);
                    j++;
                    currentCol++;
                }
                currentRow++;
                i++;
            }
            //设置单元格width
            for (int k = 0; k < colCount; k++) {
                sheet.setColumnWidth(k, (int)(config.width(currentSheet,k) * 256));
            }
            currentSheet++;
        }
        return workbook;
    }

    public static Workbook createExcel(List<List<Object>> data, boolean isExcel2007, Config config)
    {
        return createMultiExcel(new ArrayList<List<List<Object>>>(){ {add(data);} },isExcel2007,config);
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


}
