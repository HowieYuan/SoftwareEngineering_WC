package com.howie;


import org.junit.Test;


import static com.howie.WC.processFile;

/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description
 * @Date 2018-09-09
 * @Time 20:07
 */
public class WCTest {
    private String[] testArray = {
            "wc.exe -c C:\\Users\\Administrator\\Desktop\\新建文本文档.txt",
            "wc.exe -w C:\\Users\\Administrator\\Desktop\\新建文本文档.txt",
            "wc.exe -l C:\\Users\\Administrator\\Desktop\\新建文本文档.txt",
            "wc.exe -s -c C:\\Users\\Administrator\\Desktop\\新建*文?.txt",
            "wc.exe -a C:\\Users\\Administrator\\Desktop\\新建文本文档.txt"
    };

    @Test
    public void main() throws Exception {
        for (String test : testArray) {
            System.out.println(test);
            String[] commandArray = test.trim().split("\\s+");
            if ("wc.exe".equals(commandArray[0])) {
                processFile(commandArray);
                System.out.println("\n");
            } else {
                System.out.println("错误命令！");
            }
        }
    }

}
