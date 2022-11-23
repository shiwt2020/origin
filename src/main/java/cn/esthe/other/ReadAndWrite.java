package cn.esthe.other;

import io.swagger.models.auth.In;

import java.io.*;
import java.util.*;

public class ReadAndWrite {
    public static void main(String[] args) {


    }

    /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     *
     * @param fileName
     * @return
     */
    public static String readAndWriteFile(String fileName) {
        FileInputStream fileInput = null;
        FileOutputStream fileOutput = null;
        try {
            File file = new File(fileName);
            byte[] buffer = new byte[1024];
            fileInput = new FileInputStream(file);
            fileOutput = new FileOutputStream("F:\\Utopia\\copy.txt");
            int len = 0;
            // len表示一次读取到buffers中的数量。
            while ((len = fileInput.read(buffer)) != -1) {
                System.out.println(buffer);
                fileOutput.write(buffer, 0, len);
            }
        } catch (Exception e) {

        } finally {
            try {
                if (fileOutput != null) {
                    fileOutput.close();
                }
                if (fileInput != null) {
                    fileInput.close();
                }
            } catch (IOException e) {

            }
        }
        return null;
    }

    public static void ReaderAndWriter(String fileName){
        FileReader fr=null;
        try {
            // 使用文件名称创建流对象
            fr = new FileReader("F:\\Utopia\\copy1.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
