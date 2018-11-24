package com.cxyz.check.util.excel;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

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
    }

    @Test
    public void getCellValue() {
    }

}