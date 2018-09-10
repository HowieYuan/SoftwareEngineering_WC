package com.howie;

import java.io.BufferedReader;

/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description 计算文件信息函数
 * @Date 2018-09-07
 * @Time 18:38
 */
@FunctionalInterface
interface ProcessFile {
    void process(BufferedReader reader);
}