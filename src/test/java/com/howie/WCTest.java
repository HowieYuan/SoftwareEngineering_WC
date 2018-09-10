package com.howie;


import org.junit.Test;


import java.io.File;

import static com.howie.WC.processFile;

/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description 测试
 * @Date 2018-09-09
 * @Time 20:07
 */
public class WCTest {
    private String projectPath = new File("").getAbsolutePath();
    private String path = projectPath+"\\src\\main\\resources\\testFile\\";

    private String[] testArray = {
            "wc.exe -c " + path + "oneLine.txt",
            "wc.exe -w " + path + "folder\\typical.txt",
            "wc.exe -l " + path + "folder\\typical.txt",
            "wc.exe -s -c " + path + "folder\\typical.txt",
            "wc.exe -a " + path + "folder\\typical.txt",
    };

    @Test
    public void main() throws Exception {
        for (String test : testArray) {
            System.out.println(test);
            String[] commandArray = test.trim().split("\\s+");
            if ("wc.exe".equals(commandArray[0])) {
                processFile(commandArray);
                System.out.print("\n");
            } else {
                System.out.println("错误命令！");
            }
        }
    }

}
