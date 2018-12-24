package com.cxyz.check.util.excel;

import com.cxyz.check.typevalue.CheckRecordResult;
import com.cxyz.check.util.excel.config.Config;
import com.cxyz.check.util.excel.config.sub.DefaultConfig;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    public static Workbook getCheckExcel(List<Date> dates,List<Object> info,List<List<List<Object>>> datas) {

        //拼表头
        List<Object> title = new ArrayList();//标题
        title.add("萍乡学院学生考勤记录表");
        List<Object> tail = new ArrayList<>();
        tail.add("注:1.考勤情况记录分迟到早退、旷课、事假和出勤四种情况。标识符号如下:迟到早退△1;旷课 ╳1;事假O1;出勤√(说明:下标1表示旷课节数,依次类推);2.本表可续。");

        Object[] objects = {"学号", "姓名", "班级"};
        ArrayList<Object> header = new ArrayList<>();//表头

        for (int i = 0; i < objects.length; i++) {
                header.add(objects[i]);
        }

        for(Date d:dates)
        {
            if(d == null)
            {
                header.add(null);
                continue;
            }
            Map<String,Integer> map = new HashMap<>();
            map.put("month",d.getMonth()+1);
            map.put("day",d.getDate());
            header.add(map);
        }
        header.add("折合考勤成绩");

        //给数据加表头和尾巴
        for(List<List<Object>> l:datas)
        {
            l.add(0,title);
            l.add(1,info);
            l.add(2,header);
            l.add(tail);
            System.out.println(l);
        }

        Workbook excel = POIUtil.createMultiExcel(datas,true,
                new DefaultConfig() {

            @Override
            public int width(int curSheet, int curCol) {

                if (curCol == 0)
                    return 10;
                if (curCol == 1 || curCol == 2)
                    return 12;
                if (curCol == getRowData(curSheet,5).size()-1)
                    return 12;

                return 4;
            }

            @Override
            public int createCell(int curSheet, int curRow, int curCol, Object data, Row mRow, Sheet sheet, Config config) {
                if(curRow > 4&& curCol>2)
                {
                    try {
                        if(data == null)
                            return -1;
                        int result = Integer.parseInt(data.toString());
                        String tag = "";
                        switch (result)
                        {
                            case CheckRecordResult.ABSENTEEISM:tag="╳1";break;
                            case CheckRecordResult.EARLYLEAVE:tag="△1";break;
                            case CheckRecordResult.VACATE:tag="O1";break;
                        }

                        Cell cell = mRow.createCell(curCol);
                        Font font = getWorkbook().createFont();
                        if(!tag.isEmpty())
                        {
                            HSSFRichTextString text = new HSSFRichTextString(tag);
                            text.applyFont(1,2,font);
                            cell.setCellValue(text);
                        }else {
                            cell.setCellValue(tag);
                        }

                        style(getWorkbook().createCellStyle(),curSheet,curRow,curCol,data,cell);
                        font(font,curSheet,curRow,curCol,data,cell);
                        font.setTypeOffset(HSSFFont.SS_SUB);
                        return curCol;
                    }catch (NumberFormatException e)
                    {
                        return -1;
                    }
                }
                return -1;
            }

            @Override
            public int createRow(int curSheet, int curRow, Sheet sheet, List<Object> data, Config config) {
                if (curRow == 0) {
                    if(getData().get(curSheet).get(4) == null)
                        return -1;
                    Row row1 = sheet.createRow(curRow);
                    Cell cell = row1.createCell(0);
                    value(curSheet, 0, 0, data.get(0), cell);
                    //设置字体
                    Font font = createFont(16,"宋体",true);
                    CellStyle style = createStyle(true,true,font);
                    cell.setCellStyle(style);
                    addMerged(sheet,0, 1, 0, getData().get(curSheet).get(4).size()-1);
                    return 1;
                } else if (curRow == 3) {
                    Row row3 = sheet.createRow(curRow);
                    Row row4 = sheet.createRow(curRow+1);
                    for (int i = 0; i < data.size(); i++) {
                        final Cell cellR2 = row3.createCell(i);
                        final Cell cellR3 = row4.createCell(i);
                        if (i < 3 || i == data.size() - 1) {
                            createDefaultCell(cellR2,data.get(i).toString());
                            createDefaultCell(cellR3,"");
                            addMerged(sheet,curRow, curRow+1, i, i);
                        } else {
                            Map map = ((Map)data.get(i));
                            String month = "",day = "";
                            if(map != null)
                            {
                                month = map.get("month").toString();
                                day = map.get("day").toString();
                            }
                            CellStyle style = getDefaultStyle();
                            style.setAlignment(CellStyle.ALIGN_LEFT);
                            cellR2.setCellStyle(style);
                            cellR3.setCellStyle(style);
                            cellR2.setCellValue(month+"月");
                            cellR3.setCellValue(day+"日");
                        }
                    }
                    return 4;
                }else if(curRow == 2)
                {
                    CellStyle style = createStyle(false,false,null);
                    style.setAlignment(CellStyle.ALIGN_LEFT);
                    int [] array = {0,2,5,10,15,19};
                    String [] keys = {"课程编号","课程名称","任课老师","开课学期","专业","班级"};
                    Row row = sheet.createRow(2);
                    System.out.println(data);
                    for(int i = 0;i<data.size();i++)
                    {
                        Cell cell = row.createCell(array[i]);
                        cell.setCellStyle(style);
                        cell.setCellValue(keys[i]+":"+data.get(i));
                        if(i!=5)
                            addMerged(sheet,2,2,array[i],array[i+1]-1);
                        else
                            addMerged(sheet,2,2,array[i],getRowData(curSheet,curRow).size()-1);
                    }

                    return 2;
                }
                else if(curRow == getData().get(curSheet).size()+1)
                {
                    if(getData().get(curSheet).get(4) == null)
                        return -1;
                    Row row = sheet.createRow(curRow);
                    Cell cell = row.createCell(0);
                    CellStyle style = getWorkbook().createCellStyle();
                    style(style,curSheet,curRow,0,data.get(0),cell);
                    WorkBookUtil.setBorder(false,false,false,false,style);
                    style.setWrapText(true);//自动换行
                    style.setAlignment(CellStyle.ALIGN_LEFT);
                    Font font = getWorkbook().createFont();
                    font(font,curSheet,curRow,0,null,cell);
                    font.setTypeOffset(Font.SS_SUB);
                    HSSFRichTextString text = new HSSFRichTextString(data.get(0).toString());
                    text.applyFont(41,42,font);
                    text.applyFont(47,48,font);
                    text.applyFont(52,53,font);
                    cell.setCellValue(text);
                    addMerged(sheet,curRow, curRow+2,0,
                            getData().get(curSheet).get(5).size()-1);
                    return curRow;
                }
                return -1;
            }


        });
        return excel;
    }

}
