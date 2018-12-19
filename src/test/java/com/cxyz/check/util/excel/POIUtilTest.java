package com.cxyz.check.util.excel;

import com.cxyz.check.util.date.Date;
import com.cxyz.check.util.date.DateTime;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class POIUtilTest {


    @Test
    public void getBankListByExcel() {
        try {
            System.out.println(POIUtil.getBankListByExcel(Class.class.getResourceAsStream("/other/test.xlsx"),"test.xlsx"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getWorkbook() {

        List<List<Object>> data = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        int i = 0;
        list.add("夏旭晨");
        list.add("男");
        Date date = new Date();
        date.setYear(1999);
        date.setMonth(6);
        date.setDay(13);
        list.add(date.toUDate());
        list.add(null);
        data.add(list);

        try {
            FileOutputStream out = new FileOutputStream("d:/excel.xlsx");
            POIUtil.createExcel(data).write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getCellValue() {
    }

}