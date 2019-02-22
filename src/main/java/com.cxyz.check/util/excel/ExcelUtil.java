package com.cxyz.check.util.excel;

import com.cxyz.check.entity.CheckRecord;
import com.cxyz.check.entity.Lesson;
import com.cxyz.check.entity.Task;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.TaskInfo;
import com.cxyz.check.entity.User;
import com.cxyz.check.typevalue.CheckRecordResult;
import com.cxyz.check.util.excel.config.Config;
import com.cxyz.check.util.excel.config.sub.DefaultConfig;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    public static final int ROW_DATA_COUNT = 18;

    /**
     * 获取考勤的excel
     * @param lesson 课程信息
     * @param users 用户信息
     * @param gradeName 班级名称
     * @return
     */
    public static Workbook getCheckExcel(Lesson lesson, List<User> users, String gradeName) {

        List<Object> info = new ArrayList<>();
        //添加课程编号，课程名，老师名，开课学期，班级等信息
        String num = lesson.getNum();
        if(num == null)
            num = "";
        info.add(num);
        info.add(lesson.getName());
        String name = "";
        if(lesson.getSponsor() != null)
            name = lesson.getSponsor().getName();
        info.add(name);
        info.add("");
        info.add("");
        info.add(gradeName);

        List<List<List<Object>>> datas = new ArrayList<>();
        List<java.util.Date> dates = new ArrayList<>();
        List<Object> list;
        //筛选出有违规记录的completions
        List<TaskCompletion> completions = new ArrayList<>();
        for(Task task:lesson.getTasks())
        {
            List<TaskCompletion> cs = task.getCompletions();
            if(cs == null || cs.isEmpty())
                continue;
            boolean isEmpty = true;
            for(TaskCompletion t:cs)
            {
                //如果考勤结果不为空则说明有记录，否则去除
                if(t.getRecords() != null || !t.getRecords().isEmpty())
                    isEmpty = false;
                else
                    cs.remove(t);
            }

            if(isEmpty)
                continue;
            completions.addAll(cs);
        }
        Collections.sort(completions,(o1, o2) ->
        {
           if(o1.getDate().getTime()>o2.getDate().getTime())
               return 1;
           else if(o1.getDate().getTime() == o2.getDate().getTime())
               return 0;
           else
               return -1;

        });
        System.out.println("---------------");
        completions.forEach(taskCompletion -> {
            System.out.println(taskCompletion.getDate());
        });
        System.out.println("---------------");
        int added = 0;//用来标识是否添加了日期
        int size = completions.size()/ROW_DATA_COUNT+
                ((completions.size()%ROW_DATA_COUNT)==0?0:1);
        int index = 0;
        for(int i = 0;i<size;i++)//将completions分表
        {
            List<List<Object>> data = new ArrayList<>();
            for(User u:users)
            {
                list = new ArrayList<>();
                list.add(u.getId());
                list.add(u.getName());
                list.add(gradeName);
                int cur = 0;
                for(TaskCompletion c:completions)
                {
                    if(cur < index*ROW_DATA_COUNT)
                    {
                        cur++;
                        continue;
                    }


                    if(added == cur)//添加日期
                    {
                        dates.add(c.getDate());
                        added++;
                    }

                    boolean isBad = false;
                    for(CheckRecord r:c.getRecords())
                    {
                        if(r.getStu().getId().equals(u.getId()))
                        {
                            list.add(r.getResult());
                            isBad = true;
                            break;
                        }
                    }
                    if(!isBad)
                        list.add(CheckRecordResult.NORMAL);
                    cur++;
                    if(cur == (index+1)*ROW_DATA_COUNT)//如果cur为18,36...则当做一个表
                    {
                        break;
                    }
                }

                for(int j = list.size();j<ROW_DATA_COUNT+3;j++)
                {
                    if(added == j)
                    {
                        dates.add(null);
                        added++;
                    }
                    list.add(null);
                }
                list.add(null);//添加总评
                data.add(list);
            }
            index++;
            datas.add(data);
        }


        //拼表头
        List<Object> title = new ArrayList();//标题
        title.add("萍乡学院学生考勤记录表");
        List<Object> tail = new ArrayList<>();
        tail.add("注:1.考勤情况记录分迟到早退、旷课、事假和出勤四种情况。标识符号如下:迟到早退△1;旷课 ╳1;事假O1;出勤√(说明:下标1表示旷课节数,依次类推);2.本表可续。");

        int curIndex = 0;
        for(List<List<Object>> l:datas)
        {
            l.add(0,title);
            l.add(1,info);

            ArrayList<Object> header = new ArrayList<>();//表头
            Object[] objects = {"学号", "姓名", "班级"};
            for (int i = 0; i < objects.length; i++) {
                header.add(objects[i]);
            }

            for(int i = curIndex*ROW_DATA_COUNT;i<(curIndex+1)*ROW_DATA_COUNT;i++)
            {
                Date d = null;
                if(i < dates.size())
                d = dates.get(i);

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
            curIndex++;
            l.add(2,header);
            l.add(tail);
        }

        System.out.println(datas);

        Workbook excel = POIUtil.createMultiExcel(datas,false,
                new DefaultConfig() {

            @Override
            public float width(int curSheet, int curCol) {

                switch (curCol)
                {
                    case 0:return 10;
                    case 1:return 12;
                    case 2:return 10;
                    default:{
                        if (curCol == getRowData(curSheet,5).size()-1)
                            return 12;
                    }
                }

                return 4.1f;
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
                            case CheckRecordResult.LATE:
                            case CheckRecordResult.EARLYLEAVE:tag="△1";break;
                            case CheckRecordResult.VACATE:tag="O1";break;
                        }

                        Cell cell = mRow.createCell(curCol);
                        Font font = getWorkbook().createFont();
                        if(!tag.isEmpty())
                        {
                            RichTextString text = getRichTextString(tag);
                            text.applyFont(1,2,font);
                            cell.setCellValue(text);
                        }else {
                            cell.setCellValue(tag);
                        }

                        style(getWorkbook().createCellStyle(),curSheet,curRow,curCol,data,cell);
                        font(font,curSheet,curRow,curCol,data,cell);
                        font.setTypeOffset(Font.SS_SUB);
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
                    Row row1 = sheet.createRow(curRow);
                    Cell cell = row1.createCell(0);
                    value(curSheet, 0, 0, data.get(0), cell);
                    //设置字体
                    Font font = createFont(16,"宋体",true);
                    CellStyle style = createStyle(true,true,font);
                    cell.setCellStyle(style);
                    addMerged(sheet,0, 1, 0, getRecordColCount()-1);
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
                            style.setAlignment(CellStyle.ALIGN_RIGHT);
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
                    RichTextString text = getRichTextString(data.get(0).toString());
                    text.applyFont(41,42,font);
                    text.applyFont(47,48,font);
                    text.applyFont(52,53,font);
                    cell.setCellValue(text);
                    addMerged(sheet,curRow, curRow+2,0,
                            getRecordColCount()-1);
                    return curRow;
                }
                return -1;
            }

            private int getRecordColCount()
            {
                return 22;
            }


        });
        return excel;
    }

}
