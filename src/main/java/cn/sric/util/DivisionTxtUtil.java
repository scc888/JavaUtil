package cn.sric.util;


import org.springframework.util.StringUtils;

import java.io.*;

/**
 * @author sunchuanchuan
 * @version V1.0
 * @date 2021/1/19
 * @package cn.sric.activemq.controller
 * @description
 **/
public class DivisionTxtUtil {


    public static void main(String[] args) {
        divisionTxt("E:/usr", "a1", 500000);
    }

    /**
     * 拆分文本文件 按照行数来拆分
     *
     * @param path     文件路径
     * @param fileName 文件名 不需要挂.txt
     * @param line     多少行拆分一个文件
     * @return rownum 行总数, fileNo拆分了几个文件
     */
    public static String divisionTxt(String path, String fileName, Integer line) {
        if (StringUtils.isEmpty(line) || line == 0) {
            return "请填写正确的行数";
        }


        try {
            FileReader read = new FileReader(path + "/" + fileName + ".txt");
            BufferedReader br = new BufferedReader(read);
            String row;

            int rownum = 1;

            int fileNo = 1;
            FileWriter fw = new FileWriter(path + "/" + fileName + fileNo + ".txt");
            while ((row = br.readLine()) != null) {
                rownum++;
                fw.append(row + "\r\n");

                if ((rownum / line) > (fileNo - 1)) {
                    fw.close();
                    fileNo++;
                    fw = new FileWriter(path + "/" + fileName + fileNo + ".txt");
                }
            }
            fw.close();
            System.out.println("rownum=" + rownum + ";fileNo=" + fileNo);
            return "rownum=" + rownum + ";fileNo=" + fileNo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "有点出错了";
    }
}
