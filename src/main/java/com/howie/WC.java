package com.howie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description
 * @Date 2018-09-06
 * @Time 20:44
 */
public class WC {
    /*
        wc.exe -c C:\Users\Administrator\Desktop\新建文本文档.txt
        wc.exe -w C:\Users\Administrator\Desktop\新建文本文档.txt
        wc.exe -l C:\Users\Administrator\Desktop\新建文本文档.txt
        wc.exe -s -c C:\Users\Administrator\Desktop\新建*文?.txt
        wc.exe -a C:\Users\Administrator\Desktop\新建文本文档.txt
     */

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String[] commandArray = scan.nextLine().trim().split("\\s+");
            if ("wc.exe".equals(commandArray[0])) {
                processFile(commandArray);
            } else {
                System.out.println("错误命令！");
            }
        }
    }

    /**
     * 处理基本命令
     *
     * @param command 输入命令
     */
    static void processFile(String[] command) {
        if ("-c".equals(command[1])) {
            calculate(command[2], (BufferedReader reader) ->
                    System.out.println("字符数：" + reader.lines()
                            .map(s -> s.replaceAll(" ", ""))
                            .mapToInt(String::length)
                            .sum())
            );
        } else if ("-w".equals(command[1])) {
            calculate(command[2], (BufferedReader reader) ->
                    System.out.println("单词数：" + reader.lines()
                            .map(s -> s.split("\\W+"))
                            .flatMap(Arrays::stream)
                            .filter(s -> !s.isEmpty())
                            .count())
            );
        } else if ("-l".equals(command[1])) {
            calculate(command[2], (BufferedReader reader) ->
                    System.out.println("行数：" + reader.lines().count()));
        } else {
            System.out.println("错误命令！");
        }
    }


    /**
     * 根据ProcessFile的实现进行数值计算
     *
     * @param fileAddress 文件路径
     * @param processFile ProcessFile 函数
     */
    private static void calculate(String fileAddress, ProcessFile processFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileAddress));
            processFile.process(reader);
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定的文件!");
        }
    }
}
