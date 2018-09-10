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
        if ("-s".equals(command[1])) {
            processDirectory(command);
            return;
        }
        if (command.length > 3) {
            System.out.println("错误命令！");
            return;
        }
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
        } else if ("-a".equals(command[1])) {
            calculate(command[2], (BufferedReader reader) ->
                    System.out.println("空行数：" + reader.lines()
                            //正则筛选空白字符
                            .filter(s -> s.matches("\\s+") || s.length() <= 1)
                            .count())
            );
            calculate(command[2], (BufferedReader reader) ->
                    System.out.println("代码行数：" + reader.lines()
                            //正则筛选空白字符
                            .filter(s -> !s.contains("//") && !s.matches("\\s+") && s.length() > 1)
                            .count())
            );
            calculate(command[2], (BufferedReader reader) ->
                    System.out.println("注释行数：" + reader.lines()
                            .filter(s -> s.contains("//"))
                            .count())
            );
        } else {
            System.out.println("错误命令！");
        }
    }

    /**
     * 处理目录 -s 命令
     *
     * @param command 输入命令
     */
    private static void processDirectory(String[] command) {
        if (command.length < 4) {
            System.out.println("错误命令！");
            return;
        }
        String address = command[3];
        //获得通配符文件名
        String fileName = address.substring(address.lastIndexOf("\\") + 1);
        //获得目录
        File file = new File(address.substring(0, address.lastIndexOf("\\")));
        //匹配通配符
        Pattern p = Pattern.compile(fileName.replace(".", "\\.")
                .replace("*", ".*")
                .replace("?", ".?"));
        //寻找当前目录以及子目录的所有文件
        List<String> filePaths = getAllFilePaths(file, new ArrayList<>());
        //过滤出目标文件
        filePaths = filePaths.stream()
                .filter(f -> p.matcher(f.substring(f.lastIndexOf("\\") + 1)).matches())
                .collect(toList());
        if (!filePaths.isEmpty()) {
            for (String filePath : filePaths) {
                System.out.println(filePath);
                processFile(new String[]{"", command[2], filePath});
            }
        } else {
            System.out.println("符合条件的文件不存在！");
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

    /**
     * 获得当前目录与子目录的文件路径
     *
     * @param filePath  当前目录
     * @param filePaths 文件路径list
     */
    private static List<String> getAllFilePaths(File filePath, List<String> filePaths) {
        File[] files = filePath.listFiles();
        if (files == null) {
            return filePaths;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                filePaths.add(f.getPath());
                getAllFilePaths(f, filePaths);
            } else {
                filePaths.add(f.getPath());
            }
        }
        return filePaths;
    }
}
