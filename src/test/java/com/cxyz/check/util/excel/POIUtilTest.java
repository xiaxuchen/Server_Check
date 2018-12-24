package com.cxyz.check.util.excel;

import com.cxyz.check.dao.GradeDao;
import com.cxyz.check.dao.TaskDao;
import com.cxyz.check.dao.UserDao;
import com.cxyz.check.entity.CheckRecord;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.TaskInfo;
import com.cxyz.check.entity.User;
import com.cxyz.check.typevalue.CheckRecordResult;
import com.cxyz.check.util.date.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
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

    @Resource
    private TaskDao dao;

    @Resource
    private UserDao uDao;

    @Resource
    private GradeDao gradeDao;

    @Test
    public void createExcel() {

    }

}