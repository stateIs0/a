package com.ipzoe.light.test;

import com.alibaba.druid.sql.visitor.functions.Char;

import java.util.*;

/**
 * Created by cxs on 2017/4/14.
 */
public class java {


    public static void main(String args[]) {

        String[] str = new String[]{"Herlo", "dad", "Dqwe", "Pfc", "dhg"};
        List<String> list = new ArrayList<>();
        for (String s : str) {
            list.add(s);
        }
        new java().test(list);

    }


    public List<String> test(List<String> list) {
        char[] one = new char[]{'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'};
        char[] two = new char[]{'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'};
        char[] three = new char[]{'z', 'x', 'c', 'v', 'b', 'n', 'm'};

        List<char[]> charList = new ArrayList<>();
        charList.add(one);
        charList.add(two);
        charList.add(three);

        List<String> listNew = new ArrayList<>();

        // 循环参数数组
        for (String str : list) {
            String sum = "";
            str = str.toLowerCase();

            // 循环字符串处理单个字符
            label:
            for (int i = 0; i < str.length(); i++) {
                char ca = str.charAt(i);

                // 循环3行字符
                for (char[] chars : charList) {
                    // 比较拆分字符和3行字符
                    if (handleForeach(str, ca, chars)) {
                        // 如果正确则说明可以略过后面的比较
                        continue label;
                    }
                }
            }

            if (str.equals(sum)) {
                System.out.println(str);
                listNew.add(sum);
            }
            sum = "";
        }
        return listNew;
    }

    private boolean handleForeach(String str, char ca, char[] chars) {
        String sum = "";
        for (char o : chars) {
            if (ca == o) {
                sum += ca + "";
                return true;
            }
        }
        return false;

    }
}
