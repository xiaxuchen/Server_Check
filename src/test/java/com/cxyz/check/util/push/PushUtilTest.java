package com.cxyz.check.util.push;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class PushUtilTest {

    @Test
    public void jpushAndroid() {
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("caonima","caonisma");
        PushUtil.jpushAndroid("111",objectObjectHashMap,"17478102");
    }
}