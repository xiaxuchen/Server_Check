package com.cxyz.check.util.filepath;

import java.util.UUID;

public class HashPathUtil {

    public static String getPath(String name)
    {
        String suffix = name.substring(name.lastIndexOf("."));
        StringBuilder path = new StringBuilder();
        int a = name.hashCode();
        //转成二进制1000001111111100001001010
        String bin = Integer.toBinaryString(a);
        //位数 32位
        bin = "0000000000000000000000000000000"+bin;
        bin =bin.substring(bin.length()-32);

        //0xf表示16进制的15，二进制是1111，& a 可以取a的最后一位 1010
        //00000101011011010110110011010100
        int b = a & 0xf;
        bin = Integer.toBinaryString(b);//4的二进制 0100
        bin = "0000000000000000000000000000000"+bin;
        //格式化为 00000000000000000000000000001010
        bin =bin.substring(bin.length()-32);

        //转换成16进制
        String hex = Integer.toHexString(b);

        path.append(hex);


        int c = (a>>4) & 0xf;
        bin = Integer.toBinaryString(c);
        bin = "0000000000000000000000000000000"+bin;
        bin =bin.substring(bin.length()-32);

        hex = Integer.toHexString(c);
        path.append("/").append(hex).append("/").append(UUID.randomUUID().toString().replaceAll("-","")).append(suffix);
        return path.toString();
    }
}
